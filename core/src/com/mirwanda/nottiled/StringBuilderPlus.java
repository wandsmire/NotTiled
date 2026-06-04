package com.mirwanda.nottiled;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter;
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
	
	public static String luaString(String s) {
		if (s == null)
			return "";
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
	}

	public void wprop(java.util.List<property> pr){
		if (pr.size()>0){
			wlo("properties = {");
			for(int i=0;i<pr.size();i++)
			{
				property p=pr.get(i);
				String ptype = p.getType() != null ? p.getType().toLowerCase() : "";
				String pvalue = p.getValue() != null ? p.getValue() : "";
				w("[\"" + luaString(p.getName()) + "\"] = ");
				if (ptype.equals("boolean") || ptype.equals("int") || ptype.equals("float")) {
					w(pvalue.replace("\n", "\\n"));
				} else {
					w("\"" + luaString(pvalue) + "\"");
				}
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
	
	public void wpropj(java.util.List<property> pr){
		JsonValue properties = new JsonValue(ValueType.array);
		if (pr.size()>0){
			for(int i=0;i<pr.size();i++)
			{
				JsonValue property = new JsonValue(ValueType.object);
				properties.addChild(property);

				property p=pr.get(i);
				property.addChild("name",new JsonValue(p.getName()));

				if (p.getType().equals(""))
				{
					property.addChild("type", new JsonValue("string"));
				}else
				{
					property.addChild("type", new JsonValue(p.getType()));
				}
				property.addChild("value", new JsonValue(p.getValue()));
			}
		}
		wl("\"properties\":"+properties.toJson(JsonWriter.OutputType.json));
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
