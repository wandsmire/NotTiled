package com.mirwanda.nottiled;
import java.util.*;

public class Tutorials
{
	List<tutorial> T = new ArrayList<tutorial>();
	
	public Tutorials()
	{}
		
	public void generatesample()
	{
	
	tutorial a=new tutorial();
	a.setName("01 Getting started");
	a.addStep("welcome","Welcome to Not Tiled, I'll explain some of the basic so that you can quickly get a graps on this app. First, let's make a new file. Click the menu button on the bottom left corner");
	a.addStep("menu", "Good job! now, click on New File button");
	a.addStep("new", "Okay! you can set your tile size and map size here, change the map size to 50 to 50, and then click the Create new File button");
	a.addStep("newmap", "Great! You should see an empty map on your screen. Before you begin working, you need to add tileset. Tileset is a collection of images that you can use for your drawing. Click the button in the bottom center.");
	a.addStep("tilepick", "Well done! to add a new tileset, click on Add new tileset button");
	a.addStep("addtileset", "Nice! Find tilesets that match your tile size. You can find some in Not tiled folder in your SD card.");
	a.addStep("tilesetadded", "Awesome! now choose any tile that you want to use.");
	a.addStep("tilepickclick", "Good! you've got your tileset ready, put it on your map.");
	a.addStep("tileclick", "Well done! let's change the tile, click to tile selection button again.");
	a.addStep("tilepick", "Pick whichever you like.");
	a.addStep("tilepickclick", "Well, you know the drill.");
	a.addStep("tileclick", "Great! You are a fast learner! anyway, don't forget to save your map, click on the menu button one more time");
	a.addStep("menu", "Click on the save button");
		a.addStep("save", "Fantastic! now you know the basic. Thank you and have fun using Not Tiled! More tutorials will come soon.");
	a.addStep("end", "");
		
	T.add(a);
	/*
	    a=new tutorial();
		a.setName("02 Using tools");
		a.addStep("welcome","Hello again, now we are going to learn about tools. tapping everything one by one is no fun...");
		a.addStep("menu", "Good job! now, click on New File button");
		a.addStep("new", "Okay! you can set your tile size and map size here, change the map size to 50 to 50, and then click the Create new File button");
		a.addStep("newmap", "Great! You should see an empty map on your screen. Before you begin working, you need to add tileset. Tileset is a collection of images that you can use for your drawing. Click the button in the bottom center.");
		a.addStep("tilepick", "Well done! to add a new tileset, click on Add new tileset button");
		a.addStep("addtileset", "Nice! Find tilesets that match your tile size. You can find some in Not tiled folder in your SD card.");
		a.addStep("tilesetadded", "Awesome! now choose any tile that you want to use.");
		a.addStep("tilepickclick", "Good! you've got your tileset ready, put it on your map.");
		a.addStep("tileclick", "Well done! let's change the tile, click to tile selection button again.");
		a.addStep("tilepick", "Pick whichever you like.");
		a.addStep("tilepickclick", "Well, you know the drill.");
		a.addStep("tileclick", "Great! You are a fast learner! anyway, don't forget to save your map, click on the menu button one more time");
		a.addStep("menu", "Click on the save button");
		a.addStep("save", "Fantastic! now you know the basic. Thank you and have fun using Not Tiled! If you need more tutorials, find it on the main menu.");
		a.addStep("end", "");

		T.add(a);
		
		*/
	}

	public void setT(List<tutorial> t)
	{
		T = t;
	}

	public List<tutorial> getT()
	{
		return T;
	}
}
