# NotTiled — Layer List DnD TODO (Cursor Handoff)

**File:** `core/src/com/mirwanda/nottiled/MyGdxGame.java`

---

## 1. FIX CRASH (do first)

`rebuildLayerListTable()` (~line 13163) calls `skin.newDrawable("white", color)` — the Holo skin has no `"white"` region → crash when Layer Management screen opens.

Add this helper and replace all 5 `skin.newDrawable("white",...)` calls with it:

```java
private TextureRegionDrawable makeColorDrawable(Color color) {
    Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pm.setColor(color);
    pm.fill();
    TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pm)));
    pm.dispose();
    return d;
}
```

---

## 2. WRONG SCREEN — the DnD went to the wrong place

The DnD code added in the last session went to the Layer Management popup
(`loadLayerManagement()` / `tLayerMgmt`). That is NOT what the user wants.

The user wants the layer list on the MAIN MAP interface, built by
`loadList("layer")` at ~line 14594.

---

## 3. REAL TASK: Replace `loadList("layer")` with DnD

Current: each row = up-arrow button + down-arrow button + name button + eye button.
Replace with: name button + eye button + drag handle. No arrows.

Inside the `case "layer":` block (~line 14594):

- Remove the `moveup` and `movedn` Button creation + listener blocks entirely.
- Keep: `layerName` button (tap → selLayer + backToMap()), `visible` button.
- Add a DragAndDrop instance, register each row as source and target.

Drop onto GROUP row → reparent:
  moved.setParentGroupId(dstLayer.getId())
  insert moved layer right after group in flat list
  guard: !isDescendant(dstLayer.getId(), moved.getId())

Drop onto any other row → reorder in flat list, keep existing parentGroupId.

After drop: resetCaches(), updateObjectCollision(), loadList("layer")

Row background colors — use makeColorDrawable():
  Selected:           new Color(0.2f, 0.45f, 1f, 0.3f)
  Hover GROUP target: new Color(1f, 0.78f, 0.1f, 0.4f)   // gold
  Hover reorder:      new Color(0.2f, 0.8f, 1f, 0.3f)    // cyan

Existing helpers:
  getLayerDepth(layer l)            // nesting depth for indentation
  isDescendant(int descId, int ancId)  // circular-parenting guard
  resetCaches()
  updateObjectCollision()
  loadList("layer")                 // call this to rebuild after drop

---

## 4. OPTIONAL CLEANUP

- Remove rebuildLayerListTable() entirely (or make it a no-op stub)
- Remove layerListTable, layerListScrollPane, layerDnd, layerDragSrcIdx, bLayerMoveDown fields
- In loadLayerManagement(), restore ScrollPane(llayerlist) instead of layerListScrollPane

---

## Key line numbers (approximate)

  loadList("layer") case     ~14594
  rebuildLayerListTable()    ~13163  (crash source)
  refreshLayerList()         ~13150
  getLayerDepth()            ~13312
  isDescendant()             ~13440
  Skin init (Holo dark)      ~7144
