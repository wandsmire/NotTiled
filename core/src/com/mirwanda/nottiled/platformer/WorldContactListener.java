package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BOX;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMY;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMH;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLATFORMV;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERBOTTOM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
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

                        case MONSTER:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);

                            if (myobject2.objtype ==PLAYERPROJECTILE) {
                                myobject.HP-=myobject2.damage;
                                myobject2.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.objects.remove(myobject2);

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


                        break;
                    case MONSTER:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);
                        if (myobject.objtype ==PLAYERPROJECTILE) {
                            myobject.HP-=myobject.damage;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);

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


                        case BOX:

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
                mygame.killPlayer();

            }

            if (fixobject.getUserData()!=null && gameobject.class.isAssignableFrom(fixobject.getUserData().getClass()))
            {
                final gameobject myobject = (gameobject) fixobject.getUserData();
                {
                    switch (myobject.objtype){
                        case CHECKPOINT:
                            mygame.save();
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
                            mygame.killPlayer();

                            break;
                        case GEAR:
                            mygame.killPlayer();


                            break;
                        case ENEMYPROJECTILE:
                            mygame.HP-=1;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
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

                        case MONSTER:
                            mygame.HP -= myobject.damage;

                        case ITEM: case BLOCK:
                            if (myobject.obj==null) return;
                            final MapObject o = myobject.obj;
                            boolean qual=true;
                            if (o.getProperties().get( "condition" )!=null){
                                String[] ss = o.getProperties().get( "condition" ).toString().split( "," );
                                qual=false;
                                int rq=0;
                                for (int i=0;i<ss.length;i++) {
                                    String[] sv = ss[i].split( "=" );
                                    for (KV var : mygame.save.vars) {
                                        if (sv[0].equalsIgnoreCase( var.key )) {
                                            if (Integer.parseInt(sv[1]) == var.value) {
                                                rq+=1;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (rq==ss.length) qual=true;
                            }
                            if (qual)
                            {

                                if (o.getProperties().get( "setvar" )!=null){
                                    String[] ss = o.getProperties().get( "setvar" ).toString().split( "," );
                                    for (int i=0;i<ss.length;i++) {
                                        String[] sv = ss[i].split( "=" );
                                        mygame.setOrAddVars( sv[0],Integer.parseInt(sv[1]), game.VAROP.SET);
                                    }
                                }

                                if (o.getProperties().get( "addvar" )!=null){
                                    String[] ss = o.getProperties().get( "addvar" ).toString().split( "," );
                                    for (int i=0;i<ss.length;i++) {
                                        String[] sv = ss[i].split( "=" );
                                        mygame.setOrAddVars( sv[0],Integer.parseInt(sv[1]), game.VAROP.ADD);
                                    }
                                }

                                if (o.getProperties().get( "subvar" )!=null){
                                    String[] ss = o.getProperties().get( "subvar" ).toString().split( "," );
                                    for (int i=0;i<ss.length;i++) {
                                        String[] sv = ss[i].split( "=" );
                                        mygame.setOrAddVars( sv[0],Integer.parseInt(sv[1]), game.VAROP.SUB);
                                    }
                                }

                                if (myobject.objtype!=MONSTER){
                                    if (o.getProperties().get( "keep" )==null) {
                                        myobject.setCategoryFilter( game.DESTROYED_BIT );
                                        mygame.objects.remove( myobject );
                                    }

                                }

                                mygame.playSfx(mygame.sfxkey);

                                if (o.getProperties().get( "load" )!=null){
                                    mygame.load();
                                }

                                if (o.getProperties().get( "message" )!=null){
                                    mygame.msgindex=0;
                                    mygame.briefing=o.getProperties().get( "message" ).toString().split( "//" );
                                    mygame.starting=true;
                                    mygame.player.b2body.setLinearVelocity( 0,0 );
                                }

                                if (o.getProperties().get("transfer")!=null) {
                                    if (o.getProperties().get( "transfer" ).toString().equalsIgnoreCase( "" )) {
                                    } else {
                                        mygame.bgm.stop();
                                        mygame.loadingmap = true;
                                        mygame.initialise( mygame.path, o.getProperties().get( "transfer" ).toString() );

                                    }
                                }

                                if (o.getProperties().get("move")!=null) {

                                    Gdx.app.postRunnable( new Runnable() {

                                        @Override
                                        public void run() {
                                            String[] xy = o.getProperties().get( "move" ).toString().split( "," );

                                            Float px = Float.parseFloat( xy[0] );
                                            Float py = Float.parseFloat( xy[1] );
                                            mygame.player.b2body.setTransform( (px + 8) / 100f, (mygame.Th * mygame.Tsh / 100f) - (py + 8) / 100f, 0 );
                                            mygame.player.b2body.setLinearVelocity( 0, 0 );
                                            //mygame.gc.position.set(mygame.player.b2body.getPosition().x,mygame.player.b2body.getPosition().y,0);
                                        }
                                    } );
                                }

                            }


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

}
