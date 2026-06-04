#!/bin/bash
# Bump NotTiled version across project files.
#
# Usage:
#   ./versioning.sh 2.3.4
#   ./versioning.sh 2.3.4 --code 106
#
# versionCode defaults to max(major*10000 + minor*100 + patch, currentCode + 1).

set -euo pipefail

usage() {
    echo "Usage: $0 <major.minor.patch> [--code <versionCode>]" >&2
    echo "Example: $0 2.3.4" >&2
    exit 1
}

NEW_VERSION="${1:-}"
case "$NEW_VERSION" in
    [0-9]*.[0-9]*.[0-9]*)
        if ! printf '%s\n' "$NEW_VERSION" | grep -qE '^[0-9]+\.[0-9]+\.[0-9]+$'; then
            usage
        fi
        ;;
    *)
        usage
        ;;
esac

VERSION_CODE=""
shift || true
while [[ $# -gt 0 ]]; do
    case "$1" in
        --code)
            if [[ $# -lt 2 || ! "$2" =~ ^[0-9]+$ ]]; then
                usage
            fi
            VERSION_CODE="$2"
            shift 2
            ;;
        *)
            usage
            ;;
    esac
done

IFS='.' read -r MAJOR MINOR PATCH <<< "$NEW_VERSION"
COMPUTED_CODE=$(( MAJOR * 10000 + MINOR * 100 + PATCH ))

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT"

ANDROID_GRADLE="android/build.gradle"
CURRENT_CODE="$(grep -E '^[[:space:]]*versionCode[[:space:]]+[0-9]+' "$ANDROID_GRADLE" | head -1 | awk '{print $2}')"
CURRENT_NAME="$(grep -E '^[[:space:]]*versionName[[:space:]]+"' "$ANDROID_GRADLE" | head -1 | sed -E 's/.*"([^"]+)".*/\1/')"

if [[ -z "$VERSION_CODE" ]]; then
    if [[ "$COMPUTED_CODE" -le "$CURRENT_CODE" ]]; then
        VERSION_CODE=$(( CURRENT_CODE + 1 ))
    else
        VERSION_CODE="$COMPUTED_CODE"
    fi
fi

echo "Updating version $CURRENT_NAME ($CURRENT_CODE) -> $NEW_VERSION ($VERSION_CODE)"
echo

echo "android/build.gradle"
sed -i -E "s/^([[:space:]]*versionCode[[:space:]]+)[0-9]+/\1$VERSION_CODE/" "$ANDROID_GRADLE"
sed -i -E "s/^([[:space:]]*versionName[[:space:]]+\")[^\"]+(\")/\1$NEW_VERSION\2/" "$ANDROID_GRADLE"

echo "build.gradle"
sed -i -E "s/^(version = ')[^']+(')/\1$NEW_VERSION\2/" build.gradle

echo "core/src/com/mirwanda/nottiled/nullInterface.java"
sed -i -E '/public String getVersione\(\)/,/^    \}/ s/return "[^"]+"/return "'"$NEW_VERSION"'"/' \
    core/src/com/mirwanda/nottiled/nullInterface.java

echo "README.md"
sed -i -E "s/^Latest version: .*/Latest version: $NEW_VERSION/" README.md

if [[ -f ios/robovm.properties ]]; then
    echo "ios/robovm.properties"
    sed -i -E "s/^app.version=.*/app.version=$NEW_VERSION/" ios/robovm.properties
    sed -i -E "s/^app.build=.*/app.build=$VERSION_CODE/" ios/robovm.properties
fi

if [[ -f deploy_to_phone.sh ]]; then
    echo "deploy_to_phone.sh"
    sed -i -E "s/^VERSION=\"[^\"]+\"/VERSION=\"$NEW_VERSION\"/" deploy_to_phone.sh
fi

echo
echo "Done. Version is now $NEW_VERSION (versionCode $VERSION_CODE)."
