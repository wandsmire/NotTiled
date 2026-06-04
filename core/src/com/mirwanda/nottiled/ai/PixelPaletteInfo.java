package com.mirwanda.nottiled.ai;

import com.badlogic.gdx.graphics.Color;
import com.mirwanda.nottiled.PixelArtPalette;
import com.mirwanda.nottiled.tileset;

import java.util.ArrayList;
import java.util.List;

/** Palette slots for AI pixel drawing (0 = transparent, 1..N = palette colors). */
public class PixelPaletteInfo {
    public static final int EMPTY = 0;

    public int canvasWidth;
    public int canvasHeight;
    public int paletteTsetIndex;
    public int paletteFirstGid;
    public int slotCount;
    public List<String> slotColors = new ArrayList<>();

    public static PixelPaletteInfo build(List<tileset> tilesets, int paletteIndex, int usedCount, int tw, int th) {
        PixelPaletteInfo info = new PixelPaletteInfo();
        info.canvasWidth = tw;
        info.canvasHeight = th;
        if (paletteIndex < 0 || paletteIndex >= tilesets.size())
            return info;
        tileset palette = tilesets.get(paletteIndex);
        info.paletteTsetIndex = paletteIndex;
        info.paletteFirstGid = palette.getFirstgid();
        info.slotCount = Math.max(1, usedCount);
        if (palette.getPixmap() == null && palette.getTexture() != null) {
            // caller should ensure pixmap; best-effort only
        }
        for (int i = 0; i < info.slotCount; i++) {
            Color c = palette.getPixmap() != null ? PixelArtPalette.getTileColor(palette, i) : Color.BLACK;
            if (c.a < 0.05f)
                info.slotColors.add("transparent");
            else
                info.slotColors.add(String.format("#%02x%02x%02x", (int) (c.r * 255), (int) (c.g * 255),
                        (int) (c.b * 255)));
        }
        return info;
    }

    public boolean isReady() {
        return slotCount > 0 && canvasWidth > 0 && canvasHeight > 0 && paletteTsetIndex >= 0;
    }

    public String formatForPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Palette: 0=transparent");
        for (int i = 0; i < slotColors.size(); i++) {
            sb.append(", ").append(i + 1).append('=').append(slotColors.get(i));
        }
        sb.append('\n');
        return sb.toString();
    }

    public int gidToSlot(List<tileset> tilesets, long gid) {
        if (gid == 0)
            return EMPTY;
        if (gid >= paletteFirstGid && gid < paletteFirstGid + slotCount)
            return (int) (gid - paletteFirstGid) + 1;
        Color src = PixelArtPalette.colorFromGid(tilesets, (int) gid);
        if (src == null)
            return EMPTY;
        return nearestSlot(src) + 1;
    }

    public long slotToGid(int slot) {
        if (slot <= EMPTY)
            return 0;
        int idx = slot - 1;
        if (idx < 0 || idx >= slotCount)
            return 0;
        return paletteFirstGid + idx;
    }

    private int nearestSlot(Color src) {
        int best = 0;
        float bestD = Float.MAX_VALUE;
        for (int i = 0; i < slotCount; i++) {
            Color pc = slotColors.get(i).equals("transparent") ? new Color(0, 0, 0, 0)
                    : Color.valueOf(slotColors.get(i));
            float d = Math.abs(src.r - pc.r) + Math.abs(src.g - pc.g) + Math.abs(src.b - pc.b)
                    + Math.abs(src.a - pc.a);
            if (d < bestD) {
                bestD = d;
                best = i;
            }
        }
        return best;
    }
}
