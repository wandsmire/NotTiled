package com.mirwanda.nottiled.platformer;

public class KV {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String key;
    public int value;
    public boolean hud;
    public int iconx;
    public boolean hasicon;

    public boolean isKeephud() {
        return keephud;
    }

    public void setKeephud(boolean keephud) {
        this.keephud = keephud;
    }

    public boolean keephud;

    public boolean isHasicon() {
        return hasicon;
    }

    public void setHasicon(boolean hasicon) {
        this.hasicon = hasicon;
    }

    public int icony;

    public int getIcony() {
        return icony;
    }

    public void setIcony(int icony) {
        this.icony = icony;
    }

    public int getIconx() {
        return iconx;
    }

    public void setIconx(int iconx) {
        this.iconx = iconx;
    }

    public boolean isHud() {
        return hud;
    }

    public void setHud(boolean hud) {
        this.hud = hud;
    }

    public KV(){}
    public KV(String key, int value){
        this.key=key; this.value=value;
    }
}