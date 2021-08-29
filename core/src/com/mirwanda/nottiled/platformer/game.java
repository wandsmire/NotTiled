package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mirwanda.nottiled.myShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ALLSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEMSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LISTENER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.TOUCHSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.states.DEAD;


public class game {
    public boolean debugmode = false;
    public boolean playtest=true;
    public boolean uitest=false;
    public int orientation;
    public boolean night=false;
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;
    public String message;
    public boolean loadingmap;
    public boolean rpg=false;
    public Vector2 move;
/////////////////////
    float delta;
    public Stage stage;

    SpriteBatch batch, ui;
    myShapeRenderer uis;
    public Skin skin2;
    BitmapFont str1;
    int ssx = 480;
    int ssy = 800;
    int fontsize;

    private Table control;
    private Touchpad tpad;
    private boolean tanalog;
    private boolean tslot1,tslot2,tslot3,tslot4;

    public WorldContactListener mycontactlistener;
    public savegame save = new savegame();

    OrthographicCamera uicam, gamecam;
    Viewport uicamVP, gamecamVP;

    public Vector2 checkpoint = new Vector2();
    public gameobject player;
    public gameobject action1;
    public gameobject action2;
    public gameobject action3;
    public gameobject action4;
    public gameobject touchpoint;

    public List<gameobject> objects = new ArrayList<>();
    public List<particle> particles = new ArrayList<>();
    public List<Sound> sfxs = new ArrayList<>();
    public List<String> sfxpaths = new ArrayList<>();


    public Texture txBackground;
    public TextureRegion[][] hpbar;
    public TextureRegion[][] icons;
    public List<Animation<TextureRegion>> anims;
    public List<Integer> animids;

    //SOUND
    public Music bgm;
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
    public boolean sinker;
    public boolean onplatformh;
    public boolean onplatformv;
    public gameobject currentplatform;
    public int msgindex=0;
    public List<gameobject> requestkill;
    public String peektarget;
    public String transfercam;


    public float jumpinterval=0;
    public float stompinterval=0;
    public float fade=0;
    public float fadein=0;
    public float fadeinmax=10;
    public float fadeout=0;
    public float fadeoutmax=10;
    public boolean transitioning;
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
    public static final short ALLSENSOR_BIT = 1024;
    public static final short TOUCHSENSOR_BIT = 2048;
    public int Tw;
    public int Th;
    public float Tsw;
    public float Tsh;
    public float scale=100f;
    public Color bgcolor=Color.LIGHT_GRAY;

    public void create(){
        batch = new SpriteBatch();
        int assx = Gdx.graphics.getWidth();
        int assy = Gdx.graphics.getHeight();
        if (assx < assy) {
            ssx = assx;
            ssy = assy;
        } else {
            ssx = assy;
            ssy = assx;
        }
        if (ssx < ssy) {

            uicam = new OrthographicCamera();
            gamecam = new OrthographicCamera();
            uicamVP = new StretchViewport( 1920,1080 ,uicam);
            gamecamVP = new StretchViewport( 20,11 ,gamecam);
        } else {
            uicam = new OrthographicCamera();
            gamecam = new OrthographicCamera();
            uicamVP = new StretchViewport( 1080,1920 ,uicam);
            gamecamVP = new StretchViewport( 11, 20 ,gamecam);

        }
        uicamVP.apply(true);
        gamecamVP.apply(true);
        stage = new Stage( new StretchViewport( 3840 ,2160) );


        fontsize = 48;

        ui = new SpriteBatch();
        uis = new myShapeRenderer();
        skin2 = new Skin( Gdx.files.internal( "skins/skin/skin.json" ));


        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FileHandle fileHandl = Gdx.files.internal("languages/characters");
        Map<String, String> vars = new HashMap<>();
        String allstr = fileHandl.readString();
        String[] cumi = allstr.split("\r\n");
        for (String s : cumi) {
            String[] cuma = s.split( ":" );
            vars.put( cuma[0], cuma[1] );
        }

        String language = "English";
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + vars.get( language );
        parameter.borderColor = new Color(.5f, .5f, .5f, .9f);
        parameter.borderWidth = 0;
        parameter.size = fontsize;


        parameter.shadowColor = new Color(0f, 0f, 0f, .9f);
        parameter.shadowOffsetY = 4;
        FreeTypeFontGenerator generator;

        String filenam = "retro.ttf";

        generator = new FreeTypeFontGenerator(Gdx.files.internal(filenam));
        FreeTypeFontGenerator.setMaxTextureSize( 99999 );
        str1 = generator.generateFont(parameter);
        generator.dispose();
        loadTouchpad();

    }


    public boolean initialise(String path, String filename, boolean playtest){
        try {
            log("initialisation started!");
            this.playtest=playtest;
            this.path = path;
            this.file = filename;

            loadingmap = true;
            rpg = false;

            Texture tmp = new Texture( Gdx.files.internal( "platformer/hpbar.png" ) );
            hpbar = TextureRegion.split( tmp,
                    tmp.getWidth(),
                    tmp.getHeight() / 2 );

            initialiseSprites();
            initialiseBox2D();
            if (!initialiseTMX()) return false;
            initialiseMapProperties();
            initialiseTilesets();
            initialiseLayers();

            if (player == null) return false;
            fade=5;
            victory = false;
            loadingmap = false;
            ladder=false;
            touchedladder=0;
            player.body.setGravityScale(1);
            fadein = fadeinmax;
            log("initialisation finished!");

            return true;

        }catch(Exception e){
            log("initialisation failed!");

            return false;
        }
    }

    public boolean initialiseTMX(){
        log("initialising tmx file...");

        try {
            if (playtest) {
                map = new TmxMapLoader( new AbsoluteFileHandleResolver() ).load( path + "/" + file );
            } else {
                map = new TmxMapLoader( new InternalFileHandleResolver() ).load( path + "/" + file );

            }
        } catch (Exception e) {
            return false;
        }
        renderer = new OrthogonalTiledMapRenderer( map );
        return true;

    }
    public void initialiseBox2D(){
        log("initialising box2d...");
        Box2D.init();
        if (world!=null) world.dispose();
        world = new World( new Vector2( 0, -10 ), true );
        b2dr = new Box2DDebugRenderer();
        mycontactlistener = new WorldContactListener( this );
        world.setContactListener( mycontactlistener );
    }
    public void initialiseSprites(){
        log("initialising sprites...");
        objects = new ArrayList<>();
        if (particles.size()>0) particles.clear();
        stateTime = 0f;
        playerTime = 0f;
    }
    public void initialiseMapProperties(){
        log("initialising map properties...");
        Texture tmp;
        MapProperties mpa = map.getProperties();
        if (mpa != null) {

            if (mpa.containsKey( "type" )) {
                String type = (String) mpa.get( "type" );
                if (type.equalsIgnoreCase( "NotTiled platformer" )) {
                    rpg = false;
                }
                if (type.equalsIgnoreCase( "NotTiled rpg" )) {
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

            if (mpa.containsKey( "orientation" )) {
                String orien = (String) mpa.get( "orientation" );
                switch (orien){
                    case "landscape":
                        orientation=1;break;
                    case "portrait":
                        orientation=2;break;
                    default:
                        orientation=0;break;

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

            if (mpa.containsKey( "zoom" )) {
                zoom = Float.parseFloat( mpa.get( "zoom" ).toString() );
            }

            if (mpa.containsKey( "bgcolor" )){
                String cs = mpa.get( "bgcolor" ).toString();
                try {
                    float bgr = (float) Integer.parseInt(cs.substring(0, 2), 16) / 256;
                    float bgg = (float) Integer.parseInt(cs.substring(2, 4), 16) / 256;
                    float bgb = (float) Integer.parseInt(cs.substring(4, 6), 16) / 256;
                    bgcolor = new Color(bgr,bgg,bgb,1f);
                }catch(Exception e){ e.printStackTrace();}
            }

            boolean customicons = false;
            if (mpa.containsKey( "icons" )) {
                String ico = (String) mpa.get( "icons" );
                if (getFile( path + "/" + ico ).exists() && !ico.equalsIgnoreCase( "" )) {

                    if (mpa.containsKey( "iconsize" )) {
                        String isz = (String) mpa.get( "iconsize" );
                        try {
                            String[] isza = isz.split( "," );
                            int isx = Integer.parseInt( isza[0] );
                            int isy = Integer.parseInt( isza[1] );
                            tmp = new Texture( getFile( path + "/" + ico ) );
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

    }
    public void initialiseTilesets(){
        log("initialising tilesets...");
        int total=0;
        anims = new ArrayList<>();
        animids = new ArrayList<>();
        for (TiledMapTileSet tset : map.getTileSets()){
            tset.size();
            for (int i=0;i<tset.size();i++){

                try {
                    AnimatedTiledMapTile at = (AnimatedTiledMapTile) tset.getTile( total+i+1 );

                    StaticTiledMapTile[] frames = at.getFrameTiles().clone();
                    TextureRegion[] walkFrames = new TextureRegion[frames.length];
                    //log(frames.length+"FL");
                    for (int j=0;j<frames.length;j++) {
                        walkFrames[j]=frames[j].getTextureRegion();
                    }

                    Animation<TextureRegion> tempAnim = new Animation<>( at.getAnimationIntervals()[0]/1000f, walkFrames );
                    anims.add( tempAnim );
                    animids.add( total+i+1  );

                }catch(Exception e){
//                        e.printStackTrace();
                }
            }
            total+=tset.size();
        }

    }
    public void initialiseLayers(){
        log("initialising layers...");

        for (MapLayer mlayer : map.getLayers()) {
            log("initialising layer: "+mlayer.getName());

            float opacity = mlayer.getOpacity();

            boolean tilelayer = false;
            boolean objectlayer = false;


            if (mlayer instanceof  TiledMapTileLayer){
                tilelayer = true;

            }else {
                if (!(mlayer instanceof TiledMapImageLayer)) {
                    objectlayer=true;
                }
            }

            if (tilelayer) {
                if (!mlayer.isVisible()) continue;

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
                        //total++;
                        TiledMapTileLayer.Cell cece = tlayer.getCell( xx, yy );
                        if (cece==null)
                        {
                            continue;
                        }

                        //NOTE: do not put exception in a tight loop.
                        float ww;
                        float hh;
                        ww = cece.getTile().getTextureRegion().getRegionWidth();
                        hh = cece.getTile().getTextureRegion().getRegionHeight();
                        setGameObject( false, cece, xx * Tsw, yy * Tsh, ww, hh, over, null, opacity );
                    }
                }
            }
            if (objectlayer) {
                if (!mlayer.isVisible()) continue;
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

    }


    public void log(String s){
        Gdx.app.log( "LOJ",s );
    }

    public boolean checkQual(MapProperties o){
        boolean qual=true;

        if (o.containsKey( "requires")){
            String[] ss = o.get( "requires" ).toString().split( "," );
            qual=false;
            int rq=0;
            for (String s : ss) {
                boolean repeat = true;
                while (repeat) {
                    repeat = false;
                    boolean ada = false;
                    String varname = "";
                    if (s.contains( "=" )) {
                        String[] sv = s.split( "=" );
                        varname = sv[0];
                        for (KV var : save.vars) {
                            if (sv[0].equalsIgnoreCase( var.key )) {
                                ada = true;
                                if (Integer.parseInt( sv[1] ) == var.value) {
                                    rq += 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (s.contains( "!" )) {
                        String[] sv = s.split( "!" );
                        varname = sv[0];

                        String[] plus = sv[1].split( "_" );
                        boolean qualify=true;
                        for (String xxx : plus) {
                            for (KV var : save.vars) {
                                if (sv[0].equalsIgnoreCase( var.key )) {
                                    ada = true;
                                    if (Integer.parseInt(xxx) == var.value) {
                                        qualify=false;
                                    }
                                }
                            }
                        }
                        if (qualify) rq+=1;

                    }
                    if (s.contains( "&lt;" )) {
                        String[] sv = s.split( "&lt;" );
                        varname = sv[0];
                        for (KV var : save.vars) {
                            if (sv[0].equalsIgnoreCase( var.key )) {
                                ada = true;
                                if (var.value < Integer.parseInt( sv[1] )) {
                                    rq += 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (s.contains( "&gt;" )) {
                        String[] sv = s.split( "&gt;" );
                        varname = sv[0];
                        for (KV var : save.vars) {
                            if (sv[0].equalsIgnoreCase( var.key )) {
                                ada = true;
                                if (var.value > Integer.parseInt( sv[1] )) {
                                    rq += 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (!ada) {
                        setOrAddVars( varname, 0, VAROP.SET );
                        repeat = true;
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

    public void drawHUD(SpriteBatch b, BitmapFont str1){
        int index=0;
        for (KV vr: save.vars){
            if (vr.hud){
                if (vr.hasicon) {
                    try {
                        str1.draw( b, "  :  " + vr.value, 90, 1080 - 35 - 60 * index );
                        b.draw( icons[vr.icony][vr.iconx], 50, 1080 - 80 - 60 * index, 48, 48 );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    str1.draw( b, vr.key+" : " + vr.value, 50,1080-35-60*index);
                }
                index++;
            }
        }

        if (message!=null) str1.draw( ui, message, 0, 1080-35, 1920, Align.center, true );

        float input=timer;
        int MM;
        int SS;
        int MS;

        MM = (int) input / 60;
        SS = (int) (input % 60 ) % 60  ;
        MS = ((int) (input*100))-(((int) input)*100);

        String sMM = MM<10 ? "0"+MM : ""+MM;
        String sSS = SS<10 ? "0"+SS : ""+SS;
        String sMS = MS<10 ? "0"+MS : ""+MS;

        String time = sMM+":"+sSS+"."+sMS;

        str1.getData().setScale( 0.8f );

        if (timer>0) str1.draw( ui, time, 1920 - 300, 1080 - 35, 200, Align.left, false );

    }


    public float getVar(String key){
        if (key==null) return 0;
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

    public float killtimer;
    public float peektimer;
    public int salto=0;
    public boolean recoil;
    public OrthographicCamera gc;
    public float zoom=0.2f;
    public List<gameobject> overs = new ArrayList<>();
    public List<gameobject> deads = new ArrayList<>();
    public float accumulator;
    public float objaccumulator;
    public float TIME_STEP=1/60f;
    public float OBJ_STEP=1/60f;
    public boolean updatephysics;
    public boolean updategameobject;

    public void update(SpriteBatch batch, float delta) {
        if (loadingmap) return;
        updateFixedTimeStamp();
        updatePlayerMovement();
        checkGameCondition();
        checkHUD();
        updateFadeAndTransfer();
        updatePlayerSprite(delta);
        updateGameObjects();
    }

    private void updateFixedTimeStamp() {
        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            if (!starting && player.state != DEAD) world.step( TIME_STEP, 6, 2 );
            accumulator -= TIME_STEP;
            updatephysics=true;
        }
    }

    private void checkGameCondition() {
        if (timetrial) timer+=delta;
        if (player.HP <= 0) killPlayer();
        if (player.HP > player.maxHP) player.HP = player.maxHP;

        if (autorespawn>0){
            autorespawn-=delta;
            if (autorespawn<=0) respawn();
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
    }

    private void updateGameObjects() {
        //update objects
        overs.clear();deads.clear();
        updategameobject=false;
        objaccumulator += delta;
        int times =0;
        //timestamp 1/60f
        while (objaccumulator >= OBJ_STEP) {
            objaccumulator -= OBJ_STEP;
            times+=1;
            updategameobject=true;
        }

        for (int i=0;i<objects.size();i++){
            gameobject go = objects.get(i);
            if(loadingmap) return;
            if (updatephysics) go.updatePhysics( delta );
            if (updategameobject) go.update( times ,delta);
            if (go.state==DEAD) deads.add( go );
        }

        for (gameobject go:deads)
        {
            go.removeCollision();
            objects.remove( go );
        }

        for (int i = particles.size() - 1; i >= 0; i--){
            particle pe = particles.get(i);
            pe.update( Gdx.graphics.getDeltaTime() );
            if (pe.isComplete()) particles.remove( pe );
        }

        if (updategameobject) {
            if (action1 != null) action1.update(1, delta );
            if (action2 != null) action2.update(1, delta );
            if (action3 != null) action3.update(1, delta );
            if (action4 != null) action4.update(1, delta );
        }

    }

    private void drawObjectsAndParticles(){
        if (loadingmap) return;
        for (gameobject go: objects){
            if (!go.over & go!=player) {
                if (go.getTexture() != null) go.draw( batch );
            }
            if (go.HP > 0 && go.HP !=go.maxHP) {
                batch.draw( hpbar[1][0], go.getX(), go.getY() + go.getHeight() + 0.01f, go.getWidth(), hpbar[1][0].getRegionHeight() / scale );
                batch.draw( hpbar[0][0], go.getX(), go.getY() + go.getHeight() + 0.01f, go.HP / go.maxHP * go.getWidth(), hpbar[0][0].getRegionHeight() / scale );
            }

        }

        if (player.state!=DEAD) player.draw( batch );

        for (gameobject sboxes:overs)
        {
            if (sboxes.getTexture()!=null ) sboxes.draw(batch);
        }

        for (particle pe : particles){
            pe.draw( batch );
        }

    }
    private void updateFadeAndTransfer() {
        //fade and transfer
        if (transformrequest) fullfilltransformrequest();

        float posex = player.body.getPosition().x;
        float posey = player.body.getPosition().y;

        if (fadeout>0){
            fadeout--;
            if (fadeout == 0) {
                initialise( fadepath,fademapname ,playtest);
                return;
            }
        }

        if (fade>0){
            fade--;
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
                position.y = posey;

                if (transfercam!=null){
                    gameobject gj =null;
                    for (gameobject go : objects){
                        if (go.id.equalsIgnoreCase( transfercam )){
                            gj=go;
                        }
                    }
                    if (gj!=null){
                        gamecam.position.set( gj.body.getPosition().x,gj.body.getPosition().y,0 );
                    }
                    peektarget=transfercam;
                    peektimer=-1;
                    transfercam=null;
                }
                gamecam.update();

            }else{
                return;
            }
        }

        if (peektarget!=null){
            if (peektimer!=-1 && peektimer!=-2) peektimer-=delta;

            gameobject gj =null;
            for (gameobject go : objects){
                if (go.id.equalsIgnoreCase( peektarget )){
                    gj=go;
                }
            }
            if (gj!=null){
                posex = gj.body.getPosition().x;
                posey = gj.body.getPosition().y;
            }

            if (peektimer<=0 && peektimer!=-1 && peektimer!=-2){
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


        if (fadein>0) {
            transitioning=false;
            fadein--;
            if (fadein==0){
                if (bgm != null) {
                    if (!bgm.isPlaying()) bgm.play();
                }
            }
        }
        // end of fade and trasnsfer

    }

    //this happens after the keyinput, means it will override keyinput,
    // which means it would be better to put it before the physics
    //but it will correct the bug that make player speed faster
    //so I don't care... heheh
    public void updatePlayerMovement(){
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

        //reduce
        if (player.body.getLinearVelocity().y<-player.speed*3) player.body.setLinearVelocity( player.body.getLinearVelocity().x,-player.speed*3 );

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
            playSfx(player.sfxdead);
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

    }
    public void updatePlayerSprite(float delta){
        if (starting){
            if (player.anim.size()>1) {
                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( 0, true );
                player.setRegion( currentFramea );
            }else if (player.anim.size()==1) {
                TextureRegion currentFramea = player.anim.get(0).getKeyFrame( 0, true );
                player.setRegion( currentFramea );
            }
        }

        //moving
        else if (player.moving && (!jumping || onplatformv) && (!ladder && !floater && !sinker))
        {
            playerTime +=delta;
            if (jetpack) return;
            if (player.anim.size()>1) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
            }else if (player.anim.size()==1) { //just one anim

                TextureRegion currentFramea = player.anim.get(0).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
                player.setOriginCenter();

                if (!rpg){

                    switch (player.dir) {
                        case 0: //down
                            break;
                        case 1: //right
                            player.setFlip( false, false );
                            break;
                        case 2: //left
                            player.setFlip( true, false );
                            break;
                        case 3: //up
                            break;
                    }
                }
            }else{ //no anim


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
                                player.setFlip( false, false );
                                break;
                            case 2: //left
                                player.setFlip( true, false );
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
            if (player.anim.size()>1) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( 0.25f, true );
                player.setRegion( currentFramea );

            }
            else if (player.anim.size()==1) {

                TextureRegion currentFramea = player.anim.get(0).getKeyFrame( 0.25f, true );
                player.setRegion( currentFramea );

                if (!rpg){

                    switch (player.dir) {
                        case 0: //down
                            break;
                        case 1: //right
                            player.setFlip( false, false );
                            break;
                        case 2: //left
                            player.setFlip( true, false );
                            break;
                        case 3: //up
                            break;
                    }
                }

            }
            if (rpg) jumping=false;

        }
        else if ((ladder || floater || sinker) && player.moving )
        {
            if (ladder && rpg) player.dir=3;

            playerTime +=delta;
            if (player.anim.size()>1) {

                TextureRegion currentFramea = player.anim.get( player.dir ).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
            }
            else if (player.anim.size()==1) {

                TextureRegion currentFramea = player.anim.get(0).getKeyFrame( playerTime, true );
                player.setRegion( currentFramea );
            }

        }else
        {
            //idle animation if you will...

            if (player.anim.size()>0) {
                TextureRegion currentFramea;
                if (player.anim.size()==1){
                    currentFramea = player.anim.get( 0 ).getKeyFrame( 0, true );
                    player.setRegion( currentFramea );

                    switch (player.dir){
                        case 0: case 3:
                            break;
                        case 1:
                            player.setFlip( false,false );
                            break;
                        case 2:
                            player.setFlip( true,false );
                            break;
                    }
                }else{
                    currentFramea = player.anim.get( player.dir ).getKeyFrame( 0, true );
                    player.setRegion( currentFramea );
                }
            }
        }

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

                player.body.setLinearVelocity( 0, player.speed );
                return;
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
                player.body.setLinearVelocity( 0, -player.speed );
                return;
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

        if (ladder || floater || sinker) {
            player.body.setLinearVelocity(-player.speed,0);
            return;
        }
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
        if (ladder || floater || sinker) {
            player.body.setLinearVelocity(player.speed,0);
            return;
        }
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
           // } else if (onplatformv) {
                //player.body.setLinearVelocity(player.body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
            }
        }else
        {
            player.moving = false;
            player.body.setLinearVelocity( 0, 0 );
            if (onplatformh) {
                player.body.setLinearVelocity( currentplatform.body.getLinearVelocity().x, player.body.getLinearVelocity().y );
            //} else if (onplatformv) {
                //player.body.setLinearVelocity(player.body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
            }

        }
    }

    public void respawn(){
        load();
    }

    public boolean disablecontrol = false;
    public boolean enabletouch = false;
    public boolean timetrial = false;
    public float timer = 0f;
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
        switch (go.action){
            case NONE:
                break;
            case JUMP: //jumping ga usah pakai cooldown
                if (go.cooldown>0) return;

                if (!jumping) {
                    player.body.applyLinearImpulse( 0f, gravity*go.impulse, player.getX(), player.getY(), true );
                    playSfx( go.sfx );

                    jumping = true;
                    go.cooldown=go.pcooldown;

                }

                break;
            case JETPACK: //jumping ga usah pakai cooldown
                if (go.cooldown>0) return;
                playSfx( go.sfx );
                jetpack = true;
                //    jetpackcooldown = 0.1f;
                player.body.applyLinearImpulse( 0f, gravity*go.impulse, player.getX(), player.getY(), true );
                jumping = true;
                go.cooldown=go.pcooldown;


                break;
            case DASH:

                if (jumping) {
                    if (!dashed){
                        if (go.cooldown>0) return;
                    dashed=true;
                        playSfx( go.sfx );

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
                        go.cooldown=go.pcooldown;

                    }
                }
                //jumping = true;

                break;
            case SHOOT:
                if (go.cooldown>0) return;
                playSfx( go.sfx );

                gameobject newbrick = new gameobject();
                newbrick.mygame = this;
                newbrick.speed=go.pspeed;
                newbrick.maxdistance=go.pmaxdistance;
                newbrick.spread=go.pspread-(2*(float)Math.random()*go.pspread);
                newbrick.damage=go.pdamage;
                newbrick.dir=player.dir;
                newbrick.anim=go.panim;
                newbrick.deadtileID=go.pdeadanim;
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
            default:
                throw new IllegalStateException( "Unexpected value: " + go.action );
        }

        if (go.bindvar!=null){
            if (getVar( go.bindvar )<=0) {
                go.action= gameobject.actions.NONE;
            }else{
                setOrAddVars( go.bindvar,1, VAROP.SUB );
            }

        }



    }


    public void save(){
        save.mapname=file;
        save.x=player.body.getPosition().x;
        save.y=player.body.getPosition().y;
        Json json = new Json();
        FileHandle file = Gdx.files.local(path + "/save.json");
        file.writeString(json.prettyPrint(save), false);
    }


    public String fadepath;
    public String fademapname;
    public void fadeinitialise(String path, String mapname){
        if (fadeout>0) return;
        fadepath=path;
        fademapname=mapname;
        fadeout=fadeoutmax;
        //log(fadeout+"");
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

    float autorespawn;

    public void killPlayer(){
        if (player.state!= DEAD && !victory && !starting)
        {
            playSfx(player.sfxdead);
            dead+=1;
            player.bumbum();
            player.state= DEAD;
            autorespawn=1f;
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
    int emptytile;
    public void setGameObject(boolean object, TiledMapTileLayer.Cell cece, float xx, float yy, float ww, float hh, boolean over, MapObject obj, float opacity){

        if (!object && cece==null)
        {
            emptytile+=1;
            log("Empty tiles:"+ emptytile);
            return; //empty tile

        }
        MapProperties o;
        MapObject objx = null;
        TextureRegion t = null;
        TiledMapTile tlcece=null;
        gameobject newbrick = new gameobject();
        boolean flipX=false,flipY=false;
        float rota=0f;
        TiledMapTileMapObject tmo;
        if (!object) { //tile
            tlcece = cece.getTile();
            o=tlcece.getProperties();
            flipX = cece.getFlipHorizontally();
            flipY = cece.getFlipVertically();
            rota = cece.getRotation();

            for (int i=0;i<animids.size();i++) {
                if (animids.get( i ) == tlcece.getId()) {
                    newbrick.anim.add( anims.get( i )) ;
                }
            }

        }else { //object
            objx=obj;
            if (obj.getName()!=null){
                newbrick.id = obj.getName();
               // log(newbrick.id);
            }
            o = obj.getProperties();
            if (obj instanceof TiledMapTileMapObject) {
                tmo = ((TiledMapTileMapObject) obj);
                for (int i=0;i<animids.size();i++) {
                    if (animids.get( i ) == tmo.getTile().getId()) {
                        newbrick.anim.add( anims.get( i )) ;
                    }
                }

                t = tmo.getTextureRegion();
            }
        }



        newbrick.mygame = this;

        newbrick.damage = (o.containsKey( "damage" )) ? Float.parseFloat( o.get( "damage" ).toString() ) : 0f;
        newbrick.rotating = o.containsKey( "rotating" );
        newbrick.destructible = o.containsKey( "destructible" );
        newbrick.light = (o.containsKey( "light" )) ? Float.parseFloat( o.get( "light" ).toString() ) : 0;

        if (o.containsKey( "lightcolor" )){
            String cs = o.get( "lightcolor" ).toString();
                float bgr = (float) Integer.parseInt( cs.substring( 0, 2 ), 16 ) / 256;
                float bgg = (float) Integer.parseInt( cs.substring( 2, 4 ), 16 ) / 256;
                float bgb = (float) Integer.parseInt( cs.substring( 4, 6 ), 16 ) / 256;
                newbrick.lightColor = new Color( bgr, bgg, bgb, 1f );
        }
        if (o.containsKey( "bgcolor" )){
            String cs = o.get( "bgcolor" ).toString();
                float bgr = (float) Integer.parseInt(cs.substring(0, 2), 16) / 256;
                float bgg = (float) Integer.parseInt(cs.substring(2, 4), 16) / 256;
                float bgb = (float) Integer.parseInt(cs.substring(4, 6), 16) / 256;
                newbrick.bgcolor = new Color(bgr,bgg,bgb,1f);
        }
        if (o.containsKey( "color" )){
            String cs = o.get( "color" ).toString();

                float bgr = (float) Integer.parseInt( cs.substring( 0, 2 ), 16 ) / 256;
                float bgg = (float) Integer.parseInt( cs.substring( 2, 4 ), 16 ) / 256;
                float bgb = (float) Integer.parseInt( cs.substring( 4, 6 ), 16 ) / 256;
                newbrick.color = new Color( bgr, bgg, bgb, 1f );

        }



        newbrick.HP = (o.containsKey( "HP" )) ? Integer.parseInt( o.get( "HP" ).toString() ) : 1;
        newbrick.maxHP=newbrick.HP;
        newbrick.heavy = o.containsKey( "heavy" );

        if (o.containsKey( "sfx" )) {
                String sfx = o.get("sfx").toString();
                newbrick.sfx = addSfx( path + "/" + sfx );
            }

        if (o.containsKey( "psfx" )) {
            String sfx = o.get("psfx").toString();
            newbrick.psfx = addSfx( path + "/" + sfx );
        }

        if (o.containsKey( "sfxdead" )) {
            String sfx = o.get("sfxdead").toString();
            newbrick.sfxdead = addSfx( path + "/" + sfx );

        }

        if (o.containsKey( "size" )) {
            String[] sz = o.get("size").toString().split( "," );
            ww=Float.parseFloat(sz[0]);
            hh=Float.parseFloat(sz[1]);
        }

        if (o.get("name")!=null) {
            newbrick.id=o.get("name").toString();
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
                int k;
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
            //rayHandler=new RayHandler( world);
            //rayHandler.setAmbientLight( 1,1,1,0.3f);

        }

        if (o.containsKey( "zoom" )) {
            zoom = Float.parseFloat( o.get( "zoom" ).toString() );
        }

        if (o.containsKey( "gravity" )) {
            world.setGravity( new Vector2(0, -Float.parseFloat( o.get( "gravity" ).toString()) ));
        }

        if (o.containsKey( "dir" )) {
            String dir = o.get( "dir" ).toString();
            switch (dir){
                case "down":
                    newbrick.dir =1;
                    break;
                case "left":
                    newbrick.dir =2;
                    break;
                case "right":
                    newbrick.dir =3;
                    break;
                case "up":
                    newbrick.dir =4;
                    break;
                default:
                    newbrick.dir = Integer.parseInt( o.get( "dir" ).toString());
                    break;
            }
            newbrick.face = newbrick.dir;
        }

        newbrick.tameif = (o.containsKey( "tameif" )) ?  o.get( "tameif" ).toString() : "";

        newbrick.wait = (o.containsKey( "wait" )) ? Float.parseFloat( o.get( "wait" ).toString() ) : 1f;
        newbrick.speed = (o.containsKey( "speed" )) ? Float.parseFloat( o.get( "speed" ).toString() ) : 1f;
        newbrick.chase = o.containsKey( "chase" );
        newbrick.chaseRadius = (o.containsKey( "chaseRadius" )) ? Float.parseFloat( o.get( "chaseRadius" ).toString() ) : 100f;
        newbrick.deadtileID = (o.containsKey( "deadanim" )) ? Integer.parseInt( o.get( "deadanim" ).toString() ) : 0;
        newbrick.pdeadanim = (o.containsKey( "pdeadanim" )) ? Integer.parseInt( o.get( "pdeadanim" ).toString() ) : 0;
        newbrick.stepping = o.containsKey( "stepping" );
        newbrick.canshoot = o.containsKey( "canshoot" );
        newbrick.dirlocked = o.containsKey( "dirlocked" );
        newbrick.pspread = (o.containsKey( "pspread" )) ? Float.parseFloat( o.get( "pspread" ).toString() ) : 0;
        newbrick.pcooldown = (o.containsKey( "pcooldown" )) ? Float.parseFloat( o.get( "pcooldown" ).toString() ) : 1f;
        newbrick.pspeed = (o.containsKey( "pspeed" )) ? Float.parseFloat( o.get( "pspeed" ).toString() ) : 4;
        newbrick.pmaxdistance = (o.containsKey( "pmaxdistance" )) ? Integer.parseInt( o.get( "pmaxdistance" ).toString() ) : 300;
        newbrick.pdamage = (o.containsKey( "pdamage" )) ? Integer.parseInt( o.get( "pdamage" ).toString() ) : 1;


        if (o.containsKey( "object" )) {
            switch (o.get( "object" ).toString()) {
                case "player":
                    if (o.containsKey( "anim" )){
                    String anim = o.get( "anim" ).toString();
                        newbrick.anim.clear();
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
                            Animation<TextureRegion> tempAnim = new Animation<>( 0.1f, walkFrames );
                            newbrick.anim.add( tempAnim );
                        }
                    }

                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLAYER, objx, t, over ,opacity);
                    player = newbrick;

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
                case "touch":
                    if (checkQual( o)) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, TOUCHSENSOR, objx, t, over  ,opacity);
                        touchpoint=newbrick;
                    }
                    break;
                case "nuke":
                    if (checkQual( o)) {
                        newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.DynamicBody, ALLSENSOR, objx, t, over  ,opacity);
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

                    if (o.containsKey( "anim" )) {



                        String anim = o.get( "anim" ).toString();
                        Texture txMonster = new Texture( getFile( path + "/" + anim ) );
                        newbrick.anim.clear();
                        TextureRegion[][] tmp = TextureRegion.split( txMonster,
                                txMonster.getWidth() / 4,
                                txMonster.getHeight() / 4 );
                        imgsize = new Vector2( txMonster.getWidth() / 4f, txMonster.getHeight() / 4f );
                        for (int i = 0; i < 4; i++) {
                            TextureRegion[] walkFrames = new TextureRegion[4];
                            int index = 0;
                            for (int j = 0; j < 4; j++) {
                                walkFrames[index++] = tmp[i][j];
                            }
                            Animation<TextureRegion> tempAnim = new Animation<>( 0.1f, walkFrames );
                            newbrick.anim.add( tempAnim );
                        }


                    }

                    Texture txMonster;

                    if (newbrick.canshoot) {
                        if (o.containsKey( "panim" )) {
                            String anim = o.get( "panim" ).toString();
                            txMonster = new Texture( getFile( path + "/" + anim ) );
                        } else {
                            txMonster = new Texture( Gdx.files.internal( "platformer/eshoot.png" ) );
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

                        int panimID;
                        if (o.containsKey( "panim" )){
                            String pnm=o.get( "panim" ).toString();
                            if (onlyDigits( pnm,pnm.length() )) {
                                panimID = Integer.parseInt( o.get( "panim" ).toString() );
                                if (animids.size() > 0) {
                                    for (int i = 0; i < animids.size(); i++) {
                                        if (animids.get( i ) == panimID) {
                                            newbrick.panim.clear();
                                            newbrick.panim.add( anims.get( i ) );
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }


                    newbrick.mygame = this;
                    if (newbrick.heavy) {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.KinematicBody, gameobject.objecttype.MONSTER, objx, t, over  ,opacity);
                    } else {
                        newbrick.setupGameObject( world, tlcece, (int) xx, (int) yy, imgsize.x, imgsize.y, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER, objx, t, over  ,opacity);

                    }

            }


        }else{ //n tile info.
            if (!o.getKeys().hasNext()) {
                newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC, objx, t, over, opacity );
            }else{
                newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.KinematicBody, ITEM, objx, t, over  ,opacity);
            }
        }

        newbrick.flip( flipX,flipY );
        if (rota != 0) newbrick.setRotation( rota * 90 );
        objects.add( newbrick );
        if (!object) cece.setTile( null );

    }

    public static boolean onlyDigits(String str, int n)
    {
        for (int i = 0; i < n; i++)
            if (!(str.charAt( i ) >= '0'
                    && str.charAt( i ) <= '9')) {
                return false;
            }
        return true;
    }

    public Sound addSfx(String path){
        boolean ada =false;
        String sfxpath;
        Sound s = null;
        for (int i=0;i<sfxpaths.size();i++){
            sfxpath = sfxpaths.get( i );
            if (sfxpath.equalsIgnoreCase( path )){
                ada=true;
                s=sfxs.get(i);
            }
        }

        if (ada){
            return s;
        }else{
            if (getFile(path).exists()){
                s = Gdx.audio.newSound( getFile( path) );
                sfxs.add( s );
                sfxpaths.add( path );
                return s;
            }
            return null;
        }
    }

    public void resize(int width,int height){
        uicamVP.update( width,height );
        gamecamVP.update( width,height );
        stage.getViewport().update( width,height );
    }

    public void startgame(String curdir,String filex, boolean playtest){
        if (initialise(curdir, filex,playtest)) {
            gamecam.zoom = 0.2f;
            gamecam.position.set( player.body.getPosition().x, player.body.getPosition().y, 0 );
            gamecam.update();
            stage.clear();
            stage.addActor(control);
            Gdx.input.setInputProcessor(stage);
        }

    }

    boolean shutdown = false;

    public boolean render(){
        if (shutdown) return false;
        if (player==null) return false;

        delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        if (gamecam.zoom != zoom) gamecam.zoom = zoom;

        //update keystroke and logic
        keyinput();
        update( batch, delta);

        //draw bgcolor
        Gdx.gl.glClearColor( bgcolor.r,bgcolor.g,bgcolor.b,bgcolor.a);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        //draw background
        batch.setProjectionMatrix( gamecam.combined );
        batch.begin();
        if (txBackground != null) batch.draw( txBackground, gamecam.position.x - 2f, gamecam.position.y - 2f, 4, 4 );
        batch.end();

        //draw tilemaps
        renderer.setView( gamecam );
        renderer.render();

        //draw game objects
        batch.setProjectionMatrix( gamecam.combined );
        batch.begin();
        drawObjectsAndParticles();
        batch.end();

        //draw box2d debug if needed
        if (debugmode) b2dr.render( world, gamecam.combined );

        //draw HUD
        ui.setProjectionMatrix( uicam.combined );
        ui.begin();
        drawHUD( ui, str1);
        ui.end();

        //draw virtual Controller
        if (Gdx.app.getType() != Application.ApplicationType.Desktop || uitest) {
            if (!starting && player.state != gameobject.states.DEAD  && fade==0) {
                if (!disablecontrol) {
                    stage.act( delta );
                    stage.draw();
                }
            }
        }

        //draw fade effect
        uis.setProjectionMatrix( uicam.combined );
        uis.begin( ShapeRenderer.ShapeType.Filled );
        Gdx.gl.glEnable( GL20.GL_BLEND );
        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
        if (loadingmap || transitioning) {
            uis.setColor( 0f, 0f, 0, 1f );
            uis.rect( 0, 0, 1920, 1080 );
        }
        if (fadein>0) {
            uis.setColor( 0f, 0f, 0, fadein / fadeinmax );
            uis.rect( 0, 0, 1920, 1080 );
        }
        if (fadeout>0) {
            uis.setColor( 0f, 0f, 0, 1-((fadeout-1) / fadeoutmax) );
            uis.rect( 0, 0, 1920, 1080 );
        }
        uis.end();

        //tell the boss that drawing is completed
        return true;
    }

    private void loadTouchpad(){

        Button slot1 = new Button( skin2 );
        Button slot2 = new Button( skin2 );
        Button slot3 = new Button( skin2 );
        Button slot4 = new Button( skin2 );

        slot1.addListener( new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                tslot1 = false;
            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                tslot1 = true;
                return true;
            }

        });

        slot2.addListener( new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                tslot2 = false;
            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                tslot2 = true;
                return true;
            }

        });

        slot3.addListener( new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                tslot3 = false;
            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                tslot3 = true;
                return true;
            }

        });

        slot4.addListener( new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                tslot4 = false;
            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                tslot4 = true;
                return true;
            }

        });


        tpad = new Touchpad(20, skin2);
        tpad.setBounds(15, 15, 100, 100);

        tpad.addListener( new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                tanalog = false;
            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                tanalog = true;
                return true;
            }

        });

        control = new Table();
        control.setFillParent( true );
        Table action = new Table();
        control.padTop(1000);
        control.add( tpad );
        control.add().pad(1000);
        action.add();
        action.add( slot4 );
        action.add();
        action.row();
        action.add( slot3 );
        action.add();
        action.add( slot1 );
        action.row();
        action.add();
        action.add( slot2 );
        action.add();
        control.add( action );
    }

    void keyinput() {

        if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE )) escapegame();
        if (Gdx.input.isKeyJustPressed( Input.Keys.BACK )) escapegame();
        if (player.state == gameobject.states.DEAD) return;
        if (!disablecontrol) {
            boolean pressed = false;

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                tslot1 = Gdx.input.isKeyPressed( Input.Keys.X );
                tslot2 = Gdx.input.isKeyPressed( Input.Keys.C );
                tslot3 = Gdx.input.isKeyPressed( Input.Keys.D );
                tslot4 = Gdx.input.isKeyPressed( Input.Keys.S );
            }

            if (Gdx.input.isKeyPressed( Input.Keys.RIGHT )) {
                pressright();
                pressed = true;
            }
            if (Gdx.input.isKeyPressed( Input.Keys.LEFT )) {
                pressleft();
                pressed = true;
            }
            if (Gdx.input.isKeyPressed( Input.Keys.UP )) {
                pressup();
                pressed = true;
            }
            if (Gdx.input.isKeyPressed( Input.Keys.DOWN )) {
                pressdown();
                pressed = true;
            }


            if (tslot1 && action1 != null && action1.action != gameobject.actions.NONE) {
                act( action1 );
                pressed = true;
            }
            if (tslot2 && action2 != null && action2.action != gameobject.actions.NONE) {
                act( action2 );
                pressed = true;
            }
            if (tslot3 && action3 != null && action3.action != gameobject.actions.NONE) {
                act( action3 );
                pressed = true;
            }
            if (tslot4 && action4 != null && action4.action != gameobject.actions.NONE) {
                act( action4 );
                pressed = true;
            }

            if (tanalog) {
                float deltaX = tpad.getKnobPercentX();
                float deltaY = tpad.getKnobPercentY();
                if (deltaY > 0.4f) {
                    pressup();
                    pressed = true;
                }
                if (deltaY < -0.4f) {
                    pressdown();
                    pressed = true;
                }

                if (deltaX > 0.4f) {
                    pressright();
                    pressed = true;
                }
                if (deltaX < -0.4f) {
                    pressleft();
                    pressed = true;
                }

            }
            if (!pressed) stand();

        }

        if (enabletouch) {
            //touchpoint for later purposes
            for (int i = 0; i < 5; i++) { // 20 is max number of touch points
                if (Gdx.input.isTouched( i )) {
                    Vector3 touch2 = new Vector3();
                    gamecam.unproject( touch2.set( Gdx.input.getX( i ), Gdx.input.getY( i ), 0 ) );
                    if (touchpoint != null) touchpoint.body.setTransform( touch2.x, touch2.y, 0 );
                }
            }
        }


    }

    public void dispose(){
        batch.dispose();
    }

    public void escapegame(){
        if (bgm!=null && bgm.isPlaying()) bgm.stop();
        shutdown=true;
    }

}


