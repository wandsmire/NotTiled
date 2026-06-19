#!/bin/bash
# sync_to_nas.sh - Sync builds, templates, and collab server to NAS
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

NAS_LOCAL="/run/user/1000/gvfs/smb-share:server=mirwanda.local,share=docker/github/mirwanda.com"
NAS_TAILSCALE="/run/user/1000/gvfs/smb-share:server=mirwanda,share=docker/github/mirwanda.com"
NAS_DIR=""
[ -d "$NAS_LOCAL" ] && NAS_DIR="$NAS_LOCAL"
[ -z "$NAS_DIR" ] && [ -d "$NAS_TAILSCALE" ] && NAS_DIR="$NAS_TAILSCALE"

if [ -z "$NAS_DIR" ]; then
    echo "ERROR: NAS not reachable (neither LAN nor Tailscale mounted)."
    exit 1
fi

echo "NAS found at: $NAS_DIR"

echo "Syncing out/..."
cp -u out/*.apk out/*.aab "$NAS_DIR/out/" 2>/dev/null || true

echo "Syncing out_release/..."
cp -u out_release/*.apk out_release/*.aab "$NAS_DIR/out_release/" 2>/dev/null || true

echo "Syncing CollabServer..."
[ -f private/NotTiled-CollabServer.jar ] && cp -u private/NotTiled-CollabServer.jar "$NAS_DIR/"

echo "Done!"
