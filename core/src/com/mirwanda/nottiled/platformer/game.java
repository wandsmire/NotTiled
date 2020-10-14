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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.soap.Text;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.COIN;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.GEAR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.SPIKE;
import static com.mirwanda.nottiled.platformer.player.playerState.DEAD;


public class game {
    public boolean debugmode = true;
    public boolean playtest=true;

    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;
    public boolean loadingmap;
    public boolean rpg=false;
    public boolean uitest=false;
    public float HP=1;
    public int dir;

    public WorldContactListener mycontactlistener;
    public savegame save = new savegame();

    public Vector2 checkpoint = new Vector2();
    public com.mirwanda.nottiled.platformer.player player;
    public java.util.List<gameobject> objects = new ArrayList<gameobject>();

    java.util.List<Animation<TextureRegion>> animPlayer = new ArrayList<>(); // Must declare frame type (TextureRegion)
    Texture txPlayer;
    public Texture txBackground;

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
    public ParticleEffect meledak;
    public int maxhp=10;

    public boolean initialise(String path, String filename){

        this.path = path;
        this.file = filename;
        rpg=false;
        HP=maxhp;

        meledak = new ParticleEffect();
        meledak.load( getFile( path + "/died.p" ), getFile( path ) );
        meledak.getEmitters().first().setPosition( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        meledak.scaleEffect(0.001f, 0.001f); //kudu disini

        objects = new ArrayList<gameobject>();

        if (playtest) {
            map = new TmxMapLoader( new ExternalFileHandleResolver() ).load( path + "/" + filename );
        }else{
            map = new TmxMapLoader( new InternalFileHandleResolver() ).load( path + "/" + filename );

        }
        renderer = new OrthogonalTiledMapRenderer(map);

        Box2D.init();
        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();

        mycontactlistener = new WorldContactListener(this);
        world.setContactListener(mycontactlistener);

        MapProperties mpa = map.getProperties();
        if (mpa != null) {
            String bgms = (String) mpa.get("bgm");
            if (bgms != null) {

                if (getFile( path + "/" + bgms ).exists()) {
                    bgm = Gdx.audio.newMusic( getFile( path + "/" + bgms ) );
                    bgm.setLooping( true );
                    bgm.play();
                }
            }


            String bgc = (String) mpa.get("background");
            if (bgc != null) {

                    if (getFile( path + "/" + bgc ).exists()) {
                        txBackground = new Texture( getFile( path + "/" + bgc ) );
                    } else {
                        txBackground = null;
                    }

            }

            nextlevel = (String) mpa.get("nextlevel");
            debriefing = (String) mpa.get("debriefing");
            died = "GAME OVER";

        }


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
        checkpoint.set(player.b2body.getPosition().x,player.b2body.getPosition().y);

        victory=false;
        loadingmap=false;
        return false;



    }

    public void log(String s){
        Gdx.app.log( "LOJ",s );
    }
    public boolean checkQual(MapObject o){
        boolean qual=true;
        if (o.getProperties().get( "required" )!=null){
            String[] ss = o.getProperties().get( "required" ).toString().split( "," );
            qual=false;
            int rq=0;
            boolean recheck=true;
            while (recheck) {
                recheck=false;
                for (int i = 0; i < ss.length; i++) {
                    String[] sv = ss[i].split( "=" );
                    Gdx.app.log( sv[0]+"",sv[1]+"" );
                    boolean ada = false;
                    for (KV var : save.vars) {
                        if (sv[0].equalsIgnoreCase( var.key )) {
                            ada = true;
                            Gdx.app.log( sv[1]+"",var.value+"" );
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
        Gdx.app.log( "A",qual+"" );

        if (qual)
        {
            return true;
        }
        return false;

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

    public float anima;
    public int animet;
    public OrthographicCamera gc;
    public void update(SpriteBatch batch, float delta, OrthographicCamera gamecam) {
        gc=gamecam;
        if (!starting) world.step(1/60f,6,2);
        cooldown-=delta;

        if (HP<=0){
            killPlayer();
        }
        if (!rpg) {
            if (Math.abs( player.b2body.getLinearVelocity().y ) >= 0.2f) {
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
        if (player.b2body.getPosition().y<=-32/100f && player.state != DEAD)
        {
            //playSfx(sfxplayer);
            dead+=1;
            player.state = DEAD;

        }



        //gamecam.position.set(player.b2body.getPosition().x,player.b2body.getPosition().y,0);

        //if (gamecam.position.x >)
        float posex = player.b2body.getPosition().x;
        float posey = player.b2body.getPosition().y;

        float lerp = 5f;
        Vector3 position = gamecam.position;
        position.x += (posex - position.x) * lerp * delta;
        position.y += (posey - position.y) * lerp * delta;
        position.x = Math.round( position.x *500)/500f;
        position.y = Math.round( position.y *500)/500f;

        gamecam.update();

        stateTime += delta;
        if (starting){
            TextureRegion currentFramea = animPlayer.get(dir).getKeyFrame(0, true);
            player.setRegion(currentFramea);

        }
        else if (player.moving && (!jumping || onplatformv) && (!ladder && !floater && !sinker))
        {
            playerTime +=delta;
            TextureRegion currentFramea = animPlayer.get(dir).getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);
        }
        else if (jumping && !ladder && !floater && !sinker && !onplatformv) {
            TextureRegion currentFramea = animPlayer.get(dir).getKeyFrame(0.5f, true);
            player.setRegion(currentFramea);
        }
        else if ((ladder || floater || sinker) && player.moving )
        {
            if (ladder) dir=3;

            playerTime +=delta;

            TextureRegion currentFramea = animPlayer.get(dir).getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);

        }else
        {
            //idle animation if you will...
            TextureRegion currentFramea = animPlayer.get(dir).getKeyFrame(0, true);
            player.setRegion(currentFramea);
        }


        if(touchedladder>0) { ladder=true; }
        else { ladder=false; }

        if(touchedfloater>0) { floater=true; }
        else { floater=false; }

        if(touchedsinker>0) { sinker=true; }
        else { sinker=false; }

        if (floater){
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,0);
            player.b2body.setGravityScale(-0.7f);
        }else if (ladder){
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,0);
            player.b2body.setGravityScale(0f);
        }else if (sinker){

            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,0);
            player.b2body.setGravityScale(0.7f);
        } else
        {
            player.b2body.setGravityScale(1);
        }


        for (int i = objects.size() - 1; i >= 0; i--){
            objects.get( i ).update(delta);
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
            player.b2body.setActive(false);
        }

        for (gameobject sboxes:objects)
        {
            if (!sboxes.over) continue;


            if (sboxes.getTexture()!=null ) sboxes.draw(batch);
        }


    }

    public void playSfx(Sound s){
        if (s!=null) s.play(1.0f);
    }

    public float walkForce=0.5f;
    public float speedLimit=1f;
    public float jumpForce=3f;
    public float stompForce=8f;
    public float slowSpeed=0.4f;
    public float rpgSpeed=1f;


    public void pressup(){
        if (rpg) player.moving=true;
        dir=3;

        if (!rpg) {
            if ((!jumping || onplatformv) && !ladder && !sinker && !floater) {
                player.b2body.applyLinearImpulse( 0f, jumpForce, player.getX(), player.getY(), true );
                jumping = true;
                onplatformv = false;
            }
            if (ladder || floater || sinker) {
                player.b2body.setLinearVelocity( 0, slowSpeed );
            }
        }else{
            if (ladder || floater || sinker) {player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,slowSpeed);}
                player.b2body.setLinearVelocity(0,rpgSpeed);

        }
    }

    public void pressdown(){
        if (rpg) player.moving=true;
        dir=0;

        if (!rpg) {
            if (stompinterval == 0 && !ladder && !sinker && !floater) {
                player.b2body.applyLinearImpulse( 0f, -stompForce, player.getX(), player.getY(), true );
                stompinterval = 1;
            }
            if (ladder || floater || sinker) {
                player.b2body.setLinearVelocity( player.b2body.getLinearVelocity().x, -slowSpeed );
            }
        }else{
            if (ladder || floater || sinker) {player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x,-slowSpeed);}
            player.b2body.setLinearVelocity(0,-rpgSpeed);


        }

    }

    public void pressleft(){
        player.moving=true;
        dir=2;
        if (ladder || floater || sinker) {player.b2body.setLinearVelocity(-slowSpeed,player.b2body.getLinearVelocity().y);}
        if (player.b2body.getLinearVelocity().x >=-speedLimit) {
            player.faceright=false;
            if (!rpg){
                player.b2body.applyLinearImpulse(-walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.b2body.setLinearVelocity(-rpgSpeed,0);

            }
        }
    }
    public void pressright(){
        player.moving=true;
        dir=1;
        if (ladder || floater || sinker) {player.b2body.setLinearVelocity(slowSpeed,player.b2body.getLinearVelocity().y);}
        if (player.b2body.getLinearVelocity().x <=speedLimit){
            if (!rpg){

                player.b2body.applyLinearImpulse(walkForce, 0, player.getX(), player.getY(), true);
            }else{
                player.b2body.setLinearVelocity(rpgSpeed,0);

            }

        }

    }

    public void stand(){
        if (!rpg) {
            player.moving = false;
            player.b2body.setLinearVelocity( 0, player.b2body.getLinearVelocity().y );
            if (onplatformh) {
                player.b2body.setLinearVelocity( currentplatform.body.getLinearVelocity().x, player.b2body.getLinearVelocity().y );
            } else if (onplatformv) {
                //player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
            }
        }else
        {
            player.moving = false;
            player.b2body.setLinearVelocity( 0, 0 );
            if (onplatformh) {
                player.b2body.setLinearVelocity( currentplatform.body.getLinearVelocity().x, player.b2body.getLinearVelocity().y );
            } else if (onplatformv) {
                //player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
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
                shoot();
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
    float cooldown=0f;
    float setcooldown=0.2f;

    public void shoot(){
        if (cooldown>0) return;
        gameobject newbrick = new gameobject();
        newbrick.mygame = this;
        newbrick.speed=4f;
        newbrick.maxdistance=300f;
        newbrick.damage=1;
        newbrick.dir=dir;
        float posx=player.b2body.getPosition().x;
        float posy=player.b2body.getPosition().y;
        switch (dir){
            case 0:
                newbrick.setRotation( 180 );
                posy-=16/100f;

                break;
            case 1:
                newbrick.setRotation( -90 );
                posx+=16/100f;

                break;
            case 2:
                posx-=16/100f;

                newbrick.setRotation( 90 );
                break;
            case 3:
                posy+=16/100f;

                break;
        }
        newbrick.setupGameObject( world, null,posx, posy, 5, 5, BodyDef.BodyType.DynamicBody, PLAYERPROJECTILE, null ,null ,false);
        this.objects.add( newbrick );
        cooldown=setcooldown;

    }


    public void save(){
        save.mapname=file;
        save.x=player.b2body.getPosition().x;
        save.y=player.b2body.getPosition().y;
        Json json = new Json();
        FileHandle file = Gdx.files.local(path + "/save.json");
        StringWriter sw = new StringWriter();
        file.writeString(json.prettyPrint(save), false);
    }

    public void load(){
        savegame at = new savegame();
        Json json = new Json();
        FileHandle f = Gdx.files.local(path + "/save.json");
        at = json.fromJson(savegame.class, f);
        save = at;

        bgm.stop();
        loadingmap=true;
        initialise(path, at.mapname);

        Gdx.app.postRunnable( new Runnable() {

            @Override
            public void run () {
                player.b2body.setTransform(save.x, save.y,0);
                player.b2body.setLinearVelocity( 0,0 );
            }
        });

    }

    public void killPlayer(){
        if (player.state!= DEAD && !victory && !starting)
        {
            //playSfx(sfxplayer);
            dead+=1;
            meledak.setPosition( player.b2body.getPosition().x, player.b2body.getPosition().y );
            meledak.reset(false);
            meledak.start();
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
        if (!object && cece!=null) {

            TiledMapTile tlcece = cece.getTile();
            boolean flip = cece.getFlipVertically();
            float rota = cece.getRotation();


            if (cece.getTile().getProperties().containsKey( "name" )) {
                if (cece.getTile().getProperties().get( "name" ).toString().equalsIgnoreCase( "player" )) {
                    String anim = cece.getTile().getProperties().get( "anim" ).toString();
                    txPlayer = new Texture( getFile( path + "/" + anim ) );
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
                        animPlayer.add( tempAnim );
                    }

                    player = new player( world, tlcece.getTextureRegion(), (xx+6) / 100f, (yy) / 100f );//tlcece.getTextureRegion());


                } else {

                    gameobject newbrick = new gameobject();
                    newbrick.mygame = this;

                    if (cece.getTile().getProperties().containsKey( "sfx" )) {
                        String sfx = cece.getTile().getProperties().get("sfx").toString();
                        if (getFile( path + "/" + sfx ).exists())
                            newbrick.sfx = Gdx.audio.newSound( getFile( path + "/" + sfx ) );
                    }
                    if (cece.getTile().getProperties().containsKey( "sfxdead" )) {
                        String sfx = cece.getTile().getProperties().get("sfxdead").toString();
                        if (getFile( path + "/" + sfx ).exists())
                            newbrick.sfxdead = Gdx.audio.newSound( getFile( path + "/" + sfx ) );
                    }

                    switch (cece.getTile().getProperties().get( "name" ).toString()) {
                        case "brick":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BRICK, null, null, over );
                            break;
                        case "halfbrick":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.HALFBRICK, null, null, over );
                            break;
                        case "box":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.BOX, null, null, over );
                            break;
                        case "checkpoint":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, CHECKPOINT, null, null, over );
                            break;
                        case "coin":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, COIN, null, null, over );
                            break;
                        case "spike":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, SPIKE, null, null, over );
                            break;
                        case "gear":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, GEAR, null, null, over );
                            break;
                        case "key":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww,hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.KEY, null, null, over );
                            break;
                        case "lock":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LOCK, null, null, over );
                            break;
                        case "breakable":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BREAKABLE, null, null, over );
                            break;
                        case "spring":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SPRING, null, null, over );
                            break;
                        case "switch":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCH, null, null, over );
                            break;
                        case "switchon":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHON, null, null, over );
                            break;
                        case "switchoff":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHOFF, null, null, over );
                            break;
                        case "platformh":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMH, null, null, over );
                            break;
                        case "platformv":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMV, null, null, over );
                            break;
                        case "platforms":
                            newbrick.setupGameObject( world, null, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.PLATFORMS, null, null, over );
                            break;
                        case "ladder":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LADDER, null, null, over );
                            break;
                        case "floater":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.FLOATER, null, null, over );
                            break;
                        case "sinker":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.SINKER, null, null, over );
                            break;
                        case "leftslope":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.LEFTSLOPE, null, null, over );
                            break;
                        case "rightslope":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.RIGHTSLOPE, null, null, over );
                            break;
                        case "miscs":
                            newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, null, null, over );
                            break;
                        default: //other names, including the old monster
                            TiledMapTile o = cece.getTile();
                            newbrick.HP = (o.getProperties().containsKey( "HP" )) ? Integer.parseInt( o.getProperties().get( "HP" ).toString() ) : 1;
                            newbrick.speed = (o.getProperties().containsKey( "speed" )) ? Float.parseFloat( o.getProperties().get( "speed" ).toString() ) : 0.5f;
                            newbrick.chase = (o.getProperties().containsKey( "chase" )) ? Boolean.parseBoolean( o.getProperties().get( "chase" ).toString() ) : false;
                            newbrick.chaseRadius = (o.getProperties().containsKey( "chaseRadius" )) ? Float.parseFloat( o.getProperties().get( "chaseRadius" ).toString() ) : 100f;
                            if (o.getProperties().containsKey( "path" )) {
                                String[] ss = o.getProperties().get( "path" ).toString().split( "," );
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
                            newbrick.damage = (o.getProperties().containsKey( "damage" )) ? Float.parseFloat( o.getProperties().get( "damage" ).toString() ) : 1f;
                            newbrick.bird = (o.getProperties().containsKey( "bird" )) ? Boolean.parseBoolean( o.getProperties().get( "bird" ).toString() ) : false;
                            newbrick.canshoot = (o.getProperties().containsKey( "canshoot" )) ? true : false;
                            newbrick.pcooldown = (o.getProperties().containsKey( "pcooldown" )) ? Float.parseFloat( o.getProperties().get( "pcooldown" ).toString() ) : 1;
                            newbrick.pspeed = (o.getProperties().containsKey( "pspeed" )) ? Float.parseFloat( o.getProperties().get( "pspeed" ).toString() ) : 4;
                            newbrick.pmaxdistance = (o.getProperties().containsKey( "pmaxdistance" )) ? Integer.parseInt( o.getProperties().get( "pmaxdistance" ).toString() ) : 300;
                            newbrick.pdamage = (o.getProperties().containsKey( "pdamage" )) ? Integer.parseInt( o.getProperties().get( "pdamage" ).toString() ) : 1;
                            Vector2 imgsize= new Vector2(16f,16f);
                            Vector2 pimgsize= new Vector2(16f,16f);

                            if (o.getProperties().containsKey( "anim" )) {
                                String anim = o.getProperties().get( "anim" ).toString();
                                Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                                TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                        txMonster.getWidth() / 4,
                                        txMonster.getHeight() / 4 );
                                imgsize=new Vector2(txMonster.getWidth()/4,txMonster.getHeight() / 4);
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

                            if (o.getProperties().containsKey( "panim" )) {
                                String anim = o.getProperties().get( "panim" ).toString();
                                Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                                TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                        txMonster.getWidth() / 4,
                                        txMonster.getHeight() / 4 );
                                newbrick.pimagesize=new Vector2(txMonster.getWidth()/4,txMonster.getHeight() / 4);
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
                            if (newbrick.bird) {
                                newbrick.setupGameObject( world, null, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.KinematicBody, gameobject.objecttype.MONSTER, null, null, false );
                            } else {
                                newbrick.setupGameObject( world, null, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER, null, null, false );

                            }

                    }


                    if (flip) newbrick.rotate( 180 );
                    if (rota != 0) newbrick.rotate( rota * 90 );
                    if (newbrick.getWidth() > 0) objects.add( newbrick );
                }
            }else{
                gameobject newbrick = new gameobject();
                newbrick.mygame = this;
                newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, null, null, over );
                if (flip) newbrick.rotate( 180 );
                if (rota != 0) newbrick.rotate( rota * 90 );
                if (newbrick.getWidth() > 0) objects.add( newbrick );

            }
            cece.setTile( null );
        }else if (object) {
            TextureRegion t=null;
            if (obj instanceof TiledMapTileMapObject){
                t=((TiledMapTileMapObject) obj).getTextureRegion();
            }

            if (obj.getProperties().containsKey( "item" )) {
                if (checkQual( obj )) {
                    gameobject newbrick = new gameobject();
                    newbrick.mygame = this;
                    newbrick.setupGameObject( world, null, xx,yy, ww, hh, BodyDef.BodyType.StaticBody, ITEM, obj, t, false );
                    this.objects.add( newbrick );
                }
            }
            if (obj.getProperties().containsKey( "block" )) {
                if (checkQual( obj )) {
                    gameobject newbrick = new gameobject();
                    newbrick.mygame = this;
                    newbrick.setupGameObject( world, null, xx,yy,ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BLOCK, obj, t, false );
                    this.objects.add( newbrick );
                }
            }
            if (obj.getProperties().containsKey( "enemy" )) {
                if (checkQual( obj )) {
                    gameobject newbrick = new gameobject();

                    newbrick.HP = (obj.getProperties().containsKey( "HP" )) ? Integer.parseInt( obj.getProperties().get( "HP" ).toString() ) : 1;
                    newbrick.speed = (obj.getProperties().containsKey( "speed" )) ? Float.parseFloat( obj.getProperties().get( "speed" ).toString() ) : 0.5f;
                    newbrick.chase = (obj.getProperties().containsKey( "chase" )) ? Boolean.parseBoolean( obj.getProperties().get( "chase" ).toString() ) : false;
                    newbrick.chaseRadius = (obj.getProperties().containsKey( "chaseRadius" )) ? Float.parseFloat( obj.getProperties().get( "chaseRadius" ).toString() ) : 100f;
                    if (obj.getProperties().containsKey( "path" )) {
                        String[] ss = obj.getProperties().get( "path" ).toString().split( "," );
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
                    newbrick.damage = (obj.getProperties().containsKey( "damage" )) ? Float.parseFloat( obj.getProperties().get( "damage" ).toString() ) : 1f;
                    newbrick.bird = (obj.getProperties().containsKey( "bird" )) ? Boolean.parseBoolean( obj.getProperties().get( "bird" ).toString() ) : false;
                    newbrick.canshoot = (obj.getProperties().containsKey( "canshoot" )) ? true : false;
                    newbrick.pcooldown = (obj.getProperties().containsKey( "pcooldown" )) ? Float.parseFloat( obj.getProperties().get( "pcooldown" ).toString() ) : 1;
                    newbrick.pspeed = (obj.getProperties().containsKey( "pspeed" )) ? Float.parseFloat( obj.getProperties().get( "pspeed" ).toString() ) : 4;
                    newbrick.pmaxdistance = (obj.getProperties().containsKey( "pmaxdistance" )) ? Integer.parseInt( obj.getProperties().get( "pmaxdistance" ).toString() ) : 300;
                    newbrick.pdamage = (obj.getProperties().containsKey( "pdamage" )) ? Integer.parseInt( obj.getProperties().get( "pdamage" ).toString() ) : 1;
                    Vector2 imgsize = new Vector2( 16f, 16f );
                    Vector2 pimgsize = new Vector2( 16f, 16f );

                    if (obj.getProperties().containsKey( "anim" )) {
                        String anim = obj.getProperties().get( "anim" ).toString();
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

                    if (obj.getProperties().containsKey( "panim" )) {
                        String anim = obj.getProperties().get( "panim" ).toString();
                        Texture txMonster = new Texture( getFile( path + "/" + anim ) );
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
                    }

                    newbrick.mygame = this;
                    if (newbrick.bird) {
                        newbrick.setupGameObject( world, null, xx, yy, imgsize.x, imgsize.y, BodyDef.BodyType.KinematicBody, gameobject.objecttype.ENEMY, obj, t, false );
                    } else {
                        newbrick.setupGameObject( world, null, xx, yy, imgsize.x, imgsize.y, BodyDef.BodyType.DynamicBody, gameobject.objecttype.ENEMY, obj, t, false );

                    }
                    this.objects.add( newbrick );


                }
            }

            if (obj.getProperties().containsKey( "mode" )) {
                if (obj.getProperties().get( "mode" ).toString().equalsIgnoreCase( "rpg" )) {
                    world.setGravity( new Vector2( 0f, 0f ) );
                    jumping = false;
                    rpg = true;
                }
            }

        }
    }


}


