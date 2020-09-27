package com.mirwanda.nottiled;

import java.util.*;

public class automate
{
	String type;
	String name;
	int source;
	int posx=-1;



	int posy=-1;
	int width;
	int height;

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	int hit;
	int against;
	int against2;
	int against3;
	int against4;
	int against5;
	int against6;
	int beside;
	int sourcelayer;
	int destlayer;
	int objectgroup;
	String objectfill;
	String objectname;
	int cell[]=new int[40];
	java.util.List<Integer> x=new ArrayList<Integer>();
	java.util.List<Integer> y=new ArrayList<Integer>();
	java.util.List<Integer> z=new ArrayList<Integer>();

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setAgainst2(int against2)
	{
		this.against2 = against2;
	}

	public int getAgainst2()
	{
		return against2;
	}

	public void setAgainst3(int against3)
	{
		this.against3 = against3;
	}

	public int getAgainst3()
	{
		return against3;
	}

	public void setAgainst4(int against4)
	{
		this.against4 = against4;
	}

	public int getAgainst4()
	{
		return against4;
	}

	public void setAgainst5(int against5)
	{
		this.against5 = against5;
	}

	public int getAgainst5()
	{
		return against5;
	}

	public void setAgainst6(int against6)
	{
		this.against6 = against6;
	}

	public int getAgainst6()
	{
		return against6;
	}

	public void setBeside(int beside)
	{
		this.beside = beside;
	}

	public int getBeside()
	{
		return beside;
	}

	public void setObjectgroup(int objectgroup)
	{
		this.objectgroup = objectgroup;
	}

	public int getObjectgroup()
	{
		return objectgroup;
	}

	public void setObjectfill(String objectfill)
	{
		this.objectfill = objectfill;
	}

	public String getObjectfill()
	{
		return objectfill;
	}

	public void setObjectname(String objectname)
	{
		this.objectname = objectname;
	}

	public String getObjectname()
	{
		return objectname;
	}

	public void setZ(java.util.List<Integer> z)
	{
		this.z = z;
	}

	public java.util.List<Integer> getZ()
	{
		return z;
	}
	
	public void addCoord(int x,int y,int z)
	{
		this.x.add(x);
		this.y.add(y);
		this.z.add(z);
	}
	public void setX(java.util.List<Integer> x)
	{
		this.x = x;
	}

	public java.util.List<Integer> getX()
	{
		return x;
	}

	public void setY(java.util.List<Integer> y)
	{
		this.y = y;
	}

	public java.util.List<Integer> getY()
	{
		return y;
	}
	
	public void setSourcelayer(int sourcelayer)
	{
		this.sourcelayer = sourcelayer;
	}

	public int getSourcelayer()
	{
		return sourcelayer;
	}

	public void setDestlayer(int destlayer)
	{
		this.destlayer = destlayer;
	}

	public int getDestlayer()
	{
		return destlayer;
	}
	


	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setSource(int source)
	{
		this.source = source;
	}

	public int getSource()
	{
		return source;
	}

	public void setAgainst(int against)
	{
		this.against = against;
	}

	public boolean getAgainst(long num)
	{
		if (num==against && against!=0) return true;
		if (num==against2 && against2!=0) return true;
		if (num==against3 && against3!=0) return true;
		if (num==against4 && against4!=0) return true;
		if (num==against5 && against5!=0) return true;
		return false;
	}

	public void setCell(int[] cell)
	{
		this.cell = cell;
	}

	public void setCell(int pos, int cell)
	{
		this.cell[pos]=cell;
	}
	
	public int[] getCell()
	{
		return cell;
	}
	public int getCell(int pos)
	{
		return cell[pos];
	}
}
