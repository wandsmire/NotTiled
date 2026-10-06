#!/bin/bash
# Bump NotTiled version across project files.
#
# Usage:
#   ./versioning.sh 2.3.4
#   ./versioning.sh 2.3.4 --code 2030405
#
# versionCode is MMmmppbb: major, minor, patch, build (2.3.4 build 1 = 2030401).
# A new version starts at build 01; re-running with the current version bumps the build.

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
BASE_CODE=$(( MAJOR * 1000000 + MINOR * 10000 + PATCH * 100 ))

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT"

ANDROID_GRADLE="android/build.gradle"
CURRENT_CODE="$(grep -E '^[[:space:]]*versionCode[[:space:]]+[0-9]+' "$ANDROID_GRADLE" | head -1 | awk '{print $2}')"
CURRENT_NAME="$(grep -E '^[[:space:]]*versionName[[:space:]]+"' "$ANDROID_GRADLE" | head -1 | sed -E 's/.*"([^"]+)".*/\1/')"

if [[ -z "$VERSION_CODE" ]]; then
    if [[ $(( CURRENT_CODE / 100 * 100 )) -eq "$BASE_CODE" ]]; then
        VERSION_CODE=$(( CURRENT_CODE + 1 ))
    else
        VERSION_CODE=$(( BASE_CODE + 1 ))
    fi
    if [[ "$VERSION_CODE" -le "$CURRENT_CODE" ]]; then
        echo "versionCode $VERSION_CODE would not be higher than the current $CURRENT_CODE; pass --code." >&2
        exit 1
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
