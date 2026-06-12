package com.mirwanda.nottiled;

import com.badlogic.gdx.graphics.g2d.SpriteCache;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import java.util.List;

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

    private int[] cacheIDs;

    public int[] getCacheIDs() {
        return cacheIDs;
    }

    public void setCacheIDs(int[] cacheIDs) {
        this.cacheIDs = cacheIDs;
    }

    private com.badlogic.gdx.graphics.g3d.ModelInstance modelInstance;

    public com.badlogic.gdx.graphics.g3d.ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(com.badlogic.gdx.graphics.g3d.ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    private List<Decal> decals = new ArrayList<Decal>();

    public List<Decal> getDecals() {
        return decals;
    }

    public void setDecals(List<Decal> decals) {
        this.decals = decals;
    }

    public TileCache(SpriteCache cache, int cacheID, int intex, int intey){
        this.cache = cache;
        this.cacheID = cacheID;
        this.intex = intex;
        this.intey = intey;
        this.changed = false;
    }

    public TileCache(SpriteCache cache, int[] cacheIDs, int intex, int intey){
        this.cache = cache;
        this.cacheIDs = cacheIDs;
        this.intex = intex;
        this.intey = intey;
        this.changed = false;
    }
}
