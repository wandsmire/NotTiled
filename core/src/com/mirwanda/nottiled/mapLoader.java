package com.mirwanda.nottiled;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.utils.*;

public class mapLoader extends AsynchronousAssetLoader<TmxMap,mapLoader.mapParameter>
{

	@Override
	public Array<AssetDescriptor> getDependencies(String p1, FileHandle p2, mapLoader.mapParameter p3)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void loadAsync(AssetManager p1, String p2, FileHandle p3, mapLoader.mapParameter p4)
	{
		// TODO: Implement this method
		
	}

	@Override
	public TmxMap loadSync(AssetManager p1, String p2, FileHandle p3, mapLoader.mapParameter p4)
	{
		// TODO: Implement this method
		return null;
	}
	


	
	public mapLoader(FileHandleResolver resolver) {

        super(resolver);

    }
	
	public static class mapParameter extends AssetLoaderParameters<TmxMap> {

    }
	
}
