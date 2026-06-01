#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"
source "$SCRIPT_DIR/build_env.sh"

NOT2D_DIR="$NOTTILED_ROOT/not2d"

echo "=== Building Not2D Android Release APK (Java 17) ==="
cd "$NOT2D_DIR"
./gradlew android:assembleRelease

APK_SOURCE="$NOT2D_DIR/android/build/outputs/apk/release/android-release-unsigned.apk"
APK_DEST="$NOTTILED_ROOT/android/assets/not2d.apk"

if [ -f "$APK_SOURCE" ]; then
    echo "=== Copying APK to NotTiled assets ==="
    cp "$APK_SOURCE" "$APK_DEST"
    echo "Successfully embedded Not2D APK in NotTiled assets!"
    
    # Also update desktop class path target if it exists
    DESKTOP_DEST="$NOTTILED_ROOT/desktop/bin/main/not2d.apk"
    if [ -d "$(dirname "$DESKTOP_DEST")" ]; then
        cp "$APK_SOURCE" "$DESKTOP_DEST"
        echo "Successfully updated desktop runtime assets!"
    fi
else
    echo "Error: Built APK not found at $APK_SOURCE"
    exit 1
fi
