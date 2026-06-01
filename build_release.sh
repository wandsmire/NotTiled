#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"
source "$SCRIPT_DIR/build_env.sh"

echo "Building Release APKs (Play Store & Standalone)..."
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

# Build playStoreApkRelease and standaloneApkRelease
if ./gradlew clean :android:assembleApkRelease; then
    mkdir -p out_release
    VERSION=$(grep "versionName" android/build.gradle | head -n1 | cut -d'"' -f2)
    if [ -z "$VERSION" ]; then
        VERSION="2.0.0"
    fi

    TIMESTAMP=$(date +"%Y%m%d_%H%M")

    # 1. Copy Play Store Release APK
    TYPE_PS="release"
    PS_FILE="NotTiled_${VERSION}_${TYPE_PS}_${TIMESTAMP}.apk"
    
    if [ -f "../NotTiled_build/android/outputs/apk/playStore/apkRelease/android-playStore-apkRelease.apk" ]; then
        cp ../NotTiled_build/android/outputs/apk/playStore/apkRelease/android-playStore-apkRelease.apk "out_release/${PS_FILE}"
    elif [ -f "../NotTiled_build/android/outputs/apk/apkRelease/android-apkRelease.apk" ]; then
        cp ../NotTiled_build/android/outputs/apk/apkRelease/android-apkRelease.apk "out_release/${PS_FILE}"
    else
        SRC_PS=$(find ../NotTiled_build/android/outputs/apk/ -name "*playStore*apkRelease*.apk" -o -name "*android-apkRelease*.apk" | head -n 1)
        if [ -n "$SRC_PS" ]; then
            cp "$SRC_PS" "out_release/${PS_FILE}"
        fi
    fi

    # 2. Copy Standalone Release APK
    TYPE_SA="standalone-release"
    SA_FILE="NotTiled_${VERSION}_${TYPE_SA}_${TIMESTAMP}.apk"
    
    if [ -f "../NotTiled_build/android/outputs/apk/standalone/apkRelease/android-standalone-apkRelease.apk" ]; then
        cp ../NotTiled_build/android/outputs/apk/standalone/apkRelease/android-standalone-apkRelease.apk "out_release/${SA_FILE}"
    else
        SRC_SA=$(find ../NotTiled_build/android/outputs/apk/ -name "*standalone*apkRelease*.apk" | head -n 1)
        if [ -n "$SRC_SA" ]; then
            cp "$SRC_SA" "out_release/${SA_FILE}"
        fi
    fi

    echo "--------------------------------------------------"
    echo "Build complete! Release APKs copied to 'out_release/':"
    echo "  Play Store: out_release/${PS_FILE}"
    echo "  Standalone: out_release/${SA_FILE}"
    echo "--------------------------------------------------"
else
    echo "ERROR: Build failed."
    exit 1
fi
