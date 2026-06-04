# NotTiled → Tiled format standardization roadmap

Progressive milestones toward full interoperability with the [Tiled TMX/JSON map format](https://doc.mapeditor.org/en/stable/reference/tmx-map-format/). Work through them in order; each milestone builds on the previous.

**Strategy:** Fix serial/format bugs without UI first; defer **group layers** and other large UI work until the flat layer model is spec-clean.

---

## Legend

| Size | Rough effort |
|------|----------------|
| **S** | Hours – a few focused edits |
| **M** | Days |
| **L** | Weeks |
| **XL** | Major feature / UI |

**Done** = largely addressed in recent work (tile palette order, TSX fixes, JSON animations/objects, image `locked` default, shared tile writers, etc.).

---

## M0 — Foundation (mostly done)

**Goal:** Maps from NotTiled open in Tiled 1.7+ without obvious corruption.

| Item | Status |
|------|--------|
| Tile `<tile>` / JSON `tiles[]` order by **id** (palette order) | Done |
| TSX: no `firstgid`; tile animation / props / collision | Done |
| Map `tiledversion`; property `type` on save | Done |
| JSON tile animations + object shapes (gid, polygon, etc.) | Done |
| Image layer `locked` default | Done |

**Smoke test:** External TSX + animated tiles + per-tile collision → save TMX → open in Tiled 1.10+; palette order matches.

**Key files:** `MyGdxGame.java` (`buildTMX`, `saveTsx`, `loadmap`, `setjsonmap`, `getjsonmap`), `tileset.java`, `jsonmap.java`.

---

## M1 — Serial hygiene (no UI) — S / M

**Goal:** Small spec gaps; same flat layer list; no new screens.

**Status:** Done.

| Task | Why |
|------|-----|
| `nextlayerid` on TMX **save + load** | Tiled uses stable layer ids; today mainly Lua/JSON set it |
| **Stable `id` on every layer type** | Image layers use list index; tile/object layers often omit `id` |
| Unify **`visible`**: `0` vs `false` vs `true` | Object groups use `"false"`; tile layers use `"0"` |
| Use `writeTmxProperty()` for **layer** properties in `buildTMX()` | Tile layers still duplicate property XML |
| JSON: `infinite`, `compressionlevel`, `nextlayerid` / `nextobjectid` round-trip | DTO fields exist; wiring incomplete |
| JSON tileset load: finish block ~17363 (tile **object** collision, wang if any) | Parity with TMX path |
| Map **`class`** attribute (Tiled 1.9+) alongside legacy `type` on objects | Forward-compatible metadata |
| Preserve **GID high bits** (h/v/d flip) through save/load if any path strips them | Rendering touches flips; verify CSV/base64 encode/decode |

**Acceptance:** Re-save a Tiled map in NotTiled; layer ids and visibility unchanged in Tiled.

**Depends on:** M0.

---

## M2 — Layer attributes (light UI) — M

**Goal:** Modern layer metadata without hierarchy.

**Status:** Done.

| Task | Why |
|------|-----|
| Load/save: `offsetx/y`, `opacity`, `visible`, `locked`, `id` on **tile + object** layers (match image layer) | Spec fields; some only partial |
| `parallaxx/y`, `tintcolor` (Tiled 1.5+) | Optional in properties UI |
| Object layer **`draworder`** (`topdown` / `index`) | Affects object stacking in Tiled |
| **`startx` / `starty`** on infinite-style data (if staying finite, document/ignore cleanly) | Avoid silent loss |

**Acceptance:** Map with offsets + parallax opens with same layer panel values in Tiled.

**Depends on:** M1 (stable layer ids).

---

## M3 — Group layers — L / XL (deferred intentionally)

**Goal:** Real Tiled hierarchy, not a flat list with a `GROUP` icon.

| Task | Why |
|------|-----|
| **Data model:** parent/child or nested layer tree, not only `List<layer>` | TMX `<group>` nests `<layer>`, `<objectgroup>`, etc. |
| **Load/save** `<group>` in TMX + JSON `"type":"group"` | Today `GROUP` exists in enum/UI but **no** `<group>` in `buildTMX()` / `loadmap()` |
| **UI:** layer list tree, drag into/out of groups, selection | Why this was deferred |
| `selgroup` / `curgroupid` → real group membership | Names suggest early experiment, not spec model |

**Acceptance:** Grouped map from Tiled round-trips with structure intact.

**Depends on:** M1–M2 (ids + attributes on nested layers).

---

## M4 — Format parity (TMX ↔ JSON ↔ Lua) — M

**Goal:** One internal model, three exporters behave the same.

**Status:** Done (ZSTD skipped per discussion).

| Task | Why |
|------|-----|
| Single path for layers/objects/tilesets (reduce `buildTMX` vs `setjsonmap` drift) | JSON still has “need to complete” comments |
| JSON **wangsets** / tile **objectgroup** if supported in TMX | `jsonmap` has types; game code partial |
| **XML** tile layer encoding save (marked unsupported in JSON export branch) | Rare but in spec |
| **zstd** tile data (Tiled 1.8+) | Only zlib/gzip/base64/csv today |
| Align map **`version`** / **`tiledversion`** with features emitted | Avoid claiming 1.9 while omitting fields |

**Acceptance:** Same map saved as `.tmx` and `.json`; Tiled opens both equivalently.

**Depends on:** M0–M2.

---

## M5 — Tileset depth — M / L

**Goal:** Tilesets match Tiled, not only “one PNG + grid”.

| Item | Today | Target |
|------|--------|--------|
| External **`.tsx` reference** | Supported | Keep; test relative paths |
| **Embedded** tileset in TMX | Supported | Keep |
| **Image collection** tilesets | Weak / missing | Per-tile `<image>` |
| **Tile offset**, **object alignment**, **grid** | Partial / missing | TSX + map refs |
| **Terrain** (`terraintypes` + per-tile `terrain`) | Save/load in TMX | Editor + JSON wang path |
| **Wang sets** (Terrains 2.0) | JSON DTO only | Load/save + editor (big) |
| Tile **probability** / **type** (typed tiles) | Missing | If targeting Tiled 1.9+ |

**Acceptance:** TSX with wang + image collection opens and re-saves in Tiled.

**Depends on:** M0, M4 for JSON side.

---

## M6 — Map modes & compression — L / XL

**Goal:** Large / non-orthogonal maps.

| Task | Size |
|------|------|
| **Infinite maps** + `<chunk>` | XL — memory, viewport, save |
| **Hexagonal / staggered / oblique** `orientation` | XL — math, rendering, input (today: orthogonal + isometric) |
| **zstd** + `compressionlevel` on layer data | M (if infinite deferred) |
| **World** files (`.world`) linking maps | L — new file type |

**Acceptance:** Official Tiled sample maps for hex/infinite open and edit without flattening.

**Depends on:** M3 if chunks nest under grouped layers; M4 for compression.

---

## M7 — Objects & templates — M / L

**Goal:** Object layers are first-class in the ecosystem.

| Task | Why |
|------|-----|
| **`.tx` object templates** (load/place/save) | `mytemplate` / new-file templates ≠ Tiled `.tx` |
| Object **`class`** (vs deprecated `type`) everywhere | M1 starts this |
| **Template** instance attrs (`template`, `class`) | Tiled 1.4+ |
| Tile objects on object layer (**gid** tiles) | Partial via `applyJsonObjectFields` |
| **Text** objects, **point** without hacks | Shape completeness |

**Acceptance:** Template library from Tiled works in NotTiled object mode.

**Depends on:** M1, M2.

---

## M8 — Ecosystem & “full standard” — XL / ongoing

**Goal:** Not just `.tmx`, but how teams use Tiled.

| Area | Notes |
|------|--------|
| **Tiled project** (`.tiled-project`) | External paths, rules |
| **Auto-mapping** rules | Rare on mobile; optional |
| **Global tile IDs** across maps | World + external tilesets |
| **Custom properties** editor | Types: string, int, float, bool, color, file, object |
| **Scripting / plugins** | Out of scope for mobile editor |
| **Regression suite** | Golden files: open in Tiled or snapshot XML |

**Acceptance:** A real game project repo (maps + tsx + templates + world) round-trips without manual repair.

---

## Suggested order

```
M0 (done) → M1 → M2 → M4
              ↓
         M3 (group layers — when ready for UI rewrite)
              ↓
         M5 → M6
M2 → M7 → M8
```

**Practical schedule:**

1. **Next:** M1 only (good “evening” tasks).
2. **Then:** M2 + M4 alongside normal feature work.
3. **Defer M3** until the layer UI rewrite.
4. **M5–M6** only if users ship hex/infinite/wang maps.
5. **M8** when targeting project-grade, not single-map-grade.

---

## Per-milestone checklist

Close a milestone when:

- [ ] Load official Tiled example file(s) for that feature
- [ ] Edit minimally in NotTiled (or read-only if no editor yet)
- [ ] Save TMX + JSON
- [ ] Re-open in **Tiled 1.10+** — no warnings, structure intact
- [ ] Optional: diff XML against golden (whitespace-normalized)

---

## Explicit non-goals (unless scope expands)

- Tiled editor plugins / scripting
- Full auto-mapping IDE
- 3D or non-Tiled formats
- Replacing new-file templates with full `.tx` workflow (until M7)

---

## Reference

- [TMX map format](https://doc.mapeditor.org/en/stable/reference/tmx-map-format/)
- Tile list order vs `id` since Tiled 1.7+ (palette display order)
- TSX must not include `firstgid` on the tileset element

---

*Last updated: 2026-06-03*
