package com.mirwanda.nottiled.platformer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mirwanda.nottiled.myShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ALLSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.BLOCK;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEMSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.LISTENER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.PLAYERPROJECTILE;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.TOUCHSENSOR;
import static com.mirwanda.nottiled.platformer.gameobject.states.DEAD;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.util.Enumeration;
import java.net.SocketException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class game implements ControllerListener {
    public com.mirwanda.nottiled.Interface face;
    public boolean debugmode = false;
    public boolean playtest=true;
    public boolean hasMainMenu = false;
    public boolean isMainMenuActive = false;
    public boolean hasStartedPlaying = false;
    public String gameName = "";
    public boolean isMultiplayerEnabled = false;
    public boolean isMultiplayerActive = false;
    public boolean isHost = false;
    public String playerId = "Player_" + (int)(Math.random()*10000);
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
    public boolean camlockx;
    public boolean camlocky;
/////////////////////
    float delta;
    public Stage stage,stage2;

    SpriteBatch batch, ui;
    myShapeRenderer uis;
    public Skin skin2, skin;
    BitmapFont str1;
    int ssx = 480;
    int ssy = 800;
    int fontsize;

    private Touchpad tpad;
    private Table actionPad;
    private Table actionButtonLayout;
    private boolean fixedActionButtons;
    private int analogTouchPointer = -1;
    private int actionTouchPointer = -1;
    private float actionDragOriginX;
    private float actionDragOriginY;
    private boolean actionSwipeCommitted;
    private static final float ACTION_SWIPE_THRESHOLD = 60f;
    private boolean tslot1,tslot2,tslot3,tslot4;

    public WorldContactListener mycontactlistener;
    public savegame save = new savegame();

    OrthographicCamera uicam, gamecam;
    Viewport uicamVP, gamecamVP;

    public Vector2 checkpoint = new Vector2();
    public gameobject player;
    public boolean hasPlayerError = false;
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
    public String messagePicturePath;
    public String messageSpeaker;
    public String[] messageSounds;
    private Texture messageImage;
    private InputProcessor messageInput;
    private Music messageMusic;
    private Sound messageSound;
    private long messageSoundId = -1;
    public boolean jumping = false;
    public boolean ladder;
    public int groundContacts = 0;
    public int touchedladder;
    public int touchedbridge;
    public float dropThroughTimer;
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
    /** Fixture userData marker for the player foot sensor. */
    public static final Object FOOT_SENSOR = new Object();
    public int Tw;
    public int Th;
    public float Tsw;
    public float Tsh;
    public float scale=100f;
    public Color bgcolor=Color.LIGHT_GRAY;
    public float shooting;

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
        uicam = new OrthographicCamera();
        gamecam = new OrthographicCamera();
        if (assx >= assy) {
            uicamVP = new StretchViewport( 1920, 1080, uicam );
            gamecamVP = new com.badlogic.gdx.utils.viewport.FitViewport( 20, 11, gamecam );
            stage = new Stage( new ExtendViewport( 3840, 2160 ) );
            stage2 = new Stage( new ExtendViewport( 1920, 1080 ) );
        } else {
            uicamVP = new StretchViewport( 1080, 1920, uicam );
            gamecamVP = new com.badlogic.gdx.utils.viewport.FitViewport( 11, 20, gamecam );
            stage = new Stage( new ExtendViewport( 2160, 3840 ) );
            stage2 = new Stage( new ExtendViewport( 1080, 1920 ) );
        }

        ui = new SpriteBatch();
        uis = new myShapeRenderer();

        loadFontAndSkin();
        loadTouchpad();
        Controllers.addListener(this);
    }

    public void loadFontAndSkin() {
        if (str1 != null) {
            str1.dispose();
            str1 = null;
        }
        if (skin != null) {
            skin.dispose();
            skin = null;
        }
        if (skin2 != null) {
            skin2.dispose();
            skin2 = null;
        }

        int assx = Gdx.graphics.getWidth();
        int assy = Gdx.graphics.getHeight();
        if (assx < assy) {
            ssx = assx;
            ssy = assy;
        } else {
            ssx = assy;
            ssy = assx;
        }
        fontsize = 48;
        if (ssx < ssy) {
            if (fontsize == 0) fontsize = 48 * ssx / 1080;
        } else {
            if (fontsize == 0) fontsize = 48 * ssy / 1080;
        }

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = new Color( .5f, .5f, .5f, .9f );
        parameter.borderWidth = 0;
        parameter.size = fontsize;
        parameter.shadowColor = new Color( 0f, 0f, 0f, .9f );
        parameter.shadowOffsetY = 4;
        parameter.incremental = true;
        parameter.packer = new PixmapPacker(2048, 2048, Pixmap.Format.RGBA8888, 2, false);

        String language = "English";

        String[] fallbackFontNames = null;

        if (language.equalsIgnoreCase( "Japanese" )) {
            fallbackFontNames = new String[] {"japanese.otf"};
        }else if(language.equalsIgnoreCase( "Chinese" )){
            fallbackFontNames = new String[] {"chinese.ttf"};
        }

        final HashMap<String, BitmapFont> fallbackFonts;
        if (fallbackFontNames!=null) {
            fallbackFonts = new HashMap<>();
            for (String fallbackFontName : fallbackFontNames) {
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fallbackFontName));
                BitmapFont font = generator.generateFont(parameter);
                fallbackFonts.put(fallbackFontName, font);
            }
        } else {
            fallbackFonts = null;
        }

        FileHandle fontFile = null;
        if (path != null) {
            fontFile = getFile(path + "/font.ttf");
        }
        if (fontFile == null || !fontFile.exists()) {
            fontFile = Gdx.files.internal("retro.ttf");
        }

        FreeTypeFontGenerator baseFontGenerator = new FreeTypeFontGenerator(fontFile);

        if (fallbackFonts!=null) {
            FreeTypeFontGenerator.FreeTypeBitmapFontData fallbackData = new FreeTypeFontGenerator.FreeTypeBitmapFontData() {
                @Override
                public BitmapFont.Glyph getGlyph(char ch) {
                    BitmapFont.Glyph glyph = super.getGlyph(ch);
                    if (glyph != null)
                        return glyph;

                    for (BitmapFont font : fallbackFonts.values()) {
                        glyph = font.getData().getGlyph(ch);
                        if (glyph != null) {
                            return glyph;
                        }
                    }

                    return null;
                }
            };

            str1 = baseFontGenerator.generateFont(parameter, fallbackData);
        }else{
            str1 = baseFontGenerator.generateFont(parameter);
        }
        skin = new Skin();
        skin2 = new Skin( Gdx.files.internal( "skins/skin/skin.json" ));
        skin.add( "font", str1, BitmapFont.class );

        FileHandle fileHandle = Gdx.files.internal( "skins/holo/Holo-dark-hdpi.json" );
        FileHandle atlasFile = fileHandle.sibling( "Holo-dark-hdpi.atlas" );

        if (atlasFile.exists()) {
            skin.addRegions( new TextureAtlas( atlasFile ) );
        }

        skin.load( fileHandle );
    }


    public boolean initialise(String path, String filename, boolean playtest){
        try {
            log("initialisation started!");
            if (netServer != null) {
                try { netServer.stop(); } catch(Exception ignored) {}
                netServer = null;
            }
            if (netClient != null) {
                try { netClient.stop(); } catch(Exception ignored) {}
                netClient = null;
            }
            isMultiplayerActive = false;
            isHost = false;
            
            this.playtest=playtest;
            this.path = path;
            this.file = filename;

            loadFontAndSkin();
            loadTouchpad();

            loadingmap = true;
            rpg = false;



            initialiseSprites();
            initialiseBox2D();

            night=true;

            if (!initialiseTMX()) return false;
            initialiseMapProperties();
            layoutActionControls();
            initialiseTilesets();
            initialiseLayers();

            selectLocalPlayer();
            if (player == null) {
                gameobject fallbackPlayer = new gameobject();
                fallbackPlayer.mygame = this;
                fallbackPlayer.setupGameObject(world, null, 1f, 2f, 1f, 1f, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLAYER, null, null, false, 1.0f);
                objects.add(fallbackPlayer);
                player = fallbackPlayer;
            }
            action1 = null;
            action2 = null;
            action3 = null;
            action4 = null;
            fade=5;
            victory = false;
            loadingmap = false;
            ladder=false;
            touchedladder=0;
            touchedbridge=0;
            dropThroughTimer=0f;
            if (player.body != null) {
                player.body.setGravityScale(1);
            }
            fadein = fadeinmax;
            accumulator=0;
            objaccumulator=0;
            varaccumulator=0;
            log("initialisation finished!");

            return true;

        }catch(Exception e){
            e.printStackTrace();
            log("initialisation failed: " + e.getMessage());

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
            e.printStackTrace();
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
        groundContacts = 0;
    }
    public void initialiseMapProperties(){
        log("initialising map properties...");
        Texture tmp;
        fixedActionButtons = false;
        MapProperties mpa = map.getProperties();
        if (mpa != null) {
            if (mpa.containsKey("fixedbuttons")) {
                fixedActionButtons = true;
            }
            if (mpa.containsKey("mainmenu")) {
                hasMainMenu = true;
                if (!hasStartedPlaying) {
                    isMainMenuActive = true;
                }
            }
            if (mpa.containsKey("gamename")) {
                gameName = mpa.get("gamename").toString();
            } else {
                gameName = file;
            }
            if (mpa.containsKey("multiplayer")) {
                isMultiplayerEnabled = true;
            }


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
                scale = safeParseFloat( mpa.get( "scale" ), 100f );
            }



            if (mpa.containsKey( "background" )) {
                String bgc = (String) mpa.get( "background" );
                if (getFile( path + "/" + bgc ).exists() && !bgc.equalsIgnoreCase( "" )) {
                    txBackground = new Texture( getFile( path + "/" + bgc ) );
                } else {
                    txBackground = null;
                }
            }

            if (mpa.containsKey( "hpbar" )) {
                String bgc = (String) mpa.get( "hpbar" );
                if (getFile( path + "/" + bgc ).exists() && !bgc.equalsIgnoreCase( "" )) {
                    tmp = new Texture( getFile( path + "/" + bgc )  );
                } else {
                    tmp = new Texture( Gdx.files.internal( "platformer/hpbar.png" ) );
                }
                hpbar = TextureRegion.split( tmp,
                        tmp.getWidth(),
                        tmp.getHeight() / 2 );
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
            } else {
                orientation = 0;
            }
            if (face != null) {
                face.setOrientation(orientation);
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
                zoom = safeParseFloat( mpa.get( "zoom" ), 1f );
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

                        }catch (Exception ignored){
                        }
                    }
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

                boolean over = mlayer.getName().contains("*");
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
                boolean over = mlayer.getName().contains("*");
                MapObjects objects = map.getLayers().get( mlayer.getName() ).getObjects();
                for (MapObject o : objects) {
                    Rectangle rect;

                    if (o instanceof TiledMapTileMapObject) {
                        TiledMapTileMapObject obj = (TiledMapTileMapObject) o;
                        setGameObject(true, null, obj.getX(), obj.getY(), obj.getTextureRegion().getRegionWidth(), obj.getTextureRegion().getRegionHeight(), over, o, opacity);
                    } else {
                        RectangleMapObject obj = (RectangleMapObject) o;
                        rect = obj.getRectangle();
                        setGameObject( true, null, rect.x, rect.y, rect.width, rect.height, over, o, opacity );

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
                                if (safeParseInt( sv[1], 0 ) == var.value) {
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
                                    if (safeParseInt(xxx, 0) == var.value) {
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
                                if (var.value < safeParseInt( sv[1], 0 )) {
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
                                if (var.value > safeParseInt( sv[1], 0 )) {
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

    public boolean checkvars(String sss){
        boolean qual;

            String[] ss = sss.split( "," );
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
                                if (safeParseInt( sv[1], 0 ) == var.value) {
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
                                    if (safeParseInt(xxx, 0) == var.value) {
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
                                if (var.value < safeParseInt( sv[1], 0 )) {
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
                                if (var.value > safeParseInt( sv[1], 0 )) {
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

        return qual;

    }

    @Override
    public void connected(Controller controller) {
        gamepad = controller;
    }

    @Override
    public void disconnected(Controller controller) {
        gamepad=null;
    }

    public Controller gamepad;
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        gamepad = controller;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        gamepad = controller;
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        gamepad = controller;
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
        /////////////
        for (gameobject go: objects){
            if (!go.over & go!=player) {
                if (go.text!=null) {
                    Vector3 scrp = gamecam.project(new Vector3(go.getX(), go.getY(), 0f));
                    Vector3 uicp = uicam.unproject(scrp);
                    str1.draw(b, go.text, uicp.x, uicamVP.getWorldHeight()-uicp.y,0,Align.center,false);
                }
            }
        }
        /////////////
        int index=0;
        for (int i=0; i<save.vars.size();i++){
            KV vr = save.vars.get(i);
            if (vr.hud){
                if (vr.hasicon) {
                    try {
                        str1.draw( b, "  :  " + vr.value, 90, uicamVP.getWorldHeight() - 35 - 60 * index );
                        b.draw( icons[vr.icony][vr.iconx], 50, uicamVP.getWorldHeight() - 80 - 60 * index, 48, 48 );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    str1.draw( b, vr.key+" : " + vr.value, 50,uicamVP.getWorldHeight()-35-60*index);
                }
                index++;
            }
        }

        if (message!=null) str1.draw( ui, message, 0, uicamVP.getWorldHeight()-35, uicamVP.getWorldWidth(), Align.center, true );

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

        if (timer>0) str1.draw( ui, time, uicamVP.getWorldWidth() - 300, uicamVP.getWorldHeight() - 35, 200, Align.left, false );

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
    public float zoom=0.2f;
    public List<gameobject> deads = new ArrayList<>();
    public float accumulator;
    public float objaccumulator;
    public float TIME_STEP=1/60f;
    public float OBJ_STEP=1/60f;
    public boolean updatephysics;
    public boolean updategameobject;

    public void update(float delta) {
        if (loadingmap) return;
        if(isDialog) return;
        if(starting) return;

        if (dropThroughTimer > 0) {
            dropThroughTimer -= delta;
        }

        if (slot1 != null) slot1.setVisible(action1 != null && action1.action != gameobject.actions.NONE);
        if (slot2 != null) slot2.setVisible(action2 != null && action2.action != gameobject.actions.NONE);
        if (slot3 != null) slot3.setVisible(action3 != null && action3.action != gameobject.actions.NONE);
        if (slot4 != null) slot4.setVisible(action4 != null && action4.action != gameobject.actions.NONE);

        updateFixedTimeStamp();
        updatePlayerMovement();
        if (isMultiplayerActive) {
            sendLocalPlayerUpdate();
        }
        checkGameCondition();
        checkHUD();
        updateFadeAndTransfer();
        updatePlayerSprite(delta);
        updateGameObjects();
    }

    private void updateFixedTimeStamp() {
        accumulator += delta;
        while (accumulator >= TIME_STEP) {
            groundContacts = 0;
            if (!starting && player.state != DEAD) {
                world.step( TIME_STEP, 6, 2 );
                refreshGroundContactsRaycast();
            }
            accumulator -= TIME_STEP;
            updatephysics=true;
        }
    }

    /** Fallback when foot-sensor contacts miss thin tile edges. */
    private void refreshGroundContactsRaycast() {
        if (player == null || player.body == null || groundContacts > 0) return;

        float halfH = player.getHeight() / (2f * scale);
        Vector2 from = player.body.getPosition().cpy().add( 0, -halfH + 0.01f );
        Vector2 to = from.cpy().add( 0, -Math.max( halfH * 0.5f, 0.08f ) );

        world.rayCast( new RayCastCallback() {
            @Override
            public float reportRayFixture(com.badlogic.gdx.physics.box2d.Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.isSensor()) return 1;
                if (fixture.getBody() == player.body) return -1;
                if (fixture.getUserData() == FOOT_SENSOR) return -1;
                if (normal.y < 0.55f) return 1;
                groundContacts = 1;
                return 0;
            }
        }, from, to );
    }

    private void checkGameCondition() {
        if (timetrial) timer+=delta;
        if (player.HP <= 0) killPlayer();
        if (player.body.getPosition().y<-2f/scale) killPlayer();
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
                    if (go.myLight!=null) go.myLight.dispose();
                    go.state = gameobject.states.DEAD;
                    go.body.setLinearVelocity( 0, 0 );
                    if (go.body!=null) world.destroyBody( go.body );
                    go.bumbum();
                    go.playSfx( go.sfxdead );
                }
                requestkill = null;
            }
        }
    }

    private void updateGameObjects() {
        //update objects
        deads.clear();
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
            if (updategameobject) {
                go.update( times ,delta);
                if (go.objtype == gameobject.objecttype.PLAYER && go != player) {
                    updateRemotePlayerSprite(go, delta);
                }
            }
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
            if (!go.over && go != player) {
                if (go.getTexture() != null) go.draw( batch );
            }
            if (go.HP > 0 && go.HP !=go.maxHP) {
                batch.draw( hpbar[1][0], go.getX(), go.getY() + go.getHeight() + 0.01f, go.getWidth(), hpbar[1][0].getRegionHeight() / scale );
                batch.draw( hpbar[0][0], go.getX(), go.getY() + go.getHeight() + 0.01f, go.HP / go.maxHP * go.getWidth(), hpbar[0][0].getRegionHeight() / scale );
            }

        }

        if (player.state!=DEAD) player.draw( batch );

        for (gameobject go: objects){
            if (go.over && go != player) {
                if (go.getTexture() != null) go.draw( batch );
            }
        }

        for (particle pe : particles){
            pe.draw( batch );
        }

    }
    private void updateFadeAndTransfer() {
        //fade and transfer
        if (transformrequest) fullfilltransformrequest();

        float posex;
        float posey;

        if (camlocky || (player != null && player.state == DEAD)){
            posey = gamecam.position.y;
        }else{
            posey = player.body.getPosition().y;
        }
        if (camlockx || (player != null && player.state == DEAD)){
            posex = gamecam.position.x;

        }else{
            posex = player.body.getPosition().x;

        }

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
        boolean grab = ladder && (player.body.getLinearVelocity().y <= 0.1f || (player.moving && (player.dir == 3 || player.dir == 0)));

        if (floater){
            player.body.setLinearVelocity(player.body.getLinearVelocity().x,0);
            player.body.setGravityScale(-0.7f);
        }else if (grab){
            jumping = false;
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
            if (grab) {
                jumping = false;
                jumpinterval = 0.08f;
            } else if (groundContacts > 0) {
                jumping = false;
                dashed = false;
                jumpinterval = 0.08f;
            } else {
                jumpinterval -= delta;
                jumping = true;
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

        //no animation whatsoever
        if (player.anim.size()==0){
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

            //has at least 1 animation



        }else {
            //rpg with anim
            if (rpg) {
                jumping=false;
                //not rpg with anim
            }else{
                //moving
                TextureRegion currentFramea=null;
                if (player.moving && !jumping) {
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun+jetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipjetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                    }
                }

                //jumping
                else if (jumping) {
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun+jetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0.25f, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipjetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0.25f, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0.25f, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            if (player.jump!=null){
                                currentFramea = player.jump.get( k ).getKeyFrame( playerTime, true );
                            }else{
                                currentFramea = player.anim.get( k ).getKeyFrame( 0.25f, true );
                            }
                            break;
                        }

                    }
                }

                 else {
                    //idle
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun+jetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipjetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "equipgun" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( 0, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            if (player.idle!=null){
                                currentFramea = player.idle.get( k ).getKeyFrame( playerTime, true );
                            }else {
                                currentFramea = player.anim.get(k).getKeyFrame(0, true);
                            }
                            break;
                        }
                    }

                }

                if (ladder && !jumping) {
                    float state = 0;
                    if (player.moving) state = playerTime;
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "climb+jetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( state, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "climb" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( state, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( state, true );
                            break;
                        }
                    }
                }

                if (slashing) {
                        if (player.slash!=null){
                            currentFramea = player.slash.get( 0 ).getKeyFrame( playerTime, true );
                            if (playerTime > player.slash.get( 0 ).getAnimationDuration()) slashing=false;
                        }else {
                            currentFramea = player.anim.get(0).getKeyFrame(0, true);
                        }
                }

                if (jetpack) {
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "usejetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                    }
                }

                if (shooting>0) {
                    for (int k=player.anim.size()-1;k>=0;k--){
                        if (player.animID.get( k ).equalsIgnoreCase( "usegun+jetpack" )){
                            currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "usegun" )){
                            if (player.slash!=null){
                                currentFramea = player.slash.get( k ).getKeyFrame( playerTime, true );
                            }else {
                                currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            }
                            break;
                        }
                        if (player.animID.get( k ).equalsIgnoreCase( "basic" )){
                            if (player.idle!=null){
                                currentFramea = player.slash.get( k ).getKeyFrame( playerTime, true );
                            }else {
                                currentFramea = player.anim.get( k ).getKeyFrame( playerTime, true );
                            }
                            break;
                        }
                    }
                    shooting -=delta;
                }

                player.setRegion( currentFramea );

                //flip after setting region.
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

            } //rpg or not
        } //has anim or not
        playerTime += delta;

    } //void

    public void updateRemotePlayerSprite(gameobject remote, float delta) {
        if (remote.anim == null || remote.anim.isEmpty()) {
            return;
        }

        TextureRegion frame = null;
        boolean isMoving = remote.body != null && (Math.abs(remote.body.getLinearVelocity().x) > 0.1f || Math.abs(remote.body.getLinearVelocity().y) > 0.1f);
        float time = stateTime;

        int animIdx = 0;
        for (int i = 0; i < remote.animID.size(); i++) {
            if (remote.animID.get(i).equalsIgnoreCase("basic")) {
                animIdx = i;
                break;
            }
        }

        if (isMoving) {
            frame = remote.anim.get(animIdx).getKeyFrame(time, true);
        } else {
            if (remote.idle != null && !remote.idle.isEmpty()) {
                frame = remote.idle.get(0).getKeyFrame(time, true);
            } else {
                frame = remote.anim.get(animIdx).getKeyFrame(0, true);
            }
        }

        if (frame != null) {
            remote.setRegion(frame);
            if (remote.pimagesize != null) {
                remote.setSize(remote.pimagesize.x / scale, remote.pimagesize.y / scale);
            } else {
                remote.setSize(frame.getRegionWidth() / scale, frame.getRegionHeight() / scale);
            }
        }
    }

    public void playSfx(Sound s){
        if (s!=null) s.play(1.0f);
    }

    public float walkForce=0.5f;

    public boolean canJump() {
        return groundContacts > 0 || jumpinterval > 0;
    }

    public void pressup(){
        if (rpg || ladder) player.moving=true;
        player.dir=3;

        if (!rpg) {
            if (canJump() && !ladder && !sinker && !floater) {
             //   player.body.applyLinearImpulse( 0f, jumpForce, player.getX(), player.getY(), true );
                jumping = true;
                onplatformv = false;
            }
            if (ladder && !jumping) {

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
        if (player.body != null) player.body.setAwake(true);
        if (touchedbridge > 0) {
            dropThroughTimer = 0.25f;
        }
        if (rpg || ladder) player.moving=true;
        player.dir=0;

        if (!rpg) {
            if (stompinterval == 0 && !ladder && !sinker && !floater) {
                //player.body.applyLinearImpulse( 0f, -stompForce, player.getX(), player.getY(), true );
                stompinterval = 1;
            }
            if (ladder && !jumping) {
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

        if (ladder && !jumping) {
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
        if (ladder  && !jumping) {
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
    boolean slashing = false;
    float jetpackcooldown =0f;
    float gravity;
    public float varaccumulator;
    public void act(gameobject go){
//        Gdx.app.log(go.pcooldown+"", go.cooldown+"");
        if (go==null) return;

        //Gdx.app.log(go.bindvar, getVar( go.bindvar )+"");
        switch (go.action){
            case NONE:
                break;
            case JUMP: //jumping ga usah pakai cooldown
                if (go.cooldown>0) return;
                if (groundContacts <= 0 && jumpinterval <= 0) return;

                player.body.applyLinearImpulse( 0f, gravity*go.impulse, player.getX(), player.getY(), true );
                playSfx( go.sfx );

                jumping = true;
                jumpinterval = 0;
                go.cooldown=go.pcooldown;

                break;
            case SLASH:
                if (slashing) return;
                player.body.setLinearVelocity(0,0);
                go.cooldown=go.pcooldown;
                playerTime=0f;
                slashing=true;
                break;
            case JETPACK: //jumping ga usah pakai cooldown
                if (go.cooldown>0) return;
                playSfx( go.sfx );
                jetpack = true;
                jetpackcooldown = 0.1f;
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
                shooting = 0.2f;
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
                float posx=player.body.getPosition().x+player.offsetx/scale;
                float posy=player.body.getPosition().y+(player.offsety+3f)/scale;
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

                if (isMultiplayerActive) {
                    ShootPacket sp = new ShootPacket();
                    sp.shooterId = playerId;
                    sp.x = posx;
                    sp.y = posy;
                    sp.dir = player.dir;
                    sp.speed = newbrick.speed;
                    sp.maxdistance = (int) newbrick.maxdistance;
                    sp.spread = newbrick.spread;
                    sp.damage = newbrick.damage;

                    if (isHost && netServer != null) {
                        netServer.sendToAllTCP(sp);
                    } else if (netClient != null) {
                        netClient.sendTCP(sp);
                    }
                }

                go.cooldown=go.pcooldown;
                break;
            default:
                throw new IllegalStateException( "Unexpected value: " + go.action );
        }

        if (go.bindvar!=null){
            if (getVar( go.bindvar )<=0) {
                go.action= gameobject.actions.NONE;
                switch (go.slot){
                    case 1: slot1.clearChildren();break;
                    case 2: slot2.clearChildren();break;
                    case 3: slot3.clearChildren();break;
                    case 4: slot4.clearChildren();break;
                }
                for (int i=player.anim.size()-1;i>=0;i--){
                    if (player.animID.get( i ).equalsIgnoreCase( "usejetpack" )){
                        player.animID.remove( i );
                        player.anim.remove( i );
                        continue;
                    }
                    if (player.animID.get( i ).equalsIgnoreCase( "equipjetpack" )){
                        player.animID.remove( i );
                        player.anim.remove( i );
                        continue;
                    }
                    if (player.animID.get( i ).equalsIgnoreCase( "climb+jetpack" )){
                        player.animID.remove( i );
                        player.anim.remove( i );
                        continue;
                    }
                    if (player.animID.get( i ).equalsIgnoreCase( "equipgun+jetpack" )){
                        player.animID.remove( i );
                        player.anim.remove( i );
                        continue;
                    }
                    if (player.animID.get( i ).equalsIgnoreCase( "usegun+jetpack" )){
                        player.animID.remove( i );
                        player.anim.remove( i );
                    }
                }
            }else{
                varaccumulator += delta;
                while (varaccumulator >= OBJ_STEP) {
                    varaccumulator -= OBJ_STEP;
                    setOrAddVars( go.bindvar,1, VAROP.SUB );
                }


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
                    newbrick.animID.add( "basic" );
                }
            }

        }else { //object
            objx=obj;
            if (obj.getName()!=null){
                newbrick.id = obj.getName();
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

        newbrick.damage = safeParseFloat( o.get( "damage" ), 0f );
        newbrick.rotating = o.containsKey( "rotating" );
        newbrick.destructible = o.containsKey( "destructible" );

        // Parse animations
        newbrick.animBreathing = o.containsKey("breathing");
        if (newbrick.animBreathing) {
            newbrick.breathingSpeed = safeParseFloat(o.get("breathingspeed"), 8f);
            float defaultAmount = 0.05f;
            if (o.containsKey("breathingamount")) {
                newbrick.breathingAmount = safeParseFloat(o.get("breathingamount"), defaultAmount);
            } else if (!o.get("breathing").toString().equalsIgnoreCase("true")) {
                newbrick.breathingAmount = safeParseFloat(o.get("breathing"), defaultAmount);
            } else {
                newbrick.breathingAmount = defaultAmount;
            }
        }

        newbrick.animUpDown = o.containsKey("updown") || o.containsKey("up_down");
        if (newbrick.animUpDown) {
            newbrick.upDownSpeed = safeParseFloat(o.get("updownspeed"), 2f);
            float defaultHeight = 0.05f;
            if (o.containsKey("updownheight")) {
                newbrick.upDownHeight = safeParseFloat(o.get("updownheight"), defaultHeight);
            } else if (o.containsKey("updown") && !o.get("updown").toString().equalsIgnoreCase("true")) {
                newbrick.upDownHeight = safeParseFloat(o.get("updown"), defaultHeight);
            } else if (o.containsKey("up_down") && !o.get("up_down").toString().equalsIgnoreCase("true")) {
                newbrick.upDownHeight = safeParseFloat(o.get("up_down"), defaultHeight);
            } else {
                newbrick.upDownHeight = defaultHeight;
            }
        }

        newbrick.animTeeter = o.containsKey("teeter");
        if (newbrick.animTeeter) {
            newbrick.teeterSpeed = safeParseFloat(o.get("teeterspeed"), 12f);
            float defaultAngle = 10f;
            if (o.containsKey("teeterangle")) {
                newbrick.teeterAngle = safeParseFloat(o.get("teeterangle"), defaultAngle);
            } else if (!o.get("teeter").toString().equalsIgnoreCase("true")) {
                newbrick.teeterAngle = safeParseFloat(o.get("teeter"), defaultAngle);
            } else {
                newbrick.teeterAngle = defaultAngle;
            }
        }

        newbrick.animSpin = o.containsKey("spin");
        if (newbrick.animSpin) {
            float defaultSpeed = 360f;
            if (o.containsKey("spinspeed")) {
                newbrick.spinSpeed = safeParseFloat(o.get("spinspeed"), defaultSpeed);
            } else if (!o.get("spin").toString().equalsIgnoreCase("true")) {
                newbrick.spinSpeed = safeParseFloat(o.get("spin"), defaultSpeed);
            } else {
                newbrick.spinSpeed = defaultSpeed;
            }
        }

        newbrick.anim3dSpin = o.containsKey("spin3d") || o.containsKey("3dspin");
        if (newbrick.anim3dSpin) {
            float defaultSpeed = 9f;
            String keyName = o.containsKey("spin3d") ? "spin3d" : "3dspin";
            if (o.containsKey("spin3dspeed")) {
                newbrick.spin3dSpeed = safeParseFloat(o.get("spin3dspeed"), defaultSpeed);
            } else if (!o.get(keyName).toString().equalsIgnoreCase("true")) {
                newbrick.spin3dSpeed = safeParseFloat(o.get(keyName), defaultSpeed);
            } else {
                newbrick.spin3dSpeed = defaultSpeed;
            }
        }

        if (o.containsKey("pixelshift")) {
            String val = o.get("pixelshift").toString().trim().toLowerCase();
            if (val.equals("left") || val.equals("right") || val.equals("up") || val.equals("down")
                    || val.equals("leftright") || val.equals("updown")
                    || val.equals("diagonal") || val.equals("diagonally")) {
                newbrick.pixelShift = val.equals("diagonally") ? "diagonal" : val;
            } else {
                newbrick.pixelShift = "right"; // default direction when only flag is set
            }
            newbrick.pixelShiftSpeed = safeParseFloat(o.get("pixelshiftspeed"), 1f);
        }
        //newbrick.light = (o.containsKey( "light" )) ? Float.parseFloat( o.get( "light" ).toString() ) : 0;

        for (int i = 0; i < animids.size(); i++) {
            if (o.containsKey("jump")) {
                if (animids.get(i) == safeParseInt(o.get("jump"), -1)) {
                    newbrick.jump=new ArrayList<>();
                    newbrick.jump.add(anims.get(i));
                }
            }
            if (o.containsKey("slash")) {
                if (animids.get(i) == safeParseInt(o.get("slash"), -1)) {
                    newbrick.slash=new ArrayList<>();
                    newbrick.slash.add(anims.get(i));
                }
            }
            if (o.containsKey("idle")) {
                if (animids.get(i) == safeParseInt(o.get("idle"), -1)) {
                    newbrick.idle=new ArrayList<>();
                    newbrick.idle.add(anims.get(i));
                }
            }
        }


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

        if (o.containsKey( "text" )) {
            String txt = o.get("text").toString();
            newbrick.text = txt;
        }


        newbrick.HP = safeParseInt( o.get( "HP" ), 1 );
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
            ww=safeParseFloat(sz[0], 0f);
            hh=safeParseFloat(sz[1], 0f);
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
                        k = safeParseInt( ss[i], 0 );
                }
                myArray[i] = k;
            }
            newbrick.path = myArray; //new int[]{0,0,2,2,3,3,1,1,2,1};

        } else {
            newbrick.path = null; //new int[]{0,0,2,2,3,3,1,1,2,1};

        }

        if (o.containsKey( "zoom" )) {
            zoom = safeParseFloat( o.get( "zoom" ), 1f );
        }

        if (o.containsKey( "gravity" )) {
            world.setGravity( new Vector2(0, -safeParseFloat( o.get( "gravity" ), 10f ) ));
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
                    newbrick.dir = safeParseInt( o.get( "dir" ), 1 );
                    break;
            }
            newbrick.face = newbrick.dir;
        }

        newbrick.tameif = (o.containsKey( "tameif" )) ?  o.get( "tameif" ).toString() : "";

        newbrick.wait = safeParseFloat( o.get( "wait" ), 1f );
        newbrick.speed = safeParseFloat( o.get( "speed" ), 1f );
        newbrick.chase = o.containsKey( "chase" );
        newbrick.chaseRadius = safeParseFloat( o.get( "chaseradius" ), 100f );
        newbrick.deadtileID = safeParseInt( o.get( "deadanim" ), 0 );
        newbrick.pdeadanim = safeParseInt( o.get( "pdeadanim" ), 0 );
        newbrick.stepping = o.containsKey( "stepping" );
        newbrick.canshoot = o.containsKey( "canshoot" );
        newbrick.dirlocked = o.containsKey( "dirlocked" );
        newbrick.pspread = safeParseFloat( o.get( "pspread" ), 0f );
        newbrick.pcooldown = safeParseFloat( o.get( "pcooldown" ), 0f );
        newbrick.pspeed = safeParseFloat( o.get( "pspeed" ), 4f );
        newbrick.pmaxdistance = safeParseInt( o.get( "pmaxdistance" ), 300 );
        newbrick.pdamage = safeParseInt( o.get( "pdamage" ), 1 );
        newbrick.offsetx = safeParseInt( o.get( "offsetx" ), 0 );
        newbrick.offsety = safeParseInt( o.get( "offsety" ), 0 );


        if (o.containsKey( "object" )) {
            switch (o.get( "object" ).toString().trim()) {
                case "player":
                    if (o.containsKey( "anim" )){
                    String anim = o.get( "anim" ).toString().trim();
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
                            newbrick.animID.add( "rpg"+i );
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
                    if (o.containsKey( "height" )) {
                        newbrick.impulse = safeParseFloat( o.get( "height" ), 4f );
                    } else if (o.containsKey( "impulse" )) {
                        newbrick.impulse = safeParseFloat( o.get( "impulse" ), 4f );
                    } else {
                        newbrick.impulse = 4f;
                    }
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
                case "bridge":
                    newbrick.setupGameObject( world, tlcece, xx, yy, ww, hh, BodyDef.BodyType.StaticBody, gameobject.objecttype.BRIDGE, objx, t, over  ,opacity);
                    boolean defaultDown = true;
                    if (o.containsKey("down")) {
                        newbrick.goDownEnabled = o.get("down").toString().trim().equalsIgnoreCase("true");
                    } else if (o.containsKey("godown")) {
                        newbrick.goDownEnabled = o.get("godown").toString().trim().equalsIgnoreCase("true");
                    } else {
                        newbrick.goDownEnabled = defaultDown;
                    }
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
                        newbrick.setupGameObject( world, tlcece, xx, yy, 2, 2, BodyDef.BodyType.DynamicBody, TOUCHSENSOR, objx, t, over  ,opacity);
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
                                panimID = safeParseInt( o.get( "panim" ), 0 );
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
        if (width >= height) { // Landscape
            uicamVP.setWorldSize( 1920, 1080 );
            gamecamVP.setWorldSize( 20, 11 );
            if (stage.getViewport() instanceof ExtendViewport) {
                ((ExtendViewport) stage.getViewport()).setMinWorldWidth( 3840 );
                ((ExtendViewport) stage.getViewport()).setMinWorldHeight( 2160 );
            }
            if (stage2.getViewport() instanceof ExtendViewport) {
                ((ExtendViewport) stage2.getViewport()).setMinWorldWidth( 1920 );
                ((ExtendViewport) stage2.getViewport()).setMinWorldHeight( 1080 );
            }
        } else { // Portrait
            uicamVP.setWorldSize( 1080, 1920 );
            gamecamVP.setWorldSize( 11, 20 );
            if (stage.getViewport() instanceof ExtendViewport) {
                ((ExtendViewport) stage.getViewport()).setMinWorldWidth( 2160 );
                ((ExtendViewport) stage.getViewport()).setMinWorldHeight( 3840 );
            }
            if (stage2.getViewport() instanceof ExtendViewport) {
                ((ExtendViewport) stage2.getViewport()).setMinWorldWidth( 1080 );
                ((ExtendViewport) stage2.getViewport()).setMinWorldHeight( 1920 );
            }
        }
        uicamVP.update( width,height, true );
        gamecamVP.update( width,height, false );
        stage.getViewport().update( width,height, true );
        stage2.getViewport().update( width,height, true );
    }

    public void startgame(String curdir,String filex, boolean playtest){
        if (initialise(curdir, filex,playtest)) {
            if (isMainMenuActive) {
                Gdx.input.setInputProcessor(stage2);
                setupMainMenuUI();
            } else {
                gamecam.zoom = 0.2f;
                if (player != null && player.body != null) {
                    gamecam.position.set( player.body.getPosition().x, player.body.getPosition().y, 0 );
                }
                gamecam.update();
                Gdx.input.setInputProcessor(stage);
            }
        }
    }

    boolean shutdown = false;
    float logTimer = 0f;

    public boolean render(){
        if (shutdown) return false;
        if (isMainMenuActive) {
            Gdx.gl.glClearColor(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            com.badlogic.gdx.math.Matrix4 screenProj = new com.badlogic.gdx.math.Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setProjectionMatrix(screenProj);
            batch.begin();
            if (txBackground != null) {
                float screenWidth = Gdx.graphics.getWidth();
                float screenHeight = Gdx.graphics.getHeight();
                float textureWidth = txBackground.getWidth();
                float textureHeight = txBackground.getHeight();
                float screenAspect = screenWidth / screenHeight;
                float textureAspect = textureWidth / textureHeight;
                float drawWidth = screenWidth;
                float drawHeight = screenHeight;
                float drawX = 0;
                float drawY = 0;
                if (screenAspect > textureAspect) {
                    drawHeight = screenWidth / textureAspect;
                    drawY = (screenHeight - drawHeight) / 2f;
                } else {
                    drawWidth = screenHeight * textureAspect;
                    drawX = (screenWidth - drawWidth) / 2f;
                }
                batch.draw(txBackground, drawX, drawY, drawWidth, drawHeight);
            }
            batch.end();
            stage2.act(Gdx.graphics.getDeltaTime());
            stage2.draw();
            return true;
        }
        if (player==null) {
            if (hasPlayerError) {
                if (isDialog) {
                    Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                    stage2.act(Gdx.graphics.getDeltaTime());
                    stage2.draw();
                    return true;
                } else {
                    return false;
                }
            }
            hasPlayerError = true;
            msgbox("No player instance found! Make sure your map has an object with property 'object' set to 'player'.");
            return true;
        }

        delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        logTimer += delta;
        if (logTimer >= 1f) {
            logTimer = 0f;
            Gdx.app.log("GameDebug", "CamPos: " + gamecam.position + ", PlayerPos: " + (player != null ? player.body.getPosition() : "null") + ", Zoom: " + gamecam.zoom + ", Viewport: " + gamecamVP.getWorldWidth() + "x" + gamecamVP.getWorldHeight());
        }
        if (gamecam.zoom != zoom) gamecam.zoom = zoom;
        if (touchCooldown>0) touchCooldown--;
        if(touchCooldown==0)
        {
            if (touchpoint != null) {
                touchpoint.body.setTransform( -5, -5, 0 );
                touchpoint.body.setAwake( true );
                touchCooldown=-1;
            }

        }
        //update keystroke and logic
        keyinput();
        update( delta);

        //draw bgcolor
        Gdx.gl.glClearColor( bgcolor.r,bgcolor.g,bgcolor.b,bgcolor.a);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        //draw background
        com.badlogic.gdx.math.Matrix4 screenProj = new com.badlogic.gdx.math.Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix( screenProj );
        batch.begin();
        if (txBackground != null) {
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            float textureWidth = txBackground.getWidth();
            float textureHeight = txBackground.getHeight();

            float screenAspect = screenWidth / screenHeight;
            float textureAspect = textureWidth / textureHeight;

            float drawWidth = screenWidth;
            float drawHeight = screenHeight;
            float drawX = 0;
            float drawY = 0;

            if (screenAspect > textureAspect) {
                drawHeight = screenWidth / textureAspect;
                drawY = (screenHeight - drawHeight) / 2f;
            } else {
                drawWidth = screenHeight * textureAspect;
                drawX = (screenWidth - drawWidth) / 2f;
            }

            batch.draw( txBackground, drawX, drawY, drawWidth, drawHeight );
        }
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

        control.setVisible(false);
        //draw virtual Controller
        if (Gdx.app.getType() != Application.ApplicationType.Desktop || uitest) {
            if (!starting && player.state != gameobject.states.DEAD  && fade==0) {
                if (!disablecontrol && gamepad==null) {
                    control.setVisible(true);
                }
            }
        }
        stage.act( delta );
        stage.draw();
        if (starting) {
            drawMessageBoxBackground();
            ui.setProjectionMatrix( uicam.combined );
            ui.begin();
            drawMessageBox( ui );
            ui.end();
        }
        if (isMainMenuActive || isDialog) {
            stage2.act( delta );
            stage2.draw();
        }

        //draw fade effect
        uis.setProjectionMatrix( uicam.combined );
        uis.begin( ShapeRenderer.ShapeType.Filled );
        Gdx.gl.glEnable( GL20.GL_BLEND );
        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
        if (loadingmap || transitioning) {
            uis.setColor( 0f, 0f, 0, 1f );
            uis.rect( 0, 0, uicamVP.getWorldWidth(), uicamVP.getWorldHeight() );
        }
        if (fadein>0) {
            uis.setColor( 0f, 0f, 0, fadein / fadeinmax );
            uis.rect( 0, 0, uicamVP.getWorldWidth(), uicamVP.getWorldHeight() );
        }
        if (fadeout>0) {
            uis.setColor( 0f, 0f, 0, 1-((fadeout-1) / fadeoutmax) );
            uis.rect( 0, 0, uicamVP.getWorldWidth(), uicamVP.getWorldHeight() );
        }
        uis.end();

        //tell the boss that drawing is completed
        return true;
    }

    Button slot1,slot2,slot3,slot4;
    Image islot1,islot2,islot3,islot4;
    Table control;

    private void clearActionSlots() {
        tslot1 = false;
        tslot2 = false;
        tslot3 = false;
        tslot4 = false;
    }

    private boolean slotHasAction(int slot) {
        switch (slot) {
            case 1: return action1 != null && action1.action != gameobject.actions.NONE;
            case 2: return action2 != null && action2.action != gameobject.actions.NONE;
            case 3: return action3 != null && action3.action != gameobject.actions.NONE;
            case 4: return action4 != null && action4.action != gameobject.actions.NONE;
            default: return false;
        }
    }

    private void hideActionPadAfterUse() {
        if (fixedActionButtons || actionPad == null) return;
        actionPad.setVisible(false);
    }

    private void releaseAnalogTouch(int pointer) {
        if (analogTouchPointer != pointer) return;
        analogTouchPointer = -1;
        if (tpad != null && stage != null) {
            InputEvent up = new InputEvent();
            up.setType(InputEvent.Type.touchUp);
            up.setStage(stage);
            up.setStageX(tpad.getX() + tpad.getWidth() / 2);
            up.setStageY(tpad.getY() + tpad.getHeight() / 2);
            up.setPointer(pointer);
            tpad.fire(up);
            tpad.setVisible(false);
        }
    }

    private void finishSwipeActionTouch(int pointer) {
        if (actionTouchPointer != pointer) return;
        actionTouchPointer = -1;
        clearActionSlots();
        actionSwipeCommitted = false;
        if (!fixedActionButtons) actionPad.setVisible(false);
    }

    private void updateActionSlotsFromDrag(float dx, float dy) {
        if (actionSwipeCommitted) return;
        clearActionSlots();
        float absX = Math.abs(dx);
        float absY = Math.abs(dy);
        if (absX < ACTION_SWIPE_THRESHOLD && absY < ACTION_SWIPE_THRESHOLD) return;
        if (absX >= absY) {
            if (dx > 0) tslot1 = slotHasAction(1);
            else tslot3 = slotHasAction(3);
        } else {
            if (dy > 0) tslot4 = slotHasAction(4);
            else tslot2 = slotHasAction(2);
        }
        if (tslot1 || tslot2 || tslot3 || tslot4) {
            actionSwipeCommitted = true;
            hideActionPadAfterUse();
        }
    }

    public void loadTouchpad(){

          com.badlogic.gdx.graphics.Pixmap btnPixmap = new com.badlogic.gdx.graphics.Pixmap(260, 260, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
          btnPixmap.setColor(0, 0, 0, 0);
          btnPixmap.fill();
          btnPixmap.setColor(1f, 1f, 1f, 0.5f);
          btnPixmap.drawCircle(130, 130, 126);
          btnPixmap.drawCircle(130, 130, 127);
          btnPixmap.drawCircle(130, 130, 128);
          Texture btnTexture = new Texture(btnPixmap);
          btnPixmap.dispose();

          com.badlogic.gdx.graphics.Pixmap btnPixmapDown = new com.badlogic.gdx.graphics.Pixmap(260, 260, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
          btnPixmapDown.setColor(0, 0, 0, 0);
          btnPixmapDown.fill();
          btnPixmapDown.setColor(1f, 1f, 1f, 0.8f);
          btnPixmapDown.drawCircle(130, 130, 126);
          btnPixmapDown.drawCircle(130, 130, 127);
          btnPixmapDown.drawCircle(130, 130, 128);
          btnPixmapDown.setColor(1f, 1f, 1f, 0.25f);
          btnPixmapDown.fillCircle(130, 130, 125);
          Texture btnTextureDown = new Texture(btnPixmapDown);
          btnPixmapDown.dispose();

          com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable btnDrawable = new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(btnTexture);
          com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable btnDrawableDown = new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(btnTextureDown);
          btnDrawableDown.setMinWidth(260); btnDrawableDown.setMinHeight(260);
          btnDrawable.setMinWidth(260); btnDrawable.setMinHeight(260);

          Button.ButtonStyle btnStyle = new Button.ButtonStyle();
          btnStyle.up = btnDrawable;
          btnStyle.down = btnDrawableDown;
          btnStyle.over = btnDrawable;

          slot1 = new Button( btnStyle );
          slot2 = new Button( btnStyle );
          slot3 = new Button( btnStyle );
          slot4 = new Button( btnStyle );

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


         Touchpad.TouchpadStyle tpadStyle = new Touchpad.TouchpadStyle();
         if (skin2 != null) {
             com.badlogic.gdx.scenes.scene2d.utils.Drawable bg = skin2.getDrawable("Gambar1");
             if (bg != null) {
                 bg.setMinWidth(350);
                 bg.setMinHeight(350);
                 tpadStyle.background = bg;
             }
             com.badlogic.gdx.scenes.scene2d.utils.Drawable knob = skin2.getDrawable("knob");
             if (knob != null) {
                 knob.setMinWidth(150);
                 knob.setMinHeight(150);
                 tpadStyle.knob = knob;
             }
         }
         tpad = new Touchpad(20, tpadStyle);
         tpad.setSize(350, 350);
         tpad.setColor(0.3f, 0.3f, 0.3f, 0.7f);
         tpad.setResetOnTouchUp(true);
         tpad.setVisible(false);

         tpad.addListener( new InputListener() {
             @Override
             public void touchUp(InputEvent event, float x, float y,
                                 int pointer, int button) {
                 releaseAnalogTouch(pointer);
             }
         });

         actionButtonLayout = new Table();
         actionButtonLayout.add().size(260);
         actionButtonLayout.add( slot4 ).size(260).pad(25);
         actionButtonLayout.add().size(260);
         actionButtonLayout.row();
         actionButtonLayout.add( slot3 ).size(260).pad(25);
         actionButtonLayout.add().size(260);
         actionButtonLayout.add( slot1 ).size(260).pad(25);
         actionButtonLayout.row();
         actionButtonLayout.add().size(260);
         actionButtonLayout.add( slot2 ).size(260).pad(25);
         actionButtonLayout.add().size(260);

         actionPad = new Table();
         actionPad.setTouchable(Touchable.enabled);

         control = new Table();
         control.setFillParent( true );
         control.setTouchable(Touchable.enabled);

        layoutActionControls();
    }

    private InputListener createSwipeActionPadListener() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                if (actionTouchPointer < 0) actionTouchPointer = pointer;
                actionSwipeCommitted = false;
                actionDragOriginX = x;
                actionDragOriginY = y;
                updateActionSlotsFromDrag(0, 0);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y,
                                     int pointer) {
                if (pointer != actionTouchPointer) return;
                updateActionSlotsFromDrag(x - actionDragOriginX, y - actionDragOriginY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                finishSwipeActionTouch(pointer);
            }
        };
    }

    private InputListener createControlTouchListener() {
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                float stageW = stage.getWidth();
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                if (x < stageW / 2) {
                    analogTouchPointer = pointer;
                    tpad.setVisible(true);
                    tpad.setPosition(stageX - tpad.getWidth() / 2, stageY - tpad.getHeight() / 2);

                    InputEvent tempEvent = new InputEvent();
                    tempEvent.setType(InputEvent.Type.touchDown);
                    tempEvent.setStage(stage);
                    tempEvent.setStageX(stageX);
                    tempEvent.setStageY(stageY);
                    tempEvent.setPointer(pointer);
                    tempEvent.setButton(button);
                    tpad.fire(tempEvent);

                    for (com.badlogic.gdx.scenes.scene2d.EventListener l : tpad.getListeners()) {
                        stage.addTouchFocus(l, tpad, tpad, pointer, button);
                    }
                    return true;
                }

                if (fixedActionButtons) return false;

                actionTouchPointer = pointer;
                actionPad.setVisible(true);
                actionPad.setPosition(stageX - actionPad.getWidth() / 2, stageY - actionPad.getHeight() / 2);

                InputEvent actionEvent = new InputEvent();
                actionEvent.setType(InputEvent.Type.touchDown);
                actionEvent.setStage(stage);
                actionEvent.setStageX(stageX);
                actionEvent.setStageY(stageY);
                actionEvent.setPointer(pointer);
                actionEvent.setButton(button);
                actionPad.fire(actionEvent);

                for (com.badlogic.gdx.scenes.scene2d.EventListener l : actionPad.getListeners()) {
                    stage.addTouchFocus(l, actionPad, actionPad, pointer, button);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                float stageX = event.getStageX();
                if (stageX < stage.getWidth() / 2) {
                    releaseAnalogTouch(pointer);
                } else if (!fixedActionButtons) {
                    finishSwipeActionTouch(pointer);
                }
            }
        };
    }

    public void layoutActionControls() {
        if (control == null || actionButtonLayout == null) return;

        actionButtonLayout.remove();
        actionPad.clearListeners();
        actionPad.clearChildren();
        control.clearChildren();
        control.clearListeners();

        Touchable slotTouch = Touchable.enabled;
        if (slot1 != null) slot1.setTouchable(slotTouch);
        if (slot2 != null) slot2.setTouchable(slotTouch);
        if (slot3 != null) slot3.setTouchable(slotTouch);
        if (slot4 != null) slot4.setTouchable(slotTouch);

        control.row();
        control.add().expand();
        control.row();
        control.add().expand();

        if (fixedActionButtons) {
            actionPad.setVisible(false);
            control.row();
            control.add( actionButtonLayout ).right().bottom().padRight(150).padBottom(150);
        } else {
            actionPad.add( actionButtonLayout );
            actionPad.pack();
            actionPad.setVisible(false);
            actionPad.addListener( createSwipeActionPadListener() );
            if (slot1 != null) slot1.setTouchable(Touchable.disabled);
            if (slot2 != null) slot2.setTouchable(Touchable.disabled);
            if (slot3 != null) slot3.setTouchable(Touchable.disabled);
            if (slot4 != null) slot4.setTouchable(Touchable.disabled);
        }

        control.addListener( createControlTouchListener() );

        if (stage != null) {
            stage.clear();
            stage.addActor(control);
            if (!fixedActionButtons) stage.addActor(actionPad);
            stage.addActor(tpad);
        }
    }
    boolean pressed = false;

    void keyinput() {

        if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE )) escapegame();
        if (Gdx.input.isKeyJustPressed( Input.Keys.BACK )) escapegame();
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && isDialog){
            dd.hide();
            isDialog=false;
            Gdx.input.setInputProcessor( stage );
        }
        if (starting) return;
        if (player.state == gameobject.states.DEAD) return;
        if (!disablecontrol) {
            pressed=false;
            //GAMEPAD
            if (gamepad!=null){
                tslot1 = gamepad.getButton(gamepad.getMapping().buttonB);
                tslot2 = gamepad.getButton(gamepad.getMapping().buttonA);
                tslot3 = gamepad.getButton(gamepad.getMapping().buttonX);
                tslot4 = gamepad.getButton(gamepad.getMapping().buttonY);
                if (gamepad.getButton(gamepad.getMapping().buttonDpadLeft)){
                    pressleft();
                    pressed = true;
                }
                if (gamepad.getButton(gamepad.getMapping().buttonDpadRight)){
                    pressright();
                    pressed = true;
                }
                if (gamepad.getButton(gamepad.getMapping().buttonDpadUp)){
                    pressup();
                    pressed = true;
                }
                if (gamepad.getButton(gamepad.getMapping().buttonDpadDown)){
                    pressdown();
                    pressed = true;
                }

            }


            //DESKTOP
            if (Gdx.app.getType() == Application.ApplicationType.Desktop && gamepad==null) {
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
                if (actionTouchPointer >= 0 && !fixedActionButtons) hideActionPadAfterUse();
            }
            if (tslot2 && action2 != null && action2.action != gameobject.actions.NONE) {
                act( action2 );
                pressed = true;
                if (actionTouchPointer >= 0 && !fixedActionButtons) hideActionPadAfterUse();
            }
            if (tslot3 && action3 != null && action3.action != gameobject.actions.NONE) {
                act( action3 );
                pressed = true;
                if (actionTouchPointer >= 0 && !fixedActionButtons) hideActionPadAfterUse();
            }
            if (tslot4 && action4 != null && action4.action != gameobject.actions.NONE) {
                act( action4 );
                pressed = true;
                if (actionTouchPointer >= 0 && !fixedActionButtons) hideActionPadAfterUse();
            }

            if (analogTouchPointer >= 0 || gamepad!=null) {
                float deltaX;
                float deltaY;
                if (gamepad==null) {
                    deltaX = tpad.getKnobPercentX();
                    deltaY = tpad.getKnobPercentY();
                }else{
                    deltaX = gamepad.getAxis(gamepad.getMapping().axisLeftX);
                    deltaY = gamepad.getAxis(gamepad.getMapping().axisLeftY);
                    log(deltaX+"---"+deltaY);
                }
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
                    if (touchpoint != null) {
                        touchCooldown=1f;
                        touchpoint.body.setTransform( touch2.x, touch2.y, 0 );
                        touchpoint.body.setAwake( true );
                    }
                }
            }
        }


    }

    float touchCooldown=-1f;
    public void dispose(){
        dismissMessage();
        batch.dispose();
        if (str1 != null) {
            str1.dispose();
            str1 = null;
        }
        if (skin != null) {
            skin.dispose();
            skin = null;
        }
        if (skin2 != null) {
            skin2.dispose();
            skin2 = null;
        }
        if (netServer != null) {
            netServer.stop();
            netServer = null;
        }
        if (netClient != null) {
            netClient.stop();
            netClient = null;
        }
    }

    public void escapegame(){
        if (bgm!=null && bgm.isPlaying()) bgm.stop();
        shutdown=true;
        hasStartedPlaying = false;
        if (netServer != null) {
            netServer.stop();
            netServer = null;
        }
        if (netClient != null) {
            netClient.stop();
            netClient = null;
        }
    }

    boolean isDialog=false;
    Dialog dd;

    public static String propString(MapProperties props, String... keys) {
        if (props == null) return null;
        for (String key : keys) {
            if (props.containsKey(key)) {
                Object val = props.get(key);
                if (val != null) {
                    String s = val.toString().trim();
                    if (!s.isEmpty()) return s;
                }
            }
        }
        return null;
    }

    public void showMessage(String text, String picture, String speaker, String sounds) {
        if (text == null || text.trim().isEmpty()) return;
        if (starting) return;
        briefing = replaceVars(text).split("//");
        msgindex = 0;
        messagePicturePath = picture;
        messageSpeaker = speaker;
        messageSounds = (sounds != null && !sounds.trim().isEmpty())
                ? replaceVars(sounds).split("//") : null;
        starting = true;
        if (player != null && player.body != null) {
            player.body.setLinearVelocity(0, 0);
        }
        loadMessageImage();
        enableMessageInput();
        showMessagePage();
    }

    public void showMessage(String text, String picture, String speaker) {
        showMessage(text, picture, speaker, null);
    }

    private void loadMessageImage() {
        disposeMessageImage();
        if (messagePicturePath == null || messagePicturePath.isEmpty()) return;
        FileHandle imgFile = getFile(path + "/" + messagePicturePath);
        if (imgFile.exists()) {
            messageImage = new Texture(imgFile);
        }
    }

    private void enableMessageInput() {
        messageInput = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.SPACE || keycode == Input.Keys.ENTER) {
                    advanceMessage();
                    return true;
                }
                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                    escapegame();
                    return true;
                }
                return true;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                advanceMessage();
                return true;
            }
        };
        Gdx.input.setInputProcessor(messageInput);
    }

    private void disableMessageInput() {
        messageInput = null;
        if (!isDialog && !isMainMenuActive) {
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void showMessagePage() {
        if (briefing == null || msgindex >= briefing.length) {
            dismissMessage();
            return;
        }

        String pageText = briefing[msgindex].trim();
        if (pageText.isEmpty()) {
            msgindex++;
            showMessagePage();
            return;
        }

        playMessageSoundForPage(msgindex);
    }

    private void drawMessageBoxBackground() {
        if (!starting || briefing == null || msgindex >= briefing.length) return;

        float sw = uicamVP.getWorldWidth();
        float sh = uicamVP.getWorldHeight();
        float boxW = Math.min(sw * 0.92f, 1700f);
        float boxH = 260f;
        float boxX = (sw - boxW) / 2f;
        float boxY = 50f;

        uis.setProjectionMatrix(uicam.combined);
        uis.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        uis.setColor(0f, 0f, 0f, 0.55f);
        uis.rect(0, 0, sw, sh);
        uis.setColor(0.12f, 0.12f, 0.18f, 0.95f);
        uis.rect(boxX, boxY, boxW, boxH);
        uis.end();

        uis.begin(ShapeRenderer.ShapeType.Line);
        uis.setColor(0.65f, 0.65f, 0.75f, 1f);
        uis.rect(boxX, boxY, boxW, boxH);
        uis.end();
    }

    private void drawMessageBox(SpriteBatch b) {
        if (!starting || briefing == null || msgindex >= briefing.length) return;

        String pageText = briefing[msgindex].trim();
        if (pageText.isEmpty()) return;

        float sw = uicamVP.getWorldWidth();
        float boxW = Math.min(sw * 0.92f, 1700f);
        float boxH = 260f;
        float boxX = (sw - boxW) / 2f;
        float boxY = 50f;
        float pad = 24f;
        float imgW = 160f;
        float textX = boxX + pad;
        float textW = boxW - pad * 2f;

        if (messageImage != null) {
            float imgH = imgW * messageImage.getHeight() / (float) messageImage.getWidth();
            b.draw(messageImage, boxX + pad, boxY + (boxH - imgH) / 2f, imgW, imgH);
            textX += imgW + pad;
            textW -= imgW + pad;
        }

        float prevScale = str1.getData().scaleX;
        str1.getData().setScale(1f);
        if (messageSpeaker != null && !messageSpeaker.isEmpty()) {
            str1.setColor(0.85f, 0.9f, 1f, 1f);
            str1.draw(b, messageSpeaker, textX, boxY + boxH - pad, textW, Align.left, false);
        }

        str1.setColor(Color.WHITE);
        str1.draw(b, pageText, textX, boxY + boxH - 70f, textW, Align.left, true);

        boolean lastPage = msgindex >= briefing.length - 1;
        String hint = (lastPage ? "OK" : "Next") + "  \u25B6";
        str1.setColor(0.75f, 0.75f, 0.8f, 1f);
        str1.draw(b, hint, boxX + boxW - pad, boxY + pad, 0, Align.right, false);
        str1.setColor(Color.WHITE);
        str1.getData().setScale(prevScale);
    }

    private void playMessageSoundForPage(int index) {
        stopMessageSound();
        if (messageSounds == null || index >= messageSounds.length) return;
        String clip = messageSounds[index].trim();
        if (clip.isEmpty()) return;

        FileHandle file = getFile(path + "/" + clip);
        if (!file.exists()) return;

        if (clip.toLowerCase().endsWith(".mp3")) {
            messageMusic = Gdx.audio.newMusic(file);
            messageMusic.setVolume(1f);
            messageMusic.play();
        } else {
            messageSound = addSfx(path + "/" + clip);
            if (messageSound != null) {
                messageSoundId = messageSound.play(1.0f);
            }
        }
    }

    private void stopMessageSound() {
        if (messageMusic != null) {
            messageMusic.stop();
            messageMusic.dispose();
            messageMusic = null;
        }
        if (messageSound != null && messageSoundId != -1) {
            messageSound.stop(messageSoundId);
            messageSoundId = -1;
        }
    }

    public void advanceMessage() {
        msgindex++;
        if (briefing != null && msgindex < briefing.length) {
            showMessagePage();
        } else {
            dismissMessage();
        }
    }

    public void dismissMessage() {
        starting = false;
        stopMessageSound();
        disposeMessageImage();
        disableMessageInput();
        briefing = null;
        msgindex = 0;
        messagePicturePath = null;
        messageSpeaker = null;
        messageSounds = null;
    }

    private void disposeMessageImage() {
        if (messageImage != null) {
            messageImage.dispose();
            messageImage = null;
        }
    }

    public void msgbox(String msg) {
        if (isDialog) return;
        Gdx.input.setInputProcessor( stage2 );
        isDialog=true;
        dd = new Dialog("",skin);
        dd.row();

        Label lab = new Label( msg, skin );
        lab.setWrap( true );
        ScrollPane sp = new ScrollPane(lab);
        dd.add(sp).maxHeight(400).width(800).pad(5).row();

        TextButton butt = new TextButton("OK",skin);
        butt.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dd.hide();
                isDialog=false;
                if (isMainMenuActive) {
                    Gdx.input.setInputProcessor( stage2 );
                } else {
                    Gdx.input.setInputProcessor( stage );
                }
            }
        } );
        dd.add(butt).width(800).row();
        stage2.setKeyboardFocus(butt);
        dd.show(stage2);
    }

    public static float safeParseFloat(Object obj, float defaultVal) {
        if (obj == null) return defaultVal;
        String val = obj.toString().trim();
        val = val.replace(" ", "");
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static int safeParseInt(Object obj, int defaultVal) {
        if (obj == null) return defaultVal;
        String val = obj.toString().trim();
        val = val.replace(" ", "");
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    // Packet definitions for Kryonet
    public static class PositionSyncPacket {
        public String id;
        public float x, y;
        public float vx, vy;
        public boolean flipX;
        public String stateName;
    }

    public static class ShootPacket {
        public String shooterId;
        public float x, y;
        public float vx, vy;
        public int dir;
        public float damage;
        public float speed;
        public int maxdistance;
        public float spread;
    }

    public static class DamagePacket {
        public int objectIndex;
        public float damage;
    }

    public static class HandshakePacket {
        public String gameName;
    }

    public static class NicknamePacket {
        public String id;
    }

    public static class LobbyStatusPacket {
        public ArrayList<String> playerNames = new ArrayList<>();
    }

    public static class StartGamePacket {
    }

    // Multiplayer Lobby Fields
    public ArrayList<String> lobbyPlayers = new ArrayList<>();

    // Multiplayer Server/Client Fields
    public Server netServer;
    public Client netClient;
    private float syncTimer = 0f;

    public void sendLocalPlayerUpdate() {
        if (player == null) return;
        syncTimer += Gdx.graphics.getDeltaTime();
        if (syncTimer >= 0.05f) {
            syncTimer = 0f;
            PositionSyncPacket packet = new PositionSyncPacket();
            packet.id = playerId;
            packet.x = player.body != null ? player.body.getPosition().x : player.getX();
            packet.y = player.body != null ? player.body.getPosition().y : player.getY();
            packet.vx = player.body != null ? player.body.getLinearVelocity().x : 0;
            packet.vy = player.body != null ? player.body.getLinearVelocity().y : 0;
            packet.flipX = player.isFlipX();
            packet.stateName = player.state != null ? player.state.name() : "IDLE";

            if (isHost && netServer != null) {
                netServer.sendToAllUDP(packet);
            } else if (netClient != null && netClient.isConnected()) {
                netClient.sendUDP(packet);
            }
        }
    }

    public void startNetworkServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    netServer = new Server(16384, 8192);
                    Kryo kryo = netServer.getKryo();
                    kryo.register(PositionSyncPacket.class);
                    kryo.register(ShootPacket.class);
                    kryo.register(DamagePacket.class);
                    kryo.register(HandshakePacket.class);
                    kryo.register(NicknamePacket.class);
                    kryo.register(LobbyStatusPacket.class);
                    kryo.register(StartGamePacket.class);
                    kryo.register(ArrayList.class);

                    netServer.start();
                    netServer.bind(54555, 54556);

                    netServer.addListener(new Listener() {
                        @Override
                        public void connected(Connection connection) {
                            HandshakePacket hp = new HandshakePacket();
                            hp.gameName = gameName;
                            connection.sendTCP(hp);
                        }

                        @Override
                        public void disconnected(Connection connection) {
                            // Find and remove disconnected client
                            // Reset lobby list
                        }

                        @Override
                        public void received(Connection connection, Object object) {
                            if (object instanceof PositionSyncPacket) {
                                PositionSyncPacket p = (PositionSyncPacket) object;
                                netServer.sendToAllExceptUDP(connection.getID(), p);
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateRemotePlayer(p);
                                    }
                                });
                            } else if (object instanceof ShootPacket) {
                                ShootPacket p = (ShootPacket) object;
                                netServer.sendToAllExceptTCP(connection.getID(), p);
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleRemoteShoot(p);
                                    }
                                });
                            } else if (object instanceof DamagePacket) {
                                DamagePacket p = (DamagePacket) object;
                                netServer.sendToAllExceptTCP(connection.getID(), p);
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleRemoteDamage(p);
                                    }
                                });
                            } else if (object instanceof NicknamePacket) {
                                NicknamePacket p = (NicknamePacket) object;
                                if (!lobbyPlayers.contains(p.id)) {
                                    lobbyPlayers.add(p.id);
                                }
                                LobbyStatusPacket lsp = new LobbyStatusPacket();
                                lsp.playerNames.addAll(lobbyPlayers);
                                netServer.sendToAllTCP(lsp);
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        setupLobbyUI();
                                    }
                                });
                            }
                        }
                    });

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            isHost = true;
                            isMultiplayerActive = true;
                            lobbyPlayers.clear();
                            lobbyPlayers.add(playerId);
                            setupLobbyUI();
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            msgbox("Failed to start server: " + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public void startNetworkClient(final String hostIp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    netClient = new Client(16384, 8192);
                    Kryo kryo = netClient.getKryo();
                    kryo.register(PositionSyncPacket.class);
                    kryo.register(ShootPacket.class);
                    kryo.register(DamagePacket.class);
                    kryo.register(HandshakePacket.class);
                    kryo.register(NicknamePacket.class);
                    kryo.register(LobbyStatusPacket.class);
                    kryo.register(StartGamePacket.class);
                    kryo.register(ArrayList.class);

                    netClient.start();
                    netClient.connect(5000, hostIp, 54555, 54556);

                    netClient.addListener(new Listener() {
                        @Override
                        public void received(Connection connection, Object object) {
                            if (object instanceof PositionSyncPacket) {
                                final PositionSyncPacket p = (PositionSyncPacket) object;
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateRemotePlayer(p);
                                    }
                                });
                            } else if (object instanceof ShootPacket) {
                                final ShootPacket p = (ShootPacket) object;
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleRemoteShoot(p);
                                    }
                                });
                            } else if (object instanceof DamagePacket) {
                                final DamagePacket p = (DamagePacket) object;
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleRemoteDamage(p);
                                    }
                                });
                            } else if (object instanceof HandshakePacket) {
                                final HandshakePacket hp = (HandshakePacket) object;
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!hp.gameName.equalsIgnoreCase(gameName)) {
                                            if (netClient != null) {
                                                netClient.stop();
                                                netClient = null;
                                            }
                                            isMultiplayerActive = false;
                                            isMainMenuActive = true;
                                            Gdx.input.setInputProcessor(stage2);
                                            setupMainMenuUI();
                                            msgbox("Game mismatch! Host is running: " + hp.gameName + ", but you have loaded: " + gameName);
                                        } else {
                                            // Handshake OK, send nickname
                                            NicknamePacket np = new NicknamePacket();
                                            np.id = playerId;
                                            netClient.sendTCP(np);
                                        }
                                    }
                                });
                            } else if (object instanceof LobbyStatusPacket) {
                                final LobbyStatusPacket lsp = (LobbyStatusPacket) object;
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        lobbyPlayers.clear();
                                        lobbyPlayers.addAll(lsp.playerNames);
                                        setupLobbyUI();
                                    }
                                });
                            } else if (object instanceof StartGamePacket) {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        isMainMenuActive = false;
                                        hasStartedPlaying = true;
                                        selectLocalPlayer();
                                        if (player != null && player.body != null) {
                                            gamecam.zoom = 0.2f;
                                            gamecam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
                                            gamecam.update();
                                        }
                                        Gdx.input.setInputProcessor(stage);
                                    }
                                });
                            }
                        }
                    });

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            isHost = false;
                            isMultiplayerActive = true;
                            setupLobbyUI();
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            msgbox("Failed to connect to host: " + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public void updateRemotePlayer(PositionSyncPacket p) {
        gameobject remote = findOrCreateRemotePlayer(p.id);
        if (remote != null) {
            if (remote.body != null) {
                remote.body.setTransform(p.x, p.y, 0);
                remote.body.setLinearVelocity(p.vx, p.vy);
            }
            remote.setFlip(p.flipX, false);
            try {
                remote.state = gameobject.states.valueOf(p.stateName);
            } catch (Exception ignored) {}
        }
    }

    public gameobject findOrCreateRemotePlayer(String id) {
        gameobject remote = null;
        for (gameobject go : objects) {
            if (go.objtype == gameobject.objecttype.PLAYER && id.equals(go.id)) {
                remote = go;
                break;
            }
        }

        if (remote == null) {
            remote = new gameobject();
            remote.mygame = this;
            remote.id = id;
            float startX = 0f;
            float startY = 0f;
            float width = 1f;
            float height = 1f;
            if (player != null) {
                startX = player.getX();
                startY = player.getY();
                width = player.getWidth();
                height = player.getHeight();
            }

            remote.setupGameObject(world, null, startX, startY, width, height,
                                   BodyDef.BodyType.KinematicBody, gameobject.objecttype.PLAYER, null, null, false, 1.0f);
            remote.setAlpha(1.0f);
            objects.add(remote);
        }

        // Always ensure animations and size are backfilled once local player is initialized
        if (player != null) {
            if ((remote.anim == null || remote.anim.isEmpty()) && player.anim != null && !player.anim.isEmpty()) {
                remote.anim = new ArrayList<>(player.anim);
            }
            if ((remote.animID == null || remote.animID.isEmpty()) && player.animID != null) {
                remote.animID = new ArrayList<>(player.animID);
            }
            if (remote.pimagesize == null && player.pimagesize != null) {
                remote.pimagesize = new Vector2(player.pimagesize);
            }
            if (remote.getTexture() == null && player.getTexture() != null) {
                remote.setRegion(player);
                remote.setSize(player.getWidth(), player.getHeight());
            }
            remote.setAlpha(1.0f);
        }

        return remote;
    }

    public void handleRemoteShoot(ShootPacket p) {
        gameobject newbrick = new gameobject();
        newbrick.mygame = this;
        newbrick.speed = p.speed;
        newbrick.maxdistance = p.maxdistance;
        newbrick.spread = p.spread;
        newbrick.damage = (int) p.damage;
        newbrick.dir = p.dir;

        if (action1 != null && action1.action == gameobject.actions.SHOOT) {
            newbrick.anim = action1.panim;
            newbrick.deadtileID = action1.pdeadanim;
            newbrick.pimagesize = action1.pimagesize;
        } else if (action2 != null && action2.action == gameobject.actions.SHOOT) {
            newbrick.anim = action2.panim;
            newbrick.deadtileID = action2.pdeadanim;
            newbrick.pimagesize = action2.pimagesize;
        } else {
            newbrick.pimagesize = new Vector2(16, 16);
        }

        newbrick.setupGameObject(world, null, p.x, p.y, newbrick.pimagesize.x/2f, newbrick.pimagesize.y/2f, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLAYERPROJECTILE, null, null, false, 1);
        this.objects.add(newbrick);
    }

    public void handleRemoteDamage(DamagePacket p) {
        if (p.objectIndex >= 0 && p.objectIndex < objects.size()) {
            gameobject go = objects.get(p.objectIndex);
            go.HP -= p.damage;
            if (go.HP < 0) go.HP = 0;
        }
    }

    public List<gameobject> playerSpawns = new ArrayList<>();
    public void selectLocalPlayer() {
        playerSpawns.clear();
        for (gameobject go : objects) {
            // Only count player objects that do NOT have a custom remote player ID (i.e. actual map spawn placeholders)
            if (go.objtype == gameobject.objecttype.PLAYER && (go.id == null || go.id.isEmpty())) {
                playerSpawns.add(go);
            }
        }

        if (playerSpawns.isEmpty()) {
            player = null;
            Gdx.app.log("MultiplayerSpawning", "Warning: playerSpawns is empty!");
            return;
        }

        if (!isMultiplayerActive) {
            if (player == null) {
                player = playerSpawns.get(0);
            }
            return;
        }

        // Determine player lobby index
        int myIndex = lobbyPlayers.indexOf(playerId);
        if (myIndex < 0) {
            myIndex = 0;
        }

        Gdx.app.log("MultiplayerSpawning", "playerId: " + playerId + ", lobbyPlayers: " + lobbyPlayers + ", myIndex: " + myIndex + ", spawnsCount: " + playerSpawns.size());

        String targetPlayer = String.valueOf(myIndex + 1);

        gameobject selected = null;
        for (gameobject go : playerSpawns) {
            MapProperties prop = go.obj != null ? go.obj.getProperties() : (go.tlcece != null ? go.tlcece.getProperties() : null);
            if (prop != null && prop.containsKey("player")) {
                String pVal = prop.get("player").toString().trim();
                if (pVal.equalsIgnoreCase(targetPlayer)) {
                    selected = go;
                    break;
                }
            }
        }

        if (selected == null) {
            List<gameobject> randomSpawns = new ArrayList<>();
            for (gameobject go : playerSpawns) {
                MapProperties prop = go.obj != null ? go.obj.getProperties() : (go.tlcece != null ? go.tlcece.getProperties() : null);
                if (prop != null && prop.containsKey("player")) {
                    String pVal = prop.get("player").toString().trim();
                    if (pVal.equalsIgnoreCase("random")) {
                        randomSpawns.add(go);
                    }
                }
            }
            if (!randomSpawns.isEmpty()) {
                int spawnIdx = myIndex % randomSpawns.size();
                selected = randomSpawns.get(spawnIdx);
                Gdx.app.log("MultiplayerSpawning", "Selected random spawn at index " + spawnIdx + " of " + randomSpawns.size());
            }
        }

        if (selected == null) {
            selected = playerSpawns.get(myIndex % playerSpawns.size());
            Gdx.app.log("MultiplayerSpawning", "Selected fallback spawn at index " + (myIndex % playerSpawns.size()));
        }

        player = selected;
        player.setAlpha(1.0f);

        for (gameobject go : playerSpawns) {
            if (go != player) {
                if (go.body != null) {
                    world.destroyBody(go.body);
                    go.body = null;
                }
                go.setAlpha(0.0f);
            }
        }
    }

    public void setupMainMenuUI() {
        stage2.clear();
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(15).width(900).height(100);

        Label titleLabel = new Label(gameName, skin);
        titleLabel.setFontScale(1.5f);
        table.add(titleLabel).width(900).row();

        TextButton singleBtn = new TextButton("Single Player", skin);
        singleBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isMainMenuActive = false;
                hasStartedPlaying = true;
                selectLocalPlayer();
                if (player != null && player.body != null) {
                    gamecam.zoom = 0.2f;
                    gamecam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
                    gamecam.update();
                }
                Gdx.input.setInputProcessor(stage);
            }
        });
        table.add(singleBtn).row();

        if (isMultiplayerEnabled) {
            TextButton multiBtn = new TextButton("Multiplayer", skin);
            multiBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setupMultiplayerUI();
                }
            });
            table.add(multiBtn).row();
        }

        TextButton exitBtn = new TextButton("Exit", skin);
        exitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                escapegame();
            }
        });
        table.add(exitBtn).row();

        stage2.addActor(table);
    }

    public void setupMultiplayerUI() {
        stage2.clear();
        final Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(15).width(900).height(100);

        Label titleLabel = new Label("LAN Multiplayer Settings", skin);
        table.add(titleLabel).width(900).row();

        Label nameLabel = new Label("Your Nickname:", skin);
        table.add(nameLabel).row();

        final TextField nameField = new TextField(playerId, skin);
        table.add(nameField).row();

        TextButton hostBtn = new TextButton("Host Game", skin);
        hostBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerId = nameField.getText().trim();
                if (playerId.isEmpty()) {
                    playerId = "Player_" + (int)(Math.random()*10000);
                }
                startNetworkServer();
            }
        });
        table.add(hostBtn).row();

        final TextButton joinBtn = new TextButton("Join Game (Scan LAN)", skin);
        joinBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerId = nameField.getText().trim();
                if (playerId.isEmpty()) {
                    playerId = "Player_" + (int)(Math.random()*10000);
                }
                scanForLANGames(table);
            }
        });
        table.add(joinBtn).row();

        final TextField ipField = new TextField("192.168.1.", skin);
        table.add(ipField).row();

        TextButton manualJoinBtn = new TextButton("Connect directly to IP", skin);
        manualJoinBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playerId = nameField.getText().trim();
                if (playerId.isEmpty()) {
                    playerId = "Player_" + (int)(Math.random()*10000);
                }
                String ip = ipField.getText().trim();
                if (!ip.isEmpty()) {
                    startNetworkClient(ip);
                } else {
                    msgbox("Please enter a valid IP address!");
                }
            }
        });
        table.add(manualJoinBtn).row();

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setupMainMenuUI();
            }
        });
        table.add(backBtn).row();

        stage2.addActor(table);
    }

    public void setupLobbyUI() {
        stage2.clear();
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(15).width(900).height(100);

        Label titleLabel = new Label("Lobby: " + gameName, skin);
        table.add(titleLabel).row();

        Label statusLabel = new Label(isHost ? "Status: Hosting (Waiting for players...)" : "Status: Connected (Waiting for Host to start...)", skin);
        table.add(statusLabel).row();

        Label playersHeader = new Label("Players in lobby:", skin);
        table.add(playersHeader).row();

        for (String name : lobbyPlayers) {
            Label nameLabel = new Label(" • " + name, skin);
            table.add(nameLabel).row();
        }

        if (isHost) {
            TextButton startBtn = new TextButton("Start Game", skin);
            startBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (lobbyPlayers.size() <= 1) {
                        msgbox("Cannot start multiplayer game with only 1 player!");
                        return;
                    }
                    if (netServer != null) {
                        netServer.sendToAllTCP(new StartGamePacket());
                    }
                    isMainMenuActive = false;
                    hasStartedPlaying = true;
                    selectLocalPlayer();
                    if (player != null && player.body != null) {
                        gamecam.zoom = 0.2f;
                        gamecam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
                        gamecam.update();
                    }
                    Gdx.input.setInputProcessor(stage);
                }
            });
            table.add(startBtn).row();
        }

        TextButton leaveBtn = new TextButton("Leave", skin);
        leaveBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (netServer != null) {
                    netServer.stop();
                    netServer = null;
                }
                if (netClient != null) {
                    netClient.stop();
                    netClient = null;
                }
                isMultiplayerActive = false;
                hasStartedPlaying = false;
                setupMultiplayerUI();
            }
        });
        table.add(leaveBtn).row();

        stage2.addActor(table);
    }

    private void scanForLANGames(final Table table) {
        table.clear();
        table.defaults().pad(15).width(900).height(100);
        Label scanningLabel = new Label("Scanning LAN for games...", skin);
        table.add(scanningLabel).row();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client discoverClient = new Client(16384, 8192);
                    discoverClient.getKryo().register(PositionSyncPacket.class);
                    discoverClient.getKryo().register(ShootPacket.class);
                    discoverClient.getKryo().register(DamagePacket.class);
                    discoverClient.getKryo().register(HandshakePacket.class);
                    discoverClient.getKryo().register(NicknamePacket.class);
                    discoverClient.getKryo().register(LobbyStatusPacket.class);
                    discoverClient.getKryo().register(StartGamePacket.class);
                    discoverClient.getKryo().register(ArrayList.class);
                    discoverClient.start();
                    List<InetAddress> discovered = discoverClient.discoverHosts(54556, 2000);
                    if (discovered == null || discovered.isEmpty()) {
                        discovered = discoverClient.discoverHosts(54556, 1500);
                    }
                    final List<InetAddress> hosts = discovered;
                    discoverClient.stop();

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            table.clear();
                            table.defaults().pad(15).width(900).height(100);
                            Label titleLabel = new Label("Discovered Games", skin);
                            table.add(titleLabel).row();

                            if (hosts == null || hosts.isEmpty()) {
                                Label noneLabel = new Label("No games found on local network.", skin);
                                table.add(noneLabel).row();
                            } else {
                                for (final InetAddress host : hosts) {
                                    TextButton connectBtn = new TextButton("Connect: " + host.getHostAddress(), skin);
                                    connectBtn.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            startNetworkClient(host.getHostAddress());
                                        }
                                    });
                                    table.add(connectBtn).row();
                                }
                            }

                            TextButton backBtn = new TextButton("Back", skin);
                            backBtn.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    setupMultiplayerUI();
                                }
                            });
                            table.add(backBtn).row();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


