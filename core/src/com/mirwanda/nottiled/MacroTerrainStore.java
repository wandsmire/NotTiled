package com.mirwanda.nottiled;

import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Global (GID-based) terrain autotile data stored outside the TMX for Tiled compatibility. */
public class MacroTerrainStore {
	public static final String FILE_NAME = "macro auto.json";

	private final java.util.List<terrain> terrains = new ArrayList<terrain>();
	private final HashMap<Integer, tile> gidTiles = new HashMap<Integer, tile>();
	private int selTerrain = 0;
	private Map<String, List<Integer>> terrainGidMap = null;

	public java.util.List<terrain> getTerrains() {
		return terrains;
	}

	public int getSelTerrain() {
		return selTerrain;
	}

	public void setSelTerrain(int selTerrain) {
		this.selTerrain = selTerrain;
	}

	public void clear() {
		terrains.clear();
		gidTiles.clear();
		selTerrain = 0;
		clearTerrainCache();
	}

	public void clearTerrainCache() {
		terrainGidMap = null;
	}

	public tile getTileMeta(int gid) {
		return gidTiles.get(gid);
	}

	public tile getOrCreateTileMeta(int gid) {
		tile t = gidTiles.get(gid);
		if (t == null) {
			t = new tile();
			t.setTileID(gid);
			gidTiles.put(gid, t);
		}
		return t;
	}

	public void removeTileMetaIfEmpty(int gid) {
		tile t = gidTiles.get(gid);
		if (t == null)
			return;
		int[] tr = t.getTerrain();
		if (tr != null && (tr[0] != -1 || tr[1] != -1 || tr[2] != -1 || tr[3] != -1))
			return;
		if (t.getProperties().size() > 0 || t.getAnimation().size() > 0 || t.getObjects().size() > 0)
			return;
		gidTiles.remove(gid);
		clearTerrainCache();
	}

	public java.util.List<Integer> getGidsByTerrain(String terrainStr) {
		if (terrainGidMap == null) {
			terrainGidMap = new HashMap<String, List<Integer>>();
			for (Map.Entry<Integer, tile> e : gidTiles.entrySet()) {
				tile t = e.getValue();
				if (t == null || !t.isTerrainForEditor())
					continue;
				String key = t.getTerrainString();
				List<Integer> list = terrainGidMap.get(key);
				if (list == null) {
					list = new ArrayList<Integer>();
					terrainGidMap.put(key, list);
				}
				list.add(e.getKey());
			}
		}
		List<Integer> result = terrainGidMap.get(terrainStr);
		return result != null ? result : new ArrayList<Integer>(0);
	}

	public macroautodata toData() {
		macroautodata data = new macroautodata();
		data.setSelTerrain(selTerrain);
		data.setTerrains(new ArrayList<terrain>(terrains));
		java.util.List<gidterraintile> entries = new ArrayList<gidterraintile>();
		for (Map.Entry<Integer, tile> e : gidTiles.entrySet()) {
			tile t = e.getValue();
			if (t == null || !t.isTerrainForEditor())
				continue;
			entries.add(new gidterraintile(e.getKey(), t.getTerrainString()));
		}
		data.setTiles(entries);
		return data;
	}

	public void fromData(macroautodata data) {
		clear();
		if (data == null)
			return;
		selTerrain = data.getSelTerrain();
		if (data.getTerrains() != null)
			terrains.addAll(data.getTerrains());
		if (data.getTiles() != null) {
			for (gidterraintile gt : data.getTiles()) {
				tile t = new tile();
				t.setTileID(gt.getGid());
				t.setTerrain(gt.getTerrain());
				gidTiles.put(gt.getGid(), t);
			}
		}
		clampSelTerrain();
	}

	public void clampSelTerrain() {
		if (terrains.isEmpty())
			selTerrain = 0;
		else if (selTerrain < 0)
			selTerrain = 0;
		else if (selTerrain >= terrains.size())
			selTerrain = terrains.size() - 1;
	}

	public String toJsonPretty() {
		Json json = new Json();
		return json.prettyPrint(toData());
	}

	public void fromJsonString(String text) {
		Json json = new Json();
		fromData(json.fromJson(macroautodata.class, text));
	}

	public java.util.Collection<tile> getAllTileMeta() {
		return gidTiles.values();
	}

	public boolean isEmpty() {
		return terrains.isEmpty() && gidTiles.isEmpty();
	}
}
