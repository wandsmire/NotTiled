#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"
source "$SCRIPT_DIR/build_env.sh"

# Pack samples/ -> android/assets/sample.zip (edit files under samples/sample/, not the zip)
if [ ! -d "samples/sample" ]; then
    echo "ERROR: samples/sample not found"
    exit 1
fi
echo "Packing sample.zip..."
rm -f android/assets/sample.zip
(cd samples && zip -r -q ../android/assets/sample.zip sample)

if [ ! -f "newfile-kinds/newfile-kinds.json" ]; then
    echo "ERROR: newfile-kinds/newfile-kinds.json not found"
    exit 1
fi
cp newfile-kinds/newfile-kinds.json android/assets/newfile-kinds.json

echo "Building Release AAB..."
./gradlew :android:bundleRelease
if [ $? -eq 0 ]; then
    mkdir -p out_release
    VERSION="2.0.0"
    TYPE="release"
    ITERATION=1
    while [ -f "out_release/NotTiled_${VERSION}_${TYPE}_${ITERATION}.aab" ]; do
        ITERATION=$((ITERATION+1))
    done
    NEW_FILE="NotTiled_${VERSION}_${TYPE}_${ITERATION}.aab"
    cp ../NotTiled_build/android/outputs/bundle/release/android-release.aab "out_release/${NEW_FILE}"
    echo "----------------------------------------"
    echo "SUCCESS: Release AAB has been copied to: out_release/${NEW_FILE}"
    echo "----------------------------------------"
else
    echo "ERROR: Build failed."
    exit 1
fi
