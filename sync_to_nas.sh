#!/bin/bash
# sync_to_nas.sh - Sync builds, templates, and collab server to NAS with interactive progress and remote cleanup
# Optimized for rich, informative terminal output, automatic mounting, and remote directory cleanup.

# Colors for terminal styling
NC='\033[0m'
BOLD='\033[1m'
GREEN='\033[32m'
BLUE='\033[34m'
CYAN='\033[36m'
YELLOW='\033[33m'
RED='\033[31m'
GRAY='\033[90m'

# Parse command line options
FORCE_SYNC=false
DRY_RUN=false

while [[ "$#" -gt 0 ]]; do
    case $1 in
        -f|--force) FORCE_SYNC=true ;;
        -d|--dry-run) DRY_RUN=true ;;
        -h|--help)
            echo -e "${BOLD}Usage:${NC} ./sync_to_nas.sh [options]"
            echo ""
            echo -e "${BOLD}Options:${NC}"
            echo -e "  -f, --force      Force sync all files even if up-to-date"
            echo -e "  -d, --dry-run    Show what would be synced/deleted without making changes"
            echo -e "  -h, --help       Show this help message"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            echo "Use -h or --help for usage information."
            exit 1
            ;;
    esac
    shift
done

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Print Header
echo -e "${BOLD}${CYAN}==================================================${NC}"
echo -e "${BOLD}${CYAN}⚡  NotTiled NAS Sync & Cleanup Utility${NC}"
echo -e "${BOLD}${CYAN}==================================================${NC}"

# Check connection and auto-mount if needed
echo -e "${BOLD}${BLUE}🔍 Checking NAS connection...${NC}"
NAS_LOCAL="/run/user/1000/gvfs/smb-share:server=mirwanda.local,share=docker/github/mirwanda.com"
NAS_TAILSCALE="/run/user/1000/gvfs/smb-share:server=mirwanda,share=docker/github/mirwanda.com"
NAS_DIR=""

find_nas() {
    if [ -d "$NAS_LOCAL" ]; then
        NAS_DIR="$NAS_LOCAL"
        return 0
    elif [ -d "$NAS_TAILSCALE" ]; then
        NAS_DIR="$NAS_TAILSCALE"
        return 0
    fi
    return 1
}

# If not mounted, try mounting via gio
if ! find_nas; then
    echo -e "${YELLOW}NAS is not mounted. Attempting to mount automatically...${NC}"
    
    # Try mounting local
    echo -e "Trying to mount Local LAN share (smb://mirwanda.local/docker)..."
    gio mount "smb://mirwanda.local/docker" &>/dev/null || true
    
    # Check if successful
    if find_nas; then
        echo -e "${GREEN}✓ Successfully mounted NAS via Local LAN!${NC}"
    else
        # Try mounting tailscale
        echo -e "Trying to mount Tailscale share (smb://mirwanda/docker)..."
        gio mount "smb://mirwanda/docker" &>/dev/null || true
        
        if find_nas; then
            echo -e "${GREEN}✓ Successfully mounted NAS via Tailscale!${NC}"
        fi
    fi
fi

if [ -z "$NAS_DIR" ]; then
    echo -e "${RED}✗ ERROR: NAS not reachable (neither LAN nor Tailscale could be mounted).${NC}"
    echo -e "Please ensure the NAS is online and your SMB credentials are saved."
    exit 1
fi

if [ "$NAS_DIR" == "$NAS_LOCAL" ]; then
    echo -e "${GREEN}✓ Connected to NAS via Local LAN${NC}"
else
    echo -e "${GREEN}✓ Connected to NAS via Tailscale${NC}"
fi

echo -e "${GRAY}NAS path: $NAS_DIR${NC}\n"

# Verify rsync is installed
if ! command -v rsync &> /dev/null; then
    echo -e "${RED}✗ ERROR: 'rsync' command is not installed.${NC}"
    echo -e "Please install rsync to run this script (e.g. sudo apt install rsync)."
    exit 1
fi

# Helper function to get human-readable file size
get_human_size() {
    local bytes="$1"
    if [ -z "$bytes" ]; then
        echo "0 B"
        return
    fi
    if [ "$bytes" -lt 1024 ]; then
        echo "${bytes} B"
    elif [ "$bytes" -lt 1048576 ]; then
        local kb_integer=$(( bytes / 1024 ))
        local kb_fraction=$(( (bytes % 1024) * 10 / 1024 ))
        echo "${kb_integer}.${kb_fraction} KB"
    else
        local mb_integer=$(( bytes / 1048576 ))
        local mb_fraction=$(( (bytes % 1048576) * 10 / 1048576 ))
        echo "${mb_integer}.${mb_fraction} MB"
    fi
}

# Define sync tasks: type (dir|file) | source | destination | display_name
tasks=(
    "dir|out/|$NAS_DIR/out/|out"
    "dir|out_release/|$NAS_DIR/out_release/|out_release"
)
if [ -d Templates ]; then
    tasks+=("dir|Templates/|$NAS_DIR/Templates/|Templates")
fi
if [ -f private/NotTiled-CollabServer.jar ]; then
    tasks+=("file|private/NotTiled-CollabServer.jar|$NAS_DIR/NotTiled-CollabServer.jar|NotTiled-CollabServer.jar")
fi

to_copy=()
to_delete=()
up_to_date=()

# Scan for files
echo -e "${BOLD}${BLUE}📋 Scanning folders and comparing with NAS...${NC}"

for task in "${tasks[@]}"; do
    IFS='|' read -r type src dest name <<< "$task"
    
    # Ensure destination directory structure exists on NAS (skip in dry run)
    if [ "$DRY_RUN" = false ]; then
        if [ "$type" == "dir" ]; then
            mkdir -p "$dest"
        else
            mkdir -p "$(dirname "$dest")"
        fi
    fi
    
    if [ "$type" == "dir" ]; then
        # Run rsync dry-run with --itemize-changes to find what needs copying and what needs deleting
        changes=$(rsync -utr --delete --dry-run --itemize-changes "$src" "$dest" 2>/dev/null || true)
        
        # Get all local files in this directory to track what is up-to-date
        local_files=()
        while IFS= read -r -d '' f; do
            local_files+=("${f#$src}")
        done < <(find "$src" -type f -print0 2>/dev/null)
        
        # Parse changes
        changed_files=()
        while read -r line; do
            [ -z "$line" ] && continue
            if [[ "$line" == "*deleting"* ]]; then
                file="${line#*deleting   }"
                to_delete+=("$name/$file")
            elif [[ "$line" =~ ^\>f ]]; then
                file="${line:12}"
                size=$(stat -c %s "$src$file" 2>/dev/null || echo 0)
                to_copy+=("$src$file|$dest$file|$size|$name/$file")
                changed_files+=("$file")
            fi
        done <<< "$changes"
        
        # Determine up-to-date files
        for lf in "${local_files[@]}"; do
            is_changed=false
            for cf in "${changed_files[@]}"; do
                if [ "$lf" == "$cf" ]; then
                    is_changed=true
                    break
                fi
            done
            if [ "$is_changed" = false ]; then
                size=$(stat -c %s "$src$lf" 2>/dev/null || echo 0)
                up_to_date+=("$name/$lf|$size")
            fi
        done
        
    else # type == "file"
        src_size=$(stat -c %s "$src" 2>/dev/null || echo 0)
        src_time=$(stat -c %Y "$src" 2>/dev/null || echo 0)
        dest_size=$(stat -c %s "$dest" 2>/dev/null || echo 0)
        dest_time=$(stat -c %Y "$dest" 2>/dev/null || echo 0)
        
        if [ "$FORCE_SYNC" = true ] || [ ! -f "$dest" ] || [ "$src_size" -ne "$dest_size" ] || [ "$src_time" -gt "$dest_time" ]; then
            to_copy+=("$src|$dest|$src_size|$name")
        else
            up_to_date+=("$name|$src_size")
        fi
    fi
done

total_copy=${#to_copy[@]}
total_delete=${#to_delete[@]}
total_skip=${#up_to_date[@]}

echo -e "Scan complete: ${BOLD}$total_copy${NC} to copy, ${BOLD}$total_delete${NC} to delete, ${BOLD}$total_skip${NC} up-to-date.\n"

# 1. Print Up-to-Date list (briefly/dimmed)
if [ $total_skip -gt 0 ]; then
    echo -e "${BOLD}${GRAY}Already Up-to-Date:${NC}"
    for item in "${up_to_date[@]}"; do
        IFS='|' read -r name size <<< "$item"
        h_size=$(get_human_size "$size")
        echo -e "  ${GRAY}✓ $name ($h_size)${NC}"
    done
    echo ""
fi

# 2. Print Deletions list
if [ $total_delete -gt 0 ]; then
    echo -e "${BOLD}${RED}Pending Deletions (Remote Cleanup):${NC}"
    for item in "${to_delete[@]}"; do
        echo -e "  ${RED}✗ [DELETE] $item${NC}"
    done
    echo ""
fi

# 3. Sync files needing update
exit_code=0
if [ $((total_copy + total_delete)) -gt 0 ]; then
    if [ "$DRY_RUN" = true ]; then
        if [ $total_copy -gt 0 ]; then
            echo -e "${BOLD}${YELLOW}Files that would be copied:${NC}"
            for item in "${to_copy[@]}"; do
                IFS='|' read -r src dest size name <<< "$item"
                h_size=$(get_human_size "$size")
                echo -e "  ${YELLOW}➜ $name ($h_size)${NC}"
            done
            echo ""
        fi
        echo -e "${BOLD}${YELLOW}Dry Run complete. No changes were made.${NC}"
    else
        echo -e "${BOLD}${YELLOW}Executing Sync & Cleanup...${NC}"
        start_time=$(date +%s)
        
        for task in "${tasks[@]}"; do
            IFS='|' read -r type src dest name <<< "$task"
            
            # Check if this task has changes (copies or deletions)
            has_changes=false
            if [ "$type" == "dir" ]; then
                for tc in "${to_copy[@]}"; do
                    IFS='|' read -r tc_src tc_dest tc_size tc_name <<< "$tc"
                    if [[ "$tc_name" == "$name/"* ]]; then
                        has_changes=true
                        break
                    fi
                done
                if [ "$has_changes" = false ]; then
                    for td in "${to_delete[@]}"; do
                        if [[ "$td" == "$name/"* ]]; then
                            has_changes=true
                            break
                        fi
                    done
                fi
            else
                for tc in "${to_copy[@]}"; do
                    IFS='|' read -r tc_src tc_dest tc_size tc_name <<< "$tc"
                    if [ "$tc_name" == "$name" ]; then
                        has_changes=true
                        break
                    fi
                done
            fi
            
            if [ "$has_changes" = true ]; then
                echo -e "  ${BOLD}${CYAN}Syncing and cleaning folder: $name/...${NC}"
                if [ "$type" == "dir" ]; then
                    rsync -utr --delete --progress "$src" "$dest"
                else
                    rsync -ut --progress "$src" "$dest"
                fi
                
                if [ ${PIPESTATUS[0]} -eq 0 ] || [ $? -eq 0 ]; then
                    echo -e "  ${GREEN}✓ Completed sync for $name/${NC}\n"
                else
                    echo -e "  ${RED}✗ Failed sync for $name/${NC}\n"
                    exit_code=1
                fi
            else
                echo -e "  ${GRAY}No changes for $name/ (Skipped)${NC}\n"
            fi
        done
        
        end_time=$(date +%s)
        duration=$((end_time - start_time))
        echo -e "${BOLD}${GREEN}Sync completed in ${duration}s!${NC}"
    fi
else
    if [ "$DRY_RUN" = false ]; then
        echo -e "${BOLD}${GREEN}All folders are completely in sync with remote NAS. No actions needed.${NC}"
    fi
fi

if [ $exit_code -eq 0 ]; then
    echo -e "${BOLD}${GREEN}🎉 All sync tasks completed successfully!${NC}"
else
    echo -e "${BOLD}${RED}⚠️ Some sync tasks failed. Please check the output above.${NC}"
fi

exit $exit_code
