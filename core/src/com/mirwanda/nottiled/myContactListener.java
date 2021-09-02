package com.mirwanda.nottiled;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.mirwanda.nottiled.obj.objecttype.MARKER;
import static com.mirwanda.nottiled.obj.objecttype.POINTER;

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
                if (fixB.getUserData() instanceof obj){
                    game.checkBox2D((obj) fixB.getUserData());
                } else{
                    if (game.markermode) return;
                    game.markerstring=(String) fixB.getUserData();
                    if (game.markerstring.equalsIgnoreCase( "P" ))
                    {
                        game.requestoldfunction=true;
                        return;
                    }
                    game.markermode=true;
                }
                game.hidebody=true;
                game.bodyc=0.2f;
            }

            if (fixB.getUserData() == POINTER){
                if (fixA.getUserData() instanceof obj){
                    game.checkBox2D((obj) fixA.getUserData());
                } else{
                   if (game.markermode) return;
                    game.markerstring=(String) fixA.getUserData();
                    if (game.markerstring.equalsIgnoreCase( "P" ))
                    {
                        game.requestoldfunction=true;
                        return;
                    }
                    game.markermode=true;

                }
                game.hidebody=true;
                game.bodyc=0.2f;



            }

            //  Gdx.app.log("TEST","COLLIDED");


        }

        @Override
        public void endContact(Contact contact) { }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) { }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) { }


}
