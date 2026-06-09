package com.mirwanda.nottiled;

public class gidterraintile {
	private int gid;
	private String terrain;

	public gidterraintile() {
	}

	public gidterraintile(int gid, String terrain) {
		this.gid = gid;
		this.terrain = terrain;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}
}
