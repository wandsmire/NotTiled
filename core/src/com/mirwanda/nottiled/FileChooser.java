package com.mirwanda.nottiled;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.*;

public class FileChooser extends Dialog
{

	private Skin skin;
	private FileHandle directory;
	private FileHandle file;
	private String mode;
	private String[] filter;

	public String getOS() {
		return OS;
	}

	public void setOS(String OS) {
		this.OS = OS;
	}

	private String OS;



	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getMode()
	{
		return mode;
	}

	public void setFilter(String[] filter)
	{
	this.filter = filter;
		}

	public String[] getFilter()
	{
	return filter;
	}

	public FileHandle getFile()
	{
		return file;
	}
	
	public FileHandle getDirectory()
	{
		return directory;
	}

	@Override
	public Dialog show(Stage stage)
	{
		return super.show(stage);
	}

	public void setDirectory(FileHandle directory)
	{
		if (this.directory != directory)
		{
			this.directory = directory;
			this.file = null;
			buildList();
		}
	}

	public void setFile(FileHandle file)
	{
		if (this.file != file)
		{
			if (this.file != null)
			{
				Label label = (Label) this.findActor(this.file.name());
				label.setColor(Color.WHITE);
			}
			Label label = (Label) this.findActor(file.name());
			label.setColor(Color.RED);
			this.file = file;
		}      
	}

	public FileChooser(String title, Skin skin, String mode, String[] filter, String OS)
	{
		super(title, skin);
		this.getCell(getButtonTable()).expandX().fill();
		this.getButtonTable().defaults().expandX().fill();
		this.mode = mode;
		this.filter=filter;
		this.button("Cancel", "Cancel");
		this.button("Home", "Home");
		this.button("New Folder", "New Folder");
		this.button("-OK->", "OK");
		this.setModal(false);
		this.skin = skin;
		this.setOS(OS);
	}

	private void buildList()
	{
		Texture txback = new Texture(Gdx.files.internal( "images/up96.png" ));
		Texture txfolder = new Texture(Gdx.files.internal( "images/folder96.png" ));
		Texture txfile = new Texture(Gdx.files.internal( "images/file96.png" ));
		FileHandle[] files = directory.list();
		Arrays.sort(files, new Comparator<FileHandle>() {
				@Override
				public int compare(FileHandle o1, FileHandle o2)
				{
					if (o1.isDirectory() && !o2.isDirectory())
					{
						return -1;
					}
					if (o2.isDirectory() && !o1.isDirectory())
					{
						return +1;
					}
					return o1.name().compareToIgnoreCase(o2.name());
				}
			});
		ScrollPane pane = new ScrollPane(null, skin);

		Table table = new Table().top().left();
		table.defaults().left();
		//table.debug();
		ClickListener fileClickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if (event.getTarget() instanceof  Label) {
					Label target = (Label) event.getTarget();

					if (target.getName().equals( ".." )) {
						setDirectory( directory.parent() );
					} else {
						FileHandle handle = directory.child( target.getName() );
						if (handle.isDirectory()) {
							setDirectory( handle );
						} else {
							setFile( handle );
						}
					}
				}
			}
		};
		Label label;
		if (!directory.path().equalsIgnoreCase("")) {
			if (OS!=null) {

				if (OS.equalsIgnoreCase("android10+")) {
					if (directory.path().length() > Gdx.files.getExternalStoragePath().length()) {
						table.row();
						Image img = new Image(txback);
						table.add(img);
						label = new Label("..", skin);
						label.setName("..");
						label.addListener(fileClickListener);
						table.add(label).expandX().fillX().colspan(2);
					}
				} else {
					table.row();
					Image img = new Image(txback);
					table.add(img);
					label = new Label("..", skin);
					label.setName("..");
					label.addListener(fileClickListener);
					table.add(label).expandX().fillX().colspan(2);
				}
			}else{
				table.row();
				Image img = new Image(txback);
				table.add(img);
				label = new Label("..", skin);
				label.setName("..");
				label.addListener(fileClickListener);
				table.add(label).expandX().fillX().colspan(2);
			}
		}
		for (FileHandle file : files)
		{
			if (mode == "dir")
			{
				if (file.isDirectory())
				{
					table.row();
					Image img = new Image(txfolder);
					img.addListener( fileClickListener );
					table.add( img );
					label = new Label(file.name(), skin);
					label.setColor(1f,1f,0f,1f);
					label.setName(file.name());
					label.addListener(fileClickListener);
					table.add(label).colspan(2).expandX().fillX();
				} 
			}
			else
			{
				if (file.isDirectory())
				{
					table.row();
					Image img = new Image(txfolder);
					table.add( img );
					label = new Label(file.name(), skin);
					label.setColor(1f,1f,0f,1f);

					label.setName(file.name());
					label.addListener(fileClickListener);
					img.addListener( fileClickListener );

					table.add(label).colspan(2).expandX().fillX();
				} else{
					if (filter.length>0){
						for (String str: filter){
							String fn=file.file().getName().toLowerCase();
							if (fn.length()>str.length()){
							if (fn.substring(fn.length()-str.length()).equalsIgnoreCase(str)){
								table.row();

								Image img = new Image(txfile);
								table.add( img );

								label = new Label(file.name(), skin);
								label.setColor(1f,1f,1f,1f);
								label.setName(file.name());
								label.addListener(fileClickListener);
								img.addListener( fileClickListener );

								table.add(label).expandX().fillX();
								break;
							}
							}
						}
					}else{
						table.row();
						table.add();

						Image img = new Image(txfile);
						table.add( img );

						label = new Label(file.name(), skin);
						label.setColor(1f,1f,1f,1f);
						label.setName(file.name());
						label.addListener(fileClickListener);
						img.addListener( fileClickListener );

						table.add(label).expandX().fillX();
						
					}
				}
			}
		}
		pane.setScrollingDisabled(false,false);
		pane.setWidget(table);
		this.getContentTable().reset();
		this.getContentTable().add(pane).expand().fill();
	}

}

