package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class wall extends Sprite {
    public Body body;
    public wall(){}
    BodyDef bdef = new BodyDef();
    EdgeShape shape = new EdgeShape();
    FixtureDef fdef = new FixtureDef();

    public void setupWalls(World world, TiledMapTile tlcece, int xx, int yy, int width)
    {

        TextureRegion region = tlcece.getTextureRegion();
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth()/100f, region.getRegionHeight()/100f);
        setOrigin(8/100f, 8/100f);

        fdef.filter.categoryBits = game.DEFAULT_BIT;
        fdef.filter.maskBits = game.DEFAULT_BIT | game.PLAYER_BIT | game.BRICK_BIT;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((xx*16+8)/100f,(yy*16+8)/100f);
        body = world.createBody(bdef);

        float size = 8/100f;

        //top line
        shape.set(-size,size,size, size);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(gameobject.objecttype.WALLTOP);

        //left line
        shape.set(-size,-size,-size, size);
        fdef.shape = shape;
        fdef.friction=0;
        body.createFixture(fdef).setUserData(gameobject.objecttype.WALLLEFT);

        //right line
        shape.set(size,size,size, -size);
        fdef.shape = shape;
        fdef.friction=0;
        body.createFixture(fdef).setUserData(gameobject.objecttype.WALLRIGHT);

        //bottom line
        shape.set(-size,-size,size, -size);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(gameobject.objecttype.WALLBOTTOM);

        setPosition(xx*16/100f,yy*16/100f);
    }
    public void update(float dt){
        setPosition(body.getPosition().x-8/100f,body.getPosition().y-8/100f);

    }

}
