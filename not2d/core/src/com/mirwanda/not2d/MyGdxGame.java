package com.mirwanda.not2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends ApplicationAdapter {
	private com.mirwanda.nottiled.platformer.game mygame;

	@Override
	public void create () {
		mygame = new com.mirwanda.nottiled.platformer.game();
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		mygame.create();
		mygame.startgame( "data","index.tmx" ,false);
	}

	@Override
	public void render () {
		if (!mygame.render()) Gdx.app.exit();
	}

	@Override
	public void resize(int width, int height) {
		mygame.resize(width,height);
	}

	@Override
	public void dispose() {
		mygame.dispose();
	}
}