package com.mirwanda.nottiled;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * One cheap streaming pass over a TMX before the real parse.
 *
 * Collects the Tiled features NotTiled cannot round-trip — so the user can be
 * warned that saving will drop them and a pristine copy of the original can be
 * kept — and pre-computes chunk bounds for infinite maps so loadmap can stitch
 * the chunks into one fixed-size grid.
 */
public class TmxCompatScan {

    /** Stitched grids above this many cells would risk OOM on low-end devices. */
    public static final long MAX_STITCH_CELLS = 2_000_000L;

    public boolean infinite, wangsets, templates, hexagonal, staggered;
    public boolean tileoffset, transformations, backgroundcolor, xmlChunks;
    public boolean hasChunks, tooLarge;
    public int chunkMinX, chunkMinY, chunkMaxX, chunkMaxY;

    /** True when loadmap should stitch chunk data into a fixed grid. */
    public boolean stitchable() {
        return hasChunks && !tooLarge && !xmlChunks;
    }

    public static TmxCompatScan scan(InputStream in) {
        TmxCompatScan r = new TmxCompatScan();
        String dataEncoding = null;
        try {
            XmlPullParser p = XmlPullParserFactory.newInstance().newPullParser();
            p.setInput(in, null);
            int event = p.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                if (event == XmlPullParser.START_TAG) {
                    String n = p.getName();
                    if ("map".equals(n)) {
                        r.infinite = "1".equals(p.getAttributeValue(null, "infinite"));
                        String ori = p.getAttributeValue(null, "orientation");
                        r.hexagonal = "hexagonal".equalsIgnoreCase(ori);
                        r.staggered = "staggered".equalsIgnoreCase(ori);
                        r.backgroundcolor = p.getAttributeValue(null, "backgroundcolor") != null;
                    } else if ("wangsets".equals(n)) {
                        r.wangsets = true;
                    } else if ("tileoffset".equals(n)) {
                        r.tileoffset = true;
                    } else if ("transformations".equals(n)) {
                        r.transformations = true;
                    } else if ("object".equals(n)) {
                        if (p.getAttributeValue(null, "template") != null) r.templates = true;
                    } else if ("data".equals(n)) {
                        dataEncoding = p.getAttributeValue(null, "encoding");
                    } else if ("chunk".equals(n)) {
                        try {
                            int x = Integer.parseInt(p.getAttributeValue(null, "x"));
                            int y = Integer.parseInt(p.getAttributeValue(null, "y"));
                            int w = Integer.parseInt(p.getAttributeValue(null, "width"));
                            int h = Integer.parseInt(p.getAttributeValue(null, "height"));
                            if (!r.hasChunks) {
                                r.chunkMinX = x;
                                r.chunkMinY = y;
                                r.chunkMaxX = x + w;
                                r.chunkMaxY = y + h;
                            } else {
                                r.chunkMinX = Math.min(r.chunkMinX, x);
                                r.chunkMinY = Math.min(r.chunkMinY, y);
                                r.chunkMaxX = Math.max(r.chunkMaxX, x + w);
                                r.chunkMaxY = Math.max(r.chunkMaxY, y + h);
                            }
                            r.hasChunks = true;
                            if (dataEncoding == null) r.xmlChunks = true;
                        } catch (Exception ignored) {
                        }
                    }
                }
                event = p.next();
            }
        } catch (Exception ignored) {
            // A scan failure must never block loading — just report nothing.
        } finally {
            try {
                in.close();
            } catch (Exception ignored) {
            }
        }
        if (r.hasChunks) {
            long cells = (long) (r.chunkMaxX - r.chunkMinX) * (long) (r.chunkMaxY - r.chunkMinY);
            r.tooLarge = cells > MAX_STITCH_CELLS;
        }
        return r;
    }

    public boolean needsWarning() {
        return wangsets || templates || hexagonal || staggered || infinite
                || tileoffset || transformations || backgroundcolor || xmlChunks || tooLarge;
    }

    /** Human-readable list of what a NotTiled save would change or drop. */
    public List<String> lostFeatures() {
        List<String> out = new ArrayList<String>();
        if (hexagonal) out.add("Hexagonal orientation (will not display correctly)");
        if (staggered) out.add("Staggered orientation (will not display correctly)");
        if (infinite && stitchable()) out.add("Infinite map (opened as a fixed-size map)");
        if (infinite && tooLarge) out.add("Infinite map larger than supported bounds (tile data NOT loaded)");
        if (infinite && xmlChunks) out.add("XML-encoded infinite chunks (tile data NOT loaded)");
        if (wangsets) out.add("Terrain sets (wang sets)");
        if (templates) out.add("Object templates");
        if (tileoffset) out.add("Tileset drawing offset");
        if (transformations) out.add("Tileset transformation flags");
        if (backgroundcolor) out.add("Map background color");
        return out;
    }
}
