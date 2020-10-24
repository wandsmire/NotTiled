package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
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

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LISTENER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.states.DEAD;


public class game {
    public boolean debugmode = false;
    public boolean playtest=true;
    public boolean uitest=false;


    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;
    public boolean loadingmap;
    public boolean rpg=false;

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

    public int touchedsinker;

    public float jumpinterval=0;
    public float stompinterval=0;
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
    public int Tw;
    public int Th;
    public float Tsw;
    public float Tsh;


    public boolean initialise(String path, String filename){
        this.path = path;
        this.file = filename;
        rpg=false;
        particles.clear();


        Texture tmp = new Texture( getFile( path + "/hpbar.png") );
        hpbar = TextureRegion.split( tmp,
                tmp.getWidth(),
                tmp.getHeight() / 2 );

        tmp = new Texture( getFile( path + "/icons.png") );
        icons = TextureRegion.split( tmp,
                tmp.getWidth() / 5,
                tmp.getHeight() / 5 );

        objects = new ArrayList<gameobject>();

        try {
            if (playtest) {
                map = new TmxMapLoader( new ExternalFileHandleResolver() ).load( path + "/" + filename );
            } else {
                map = new TmxMapLoader( new InternalFileHandleResolver() ).load( path + "/" + filename );

            }
        }catch(Exception e){
            return false;
        }
        renderer = new OrthogonalTiledMapRenderer(map);

        Box2D.init();
        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();

        mycontactlistener = new WorldContactListener(this);
        world.setContactListener(mycontactlistener);



        stateTime = 0f;
        playerTime = 0f;



        for (MapLayer mlayer: map.getLayers()){
            if (mlayer.getName().startsWith( "Tile" )) {
                boolean over = false;
                if (mlayer.getName().equalsIgnoreCase( "Tile 3" )){
                    over=true;
                }
                TiledMapTileLayer tlayer = (TiledMapTileLayer) mlayer;
                this.Tw= tlayer.getWidth();
                this.Th=tlayer.getHeight();
                this.Tsw= tlayer.getTileWidth();
                this.Tsh=tlayer.getTileHeight();
                for (int yy = 0; yy < tlayer.getHeight(); yy++) {
                    for (int xx = 0; xx < tlayer.getWidth(); xx++) {
                        TiledMapTileLayer.Cell cece = tlayer.getCell( xx, yy );
                        float ww=0;float hh=0;
                        try {
                             ww = cece.getTile().getTextureRegion().getRegionWidth();
                             hh = cece.getTile().getTextureRegion().getRegionHeight();
                        }catch(Exception e){}
                        setGameObject(false, cece,xx*Tsw,yy*Tsh,ww,hh,over, null);
                    }
                }
            } else if (mlayer.getName().startsWith( "Object" )) {
                MapObjects objects = map.getLayers().get(mlayer.getName()).getObjects();
                for (MapObject o: objects){
                    Rectangle rect;
                    if (o instanceof TiledMapTileMapObject){
                        TiledMapTileMapObject obj = (TiledMapTileMapObject) o;
                        setGameObject(true,null,obj.getX(),obj.getY(),obj.getTextureRegion().getRegionWidth(),obj.getTextureRegion().getRegionHeight(),false,o);

                    }else{
                        RectangleMapObject obj = (RectangleMapObject) o;
                        rect = obj.getRectangle();
                        setGameObject(true,null,rect.x,rect.y,rect.width,rect.height,false,o);


                    }
                }

            }
        }

        if (player!=null){

        MapProperties mpa = map.getProperties();
        if (mpa != null) {
            String bgms = (String) mpa.get( "bgm" );
            if (bgms != null) {

                if (getFile( path + "/" + bgms ).exists()) {
                    bgm = Gdx.audio.newMusic( getFile( path + "/" + bgms ) );
                    bgm.setLooping( true );
                    bgm.play();
                }
            }


            String bgc = (String) mpa.get( "background" );
            if (bgc != null) {

                if (getFile( path + "/" + bgc ).exists()) {
                    txBackground = new Texture( getFile( path + "/" + bgc ) );
                } else {
                    txBackground = null;
                }

            }

            nextlevel = (String) mpa.get( "nextlevel" );
            debriefing = (String) mpa.get( "debriefing" );
            died = "GAME OVER";
        }
        } else {
            RectangleMapObject o = new RectangleMapObject(  );
            o.getProperties().put( "name","player" );
            o.getProperties().put( "anim",": \nPlease add one player object" );
            RectangleMapObject obj = (RectangleMapObject) o;
            Rectangle rect = obj.getRectangle();
            setGameObject(true,null,rect.x,rect.y,rect.width,rect.height,false,o);

            return true;
        }


        victory=false;
        loadingmap=false;
        //save();
        return false;



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
                    str1.draw( b, " x " + vr.value, 90,ssy-20-50*index);
                    b.draw( icons[vr.iconx][vr.icony],50,ssy-50-50*index,32,32 );
                   // str1.draw( b, vr.value, ssy-10-10*index)
                }else{
                    str1.draw( b, vr.key+" x " + vr.value, 50,ssy-20-50*index);
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
    public OrthographicCamera gc;
    public void update(SpriteBatch batch, float delta, OrthographicCamera gamecam) {
        gc=gamecam;
        if (!starting && player.state!=DEAD) world.step(1/60f,6,2);

        if (player.HP<=0){
            killPlayer();
        }
        if (player.HP>player.maxHP){
            player.HP=player.maxHP;
        }

        checkHUD();

        if (!rpg) {
            if (Math.abs( player.body.getLinearVelocity().y ) >= 0.2f) {
                jumping = true;
                jumpinterval = 0;
            } else {
                jumpinterval += delta;
            }

            if (jumpinterval >= 0.1f) {
                jumping = false;
            }
            stompinterval -= delta;
            if (stompinterval < 0) stompinterval = 0;
        }
        if (player.body.getPosition().y<=-32/100f && player.state != DEAD)
        {
            //playSfx(sfxplayer);
            dead+=1;
            player.state = DEAD;

        }



        //gamecam.position.set(player.body.getPosition().x,player.body.getPosition().y,0);

        //if (gamecam.position.x >)
        float posex = player.body.getPosition().x;
        float posey = player.body.getPosition().y;

        float lerp = 5f;
        Vector3 position = gamecam.position;
        position.x += (posex - position.x) * lerp * delta;
        position.y += (posey - position.y) * lerp * delta;
        position.x = Math.round( position.x *500)/500f;
        position.y = Math.round( position.y *500)/500f;

        gamecam.update();

        stateTime += delta;
        if (starting){
            TextureRegion currentFramea = player.anim.get(player.dir).getKeyFrame(0, true);
            player.setRegion(currentFramea);

        }
        else if (player.moving && (!jumping || onplatformv) && (!ladder && !floater && !sinker))
        {
            playerTime +=delta;
            TextureRegion currentFramea = player.anim.get(player.dir).getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);
        }
        else if (jumping && !ladder && !floater && !sinker && !onplatformv) {
            TextureRegion currentFramea = player.anim.get(player.dir).getKeyFrame(0.5f, true);
            player.setRegion(currentFramea);
        }
        else if ((ladder || floater || sinker) && player.moving )
        {
            if (ladder) player.dir=3;

            playerTime +=delta;

            TextureRegion currentFramea = player.anim.get(player.dir).getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);

        }else
        {
            //idle animation if you will...
            TextureRegion currentFramea = player.anim.get(player.dir).getKeyFrame(0, true);
            player.setRegion(currentFramea);
        }


        ladder= touchedladder > 0;



        if (floater){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(-0.7f);
        }else if (ladder){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(0f);
        }else if (sinker){

            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(0.7f);
        } else
        {
            player.body.setGravityScale(1);
        }


        for (int i = objects.size() - 1; i >= 0; i--){
            gameobject go = objects.get( i );
            go.update(delta);
            if (go.state==DEAD){
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
                batch.draw( hpbar[1][0], sboxes.getX(), sboxes.getY() + sboxes.getHeight() + 0.01f, sboxes.getWidth(), hpbar[1][0].getRegionHeight() / 100f );
                batch.draw( hpbar[0][0], sboxes.getX(), sboxes.getY() + sboxes.getHeight() + 0.01f, sboxes.HP / sboxes.maxHP * sboxes.getWidth(), hpbar[0][0].getRegionHeight() / 100f );
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

    }

    public void playSfx(Sound s){
        if (s!=null) s.play(1.0f);
    }

    public float walkForce=0.5f;
    public float speedLimit=1f;
    public float slowSpeed=0.4f;
    public float rpgSpeed=1f;


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
                player.body.setLinearVelocity( 0, slowSpeed );
            }
        }else{
            if (ladder || floater || sinker) {player.body.setLinearVelocity(player.body.getLinearVelocity().x,slowSpeed);}
                player.body.setLinearVelocity(0,rpgSpeed);

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
                player.body.setLinearVelocity( player.body.getLinearVelocity().x, -slowSpeed );
            }
        }else{
            if (ladder || floater || sinker) {player.body.setLinearVelocity(player.body.getLinearVelocity().x,-slowSpeed);}
            player.body.setLinearVelocity(0,-rpgSpeed);


        }

    }

    public void pressleft(){
        player.moving=true;
        player.dir=2;
        if (ladder || floater || sinker) {player.body.setLinearVelocity(-slowSpeed,player.body.getLinearVelocity().y);}
        if (player.body.getLinearVelocity().x >=-speedLimit) {
            if (!rpg){
                player.body.applyLinearImpulse(-walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.body.setLinearVelocity(-rpgSpeed,0);

            }
        }
    }
    public void pressright(){
        player.moving=true;
        player.dir=1;
        if (ladder || floater || sinker) {player.body.setLinearVelocity(slowSpeed,player.body.getLinearVelocity().y);}
        if (player.body.getLinearVelocity().x <=speedLimit){
            if (!rpg){

                player.body.applyLinearImpulse(walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.body.setLinearVelocity(rpgSpeed,0);

            }

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

    public void act(gameobject go){
//        Gdx.app.log(go.pcooldown+"", go.cooldown+"");
        if (go==null) return;
        if (go.cooldown>0) return;

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
            case JUMP:
                player.body.applyLinearImpulse( 0f, go.impulse, player.getX(), player.getY(), true );
                jumping = true;
                go.cooldown=go.pcooldown;

                break;
            case DASH:
                switch (player.dir){
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
                jumping = true;
                go.cooldown=go.pcooldown;

                break;
            case SHOOT:
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
                        posy-=player.getRegionHeight()/100f;
                        break;
                    case 1:
                        posx+=player.getRegionWidth()/100f;

                        break;
                    case 2:
                        posx-=player.getRegionWidth()/100f;
                        break;
                    case 3:
                        posy+=player.getRegionHeight()/100f;

                        break;
                }
                newbrick.setupGameObject( world, null,posx, posy, newbrick.pimagesize.x/2f, newbrick.pimagesize.y/2f, BodyDef.BodyType.DynamicBody, PLAYERPROJECTILE, null ,null ,false);
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

    public void load(){
        try {
            savegame at = new savegame();
            Json json = new Json();
            FileHandle f = Gdx.files.local( path + "/save.json" );
            at = json.fromJson( savegame.class, f );
            save = at;

            bgm.stop();
            loadingmap = true;
            initialise( path, at.mapname );

            Gdx.app.postRunnable( new Runnable() {

                @Override
                public void run() {
                    player.body.setTransform( save.x, save.y, 0 );
                    player.body.setLinearVelocity( 0, 0 );
                }
            } );
        }catch(Exception e){

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
            return Gdx.files.external( fullpath );
        }else{
            return Gdx.files.internal( fullpath );
        }
    }

    public void setGameObject(boolean object, TiledMapTileLayer.Cell cece, float xx, float yy, float ww, float hh, boolean over, MapObject obj){

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

        if (o.containsKey( "mode" )) {
            if (o.get( "mode" ).toString().equalsIgnoreCase( "rpg" )) {
                world.setGravity( new Vector2( 0f, 0f ) );
                jumping = false;
                rpg = true;
                return;
            }
        }

        if (o.containsKey( "name" )) {
            switch (o.get( "name" ).toString()) {
                case "player":
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

                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLAYER, objx, t, over );
                    player = newbrick;
                    break;
                case "brick":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BRICK, objx, t, over );
                    break;
                case "halfbrick":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.HALFBRICK, objx, t, over );
                    break;
                case "box":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.BOX, objx, t, over );
                    break;
                case "checkpoint":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, CHECKPOINT, objx, t, over );
                    break;
                case "spring":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SPRING, objx, t, over );
                    break;
                case "switch":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCH, objx, t, over );
                    break;
                case "switchon":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHON, objx, t, over );
                    break;
                case "switchoff":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHOFF, objx, t, over );
                    break;
                case "platformh":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMH, objx, t, over );
                    break;
                case "platformv":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMV, objx, t, over );
                    break;
                case "platforms":
                    newbrick.setupGameObject( world, null, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.PLATFORMS, objx, t, over );
                    break;
                case "ladder":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LADDER, objx, t, over );
                    break;
                case "leftslope":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LEFTSLOPE, objx, t, over );
                    break;
                case "rightslope":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.RIGHTSLOPE, objx, t, over );
                    break;
                case "miscs":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, objx, t, over );
                    break;
                case "item":
                    if (checkQual( o)) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, ITEM, objx, t, over );
                    }
                    break;
                case "block":
                    if (checkQual( o )) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, BLOCK, objx, t, over );
                    }
                    break;
                case "listener":
                    if (checkQual( o )) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, LISTENER, objx, t, over );
                    }
                    break;
                case "action":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, LISTENER, objx, t, over );

                    return;


                default: //other names, including the old monster
                    newbrick.dir = (o.containsKey( "dir" )) ? Integer.parseInt( o.get( "dir" ).toString() ) : 1;
                    newbrick.tameif = (o.containsKey( "tameif" )) ?  o.get( "tameif" ).toString() : "";

                    newbrick.speed = (o.containsKey( "speed" )) ? Float.parseFloat( o.get( "speed" ).toString() ) : 0.5f;
                    newbrick.chase = o.containsKey( "chase" );
                    newbrick.chaseRadius = (o.containsKey( "chaseRadius" )) ? Float.parseFloat( o.get( "chaseRadius" ).toString() ) : 100f;
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
                                default:
                                    k = Integer.parseInt( ss[i] );
                            }
                            myArray[i] = k;
                        }
                        newbrick.path = myArray; //new int[]{0,0,2,2,3,3,1,1,2,1};

                    } else {
                        newbrick.path = null; //new int[]{0,0,2,2,3,3,1,1,2,1};

                    }
                    newbrick.stepping = o.containsKey( "stepping" );
                    newbrick.canshoot = o.containsKey( "canshoot" );
                    newbrick.dirlocked = o.containsKey( "dirlocked" );
                    newbrick.pspread = (o.containsKey( "pspread" )) ? Float.parseFloat( o.get( "pspread" ).toString() ) : 0;
                    newbrick.pcooldown = (o.containsKey( "pcooldown" )) ? Float.parseFloat( o.get( "pcooldown" ).toString() ) : 1;
                    newbrick.pspeed = (o.containsKey( "pspeed" )) ? Float.parseFloat( o.get( "pspeed" ).toString() ) : 4;
                    newbrick.pmaxdistance = (o.containsKey( "pmaxdistance" )) ? Integer.parseInt( o.get( "pmaxdistance" ).toString() ) : 300;
                    newbrick.pdamage = (o.containsKey( "pdamage" )) ? Integer.parseInt( o.get( "pdamage" ).toString() ) : 1;
                    Vector2 imgsize = new Vector2( 16f, 16f );
                    Vector2 pimgsize = new Vector2( 16f, 16f );

                    if (o.containsKey( "anim" )) {
                        anim = o.get( "anim" ).toString();
                        Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                        tmp = TextureRegion.split( txMonster,
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

                    if (o.containsKey( "panim" )) {
                        anim = o.get( "panim" ).toString();
                        Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                        tmp = TextureRegion.split( txMonster,
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
                    }

                    newbrick.mygame = this;
                    if (newbrick.heavy) {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.KinematicBody, gameobject.objecttype.MONSTER, objx, t, over );
                    } else {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER, objx, t, over );

                    }

            }


        }else{ //no name properties, decoration tile.
            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, objx, t, over );
        }

        if (flip) newbrick.rotate( 180 );
        if (rota != 0) newbrick.rotate( rota * 90 );
        objects.add( newbrick );
        if (!object) cece.setTile( null );

    }

}


