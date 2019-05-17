package com.mirwanda.nottiled;

public class floodfill
{
	private int num,direction;
	private long oi, from;
	
	public floodfill(int num, long oi,long from, int direction){
		this.num=num;
		this.oi=oi;
		this.direction=direction;
		this.from=from;
	}
	


	public void setNum(int num)
	{
		this.num = num;
	}

	public int getNum()
	{
		return num;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setOi(long oi)
	{
		this.oi = oi;
	}

	public long getOi()
	{
		return oi;
	}

	public void setFrom(long from)
	{
		this.from = from;
	}

	public long getFrom()
	{
		return from;
	}}
