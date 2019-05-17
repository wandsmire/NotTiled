package com.mirwanda.nottiled;

//custom properties
public class property
{
	private String name="";
	private String type ="";
	private String value="";

	public property(){}
	
	public property(String name, String type, String value){
		this.name=name;
		this.type=type;
		this.value=value;
	}
	public property(String name, String value){
		this.name=name;
		this.value=value;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}}
