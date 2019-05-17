package com.mirwanda.nottiled;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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

	public FileChooser(String title, Skin skin, String mode, String[] filter)
	{
		super(title, skin);
		this.getCell(getButtonTable()).expandX().fill();
		this.getButtonTable().defaults().expandX().fill();
		this.mode = mode;
		this.filter=filter;
		this.button("Cancel", "Cancel");
		this.button("OK", "OK");

		this.setModal(false);
		this.skin = skin;
	}

	private void buildList()
	{
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
		ClickListener fileClickListener = new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Label target = (Label) event.getTarget();
				if (target.getName().equals(".."))
				{
					setDirectory(directory.parent());
				}
				else
				{
					FileHandle handle = directory.child(target.getName());
					if (handle.isDirectory())
					{
						setDirectory(handle);
					}
					else
					{
						setFile(handle);
					}
				}            
			}
		};
		table.row();
		Label label = new Label("..", skin);
		label.setName("..");
		label.addListener(fileClickListener);
		table.add(label).expandX().fillX();
		for (FileHandle file : files)
		{
			if (mode == "dir")
			{
				if (file.isDirectory())
				{
					table.row();
					label = new Label(file.name(), skin);
					label.setColor(1f,1f,0f,1f);
					label.setName(file.name());
					label.addListener(fileClickListener);
					table.add(label).expandX().fillX();
				} 
			}
			else
			{
				if (file.isDirectory())
				{
					table.row();
					label = new Label(file.name(), skin);
					label.setColor(1f,1f,0f,1f);
					label.setName(file.name());
					label.addListener(fileClickListener);
					table.add(label).expandX().fillX();
				} else{
					if (filter.length>0){
						for (String str: filter){
							String fn=file.file().getName().toLowerCase();
							if (fn.length()>str.length()){
							if (fn.substring(fn.length()-str.length()).equalsIgnoreCase(str)){
								table.row();
								label = new Label(file.name(), skin);
								label.setColor(1f,1f,1f,1f);
								label.setName(file.name());
								label.addListener(fileClickListener);
								table.add(label).expandX().fillX();
								break;
							}
							}
						}
					}else{
						table.row();
						label = new Label(file.name(), skin);
						label.setColor(1f,1f,1f,1f);
						label.setName(file.name());
						label.addListener(fileClickListener);
						table.add(label).expandX().fillX();
						
					}
				}
			}
		}
		pane.setScrollingDisabled(true,false);
		pane.setWidget(table);
		this.getContentTable().reset();
		this.getContentTable().add(pane).expand().fill();
	}

}

