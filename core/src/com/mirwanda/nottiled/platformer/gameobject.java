package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.PointLight;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMYPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;

public class gameobject extends Sprite {
    public String name="";
    public game mygame;
    public gameobject(){}
    public gameobject.objecttype objtype;
    public MapObject obj;
    public Sound sfx,sfxdead,psfx;
    public enum move {RIGHT,LEFT,UP,DOWN}
    public int dir;
    public boolean moving, dirlocked;
    public float damage=0;
    public float HP=1;
    public float maxHP=1;
    public float speed;
    public boolean stepping;
    public float pspread=0, spread=0;
    public boolean heavy;
    public actions action;
    public enum actions{JUMP,DASH,SHOOT,NONE}
    public float impulse;
    public String bindvar;
    public TiledMapTile tlcece;
    public float maxdistance;
    public float distance;
    java.util.List<Animation<TextureRegion>> anim = new ArrayList<>(); // Must declare frame type (TextureRegion)
    java.util.List<Animation<TextureRegion>> panim = new ArrayList<>(); // Must declare frame type (TextureRegion)
    public Vector2 pimagesize;
    public ParticleEffect meledak;
    public PointLight myLight;
    public float light;
    public Color lightColor = Color.WHITE;

    ////
    public boolean chase;
    public float chaseRadius;
    public int[] path;
    public int currentPath;
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
    public int ts=16;
    public int tso=8;
    public java.util.List<TextureRegion> animations;
    public states state;
    public boolean destructible;
    public enum states{ALIVE,DEAD}
    public enum objecttype {
        PLAYER,
        WALLLEFT,WALLTOP,WALLBOTTOM,WALLRIGHT, WALLCENTER, LADDER, FLOATER, SINKER,
        BRICK, HALFBRICK, BOX, CHECKPOINT,  BREAKABLE, SPRING,ACTION,
        SWITCH, SWITCHON, SWITCHOFF, PLATFORMH, PLATFORMV, PLATFORMS, MONSTER, MISC,
        LEFTSLOPE, RIGHTSLOPE, TRANSFER, BLOCK, ITEM, ENEMY, PLAYERPROJECTILE, ENEMYPROJECTILE, LISTENER
    }

    public void setupGameObject(World world, TiledMapTile tlcece, float xx, float yy, float width, float height, BodyDef.BodyType type, gameobject.objecttype objecttype, MapObject obj, TextureRegion tt, boolean over)
    {
        this.tlcece=tlcece;
        objtype = objecttype;
        this.obj=obj;
        float Tswh=width/2f;
        float Tshh=height/2f;
        float Tpx = Tswh;
        float Tpy = Tshh;


        ///
        meledak = new ParticleEffect();
        meledak.load( Gdx.files.internal("platformer/died.p"), Gdx.files.internal("platformer"));
        meledak.getEmitters().first().setPosition( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        meledak.scaleEffect(0.001f, 0.001f); //kudu disini
        ///


        if (tlcece!=null) {
            TextureRegion region = tlcece.getTextureRegion();
            setRegion( region );
            setColor( 1, 1, 1, 1 );
            setSize( getRegionWidth() / 100f, getRegionHeight() / 100f );
            setOrigin( Tswh/ 100f, Tshh / 100f );
             Tpx = getRegionWidth()/2;
             Tpy = getRegionHeight()/2;

        }
        if (tt!=null) {
            setRegion( tt );
            setColor( 1, 1, 1, 1 );
            setSize( getRegionWidth() / 100f, getRegionHeight() / 100f );
            setOrigin( Tswh/ 100f, Tshh / 100f );
             Tpx = getRegionWidth()/2;
             Tpy = getRegionHeight()/2;

        }

        if(anim.size()>0){
            setSize(anim.get( 0 ).getKeyFrame( 0 ).getRegionWidth()/100f, anim.get( 0 ).getKeyFrame( 0 ).getRegionWidth()/100f );
        }





        switch(objtype){
            //object position
            case PLAYERPROJECTILE: case ENEMYPROJECTILE:
                setPosition( 9999f,9999f );
                break;
            case ENEMY:
                objtype=MONSTER;
            case BLOCK: case ITEM:

                setPosition( xx / 100f, yy / 100f );
                break;
            //tile position
            default:
                setPosition( xx / 100f, yy / 100f );
        }

        this.over=over;



        switch (objtype) {
            //dorongable
            case BRICK:
                EdgeShape shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                float sz = Tswh/100f;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
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
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                sz = 8/100f;
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
                bdef.type = type;
                bdef.linearDamping = 2f;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);

                //shape.setRadius(8/100f);
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
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(Tswh / 100f, Tshh / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                break;
            case LADDER:

                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(Tswh / 500f, Tshh / 100f);
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
                bdef.type = type;
                bdef.position.set(xx, yy);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case ENEMYPROJECTILE:
                fdef.filter.categoryBits = game.ENEMYPROJECTILE_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set(xx, yy);
                body = world.createBody(bdef);
                shape.setAsBox(width / 200f, height / 200f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case PLATFORMS:

                fdef.filter.categoryBits = game.MARKER_BIT;
                fdef.filter.maskBits = game.PLATFORM_BIT;
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
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
                bdef.type = type;
                    bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                    fixture.setUserData(this);

                    //setCategoryFilter(game.COIN_BIT);


                break;


            case PLATFORMH: case PLATFORMV:
                fdef.filter.categoryBits = game.PLATFORM_BIT;
                fdef.filter.maskBits = game.MARKER_BIT | game.PLAYER_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                body.setGravityScale(0);
                MassData md = new MassData();
                md.mass=999999;
                body.setMassData(md);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case BLOCK:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT | game.ENEMYPROJECTILE_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);

                shape.setAsBox(Tswh / 100f, Tshh / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

            case PLAYER:
                fdef.filter.categoryBits = game.PLAYER_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.COIN_BIT | game.BRICK_BIT | game.ENEMYPROJECTILE_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                body = world.createBody(bdef);

                shape.setAsBox(Tswh / 100f, Tshh / 100f);
                fdef.shape = shape;
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
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
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
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                EdgeShape shape = new EdgeShape();
                float size =tso/100f;
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
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/100f;
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
                bdef.type = type;
                bdef.position.set((xx + Tpx) / 100f, (yy + Tpy) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/100f;
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

                shap.setAsBox(5 / 100f, 5 / 100f);
                fdef.shape = shap;
                fdef.isSensor=true;
                fixture5 = body.createFixture(fdef);
                fixture5.setUserData(objecttype.WALLCENTER);
                break;



            case MISC:
        }

        //setSize( width/ 100f, height / 100f );
        //setOrigin( width / 100f, height / 100f );

        if (light>0 && mygame.night) {
            myLight = new PointLight( mygame.rayHandler, 100, lightColor, light, 0, 0 );
            myLight.setSoftnessLength( 0.3f );
            myLight.attachToBody( body );
        }




    }

    public Vector2 lastPos;
    public boolean rotating;
    public void update(float dt){
        if (state==states.DEAD) return;





       if (body!=null) setPosition(body.getPosition().x-getWidth()/2f,body.getPosition().y-getHeight()/2f);
        if (HP>maxHP) HP=maxHP;
        if (rotating) {
            setOriginCenter();
            rotate(10f);
        }
        if (cooldown >=0) cooldown-=dt;
        if (destructible){
            assert body != null;


            if (HP<=0 || body.getPosition().y<=-0.5f){
                setCategoryFilter(game.DESTROYED_BIT);
                state=states.DEAD;
                body.setLinearVelocity( 0,0 );
                bumbum();
                playSfx( sfxdead );



                MapProperties o=null;
                if (obj!=null) o=obj.getProperties();
                if (tlcece!=null) o=tlcece.getProperties();

                if (o.get( "xsetvar" ) != null) {
                    String[] ss = o.get( "xsetvar" ).toString().split( "," );
                    for (int i = 0; i < ss.length; i++) {
                        String[] sv = ss[i].split( "=" );
                        mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SET );
                    }
                }

                if (o.get( "xaddvar" ) != null) {
                    String[] ss = o.get( "xaddvar" ).toString().split( "," );
                    for (int i = 0; i < ss.length; i++) {
                        String[] sv = ss[i].split( "=" );
                        mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.ADD );
                    }
                }

                if (o.get( "xsubvar" ) != null) {
                    String[] ss = o.get( "xsubvar" ).toString().split( "," );
                    for (int i = 0; i < ss.length; i++) {
                        String[] sv = ss[i].split( "=" );
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

            case PLAYERPROJECTILE:
            case ENEMYPROJECTILE:

                switch(dir){
                    case 0://bawah
                        body.setLinearVelocity(spread, -speed);
                        break;
                    case 1://kanan
                        body.setLinearVelocity(speed, spread);
                        break;
                    case 2://kiri
                        body.setLinearVelocity(-speed, spread);
                        break;
                    case 3://atas
                        body.setLinearVelocity(spread, speed);
                        break;
                }
                distance+=speed;
                if (distance>maxdistance){
                    setCategoryFilter(game.DESTROYED_BIT);
                    body.setLinearVelocity( 0,0 );
                    state=states.DEAD;

                }
                if (anim.size()>0) {
                    TextureRegion currentFrame = anim.get( dir ).getKeyFrame( mygame.stateTime, true );
                    setRegion( currentFrame );
                }else{
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

                break;

            //if (moving) {
            //}

            case MONSTER:

                if (tameif!=""){
                    String[] ss= tameif.split( "," );
                    int met=0;

                    for (String s: ss){
                        String[] sv = s.split( "=" );
                        if (mygame.getVar(sv[0])==Float.parseFloat( sv[1] )){
                            met++;
                        }
                    }
                    if (met==ss.length){
                        tame=true;
                    }else{
                        tame=false;
                    }
                }

                if (body.getPosition().dst( mygame.player.body.getPosition()) <chaseRadius/100f) {
                   if (mygame.player.state!= states.DEAD) enemyshoot();
               }


                if (anim.size()>0) {
                    if (moving || stepping) {
                        TextureRegion currentFrame = anim.get( dir ).getKeyFrame( mygame.stateTime, true );
                        setRegion( currentFrame );
                    } else {
                        TextureRegion currentFrame = anim.get( dir ).getKeyFrame( 0f, true );
                        setRegion( currentFrame );

                    }
                }else{
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


                if (path==null && !dirlocked) {
                    if (body.getPosition().dst( mygame.player.body.getPosition() ) < chaseRadius / 100f) {
                        if (chase &&!tame) moving = true;

                        if (Math.abs( body.getPosition().x - mygame.player.body.getPosition().x ) >= speed * 10 / 100f) {

                            if (body.getPosition().x > mygame.player.body.getPosition().x) {
                                dir = 2;
                            } else {
                                dir = 1;
                            }
                        } else {
                            if (Math.abs( body.getPosition().y - mygame.player.body.getPosition().y ) >= speed * 10 / 100f) {
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
                        //dir=0;
                        moving = false;
                    }
                }


                else if (path!=null){
                    if (lastPos==null){
                        currentPath=0;
                        dir = path[0];
                        currentPath=0;
                        lastPos=new Vector2(body.getPosition().x,body.getPosition().y);
                        moving=true;
                    }

                    if(lastPos.dst(body.getPosition().x,body.getPosition().y) >=16/100f){
                        currentPath+=1;
                        if (currentPath>=path.length) currentPath=0;
                        dir = path[currentPath];
                        lastPos=new Vector2(body.getPosition().x,body.getPosition().y);
                    }
                }


                if (mygame.player.state == states.DEAD) moving=false;

                if (moving) {
                    if (mygame.rpg||heavy) {
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
                    }else{
                        switch (dir) {

                            case 1://kanan
                                body.setLinearVelocity( speed, body.getLinearVelocity().y );
                                break;
                            case 2://kiri
                                body.setLinearVelocity( -speed, body.getLinearVelocity().y );
                                break;
                            default:
                                body.setLinearVelocity( 0, 0 );

                                moving=false;
                        }

                    }
                }else{


                    if (anim.size()>0) {
                        if (mygame.rpg || stepping) body.setLinearVelocity( 0, 0 );
                        body.setLinearVelocity( body.getLinearVelocity().x, body.getLinearVelocity().y );
                    }else{
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
        }

    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
        if (fixture2!=null) fixture2.setFilterData(filter);
        if (fixture3!=null) fixture3.setFilterData(filter);
        if (fixture4!=null) fixture4.setFilterData(filter);
        if (fixture5!=null) fixture5.setFilterData(filter);


    }

    public float cooldown;
    public float pcooldown =1;
    public float pspeed =4;
    public float pmaxdistance =300;
    public int pdamage =1;
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
        newbrick.spread=pspread-(2*(float)Math.random()*pspread);
        newbrick.speed=pspeed;
        newbrick.maxdistance=pmaxdistance;
        newbrick.damage=pdamage;
        newbrick.anim=panim;
        newbrick.dir=dir;
        float posx=body.getPosition().x;
        float posy=body.getPosition().y;
        switch (dir){
            case 0:
              //  newbrick.setRotation( 180 );
                posy-=16/100f;

                break;
            case 1:
               // newbrick.setRotation( -90 );
                posx+=16/100f;

                break;
            case 2:
                posx-=16/100f;

              //  newbrick.setRotation( 90 );
                break;
            case 3:
                posy+=16/100f;

                break;
        }
        newbrick.setupGameObject( mygame.world,null,posx, posy, pimagesize.x, pimagesize.y, BodyDef.BodyType.DynamicBody, ENEMYPROJECTILE, null ,null ,false);
        mygame.objects.add( newbrick );
        cooldown= pcooldown;

    }

    public void playSfx(Sound s){
       try{
           s.play(1.0f);
       }catch(Exception e){

       }
    }

    public void bumbum(){
        mygame.particles.add( meledak );
        meledak.setPosition( body.getPosition().x, body.getPosition().y );
        meledak.reset(false);
        meledak.start();
    }

}
