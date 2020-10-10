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
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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

import static com.mirwanda.nottiled.platformer.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.COIN;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.GEAR;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.ITEM;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.platformer.gameobject.objecttype.SPIKE;


public class game {
    public boolean debugmode = false;
    public boolean playtest=true;

    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;
    public boolean loadingmap;
    public boolean rpg=false;
    public boolean uitest=false;
    public int dir;

    public WorldContactListener mycontactlistener;
    public savegame save = new savegame();

    public Vector2 checkpoint = new Vector2();
    public com.mirwanda.nottiled.platformer.player player;
    public java.util.List<gameobject> objects = new ArrayList<gameobject>();;
    public java.util.List<wall> walls  = new ArrayList<wall>();;

    //TILE
    public java.util.List<TiledMapTile> tlbrick=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlboxes=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlcheckpoints=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlcoins=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlspikes=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlgears=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlgirls=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlkeys=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tllocks=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlbreakables=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlsprings=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlswitches=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlswitchons=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlswitchoffs=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlplatformh=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlplatformv=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlplatforms=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlmonsters=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlmiscs=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlladder=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlfloater=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlsinker=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlleftslope=new ArrayList<TiledMapTile>();
    public java.util.List<TiledMapTile> tlrightslope=new ArrayList<TiledMapTile>();

    Animation<TextureRegion> animMonster; // Must declare frame type (TextureRegion)
    Texture txMonster;
    java.util.List<Animation<TextureRegion>> animPlayer = new ArrayList<>(); // Must declare frame type (TextureRegion)
    Texture txPlayer;
    public Texture txBackground;
    public TiledMapTile tlplayer=null;
    public TiledMapTile tlempty=null;

    //SOUND
    public Music bgm;
    public Sound sfxcheckpoint;
    public Sound sfxspring;
    public Sound sfxcoin;
    public Sound sfxmonster;
    public Sound sfxswitch;
    public Sound sfxgirl;
    public Sound sfxplayer;
    public Sound sfxkey;
    public Sound sfxlock;

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
    public int Tw;
    public int Th;
    public float Tsw;
    public float Tsh;
    public ParticleEffect pe;
    public boolean gravity=true;

    public boolean initialise(String path, String filename){

        this.path = path;
        this.file = filename;
        rpg=false;
        //starting = true;
        pe = new ParticleEffect();

        if (playtest) {
            pe.load( Gdx.files.external( path + "/died.p" ), Gdx.files.external( path ) );
        }else{
            pe.load( Gdx.files.internal( path + "/died.p" ), Gdx.files.internal( path ) );

        }
        pe.getEmitters().first().setPosition( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        pe.scaleEffect(0.001f, 0.001f); //kudu disini

        objects = new ArrayList<gameobject>();;
        walls  = new ArrayList<wall>();;

        tlbrick=new ArrayList<TiledMapTile>();
        tlboxes=new ArrayList<TiledMapTile>();
        tlcheckpoints=new ArrayList<TiledMapTile>();
        tlcoins=new ArrayList<TiledMapTile>();
        tlspikes=new ArrayList<TiledMapTile>();
        tlgears=new ArrayList<TiledMapTile>();
        tlgirls=new ArrayList<TiledMapTile>();
        tlkeys=new ArrayList<TiledMapTile>();
        tllocks=new ArrayList<TiledMapTile>();
        tlbreakables=new ArrayList<TiledMapTile>();
        tlsprings=new ArrayList<TiledMapTile>();
        tlswitches=new ArrayList<TiledMapTile>();
        tlswitchons=new ArrayList<TiledMapTile>();
        tlswitchoffs=new ArrayList<TiledMapTile>();
        tlplatformh=new ArrayList<TiledMapTile>();
        tlplatformv=new ArrayList<TiledMapTile>();
        tlplatforms=new ArrayList<TiledMapTile>();
        tlmonsters=new ArrayList<TiledMapTile>();
        tlmiscs=new ArrayList<TiledMapTile>();
        tlladder=new ArrayList<TiledMapTile>();
        tlfloater=new ArrayList<TiledMapTile>();
        tlsinker=new ArrayList<TiledMapTile>();
        tlleftslope=new ArrayList<TiledMapTile>();
        tlrightslope=new ArrayList<TiledMapTile>();

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

                if (playtest) {
                    if (Gdx.files.external( path + "/" + bgms ).exists()) {
                        bgm = Gdx.audio.newMusic( Gdx.files.external( path + "/" + bgms ) );
                        bgm.setLooping( true );
                        bgm.play();
                    }
                }else{
                    if (Gdx.files.internal( path + "/" + bgms ).exists()) {
                        bgm = Gdx.audio.newMusic( Gdx.files.internal( path + "/" + bgms ) );
                        bgm.setLooping( true );
                        bgm.play();
                    }

                }

            }


            String bgc = (String) mpa.get("background");
            if (bgc != null) {

                if (playtest) {

                    if (Gdx.files.external( path + "/" + bgc ).exists()) {
                        txBackground = new Texture( Gdx.files.external( path + "/" + bgc ) );
                    } else {
                        txBackground = null;
                    }
                }else
                {
                    if (Gdx.files.internal( path + "/" + bgc ).exists()) {
                        txBackground = new Texture( Gdx.files.internal( path + "/" + bgc ) );
                    } else {
                        txBackground = null;
                    }

                }
            }

            nextlevel = (String) mpa.get("nextlevel");
            //briefing = (String) mpa.get("briefing");
            debriefing = (String) mpa.get("debriefing");
            died = (String) mpa.get("diedmsg");

        }


        stateTime = 0f;
        playerTime = 0f;


        for (TiledMapTileSet tset : map.getTileSets()) {
            TiledMapTileSet ttset = tset;
            ttset.size();
            for (int ts = 0; ts < ttset.size(); ts++) {
                TiledMapTile tltemp = ttset.getTile(ts);

                if (tltemp != null) {
                    MapProperties mp = tltemp.getProperties();
                    if (mp != null) {
                        String propname = (String) mp.get("name");
                        if (propname != null) {

                            switch (propname) {
                                case "brick":
                                    tlbrick.add(tltemp);
                                    break;
                                case "box":
                                    tlboxes.add(tltemp);
                                    break;
                                case "checkpoint":
                                    tlcheckpoints.add(tltemp);
                                    break;
                                case "player":
                                    tlplayer = tltemp;
                                    break;
                                case "empty":
                                    tlempty = tltemp;
                                    break;
                                case "coin":
                                    tlcoins.add(tltemp);
                                    break;
                                case "spike":
                                    tlspikes.add(tltemp);
                                    break;
                                case "gear":
                                    tlgears.add(tltemp);
                                    break;
                                case "girl":
                                    tlgirls.add(tltemp);
                                    break;
                                case "key":
                                    tlkeys.add(tltemp);
                                    break;
                                case "lock":
                                    tllocks.add(tltemp);
                                    break;
                                case "breakable":
                                    tlbreakables.add(tltemp);
                                    break;
                                case "spring":
                                    tlsprings.add(tltemp);
                                    break;
                                case "switch":
                                    tlswitches.add(tltemp);
                                    break;
                                case "switchon":
                                    tlswitchons.add(tltemp);
                                    break;
                                case "switchoff":
                                    tlswitchoffs.add(tltemp);
                                    break;
                                case "platformh":
                                    tlplatformh.add(tltemp);
                                    break;
                                case "platformv":
                                    tlplatformv.add(tltemp);
                                    break;
                                case "platforms":
                                    tlplatforms.add(tltemp);
                                    break;
                                case "monster":
                                    tlmonsters.add(tltemp);
                                    break;
                                case "ladder":
                                    tlladder.add(tltemp);
                                    break;
                                case "floater":
                                    tlfloater.add(tltemp);
                                    break;
                                case "sinker":
                                    tlsinker.add(tltemp);
                                case "leftslope":
                                    tlleftslope.add(tltemp);
                                    break;
                                case "rightslope":
                                    tlrightslope.add(tltemp);
                                    break;
                            }
                        } else {
                            tlmiscs.add(tltemp);
                        }


                        String anim = (String) mp.get("anim");
                        if (anim != null && propname != null) {
                            if (playtest) {


                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        //if (Gdx.files.external(path+"/"+sfx).exists()) sfxcheckpoint = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "player":
                                        txPlayer = new Texture( Gdx.files.external( path + "/" + anim ) );
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
                                        break;

                                    case "empty":

                                        break;
                                    case "coin":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxcoin = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "spike":

                                        break;
                                    case "gear":

                                        break;
                                    case "girl":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxgirl= Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "key":

                                        break;
                                    case "lock":

                                        break;
                                    case "breakable":

                                        break;
                                    case "spring":
                                        //  if (Gdx.files.external(path+"/"+sfx).exists()) sfxspring = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "switch":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxswitch = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "switchon":

                                        break;
                                    case "switchoff":

                                        break;
                                    case "platform":

                                        break;
                                    case "monster":

                                        txMonster = new Texture( Gdx.files.external( path + "/" + anim ) );
                                        tmp = TextureRegion.split( txMonster,
                                                txMonster.getWidth() / 2,
                                                txMonster.getHeight() / 2 );

                                        TextureRegion[] walkFrames = new TextureRegion[2 * 2];
                                        int index = 0;
                                        for (int i = 0; i < 2; i++) {
                                            for (int j = 0; j < 2; j++) {
                                                walkFrames[index++] = tmp[i][j];
                                            }
                                        }
                                        animMonster = new Animation<TextureRegion>( 0.1f, walkFrames );
                                        break;

                                }
                            }else{
                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        //if (Gdx.files.external(path+"/"+sfx).exists()) sfxcheckpoint = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "player":
                                        txPlayer = new Texture( Gdx.files.internal( path + "/" + anim ) );
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
                                        break;

                                    case "empty":

                                        break;
                                    case "coin":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxcoin = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "spike":

                                        break;
                                    case "gear":

                                        break;
                                    case "girl":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxgirl= Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "key":

                                        break;
                                    case "lock":

                                        break;
                                    case "breakable":

                                        break;
                                    case "spring":
                                        //  if (Gdx.files.external(path+"/"+sfx).exists()) sfxspring = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "switch":
                                        // if (Gdx.files.external(path+"/"+sfx).exists()) sfxswitch = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "switchon":

                                        break;
                                    case "switchoff":

                                        break;
                                    case "platform":

                                        break;
                                    case "monster":

                                        txMonster = new Texture( Gdx.files.internal( path + "/" + anim ) );
                                        tmp = TextureRegion.split( txMonster,
                                                txMonster.getWidth() / 2,
                                                txMonster.getHeight() / 2 );

                                        TextureRegion[] walkFrames = new TextureRegion[2 * 2];
                                        int index = 0;
                                        for (int i = 0; i < 2; i++) {
                                            for (int j = 0; j < 2; j++) {
                                                walkFrames[index++] = tmp[i][j];
                                            }
                                        }
                                        animMonster = new Animation<TextureRegion>( 0.1f, walkFrames );
                                        break;

                                }
                            }
                        } else {

                        }


                        String sfx = (String) mp.get("sfx");
                        if (sfx != null && propname != null) {
                            if (playtest) {
                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxcheckpoint = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "player":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxplayer = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "empty":

                                        break;
                                    case "coin":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxcoin = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "spike":

                                        break;
                                    case "gear":

                                        break;
                                    case "girl":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxgirl = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "key":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxkey = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "lock":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxlock = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );
                                        break;

                                    case "breakable":

                                        break;
                                    case "spring":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxspring = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "switch":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxswitch = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;
                                    case "switchon":

                                        break;
                                    case "switchoff":

                                        break;
                                    case "platform":

                                        break;
                                    case "monster":
                                        if (Gdx.files.external( path + "/" + sfx ).exists())
                                            sfxmonster = Gdx.audio.newSound( Gdx.files.external( path + "/" + sfx ) );

                                        break;

                                }
                            }else{
                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxcheckpoint = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "player":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxplayer = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "empty":

                                        break;
                                    case "coin":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxcoin = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "spike":

                                        break;
                                    case "gear":

                                        break;
                                    case "girl":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxgirl = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "key":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxkey= Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "lock":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxlock= Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));
                                        break;

                                    case "breakable":

                                        break;
                                    case "spring":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxspring = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "switch":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxswitch = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;
                                    case "switchon":

                                        break;
                                    case "switchoff":

                                        break;
                                    case "platform":

                                        break;
                                    case "monster":
                                        if (Gdx.files.internal(path + "/" + sfx).exists())
                                            sfxmonster = Gdx.audio.newSound( Gdx.files.internal(path + "/" + sfx));

                                        break;

                                }

                            }
                        } else {

                        }

                    }
                }
            }
        }

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

                        if (cece != null) {

                            TiledMapTile tlcece = cece.getTile();
                            boolean flip = cece.getFlipVertically();
                            float rota = cece.getRotation();
                            if (cece.getTile() == tlplayer) {

                                player = new player( world, tlcece.getTextureRegion(), (xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f );//tlcece.getTextureRegion());

                            } else if (tlbrick.contains( cece.getTile() )) {
                                wall newwall = new wall();
                                newwall.setupWalls( world, tlcece, xx, yy, 7 );
                                walls.add( newwall );
                            } else {
                                gameobject newbrick = new gameobject();
                                newbrick.mygame = this;

                                if (tlboxes.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.BOX ,null,null,over);
                                } else if (tlcheckpoints.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, CHECKPOINT ,null,null,over);
                                } else if (tlcoins.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, COIN ,null,null,over);
                                    coin += 1;
                                } else if (tlspikes.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 5, BodyDef.BodyType.StaticBody, SPIKE ,null,null,over);
                                } else if (tlgears.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, GEAR ,null,null,over);
                                } else if (tlgirls.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.GIRL ,null,null,over);
                                } else if (tlkeys.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.KEY ,null,null,over);
                                } else if (tllocks.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.LOCK ,null,null,over);
                                } else if (tlbreakables.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.BREAKABLE ,null,null,over);
                                } else if (tlsprings.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SPRING ,null,null,over);
                                } else if (tlswitches.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCH ,null,null,over);
                                } else if (tlswitchons.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHON ,null,null,over);
                                } else if (tlswitchoffs.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHOFF ,null,null,over);
                                } else if (tlplatformh.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMH ,null,null,over);
                                } else if (tlplatformv.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMV ,null,null,over);
                                } else if (tlplatforms.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlempty, xx, yy, 5, BodyDef.BodyType.StaticBody, gameobject.objecttype.PLATFORMS ,null,null,over);
                                } else if (tlmonsters.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 6, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER ,null,null,over);
                                } else if (tlladder.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.LADDER ,null,null,over);
                                } else if (tlfloater.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.FLOATER ,null,null,over);
                                } else if (tlsinker.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 6, BodyDef.BodyType.StaticBody, gameobject.objecttype.SINKER ,null,null,over);
                                } else if (tlleftslope.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.LEFTSLOPE ,null,null,over);
                                } else if (tlrightslope.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.RIGHTSLOPE ,null,null,over);
                                } else if (tlmiscs.contains( cece.getTile() )) {
                                    newbrick.setupGameObject( world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC ,null,null,over);
                                }

                                if (flip) newbrick.rotate( 180 );
                                if (rota != 0) newbrick.rotate( rota * 90 );
                                objects.add( newbrick );
                            }
                            cece.setTile( null );
                        }

                    }
                }
            } else if (mlayer.getName().startsWith( "Object" )) {
                MapObjects objects = map.getLayers().get(mlayer.getName()).getObjects();
                for (MapObject o: objects){
                    Rectangle rect;
                    TextureRegion t=null;
                    if (o instanceof TextureMapObject) {
                        TextureMapObject tobj = (TextureMapObject) o;
                        t=tobj.getTextureRegion();
                        rect = new Rectangle( tobj.getX(),tobj.getY(),16,16 );
                    }else{
                        RectangleMapObject obj = (RectangleMapObject) o;
                        rect = obj.getRectangle();

                    }


                    if (o.getProperties().containsKey( "transfer" )) {
                        gameobject newbrick = new gameobject();
                        newbrick.mygame = this;
                        newbrick.setupGameObject( world, null, (int) rect.x, (int) rect.y, 2, BodyDef.BodyType.StaticBody, gameobject.objecttype.TRANSFER, o ,null,false);
                        this.objects.add( newbrick );
                    }
                    if (o.getProperties().containsKey( "item" )) {
                        if (checkQual( o )) {
                            gameobject newbrick = new gameobject();
                            newbrick.mygame = this;
                            newbrick.setupGameObject( world, null, (int) rect.x, (int) rect.y, 5, BodyDef.BodyType.StaticBody, ITEM, o ,t ,false);
                            this.objects.add( newbrick );
                        }
                    }
                    if (o.getProperties().containsKey( "block" )) {
                        if (checkQual( o )) {
                            gameobject newbrick = new gameobject();
                            newbrick.mygame = this;
                            newbrick.setupGameObject( world, null, (int) rect.x, (int) rect.y, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.BLOCK, o ,t ,false);
                            this.objects.add( newbrick );
                        }
                    }
                    if (o.getProperties().containsKey( "mode" )) {
                        if (o.getProperties().get( "mode" ).toString().equalsIgnoreCase( "rpg" )){
                            world.setGravity( new Vector2(0f,0f ));
                            jumping=false;
                            rpg=true;
                        }
                    }
                    if (o.getProperties().containsKey( "maxspeed" )) {
                        if (o.getProperties().get( "maxspeed" ).toString().equalsIgnoreCase( "rpg" )){
                            world.setGravity( new Vector2(0f,0f ));
                            jumping=false;
                            rpg=true;
                        }
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
        //setOrAddVars( "asu",1,VAROP.SET );
        //setOrAddVars( "sua",1,VAROP.SET );
        //setOrAddVars( "isu",1,VAROP.SET );
        if (o.getProperties().get( "req" )!=null){
            String[] ss = o.getProperties().get( "req" ).toString().split( "," );
            String[] vv = o.getProperties().get( "reqval" ).toString().split( "," );
            qual=false;
            int rq=0;
            boolean recheck=true;
            while (recheck) {
                recheck=false;
                for (int i = 0; i < ss.length; i++) {
                    boolean ada = false;
                    for (KV var : save.vars) {
                        if (ss[i].equalsIgnoreCase( var.key )) {
                            ada = true;
                            if (vv[i].equalsIgnoreCase(var.value+"")) {
                                rq += 1;
                                break;
                            }
                        }
                    }
                    if (!ada) {
                        setOrAddVars( ss[i], 0, VAROP.SET );
                        recheck=true;
                    }

                }
            }
            if (rq==ss.length) qual=true;
        }
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
        world.step(1/60f,6,2);
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
        if (player.b2body.getPosition().y<=-32/100f && player.state != com.mirwanda.nottiled.platformer.player.playerState.DEAD)
        {
            playSfx(sfxplayer);
            dead+=1;
            player.state = com.mirwanda.nottiled.platformer.player.playerState.DEAD;

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

        for (wall wl:walls)
        {
            if (!debugmode) wl.draw(batch);
        }
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


        for (gameobject sboxes:objects)
        {
            if (sboxes.over) continue;
            if (sboxes.objtype==MONSTER){
                TextureRegion currentFrame = animMonster.getKeyFrame(stateTime, true);
                sboxes.setRegion(currentFrame);
            }
            // if (sboxes.objtype==ITEM){Gdx.app.log( "AS",sboxes.toString() );}
            sboxes.update(delta);

            if (!debugmode && sboxes.getTexture()!=null ) sboxes.draw(batch);
        }

        player.update(delta);
        if (player.state != com.mirwanda.nottiled.platformer.player.playerState.DEAD){
            player.draw(batch);
        }else
        {
            player.b2body.setActive(false);
        }

        for (gameobject sboxes:objects)
        {
            if (!sboxes.over) continue;
            if (sboxes.objtype==MONSTER){
                TextureRegion currentFrame = animMonster.getKeyFrame(stateTime, true);
                sboxes.setRegion(currentFrame);
            }
            // if (sboxes.objtype==ITEM){Gdx.app.log( "AS",sboxes.toString() );}
            sboxes.update(delta);

            if (!debugmode && sboxes.getTexture()!=null ) sboxes.draw(batch);
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
        player.moving=true;
        if (rpg) dir=3;

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
        player.moving=true;
        if (rpg) dir=0;

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
        player.b2body.setLinearVelocity(0,0);
        player.b2body.setActive(true);
        player.b2body.setTransform(checkpoint.x,checkpoint.y,0);
        player.state = com.mirwanda.nottiled.platformer.player.playerState.ALIVE;
    }

    public void keyinput(){





        if (Gdx.app.getType() == Application.ApplicationType.Desktop && !uitest ) {

            if (victory||starting) return;

            if (player.state == com.mirwanda.nottiled.platformer.player.playerState.DEAD) return;


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

}


