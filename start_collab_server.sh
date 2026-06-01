#!/bin/bash
set -e
cd "$(dirname "$0")"

PORT="${1:-5555}"

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

cp "$JAR" ./NotTiled-CollabServer.jar
echo "Built: NotTiled-CollabServer.jar (from $JAR)"
echo "Starting standalone collaboration server on port $PORT..."
exec java -jar NotTiled-CollabServer.jar "$PORT"
