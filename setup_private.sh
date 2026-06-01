#!/bin/bash
# Links private/ into the places Gradle and the app expect local files.
# After cloning the repo: copy your backed-up private/ folder here, then run this once.
set -e
cd "$(dirname "$0")"

mkdir -p private/keystore android/assets

if [ ! -f private/local.properties ]; then
    if [ -f private.example/local.properties.example ]; then
        cp private.example/local.properties.example private/local.properties
        echo "Created private/local.properties from template — edit it with your SDK path and signing passwords."
    else
        echo "Missing private/local.properties. Copy private.example/ to private/ and fill in your values."
        exit 1
    fi
fi

ln -sfn "$(pwd)/private/local.properties" local.properties

if [ -f private/export.keystore ]; then
    ln -sfn "$(pwd)/private/export.keystore" android/assets/export.keystore
else
    echo "Note: private/export.keystore not found (needed for in-app export signing)."
fi

if [ -f private/google-services.json ]; then
    ln -sfn "$(pwd)/private/google-services.json" android/google-services.json
fi

echo "private/ is linked for builds. Back up the whole private/ folder to keep secrets safe."
