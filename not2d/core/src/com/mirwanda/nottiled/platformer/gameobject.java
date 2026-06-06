package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.PointLight;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMYPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PARTICLE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYER;

public class gameobject extends Sprite {
    public String id="";
    public String name="";
    public String text=null;

    public game mygame;
    public int offsetx;
    public int offsety;
    public int slot;

    // Animation attributes
    public boolean animBreathing = false;
    public float breathingSpeed = 8f;
    public float breathingAmount = 0.05f;

    public boolean animUpDown = false;
    public float upDownSpeed = 2f;
    public float upDownHeight = 0.05f;

    public boolean animTeeter = false;
    public float teeterSpeed = 12f;
    public float teeterAngle = 10f;

    public boolean animSpin = false;
    public float spinSpeed = 360f;

    public boolean anim3dSpin = false;
    public float spin3dSpeed = 9f;

    public String pixelShift = null;
    public float pixelShiftSpeed = 1f;

    public boolean goDownEnabled = true;

    // Spring bounce animation (triggered when player lands on a spring)
    public float springBounceStart = -1f;
    public static final float SPRING_BOUNCE_DURATION = 0.35f;

    public gameobject(){}
    public objecttype objtype;
    public String type;
    public MapObject obj;
    public Sound sfx,sfxdead,psfx;
    public enum move {RIGHT,LEFT,UP,DOWN}
    public int dir;
    public int face;
    public boolean moving, dirlocked;
    public float damage=0;
    public float HP=1;
    public float maxHP=1;
    public float speed;
    public boolean stepping;
    public float pspread=0, spread=0;
    public boolean heavy;
    public actions action;
    public enum actions{JUMP,DASH,SHOOT,JETPACK,NONE,SLASH}
    public float impulse;
    public float wait;
    public String bindvar;
    public TiledMapTile tlcece;
    public float maxdistance;
    public float distance;
    java.util.List<Animation<TextureRegion>> anim = new ArrayList<>(); // Must declare frame type (TextureRegion)
    java.util.List<Animation<TextureRegion>> idle;
    java.util.List<Animation<TextureRegion>> jump;
    java.util.List<Animation<TextureRegion>> slash; // Must declare frame type (TextureRegion)
    java.util.List<Animation<TextureRegion>> panim = new ArrayList<>(); // Must declare frame type (TextureRegion)
    java.util.List<String> animID = new ArrayList<>();
    public Vector2 pimagesize;
    //public ParticleEffect meledak;
    public PointLight myLight;
    public float light;
    public Color bgcolor;
    public Color lightColor = Color.WHITE;
    public Color color;

    ////
    public boolean chase;
    public float chaseRadius;
    public int[] path;
    public int currentPath;
    public int deadtileID;
    ////
    public boolean over;
    public boolean status;
    public Fixture fixture2;
    public Fixture fixture3;
    public Fixture fixture4;
    public Fixture fixture5;

    public Body body;
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    public Fixture fixture;
    public int ts;
    public int tso;
    public java.util.List<TextureRegion> animations;
    public states state;
    public boolean destructible;
    public enum states{ALIVE,DEAD}
    public enum objecttype {
        PLAYER,
        WALLLEFT,WALLTOP,WALLBOTTOM,WALLRIGHT, WALLCENTER, LADDER, BRIDGE, FLOATER, SINKER,
        BRICK, HALFBRICK, BOX, CHECKPOINT,  BREAKABLE, SPRING,ACTION,TOUCHSENSOR,
        SWITCH, SWITCHON, SWITCHOFF, PLATFORMH, PLATFORMV, PLATFORMS, MONSTER, MISC, ITEMSENSOR, ALLSENSOR,
        LEFTSLOPE, RIGHTSLOPE, TRANSFER, BLOCK, ITEM, ENEMY, PLAYERPROJECTILE, ENEMYPROJECTILE, PARTICLE, LISTENER
    }

    float Tswh;
    float Tshh;
    float Tpx;
    float Tpy;
    float xx;
    float yy;
    float width;
    float height;
    BodyDef.BodyType collisiontype;
    World world;

    public void setupGameObject(World world, TiledMapTile tlcece, float xx, float yy, float width, float height, BodyDef.BodyType type, objecttype objecttype, MapObject obj, TextureRegion tt, boolean over, float opacity)
    {
        ts = (int) mygame.Tsw;
        tso = ts/2;
        this.tlcece=tlcece;
        objtype = objecttype;
        this.obj=obj;
        Tswh=width/2f;
        Tshh=height/2f;
        Tpx = Tswh;
        Tpy = Tshh;
        this.xx=xx;
        this.yy=yy;
        this.width=width;
        this.height=height;
        this.collisiontype = type;
        this.world = world;

        ///
      //  meledak = new ParticleEffect();
      //  meledak.load( Gdx.files.internal("platformer/died.p"), Gdx.files.internal("platformer"));
      //  meledak.getEmitters().first().setPosition( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
      //  meledak.scaleEffect(0.001f, 0.001f); //kudu disini
        ///


        if (tlcece!=null) {
            TextureRegion region = tlcece.getTextureRegion();
            setRegion( region );
            setColor( 1, 1, 1, opacity );
            setSize( getRegionWidth() /mygame.scale, getRegionHeight() /mygame.scale );
            setOrigin( Tswh/mygame.scale, Tshh /mygame.scale );
             Tpx = getRegionWidth()/2f;
             Tpy = getRegionHeight()/2f;

        }
        if (tt!=null) {
            setRegion( tt );
            setColor( 1, 1, 1, opacity );
            setSize( getRegionWidth() /mygame.scale, getRegionHeight() /mygame.scale );
            setOrigin( Tswh/mygame.scale, Tshh /mygame.scale );
             Tpx = getRegionWidth()/2f;
             Tpy = getRegionHeight()/2f;

        }

        if(anim.size()>0){
            setSize(anim.get( 0 ).getKeyFrame( 0 ).getRegionWidth()/mygame.scale, anim.get( 0 ).getKeyFrame( 0 ).getRegionWidth()/mygame.scale );
        }





        switch(objtype){
            //object position
            case PLAYERPROJECTILE: case ENEMYPROJECTILE: case PARTICLE:
                setPosition( 9999f,9999f );
                break;
            case ENEMY:
                objtype=MONSTER;
            case BLOCK: case ITEM:

                setPosition( xx /mygame.scale, yy /mygame.scale );
                break;
            //tile position
            default:
                setPosition( xx /mygame.scale, yy /mygame.scale );
        }

        this.over=over;


        //pastikan 2 hal, bukan sensor, sama kedua objek saling filter.
        addBody();
        addFixtures();

        //setSize( width/mygame.scale, height /mygame.scale );
        //setOrigin( width /mygame.scale, height /mygame.scale );


        /*
        if (light>0 && mygame.night) {
            myLight = new PointLight( mygame.rayHandler, 500, lightColor, 100/mygame.scale, 0, 0 );
            myLight.setSoftnessLength(0f );
            myLight.attachToBody( body );
        }

         */






    }

    public void removeCollision(){
        if (body != null && world.getBodyCount()>0) {
            world.destroyBody( body );
            body = null;
        }
    }

    public void removeFixtures(){
       for (Fixture f: body.getFixtureList()){
           body.destroyFixture( f );
       }
    }

    public void addBody(){
        switch (objtype) {
            //dorongable
            case BRICK:
            case HALFBRICK:
            case CHECKPOINT:
            case FLOATER:
            case SINKER:
            case TRANSFER:
            case ITEM:
            case LISTENER:
            case PLATFORMS:
            case SWITCHOFF:
            case BLOCK:
            case PLAYER:
            case SPRING:
            case SWITCH:
            case MONSTER:
            case LEFTSLOPE:
            case RIGHTSLOPE:
            case SWITCHON:
            case LADDER:
            case BRIDGE:

                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                break;

            case BOX:
                bdef.type = collisiontype;
                bdef.linearDamping = 2f;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                break;

            case ITEMSENSOR:
            case TOUCHSENSOR:
            case ALLSENSOR:
            case PLATFORMH:
            case PLATFORMV:
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                body.setGravityScale( 0 );
                break;

            case PLAYERPROJECTILE:
            case ENEMYPROJECTILE:
            case PARTICLE:
                bdef.type = collisiontype;
                bdef.position.set(xx, yy);
                body = world.createBody(bdef);
                break;

            case MISC:
        }
    }

    public void addFixtures(){
        switch (objtype) {
            //dorongable
            case BRICK:
                EdgeShape shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT | game.ALLSENSOR_BIT | game.TOUCHSENSOR_BIT;
                float sz = Tswh/mygame.scale;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;

                body.createFixture(fdef).setUserData(this);

                //left line
                shaper.set(-sz,-sz,-sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //right line
                shaper.set(sz,sz,sz, -sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //bottom line
                shaper.set(-sz,-sz,sz, -sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);



                break;

            case HALFBRICK:
                shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                float szx = Tswh/mygame.scale;
                float szy = Tshh/mygame.scale;
                //top line
                shaper.set(-szx,szy,szx, szy);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);

                //left line
                shaper.set(-szx,0,-szx, szy);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //right line
                shaper.set(szx,szy,szx, 0);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //bottom line
                shaper.set(-szx,0,szx, 0);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);


                break;

            case BOX:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width /mygame.scale, width /mygame.scale);

                //shape.setRadius(8/mygame.scale);
                // shape.set(0,0,0,0);
                fdef.shape = shape;

                MassData mas = new MassData();
                mas.mass = 10;
                body.setMassData(mas);
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.DEFAULT_BIT);


                break;

            case CHECKPOINT:
            case FLOATER: case SINKER:

            case TRANSFER:
            case ITEM:
            case LISTENER:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ITEMSENSOR_BIT | game.ALLSENSOR_BIT | game.TOUCHSENSOR_BIT;
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                break;
            case ITEMSENSOR:
                fdef.filter.categoryBits = game.ITEMSENSOR_BIT;
                fdef.filter.maskBits = game.COIN_BIT;
                /////
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.friction=0;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case ALLSENSOR:
                fdef.filter.categoryBits = game.ALLSENSOR_BIT;
                fdef.filter.maskBits = game.COIN_BIT | game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;;
                /////
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fdef.friction=0;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case TOUCHSENSOR:
                fdef.filter.categoryBits = game.TOUCHSENSOR_BIT;
                fdef.filter.maskBits = game.COIN_BIT | game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;;
                /////
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.friction=0;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            //interact with environment & player

            case LADDER:

                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(Tswh / 500f, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case BRIDGE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT | game.ALLSENSOR_BIT | game.TOUCHSENSOR_BIT;
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = false;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                break;

            //setCategoryFilter(game.COIN_BIT);


            case PLAYERPROJECTILE:
                fdef.filter.categoryBits = game.PLAYERPROJECTILE_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = false;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case ENEMYPROJECTILE:
                fdef.filter.categoryBits = game.ENEMYPROJECTILE_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fdef.isSensor = false;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

            case PARTICLE:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT;
                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(game.COIN_BIT);


                break;

            case PLATFORMS:

                fdef.filter.categoryBits = game.MARKER_BIT;
                fdef.filter.maskBits = game.PLATFORM_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            //lewatable SENSOR
            case SWITCHOFF:
                fdef.filter.categoryBits = game.DESTROYED_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;


            case PLATFORMH: case PLATFORMV:
                fdef.filter.categoryBits = game.PLATFORM_BIT;
                fdef.filter.maskBits = game.MARKER_BIT | game.PLAYER_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case BLOCK:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT | game.ALLSENSOR_BIT | game.TOUCHSENSOR_BIT;
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


            case PLAYER:
                addPlayerCapsuleFixtures();
                break;
            //interact with environment & player
            case SPRING:
            case SWITCH: case MONSTER:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ALLSENSOR_BIT;
                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


            //FULL STATIC MODE

            case LEFTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                EdgeShape shape = new EdgeShape();
                float size =tso/mygame.scale;
                shape.set(-size,-size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line

                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////

                break;

            case RIGHTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape = new EdgeShape();
                size =tso/mygame.scale;
                shape.set(-size,size,size, -size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);

                shape.set(-size,size,-size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);

                break;


            case SWITCHON:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ALLSENSOR_BIT;
                shape = new EdgeShape();
                size =tso/mygame.scale;
                shape.set(-size,size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line
                shape.set(-size,-size,-size, size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture2 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture2,game.DEFAULT_BIT);
                //right line
                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////
                PolygonShape shap = new PolygonShape();

                shap.setAsBox(5 /mygame.scale, 5 /mygame.scale);
                fdef.shape = shap;
                fdef.isSensor=true;
                fixture5 = body.createFixture(fdef);
                fixture5.setUserData( objecttype.WALLCENTER );
                break;



            case MISC:
        }
    }

    /*
    public void addCollision(){
        switch (objtype) {
            //dorongable
            case BRICK:
                EdgeShape shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                float sz = Tswh/mygame.scale;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;

                body.createFixture(fdef).setUserData(this);

                //left line
                shaper.set(-sz,-sz,-sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //right line
                shaper.set(sz,sz,sz, -sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //bottom line
                shaper.set(-sz,-sz,sz, -sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);



                break;

            case HALFBRICK:
                shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                sz = 8/mygame.scale;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);

                //left line
                shaper.set(-sz,0,-sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //right line
                shaper.set(sz,sz,sz, 0);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(this);

                //bottom line
                shaper.set(-sz,0,sz, 0);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(this);


                break;

            case BOX:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.linearDamping = 2f;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                shape.setAsBox(width /mygame.scale, width /mygame.scale);

                //shape.setRadius(8/mygame.scale);
                // shape.set(0,0,0,0);
                fdef.shape = shape;

                MassData mas = new MassData();
                mas.mass = 10;
                body.setMassData(mas);
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.DEFAULT_BIT);


                break;

            case CHECKPOINT:
            case FLOATER: case SINKER:

            case TRANSFER:
            case ITEM:
            case LISTENER:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.ITEMSENSOR_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                break;
            case ITEMSENSOR:
                fdef.filter.categoryBits = game.ITEMSENSOR_BIT;
                fdef.filter.maskBits = game.COIN_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                body.setGravityScale( 0 );

                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.friction=0;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;
            //interact with environment & player

            case LADDER:

                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                shape.setAsBox(Tswh / 500f, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            //setCategoryFilter(game.COIN_BIT);


            case PLAYERPROJECTILE:
                fdef.filter.categoryBits = game.PLAYERPROJECTILE_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.position.set(xx, yy);
                body = world.createBody(bdef);
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = false;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case ENEMYPROJECTILE:
                fdef.filter.categoryBits = game.ENEMYPROJECTILE_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.position.set(xx, yy);
                body = world.createBody(bdef);
                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fdef.isSensor = false;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case PLATFORMS:

                fdef.filter.categoryBits = game.MARKER_BIT;
                fdef.filter.maskBits = game.PLATFORM_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            //lewatable SENSOR
            case SWITCHOFF:
                fdef.filter.categoryBits = game.DESTROYED_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;


            case PLATFORMH: case PLATFORMV:
                fdef.filter.categoryBits = game.PLATFORM_BIT;
                fdef.filter.maskBits = game.MARKER_BIT | game.PLAYER_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                setPosition(xx*ts/mygame.scale,yy*ts/mygame.scale);
                body = world.createBody(bdef);
                body.setGravityScale(0);
                MassData md = new MassData();
                md.mass=999999;
                body.setMassData(md);
                shape.setAsBox(width /mygame.scale, width /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case BLOCK:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);

                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


            case PLAYER:
                fdef.filter.categoryBits = game.PLAYER_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.COIN_BIT | game.BRICK_BIT | game.ENEMYPROJECTILE_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);

                shape.setAsBox(Tswh /mygame.scale, Tshh /mygame.scale);
                fdef.shape = shape;
                fdef.friction=0;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;
            //interact with environment & player
            case SPRING:
            case SWITCH: case MONSTER:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                body = world.createBody(bdef);

                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;


            //FULL STATIC MODE

            case LEFTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                setPosition(xx*ts/mygame.scale,yy*ts/mygame.scale);
                body = world.createBody(bdef);
                //top line



                EdgeShape shape = new EdgeShape();
                float size =tso/mygame.scale;
                shape.set(-size,-size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line

                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////

                break;

            case RIGHTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                setPosition(xx*ts/mygame.scale,yy*ts/mygame.scale);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/mygame.scale;
                shape.set(-size,size,size, -size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);

                shape.set(-size,size,-size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);

                break;


            case SWITCHON:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = collisiontype;
                bdef.position.set((xx + Tpx) /mygame.scale, (yy + Tpy) /mygame.scale);
                setPosition(xx*ts/mygame.scale,yy*ts/mygame.scale);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/mygame.scale;
                shape.set(-size,size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line
                shape.set(-size,-size,-size, size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture2 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture2,game.DEFAULT_BIT);
                //right line
                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture.setUserData(this);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////
                PolygonShape shap = new PolygonShape();

                shap.setAsBox(5 /mygame.scale, 5 /mygame.scale);
                fdef.shape = shap;
                fdef.isSensor=true;
                fixture5 = body.createFixture(fdef);
                fixture5.setUserData( gameobject.objecttype.WALLCENTER );
                break;



            case MISC:
        }
    }

     */
    public Vector2 lastPos;
    public boolean rotating;
    public float waitTime;

    public void updatePhysics(float dt){
        if (body!=null) setPosition(body.getPosition().x-getWidth()/2f+offsetx/mygame.scale,body.getPosition().y-getHeight()/2f+offsety/mygame.scale);
    }
    public void update(int times, float dt){
        for (int t=0;t<times;t++){
        if (state== states.DEAD) return;
        //if (body==null) return;

        if (color!=null){
            setColor( color.r,color.g,color.b,getColor().a );
            color=null;
        }

        if (HP>maxHP) HP=maxHP;
        if (rotating) {
            setOriginCenter();
            rotate(10f);
        }
        if (cooldown >=0) cooldown-=dt;
        if (destructible){
            assert body != null;


            if (HP<=0 || body.getPosition().y<=-0.5f){
                setCategoryFilter( game.DESTROYED_BIT);
                state= states.DEAD;
                body.setLinearVelocity( 0,0 );
                bumbum();
                playSfx( sfxdead );



                MapProperties o=null;
                if (obj!=null) o=obj.getProperties();
                if (tlcece!=null) o=tlcece.getProperties();

                assert o != null;
                if (o.get( "xsetvar" ) != null) {
                    String[] ss = o.get( "xsetvar" ).toString().split( "," );
                    for (String s : ss) {
                        String[] sv = s.split( "=" );
                        mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SET );
                    }
                }

                if (o.get( "xaddvar" ) != null) {
                    String[] ss = o.get( "xaddvar" ).toString().split( "," );
                    for (String s : ss) {
                        String[] sv = s.split( "=" );
                        mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.ADD );
                    }
                }

                if (o.get( "xsubvar" ) != null) {
                    String[] ss = o.get( "xsubvar" ).toString().split( "," );
                    for (String s : ss) {
                        String[] sv = s.split( "=" );
                        mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SUB );
                    }
                }

            }

        }

        if (objtype==null) return;
        switch (objtype) {
            case PLATFORMH:

            case PLATFORMV:

            case LISTENER:
                mygame.mycontactlistener.eventobject( this );
                break;
            case PLAYER:
                break;
            case PLAYERPROJECTILE:
            case ENEMYPROJECTILE:
            case PARTICLE:

                assert body != null;
                switch (dir) {
                    case 0://bawah
                        body.setLinearVelocity( spread, -speed );
                        break;
                    case 1://kanan
                        body.setLinearVelocity( speed, 0.01f + spread );
                        break;
                    case 2://kiri
                        body.setLinearVelocity( -speed, 0.01f + spread );
                        break;
                    case 3://atas
                        body.setLinearVelocity( spread, speed );
                        break;
                }
                distance += speed;
                if (distance > maxdistance) {
                    setCategoryFilter( game.DESTROYED_BIT );
                    body.setLinearVelocity( 0, 0 );
                    state = states.DEAD;

                }
                if (anim.size() > 0) {
                    if (anim.size() == 1) {
                        TextureRegion currentFrame = anim.get( 0 ).getKeyFrame( mygame.stateTime, true );
                        setRegion( currentFrame );

                    } else {
                        TextureRegion currentFrame = anim.get( dir ).getKeyFrame( mygame.stateTime, true );
                        setRegion( currentFrame );

                    }
                }

                if (!rotating) {
                    setOriginCenter();
                    switch (dir) {
                        case 0: //down
                            setFlip( false, false );
                            setRotation( -90 );
                            break;
                        case 1: //right
                            setFlip( false, false );
                            setRotation( 0 );
                            break;
                        case 2: //left
                            setFlip( true, false );
                            setRotation( 0 );
                            break;
                        case 3: //up
                            setFlip( false, false );
                            setRotation( 90 );
                            break;
                    }
                }


                break;

            //if (moving) {
            //}

            case MONSTER:

                if (!tameif.equals( "" )) {
                    String[] ss = tameif.split( "," );
                    int met = 0;

                    for (String s : ss) {
                        String[] sv = s.split( "=" );
                        if (mygame.getVar( sv[0] ) == Float.parseFloat( sv[1] )) {
                            met++;
                        }
                    }
                    tame = met == ss.length;
                }
                boolean shooting = false;
                assert body != null;
                if (body.getPosition().dst( mygame.player.body.getPosition() ) < chaseRadius / mygame.scale) {
                    if (mygame.player.state != states.DEAD && canshoot) {
                        shooting = true;
                        if (!mygame.rpg) {
                            //player ada di kanan
                            if (mygame.player.body.getPosition().x > body.getPosition().x) face = 1;
                            //player ada di kanan
                            if (mygame.player.body.getPosition().x < body.getPosition().x) face = 2;
                        }
                        enemyshoot();
                    }

                }


                if (anim.size() > 0) {
                    float time;
                    int fc = dir;
                    if (anim.size() == 1) fc = 0;
                    if (moving || stepping) {
                        TextureRegion currentFrame = anim.get( fc ).getKeyFrame( mygame.stateTime, true );
                        setRegion( currentFrame );
                    } else {
                        TextureRegion currentFrame = anim.get( fc ).getKeyFrame( 0f, true );
                        setRegion( currentFrame );

                    }
                } else {
                    if (!rotating) {
                        if (mygame.rpg) {
                            setOriginCenter();
                            switch (dir) {
                                case 0: //down
                                    setRotation( 180 );
                                    break;
                                case 1: //right
                                    setRotation( 270 );
                                    break;
                                case 2: //left
                                    setRotation( 90 );
                                    break;
                                case 3: //up
                                    setRotation( 0 );
                                    break;
                            }
                        } else {
                            switch (dir) {
                                case 0: //down
                                    break;
                                case 1: //right
                                    setFlip( true, false );
                                    break;
                                case 2: //left
                                    setFlip( false, false );
                                    break;
                                case 3: //up
                                    break;
                            }
                        }
                    }

                }

                if (path == null && !dirlocked) {
                    if (body.getPosition().dst( mygame.player.body.getPosition() ) < chaseRadius / mygame.scale) {
                        if (chase && !tame && !shooting) moving = true;


                        if (!mygame.rpg) {
                            if (Math.abs( body.getPosition().x - mygame.player.body.getPosition().x ) >= speed * 10 / mygame.scale) {

                                if (body.getPosition().x > mygame.player.body.getPosition().x) {
                                    dir = 2;
                                } else {
                                    dir = 1;
                                }
                            } else {
                                if (Math.abs( body.getPosition().y - mygame.player.body.getPosition().y ) >= speed * 10 / mygame.scale) {
                                    if (body.getPosition().y > mygame.player.body.getPosition().y) {
                                        dir = 0;
                                    } else {
                                        dir = 3;
                                    }
                                } else {
                                    moving = false;
                                }
                            }
                        } else {
                            float ppx = mygame.player.body.getPosition().x;
                            float ppy = mygame.player.body.getPosition().y;
                            float opx = body.getPosition().x;
                            float opy = body.getPosition().y;
                            float gapx = Math.abs( ppx - opx );
                            float gapy = Math.abs( ppy - opy );

                            //left down up right
                            // means mending ke atas/bawah dulu karena lebih dekat
                            if (gapx > gapy) {
                                if (gapx < 16 / mygame.scale) {
                                    if (opx > ppx) {
                                        //lihat kiri
                                        dir = 0;
                                    } else {
                                        //lihat kanan
                                        dir = 3;
                                    }
                                    //menghadap ke player

                                } else {
                                    //reposisi
                                    if (opy > ppy) {
                                        //liat bawah
                                        dir = 1;
                                    } else {
                                        //liat atas
                                        dir = 2;
                                    }
                                }

                                // means mending ke kiri/kanan dulu karena lebih dekat
                            } else {
                                if (gapy < 16 / mygame.scale) {
                                    //menghadap ke player
                                    if (opy > ppy) {
                                        //lihat atas
                                        dir = 2;
                                    } else {
                                        //lihat bawah
                                        dir = 1;
                                    }

                                } else {
                                    //reposisi
                                    if (opx > ppx) {
                                        //lihat atas
                                        dir = 3;
                                    } else {
                                        //lihat bawah
                                        dir = 0;
                                    }
                                }
                            }


                        }


                    } else {
                        //dir=0;
                        moving = false;
                    }
                } else if (path != null) {
                    if (lastPos == null) {
                        currentPath = 0;
                        dir = path[0];
                        currentPath = 0;
                        lastPos = new Vector2( body.getPosition().x, body.getPosition().y );
                        moving = true;
                    }

                    if (lastPos.dst( body.getPosition().x, body.getPosition().y ) >= mygame.Tsw / mygame.scale) {

                        switch (dir){
                            case 0: //down
                                body.setTransform( lastPos.x,lastPos.y-mygame.Tsh / mygame.scale,0 );
                                break;
                            case 1: //left
                                body.setTransform( lastPos.x+mygame.Tsw / mygame.scale,lastPos.y,0 );
                                break;
                            case 2: //right
                                body.setTransform( lastPos.x-mygame.Tsw / mygame.scale,lastPos.y,0 );
                                break;
                            case 3: //up
                                body.setTransform( lastPos.x,lastPos.y+mygame.Tsh / mygame.scale,0 );
                                break;
                        }
                        currentPath += 1;
                        if (currentPath >= path.length) currentPath = 0;
                        dir = path[currentPath];
                        lastPos = new Vector2( body.getPosition().x, body.getPosition().y );
                    }
                }


                if (mygame.player.state == states.DEAD) moving = false;

                if (moving) {
                    if (mygame.rpg || heavy) {
                        switch (dir) {
                            case 0://bawah
                                body.setLinearVelocity( 0, -speed );
                                break;
                            case 1://kanan
                                body.setLinearVelocity( speed, 0 );
                                break;
                            case 2://kiri
                                body.setLinearVelocity( -speed, 0 );
                                break;
                            case 3://atas
                                body.setLinearVelocity( 0, speed );
                                break;
                        }
                    } else {
                        switch (dir) {

                            case 1://kanan
                                body.setLinearVelocity( speed, body.getLinearVelocity().y );
                                break;
                            case 2://kiri
                                body.setLinearVelocity( -speed, body.getLinearVelocity().y );
                                break;
                            default:
                                body.setLinearVelocity( 0, 0 );

                                moving = false;
                        }

                    }
                } else {


                    if (anim.size() > 0) {
                        if (mygame.rpg || stepping) body.setLinearVelocity( 0, 0 );
                        body.setLinearVelocity( body.getLinearVelocity().x, body.getLinearVelocity().y );
                    } else {
                        if (!rotating) {
                            if (mygame.rpg) {
                                setOriginCenter();
                                switch (dir) {
                                    case 0: //down
                                        setRotation( 180 );
                                        break;
                                    case 1: //right
                                        setRotation( 270 );
                                        break;
                                    case 2: //left
                                        setRotation( 90 );
                                        break;
                                    case 3: //up
                                        setRotation( 0 );
                                        break;
                                }
                            } else {
                                switch (dir) {
                                    case 0: //down
                                        break;
                                    case 1: //right
                                        setFlip( true, false );
                                        break;
                                    case 2: //left
                                        setFlip( false, false );
                                        break;
                                    case 3: //up
                                        break;
                                }
                            }
                        }

                    }
                }


                break;

            //make sure the object is kinematic and not static.
            case ITEM:
            case BLOCK:
            default:

                if (objtype == PLAYER) return;
                ///

                if (path != null) {

                    if (lastPos == null) {
                        currentPath = 0;
                        dir = path[0];
                        assert body != null;
                        lastPos = new Vector2( body.getPosition().x, body.getPosition().y );
                        moving = true;
                    }

                    if (dir != 4) {
                        assert body != null;
                        if (lastPos.dst( body.getPosition().x, body.getPosition().y ) >= mygame.Tsw / mygame.scale) {
                            currentPath += 1;
                            if (currentPath >= path.length) currentPath = 0;
                            dir = path[currentPath];
                            lastPos = new Vector2( body.getPosition().x, body.getPosition().y );
                            if (dir == 4) waitTime = wait;

                        }
                    } else {
                        if (waitTime > 0) {
                            waitTime -= dt;
                            if (waitTime <= 0) {
                                currentPath += 1;
                                if (currentPath >= path.length) currentPath = 0;
                                dir = path[currentPath];
                                assert body != null;
                                lastPos = new Vector2( body.getPosition().x, body.getPosition().y );
                                if (dir == 4) waitTime = wait;

                            }
                        }

                    }
                }


                if (mygame.player.state == states.DEAD) moving = false;

                if (moving) {
                    assert body != null;
                    if (mygame.rpg || heavy || objtype == objecttype.BLOCK || objtype == objecttype.ITEM) {
                        switch (dir) {
                            case 0://bawah
                                body.setLinearVelocity( 0, -speed );
                                break;
                            case 1://kanan
                                body.setLinearVelocity( speed, 0 );
                                break;
                            case 2://kiri
                                body.setLinearVelocity( -speed, 0 );
                                break;
                            case 3://atas
                                body.setLinearVelocity( 0, speed );
                                break;
                            case 4://wait
                                body.setLinearVelocity( 0, 0 );
                                break;

                        }
                    } else {

                        switch (dir) {

                            case 1://kanan
                                body.setLinearVelocity( speed, body.getLinearVelocity().y );
                                break;
                            case 2://kiri
                                body.setLinearVelocity( -speed, body.getLinearVelocity().y );
                                break;
                            default:
                                body.setLinearVelocity( 0, 0 );

                                moving = false;
                        }

                    }
                }

                if (anim.size() > 0) {
                    //mygame.log("HERE");
                    assert body != null;
                    if (mygame.rpg || stepping) body.setLinearVelocity( 0, 0 );
                    body.setLinearVelocity( body.getLinearVelocity().x, body.getLinearVelocity().y );

                    TextureRegion currentFrame;
                    if (anim.size() == 1) {
                        currentFrame = anim.get( 0 ).getKeyFrame( mygame.stateTime, true );
                    } else {
                        currentFrame = anim.get( dir ).getKeyFrame( mygame.stateTime, true );

                    }
                    setRegion( currentFrame );
                }
            }
                ///
        }

    }

    public void setCategoryFilter(short filterBit){
        if (fixture==null) return;
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
        if (fixture2!=null) fixture2.setFilterData(filter);
        if (fixture3!=null) fixture3.setFilterData(filter);
        if (fixture4!=null) fixture4.setFilterData(filter);
        if (fixture5!=null) {
            Filter footFilter = new Filter();
            footFilter.categoryBits = filterBit;
            footFilter.maskBits = game.DEFAULT_BIT | game.BRICK_BIT;
            fixture5.setFilterData(footFilter);
        }


    }

    /** Capsule body (box + end caps) plus foot sensor for reliable ground detection. */
    private void addPlayerCapsuleFixtures() {
        fdef.filter.categoryBits = game.PLAYER_BIT;
        fdef.filter.maskBits = game.DEFAULT_BIT | game.COIN_BIT | game.BRICK_BIT | game.ENEMYPROJECTILE_BIT | game.ALLSENSOR_BIT;
        fdef.friction = 0;
        fdef.isSensor = false;

        float hw = Tswh / mygame.scale;
        float hh = Tshh / mygame.scale;
        float radius = Math.min(hw, hh);
        float boxHalfH = hh - radius;

        if (boxHalfH < 0.02f / mygame.scale) {
            CircleShape circle = new CircleShape();
            circle.setRadius(radius);
            fdef.shape = circle;
            fixture = body.createFixture(fdef);
            fixture.setUserData(this);
            circle.dispose();
        } else {
            shape.setAsBox(radius * 0.92f, boxHalfH);
            fdef.shape = shape;
            fixture = body.createFixture(fdef);
            fixture.setUserData(this);

            CircleShape circle = new CircleShape();
            circle.setRadius(radius);
            fdef.shape = circle;
            circle.setPosition(new Vector2(0, boxHalfH));
            fixture2 = body.createFixture(fdef);
            fixture2.setUserData(this);
            circle.setPosition(new Vector2(0, -boxHalfH));
            fixture3 = body.createFixture(fdef);
            fixture3.setUserData(this);
            circle.dispose();
        }

        if (collisiontype != BodyDef.BodyType.DynamicBody) return;

        PolygonShape footShape = new PolygonShape();
        float footW = radius * 0.7f;
        float footH = Math.max(0.05f / mygame.scale, 0.02f);
        footShape.setAsBox(footW, footH, new Vector2(0, -hh + footH * 0.25f), 0);
        fdef.shape = footShape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = game.PLAYER_BIT;
        fdef.filter.maskBits = game.DEFAULT_BIT | game.BRICK_BIT;
        fixture5 = body.createFixture(fdef);
        fixture5.setUserData(game.FOOT_SENSOR);
        fdef.isSensor = false;
        footShape.dispose();
    }

    public float cooldown=0;
    public float pcooldown =0;
    public float pspeed =4;
    public float pmaxdistance =300;
    public int pdamage =1;
    public int pdeadanim;
    public boolean canshoot=false;
    public boolean tame;
    public String tameif;

    public void enemyshoot(){
        if (mygame.starting) return;
        if (tame) return;
        if (cooldown>0||!canshoot) return;
        playSfx( psfx );
        gameobject newbrick = new gameobject();
        newbrick.mygame = mygame;
        newbrick.deadtileID = pdeadanim;
        newbrick.spread=pspread-(2*(float)Math.random()*pspread);
        newbrick.speed=pspeed;
        newbrick.maxdistance=pmaxdistance;
        newbrick.damage=pdamage;
        newbrick.anim=panim;
        newbrick.dir=face;

        float posx=body.getPosition().x;
        float posy=body.getPosition().y;
        switch (dir){
            case 0:
              //  newbrick.setRotation( 180 );
                posy-=mygame.Tsw/mygame.scale;

                break;
            case 1:
               // newbrick.setRotation( -90 );
                posx+=mygame.Tsw/mygame.scale;

                break;
            case 2:
                posx-=mygame.Tsw/mygame.scale;

              //  newbrick.setRotation( 90 );
                break;
            case 3:
                posy+=mygame.Tsw/mygame.scale;

                break;
        }

        if (newbrick.damage>0) {
            newbrick.setupGameObject( mygame.world, null, posx, posy, pimagesize.x, pimagesize.y, BodyDef.BodyType.DynamicBody, ENEMYPROJECTILE, null, null, false, 1 );
        }else{
            newbrick.setupGameObject( mygame.world, null, posx, posy, pimagesize.x, pimagesize.y, BodyDef.BodyType.DynamicBody, PARTICLE, null, null, false, 1 );
        }
        mygame.objects.add( newbrick );

        //myLight = new PointLight( mygame.rayHandler, 500, lightColor, 30/mygame.scale, 0, 0 );
        //myLight.setSoftnessLength( 0f );
        //myLight.setSoftnessLength( 0f );
        //myLight.attachToBody( newbrick.body );

        cooldown= pcooldown;

    }

    public void playSfx(Sound s){
       try{
           s.play(1.0f);
       }catch(Exception ignored){

       }
    }

    public void bumbum(){
        particle p = new particle(mygame);

        float px = 0;
        float py = 0;
        if (body != null) {
            px = body.getPosition().x;
            py = body.getPosition().y;
        } else {
            px = getX() + getWidth() / 2f;
            py = getY() + getHeight() / 2f;
        }
        p.setPosition( px-0.5f*mygame.Tsw/mygame.scale,py -0.5f*mygame.Tsh/mygame.scale);
        if (mygame.animids.size()>0){
            for (int i=0; i< mygame.animids.size();i++){
                if (mygame.animids.get( i )==deadtileID){
                    p.anim = mygame.anims.get( i );
                    mygame.particles.add( p );
                    break;
                }
            }
        }

    }

    @Override
    public void draw(Batch batch) {
        if (state == states.DEAD) return;

        float time = mygame.stateTime;
        // Position-based offset to make animations organic and desynchronized
        float offset = (body != null) ? (body.getPosition().x + body.getPosition().y) : (getX() + getY());
        float animTime = time + offset * 0.5f;

        // Backup current sprite properties
        float originalScaleX = getScaleX();
        float originalScaleY = getScaleY();
        float originalX = getX();
        float originalY = getY();
        float originalRotation = getRotation();

        // Spring squish-pop when the player lands on it
        if (springBounceStart >= 0f) {
            float elapsed = time - springBounceStart;
            if (elapsed >= SPRING_BOUNCE_DURATION) {
                springBounceStart = -1f;
            } else {
                float sx, sy;
                if (elapsed < 0.08f) {
                    float p = elapsed / 0.08f;
                    sx = 1f + 0.25f * p;
                    sy = 1f - 0.4f * p;
                } else if (elapsed < 0.25f) {
                    float p = (elapsed - 0.08f) / 0.17f;
                    sx = 1.25f - 0.35f * p;
                    sy = 0.6f + 0.55f * p;
                } else {
                    float p = (elapsed - 0.25f) / (SPRING_BOUNCE_DURATION - 0.25f);
                    sx = 0.9f + 0.1f * p;
                    sy = 1.15f - 0.15f * p;
                }
                setScale(originalScaleX * sx, originalScaleY * sy);
                setY(originalY + (sy - 1f) * getHeight() * 0.5f);
            }
        }

        // Apply breathing (scale pulsing - vertical breathing squash & stretch, anchored to bottom)
        if (animBreathing && springBounceStart < 0f) {
            float pulse = (float) Math.max(0.0, Math.sin(animTime * breathingSpeed)) * breathingAmount;
            setScale(originalScaleX * (1.0f - pulse * 0.5f), originalScaleY * (1.0f + pulse));
            setY(originalY + (pulse * getHeight() * 0.5f));
        }

        // Apply upDown (vertical bobbing)
        if (animUpDown) {
            float bob = (float) Math.sin(animTime * upDownSpeed) * upDownHeight * getHeight();
            setY(originalY + bob);
        }

        // Apply teeter (back-and-forth rotation)
        if (animTeeter) {
            float angle = (float) Math.sin(animTime * teeterSpeed) * teeterAngle;
            setRotation(originalRotation + angle);
        }

        // Apply spin (continuous rotation)
        if (animSpin) {
            float angle = (time * spinSpeed) % 360f;
            setRotation(originalRotation + angle);
        }

        // Apply 3D spin (horizontal perspective flip - coin spin)
        if (anim3dSpin) {
            float scaleFactor = (float) Math.cos(time * spin3dSpeed);
            setScale(originalScaleX * scaleFactor, originalScaleY);
        }

        // Apply pixel shift if specified
        if (pixelShift != null && !pixelShift.isEmpty()) {
            drawPixelShift(batch, time);
        } else {
            super.draw(batch);
        }

        // Restore original properties
        setScale(originalScaleX, originalScaleY);
        setPosition(originalX, originalY);
        setRotation(originalRotation);
    }

    public void triggerSpringBounce() {
        springBounceStart = mygame.stateTime;
    }

    /** Ping-pong shift 0→1→0 for oscillating pixel shift modes. */
    private float getPixelShiftPingPong(float time) {
        float t = (time * pixelShiftSpeed) % 2.0f;
        if (t < 0) t += 2.0f;
        return t < 1.0f ? t : (2.0f - t);
    }

    /** Linear wrap shift 0→1 for scrolling pixel shift modes. */
    private float getPixelShiftScroll(float time) {
        float shift = (time * pixelShiftSpeed) % 1.0f;
        if (shift < 0) shift += 1.0f;
        return shift;
    }

    private void drawPixelShift(Batch batch, float time) {
        TextureRegion region = this;
        Texture texture = region.getTexture();
        if (texture == null) return;

        float u = region.getU();
        float v = region.getV();
        float u2 = region.getU2();
        float v2 = region.getV2();

        float du = Math.abs(u2 - u);
        float dv = Math.abs(v2 - v);

        float x = getX();
        float y = getY();
        float w = getWidth();
        float h = getHeight();
        float ox = getOriginX();
        float oy = getOriginY();
        float sx = getScaleX();
        float sy = getScaleY();
        float rot = getRotation();
        Color originalColor = batch.getColor();
        batch.setColor(getColor());

        TextureRegion part1 = new TextureRegion(region);
        TextureRegion part2 = new TextureRegion(region);
        TextureRegion part3 = new TextureRegion(region);
        TextureRegion part4 = new TextureRegion(region);

        if (pixelShift.equals("right") || pixelShift.equals("left")) {
            float shift = getPixelShiftScroll(time);
            float effShift = pixelShift.equals("right") ? shift : (1.0f - shift);
            float uSplit;
            if (u < u2) {
                uSplit = u + (1.0f - effShift) * du;
                part1.setRegion(uSplit, v, u2, v2);
                part2.setRegion(u, v, uSplit, v2);
            } else {
                uSplit = u - (1.0f - effShift) * du;
                part1.setRegion(uSplit, v, u2, v2);
                part2.setRegion(u, v, uSplit, v2);
            }
            float w1 = effShift * w;
            float w2 = (1.0f - effShift) * w;
            batch.draw(part1, x, y, ox, oy, w1, h, sx, sy, rot);
            batch.draw(part2, x + w1, y, ox - w1, oy, w2, h, sx, sy, rot);
        } else if (pixelShift.equals("down") || pixelShift.equals("up")) {
            float shift = getPixelShiftScroll(time);
            float effShift = pixelShift.equals("down") ? shift : (1.0f - shift);
            float vSplit;
            if (v < v2) {
                vSplit = v + (1.0f - effShift) * dv;
                part1.setRegion(u, vSplit, u2, v2);
                part2.setRegion(u, v, u2, vSplit);
            } else {
                vSplit = v - (1.0f - effShift) * dv;
                part1.setRegion(u, vSplit, u2, v2);
                part2.setRegion(u, v, u2, vSplit);
            }
            float h1 = effShift * h;
            float h2 = (1.0f - effShift) * h;
            batch.draw(part1, x, y + h2, ox, oy - h2, w, h1, sx, sy, rot);
            batch.draw(part2, x, y, ox, oy, w, h2, sx, sy, rot);
        } else if (pixelShift.equals("leftright")) {
            float effShift = getPixelShiftPingPong(time);
            float uSplit;
            if (u < u2) {
                uSplit = u + (1.0f - effShift) * du;
                part1.setRegion(uSplit, v, u2, v2);
                part2.setRegion(u, v, uSplit, v2);
            } else {
                uSplit = u - (1.0f - effShift) * du;
                part1.setRegion(uSplit, v, u2, v2);
                part2.setRegion(u, v, uSplit, v2);
            }
            float w1 = effShift * w;
            float w2 = (1.0f - effShift) * w;
            batch.draw(part1, x, y, ox, oy, w1, h, sx, sy, rot);
            batch.draw(part2, x + w1, y, ox - w1, oy, w2, h, sx, sy, rot);
        } else if (pixelShift.equals("updown")) {
            float effShift = getPixelShiftPingPong(time);
            float vSplit;
            if (v < v2) {
                vSplit = v + (1.0f - effShift) * dv;
                part1.setRegion(u, vSplit, u2, v2);
                part2.setRegion(u, v, u2, vSplit);
            } else {
                vSplit = v - (1.0f - effShift) * dv;
                part1.setRegion(u, vSplit, u2, v2);
                part2.setRegion(u, v, u2, vSplit);
            }
            float h1 = effShift * h;
            float h2 = (1.0f - effShift) * h;
            batch.draw(part1, x, y + h2, ox, oy - h2, w, h1, sx, sy, rot);
            batch.draw(part2, x, y, ox, oy, w, h2, sx, sy, rot);
        } else if (pixelShift.equals("diagonal") || pixelShift.equals("diagonally")) {
            float shift = getPixelShiftScroll(time);
            float hEffShift = 1.0f - shift; // right to left
            float vEffShift = shift;        // down
            float uSplit;
            if (u < u2) {
                uSplit = u + (1.0f - hEffShift) * du;
            } else {
                uSplit = u - (1.0f - hEffShift) * du;
            }
            float vSplit;
            if (v < v2) {
                vSplit = v + (1.0f - vEffShift) * dv;
            } else {
                vSplit = v - (1.0f - vEffShift) * dv;
            }
            float w1 = hEffShift * w;
            float w2 = (1.0f - hEffShift) * w;
            float h1 = vEffShift * h;
            float h2 = (1.0f - vEffShift) * h;
            part1.setRegion(uSplit, vSplit, u2, v2);
            part2.setRegion(u, vSplit, uSplit, v2);
            part3.setRegion(uSplit, v, u2, vSplit);
            part4.setRegion(u, v, uSplit, vSplit);
            batch.draw(part1, x, y + h2, ox, oy - h2, w1, h1, sx, sy, rot);
            batch.draw(part2, x + w1, y + h2, ox - w1, oy - h2, w2, h1, sx, sy, rot);
            batch.draw(part3, x, y, ox, oy, w1, h2, sx, sy, rot);
            batch.draw(part4, x + w1, y, ox - w1, oy, w2, h2, sx, sy, rot);
        } else {
            super.draw(batch);
        }

        batch.setColor(originalColor);
    }

}
