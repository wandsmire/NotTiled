#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"
source "$SCRIPT_DIR/build_env.sh"

echo "Building Debug APKs (Play Store & Standalone)..."
chmod +x ./gradlew

# Run python script to stamp icons
if [ -f "./stamp_icons.py" ]; then
    python3 ./stamp_icons.py
fi

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

if ./gradlew clean :android:assembleDebug; then
    mkdir -p out
    VERSION=$(grep "versionName" android/build.gradle | head -n1 | cut -d'"' -f2)
    if [ -z "$VERSION" ]; then
        VERSION="2.0.0"
    fi

    TIMESTAMP=$(date +"%Y%m%d_%H%M")

    # 1. Copy Play Store Debug APK
    TYPE_PS="debug"
    PS_FILE="NotTiled_${VERSION}_${TYPE_PS}_${TIMESTAMP}.apk"
    
    if [ -f "../NotTiled_build/android/outputs/apk/playStore/debug/android-playStore-debug.apk" ]; then
        cp ../NotTiled_build/android/outputs/apk/playStore/debug/android-playStore-debug.apk "out/${PS_FILE}"
    else
        SRC_PS=$(find ../NotTiled_build/android/outputs/apk/ -name "*playStore*debug*.apk" -o -name "*android-debug*.apk" | head -n 1)
        if [ -n "$SRC_PS" ]; then
            cp "$SRC_PS" "out/${PS_FILE}"
        fi
    fi

    # 2. Copy Standalone Debug APK
    TYPE_SA="standalone-debug"
    SA_FILE="NotTiled_${VERSION}_${TYPE_SA}_${TIMESTAMP}.apk"
    
    if [ -f "../NotTiled_build/android/outputs/apk/standalone/debug/android-standalone-debug.apk" ]; then
        cp ../NotTiled_build/android/outputs/apk/standalone/debug/android-standalone-debug.apk "out/${SA_FILE}"
    else
        SRC_SA=$(find ../NotTiled_build/android/outputs/apk/ -name "*standalone*debug*.apk" | head -n 1)
        if [ -n "$SRC_SA" ]; then
            cp "$SRC_SA" "out/${SA_FILE}"
        fi
    fi

    echo "--------------------------------------------------"
    echo "Build complete! Debug APKs copied to 'out/':"
    echo "  Play Store: out/${PS_FILE}"
    echo "  Standalone: out/${SA_FILE}"
    echo "--------------------------------------------------"

    # Keep only the latest Play Store and Standalone debug APKs in out/
    shopt -s nullglob
    for f in out/NotTiled_*_debug_*.apk; do
        case "$f" in
            *standalone*) ;;
            *) [ "$f" = "out/${PS_FILE}" ] || rm -f "$f" ;;
        esac
    done
    for f in out/NotTiled_*_standalone-debug_*.apk; do
        [ "$f" = "out/${SA_FILE}" ] || rm -f "$f"
    done
    shopt -u nullglob

else
    echo "ERROR: Build failed."
    exit 1
fi
