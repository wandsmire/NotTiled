package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
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

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMYPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.player.playerState.DEAD;

public class gameobject extends Sprite {
    public String name;
    public game mygame;
    public gameobject(){}
    public gameobject.objecttype objtype;
    public MapObject obj;
    public enum move {RIGHT,LEFT,UP,DOWN}
    public int dir;
    public boolean moving;
    public float damage=1;
    public float HP;
    public float speed;
    public boolean bird;
    public float maxdistance;
    public float distance;
    java.util.List<Animation<TextureRegion>> anim = new ArrayList<>(); // Must declare frame type (TextureRegion)


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
    public enum objecttype {
        PLAYER, PLAYERLEFT,PLAYERRIGHT,PLAYERTOP,PLAYERBOTTOM, PLAYERCENTER,
        WALLLEFT,WALLTOP,WALLBOTTOM,WALLRIGHT, WALLCENTER, LADDER, FLOATER, SINKER,
        BRICK, HALFBRICK, BOX, CHECKPOINT, COIN, KEY, LOCK, GIRL, SPIKE, GEAR, BREAKABLE, SPRING,
        SWITCH, SWITCHON, SWITCHOFF, PLATFORMH, PLATFORMV, PLATFORMS, MONSTER, MISC,
        LEFTSLOPE, RIGHTSLOPE, TRANSFER, BLOCK, ITEM, ENEMY, PLAYERPROJECTILE, ENEMYPROJECTILE, LISTENER
    }

    public void setupGameObject(World world, TiledMapTile tlcece, float xx, float yy, int width, BodyDef.BodyType type, gameobject.objecttype objecttype, MapObject obj, TextureRegion tt, boolean over)
    {
        objtype = objecttype;
        this.obj=obj;

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
                setPosition( xx * ts / 100f, yy * ts / 100f );
        }

        this.over=over;
        switch (objtype) {
            //dorongable
            case BRICK:
                EdgeShape shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                body = world.createBody(bdef);
                float sz = 8/100f;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLTOP);

                //left line
                shaper.set(-sz,-sz,-sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLLEFT);

                //right line
                shaper.set(sz,sz,sz, -sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLRIGHT);

                //bottom line
                shaper.set(-sz,-sz,sz, -sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLBOTTOM);



                break;

            case HALFBRICK:
                shaper = new EdgeShape();
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                body = world.createBody(bdef);
                sz = 8/100f;
                //top line
                shaper.set(-sz,sz,sz, sz);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLTOP);

                //left line
                shaper.set(-sz,0,-sz, sz);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLLEFT);

                //right line
                shaper.set(sz,sz,sz, 0);
                fdef.shape = shaper;
                fdef.friction=0;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLRIGHT);

                //bottom line
                shaper.set(-sz,0,sz, 0);
                fdef.shape = shaper;
                body.createFixture(fdef).setUserData(gameobject.objecttype.WALLBOTTOM);



                break;

            case BOX:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.linearDamping = 2f;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
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
            case CHECKPOINT: case COIN:case KEY: case GIRL:
            case FLOATER: case SINKER:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);
                break;
            case LADDER:

                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 500f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

            case TRANSFER: case ITEM:
                fdef.filter.categoryBits = game.COIN_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                bdef.type = type;
                bdef.position.set((xx +tso) / 100f, (yy + tso) / 100f);
                body = world.createBody(bdef);
                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fdef.isSensor = true;
                fixture = body.createFixture(fdef);
                fixture.setUserData(this);

                //setCategoryFilter(game.COIN_BIT);


                break;

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
                shape.setAsBox(width / 100f, width / 100f);
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
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
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
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
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
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
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
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx  + tso) / 100f, (yy  + tso) / 100f);
                body = world.createBody(bdef);

                shape.setAsBox(width / 100f, width / 100f);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                fixture.setUserData(this);
                break;

                //interact with environment & player
            case LOCK:  case BREAKABLE: case GEAR: case SPRING: case SPIKE:
            case SWITCH: case MONSTER:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT | game.PLAYERPROJECTILE_BIT;
                /////
                bdef.type = type;
                bdef.position.set(getX()+width/100f, getY()+width/100f);
                body = world.createBody(bdef);

                shape.setAsBox(width / 100f, width / 100f);
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
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                EdgeShape shape = new EdgeShape();
                float size =tso/100f;
                shape.set(-size,-size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line

                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLRIGHT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);
                /////

                break;

            case RIGHTSLOPE:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/100f;
                shape.set(-size,size,size, -size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);

                shape.set(-size,size,-size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLLEFT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
                //setCategoryFilter(fixture4,game.DEFAULT_BIT);

                break;


            case SWITCHON:
                fdef.filter.categoryBits = game.DEFAULT_BIT;
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
                body = world.createBody(bdef);
                //top line



                shape = new EdgeShape();
                size =tso/100f;
                shape.set(-size,size,size, size);
                fdef.shape = shape;
                fixture = body.createFixture(fdef);
                fixture.setUserData(objecttype.WALLTOP);
                //setCategoryFilter(fixture,game.DEFAULT_BIT);
                //left line
                shape.set(-size,-size,-size, size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture2 = body.createFixture(fdef);
                fixture2.setUserData(objecttype.WALLLEFT);
                //setCategoryFilter(fixture2,game.DEFAULT_BIT);
                //right line
                shape.set(size,size,size, -size);
                fdef.shape = shape;
                fdef.friction=0;
                fixture3 = body.createFixture(fdef);
                fixture3.setUserData(objecttype.WALLRIGHT);
                //setCategoryFilter(fixture3,game.DEFAULT_BIT);
                //bottom line
                shape.set(-size,-size,size, -size);
                fdef.shape = shape;
                fixture4 = body.createFixture(fdef);
                fixture4.setUserData(objecttype.WALLBOTTOM);
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


        if (tlcece!=null) {
            TextureRegion region = tlcece.getTextureRegion();
            setRegion( region );
            setColor( 1, 1, 1, 1 );
            setSize( region.getRegionWidth() / 100f, region.getRegionHeight() / 100f );
            setOrigin( tso / 100f, tso / 100f );
        }
        if (tt!=null) {
            Gdx.app.log("ASO",tt.getRegionHeight()+"");
            setRegion( tt );
            setColor( 1, 1, 1, 1 );
            setSize( tt.getRegionWidth() / 100f, tt.getRegionHeight() / 100f );
            setOrigin( tso / 100f, tso / 100f );
        }


    }

    public Vector2 lastPos;
    public void update(float dt){


       if (body!=null) setPosition(body.getPosition().x-tso/100f,body.getPosition().y-tso/100f);


        switch (objtype) {
            //dorongable
            case GEAR:
                rotate(5f);
                break;
            case PLATFORMH:

            case PLATFORMV:


            case PLAYERPROJECTILE:

                switch(dir){
                    case 0://bawah
                        body.setLinearVelocity(0, -speed);
                        break;
                    case 1://kanan
                        body.setLinearVelocity(speed, 0);
                        break;
                    case 2://kiri
                        body.setLinearVelocity(-speed, 0);
                        break;
                    case 3://atas
                        body.setLinearVelocity(0, speed);
                        break;
                }
                distance+=speed;
                if (distance>maxdistance){
                    setCategoryFilter(game.DESTROYED_BIT);
                    body.setLinearVelocity( 0,0 );
                    mygame.objects.remove(this);

                }
                break;
            case ENEMYPROJECTILE:

                switch(dir){
                    case 0://bawah
                        body.setLinearVelocity(0, -speed);
                        break;
                    case 1://kanan
                        body.setLinearVelocity(speed, 0);
                        break;
                    case 2://kiri
                        body.setLinearVelocity(-speed, 0);
                        break;
                    case 3://atas
                        body.setLinearVelocity(0, speed);
                        break;
                }
                distance+=speed;
                if (distance>maxdistance){
                    setCategoryFilter(game.DESTROYED_BIT);
                    mygame.objects.remove(this);

                }
                break;

            case MONSTER:

               if (body.getPosition().dst( mygame.player.b2body.getPosition()) <chaseRadius/100f) {
                   if (mygame.player.state!= player.playerState.DEAD) enemyshoot();
               }

                cooldown-=dt;
                if (HP<=0 || body.getPosition().y<=-0.5f){
                    setCategoryFilter(game.DESTROYED_BIT);
                    body.setLinearVelocity( 0,0 );

                    mygame.meledak.setPosition( body.getPosition().x, body.getPosition().y );
                    mygame.meledak.reset(false);
                    mygame.meledak.start();


                    mygame.objects.remove(this);
                    if (obj!=null) {
                        if (obj.getProperties().get( "xsetvar" ) != null) {
                            String[] ss = obj.getProperties().get( "xsetvar" ).toString().split( "," );
                            String[] vv = obj.getProperties().get( "xsetvarval" ).toString().split( "," );
                            int rq = 0;
                            for (int i = 0; i < ss.length; i++) {
                                mygame.setOrAddVars( ss[i], Integer.parseInt( vv[i] ), game.VAROP.SET );
                            }
                        }

                        if (obj.getProperties().get( "xaddvar" ) != null) {
                            String[] ss = obj.getProperties().get( "xaddvar" ).toString().split( "," );
                            String[] vv = obj.getProperties().get( "xaddvarval" ).toString().split( "," );
                            int rq = 0;
                            for (int i = 0; i < ss.length; i++) {
                                mygame.setOrAddVars( ss[i], Integer.parseInt( vv[i] ), game.VAROP.ADD );
                            }
                        }

                        if (obj.getProperties().get( "xsubvar" ) != null) {
                            String[] ss = obj.getProperties().get( "xsubvar" ).toString().split( "," );
                            String[] vv = obj.getProperties().get( "xsubvarval" ).toString().split( "," );
                            int rq = 0;
                            for (int i = 0; i < ss.length; i++) {
                                mygame.setOrAddVars( ss[i], Integer.parseInt( vv[i] ), game.VAROP.SUB );
                            }
                        }
                    }


                }

                if (moving || bird) {
                    TextureRegion currentFrame = anim.get( dir ).getKeyFrame( mygame.stateTime, true );
                    setRegion( currentFrame );
                }else{
                    TextureRegion currentFrame = anim.get( dir ).getKeyFrame( 0f, true );
                    setRegion( currentFrame );

                }


                if (path==null) {
                    if (body.getPosition().dst( mygame.player.b2body.getPosition() ) < chaseRadius / 100f) {
                        if (chase) moving = true;

                        if (Math.abs( body.getPosition().x - mygame.player.b2body.getPosition().x ) >= speed * 10 / 100f) {

                            if (body.getPosition().x > mygame.player.b2body.getPosition().x) {
                                dir = 2;
                            } else {
                                dir = 1;
                            }
                        } else {
                            if (Math.abs( body.getPosition().y - mygame.player.b2body.getPosition().y ) >= speed * 10 / 100f) {
                                if (body.getPosition().y > mygame.player.b2body.getPosition().y) {
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


                if (mygame.player.state == player.playerState.DEAD) moving=false;

                if (moving) {
                    if (mygame.rpg||bird) {
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
                    if (mygame.rpg || bird)  body.setLinearVelocity( 0, 0 );
                    body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
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
    public float setcooldown=0.1f;
    public void enemyshoot(){
        if (cooldown>0) return;
        if (1==1) return;
        gameobject newbrick = new gameobject();
        newbrick.mygame = mygame;
        newbrick.speed=4f;
        newbrick.maxdistance=300f;
        newbrick.damage=1;
        newbrick.dir=dir;
        float posx=body.getPosition().x;
        float posy=body.getPosition().y;
        switch (dir){
            case 0:
                newbrick.setRotation( 180 );
                posy-=16/100f;

                break;
            case 1:
                newbrick.setRotation( -90 );
                posx+=16/100f;

                break;
            case 2:
                posx-=16/100f;

                newbrick.setRotation( 90 );
                break;
            case 3:
                posy+=16/100f;

                break;
        }
        newbrick.setupGameObject( mygame.world, mygame.tlplatformh.get(0),posx, posy, 5, BodyDef.BodyType.DynamicBody, ENEMYPROJECTILE, null ,null ,false);
        mygame.objects.add( newbrick );
        cooldown=setcooldown;

    }


}
