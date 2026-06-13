package com.mirwanda.nottiled;

import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g3d.Model;

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
    private com.badlogic.gdx.graphics.g3d.ModelInstance[] layerModelInstances;
    private com.badlogic.gdx.graphics.g3d.ModelInstance shadowModelInstance;
    /** The raw Model backing shadowModelInstance — must be disposed when the chunk is rebuilt. */
    private Model shadowModel;

    public com.badlogic.gdx.graphics.g3d.ModelInstance getModelInstance() {
        return modelInstance;
    }

    public void setModelInstance(com.badlogic.gdx.graphics.g3d.ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    public com.badlogic.gdx.graphics.g3d.ModelInstance[] getLayerModelInstances() {
        return layerModelInstances;
    }

    public void setLayerModelInstances(com.badlogic.gdx.graphics.g3d.ModelInstance[] layerModelInstances) {
        this.layerModelInstances = layerModelInstances;
    }

    public com.badlogic.gdx.graphics.g3d.ModelInstance getShadowModelInstance() {
        return shadowModelInstance;
    }

    public void setShadowModelInstance(com.badlogic.gdx.graphics.g3d.ModelInstance shadowModelInstance) {
        this.shadowModelInstance = shadowModelInstance;
    }

    public Model getShadowModel() {
        return shadowModel;
    }

    public void setShadowModel(Model shadowModel) {
        this.shadowModel = shadowModel;
    }

    /** Dispose the shadow model to free GPU memory before rebuilding or destroying this chunk. */
    public void disposeShadowModel() {
        if (shadowModel != null) {
            shadowModel.dispose();
            shadowModel = null;
            shadowModelInstance = null;
        }
    }

    public void disposeModel() {
        if (modelInstance != null && modelInstance.model != null) {
            modelInstance.model.dispose();
            modelInstance = null;
        }
        if (layerModelInstances != null) {
            // Models are shared, already disposed above if it was part of modelInstance
            layerModelInstances = null;
        }
    }

    public static class AnimatedTileRef {
        public final int layer;
        public final int position;
        public final long ini;
        public final int initset;
        public final String flag;
        public final int tilesetTileIndex;
        public final float tileZ;

        public AnimatedTileRef(int layer, int position, long ini, int initset, String flag, int tilesetTileIndex, float tileZ) {
            this.layer = layer;
            this.position = position;
            this.ini = ini;
            this.initset = initset;
            this.flag = flag;
            this.tilesetTileIndex = tilesetTileIndex;
            this.tileZ = tileZ;
        }
    }

    private List<AnimatedTileRef> animatedTiles = new ArrayList<AnimatedTileRef>();

    public List<AnimatedTileRef> getAnimatedTiles() {
        return animatedTiles;
    }

    public void setAnimatedTiles(List<AnimatedTileRef> animatedTiles) {
        this.animatedTiles = animatedTiles;
    }

    private List<Decal> decals = new ArrayList<Decal>();
    private List<Decal>[] layerDecals;

    public List<Decal> getDecals() {
        return decals;
    }

    public void setDecals(List<Decal> decals) {
        this.decals = decals;
    }

    public List<Decal>[] getLayerDecals() {
        return layerDecals;
    }

    public void setLayerDecals(List<Decal>[] layerDecals) {
        this.layerDecals = layerDecals;
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
