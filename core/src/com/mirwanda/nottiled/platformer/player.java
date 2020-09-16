package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class player extends Sprite {
    public World world;
    public Body b2body;
    public enum playerState {ALIVE,DEAD}
    public playerState state;
    public boolean moving,faceright;
    public float posx,posy;

    public player(World world, TextureRegion region, float posx, float posy){
        this.world = world;
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth()/100f, region.getRegionHeight()/100f);
        setOrigin(8/100f, 8/100f);
        this.posx=posx;
        this.posy=posy;
        BodyDef bdef = new BodyDef();
        bdef.position.set(posx,posy);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = game.PLAYER_BIT;
        fdef.filter.maskBits = game.DEFAULT_BIT | game.COIN_BIT | game.BRICK_BIT | game.PLATFORM_BIT;
        EdgeShape shape = new EdgeShape();
        float size = 8/100f;

        //top
        shape.set(-2/100f,7/100f,2/100f, 7/100f);
        fdef.shape = shape;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYERTOP);

        //left line
        shape.set(-6/100f,-size/4,-6/100f, size/4);
        fdef.shape = shape;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYERLEFT);

        //right line
        shape.set(6/100f,size/4,6/100f, -size/4);
        fdef.shape = shape;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYERRIGHT);

        //CENTER
        shape.set(1/100f,0,1/100, 0);
        fdef.shape = shape;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYERCENTER);

        //bottom line
        shape.set(-size/2,-10/100f,size/2, -10/100f);
        fdef.shape = shape;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYERBOTTOM);

        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(5/100f,6/100f);

        fdef.shape = shape2;
        fdef.isSensor=false;
        b2body.createFixture(fdef).setUserData(gameobject.objecttype.PLAYER);;

    }


    public void update(float dt){
        if (state != playerState.DEAD) setPosition(b2body.getPosition().x-8/100f,b2body.getPosition().y-8/100f);
        if (b2body.getLinearVelocity().y <-200/100f)
        {
            b2body.setLinearVelocity(b2body.getLinearVelocity().x,-200/100f) ;
        }


    }

}
