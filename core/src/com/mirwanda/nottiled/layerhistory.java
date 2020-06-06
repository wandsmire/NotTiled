package com.mirwanda.nottiled;

public class layerhistory extends packet
{
	public boolean follower;
	public int location;
	public long to;
	public long from;
	public int layer;
	public int oldtset;
	public int newtset;
	public boolean undo;

	public layerhistory(){
	}
	public layerhistory(boolean follower, long from, long to, int location, int layer, int oldtset,int newtset){
		this.follower=follower;
		this.to=to;
		this.from=from; 
		this.location=location;
		this.layer=layer;
		this.oldtset=oldtset;
		this.newtset=newtset;
	}

	public void setNewtset(int newtset)
	{
		this.newtset = newtset;
	}

	public int getNewtset()
	{
		return newtset;
	}

	public void setoldTset(int oldtset)
	{
		this.oldtset = oldtset;
	}

	public int getoldTset()
	{
		return oldtset;
	}

	public void setFollower(boolean follower)
	{
		this.follower = follower;
	}

	public boolean isIdentical(layerhistory lh){
		if (this.location==lh.location){
			if (this.to==lh.to){
				if (this.from==lh.from){
					if(this.layer==lh.layer){
						return true;
					}
				}
			}
		}
		return false;
	}
	public void setLayer(int layer)
	{
		this.layer = layer;
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLocation(int location)
	{
		this.location = location;
	}

	public int getLocation()
	{
		return location;
	}
	

	public boolean isFollower()
	{
		return follower;
	}

	public void setTo(long to)
	{
		this.to = to;
	}

	public long getTo()
	{
		return to;
	}

	public void setFrom(long from)
	{
		this.from = from;
	}

	public long getFrom()
	{
		return from;
	}}
