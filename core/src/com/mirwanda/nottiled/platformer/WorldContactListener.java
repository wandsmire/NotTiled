package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BOX;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMH;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMV;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERBOTTOM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.WALLCENTER;

public class WorldContactListener implements ContactListener {

    game mygame;
    public WorldContactListener(game mygame){
        this.mygame = mygame;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        /////

        if (fixA.getUserData() ==gameobject.objecttype.WALLLEFT || fixB.getUserData() ==gameobject.objecttype.WALLLEFT ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.WALLLEFT ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){
                        case MONSTER:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject.moving == gameobject.move.RIGHT) {
                                myobject.moving = gameobject.move.LEFT;

                            }
                            else if (myobject.moving == gameobject.move.LEFT) {
                                myobject.moving = gameobject.move.RIGHT;
                            }
                            break;
                    }
                }
            }
        }

        if (fixA.getUserData() ==gameobject.objecttype.WALLRIGHT || fixB.getUserData() ==gameobject.objecttype.WALLRIGHT ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.WALLRIGHT ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case MONSTER:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject.moving == gameobject.move.RIGHT) {
                                myobject.moving = gameobject.move.LEFT;

                            }
                            else if (myobject.moving == gameobject.move.LEFT) {
                                myobject.moving = gameobject.move.RIGHT;
                            }
                            break;

                    }
                }
            }
        }




        //OBJECT TO OBJECT COLLISION
            if (fixA.getUserData()!=null && gameobject.class.isAssignableFrom(fixA.getUserData().getClass()) && fixB.getUserData()!=null && gameobject.class.isAssignableFrom(fixB.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixA.getUserData();
                gameobject myobject2 = (gameobject) fixB.getUserData();

                    switch (myobject.objtype){
                        case PLATFORMS:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject2.objtype ==PLATFORMH) {
                                if (myobject2.moving == gameobject.move.RIGHT) {
                                    myobject2.moving = gameobject.move.LEFT;

                                } else if (myobject2.moving == gameobject.move.LEFT) {
                                    myobject2.moving = gameobject.move.RIGHT;
                                }
                            } else if (myobject2.objtype ==PLATFORMV){
                                //myobject.setCategoryFilter(game.DESTROYED_BIT);
                                if (myobject2.moving == gameobject.move.UP) {
                                    myobject2.moving = gameobject.move.DOWN;

                                }
                                else if (myobject2.moving == gameobject.move.DOWN) {
                                    myobject2.moving = gameobject.move.UP;
                                }
                            }
                            break;
                        case MONSTER:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject2.objtype ==MONSTER) {
                                if (myobject.moving == gameobject.move.RIGHT) {
                                    myobject.moving = gameobject.move.LEFT;

                                } else if (myobject.moving == gameobject.move.LEFT) {
                                    myobject.moving = gameobject.move.RIGHT;
                                }
                            }

                            if (myobject2.objtype == BOX) {
                                if (myobject.moving == gameobject.move.RIGHT) {
                                    myobject.moving = gameobject.move.LEFT;

                                } else if (myobject.moving == gameobject.move.LEFT) {
                                    myobject.moving = gameobject.move.RIGHT;
                                }
                            }
                        if (myobject2.objtype ==gameobject.objecttype.SWITCH) {
                            TextureRegion regon = null;
                            TextureRegion regoff = null;
                            for (TiledMapTile tmt : mygame.tlswitchons) {
                                regon = tmt.getTextureRegion();
                            }
                            for (TiledMapTile tmt : mygame.tlswitchoffs) {
                                regoff = tmt.getTextureRegion();
                            }
                            for (gameobject goj : mygame.objects) {
                                if (goj.objtype == gameobject.objecttype.SWITCHON) {
                                    goj.objtype = gameobject.objecttype.SWITCHOFF;
                                    goj.setCategoryFilter(game.DESTROYED_BIT);
                                    if (regoff != null) goj.setRegion(regoff);
                                    continue;
                                }
                                if (goj.objtype == gameobject.objecttype.SWITCHOFF) {
                                    goj.objtype = gameobject.objecttype.SWITCHON;
                                    goj.setCategoryFilter(game.DEFAULT_BIT);

                                    if (regon != null) goj.setRegion(regon);
                                    continue;
                                }
                            }
                            mygame.playSfx(mygame.sfxswitch);

                                if (myobject.moving == gameobject.move.RIGHT) {
                                    myobject.moving = gameobject.move.LEFT;

                                } else if (myobject.moving == gameobject.move.LEFT) {
                                    myobject.moving = gameobject.move.RIGHT;
                                }

                        }

                            break;
                        case GEAR:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject2.objtype ==MONSTER) {
                                myobject2.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.objects.remove(myobject2);
                                mygame.playSfx(mygame.sfxmonster);
                            }
                            break;

                    }

                    ////////////?REVERSE
                switch (myobject2.objtype){
                    case PLATFORMS:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);
                        if (myobject.objtype ==PLATFORMH) {
                            if (myobject.moving == gameobject.move.RIGHT) {
                                myobject.moving = gameobject.move.LEFT;

                            } else if (myobject.moving == gameobject.move.LEFT) {
                                myobject.moving = gameobject.move.RIGHT;
                            }
                        } else if (myobject.objtype ==PLATFORMV){
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject.moving == gameobject.move.UP) {
                                myobject.moving = gameobject.move.DOWN;

                            }
                            else if (myobject.moving == gameobject.move.DOWN) {
                                myobject.moving = gameobject.move.UP;
                            }
                        }
                        break;

                    case MONSTER:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);
                        if (myobject.objtype ==MONSTER) {
                            if (myobject2.moving == gameobject.move.RIGHT) {
                                myobject2.moving = gameobject.move.LEFT;

                            } else if (myobject2.moving == gameobject.move.LEFT) {
                                myobject2.moving = gameobject.move.RIGHT;
                            }

                        }
                        if (myobject.objtype ==gameobject.objecttype.SWITCH) {
                            TextureRegion regon = null;
                            TextureRegion regoff = null;
                            for (TiledMapTile tmt : mygame.tlswitchons) {
                                regon = tmt.getTextureRegion();
                            }
                            for (TiledMapTile tmt : mygame.tlswitchoffs) {
                                regoff = tmt.getTextureRegion();
                            }
                            for (gameobject goj : mygame.objects) {
                                if (goj.objtype == gameobject.objecttype.SWITCHON) {
                                    goj.objtype = gameobject.objecttype.SWITCHOFF;
                                    goj.setCategoryFilter(game.DESTROYED_BIT);
                                    if (regoff != null) goj.setRegion(regoff);
                                    continue;
                                }
                                if (goj.objtype == gameobject.objecttype.SWITCHOFF) {
                                    goj.objtype = gameobject.objecttype.SWITCHON;
                                    goj.setCategoryFilter(game.DEFAULT_BIT);

                                    if (regon != null) goj.setRegion(regon);
                                    continue;
                                }
                            }
                            mygame.playSfx(mygame.sfxswitch);

                            if (myobject2.moving == gameobject.move.RIGHT) {
                                myobject2.moving = gameobject.move.LEFT;

                            } else if (myobject2.moving == gameobject.move.LEFT) {
                                myobject2.moving = gameobject.move.RIGHT;
                            }

                        }

                        break;
                    case GEAR:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);
                        if (myobject.objtype ==MONSTER) {
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxmonster);
                        }
                        break;

                }
            }


        //bottom
        if (fixA.getUserData() ==gameobject.objecttype.PLAYERBOTTOM || fixB.getUserData() ==gameobject.objecttype.PLAYERBOTTOM ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.PLAYERBOTTOM  ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;
            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){
                        case MONSTER:
                            if (mygame.player.b2body.getLinearVelocity().y >= -1) return;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.player.b2body.setLinearVelocity(0,2);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxmonster);
                            break;
                        case PLATFORMH:
                            mygame.onplatformh=true;
                            mygame.currentplatform = myobject;
                            break;
                        case PLATFORMV:
                            mygame.onplatformv=true;
                            mygame.currentplatform = myobject;
                            break;


                    }
                }
            }
        }

        //bottom
        if (fixA.getUserData() ==gameobject.objecttype.WALLCENTER || fixB.getUserData() ==gameobject.objecttype.WALLCENTER ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.WALLCENTER  ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;
            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case MONSTER:
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.player.b2body.setLinearVelocity(0,2);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxmonster);
                            break;

                    }
                }
            }
        }


        //left
        if (fixA.getUserData() ==gameobject.objecttype.PLAYERLEFT || fixB.getUserData() ==gameobject.objecttype.PLAYERLEFT ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.PLAYERLEFT  ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case MONSTER:
                            if (mygame.stompinterval>0){
                                myobject.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.player.b2body.setLinearVelocity(0,2);
                                mygame.objects.remove(myobject);
                                mygame.playSfx(mygame.sfxmonster);
                            }else{
                                killPlayer();
                            }
                            break;

                    }
                }
            }
        }

        //right
        if (fixA.getUserData() ==gameobject.objecttype.PLAYERRIGHT || fixB.getUserData() ==gameobject.objecttype.PLAYERRIGHT ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.PLAYERRIGHT  ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case MONSTER:
                            if (mygame.stompinterval>0){
                                myobject.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.player.b2body.setLinearVelocity(0,2);
                                mygame.objects.remove(myobject);
                                mygame.playSfx(mygame.sfxmonster);
                            }else{
                                killPlayer();
                            }
                            break;

                    }
                }
            }
        }

        //top
        if (fixA.getUserData() ==gameobject.objecttype.PLAYERTOP || fixB.getUserData() ==gameobject.objecttype.PLAYERTOP ) {
            Fixture fixplayer = fixA.getUserData() == gameobject.objecttype.PLAYERTOP ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case MONSTER:
                            if (mygame.stompinterval>0){
                                myobject.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.player.b2body.setLinearVelocity(0,2);
                                mygame.objects.remove(myobject);
                                mygame.playSfx(mygame.sfxmonster);
                            }else{
                                killPlayer();
                            }
                            break;
                        case BOX:
                            if (myobject.body.getLinearVelocity().y!=0)
                            {
                                killPlayer();
                            }
                            break;
                        case SWITCH:
                            TextureRegion regon = null;
                            TextureRegion regoff = null;
                            for (TiledMapTile tmt: mygame.tlswitchons)
                            {
                                regon = tmt.getTextureRegion();
                            }
                            for (TiledMapTile tmt: mygame.tlswitchoffs)
                            {
                                regoff = tmt.getTextureRegion();
                            }
                            for (gameobject goj: mygame.objects)
                            {
                                if (goj.objtype==gameobject.objecttype.SWITCHON){
                                    goj.objtype=gameobject.objecttype.SWITCHOFF;
                                    goj.setCategoryFilter(game.DESTROYED_BIT);
                                    if (regoff!=null) goj.setRegion(regoff);
                                    continue;
                                }
                                if (goj.objtype==gameobject.objecttype.SWITCHOFF){
                                    goj.objtype=gameobject.objecttype.SWITCHON;
                                    goj.setCategoryFilter(game.DEFAULT_BIT);

                                    if (regon!=null) goj.setRegion(regon);
                                    continue;
                                }
                            }
                            mygame.playSfx(mygame.sfxswitch);
                            break;
                    }
                }
            }
        }

        /////
        if (fixA.getUserData() ==PLAYER || fixB.getUserData() ==PLAYER ) {
            Fixture fixplayer = fixA.getUserData() == PLAYER ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()==WALLCENTER)
            {
                killPlayer();
            }

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){
                        case CHECKPOINT:
                            mygame.checkpoint.set(fixobject.getBody().getPosition().x,fixobject.getBody().getPosition().y);
                            mygame.playSfx(mygame.sfxcheckpoint);
                            break;
                        case SPRING:
                            if (mygame.player.b2body.getLinearVelocity().y<0) {
                                mygame.player.b2body.setLinearVelocity(0, 4);
                                mygame.playSfx(mygame.sfxspring);
                            }
                            break;
                        case SPIKE:
                            killPlayer();
                            break;
                        case GEAR:
                            killPlayer();

                            break;
                        case COIN:
                            mygame.coin-=1;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxcoin);
                            break;
                        case LADDER:
                            mygame.touchedladder+=1;
                            break;
                        case FLOATER:
                            mygame.touchedfloater+=1;
                            break;
                        case SINKER:
                            mygame.touchedsinker+=1;
                            break;

                        case PLATFORMV:
                            break;
                        case KEY:
                            mygame.key+=1;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxkey);

                            break;
                        case LOCK:
                            if (mygame.key <1) return;
                            mygame.key-=1;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            mygame.playSfx(mygame.sfxlock);
                            break;
                        case BREAKABLE:

                            mygame.player.b2body.setLinearVelocity(0,2);
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            break;

                        case GIRL:
                            if (mygame.coin==0) {
                                mygame.victory = true;
                                mygame.player.b2body.setLinearVelocity(0, 0);
                                mygame.playSfx(mygame.sfxgirl);
                            }

                            break;


                    }
                }
            }

        }
    }



    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        /////
        if (fixA.getUserData() ==PLAYER || fixB.getUserData() ==PLAYER ) {
            Fixture fixplayer = fixA.getUserData() == PLAYER ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case LADDER:
                            mygame.touchedladder-=1;
                            break;
                        case FLOATER:
                            mygame.touchedfloater-=1;
                            break;
                        case SINKER:
                            mygame.touchedsinker-=1;
                            break;
                    }
                }
            }

        }

        if (fixA.getUserData() ==PLAYERBOTTOM || fixB.getUserData() ==PLAYERBOTTOM ) {
            Fixture fixplayer = fixA.getUserData() == PLAYERBOTTOM ? fixA : fixB;
            Fixture fixobject = fixplayer == fixA ? fixB : fixA;

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){

                        case PLATFORMH:
                            mygame.onplatformh=false;
                            break;
                        case PLATFORMV:
                            mygame.onplatformv=false;
                            break;
                    }
                }
            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void killPlayer(){
        if (mygame.player.state!= player.playerState.DEAD && !mygame.victory && !mygame.starting)
        {
            mygame.playSfx(mygame.sfxplayer);
            mygame.dead+=1;
            mygame.pe.reset(false);
            mygame.pe.start();
            mygame.player.state= com.mirwanda.nottiled.platformer.player.playerState.DEAD;
            ///////////////////



            ///////////////////
        }

    }
}
