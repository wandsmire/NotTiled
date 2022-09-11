package com.mirwanda.nottiled;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class layer
{
	public List<Long> gid = new ArrayList<Long>();
	public List<Integer> tset = new ArrayList<Integer>();

	public List<Integer> getTile() {
		return tile;
	}

	public void setTile(List<Integer> tile) {
		this.tile = tile;
	}

	public List<Integer> tile = new ArrayList<Integer>();
	public List<Color> color = new ArrayList<Color>();
	public List<property> properties = new ArrayList<property>();
	private String name="";
	private boolean visible;
	private float opacity;
	private boolean locked = false;
	private float offsetX=0f;
	private float offsetY=0f;
	private String image;
	private int imagewidth;
	private int imageheight;

	public void clearLayer(){
		for (int i=0;i<gid.size();i++){
			if (gid.get(i)!=0){
				gid.set(i,(long) 0);
				tset.set(i,-1);
				tile.set(i,-1);
			}
		}
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	private Texture texture;

	public Pixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(Pixmap pixmap) {
		this.pixmap = pixmap;
	}

	private Pixmap pixmap;

	public String getTrans() {
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	private String trans;

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getImagewidth() {
		return imagewidth;
	}

	public void setImagewidth(int imagewidth) {
		this.imagewidth = imagewidth;
	}

	public int getImageheight() {
		return imageheight;
	}

	public void setImageheight(int imageheight) {
		this.imageheight = imageheight;
	}



	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	private Type type;
	public enum Type { TILE,OBJECT,GROUP,IMAGE }
	private java.util.List<obj> objects = new ArrayList<obj>();

	public void setObjects(java.util.List<obj> objects)
	{
		this.objects = objects;
	}

	public java.util.List<obj> getObjects()
	{
		return objects;
	}

	public void setTset(List<Integer> tset)
	{
		this.tset = tset;
	}

	public List<Integer> getTset()
	{
		return tset;
	}

	public void setProperties(List<property> properties)
	{
		this.properties = properties;
	}

	public List<property> getProperties()
	{
		return properties;
	}

	public void setOpacity(float opacity)
	{
		this.opacity = opacity;
	}

	public float getOpacity()
	{
		return opacity;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setStr(List<Long> str)
	{
		this.gid = str;
	}

	public List<Long> getStr()
	{
		return gid;
	}
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}