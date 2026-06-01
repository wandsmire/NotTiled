# New file picker (source)

Edit **`newfile-kinds.json`** to change the “What do you want to create?” screen.

| Field | Meaning |
|-------|---------|
| `title` | Heading at the top of the screen |
| `kinds[].title` | Row label |
| `kinds[].description` | Short help text under the label |
| `kinds[].template` | Folder name under `samples/sample/template/` (empty string = blank map, no template) |
| `kinds[].icon` | Path under `android/assets/` (e.g. `images/map.png`) |

`build_debug.sh`, `build_release.sh`, and `build_aab.sh` copy this file to `android/assets/newfile-kinds.json` before Gradle runs.
