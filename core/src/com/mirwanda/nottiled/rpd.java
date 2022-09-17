package com.mirwanda.nottiled;

public class rpd {
    Integer width; //
    Integer height; //
    boolean customTiles = false; //
    boolean boss_level;

    String tiles; //
    String tiles_base; //
    String tiles_deco; //
    String tiles_deco2; //
    String tiles_logic; //
    String tiles_roof_base; //
    String tiles_roof_deco; //
    String tiles_mobs;
    String tiles_objects;
    String water; //

    Integer[]entrance; //
    Integer[][]multiexit; // **
    Integer[]compassTarget;

    Integer[] map; //
    Integer[] baseTileVar; //
    Integer[] decoTileVar; //
    Integer[] deco2TileVar; //
    Integer[] roofBaseTileVar; //
    Integer[] roofDecoTileVar; //
    String[] decoDesc; //
    String[] decoName; //
    obj[] mobs; //
    obj[] items; //
    obj[] objects; //

    public static class obj {
        String kind;
        int x;
        int y;
        String levelId;
        int depth;
        tele target;
        String text;
        int uses;
        String trapKind;
        String script;
        String object_desc;
        int level;
    }

    public static class tele{
        String levelId;
        int x;
        int y;
    }
}
