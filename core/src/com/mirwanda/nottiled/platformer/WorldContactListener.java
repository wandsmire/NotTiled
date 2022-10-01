package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;

import static com.mirwanda.nottiled.platformer.game.onlyDigits;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ACTION;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ALLSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BRICK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ENEMYPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEMSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LADDER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.TOUCHSENSOR;

public class WorldContactListener implements ContactListener {

    game mygame;
    public WorldContactListener(game mygame){
        this.mygame = mygame;
    }


    public boolean check(gameobject.objecttype type1, gameobject.objecttype type2, gameobject obj1, gameobject obj2){
        if (obj1.objtype==type1 && obj2.objtype==type2) return true;
        return obj2.objtype == type1 && obj1.objtype == type2;
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
            for (String s : ss) {


                if (s.contains( "=" )) {
                    String[] sv = s.split( "=" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if (Integer.parseInt( sv[1] ) == var.value) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }
                if (s.contains( "!" )) {
                    String[] sv = s.split( "!" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if (Integer.parseInt( sv[1] ) != var.value) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }

                if (s.contains( "&lt;" )) {
                    String[] sv = s.split( "&lt;" );
                    for (KV var : mygame.save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            if (var.value < Integer.parseInt( sv[1] )) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                }
                if (s.contains( "&gt;" )) {
                    String[] sv = s.split( "&gt;" );
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
                for (String s : ss) {
                    String[] sv = s.split( "=" );
                    mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.SET );
                }
            }

            if (o.get( "addvar" ) != null) {
                String[] ss = o.get( "addvar" ).toString().split( "," );
                for (String s : ss) {
                    String[] sv = s.split( "=" );
                    mygame.setOrAddVars( sv[0], Integer.parseInt( sv[1] ), game.VAROP.ADD );
                }
            }

            if (o.get( "subvar" ) != null) {
                String[] ss = o.get( "subvar" ).toString().split( "," );
                for (String s : ss) {
                    String[] sv = s.split( "=" );
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

            if (o.containsKey( "stopbgm" )) {
                if (mygame.bgm != null && mygame.bgm.isPlaying()) {
                    mygame.bgm.stop();
                }
            }

            if (o.containsKey( "playbgm" )) {
                if (mygame.bgm != null && !mygame.bgm.isPlaying()) {
                    mygame.bgm.play();
                }
            }

            if (o.containsKey( "changebgm" )) {
                if (mygame.bgm != null && mygame.bgm.isPlaying()) {
                    mygame.bgm.stop();
                }

                String bgms = (String) o.get( "changebgm" );
                if (mygame.getFile( mygame.path + "/" + bgms ).exists() && !bgms.equalsIgnoreCase( "" )) {
                    if (mygame.bgm == null) {
                        mygame.bgm = Gdx.audio.newMusic( mygame.getFile( mygame.path + "/" + bgms ) );
                        mygame.bgm.setLooping( true );
                    } else {
                        if (!mygame.bgm.isPlaying()) {
                            mygame.bgm = Gdx.audio.newMusic( mygame.getFile( mygame.path + "/" + bgms ) );
                            mygame.bgm.setLooping( true );
                        }
                    }
                }
            }

            if (o.containsKey( "setbgm" )) {
                String bgms = (String) o.get( "setbgm" );
                if (mygame.getFile( mygame.path + "/" + bgms ).exists() && !bgms.equalsIgnoreCase( "" )) {
                    if (mygame.bgm == null) {
                        mygame.bgm = Gdx.audio.newMusic( mygame.getFile( mygame.path + "/" + bgms ) );
                        mygame.bgm.setLooping( true );
                    } else {
                        if (!mygame.bgm.isPlaying()) {
                            mygame.bgm = Gdx.audio.newMusic( mygame.getFile( mygame.path + "/" + bgms ) );
                            mygame.bgm.setLooping( true );
                        }
                    }
                }
            }

            if (o.containsKey( "changebackground" )) {
                String bgc = (String) o.get( "changebackground" );
                if (mygame.getFile( mygame.path + "/" + bgc ).exists() && !bgc.equalsIgnoreCase( "" )) {
                    mygame.txBackground = new Texture( mygame.getFile( mygame.path + "/" + bgc ) );
                } else {
                    mygame.txBackground = null;
                }
            }

            if (o.containsKey( "removebackground" )) {
                mygame.txBackground = null;
            }


            if (o.get( "notice" ) != null) {
                mygame.message = o.get( "notice" ).toString();
            }

            if(o.get("alert")!=null){
                //////////
                mygame.msgbox(o.get("alert").toString());
                //////////
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

                    FileHandle tgt = mygame.getFile( mygame.path+"/"+o.get( "transfer" ).toString() );
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

            if (o.containsKey( "moveX")) {
                String tgt = of.get( "moveX" ).toString();
                float px =0;
                float py =0;
                if (tgt.equalsIgnoreCase( "," )) {
                    String[] xy = of.get( "moveX" ).toString().split( "," );
                    px = Float.parseFloat( xy[0] );
                    px = (px + mygame.Tsw/2) /mygame.scale;
                    py = mygame.player.body.getPosition().y;
                }else{
                    for (gameobject go : mygame.objects){
                        if (go.id.equalsIgnoreCase( tgt )){
                            px = go.body.getPosition().x;
                            py = mygame.player.body.getPosition().y;

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

            if (o.containsKey( "moveY")) {
                String tgt = of.get( "moveY" ).toString();
                float px =0;
                float py =0;
                if (tgt.equalsIgnoreCase( "," )) {
                    String[] xy = of.get( "moveY" ).toString().split( "," );
                    py = Float.parseFloat( xy[1] );
                    px = mygame.player.body.getPosition().x;
                    py = (mygame.Th * mygame.Tsh /mygame.scale) - (py + mygame.Tsw/2) /mygame.scale;
                }else{
                    for (gameobject go : mygame.objects){
                        if (go.id.equalsIgnoreCase( tgt )){
                            px = mygame.player.body.getPosition().x;
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

            if (o.containsKey( "kill")) {
                String tgt = of.get( "kill" ).toString();
                for (gameobject go : mygame.objects){
                    if (go.id.equalsIgnoreCase( tgt )){
                        go.setCategoryFilter( game.DESTROYED_BIT);
                        go.state= gameobject.states.DEAD;
                        go.body.setLinearVelocity( 0,0 );
                        go.bumbum();
                        go.playSfx( go.sfxdead );
                    }
                }
            }


            if (o.containsKey( "delayedkill")) {
                String tgt = of.get( "delayedkill" ).toString();
                mygame.requestkill=new ArrayList<>();
                for (gameobject go : mygame.objects){
                    if (go.id.equalsIgnoreCase( tgt )){
                        mygame.requestkill.add( go );
                    }
                }
                mygame.killtimer=1.5f;
            }

            if (o.containsKey( "peek")) {
                mygame.peektarget = of.get( "peek" ).toString();

                if (o.containsKey( "peektime")) {
                    String pt = of.get( "peektime" ).toString();
                    try{
                        mygame.peektimer=Float.parseFloat(pt);
                    }catch(Exception e){
                        mygame.peektimer=3f;

                    }
                }else{
                    mygame.peektimer=3f;
                }
            }

            if (o.containsKey( "cam")) {
                mygame.peektarget = of.get( "cam" ).toString();
                mygame.peektimer=-1f;
            }

            if (o.containsKey( "transfercam")) {
                mygame.transfercam = of.get( "transfercam" ).toString();
            }

            if (o.containsKey( "return")) {
                mygame.peektarget=null;
            }


            if (o.get( "sethud" )!=null) {
                boolean keep = o.get( "keephud" ) != null;

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

            if (o.containsKey( "enabletouch" )){
                mygame.enabletouch = true;
            }
            if (o.containsKey( "disabletouch" )){
                mygame.enabletouch = false;
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


            if (o.containsKey( "camlockx" )){
                mygame.camlockx = true;
            }

            if (o.containsKey( "camlocky" )){
                mygame.camlocky = true;
            }

            if (o.containsKey( "camreleasex" )){
                mygame.camlockx = false;
            }

            if (o.containsKey( "camreleasey" )){
                mygame.camlocky = false;
            }

            if (o.containsKey( "resettimer" )){
                mygame.timer = 0f;
            }

            if (o.containsKey( "starttimer" )){
                mygame.timetrial = true;
            }

            if (o.containsKey( "stoptimer" )){
                mygame.timetrial = false;
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

                        newbrick.sfx = mygame.addSfx(mygame.path + "/" + sfx);
                }

                String act = null;
                if (o.containsKey( "action" )){
                    act = o.get( "action" ).toString();
                }
                if (act==null){
                    act = o.get( "setaction" ).toString();
                }

                if (o.containsKey( "equipjetpack" )){
                    String pnm=o.get( "equipjetpack" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "equipjetpack" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "equipjetpack" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "usejetpack" )){
                    String pnm=o.get( "usejetpack" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "usejetpack" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "usejetpack" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "equipgun" )){
                    String pnm=o.get( "equipgun" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "equipgun" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "equipgun" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "usegun" )){
                    String pnm=o.get( "usegun" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "usegun" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "usegun" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "equipgun+jetpack" ) && mygame.checkvars( "gun&gt;0,jetpack&gt;0" )){
                    String pnm=o.get( "equipgun+jetpack" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "equipgun+jetpack" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "equipgun+jetpack" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "usegun+jetpack" ) && mygame.checkvars( "gun&gt;0,jetpack&gt;0" )){
                    String pnm=o.get( "usegun+jetpack" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "usegun+jetpack" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "usegun+jetpack" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "climb" )){
                    String pnm=o.get( "climb" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "climb" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "climb" );
                                    break;
                                }
                            }
                        }
                    }
                }

                if (o.containsKey( "climb+jetpack" )){
                    String pnm=o.get( "climb+jetpack" ).toString();
                    if (onlyDigits( pnm,pnm.length() )) {
                        int animID = Integer.parseInt( o.get( "climb+jetpack" ).toString() );
                        if (mygame.animids.size() > 0) {
                            for (int i = 0; i < mygame.animids.size(); i++) {
                                if (mygame.animids.get( i ) == animID) {
                                    mygame.player.anim.add( mygame.anims.get( i ) );
                                    mygame.player.animID.add( "climb+jetpack" );
                                    break;
                                }
                            }
                        }
                    }
                }

                switch (act){
                    case "jump":
                        newbrick.action = gameobject.actions.JUMP;
                        newbrick.impulse = (o.containsKey( "impulse" )) ? Float.parseFloat( o.get( "impulse" ).toString() ) : 3f;
                        newbrick.pcooldown = (o.containsKey( "cooldown" )) ? Float.parseFloat( o.get( "cooldown" ).toString() ) : 0.8f;
                        Actor a = new Actor();

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
                        newbrick.pdeadanim = (o.containsKey( "pdeadanim" )) ? Integer.parseInt( o.get( "pdeadanim" ).toString() ) : 0;
                        Texture txMonster;
                            try {
                                String anim = o.get( "panim" ).toString();


                                txMonster = new Texture( mygame.getFile( mygame.path + "/" + anim ) );
                            } catch (Exception f) {
                                txMonster = new Texture( Gdx.files.internal( "platformer/shoot.png" ) );
                            }

                            TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                    txMonster.getWidth() / 4,
                                    txMonster.getHeight() / 4 );
                            newbrick.pimagesize = new Vector2( txMonster.getWidth() / 4f, txMonster.getHeight() / 4f );
                            for (int i = 0; i < 4; i++) {
                                TextureRegion[] walkFrames = new TextureRegion[4];
                                int index = 0;
                                for (int j = 0; j < 4; j++) {
                                    walkFrames[index++] = tmp[i][j];
                                }
                                Animation<TextureRegion> tempAnim = new Animation<>( 0.1f, walkFrames );
                                newbrick.panim.add( tempAnim );
                            }

                        try {
                            int panimID = Integer.parseInt(o.get( "panim" ).toString());
                            if (mygame.animids.size()>0){
                                for (int i=0; i< mygame.animids.size();i++){
                                    if (mygame.animids.get( i )==panimID){
                                        newbrick.panim.clear();
                                        newbrick.panim.add(mygame.anims.get( i ));
                                        break;
                                    }
                                }
                            }

                        }catch(Exception ignored) {}



                            break;
                }

                int iconx=-1;
                int icony=-1;

                if (o.get( "icon" ) !=null) {
                    String[] ss = o.get( "icon" ).toString().split( "," );
                    iconx=Integer.parseInt( ss[1]);
                    icony=Integer.parseInt( ss[0]);
                }


                if (o.containsKey( "slot" )){
                    String slots = o.get( "slot" ).toString();
                    String[] sso = slots.split( "" );
                    for (String sr: sso){
                        switch (sr){
                            case "1":
                                newbrick.slot=1;
                                mygame.action1=newbrick;
                                mygame.slot1.removeActor( mygame.islot1  );
                                if (act.equalsIgnoreCase( "none" )) break;
                                if (iconx ==-1 && icony ==-1) break;
                                if (mygame.icons==null) break;
                                mygame.islot1 = new Image();
                                mygame.islot1 .setDrawable(new SpriteDrawable(new Sprite(mygame.icons[iconx][icony])));
                                mygame.islot1 .setScale( 10f );
                                mygame.slot1.add(mygame.islot1 ).padTop( 190f ).padLeft( -175f );
                                break;
                            case "2":
                                newbrick.slot=2;
                                mygame.action2=newbrick;
                                mygame.slot2.removeActor( mygame.islot2  );
                                if (act.equalsIgnoreCase( "none" )) break;
                                if (iconx ==-1 && icony ==-1) break;
                                if (mygame.icons==null) break;

                                mygame.islot2 = new Image();
                                mygame.islot2 .setDrawable(new SpriteDrawable(new Sprite(mygame.icons[iconx][icony])));
                                mygame.islot2 .setScale( 10f );
                                mygame.slot2.add(mygame.islot2 ).padTop( 190f ).padLeft( -175f );
                                break;

                            case "3":
                                newbrick.slot=3;
                                mygame.action3=newbrick;
                                mygame.slot3.removeActor( mygame.islot3  );
                                if (act.equalsIgnoreCase( "none" )) break;
                                if (iconx ==-1 && icony ==-1) break;
                                if (mygame.icons==null) break;

                                mygame.islot3 = new Image();
                                mygame.islot3 .setDrawable(new SpriteDrawable(new Sprite(mygame.icons[iconx][icony])));
                                mygame.islot3 .setScale( 10f );
                                mygame.slot3.add(mygame.islot3 ).padTop( 190f ).padLeft( -175f );

                                break;
                            case "4":
                                newbrick.slot=4;
                                mygame.action4=newbrick;
                                mygame.slot4.removeActor( mygame.islot4  );
                                if (act.equalsIgnoreCase( "none" )) break;
                                if (iconx ==-1 && icony ==-1) break;
                                if (mygame.icons==null) break;

                                mygame.islot4 = new Image();
                                mygame.islot4 .setDrawable(new SpriteDrawable(new Sprite(mygame.icons[iconx][icony])));
                                mygame.islot4 .setScale( 10f );
                                mygame.slot4.add(mygame.islot4 ).padTop( 190f ).padLeft( -175f );

                                break;
                        }

                    }

                }

               // mygame.loadTouchpad();

            }

            if (o.containsKey( "once" )) {
                myobject.bumbum();
                myobject.state = gameobject.states.DEAD;
                //myobject.setCategoryFilter( game.DESTROYED_BIT );
                //mygame.objects.remove( myobject );
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
            pp.setCategoryFilter( game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;
        }

        if (check(BLOCK,PLAYERPROJECTILE,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject pp = select( PLAYERPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            pp.bumbum();
            bl.HP-=pp.damage;
            pp.body.setLinearVelocity( 0,0 );
            pp.setCategoryFilter( game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;

        }

        if (check(BRICK,PLAYERPROJECTILE,o1,o2)){
            //gameobject bl = select( BLOCK,o1,o2 );
            gameobject pp = select( PLAYERPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            pp.bumbum();
            pp.body.setLinearVelocity( 0,0 );
            pp.setCategoryFilter( game.DESTROYED_BIT);
            pp.state= gameobject.states.DEAD;

        }

        if (check(BLOCK,ENEMYPROJECTILE,o1,o2)){
            gameobject bl = select( BLOCK,o1,o2 );
            gameobject ep = select( ENEMYPROJECTILE,o1,o2 );
            //Gdx.app.log( bl.HP+"",pp.damage+"");
            ep.bumbum();

            bl.HP-=ep.damage;
            ep.body.setLinearVelocity( 0,0 );
            ep.setCategoryFilter( game.DESTROYED_BIT);
            ep.state= gameobject.states.DEAD;

        }

        if (check(BRICK,ENEMYPROJECTILE,o1,o2)) {
            //gameobject bl = select( BRICK, o1, o2 );
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
            ep.setCategoryFilter( game.DESTROYED_BIT);
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

        if (check(ITEM, gameobject.objecttype.ITEMSENSOR,o1,o2)){
            //gameobject it = select( ITEM,o1,o2 );
            gameobject is = select( ITEMSENSOR,o1,o2 );
            eventobject(is);
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

        if (check(ALLSENSOR,BRICK,o1,o2)){
            gameobject cp = select( BRICK,o1,o2 );
            cp.state = gameobject.states.DEAD;
        }
        if (check(ALLSENSOR,ITEM,o1,o2)){
            gameobject cp = select( ITEM,o1,o2 );
            cp.state = gameobject.states.DEAD;
        }
        if (check(ALLSENSOR,BLOCK,o1,o2)){
            gameobject cp = select( BLOCK,o1,o2 );
            cp.state = gameobject.states.DEAD;
        }
        if (check(ALLSENSOR,LADDER,o1,o2)){
            gameobject cp = select( LADDER,o1,o2 );
            cp.state = gameobject.states.DEAD;
        }
        if (check(ALLSENSOR,MONSTER,o1,o2)){
            gameobject cp = select( MONSTER,o1,o2 );
            cp.state = gameobject.states.DEAD;
        }

        if (check(TOUCHSENSOR,ITEM,o1,o2)){
            gameobject cp = select( ITEM,o1,o2 );
            eventobject(cp);
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
