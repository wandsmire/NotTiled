#!/bin/bash
set -e

# Define directories
NOTTILED_DIR="/home/reza/Documents/NotTiled"
NOT2D_DIR="$NOTTILED_DIR/not2d"

# Export Java 17 path to avoid Gradle compatibility errors with newer Java versions
export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
export PATH="$JAVA_HOME/bin:$PATH"

echo "=== Building Not2D Android Release APK (Java 17) ==="
cd "$NOT2D_DIR"
./gradlew android:assembleRelease

APK_SOURCE="$NOT2D_DIR/android/build/outputs/apk/release/android-release-unsigned.apk"
APK_DEST="$NOTTILED_DIR/android/assets/not2d.apk"

if [ -f "$APK_SOURCE" ]; then
    echo "=== Copying APK to NotTiled assets ==="
    cp "$APK_SOURCE" "$APK_DEST"
    echo "Successfully embedded Not2D APK in NotTiled assets!"
    
    # Also update desktop class path target if it exists
    DESKTOP_DEST="$NOTTILED_DIR/desktop/bin/main/not2d.apk"
    if [ -d "$(dirname "$DESKTOP_DEST")" ]; then
        cp "$APK_SOURCE" "$DESKTOP_DEST"
        echo "Successfully updated desktop runtime assets!"
    fi
else
    echo "Error: Built APK not found at $APK_SOURCE"
    exit 1
fi
