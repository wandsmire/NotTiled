package com.mirwanda.nottiled;

import com.badlogic.gdx.utils.StringBuilder;

public class StringBuilderPlus extends com.badlogic.gdx.utils.StringBuilder
{
	public int indent=0;
	
	public void w(String s){
		String pre="";
		for (int i=0;i<indent;i++){
			pre+= "  ";
		}
		append(pre+s);
	}
	
	public void wprop(java.util.List<property> pr){
		if (pr.size()>0){
			wlo("properties = {");
			for(int i=0;i<pr.size();i++)
			{
				property p=pr.get(i);
				w("[\""+p.getName()+"\"] = \""+p.getValue()+"\"");
				if (i!=pr.size()-1) 
				{
					append(",\n");
				}else
				{
					append("\n");
				}
					
			}
			wlc("},");
		}else
		{
			wl("properties = {},");
		}
	}
	
	public void wl(String s){
		String pre="";
		for (int i=0;i<indent;i++){
			pre+= "  ";
		}
		appendLine(pre+s);
	}
	
	public void wlo(String s){
		String pre="";
		for (int i=0;i<indent;i++){
			pre+= "  ";
		}
		appendLine(pre+s);
		indent++;
	}
	
	public void wlc(String s){
		indent--;
		String pre="";
		for (int i=0;i<indent;i++){
			pre+= "  ";
		}
		appendLine(pre+s);
	}
}
