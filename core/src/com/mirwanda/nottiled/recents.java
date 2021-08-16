package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;

import java.util.*;

public class recents
{
	public recents(){}
	
	private java.util.List<String> paths = new ArrayList<String>();
	private java.util.List<String> filenames = new ArrayList<String>();
	private java.util.List<String> types = new ArrayList<String>();

	public void setPaths(java.util.List<String> paths)
	{
		this.paths = paths;
	}

	public java.util.List<String> getPaths()
	{
		return paths;
	}
	public java.util.List<String> getFilenames()
	{
		return filenames;
	}
	public java.util.List<String> getTypes()
	{
		return types;
	}

	public void addrecent(String path, String filename, String type){
		try {
			int ny = paths.size() - 1;

			for (int i = ny; i >= 0; i--) {
				//Gdx.app.log("A",paths.get(i)+"/"+i);

				if (filenames.get( i ) != null) {
					if (filenames.get( i ).equalsIgnoreCase( filename )) {
						paths.remove( i );
						filenames.remove( i );
						types.remove( i );
					}
				}
			}


			paths.add( path );
			filenames.add( filename );
			types.add( type );
			if (paths.size() > 20) {
				paths.remove( 0 );
				filenames.remove( 0 );
				types.remove( 0 );
			}
		}catch(Exception e){
			paths.clear();
			filenames.clear();
			types.clear();

		}
	}
}
