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
	gui pickertool1=new gui();
	gui pickertool2=new gui();
	gui pickertool3=new gui();
	gui pickertool4=new gui();
	gui pickertool5=new gui();
	gui undo=new gui();
	gui redo=new gui();
	gui info=new gui();
	gui fps=new gui();
	gui status=new gui();
	gui tile=new gui();
	gui mode=new gui();
	gui layer=new gui();
	gui viewmode=new gui();
	gui objectpickermid=new gui();
	gui objectpickerleft=new gui();
	gui objectpickerright=new gui();
	gui newterrain=new gui();
	gui screenshot=new gui();
	gui tilesetsleft=new gui();
	gui tilesetsright=new gui();
	gui tilesetsmid=new gui();
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
	gui tilemode = new gui();
	gui editormode = new gui();
	gui editorsave = new gui();
	gui editorcancel = new gui();
	gui editorleft = new gui();
	gui editorright = new gui();
	gui editorup = new gui();
	gui editordown = new gui();

	gui up=new gui();
	gui down=new gui();
	gui left=new gui();
	gui right=new gui();
	gui action1=new gui();
	gui action2=new gui();
	gui action3=new gui();
	gui action4=new gui();

	gui restart=new gui();
	gui exit=new gui();
	gui respawn=new gui();
	gui gamestatus=new gui();
	gui canceltutorial=new gui();
	//gui swatches=new gui();
	gui sw1=new gui();
	gui sw2=new gui();
	gui sw3=new gui();
	gui sw4=new gui();
	gui sw5=new gui();
	gui sw6=new gui();

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
		pickertool1.setp(85,100,19,28);
		pickertool2.setp(85,100,28,37);
		pickertool3.setp(85,100,37,46);
		pickertool4.setp(85,100,46,55);
		pickertool5.setp(85,100,55,64);

		pickerback.setp(0,15,10,19);
		tilewrite.setp(0,15,19,28);
		tilesettings.setp(0,15,28,37);
		tileproperties.setp(0,15,37,46);
		tileremove.setp(0,15,46,55);
		tileadd.setp(0,15,55,64);
		tileoverlay.setp(0,100,70,100);
		tilemode.setp(40,60,10,20);


		info.setp(0, 100, 70, 80);
		fps.setp(85,100,81,90);
		status.setp(0,100,70,80);
		mode.setp(15,30,90,100);
		layer.setp(30,70,90,100);
		viewmode.setp(70,85,90,100);

		objectpickermid.setp(40, 70, 0, 10);
		objectpickerleft.setp(30, 40, 0, 10);
		objectpickerright.setp(70, 80, 0, 10);

		newterrain.setp(80,100,10,20);
		screenshot.setp(15,85,0,10);

		tilesetsleft.setp(0,15,0,10);
		tilesetsmid.setp(15,85,0,10);
		tilesetsright.setp(85,100,0,10);

		center.setp(85,100,81,90);
		save.setp(0,15,19,28);
		play.setp(0,15,28,37);
		minimap.setp(0,30,70,89);

		canceltutorial.setp(30,70,20,30);

		up.setp(16,36,18,26);
		down.setp(16,36,2,10);
		left.setp(4,26,10,18);
		right.setp(26,48,10,18);
		action1.setp(74,96,10,18);
		action2.setp(64,84,2,10);
		action3.setp(52,74,10,18);
		action4.setp(64,84,18,26);

		restart.setp(0,20,90,100);
		exit.setp(80,100,90,100);

		respawn.setp(80,100,0,10);
		gamestatus.setp(0,100,0,40);
		lock.setp(85,100,10,19);

		//swatches.setp(25,75,10,28);
		sw1.setp(25,42,10,19);
		sw2.setp(42,58,10,19);
		sw3.setp(58,75,10,19);
		sw4.setp(25,42,19,28);
		sw5.setp(42,58,19,28);
		sw6.setp(58,75,19,28);

		editormode.setp(20,40,50,60);
		editorsave.setp(20,40,40,50);
		editorcancel.setp(20,40,30,40);
		editorleft.setp(50,65,40,50);
		editorright.setp(65,80,40,50);
		editorup.setp(58,72,30,40);
		editordown.setp(58,72,50,60);


		menu.setl(0,10,0,15);
		map.setl(0,10,15,25);
		save.setl(0,10,25,35);
		play.setl(0,10,35,45);
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
		pickertool5.setl(90,100,60,70);
		pickertool4.setl(90,100,50,60);
		pickertool3.setl(90,100,40,50);
		pickertool2.setl(90,100,30,40);
		pickertool1.setl(90,100,20,30);

		undo.setl(0,10,90,100);
		redo.setl(90,100,90,100);
		info.setl(0, 100, 70, 80);
		fps.setl(90,100,80,90);
		status.setl(0,100,80,90);

		mode.setl(10,20,90,100);
		layer.setl(20,80,90,100);
		viewmode.setl(80,90,90,100);

		objectpickermid.setl(50, 80, 0, 10);
		objectpickerleft.setl(40, 50, 0, 10);
		objectpickerright.setl(80, 90, 0, 10);

		newterrain.setl(90,100,10,20);
		screenshot.setl(30,70,0,10);

		tilesetsleft.setl(0,10,0,10);
		tilesetsmid.setl(10,90,0,10);
		tilesetsright.setl(90,100,0,10);
		tilemode.setl(40,60,10,20);

		minimap.setl(0,17,60,89);
		lock.setl(90,100,15,25);

		pickerback.setl(0,10,10,20);
		tilewrite.setl(0,10,20,30);
		tilesettings.setl(0,10,30,40);
		tileproperties.setl(0,10,40,50);
		tileremove.setl(0,10,50,60);
		tileadd.setl(0,10,60,70);
		tileoverlay.setl(0,100,70,100);




		canceltutorial.setl(40,60,20,30);

		up.setl(5,15,24,36);
		down.setl(5,15,0, 12);
		left.setl(0,10,12,24);
		right.setl(10,20,12,24);
		action1.setl(90,100,12,24);
		action2.setl(85,95,0,12);
		action3.setl(80,90,12,24);
		action4.setl(85,95,24,36);


		restart.setl(0,10,90,100);
		exit.setl(90,100,90,100);
		respawn.setl(80,98,2,10);
		gamestatus.setl(0,100,0,30);

		//swatches.setl(10,35,0,28);
		sw1.setl(10,18,0,14);
		sw2.setl(18,26,0,14);
		sw3.setl(26,34,0,14);
		sw4.setl(10,18,14,28);
		sw5.setl(18,26,14,28);
		sw6.setl(26,34,14,28);

		editormode.setl(35,45,50,60);
		editorsave.setl(35,45,40,50);
		editorcancel.setl(35,45,30,40);
		editorleft.setl(60,70,40,50);
		editorright.setl(70,80,40,50);
		editorup.setl(65,75,30,40);
		editordown.setl(65,75,50,60);



	}
	
	
}
