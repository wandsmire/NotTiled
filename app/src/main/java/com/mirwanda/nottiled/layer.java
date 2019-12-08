package com.mirwanda.nottiled;
import java.util.*;

public class layer implements Cloneable
{
	private List<Long> gid = new ArrayList<Long>();
	private List<Integer> tset = new ArrayList<Integer>();
	private List<property> properties = new ArrayList<property>();
	private String name;
	private float opacity;

	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}  
	
	public void setTset(List<Integer> tset)
	{
		this.tset = tset;
	}

	public List<Integer> getTset()
	{
		return tset;
	}

	public void setProperties(List<property> properties)
	{
		this.properties = properties;
	}

	public List<property> getProperties()
	{
		return properties;
	}

	public void setOpacity(float opacity)
	{
		this.opacity = opacity;
	}

	public float getOpacity()
	{
		return opacity;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}


	public void setStr(List<Long> str)
	{
		this.gid = str;
	}

	public List<Long> getStr()
	{
		return gid;
	}}
