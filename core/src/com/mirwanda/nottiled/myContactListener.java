package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mirwanda.nottiled.platformer.game;
import com.mirwanda.nottiled.platformer.gameobject;
import com.mirwanda.nottiled.platformer.player;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BOX;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMH;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMV;
import static com.mirwanda.nottiled.obj.objecttype.OBJECT;
import static com.mirwanda.nottiled.obj.objecttype.POINTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERBOTTOM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.WALLCENTER;

public class myContactListener implements ContactListener {
        MyGdxGame game;
        public myContactListener(MyGdxGame game){
            this.game=game;
        }

        @Override
        public void beginContact(Contact contact) {
            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();
            if (fixA.getUserData() == POINTER){
                game.showPropBox2D((obj) fixB.getUserData());

            }
            if (fixB.getUserData() == POINTER){
                game.showPropBox2D((obj) fixA.getUserData());

            }

                Gdx.app.log("TEST","COLLIDED");


        }

        @Override
        public void endContact(Contact contact) { }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) { }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) { }


}
