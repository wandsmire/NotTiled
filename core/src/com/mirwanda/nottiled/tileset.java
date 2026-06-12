package com.mirwanda.nottiled;
import com.badlogic.gdx.graphics.*;
import java.util.*;

public class tileset
{
	public java.util.List<tile>tiles = new ArrayList<tile>();
	public java.util.List<terrain> terrains = new ArrayList<terrain>();
	public java.util.List<property> properties = new java.util.ArrayList<property>();
	public java.util.List<Color> colors = new java.util.ArrayList<Color>();

	public List<obj> getCollision() {
		return collision;
	}

	public void setCollision(List<obj> collision) {
		this.collision = collision;
	}

	public java.util.List<obj> collision = new ArrayList<obj>();

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
	private boolean collection;
	private String collectionBase = "";
	private Map<Integer, Integer> gridIndexForTileId = new HashMap<Integer, Integer>();
	private int[] gridIndexToTileId = new int[0];
	private java.util.HashMap<Integer, Boolean> opaqueCache = new java.util.HashMap<Integer, Boolean>();

	public boolean isBillboardSprite(int localTileId) {
		tile t = getTileMeta(localTileId);
		if (t != null) {
			for (property p : t.getProperties()) {
				if (p.getName().equalsIgnoreCase("sprite")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isTileOpaque(int localTileId) {
		Boolean cached = opaqueCache.get(localTileId);
		if (cached != null) return cached;

		if (getPixmap() == null) {
			opaqueCache.put(localTileId, false);
			return false;
		}
		
		if (isCollection()) {
			opaqueCache.put(localTileId, false);
			return false;
		}

		int x = getSprX(localTileId);
		int y = getSprY(localTileId);
		int tw = getTilewidth();
		int th = getTileheight();
		
		boolean opaque = true;
		for (int i = 0; i < tw; i++) {
			for (int j = 0; j < th; j++) {
				int pixel = getPixmap().getPixel(x + i, y + j);
				if ((pixel & 0x000000ff) == 0) { // any fully transparent pixel means not opaque
					opaque = false;
					break;
				}
			}
			if (!opaque) break;
		}
		opaqueCache.put(localTileId, opaque);
		return opaque;
	}
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
		clearTerrainCache();
	}

	public java.util.List<tile> getTiles()
	{
		return tiles;
	}

	/** Keep tile metadata in ascending local tile ID order (Tiled 1.7+ palette order). */
	public void sortTilesById()
	{
		if (tiles.size() < 2)
			return;
		Collections.sort(tiles, new Comparator<tile>() {
			@Override
			public int compare(tile a, tile b) {
				return Integer.compare(a.getTileID(), b.getTileID());
			}
		});
		clearTerrainCache();
	}

	/** Insert or return index of tile metadata entry sorted by tile ID. */
	public int addTileMetadata(tile t)
	{
		int id = t.getTileID();
		for (int i = 0; i < tiles.size(); i++) {
			int existing = tiles.get(i).getTileID();
			if (existing == id)
				return i;
			if (existing > id) {
				tiles.add(i, t);
				clearTerrainCache();
				return i;
			}
		}
		tiles.add(t);
		clearTerrainCache();
		return tiles.size() - 1;
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
	}

	private Map<String, List<Integer>> terrainTileMap = null;

	public List<Integer> getTilesByTerrain(String terrainStr) {
		if (terrainTileMap == null) {
			terrainTileMap = new HashMap<String, List<Integer>>();
			for (int i = 0; i < tiles.size(); i++) {
				tile t = tiles.get(i);
				if (collection && !hasTileImage(t.getTileID()))
					continue;
				String key = t.getTerrainString();
				List<Integer> list = terrainTileMap.get(key);
				if (list == null) {
					list = new ArrayList<Integer>();
					terrainTileMap.put(key, list);
				}
				list.add(i);
			}
		}
		List<Integer> result = terrainTileMap.get(terrainStr);
		return result != null ? result : new ArrayList<Integer>(0);
	}

	public void clearTerrainCache() {
		terrainTileMap = null;
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

	public String getCollectionBase() {
		return collectionBase;
	}

	public void setCollectionBase(String collectionBase) {
		this.collectionBase = collectionBase != null ? collectionBase : "";
	}

	public void setGridIndexMap(Map<Integer, Integer> gridIndexForTileId) {
		this.gridIndexForTileId = gridIndexForTileId != null
				? new HashMap<Integer, Integer>(gridIndexForTileId)
				: new HashMap<Integer, Integer>();
		rebuildGridIndexToTileIdArray();
	}

	public void mapLocalTileToGrid(int localTileId, int gridIndex) {
		if (!collection || localTileId < 0 || gridIndex < 0)
			return;
		gridIndexForTileId.put(localTileId, gridIndex);
		rebuildGridIndexToTileIdArray();
	}

	private void rebuildGridIndexToTileIdArray() {
		int maxGi = -1;
		for (Integer gi : gridIndexForTileId.values()) {
			if (gi > maxGi)
				maxGi = gi;
		}
		if (maxGi < 0) {
			gridIndexToTileId = new int[0];
			return;
		}
		gridIndexToTileId = new int[maxGi + 1];
		java.util.Arrays.fill(gridIndexToTileId, -1);
		for (Map.Entry<Integer, Integer> e : gridIndexForTileId.entrySet()) {
			int gi = e.getValue();
			if (gi >= 0 && gi < gridIndexToTileId.length)
				gridIndexToTileId[gi] = e.getKey();
		}
	}

	public int getGridIndex(int localTileId) {
		if (!collection)
			return localTileId;
		Integer gi = gridIndexForTileId.get(localTileId);
		return gi != null ? gi : -1;
	}

	public int getSprX(int localTileId) {
		return CollectionTilesetOps.getSprX(this, localTileId);
	}

	public int getSprY(int localTileId) {
		return CollectionTilesetOps.getSprY(this, localTileId);
	}

	public int getSrcX(int localTileId) {
		return CollectionTilesetOps.getSrcX(this, localTileId);
	}

	public int getSrcY(int localTileId) {
		return CollectionTilesetOps.getSrcY(this, localTileId);
	}

	public boolean hasTileImage(int localTileId) {
		return CollectionTilesetOps.hasTileImage(this, localTileId);
	}

	public int nextCollectionTileId() {
		return CollectionTilesetOps.nextTileId(this);
	}

	public tile getTileMeta(int localTileId) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getTileID() == localTileId)
				return tiles.get(i);
		}
		return null;
	}

	public int findTileListIndex(int localTileId) {
		for (int i = 0; i < tiles.size(); i++) {
			if (tiles.get(i).getTileID() == localTileId)
				return i;
		}
		return -1;
	}

	public int getTileIdAtGridIndex(int gridIndex) {
		if (!collection)
			return gridIndex;
		if (gridIndex < 0 || gridIndex >= gridIndexToTileId.length)
			return -1;
		return gridIndexToTileId[gridIndex];
	}

	public long getGidAtGridIndex(int gridIndex) {
		return (long) getFirstgid() + getTileIdAtGridIndex(gridIndex);
	}
}
