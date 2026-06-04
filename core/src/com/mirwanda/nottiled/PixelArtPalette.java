package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

/** Helpers for Pixel Editor swatch + editable palette tilesets. */
public final class PixelArtPalette {

    public static final int PALETTE_COLUMNS = 8;
    public static final String PROP_PALETTE_COUNT = "palette_count";
    /** First tileset on a Pixel Editor map (type=Pixel Editor). */
    public static final int SWATCH_TSET_INDEX = 0;
    /** Second tileset on a Pixel Editor map (type=Pixel Editor). */
    public static final int PALETTE_TSET_INDEX = 1;

    private PixelArtPalette() {
    }

    public static Color colorFromGid(List<tileset> tilesets, int gid) {
        if (gid <= 0)
            return null;
        for (tileset ts : tilesets) {
            if (gid >= ts.getFirstgid() && gid < ts.getFirstgid() + ts.getTilecount()) {
                return getTileColor(ts, gid - ts.getFirstgid());
            }
        }
        return null;
    }

    public static Color getTileColor(tileset ts, int tileIndex) {
        int col = tileIndex % ts.getColumns();
        int row = tileIndex / ts.getColumns();
        return new Color(ts.getPixmap().getPixel(col, row));
    }

    public static void setTileColor(tileset ts, int tileIndex, Color color) {
        ensureCapacity(ts, tileIndex);
        int col = tileIndex % ts.getColumns();
        int row = tileIndex / ts.getColumns();
        ts.getPixmap().drawPixel(col, row, Color.rgba8888(color));
    }

    public static void ensureCapacity(tileset palette, int tileIndex) {
        int needed = tileIndex + 1;
        if (needed <= palette.getTilecount())
            return;

        int cols = PALETTE_COLUMNS;
        int rows = (needed + cols - 1) / cols;
        Pixmap old = palette.getPixmap();
        Pixmap resized = new Pixmap(cols, rows, Pixmap.Format.RGBA8888);
        resized.setColor(0, 0, 0, 255);
        resized.fill();
        if (old != null)
            resized.drawPixmap(old, 0, 0);
        if (old != null)
            old.dispose();

        palette.setPixmap(resized);
        palette.setColumns(cols);
        palette.setWidth(cols);
        palette.setHeight(rows);
        palette.setTilecount(cols * rows);
        palette.setOriginalwidth(cols);
        palette.setOriginalheight(rows);
    }

    /** Clears a palette slot to transparent (map GIDs stay the same). */
    public static void setTileTransparent(tileset palette, int tileIndex) {
        if (tileIndex < 0 || palette.getPixmap() == null)
            return;
        int col = tileIndex % palette.getColumns();
        int row = tileIndex / palette.getColumns();
        palette.getPixmap().drawPixel(col, row, 0);
    }

    public static void refreshTexture(tileset ts) {
        if (ts.getTexture() != null)
            ts.getTexture().dispose();
        ts.setTexture(new Texture(ts.getPixmap()));
    }

    public static void syncEmbeddedPng(tileset palette) {
        if (palette == null || palette.getPixmap() == null)
            return;

        FileHandle tmp = Gdx.files.local(".pixel_palette_save.png");
        try {
            PixmapIO.writePNG(tmp, palette.getPixmap());
            byte[] bytes = tmp.readBytes();
            String encoded = android.util.Base64.encodeToString(bytes, 0);
            boolean found = false;
            for (property p : palette.getProperties()) {
                if (p.getName().equalsIgnoreCase("embedded_png")) {
                    p.setValue(encoded);
                    found = true;
                    break;
                }
            }
            if (!found)
                palette.getProperties().add(new property("embedded_png", encoded));
        } finally {
            if (tmp.exists())
                tmp.delete();
        }
    }

    public static Pixmap buildPalettePixmap(List<Color> colors) {
        int count = Math.max(colors.size(), 1);
        int cols = PALETTE_COLUMNS;
        int rows = (count + cols - 1) / cols;
        Pixmap pm = new Pixmap(cols, rows, Pixmap.Format.RGBA8888);
        pm.setColor(0, 0, 0, 255);
        pm.fill();
        int x = 0, y = 0;
        for (Color c : colors) {
            pm.drawPixel(x, y, Color.rgba8888(c));
            x++;
            if (x == cols) {
                x = 0;
                y++;
            }
        }
        return pm;
    }

    public static FileHandle swatchTemplateFile(String basepath) {
        FileHandle fh = Gdx.files.absolute(basepath + "NotTiled/sample/template/Pixel Editor/swatch.png");
        if (fh.exists())
            return fh;
        return Gdx.files.absolute(basepath + "NotTiled/swatch.png");
    }

}
