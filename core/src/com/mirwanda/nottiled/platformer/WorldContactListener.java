package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ACTION;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BRICK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMY;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMYPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LADDER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.WALLCENTER;

public class WorldContactListener implements ContactListener {

    game mygame;
    public WorldContactListener(game mygame){
        this.mygame = mygame;
    }


    public boolean check(gameobject.objecttype type1, gameobject.objecttype type2, gameobject obj1, gameobject obj2){
        if (obj1.objtype==type1 && obj2.objtype==type2) return true;
        if (obj2.objtype==type1 && obj1.objtype==type2) return true;
        return false;
    }

    public gameobject select(gameobject.objecttype type1, gameobject obj1, gameobject obj2){
        if (obj1.objtype==type1) return obj1;
        if (obj2.objtype==type1 ) return obj2;
        return null;
    }

    public void eventobject(gameobject myobject){
        MapProperties o=null;
        if (myobject.obj !=null) o = myobject.obj.getProperties();
        if (myobject.tlcece !=null) o = myobject.tlcece.getProperties();
        final MapProperties of = o;
        //if (myobject.obj==null) return;
        //final MapObject o = myobject.obj;
        boolean qual=true;
        if (o.get( "condition" )!=null){
            String[] ss = o.get( "condition" ).toString().split( "," );
            qual=false;
            int rq=0;
            for (int i=0;i<ss.length;i++) {

                if (ss[i].contains( "=" )) {
                    String[] sv = ss[i].split( "=" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if (Integer.parseInt( sv[1] ) == var.value) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }
                if (ss[i].contains( "&lt;" )) {
                    String[] sv = ss[i].split( "&lt;" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if ( var.value < Integer.parseInt( sv[1] )) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }
                if (ss[i].contains( "&gt;" )) {
                    String[] sv = ss[i].split( "&gt;" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if (var.value > Integer.parseInt( sv[1] )) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }
            }
            if (rq==ss.length) qual=true;
        }
        if (qual) {

            if (o.get( "setvar" ) != null) {
                String[] ss = o.get( "setvar" ).toString().split( "," );
                for (int i = 0; i < ss.length; i++) {
                    String[] sv = ss[i].split( "=" );
                    mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SET );
                }
            }

            if (o.get( "addvar" ) != null) {
                String[] ss = o.get( "addvar" ).toString().split( "," );
                for (int i = 0; i < ss.length; i++) {
                    String[] sv = ss[i].split( "=" );
                    mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.ADD );
                }
            }

            if (o.get( "subvar" ) != null) {
                String[] ss = o.get( "subvar" ).toString().split( "," );
                for (int i = 0; i < ss.length; i++) {
                    String[] sv = ss[i].split( "=" );
                    mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SUB );
                }
            }

            if (o.get( "sfx" ) != null) {
                myobject.playSfx( myobject.sfx );
            }

            if (o.get( "load" ) != null) {
                mygame.load();
            }

            if (o.get( "save" ) != null) {
                mygame.save();
            }

            if (o.get( "message" ) != null) {
                mygame.msgindex = 0;
                String mbr = mygame.replaceVars(o.get( "message" ).toString());
                mygame.briefing = mbr.split( "//" );
                mygame.starting = true;
                mygame.player.body.setLinearVelocity( 0, 0 );
            }

            if (o.get( "restoreHP" ) != null) {
                mygame.player.HP=mygame.player.maxHP;
            }

            if (o.get( "increaseMaxHP" ) != null) {
                mygame.player.maxHP+=Float.parseFloat(o.get( "increaseMaxHP" ).toString());
            }

            if (o.get( "transfer" ) != null) {

                if (o.get( "transfer" ).toString().equalsIgnoreCase( "" )) {
                    mygame.fadeinitialise( mygame.path,mygame.file );
                } else {
                    if (mygame.bgm!=null) {
                        if (!mygame.file.equalsIgnoreCase( o.get( "transfer" ).toString() ))  {
                            mygame.bgm.stop();
                        }
                    }

                    FileHandle tgt = Gdx.files.absolute( mygame.path+o.get( "transfer" ).toString() );
                    if (tgt.exists()){
                        mygame.fadeinitialise( mygame.path, o.get( "transfer" ).toString() );
                    }else{
                        mygame.fadeinitialise( mygame.path,mygame.file );
                    }

                }
            }

            if (o.containsKey( "move")) {
                String tgt = of.get( "move" ).toString();
                float px =0;
                float py =0;
                if (tgt.equalsIgnoreCase( "," )) {
                    String[] xy = of.get( "move" ).toString().split( "," );
                    px = Float.parseFloat( xy[0] );
                    py = Float.parseFloat( xy[1] );
                    px = (px + mygame.Tsw/2) /mygame.scale;
                    py = (mygame.Th * mygame.Tsh /mygame.scale) - (py + mygame.Tsw/2) /mygame.scale;
                }else{
                    for (gameobject go : mygame.objects){
                        if (go.id.equalsIgnoreCase( tgt )){
                            px = go.body.getPosition().x;
                            py = go.body.getPosition().y;

                        }
                    }
                }
                if (o.containsKey( "transfer" )) {
                    mygame.move = new Vector2( px, py );
                }else{
                    //box2d is locked when contact.
                    mygame.requesttransform( px,py );

                }
            }


            if (o.get( "sethud" )!=null) {
                boolean keep =  (o.get( "keephud" ) !=null) ? true: false;

                if (o.get( "icon" ) !=null) {
                    String[] ss = o.get( "icon" ).toString().split( "," );
                    mygame.setHUD(o.get( "sethud" ).toString(), keep, true, Integer.parseInt(ss[0]),Integer.parseInt(ss[1]));

                }else{
                    mygame.setHUD(o.get( "sethud" ).toString(), keep, false,0,0);
                }

            }

            if (o.containsKey( "disabledpad" )){
                mygame.disabledpad = true;
            }
            if (o.containsKey( "enabledpad" )){
                mygame.disabledpad = false;
            }

            if (o.containsKey( "disableXaxis" )){
                mygame.disableXaxis = true;
            }
            if (o.containsKey( "enabledXaxis" )){
                mygame.disableXaxis = false;
            }

            if (o.containsKey( "disableYaxis" )){
                mygame.disableYaxis = true;
            }
            if (o.containsKey( "enableYaxis" )){
                mygame.disableYaxis = false;
            }
            if (o.containsKey( "disablecontrol" )){
                mygame.disablecontrol = true;
            }
            if (o.containsKey( "enablecontrol" )){
                mygame.disablecontrol = false;
            }

            if (o.containsKey( "zoom" )) {
                mygame.zoom = Float.parseFloat( o.get( "zoom" ).toString() );
            }

            if (o.containsKey( "gravity" )) {
                mygame.world.setGravity( new Vector2(0, -Float.parseFloat( o.get( "gravity" ).toString()) ));
            }


            if (o.get( "setaction" ) != null) {
                gameobject newbrick = new gameobject();
                newbrick.mygame = mygame;
                newbrick.objtype= ACTION;
                newbrick.bindvar = (o.containsKey( "bindvar" )) ?  o.get( "bindvar" ).toString() : null;
                newbrick.name = (o.containsKey( "label" )) ?  o.get( "label" ).toString() : "";

                if (o.containsKey( "asfx" )) {
                    String sfx = o.get("asfx").toString();
                    if (mygame.getFile( mygame.path + "/" + sfx ).exists())
                        newbrick.sfx = Gdx.audio.newSound( mygame.getFile( mygame.path + "/" + sfx ) );
                }

                String act = null;
                if (o.containsKey( "action" )){
                    act = o.get( "action" ).toString();
                }
                if (act==null){
                    act = o.get( "setaction" ).toString();
                }
                switch (act){
                    case "jump":
                        newbrick.action = gameobject.actions.JUMP;
                        newbrick.impulse = (o.containsKey( "impulse" )) ? Float.parseFloat( o.get( "impulse" ).toString() ) : 3f;
                        newbrick.pcooldown = (o.containsKey( "cooldown" )) ? Float.parseFloat( o.get( "cooldown" ).toString() ) : 0.8f;

                        break;
                    case "jetpack":
                        newbrick.action = gameobject.actions.JETPACK;
                        newbrick.impulse = (o.containsKey( "impulse" )) ? Float.parseFloat( o.get( "impulse" ).toString() ) : 3f;
                        newbrick.pcooldown = (o.containsKey( "cooldown" )) ? Float.parseFloat( o.get( "cooldown" ).toString() ) : 0.8f;

                        break;

                    case "dash":
                        newbrick.action = gameobject.actions.DASH;
                        newbrick.impulse = (o.containsKey( "impulse" )) ? Float.parseFloat( o.get( "impulse" ).toString() ) : 1;
                        newbrick.pcooldown = (o.containsKey( "cooldown" )) ? Float.parseFloat( o.get( "cooldown" ).toString() ) : 1;
                        break;
                    case "none":
                        newbrick.action = gameobject.actions.NONE;

                        break;
                    case "shoot":
                        newbrick.action = gameobject.actions.SHOOT;

                        newbrick.pcooldown = (o.containsKey( "cooldown" )) ? Float.parseFloat( o.get( "cooldown" ).toString() ) : 1;
                        newbrick.pspeed = (o.containsKey( "pspeed" )) ? Float.parseFloat( o.get( "pspeed" ).toString() ) : 4;
                        newbrick.pmaxdistance = (o.containsKey( "pmaxdistance" )) ? Integer.parseInt( o.get( "pmaxdistance" ).toString() ) : 300;
                        newbrick.pdamage = (o.containsKey( "pdamage" )) ? Integer.parseInt( o.get( "pdamage" ).toString() ) : 1;

                        Texture txMonster;
                        try{
                            String anim = o.get( "panim" ).toString();


                                txMonster = new Texture( mygame.getFile( mygame.path + "/" + anim ) );
                            }catch(Exception e){
                                txMonster = new Texture(Gdx.files.internal( "platformer/shoot.png" ));
                            }

                            TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                    txMonster.getWidth() / 4,
                                    txMonster.getHeight() / 4 );
                            newbrick.pimagesize=new Vector2(txMonster.getWidth()/4,txMonster.getHeight() / 4);
                            mygame.log("NOGHE"+newbrick.pimagesize);
                            for (int i = 0; i < 4; i++) {
                                TextureRegion[] walkFrames = new TextureRegion[4];
                                int index = 0;
                                for (int j = 0; j < 4; j++) {
                                    walkFrames[index++] = tmp[i][j];
                                }
                                Animation<TextureRegion> tempAnim = new Animation<TextureRegion>( 0.1f, walkFrames );
                                newbrick.panim.add( tempAnim );
                            }


                        break;
                }
                switch (o.get( "slot" ).toString()){
                    case "1":
                        mygame.action1=newbrick;
                        break;
                    case "2":
                        mygame.action2=newbrick;
                        break;
                    case "3":
                        mygame.action3=newbrick;
                        break;
                    case "4":
                        mygame.action4=newbrick;
                        break;
                }

            }

            if (o.get( "once" ) != null) {
                myobject.setCategoryFilter( game.DESTROYED_BIT );
                mygame.objects.remove( myobject );
            }

            if (o.get( "bgcolor" ) != null) {
                mygame.bgcolor=myobject.bgcolor;
            }

        }else{ //!qual
            if (o.get( "premessage" ) != null) {
                mygame.msgindex = 0;
                String mbr = mygame.replaceVars(o.get( "premessage" ).toString());
                mygame.briefing = mbr.split( "//" );
                mygame.starting = true;
                mygame.player.body.setLinearVelocity( 0, 0 );
            }

        }

        }
    @Override
    public void beginContact(Contact contact) {
        gameobject o1 = (gameobject) contact.getFixtureA().getUserData();
        gameobject o2 = (gameobject) contact.getFixtureB().getUserData();

        if (check(PLAYER,CHECKPOINT,o1,o2)){
            gameobject cp = select( CHECKPOINT,o1,o2 );
            cp.playSfx(cp.sfx );
            mygame.checkpoint.set(cp.body.getPosition().x,cp.body.getPosition().y);
            mygame.save();
        }

        if (check(MONSTER,PLAYERPROJECTILE,o1,o2)){
            gameobject mo = select( MONSTER,o1,o2 );
            gameobject pp = select( PLAYERPROJECTILE,o1,o2 );
            pp.bumbum();
            mo.playSfx( mo.sfx );
            mo.HP-=pp.damage;
            pp.setCategoryFilter(game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;
        }

        if (check(BLOCK,PLAYERPROJECTILE,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject pp = select( PLAYERPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            pp.bumbum();
            bl.HP-=pp.damage;
            pp.body.setLinearVelocity( 0,0 );
            pp.setCategoryFilter(game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;

        }

        if (check(BRICK,PLAYERPROJECTILE,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject pp = select( PLAYERPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            pp.bumbum();
            pp.body.setLinearVelocity( 0,0 );
            pp.setCategoryFilter(game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;

        }

        if (check(BLOCK,ENEMYPROJECTILE,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject ep = select( ENEMYPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            ep.bumbum();

            bl.HP-=ep.damage;
            ep.body.setLinearVelocity( 0,0 );
            ep.setCategoryFilter(game.DESTROYED_BIT);
            ep.state= gameobject.states.DEAD;

        }

        if (check(BRICK,ENEMYPROJECTILE,o1,o2)) {
            gameobject bl = select( BRICK, o1, o2 );
            gameobject ep = select( ENEMYPROJECTILE, o1, o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            ep.bumbum();

            ep.body.setLinearVelocity( 0, 0 );
            ep.setCategoryFilter( game.DESTROYED_BIT );
            ep.state = gameobject.states.DEAD;

        }
            if (check(PLAYER,ENEMYPROJECTILE,o1,o2)){
            gameobject ep = select( ENEMYPROJECTILE,o1,o2 );
            gameobject pl = select( PLAYER,o1,o2 );
            ep.bumbum();
            pl.playSfx( pl.sfx );
            pl.HP-=ep.damage;
            ep.setCategoryFilter(game.DESTROYED_BIT);
            ep.state= gameobject.states.DEAD;

        }

        if (check(PLAYER,MONSTER,o1,o2)){
            gameobject en = select( MONSTER,o1,o2 );
            gameobject pl = select( PLAYER,o1,o2 );
            //pl.bumbum();
            pl.HP-=en.damage;
            eventobject(en);
            if (en.damage>0) mygame.recoil=true;
        }

        if (check(PLAYER,ITEM,o1,o2)){
            gameobject it = select( ITEM,o1,o2 );
            gameobject pl = select( PLAYER,o1,o2 );
            pl.HP-=it.damage;
            eventobject(it);
        }

        if (check(PLAYER,BLOCK,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject pl = select( PLAYER,o1,o2 );
            pl.HP-=bl.damage;
            eventobject(bl);
        }

        if (check(PLAYER,LADDER,o1,o2)){
            mygame.touchedladder+=1;
        }

        // if (myobject.objtype==PLAYER && myobject2.objtype==BRICK)
        /*
            if (fixA.getUserData()!=null && gameobject.class.isAssignableFrom(fixA.getUserData().getClass()) && fixB.getUserData()!=null && gameobject.class.isAssignableFrom(fixB.getUserData().getClass()))
            {

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



                        }

                            break;
                        case GEAR:
                            //myobject.setCategoryFilter(game.DESTROYED_BIT);
                            if (myobject2.objtype ==MONSTER) {
                                myobject2.setCategoryFilter(game.DESTROYED_BIT);
                                mygame.objects.remove(myobject2);
                                //mygame.playSfx(mygame.sfxmonster);
                            }
                            break;

                    }

                    ////////////?REVERSE
                switch (myobject2.objtype){
                    case PLATFORMS:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);


                        break;
                    case MONSTER:


                        if (myobject.objtype ==gameobject.objecttype.SWITCH) {
                            TextureRegion regon = null;
                            TextureRegion regoff = null;
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
                           // mygame.playSfx(mygame.sfxswitch);



                        }

                        break;
                    case GEAR:
                        //myobject.setCategoryFilter(game.DESTROYED_BIT);
                        if (myobject.objtype ==MONSTER) {
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                           // mygame.playSfx(mygame.sfxmonster);
                        }
                        break;

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

                            break;
                        case SPRING:
                            if (mygame.player.body.getLinearVelocity().y<0) {
                                mygame.player.body.setLinearVelocity(0, 4);
                               // mygame.playSfx(mygame.sfxspring);
                                myobject.playSfx(myobject.sfx );

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
                            //mygame.playSfx(mygame.sfxcoin);
                            myobject.playSfx(myobject.sfx );

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

                            break;

                        case MONSTER:
                            mygame.HP -= myobject.damage;

                        case ITEM: case BLOCK:

                            }


                            break;
                        case LOCK:
                            if (mygame.key <1) return;
                            mygame.key-=1;
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            //mygame.playSfx(mygame.sfxlock);
                            break;
                        case BREAKABLE:

                            mygame.player.body.setLinearVelocity(0,2);
                            myobject.setCategoryFilter(game.DESTROYED_BIT);
                            mygame.objects.remove(myobject);
                            break;

                        case GIRL:
                            if (mygame.coin==0) {
                                mygame.victory = true;
                                mygame.player.body.setLinearVelocity(0, 0);
                                //mygame.playSfx(mygame.sfxgirl);
                            }

                            break;

                    }
                }
            }

        }

         */
    }



    @Override
    public void endContact(Contact contact) {
        gameobject o1 = (gameobject) contact.getFixtureA().getUserData();
        gameobject o2 = (gameobject) contact.getFixtureB().getUserData();

        if (check(PLAYER,LADDER,o1,o2)){
            mygame.touchedladder-=1;
        }


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
