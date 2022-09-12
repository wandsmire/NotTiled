package com.mirwanda.nottiled;

//Tiled JSON latest version
public class jsonmap {
    String type;
    String tiledversion;
    String orientation;
    String renderorder;
    float version;
    boolean infinite;
    int width;
    int height;
    int tilewidth;
    int tileheight;
    int compressionlevel;
    int nextlayerid;
    int nextobjectid;
    layer[] layers;
    tileset[] tilesets;
    property[] properties;

    public static class layer{
        String name;
        String type;
        int id;
        float opacity;
        float x;
        float y;
        boolean visible;
        property[] properties;
        String data;
        String encoding;
        String compression;
        int height;
        int width;
        String draworder;
        object[] objects;
        String image;

    }

    public static class object{
        String name;
        String class_x;
        float x;
        float y;
        int width;
        int height;
        int id;
        int rotation;
        boolean visible;
        poly[] polygon;
        poly[] polyline;
        property[] properties;
    }

    public static class tileset{
        String image;
        int imagewidth;
        int imageheight;

        String name;
        int firstgid;
        int tilewidth;
        int tileheight;
        int columns;
        int tilecount;
        int margin;
        int spacing;

        tile[] tiles;
        property[] properties;
        wangset[] wangsets;
        terrain[] terraintypes;
    }

    public static class tile{
        int id;
        String terrain;
        property[] properties;
        animate[] animation;
        objectgrp objectgroup;
    }

    public static class objectgrp{
        String draworder;
        String name;
        String type;
        boolean visible;
        float opacity;
        float x;
        float y;
        object[] objects;
    }

    public static class animate{
        int duration;
        int tileid;
    }

    public static class wangset{
        String name;
        String type;
        int tile;
        wangtile[] wangtiles;
        color[] colors;
    }

    public static class color{
        String color;
        String name;
        float probability;
        int tile;
    }

    public static class wangtile{
        int tileid;
        int[] wangid;
    }

    public static class terrain{
        String name;
        int tile;
    }

    public static class poly{
        float x;
        float y;
    }

    public static class property{
        String name;
        String propertytype;
        String type;
        String value;
    }



}
