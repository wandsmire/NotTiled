package com.mirwanda.nottiled;

public class frame
{
	private int duration;
	private int tileID;


	public frame(int tileID, int duration){
		this.tileID =tileID;
		this.duration=duration;
	}
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setTileID(int tileID)
	{
		this.tileID = tileID;
	}

	public int getTileID()
	{
		return tileID;
	}}
