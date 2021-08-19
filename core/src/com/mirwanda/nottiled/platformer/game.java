package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEMSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LISTENER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.states.DEAD;


public class game {
    public boolean debugmode = false;
    public boolean playtest=true;
    public boolean uitest=false;

    public boolean night=false;
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;
    public boolean loadingmap;
    public boolean rpg=false;
    public Vector2 move;

    public WorldContactListener mycontactlistener;
    public savegame save = new savegame();

    public Vector2 checkpoint = new Vector2();
    public gameobject player;
    public gameobject action1;
    public gameobject action2;
    public gameobject action3;
    public gameobject action4;

    public java.util.List<gameobject> objects = new ArrayList<gameobject>();
    public java.util.List<ParticleEffect> particles = new ArrayList<>();

    public Texture txBackground;
    public TextureRegion[][] hpbar;
    public TextureRegion[][] icons;

    //SOUND
    public Music bgm;

    public int key;
    public int coin;
    public int dead;
    public String nextlevel;
    public String[] briefing;
    public String debriefing;
    public String died;

    public boolean victory;
    public boolean starting;
    public boolean jumping = false;
    public boolean ladder;
    public int touchedladder;
    public boolean floater;
    public int touchedfloater;
    public boolean sinker;
    public boolean onplatformh;
    public boolean onplatformv;
    public gameobject currentplatform;
    public int msgindex=0;
    public List<gameobject> requestkill;
    public gameobject peektarget;

    public int touchedsinker;

    public float jumpinterval=0;
    public float stompinterval=0;
    public float fade=0;
    public float fadein=0;
    public float fadeinmax=20;
    public float fadeout=0;
    public float fadeoutmax=10;

    float stateTime, playerTime;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short MARKER_BIT = 32;
    public static final short PLATFORM_BIT = 64;
    public static final short PLAYERPROJECTILE_BIT = 128;
    public static final short ENEMYPROJECTILE_BIT = 256;
    public static final short ITEMSENSOR_BIT = 512;
    public int Tw;
    public int Th;
    public float Tsw;
    public float Tsh;
    public RayHandler rayHandler;
    public float scale=100f;
    public Color bgcolor=Color.LIGHT_GRAY;

    public boolean initialise(String path, String filename){
        try {
            loadingmap = true;
            this.path = path;
            this.file = filename;
            fade = 10;
            rpg = false;
            particles.clear();


            Texture tmp = new Texture( Gdx.files.internal( "platformer/hpbar.png" ) );
            hpbar = TextureRegion.split( tmp,
                    tmp.getWidth(),
                    tmp.getHeight() / 2 );

            objects = new ArrayList<gameobject>();

            try {
                if (playtest) {
                    map = new TmxMapLoader( new AbsoluteFileHandleResolver() ).load( path + "/" + filename );
                } else {
                    map = new TmxMapLoader( new InternalFileHandleResolver() ).load( path + "/" + filename );

                }
            } catch (Exception e) {
                return false;
            }
            renderer = new OrthogonalTiledMapRenderer( map );

            Box2D.init();
            world = new World( new Vector2( 0, -10 ), true );

            b2dr = new Box2DDebugRenderer();

            mycontactlistener = new WorldContactListener( this );
            world.setContactListener( mycontactlistener );


            stateTime = 0f;
            playerTime = 0f;

            ////

            MapProperties mpa = map.getProperties();
            if (mpa != null) {

                if (mpa.containsKey( "type" )) {
                    String type = (String) mpa.get( "type" );
                    if (type.equalsIgnoreCase( "NotTiled platformer" )) {
                        rpg = false;
                    }
                    if (type.equalsIgnoreCase( "NotTiled rpg" )) {
                        rpg = true;
                        world.setGravity( new Vector2( 0f, 0f ) );
                        jumping = false;
                        rpg = true;
                    }
                } else {
                    rpg = true;
                }

                if (mpa.containsKey( "scale" )) {
                    try {
                        String scl = (String) mpa.get( "scale" );
                        scale = Float.parseFloat( scl );
                    }catch(Exception f){
                        scale=100f;
                    }
                }

                if (mpa.containsKey( "background" )) {
                    String bgc = (String) mpa.get( "background" );
                    if (getFile( path + "/" + bgc ).exists() && !bgc.equalsIgnoreCase( "" )) {
                        txBackground = new Texture( getFile( path + "/" + bgc ) );
                    } else {
                        txBackground = null;
                    }
                }

                if (mpa.containsKey( "bgm" )) {
                    String bgms = (String) mpa.get( "bgm" );
                    if (getFile( path + "/" + bgms ).exists() && !bgms.equalsIgnoreCase( "" )) {
                        if (bgm == null) {
                            bgm = Gdx.audio.newMusic( getFile( path + "/" + bgms ) );
                            bgm.setLooping( true );
                        } else {
                            if (!bgm.isPlaying()) {
                                bgm = Gdx.audio.newMusic( getFile( path + "/" + bgms ) );
                                bgm.setLooping( true );
                            }
                        }
                    }
                }

                boolean customicons = false;
                if (mpa.containsKey( "icons" )) {
                    String ico = (String) mpa.get( "icons" );
                    if (getFile( path + "/" + ico ).exists() && !ico.equalsIgnoreCase( "" )) {

                        if (mpa.containsKey( "iconsize" )) {
                            String isz = (String) mpa.get( "iconsize" );
                            try {
                                String isza[] = isz.split( "," );
                                int isx = Integer.parseInt( isza[0] );
                                int isy = Integer.parseInt( isza[1] );
                                tmp = new Texture( Gdx.files.absolute( path + "/" + ico ) );
                                icons = TextureRegion.split( tmp,
                                        tmp.getWidth() / isx,
                                        tmp.getHeight() / isy );
                                customicons=true;

                            }catch (Exception e){
                                customicons=false;
                            }
                        }else{
                            customicons=false;
                        }
                    } else {
                        customicons=false;
                    }
                }

                if (!customicons){
                    tmp = new Texture( Gdx.files.internal( "platformer/icons.png" ) );
                    icons = TextureRegion.split( tmp,
                            tmp.getWidth() / 16,
                            tmp.getHeight() / 19 );

                }


                nextlevel = (String) mpa.get( "nextlevel" );
                debriefing = (String) mpa.get( "debriefing" );
                died = "GAME OVER";
            }
            ////

            log( "ROTATING..." );

            for (MapLayer mlayer : map.getLayers()) {
                boolean tilelayer = false;
                boolean imagelayer = false;
                boolean objectlayer = false;
                float opacity = mlayer.getOpacity();

                log( mlayer.getName() + tilelayer + imagelayer + objectlayer );

                boolean visible = mlayer.isVisible();
                try {
                    TiledMapTileLayer tlayer = (TiledMapTileLayer) mlayer;
                    tilelayer = true;
                } catch (Exception e) {
                    try {
                        TiledMapImageLayer tlayer = (TiledMapImageLayer) mlayer;
                        imagelayer = true;
                    } catch (Exception f) {
                        objectlayer = true;
                    }
                }


                if (tilelayer) {
                    if (mlayer.isVisible() == false) continue;

                    boolean over = false;
                    if (mlayer.getName().contains( "*" )) {
                        over = true;
                    }
                    TiledMapTileLayer tlayer = (TiledMapTileLayer) mlayer;
                    this.Tw = tlayer.getWidth();
                    this.Th = tlayer.getHeight();
                    this.Tsw = tlayer.getTileWidth();
                    this.Tsh = tlayer.getTileHeight();
                    for (int yy = 0; yy < tlayer.getHeight(); yy++) {
                        for (int xx = 0; xx < tlayer.getWidth(); xx++) {
                            TiledMapTileLayer.Cell cece = tlayer.getCell( xx, yy );
                            float ww = 0;
                            float hh = 0;
                            try {
                                ww = cece.getTile().getTextureRegion().getRegionWidth();
                                hh = cece.getTile().getTextureRegion().getRegionHeight();
                            } catch (Exception e) {
                            }
                            setGameObject( false, cece, xx * Tsw, yy * Tsh, ww, hh, over, null, opacity );
                        }
                    }
                }
                if (objectlayer) {
                    if (mlayer.isVisible() == false) continue;
                    MapObjects objects = map.getLayers().get( mlayer.getName() ).getObjects();
                    for (MapObject o : objects) {
                        Rectangle rect;
                        if (o instanceof TiledMapTileMapObject) {
                            TiledMapTileMapObject obj = (TiledMapTileMapObject) o;
                            setGameObject( true, null, obj.getX(), obj.getY(), obj.getTextureRegion().getRegionWidth(), obj.getTextureRegion().getRegionHeight(), false, o, opacity );

                        } else {
                            RectangleMapObject obj = (RectangleMapObject) o;
                            rect = obj.getRectangle();
                            setGameObject( true, null, rect.x, rect.y, rect.width, rect.height, false, o, opacity );


                        }
                    }

                }
            }

            //no player object
            if (player == null) return false;

            //save(); //'saving on current map makes load useless.
            victory = false;
            loadingmap = false;
            fadein = fadeinmax;
            return true;
        }catch(Exception e){
            return false;
        }



    }

    public void log(String s){
        Gdx.app.log( "LOJ",s );
    }
    public boolean checkQual(MapProperties o){
        boolean qual=true;
        if (o.get( "requires" )!=null){
            String[] ss = o.get( "requires" ).toString().split( "," );
            qual=false;
            int rq=0;
            boolean recheck=true;
            while (recheck) {
                recheck=false;
                for (int i = 0; i < ss.length; i++) {
                    String[] sv = ss[i].split( "=" );
                    //Gdx.app.log( sv[0]+"",sv[1]+"" );
                    boolean ada = false;
                    for (KV var : save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            ada = true;
                            //Gdx.app.log( sv[1]+"",var.value+"" );
                            //var.value
                            if (sv[1].equalsIgnoreCase(var.value+"")) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                    if (!ada) {
                        setOrAddVars( sv[0], 0, VAROP.SET );
                        recheck=true;
                    }

                }
            }
            if (rq==ss.length) qual=true;
        }

        return qual;

    }
    public enum VAROP{SET,ADD, SUB}

    public void setOrAddVars(String key, int value, VAROP v){
        boolean ada = false;
        KV varnya=null;
        for (KV vr: save.vars){
            if (key.equalsIgnoreCase( vr.key )){
                varnya= vr;
                ada=true;
                break;
            }
        }

        if (ada){
            switch (v){
                case SET:
                    varnya.value=value;
                    break;
                case SUB:
                    varnya.value-=value;
                    break;
                case ADD:
                    varnya.value+=value;
                    break;
            }
        }else{
            save.vars.add( new KV(key,value ));
        }
    }

    public void setHUD(String key, boolean keep, boolean hasicon, int iconx, int icony){
        if (key==null) return;
        for (KV vr: save.vars){
            if (key.equalsIgnoreCase( vr.key )){
                vr.hud=true;
                vr.hasicon=hasicon;
                vr.keephud=keep;
                vr.iconx=iconx;
                vr.icony=icony;
            }
        }
    }

    public void checkHUD(){
        for (KV vr: save.vars){
            if (vr.value<=0){
                if (!vr.keephud) vr.hud=false;
            }
        }
    }

    public void drawHUD(SpriteBatch b, BitmapFont str1, int ssx, int ssy){
        int index=0;
        for (KV vr: save.vars){
            if (vr.hud){
                if (vr.hasicon) {
                    try {
                        str1.draw( b, "  :  " + vr.value, 90, ssy - 20 - 60 * index );
                        b.draw( icons[vr.icony][vr.iconx], 50, ssy - 65 - 60 * index, 48, 48 );
                    }catch(Exception e){
                    }
                   // str1.draw( b, vr.value, ssy-10-10*index)
                }else{
                    str1.draw( b, vr.key+" : " + vr.value, 50,ssy-20-60*index);
                }
                index++;
            }
        }
    }
    public float getVar(String key){
        if (key==null) return 0;
        KV varnya=null;
        for (KV vr: save.vars){
            if (key.equalsIgnoreCase( vr.key )){
                return vr.value;
            }
        }
        return 0;
    }

    public String replaceVars(String s){
        String k=s;
        for (KV vr: save.vars){
            k=k.replace( "["+vr.key+"]" ,(int) vr.getValue()+"");
        }

        //k=k.replace( "[*]" ,"0");
        return k;
    }

    public float anima;
    public int animet;
    public float killtimer;
    public float peektimer;
    public int salto=0;
    public boolean recoil;
    public OrthographicCamera gc;
    public float zoom=0.2f;
    public void update(SpriteBatch batch, float delta, OrthographicCamera gamecam) {
        gc = gamecam;
        if (gc.zoom != zoom) gc.zoom = zoom;
        if (!starting && player.state != DEAD) world.step( 1 / 60f, 6, 2 );

        if (player.HP <= 0) {
            killPlayer();
        }
        if (player.HP > player.maxHP) {
            player.HP = player.maxHP;
        }

        if (requestkill!=null){
            killtimer-=delta;
            if (killtimer<=0) {
                for (gameobject go : requestkill){
                    go.setCategoryFilter( game.DESTROYED_BIT );
                    go.state = gameobject.states.DEAD;
                    go.body.setLinearVelocity( 0, 0 );
                    go.bumbum();
                    go.playSfx( go.sfxdead );
                }
                requestkill = null;
            }
        }
        if (recoil){
            salto=360;
            player.body.applyLinearImpulse( player.body.getLinearVelocity().x,3f,player.body.getPosition().x,player.body.getPosition().y,true );
            recoil=false;
        }

        if (!rpg){
            if (world.getGravity().y!=0) gravity = -world.getGravity().y/Math.abs( world.getGravity().y);
            if (jetpack) {
                if (Math.abs(player.body.getLinearVelocity().y)>player.speed){
                    player.body.setLinearVelocity( player.body.getLinearVelocity().x,gravity*player.speed );
                }

                jetpackcooldown -= delta;
                if (jetpackcooldown<=0) jetpack=false;
            }

            if (player.body.getLinearVelocity().x >player.speed && !jumping) {
                player.body.setLinearVelocity( player.speed,player.body.getLinearVelocity().y );
            }
            if (player.body.getLinearVelocity().x <-player.speed && !jumping) {
                player.body.setLinearVelocity( -player.speed,player.body.getLinearVelocity().y );
            }

        }

        checkHUD();
        if (transformrequest) fullfilltransformrequest();

        if (!rpg) {
            if (Math.abs( player.body.getLinearVelocity().y ) > 0f) {
                jumping = true;
                jumpinterval = 0;
            } else {
                jumpinterval += delta;
            }

            if (jumpinterval >= 0.02f) {
                jumping = false; dashed=false;
            }
            stompinterval -= delta;
            if (stompinterval < 0) stompinterval = 0;
        }
        if (player.body.getPosition().y<=-Tsh/scale && player.state != DEAD)
        {
            //playSfx(sfxplayer);
            dead+=1;
            player.state = DEAD;

        }

        if (salto>0) salto-=20;

        if (salto>=0){
            if (player.dir==1){
                player.setRotation( salto );
            }
            else {
                player.setRotation( 360-salto );
            }
        }



        //gamecam.position.set(player.body.getPosition().x,player.body.getPosition().y,0);

        //if (gamecam.position.x >)
        float posex = player.body.getPosition().x;
        float posey = player.body.getPosition().y;

        if (peektarget!=null){
            if (peektimer!=-1) peektimer-=delta;
            posex = peektarget.body.getPosition().x;
            posey = peektarget.body.getPosition().y;
            if (peektimer<=0 && peektimer!=-1){
                peektarget=null;
            }
        }
            if (fade==0) {

                float lerp = 5f;
                Vector3 position = gamecam.position;
                position.x += (posex - position.x) * lerp * delta;
                position.y += (posey - position.y) * lerp * delta;
                position.x = Math.round( position.x * 500 ) / 500f;
                position.y = Math.round( position.y * 500 ) / 500f;
                gamecam.update();
            }


        //log(fadeout+"");
        if (fadeout>0){
            fadeout--;
            if (fadeout == 0) {

                initialise( fadepath,fademapname );
            }
        }


        if (fade>0){
            if (!loadingmap) fade--;
            if (fade == 0) {
                if (move!=null){
                    player.body.setTransform( move.x, move.y, 0 );
                    player.body.setLinearVelocity( 0, 0 );
                    move=null;
                }


                posex = player.body.getPosition().x;
                posey = player.body.getPosition().y;

                Vector3 position = gamecam.position;
                position.x = posex;
                position.y=posey;
                gamecam.update();

            }else{
                return;
            }
        }

        if (fadein>0) {
            fadein--;
            if (fadein==0){
                if (bgm != null) {
                    if (!bgm.isPlaying()) bgm.play();
                }
            }
        }


        stateTime += delta;
        if (starting){
            if (player.anim.size()>0) {
                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( 0, true );
                player.setRegion( currentFramea );
            }

        }

        //moving
        else if (player.moving && (!jumping || onplatformv) && (!ladder && !floater && !sinker))
        {
            playerTime +=delta;
            if (player.anim.size()>0) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
            }else{


                if (!player.dirlocked) {
                    if (rpg) {
                        player.setOriginCenter();
                        switch (player.dir) {
                            case 0: //down
                                player.setRotation( 180 );
                                break;
                            case 1: //right
                                player.setRotation( 270 );
                                break;
                            case 2: //left
                                player.setRotation( 90 );
                                break;
                            case 3: //up
                                player.setRotation( 0 );
                                break;
                        }
                    } else {
                        switch (player.dir) {
                            case 0: //down
                                break;
                            case 1: //right
                                player.setFlip( true, false );
                                break;
                            case 2: //left
                                player.setFlip( false, false );
                                break;
                            case 3: //up
                                break;
                        }
                    }
                }

            }
        }

        //jumping
        else if (jumping && !ladder && !floater && !sinker && !onplatformv) {
            if (player.anim.size()>0) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( 0.5f, true );
                player.setRegion( currentFramea );

            }
            if (rpg) jumping=false;

        }
        else if ((ladder || floater || sinker) && player.moving )
        {
            if (ladder) player.dir=3;

            playerTime +=delta;
            if (player.anim.size()>0) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
            }

        }else
        {
            //idle animation if you will...

            if (player.anim.size()>0) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( 0, true );
                player.setRegion( currentFramea );
            }
        }


        ladder= touchedladder > 0;



        if (floater){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(-0.7f);
        }else if (ladder){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(0f);
        }else if (sinker) {

            player.body.setLinearVelocity( player.body.getLinearVelocity().x, 0 );
            player.body.setGravityScale( 0.7f );
        }else{
            player.body.setGravityScale(1);
        }


        for (int i = objects.size() - 1; i >= 0; i--){
            gameobject go = objects.get( i );
            go.update(delta);
            if (go.state==DEAD){
                //go.myLight.remove( true );
                if (world.getBodyCount()>0) world.destroyBody( go.body );
                objects.remove( go );

            }

        }

        for (gameobject sboxes:objects)
        {
            if (sboxes.over) continue;

            if (sboxes.getTexture()!=null ) sboxes.draw(batch);

        }


        player.update(delta);
        if (player.state != DEAD){
            player.draw(batch);
        }else
        {
            player.body.setActive(false);
            objects.remove( player );
        }

        for (gameobject sboxes:objects)
        {
            if (!sboxes.over) continue;


            if (sboxes.getTexture()!=null ) sboxes.draw(batch);
        }
        //batch.end();


        for (gameobject sboxes:objects) {
            if (sboxes.HP > 0 && sboxes.HP !=sboxes.maxHP) {
                batch.draw( hpbar[1][0], sboxes.getX(), sboxes.getY() + sboxes.getHeight() + 0.01f, sboxes.getWidth(), hpbar[1][0].getRegionHeight() / scale );
                batch.draw( hpbar[0][0], sboxes.getX(), sboxes.getY() + sboxes.getHeight() + 0.01f, sboxes.HP / sboxes.maxHP * sboxes.getWidth(), hpbar[0][0].getRegionHeight() / scale );
            }

           // sboxes.meledak.update( Gdx.graphics.getDeltaTime() );
           // sboxes.meledak.draw( batch );
        }

        for (int i = particles.size() - 1; i >= 0; i--){
            ParticleEffect pe = particles.get(i);
            pe.update( Gdx.graphics.getDeltaTime() );
            pe.draw( batch );

            if (pe.isComplete()) {particles.remove( pe );pe.dispose();}
        }

        if (action1!=null) action1.update( delta );
        if (action2!=null) action2.update( delta );
        if (action3!=null) action3.update( delta );
        if (action4!=null) action4.update( delta );

        if (night) rayHandler.updateAndRender();

    }

    public void playSfx(Sound s){
        if (s!=null) s.play(1.0f);
    }

    public float walkForce=0.5f;


    public void pressup(){
        if (rpg || ladder) player.moving=true;
        player.dir=3;

        if (!rpg) {
            if ((!jumping || onplatformv) && !ladder && !sinker && !floater) {
             //   player.body.applyLinearImpulse( 0f, jumpForce, player.getX(), player.getY(), true );
                jumping = true;
                onplatformv = false;
            }
            if (ladder || floater || sinker) {
                player.body.setLinearVelocity( 0, player.speed/2f );
            }
            if (jetpack) {
                player.body.setLinearVelocity( player.body.getLinearVelocity().x, player.speed/2f );
            }
        }else{
            if (ladder || floater || sinker) {player.body.setLinearVelocity(player.body.getLinearVelocity().x,player.speed/2f);}
                player.body.setLinearVelocity(0,player.speed);

        }
    }

    public void pressdown(){
        if (rpg || ladder) player.moving=true;
        player.dir=0;

        if (!rpg) {
            if (stompinterval == 0 && !ladder && !sinker && !floater) {
                //player.body.applyLinearImpulse( 0f, -stompForce, player.getX(), player.getY(), true );
                stompinterval = 1;
            }
            if (ladder || floater || sinker) {
                player.body.setLinearVelocity( player.body.getLinearVelocity().x, -player.speed/2f );
            }
            if (jetpack) {
                player.body.setLinearVelocity( player.body.getLinearVelocity().x, -player.speed/2f );
            }

        }else{
            if (ladder || floater || sinker) {player.body.setLinearVelocity(player.body.getLinearVelocity().x,-player.speed/2f);}
            player.body.setLinearVelocity(0,-player.speed);


        }

    }



    public void pressleft(){
        player.moving=true;
        player.dir=2;

        if (ladder || floater || sinker) {player.body.setLinearVelocity(-player.speed/2f,player.body.getLinearVelocity().y);}
        if (player.body.getLinearVelocity().x >=-player.speed) {
            if (!rpg){
                player.body.applyLinearImpulse(-walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.body.setLinearVelocity(-player.speed,0);

            }
        }else{
            if (!jumping) player.body.setLinearVelocity(-player.speed,player.body.getLinearVelocity().y);
        }
    }
    public void pressright(){
        player.moving=true;
        player.dir=1;
        if (ladder || floater || sinker) {player.body.setLinearVelocity(player.speed/2f,player.body.getLinearVelocity().y);}
        if (player.body.getLinearVelocity().x <=player.speed){
            if (!rpg){

                player.body.applyLinearImpulse(walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.body.setLinearVelocity(player.speed,0);

            }

        }else{
            if (!jumping) player.body.setLinearVelocity(player.speed,player.body.getLinearVelocity().y);
        }

    }

    public void stand(){
        if (!rpg) {
            player.moving = false;
            player.body.setLinearVelocity( 0, player.body.getLinearVelocity().y );
            if (onplatformh) {
                player.body.setLinearVelocity( currentplatform.body.getLinearVelocity().x, player.body.getLinearVelocity().y );
            } else if (onplatformv) {
                //player.body.setLinearVelocity(player.body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
            }
        }else
        {
            player.moving = false;
            player.body.setLinearVelocity( 0, 0 );
            if (onplatformh) {
                player.body.setLinearVelocity( currentplatform.body.getLinearVelocity().x, player.body.getLinearVelocity().y );
            } else if (onplatformv) {
                //player.body.setLinearVelocity(player.body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
            }

        }
    }

    public void respawn(){
        load();

    }

    public void keyinput(){





        if (Gdx.app.getType() == Application.ApplicationType.Desktop && !uitest ) {

            if (victory||starting) return;


            if (player.state == DEAD) return;

            if (Gdx.input.isKeyPressed( Input.Keys.X )) {
                act(action1);
            }
            if (Gdx.input.isKeyPressed( Input.Keys.C )) {
                act(action2);
            }
            if (Gdx.input.isKeyPressed( Input.Keys.D )) {
                act(action3);
            }
            if (Gdx.input.isKeyPressed( Input.Keys.S )) {
                act(action4);
            }


            if (!rpg) {
                if (Gdx.input.isKeyPressed( Input.Keys.RIGHT )) {
                    pressright();
                    player.moving = true;
                } else if (Gdx.input.isKeyPressed( Input.Keys.LEFT )) {
                    pressleft();

                } else {
                    stand();

                }

                if (ladder || floater || sinker) {
                    if (Gdx.input.isKeyPressed( Input.Keys.UP )) pressup();
                } else {
                    if (Gdx.input.isKeyJustPressed( Input.Keys.UP )) pressup();
                }

                if (ladder || floater || sinker) {
                    if (Gdx.input.isKeyPressed( Input.Keys.DOWN )) pressdown();
                } else {
                    if (Gdx.input.isKeyJustPressed( Input.Keys.DOWN )) pressdown();
                }
            }else{
                if (Gdx.input.isKeyPressed( Input.Keys.RIGHT )) {
                    pressright();
                    player.moving = true;
                } else if (Gdx.input.isKeyPressed( Input.Keys.LEFT )) {
                    pressleft();
                    player.moving = true;
                } else if (Gdx.input.isKeyPressed( Input.Keys.UP )) {
                    pressup();
                    player.moving = true;
                } else if (Gdx.input.isKeyPressed( Input.Keys.DOWN )) {
                    pressdown();
                    player.moving = true;

                } else {
                    stand();

                }

            }


        }


    }

    public boolean disablecontrol = false;
    public boolean disabledpad = false;
    public boolean disableXaxis = false;
    public boolean disableYaxis = false;
    boolean dashed = false;
    boolean jetpack = false;
    float jetpackcooldown =0;
    float gravity;
    public void act(gameobject go){
//        Gdx.app.log(go.pcooldown+"", go.cooldown+"");
        if (go==null) return;

        //Gdx.app.log(go.bindvar, getVar( go.bindvar )+"");
        if (go.bindvar!=null){
            if (getVar( go.bindvar )<=0) {return;}else{
                setOrAddVars( go.bindvar,1,VAROP.SUB );
            }

        }
        playSfx( go.sfx );
        switch (go.action){
            case NONE:
                break;
            case JUMP: //jumping ga usah pakai cooldown
                if (!jumping) {
                    player.body.applyLinearImpulse( 0f, gravity*go.impulse, player.getX(), player.getY(), true );
                    jumping = true;
                }

                break;
            case JETPACK: //jumping ga usah pakai cooldown
                if (go.cooldown>0) return;

                jetpack = true;
                //    jetpackcooldown = 0.1f;
                player.body.applyLinearImpulse( 0f, gravity*go.impulse, player.getX(), player.getY(), true );
                jumping = true;
                go.cooldown=go.pcooldown;


                break;
            case DASH:
                if (jumping) {
                    if (!dashed){
                    dashed=true;
                    switch (player.dir) {
                        case 0:
                            player.body.applyLinearImpulse( 0f, -go.impulse, player.getX(), player.getY(), true );
                            break;
                        case 1:
                            player.body.applyLinearImpulse( go.impulse, 0f, player.getX(), player.getY(), true );
                            break;
                        case 2:
                            player.body.applyLinearImpulse( -go.impulse, 0f, player.getX(), player.getY(), true );
                            break;
                        case 3:
                            player.body.applyLinearImpulse( 0f, go.impulse, player.getX(), player.getY(), true );
                            break;

                    }
                    salto=360;
                    }
                }
                //jumping = true;
                go.cooldown=go.pcooldown;

                break;
            case SHOOT:
                if (go.cooldown>0) return;

                gameobject newbrick = new gameobject();
                newbrick.mygame = this;
                newbrick.speed=go.pspeed;
                newbrick.maxdistance=go.pmaxdistance;
                newbrick.spread=go.pspread-(2*(float)Math.random()*go.pspread);
                newbrick.damage=go.pdamage;
                newbrick.dir=player.dir;
                newbrick.anim=go.panim;
                newbrick.pimagesize=go.pimagesize;
                float posx=player.body.getPosition().x;
                float posy=player.body.getPosition().y;
                switch (player.dir){
                    case 0:
                        posy-=player.getRegionHeight()/scale;
                        break;
                    case 1:
                        posx+=player.getRegionWidth()/scale;

                        break;
                    case 2:
                        posx-=player.getRegionWidth()/scale;
                        break;
                    case 3:
                        posy+=player.getRegionHeight()/scale;

                        break;
                }
                newbrick.setupGameObject( world, null,posx, posy, newbrick.pimagesize.x/2f, newbrick.pimagesize.y/2f, BodyDef.BodyType.DynamicBody, PLAYERPROJECTILE, null ,null ,false,1);
                this.objects.add( newbrick );
                go.cooldown=go.pcooldown;
                break;
        }


    }


    public void save(){
        save.mapname=file;
        save.x=player.body.getPosition().x;
        save.y=player.body.getPosition().y;
        Json json = new Json();
        FileHandle file = Gdx.files.local(path + "/save.json");
        StringWriter sw = new StringWriter();
        file.writeString(json.prettyPrint(save), false);
    }


    public String fadepath;
    public String fademapname;
    public void fadeinitialise(String path, String mapname){
        if (fadeout>0) return;
        fadepath=path;
        fademapname=mapname;
        fadeout=fadeoutmax;
        log(fadeout+"");
    }

    public void load(){
        try {
            savegame at;
            Json json = new Json();
            FileHandle f = Gdx.files.local( path + "/save.json" );
            at = json.fromJson( savegame.class, f );
            save = at;
            move = new Vector2(save.x,save.y);

            if (bgm!=null) {
             if (!this.file.equalsIgnoreCase( at.mapname))  {
                 bgm.stop();
             }
            }
            fadeinitialise( path, at.mapname );

        }catch(Exception e){
            fadeinitialise( path, file );
        }

    }

    public void killPlayer(){
        if (player.state!= DEAD && !victory && !starting)
        {
            playSfx(player.sfxdead);
            dead+=1;
            player.bumbum();
            player.state= DEAD;
            ///////////////////



            ///////////////////
        }

    }

    public FileHandle getFile(String fullpath){
        if (playtest){
            return Gdx.files.absolute( fullpath );
        }else{
            return Gdx.files.internal( fullpath );
        }
    }


    float reqx=0;
    float reqy=0;
    boolean transformrequest;
    public void requesttransform(float x,float y){
        transformrequest=true;
        reqx=x;
        reqy=y;
    }

    public void fullfilltransformrequest(){
        transformrequest=false;
        player.body.setTransform( reqx, reqy, 0 );
        if (rpg) player.body.setLinearVelocity( 0, 0 );
    }

    public void setGameObject(boolean object, TiledMapTileLayer.Cell cece, float xx, float yy, float ww, float hh, boolean over, MapObject obj, float opacity){

        if (!object && cece==null) return; //empty tile

        MapProperties o;
        MapObject objx = null;
        TextureRegion t = null;
        TiledMapTile tlcece=null;
        boolean flip=false;
        float rota=0f;


        if (!object) { //tile
            tlcece = cece.getTile();
            o=tlcece.getProperties();
            flip = cece.getFlipVertically();
            rota = cece.getRotation();
        }else { //object
            objx=obj;
            o = obj.getProperties();
            if (obj instanceof TiledMapTileMapObject) {
                t = ((TiledMapTileMapObject) obj).getTextureRegion();
            }
        }

        gameobject newbrick = new gameobject();
        newbrick.mygame = this;
        newbrick.damage = (o.containsKey( "damage" )) ? Float.parseFloat( o.get( "damage" ).toString() ) : 0f;
        newbrick.rotating = o.containsKey( "rotating" );
        newbrick.destructible = o.containsKey( "destructible" );
        newbrick.light = (o.containsKey( "light" )) ? Float.parseFloat( o.get( "light" ).toString() ) : 0;

        if (o.containsKey( "lightcolor" )){
            String cs = o.get( "lightcolor" ).toString();
            try {
                float bgr = (float) Integer.parseInt( cs.substring( 0, 2 ), 16 ) / 256;
                float bgg = (float) Integer.parseInt( cs.substring( 2, 4 ), 16 ) / 256;
                float bgb = (float) Integer.parseInt( cs.substring( 4, 6 ), 16 ) / 256;
                newbrick.lightColor = new Color( bgr, bgg, bgb, 1f );
            }catch(Exception e){
                newbrick.lightColor = Color.WHITE;
            }

        }
        if (o.containsKey( "bgcolor" )){
            String cs = o.get( "bgcolor" ).toString();
            try {
                float bgr = (float) Integer.parseInt(cs.substring(0, 2), 16) / 256;
                float bgg = (float) Integer.parseInt(cs.substring(2, 4), 16) / 256;
                float bgb = (float) Integer.parseInt(cs.substring(4, 6), 16) / 256;
                newbrick.bgcolor = new Color(bgr,bgg,bgb,1f);
            }catch(Exception e){
            }
        }
        if (o.containsKey( "color" )){
            String cs = o.get( "color" ).toString();
            try {
                float bgr = (float) Integer.parseInt( cs.substring( 0, 2 ), 16 ) / 256;
                float bgg = (float) Integer.parseInt( cs.substring( 2, 4 ), 16 ) / 256;
                float bgb = (float) Integer.parseInt( cs.substring( 4, 6 ), 16 ) / 256;
                newbrick.color = new Color( bgr, bgg, bgb, 1f );
            }catch(Exception e){
            }

        }



        newbrick.HP = (o.containsKey( "HP" )) ? Integer.parseInt( o.get( "HP" ).toString() ) : 1;
        newbrick.maxHP=newbrick.HP;
        newbrick.heavy = o.containsKey( "heavy" );

        if (o.containsKey( "sfx" )) {
                String sfx = o.get("sfx").toString();
                if (getFile( path + "/" + sfx ).exists())
                    newbrick.sfx = Gdx.audio.newSound( getFile( path + "/" + sfx ) );
            }

        if (o.containsKey( "psfx" )) {
            String sfx = o.get("psfx").toString();
            if (getFile( path + "/" + sfx ).exists())
                newbrick.psfx = Gdx.audio.newSound( getFile( path + "/" + sfx ) );
        }

        if (o.containsKey( "sfxdead" )) {
            String sfx = o.get("sfxdead").toString();
            if (getFile( path + "/" + sfx ).exists())
                newbrick.sfxdead = Gdx.audio.newSound( getFile( path + "/" + sfx ) );
        }

        if (o.containsKey( "size" )) {
            String[] sz = o.get("size").toString().split( "," );
            ww=Float.parseFloat(sz[0]);
            hh=Float.parseFloat(sz[1]);
        }

        if (o.containsKey( "id" )) {
            newbrick.id=o.get("id").toString();
        }

        if (o.containsKey( "mode" )) {
            if (o.get( "mode" ).toString().equalsIgnoreCase( "rpg" )) {
                world.setGravity( new Vector2( 0f, 0f ) );
                jumping = false;
                rpg = true;
            }
        }

        if (o.containsKey( "path" )) {
            String[] ss = o.get( "path" ).toString().split( "," );
            int[] myArray = new int[ss.length];

            for (int i = 0; i < myArray.length; i++) {
                int k = 0;
                switch (ss[i]) {
                    case "down":
                        k = 0;
                        break;
                    case "right":
                        k = 1;
                        break;
                    case "left":
                        k = 2;
                        break;
                    case "up":
                        k = 3;
                        break;
                    case "wait":
                        k = 4;
                        break;
                    default:
                        k = Integer.parseInt( ss[i] );
                }
                myArray[i] = k;
            }
            newbrick.path = myArray; //new int[]{0,0,2,2,3,3,1,1,2,1};

        } else {
            newbrick.path = null; //new int[]{0,0,2,2,3,3,1,1,2,1};

        }

        if (o.containsKey( "night" )) {
            night=true;
            rayHandler=new RayHandler( world);
            rayHandler.setAmbientLight( 1,1,1,0.3f);

        }

        if (o.containsKey( "zoom" )) {
            zoom = Float.parseFloat( o.get( "zoom" ).toString() );
        }

        if (o.containsKey( "gravity" )) {
            world.setGravity( new Vector2(0, -Float.parseFloat( o.get( "gravity" ).toString()) ));
        }

        newbrick.dir = (o.containsKey( "dir" )) ? Integer.parseInt( o.get( "dir" ).toString() ) : 1;
        newbrick.tameif = (o.containsKey( "tameif" )) ?  o.get( "tameif" ).toString() : "";

        newbrick.wait = (o.containsKey( "wait" )) ? Float.parseFloat( o.get( "wait" ).toString() ) : 1f;
        newbrick.speed = (o.containsKey( "speed" )) ? Float.parseFloat( o.get( "speed" ).toString() ) : 1f;
        newbrick.chase = o.containsKey( "chase" );
        newbrick.chaseRadius = (o.containsKey( "chaseRadius" )) ? Float.parseFloat( o.get( "chaseRadius" ).toString() ) : 100f;
        newbrick.stepping = o.containsKey( "stepping" );
        newbrick.canshoot = o.containsKey( "canshoot" );
        newbrick.dirlocked = o.containsKey( "dirlocked" );
        newbrick.pspread = (o.containsKey( "pspread" )) ? Float.parseFloat( o.get( "pspread" ).toString() ) : 0;
        newbrick.pcooldown = (o.containsKey( "pcooldown" )) ? Float.parseFloat( o.get( "pcooldown" ).toString() ) : 1f;
        newbrick.pspeed = (o.containsKey( "pspeed" )) ? Float.parseFloat( o.get( "pspeed" ).toString() ) : 4;
        newbrick.pmaxdistance = (o.containsKey( "pmaxdistance" )) ? Integer.parseInt( o.get( "pmaxdistance" ).toString() ) : 300;
        newbrick.pdamage = (o.containsKey( "pdamage" )) ? Integer.parseInt( o.get( "pdamage" ).toString() ) : 1;


        if (o.containsKey( "name" )) {
            switch (o.get( "name" ).toString()) {
                case "player":
                    if (o.containsKey( "anim" )){
                    String anim = o.get( "anim" ).toString();

                        Texture txPlayer = new Texture( getFile( path + "/" + anim ) );
                        TextureRegion[][] tmp = TextureRegion.split( txPlayer,
                                txPlayer.getWidth() / 4,
                                txPlayer.getHeight() / 4 );

                        for (int i = 0; i < 4; i++) {
                            TextureRegion[] walkFrames = new TextureRegion[4];
                            int index = 0;
                            for (int j = 0; j < 4; j++) {
                                walkFrames[index++] = tmp[i][j];
                            }
                            Animation<TextureRegion> tempAnim = new Animation<TextureRegion>( 0.1f, walkFrames );
                            newbrick.anim.add( tempAnim );
                        }
                    }

                    log("setupping game object");
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLAYER, objx, t, over ,opacity);
                    player = newbrick;
                    log("player set");

                    break;
                case "brick":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BRICK, objx, t, over  ,opacity);
                    break;
                case "halfbrick":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.HALFBRICK, objx, t, over  ,opacity);
                    break;
                case "box":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.BOX, objx, t, over ,opacity );
                    break;
                case "checkpoint":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, CHECKPOINT, objx, t, over ,opacity );
                    break;
                case "spring":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SPRING, objx, t, over ,opacity );
                    break;
                case "switch":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCH, objx, t, over  ,opacity);
                    break;
                case "switchon":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHON, objx, t, over ,opacity );
                    break;
                case "switchoff":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHOFF, objx, t, over ,opacity );
                    break;
                case "platformh":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMH, objx, t, over  ,opacity);
                    break;
                case "platformv":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMV, objx, t, over  ,opacity);
                    break;
                case "platforms":
                    newbrick.setupGameObject( world, null, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.PLATFORMS, objx, t, over  ,opacity);
                    break;
                case "ladder":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LADDER, objx, t, over  ,opacity);
                    break;
                case "leftslope":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LEFTSLOPE, objx, t, over ,opacity );
                    break;
                case "rightslope":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.RIGHTSLOPE, objx, t, over  ,opacity);
                    break;
                case "miscs":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, objx, t, over  ,opacity);
                    break;
                case "item":
                    if (checkQual( o)) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.KinematicBody, ITEM, objx, t, over  ,opacity);
                    }
                    break;
                case "itemsensor":
                    if (checkQual( o)) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, ITEMSENSOR, objx, t, over  ,opacity);
                    }
                    break;
                case "block":
                    if (checkQual( o )) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.KinematicBody, BLOCK, objx, t, over  ,opacity);
                    }
                    break;
                case "listener":
                    if (checkQual( o )) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, LISTENER, objx, t, over  ,opacity);
                    }
                    break;
                case "action":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, LISTENER, objx, t, over  ,opacity);

                    return;

                case "enemy": case "monster":
                    newbrick.destructible=true;
                default: //other names, including the old

                    Vector2 imgsize = new Vector2( Tsw, Tsh );
                    Vector2 pimgsize = new Vector2( Tsw, Tsh );

                    if (o.containsKey( "anim" )) {
                        String anim = o.get( "anim" ).toString();
                        Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                        TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                txMonster.getWidth() / 4,
                                txMonster.getHeight() / 4 );
                        imgsize = new Vector2( txMonster.getWidth() / 4, txMonster.getHeight() / 4 );
                        for (int i = 0; i < 4; i++) {
                            TextureRegion[] walkFrames = new TextureRegion[4];
                            int index = 0;
                            for (int j = 0; j < 4; j++) {
                                walkFrames[index++] = tmp[i][j];
                            }
                            Animation<TextureRegion> tempAnim = new Animation<TextureRegion>( 0.1f, walkFrames );
                            newbrick.anim.add( tempAnim );
                        }


                    }

                    Texture txMonster;
                    try {
                        String anim = o.get( "panim" ).toString();
                        txMonster = new Texture( getFile( path + "/" + anim ) );
                    }catch(Exception e){
                        txMonster = new Texture(Gdx.files.internal( "platformer/eshoot.png" ) );
                    }
                    TextureRegion[][] tmp = TextureRegion.split( txMonster,
                            txMonster.getWidth() / 4,
                            txMonster.getHeight() / 4 );
                    newbrick.pimagesize = new Vector2( txMonster.getWidth() / 4, txMonster.getHeight() / 4 );
                    for (int i = 0; i < 4; i++) {
                        TextureRegion[] walkFrames = new TextureRegion[4];
                        int index = 0;
                        for (int j = 0; j < 4; j++) {
                            walkFrames[index++] = tmp[i][j];
                        }
                        Animation<TextureRegion> tempAnim = new Animation<TextureRegion>( 0.1f, walkFrames );
                        newbrick.panim.add( tempAnim );
                    }


                    newbrick.mygame = this;
                    if (newbrick.heavy) {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.KinematicBody, gameobject.objecttype.MONSTER, objx, t, over  ,opacity);
                    } else {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER, objx, t, over  ,opacity);

                    }

            }


        }else{ //no name properties, decoration tile.
            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, objx, t, over  ,opacity);
        }

        if (flip) newbrick.rotate( 180 );
        if (rota != 0) newbrick.rotate( rota * 90 );
        objects.add( newbrick );
        if (!object) cece.setTile( null );

    }

}


