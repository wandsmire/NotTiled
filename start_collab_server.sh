#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"
source "$SCRIPT_DIR/build_env.sh"

PORT="${1:-5555}"

mkdir -p private

echo "Building standalone collaboration server..."
./gradlew :desktop:distCollabServer

JAR=""
for dir in "../NotTiled_build/desktop/libs" "desktop/build/libs"; do
    if [ -d "$dir" ]; then
        JAR=$(find "$dir" -maxdepth 1 -name '*-collabserver.jar' | head -1)
        [ -n "$JAR" ] && break
    fi
done

if [ -z "$JAR" ] || [ ! -f "$JAR" ]; then
    echo "Build failed: could not find *-collabserver.jar under NotTiled_build/desktop/libs or desktop/build/libs"
    exit 1
fi

cp "$JAR" ./private/NotTiled-CollabServer.jar
echo "Built: private/NotTiled-CollabServer.jar (from $JAR)"

echo "Starting standalone collaboration server on port $PORT..."
exec java -jar private/NotTiled-CollabServer.jar "$PORT"
