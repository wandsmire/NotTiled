package com.mirwanda.nottiled;

import java.util.*;

public class recents
{
	public recents(){}
	
	private java.util.List<String> paths = new ArrayList<String>();

	public void setPaths(java.util.List<String> paths)
	{
		this.paths = paths;
	}

	public java.util.List<String> getPaths()
	{
		return paths;
	}
	
	public void addrecent(String path){
		for (int i=paths.size()-1; i>=0;i--)
		{
			if (paths.get(i).equalsIgnoreCase(path))
			{
				paths.remove(i);
			}
		}
		paths.add(path);
		if (paths.size()>20)
		{
			paths.remove(0);
		}
	}
}
