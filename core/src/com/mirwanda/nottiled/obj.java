package com.mirwanda.nottiled;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mirwanda.nottiled.platformer.game;

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
	private List<Vector2> points = new ArrayList<Vector2>();
	private boolean wrap=false;
	private String text="";
	private float rotation=0f;
	private boolean active=false;

	private Body body;
	private BodyDef bdef = new BodyDef();
	private PolygonShape pshape;
	private EdgeShape eshape;
	private FixtureDef fdef = new FixtureDef();
	private Fixture fixture;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public void setBody(Body body) {
		this.body = body;
	}


	public void setBdef(BodyDef bdef) {
		this.bdef = bdef;
	}


	public void setPshape(PolygonShape pshape) {
		this.pshape = pshape;
	}

	public EdgeShape getEshape() {
		return eshape;
	}

	public void setEshape(EdgeShape eshape) {
		this.eshape = eshape;
	}


	public void setFdef(FixtureDef fdef) {
		this.fdef = fdef;
	}


	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public objecttype getObjType() {
		return objType;
	}

	public void setObjType(objecttype objType) {
		this.objType = objType;
	}

	public static short getDefaultBit() {
		return DEFAULT_BIT;
	}

	public static short getPlayerBit() {
		return PLAYER_BIT;
	}

	public static short getMarkerBit() {
		return MARKER_BIT;
	}

	public static double getDegreesToRadians() {
		return DEGREES_TO_RADIANS;
	}

	public enum objecttype {
		OBJECT, POINTER, MARKER
	}
	public objecttype objType = objecttype.OBJECT;
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short MARKER_BIT = 4;


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
	obj(){ }

	obj(int id,int x,int y,int w,int h, String name, String type, World world){
		this.id =id;
		this.x =x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.name=name;
		this.type=type;


	}
	public void setupBox2D(World world){
	}
	public void destroyBody(World world){
		if (body != null) {
			world.destroyBody( body );
			body = null;
		}
	}

	public void updateVertices(World world, int Tsh){
		bdef.type = BodyDef.BodyType.StaticBody;
		fdef.filter.categoryBits = DEFAULT_BIT;
		fdef.filter.maskBits = PLAYER_BIT;

		objType = objecttype.OBJECT;

		Vector2[] vertices = new Vector2[4];
		if (shape=="image") {
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( 0f, 0f );
			vertices[1] = new Vector2( 0, h );
			vertices[2] = new Vector2( w, h );
			vertices[3] = new Vector2( w, 0 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);


		}else if (shape=="polygon"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			if (points.size()>2 & points.size()<9) {
				vertices = new Vector2[points.size()];
				for(int i=0;i<points.size();i++){
					vertices[i]=new Vector2(points.get(i).x,-points.get(i).y);
				}
			}else
			{
				vertices[0] = new Vector2( 0f, 0f );
				vertices[1] = new Vector2( 0, -10 );
				vertices[2] = new Vector2( 10, -10 );
				vertices[3] = new Vector2( 10, 0 );
			}
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);


		}else if (shape=="polyline"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			for(int i=0;i<points.size()-1;i++){
				float x1 = points.get(i).x;
				float y1 = -points.get(i).y;
				float x2 = points.get(i+1).x;
				float y2 = -points.get(i+1).y;
				float dx = x2 - x1;
				float dy = y2 - y1;
				float len = (float) Math.sqrt(dx * dx + dy * dy);
				if (len > 0.1f) {
					float nx = -dy / len;
					float ny = dx / len;
					float r = 12f; // Selection padding radius
					Vector2[] verts = new Vector2[4];
					verts[0] = new Vector2(x1 + r * nx, y1 + r * ny);
					verts[1] = new Vector2(x1 - r * nx, y1 - r * ny);
					verts[2] = new Vector2(x2 - r * nx, y2 - r * ny);
					verts[3] = new Vector2(x2 + r * nx, y2 + r * ny);
					PolygonShape polySeg = new PolygonShape();
					polySeg.set(verts);
					fdef.shape = polySeg;
					fixture = body.createFixture(fdef);
					fixture.setUserData(this);
				}
			}

		}else if (shape=="point"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( -Tsh/4f, -Tsh/4f );
			vertices[1] = new Vector2( Tsh/4, -Tsh/4 );
			vertices[2] = new Vector2( -Tsh/4, Tsh/4 );
			vertices[3] = new Vector2( Tsh/4, Tsh/4 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);
		}else{
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( 0f, 0f );
			vertices[1] = new Vector2( 0, -h );
			vertices[2] = new Vector2( w, -h );
			vertices[3] = new Vector2( w, 0 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);


		}

		float angle = (float) ((360-rotation)*DEGREES_TO_RADIANS);
		body.setTransform( body.getPosition(),angle);
		body.setUserData(this);
	}

	public void updateVerticesActive(World world, int Tsh, float zoom){
		if (body!=null) {
			world.destroyBody( body );
			body = null;
		}
		bdef.type = BodyDef.BodyType.StaticBody;
		fdef.filter.categoryBits = DEFAULT_BIT;
		fdef.filter.maskBits = PLAYER_BIT;

		objType = objecttype.OBJECT;

		Vector2[] vertices = new Vector2[4];
		if (shape=="image") {
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( 0f, 0f );
			vertices[1] = new Vector2( 0, h );
			vertices[2] = new Vector2( w, h );
			vertices[3] = new Vector2( w, 0 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);
			addMarker( "SEI", zoom );
			//addMarker( "R" );

		}else if (shape=="polygon"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			if (points.size()>2 & points.size()<9) {
				vertices = new Vector2[points.size()];
				for(int i=0;i<points.size();i++){
					vertices[i]=new Vector2(points.get(i).x,-points.get(i).y);
				}
			}else
			{
				vertices[0] = new Vector2( 0f, 0f );
				vertices[1] = new Vector2( 0, -10 );
				vertices[2] = new Vector2( 10, -10 );
				vertices[3] = new Vector2( 10, 0 );
			}
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);
			//addMarker( "R" );


		}else if (shape=="polyline"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			for(int i=0;i<points.size()-1;i++){
				float x1 = points.get(i).x;
				float y1 = -points.get(i).y;
				float x2 = points.get(i+1).x;
				float y2 = -points.get(i+1).y;
				float dx = x2 - x1;
				float dy = y2 - y1;
				float len = (float) Math.sqrt(dx * dx + dy * dy);
				if (len > 0.1f) {
					float nx = -dy / len;
					float ny = dx / len;
					float r = 12f; // Selection padding radius
					Vector2[] verts = new Vector2[4];
					verts[0] = new Vector2(x1 + r * nx, y1 + r * ny);
					verts[1] = new Vector2(x1 - r * nx, y1 - r * ny);
					verts[2] = new Vector2(x2 - r * nx, y2 - r * ny);
					verts[3] = new Vector2(x2 + r * nx, y2 + r * ny);
					PolygonShape polySeg = new PolygonShape();
					polySeg.set(verts);
					fdef.shape = polySeg;
					fixture = body.createFixture(fdef);
					fixture.setUserData(this);
				}
			}
			//addMarker( "R" );

		}else if (shape=="point"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( -Tsh/4f, -Tsh/4f );
			vertices[1] = new Vector2( Tsh/4, -Tsh/4 );
			vertices[2] = new Vector2( -Tsh/4, Tsh/4 );
			vertices[3] = new Vector2( Tsh/4, Tsh/4 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);
		}else if (shape=="text"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( -Tsh/4f, -Tsh/4f );
			vertices[1] = new Vector2( Tsh/4, -Tsh/4 );
			vertices[2] = new Vector2( -Tsh/4, Tsh/4 );
			vertices[3] = new Vector2( Tsh/4, Tsh/4 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);
			addMarker( "SE", zoom );

		}else if (shape=="ellipse"){
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( 0f, 0f );
			vertices[1] = new Vector2( 0, -h );
			vertices[2] = new Vector2( w, -h );
			vertices[3] = new Vector2( w, 0 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);

			addMarker( "SE", zoom );
			///

		}else{
			bdef.position.set(x,-y+Tsh);
			body = world.createBody(bdef);

			vertices[0] = new Vector2( 0f, 0f );
			vertices[1] = new Vector2( 0, -h );
			vertices[2] = new Vector2( w, -h );
			vertices[3] = new Vector2( w, 0 );
			pshape = new PolygonShape();
			pshape.set(vertices);
			fdef.shape = pshape;
			fixture = body.createFixture(fdef);
			fixture.setUserData(this);

			addMarker( "SE", zoom );
			//addMarker( "R" );
			///
			///
		}

		addMarker( "NW", zoom );
		addMarker( "NE", zoom );
		addMarker( "SW", zoom );
		addMarker( "N", zoom );
		addMarker( "S", zoom );
		addMarker( "E", zoom );
		addMarker( "W", zoom );
		addMarker( "C", zoom );





		float angle = (float) ((360-rotation)*DEGREES_TO_RADIANS);
		body.setTransform( body.getPosition(),angle);
		body.setUserData(this);
	}

	private void addMarker(String markerstring, float zoom){
		Vector2[] vertices = new Vector2[4];
		float ow = 32f * zoom;

		switch (markerstring){
			case "NW":
				vertices[0] = new Vector2( -ow, -ow );
				vertices[1] = new Vector2( -ow, ow );
				vertices[2] = new Vector2( ow, -ow );
				vertices[3] = new Vector2( ow, ow );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("NW");
				break;

			case "SE":
				vertices[0] = new Vector2( -ow+w, -ow-h );
				vertices[1] = new Vector2( -ow+w, ow-h );
				vertices[2] = new Vector2( ow+w, -ow-h );
				vertices[3] = new Vector2( ow+w, ow-h );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("SE");
				break;

			case "SEI":
				vertices[0] = new Vector2( -ow+w, -ow+h );
				vertices[1] = new Vector2( -ow+w, ow+h );
				vertices[2] = new Vector2( ow+w, -ow+h );
				vertices[3] = new Vector2( ow+w, ow+h );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("SEI");
				break;

			case "NE":
				vertices[0] = new Vector2( -ow+w, -ow );
				vertices[1] = new Vector2( -ow+w, ow );
				vertices[2] = new Vector2( ow+w, -ow );
				vertices[3] = new Vector2( ow+w, ow );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("NE");
				break;

			case "SW":
				vertices[0] = new Vector2( -ow, -ow-h );
				vertices[1] = new Vector2( -ow, ow-h );
				vertices[2] = new Vector2( ow, -ow-h );
				vertices[3] = new Vector2( ow, ow-h );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("SW");
				break;

			case "N":
				vertices[0] = new Vector2( -ow+w/2f, -ow );
				vertices[1] = new Vector2( -ow+w/2f, ow );
				vertices[2] = new Vector2( ow+w/2f, -ow );
				vertices[3] = new Vector2( ow+w/2f, ow );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("N");
				break;

			case "S":
				vertices[0] = new Vector2( -ow+w/2f, -ow-h );
				vertices[1] = new Vector2( -ow+w/2f, ow-h );
				vertices[2] = new Vector2( ow+w/2f, -ow-h );
				vertices[3] = new Vector2( ow+w/2f, ow-h );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("S");
				break;

			case "W":
				vertices[0] = new Vector2( -ow, -ow-h/2f );
				vertices[1] = new Vector2( -ow, ow-h/2f );
				vertices[2] = new Vector2( ow, -ow-h/2f );
				vertices[3] = new Vector2( ow, ow-h/2f );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("W");
				break;

			case "E":
				vertices[0] = new Vector2( -ow+w, -ow-h/2f );
				vertices[1] = new Vector2( -ow+w, ow-h/2f );
				vertices[2] = new Vector2( ow+w, -ow-h/2f );
				vertices[3] = new Vector2( ow+w, ow-h/2f );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("E");
				break;

			case "R":
				vertices[0] = new Vector2( -ow+w/2f, -ow+2*ow );
				vertices[1] = new Vector2( -ow+w/2f, ow+2*ow );
				vertices[2] = new Vector2( ow+w/2f, -ow+2*ow );
				vertices[3] = new Vector2( ow+w/2f, ow+2*ow );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("R");
				break;

			case "P":
				vertices[0] = new Vector2( -ow-2*ow, -ow-h/2f );
				vertices[1] = new Vector2( -ow-2*ow, ow-h/2f );
				vertices[2] = new Vector2( ow-2*ow, -ow-h/2f );
				vertices[3] = new Vector2( ow-2*ow, ow-h/2f );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("P");
				break;

			case "C":
				vertices[0] = new Vector2( -ow+w/2f, -ow-h/2f );
				vertices[1] = new Vector2( -ow+w/2f, ow-h/2f );
				vertices[2] = new Vector2( ow+w/2f, -ow-h/2f );
				vertices[3] = new Vector2( ow+w/2f, ow-h/2f );
				pshape = new PolygonShape();
				pshape.set(vertices);
				fdef.shape = pshape;
				fixture = body.createFixture(fdef);
				fixture.setUserData("C");
				break;
		}

	}

	private static final double DEGREES_TO_RADIANS = (double)(Math.PI/180);

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

	public void setPoints(List<Vector2> points)
	{
		this.points = points;
	}

	public List<Vector2> getPoints()
	{
		return points;
	}
	
	public void addPoint(float x,float y){
		this.points.add(new Vector2(x,y));
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
		g[a*2]=points.get(a).x+getX();
		g[a*2+1]=-points.get(a).y-getY()+tsh;
		}
		return g;
	}

	public float[] getVertices(float tsh, int addx, int addy)
	{
		float[] g=new float[points.size()*2];
		for (int a=0;a<points.size();a++){
			g[a*2]=points.get(a).x+getX()+addx;
			g[a*2+1]=-points.get(a).y-getY()+tsh+addy;
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
			points.add(new Vector2(Float.parseFloat(xny[0]),Float.parseFloat(xny[1])));
		}
	}
	
	public String getPointsString()
	{
		StringBuilder sb=new StringBuilder();
		for (Vector2 p: points){
			Float xx,yy; String xx2,yy2;
			xx=p.x;yy=p.y;
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
