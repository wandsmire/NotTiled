package com.mirwanda.nottiled.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mirwanda.nottiled.gui;

import java.util.ArrayList;

import static com.mirwanda.nottiled.game.gameobject.objecttype.CHECKPOINT;
import static com.mirwanda.nottiled.game.gameobject.objecttype.COIN;
import static com.mirwanda.nottiled.game.gameobject.objecttype.GEAR;
import static com.mirwanda.nottiled.game.gameobject.objecttype.MONSTER;
import static com.mirwanda.nottiled.game.gameobject.objecttype.SPIKE;

public class game {
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public Box2DDebugRenderer b2dr;
    public World world;
    public String path,file;

    public WorldContactListener mycontactlistener;



    public Vector2 checkpoint = new Vector2();
    public com.mirwanda.nottiled.game.player player;
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
    Animation<TextureRegion> animPlayer; // Must declare frame type (TextureRegion)
    Texture txPlayer;
    public Texture txBackground;
    public TiledMapTile tlplayer=null;
    public TiledMapTile tlempty=null;
    public boolean debugmode = false;

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
    public String briefing;
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

    public ParticleEffect pe;

    public boolean initialise(String path, String filename){

            this.path = path;
            this.file = filename;
            starting = true;
            pe = new ParticleEffect();
            pe.load(Gdx.files.external(path + "/died.p"), Gdx.files.external(path));
            pe.getEmitters().first().setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            pe.scaleEffect(0.001f, 0.001f); //kudu disini


            map = new TmxMapLoader(new ExternalFileHandleResolver()).load(path + "/" + filename);

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
                    if (Gdx.files.external(path + "/" + bgms).exists()) {
                        bgm = Gdx.audio.newMusic(Gdx.files.external(path + "/" + bgms));
                        bgm.setLooping(true);
                        bgm.play();
                    }

                }


                String bgc = (String) mpa.get("background");
                if (bgc != null) {
                    if (Gdx.files.external(path + "/" + bgc).exists()) {
                        txBackground = new Texture(Gdx.files.external(path + "/" + bgc));
                    }
                }

                nextlevel = (String) mpa.get("nextlevel");
                briefing = (String) mpa.get("briefing");
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

                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        //if (Gdx.files.external(path+"/"+sfx).exists()) sfxcheckpoint = Gdx.audio.newSound(Gdx.files.external(path+"/"+sfx));

                                        break;
                                    case "player":
                                        txPlayer = new Texture(Gdx.files.external(path + "/" + anim));
                                        TextureRegion[][] tmp = TextureRegion.split(txPlayer,
                                                txPlayer.getWidth() / 2,
                                                txPlayer.getHeight() / 2);

                                        TextureRegion[] walkFrames = new TextureRegion[2 * 2];
                                        int index = 0;
                                        for (int i = 0; i < 2; i++) {
                                            for (int j = 0; j < 2; j++) {
                                                walkFrames[index++] = tmp[i][j];
                                            }
                                        }
                                        animPlayer = new Animation<TextureRegion>(0.1f, walkFrames);
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

                                        txMonster = new Texture(Gdx.files.external(path + "/" + anim));
                                        tmp = TextureRegion.split(txMonster,
                                                txMonster.getWidth() / 2,
                                                txMonster.getHeight() / 2);

                                        walkFrames = new TextureRegion[2 * 2];
                                        index = 0;
                                        for (int i = 0; i < 2; i++) {
                                            for (int j = 0; j < 2; j++) {
                                                walkFrames[index++] = tmp[i][j];
                                            }
                                        }
                                        animMonster = new Animation<TextureRegion>(0.1f, walkFrames);
                                        break;

                                }
                            } else {

                            }


                            String sfx = (String) mp.get("sfx");
                            if (sfx != null && propname != null) {

                                switch (propname) {
                                    case "brick":

                                        break;
                                    case "box":

                                        break;
                                    case "checkpoint":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxcheckpoint = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "player":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxplayer = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "empty":

                                        break;
                                    case "coin":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxcoin = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "spike":

                                        break;
                                    case "gear":

                                        break;
                                    case "girl":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxgirl = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "key":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxkey= Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "lock":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxlock= Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));
                                        break;

                                    case "breakable":

                                        break;
                                    case "spring":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxspring = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "switch":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxswitch = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;
                                    case "switchon":

                                        break;
                                    case "switchoff":

                                        break;
                                    case "platform":

                                        break;
                                    case "monster":
                                        if (Gdx.files.external(path + "/" + sfx).exists())
                                            sfxmonster = Gdx.audio.newSound(Gdx.files.external(path + "/" + sfx));

                                        break;

                                }
                            } else {

                            }

                        }
                    }
                }
            }

            for (MapLayer mlayer: map.getLayers()){
            TiledMapTileLayer tlayer = (TiledMapTileLayer) mlayer;
            for (int yy = 0; yy < tlayer.getHeight(); yy++) {
                for (int xx = 0; xx < tlayer.getWidth(); xx++) {
                    TiledMapTileLayer.Cell cece = tlayer.getCell(xx, yy);

                    if (cece != null) {

                        TiledMapTile tlcece = cece.getTile();
                        boolean flip = cece.getFlipVertically();
                        float rota = cece.getRotation();
                        if (cece.getTile() == tlplayer) {

                            player = new player(world, tlcece.getTextureRegion(), (xx * 16 + 8) / 100f, (yy * 16 + 8) / 100f);//tlcece.getTextureRegion());

                        } else if (tlbrick.contains(cece.getTile())) {
                            wall newwall = new wall();
                            newwall.setupWalls(world, tlcece, xx, yy, 8);
                            walls.add(newwall);
                        } else {
                            gameobject newbrick = new gameobject();
                            newbrick.mygame = this;

                            if (tlboxes.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.BOX);
                            } else if (tlcheckpoints.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, CHECKPOINT);
                            } else if (tlcoins.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, COIN);
                                coin += 1;
                            } else if (tlspikes.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 5, BodyDef.BodyType.StaticBody, SPIKE);
                            } else if (tlgears.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, GEAR);
                            } else if (tlgirls.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.GIRL);
                            } else if (tlkeys.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.KEY);
                            } else if (tllocks.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.LOCK);
                            } else if (tlbreakables.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.BREAKABLE);
                            } else if (tlsprings.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SPRING);
                            } else if (tlswitches.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCH);
                            } else if (tlswitchons.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHON);
                            } else if (tlswitchoffs.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.SWITCHOFF);
                            } else if (tlplatformh.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMH);
                            } else if (tlplatformv.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.DynamicBody, gameobject.objecttype.PLATFORMV);
                            } else if (tlplatforms.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlempty, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.PLATFORMS);
                            } else if (tlmonsters.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 6, BodyDef.BodyType.DynamicBody, gameobject.objecttype.MONSTER);
                            } else if (tlladder.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.LADDER);
                            } else if (tlfloater.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.FLOATER);
                            } else if (tlsinker.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.SINKER);
                            } else if (tlleftslope.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.LEFTSLOPE);
                            } else if (tlrightslope.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 8, BodyDef.BodyType.StaticBody, gameobject.objecttype.RIGHTSLOPE);
                            } else if (tlmiscs.contains(cece.getTile())) {
                                newbrick.setupGameObject(world, tlcece, xx, yy, 7, BodyDef.BodyType.StaticBody, gameobject.objecttype.MISC);
                            }

                            if (flip) newbrick.rotate(180);
                            if (rota != 0) newbrick.rotate(rota * 90);
                            objects.add(newbrick);
                        }
                        cece.setTile(null);
                    }

                }
            }
        }
        checkpoint.set(player.b2body.getPosition().x,player.b2body.getPosition().y);
        victory=false;
            return false;



    }

    public float anima;
    public int animet;
    public void update(SpriteBatch batch, float delta, OrthographicCamera gamecam) {
        world.step(1/60f,6,2);
            if (Math.abs(player.b2body.getLinearVelocity().y)>=0.2f)
            {
                jumping=true;
                jumpinterval=0;
            }else
            {
                jumpinterval+=delta;
            }

            if (jumpinterval >=0.1f){
                jumping=false;
            }
            stompinterval-=delta;
            if (stompinterval<0)stompinterval=0;

        if (player.b2body.getPosition().y<=-32/100f && player.state != com.mirwanda.nottiled.game.player.playerState.DEAD)
        {
            playSfx(sfxplayer);
            dead+=1;
            player.state = com.mirwanda.nottiled.game.player.playerState.DEAD;

        }



        gamecam.position.set(player.b2body.getPosition().x,player.b2body.getPosition().y,0);
        gamecam.update();

        for (wall wl:walls)
        {
            if (!debugmode) wl.draw(batch);
        }
        stateTime += delta;

        if (player.moving && (!jumping || onplatformv) && (!ladder && !floater && !sinker))
        {
            playerTime +=delta;
            TextureRegion currentFramea = animPlayer.getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);
        }
        else if (jumping && !ladder && !floater && !sinker && !onplatformv) {
            TextureRegion currentFramea = animPlayer.getKeyFrame(3, true);
            player.setRegion(currentFramea);
        }
        else if ((ladder || floater || sinker) && player.moving )
        {
            playerTime +=delta;

            TextureRegion currentFramea = animPlayer.getKeyFrame(playerTime, true);
            player.setRegion(currentFramea);

        }else
        {
            //idle animation if you will...
            TextureRegion currentFramea = animPlayer.getKeyFrame(0, true);
            player.setRegion(currentFramea);
        }




        if (player.faceright){
            player.setFlip(true, false);}

        for (gameobject sboxes:objects)
        {
            if (sboxes.objtype==MONSTER){
                TextureRegion currentFrame = animMonster.getKeyFrame(stateTime, true);
                sboxes.setRegion(currentFrame);
            }

            sboxes.update(delta);

            if (!debugmode) sboxes.draw(batch);
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


        player.update(delta);
        if (player.state != com.mirwanda.nottiled.game.player.playerState.DEAD){
            player.draw(batch);
        }else
        {
            player.b2body.setActive(false);
        }

    }

    public void playSfx(Sound s){
        if (s!=null) s.play(1.0f);
    }

    public void pressup(){
        player.moving=true;
        if ((!jumping || onplatformv) && !ladder && !sinker && !floater) {
            player.b2body.applyLinearImpulse(0f,2.8f,player.getX(),player.getY(),true);
            jumping=true;
        }
        if (ladder||floater||sinker) {
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0.5f);
        }
    }

    public void pressdown(){
        player.moving=true;
        if (stompinterval==0 && !ladder && !sinker && !floater) {
            player.b2body.applyLinearImpulse(0f, -8f, player.getX(), player.getY(), true);
            stompinterval=1;
        }
        if (ladder||floater||sinker) {
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, -0.5f);
        }

    }

    public void pressleft(){
        player.moving=true;
        float speedlimit =-1.5f;
        if (ladder || floater || sinker) {speedlimit = -0.4f; player.b2body.setLinearVelocity(speedlimit,player.b2body.getLinearVelocity().y);}
        if (player.b2body.getLinearVelocity().x >=speedlimit) {
            player.faceright=false;
            player.b2body.applyLinearImpulse(-0.5f, 0, player.getX(), player.getY(), true);

        }
    }
    public void pressright(){
        player.moving=true;
        float speedlimit =1.5f;
        if (ladder || floater || sinker) {speedlimit = 0.4f; player.b2body.setLinearVelocity(speedlimit,player.b2body.getLinearVelocity().y);}
        if (player.b2body.getLinearVelocity().x <=speedlimit){
            player.faceright=true;
            player.b2body.applyLinearImpulse(0.5f, 0, player.getX(), player.getY(), true);
        }

    }

    public void stand(){
        player.moving=false;
            player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
        if (onplatformh){
            player.b2body.setLinearVelocity(currentplatform.body.getLinearVelocity().x, player.b2body.getLinearVelocity().y);
        }else if (onplatformv){
            //player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, currentplatform.body.getLinearVelocity().y);
        }
    }

    public void respawn(){
        player.b2body.setLinearVelocity(0,0);
        player.b2body.setActive(true);
        player.b2body.setTransform(checkpoint.x,checkpoint.y,0);
        player.state = com.mirwanda.nottiled.game.player.playerState.ALIVE;
    }

    public void keyinput(){
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

            if (victory||starting) return;

            if (player.state == com.mirwanda.nottiled.game.player.playerState.DEAD) return;

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                pressright();
                player.moving=true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                pressleft();

            } else {
                stand();

            }

            if (ladder||floater||sinker){
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) pressup();
            }
            else
            {
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) pressup();
            }

            if (ladder||floater||sinker){
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) pressdown();
            }else
            {
                if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) pressdown();
            }
        }


    }
}
