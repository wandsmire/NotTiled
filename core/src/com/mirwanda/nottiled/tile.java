package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;

import java.util.List;

public class tile
{
	private int tileID;
	private int activeFrameIndex;
	private int activeFrameID;
	private int activeFrameDuration;
	private float timer;
	public java.util.List<frame> animation = new java.util.ArrayList<frame>();
	public java.util.List<property> properties = new java.util.ArrayList<property>();

	public List<obj> getObjects() {
		return objects;
	}

	public void setObjects(List<obj> objects) {
		this.objects = objects;
	}

	private java.util.List<obj> objects = new java.util.ArrayList<obj>();
	
	private int[] terrain = new int[]{-1,-1,-1,-1};



	public void setTerrain(int[] terrain)
	{
		this.terrain = terrain;
	}

	public void setTerrain(String s)
	{
		if (s==null) return;
		if (s.equalsIgnoreCase( "" )) return;
		String sp[]=s.split(",",-1);
		int[] tr = new int[]{-1,-1,-1,-1};
		//Gdx.app.log("A",s+"}}}"+sp.length);
		if (!sp[0].equalsIgnoreCase( "" )) tr[0]=Integer.parseInt( sp[0] );
		if (!sp[1].equalsIgnoreCase( "" )) tr[1]=Integer.parseInt( sp[1] );
		if (!sp[2].equalsIgnoreCase( "" )) tr[2]=Integer.parseInt( sp[2] );
		if (!sp[3].equalsIgnoreCase( "" )) tr[3]=Integer.parseInt( sp[3] );

		this.terrain=tr;
		
	}
	public void setTerrain(int a, int b, int c, int d)
	{
		
		this.terrain=new int[]{a,b,c,d};

	}
	
	public int[] getTerrain()
	{
		if (this.terrain !=null)
		{
			return terrain;
		}
		else
		{
			return null;
		}
	}
	
	public boolean isTerrain()
	{
		if (terrain[0]==-1 || terrain[1]==-1 ||terrain[2]==-1 ||terrain[3]==-1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean isTerrainForEditor()
	{
		if (getTerrainString().equalsIgnoreCase( "-1,-1,-1,-1" ))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	public String getTerrainString()
	{
		if (this.terrain !=null)
		{
			return Integer.toString(terrain[0])+","+Integer.toString(terrain[1])+","+Integer.toString(terrain[2])+","+Integer.toString(terrain[3]);
		}
		return null;
	}
	
	public boolean isCenter(){
		if (this.terrain !=null)
		{
			if (terrain[0]==terrain[1] && terrain[0]==terrain[2] && terrain[0]==terrain[3])
			{
				return true;
			}
		}
		return false;
	}

	public boolean isTransition(){
		if (this.terrain !=null)
		{
			if (terrain[0]!=terrain[1] || terrain[0]!=terrain[2] || terrain[0]!=terrain[3])
			{
				return true;
			}
			if (terrain[1]!=terrain[2] || terrain[1]!=terrain[3])
			{
				return true;
			}
			if (terrain[1]!=terrain[3])
			{
				return true;
			}
		}
		return false;
	}

	public int getTransA(){

		int b=-1;
		int a =terrain[0];
		if (terrain[1]!=a) b=terrain[1];
		if (terrain[2]!=a) b=terrain[2];
		if (terrain[3]!=a) b=terrain[3];

		return Math.min( a,b );
	}

	public int getTransB(){

		int b=-1;
		int a =terrain[0];
		if (terrain[1]!=a) b=terrain[1];
		if (terrain[2]!=a) b=terrain[2];
		if (terrain[3]!=a) b=terrain[3];

		return Math.max( a,b );
	}


	public void setActiveFrameID(int activeFrameGid)
	{
		this.activeFrameID = activeFrameGid;
	}

	public int getActiveFrameID()
	{
		return activeFrameID;
	}

	public void setActiveFrameDuration(int activeFrameDuration)
	{
		this.activeFrameDuration = activeFrameDuration;
	}

	public int getActiveFrameDuration()
	{
		return activeFrameDuration;
	}

	public void setAnimation(java.util.List<frame> animation)
	{
		this.animation = animation;
	}

	public java.util.List<frame> getAnimation()
	{
		return animation;
	}

	public void setProperties(java.util.List<property> properties)
	{
		this.properties = properties;
	}

	public java.util.List<property> getProperties()
	{
		return properties;
	}

	public void setActiveFrameIndex(int activeFrameID)
	{
		this.activeFrameIndex = activeFrameID;
	}

	public int getActiveFrameIndex()
	{
		return activeFrameIndex;
	}

	public void addTimer(float delta)
	{
		this.timer += delta;
	}
	public void setTimer(float timer)
	{
		this.timer = timer;
	}
	public float getTimer()
	{
		return timer;
	}
	
	
	


public void setTileID(int tileID)
{
this.tileID = tileID;
}

public int getTileID()
{
return tileID;
}

}
