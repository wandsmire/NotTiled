# NotTiled use-case growth roadmap

Research on **who NotTiled can help** and **what workflows to lean into**, beyond engine export formats. Complements [EXPORT_FORMAT_GROWTH_ROADMAP.md](./EXPORT_FORMAT_GROWTH_ROADMAP.md) (how maps leave the app) and [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md) (TMX/JSON correctness).

**Core insight:** NotTiled is not only a “mobile Tiled clone.” It is a **touch-first creative studio** for grid-based work: maps, pixels, music, procgen, playtesting, and community modding—often on devices where desktop Tiled is awkward or unavailable.

**Last reviewed:** 2026-06-03

---

## Legend

| Tier | Meaning |
|------|---------|
| **Tier 1** | Strong fit today; high growth potential with messaging + small polish |
| **Tier 2** | Real audience; needs clearer UX, templates, or docs to convert |
| **Tier 3** | Possible but weak fit, crowded market, or heavy feature gap |

| Size | Rough effort to improve capture of this audience |
|------|--------------------------------------------------|
| **S** | Copy, templates, tutorials, store listing |
| **M** | Workflow polish, export presets, sample projects |
| **L** | New sub-product features or integrations |

---

## What NotTiled already supports (inventory)

| Capability | Where it shows up |
|------------|-------------------|
| TMX / JSON / Lua / PNG export | Export menu, save |
| Touch-optimized map editing | Core app |
| Rusted Warfare templates & tutorials | New file kinds, samples, Steam community guides |
| Remixed Dungeon export | Custom JSON pipeline |
| Procedural map generation | Dungeon / cellular generators |
| Pixel art mode | Pixel Editor template, tutorial 05 |
| Music → MIDI | Music composer template, `exporttomidi` |
| Not2D no-code games + playtest | Embedded framework, tutorial 06 |
| Standalone APK export | Export menu (Android) |
| Online templates | Downloadable template browser |
| Real-time collaboration rooms | Create/join room UI |
| Object templates / property export | Template export, autotiles/macros |
| Built-in PNG / tileset tooling | Import, selection-as-tileset, generators |

Play Store positioning already mentions: custom game maps, pixel art, MIDI, photo mosaics, tabletop RPG maps, mini games, cross-stitch—see [NotTiled on Google Play](https://play.google.com/store/apps/details?id=com.mirwanda.nottiled).

---

## Tier 1 — Strongest audiences (lead with these)

### 1. Game modding & custom maps (community-first)

**Who:** Players and hobbyist creators for games that accept Tiled-family maps, especially on **phone/tablet**.

| Community | Why NotTiled fits | Today |
|-----------|-------------------|--------|
| **Rusted Warfare** | Official community guides cite NotTiled alongside Tiled; RW needs cross-platform `.tmx`; huge Workshop map count | Templates V2/V3, RW tutorials, autotiles |
| **Other TMX games** | Same workflow: design layers, objects, spawn points, export TMX | Generic TMX/JSON |
| **Remixed Dungeon** | Dedicated export path | `exporttorpd` |

**Growth levers (S–M):**

- “Supported games” page: RW, Remixed Dungeon, + engines that import TMX (link to export roadmap).
- RW-focused onboarding: skirmish vs mission checklist (layers, units, `set` layer).
- Share maps via Discord/Workshop with **export checklist** (embedded tilesets, relative paths).

**References:** [RW skirmish map guide (mentions NotTiled)](https://steamcommunity.com/sharedfiles/filedetails/?id=3530330290), [RW Workshop maps](https://steamcommunity.com/app/647960/workshop/)

---

### 2. Mobile-first indie & game-jam level design

**Who:** Solo devs who think of levels on the go, finish in Godot/Phaser/Love2D/GDevelop on PC.

**Workflow:** Sketch layout on phone → export JSON/Lua/TMX → import in engine (see export roadmap).

**Why NotTiled vs desktop Tiled:** No laptop required for iteration; generators for quick drafts; touch painting for terrain.

**Growth levers (S–M):**

- Preset export profiles: “Phaser”, “Love2D”, “GDevelop”, “Godot import”.
- Short “commute workflow” tutorial: new empty map → paint → export JSON → open in engine.
- Game jam template: small map size, embedded tileset, object layer for spawns.

**References:** [Tiled as general level editor](https://doc.mapeditor.org/en/stable/manual/introduction/), [Phaser + Tiled JSON](https://docs.phaser.io/api-documentation/class/tilemaps-tilemap)

---

### 3. No-code / beginner game making (Not2D)

**Who:** People who want a **game** without learning an engine—especially younger users and non-programmers.

**Today:** Not2D framework, platformer tutorial, in-editor playtest, property-driven logic, Standalone APK export.

**Growth levers (M–L):**

- Restore/promote **NotTiled platformer** in new-file picker (tutorial exists; picker currently shows RW / pixel / empty).
- “Publish APK” guided flow: playtest → export → share.
- Sample games gallery (itch-style) tied to online templates.

**References:** [not2d/README.md](../not2d/README.md)

---

### 4. Tabletop RPG & battlemap design (export as image)

**Who:** DMs and players building **dungeon/overworld battlemaps** on tablet; export PNG for Roll20, Foundry, or print.

**Market:** Mobile competitors exist ([Tiled Map Maker](https://play.google.com/store/apps/details?id=com.dsstudio.tiledmapmaker), [VTT RPG Manager dungeon builder](https://virtual-tabletop-rpg-manager.com/android-vtt-dungeon-builder/)); browser tools like [Dungeon Scrawl](https://www.dungeonscrawl.com/) dominate schematic maps.

**NotTiled angle:** Tile-accurate grids, reusable tilesets, layers (floor / walls / objects), **Export to PNG**, procgen for dungeon drafts.

**Growth levers (S–M):**

- TTRPG template: square grid, 20×20 or 30×30, dungeon tileset, object layer for doors/traps.
- Export presets: 70 px/cell, 140 px/cell filenames (VTT-friendly naming).
- Tutorial: “Battlemap in 10 minutes” → PNG to Discord/VTT.

**Gap vs competitors:** No fog-of-war, tokens, or PDF multi-page export (Tier 3 unless partnered).

---

## Tier 2 — Real audiences; need clearer product story

### 5. Pixel art, sprites, and animation sheets

**Who:** Hobby pixel artists; devs making placeholder or final 8–32 px art on mobile.

**Today:** Pixel Editor kind, palette/swatch, layer-based animation (redo long-press), export PNG / tileset.

**Growth levers (M):**

- Promote in new-file picker (already partially there).
- Export “spritesheet” preset: fixed grid, transparent PNG.
- Link to GB Studio / engine import as **background or tileset PNG**.

---

### 6. Education & workshops

**Who:** Teachers, coding clubs, STEM camps—**game design without install friction** on school devices.

**Competition:** Browser tools ([PixLab](https://pixlab.io/tilemap-2d-level-editor), [PixelForge](https://www.pixelforgegames.net/), [GDevelop for education](https://gdevelop.io/)) run on Chromebooks without APK install.

**NotTiled angle:** Offline-capable Android app; structured tutorials; collaboration rooms for pair/group map building; free/no ads.

**Growth levers (M–L):**

- “Classroom mode” doc: same room join, template map, export PNG for show-and-tell.
- Teacher one-pager: learning goals (grids, layers, coordinates, design iteration).
- Optional: export to GDevelop-friendly JSON as homework handoff.

---

### 7. Procedural / rapid map prototyping

**Who:** Designers who want a **starting dungeon or island**, then hand-edit.

**Today:** Dungeon generator, cellular automata (noise4j).

**Growth levers (S–M):**

- One-tap “Generate dungeon” in new-file flow with size presets.
- Document seed + regenerate workflow for game jams.
- Pair with TTRPG (“random dungeon Tuesday”) and roguelike dev prototypes.

---

### 8. Music sketching (MIDI composer)

**Who:** Curious users; game devs needing placeholder BGM; education (grid = pitch/time).

**Today:** Music composer template, jfugue-style maps, MIDI export—**rare among map editors**.

**Growth levers (S):**

- Own tutorial + store screenshots (differentiator).
- Optional: “export for LMMS / FL Mobile / GarageBand import” how-to.

**Risk:** Niche; don’t overshadow map editing brand.

---

### 9. Collaborative design & template sharing

**Who:** Friends co-editing a map; community sharing RW/indie templates.

**Today:** Collaboration rooms, online template download.

**Growth levers (M–L):**

- Clearer discoverability (many users may not find rooms/templates).
- Template tagging: RW, platformer, TTRPG, pixel, jam.
- Optional curated “featured maps” feed.

---

### 10. Data-driven design (objects as logic, not just art)

**Who:** Intermediate devs using Tiled as **level database**: spawn points, loot tables, triggers, zones—via object layers + custom properties.

**Industry pattern:** Tiled used for UI layout and config export via scripts ([DEV: “More than Maps”](https://dev.to/dungeoncrawlsoft/more-than-maps-1f25)); object layers hold structured game data ([Tiled layers docs](https://doc.mapeditor.org/manual/layers/)).

**NotTiled angle:** Object layers, properties, templates—export JSON/Lua/TMX for runtime loaders.

**Growth levers (M):**

- Tutorial: object types + properties for chests, enemies, zones.
- Document property types in exports (boolean/int) for Love2D/STI consumers.

---

## Tier 3 — Possible but poor ROI (unless requested)

| Use case | Why Tier 3 |
|----------|------------|
| **Full VTT replacement** (tokens, fog, initiative) | Crowded; not a map editor core competency |
| **Dungeon Scrawl–style vector schematics** | Different interaction model (polygons vs tiles) |
| **RPG Maker / Minecraft structure export** | Proprietary formats; legal/technical friction |
| **Professional studio pipeline** | Desktop Tiled + CI + plugins; NotTiled lacks scripting |
| **Cross-stitch / photo mosaic** | Mentioned on Play Store; verify feature maturity before marketing |
| **Architecture / floor plans** | Grid tools exist elsewhere; weak brand fit |
| **iOS App Store** | README notes iOS build possible but not shipped—blocks half of “mobile creator” market |

---

## Positioning matrix (summary)

| Persona | Primary need | NotTiled hook | Main export |
|---------|--------------|---------------|-------------|
| RW map maker | Mod skirmish/mission maps on phone | RW templates, tutorials, community | TMX |
| Indie dev | Level layout on the go | TMX/JSON/Lua + object properties | JSON / Lua / TMX |
| Beginner | Playable game without code | Not2D + APK + playtest | APK / Not2D assets |
| DM / TTRPG | Battlemap visual | Layers, tile paint, procgen | PNG |
| Student / club | Learn game design | Tutorials, collaboration | PNG / JSON |
| Pixel artist | Small sprite/sheet | Pixel Editor kind | PNG |
| Jam participant | Fast draft map | Generators + small templates | JSON / PNG |

---

## Recommended focus order (use cases, not formats)

1. **Double down on RW + modding communities** — existing moat, documented demand, word-of-mouth on Steam/Discord.
2. **Package “mobile indie pipeline”** — one doc + export presets tying to Tier 1 engines (see export roadmap).
3. **TTRPG battlemap lane** — template + PNG tutorial; cheap acquisition via “D&D map maker android” search terms.
4. **Not2D / APK story** — unique vs Tiled desktop; target “make a game on your phone.”
5. **Education & collaboration** — if bandwidth allows; competes with browser tools on Chromebooks.
6. **Music / mosaic / cross-stitch** — only after core map personas are well served.

---

## Messaging suggestions (store / website)

**Current:** “2D map editor for gamers” (accurate but narrow).

**Broader (still honest):**

- “Design game levels, battlemaps, and pixel art on your phone—export to TMX, JSON, Lua, or PNG.”
- “Built for Rusted Warfare mappers; works with Love2D, Phaser, GDevelop, and more.”
- “Sketch dungeons on the bus; playtest mini-games without a PC.”

Avoid claiming full Tiled feature parity or professional studio replacement.

---

## Related docs

- [EXPORT_FORMAT_GROWTH_ROADMAP.md](./EXPORT_FORMAT_GROWTH_ROADMAP.md)
- [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md)
- [README.md](../README.md)
- [not2d/README.md](../not2d/README.md)
- [newfile-kinds/README.md](../newfile-kinds/README.md)
