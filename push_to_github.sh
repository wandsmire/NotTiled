#!/bin/bash
set -e

MSG="${1:-Update}"

git add -A
git commit -m "$MSG" || echo "Nothing to commit."
git push origin master

echo "Done! Pushed to github.com/wandsmire/NotTiled"
