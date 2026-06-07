package com.mirwanda.nottiled;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Helpers for Tiled-style image collection tilesets (uniform tile size). */
public final class CollectionTilesetOps {

	public static final String PROP_COLLECTION_BASE = "nottiled_collection_base";
	public static final String PROP_IMAGE_SRC_X = "nottiled_image_x";
	public static final String PROP_IMAGE_SRC_Y = "nottiled_image_y";

	private CollectionTilesetOps() {
	}

	public static void applyTilesetProperty(tileset ts, String name, String value) {
		if (name == null || value == null)
			return;
		if (PROP_COLLECTION_BASE.equalsIgnoreCase(name))
			ts.setCollectionBase(value);
	}

	public static void applyTileProperty(tile t, String name, String value) {
		if (name == null || value == null || t == null)
			return;
		try {
			if (PROP_IMAGE_SRC_X.equalsIgnoreCase(name))
				t.setImageSrcX(Integer.parseInt(value.trim()));
			else if (PROP_IMAGE_SRC_Y.equalsIgnoreCase(name))
				t.setImageSrcY(Integer.parseInt(value.trim()));
		} catch (NumberFormatException ignored) {
		}
	}

	/** True when tile image source offset should be written to TMX/JSON (including 0,0). */
	public static boolean hasCollectionTileWriteProperties(tile t) {
		return hasCollectionTileWriteProperties(t, 1, 1);
	}

	public static boolean hasCollectionTileWriteProperties(tile t, int tileWidth, int tileHeight) {
		if (t == null || !t.hasImage())
			return false;
		if (t.getImageSrcX() != 0 || t.getImageSrcY() != 0)
			return true;
		int tw = Math.max(1, tileWidth);
		int th = Math.max(1, tileHeight);
		return t.getImageWidth() > tw || t.getImageHeight() > th;
	}

	public static boolean sheetHasTileAtOrigin(List<tile> group, int tileWidth, int tileHeight,
			int margin, int spacing) {
		if (group == null || group.isEmpty())
			return false;
		int originX = sheetSrcX(0, tileWidth, margin, spacing);
		int originY = sheetSrcY(0, tileHeight, margin, spacing);
		for (int i = 0; i < group.size(); i++) {
			tile t = group.get(i);
			if (t.getImageSrcX() == originX && t.getImageSrcY() == originY)
				return true;
		}
		return false;
	}

	public static boolean needsCollectionBaseProperty(tileset ts) {
		return ts != null && ts.isCollection()
				&& ts.getCollectionBase() != null && !ts.getCollectionBase().isEmpty();
	}

	public static int sheetColumns(int imageWidth, int tileWidth, int margin, int spacing) {
		int stride = tileWidth + spacing;
		if (tileWidth <= 0 || stride <= 0)
			return 1;
		int usable = imageWidth - margin * 2;
		if (usable < tileWidth)
			return 1;
		return (usable + spacing) / stride;
	}

	public static int sheetRows(int imageHeight, int tileHeight, int margin, int spacing) {
		int stride = tileHeight + spacing;
		if (tileHeight <= 0 || stride <= 0)
			return 1;
		int usable = imageHeight - margin * 2;
		if (usable < tileHeight)
			return 1;
		return (usable + spacing) / stride;
	}

	public static int sheetSrcX(int col, int tileWidth, int margin, int spacing) {
		return margin + col * (tileWidth + spacing);
	}

	public static int sheetSrcY(int row, int tileHeight, int margin, int spacing) {
		return margin + row * (tileHeight + spacing);
	}

	public static int sheetColFromSrcX(int srcX, int tileWidth, int margin, int spacing) {
		int stride = tileWidth + spacing;
		if (stride <= 0)
			return 0;
		return (srcX - margin) / stride;
	}

	public static int sheetRowFromSrcY(int srcY, int tileHeight, int margin, int spacing) {
		int stride = tileHeight + spacing;
		if (stride <= 0)
			return 0;
		return (srcY - margin) / stride;
	}

	public static int sheetTileIndex(int col, int row, int columns) {
		return row * Math.max(1, columns) + col;
	}

	/** Grid cells in the picker atlas (collection) or tile count (regular). */
	public static int getPickerGridSlots(tileset ts) {
		if (ts == null)
			return 0;
		if (ts.isCollection()) {
			int w = Math.max(1, ts.getWidth());
			int h = Math.max(1, ts.getHeight());
			return w * h;
		}
		return ts.getTilecount();
	}

	public interface ImageLoader {
		Pixmap loadPixmap(tile t);
	}

	public static int resolveGridIndex(tileset ts, int localTileId) {
		if (ts == null || !ts.isCollection())
			return localTileId;
		int gi = ts.getGridIndex(localTileId);
		if (gi >= 0)
			return gi;
		tile meta = ts.getTileMeta(localTileId);
		if (meta == null || !meta.hasImage())
			return -1;
		Map<Integer, Integer> computed = computeCollectionGridMap(ts);
		Integer mapped = computed.get(localTileId);
		if (mapped != null) {
			ts.mapLocalTileToGrid(localTileId, mapped);
			return mapped;
		}
		return -1;
	}

	public static int getSprX(tileset ts, int localTileId) {
		if (!ts.isCollection()) {
			int w = Math.max(1, ts.getWidth());
			return localTileId % w;
		}
		int gi = resolveGridIndex(ts, localTileId);
		if (gi < 0)
			return 0;
		int cols = Math.max(1, ts.getColumns());
		return gi % cols;
	}

	public static int getSprY(tileset ts, int localTileId) {
		if (!ts.isCollection()) {
			int w = Math.max(1, ts.getWidth());
			return localTileId / w;
		}
		int gi = resolveGridIndex(ts, localTileId);
		if (gi < 0)
			return 0;
		int cols = Math.max(1, ts.getColumns());
		return gi / cols;
	}

	/** Pixel X in the combined collection atlas texture (not Tiled sheet margin/spacing). */
	public static int getSrcX(tileset ts, int localTileId) {
		if (!ts.isCollection()) {
			return getSprX(ts, localTileId) * (ts.getTilewidth() + ts.getSpacing()) + ts.getMargin();
		}
		int gi = resolveGridIndex(ts, localTileId);
		if (gi < 0)
			return 0;
		int cols = Math.max(1, ts.getColumns());
		return (gi % cols) * Math.max(1, ts.getTilewidth());
	}

	/** Pixel Y in the combined collection atlas texture (not Tiled sheet margin/spacing). */
	public static int getSrcY(tileset ts, int localTileId) {
		if (!ts.isCollection()) {
			return getSprY(ts, localTileId) * (ts.getTileheight() + ts.getSpacing()) + ts.getMargin();
		}
		int gi = resolveGridIndex(ts, localTileId);
		if (gi < 0)
			return 0;
		int cols = Math.max(1, ts.getColumns());
		return (gi / cols) * Math.max(1, ts.getTileheight());
	}

	public static boolean hasTileImage(tileset ts, int localTileId) {
		if (!ts.isCollection())
			return localTileId >= 0 && localTileId < ts.getTilecount();
		if (resolveGridIndex(ts, localTileId) < 0)
			return false;
		tile meta = ts.getTileMeta(localTileId);
		return meta != null && meta.hasImage();
	}

	public static int nextTileId(tileset ts) {
		int max = -1;
		for (tile t : ts.getTiles()) {
			if (t.getTileID() > max)
				max = t.getTileID();
		}
		return max + 1;
	}

	private static final class SheetBlock {
		final String source;
		final List<tile> tiles = new ArrayList<tile>();
		int sheetCols;
		int sheetRows;
		int atlasX;
		int atlasY;

		SheetBlock(String source) {
			this.source = source;
		}
	}

	/** Grid index for every image tile — independent of pixmap loading. */
	public static Map<Integer, Integer> computeCollectionGridMap(tileset ts) {
		Map<Integer, Integer> gridMap = new HashMap<Integer, Integer>();
		List<tile> imageTiles = tilesWithImages(ts);
		if (imageTiles.isEmpty())
			return gridMap;

		Collections.sort(imageTiles, new Comparator<tile>() {
			@Override
			public int compare(tile a, tile b) {
				return Integer.compare(a.getTileID(), b.getTileID());
			}
		});

		int tw = Math.max(1, ts.getTilewidth());
		int th = Math.max(1, ts.getTileheight());

		LinkedHashMap<String, SheetBlock> blocksBySource = new LinkedHashMap<String, SheetBlock>();
		for (int i = 0; i < imageTiles.size(); i++) {
			tile t = imageTiles.get(i);
			String src = t.getImageSource();
			SheetBlock block = blocksBySource.get(src);
			if (block == null) {
				block = new SheetBlock(src);
				blocksBySource.put(src, block);
			}
			block.tiles.add(t);
		}

		List<SheetBlock> blocks = new ArrayList<SheetBlock>(blocksBySource.values());
		for (int i = 0; i < blocks.size(); i++) {
			SheetBlock block = blocks.get(i);
			int maxCx = 0;
			int maxCy = 0;
			int blockMarginX = Integer.MAX_VALUE;
			int blockMarginY = Integer.MAX_VALUE;
			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				if (t.getImageSrcX() < blockMarginX)
					blockMarginX = t.getImageSrcX();
				if (t.getImageSrcY() < blockMarginY)
					blockMarginY = t.getImageSrcY();
			}
			if (blockMarginX == Integer.MAX_VALUE)
				blockMarginX = 0;
			if (blockMarginY == Integer.MAX_VALUE)
				blockMarginY = 0;
			int blockSpacingX = ts.getSpacing();
			int blockSpacingY = ts.getSpacing();
			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				int cx = sheetColFromSrcX(t.getImageSrcX(), tw, blockMarginX, blockSpacingX);
				int cy = sheetRowFromSrcY(t.getImageSrcY(), th, blockMarginY, blockSpacingY);
				if (cx > maxCx)
					maxCx = cx;
				if (cy > maxCy)
					maxCy = cy;
			}
			block.sheetCols = Math.max(1, maxCx + 1);
			block.sheetRows = Math.max(1, maxCy + 1);
		}

		int hGap = 0;
		int vGap = 0;
		int widest = 1;
		int totalRowWidth = 0;
		for (int i = 0; i < blocks.size(); i++) {
			widest = Math.max(widest, blocks.get(i).sheetCols);
			if (i > 0)
				totalRowWidth += hGap;
			totalRowWidth += blocks.get(i).sheetCols;
		}
		int maxRowWidth;
		if (totalRowWidth <= 160)
			maxRowWidth = totalRowWidth;
		else
			maxRowWidth = Math.min(160, Math.max(64, widest));

		int packX = 0;
		int packY = 0;
		int rowHeight = 0;
		int atlasCols = 0;
		int atlasRows = 0;
		for (int i = 0; i < blocks.size(); i++) {
			SheetBlock block = blocks.get(i);
			if (packX > 0 && packX + block.sheetCols > maxRowWidth) {
				packY += rowHeight + vGap;
				packX = 0;
				rowHeight = 0;
			}
			block.atlasX = packX;
			block.atlasY = packY;
			packX += block.sheetCols + hGap;
			if (block.sheetRows > rowHeight)
				rowHeight = block.sheetRows;
			int blockRight = block.atlasX + block.sheetCols;
			int blockBottom = block.atlasY + block.sheetRows;
			if (blockRight > atlasCols)
				atlasCols = blockRight;
			if (blockBottom > atlasRows)
				atlasRows = blockBottom;
		}
		if (rowHeight > 0)
			atlasRows = Math.max(atlasRows, packY + rowHeight);
		atlasCols = Math.max(1, atlasCols);
		atlasRows = Math.max(1, atlasRows);

		for (int b = 0; b < blocks.size(); b++) {
			SheetBlock block = blocks.get(b);
			int blockMarginX = Integer.MAX_VALUE;
			int blockMarginY = Integer.MAX_VALUE;
			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				if (t.getImageSrcX() < blockMarginX)
					blockMarginX = t.getImageSrcX();
				if (t.getImageSrcY() < blockMarginY)
					blockMarginY = t.getImageSrcY();
			}
			if (blockMarginX == Integer.MAX_VALUE)
				blockMarginX = 0;
			if (blockMarginY == Integer.MAX_VALUE)
				blockMarginY = 0;
			int blockSpacingX = ts.getSpacing();
			int blockSpacingY = ts.getSpacing();

			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				int id = t.getTileID();
				int cx = sheetColFromSrcX(t.getImageSrcX(), tw, blockMarginX, blockSpacingX);
				int cy = sheetRowFromSrcY(t.getImageSrcY(), th, blockMarginY, blockSpacingY);
				int atlasTileX = block.atlasX + cx;
				int atlasTileY = block.atlasY + cy;
				int gi = atlasTileY * atlasCols + atlasTileX;
				gridMap.put(id, gi);
			}
		}
		return gridMap;
	}

	public static void rebuildAtlas(tileset ts, ImageLoader loader) {
		List<tile> imageTiles = tilesWithImages(ts);
		if (imageTiles.isEmpty()) {
			ts.setCollection(true);
			return;
		}

		int tw = Math.max(1, ts.getTilewidth());
		int th = Math.max(1, ts.getTileheight());
		int count = imageTiles.size();

		Map<Integer, Integer> gridMap = computeCollectionGridMap(ts);
		int maxId = 0;
		for (Integer id : gridMap.keySet()) {
			if (id > maxId)
				maxId = id;
		}

		LinkedHashMap<String, SheetBlock> blocksBySource = new LinkedHashMap<String, SheetBlock>();
		for (int i = 0; i < imageTiles.size(); i++) {
			tile t = imageTiles.get(i);
			String src = t.getImageSource();
			SheetBlock block = blocksBySource.get(src);
			if (block == null) {
				block = new SheetBlock(src);
				blocksBySource.put(src, block);
			}
			block.tiles.add(t);
		}
		List<SheetBlock> blocks = new ArrayList<SheetBlock>(blocksBySource.values());

		// Recompute block layout (must match computeCollectionGridMap)
		for (int i = 0; i < blocks.size(); i++) {
			SheetBlock block = blocks.get(i);
			int maxCx = 0;
			int maxCy = 0;
			int blockMarginX = Integer.MAX_VALUE;
			int blockMarginY = Integer.MAX_VALUE;
			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				if (t.getImageSrcX() < blockMarginX)
					blockMarginX = t.getImageSrcX();
				if (t.getImageSrcY() < blockMarginY)
					blockMarginY = t.getImageSrcY();
			}
			if (blockMarginX == Integer.MAX_VALUE)
				blockMarginX = 0;
			if (blockMarginY == Integer.MAX_VALUE)
				blockMarginY = 0;
			int blockSpacingX = ts.getSpacing();
			int blockSpacingY = ts.getSpacing();
			for (int j = 0; j < block.tiles.size(); j++) {
				tile t = block.tiles.get(j);
				int cx = sheetColFromSrcX(t.getImageSrcX(), tw, blockMarginX, blockSpacingX);
				int cy = sheetRowFromSrcY(t.getImageSrcY(), th, blockMarginY, blockSpacingY);
				if (cx > maxCx)
					maxCx = cx;
				if (cy > maxCy)
					maxCy = cy;
			}
			block.sheetCols = Math.max(1, maxCx + 1);
			block.sheetRows = Math.max(1, maxCy + 1);
		}
		int hGap = 0;
		int vGap = 0;
		int widest = 1;
		int totalRowWidth = 0;
		for (int i = 0; i < blocks.size(); i++) {
			widest = Math.max(widest, blocks.get(i).sheetCols);
			if (i > 0)
				totalRowWidth += hGap;
			totalRowWidth += blocks.get(i).sheetCols;
		}
		int maxRowWidth;
		if (totalRowWidth <= 160)
			maxRowWidth = totalRowWidth;
		else
			maxRowWidth = Math.min(160, Math.max(64, widest));
		int packX = 0;
		int packY = 0;
		int rowHeight = 0;
		int atlasCols = 0;
		int atlasRows = 0;
		for (int i = 0; i < blocks.size(); i++) {
			SheetBlock block = blocks.get(i);
			if (packX > 0 && packX + block.sheetCols > maxRowWidth) {
				packY += rowHeight + vGap;
				packX = 0;
				rowHeight = 0;
			}
			block.atlasX = packX;
			block.atlasY = packY;
			packX += block.sheetCols + hGap;
			if (block.sheetRows > rowHeight)
				rowHeight = block.sheetRows;
			int blockRight = block.atlasX + block.sheetCols;
			int blockBottom = block.atlasY + block.sheetRows;
			if (blockRight > atlasCols)
				atlasCols = blockRight;
			if (blockBottom > atlasRows)
				atlasRows = blockBottom;
		}
		if (rowHeight > 0)
			atlasRows = Math.max(atlasRows, packY + rowHeight);
		atlasCols = Math.max(1, atlasCols);
		atlasRows = Math.max(1, atlasRows);

		Map<String, Pixmap> pixmapCache = new HashMap<String, Pixmap>();
		List<Pixmap> loaded = new ArrayList<Pixmap>();

		Pixmap atlas = new Pixmap(atlasCols * tw, atlasRows * th, Pixmap.Format.RGBA8888);
		atlas.setColor(0, 0, 0, 0);
		atlas.fill();

		try {
			for (int b = 0; b < blocks.size(); b++) {
				SheetBlock block = blocks.get(b);
				Pixmap pm = pixmapCache.get(block.source);
				if (pm == null) {
					pm = loader.loadPixmap(block.tiles.get(0));
					if (pm == null)
						continue;
					pixmapCache.put(block.source, pm);
					loaded.add(pm);
				}

				int blockMarginX = Integer.MAX_VALUE;
				int blockMarginY = Integer.MAX_VALUE;
				for (int j = 0; j < block.tiles.size(); j++) {
					tile t = block.tiles.get(j);
					if (t.getImageSrcX() < blockMarginX)
						blockMarginX = t.getImageSrcX();
					if (t.getImageSrcY() < blockMarginY)
						blockMarginY = t.getImageSrcY();
				}
				if (blockMarginX == Integer.MAX_VALUE)
					blockMarginX = 0;
				if (blockMarginY == Integer.MAX_VALUE)
					blockMarginY = 0;
				int blockSpacingX = ts.getSpacing();
				int blockSpacingY = ts.getSpacing();

				for (int j = 0; j < block.tiles.size(); j++) {
					tile t = block.tiles.get(j);
					int id = t.getTileID();
					maxId = Math.max(maxId, id);

					Integer giBox = gridMap.get(id);
					if (giBox == null)
						continue;
					int gi = giBox;
					int atlasTileX = gi % atlasCols;
					int atlasTileY = gi / atlasCols;
					int gx = atlasTileX * tw;
					int gy = atlasTileY * th;
					int sx = t.getImageSrcX();
					int sy = t.getImageSrcY();
					int sw = Math.min(tw, pm.getWidth() - sx);
					int sh = Math.min(th, pm.getHeight() - sy);
					if (sw <= 0 || sh <= 0)
						continue;
					atlas.drawPixmap(pm, sx, sy, sw, sh, gx, gy, sw, sh);
				}
			}

			int maxGi = -1;
			for (Integer gi : gridMap.values()) {
				if (gi > maxGi)
					maxGi = gi;
			}
			if (maxGi >= 0) {
				int usedRows = (maxGi / atlasCols) + 1;
				if (usedRows < atlasRows) {
					Pixmap trimmed = new Pixmap(atlasCols * tw, usedRows * th, Pixmap.Format.RGBA8888);
					trimmed.drawPixmap(atlas, 0, 0);
					atlas.dispose();
					atlas = trimmed;
					atlasRows = usedRows;
				}
			}

			disposeTilesetGraphics(ts);
			ts.setPixmap(atlas);
			ts.setTexture(new Texture(atlas));
			ts.setCollection(true);
			ts.setSource("");
			ts.setGridIndexMap(gridMap);
			ts.setWidth(atlasCols);
			ts.setHeight(atlasRows);
			ts.setColumns(atlasCols);
			ts.setTilecount(Math.max(maxId + 1, count));
			ts.setOriginalwidth(atlasCols * tw);
			ts.setOriginalheight(atlasRows * th);
			ts.clearTerrainCache();
		} finally {
			for (Pixmap pm : loaded) {
				if (pm != null)
					pm.dispose();
			}
		}
	}

	public static List<tile> tilesWithImages(tileset ts) {
		List<tile> out = new ArrayList<tile>();
		for (tile t : ts.getTiles()) {
			if (t.getImageSource() != null && !t.getImageSource().isEmpty())
				out.add(t);
		}
		return out;
	}

	public static void disposeTilesetGraphics(tileset ts) {
		if (ts.getTexture() != null) {
			ts.getTexture().dispose();
			ts.setTexture(null);
		}
		if (ts.getPixmap() != null) {
			ts.getPixmap().dispose();
			ts.setPixmap(null);
		}
	}

	public static boolean isImageFile(FileHandle fh) {
		if (fh == null || fh.isDirectory())
			return false;
		String ext = fh.extension().toLowerCase();
		return ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg")
				|| ext.equals("gif") || ext.equals("bmp");
	}

}
