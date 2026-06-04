package com.mirwanda.nottiled.ai;

import com.mirwanda.nottiled.layer;
import com.mirwanda.nottiled.tileset;

import java.util.ArrayList;
import java.util.List;

/** Row-RLE encode/decode for Pixel Editor active layer (palette slots). */
public class LeanPixelCodec {

    public static String encodeLayer(layer l, List<tileset> tilesets, PixelPaletteInfo palette, int tw, int th) {
        if (l == null || l.getStr() == null || l.getStr().size() < tw * th)
            return "EMPTY\n";
        boolean any = false;
        for (Long g : l.getStr()) {
            if (g != null && g != 0) {
                any = true;
                break;
            }
        }
        if (!any)
            return "EMPTY\n";
        StringBuilder sb = new StringBuilder();
        sb.append("# palette slots per cell\n");
        for (int y = 0; y < th; y++) {
            String row = encodeRow(l, tilesets, palette, y, tw);
            if (row != null)
                sb.append(row).append('\n');
        }
        return sb.toString();
    }

    private static String encodeRow(layer l, List<tileset> tilesets, PixelPaletteInfo palette, int y, int tw) {
        StringBuilder rle = new StringBuilder();
        int x = 0;
        while (x < tw) {
            int idx = y * tw + x;
            long gid = l.getStr().get(idx);
            int slot = palette.gidToSlot(tilesets, gid);
            int run = 1;
            while (x + run < tw) {
                long g2 = l.getStr().get(y * tw + x + run);
                if (palette.gidToSlot(tilesets, g2) != slot)
                    break;
                run++;
            }
            if (rle.length() > 0)
                rle.append(',');
            rle.append(slot).append('*').append(run);
            x += run;
        }
        return "R" + y + ',' + rle;
    }

    public static int[][] decodePixelBlock(String text, int tw, int th) {
        int[][] grid = new int[th][tw];
        if (text == null)
            return grid;
        for (String line : text.split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#"))
                continue;
            if (line.startsWith("F")) {
                int slot = parseSlot(line.substring(1));
                for (int y = 0; y < th; y++)
                    for (int x = 0; x < tw; x++)
                        grid[y][x] = slot;
                continue;
            }
            if (line.startsWith("R")) {
                int comma = line.indexOf(',');
                if (comma < 0)
                    continue;
                try {
                    int y = Integer.parseInt(line.substring(1, comma).trim());
                    if (y < 0 || y >= th)
                        continue;
                    applyRowRle(grid[y], line.substring(comma + 1), tw);
                } catch (NumberFormatException ignored) {
                }
                continue;
            }
            if (line.startsWith("P")) {
                int[] p = parsePoint(line);
                if (p == null)
                    continue;
                if (p[0] >= 0 && p[0] < tw && p[1] >= 0 && p[1] < th)
                    grid[p[1]][p[0]] = p[2];
            }
        }
        return grid;
    }

    private static void applyRowRle(int[] row, String rle, int tw) {
        int x = 0;
        for (String part : rle.split(",")) {
            part = part.trim();
            int star = part.indexOf('*');
            if (star < 0)
                return;
            try {
                int slot = Integer.parseInt(part.substring(0, star));
                int run = Integer.parseInt(part.substring(star + 1));
                for (int k = 0; k < run && x < tw; k++)
                    row[x++] = slot;
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

    private static int[] parsePoint(String line) {
        String[] p = line.substring(1).split(",");
        if (p.length < 3)
            return null;
        try {
            return new int[] {
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim()),
                    parseSlot(p[2])
            };
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int parseSlot(String s) {
        s = s.trim().replace(':', ',').split(",")[0].trim();
        return Integer.parseInt(s);
    }

    public static class Validation {
        public boolean ok;
        public String errors = "";
    }

    /** Check decoded grid and clamp out-of-range slots to 0. */
    public static Validation validateAndNormalize(int[][] grid, int tw, int th, int maxSlot) {
        Validation v = new Validation();
        v.ok = true;
        StringBuilder err = new StringBuilder();
        boolean any = false;
        for (int y = 0; y < th; y++) {
            for (int x = 0; x < tw; x++) {
                int slot = grid[y][x];
                if (slot != 0)
                    any = true;
                if (slot < 0 || slot > maxSlot) {
                    grid[y][x] = 0;
                    if (v.ok) {
                        err.append("slot out of range (0-").append(maxSlot).append(")");
                        v.ok = false;
                    }
                }
            }
        }
        if (!any) {
            v.ok = false;
            err.append("no pixels drawn");
        }
        int rowsWithData = 0;
        for (int y = 0; y < th; y++) {
            boolean rowEmpty = true;
            for (int x = 0; x < tw; x++) {
                if (grid[y][x] != 0) {
                    rowEmpty = false;
                    break;
                }
            }
            if (!rowEmpty)
                rowsWithData++;
        }
        if (rowsWithData < Math.max(1, th / 4)) {
            v.ok = false;
            if (err.length() > 0)
                err.append("; ");
            err.append("too few rows with content (").append(rowsWithData).append('/').append(th).append(')');
        }
        v.errors = err.toString();
        return v;
    }

    public static Validation validateBlock(String block, int tw, int th, int maxSlot) {
        Validation v = new Validation();
        v.ok = true;
        StringBuilder err = new StringBuilder();
        if (block == null || block.trim().isEmpty()) {
            v.ok = false;
            v.errors = "empty PIXEL block";
            return v;
        }
        boolean hasFill = false;
        boolean[] rowSeen = new boolean[th];
        for (String line : block.split("\n")) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#"))
                continue;
            if (line.startsWith("F")) {
                hasFill = true;
                try {
                    int slot = parseSlot(line.substring(1));
                    if (slot < 0 || slot > maxSlot) {
                        v.ok = false;
                        err.append("F uses invalid slot ").append(slot).append(". ");
                    }
                } catch (NumberFormatException e) {
                    v.ok = false;
                    err.append("bad F line. ");
                }
                continue;
            }
            if (line.startsWith("R")) {
                int comma = line.indexOf(',');
                if (comma < 0) {
                    v.ok = false;
                    err.append("bad R line. ");
                    continue;
                }
                try {
                    int y = Integer.parseInt(line.substring(1, comma).trim());
                    if (y < 0 || y >= th) {
                        v.ok = false;
                        err.append("R row y=").append(y).append(" out of range 0-").append(th - 1).append(". ");
                        continue;
                    }
                    int width = measureRowWidth(line.substring(comma + 1));
                    if (width != tw) {
                        v.ok = false;
                        err.append("R").append(y).append(" width=").append(width).append(" expected ")
                                .append(tw).append(". ");
                    } else {
                        rowSeen[y] = true;
                    }
                } catch (NumberFormatException e) {
                    v.ok = false;
                    err.append("bad R line. ");
                }
            }
        }
        if (!hasFill) {
            int missing = 0;
            for (boolean b : rowSeen) {
                if (!b)
                    missing++;
            }
            if (missing > 0) {
                v.ok = false;
                err.append("missing R rows (need R0..R").append(th - 1).append(" or F fill). ");
            }
        }
        v.errors = err.toString().trim();
        if (v.errors.isEmpty() && !v.ok)
            v.errors = "invalid output";
        return v;
    }

    private static int measureRowWidth(String rle) {
        int x = 0;
        for (String part : rle.split(",")) {
            part = part.trim();
            int star = part.indexOf('*');
            if (star < 0)
                return -1;
            try {
                x += Integer.parseInt(part.substring(star + 1));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return x;
    }

    public static boolean wantsFullReplace(String block) {
        if (block == null)
            return false;
        for (String line : block.split("\n")) {
            line = line.trim();
            if (line.startsWith("F") || line.startsWith("R"))
                return true;
        }
        return false;
    }

    public static String extractPixelBlock(String aiText) {
        if (aiText == null)
            return "";
        String t = aiText.trim();
        int pix = t.indexOf("PIXEL");
        int end = t.lastIndexOf("END");
        if (pix >= 0) {
            int start = pix + 5;
            if (end > pix)
                return t.substring(start, end).trim();
            return t.substring(start).trim();
        }
        int draw = t.indexOf("DRAW");
        if (draw >= 0) {
            int start = draw + 4;
            if (end > draw)
                return t.substring(start, end).trim();
            return t.substring(start).trim();
        }
        return t;
    }
}
