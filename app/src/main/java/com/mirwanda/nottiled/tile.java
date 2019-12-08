package com.mirwanda.nottiled;

public class tile
{
	private int tileID;
	private int activeFrameIndex;
	private int activeFrameID;
	private int activeFrameDuration;
	private float timer;
	private java.util.List<frame> animation = new java.util.ArrayList<frame>();
	private java.util.List<property> properties = new java.util.ArrayList<property>();
	private java.util.List<obj> collisions = new java.util.ArrayList<obj>();
	
	private int[] terrain = new int[]{-1,-1,-1,-1};

	public void setCollisions(java.util.List<obj> collisions)
	{
		this.collisions = collisions;
	}

	public java.util.List<obj> getCollisions()
	{
		return collisions;
	}

	public void setTerrain(int[] terrain)
	{
		this.terrain = terrain;
	}

	public void setTerrain(String s)
	{
		String sp[]=s.split(",");
		this.terrain=new int[]{Integer.parseInt(sp[0]),Integer.parseInt(sp[1]),Integer.parseInt(sp[2]), Integer.parseInt(sp[3])};
		
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
		if (!getTerrainString().equalsIgnoreCase("-1,-1,-1,-1")) 
		{
			return true;
		}
		else
		{
			return false;
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
