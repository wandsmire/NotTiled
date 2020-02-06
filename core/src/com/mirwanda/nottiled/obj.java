package com.mirwanda.nottiled;
import java.util.*;

public class obj implements Cloneable
{
	private int id=0;
	private float x=0;
	private float y=0;
	private float w=0;
	private float h=0;
	private String type ="";
	private String shape="";
	private int gid=0;
	private List<point> points = new ArrayList<point>();
	private boolean wrap=false;
	private String text="";
	private float rotation=0f;
	
	private java.util.List<property> properties = new ArrayList<property>();
	
	private String name;
	obj(obj o){
		this.x=o.getX();
		this.y=o.getY();
		this.w=o.getW();
		this.h=o.getH();
		this.type=o.getType();
		this.name=o.getName();
		this.shape=o.getShape();
		this.gid=o.getGid();
		this.points=o.getPoints();
		this.setWrap(o.wrap);
		this.text=o.getText();
		this.properties=o.getProperties();
	}
	obj(int id,int x,int y,int w,int h){
		this.id =id;
		this.x =x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.name="";
		
	}
	obj(){
	}
	obj(int id,int x,int y,int w,int h, String name, String type){
		this.id =id;
		this.x =x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.name=name;
		this.type=type;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public float getRotation()
	{
		return rotation;
	}

	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}  

	public void setShape(String shape)
	{
		this.shape = shape;
	}

	public String getShape()
	{
		return shape;
	}

	public void setGid(int gid)
	{
		this.gid = gid;
	}

	public int getGid()
	{
		return gid;
	}

	public void setPoints(List<point> points)
	{
		this.points = points;
	}

	public List<point> getPoints()
	{
		return points;
	}
	
	public void addPoint(float x,float y){
		this.points.add(new point(x,y));
	}
	
	public void undoPoint(){
		if (this.points.size()>1)
		{
		this.points.remove(this.points.size()-1);
		}
	}

	public float[] getVertices(float tsh)
	{
		float[] g=new float[points.size()*2];
		for (int a=0;a<points.size();a++){
		g[a*2]=points.get(a).getX()+getX();
		g[a*2+1]=-points.get(a).getY()-getY()+tsh;
		}
		return g;
	}
	
	public int getPointsSize(){
		return points.size();
	}
	
	public void setPointsFromString(String str)
	{
		points.clear();
		String[] parts = str.split(" ");
		for (String s: parts){
			String[] xny = s.split(",");
			points.add(new point(Float.parseFloat(xny[0]),Float.parseFloat(xny[1])));
		}
	}
	
	public String getPointsString()
	{
		StringBuilder sb=new StringBuilder();
		for (point p: points){
			Float xx,yy; String xx2,yy2;
			xx=p.getX();yy=p.getY();
			if (xx % 1==0){
				xx2=Integer.toString(xx.intValue());
			}else
			{xx2=Float.toString(xx);}
			
			if (yy % 1==0){
				yy2=Integer.toString(yy.intValue());
			}else
			{yy2=Float.toString(yy);}
			
			sb.append(xx2+","+yy2+" ");
		}
		sb.substring(0,sb.length()-1);
		return sb.toString();
	}
	
	
	public void setWrap(boolean wrap)
	{
		this.wrap = wrap;
	}

	public boolean isWrap()
	{
		return wrap;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public void setProperties(java.util.List<property> properties)
	{
		this.properties = properties;
	}

	public java.util.List<property> getProperties()
	{
		return properties;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	
	
	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getX()
	{
		return x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getY()
	{
		return y;
	}

	

	public float getXa()
	{
		return x-w/2;
	}

	
	public float getYa()
	{
		return y-h/2;
	}
	

	public float getWa()
	{
		return w/2;
	}

	public float getHa()
	{
		return h/2;
	}
	public void setW(float w)
	{
		this.w = w;
	}

	public float getW()
	{
		return w;
	}

	public void setH(float h)
	{
		this.h = h;
	}

	public float getH()
	{
		return h;
	}

public float getYantingelag(float f){
	return -y+f;
}
	
	
public class point{
	private float x;
	private float y;
	
	public point(float x, float y){
		this.x=x;
		this.y=y;
	}
		public void setX(float x)
		{
			this.x = x;
		}

		public float getX()
		{
			return x;
		}

		public void setY(float y)
		{
			this.y = y;
		}

		public float getY()
		{
			return y;
		}}

}
