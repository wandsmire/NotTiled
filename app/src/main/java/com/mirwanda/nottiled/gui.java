package com.mirwanda.nottiled;

public class gui
{
	float x;
	float y;
	float w;
	float h;
	float xl;
	float yl;
	float wl;
	float hl;
	public gui(){}
	public gui(float x,float y, float w, float h, float x2, float y2, float w2, float h2){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.xl=x2;
		this.yl=y2;
		this.wl=w2;
		this.hl=h2;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getX()
	{
		return x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getY()
	{
		return y;
	}

	public void setW(float w)
	{
		this.w = w;
	}

	public float getW()
	{
		return w;
	}

	public void setH(float h)
	{
		this.h = h;
	}

	public float getH()
	{
		return h;
	}

	public void setXl(float xl)
	{
		this.xl = xl;
	}

	public float getXl()
	{
		return xl;
	}

	public void setYl(float yl)
	{
		this.yl = yl;
	}

	public float getYl()
	{
		return yl;
	}

	public void setWl(float wl)
	{
		this.wl = wl;
	}

	public float getWl()
	{
		return wl;
	}

	public void setHl(float hl)
	{
		this.hl = hl;
	}

	public float getHl()
	{
		return hl;
	}

	public void setp(float x,float w,float y,float h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.xl=x;
		this.yl=y;
		this.wl=w;
		this.hl=h;
	}

	public void setl(float x,float w,float y,float h){
		this.xl=x;
		this.yl=y;
		this.wl=w;
		this.hl=h;
	}
}


