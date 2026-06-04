# NotTiled feature priority roadmap

Actionable feature priorities for **what to build next** in the app—not export formats ([EXPORT_FORMAT_GROWTH_ROADMAP.md](./EXPORT_FORMAT_GROWTH_ROADMAP.md)) and not TMX spec milestones ([TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md)).

**Strategy (aligned with focus):** Make NotTiled **reliable and fast on phone** for Rusted Warfare mappers, mobile indie level designers, and PNG battlemap creators. Avoid sprawling toward “full desktop Tiled.”

**Last reviewed:** 2026-06-03

---

## Legend

| Tier | Meaning |
|------|---------|
| **A** | Do first — high user pain, high impact, fits focus |
| **B** | Strong next — clear value for core audiences |
| **C** | Good later — defer until A/B are solid |

| Size | Rough effort |
|------|----------------|
| **S** | Hours – a few focused edits |
| **M** | Days |
| **L** | Weeks |
| **XL** | Major feature / UI rewrite |

**Depends on:** Cross-reference to standardization milestones (M1, M2, …) where relevant.

---

## Top 5 (if only five ships)

| # | Feature | Tier | Size |
|---|---------|------|------|
| 1 | Export / save **validation** + fix hints | A | M |
| 2 | **M1** spec hygiene (layer ids, visibility, flips) | A | S–M |
| 3 | **Export presets** (RW, Love2D, Phaser/GDevelop, PNG) | A | M |
| 4 | **Layer workflow** (hide others, reorder, duplicate, rename) | A | M |
| 5 | **RW checklist** + copy-to-game polish | B | M |

---

## Tier A — Do first

### A1. Export / save validation with clear fixes — **M**

**Problem:** Maps fail silently in game engines or Tiled on PC (missing embed, bad paths, infinite map, wrong RW layers).

**Proposal:** Pre-flight check before Save / Export; dialog lists issues + suggested fixes (link to embed tileset, fix path, etc.).

| Check | Why |
|-------|-----|
| Tileset embedded vs external `.tsx` | Phaser, STI, Construct require embed for typical import |
| Image files resolve relative to map | Broken paths after move/share |
| Map not infinite | GDevelop and many engines reject infinite |
| Empty or missing tile layers | Obvious corruption |
| RW-specific layer names / spawn setup | Optional when RW template or tag detected |

**Acceptance:** User exporting broken map sees warnings; embedded-tileset fix is one tap where possible.

**Related:** [EXPORT_FORMAT_GROWTH_ROADMAP.md](./EXPORT_FORMAT_GROWTH_ROADMAP.md) Tier 1.1 (document engine requirements).

---

### A2. M1 spec hygiene (save/load trust) — **S / M**

**Problem:** Round-trip to desktop Tiled or engine loses layer ids, visibility, flip flags—erodes trust in mobile ↔ PC workflow.

**Proposal:** Implement [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md) **M1** (and start **M2** layer attributes):

- `nextlayerid` on TMX save + load
- Stable `id` on every layer type
- Unified `visible` representation
- Preserve GID flip bits through encode/decode
- JSON `nextobjectid` / `nextlayerid` round-trip

**Acceptance:** Tiled 1.10+ re-opens NotTiled-saved map without layer/id/visibility drift.

**Depends on:** M0 (done).

---

### A3. Export presets — **M**

**Problem:** Users don’t know which export menu + settings match their target (Lua vs JSON, CSV vs base64, embed rules).

**Proposal:** Named presets in Export UI (or quick picker):

| Preset | Format | Notes |
|--------|--------|-------|
| **Rusted Warfare** | TMX to storage | Embed tilesets, RW path hints |
| **Love2D (STI)** | Lua | Embedded tileset, flat `data` |
| **Phaser / GDevelop** | JSON (`.json` or `.tmj`) | Embedded, CSV/base64 per map format setting |
| **PNG battlemap** | PNG | Visible layers, scale option (see B4) |
| **Generic Tiled** | TMX | Default save behavior |

**Acceptance:** One selection sets format + runs validation (A1).

**Related:** Export refactor already routes JSON through `setjsonmap()` / `savejsonmap()`.

---

### A4. Layer workflow for touch — **M**

**Problem:** RW and large maps need constant layer toggling; mobile UI is slower than desktop Tiled layer panel.

**Proposal:**

- **Solo layer** / hide all except active (RW tutorials already teach this manually)
- Reorder layers (drag or move up/down)
- Duplicate layer
- Rename layer in-place
- Optional: layer search/filter when layer count > N

**Acceptance:** RW mapper can isolate units vs ground vs items in ≤2 taps without tutorial.

---

### A5. Autosave + crash recovery — **M**

**Problem:** Mobile sessions interrupt; lost work is the #1 reason users churn.

**Proposal:** Build on existing autosave interval (`autosaveInterval`, periodic save hook):

- Visible “last autosaved at …” or subtle indicator
- Recovery prompt on startup if dirty temp copy exists
- User preference: interval + max backup count
- Optional: export backup folder alongside project

**Acceptance:** Force-kill app → reopen → offer to restore last autosave.

---

## Tier B — Strong next

### B1. RW “map health” checklist — **M**

**Problem:** New RW mappers miss spawn points, wrong layer selection, or `set` layer workflow.

**Proposal:** Optional panel or pre-export checklist (template-driven):

- Required layers visible / present (ground, units, items, set, … per template)
- Objects on correct layers
- Map dimensions within norms
- Link to built-in RW tutorial steps

**Acceptance:** Checklist passes for included sample RW map; fails with actionable items on blank RW template.

**Related:** [USE_CASE_GROWTH_ROADMAP.md](./USE_CASE_GROWTH_ROADMAP.md) Tier 1 modding.

---

### B2. Copy to Rusted Warfare — primary finish step — **S / M**

**Problem:** Feature exists (`copytorustedwarfare`) but may be buried; RW mobile mappers want “done → in game.”

**Proposal:**

- Prominent RW export path after checklist (B1)
- Detect / remember RW mods folder where possible
- Combine with validation (A1)
- Success toast + “open game” hint

**Acceptance:** RW mapper on Android: edit → validate → copy → test in game without manual file hunting.

---

### B3. Typed custom properties — **M**

**Problem:** String-only properties limit game logic; engines expect bool/int/float; Lua export partially handles types.

**Proposal:** Property editor supports types (string, int, float, bool, color); save/load in TMX/JSON/Lua consistently.

**Acceptance:** Bool property exports unquoted in Lua; round-trips in TMX `type` attribute.

**Related:** M8 in standardization roadmap; [StringBuilderPlus.wprop](../core/src/com/mirwanda/nottiled/StringBuilderPlus.java).

---

### B4. Object templates (lightweight) — **M / L**

**Problem:** Repetitive placement of spawns, units, props, TTRPG markers.

**Proposal:** Phase 1 without full Tiled `.tx`:

- Save object + properties as app template
- Stamp from template library
- RW / TTRPG starter packs in samples

**Acceptance:** Place saved “player spawn” template twice; properties copied.

**Related:** M7 in [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md) (full `.tx` later).

---

### B5. PNG export presets (battlemaps) — **S / M**

**Problem:** TTRPG users need VTT-ready PNGs; current export may need manual merge/scale.

**Proposal:**

- Export visible layers only (or pick layers)
- Scale: 1×, 2×, or px-per-tile (70 / 140 presets)
- Optional: transparent padding, no grid overlay
- Filename hint: `mapname_70px.png`

**Acceptance:** 20×20 dungeon exports readable PNG for Roll20/Discord.

**Related:** [USE_CASE_GROWTH_ROADMAP.md](./USE_CASE_GROWTH_ROADMAP.md) TTRPG tier.

---

### B6. Project folder memory + recents — **S / M**

**Problem:** Mod projects use many maps + shared tilesets; opening one file loses folder context.

**Proposal:**

- Recents store **project directory** + last files
- “Open folder” sets tileset resolve root (`curdir` behavior documented)
- Pin favorite project path

**Acceptance:** Switch between two `.tmx` files in same mod folder without re-picking directory.

---

## Tier C — Defer

| Feature | Why defer |
|---------|-----------|
| **Group layers (M3)** | XL UI rewrite; flat layers OK for RW/indie |
| **Infinite / hex maps (M6)** | XL; not core focus |
| **Wang sets / terrain 2.0 (M5)** | L; RW macros/autotiles may suffice |
| **LDtk export** | See export roadmap; after trust |
| **iOS App Store** | Distribution effort; separate track |
| **Collaboration rooms v2** | Niche until solo workflow solid |
| **Music / mosaic / cross-stitch** | Different personas; don’t distract |
| **Tiled scripting / plugins** | Explicit non-goal in README |
| **Full VTT** (tokens, fog) | Different product category |

---

## Suggested build order

```
A2 (M1 trust) ──┬── A1 (validation)
                ├── A3 (export presets)
                └── A4 (layer UX)
A5 (autosave) ── parallel when touching save path

B1 (RW checklist) → B2 (copy to RW)
B3 (typed props) ── parallel with M1/M4 export paths
B4 (object templates) → later M7 (.tx)
B5 (PNG presets) ── quick win for TTRPG
B6 (project recents) ── anytime
```

**Practical schedule:**

1. **Sprint 1:** A2 (M1 items) + A1 validation skeleton  
2. **Sprint 2:** A3 presets wired to validation + A4 layer solo/hide  
3. **Sprint 3:** A5 autosave UX + B2 RW copy flow + B1 checklist  
4. **Backlog:** B3–B6 by user feedback; Tier C only on demand  

---

## Explicit non-goals (this roadmap)

- Replacing desktop Tiled feature-for-feature  
- New engine-specific binary formats (see export roadmap)  
- Auto-mapping IDE, world files, `.tiled-project` (M8 — project-grade)  
- Marketing-only work (store copy lives in use-case roadmap)  

---

## Per-feature done checklist

Before marking a feature complete:

- [ ] Works on Android touch + desktop (if applicable)
- [ ] Documented in-app (tutorial step or help) or README snippet
- [ ] Does not regress TMX/JSON round-trip (smoke: open in Tiled 1.10+)
- [ ] Strings added to language files if user-visible

---

## Related docs

- [USE_CASE_GROWTH_ROADMAP.md](./USE_CASE_GROWTH_ROADMAP.md) — who we build for  
- [EXPORT_FORMAT_GROWTH_ROADMAP.md](./EXPORT_FORMAT_GROWTH_ROADMAP.md) — export formats  
- [TILED_STANDARDIZATION_ROADMAP.md](./TILED_STANDARDIZATION_ROADMAP.md) — M1–M8 spec milestones  
- [README.md](../README.md)

---

*Last updated: 2026-06-03*
