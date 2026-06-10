package com.mirwanda.nottiled;

/** Lightweight reference to an object on an object layer for the Object List UI. */
public class ObjectListRef {
	public int layerIdx;
	public int objectIdx;
	public obj object;

	public ObjectListRef(int layerIdx, int objectIdx, obj object) {
		this.layerIdx = layerIdx;
		this.objectIdx = objectIdx;
		this.object = object;
	}
}
