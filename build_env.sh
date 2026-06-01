# Shared environment for NotTiled build scripts.
# Usage: source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/build_env.sh"

if [ -n "${NOTTILED_BUILD_ENV_LOADED:-}" ]; then
    return 0 2>/dev/null || exit 0
fi
NOTTILED_BUILD_ENV_LOADED=1

NOTTILED_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

_nottiled_read_property() {
    local file="$1"
    local key="$2"
    [ -f "$file" ] || return 1
    grep -E "^[[:space:]]*${key}[[:space:]]*=" "$file" | head -1 | cut -d= -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'
}

if [ -z "${ANDROID_HOME:-}" ]; then
    SDK_DIR="$(_nottiled_read_property "$NOTTILED_ROOT/private/local.properties" sdk.dir)" || true
    if [ -z "$SDK_DIR" ]; then
        SDK_DIR="$(_nottiled_read_property "$NOTTILED_ROOT/local.properties" sdk.dir)" || true
    fi
    if [ -n "$SDK_DIR" ]; then
        export ANDROID_HOME="$SDK_DIR"
    fi
fi

if [ -z "${ANDROID_HOME:-}" ]; then
    echo "ERROR: ANDROID_HOME is not set and sdk.dir was not found in private/local.properties." >&2
    echo "Copy private.example/ to private/, set sdk.dir, then run ./setup_private.sh" >&2
    return 1 2>/dev/null || exit 1
fi

if [ -z "${JAVA_HOME:-}" ]; then
    for candidate in \
        /usr/lib/jvm/java-17-openjdk-amd64 \
        /usr/lib/jvm/java-17-openjdk \
        /usr/lib/jvm/java-1.17.0-openjdk-amd64 \
        /usr/lib/jvm/java-1.17.0-openjdk; do
        if [ -d "$candidate" ]; then
            export JAVA_HOME="$candidate"
            break
        fi
    done
fi

if [ -z "${JAVA_HOME:-}" ]; then
    echo "ERROR: JAVA_HOME is not set and Java 17 was not found under /usr/lib/jvm/." >&2
    echo "Install JDK 17 and export JAVA_HOME before running build scripts." >&2
    return 1 2>/dev/null || exit 1
fi

export PATH="$JAVA_HOME/bin:$PATH"
