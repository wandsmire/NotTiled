package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class gameobject extends Sprite {
    public game mygame;
    public gameobject(){}
    public gameobject.objecttype objtype;
    public MapObject obj;
    public enum move {RIGHT,LEFT,UP,DOWN}
    public move moving;
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
        BOX, CHECKPOINT, COIN, KEY, LOCK, GIRL, SPIKE, GEAR, BREAKABLE, SPRING,
        SWITCH, SWITCHON, SWITCHOFF, PLATFORMH, PLATFORMV, PLATFORMS, MONSTER, MISC,
        LEFTSLOPE, RIGHTSLOPE, TRANSFER, BLOCK, ITEM
    }

    public void setupGameObject(World world, TiledMapTile tlcece, int xx, int yy, int width, BodyDef.BodyType type, gameobject.objecttype objecttype, MapObject obj, TextureRegion tt, boolean over)
    {
        this.over=over;
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
        objtype = objecttype;
        switch(objtype){
            case TRANSFER: case BLOCK: case ITEM:
                this.obj=obj;
                setSize( 16 / 100f, 16 / 100f );
                setPosition( xx / 100f, yy / 100f );
            break;
            default:
                setPosition( xx * ts / 100f, yy * ts / 100f );
        }
        switch (objecttype) {
            //dorongable
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
            case CHECKPOINT: case COIN:case KEY: case GIRL: case MISC:
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
                fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
                /////
                bdef.type = type;
                bdef.position.set((xx * ts + tso) / 100f, (yy * ts + tso) / 100f);
                setPosition(xx*ts/100f,yy*ts/100f);
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



            default:
        }




        if (objtype==objecttype.PLATFORMH){
            moving=move.LEFT;
        }

        if (objtype==objecttype.PLATFORMV){
            moving=move.UP;
        }

        if (objtype==objecttype.MONSTER){
            moving=move.LEFT;
        }
    }


    public void update(float dt){


       if (body!=null) setPosition(body.getPosition().x-tso/100f,body.getPosition().y-tso/100f);


        switch (objtype) {
            //dorongable
            case GEAR:
                rotate(5f);
                break;
            case PLATFORMH:
                if (moving== move.RIGHT)
                {
                    body.setLinearVelocity(0.5f,body.getLinearVelocity().y);
                }
                else if (moving== move.LEFT)
                {
                    body.setLinearVelocity(-0.5f,body.getLinearVelocity().y);
                }
                break;
            case PLATFORMV:
                if (moving== move.UP)
                {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0.5f);
                }
                else
                {
                    body.setLinearVelocity(body.getLinearVelocity().x, -0.5f);
                }
                break;

            case MONSTER:
                if (body.getPosition().dst( mygame.player.b2body.getPosition()) <50/100f && mygame.dead==0){
                    if (body.getPosition().x>mygame.player.b2body.getPosition().x){
                        moving=move.LEFT;
                    }else
                    {
                        moving=move.RIGHT;
                    }
                }

                if (moving== move.RIGHT)
                {
                   // if (body.getLinearVelocity().x >=-0.5f) {
                       // body.applyLinearImpulse(-0.5f, 0, 0, 0, true);
                        body.setLinearVelocity(0.5f,body.getLinearVelocity().y);
                  //  }
                    setFlip(true,false);

                }
                else
                {
                   // if (body.getLinearVelocity().x <=0.5f) {
                        body.setLinearVelocity(-0.5f,body.getLinearVelocity().y);

                   // }
                    setFlip(false,false);

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

}
