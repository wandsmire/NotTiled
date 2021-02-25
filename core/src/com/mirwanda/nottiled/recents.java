package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;

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
		int ny = paths.size()-1;
		for (int i=ny; i>=0;i--){
			//Gdx.app.log("A",paths.get(i)+"/"+i);

			if (paths.get(i)!=null) {
				if (paths.get( i ).equalsIgnoreCase( path )) {
					paths.remove( i );
				}
			}
		}
		paths.add(path);
		if (paths.size()>20)
		{
			paths.remove(0);
		}
	}
}
