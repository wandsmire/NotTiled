package com.mirwanda.not2d.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.mirwanda.not2d.MyGdxGame;
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration cfg3 = new Lwjgl3ApplicationConfiguration();
		cfg3.setTitle( "Not2D" );
		cfg3.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES20, 0, 0);
		new Lwjgl3Application(new MyGdxGame(), cfg3);
	}
}
