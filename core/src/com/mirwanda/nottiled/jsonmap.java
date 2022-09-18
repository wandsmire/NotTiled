package com.mirwanda.nottiled;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

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

    public static class layer {//implements Json.Serializable {
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

        /*
        @Override
        public void write(Json json) {
            json.writeValue(name);
            json.writeValue(type);
            json.writeValue(id);
            json.writeValue(opacity);
            json.writeValue(x);
            json.writeValue(y);
            json.writeValue(visible);
            json.writeValue(properties);

            json.writeValue(encoding);
            json.writeValue(compression);
            json.writeValue(height);
            json.writeValue(width);
            json.writeValue(draworder);
            json.writeValue(objects);
            json.writeValue(image);
        }
        @Override
        public void read(Json json, JsonValue jsonData) {
            //System.out.print(jsonData.toString());
            for (JsonValue child = jsonData.child; child != null; child = child.next) {
                System.out.print(child);
            }

         */
/*
            for(JsonValue jv : jsonData){
                switch(jv.name){
                    case "name": name = json.readValue(String.class,jv); break;
                    case "type": type = json.readValue(String.class,jv); break;
                    case "id": id = json.readValue(Integer.class,jv); break;
                    case "opacity": opacity = json.readValue(Float.class,jv); break;
                    case "x": x = json.readValue(Float.class,jv); break;
                    case "y": y = json.readValue(Float.class,jv); break;
                    case "visible": visible = json.readValue(Boolean.class,jv);  break;
                    case "encoding": encoding = json.readValue(String.class,jv); break;
                    case "compression": compression = json.readValue(String.class,jv); break;
                    case "width": width = json.readValue(Integer.class,jv); break;
                    case "height": height = json.readValue(Integer.class,jv); break;
                    case "draworder": draworder = json.readValue(String.class,jv); break;
                    case "image": image = json.readValue(String.class,jv); break;
                    case "properties": properties=json.readValue(property[].class,jv); break;
                    case "objects": objects=json.readValue(object[].class,jv); break;
                    case "data":

                }
            }


        }
*/
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
