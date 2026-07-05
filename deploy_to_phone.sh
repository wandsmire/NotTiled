#!/bin/bash
# deploy_to_phone.sh
# Builds (optional), installs, and launches NotTiled on a connected Android device.
#
# Usage:
#   ./deploy_to_phone.sh            # install latest APK from out/ and launch
#   ./deploy_to_phone.sh --build    # build first, then install latest and launch
#   ./deploy_to_phone.sh --release  # use the release APK instead of debug

set -e

BASE_PACKAGE="com.mirwanda.nottiled"
APP_ACTIVITY=".MainActivity"
VERSION="2.3.1"
# Collect types to deploy
TYPES=()
BUILD=false

# Parse args
for arg in "$@"; do
    case "$arg" in
        debug|release|sdebug|srelease)
            TYPES+=("$arg")
            ;;
    esac
done

# Default to sdebug if no type is specified
if [ ${#TYPES[@]} -eq 0 ]; then
    TYPES+=("debug")
fi

# Always build
echo "==> Building..."
HAS_DEBUG=false
HAS_RELEASE=false
for t in "${TYPES[@]}"; do
    if [ "$t" = "debug" ] || [ "$t" = "sdebug" ]; then
        HAS_DEBUG=true
    fi
    if [ "$t" = "release" ] || [ "$t" = "srelease" ]; then
        HAS_RELEASE=true
    fi
done
if [ "$HAS_DEBUG" = true ]; then
    ./build_debug.sh
fi
if [ "$HAS_RELEASE" = true ]; then
    ./build_release.sh
fi

# Check ADB is available
if ! command -v adb &>/dev/null; then
    echo "ERROR: 'adb' not found. Install Android platform-tools and add to PATH."
    exit 1
fi

# Get all connected devices
DEVICE_IDS=($(adb devices | awk '/device$/ {print $1}'))

if [ ${#DEVICE_IDS[@]} -eq 0 ]; then
    echo "ERROR: No device connected. Enable USB debugging and connect your phone."
    exit 1
fi

echo "==> Found ${#DEVICE_IDS[@]} device(s): ${DEVICE_IDS[*]}"

# Process each requested variant
for t in "${TYPES[@]}"; do
    case "$t" in
        debug)
            APK_TYPE="debug"
            FOLDER="out"
            APP_PACKAGE="com.mirwanda.nottiled.debug"
            ;;
        release)
            APK_TYPE="release"
            FOLDER="out_release"
            APP_PACKAGE="com.mirwanda.nottiled.release"
            ;;
        sdebug)
            APK_TYPE="standalone-debug"
            FOLDER="out"
            APP_PACKAGE="com.mirwanda.nottiled.standalone.debug"
            ;;
        srelease)
            APK_TYPE="standalone-release"
            FOLDER="out_release"
            APP_PACKAGE="com.mirwanda.nottiled.standalone.release"
            ;;
    esac

    # Find the latest APK of the requested type
    LATEST_APK=$(ls -1 ${FOLDER}/NotTiled_${VERSION}_${APK_TYPE}_*.apk 2>/dev/null | sort -V | tail -1)

    if [ -z "$LATEST_APK" ]; then
        echo "ERROR: No ${APK_TYPE} APK found in ${FOLDER}/. Run the appropriate build script first."
        exit 1
    fi

    echo "==> Deploying type: $t"
    echo "==> Using APK: $LATEST_APK"

    for DEVICE_ID in "${DEVICE_IDS[@]}"; do
        echo "==> Deploying to device: $DEVICE_ID"
        echo "==> Waiting for device..."
        adb -s "$DEVICE_ID" wait-for-device

        # Install APK (replace existing)
        echo "==> Installing $LATEST_APK on $DEVICE_ID..."
        adb -s "$DEVICE_ID" install -r "$LATEST_APK"

        # Launch the app
        echo "==> Launching $APP_PACKAGE on $DEVICE_ID..."
        adb -s "$DEVICE_ID" shell am start -n "${APP_PACKAGE}/${BASE_PACKAGE}${APP_ACTIVITY}"
        echo ""
    done
done

echo "==> Done! All requested builds have been deployed and launched."
