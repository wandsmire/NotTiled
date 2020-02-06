package com.mirwanda.nottiled;

public class autotile
{
	private String name;
	private java.util.List<property> properties = new java.util.ArrayList<property>();
	
	public autotile(){}
	public autotile(String name){
		this.name=name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setProperties(java.util.List<property> properties)
	{
		this.properties = properties;
	}

	public java.util.List<property> getProperties()
	{
		return properties;
	}}
