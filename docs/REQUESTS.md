# NotTiled feature requests and open bugs

This file tracks the feature requests from the Discord #requests channel (April 2020 to now) and the 2026 bug reports that still look open.
Interactive sessions maintain it, and the autocheck bot reads it to answer "is X planned / done?" questions.

Last updated: 2026-10-23

How to read it:
- **Asked by (+N)**: the first person to ask, and how many others asked for the same thing.
- **First asked**: the date and the Discord message id of the first request.
- **Open?** means the item looks open but we have not confirmed it. Check it again before you promise anything.
- Code pointers refer to `core/src/com/mirwanda/nottiled/MyGdxGame.java` unless a file is named.
- When something ships, move it to **Done** and give the version or commit.

## Open requests

### Easy (a few hours: a UI tweak or a small option)

| Request | Asked by (+N) | First asked | Notes |
|---|---|---|---|
| Folder bookmarks / favourite folders in the file dialog | HamSter (+2: a deleted user, User_6577) | 2021-07-24, 868387355181080586 | Nothing in `FileChooser.java` does this yet. It already has Home and App Storage buttons, so you would store a list of paths in prefs and show them as buttons there. |
| "Go to coordinate" / a permanent location marker | HamSter | 2026-05-30, 1510253157605834904 | The other half of that message (removing the drag boundary) is done. You would add a marker point and an input dialog, then draw a crosshair in the render loop. |
| Show the area (W x H) of a macro or tile group in the picker | deleted user | 2021-08-12, 875331259805610024 | This is a label next to the macro name. |
| Show the picked tile's properties at the top of the screen | deleted user | 2021-08-19, 877732170037334097 | The tileset custom-properties shortcut exists, but nothing shows the properties when you pick a tile. You would add a status line when the PICKER tool picks (`selectTool.PICKER` branch, about line 37439). |
| Text labels on the zoom/move buttons | User_6577 | 2024-03-16, 1218597494913761381 | A small text hint while you edit. |
| `android:installLocation="auto"` | Hyasabaru | 2021-09-03, 883227069130084393 | `android/AndroidManifest.xml` has no installLocation attribute. |
| Make the UI editor start from the real default layout | HamSter (+1: VimeeTM had to reinstall after saving a broken layout) | 2026-06-18, 1517172766581854281 | The developer said he hid it under More options. You could also add a "Reset to default" button. |
| Rotate the tiles in a selection | HamSter | 2021-08-06, 873125232012034078 | **Open?** The message is partly cut off. The Picker menu has FLIP but no rotate (`selectTool` enum, line 237). |
| Set an image layer's width/height to stretch it (with a keep-aspect option) | (relayed by WandsMire) | 2021-08-20, 878219454394282006 | `layer.java` stores `imagewidth` from the image file only, and the Image Layer dialog (about line 15700) has no size fields. |
| Go back to the map after "+ Layer" | HamSter | 2026-06-12, 1515037518008160266 | **Open?** It already goes back to the map when you add the layer from the main menu (`addNewLayerFromMainMenu`). It still goes back to the layer manager when you add from the layer list. |
| Tile objects inherit the tile's custom properties | Nick Nik Gr | 2024-07-30, 1267779230654009449 | **Open?** It was asked as a question. Copying the tile's properties when you place an image/tile object would cover it. |
| Option to improve image quality (texture filtering) | deleted user | 2025-01-12, 1327829523776929824 | **Open?** It is not clear what was meant. It may be a linear/nearest filter toggle. |
| Scroll bar on the tileset bar to switch tilesets | HamSter | 2022-08-11, 1007131376794992683 | **Open?** The developer accepted it in 2022. We have not checked whether it was built (long-pressing the tileset name opens a list). |
| Add `gid` to tile objects in the JSON export | Abas | 2024-04-01, 1224171306166128752 | **Open?** It may be fixed by "Improve Tiled TMX/TSX/JSON interoperability" (1415035). Test it again. |

### Medium (a day or two: a new tool or dialog)

| Request | Asked by (+N) | First asked | Notes |
|---|---|---|---|
| Symmetry / mirror painting (2-, 4- or 8-way) | марк (+1: KK) | 2021-02-24, 814148584143454218 ("1/8 mirror"); KK 2026-07-12, 1525758692954210454 | Today there is only the whole-layer Mirror menu (`runhmirror`, `setMirrorMap` about line 12463). Live symmetry would need a hook in the paint path (`updateTileData`). |
| Mirror the map's objects/triggers too | марк | 2020-10-30, 771627047445200907 | The Mirror menu flips tile layers only (`runhmirror` returns early for non-TILE layers). |
| Free-form (non-rectangular) selection and copy | марк | 2020-10-01, 761318553755975720 (again 2021-02-27, 815319945869000785) | Copy works on rectangles only (mapstartSelect/mapendSelect). |
| Undo for Run Macro, adding/removing tilesets and Trace Background | Hyasabaru (+1: a deleted user) | 2021-06-04, 850296328252817429; again 2023-04-04, 1092765395435388978 | Replace is undoable now. **Open?** There is no undo snapshot in `runAutoTiles` or `traceBackground`. |
| UI editor: per-button opacity, hide buttons, key hints on PC, hide button labels | HamSter (relayed by WandsMire) | 2021-02-23, 813675393940717630 | The UI editor exists (`gui.java`) but has none of these options. |
| Swipe left/right to switch the tile and object tool panels | deleted user | 2021-08-11, 874964209602011147 | A gesture plus an animation. |
| Tileset property browser (list the tiles that have a given property) | deleted user | 2021-08-19, 877731081355423784 | A new dialog, similar to the terrain list. |
| Object templates (save objects as a reusable template) | deleted user | 2021-02-17, 811397572966481940 | Similar to Tiled's .tx templates. |
| "Range circle" object (radius r in RW units = 20 x tile units, resized only by typing r) | VimeeTM | 2026-06-12, 1514901810203328623 | This is an ellipse object with a fixed centre, plus a property editor field (`obj.java`). |
| Import Folder: follow the map's tile size and an option to embed as base64 | HamSter | 2022-08-05, 1004945996578566167 | `selfolder` imports as an image collection (`rebuildAllCollectionTilesets`). |
| Native OS file dialog on PC | (relayed by WandsMire) | 2021-02-21, 812942779629830184 | The desktop build uses NotTiled's own browser (fixed for 2.3.1). |
| Context-specific translations (the same English text translated differently in different places) | HamSter | 2026-05-29, 1509837698817327197 | The first half (missing strings) is done. This half needs separate keys in `language.java` and the language files. |
| Road terrain type ("macro roads") | Kend (via VimeeTM) | 2026-06-13, 1515300736190120006 | This would extend macro terrain (`MacroTerrainStore.java`) with road/edge connections. |
| "Tile based on another layer" | (WandsMire's note) | 2021-03-06, 817868996253712445 | **Open?** It is not clear what was meant. It may be autotile rules that read another layer. |
| Switch between the old and new triggers in settings | Tstis | 2021-12-26, 924603351243554816 | **Open?** It is not clear what was meant. |

### Hard (a big feature, a new subsystem or file-format work)

| Request | Asked by (+N) | First asked | Notes |
|---|---|---|---|
| `.tbin` (Stardew Valley) import/export or conversion to .tmx | (relayed by WandsMire) (+1: 1Sekon) | 2021-02-23, 813587206874726420; 2024-05-05, 1236637775164608512 | There is no tbin code. It is a binary format and needs a new reader and writer. |
| A "main" virtual tileset that collects tiles copied from other tilesets, with its own macro terrain | VimeeTM | 2026-06-13, 1515274228151353445 | The developer said "I'll think about it". It touches the GID model and macro terrain. |
| Visual auto-mapping for complex terrain, such as ring-shaped mountain cliffs | RT1_JMJS (via XLR) | 2026-06-17, 1516608313574555729 | Macro terrain covers part of this. The full request is an automapping rules system. |
| Custom keyboard that looks the same everywhere ("like p1, not p2") | HamSter | 2022-08-12, 1007682931738804245 | **Open?** The developer said it needs a lot of work. The screenshots are not in the log. |
| Stop other people from reusing the custom tiles in a published map | Victor | 2023-03-09, 1083414950908792955 | This cannot really be enforced, because the map files are open. The community suggested putting a credit tile in the tileset. We suggest declining it. |

## Open bugs (2026)

These are reports with no "Fixed" reply from NotTiled helper or WandsMire afterwards. **Open?** means it may already be fixed by later work and needs a re-test.

| Bug | Reported by | Date, msg id | Notes |
|---|---|---|---|
| Point objects show resize handles, and dragging them is glitchy | TBL | 2026-10 (#nottiled channel) | Confirmed open. |
| The App storage subtitle is cut off in the "Where should your map be saved?" dialog | (internal) | 2026-10 | Confirmed open. The text is "App storage\nHidden from other apps - deleted if you uninstall" (about line 21264). |
| Custom tilesets / embedded_png don't work after 2.0 | Gamer2809 (+2: KK, Bananomonkey 2.0) | 2026-06-26, 1520150105485738134 | **Open?** WandsMire: "haven't got the time to code yet". KK (1541699811479261204) says 2.0 broke their tilesets. Bananomonkey (1531672659732205698) says the V4 tileset has no tree surface, Import TSX does nothing and the other versions are broken. Re-test with 2.3.3. |
| Collaboration plus an object layer: the loading screen and all buttons disappear | Akib | 2026-07-21, 1529003033923551275 | No reply. |
| Random tiles only work with a 1x1 brush | KAVA ULTRA | 2026-07-28, 1531724703763529919 | The reporter says it has been like this for about 4 years. |
| The menu stays on screen after opening a map | KAVA ULTRA | 2026-07-28, 1531725062720323766 | **Open?** A similar new-map overlay bug was fixed on 2026-06-07. |
| Background problem in a tileset from the gallery | KAVA ULTRA | 2026-07-28, 1531725941175488602 | **Open?** It is not clear what is wrong (the screenshot is not in the log). |
| Auto-tile cannot be created: the naming pop-up gets stuck and Back does nothing | HamSter | 2026-06-08, 1513475416608870531 | **Open?** No reply. The terrain editor was reworked later. |
| Brush/Fill/Rectangle taps go through the Redo button, and object Create goes through Undo | HamSter | 2026-06-08, 1513477000986099773 | **Open?** The same problem in the terrain editor was fixed in 2.3.3. The map tools were not checked. |
| The FPS counter overlaps another default UI element | HamSter | 2026-06-01, 1510884638351036509 | **Open?** WandsMire said "ok thanks". No fix was posted. |
| Loading-screen text overlaps on some devices | HamSter | 2026-05-29, 1509876498046124083 | **Open?** No fix was posted. |

## Done

- Mirror works with the new autotiles: commit "mirror terrain" (2020-09-28)
- Tiles from more than one template in one map (extract tilesets from any .tmx): 1.6.1
- Copy tiles from all layers at once: the Picker copy mode loops over all tile layers (copy code, about line 37540)
- Copy that skips empty tiles: the copy loop has `if (oi == 0) continue`
- Typing Russian/Cyrillic: 2020-10-22
- Layer custom properties: 1.6.2a
- Copy groups of objects/triggers, and multi-select to move/resize/delete: object Copy tool; 2.0 (2026-05-29)
- Show tile X/Y: Preferences > show coordinates
- Grow or shrink the map on the left/top: Margin option; commit "Fix shrinking map width from the left"
- Better Replace Tiles: picked tile is the default (2021); previews, range and layer scope (faa638a); Chance and Remove all (1994ca1, 2.3.3)
- Remember the Tiles vs AutoTiles tab: remove the "forced autotile" tileset property
- Export a selection as a tile group: "added tile group" (2021-08-11)
- A game engine inside the editor: NotTiled platformer template
- Autotiles across different tilesets: 39d8b33, cross-tileset macro terrain
- Revamped terrain editor with deletable terrains: 2.x terrain editor (2026-06)
- Export to CSV: "Export layer(s) as CSV"
- Chinese characters on PC: NotoSansCJK fallback font
- Diamond and circle brushes, size up to 32, a thinner pencil, a list on long-press for the brush/picker/fill/shape/rotation tools: 2026-06-10 / 2026-06-15
- Brush size from the long-press menu, not typed in: 2026-06-10
- Phone Back button goes to the previous menu: Back handling; Import > Back fix (2.3.1)
- High memory use and crashes: 1.7.3
- Cancel buttons (add tiles, new-map location): 2.3.3
- GID labels only on the active layer: `sShowGIDmap` draws `selLayer` only
- Remixed Pixel Dungeon JSON: 1.8.5 (`rpd.java`, PR #10)
- Button to jump to NotTiled's own folder: already there; App Storage button in 2.3.2
- Find missing tiles / show a missing texture when a tileset is deleted: b80b590, orphan rendering
- Warn before deleting a tileset that is in use: "This tileset is used on ..."
- Access to Android data / all files: Standalone build (2.0)
- Separate package name for debug builds: 2.0 (official, debug, release, standalone variants)
- Drag the map past its edge, and pan to objects outside the map: 2.0; 2.3.3
- Adjustable default object size: Preferences (2.0)
- Keep the camera position when a collab push reloads the map: `loadtmx(..., true)` on `mapInformationAll`
- Safe mode after repeated crashes: 2.0 (2026-06-04)
- Lag with large tilesets: tile picker caching (2026-06-05)
- Thicker split lines in the tile splitter, an insert line and auto-scroll when dragging: 2026-06-07
- Shape drag speed follows the zoom level: 2026-06-09
- Object List with search and batch editing: 2.0 Object List
- Long-press Info opens the object directly: 2026-06-14
- Turn automatic rotation off: Preferences > orientation (2026-05-29)
- Collab remembers your name, shows the user list, supports P2P and custom servers: `collab_username` pref, `broadcastRoomUsers`, CollabServer
- Saves go to the real file (SAF), with a warning when another app changed it: 2.3.1; 2.3.3
- In-app crash report pop-up is back: 2.3.3
- Rusted Warfare 1.14/1.15 tilesets: "Rusted Warfare V4" template (155 tilesets)
- Virtual tiles for autotile (same png, several terrains): macro terrain across duplicate tilesets (workaround, 2026-06-10)
- Zoom out further on big maps: zoom limit slider (2026-06-09)
- Tiled JSON export (Phaser): JSON save/load, 1.8.5
- Picker jumps to the picked tile's tileset: "instant list scroll" (5e1acf4)
- Missing translations ("Generate Terrain Set", "Trace Background", ...): 2026-06-07 / 2026-06-13, with English as the fallback for missing strings

## Declined

- iOS version (VimeeTM, 2026-06-10, 1514117500567289957; MangaManzi in 2020): Apple's $99/year fee and no iPhone. WandsMire said he "will consider it", but nothing is planned.
- Properties for several Items layers (BTDbu, 2024-03-16, 1218614173412626624): this is a Rusted Warfare limitation, not a NotTiled one (Tstis).
- Easter eggs / hidden minigames (AIRONIUM, 2021-02-23, 813816157617651812): the NotTiled platformer already fills that role.
