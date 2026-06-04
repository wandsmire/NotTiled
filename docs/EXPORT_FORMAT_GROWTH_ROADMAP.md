# NotTiled export format growth roadmap

Research-backed priorities for expanding NotTiled’s audience through map export formats. Complements [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md), which focuses on TMX/JSON spec correctness—not which engines to target.

**Context:** Most 2D engines do not want a custom NotTiled format. They consume **Tiled-compatible** (or closely related) exports. NotTiled’s edge is **mobile editing**; prioritize formats used by solo, indie, and cross-platform developers who design levels on phone or tablet.

**Last reviewed:** 2026-06-03

---

## Legend

| Tier | Meaning |
|------|---------|
| **Tier 1** | Best ROI — high reach, reasonable effort |
| **Tier 2** | Large audience, heavy implementation or ongoing maintenance |
| **Tier 3** | Niche, legacy, or weak fit for NotTiled’s strengths |

| Size | Rough effort |
|------|----------------|
| **S** | Hours – a few focused edits |
| **M** | Days |
| **L** | Weeks |
| **XL** | Major feature; expect format churn from target engine |

---

## Already covered (no new format required)

These engines load Tiled-family files directly. Growth here is mostly **compatibility polish**, **documentation**, and **marketing**—not inventing new serializers.

| Engine / tool | Typical import | NotTiled export today |
|---------------|----------------|------------------------|
| Love2D (STI) | `.lua` | Export to Lua |
| Phaser 3 | Tiled `.json` / `.tmj` | Export to JSON |
| GDevelop | Tiled JSON (embedded tileset) | Export to JSON |
| Construct 3 | `.tmx` (embedded tileset) | Export to storage (TMX) |
| Godot 4 | `.tmx` / `.tmj` (YATI, built-in import) | TMX + JSON |
| Flutter / Flame | `.tmx` / JSON | TMX + JSON |
| Solar2D | JSON or Lua (PonyTiled, Berry) | JSON + Lua |
| Unity | `.tmx` (SuperTiled2Unity, KITTY) | TMX |
| Defold | Tiled JSON / native Defold export | JSON (partial vs Tiled’s Defold plugin) |

**References**

- [Tiled export formats](https://doc.mapeditor.org/en/stable/manual/export/)
- [STI (Love2D)](https://github.com/karai17/Simple-Tiled-Implementation)
- [Phaser tilemapTiledJSON](https://docs.phaser.io/api-documentation/class/tilemaps-tilemap)
- [GDevelop external tilemap (Tiled/LDtk)](https://wiki.gdevelop.io/gdevelop5/objects/tilemap/)

---

## Tier 1 — Best ROI

### 1. Polish & document TMX / JSON / Lua for major importers — **S / M**

**Goal:** Users can pick an engine from a README table and export without guessing.

| Task | Notes |
|------|--------|
| README “Supported engines” table | Link export menu item → engine → loader |
| JSON: embedded tilesets, non-infinite maps | Required by Phaser, GDevelop |
| Lua: embedded tilesets, `encoding = "lua"` flat `data` | Required by STI |
| TMX: embedded tileset, `renderorder="right-down"` | Required by Construct 3 |
| Optional: export filename uses `.tmj` | Modern Tiled JSON extension |

**Why:** Largest reach with work mostly already done.

---

### 2. CSV layer export — **S**

**Goal:** One CSV file per tile layer, matching [Tiled’s CSV export](https://doc.mapeditor.org/en/stable/manual/export/).

| Task | Notes |
|------|--------|
| Add “Export layer(s) as CSV” | Reuse existing `savecsv()` |
| Document empty cell convention | Tiled uses `-1` for empty in CSV export |

**Status:** Done.

**Why:** Simple format; custom engines and tutorials still use it. Low effort.

---

### 3. LDtk (`.ldtk`) export — **M / L**

**Goal:** Open NotTiled maps in the [LDtk](https://ldtk.io/) ecosystem and GDevelop’s LDtk tilemap path.

| Task | Notes |
|------|--------|
| Research LDtk JSON schema (levels, layers, entities, tilesets) | [LDtk JSON doc](https://github.com/deepnight/ldtk/blob/master/JSON_DOC.md) |
| Map NotTiled layers → LDtk layer types | Tiles vs entities vs int-grid is not 1:1 |
| Export or round-trip decision | Import may be separate milestone |

**Why:** Clear differentiator vs desktop-only Tiled. Competitors (e.g. Eggame Tools) advertise TMX + LDtk. GDevelop supports LDtk tilemaps.

**Risk:** LDtk’s entity/layer model differs from Tiled; full fidelity is hard.

---

## Tier 2 — Big audience, heavy work

### 4. GameMaker Studio 2/3 — `room.yy` export — **L / XL**

**Goal:** Overwrite or generate GameMaker room JSON from a NotTiled map.

| Task | Notes |
|------|--------|
| Study [Tiled GameMaker export](https://doc.mapeditor.org/en/latest/manual/export-yy/) | Official reference implementation |
| Track GM format changes | 2024+ `$GM*` type tags; ongoing churn |
| Document prerequisite | Sprites/tilesets must already exist in GM project |

**Why:** Huge indie/mobile segment; established Tiled → GM workflow.

**Risk:** High maintenance; format breaks on GameMaker updates.

---

### 5. Godot 4 — `.tscn` / `.tres` export — **XL**

**Goal:** Native Godot scenes from NotTiled maps (optional alternative to YATI import of TMX/TMJ).

| Task | Notes |
|------|--------|
| Study [Tiled Godot 4 exporter](https://doc.mapeditor.org/en/latest/manual/export-tscn/) | TileMap node rules, `tilesetResPath`, flips |
| Godot 4.2+ flip/rotation vs older `exportAlternates` | Tiled 1.11+ behavior |

**Why:** Godot is massive on mobile and desktop.

**Risk:** Strict tileset/layout rules; image collections and image layers unsupported in Tiled’s exporter too.

**Alternative:** Recommend YATI + TMX/TMJ export (already supported)—lower cost.

---

### 6. Defold — `.tilemap` / collection export — **L**

**Goal:** Match [Tiled’s Defold plugins](https://doc.mapeditor.org/en/stable/manual/export/) (`defold`, `defoldcollection`).

**Why:** Loyal, smaller community.

**Risk:** Medium effort; audience smaller than GM/Godot/LDtk.

---

## Tier 3 — Niche or weak fit

| Format | Verdict |
|--------|---------|
| **Ogmo (`.ogmo`)** | Legacy; LDtk has more mindshare. Add only if users request. |
| **RPG Maker (MV/MZ JSON, 2000/2003 LMU)** | Large fan base but engine-specific layer logic; conversion incomplete and legally awkward for old binaries. |
| **GB Studio** | Wants PNG backgrounds, not structured maps — **Export to PNG** already fits. |
| **Engine-specific binary formats** | Only for deep partnerships (cf. Remixed Dungeon JSON). |

---

## Recommended execution order

1. **Tier 1.1** — Document + smoke-test TMX / JSON / Lua against Phaser, GDevelop, Construct, Love2D, Godot-via-YATI.
2. **Tier 1.2** — CSV layer export.
3. **Tier 1.3** — LDtk export (spike → MVP → entity mapping).
4. **Tier 2** — GameMaker `.yy` or Godot `.tscn` only after user demand or partnership justifies maintenance.
5. **Tier 3** — Defer unless explicit requests.

---

## NotTiled-specific positioning

**Do**

- Target mobile-first solo/indie devs.
- Lead with “Edit on device, export to Tiled JSON/Lua/TMX.”
- Call out Love2D, Phaser, GDevelop, Construct, Godot (import), Flutter in store/README copy.

**Avoid**

- Investing in proprietary RPG Maker binaries before TMX/JSON/LDtk are bulletproof.
- Duplicating Tiled desktop’s full plugin surface (GM/Godot/Defold) without a maintenance plan.

---

## Related docs

- [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md) — TMX/JSON spec parity with Tiled desktop
- Export implementation: `MyGdxGame.java` (`exporttolua`, `exporttojson`, `setjsonmap`, `buildTMX`, `savecsv`)
