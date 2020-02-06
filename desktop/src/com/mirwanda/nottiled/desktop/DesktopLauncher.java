package com.mirwanda.nottiled.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mirwanda.nottiled.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-gdx-game";
		cfg.useGL30 = false;
		cfg.width = 1920;
		cfg.height = 1080;
		com.mirwanda.nottiled.nullInterface ni= new com.mirwanda.nottiled.nullInterface();
		new LwjglApplication(new MyGdxGame("",ni), cfg);
	}
}
