package com.mirwanda.nottiled;

import com.badlogic.gdx.graphics.g2d.SpriteCache;

import java.util.ArrayList;

public class TileCache {
    private SpriteCache cache;
    private int cacheID;
    private boolean changed;
    private int intex;
    private int intey;

    public SpriteCache getCache() {
        return cache;
    }

    public void setCache(SpriteCache cache) {
        this.cache = cache;
    }

    public int getCacheID() {
        return cacheID;
    }

    public void setCacheID(int cacheID) {
        this.cacheID = cacheID;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getIntex() {
        return intex;
    }

    public void setIntex(int intex) {
        this.intex = intex;
    }

    public int getIntey() {
        return intey;
    }

    public void setIntey(int intey) {
        this.intey = intey;
    }

    public TileCache(SpriteCache cache, int cacheID, int intex, int intey){
        this.cache = cache;
        this.cacheID = cacheID;
        this.intex = intex;
        this.intey = intey;
        this.changed = false;
    }
}
