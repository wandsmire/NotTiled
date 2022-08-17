package com.mirwanda.nottiled;
import com.badlogic.gdx.graphics.*;
import java.util.*;

public class tileset
{
	private java.util.List<tile>tiles = new ArrayList<tile>();
	private java.util.List<terrain> terrains = new ArrayList<terrain>();
	private java.util.List<property> properties = new java.util.ArrayList<property>();
	private java.util.List<Color> colors = new java.util.ArrayList<Color>();

	public List<obj> getCollision() {
		return collision;
	}

	public void setCollision(List<obj> collision) {
		this.collision = collision;
	}

	private java.util.List<obj> collision = new ArrayList<obj>();

	private String name;
	private String source;
	private String trans;
	private int width;
	private int height;
	private int firstgid;
	private int tilewidth;
	private int tileheight;
	private int tilecount;
	private int spacing=0;
	private int margin=0;
	private int originalheight;
	private int originalwidth;
	private int columns;
	private int selTerrain=0;
	private Texture texture;
	private Pixmap pixmap;
	private Texture etc1texture;
	private String tsxfile;
	private boolean usetsx;
	public tileset(){
		
	}
	public tileset(String name, String source, int width, int height, int tilewidth, int tileheight, int tilecount, int columns, int firstgid, String trans){
		this.name = name;
		this.source = source;
		this.width= width;
		this.height =height;
		this.trans=trans;
		this.firstgid = firstgid;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.tilecount= tilecount;
		this.columns= columns;
		
	}
	public void setPixmap(Pixmap pixmap)
	{
		this.pixmap = pixmap;
	}

	public Pixmap getPixmap()
	{
		return pixmap;
	}

	public void setProperties(java.util.List<property> properties)
	{
		this.properties = properties;
	}

	public java.util.List<property> getProperties()
	{
		return properties;
	}

	public void setEtc1texture(Texture etc1texture)
	{
		this.etc1texture = etc1texture;
	}

	public Texture getEtc1texture()
	{
		return etc1texture;
	}

	public void setSelTerrain(int selTerrain)
	{
		this.selTerrain = selTerrain;
	}

	public int getSelTerrain()
	{
		return selTerrain;
	}

	public void setTerrains(java.util.List<terrain> terrains)
	{
		this.terrains = terrains;
	}

	public java.util.List<terrain> getTerrains()
	{
		return terrains;
	}

	public void setTsxfile(String tsxfile)
	{
	this.tsxfile = tsxfile;
	}

	public String getTsxfile()
	{
	return tsxfile;
	}

	public void setUsetsx(boolean usetsx)
	{
	this.usetsx = usetsx;
	}

	public boolean isUsetsx()
	{
	return usetsx;
	}

	

	public void setTiles(java.util.List<tile> tiles)
	{
		this.tiles = tiles;
	}

	public java.util.List<tile> getTiles()
	{
		return tiles;
	}

	public void setOriginalheight(int originalheight)
	{
		this.originalheight = originalheight;
	}

	public int getOriginalheight()
	{
		return originalheight;
	}

	public void setOriginalwidth(int originalwidth)
	{
		this.originalwidth = originalwidth;
	}

	public int getOriginalwidth()
	{
		return originalwidth;
	}

	public void setSpacing(int spacing)
	{
		this.spacing = spacing;
	}

	public int getSpacing()
	{
		return spacing;
	}

	public void setMargin(int margin)
	{
		this.margin = margin;
	}

	public int getMargin()
	{
		return margin;
	}

	public void setTrans(String trans)
	{
		this.trans = trans;
	}

	public String getTrans()
	{
		return trans;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public Texture getTexture()
	{
		return texture;
	}
	public void setTilecount(int tilecount)
	{
		this.tilecount = tilecount;
	}

	public int getTilecount()
	{
		return tilecount;
	}

	public void setColumns(int columns)
	{
		this.columns = columns;
	}

	public int getColumns()
	{
		return columns;
	}

	public void setFirstgid(int firstgid)
	{
		this.firstgid = firstgid;
	}

	public int getFirstgid()
	{
		return firstgid;
	}

	public void setTilewidth(int tilewidth)
	{
		this.tilewidth = tilewidth;
	}

	public int getTilewidth()
	{
		return tilewidth;
	}

	public void setTileheight(int tileheight)
	{
		this.tileheight = tileheight;
	}

	public int getTileheight()
	{
		return tileheight;
	}
	


	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getWidth()
	{
		return width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getHeight()
	{
		return height;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getSource()
	{
		return source;
	}}
