package com.mirwanda.nottiled;
//batch.draw(tilesets.get(initset).getTexture(), xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswa, Tsha, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, true);

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;

public class drawer implements Comparable 
{

	
	public void setFlagged(boolean flagged)
	{
		this.flagged = flagged;
	}

	public boolean isFlagged()
	{
		return flagged;
	}

	public void setInitset(int initset)
	{
		this.initset = initset;
	}

	public int getInitset()
	{
		return initset;
	}

	@Override
	public int compareTo(Object comparestu)
	{
		int compareage=((drawer)comparestu).getInitset();
        /* For Ascending order*/
        return this.initset-compareage;
	}
	
	int initset;
	float x;
	float y;
	float originX;
	float originY;
	float width;
	float height;
	float scaleX;
	float scaleY;
	float rotation;
	int srcX;
	int srcY;
	int srcWidth;
	int srcHeight;
	boolean flipX;
	boolean flipY;
	boolean flagged;
	long mm;
	public drawer(){}
	public void setdrawer(int initset, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
	{
		this.initset=initset;
		this.x=x;
		this.y=y;
		this.originX=originX;
		this.originY=originY;
		this.width=width;
		this.height=height;
		this.scaleX=scaleX;
		this.scaleY= scaleY;
		this.rotation=rotation;
		this.srcX=srcX;
		this.srcY=srcY;
		this.srcWidth=srcWidth;
		this.srcHeight=srcHeight;
		this.flipX=flipX;
		this.flipY=flipY;
	}
	
	public void draw(SpriteBatch batch,java.util.List<tileset> tilesets)
	{
		if (tilesets.get(initset).getTexture()!=null){
			
		batch.draw(tilesets.get(initset).getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		}
	}
	
	public void draw(Pixmap pm2, List<tileset> tilesets,int Tsw,int Tsh)
	{
		if (tilesets.get(initset).getTexture()!=null){
			for (int xa = 0; xa < Tsw; xa++) {
				for (int ya = 0; ya < Tsh; ya++) {
					Color c =  new Color(tilesets.get(initset).getPixmap().getPixel((int)((float)(xa/(float)Tsw)*width+srcX),(int)((float)(ya/(float)Tsw)*height+srcY)));
					pm2.drawPixel((int)x+xa,(int)y+ya,Color.rgba8888(c));
				}
			}
			//batch.draw(tilesets.get(initset).getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		}
	}

	public void draw(Pixmap pm2, Pixmap p,int Tsw,int Tsh)
	{

			for (int xa = 0; xa < Tsw; xa++) {
				for (int ya = 0; ya < Tsh; ya++) {
					Color c =  new Color(p.getPixel((int)((float)(xa/(float)Tsw)*width+srcX),(int)((float)(ya/(float)Tsw)*height+srcY)));
					pm2.drawPixel((int)x+xa,(int)y+ya,Color.rgba8888(c));
				}
			}
			//batch.draw(tilesets.get(initset).getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);

	}
	
	
	public void draw(SpriteBatch batch,Texture texture)
	{
		if (texture!=null){

			batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		}
	}
	
	public void write(BitmapFont str1, SpriteBatch batch)
	{
		//batch.draw(tilesets.get(initset).getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		//str1.getData().setScale(0.2f);
		str1.draw(batch, mm+"", x+2,y+height-2);
		//str1.getData().setScale(1f);
	}
	
	public void add(SpriteCache batch,java.util.List<tileset> tilesets)
	{
		if (tilesets.get(initset).getTexture()!=null){
		batch.add(tilesets.get(initset).getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		}
	}
	
	public void add(SpriteCache batch,Texture texture)
	{
		if (texture!=null){
			batch.add(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		}
	}
}
