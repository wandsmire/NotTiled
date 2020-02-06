package com.mirwanda.nottiled;

public class guis
{
	gui menu=new gui();
	gui map=new gui();
	gui picker=new gui();
	gui pickerbg=new gui();
	gui rotation=new gui();
	gui autotile=new gui();
	gui autopicker=new gui();
	gui layerpick=new gui();
	gui tool=new gui();
	gui tool1=new gui();
	gui tool2=new gui();
	gui tool3=new gui();
	gui tool4=new gui();
	gui tool5=new gui();
	gui undo=new gui();
	gui redo=new gui();
	gui info=new gui();
	gui fps=new gui();
	gui status=new gui();
	gui tile=new gui();
	gui mode=new gui();
	gui layer=new gui();
	gui viewmode=new gui();
	gui objectpicker=new gui();
	gui objectpickerlefticon=new gui();
	gui objectpickerrighticon=new gui();
	gui objectpickerleft=new gui();
	gui objectpickerright=new gui();
	gui newterrain=new gui();
	gui screenshot=new gui();
	gui tilesets=new gui();
	gui tilesetsleft=new gui();
	gui tilesetsright=new gui();
	gui tilesetsmid=new gui();
	gui tilesetslefticon=new gui();
	gui tilesetrighticon=new gui();
	gui center=new gui();
	gui save=new gui();
	gui play=new gui();
	gui minimap=new gui();
	gui lock = new gui();
	gui pickerback = new gui();
	gui tileoverlay = new gui();
	gui tileadd = new gui();
	gui tileremove = new gui();
	gui tileproperties = new gui();
	gui tilesettings = new gui();
	gui tilewrite = new gui();

	gui up=new gui();
	gui down=new gui();
	gui left=new gui();
	gui right=new gui();
	gui restart=new gui();
	gui exit=new gui();
	gui respawn=new gui();
	gui gamestatus=new gui();
	gui canceltutorial=new gui();


	public guis(){
		menu.setp(0,20,0,10);
		map.setp(0,15,10,19);
		undo.setp(0,15,90,100);
		redo.setp(85,100,90,100);
		layerpick.setp(80,100,0,10);
		picker.setp(40,60,0,10);
		pickerbg.setp(42,58,1,9);
		rotation.setp(85,100,10,19);
		autotile.setp(25,40,0,9);
		autopicker.setp(60,75,0,9);
		tool.setp(85,100,0,10);
		tool1.setp(85,100,19,28);
		tool2.setp(85,100,28,37);
		tool3.setp(85,100,37,46);
		tool4.setp(85,100,46,55);
		tool5.setp(85,100,55,64);

		pickerback.setp(0,15,10,19);
		tilewrite.setp(0,15,19,28);
		tilesettings.setp(0,15,28,37);
		tileproperties.setp(0,15,37,46);
		tileremove.setp(0,15,46,55);
		tileadd.setp(0,15,55,64);
		tileoverlay.setp(0,100,70,100);


		info.setp(0, 100, 70, 80);
		fps.setp(70,85,81,90);
		status.setp(0,100,70,80);
		mode.setp(15,30,90,100);
		layer.setp(30,70,90,100);
		viewmode.setp(70,85,90,100);
		objectpicker.setp(30, 80, 0, 10);
		objectpickerleft.setp(30, 55, 0, 10);
		objectpickerright.setp(55, 80, 0, 10);
		objectpickerlefticon.setp(30, 40, 0, 10);
		objectpickerrighticon.setp(70, 80, 0, 10);
		newterrain.setp(80,100,10,20);
		screenshot.setp(15,85,0,10);
		tilesets.setp(0,100,0,10);
		tilesetsleft.setp(0,50,0,10);
		tilesetsright.setp(50,100,0,10);
		tilesetsmid.setp(15,85,0,10);
		tilesetslefticon.setp(0,15,0,10);
		tilesetrighticon.setp(85,100,0,10);
		center.setp(85,100,81,90);
		save.setp(0,15,19,28);
		play.setp(0,15,28,37);
		minimap.setp(0,30,70,90);

		canceltutorial.setp(30,70,20,30);

		up.setp(80,100,10,20);
		down.setp(60,80,0,10);
		left.setp(0,20,10,20);
		right.setp(20,40,10,20);
		restart.setp(0,20,90,100);
		exit.setp(80,100,90,100);
		respawn.setp(30,70,40,50);
		gamestatus.setp(0,100,50,60);
		lock.setp(85,100,10,19);

		menu.setl(0,10,0,10);
		map.setl(0,10,10,20);
		save.setl(0,10,20,30);
		play.setl(0,10,30,40);
		layerpick.setl(90,100,0,15);
		center.setl(10,20,0,10);
		picker.setl(45,55,0,15);
		pickerbg.setl(45,55,0,15);
		rotation.setl(80,90,0,10);
		autotile.setl(35,45,0,10);
		autopicker.setl(55,65,0,10);
		tool.setl(90,100,10,20);
		tool5.setl(90,100,60,70);
		tool4.setl(90,100,50,60);
		tool3.setl(90,100,40,50);
		tool2.setl(90,100,30,40);
		tool1.setl(90,100,20,30);
		undo.setl(0,10,90,100);
		redo.setl(90,100,90,100);
		info.setl(0, 100, 70, 80);
		fps.setl(90,100,80,90);
		status.setl(0,100,80,90);

		mode.setl(10,20,90,100);
		layer.setl(20,80,90,100);
		viewmode.setl(80,90,90,100);

		objectpicker.setl(40, 90, 0, 10);
		objectpickerleft.setl(40, 60, 0, 10);
		objectpickerright.setl(60, 90, 0, 10);
		objectpickerlefticon.setl(40, 45, 0, 10);
		objectpickerrighticon.setl(85, 90, 0, 10);
		newterrain.setl(90,100,10,20);
		screenshot.setl(30,70,0,10);
		tilesets.setl(0,100,0,10);
		tilesetsleft.setl(0,50,0,10);
		tilesetsright.setl(50,100,0,10);
		tilesetsmid.setl(15,85,0,10);
		tilesetslefticon.setl(0,15,0,10);
		tilesetrighticon.setl(85,100,0,10);
		minimap.setl(0,17,60,90);
		lock.setl(90,100,15,25);

		pickerback.setl(0,10,10,20);
		tilewrite.setl(0,10,20,30);
		tilesettings.setl(0,10,30,40);
		tileproperties.setl(0,10,40,50);
		tileremove.setl(0,10,50,60);
		tileadd.setl(0,10,60,70);
		tileoverlay.setl(0,100,70,100);




		canceltutorial.setl(40,60,20,30);

		up.setl(85,100,10,30);
		down.setl(65,80,0,20);
		left.setl(0,15,10,30);
		right.setl(15,30,10,30);
		restart.setl(0,10,90,100);
		exit.setl(90,100,90,100);
		respawn.setl(40,60,40,50);
		gamestatus.setl(0,100,50,60);
	}
	
	
}
