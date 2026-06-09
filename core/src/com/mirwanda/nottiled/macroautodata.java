package com.mirwanda.nottiled;

import java.util.ArrayList;

public class macroautodata {
	private int selTerrain;
	private java.util.List<terrain> terrains = new ArrayList<terrain>();
	private java.util.List<gidterraintile> tiles = new ArrayList<gidterraintile>();

	public macroautodata() {
	}

	public int getSelTerrain() {
		return selTerrain;
	}

	public void setSelTerrain(int selTerrain) {
		this.selTerrain = selTerrain;
	}

	public java.util.List<terrain> getTerrains() {
		return terrains;
	}

	public void setTerrains(java.util.List<terrain> terrains) {
		this.terrains = terrains;
	}

	public java.util.List<gidterraintile> getTiles() {
		return tiles;
	}

	public void setTiles(java.util.List<gidterraintile> tiles) {
		this.tiles = tiles;
	}
}
