package com.mirwanda.nottiled;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Curvature;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.postprocessing.effects.Zoomer;
import com.bitfire.postprocessing.filters.Combine;
import com.bitfire.postprocessing.filters.CrtScreen;
import com.bitfire.postprocessing.filters.RadialBlur;
import com.bitfire.utils.ShaderLoader;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mirwanda.nottiled.ai.ATGraph;
import com.mirwanda.nottiled.ai.AutoTile;
import com.mirwanda.nottiled.platformer.game;
import com.mirwanda.nottiled.platformer.gameobject;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.lang.Thread.sleep;
import static org.jfugue.midi.MidiFileManager.savePatternToMidi;


public class MyGdxGame extends ApplicationAdapter implements GestureListener {
    private static final boolean isDesktop = false;//(Gdx.app.getType() == Application.ApplicationType.Desktop);
    final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tffint = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();
    final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tfffloat = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter() {
        public boolean acceptChar(TextField p1, char c) {
            return Character.toString( c ).matches( "[0-9.-]+" );
        }
    };
    final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tffcolor = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter() {
        public boolean acceptChar(TextField p1, char c) {
            return Character.toString( c ).matches( "[a-fA-F0-9#]+" );
        }
    };
    public Tutorials tutor = new Tutorials();
    public com.mirwanda.nottiled.Interface face;
    public guis gui = new guis();
    public language z = new language();

    ThreadPool autotilePool = new ThreadPool(1, 100000);
    ThreadPool autosavePool = new ThreadPool(1, 10);

    public drawer tempdrawer = new drawer();
    public Bloom bloom;
    public Curvature curvature;
    public Zoomer zoomer;
    public CrtMonitor crt;
    public Vignette vignette;
    public boolean frompick;
    private layer.Type newLayerType;
    Boolean pv;
    String oldlang;
    Integer oldfontsize;
    String oldcustomfont;
    PostProcessor postProcessor;
    Slider slfirstgen;
    //Animation<TextureRegion> animation;
    float elapsed;
    String temproname = "";
    String temprotype = "";
    String temprovalue = "";
    private java.util.List<Integer> swatchValue = new ArrayList<Integer>();
    Pixmap pmMinimap;
    Texture txMinimap;

    //////////////////////////////////////////////////////
//            VARIABLES
//////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////
    float delta;



    public enum selectTool {PICKER, COPY, MOVE, FLIP, CLONE}

    selectTool movetool = selectTool.PICKER;
    String debugMe = " ", debugYou = " ";
    Table lastStage;
    String sender;
    boolean swatches = false;
    int senderID; //custom properties
    int selTsetID; //tiles
    String fps = "";
    boolean nativefilechooser = false;
    float keydown = 0f;
    boolean backing;
    String shapeName = "rectangle", rotationName = "0", toolName = "Tile", viewModeName = "Stack", objViewModeName = "All";
    int magnet = 1;
    boolean eraser = false;
    String magnetName = "lock";
    int activetool = 0, activeobjtool = 0;
    float blink = 0;
    boolean turun = false;
    String info;
    obj copyobj = null;
    boolean sShowGrid = true, sShowFPS, sAutoSave, sSaveTsx = false, sShowGID = false, sMinimap;
    boolean sShowCoords;
    String sCustomFont = "";
    boolean sCustomUI;
    boolean sShowCustomGrid = false;
    boolean sResizeTiles = false;
    int sGridX, sGridY;
    boolean anime = false, deletinganim = false;
    float bgr, bgg, bgb;
    String renderorder = "right-down";
    String orientation = "orthogonal";
    java.util.List<Long> spr;

    enum ViewMode {STACK, SINGLE, ALL, CUSTOM}

    ViewMode viewMode = ViewMode.CUSTOM;
    boolean issettingtile = false;
    int objviewMode;
    int selTileID = -1;
    int tempframeid;
    int templastID;
    int Tsw = 32, Tsh = 32, Tw = 5, Th = 5;
    int rotate = 0;
    int ssx = 480;
    int ssy = 800;
    int nssx = 480;
    int nssy = 800;

    int btnx = 440, btny = 50;
    int jon, joni;
    int selat;
    int startSelect, endSelect, initialSelect;
    boolean rising = false;
    boolean tutoring = false;
    int activetutor = 0;
    String mapFormat = "csv", tsxFile = "", activeFilename;
    String kartu = "", mode, lastpath, openedfile, tilePicker = "", saveasdir, rwpath;
    int autosaveInterval = 1;
    int gridOpacity = 5;
    boolean isSampleReloaded;
    int curspr, curid;
    obj curobj;
    String curgroup = "default";
    int selgroup = 0, selLayer = 0, oedit = 0, ogroup = 0, seltset = 0;
    String encoding = "";
    String compression = "";
    String texFile = "", curdir = "/sdcard/Assets/", curfile = "";
    SpriteBatch batch, ui;
    myShapeRenderer uis;
    ShapeRendererPlus sr;
    OrthographicCamera cam, uicam, tilecam, minicam, gamecam;


    GestureDetector gd;
    InputProcessor ip;
    InputMultiplexer im;

    boolean drag, roll, stamp = false;
    BitmapFont str1;
    decoder decoder = new decoder();
    Skin skin;
    java.util.List<property> properties = new ArrayList<property>();
    java.util.List<layer> layers = new ArrayList<layer>();
    layer cliplayer = new layer();
    int clipsource = 0;
    java.util.List<tileset> tilesets = new ArrayList<tileset>();
    java.util.List<tileset> tilesets2 = new ArrayList<tileset>();
    java.util.List<drawer> drawers = new ArrayList<drawer>();
    java.util.List<autotile> autotiles = new ArrayList<autotile>();
    java.util.List<TextField> tf = new ArrayList<TextField>();
    java.util.List<Label> tla = new ArrayList<Label>();
    java.util.List<layerhistory> undolayer = new ArrayList<layerhistory>();
    java.util.List<layerhistory> redolayer = new ArrayList<layerhistory>();
    java.util.List<TileCache> tcaches = new ArrayList<TileCache>();

    layer tempLayer;
    tileset tempTset;
    float initialZoom;
    CheckBox cbShowGrid, cbShowFPS, cbShowGid, cbShowCoords, cbAutoSave, cbShowGidmap, cbResize, cbMinimap;
    CheckBox cbCustomUI, cbnativefc;
    TextField tfCustomFont;
    TextButton bBack3;
    CheckBox cbShowCustomGrid;
    TextField fGridX, fGridY, fBgcolor;
    Table tObjProp;
    TextButton bApply, bCancel, bRemove, bProps;
    Table tNF, tMP;
    com.badlogic.gdx.Input.TextInputListener pSaveAs;
    //nf
    TextField fNFilename, fNCurdir, fNTsw, fNTsh, fNTw, fNTh;
    SelectBox sbNMapFormat;
    SelectBox sbNMapRenderOrder;
    SelectBox sbNMapOrientation;
    TextButton bNSelDir, bNNew, bNNewplus, bNCancel;
    //mp
    TextField fFilename, fCurdir, fTsw, fTsh, fTw, fTh, fTsx;
    SelectBox sbMapFormat;
    SelectBox sbMapRenderOrder;
    SelectBox sbMapOrientation;
    CheckBox cbUseTsx, cb1, cb2;
    TextButton bUseTsx, bApplyMP, bCancelMP, bPropertiesMap;
    ChangeListener listBack;
    Table tMenu, tMenu1, tMenu2, tOpen, tNewFile, tSaveAs, tLicense, tTutorial;
    TextButton bNew, bOpen, bSave, bSaveAs, bExit, bBack, bLicense, bReload, bTutorial, bCopyto;
    ImageButton bcc;
    TextButton btiled, bCollaboration, bPatreon2, bTools, bBackground;
    TextButton bTutorOK, bTutorBack, bPatreon, bExporter, credito;
    Table tMap, tLayerMgmt, tTileMgmt, tObjMgmt, tFrameMgmt, tPropsMgmt, tPreference, tProperties, tTsetMgmt, tAutoMgmt, tAutoform;
    Table tMap1, tMap2;
    TextButton bTileMgmt, bTileSettingsMgmt, bPreference, bProperties, bTsetMgmt, bBack2, bAutoMgmt, bFeedback;
    TextButton bUIEditor;
    Table tRecent;
    Table tCollab, tCollab1, tCollab2;
    Label lcollabstatus;
    TextButton bRecent, bRecentOpen, bRecentBack;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lrecentlist;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> ltutorial;
    Table tTemplate, tOnline;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> ltemplate;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lonline;
    TextButton bTmplOK, bTmplBack, bTmplDownload;
    TextButton bOnlineDownload, bOnlineBack, bOnlineRefresh;
    Table tImport, tExport;
    private TextField fExportFilename;
    TextField fImportWidth;
    TextField fImportHeight;
    CheckBox cImportEmbed;
    TextButton bImportOK;
    Table tLinks;
    TextButton bRusted, bWardate, bManual;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lautolist;
    TextButton bAutoadd, bAutoprops, bAutoload, bAutosave, bAutorename, bAutoremove, bAutomoveup, bAutoback;
    com.badlogic.gdx.Input.TextInputListener pAutoadd;
    com.badlogic.gdx.Input.TextInputListener pAutorename;
    Table tLayerNew;
    TextButton bLayerGroup, blayerBack;
    Table tPropEditor;
    TextField fPropName;
    Table tImageLayer;
    TextField tfImageName, tfImageSource, tfImageKey, tfImageOpacity, tfImageOffsetX, tfImageOffsetY;
    SelectBox sbPropType, sbPropValbool;
    TextArea fPropVal; //str,int,float,color
    TextButton bPropValfile, bPropApply, bPropCancel, bPropCopy, bPropPaste, bPropGid, bProppng, bPropCp;
    String clipProp = "", clipobjcpy = "";
    com.badlogic.gdx.scenes.scene2d.ui.List<String> llayerlist;
    com.badlogic.gdx.Input.TextInputListener pNewLayer;
    com.badlogic.gdx.Input.TextInputListener pNewLayerSC;
    com.badlogic.gdx.Input.TextInputListener pAddMacro;
    com.badlogic.gdx.Input.TextInputListener pBrushSize;

    com.badlogic.gdx.Input.TextInputListener pEditLayer;
    com.badlogic.gdx.Input.TextInputListener pSetOpacity;
    TextButton bAddLayer, bRemoveLayer, bMoveLayer, bEditLayer, bBackLayer, bSetOpacity;
    TextButton bLayerDuplicate, bLayerProperties;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lproplist;
    com.badlogic.gdx.Input.TextInputListener pNewProp;
    String tempNameNew;
    com.badlogic.gdx.Input.TextInputListener pEditProp;
    String tempNameEdit;
    com.badlogic.gdx.Input.TextInputListener pNewProp2;
    Label lPropID;
    com.badlogic.gdx.Input.TextInputListener pEditProp2;
    TextButton bAddProp, bRemoveProp, bMoveProp, bEditProp, bBackProp;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> ltsetlist;
    TextButton bAddTset, bRemoveTset, bMoveTset, bBackTset, bPropTset, bImportFolder;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lobjlist;
    com.badlogic.gdx.Input.TextInputListener pNewObjLayer;
    com.badlogic.gdx.Input.TextInputListener pNewObjLayerSC;
    com.badlogic.gdx.Input.TextInputListener pEditObjLayer;
    TextButton bAddObjLayer, bRemoveObjLayer, bMoveObjLayer, bEditObjLayer, bBackObjLayer;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> ltilelist;
    TextButton bAddTileLayer, bTileAnimations, bReplaceTileLayer, bRemoveTileLayer, bMoveTileLayer, bBackTileLayer, bPropsTileLayer, bTerrainEditor;
    com.badlogic.gdx.Input.TextInputListener pNewTerrain;
    com.badlogic.gdx.scenes.scene2d.ui.List<String> lframelist;
    com.badlogic.gdx.Input.TextInputListener pNewFrame;
    com.badlogic.gdx.Input.TextInputListener pEditFrame;
    TextField fDurationframe;
    TextButton bAddFrameLayer, bEditFrameLayer, bReplaceFrameLayer, bRemoveFrameLayer, bMoveFrameLayer, bBackFrameLayer, bDuration;
    Image iFrameView;
    Label lFrameID;
    Table tTsProp;
    TextButton bTsPropOK, bTsPropCancel, bTsPropSaveAsTsx, bTsPropChangeSource, bTsPropSaveAsPNG;
    TextField fTsPropName, fTsPropSource, fTsPropTrans;
    TextField fTsPropSpacing, fTsPropMargin, fTsPropTsxFile;
    TextField fTsPropTsw, fTsPropTsh, fTsPropTc, fTsPropCols;
    CheckBox cbTsPropUseTsx;
    FileChooser fcOpen, fcSaveAs;
    Preferences prefs;
    boolean loadingfile;
    boolean bypassads = false;
    //fastah mastah
    java.util.List<tile> tiles;
    int sprX, sprY, margin, spacing;
    int xpos, ypos;
    float camA, camB, camC, camD;
    String hex, trailer;
    AsyncExecutor asyncExecutor = new AsyncExecutor( 10 );
    AsyncResult<Void> task;
    obj newobject = new obj();
    com.badlogic.gdx.Input.TextInputListener psaveproptemplate = new com.badlogic.gdx.Input.TextInputListener() {

        @Override
        public void input(String input) {
            if (input == "") {
                return;
            }
            writeThis("NotTiled/sample/json/"+input+".json",clipProp);
            msgbox(z.filesaved);
        }

        @Override
        public void canceled() {
        }

    };

    com.badlogic.gdx.Input.TextInputListener pnewtextobject = new com.badlogic.gdx.Input.TextInputListener() {

        @Override
        public void input(String input) {
            if (input == "") {
                return;
            }
            newobject.setText( input );

        }

        @Override
        public void canceled() {
        }

    };
    AssetManager manager = new AssetManager();
    String vers;
    boolean startup = false;

    enum ShapeTool {RECTANGLE, CIRCLE, LINE}

    ShapeTool currentShape = ShapeTool.RECTANGLE;
    float timeToPan, panTargetX, panTargetY, panTargetZoom, panOriginX, panOriginY, panOriginZoom, panDuration;
    float timeToPan2, panTargetZoom2, panOriginZoom2, panDuration2;
    int panType = 0;
    boolean downloading = false;
    FileHandle thefile;
    int firstload = -1;
    int loadtime = 10;
    private CheckBox[] newcb;
    private int scrollspeed;
    private Slider sdScrollSpeed, sdGridOpacity;
    private SelectBox sbLanguage;
    private Online templates = new Online();
    private java.util.List<Boolean> autoed = new ArrayList<Boolean>();
    private java.util.List<String> recentfiles = new ArrayList<String>();
    private int position;
    private int kyut;
    private Table ttools;
    private int tilesetsize;
    private Table nullTable;
    private int tilesize;
    private int Tswa;
    private int Tsha;
    private int initset;
    private boolean rotating;
    private Texture background;
    private boolean sEnableBlending;
    private CheckBox cbEnableBlending;
    private String cammode = "";
    private java.util.List<Boolean> massprops = new ArrayList<Boolean>();
    private int mapstartSelect;
    private int mapendSelect;
    private int mapinitialSelect;
    private int mapfinalSelect;
    private String errors;
    private boolean sShowGIDmap;
    private boolean caching;
    private TextButton bPropTemplate,bPropExportAsTemplate;
    private TextButton bPropParse;
    private Table tpt;
    private int brushsize = 1;
    private String language;
    private boolean landscape;
    private int redux;
    private int reduy;
    private TextButton bLinks;
    private TextButton bVideos, bDiscord, bWhatsapp, bManualCN;
    private String wl;
    private TextButton bTsPropCustomProp;
    private String oldowner;
    private property tempe;
    private boolean propertystart;
    private boolean alreadyloaded;
    private String intend;
    private recents recents = new recents();
    private Texture txnumbers;
    private FrameBuffer fbo;
    private boolean takingss;
    private Stage hstage;
    private Stage vstage;
    private Dialog dialog;
    private Texture txrectangle, txsave, txsave2, txVisible, txInvisible;
    private Texture txTypeTile, txTypeObject, txTypeImage, txTypeGroup, txUp, txDown, txLeft, txRight;
    private Texture txClone, txOutline, txEraser2, txRectangle2;
    private Texture txmenu, txmap, txundo, txredo, txtile, txauto, txlayer, txautopick, txcenter;
    private Texture txstamp, txadd, txdelete, txinfo, txtiles, txplay;
    private Texture txresources;
    private TextButton bMassAddProp, bTileCollision;
    private int zoomTreshold;
    private TextField fzoomtresh, frwpath;
    private CheckBox cball;
    private Table bigman;
    private boolean zooming;
    private float autosave = 0f;
    private float redraw = 0f;
    private float rotator = 0f;
    private float undohistory = 0f;
    private Table trandomgen, treplacetiles;
    private TextField ffirstgen;
    private TextField fgencount;
    private TextField fbirthlim;
    private TextField fdeathlim;
    private TextField flivestr, fprevstr;
    private TextField flivetset;
    private TextField fdeadstr, fnextstr;
    private TextField fdeadtset;
    private boolean firstrun = true;
    private Texture txlauncher;
    private String sBgcolor;
    private float prevx = 0;
    private float prevy = 0;
    private float velx;
    private float vely;
    private float velredx;
    private float velredy;
    private int fontsize;
    private TextField fFontsize, fAutoSaveInterval;
    private float nofling;
    private Stage stage;
    private Texture txpencil;
    private Texture txline, txcircle;
    private Texture txeraser;
    private Texture txfill;
    private Texture txcopy, txmove, txflip, txpicker;
    private Texture txbrush;
    private Texture txlock;
    private Texture txunlock;
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myParser;
    private float statustimeout;
    private int newTerrainID;
    private boolean n;
    private boolean lockUI;

    //private AdView ads;
    public MyGdxGame(String intend, Interface face)//, AdView ads)
    {
        this.intend = intend;
        this.face = face;
        //this.ads= ads;
    }

    public static long getUsedMemorySize() {

        long freeSize = 0L;
        long totalSize = 0L;
        long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedSize;

    }

    public static java.util.List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        java.util.List<String> ret = new ArrayList<String>( (text.length() + size - 1) / size );

        for (int start = 0; start < text.length(); start += size) {
            ret.add( text.substring( start, Math.min( text.length(), start + size ) ) );
        }
        return ret;
    }

    private static boolean isPowerOf2(final int n) {
        if (n <= 0) {
            return false;
        }
        return Integer.bitCount( n ) == 1;
    }

    @Override
    public void pinchStop() {
        prevx = 0;
        prevy = 0;
        // TODO: Implement this method
    }

    //////////////////////////////////////////////////////
//            APP CYCLE
//////////////////////////////////////////////////////
    String basepath;

    @Override
    public void create() {
        log( "Gdx create started!" );
        basepath = Gdx.files.getExternalStoragePath();
        log("basepath = "+basepath);
        nullTable = new Table();
        vers = face.getVersione();
        prefs = Gdx.app.getPreferences( "My Preferences" );
        language = prefs.getString( "language", "English" );
        fontsize = prefs.getInteger( "fontsize", 0 );
        sCustomFont = prefs.getString( "customfont", "" );

        try {
            reloadLanguage();
            log( "Language reloaded!" );

        } catch (Exception e) {
            ErrorBung( e, "langeror.txt" );
            language = "English";
            prefs.putString( "language", "English" );
            reloadLanguage();
            log( "Language error, default to english!" );

        }
        gd = new GestureDetector( this );
        ip = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                if (amount >0) {
                    cam.zoom = cam.zoom * 1.2f;
                }else{
                    cam.zoom = cam.zoom * 0.8f;
                }
                if (cam.zoom > Tsw * 2) {
                    cam.zoom = Tsw * 2;
                }
                if (cam.zoom < Tsw / 320f)//zoom in
                {
                    cam.zoom = Tsw / 320f;
                }

                cam.update();
                return false;
            }
        };
        im = new InputMultiplexer();
        im.addProcessor(gd);
        im.addProcessor(ip);


        initSD();
        initBox2D();
        loadGdxStuff();
        loadExport();
        loadListener();
        loadMenuMap();
        loadPreferences();
        initErrorHandling();
        loadOpen();
        loadLicense();
        loadTsetManagement();
        loadLayerManagement();
        loadTileManagement();
        loadFrameManagement();
        loadAutoManagement();
        loadTools();
        loadImport();
        loadNewLayer();
        loadObjProp();
        loadMapProperties();
        loadTilesetProperties();
        loadPropsManagement();
        loadOnline();
        loadNewFile();
        loadTemplate();
        loadPropEditor();
        loadPropTemplate();
        loadImageLayer();
        loadKryonet();
        initializePostProcessor();
        createSwatches();
        log( "Initialization complete" );


        activetool = 4;


        //animation = GifDecoder.loadGIFAnimation(2, Gdx.files.absolute("loading.gif").read());
        manager.setLoader(
                TmxMap.class,
                new mapLoader(
                        new ExternalFileHandleResolver()
                )
        );

    }

    public Box2DDebugRenderer b2dr;
    public World world;
    public myContactListener mycontactlistener;

    private void initBox2D() {
        Box2D.init();
        world = new World( new Vector2( 0, 0 ), true );
        b2dr = new Box2DDebugRenderer();

        mycontactlistener = new myContactListener( this );
        world.setContactListener( mycontactlistener );
    }

    private void createSwatches() {
        Integer sw = 0;
        for (int ii = 0; ii < 6; ii++) {
            swatchValue.add( sw );
        }
    }

    String pet = "";

    private void nativeOpen(final String tujuan, final Table T) {
        pet="";
        new Thread( new Runnable() {
            @Override
            public void run() {
                face.openDialog();
                while (pet.equalsIgnoreCase( "" )){
                    pet = face.opet();
                }

                if (pet.equalsIgnoreCase( "cancel" )){
                    exitDialog(T);
                    return;
                }
                log(pet);
                cleanPet();
                log(pet);
                FileHandle f = Gdx.files.absolute( pet );
                exitDialog( T );
                tujuanDialog( tujuan, f);
            }
        } ).start();
    }

    private void cleanPet(){
        //check android uris
        if (pet.startsWith( "/document/" )){
            String[] pen = pet.split( ":" );
            String poi = pen[0].substring( 9 );
            log(poi);
            switch (poi){
                case "image": break;
                case "video": break;
                case "audio": break;
                case "primary":
                default:
                    pet=pet.replace( "/document/primary:",basepath );
                    pet=pet.replace( pen[0] +":","/storage/"+poi+"/" );
                    break;
            }
        }

        if (pet.startsWith( "/uid/file" )) {
            pet=pet.substring( 10 );
            pet=pet.replace( "%20"," " );
        }
            pet=pet.replace( "(invalid)","" );

    }
    private void nativeDirectory(final String tujuan, final Table T) {
        pet="";
        new Thread( new Runnable() {
            @Override
            public void run() {
                face.openDirectory();
                while (pet.equalsIgnoreCase( "" )){
                    pet = face.opet();
                }

                if (pet.equalsIgnoreCase( "cancel" )){
                    exitDialog(T);
                    return;
                }
                FileHandle f = Gdx.files.absolute( pet );
                if (f.exists()) f.delete();
                log(pet);
                cleanPet();
                log(pet);
                 f = Gdx.files.absolute( pet );
                exitDialog( T );
                tujuanDialog( tujuan, f);

            }
        } ).start();
    }


    private void resetSwatches() {
        Integer sw = 0;
        for (int ii = 0; ii < 6; ii++) {
            swatchValue.set( ii, 0 );
        }


        for (property p : properties) {
            if (p.getName().equalsIgnoreCase( "sw1" )) {
                swatchValue.set( 0, Integer.parseInt( p.getValue() ) );
            }
            if (p.getName().equalsIgnoreCase( "sw2" )) {
                swatchValue.set( 1, Integer.parseInt( p.getValue() ) );
            }
            if (p.getName().equalsIgnoreCase( "sw3" )) {
                swatchValue.set( 2, Integer.parseInt( p.getValue() ) );
            }
            if (p.getName().equalsIgnoreCase( "sw4" )) {
                swatchValue.set( 3, Integer.parseInt( p.getValue() ) );
            }
            if (p.getName().equalsIgnoreCase( "sw5" )) {
                swatchValue.set( 4, Integer.parseInt( p.getValue() ) );
            }
            if (p.getName().equalsIgnoreCase( "sw6" )) {
                swatchValue.set( 5, Integer.parseInt( p.getValue() ) );
            }
        }

    }


    private void asyncinitiation() {
        loadingfile = true;
        //manager.load(new AssetDescriptor<TmxMap>( "data/colors.txt", TmxMap.class, new mapLoader.mapParameter()));


        task = asyncExecutor.submit( new com.badlogic.gdx.utils.async.AsyncTask<Void>() {
            public Void call() {
                initallthings();
                return null;
            }
        } );

    }

    private void initallthings() {
        try {

            initializeThings();

            //msgbox("Welcome to NotTiled 1.0.8");
			/*
			if (!prefs.getBoolean("tutorial", false))
			{
				beg();
			}
			*/
			/*
			 language lang=new language();
			 defaultlang(lang);
			 Json json = new Json();
			 writeThis("language.tmx",json.prettyPrint(lang));
			 */
            //msgbox("auto.json saved!");
            //loadingfile=false;

			/*
			try{

					autotiles at=new autotiles();
					Json json = new Json();
					FileHandle f = new FileHandle(curdir+"/auto.json");
					at = json.fromJson(autotiles.class, f);
					autotiles=at.getAutotiles();
					refreshAutoMgmt();
					}catch(Exception e)
					{
						msgbox("place auto.json on the same folder with the tmx file.");
					}
			*/

        } catch (Exception e) {
            ErrorBung( e, "errorloginit.txt" );
        }
    }

    public void showCredits() {
        Table boss = new Table();
        Table table = new Table();
        table.center();
        table.defaults().height( btny );
        boss.setWidth( btnx );
        boss.setFillParent( true );
        ScrollPane spp = new ScrollPane( table );


        FileHandle handle = Gdx.files.internal( "credits.txt" );

        String text = handle.readString();
        String[] wordsArray = text.replace( "\r\n", "\n" ).replace( "\r", "\n" ).split( "\n", -1 );

        for (int i = 0; i < wordsArray.length; i++) {
            String[] h = wordsArray[i].split( ">" );
            switch (h[0]) {
                case "l":
                    Label lbl = new Label( h[1], skin );
                    lbl.setWrap( true );
                    lbl.setColor( 1, 1, 0, 1 );
                    lbl.setAlignment( Align.center );
                    table.add( lbl ).width( btnx ).padBottom( 10 ).row();
                    break;
                case "m":
                    lbl = new Label( h[1], skin );
                    lbl.setWrap( true );
                    lbl.setColor( 1, 1, 0, 1 );
                    lbl.setAlignment( Align.center );
                    table.add( lbl ).width( btnx ).padTop( 5 ).row();
                    break;
                case "s":
                    lbl = new Label( h[1], skin );
                    lbl.setWrap( true );
                    lbl.setAlignment( Align.center );
                    table.add( lbl ).width( btnx ).row();
                    break;
            }
        }

        TextButton back = new TextButton( z.back, skin );
        back.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();

            }
        } );
        table.add( back ).width( btnx ).padTop( 10 ).row();
        boss.add( spp );
        gotoStage( boss );
    }

    public void defaultlang(language lang) {
        lang.menu = "???";
        lang.map = "???";
        lang.select = "???";
        lang.rotate = "???";
        lang.flip = "???";
        lang.layer = "???";
        lang.activelayer = "???";
        lang.visibility = "???";
        lang.tile = "???";
        lang.object = "???";
        lang.all = "???";
        lang.stack = "???";
        lang.single = "???";
        lang.free = "???";
        lang.lock = "???";
        lang.activeobjecttool = "???";
        lang.rectangle = "???";
        lang.ellipse = "???";
        lang.point = "???";
        lang.polygon = "???";
        lang.polyline = "???";
        lang.text = "???";
        lang.image = "???";
        lang.copypaste = "???";
        lang.eraser = "???";

        //menu
        lang.supportnottiled = "???";
        lang.newfile = "???";
        lang.open = "???";
        lang.save = "???";
        lang.saveas = "???";
        lang.discordserver = "???";
        lang.whatsappgroup = "???";
        lang.license = "???";
        lang.reloadsamples = "???";
        lang.copytorustedwarfare = "???";
        lang.exit = "???";
        lang.back = "???";

        //map
        lang.preferences = "???";
        lang.background = "???";
        lang.manage = "???";
        lang.management = "???";

        //new
        lang.filename = "???";
        lang.directory = "???";
        lang.selectfolder = "???";
        lang.mapformat = "???";
        lang.renderorder = "???";
        lang.orientation = "???";
        lang.createnewfile = "???";
        lang.cancel = "???";
        lang.opentmxfile = "???";
        lang.ok = "???";

        //messages
        lang.info = "???";
        lang.yourmaphasbeensaved = "???";
        lang.selectnewlocation = "???";
        lang.sampleshasbeenreloaded = "???";
        lang.mapsenttorustedwatfare = "???";

        //prefs
        lang.enableblending = "???";
        lang.showgrid = "???";
        lang.showfps = "???";
        lang.showgidinpicker = "???";
        lang.showgidinmap = "???";
        lang.scrollspeed = "???";
        lang.showcustomgrid = "???";
        lang.gridx = "???";
        lang.gridy = "???";
        lang.openimagefile = "???";
        lang.customproperties = "???";
        lang.apply = "???";

        //base
        lang.autotile = "???";
        lang.tileset = "???";
        lang.tilelayer = "???";
        lang.objectgroup = "???";
        lang.property = "???";
        lang.tiles = "???";
        lang.tilesetting = "???";
        lang.animation = "???";
        lang.terraineditor = "???";
        lang.addnew = "???";
        lang.rename = "???";
        lang.properties = "???";
        lang.moveup = "???";
        lang.remove = "???";
        lang.name = "???";

        lang.template = "???";
        lang.type = "???";
        lang.value = "???";
        lang.tilepicker = "???";
        lang.copyall = "???";
        lang.setopacity = "???";

        lang.addimagetileset = "???";
        lang.importtsxfile = "???";
        lang.importfolder = "???";
        lang.saveastsx = "???";

        lang.set = "???";
        lang.duration = "???";
        lang.frame = "???";
        lang.replace = "???";
        lang.edit = "???";
        lang.default_ = "???";

        lang.source = "???";
        lang.tsxfile = "???";
        lang.usetsxfile = "???";
        lang.keycolor = "???";
        lang.tilecolumn = "???";
        lang.tilecount = "???";
        lang.spacing = "???";
        lang.margin = "???";

        lang.id = "???";
        lang.x = "???";
        lang.y = "???";
        lang.width = "???";
        lang.height = "???";
        lang.rotation = "???";
        lang.language = "???";

    }

    public void panTileTo(float x, float y, float duration) {
        panOriginX = tilecam.position.x;
        panOriginY = tilecam.position.y;
        panOriginZoom = panTargetZoom = tilecam.zoom;
        panTargetX = x;
        panTargetY = y;
        timeToPan = panDuration = duration;
        panType = 1;
    }

    public void panTo(float x, float y, float z, float duration) {
        panOriginX = cam.position.x;
        panOriginY = cam.position.y;
        panOriginZoom = cam.zoom;
        panTargetX = x;
        panTargetY = y;
        panTargetZoom = z;
        timeToPan = panDuration = duration;
        panType = 0;
    }


    public void uiAnim() {
        panOriginZoom2 = 1f;
        panTargetZoom2 = 1;
        timeToPan2 = panDuration2 = 1f;
    }

    String texta = "";

    public void drawLoadingScreen() {
        //if(!task.isDone()) {
        Gdx.gl.glEnable( GL20.GL_BLEND );
        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        ui.setProjectionMatrix( uicam.combined );
        ui.begin();

        texta = z.storageaccess;// "Make sure to give NotTiled access to your storage, \notherwise you will stuck on this screen...";

        if (landscape) {
            ui.draw( txlauncher, (ssy / 2) - (156 / 2), (ssx / 2) - (156 / 2) + 50, 156, 156 );
            str1.draw( ui, z.loadingfiles, 0, (ssx / 2) - 80 + 50, ssy, Align.center, true );
            str1.draw( ui, vers, 0, (ssx / 2) - 280 + 50, ssy, Align.center, true );
            str1.getData().setScale( .7f );
            str1.draw( ui, texta, 0, (ssx / 2) - 480 + 50, ssy, Align.center, true );
            str1.getData().setScale( 1f );

        } else {

            ui.draw( txlauncher, (ssx / 2) - (156 / 2), (ssy / 2) - (156 / 2) + 50, 156, 156 );
            str1.draw( ui, z.loadingfiles, 0, (ssy / 2) - 80 + 50, ssx, Align.center, true );
            str1.draw( ui, vers, 0, (ssy / 2) - 280 + 50, ssx, Align.center, true );
            str1.getData().setScale( .7f );
            str1.draw( ui, texta, 0, (ssy / 2) - 480 + 50, ssx, Align.center, true );
            str1.getData().setScale( 1f );

        }

        ui.end();
    }


    float loadingtime = 0;

    int FPSCount =0;

    @Override
    public void render() {

        FPSCount +=1;
        if (FPSCount ==60) FPSCount=0;

        try {
            if (firstrun) {
                asyncinitiation();
                firstrun = false;
            }
            if (loadingfile) {
                loadingtime += delta;
                drawLoadingScreen();

            } else {
                world.step( 1 / 60f, 6, 2 );

                Gdx.gl.glClearColor( bgr, bgg, bgb, 1f );
                clsEnter();
                if (firstload > 0) {
                    firstload -= 1;
                    Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
                    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                    return;
                }
                if (firstload == 0) {
                    uiAnim();
                    firstload = -1;
                }

                if (timeToPan >= 0) {
                    timeToPan -= delta;
                    float progress = timeToPan < 0 ? 1 : 1f - timeToPan / panDuration;
                    OrthographicCamera cammy;
                    switch (panType) {
                        case 1:
                            cammy = tilecam;
                            break;
                        case 2:
                            cammy = uicam;
                            break;
                        case 3:
                            cammy = gamecam;
                            break;
                        default:
                            cammy = cam;
                    }
                    cammy.position.x = Interpolation.fade.apply( panOriginX, panTargetX, progress );
                    cammy.position.y = Interpolation.fade.apply( panOriginY, panTargetY, progress );
                    cammy.zoom = Interpolation.fade.apply( panOriginZoom, panTargetZoom, progress );
                    //cammy.position.x = (float) Math.round(cammy.position.x * 100f) / 100f;
                    //cammy.position.y = (float) Math.round(cammy.position.y * 100f) / 100f;

                    cammy.update();
                }



                autosave += delta;

                if (autosave > 60f * autosaveInterval) {
                    log(autosaveInterval+"");
                    autosave = 0;
                    if (sAutoSave) {
                        status( z.autosaving, 1 );

                        try {
                            autosavePool.execute( new Runnable(  ){
                                @Override
                                public void run() {
                                    saveMap( curdir + "/" + curfile );


                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                }

                redraw += delta;

                if (redraw > 0.1f) {
                    redraw = 0;
                    cacheTiles();
                }

                undohistory += delta;

                if (undohistory > 5f) {
                    undohistory = 0;
                    //  Gdx.app.log("Cache","Clearing...");
                    if (undolayer.size() > Tw * Th * 2) {
                        undolayer.subList( 0, undolayer.size() - (Tw * Th * 2) ).clear();
                    }
                    // Gdx.app.log("Cache","Cleared");
                }


                rotator += delta;

                if (rotator > 0.15f) {
                    rotator = 0;
                    if (rotating && layers.size() > 0) {
                        viewMode = ViewMode.SINGLE;

                        int lastvis = -1;
                        int firstvis = -1;

                        for (int i = 0; i < layers.size(); i++) {
                            if (layers.get( i ).isVisible()) {
                                if (firstvis == -1) {
                                    firstvis = i;
                                }

                                lastvis = i;
                            }
                        }
                        Gdx.app.log( firstvis + "", lastvis + "" );
                        if (firstvis != -1) {
                            if (selLayer >= lastvis) {
                                selLayer = firstvis;
                            } else {

                                for (int i = 0; i < layers.size(); i++) {
                                    if (layers.get( i ).isVisible() && i > selLayer) {
                                        selLayer = i;
                                        resetCaches();
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }

                animate( delta );



                checkConnectionStatus();

                if (nofling > 0) nofling -= delta;
                switch (kartu) {
                    case "game":

                        //crt.setEnabled(true);
                        //vignette.setEnabled(true);
                        //curvature.setEnabled(true);
                        bloom.setEnabled( false );

                        postProcessor.capture();
                        mygame.keyinput();
                        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
                        batch.setProjectionMatrix( gamecam.combined );
                        batch.begin();
                        if (mygame.txBackground != null)
                            batch.draw( mygame.txBackground, gamecam.position.x - 2f, gamecam.position.y - 2f, 4, 4 );
                        batch.end();

                        mygame.renderer.setView( gamecam );
                        if (!mygame.loadingmap) {
                            mygame.renderer.render();
                            batch.setProjectionMatrix( gamecam.combined );
                            batch.begin();

                            ///
                            if (mygame.night) mygame.rayHandler.setCombinedMatrix( gamecam );

                            mygame.update( batch, delta, gamecam );

                            if (mygame.debugmode)
                                mygame.b2dr.render( mygame.world, gamecam.combined );


                            batch.end();
                        }
                        postProcessor.render();


                        Vector3 mouse = new Vector3( Gdx.input.getX(), Gdx.input.getY(), 0 );
                        uicam.unproject( mouse ); // mousePos is now in world coordinates

                        //background tombol
                        Gdx.gl.glEnable( GL20.GL_BLEND );
                        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
                        uis.setProjectionMatrix( uicam.combined );
                        uis.begin( ShapeRenderer.ShapeType.Filled );
                        uis.setColor( 0f, 0f, 0, 0.6f );
                        if (Gdx.app.getType() != Application.ApplicationType.Desktop || mygame.uitest) {
                            if (!mygame.starting && mygame.player.state != gameobject.states.DEAD) {

                                uisrect( gui.left, mouse, null );
                                uisrect( gui.right, mouse, null );
                                uisrect( gui.up, mouse, null );
                                uisrect( gui.down, mouse, null );
                                if (mygame.action1 != null) uisrect( gui.action1, mouse, null );
                                if (mygame.action2 != null) uisrect( gui.action2, mouse, null );
                                if (mygame.action3 != null) uisrect( gui.action3, mouse, null );
                                if (mygame.action4 != null) uisrect( gui.action4, mouse, null );
                            }
                        }
                        //uisrect(gui.restart,mouse,null);
                        if (mygame.player.state == gameobject.states.DEAD || (mygame.victory && mygame.nextlevel != null) || mygame.starting)
                            uisrect( gui.respawn, mouse, new Color( 0, 1f, 0, 1f ) );
                        if (mygame.playtest) uisrect( gui.exit, mouse, null );
                        if (mygame.victory || mygame.player.state == gameobject.states.DEAD || mygame.starting)
                            uisrect( gui.gamestatus, mouse, null );
                        uis.end();

                        ui.setProjectionMatrix( uicam.combined );
                        ui.begin();
                        str1.getData().setScale( 0.8f );
                        if (landscape) {
                            mygame.drawHUD( ui, str1, ssy, ssx );
                        } else {
                            mygame.drawHUD( ui, str1, ssx, ssy );

                        }
                        str1.getData().setScale( 4f );

                        if (mygame.victory) {
                            String msg = mygame.debriefing;
                            if (msg == null) msg = "You Win!";
                            str1draw( ui, msg, gui.gamestatus );

                        } else if (mygame.starting) {

                            String msg = mygame.briefing[mygame.msgindex].replace( "\\n", "\n" );
                            if (msg == null) msg = "Ready?";
                            str1draw( ui, msg, gui.gamestatus );


                        } else if (mygame.player.state == gameobject.states.DEAD) {
                            String msg = mygame.died;
                            if (msg == null) msg = "Oops... try again?";
                            str1draw( ui, msg, gui.gamestatus );
                        }


                        //str1draw(ui,"Keys : "+mygame.key+" | Items Left : "+mygame.coin , gui.layer);
                        if (Gdx.app.getType() != Application.ApplicationType.Desktop || mygame.uitest) {
                            if (!mygame.starting && mygame.player.state != gameobject.states.DEAD) {
                                uidrawbutton( txLeft, "Left", gui.left, 3 );
                                uidrawbutton( txDown, "Down", gui.down, 3 );
                                uidrawbutton( txRight, "Right", gui.right, 3 );
                                uidrawbutton( txUp, "Up", gui.up, 3 );
                                if (mygame.action1 != null)
                                    uidrawbutton( txRight, mygame.action1.name, gui.action1, 3 );
                                if (mygame.action2 != null)
                                    uidrawbutton( txDown, mygame.action2.name, gui.action2, 3 );
                                if (mygame.action3 != null)
                                    uidrawbutton( txLeft, mygame.action3.name, gui.action3, 3 );
                                if (mygame.action4 != null)
                                    uidrawbutton( txUp, mygame.action4.name, gui.action4, 3 );
                            }

                        }
                        //str1draw(ui,"Restart", gui.restart);
                        if (mygame.player.state == gameobject.states.DEAD)
                            str1draw( ui, "Respawn", gui.respawn );
                        if (mygame.victory && mygame.nextlevel != null)
                            str1draw( ui, "Next Level", gui.respawn );
                        if (mygame.starting) str1draw( ui, "OK", gui.respawn );
                        if (mygame.playtest) str1draw( ui, "Exit", gui.exit );
                        ui.end();


                        break;
                    case "world":
                        bloom.setEnabled( false );
                        vignette.setEnabled( false );
                        Gdx.gl.glEnable( GL20.GL_BLEND );
                        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                        postProcessor.capture();
                        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                        batch.totalRenderCalls = 0;


                        if (layers.size() > 0 && mode != "newpoly") {
                            if (selLayer >= layers.size()) selLayer = 0;
                            if (layers.get( selLayer ).getType() == layer.Type.TILE) {
                                mode = "tile";
                            } else if (layers.get( selLayer ).getType() == layer.Type.OBJECT) {
                                mode = "object";
                            } else if (layers.get( selLayer ).getType() == layer.Type.IMAGE) {
                                mode = "image";
                            }
                        }

                        if (midiplaying) {
                            if (composerPlayer != null) {
                                if (!composerPlayer.isPlaying()) {
                                    composerPlayer = null;
                                    midiplaying = false;
                                }
                            }
                        }

                        resetMinimap();

                        drawTiles();
                        drawGrid();
                        drawObjects();
                        drawObjectsInfo();
                        postProcessor.render();
                        drawWorldUI();
                        //draw debug for collision detection
                        //b2dr.render(world,cam.combined);

                        drawstage( delta );

                        break;
                    case "stage":
                        //crt.setEnabled(true);
                        bloom.setEnabled( true );

                        Gdx.gl.glEnable( GL20.GL_BLEND );
                        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                        postProcessor.capture();
                        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                        batch.totalRenderCalls = 0;
                        drawTiles();
                        //drawGrid();
                        postProcessor.render();
                        //drawObjects();
                        //drawObjectsInfo();
                        //drawWorldUI();
                        drawstage( delta );
                        stage.getCamera().update();

                        break;
                    case "tile":
                    case "pickanim":
                    case "pick":
                        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                        drawpicker();
                        drawstage( delta );

                        break;
                    case "editor":
                        switch (mode) {
                            case "tile":
                            case "object":
                            case "image":
                                bloom.setEnabled( false );
                                vignette.setEnabled( false );
                                Gdx.gl.glEnable( GL20.GL_BLEND );
                                Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                                postProcessor.capture();
                                Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                                batch.totalRenderCalls = 0;


                                resetMinimap();

                                drawTiles();
                                drawGrid();
                                drawObjects();
                                drawObjectsInfo();
                                postProcessor.render();
                                drawWorldUI();

                                drawstage( delta );
                                break;
                            case "pick":
                                Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

                                drawpicker();
                                drawstage( delta );

                                break;

                        }


                        break;

                    //case "stage":

                    //break;
                }

                keyinput( delta );
                if (timeToPan2 >= 0) {
                    timeToPan2 -= delta;
                    float progress = timeToPan2 < 0 ? 1 : 1f - timeToPan2 / panDuration2;
                    uicam.zoom = Interpolation.fade.apply( panOriginZoom2, panTargetZoom2, progress );

                    uicam.update();
                    uis.setProjectionMatrix( uicam.combined );
                    uis.begin( ShapeRenderer.ShapeType.Filled );
                    Gdx.gl.glEnable( GL20.GL_BLEND );
                    Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                    float alfa = timeToPan2;
                    Color cc = new Color( 0, 0, 0, alfa );
                    if (!landscape) {
                        uis.rect( 0f, 0f, (float) ssx, (float) ssy, cc, cc, cc, cc );
                    } else {
                        uis.rect( 0f, 0f, (float) ssy, (float) ssx, cc, cc, cc, cc );
                    }
                    uis.end();

                }

                if (firstload > 0) {
                    uis.setProjectionMatrix( uicam.combined );
                    uis.begin( ShapeRenderer.ShapeType.Filled );
                    Gdx.gl.glEnable( GL20.GL_BLEND );
                    Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                    Color cc = new Color( 0, 0, 0, 1 );
                    uis.rect( 0f, 0f, (float) ssx, (float) ssy, cc, cc, cc, cc );
                    uis.end();
                    firstload -= 1;
                }


            }
        } catch (Exception e) {
            ErrorBung( e, "errorlog2.txt" );
            //status("Error in render", 5);
            if (batch.isDrawing()) batch.end();
            if (ui.isDrawing()) ui.end();
            if (sr.isDrawing()) sr.end();
            if (uis.isDrawing()) uis.end();


            //newtmxfile(false);
        }

    }

    void drawCoordinates() {
        if (sShowCoords) {
            str1.getData().setScale( 0.01f + Tsw / 160f );
            for (int yy = 0; yy < Th; yy++) {
                for (int xx = 0; xx < Tw; xx++) {
                    if (orientation.equalsIgnoreCase( "isometric" )) {
                        float offsetx = (xx * Tsw / 2) + (yy * Tsw / 2);
                        float offsety = (xx * Tsh / 2) - (yy * Tsh / 2);

                        str1.draw( batch, xx + "," + yy, (xx * Tsw + Tsw / 8f) - offsetx, (-yy * Tsh + Tsh / 3f) - offsety );

                    } else {
                        str1.draw( batch, xx + "," + yy, xx * Tsw + Tsw / 8f, -yy * Tsh + Tsh / 3f );
                    }
                }

            }
            str1.getData().setScale( 1f );
        }
        if (sShowGIDmap) {
            str1.getData().setScale( 0.01f + Tsw / 160f );
            for (int yy = 0; yy < Th; yy++) {
                for (int xx = 0; xx < Tw; xx++) {
                    int num = yy*Tw+xx;
                    long spr = layers.get(selLayer).getStr().get(num);

                    if (orientation.equalsIgnoreCase( "isometric" )) {
                        float offsetx = (xx * Tsw / 2) + (yy * Tsw / 2);
                        float offsety = (xx * Tsh / 2) - (yy * Tsh / 2);


                        if (spr!=0) str1.draw( batch, spr+"", (xx * Tsw + Tsw / 8f) - offsetx, (-yy * Tsh + Tsh ) - offsety );

                    } else {
                        if (spr!=0) str1.draw( batch, spr+"", xx * Tsw + Tsw / 8f, -yy * Tsh + Tsh  );
                    }
                }

            }
            str1.getData().setScale( 1f );
        }


    }

    void initializePostProcessor() { //I DONT EVEN KNOW WHAT THESE MEANS LOL
        ShaderLoader.BasePath = "data/shaders/";
        postProcessor = new PostProcessor( false, true, isDesktop );

        postProcessor.setClearColor( .5f, .5f, .5f, 1 );
        int vpW = Gdx.graphics.getWidth();
        int vpH = Gdx.graphics.getHeight();
        // create the effects you want
        bloom = new Bloom( (int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f) );
        curvature = new Curvature();
        zoomer = new Zoomer( vpW, vpH, isDesktop ? RadialBlur.Quality.VeryHigh : RadialBlur.Quality.Low );
        int effects = CrtScreen.Effect.TweakContrast.v | CrtScreen.Effect.PhosphorVibrance.v | CrtScreen.Effect.Scanlines.v | CrtScreen.Effect.Tint.v;
        crt = new CrtMonitor( vpW, vpH, false, false, CrtScreen.RgbMode.ChromaticAberrations, effects );
        crt.setTint( 0.8f, 0.8f, .8f );
        Combine combine = crt.getCombinePass();
        combine.setSource1Intensity( 0f );
        combine.setSource2Intensity( 1f );
        combine.setSource1Saturation( 0f );
        combine.setSource2Saturation( 1f );
        vignette = new Vignette( vpW, vpH, false );
        // add them to the postprocessor
        postProcessor.addEffect( curvature );
        postProcessor.addEffect( zoomer );
        postProcessor.addEffect( vignette );
        postProcessor.addEffect( crt );
        postProcessor.addEffect( bloom );
        bloom.setBaseIntesity( 0.0f );
        bloom.setBlurAmount( 1 );//5
        zoomer.setBlurStrength( -0.1f );
        zoomer.setOrigin( Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 );
        curvature.setZoom( 1f );
        vignette.setIntensity( 1f );
        bloom.setEnabled( false );
        crt.setEnabled( false );
        vignette.setEnabled( false );
        curvature.setEnabled( false );
        zoomer.setEnabled( false );
    }

    private void clsEnter() {
        delta = Gdx.graphics.getDeltaTime();
        fps = Integer.toString( Gdx.graphics.getFramesPerSecond() );

    }

    public void animate(float delta) {
        if (turun) {
            blink -= 0.02f;
        } else {
            blink += 0.02f;
        }
        if (blink >= 1) {
            blink = 1;
            turun = true;
        }
        if (blink <= 0) {
            blink = 0;
            turun = false;
        }

        if (tilesets.size() > 0 && !deletinganim) {
            for (int g = 0; g < tilesets.size(); g++) {
                tiles = tilesets.get( g ).getTiles();
                if (tiles.size() > 0) {
                    for (int i = 0; i < tiles.size(); i++) {
                        if (tiles.get( i ).getAnimation().size() > 0) {
                            tiles.get( i ).addTimer( delta );
                            int actFrame = tiles.get( i ).getActiveFrameIndex();
                            if (tiles.get( i ).getTimer() >= (float) tiles.get( i ).getActiveFrameDuration() / 1000) {
                                tiles.get( i ).setTimer( 0 );
                                tiles.get( i ).setActiveFrameIndex( tiles.get( i ).getActiveFrameIndex() + 1 );
                                if (tiles.get( i ).getActiveFrameIndex() >= tiles.get( i ).getAnimation().size()) {
                                    tiles.get( i ).setActiveFrameIndex( 0 );
                                }
                                int newActframe = tiles.get( i ).getActiveFrameIndex();
                                if (!deletinganim)
                                    tiles.get( i ).setActiveFrameID( tiles.get( i ).getAnimation().get( newActframe ).getTileID() );
                                if (!deletinganim)
                                    tiles.get( i ).setActiveFrameDuration( tiles.get( i ).getAnimation().get( newActframe ).getDuration() );

                            }
                        }
                    }
                }//if
            }//for
        }//if
    }



    public void escapegame() {
        kartu = "world";
        if (mygame.bgm.isPlaying()) mygame.bgm.stop();
    }

    public void restartgame() {
        if (mygame.bgm.isPlaying()) mygame.bgm.stop();
        playgame( curdir, mygame.file );
    }

    public void nextlevel() {
        prefs.putString( curfile, mygame.nextlevel ).flush();
        if (mygame.bgm.isPlaying()) mygame.bgm.stop();
        if (mygame.nextlevel != null) playgame( curdir, mygame.nextlevel );

    }

    boolean touched, usetool;
    boolean touchable = true;

    void keyinput(float delta) {
        if (!Gdx.input.isTouched() && iseditGUI) {
            iseditGUI = false;
        }

        if (Gdx.input.isTouched()) {
            touched = true;
        }
        if (touched & usetool & !Gdx.input.isTouched()) {
            touched = false;
            usetool = false;
            //updateMinimap();
        }

        if (roll) {
            if (!Gdx.input.isTouched()) {
                if (mapstartSelect == mapendSelect) {
                    roll = false;
                }
            }

            float pex, pey;
            pex = (float) Gdx.input.getX() / nssx;
            pey = (float) Gdx.input.getY() / nssy;


            //Gdx.app.log("pexpey", ""+ Gdx.input.getX() + " | "+ nssx  + " | "+ Gdx.input.getY() + " | "+  nssy);
            if (pex > 0.90f) cam.translate( 5, 0 );
            if (pex < 0.10f) cam.translate( -5, 0 );
            if (pey > 0.90f) cam.translate( 0, -5 );
            if (pey < 0.10f) cam.translate( 0, 5 );
            int onset = 0;
            if (orientation.equalsIgnoreCase( "isometric" )) {
                onset = Tsw * Tw / 2;
            }
            if (!orientation.equalsIgnoreCase( "isometric" )) {
                if (cam.position.x < 0 - onset) cam.position.x = 0 - onset;
                if (cam.position.x > Tsw * Tw - onset) cam.position.x = Tsw * Tw - onset;
                if (cam.position.y < -Tsh * Th) cam.position.y = -Tsh * Th;
                if (cam.position.y > 0) cam.position.y = 0;
            }

            cam.update();
            // cam.translate( -x, y );

            //if (p1>500) cam.position.x-=10;
            //    if (cam.position.x > Tsw * Tw - onset) cam.position.x = Tsw * Tw - onset;
            //   if (cam.position.y < -Tsh * Th) cam.position.y = -Tsh * Th;
            //   if (cam.position.y > 0) cam.position.y = 0;


        }

        if (kartu == "game") {

            if (Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE )) escapegame();
            if (Gdx.input.isKeyJustPressed( Input.Keys.R )) restartgame();

            if (Gdx.input.isKeyJustPressed( Input.Keys.SPACE )) {
                if (mygame.victory) {
                    nextlevel();
                } else if (mygame.starting) {
                    if (mygame.msgindex < mygame.briefing.length - 1) {
                        mygame.msgindex += 1;
                    } else {
                        mygame.starting = false;
                    }

                } else if (mygame.player.state == gameobject.states.DEAD) {
                    mygame.respawn();
                }
            }

            if (touchable == false && !Gdx.input.isTouched()) {
                touchable = true;
            }
            boolean pressed = false;
            for (int i = 0; i < 5; i++) { // 20 is max number of touch points
                if (Gdx.input.isTouched( i )) {
                    Vector3 touch2 = new Vector3();
                    uicam.unproject( touch2.set( Gdx.input.getX( i ), Gdx.input.getY( i ), 0 ) );

                    //no limitation
                    //if (tapped(touch2, gui.restart)) restartgame();
                    if (tapped( touch2, gui.exit ) && mygame.playtest) escapegame();

                    //no press when victory


                    if (tapped( touch2, gui.gamestatus ) && touchable) {
                        touchable = false;
                        if (mygame.player.state == gameobject.states.DEAD) {
                            mygame.respawn();
                        }
                        if (mygame.victory && mygame.nextlevel != null) {
                            nextlevel();
                        }
                        if (mygame.starting) {
                            if (mygame.msgindex < mygame.briefing.length - 1) {
                                mygame.msgindex += 1;
                            } else {
                                mygame.starting = false;
                            }

                        }
                    }

                    if (mygame.victory || mygame.starting) return;
                    //no press when dead, no press when desktop
                    if (Gdx.app.getType() != Application.ApplicationType.Desktop || mygame.uitest) {
                        if (mygame.player.state == com.mirwanda.nottiled.platformer.gameobject.states.DEAD)
                            return;
                        pressed = true;
                        if (tapped( touch2, gui.up )) mygame.pressup();
                        if (tapped( touch2, gui.left )) mygame.pressleft();
                        if (tapped( touch2, gui.down )) mygame.pressdown();
                        if (tapped( touch2, gui.right )) mygame.pressright();
                        if (mygame.action1 != null && tapped( touch2, gui.action1 ))
                            mygame.act( mygame.action1 );
                        if (mygame.action2 != null && tapped( touch2, gui.action2 ))
                            mygame.act( mygame.action2 );
                        if (mygame.action3 != null && tapped( touch2, gui.action3 ))
                            mygame.act( mygame.action3 );
                        if (mygame.action4 != null && tapped( touch2, gui.action4 ))
                            mygame.act( mygame.action4 );
                    }

                }

            }
            if (Gdx.app.getType() == Application.ApplicationType.Desktop && !mygame.uitest) return;

            if (!pressed) {
                mygame.stand();
            }
            return;

        }


        if (Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT ) && Gdx.input.isKeyJustPressed( Input.Keys.Z )) {
            if (mode == "tile") {
                try {

                    if (undolayer.size() > 0) {
                        layerhistory lh;
                        long histcount = 0;
                        for (int n = undolayer.size() - 1; n >= 0; n--) {

                            lh = undolayer.get( n );
                            layers.get( lh.getLayer() ).getStr().set( lh.getLocation(), lh.getFrom() );
                            layers.get( lh.getLayer() ).getTset().set( lh.getLocation(), lh.getoldTset() );
                            redolayer.add( lh );
                            histcount++;
                            if (!lh.isFollower()) {
                                java.util.List<layerhistory> templist = new ArrayList<layerhistory>();

                                for (int t = 0; t < undolayer.size() - histcount; t++) {
                                    templist.add( undolayer.get( t ) );
                                }
                                undolayer = new ArrayList<layerhistory>();
                                undolayer = templist;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    ErrorBung( e, "undo.txt" );
                }
            }
            if (mode == "newpoly") {

                newobject.undoPoint();
            }
        }

        if (kartu != "stage") {
            if (Gdx.input.isKeyJustPressed( Input.Keys.E )) {
                eraser = !eraser;
            }
            if (Gdx.input.isKeyPressed( Input.Keys.W ) || Gdx.input.isKeyPressed( Input.Keys.UP )) {
                cam.position.add( 0, 40f, 0 );
            }
            if (Gdx.input.isKeyPressed( Input.Keys.A ) || Gdx.input.isKeyPressed( Input.Keys.LEFT )) {
                cam.position.add( -40f, 0, 0 );
            }
            if (Gdx.input.isKeyPressed( Input.Keys.S ) || Gdx.input.isKeyPressed( Input.Keys.DOWN )) {
                cam.position.add( 0, -40f, 0 );
            }
            if (Gdx.input.isKeyPressed( Input.Keys.D ) || Gdx.input.isKeyPressed( Input.Keys.RIGHT )) {
                cam.position.add( 40f, 0, 0 );
            }
            cam.update();
        }

        if (Gdx.input.isKeyPressed( Input.Keys.X )) {
            if (cammode == "View only") {
                if (cam.zoom < zoomTreshold) {
                    cammode = "";
                }
            } else {
                if (cam.zoom > zoomTreshold) {
                    cammode = "View only";
                    cacheTiles();
                }
            }
            if (kartu == "world") {
                if (cam.zoom < 100) {
                    cam.zoom = cam.zoom * 1.1f;
                    cam.update();
                }
            } else if (kartu == "tile") {
                if (tilecam.zoom < 100) {
                    tilecam.zoom = tilecam.zoom * 1.1f;
                    tilecam.update();
                }
            }
        }
        if (Gdx.input.isKeyPressed( Input.Keys.C )) {
            if (cammode == "View only") {
                if (cam.zoom < zoomTreshold) {
                    cammode = "";
                }
            } else {
                if (cam.zoom > zoomTreshold) {
                    cammode = "View only";
                    cacheTiles();
                }
            }
            if (kartu == "world") {
                if (cam.zoom > 0.01) {
                    cam.zoom = cam.zoom / 1.1f;
                    cam.update();
                }
            } else if (kartu == "tile") {
                if (tilecam.zoom > 0.01) {
                    tilecam.zoom = tilecam.zoom / 1.1f;
                    tilecam.update();
                }
            }
        }
        //{tilecam.zoom = Tsw/320f;}

        getbacknow();
        if (velx > 10) velx = velx / 1.2f;
        if (velx < -10) velx = velx / 1.2f;
        if (vely > 10) vely = vely / 1.2f;
        if (vely < -10) vely = vely / 1.2f;
        if (velx <= 10 && velx >= -10) velx = 0;
        if (vely <= 10 && vely >= -10) vely = 0;
        if (!(velx == 0 && vely == 0)) {
            pan( 0, 0, velx / 50, vely / 50 );
        } else {
            drag = false;
        }


    }

    boolean asked = false;

    public void getbacknow() {
        if (Gdx.input.isKeyJustPressed( Input.Keys.BACK ) || Gdx.input.isKeyJustPressed( Input.Keys.ESCAPE )) {
            //all the damn backs should stays here....

            switch (kartu) {
                case "world":
                    switch (mode) {
                        case "tile":
                        case "object":
                            if (!stamp & !asked & !assemblymode) {
                                Dialog dialog = new Dialog( z.confirmation, skin, "dialog" ) {
                                    public void result(Object obj) {
                                        System.out.println( "result " + obj );
                                        if ((boolean) obj) Gdx.app.exit();
                                        Gdx.input.setInputProcessor( im );
                                        asked = false;
                                    }
                                };
                                Gdx.input.setInputProcessor( stage );
                                asked = true;
                                dialog.text( z.quit );
                                dialog.button( z.yes, true ); //sends "true" as the result
                                dialog.button( z.no, false );  //sends "false" as the result
                                dialog.show( stage );
                            }
                            assemblymode=false;
                            roll = false;
                            stamp = false;
                            rotating = false;
                            break;
                        case "newpoly":
                            if (newobject.getPointsSize() == 1) {
                                newobject.setShape( "point" );
                            }
                            if (newobject.getPointsSize() == 2) {
                                newobject.setShape( "polyline" );
                            }
                            mode = "object";
                            break;
                    }
                case "stage":
                    if (lastStage == tFrameMgmt) {
				/*
				if (tilesets.size()>0){
					for (int g=0;g<tilesets.size();g++){
						tiles = tilesets.get(g).getTiles();

						String[] srr = new String[tiles.size()];
						for (int i=0;i < tiles.size();i++)
						{
							srr[i] = Integer.toString(tiles.get(i).getTileID());
							ltilelist.setItems(srr);
						}
					}}
				*/
                        selTileID = -1;
                        anime = false;
                        gotoStage( tTileMgmt );

                    } else if (lastStage == tPropsMgmt) {
                        switch (sender) {
                            case "object":
                                gotoStage( tObjProp );
                                break;
                            case "tile":
                                gotoStage( tTileMgmt );
                                break;
                            case "tset":
                                gotoStage( tTsProp );
                                break;
                            case "map":
                                gotoStage( tProperties );
                                break;
                            case "auto":
                                gotoStage( tAutoMgmt );
                                break;
                        }

                    } else if (lastStage == tTsProp) {
                        int dexo = ltsetlist.getSelectedIndex();
                        int saiz = tilesets.size();
                        String[] srr = new String[saiz];
                        for (int i = 0; i < saiz; i++) {
                            srr[i] = tilesets.get( i ).getName();
                        }
                        ltsetlist.setItems( srr );
                        ltsetlist.setSelectedIndex( dexo );
                        if (frompick) {
                            onToPicker();
                            //lastStage=nullTable;
                        } else {
                            gotoStage( tTsetMgmt );
                        }
                        //gotoStage(tTsetMgmt);
                    } else if (lastStage == tTileMgmt) {

                        //fuck this bug
                        /**/
                        if (frompick) {
                            onToPicker();
                            frompick = false;
                            lastStage = null;
                            //gotoStage(tTsetMgmt);
                        } else {
                            gotoStage( tTsetMgmt );
                        }
                        backing = false;
                        return;

                        /**/
                    } else if (lastStage == trandomgen) {
                        gotoStage( ttools );
                    } else if (lastStage == tpt) {
                        gotoStage( tPropsMgmt );
                    }

                    break;


                case "tile":
                    backToMap();
                    break;
                case "pickanim":
                    if (tilePicker == "terraineditor") {
                        lastStage = null;
                        gotoStage( tTileMgmt );
                    }
                    break;
            }

            //working

            //this one is also working


        }

    }

    private void drawstage(float delta) {
        stage.act( delta );
        stage.draw();
        ui.setProjectionMatrix( uicam.combined );
        ui.begin();
        long ini = selTileID;

        if (selTileID != -1 && tilesets.size() > 0 && anime) {
            if (tilesets.size() > 0) {
                tileset ts = tilesets.get( selTsetID );
                tiles = tilesets.get( selTsetID ).getTiles();
                if (tiles.get( selTileID ).getAnimation().size() > 0) {

                    ini = tiles.get( selTileID ).getActiveFrameID();

                    int xpos = 160;
                    int ypos = -100;
                    int xpos2 = (int) ini % ts.getWidth();
                    int ypos2 = (int) ini / ts.getWidth();

                    ui.draw( ts.getTexture(), xpos, ypos, xpos2 * ts.getTilewidth(), ypos2 * ts.getTileheight(), ts.getTilewidth(), ts.getTileheight() );

                } else {
                    //ini = layers.get(selAnim).getStr() .get(animations.get(selAnim).getActiveFrameID());
                }
            }
        }
        ui.end();
    }

    private void drawpicker() {
        if (sEnableBlending) {
            Gdx.gl.glEnable( GL20.GL_BLEND );
            Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
            batch.setColor( 1, 1, 1, 1 );
        }

        int seltset = 0;
        batch.setProjectionMatrix( tilecam.combined );

        if (tilesets.size() > 0) {
            batch.begin();
            tileset ts = null;
            //tile, pick, pickanim
            if (kartu == "tile" || kartu == "editor") {
                seltset = this.seltset;
            } else {
                switch (tilePicker) {
                    case "newimgobj":
                        seltset = this.seltset;
                        break;
                    case "props":
                    case "rnda":
                    case "rndb":
                    case "repa":
                    case "repb":
                    case "sw1":
                    case "sw2":
                    case "sw3":
                    case "sw4":
                    case "sw5":
                    case "sw6":
                        seltset = this.seltset;
                        break;
                    default:
                        seltset = this.selTsetID;
                        break;
                }
            }

            ts = tilesets.get( seltset );

            java.util.List<terrain> tr = tilesets.get( seltset ).getTerrains();


            if (pickAuto) { //auto list
                for (int n = 0; n < tr.size(); n++) {
                    //tile t =tilesets.get( seltset ).getTiles().get( tr.get( n ).getTile());
                    long ist = tr.get( n ).getTile();//t.getTileID();//-tilesets.get(seltset).getFirstgid();
                    int wd = ts.getWidth();
                    int xpos2 = (int) (ist) % wd;
                    int ypos2 = (int) (ist) / wd;
                    int margin = ts.getMargin();
                    int spacing = ts.getSpacing();

                    batch.draw( ts.getTexture(), 0, -n * ts.getTileheight(), (xpos2 * (ts.getTilewidth() + spacing)) + margin, (ypos2 * (ts.getTileheight() + spacing)) + margin, ts.getTilewidth(), ts.getTileheight() );
                    str1.getData().setScale( .2f );
                    str1.draw( batch, tr.get( n ).getName(), 24, 14 + -n * ts.getTileheight(), ts.getTilewidth(), Align.left, false );
                    str1.getData().setScale( 1f );

                }
            } else { //manual
                for (int i = 0; i < ts.getTilecount(); i++) { //manual drawing

                    long ini = i;//+tilesets.get(seltset.getFirstgid();
                    boolean terrain = false, autotl = false;

                    if (tr.size() > 0) {
                        for (int n = 0; n < tr.size(); n++) {
                            if (ini == tr.get( n ).getTile()) {
                                terrain = true;
                            }
                        }
                    }
				/* cause lags if there are lots of autotiles
				if (autotiles.size() > 0)
				{
					for (int n =0;n < autotiles.size();n++)
					{
						for (int m=0;m<autotiles.get(n).getProperties().size();m++)
						{
							if (autotiles.get(n).getProperties().get(m).getName().equalsIgnoreCase("source"))
							{
								if (ini+tilesets.get(seltset).getFirstgid() == Integer.parseInt(autotiles.get(n).getProperties().get(m).getValue())){
									autotl=true;
									break;
								}
							}
						}

					}
				}
				*/
                    tiles = ts.getTiles();
                    if (tiles.size() > 0) {
                        for (int n = 0; n < tiles.size(); n++) {
                            if (ini == tiles.get( n ).getTileID()) {


                                if (tiles.get( n ).getAnimation().size() > 0) {
                                    ini = tiles.get( n ).getActiveFrameID();
                                }

                            }
                        }
                    }

                    long ist = ini;//-tilesets.get(seltset).getFirstgid();
                    int wd = ts.getWidth();
                    int xpos = (i) % wd;
                    int ypos = (i) / wd;
                    int xpos2 = (int) (ist) % wd;
                    int ypos2 = (int) (ist) / wd;
                    int margin = ts.getMargin();
                    int spacing = ts.getSpacing();


                    batch.draw( ts.getTexture(), xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), (xpos2 * (ts.getTilewidth() + spacing)) + margin, (ypos2 * (ts.getTileheight() + spacing)) + margin, ts.getTilewidth(), ts.getTileheight() );
                    if (sShowGID) {
                        str1.getData().setScale( 0.1f );
                        str1.draw( batch, Long.toString( ini + tilesets.get( seltset ).getFirstgid() ), (float) xpos * ts.getTilewidth() + 2, (float) -ypos * ts.getTileheight() + ts.getTileheight() - 2, ts.getTilewidth(), Align.left, false );
                        str1.draw( batch, Long.toString( ini ), (float) xpos * ts.getTilewidth() + 2, (float) -ypos * ts.getTileheight() + ts.getTileheight() - 10, ts.getTilewidth(), Align.left, false );

                        str1.getData().setScale( 1f );
                    }
                    str1.getData().setScale( .2f );
                    if (terrain)
                        str1.draw( batch, "Auto", xpos * ts.getTilewidth(), -ypos * ts.getTileheight() + Tsh * 3 / 4, Tsw, Align.center, false );
                    if (autotl)
                        str1.draw( batch, "Macro", xpos * ts.getTilewidth(), -ypos * ts.getTileheight() + Tsh * 3 / 4, Tsw, Align.center, false );
                    str1.getData().setScale( 1f );
                }//end of manual drawing
            }//end of drawing
            batch.end();


            //GRID FOR TILES SELECTOR
            sr.setProjectionMatrix( tilecam.combined );
            Gdx.gl.glEnable( GL20.GL_BLEND );
            Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

            sr.begin( ShapeRenderer.ShapeType.Filled );

            sr.setColor( 0, 0, 0, 0.5f );
            int weight = 1, cool = 1;
            if (Tsw >= 64) weight = 2;
            int Tswa = tilesets.get( seltset ).getTilewidth();
            int Tsha = tilesets.get( seltset ).getTileheight();

            if (!pickAuto) {
                for (int i = 0; i <= tilesets.get( seltset ).getWidth(); i++) {
                    //if (i%5==0){cool=2;}else{cool=1;}

                    sr.rectLine( (Tswa * i), Tsha, (i * Tswa), -Tsha * tilesets.get( seltset ).getHeight() + Tsha, Tswa / 16f );
                }
                for (int j = -1; j < tilesets.get( seltset ).getHeight(); j++) {
                    //if ((j+1)%5==0){cool=2;}else{cool=1;}
                    sr.rectLine( 0, -(Tsha * j), Tswa * tilesets.get( seltset ).getWidth(), -(j * Tsha), Tsha / 16f );
                }
            } else {
                for (int i = 0; i <= 6; i++) {
                    //if (i%5==0){cool=2;}else{cool=1;}

                    if (i == 0 || i == 6)
                        sr.rectLine( (Tswa * i), Tsha, (i * Tswa), -Tsha * tilesets.get( seltset ).getTerrains().size() + Tsha, Tswa / 16f );
                }
                for (int j = -1; j < tilesets.get( seltset ).getTerrains().size(); j++) {
                    //if ((j+1)%5==0){cool=2;}else{cool=1;}
                    sr.rectLine( 0, -(Tsha * j), Tswa * 6, -(j * Tsha), Tsha / 16f );
                }
            }
            //debugMe=tilesets.get(seltset).getWidth() + "-" + tilesets.get(seltset).getHeight();
            if (kartu == "tile" || tilePicker == "massprops") {
                if (stamp) {
                    sr.setColor( 1, 0, 0, 0.5f );
                    int SprW = tilesets.get( seltset ).getWidth();

                    sr.rect( (startSelect % SprW) * Tswa, -(startSelect / SprW) * Tsha + Tsha, ((endSelect % SprW) * Tswa + Tswa) - (startSelect % SprW) * Tswa, -(endSelect / SprW) * Tsha + (startSelect / SprW) * Tsha - Tsha );
                    sr.setColor( 0, 0, 0, 1 );
                }
            }
            if (kartu == "tile" && issettingtile) {
                for (int i = 0; i < ts.getTilecount(); i++) {
                    int wd = ts.getWidth();
                    int xpos = (i) % wd;
                    int ypos = (i) / wd;
                    if (massprops.get( i )) {
                        sr.setColor( 1, 0, 0, 0.5f );
                        sr.rect( xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), ts.getTilewidth(), ts.getTileheight() );
                        sr.setColor( 0, 0, 0, 1 );
                    }
                }
            }

            if (kartu == "pickanim" && tilePicker == "massprops") {
                sr.setColor( 0, 1, 0, .5f );
                for (int i = 0; i < ts.getTilecount(); i++) {
                    int wd = ts.getWidth();
                    int xpos = (i) % wd;
                    int ypos = (i) / wd;
                    if (massprops.get( i )) {
                        sr.rect( xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), ts.getTilewidth(), ts.getTileheight() );
                    }
                }
            }
            if (kartu == "pickanim" && tilePicker == "terraineditor") {
                for (int i = 0; i < ts.getTilecount(); i++) {

                    long ini = i;//+tilesets.get(seltset.getFirstgid();
                    boolean terrain = false;
                    int curTerrain = 0;
                    int[] curNodes = null;
                    tiles = ts.getTiles();
                    if (tiles.size() > 0) {
                        for (int n = 0; n < tiles.size(); n++) {
                            if (ini == tiles.get( n ).getTileID()) {

                                if (tiles.get( n ).isTerrainForEditor()) {
                                    terrain = true;
                                    curNodes = tiles.get( n ).getTerrain();
                                }

                                if (tiles.get( n ).getAnimation().size() > 0) {
                                    ini = tiles.get( n ).getActiveFrameID();
                                }

                            }
                        }
                    }

                    long ist = ini;//-tilesets.get(seltset).getFirstgid();
                    int wd = ts.getWidth();
                    int xpos = (i) % wd;
                    int ypos = (i) / wd;
                    int xpos2 = (int) (ist) % wd;
                    int ypos2 = (int) (ist) / wd;
                    int margin = ts.getMargin();
                    int spacing = ts.getSpacing();

                    sr.setColor( 1, 0, 0, .5f );
                    if (terrain) {
                        curTerrain = tilesets.get( selTsetID ).getSelTerrain();
                        if (curNodes[0] == curTerrain)
                            sr.rect( xpos * ts.getTilewidth(), -ypos * ts.getTileheight() + ts.getTileheight() / 2, ts.getTilewidth() / 2, ts.getTileheight() / 2 );
                        if (curNodes[1] == curTerrain)
                            sr.rect( xpos * ts.getTilewidth() + ts.getTilewidth() / 2, -ypos * ts.getTileheight() + ts.getTileheight() / 2, ts.getTilewidth() / 2, ts.getTileheight() / 2 );
                        if (curNodes[2] == curTerrain)
                            sr.rect( xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), ts.getTilewidth() / 2, ts.getTileheight() / 2 );
                        if (curNodes[3] == curTerrain)
                            sr.rect( xpos * ts.getTilewidth() + ts.getTilewidth() / 2, -ypos * ts.getTileheight(), ts.getTilewidth() / 2, ts.getTileheight() / 2 );


                    }


                }
            }
            sr.end();
            if (sEnableBlending) {
                Gdx.gl.glDisable( GL20.GL_BLEND );
            }
        }
        //nothing should apper if tileset is empty.

        //BACKGROUND FOR UI
        Gdx.gl.glEnable( GL20.GL_BLEND );
        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

        uis.setProjectionMatrix( uicam.combined );
        uis.begin( ShapeRenderer.ShapeType.Filled );
        uis.setColor( 0f, 0f, 0, 0.4f );
        Vector3 mouse = new Vector3( Gdx.input.getX(), Gdx.input.getY(), 0 );
        uicam.unproject( mouse ); // mousePos is now in world coordinates


        if (tilesets.size() == 0) {
            //uisrect(0, 100,50,60);
            uisrect( gui.pickerback, mouse, null );//tool switch

        } else {
            if (kartu == "pickanim") {
                uisrect( gui.pickerback, mouse, null );//tool switch
                if (tilePicker == "terraineditor") {
                    uisrect( gui.newterrain, mouse, null );//tool switch
                } else if (tilePicker == "massprops") {
                    uisrect( gui.newterrain, mouse, null );
                }
            }

        }
/////
        if (kartu == "editor") uisrect( gui.tilemode, mouse, null );
        if (kartu == "tile" || kartu == "editor") {
            uisrect( gui.tilesetsmid, mouse, null );
            if (tilesets.size() > 0) {
                uisrect( gui.tilesetsleft, mouse, null );
                uisrect( gui.tilesetsright, mouse, null );
                if (tilesets.get( seltset ).getTerrains().size() > 0) {
                    uisrect( gui.tilemode, mouse, null );
                }
            }

        } else {


            switch (tilePicker) {
                case "props":
                case "rnda":
                case "rndb":
                case "repa":
                case "repb":
                case "sw1":
                case "sw2":
                case "sw3":
                case "sw4":
                case "sw5":
                case "sw6":
                case "newimgobj":

                    uisrect( gui.tilesetsmid, mouse, null );
                    uisrect( gui.tilesetsleft, mouse, null );
                    uisrect( gui.tilesetsright, mouse, null );

                    break;

                default:
                    uisrect( gui.tilesetsmid, mouse, null );

                    break;
            }
        }

        ////


        if (kartu == "editor") {
            uisrect( gui.editormode, mouse, null );
            uisrect( gui.editorcancel, mouse, null );
            uisrect( gui.editorsave, mouse, null );
            uisrect( gui.editorleft, mouse, vis( "editorleft" ) );//tile/obj switch
            uisrect( gui.editorright, mouse, vis( "editorright" ) );//layer switch
            uisrect( gui.editorup, mouse, vis( "editorup" ) );//viewmode switch
            uisrect( gui.editordown, mouse, vis( "editordown" ) );//tile/obj switch


        }
        if (tilesets.size() != 0 && (kartu == "tile" || kartu == "editor")) {
            uisrect( gui.pickertool1, mouse, null );
            uisrect( gui.pickertool2, mouse, null );
            uisrect( gui.pickertool3, mouse, null );
            uisrect( gui.pickertool5, mouse, null );
            uisrect( gui.pickerback, mouse, null );//tool switch


            if (issettingtile || kartu == "editor") {
                uisrect( gui.tilewrite, mouse, null );
                uisrect( gui.tilesettings, mouse, new Color( 1f, 1f, 0f, .4f ) );
                if (somethingisselected() || kartu == "editor") {
                    uisrect( gui.tileproperties, mouse, null );
                    uisrect( gui.tileremove, mouse, null );
                    uisrect( gui.tileadd, mouse, null );//tool switch
                    uisrect( gui.tileoverlay, mouse, null );
                }
            } else {
                uisrect( gui.tilewrite, mouse, new Color( 1f, 1f, 0f, .4f ) );
                uisrect( gui.tilesettings, mouse, null );
            }

        }
        uis.end();
        ui.setProjectionMatrix( uicam.combined );
        ui.begin();
        //str1.draw(ui, debugYou, 15, -50);
		/*
		 for (int n =0;n < animations.size();n++)
		 {
		 if (animations.get(n).getFrameIDs().size() > 0)
		 {
		 str1.draw(ui,""+animations.get(n).getFrameIDs().get(animations.get(n).getActiveFrameID()) , 15, -30);

		 }
		 }*/

        if (tilesets.size() == 0) {
            //str1draw(ui, "Tileset is empty.",0,100,50);
            str1draw( ui, z.addnew + " " + z.tileset, gui.tilesetsmid );
            uidrawbutton( txundo, z.back, gui.pickerback, 3 );

        } else {

            if (kartu == "tile" || kartu == "editor") {


                if (landscape) {
                    str1.getData().setScale( .7f );
                } else {
                    str1.getData().setScale( 1f );
                }

                if (kartu == "editor") {
                    uidrawbutton( txmap, z.edit, gui.editormode, 1 );
                    uidrawbutton( txundo, z.cancel, gui.editorcancel, 1 );
                    uidrawbutton( txsave, z.save, gui.editorsave, 1 );
                    uidrawbutton( txLeft, "-", gui.editorleft, 1 );
                    uidrawbutton( txRight, "+", gui.editorright, 1 );
                    uidrawbutton( txDown, "+", gui.editorup, 1 );
                    uidrawbutton( txUp, "-", gui.editordown, 1 );
                    uidrawbutton( txTypeImage, z.autotile, gui.tilemode, 3 );


                }
                uidrawbutton( txundo, z.back, gui.pickerback, 3 );
                //modeltekwan

                if (tilesets.get( seltset ).getTerrains().size() > 0) {
                    if (pickAuto) {
                        uidrawbutton( txTypeImage, z.tiles, gui.tilemode, 3 );
                    } else {
                        uidrawbutton( txTypeImage, z.autotile, gui.tilemode, 3 );
                    }
                }

                uidrawbutton( txLeft, "", gui.tilesetsleft, 1 );
                str1draw( ui, tilesets.get( seltset ).getName(), gui.tilesetsmid );
                uidrawbutton( txRight, "", gui.tilesetsright, 1 );

                uidrawbutton( txadd, z.tileset, gui.pickertool1, 2 );
                uidrawbutton( txinfo, z.properties, gui.pickertool2, 2 );
                uidrawbutton( txtiles, z.tiles, gui.pickertool3, 2 );
                uidrawbutton( txdelete, z.remove, gui.pickertool5, 2 );

                uidrawbutton( txpencil, z.tilepicker, gui.tilewrite, 2 );
                uidrawbutton( txtiles, z.edit, gui.tilesettings, 2 );
                if (issettingtile || kartu == "editor") {
                    if (somethingisselected() || kartu == "editor") {
                        uidrawbutton( txinfo, z.info, gui.tileproperties, 2 );
                        uidrawbutton( txeraser, z.remove, gui.tileremove, 2 );
                        uidrawbutton( txadd, z.addnew, gui.tileadd, 2 );
                        String txt = "";
                        int totalview = 0;
                        for (tile tt : tilesets.get( seltset ).getTiles()) {
                            if (tt.getTileID() == curspr - tilesets.get( seltset ).getFirstgid()) {
                                if (tt.getProperties() == null) continue;
                                for (property pp : tt.getProperties()) {
                                    if (totalview < 5) {
                                        txt += pp.getName() + " : " + pp.getValue() + "\n";
                                        totalview += 1;
                                    }
                                }
                                str1draw( ui, txt, gui.tileoverlay, 26 );
                            }
                        }

                    }
                }


            } else if (kartu == "pickanim") {
                uidrawbutton( txundo, z.back, gui.pickerback, 3 );
                switch (tilePicker) {
                    case "props":
                    case "rnda":
                    case "rndb":
                    case "repa":
                    case "repb":
                    case "sw1":
                    case "sw2":
                    case "sw3":
                    case "sw4":
                    case "sw5":
                    case "sw6":

                        uidrawbutton( txundo, "", gui.tilesetsleft, 1 );
                        str1draw( ui, tilesets.get( seltset ).getName(), gui.tilesetsmid );
                        uidrawbutton( txredo, "", gui.tilesetsright, 1 );


                        break;
                    case "newimgobj":
                        uidrawbutton( txundo, "", gui.tilesetsleft, 1 );
                        str1draw( ui, tilesets.get( seltset ).getName(), gui.tilesetsmid );
                        uidrawbutton( txredo, "", gui.tilesetsright, 1 );


                        break;
                    case "terraineditor":
                        if (tilesets.get( seltset ).getTerrains().size() > 0)
                            str1draw( ui, "Terrain: " + tilesets.get( seltset ).getTerrains().get( tilesets.get( seltset ).getSelTerrain() ).getName(), gui.tilesetsmid );
                        uidrawbutton( txadd, z.addnew, gui.newterrain, 3 );

                        break;
                    case "massprops":
                        str1draw( ui, z.tileset + ": " + tilesets.get( selTsetID ).getName(), gui.tilesetsmid );
                        //str1draw(ui, z.ok, gui.newterrain);
                        uidrawbutton( txsave, z.ok, gui.newterrain, 3 );
                        break;

                    default:
                        str1draw( ui, z.tileset + ": " + tilesets.get( selTsetID ).getName(), gui.tilesetsmid );

                        break;
                }
            }
        }
        str1.setColor( 1, 1, 1, 1 );
        ui.end();
        Gdx.gl.glDisable( GL20.GL_BLEND );
    }

    boolean pickAuto = false;

    private void drawGrid() {
        if (cammode != "View only") {
            //GRID IN MAIN VIEW
            if (sEnableBlending) {
                Gdx.gl.glEnable( GL20.GL_BLEND );
                Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

            }
            sr.setProjectionMatrix( cam.combined );
            sr.begin( ShapeRenderer.ShapeType.Filled );

            //red box
            if ((roll && activetool != 4) || (assemblymode && assemblynum!=-1)) {

                sr.setColor( 1, 0, 0, 0.5f );
                if (assemblying) {
                    sr.setColor( 0, 0, 1, 0.5f );
                    assemblying=false;
                }
                int SprW = Tw;
                if (orientation.equalsIgnoreCase( "isometric" )) {
                    int offsetx = 0, offsety = 0;
                    xpos = mapstartSelect % Tw;
                    ypos = mapstartSelect / Tw;
                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                    int xpos2 = mapendSelect % Tw;
                    int ypos2 = mapendSelect / Tw;
                    int offsetx2 = (xpos2 * Tsw / 2) + (ypos2 * Tsw / 2);
                    int offsety2 = (xpos2 * Tsh / 2) - (ypos2 * Tsh / 2);
                    int offsetx3 = (xpos2 * Tsw / 2) + (ypos * Tsw / 2);
                    int offsety3 = (xpos2 * Tsh / 2) - (ypos * Tsh / 2);
                    int offsetx4 = (xpos * Tsw / 2) + (ypos2 * Tsw / 2);
                    int offsety4 = (xpos * Tsh / 2) - (ypos2 * Tsh / 2);
                    sr.polygon( new float[]{xpos * Tsw + (Tsw / 2) - offsetx, -ypos * Tsh + Tsh - offsety, xpos2 * Tsw + Tsw - offsetx3, -ypos * Tsh + (Tsh / 2) - offsety3, (xpos2 * Tsw) + (Tsw / 2) - offsetx2, -ypos2 * Tsh - offsety2, xpos * Tsw - offsetx4, -ypos2 * Tsh + (Tsh / 2) - offsety4} );
                    //sr.rect((mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh);

                } else if (orientation.equalsIgnoreCase( "orthogonal" )) {
                    if (activetool == 0) {
                        switch (currentShape) {
                            case RECTANGLE: case CIRCLE:
                                sr.rect( (mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh );
                                break;
                                /*
                            case CIRCLE:
                                xpos = mapstartSelect % Tw;
                                ypos = mapstartSelect / Tw;
                                int xpos2 = mapendSelect % Tw;
                                int ypos2 = mapendSelect / Tw;
                                int widih = xpos2 - xpos;
                                int heih = ypos2 - ypos;
                                float radi = widih > heih ? heih : widih;
                                radi = radi * Tsw / 2f;
                                if (widih > heih) {
                                    sr.circle( (xpos * Tsw) + radi - radi / 2f + widih * Tsw / 2f - Tsw, -ypos * Tsh + Tsh - radi, radi );
                                } else {
                                    sr.circle( (xpos * Tsw) + radi, -ypos * Tsh + Tsh - radi + radi / 2f - heih * Tsh / 2f + Tsh, radi );
                                }
                                // float widihh = (((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw)/2f;
                                //sr.circle((mapstartSelect % SprW * Tsw)+radi, -(mapstartSelect / SprW) * Tsh + Tsh-radi, radi);
                                break;

                                 */
                            case LINE:
                                if (rising) {
                                    sr.rectLine( (mapstartSelect % SprW) * Tsw + Tsw / 2f, -(mapendSelect / SprW) * Tsh + Tsh / 2f, ((mapendSelect % SprW) * Tsw + Tsw / 2f), -(mapstartSelect / SprW) * Tsh + Tsh / 2f, Tsw / 2f );
                                } else {
                                    sr.rectLine( (mapstartSelect % SprW) * Tsw + Tsw / 2f, -(mapstartSelect / SprW) * Tsh + Tsh / 2f, ((mapendSelect % SprW) * Tsw + Tsw / 2f), -(mapendSelect / SprW) * Tsh + Tsh / 2f, Tsw / 2f );
                                }
                                break;
                        }


                    } else {
                        sr.rect( (mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh );
                    }
                }
                sr.setColor( 0, 0, 0, 1 );
            }

            Gdx.gl20.glLineWidth( 1 );//average
            sr.setColor( 0, 0, 0, gridOpacity / 10f );

            int offsetx = 0, offsety = 0;
            if (orientation.equalsIgnoreCase( "isometric" )) {
                offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
            }

            for (int i = 0; i <= Tw; i++)//vertical
            {

                if (orientation.equalsIgnoreCase( "isometric" )) {
                    if (sShowGrid)
                        sr.rectLine( Tsw / 2 + i * Tsw / 2, -i * Tsh / 2 + Tsh, Tsw / 2 + i * Tsw / 2 - Tsw * Th / 2, -i * Tsh / 2 - Tsh * Th / 2 + Tsh, Tsw / 32f );
                    //sr.rectLine(Tsw / 2 + i * Tsw / 2, -i * Tsh / 2 + Tsh, Tsw / 2 + i * Tsw / 2 - Tsw * Tw / 2, -i * Tsh / 2 - Tsh * Th / 2 + Tsh, Tsw / 16f);
                } else {
                    if (!landscape) {
                        if (i * Tsw + Tsw < cam.position.x - redux * cam.zoom) continue;
                        if (i * Tsw > cam.position.x + redux * cam.zoom) continue;
                    }

                    if (sShowGrid || i == 0 || i == Tw) {
                        sr.rectLine( (Tsw * i), Tsh, (i * Tsw), -Tsh * Th + Tsh, Tsw / 16f );
                    }


                }
            }
            for (int j = -1; j < Th; j++)//horizontal
            {

                if (orientation.equalsIgnoreCase( "isometric" )) {
                    if (sShowGrid)
                        sr.rectLine( 0 - j * Tsw / 2, Tsh / 2 - (Tsh * j / 2), (0 - j * Tsw / 2) + Tsw * Tw / 2, Tsh / 2 - (Tsh * j / 2) - Tsh * Tw / 2, Tsw / 32f );
                } else {

                    if (-Tsh * j + Tsh < cam.position.y - reduy * cam.zoom) continue;
                    if ((-Tsh * j) - 2 * Tsh > cam.position.y + (reduy + Tsh) * cam.zoom) continue;

                    if (sShowGrid || j == -1 || j == Th - 1) {
                        sr.rectLine( 0, -(Tsh * j), Tsw * Tw, -(j * Tsh), Tsw / 16f );
                    }
                }
            }


            boolean ifmusic = false;
            for (property p : properties) {
                if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "NotTiled music" )) {
                    ifmusic = true;
                    break;
                }

            }


            if (sShowCustomGrid) {
                sr.setColor( 1, 0, 0, 0.5f );
                offsetx = 0;
                offsety = 0;
                if (orientation.equalsIgnoreCase( "isometric" )) {
                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                }

                for (int i = 0; i <= Tw; i++)//vertical
                {


                    if (i % sGridX == 0) {

                        if (orientation.equalsIgnoreCase( "isometric" )) {
                            sr.rectLine( Tsw / 2 + i * Tsw / 2, -i * Tsh / 2 + Tsh, Tsw / 2 + i * Tsw / 2 - Tsw * Th / 2, -i * Tsh / 2 - Tsh * Th / 2 + Tsh, Tsw / 16f );
                        } else {
                            if (i * Tsw + Tsw < cam.position.x - ssx * cam.zoom) continue;
                            if (i * Tsw > cam.position.x + ssx * cam.zoom) continue;

                            sr.rectLine( (Tsw * i), Tsh, (i * Tsw), -Tsh * Th + Tsh, Tsw / 8f );


                        }
                    }
                }
                for (int j = -1; j < Th; j++)//horizontal
                {

                    if ((j + 1) % sGridY == 0) {

                        if (orientation.equalsIgnoreCase( "isometric" )) {
                            sr.rectLine( 0 - j * Tsw / 2, Tsh / 2 - (Tsh * j / 2), (0 - j * Tsw / 2) + Tsw * Tw / 2, Tsh / 2 - (Tsh * j / 2) - Tsh * Tw / 2, Tsw / 16f );
                        } else {
                            if (-Tsh * j + Tsh < cam.position.y - ssy * cam.zoom) continue;
                            if (-Tsh * j > cam.position.y + ssy * cam.zoom) continue;

                            sr.rectLine( 0, -(Tsh * j), Tsw * Tw, -(j * Tsh), Tsw / 8f );
                        }
                    }
                }
            }


            if (activetool == 3 || (assemblymode && assemblynum!=-1)) {
                sr.setColor( 0, 1, 0, 1f );


                if (orientation.equalsIgnoreCase( "isometric" )) {
                    offsetx = 0;
                    offsety = 0;
                    xpos = mapstartSelect % Tw;
                    ypos = mapstartSelect / Tw;
                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                    int xpos2 = mapendSelect % Tw;
                    int ypos2 = mapendSelect / Tw;
                    int offsetx2 = (xpos2 * Tsw / 2) + (ypos2 * Tsw / 2);
                    int offsety2 = (xpos2 * Tsh / 2) - (ypos2 * Tsh / 2);
                    int offsetx3 = (xpos2 * Tsw / 2) + (ypos * Tsw / 2);
                    int offsety3 = (xpos2 * Tsh / 2) - (ypos * Tsh / 2);
                    int offsetx4 = (xpos * Tsw / 2) + (ypos2 * Tsw / 2);
                    int offsety4 = (xpos * Tsh / 2) - (ypos2 * Tsh / 2);
                    float p1 = xpos * Tsw + (Tsw / 2) - offsetx;
                    float p2 = -ypos * Tsh + Tsh - offsety;
                    float p3 = xpos2 * Tsw + Tsw - offsetx3;
                    float p4 = -ypos * Tsh + (Tsh / 2) - offsety3;
                    float p5 = (xpos2 * Tsw) + (Tsw / 2) - offsetx2;
                    float p6 = -ypos2 * Tsh - offsety2;
                    float p7 = xpos * Tsw - offsetx4;
                    float p8 = -ypos2 * Tsh + (Tsh / 2) - offsety4;


                    sr.rectLine( p1, p2, p3, p4, Tsw / 16f );
                    sr.rectLine( p3, p4, p5, p6, Tsw / 16f );
                    sr.rectLine( p5, p6, p7, p8, Tsw / 16f );
                    sr.rectLine( p7, p8, p1, p2, Tsw / 16f );

                    //sr.rect((mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh);

                } else if (orientation.equalsIgnoreCase( "orthogonal" )) {
                    int eiks = (mapstartSelect % Tw) * Tsw;
                    int yeh = -(mapstartSelect / Tw) * Tsh + Tsh;
                    int widih = (mapendSelect % Tw) * Tsw + Tsw;
                    int heih = -(mapendSelect / Tw) * Tsh;
                    sr.rectLine( eiks, yeh, eiks, heih, Tsw / 16f );
                    sr.rectLine( widih, yeh, widih, heih, Tsw / 16f );
                    sr.rectLine( eiks, heih, widih, heih, Tsw / 16f );
                    sr.rectLine( eiks, yeh, widih, yeh, Tsw / 16f );
                    //sr.rect(eiks,yeh,widih,heih);
                }


            }
            sr.setColor( 0, 0, 0, 0.5f );

            sr.end();
            if (sEnableBlending) {
                Gdx.gl.glDisable( GL20.GL_BLEND );
            }
        }
    }

    private void drawTiles() {
        if (landscape) {
            redux = ssy / 2;
            reduy = ssx / 2;
        } else {
            redux = ssx / 2;
            reduy = ssy / 2;
        }

        if (orientation.equalsIgnoreCase( "isometric" )) {
            //Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.setColor( 1, 1, 1, 1 );
            batch.setProjectionMatrix( cam.combined );
            batch.begin();
            try {
                batch.draw( background, 0, -Tsh * Th + Tsh, Tsw * Tw, Tsh * Th, 0, 0, background.getWidth(), background.getHeight(), false, false );
            } catch (Exception e) {
            }
            camA = cam.position.x - ssx * cam.zoom;
            camB = cam.position.x + ssx * cam.zoom;
            camC = cam.position.y - ssy * cam.zoom;
            camD = cam.position.y + ssy * cam.zoom;

            tilesetsize = tilesets.size();
            if (tilesetsize > 0 && !loadingfile) {
                int offsetx = 0, offsety = 0;
                int jon = 0, joni = 0;
                long ini;
                int total = Tw * Th;
                int startx = 0, stopx = Tw;
                int starty = 0, stopy = Th;
                //no optimization for iso map yet
                if (orientation.equalsIgnoreCase( "orthogonal" )) {


                    if (landscape) {
                        redux = ssy / 2;
                        reduy = ssx / 2;
                    }
                    for (int i = 0; i <= Tw; i++)//vertical
                    {

                        if (i * Tsw < cam.position.x - redux * cam.zoom) {
                            startx = i;
                            continue;
                        }

                        if (i * Tsw > cam.position.x + redux * cam.zoom) {
                            stopx = i;
                            break;
                        }

                    }
                    for (int j = 0; j < Th; j++)//horizontal
                    {

                        if ((-Tsh * j) + Tsh < cam.position.y - reduy * cam.zoom) {
                            stopy = j;
                            break;
                        }
                        //starts from

                        if ((-Tsh * j) - 2 * Tsh > cam.position.y + ((reduy + Tsh) * cam.zoom)) {
                            starty = j;
                            continue;
                        }

                    }
                }
                int aa = 0, bb = 0, cc = 0, dd = 0;
                switch (renderorder) {
                    case "right-down":
                        aa = starty;
                        bb = stopy;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-down":
                        aa = starty;
                        bb = stopy;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                    case "right-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                }


                String flag;
                Long mm = null;
                flag = "00";

                for (int jo = 0; jo < layers.size(); jo++) {
                    boolean isShown = false;
                    switch (viewMode) {
                        case ALL:
                            isShown = true;
                            break;
                        case STACK:
                            if (jo <= selLayer) isShown = true;
                            break;
                        case SINGLE:
                            if (jo == selLayer) isShown = true;
                            break;
                        case CUSTOM:
                            if (layers.get( jo ).isVisible()) isShown = true;
                            break;
                    }
                    if (layers.get( jo ).getType() == layer.Type.TILE && isShown) {
                        if (layers.get( jo ).getOpacity() != 0 && sEnableBlending) {
                            Gdx.gl.glEnable( GL20.GL_BLEND );
                            Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
                            batch.setColor( 1, 1, 1, layers.get( jo ).getOpacity() );
                        }
                        drawers.clear();
                        for (int a = aa; a < bb; a++) {
                            for (int b = cc; b < dd; b++) {
                                //position=(Math.abs(a)*Tw)+Math.abs(b);

                                position = (abs( a ) * Tw) + abs( b );
                                ini = layers.get( jo ).getStr().get( position );
                                initset = layers.get( jo ).getTset().get( position );
                                if (initset == -1) continue;
                                if (ini == 0) continue;//dont draw empty, amazing performance boost
                                xpos = position % Tw;
                                ypos = position / Tw;
                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                }

                                mm = ini;
                                flag = "00";
                                if (ini > total) {
                                    hex = Long.toHexString( ini );
                                    trailer = "00000000" + hex;
                                    hex = trailer.substring( trailer.length() - 8 );
                                    flag = hex.substring( 0, 2 );
                                    mm = Long.decode( "#00" + hex.substring( 2, 8 ) );
                                }
                                tiles = tilesets.get( initset ).getTiles();
                                tilesize = tiles.size();

                                if (tilesize > 0) {
                                    for (int n = 0; n < tilesize; n++) {
                                        if (tiles.get( n ).getAnimation().size() > 0) {
                                            if (mm == tiles.get( n ).getTileID() + tilesets.get( initset ).getFirstgid()) {
                                                mm = (long) tiles.get( n ).getActiveFrameID() + tilesets.get( initset ).getFirstgid();
                                            }
                                        }
                                    }
                                }

                                sprX = (int) (mm - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                                sprY = (int) (mm - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                                margin = tilesets.get( initset ).getMargin();
                                spacing = tilesets.get( initset ).getSpacing();
                                Tswa = tilesets.get( initset ).getTilewidth();
                                Tsha = tilesets.get( initset ).getTileheight();

                                tempdrawer = new drawer();
                                tempdrawer.mm = mm;
                                int Tswad = 0;
                                int Tshad = 0;
                                if (sResizeTiles) {
                                    Tswad = Tsw;
                                    Tshad = Tsh;
                                } else {
                                    Tswad = Tswa;
                                    Tshad = Tsha;
                                }
                                float ttx = -(Tswad / 2) + (Tsw / 2);
                                float tty = -(Tshad / 2) + (Tsh / 2);

                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    tty = 0;
                                    ttx = 0;
                                }


                                switch (flag) {
                                    case "20"://diagonal flip 'THIS ONE"
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "40"://flipy nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true );
                                        break;
                                    case "60"://270 degrees clockwise nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "80"://flipx nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "a0"://90 degress cw
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "c0"://180 degrees cw nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "e0"://180 degrees ccw "AND THIS ONE"
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "00":
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                }
                                drawers.add( tempdrawer );
                            } //for  b
                        }//for a

                        for (drawer d : drawers){
                            d.draw(batch,tilesets.get(d.initset).getTexture() );
                        }

                        drawCoordinates();

                        if (layers.get( jo ).getOpacity() != 0 && sEnableBlending) {
                            Gdx.gl.glDisable( GL20.GL_BLEND );
                            batch.setColor( 1, 1, 1, 1 );
                        }
                    } else if (layers.get( jo ).getType() == layer.Type.IMAGE && isShown) {
                        layer lay = layers.get( jo );
                        if (lay.getTexture() != null) {
                            try {
                                if (lay.getOpacity() != 0f) {
                                    batch.setColor( 1f, 1f, 1f, lay.getOpacity() );
                                    batch.draw( lay.getTexture(), lay.getOffsetX(), -lay.getImageheight() - lay.getOffsetY() + Tsh );
                                    batch.setColor( 1f, 1f, 1f, 1 );

                                } else {
                                    batch.draw( lay.getTexture(), lay.getOffsetX(), -lay.getImageheight() - lay.getOffsetY() + Tsh );

                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }//for jo


            }//no tileswt

            batch.end();


            //orthogonal
        } else {

            //fbo.begin();
            batch.begin();
            try {

                batch.draw( background, 0, -Tsh * Th + Tsh, Tsw * Tw, Tsh * Th, 0, 0, background.getWidth(), background.getHeight(), false, false );
            } catch (Exception e) {
            }
            batch.end();
            if (!caching) {
                for (int i = 0; i < tcaches.size(); i++) {
                    TileCache tc = tcaches.get( i );

                    if (!orientation.equalsIgnoreCase( "isometric" )) {


                        if ((tc.getIntex()) * widd * Tsw < cam.position.x - redux * cam.zoom - widd * Tsw)
                            continue;

                        if ((tc.getIntex()) * widd * Tsw > cam.position.x + redux * cam.zoom + widd * Tsw)
                            continue;

                        if ((tc.getIntey()) * heii * -Tsh + Tsh < cam.position.y - reduy * cam.zoom - heii * Tsh)
                            continue;

                        if ((tc.getIntey()) * heii * -Tsh + Tsh > cam.position.y + reduy * cam.zoom + heii * Tsh)
                            continue;
                    }


                    SpriteCache cache = tc.getCache();
                    int myid = tc.getCacheID();
                    cache.setProjectionMatrix( cam.combined );
                    Gdx.gl.glEnable( GL20.GL_BLEND );
                    Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

                    cache.begin();
                    cache.draw( myid ); //call our cache with cache ID and draw it
                    cache.end();

                    //////////
                    //draw preview of macro tiles
                    if (assemblymode && assemblynum!=-1) {
                        drawers.clear();
                        xpos = (assemblynum % Tw);
                        ypos = (assemblynum / Tw);

                        int lox=xpos ; int loy=ypos;
                        int hix=xpos ; int hiy=ypos;

                        for (property p : autotiles.get( assemblyid ).getProperties()) {

                            if (p.getName().contains( "," )) {
                                String[] ass = p.getName().split( "," );
                                int axpos = Integer.parseInt( ass[0] );
                                int aypos = Integer.parseInt( ass[1] );
                                int aspr = Integer.parseInt( p.getValue() );
                                int atst = getTsetFromSpr( aspr );

                                //////
                                int offsetx=0; int offsety=0;

                                position = assemblynum;
                                int ini = aspr;
                                int initset = atst;
                                xpos = (position % Tw)+axpos;
                                ypos = (position / Tw)+aypos;
                                if (xpos<lox) lox=xpos;
                                if (ypos<loy) loy=ypos;
                                if (xpos>hix) hix=xpos;
                                if (ypos>hiy) hiy=ypos;
                                if (initset == -1) continue;
                                if (ini == 0) continue;//dont draw empty, amazing performance boost

                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                }

                                long mm = ini;
                                String flag = "00";
                                if (ini > Tw*Th) {
                                    hex = Long.toHexString( ini );
                                    trailer = "00000000" + hex;
                                    hex = trailer.substring( trailer.length() - 8 );
                                    flag = hex.substring( 0, 2 );
                                    mm = Long.decode( "#00" + hex.substring( 2, 8 ) );
                                }
                                tiles = tilesets.get( initset ).getTiles();
                                tilesize = tiles.size();

                                if (tilesize > 0) {
                                    for (int n = 0; n < tilesize; n++) {
                                        if (tiles.get( n ).getAnimation().size() > 0) {
                                            if (mm == tiles.get( n ).getTileID() + tilesets.get( initset ).getFirstgid()) {
                                                mm = (long) tiles.get( n ).getActiveFrameID() + tilesets.get( initset ).getFirstgid();
                                            }
                                        }
                                    }
                                }

                                sprX = (int) (mm - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                                sprY = (int) (mm - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                                margin = tilesets.get( initset ).getMargin();
                                spacing = tilesets.get( initset ).getSpacing();
                                Tswa = tilesets.get( initset ).getTilewidth();
                                Tsha = tilesets.get( initset ).getTileheight();

                                tempdrawer = new drawer();
                                tempdrawer.mm = mm;
                                int Tswad = 0;
                                int Tshad = 0;
                                if (sResizeTiles) {
                                    Tswad = Tsw;
                                    Tshad = Tsh;
                                } else {
                                    Tswad = Tswa;
                                    Tshad = Tsha;
                                }
                                float ttx = -(Tswad / 2) + (Tsw / 2);
                                float tty = -(Tshad / 2) + (Tsh / 2);

                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    tty = 0;
                                    ttx = 0;
                                }


                                switch (flag) {
                                    case "20"://diagonal flip 'THIS ONE"
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "40"://flipy nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true );
                                        break;
                                    case "60"://270 degrees clockwise nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "80"://flipx nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "a0"://90 degress cw
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "c0"://180 degrees cw nd
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "e0"://180 degrees ccw "AND THIS ONE"
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "00":
                                        tempdrawer.setdrawer( initset, xpos * Tsw + ttx - offsetx, -ypos * Tsh + tty - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                }
                                drawers.add( tempdrawer );
                                //////

                            } //only ones that has comma
                        } //for each property
                        //int newpos = (ypos *Tw) + xpos;

                        mapstartSelect=(loy*Tw)+lox;
                        mapendSelect = (hiy*Tw)+hix;

                    } //isassembly

                    //////////

                }

                batch.begin();
                if (assemblymode && assemblynum!=-1) {
                    for (drawer d : drawers) {
                        d.draw( batch, tilesets.get( d.initset ).getTexture() );
                    }
                }
                drawCoordinates();
                batch.end();
            }


            //Drawing of RW thingy, but it cause a noticable lag, so no.
            if (cam.zoom>zoomTreshold) {
                batch.setProjectionMatrix( cam.combined );
                batch.begin();

                drawCoordinates();

                if (layers.size() > 2 && tilesets.size() > 0) {
                    if (layers.get( 2 ).getName().equalsIgnoreCase( "Units" )) {

                        for (int aa = 0; aa < Tw * Th; aa++) {

                            long mm = layers.get( 2 ).getStr().get( aa );
                            int mmo = layers.get( 2 ).getTset().get( aa );
                            if (mmo == -1) continue;
                            int xpos = aa % Tw;
                            int ypos = aa / Tw;
                            int maex = -1, maye = -1;
                            for (tile t : tilesets.get( mmo ).getTiles()) {
                                //log(mm+"P");

                                if (t.getTileID() + tilesets.get( mmo ).getFirstgid() == mm) {
                                    boolean isCC = false;
                                    int team = -1;
                                    for (property p : t.getProperties()) {
                                        if (p.getName().equalsIgnoreCase( "unit" ) && p.getValue().equalsIgnoreCase( "commandCenter" )) {
                                            isCC = true;
                                            //log(isCC+"P");
                                        }
                                        if (p.getName().equalsIgnoreCase( "team" )) {
                                            team = Integer.parseInt( p.getValue() );
                                        }
                                    }
                                    if (!isCC) continue;
                                    //log(isCC+"");
                                    switch (team) {
                                        case 0:
                                            maex = 0;
                                            maye = 0;
                                            break;
                                        case 1:
                                            maex = 1 * 56;
                                            maye = 0;
                                            break;
                                        case 2:
                                            maex = 2 * 56;
                                            maye = 0;
                                            break;
                                        case 3:
                                            maex = 3 * 56;
                                            maye = 0;
                                            break;
                                        case 4:
                                            maex = 4 * 56;
                                            maye = 0;
                                            break;
                                        case 5:
                                            maex = 0;
                                            maye = 56;
                                            break;
                                        case 6:
                                            maex = 1 * 56;
                                            maye = 56;
                                            break;
                                        case 7:
                                            maex = 2 * 56;
                                            maye = 56;
                                            break;
                                        case 8:
                                            maex = 3 * 56;
                                            maye = 56;
                                            break;
                                        case 9:
                                            maex = 4 * 56;
                                            maye = 56;
                                            break;

                                    }
                                }
                            }

                            if (maex != -1) {
                                drawer tempdrawer2 = new drawer();
                                int widthy = (int) (cam.zoom * 60);
                                tempdrawer2.setdrawer( 0, xpos * Tsw - widthy / 2, -ypos * Tsh - widthy / 2, Tsw / 2, Tsh / 2, widthy, widthy, 1f, 1f, 0f, maex, maye, 55, 55, false, false );
                                tempdrawer2.draw( batch, txnumbers );
                            }

                            mm = layers.get( 1 ).getStr().get( aa );
                            if (mm == 284) {
                                drawer tempdrawer2 = new drawer();
                                int widthy = (int) (cam.zoom * 30);
                                tempdrawer2.setdrawer( 0, xpos * Tsw - widthy / 2, -ypos * Tsh - widthy / 2, Tsw / 2, Tsh / 2, widthy, widthy, 1f, 1f, 0f, 0, 0, 32, 32, false, false );
                                tempdrawer2.draw( batch, txresources );
                            }
                        }//for

                    }//if
                    if (layers.get( 1 ).getName().equalsIgnoreCase( "Items" )) {

                        for (int aa = 0; aa < Tw * Th; aa++) {

                            long mm = layers.get( 1 ).getStr().get( aa );
                            int mmo = layers.get( 1 ).getTset().get( aa );
                            if (mmo == -1) continue;
                            int xpos = aa % Tw;
                            int ypos = aa / Tw;
                            boolean isPool = false;
                            for (tile t : tilesets.get( mmo ).getTiles()) {

                                if (t.getTileID() + tilesets.get( mmo ).getFirstgid() == mm) {

                                    for (property p : t.getProperties()) {
                                        if (p.getName().equalsIgnoreCase( "res_pool" )) {
                                            isPool = true;
                                        }
                                    }
                                    if (!isPool) continue;
                                }
                            }
                            if (isPool) {
                                drawer tempdrawer2 = new drawer();
                                int widthy = (int) (cam.zoom * 30);
                                tempdrawer2.setdrawer( 0, xpos * Tsw - widthy / 2, -ypos * Tsh - widthy / 2, Tsw / 2, Tsh / 2, widthy, widthy, 1f, 1f, 0f, 0, 0, 32, 32, false, false );
                                tempdrawer2.draw( batch, txresources );
                            }

                        }//for

                    }//if
                }// if
                batch.end();
            }

        }
    }

    private int abs(int i) {
        return (i + (i >> 31)) ^ (i >> 31);
    }

    private int mod(int i, int len) {
        return ((i < len) ? i : 0);
    }


    int widd;
    int heii;
    int buffersz;

    private void resetCaches() {
        //This thing is slow.
        try {
            //jangan dihapus, karena valuenya bakal di set lagi di bawah.
            widd=50;
            heii=50;
            buffersz=8191; //2500 per layer, 3 full layer kebangetan.
            if (widd > Tw) widd = Tw; //kode asu
            if (heii > Th) heii = Th; //temennya

            log( "resetting cache...");
                tcaches.clear();
            int maxx = Tw / widd;
            if (Tw % widd != 0) maxx++;
            //maxx=1;
            int maxy = Th / heii;
            if (Th % heii != 0) maxy++;

            SpriteCache cache;
            TileCache tcache;

            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    cache = new SpriteCache( buffersz, true );//8191max
                    int cid = cacheTilesOn( cache, x, y );
                    tcache = new TileCache( cache, cid, x, y );
                    tcaches.add( tcache );

                }
            }
            isupdatingcache=true;
            log( "resetting cache ok");
            log( "updating AT" );
            updateAT();
            log( "updating AT OK" );
            //status("Cache reset.",2);
        }catch(Exception e){
            log("error cache:");
            e.printStackTrace();
        }
    }

    boolean isupdatingcache=false;
    private void updateCache(int num) {
        //// magic
        int posx = (num % Tw) / widd;
        int posy = (num / Tw) / heii;
        int maxx = Tw / widd;
        if (Tw % widd != 0) maxx++;
        int tci = (posy * maxx) + posx;
        tcaches.get( tci ).setChanged( true );
        isupdatingcache = true;

        ////
    }

    private void cacheTiles() {

        caching = true;
        SpriteCache cache;
        for (int i = 0; i < tcaches.size(); i++) {
            if (tcaches.get( i ).isChanged()) {
               // tcaches.get( i ).getCache().dispose();
                TileCache tc = tcaches.get( i );
                cache = new SpriteCache( buffersz, true );//max8191indice
                int cid = cacheTilesOn( cache, tc.getIntex(), tc.getIntey() );
                tc.setCache( cache );
                tc.setCacheID( cid );
                tc.setChanged( false );
            }
        }

        caching = false;
    }

    private int cacheTilesOn(SpriteCache cache, int intex, int intey) {

        tilesetsize = tilesets.size();
//        if (tilesetsize > 0) {
        int offsetx = 0, offsety = 0;
        int jon = 0, joni = 0;
        long ini;
        int total = Tw * Th;

        int startx = intex * widd, stopx = intex * widd + widd;
        if (stopx > Tw) stopx = intex * widd + (Tw % widd);
        int starty = intey * heii, stopy = intey * heii + heii;
        if (stopy > Th) stopy = intey * heii + (Th % heii);
        int aa = 0, bb = 0, cc = 0, dd = 0;
        switch (renderorder) {
            case "right-down":
                aa = starty;
                bb = stopy;
                cc = startx;
                dd = stopx;
                break;
            case "left-down":
                aa = starty;
                bb = stopy;
                cc = -stopx + 1;
                dd = -startx + 1;
                break;
            case "right-up":
                aa = -stopy + 1;
                bb = -starty + 1;
                cc = startx;
                dd = stopx;
                break;
            case "left-up":
                aa = -stopy + 1;
                bb = -starty + 1;
                cc = -stopx + 1;
                dd = -startx + 1;
                break;
        }

        String flag;
        Long mm = null;
        flag = "00";

        cache.beginCache();

        for (int jo = 0; jo < layers.size(); jo++) {
            //check visibility
            boolean vis = false;
            switch (viewMode) {
                case STACK:
                    if (jo <= selLayer) vis = true;
                    break;
                case ALL:
                    vis = true;
                    break;
                case SINGLE:
                    if (selLayer == jo) vis = true;
                    break;
                case CUSTOM:
                    vis = layers.get( jo ).isVisible();
                    break;
            }

            if (layers.get( jo ).getType() == layer.Type.TILE && vis) {
                if (layers.get( jo ).getOpacity() != 0 && sEnableBlending) {
                    Gdx.gl.glEnable( GL20.GL_BLEND );
                    Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
                    batch.setColor( 1, 1, 1, layers.get( jo ).getOpacity() );
                }
                java.util.List<drawer> drawers = new ArrayList<drawer>();
                drawers.clear();
                for (int a = aa; a < bb; a++) {
                    for (int b = cc; b < dd; b++) {
                        //position=(Math.abs(a)*Tw)+Math.abs(b);

                        int position = (abs( a ) * Tw) + abs( b );
                        ini = layers.get( jo ).getStr().get( position );
                        int initset = -1;
                        if (tilesets.size() > 0)
                            initset = layers.get( jo ).getTset().get( position );
                        if (initset == -1) continue;
                        if (ini == 0) continue;//dont draw empty, amazing performance boost
                        int xpos = position % Tw;
                        int ypos = position / Tw;
                        if (orientation.equalsIgnoreCase( "isometric" )) {
                            offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                            offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                        }

                        mm = ini;
                        flag = "00";
                        if (ini > total) {
                            hex = Long.toHexString( ini );
                            trailer = "00000000" + hex;
                            hex = trailer.substring( trailer.length() - 8 );
                            flag = hex.substring( 0, 2 );
                            mm = Long.decode( "#00" + hex.substring( 2, 8 ) );
                        }
                        tiles = tilesets.get( initset ).getTiles();
                        tilesize = tiles.size();

                        //this code is kinda useless, as the animation won't work on cache.
                        /*
                        if (tilesize > 0) {
                            for (int n = 0; n < tilesize; n++) {
                                if (tiles.get( n ).getAnimation().size() > 0) {
                                    if (mm == tiles.get( n ).getTileID() + tilesets.get( initset ).getFirstgid()) {
                                        mm = (long) tiles.get( n ).getActiveFrameID() + tilesets.get( initset ).getFirstgid();
                                    }
                                }
                            }
                        }

                         */

                        sprX = (int) (mm - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                        sprY = (int) (mm - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                        margin = tilesets.get( initset ).getMargin();
                        spacing = tilesets.get( initset ).getSpacing();
                        Tswa = tilesets.get( initset ).getTilewidth();
                        Tsha = tilesets.get( initset ).getTileheight();
                        int Tswad = 0;
                        int Tshad = 0;
                        if (sResizeTiles) {
                            Tswad = Tsw;
                            Tshad = Tsh;
                        } else {
                            Tswad = Tswa;
                            Tshad = Tsha;
                        }
                        drawer tempdrawer = new drawer();
                        switch (flag) {
                            case "20"://diagonal flip 'THIS ONE"
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                break;
                            case "40"://flipy nd
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true );
                                break;
                            case "60"://270 degrees clockwise nd
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                break;
                            case "80"://flipx nd
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                break;
                            case "a0"://90 degress cw
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                break;
                            case "c0"://180 degrees cw nd
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                break;
                            case "e0"://180 degrees ccw "AND THIS ONE"
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                break;
                            case "00":
                                tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, -ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                break;

                        }

                        drawers.add( tempdrawer );


                    } //for  b
                }//for a

                //java.util.Collections.sort(drawers);//fps hogger
                int counting = 0;
                for (drawer drawer : drawers) {

                    counting += 1;
                    drawer.add( cache, tilesets );

                        /* cache should be prevented to reach 8000.
                        if (counting == 8000) {
                            int id = cache.endCache();
                            cacheIDs.add(id);
                            caches.add(cache);

                            cache = new SpriteCache(8000, true);

                            cache.beginCache();
                            counting = 0;
                        }
                         */
                }


                if (layers.get( jo ).getOpacity() != 0 && sEnableBlending) {
                    Gdx.gl.glDisable( GL20.GL_BLEND );
                }

            }//for jo
        }
        int id = cache.endCache();
        return id;
    }

    private void drawObjects() {
        sr.setProjectionMatrix( cam.combined );

        //Part one of 2, filled.
        sr.begin( ShapeRenderer.ShapeType.Filled ); //Edit it with filled to make it looks gorgeous.
        Gdx.gl.glEnable( GL20.GL_BLEND );

        Gdx.gl20.glLineWidth( 5 );//average
        sr.setColor( 0.5f, 0.5f, 0.5f, 0.5f ); //blink

        if (layers.size() > 0) {
            for (int i = 0; i < layers.size(); i++) {

                if (i == selLayer) {
                    sr.setColor( 0.5f, 0.7f, 0.5f, 0.5f ); //blink

                } else {
                    sr.setColor( 0.5f, 0.5f, 0.5f, 0.5f ); //blink

                }

                boolean isShown = false;
                switch (viewMode) {
                    case ALL:
                        isShown = true;
                        break;
                    case STACK:
                        if (i <= selLayer) isShown = true;
                        break;
                    case SINGLE:
                        if (i == selLayer) isShown = true;
                        break;
                    case CUSTOM:
                        if (layers.get( i ).isVisible()) isShown = true;
                        break;
                }

                if (layers.get( i ).getType() == layer.Type.OBJECT && isShown && layers.get( i ).getObjects().size() > 0) {

                    for (int j = 0; j < layers.get( i ).getObjects().size(); j++) {

                        obj ox = layers.get( i ).getObjects().get( j );

                        if (ox.getShape() != null) {
                            switch (ox.getShape()) {
                                case "ellipse":
                                    sr.ellipse( ox.getX(), -ox.getY() + Tsh - ox.getH(), ox.getW(), ox.getH() );

                                    break;
                                case "point":
                                    sr.ellipse( ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2 );

                                    sr.rect( ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2 );

                                    break;
                                case "polygon":
                                    //sr.ellipse(ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2);
                                    //sr.ellipse(ox.getXa() + Tsh * 3 / 8, -ox.getYa() + Tsh - Tsh * 5 / 8, Tsw / 4, Tsh / 4);


                                    float[] f = ox.getVertices( Tsh );
                                    if (ox.getPointsSize() >= 3) {
                                        Polygon polygon = new Polygon();
                                        polygon.setVertices( f );
                                        polygon.setOrigin( ox.getX(), -ox.getY() + Tsh );
                                        polygon.rotate( 360 - ox.getRotation() );
                                        sr.polygon( polygon.getTransformedVertices() );
                                    }

                                    break;
                                case "polyline":
                                    //sr.ellipse(ox.getXa() + Tsh / 4f, -ox.getYa() + Tsh - Tsh * 3f / 4f, Tsw / 2f, Tsh / 2f);
                                    //sr.ellipse(ox.getXa() + Tsh * 3f / 8f, -ox.getYa() + Tsh - Tsh * 5f / 8f, Tsw / 4f, Tsh / 4f);

                                    /*
                                    if (ox.getPointsSize() >= 2) {
                                        f = ox.getVertices(Tsh);
                                        Polyline polyline = new Polyline(f);
                                        polyline.setOrigin(ox.getX(), -ox.getY() + Tsh);
                                        polyline.rotate(360 - ox.getRotation());
                                        sr.polyline(polyline.getTransformedVertices());
                                    }
                                    */

                                    break;
                                case "text":
                                    sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), 0, 0, ox.getW(), ox.getH(), 1, 1, ox.getRotation() );
                                    //str1.draw(batch, ox.getText(), ox.getX(), -ox.getYantingelag(Tsh));

                                    break;
                                case "image":
                                    sr.rect( ox.getX(), ox.getYantingelag( Tsh ), 0, 0, ox.getW(), ox.getH(), 1, 1, 360 - ox.getRotation() );
                                    break;
                                default:
                                    if (orientation.equalsIgnoreCase( "orthogonal" )) {
                                        sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), 0, ox.getH(), ox.getW(), ox.getH(), 1, 1, 360 - ox.getRotation() );
                                    } else if (orientation.equalsIgnoreCase( "isometric" )) {
                                        //anchor
                                        float offx = (ox.getX() / Tsh * Tsw / 2) - (ox.getY() / Tsh * Tsw / 2);//-ox.getY();
                                        float offy = (-ox.getX() / Tsh * Tsh / 2) - (ox.getY() / Tsh * Tsh / 2); //-ox.getY()*Tsh/2;

                                        float op1 = Tsw / 2; //'0'
                                        float op2 = Tsh; //0
                                        float op3 = ox.getW() / Tsh * Tsw / 2;
                                        float op4 = ox.getW() / Tsh * Tsh / 2;
                                        float op5 = (ox.getW() / Tsh * Tsw / 2) - (ox.getH() / Tsh * Tsw / 2);
                                        float op6 = (-ox.getH() / Tsh * Tsh / 2) - (ox.getW() / Tsh * Tsh / 2);
                                        float op7 = -ox.getH() / Tsh * Tsw / 2;
                                        float op8 = -ox.getH() / Tsh * Tsh / 2;

                                        float p1 = offx + op1;
                                        float p2 = offy + op2;
                                        float p3 = offx + op1 + op3;
                                        float p4 = offy + op2 - op4;
                                        float p5 = offx + op1 + op5;
                                        float p6 = offy + op2 + op6;
                                        float p7 = offx + op1 + op7;
                                        float p8 = offy + op2 + op8;

                                        sr.polygon( new float[]{p1, p2, p3, p4, p5, p6, p7, p8} );


                                    }
                                    //str1.draw(batch, j+"", 0, -j);


                                    break;
                            }


                        } else {
                            sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), ox.getW(), ox.getH() );

                        }


                    }
                }
            }
        }

        sr.end();

        //part 2 of 2, outline.
        sr.begin( ShapeRenderer.ShapeType.Line ); //Edit it with filled to make it looks gorgeous.
        Gdx.gl.glEnable( GL20.GL_BLEND );

        Gdx.gl20.glLineWidth( 5 );//average
        //sr.setColor(0.8f, 0.8f, 0.8f, 0.5f); //blink

        if (layers.size() > 0) {
            for (int i = 0; i < layers.size(); i++) {

                if (i == selLayer) {
                    sr.setColor( 0.5f, 1f, 0.5f, 1f ); //blink

                } else {
                    sr.setColor( 0.8f, 0.8f, 0.8f, 0.5f ); //blink

                }

                boolean isShown = false;
                switch (viewMode) {
                    case ALL:
                        isShown = true;
                        break;
                    case STACK:
                        if (i <= selLayer) isShown = true;
                        break;
                    case SINGLE:
                        if (i == selLayer) isShown = true;
                        break;
                    case CUSTOM:
                        if (layers.get( i ).isVisible()) isShown = true;
                        break;
                }

                if (layers.get( i ).getType() == layer.Type.OBJECT && isShown && layers.get( i ).getObjects().size() > 0) {

                    for (int j = 0; j < layers.get( i ).getObjects().size(); j++) {

                        obj ox = layers.get( i ).getObjects().get( j );

                        if (ox.getShape() != null) {
                            switch (ox.getShape()) {
                                case "ellipse":
                                    sr.ellipse( ox.getX(), -ox.getY() + Tsh - ox.getH(), ox.getW(), ox.getH() );

                                    break;
                                case "point":
                                    sr.ellipse( ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2 );

                                    sr.rect( ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2 );

                                    break;
                                case "polygon":
                                    sr.ellipse( ox.getXa() + Tsh / 4, -ox.getYa() + Tsh - Tsh * 3 / 4, Tsw / 2, Tsh / 2 );
                                    sr.ellipse( ox.getXa() + Tsh * 3 / 8, -ox.getYa() + Tsh - Tsh * 5 / 8, Tsw / 4, Tsh / 4 );


                                    float[] f = ox.getVertices( Tsh );
                                    if (ox.getPointsSize() == 2) {

                                        f = ox.getVertices( Tsh );
                                        Polyline polyline = new Polyline( f );
                                        polyline.setOrigin( ox.getX(), -ox.getY() + Tsh );
                                        polyline.rotate( 360 - ox.getRotation() );
                                        sr.polyline( polyline.getTransformedVertices() );

                                    } else if (ox.getPointsSize() >= 3) {
                                        Polygon polygon = new Polygon();
                                        polygon.setVertices( f );
                                        polygon.setOrigin( ox.getX(), -ox.getY() + Tsh );
                                        polygon.rotate( 360 - ox.getRotation() );
                                        sr.polygon( polygon.getTransformedVertices() );
                                    }

                                    break;
                                case "polyline":
                                    sr.ellipse( ox.getXa() + Tsh / 4f, -ox.getYa() + Tsh - Tsh * 3f / 4f, Tsw / 2f, Tsh / 2f );
                                    sr.ellipse( ox.getXa() + Tsh * 3f / 8f, -ox.getYa() + Tsh - Tsh * 5f / 8f, Tsw / 4f, Tsh / 4f );

                                    if (ox.getPointsSize() >= 2) {
                                        f = ox.getVertices( Tsh );
                                        Polyline polyline = new Polyline( f );
                                        polyline.setOrigin( ox.getX(), -ox.getY() + Tsh );
                                        polyline.rotate( 360 - ox.getRotation() );
                                        sr.polyline( polyline.getTransformedVertices() );
                                    }
                                    break;
                                case "text":
                                    sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), 0, 0, ox.getW(), ox.getH(), 1, 1, ox.getRotation() );
                                    //str1.draw(batch, ox.getText(), ox.getX(), -ox.getYantingelag(Tsh));

                                    break;
                                case "image":
                                    sr.rect( ox.getX(), ox.getYantingelag( Tsh ), 0, 0, ox.getW(), ox.getH(), 1, 1, 360 - ox.getRotation() );
                                    break;
                                default:
                                    if (orientation.equalsIgnoreCase( "orthogonal" )) {
                                        sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), 0, ox.getH(), ox.getW(), ox.getH(), 1, 1, 360 - ox.getRotation() );
                                    } else if (orientation.equalsIgnoreCase( "isometric" )) {

                                        //anchor
                                        float offx = (ox.getX() / Tsh * Tsw / 2) - (ox.getY() / Tsh * Tsw / 2);//-ox.getY();
                                        float offy = (-ox.getX() / Tsh * Tsh / 2) - (ox.getY() / Tsh * Tsh / 2); //-ox.getY()*Tsh/2;

                                        float op1 = Tsw / 2; //'0'
                                        float op2 = Tsh; //0
                                        float op3 = ox.getW() / Tsh * Tsw / 2;
                                        float op4 = ox.getW() / Tsh * Tsh / 2;
                                        float op5 = (ox.getW() / Tsh * Tsw / 2) - (ox.getH() / Tsh * Tsw / 2);
                                        float op6 = (-ox.getH() / Tsh * Tsh / 2) - (ox.getW() / Tsh * Tsh / 2);
                                        float op7 = -ox.getH() / Tsh * Tsw / 2;
                                        float op8 = -ox.getH() / Tsh * Tsh / 2;

                                        float p1 = offx + op1;
                                        float p2 = offy + op2;
                                        float p3 = offx + op1 + op3;
                                        float p4 = offy + op2 - op4;
                                        float p5 = offx + op1 + op5;
                                        float p6 = offy + op2 + op6;
                                        float p7 = offx + op1 + op7;
                                        float p8 = offy + op2 + op8;

                                        sr.polygon( new float[]{p1, p2, p3, p4, p5, p6, p7, p8} );


                                    }
                                    //str1.draw(batch, j+"", 0, -j);


                                    break;
                            }


                        } else {
                            sr.rect( ox.getX(), ox.getYantingelag( Tsh ) - ox.getH(), ox.getW(), ox.getH() );

                        }


                    }
                }
            }
        }

        sr.end();

    }

    public void drawObjectsInfo() {

        batch.setProjectionMatrix( cam.combined );
        batch.begin();
        str1.getData().setScale( Tsw / 200f );
        if (layers.size() > 0) {
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get( i ).getType() == layer.Type.OBJECT && layers.get( i ).isVisible() && layers.get( i ).getObjects().size() > 0) {

                    for (int j = 0; j < layers.get( i ).getObjects().size(); j++) {

                        obj ox = layers.get( i ).getObjects().get( j );

                        if (ox.getGid() != 0) {
                            int kyut = 0;
                            for (int c = 0; c < tilesets.size(); c++) {
                                if (ox.getGid() >= tilesets.get( c ).getFirstgid() && ox.getGid() < tilesets.get( c ).getFirstgid() + tilesets.get( c ).getTilecount()) {
                                    kyut = c;
                                    break;
                                }
                            }

                            if (ox.getGid() != 0) {
                                int sprX = (ox.getGid() - tilesets.get( kyut ).getFirstgid()) % (tilesets.get( kyut ).getWidth());
                                int sprY = (ox.getGid() - tilesets.get( kyut ).getFirstgid()) / (tilesets.get( kyut ).getWidth());
                                int margin = tilesets.get( kyut ).getMargin();
                                int spacing = tilesets.get( kyut ).getSpacing();
                                int Tswa = tilesets.get( kyut ).getTilewidth();
                                int Tsha = tilesets.get( kyut ).getTileheight();

                                TextureRegion region = new TextureRegion( tilesets.get( kyut ).getTexture(), (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                                Sprite s = new Sprite( region );
                                // s.setX( ox.getX() );
                                // s.setY( ox.getYantingelag( Tsh ) - ox.getH());
                                s.setBounds( ox.getX(), ox.getYantingelag( Tsh ), ox.getW(), ox.getH() );

                                s.setOrigin( 0, 0 );
                                s.setRotation( 360 - ox.getRotation() );

                                //for RW ONLY
                                if (layers.get( i ).getName().equalsIgnoreCase( "UnitObjects" )) {
                                    if (s.getRotation() == 360 + 90) {
                                        s.rotate90( true );
                                    }
                                }


                                //  s.rotate90( false );
                                s.draw( batch );
                                //batch.draw(region, ox.getX(), ox.getYantingelag(Tsh) - ox.getH(), 0,ox.getH(),ox.getW(), ox.getH(),1,1, 360-ox.getRotation(),true);
                            }

                        }
                        String kucing = "";
                        if (ox.getName() != null) kucing = ox.getName();
                        str1.getData().setScale( 0.2f + Tsw / 160f );
                        float offx = 0, offy = 0;
                        int multiplier = 1;

                        if (orientation.equalsIgnoreCase( "isometric" )) {
                            multiplier = 2;
                            offx = (ox.getX() + (ox.getY()));//-ox.getY();
                            offy = (ox.getX() / 2f) + (-ox.getY() / 2f); //-ox.getY()*Tsh/2;
                        }
                        //draw object label
                        str1.getData().setScale( 0.1f + Tsw / 160f );
                        str1.draw( batch, kucing, ox.getX() * multiplier - offx, ox.getYantingelag( Tsh ) + 2 - offy );

                        if (ox.getShape() != null) {
                            switch (ox.getShape()) {
                                case "text":


                                    str1.getData().setScale( 0.2f + Tsw / 160f );
                                    if (ox.getText() != null) {
                                        if (ox.isWrap()) {
                                            str1.draw( batch, ox.getText(), ox.getX() * multiplier - offx, ox.getYantingelag( Tsh ) - offy, ox.getW(), com.badlogic.gdx.utils.Align.left, false );
                                        } else {
                                            str1.draw( batch, ox.getText(), ox.getX() * multiplier - offx, ox.getYantingelag( Tsh ) - offy );
                                        }
                                    }
                                    str1.getData().setScale( 0.2f + Tsw / 160f );

                                    break;
                            }
                        }


                    }
                }
            }
        }
        str1.getData().setScale( 1f );
        batch.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        //if (loadingfile) return;
        float tx, ty, tz;
        float tx1, ty1, tz1;
        float tx2, ty2, tz2;
        float tx3, ty3, tz3;

        tx = cam.position.x;
        ty = cam.position.y;
        tz = cam.position.z;
        tx1 = tilecam.position.x;
        ty1 = tilecam.position.y;
        tz1 = tilecam.position.z;
        tx3 = uicam.position.x;
        ty3 = uicam.position.y;
        tz3 = uicam.position.z;

        //cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        nssx = width;
        nssy = height;
        if (width > height) {
            stage = hstage;
            //stage.addActor(hstage.getRoot());
            cam.setToOrtho( false, ssy, ssx );
            tilecam.setToOrtho( false, ssy, ssx );
            uicam.setToOrtho( false, ssy, ssx );
            minicam.setToOrtho( false, ssy, ssx );
            gamecam.setToOrtho( false, 20, 11 );
            tx3 = Tsw * Tw * 10f;
            ty3 = 0;
            tz3 = 0;


            landscape = true;
        } else {
            stage = vstage;
            //stage.addActor(vstage.getRoot());
            cam.setToOrtho( false, ssx, ssy );
            tilecam.setToOrtho( false, ssx, ssy );
            uicam.setToOrtho( false, ssx, ssy );
            minicam.setToOrtho( false, ssx, ssy );
            gamecam.setToOrtho( false, 11, 20 );
            tx3 = 0;
            ty3 = -0;
            tz3 = 0;
            landscape = false;
        }
        stage.getViewport().setScreenSize( width, height );
        if (kartu == "stage") {
            //setMenuMap();
            gotoStage( lastStage );
            if (lastStage == tMenu || lastStage == tMap) {
                setMenuMap();
                gotoStage( tMenu );
            }

            //gotoStage(lastStage);
            //backToMap();
        } else if (kartu == "game") {
        } else {
            if (dialog != null) {
                dialog.hide();

            }
            if (startup != false) {
                //setMenuMap();
                //gotoStage(tMenu);
            }
            startup = true;

        }


        cam.position.set( tx, ty, tz );
        cam.update();
        tilecam.position.set( tx1, ty1, tz1 );
        tilecam.update();
        gamecam.update();
        recenterUI();
        resetMinimap();
        resetCaches();
        //resetcam(false);
        //minicam.position.set(tx3,ty3,tz3);
        //minicam.update();
        //uicam.position.set(tx2,ty2,tz2);
        //uicam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void uisrect(gui gui, Vector3 mousepos, Color color) {

        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl();
            w = gui.getWl();
            h = gui.getHl();
        } else {
            x = gui.getX();
            y = gui.getY();
            w = gui.getW();
            h = gui.getH();
        }


        if (!landscape) {

            if (Gdx.input.isTouched() && mousepos.x >= x / 100 * ssx && mousepos.x <= w / 100 * ssx && mousepos.y >= y / 100 * ssy && mousepos.y <= h / 100 * ssy && drag == false) {
                uis.setColor( 0, 0.4f, 1, 0.4f );
            } else {
                if (color == null) {
                    uis.setColor( 0, 0, 0, 0.4f );
                } else if (color.toString().equalsIgnoreCase( "00ff00ff" )) {
                    uis.setColor( 0f, blink, 0f, .4f );
                } else {
                    if (!tutoring) uis.setColor( color );
                }

            }


            float xredux = .5f; //if (w==100||x==0) xredux=0;
            float yredux = .3f; //if (h==100||y==0) yredux=0;
            uis.roundedRect( (x + xredux) / 100 * ssx, (y + yredux) / 100 * ssy, (w - x - xredux * 2) / 100 * ssx, (h - y - yredux * 2) / 100 * ssy, 20f );
        } else {

            if (Gdx.input.isTouched() && mousepos.x >= x / 100 * ssy && mousepos.x <= w / 100 * ssy && mousepos.y >= y / 100 * ssx && mousepos.y <= h / 100 * ssx && drag == false) {
                uis.setColor( 0, 0.4f, 1, 0.4f );
            } else {
                if (color == null) {
                    uis.setColor( 0, 0, 0, 0.4f );
                } else if (color.toString().equalsIgnoreCase( "00ff00ff" )) {
                    uis.setColor( 0f, blink, 0f, .4f );
                } else {
                    if (!tutoring) uis.setColor( color );
                }

            }


            float xredux = .2f; //if (w==100||x==0) xredux=0;
            float yredux = .5f; //if (h==100||y==0) yredux=0;

            uis.roundedRect( (x + xredux) / 100 * ssy, (y + yredux) / 100 * ssx, (w - x - xredux * 2) / 100 * ssy, (h - y - yredux * 2) / 100 * ssx, 10f );

        }
    }

    public void str1drawlabel(SpriteBatch ui, String strin, gui gui) {
        float x = 0, y = 0, w = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl() + 2.7f;
            w = gui.getWl();

        } else {
            x = gui.getX();
            y = gui.getY() + 2.7f;
            w = gui.getW();

        }
        if (!landscape) {
            str1.draw( ui, strin, x / 100 * ssx, (y + 6) / 100 * ssy, (w - x) / 100 * ssx, Align.center, true );
        } else {
            str1.draw( ui, strin, x / 100 * ssy, (y + 6) / 100 * ssx, (w - x) / 100 * ssy, Align.center, true );
        }

    }

    public void str1drawbuttonlabel(SpriteBatch ui, String strin, gui gui) {
        str1.setColor( 1, 1, 1, 1 );

        if (landscape) {
            str1.getData().setScale( .5f );
        } else {
            str1.getData().setScale( .6f );
        }

        float x = 0, y = 0, w = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl() - 2.8f;
            w = gui.getWl();

        } else {
            x = gui.getX();
            y = gui.getY() - 2.8f;
            w = gui.getW();

        }
        if (!landscape) {
            str1.draw( ui, strin, x / 100 * ssx, (y + 6) / 100 * ssy, (w - x) / 100 * ssx, Align.center, true );
        } else {
            str1.draw( ui, strin, x / 100 * ssy, (y + 6) / 100 * ssx, (w - x) / 100 * ssy, Align.center, true );
        }

    }

    public void str1draw(SpriteBatch ui, String strin, gui gui) {
        str1.setColor( 1, 1, 1, 1 );
        if (landscape) {
            str1.getData().setScale( .7f );
        } else {
            str1.getData().setScale( 1f );
        }

        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl();
            w = gui.getWl();
            h = gui.getHl();


        } else {
            x = gui.getX();
            y = gui.getY();
            w = gui.getW();
            h = gui.getH();


        }
        if (!landscape) {
            str1.draw( ui, strin, x / 100 * ssx, (h - ((h - y) / 2.8f)) / 100 * ssy, (w - x) / 100 * ssx, Align.center, true );
        } else {
            str1.draw( ui, strin, x / 100 * ssy, (h - ((h - y) / 2.8f)) / 100 * ssx, (w - x) / 100 * ssy, Align.center, true );
        }

    }

    public void str1draw(SpriteBatch ui, String strin, gui gui, int offset) {
        str1.setColor( 1, 1, 1, 1 );
        if (landscape) {
            str1.getData().setScale( .7f );
        } else {
            str1.getData().setScale( 1f );
        }

        float x = 0, y = 0, w = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl();
            w = gui.getWl();

        } else {
            x = gui.getX();
            y = gui.getY();
            w = gui.getW();

        }
        if (!landscape) {
            str1.draw( ui, strin, x / 100 * ssx, (y + offset) / 100 * ssy, (w - x) / 100 * ssx, Align.center, true );
        } else {
            str1.draw( ui, strin, x / 100 * ssy, (y + offset) / 100 * ssx, (w - x) / 100 * ssy, Align.center, true );
        }

    }

    public void uidraw(Texture tx, gui gui, int margin) {
        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl() + margin + 1;
            y = gui.getYl() + margin;
            w = gui.getWl() - margin - 1;
            h = gui.getHl() - margin;
        } else {
            x = gui.getX() + margin + 1;
            y = gui.getY() + margin;
            w = gui.getW() - margin - 1;
            h = gui.getH() - margin;
        }
        if (!landscape) {
            ui.draw( tx, x / 100 * ssx, y / 100 * ssy, (w - x) / 100 * ssx, (h - y) / 100 * ssy );
        } else {
            ui.draw( tx, x / 100 * ssy, y / 100 * ssx, (w - x) / 100 * ssy, (h - y) / 100 * ssx );
        }

    }

    public void uidrawbutton(Texture tx, String strin, gui gui, float margin) {
        //margin=1;
        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl();// + margin + 1;
            y = gui.getYl() + margin + 1;
            w = gui.getWl();// - margin - 1;
            h = gui.getHl() - margin + 1;
        } else {
            x = gui.getX();// + margin + 1;
            y = gui.getY() + margin + 1;
            w = gui.getW();// - margin - 1;
            h = gui.getH() - margin + 1;
        }
        if (!landscape) {
            ui.setColor( Color.BLACK );
            ui.draw( tx, 1 + (x / 100 * ssx) + ((w - x) / 100 * ssx / 2) - ((h - y) / 100 * ssy / 2), -1 + y / 100 * ssy, (h - y) / 100 * ssy, (h - y) / 100 * ssy );
            ui.setColor( Color.WHITE );
            ui.draw( tx, (x / 100 * ssx) + ((w - x) / 100 * ssx / 2) - ((h - y) / 100 * ssy / 2), y / 100 * ssy, (h - y) / 100 * ssy, (h - y) / 100 * ssy );
            // ui.draw(tx, x / 100 * ssx, y / 100 * ssy, (w - x) / 100 * ssx, (h - y) / 100 * ssy);
        } else {
            ui.setColor( Color.BLACK );
            ui.draw( tx, 1 + (x / 100 * ssy) + ((w - x) / 100 * ssy / 2) - ((h - y) / 100 * ssx / 2), -1 + y / 100 * ssx, (h - y) / 100 * ssx, (h - y) / 100 * ssx );
            ui.setColor( Color.WHITE );
            ui.draw( tx, (x / 100 * ssy) + ((w - x) / 100 * ssy / 2) - ((h - y) / 100 * ssx / 2), y / 100 * ssx, (h - y) / 100 * ssx, (h - y) / 100 * ssx );

            //            ui.draw(tx, x / 100 * ssy, y / 100 * ssx, (w - x) / 100 * ssy, (h - y) / 100 * ssx);
        }

///////////LABEL///////////////////////////////////
        str1.setColor( 1f, 1f, 1, 1 );

        if (landscape) {
            str1.getData().setScale( .5f );
        } else {
            str1.getData().setScale( .6f );
        }

        if (landscape) {
            x = gui.getXl();
            y = gui.getYl() - 3;
            w = gui.getWl();

        } else {
            x = gui.getX();
            y = gui.getY() - 3;
            w = gui.getW();

        }

        if (margin == 0) y -= 0.5f;
        if (margin == 3.1f) y += 1f;
        if (!landscape) {
            str1.draw( ui, strin, x / 100 * ssx, (y + 6) / 100 * ssy, (w - x) / 100 * ssx, Align.center, true );
        } else {
            str1.draw( ui, strin, x / 100 * ssy, (y + 6) / 100 * ssx, (w - x) / 100 * ssy, Align.center, true );
        }


        if (landscape) {
            str1.getData().setScale( .7f );
        } else {
            str1.getData().setScale( 1f );
        }
    }

    public void uidraw(Texture tx, gui gui, int margin, int srcx, int srcy, int srcw, int srch) {
        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl() + margin + 1.5f;
            y = gui.getYl() + margin + 3;
            w = gui.getWl() - margin - 1.5f;
            h = gui.getHl() - margin - 1;
        } else {
            x = gui.getX() + margin + 1;
            y = gui.getY() + margin + 1;
            w = gui.getW() - margin - 1;
            h = gui.getH() - margin + 1;
        }
        float roti = 0;
        boolean flipx = false, flipy = false;
        switch (rotate) {
            case 0://"20"://diagonal flip
                roti = 0;
                flipx = false;
                flipy = false;
                break;
            case 1://"40"://flipy
                roti = 270f;
                flipx = false;
                flipy = false;
                break;
            case 2://"60"://270 degrees clockwise
                roti = 180f;
                flipx = false;
                flipy = false;
                break;
            case 3://"80"://flipx
                roti = 90f;
                flipx = false;
                flipy = false;
                break;
            case 4://"A0"://90 degress cw
                roti = 0f;
                flipx = true;
                flipy = false;
                break;
            case 5://"C0"://180 degrees cw
                roti = 270f;
                flipx = true;
                flipy = false;
                break;
            case 6://
                roti = 180f;
                flipx = true;
                flipy = false;
                break;
            case 7://
                roti = 90f;
                flipx = true;
                flipy = false;
                break;
        }

        if (!landscape) {
            ui.draw( tx, x / 100 * ssx, y / 100 * ssy, (w - x) / 100 * ssx / 2, (h - y) / 100 * ssy / 2, (w - x) / 100 * ssx, (h - y) / 100 * ssy, 1, 1, roti, srcx, srcy, srcw, srch, flipx, flipy );
        } else {
            ui.draw( tx, x / 100 * ssy, y / 100 * ssx, (w - x) / 100 * ssy / 2, (h - y) / 100 * ssx / 2, (w - x) / 100 * ssy, (h - y) / 100 * ssx, 1, 1, roti, srcx, srcy, srcw, srch, flipx, flipy );
        }

    }

    Pixmap minipix=null;
    Texture txxx = null;
    FrameBuffer fboo=null;
    public void drawWorldUI() {
        //status(cam.zoom+"",4);
        Gdx.gl.glEnable( GL20.GL_BLEND );
        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
        Vector3 mouse = new Vector3( Gdx.input.getX(), Gdx.input.getY(), 0 );
        uicam.unproject( mouse ); // mousePos is now in world coordinates

        //status(Integer.toString(batch.totalRenderCalls),5);
        //status(Float.toString(cam.zoom),1);
        uis.setProjectionMatrix( uicam.combined );
        uis.begin( ShapeRenderer.ShapeType.Filled );
        uis.setColor( 0f, 0f, 0, 0.6f );

        if (cammode == "View only") {
            if (!takingss) {
                uisrect( gui.screenshot, mouse, null );//tile/obj switch
                uisrect( gui.center, mouse, vis( "center" ) );//map props. button

            }
            uis.end();
            ui.setProjectionMatrix( uicam.combined );
            ui.begin();
            if (!takingss) {
                uidraw( txcenter, gui.center, 2 );
                str1draw( ui, z.screenshot, gui.screenshot );

            }

            if (statustimeout > 0) {
                statustimeout -= delta;
                str1.setColor( 1, 0, 0, 1 );
                if (!takingss) str1draw( ui, debugMe, gui.status );
                str1.setColor( 1, 1, 1, 1 );
            }

            ui.end();
        } else if (cammode != "View only") {


            if (roll || stamp || assemblymode) uisrect( gui.info, mouse, null );//info

            if (tutoring) {
                uisrect( gui.canceltutorial, mouse, vis( "cancel" ) );//tile/obj switch
            }

            if (kartu == "editor") {
                uisrect( gui.editorcancel, mouse, vis( "editorcancel" ) );//tile/obj switch
                uisrect( gui.editormode, mouse, vis( "editormode" ) );//layer switch
                uisrect( gui.editorsave, mouse, vis( "editorsave" ) );//viewmode switch
                uisrect( gui.editorleft, mouse, vis( "editorleft" ) );//tile/obj switch
                uisrect( gui.editorright, mouse, vis( "editorright" ) );//layer switch
                uisrect( gui.editorup, mouse, vis( "editorup" ) );//viewmode switch
                uisrect( gui.editordown, mouse, vis( "editordown" ) );//tile/obj switch
                uisrect( gui.info, mouse, vis( "infoinfo" ) );
            }

            if (mode == "tile" || mode == "object" || mode == "image") {
                uisrect( gui.mode, mouse, vis( "addlayer" ) );//tile/obj switch
                uisrect( gui.layer, mouse, vis( "layerlist" ) );//layer switch
                uisrect( gui.viewmode, mouse, vis( "viewmode" ) );//viewmode switch
                //uisrect(gui.center, mouse, vis("center"));//map props. button
                uisrect( gui.menu, mouse, vis( "menu" ) );//main menu button
                uisrect( gui.map, mouse, vis( "map" ) );//map props. button
                uisrect( gui.save, mouse, vis( "quicksave" ) );//main menu button
                uisrect( gui.layerpick, mouse, vis( "layerpick" ) );//layerpicker
                /* draw filled rectangle for the whole minimap.
                if (sMinimap){
                if (!landscape) {
                    uis.rect( gui.minimap.x/100f*ssx, gui.minimap.y/100f*ssy, (gui.minimap.w-gui.minimap.x)/100f*ssx,(gui.minimap.h-gui.minimap.y)/100f*ssy) ;
                }else {
                    uis.rect( gui.minimap.xl / 100f * ssy, gui.minimap.yl / 100f * ssx, (gui.minimap.wl - gui.minimap.xl) / 100f * ssy, (gui.minimap.hl - gui.minimap.yl) / 100f * ssx );
                }


                }
*/


                for (property p : properties) {
                    if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "NotTiled platformer" )) {
                        uisrect( gui.play, mouse, vis( "play" ) );//redo
                    } else if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "NotTiled music" )) {
                        uisrect( gui.play, mouse, vis( "play" ) );//redo
                    } else if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "Pixel Editor" )) {
                        uisrect( gui.play, mouse, vis( "play" ) );//redo
                    } else if (p.getName().equalsIgnoreCase( "tag" ) && p.getValue().equalsIgnoreCase( "RW" )) {
                        uisrect( gui.play, mouse, vis( "play" ) );//redo
                    }

                }
            }


            if (mode == "tile") {

                if (sShowFPS) uisrect( gui.fps, mouse, vis( "FPS" ) );//tile/obj switch

                uisrect( gui.rotation, mouse, vis( "rotation" ) );//rotation switch
                uisrect( gui.undo, mouse, vis( "undo" ) );//undo
                uisrect( gui.redo, mouse, vis( "redo" ) );//redo
                if (swatches) {
                    //uisrect( gui.swatches, mouse, vis( "swatches" ) );//swatches
                    uisrect( gui.sw1, mouse, vis( "swatches1" ) );//swatches
                    uisrect( gui.sw2, mouse, vis( "swatches2" ) );//swatches
                    uisrect( gui.sw3, mouse, vis( "swatches3" ) );//swatches
                    uisrect( gui.sw4, mouse, vis( "swatches4" ) );//swatches
                    uisrect( gui.sw5, mouse, vis( "swatches5" ) );//swatches
                    uisrect( gui.sw6, mouse, vis( "swatches6" ) );//swatches

                }

                //show macro button in selection tool

                if ((activetool==3 && !assemblymode && !stamp)||kartu=="editor"){
                    uisrect( gui.addmacro, mouse, vis( "addmacro" ) );//add macro
                }

                uisrect( gui.picker, mouse, vis( "tilepick" ) );//select tile
                if (autotiles.size() > 0 || kartu == "editor")
                    uisrect( gui.autopicker, mouse, vis( "autotilepick" ) );//select auto tile
                if (autotiles.size() > 0 || kartu == "editor")
                    uisrect( gui.autotile, mouse, vis( "refresh" ) );//tool switch


                Color c1 = null, c2 = null, c3 = null, c4 = null, c5 = null;
                switch (activetool) {
                    case 0:
                        c1 = new Color( 1f, 1f, 0f, .4f );
                        break;
                    case 1:
                        break;
                    case 2:
                        c3 = new Color( 1f, 1f, 0f, .4f );
                        break;
                    case 3:
                        c4 = new Color( 1f, 1f, 0f, .4f );
                        break;
                    case 4:
                        c5 = new Color( 1f, 1f, 0f, .4f );
                        break;
                }

                if (eraser) c2 = new Color( 1f, 0.3f, 0f, .4f );


                if (!tutoring) {
                    uisrect( gui.tool1, mouse, c1 );
                    uisrect( gui.tool2, mouse, c2 );
                    uisrect( gui.tool3, mouse, c3 );
                    uisrect( gui.tool4, mouse, c4 );
                    uisrect( gui.tool5, mouse, c5 );
                } else {
                    uisrect( gui.tool1, mouse, vis( "tool1" ) );
                    uisrect( gui.tool2, mouse, vis( "tool2" ) );
                    uisrect( gui.tool3, mouse, vis( "tool3" ) );
                    uisrect( gui.tool4, mouse, vis( "tool4" ) );
                    uisrect( gui.tool5, mouse, vis( "tool5" ) );
                }
            }

            if (mode == "object") {
                uisrect( gui.objectpickermid, mouse, null );//objtools switch
                uisrect( gui.objectpickerleft, mouse, null );//objtools switch
                uisrect( gui.objectpickerright, mouse, null );//objtools switch
                uisrect( gui.lock, mouse, null );//tool switch

            }
            if (mode == "image") {
                uisrect( gui.objectpickermid, mouse, null );//objtools switch

            }
            if (mode == "newpoly") {
                uisrect( gui.undo, mouse, null );//undo
                uisrect( gui.tool, mouse, null );//tool switch
            }

            uis.end();

            /* Draw rectangle on minimap UI position.
            if (sMinimap) {
                uis.setProjectionMatrix( uicam.combined );
                uis.begin( ShapeRenderer.ShapeType.Line );
                uis.setColor( 0f, 0f, 0f, 0.7f );
                if (!landscape) {
                    uis.rect( gui.minimap.x / 100f * ssx, gui.minimap.y / 100f * ssy, (gui.minimap.w - gui.minimap.x) / 100f * ssx, (gui.minimap.h - gui.minimap.y) / 100f * ssy );
                } else {
                    uis.rect( gui.minimap.xl / 100f * ssy, gui.minimap.yl / 100f * ssx, (gui.minimap.wl - gui.minimap.xl) / 100f * ssy, (gui.minimap.hl - gui.minimap.yl) / 100f * ssx );

                }
                uis.end();
            }
             */
            /////////////// draw minimap /////////////////

            if (sMinimap) {
                //if (txMinimap!=null) ui.draw(txMinimap,0,0);

                if (!caching & tcaches.size() > 0) {
                    if (isupdatingcache & FPSCount==1) {
                        isupdatingcache=false;


                        if (landscape) {
                            fboo = new FrameBuffer( Pixmap.Format.RGBA8888, ssy, ssx, false );
                        }else{
                            fboo = new FrameBuffer( Pixmap.Format.RGBA8888, ssx, ssy, false );

                        }

                        log ("Cache size: "+tcaches.size());
                        log("caching...");

                        fboo.begin();
                        Gdx.gl.glEnable( GL20.GL_BLEND );
                        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );

//                        Gdx.gl.glBlendFunc( GL20.GL_ONE_MINUS_DST_ALPHA, GL20.GL_SRC_ALPHA );
                        SpriteCache cache;

                        for (int i = 0; i < tcaches.size(); i++) {

                            cache = tcaches.get( i ).getCache();

                            int myid = tcaches.get( i ).getCacheID();
                            cache.setProjectionMatrix( minicam.combined );

                            cache.begin();
                            cache.draw( myid ); //call our cache with cache ID and draw it
                            cache.end();

                        }


                        Gdx.gl.glEnable( GL20.GL_BLEND );
                        Gdx.gl.glBlendFunc( GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );
                        fboo.end();
                        txxx=fboo.getColorBufferTexture();
                        log("caching finished");





                    }
                    // draw the texture, even on the 1st fps
                    ui.begin();

                    if (landscape) {
                        if (txxx!=null) ui.draw(txxx,0,0,ssy,ssx,0,0,ssy,ssx,false,true);
                    }else{
                        if (txxx!=null) ui.draw(txxx,0,0,ssx,ssy,0,0,ssx,ssy,false,true);
                    }
                    ui.end();

                }

                //red rectangle
                uis.setProjectionMatrix( minicam.combined );
                uis.begin( ShapeRenderer.ShapeType.Line );
                uis.setColor( 0f, 0f, 0f, 0.7f );

                int onset = 0;
                if (orientation.equalsIgnoreCase( "isometric" )) {
                    onset = Tsw * Tw / 2;
                }
                uis.rect( 0 - onset, Tsh, Tsw * Tw, -Tsh * Th );
                if (1500 * cam.zoom < Tsw * Tw) {
                    uis.setColor( 1f, 0f, 0, 1f );

                    if (landscape) {
                        uis.rect( cam.position.x - (1500 * cam.zoom / 2), cam.position.y - (800 * cam.zoom / 2), 1500 * cam.zoom, 800 * cam.zoom );
                    } else {
                        uis.rect( cam.position.x - (800 * cam.zoom / 2), cam.position.y - (1500 * cam.zoom / 2), 800 * cam.zoom, 1500 * cam.zoom );
                    }
                }
                uis.end();
            }

            /////////////// tile view /////////////////

            ui.setProjectionMatrix( uicam.combined );
            ui.begin();
            //////////
            if (tilesets.size() > 0 && mode == "tile" && curspr != 0) {
                long num = curspr;
                int initset = 0;
                for (int o = 0; o < tilesets.size(); o++) {
                    if (num >= tilesets.get( o ).getFirstgid() && num < tilesets.get( o ).getFirstgid() + tilesets.get( o ).getTilecount()) {
                        initset = o;
                        break;
                    }
                }
                sprX = (int) (num - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                sprY = (int) (num - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                margin = tilesets.get( initset ).getMargin();
                spacing = tilesets.get( initset ).getSpacing();
                Tswa = tilesets.get( initset ).getTilewidth();
                Tsha = tilesets.get( initset ).getTileheight();
                //TextureRegion region= new TextureRegion(tilesets.get(initset).getTexture(), );
                uidraw( tilesets.get( initset ).getTexture(), gui.picker, 1, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
            }

            if (tilesets.size() > 0 && mode == "tile" && swatches) {

                for (int ii = 0; ii < 6; ii++) {
                    long num = swatchValue.get( ii );// ini intinyakah?
                    if (num == 0) continue;
                    int initset = 0;
                    for (int o = 0; o < tilesets.size(); o++) {
                        if (num >= tilesets.get( o ).getFirstgid() && num < tilesets.get( o ).getFirstgid() + tilesets.get( o ).getTilecount()) {
                            initset = o;
                            break;
                        }
                    }
                    sprX = (int) (num - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                    sprY = (int) (num - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                    margin = tilesets.get( initset ).getMargin();
                    spacing = tilesets.get( initset ).getSpacing();
                    Tswa = tilesets.get( initset ).getTilewidth();
                    Tsha = tilesets.get( initset ).getTileheight();
                    //TextureRegion region= new TextureRegion(tilesets.get(initset).getTexture(), );
                    int mgn = 1;
                    if (landscape) mgn = 0;
                    switch (ii + 1) {
                        case 1:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw1, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                        case 2:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw2, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                        case 3:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw3, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                        case 4:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw4, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                        case 5:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw5, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                        case 6:
                            uidraw( tilesets.get( initset ).getTexture(), gui.sw6, mgn, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha );
                            break;
                    }
                }
            }

            //////////


            //normal font
            if (landscape) {
                str1.getData().setScale( .7f );
            } else {
                str1.getData().setScale( 1f );
            }
            if (statustimeout > 0) {
                statustimeout -= delta;
                str1.setColor( 1, 0, 0, 1 );
                if (!takingss) str1draw( ui, debugMe, gui.status );
                str1.setColor( 1, 1, 1, 1 );
            }
            //z.canceltutorial="Cancel Tutorial";
            if (tutoring) str1draw( ui, z.canceltutorial, gui.canceltutorial );
            if (kartu == "editor") {
                uidrawbutton( txsave, z.save, gui.editorsave, 1 );
                uidrawbutton( txundo, z.cancel, gui.editorcancel, 1 );
                uidrawbutton( txmap, z.edit, gui.editormode, 1 );
                uidrawbutton( txLeft, "-", gui.editorleft, 1 );
                uidrawbutton( txRight, "+", gui.editorright, 1 );
                uidrawbutton( txDown, "+", gui.editorup, 1 );
                uidrawbutton( txUp, "-", gui.editordown, 1 );
            }

            if (cammode != "View only") {

                if (mode == "tile" || mode == "object" || mode == "image") {

                    if (sMinimap) {
                        if (txMinimap != null) {

                            if (!landscape) {
                                ui.draw( txMinimap, gui.minimap.x / 100f * ssx, gui.minimap.y / 100f * ssy, (gui.minimap.w - gui.minimap.x) / 100f * ssx, (gui.minimap.h - gui.minimap.y) / 100f * ssy );
                            } else {
                                ui.draw( txMinimap, gui.minimap.xl / 100f * ssy, gui.minimap.yl / 100f * ssx, (gui.minimap.wl - gui.minimap.xl) / 100f * ssy, (gui.minimap.hl - gui.minimap.yl) / 100f * ssx );

                            }

                            //uidraw( txMinimap, gui.minimap, 0);

                        }

                    }


                    if (layers.size() > 0)
                        str1draw( ui, layers.get( selLayer ).getName(), gui.layer );
                    uidrawbutton( txlayer, z.layer, gui.layerpick, 1 );
                    uidrawbutton( txmenu, z.menu, gui.menu, 2 );
                    uidrawbutton( txmap, z.map, gui.map, 2 );
                    uidrawbutton( txadd, z.layer, gui.mode, 3.1f );
                    if (layers.size() > 0) {
                        String vm = "";
                        switch (viewMode) {
                            case STACK:
                                vm = z.stack;
                                break;
                            case ALL:
                                vm = z.all;
                                break;
                            case SINGLE:
                                vm = z.single;
                                break;
                            case CUSTOM:
                                vm = z.visibility;
                                break;
                        }

                        if (layers.get( selLayer ).isVisible()) {
                            uidrawbutton( txVisible, vm, gui.viewmode, 3.1f );
                        } else {
                            uidrawbutton( txInvisible, vm, gui.viewmode, 3.1f );
                        }
                    }


                    if (sAutoSave) {
                        uidrawbutton( txsave2, z.save, gui.save, 2 );
                    } else {
                        uidrawbutton( txsave, z.save, gui.save, 2 );
                    }
                    //uidrawbutton(txcenter, z.recenter, gui.center, 2);

                    for (property p : properties) {
                        if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "NotTiled platformer" )) {
                            uidrawbutton( txplay, z.play, gui.play, 2 );
                        } else if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "NotTiled music" )) {
                            if (midiplaying) {
                                uidrawbutton( txLeft, z.stop, gui.play, 2 );
                            } else {
                                uidrawbutton( txplay, z.play, gui.play, 2 );

                            }

                        } else if (p.getName().equalsIgnoreCase( "type" ) && p.getValue().equalsIgnoreCase( "Pixel Editor" )) {
                            if (rotating) {
                                uidrawbutton( txLeft, z.stop, gui.play, 2 );
                            } else {
                                uidrawbutton( txplay, z.play, gui.play, 2 );

                            }

                        } else if (p.getName().equalsIgnoreCase( "tag" ) && p.getValue().equalsIgnoreCase( "RW" )) {
                            uidrawbutton( txplay, z.play, gui.play, 2 );
                        }

                    }



                }

                if (mode == "tile") {
                    if (sShowFPS) str1draw( ui, fps, gui.fps );


                    str1draw( ui, rotationName, gui.rotation );
                    uidrawbutton( txtile, z.tile, gui.picker, 0 );

                    if ((activetool==3 && !assemblymode && !stamp)||kartu=="editor"){
                        uidrawbutton( txadd, z.macro, gui.addmacro, 2 );

                    }

                    if (stamp) {
                        if (roll) {
                            str1draw( ui, z.smartstampmode, gui.info );
                        } else {
                            str1draw( ui, z.stampmode, gui.info );
                        }
                    } else {

                        if (assemblymode){
                            str1draw( ui, autotiles.get( assemblyid ).getName(), gui.info );
                        }

                        if (roll) {
                            if (activetool == 0) {
                                switch (currentShape) {
                                    case RECTANGLE:
                                        str1draw( ui, z.rectangle, gui.info );
                                        break;
                                    case CIRCLE:
                                        str1draw( ui, z.circle, gui.info );
                                        break;
                                    case LINE:
                                        str1draw( ui, z.line, gui.info );
                                        break;
                                }

                            } else if (activetool == 1) {
                                str1draw( ui, z.eraser, gui.info );
                            } else if (activetool == 3) {
                                str1draw( ui, z.select, gui.info );
                            } else if (activetool == 4) {
                                str1draw( ui, z.paintbrush, gui.info );
                            }
                        }
                    }

                    /*
                    if (swatches) {
                        uidrawbutton( txtile, "", gui.sw1, 0 );
                        uidrawbutton( txtile, "", gui.sw2, 0 );
                        uidrawbutton( txtile, "", gui.sw3, 0 );
                        uidrawbutton( txtile, "", gui.sw4, 0 );
                        uidrawbutton( txtile, "", gui.sw5, 0 );
                        uidrawbutton( txtile, "", gui.sw6, 0 );
                    }

                     */


                    if (autotiles.size() > 0 || kartu == "editor") {
                        uidrawbutton( txautopick, z.macrotiles, gui.autopicker, 2 );
                        uidrawbutton( txplay, z.runmacro, gui.autotile, 2 );
                    }


                    switch (currentShape) {
                        case RECTANGLE:
                            uidrawbutton( txrectangle, z.rectangle, gui.tool1, 3 );
                            break;
                        case CIRCLE:
                            uidrawbutton( txcircle, z.circle, gui.tool1, 3 );
                            break;
                        case LINE:
                            uidrawbutton( txline, z.line, gui.tool1, 3 );
                            break;
                    }


                    uidrawbutton( txeraser, z.eraser, gui.tool2, 3 );
                    uidrawbutton( txfill, z.fill, gui.tool3, 3 );
                    switch (movetool) {
                        case PICKER:
                            uidrawbutton( txpicker, z.picker, gui.tool4, 3 );
                            break;
                        case COPY:
                            uidrawbutton( txcopy, z.copy, gui.tool4, 3 );
                            break;
                        case MOVE:
                            uidrawbutton( txmove, z.move, gui.tool4, 3 );
                            break;
                        case FLIP:
                            uidrawbutton( txflip, z.flip, gui.tool4, 3 );
                            break;
                        case CLONE:
                            uidrawbutton( txClone, z.clone, gui.tool4, 3 );
                            break;
                    }

                    uidrawbutton( txbrush, z.brush, gui.tool5, 3 );
                    uidrawbutton( txundo, z.undo, gui.undo, 3.1f );
                    uidrawbutton( txredo, z.redo, gui.redo, 3.1f );
                } else if (mode == "object") {


                    str1draw( ui, shapeName, gui.objectpickermid );
                    uidrawbutton( txLeft, "", gui.objectpickerleft, 3 );
                    uidrawbutton( txRight, "", gui.objectpickerright, 3 );

                    if (magnet == 0) {
                        uidrawbutton( txunlock, z.grid, gui.lock, 3 );
                    } else {
                        uidrawbutton( txlock, z.grid, gui.lock, 3 );
                    }
                    //str1draw(ui, magnetName, 85,100,10);

                } else if (mode == "newpoly") {
                    uidrawbutton( txundo, z.undo, gui.undo, 3 );
                    str1draw( ui, z.ok, gui.tool );
                } else if (mode.equalsIgnoreCase( "image" )) {
                    str1draw( ui, z.properties, gui.objectpickermid );
                }

                //smaller yellow font for label

                str1.getData().setScale( .4f );
                str1.setColor( 1, 1, 0, 1 );
                if (mode == "tile" || mode == "object" || mode == "image") {
                    //   str1drawlabel(ui, z.activelayer, gui.layer);
                    if (mode == "tile") {
                        // str1drawbuttonlabel(ui, z.tilelayer, gui.layer);
                    } else if (mode == "object") {
                        //  str1drawbuttonlabel(ui, z.objectgroup, gui.layer);
                    } else if (mode == "image") {
                        //    str1drawbuttonlabel(ui, z.imagelayer, gui.layer);
                    }
                }


                if (mode == "tile") {
                    if (sShowFPS) str1drawbuttonlabel( ui, "FPS", gui.fps );

                    if (rotate < 4) {
                        str1drawbuttonlabel( ui, z.rotate, gui.rotation );
                    } else {
                        str1drawbuttonlabel( ui, z.flip, gui.rotation );
                    }
                }

                if (mode == "object") {
                    // str1drawbuttonlabel(ui, z.activeobjecttool, gui.objectpickermid);

                }

            }
            //turn it back to normal and end.

            ui.end();
        }
        str1.setColor( 1, 1, 1, 1 );
        str1.getData().setScale( 1f );


        Gdx.gl.glDisable( GL20.GL_BLEND );

    }

    //////////////////////////////////////////////////////
//            APPLICATION VOID
//////////////////////////////////////////////////////
    private void loadOpen() {
        tOpen = new Table();
        tOpen.setFillParent( true );
    }

    private void initSD() {
    }

    private void initErrorHandling() {
        Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                ErrorBung( (Exception) e, "errorlog.txt" );
                Gdx.net.openURI( "https://www.mirwanda.com/p/nottiled-crashed.html?m=1" );
                System.exit( 1 );
            }
        } );
    }

    private void ErrorBung(Exception e, String filenya) {
        filenya = "errorlog.txt";
        FileHandle file = Gdx.files.absolute( basepath+filenya );
        StringWriter sw = new StringWriter();
        e.printStackTrace( new PrintWriter( sw ) );
        String exceptionAsString = sw.toString();
        System.out.println( exceptionAsString );
        file.writeString( "\n\n" + exceptionAsString, true );

        saveMap( curdir + "/" + curfile + "_re.tmx" );
        prefs.putString( "lof", basepath+"NotTiled/" + "sample/island.tmx" );
        prefs.putString( "lastpath", basepath+"NotTiled/" );
        prefs.flush();

    }

    private void writeThis(String path, String isi) {
        FileHandle file = Gdx.files.absolute( path );
        StringWriter sw = new StringWriter();
        file.writeString( isi, false );
    }

    private void writeThisAbs(String path, String isi) {
        FileHandle file = Gdx.files.absolute( path );
        StringWriter sw = new StringWriter();
        file.writeString( isi, false );
    }

    private void loadGdxStuff() {
        Gdx.input.setCatchBackKey( true );
        Gdx.gl.glClearColor( 0.5f, 0.5f, 0.5f, 1f );
        Gdx.input.setInputProcessor( im );
        gd.setLongPressSeconds( .4f );
        int assx = Gdx.graphics.getWidth();
        int assy = Gdx.graphics.getHeight();
        if (assx < assy) {
            ssx = assx;
            ssy = assy;
        } else {
            ssx = assy;
            ssy = assx;
        }
        btnx = (int) (ssx * .9f);

        if (ssx < ssy) {
            btny = 100 * ssx / 1080;
        } else {
            btny = 100 * ssy / 1080;
        }

        txline = new Texture( Gdx.files.internal( "images/line.png" ) );
        txcircle = new Texture( Gdx.files.internal( "images/circle.png" ) );
        txpencil = new Texture( Gdx.files.internal( "images/pencil.png" ) );
        txfill = new Texture( Gdx.files.internal( "images/fill.png" ) );
        txcopy = new Texture( Gdx.files.internal( "images/copy.png" ) );
        txmove = new Texture( Gdx.files.internal( "images/move.png" ) );
        txflip = new Texture( Gdx.files.internal( "images/flip96.png" ) );
        txpicker = new Texture( Gdx.files.internal( "images/picker96.png" ) );
        txeraser = new Texture( Gdx.files.internal( "images/eraser.png" ) );
        txbrush = new Texture( Gdx.files.internal( "images/brush.png" ) );
        txlock = new Texture( Gdx.files.internal( "images/lock.png" ) );
        txunlock = new Texture( Gdx.files.internal( "images/unlock.png" ) );
        txnumbers = new Texture( Gdx.files.internal( "images/numbers.png" ) );
        txrectangle = new Texture( Gdx.files.internal( "images/rectangle.png" ) );
        txmenu = new Texture( Gdx.files.internal( "images/menu.png" ) );
        txsave = new Texture( Gdx.files.internal( "images/save.png" ) );
        txsave2 = new Texture( Gdx.files.internal( "images/save2.png" ) );
        txcenter = new Texture( Gdx.files.internal( "images/center.png" ) );
        txadd = new Texture( Gdx.files.internal( "images/add.png" ) );
        txdelete = new Texture( Gdx.files.internal( "images/delete.png" ) );
        txtiles = new Texture( Gdx.files.internal( "images/tile96.png" ) );
        txinfo = new Texture( Gdx.files.internal( "images/info.png" ) );
        txplay = new Texture( Gdx.files.internal( "images/play.png" ) );
        txmap = new Texture( Gdx.files.internal( "images/map.png" ) );
        txlayer = new Texture( Gdx.files.internal( "images/layer.png" ) );
        txundo = new Texture( Gdx.files.internal( "images/undo.png" ) );
        txredo = new Texture( Gdx.files.internal( "images/redo.png" ) );
        txtile = new Texture( Gdx.files.internal( "images/tile.png" ) );
        txauto = new Texture( Gdx.files.internal( "images/autotile.png" ) );


        txautopick = new Texture( Gdx.files.internal( "images/autopick.png" ) );
        txresources = new Texture( Gdx.files.internal( "images/resources.png" ) );
        txstamp = new Texture( Gdx.files.internal( "images/stamp.png" ) );
        txlauncher = new Texture( Gdx.files.internal( "images/ic_launcher.png" ) );
        txVisible = new Texture( Gdx.files.internal( "images/visible.png" ) );
        txInvisible = new Texture( Gdx.files.internal( "images/invisible.png" ) );

        txTypeTile = new Texture( Gdx.files.internal( "images/tiletype.png" ) );
        txTypeObject = new Texture( Gdx.files.internal( "images/object.png" ) );
        txTypeImage = new Texture( Gdx.files.internal( "images/image.png" ) );
        txTypeGroup = new Texture( Gdx.files.internal( "images/group.png" ) );
        txUp = new Texture( Gdx.files.internal( "images/up96.png" ) );
        txDown = new Texture( Gdx.files.internal( "images/down96.png" ) );
        txLeft = new Texture( Gdx.files.internal( "images/left96.png" ) );
        txRight = new Texture( Gdx.files.internal( "images/right96.png" ) );
        txClone = new Texture( Gdx.files.internal( "images/clone.png" ) );
        txOutline = new Texture( Gdx.files.internal( "images/outline.png" ) );
        txRectangle2 = new Texture( Gdx.files.internal( "images/rectangle2.png" ) );
        txEraser2 = new Texture( Gdx.files.internal( "images/eraser2.png" ) );


        if (ssx < ssy) {
            hstage = new Stage( new StretchViewport( ssy * 1.5f, ssx * 1.5f ) );
            vstage = new Stage( new StretchViewport( ssx, ssy ) );
            stage = new Stage( new StretchViewport( ssx, ssy ) );
            tilecam = new OrthographicCamera( ssx, ssy );
            uicam = new OrthographicCamera( ssx, ssy );
            cam = new OrthographicCamera( ssx, ssy );
            gamecam = new OrthographicCamera( ssx, ssy );
            minicam = new OrthographicCamera( 11, 20 );
        } else {
            hstage = new Stage( new StretchViewport( ssx * 1.5f, ssy * 1.5f ) );
            vstage = new Stage( new StretchViewport( ssy, ssx ) );
            stage = new Stage( new StretchViewport( ssy, ssx ) );
            tilecam = new OrthographicCamera( ssy, ssx );
            uicam = new OrthographicCamera( ssy, ssx );
            cam = new OrthographicCamera( ssy, ssx );
            gamecam = new OrthographicCamera( ssy, ssx );
            minicam = new OrthographicCamera( 20, 11 );

        }
        minicam.zoom = 10f;
        minicam.update();


        batch = new SpriteBatch( 8191 );
        fbo = new FrameBuffer( Pixmap.Format.RGBA8888, ssx, ssy, false );
        ui = new SpriteBatch();
        uis = new myShapeRenderer();
        sr = new ShapeRendererPlus();
        //dialog=new Dialog(z.info,skin,"");
        //str1 = new BitmapFont();

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FileHandle fileHandl = Gdx.files.internal( "languages/characters" );
        Map<String, String> vars = new HashMap<String, String>();
        String allstr = fileHandl.readString();
        String[] cumi = allstr.split( "\r\n" );
        for (int ad = 0; ad < cumi.length; ad++) {
            String[] cuma = cumi[ad].split( ">>>" );
            vars.put( cuma[0], cuma[1] );
        }
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS+ vars.get( language );
        parameter.borderColor = new Color( .5f, .5f, .5f, .9f );
        parameter.borderWidth = 0;
        if (ssx < ssy) {
            if (fontsize == 0) fontsize = 48 * ssx / 1080;

        } else {
            if (fontsize == 0) fontsize = 48 * ssy / 1080;

        }
        parameter.size = fontsize;
        parameter.shadowColor = new Color( 0f, 0f, 0f, .9f );
        parameter.shadowOffsetY = 4;
        FreeTypeFontGenerator generator = null;

        String filenam = "font.ttf";
        if (language.equalsIgnoreCase( "Chinese" )) {
            filenam = "chinese.ttf";
        }
        if (language.equalsIgnoreCase( "Japanese" )) {
            filenam = "japanese.otf";
        }


        if (sCustomFont.equalsIgnoreCase( "" )) {
            generator = new FreeTypeFontGenerator( Gdx.files.internal( filenam ) );
        } else {
            try {
                generator = new FreeTypeFontGenerator( Gdx.files.absolute( sCustomFont ) );
            } catch (Exception e) {
                generator = new FreeTypeFontGenerator( Gdx.files.internal( filenam ) );
            }
        }
        generator.setMaxTextureSize( 99999 );
        str1 = generator.generateFont( parameter );
        generator.dispose();

        skin = new Skin();
        skin.add( "font", str1, BitmapFont.class );

        FileHandle fileHandle = Gdx.files.internal( "skins/holo/Holo-dark-hdpi.json" );
        FileHandle atlasFile = fileHandle.sibling( "Holo-dark-hdpi.atlas" );

        if (atlasFile.exists()) {
            skin.addRegions( new TextureAtlas( atlasFile ) );
        }

        skin.load( fileHandle );
    }

    public void checkAndReloadSamples() {

        if (!isSampleReloaded) {
            FileHandle fh2 = Gdx.files.absolute( basepath+"NotTiled/" );
            if (!fh2.exists()) fh2.mkdirs();
            FileHandle fh3 = Gdx.files.absolute( basepath+"NotTiled/sample" );
            if (fh3.exists() && !fh3.isDirectory()) fh3.delete();
            FileHandle fhc = Gdx.files.absolute( basepath+"NotTiled/sample/island.tmx" );
            if (!fhc.exists()) {

                FileHandle from = Gdx.files.internal( "sample.zip" );
                FileHandle to = Gdx.files.absolute( basepath+"NotTiled" );
                from.copyTo( to );
                FileHandle zipo = Gdx.files.absolute( basepath+"NotTiled/sample.zip" );
                unzip( zipo, to );

                prefs.putString( "lof", basepath+"NotTiled/sample/island.tmx" ).flush();
                lastpath = basepath+"NotTiled/sample";
                prefs.putString( "lastpath", lastpath ).flush();
            }
            isSampleReloaded = true;
            prefs.putBoolean( "reloaded", isSampleReloaded ).flush();
        }
    }

    public void justReloadSamples() {
        FileHandle fh2 = Gdx.files.absolute( basepath+"NotTiled/" );
        if (!fh2.exists()) fh2.mkdirs();
        FileHandle fh3 = Gdx.files.absolute( basepath+"NotTiled/sample" );
        if (fh3.exists() && !fh3.isDirectory()) fh3.delete();

        FileHandle from = Gdx.files.internal( "sample.zip" );
        FileHandle to = Gdx.files.absolute( basepath+"NotTiled" );
        from.copyTo( to );
        FileHandle zipo = Gdx.files.absolute( basepath+"NotTiled/sample.zip" );
        unzip( zipo, to );
        // zipo.delete();
//            FileHandle from = Gdx.files.internal("sample/");
        //          from.copyTo(Gdx.files.absolute("NotTiled/"));
        prefs.putString( "lof", basepath+"NotTiled/sample/island.tmx" );
        lastpath = basepath+"NotTiled/sample";
        prefs.putString( "lastpath", lastpath );
        prefs.flush();
    }

    public void unzip(FileHandle src, FileHandle dest) {

        // create output directory if it doesn't exist
        if (!dest.exists()) dest.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream( src.file() );
            ZipInputStream zis = new ZipInputStream( fis );
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {

                String fileName = ze.getName();
                FileHandle newFile = Gdx.files.absolute( dest.path() + File.separator + fileName );
                System.out.println( "Unzipping to " + newFile.path() );

                if (ze.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    FileOutputStream fos = new FileOutputStream( newFile.file() );
                    int len;
                    while ((len = zis.read( buffer )) > 0) {
                        fos.write( buffer, 0, len );
                    }
                    fos.close();
                }
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            ErrorBung( e, "errorlog.txt" );
        }

    }

    public void initializeThings() {
        //newtmxfile(false);
        try {

            FileHandle fh1 = Gdx.files.absolute( basepath+"NotTiled/noads.txt" );
            if (fh1.exists()) {
                bypassads = true;
            }


            checkAndReloadSamples();
            log("checking samples...");
            if (!intend.isEmpty()) {
                String raw = intend.substring( 7 );
                try {
                    raw = java.net.URLDecoder.decode( raw, "UTF-8" );
                } catch (UnsupportedEncodingException e) {
                }
                loadtmx( raw );
                return;
            }
            String lf = prefs.getString( "lof", basepath+"NotTiled/sample/island.tmx" );
            log(lf);
            FileHandle tst = Gdx.files.absolute( lf );

            if (tst.exists()) {
                log("Exist.");
                loadtmx( lf );

            } else {
                log("Does not exist.");
                newtmxfile( false );
                log("New tmx ok.");
            }
        } catch (Exception e) {
            status( "Error, check storage permission.", 5 );
        }

    }

    public void newtmxfile(boolean user) {
        stamp=false;
        assemblymode=false;
        loadingfile = true;
        selLayer = 0;
        background = null;
        if (user) {
            curdir = fNCurdir.getText();
            curdir = curdir.replace( basepath+"//", "/" );
            curfile = fNFilename.getText();
            Tw = Integer.parseInt( fNTw.getText() );
            Th = Integer.parseInt( fNTh.getText() );
            Tsw = Integer.parseInt( fNTsw.getText() );
            Tsh = Integer.parseInt( fNTsh.getText() );
            renderorder = sbNMapRenderOrder.getSelected().toString();
            mapFormat = sbNMapFormat.getSelected().toString();
            orientation = sbNMapOrientation.getSelected().toString();
        } else {
            curdir = basepath+"NotTiled/";
            curfile = "map01.tmx";
            Tw = 10;
            Th = 10;
            Tsw = 20;
            Tsh = 20;
            mapFormat = "csv";
            renderorder = "right-down";
            orientation = "orthogonal";
            log("newtmx?");
        }
        undolayer.clear();
        redolayer.clear();
        cliplayer = null;
        clipsource = 0;
        String isi = "";
        String prName = "";
        String prValue = "";
        int lastPid = 0;
        selgroup = 0;
        selLayer = 0;
        templastID = 1;
        seltset = 0;
        layers.clear();
        properties.clear();
        tilesets.clear();
        autotiles.clear();

        int curgroupid = -1;
        int curobjid = -1;
        curid = 1;
        layer l = new layer();
        l.setType( layer.Type.TILE );
        l.setVisible( true );
        l.setName( z.layer + " 1" );
        java.util.List<Long> ls = new ArrayList<Long>();
        java.util.List<Integer> lts = new ArrayList<Integer>();

        for (long i = 0; i < Tw * Th; i++) {
            ls.add( (long) 0 );
            lts.add( -1 );
        }


        l.setStr( ls );
        l.setTset( lts );
        layers.add( l );

        kartu = "world";
        mode = "tile";
        curspr = 0;
        cam.position.set( Tsw * Tw / 2, -Tsh * Th / 2, 0 );
        cam.zoom = .5f;
        cam.update();
        panTo( Tsw * Tw / 2, -Tsh * Th / 2, .25f, 1f );


        //loadautotiles();
        resetCaches();
        resetSwatches();
        //uicam.zoom=0.5f;
        //uicam.update();
        firstload = loadtime;
        resetMinimap();
        resetcam( false );
        loadingfile = false;
    }

    public void newtmxfileplus(boolean user) {
        loadingfile = true;
        stamp=false;
        assemblymode=false;

        String faths = basepath+"NotTiled/sample/template/" + ltemplate.getSelected();
        FileHandle fh = Gdx.files.absolute( faths );
        if (!fh.exists()) {
            msgbox( "Template file not found. Please redownload." );
            return;
        }
        loadtmxnewplus( faths );
        resetSwatches();


    }

    private void showtsetselection(final java.util.List<tileset> tset) {
        bigman = new Table();
        bigman.setFillParent( true );
        Table tsetsel = new Table();
        tsetsel.defaults().width( btnx ).height( btny ).padBottom( 2 );
        ScrollPane sps = new ScrollPane( tsetsel );
        bigman.add( sps );
        tsetsel.add( new Label( z.selecttilesets, skin ) ).row();
        cball = new CheckBox( z.selectall, skin );
        cball.setChecked( true );
        cball.align( Align.left );
        tsetsel.add( cball ).width( btnx ).left().align( Align.left ).row();
        newcb = new CheckBox[tset.size()];

        for (int i = 0; i < tset.size(); i++) {
            newcb[i] = new CheckBox( tset.get( i ).getName(), skin );
            newcb[i].setChecked( true );
            newcb[i].align( Align.left );
            tsetsel.add( newcb[i] ).width( btnx ).left().align( Align.left ).row();
        }
        TextButton okey = new TextButton( z.apply, skin );

        okey.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = tset.size() - 1; i >= 0; i--) {
                    if (!newcb[i].isChecked()) {
                        tset.remove( i );
                    }
                }

                if (tset == tilesets2) {
                    for (tileset t : tilesets2) {
                        t.setFirstgid( requestGid() );
                        tilesets.add( t );
                    }
                }
                CacheAllTset();
                resetCaches();
                backToMap();
                cue( "applytemplate" );
            }
        } );
        tsetsel.add( okey ).row();

        cball.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (cball.isChecked()) {
                    for (int i = 0; i < tset.size(); i++) {
                        newcb[i].setChecked( true );
                    }
                } else {
                    for (int i = 0; i < tset.size(); i++) {
                        newcb[i].setChecked( false );
                    }
                }
                //CacheAllTset();
            }
        } );

        gotoStage( bigman );

    }

    public void loadTemplate() {
        //
        bTmplBack = new TextButton( z.back, skin );
        bTmplOK = new TextButton( z.ok, skin );
        bTmplDownload = new TextButton( z.download, skin );
        ltemplate = new com.badlogic.gdx.scenes.scene2d.ui.List<String>( skin );
        ScrollPane spok = new ScrollPane( ltemplate );

        bTmplBack.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!cue( "usetemplateback" ) && lockUI) return;
                gotoStage( tNewFile );
            }
        } );

        bTmplOK.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!softcue( "usetemplateok" ) && lockUI) return;
                if (ltemplate.getSelectedIndex() < 0) return;
                if (fNTsh.getText() == null) fNTsh.setText( "20" );
                if (fNTsw.getText() == null) fNTsw.setText( "20" );
                if (fNTh.getText() == null) fNTh.setText( "20" );
                if (fNTw.getText() == null) fNTw.setText( "20" );
                prefs.putString( "Tsw", fNTsw.getText() );
                prefs.putString( "Tsh", fNTsh.getText() );
                prefs.putString( "Tw", fNTw.getText() );
                prefs.putString( "Th", fNTh.getText() );
                prefs.flush();
                newtmxfileplus( true );

            }
        } );

        bTmplDownload.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!cue( "usetemplatedownload" ) && lockUI) return;
                gotoStage( tOnline );
                refreshOnline();
            }
        } );

        tTemplate = new Table();
        tTemplate.setFillParent( true );
        tTemplate.defaults().width( btnx ).padBottom( 2 );
        tTemplate.add( new Label( z.selecttemplate, skin ) ).padTop( 10 ).row();
        tTemplate.add( spok ).height( 0.45f * ssy ).row();
        tTemplate.add( bTmplOK ).row();
        tTemplate.add( bTmplDownload ).row();
        tTemplate.add( bNNew ).row();

        tTemplate.add( bTmplBack ).row();
    }

    public void refreshTemplate() {
        String[] srr = new String[]{};
        FileHandle del = Gdx.files.absolute( basepath+"NotTiled/sample/template/" );

        FileHandle[] handle = del.list();

        if (handle.length > 0) {
            int ia = 0;
            for (FileHandle file : handle) {
                if (file.isDirectory()) {
                    ia += 1;
                }
            }
            srr = new String[ia];
            int i = 0;
            for (FileHandle file : handle) {
                if (file.isDirectory()) {
                    srr[i] = file.name();
                    i += 1;
                }
            }

        }
        ltemplate.setItems( srr );

        if (ltemplate.getItems().size > 0) {
            ltemplate.setSelectedIndex( 0 );
        }
    }

    public void refreshOnline() {
        String[] srr = new String[]{};
        lonline.setItems( srr );
        Net.HttpRequest request = new Net.HttpRequest( Net.HttpMethods.GET );
        request.setUrl( "https://www.dropbox.com/s/d114sswkkv64vs6/test.txt?dl=1" );
        Gdx.net.sendHttpRequest( request, new com.badlogic.gdx.Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final FileHandle tmpFile = FileHandle.tempFile( "model" );
                tmpFile.write( httpResponse.getResultAsStream(), false );
                Gdx.app.postRunnable( new Runnable() {
                    @Override
                    public void run() {
                        //String sss= tmpFile.readString();
                        //msgbox(sss);
                        try {
                            templates = new Online();

                            Json json = new Json();
                            templates = json.fromJson( Online.class, tmpFile );

                            String[] srr = new String[templates.getTemplates().size()];
                            for (int i = 0; i < templates.getTemplates().size(); i++) {
                                String nem = templates.getTemplates().get( i ).getName();
                                FileHandle del = Gdx.files.absolute( basepath+"NotTiled/sample/template/" + nem );
                                if (del.exists()) {
                                    srr[i] = nem + " (" + z.saved + ")";
                                } else {
                                    srr[i] = nem + " *" + z.newnew + "*";
                                }
                            }

                            lonline.setItems( srr );
                            if (lonline.getItems().size > 0) {
                                lonline.setSelectedIndex( 0 );
                            }

                        } catch (Exception e) {
                            ErrorBung( e, "errorlog.txt" );
                        }
                    }
                } );
            }

            @Override
            public void failed(Throwable t) {
                msgbox( z.checkinternet );
            }

            @Override
            public void cancelled() {
                msgbox( z.downloadcancel );
            }
        } );

    }

    public void downloadTemplate() {
        if (lonline.getSelectedIndex() < 0) return;
        final String foldname = templates.getTemplates().get( lonline.getSelectedIndex() ).getName();
        final String lonk1 = templates.getTemplates().get( lonline.getSelectedIndex() ).getTemplate();
        final String lonk2 = templates.getTemplates().get( lonline.getSelectedIndex() ).getExtension();
        final String extName = templates.getTemplates().get( lonline.getSelectedIndex() ).getExtension_name();

        FileHandle fh = Gdx.files.absolute( basepath+"NotTiled/sample/template/" + foldname );
        if (!fh.exists()) fh.mkdirs();

        if (lonk2 != null) {
            Net.HttpRequest request2 = new Net.HttpRequest( Net.HttpMethods.GET );
            request2.setUrl( lonk2 );
            Gdx.net.sendHttpRequest( request2, new com.badlogic.gdx.Net.HttpResponseListener() {

                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    final FileHandle tmpFile = FileHandle.tempFile( "mode3" );
                    tmpFile.write( httpResponse.getResultAsStream(), false );
                    Gdx.app.postRunnable( new Runnable() {
                        @Override
                        public void run() {

                            try {
                                templates = new Online();
                                FileHandle fh = Gdx.files.absolute( basepath+"NotTiled/sample/template/" + foldname + "/" + extName );
                                fh.write( tmpFile.read(), false );
                            } catch (Exception e) {
                                ErrorBung( e, "errorlog.txt" );
                            }
                        }
                    } );
                }

                @Override
                public void failed(Throwable t) {
                    msgbox( z.checkinternet );
                }

                @Override
                public void cancelled() {
                    msgbox( z.downloadcancel );
                }
            } );
        }

        Net.HttpRequest request = new Net.HttpRequest( Net.HttpMethods.GET );
        request.setUrl( lonk1 );
        Gdx.net.sendHttpRequest( request, new com.badlogic.gdx.Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final FileHandle tmpFile = FileHandle.tempFile( "mode2" );
                tmpFile.write( httpResponse.getResultAsStream(), false );
                Gdx.app.postRunnable( new Runnable() {
                    @Override
                    public void run() {
                        String sss = tmpFile.readString();

                        try {
                            templates = new Online();
                            FileHandle fh = Gdx.files.absolute( basepath+"NotTiled/sample/template/" + foldname + "/template.tmx" );
                            fh.writeString( sss, false );
                            refreshOnline();
                            msgbox( z.downloadcomplete );
                            downloading = false;

                        } catch (Exception e) {
                            ErrorBung( e, "enyoh.txt" );
                        }
                    }
                } );
            }

            @Override
            public void failed(Throwable t) {
                msgbox( z.checkinternet );
                downloading = false;
            }

            @Override
            public void cancelled() {
                msgbox( z.downloadcancel );
                downloading = false;
            }
        } );
    }

    public void loadExport() {
        tExport = new Table();
        tExport.setFillParent( true );
        tExport.defaults().width( btnx ).padBottom( 2 );
        fExportFilename = new TextField( "export", skin );
        TextButton totemplate = new TextButton( z.exportastemplate, skin );
        TextButton topng = new TextButton( z.exporttopng, skin );
        TextButton astileset = new TextButton( z.exportastileset, skin );
        TextButton selastset = new TextButton( z.selectionastileset, skin );
        TextButton tolua = new TextButton( z.exporttolua, skin );
        TextButton tojson = new TextButton( z.exporttojson, skin );
        TextButton tomidi = new TextButton( z.exporttomidi, skin );
        TextButton towav = new TextButton( z.recordwav, skin );
        TextButton toback = new TextButton( z.back, skin );
        tExport.add( new Label( z.filename, skin ) ).row();
        tExport.add( fExportFilename ).row();
        tExport.add( totemplate ).row();
        tExport.add( selastset ).row();
        tExport.add( topng ).row();
        tExport.add( astileset ).row();
        tExport.add( tolua ).row();
        tExport.add( tojson ).row();
        tExport.add( tomidi ).row();
        tExport.add( towav ).row();
        tExport.add( toback ).row();

        totemplate.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exportastemplate( fExportFilename.getText() );
                cue( "exportastemplate" );
            }
        } );



        topng.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exporttopng( fExportFilename.getText() );
                cue( "exporttopng" );

            }
        } );

        selastset.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectionastset( fExportFilename.getText() );
                cue( "selastset" );

            }
        } );

        astileset.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exportastileset( fExportFilename.getText() );
            }
        } );

        tolua.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exporttolua( fExportFilename.getText() );
            }
        } );

        tojson.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exporttojson( fExportFilename.getText() );
            }


        } );

        tomidi.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exporttomidi( fExportFilename.getText() );
            }


        } );
        towav.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                recordwav( fExportFilename.getText() );
            }


        } );

        toback.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        } );
    }

    public void recordAudio(int wavwidth, final String filenamenya) {
        final int samples = 22000;
        boolean isMono = true;
        final short[] data = new short[samples * (wavwidth)];

        final AudioRecorder recorder = Gdx.audio.newAudioRecorder( samples, isMono );
        final AudioDevice player = Gdx.audio.newAudioDevice( samples, isMono );
        //Sound sfxlock =  Gdx.audio.newSound(Gdx.files.absolute("test.wav"));
        //sfxlock.play();
        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println( "Record: Start" );

                recorder.read( data, 0, data.length );
                recorder.dispose();
                System.out.println( "Record: End" );

                System.out.println( "Saving File..." );
                FileHandle fh = Gdx.files.absolute( curdir + "/" + filenamenya + ".wav" );
                try {
                    OutputStream out = new FileOutputStream( fh.file() );
                    PCMtoFile( out, data, samples, 1, 16 );
                    System.out.println( "Saved." );
                } catch (Exception e) {
                    ErrorBung( e, "waveerrror.txt" );
                    System.out.println( "Saving Error." );
                }
            }
        } ).start();
    }

    public void loadImport() {

        bImportOK = new TextButton( z.ok, skin );
        TextButton bImportImageSize = new TextButton( z.imagesize, skin );
        TextButton bImportTileSize = new TextButton( z.tilesize, skin );
        TextButton bImportPowerOfTwo = new TextButton( z.powerof2, skin );

        bImportOK.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    loadingfile = true;
                    errors = " ";
                    if (fImportWidth.getText().equalsIgnoreCase( "" )) {
                        fImportWidth.setText( Tsw + "" );
                    }
                    if (fImportHeight.getText().equalsIgnoreCase( "" )) {
                        fImportHeight.setText( Tsh + "" );
                    }
                    if (thefile.file().getName().toLowerCase().contains( ".tsx" )) {
                        loadtsx( openedfile, tilesets, curdir );
                    } else {
                        addImageTset( thefile );
                    }

                    CacheAllTset();
                    loadingfile = false;


                    seltset = tilesets.size() - 1;
                    onToPicker();
                    recenterpick();
                    cue( "importtilesetok" );
                } catch (Exception e) {
                    loadingfile = false;
                    onToPicker();
                }

                if (errors != " ") {
                    status( errors, 5 );
                } else {
                    cue( "tilesetadded" );
                }
            }
        } );

        bImportTileSize.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fImportHeight.setText( Tsh + "" );
                fImportWidth.setText( Tsw + "" );
            }
        } );

        bImportImageSize.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (thefile.file().getName().toLowerCase().contains( ".tsx" )) {
                    msgbox( "TSX" );
                } else {

                    try {
                        File f = thefile.file();
                        SimpleImageInfo s = new SimpleImageInfo( f );
                        fImportHeight.setText( s.getHeight() + "" );
                        fImportWidth.setText( s.getWidth() + "" );
                    } catch (Exception e) {
                        msgbox( e.toString() );
                    }


                }
            }
        } );

        bImportPowerOfTwo.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!isPowerOf2( Integer.parseInt( fImportHeight.getText() ) )) {
                    fImportHeight.setText( 16 + "" );
                    fImportWidth.setText( 16 + "" );
                } else {
                    int thenum = Integer.parseInt( fImportHeight.getText() );
                    if (thenum >= 256) {
                        fImportHeight.setText( 16 + "" );
                        fImportWidth.setText( 16 + "" );
                    } else {
                        thenum = thenum * 2;
                        fImportHeight.setText( thenum + "" );
                        fImportWidth.setText( thenum + "" );
                    }
                }

            }
        } );

        fImportWidth = new TextField( "", skin );
        fImportHeight = new TextField( "", skin );
        fImportWidth.setTextFieldFilter( tffint );
        fImportHeight.setTextFieldFilter( tffint );
        cImportEmbed = new CheckBox( z.embedtileset, skin );

        tImport = new Table();
        tImport.setFillParent( true );
        tImport.defaults().width( btnx ).padBottom( 2 );
        tImport.add( new Label( z.importtileset, skin ) ).padTop( 10 ).colspan( 2 ).row();

        tImport.add( bImportTileSize ).colspan( 2 ).row();
        tImport.add( bImportImageSize ).colspan( 2 ).row();
        tImport.add( bImportPowerOfTwo ).colspan( 2 ).row();

        tImport.add( new Label( z.tilewidth, skin ) ).width( btnx / 2 ).padTop( 10 );
        tImport.add( fImportWidth ).width( btnx / 2 ).row();
        tImport.add( new Label( z.tileheight, skin ) ).width( btnx / 2 ).padTop( 10 );
        tImport.add( fImportHeight ).width( btnx / 2 ).row();
        tImport.add( cImportEmbed ).colspan( 2 ).row();
        tImport.add( bImportOK ).colspan( 2 ).row();


    }

    public void loadOnline() {
        bOnlineBack = new TextButton( z.back, skin );
        bOnlineRefresh = new TextButton( z.refresh, skin );
        bOnlineDownload = new TextButton( z.dls, skin );
        lonline = new com.badlogic.gdx.scenes.scene2d.ui.List<String>( skin );
        ScrollPane spok = new ScrollPane( lonline );

        bOnlineBack.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tTemplate );
                refreshTemplate();
            }
        } );

        bOnlineRefresh.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lonline.clearItems();
                refreshOnline();
            }
        } );

        bOnlineDownload.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (downloading) {
                    msgbox( z.downloading );
                    return;
                }
                downloading = true;
                downloadTemplate();
                //gotoStage(tOnline);
            }
        } );

        tOnline = new Table();
        tOnline.setFillParent( true );
        tOnline.defaults().width( btnx ).padBottom( 2 );
        tOnline.add( new Label( z.onlinetemplate, skin ) ).padTop( 10 ).row();
        tOnline.add( spok ).height( 0.5f * ssy ).row();
        tOnline.add( bOnlineDownload ).row();
        tOnline.add( bOnlineRefresh ).row();
        tOnline.add( bOnlineBack ).row();

    }

    public void loadListener() {
        listBack = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        };
    }

    public void loadMenuMap() {
        bLicense = new TextButton( z.license, skin );
        bNew = new TextButton( z.newfile, skin );
        bOpen = new TextButton( z.open, skin );
        bSave = new TextButton( z.save, skin );
        bSaveAs = new TextButton( z.saveas, skin );
        bTutorial = new TextButton( z.tutorial, skin );
        bTutorBack = new TextButton( z.back, skin );
        bTutorOK = new TextButton( z.ok, skin );

        bExporter = new TextButton( z.export, skin );
        bDiscord = new TextButton( z.discordserver, skin );
        bWhatsapp = new TextButton( z.whatsappgroup, skin );
        bPatreon = new TextButton( z.supportnottiled, skin );
        bPatreon2 = new TextButton( z.supportnottiled, skin );
        credito = new TextButton( z.credits, skin );
        bBackground = new TextButton( z.background, skin );
        bCollaboration = new TextButton( z.collaboration, skin );
        bReload = new TextButton( z.reloadsamples, skin );
        bCopyto = new TextButton( z.copytorustedwarfare, skin );
        bRusted = new TextButton( "Rusted Warfare", skin );
        bWardate = new TextButton( "Rusted WarDate", skin );
        bManual = new TextButton( z.manualbook, skin );
        bVideos = new TextButton( z.videotutorials, skin );

        tRecent = new Table();
        tRecent.setFillParent( true );
        tRecent.defaults().width( btnx ).padBottom( 2 );
        bRecent = new TextButton( z.recentfile, skin );
        bRecentOpen = new TextButton( z.open, skin );
        bRecentBack = new TextButton( z.back, skin );
        lrecentlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>( skin );
        ScrollPane spok = new ScrollPane( lrecentlist );
        tRecent.add( spok ).height( 0.7f * ssy ).row();
        tRecent.add( bRecentOpen ).row();
        tRecent.add( bRecentBack );
        try {
            recents = new recents();
            Json json = new Json();
            FileHandle f = Gdx.files.absolute( basepath+"NotTiled/recents.json" );
            log("recent loaded");
            recents = json.fromJson( recents.class, f );
        } catch (Exception e) {
            log("recent error");
        }

        tTutorial = new Table();
        tTutorial.setFillParent( true );
        tTutorial.defaults().width( btnx ).padBottom( 2 );
        ltutorial = new com.badlogic.gdx.scenes.scene2d.ui.List<String>( skin );
        ScrollPane spoki = new ScrollPane( ltutorial );
        tTutorial.add( spoki ).height( 0.7f * ssy ).row();
        tTutorial.add( bTutorOK ).row();
        tTutorial.add( bTutorBack );

        bLinks = new TextButton( z.links, skin );

        bExit = new TextButton( z.exit, skin );
        bBack = new TextButton( z.back, skin );

        bFeedback = new TextButton( z.sendfeedback, skin );
        bPreference = new TextButton( z.preferences, skin );
        bProperties = new TextButton( z.mapproperties, skin );
        bTileMgmt = new TextButton( z.layer, skin );
        bTsetMgmt = new TextButton( z.tileset, skin );
        bAutoMgmt = new TextButton( z.macro, skin );
        bUIEditor = new TextButton( z.uieditor, skin );
        bTools = new TextButton( z.tools, skin );


        ///////
		/*
		TextButton bMains = new TextButton("Menu",skin);
		bMains.setPosition(ssx/2,ssy/2);
		stage.addActor(bMains);
		Gdx.input.setInputProcessor(stage);
		bMains.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					MoveByAction ma = new MoveByAction();
					ma.setAmountY(-100);
					ma.setDuration(1);
					ma.setInterpolation(Interpolation.fastSlow);
					actor.addAction(ma);
				}
			});
		*/
        /////

        bRecentBack.addListener( listBack );
        bTutorBack.addListener( listBack );

        bBackground.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog( z.openimagefile, "background", "file", new String[]{".png", ".bmp", ".jpg", ".jpeg", ".gif"}, null );
            }
        } );

        credito.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Json json = new Json();
                //writeThis( "testtut.json", json.prettyPrint( tutor ) );
                showCredits();
            }
        } );

        bReload.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                justReloadSamples();
                msgbox( z.sampleshasbeenreloaded );

            }
        } );

        bCopyto.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                copyToRW();

            }
        } );


        bBack.addListener( listBack );
        bBack2 = new TextButton( z.back, skin );
        bBack2.addListener( listBack );
        bBack3 = new TextButton( z.back, skin );
        bBack3.addListener( listBack );
        bExit.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        } );

        bLinks.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setLinksMap();
                gotoStage( tLinks );
                cue( "links" );
            }
        } );

        bCollaboration.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tCollab );
                cue( "collab" );
            }
        } );

        bTutorial.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tTutorial );
            }
        } );

        bNew.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lastpath = prefs.getString( "lastpath", basepath+"NotTiled" );

                fNCurdir.setText( lastpath );
                fNTsw.setText( prefs.getString( "Tsw", "20" ) );
                fNTsh.setText( prefs.getString( "Tsh", "20" ) );
                fNTw.setText( prefs.getString( "Tw", "20" ) );
                fNTh.setText( prefs.getString( "Th", "20" ) );
                sbNMapFormat.setSelected( "base64-gzip" );

                gotoStage( tNewFile );
                cue( "new" );
            }
        } );

        bOpen.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog( z.opentmxfile, "open", "file", new String[]{".tmx", ".ntp"}, null );
            }
        } );

        bLicense.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tLicense );
            }
        } );

        bExporter.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tExport );
                cue( "export" );
            }
        } );


        bRecent.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tRecent );
                lrecentlist.setItems();

                java.util.List<String> spt = new ArrayList<String>();
                for (int i = recents.getPaths().size() - 1; i >= 0; i--) {
                    if (recents.getPaths().get(i)!=null) {
                        spt.add( recents.getPaths().get( i ) );
                    }
                }
                lrecentlist.setItems( spt.toArray( new String[0] ) );
            }
        } );

        bRecentOpen.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileHandle file = Gdx.files.absolute( lrecentlist.getSelected() );
                if (file.extension().equalsIgnoreCase( "ntp" )) {
                    backToMap();
                    String curdir = file.parent().path();
                    FileHandle tmpfolder = Gdx.files.absolute( basepath+"NotTiled/Temp" );
                    unzip( file, tmpfolder );
                    recents.addrecent( file.path() );
                    saveRecents();
                    playgame( basepath+"NotTiled/Temp", "index.tmx" );
                } else {
                    loadtmx( lrecentlist.getSelected() );
                    backToMap();
                }

            }
        } );


        bSave.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveMap( curdir + "/" + curfile );
                cue( "save" );
                msgbox( z.yourmaphasbeensaved );
                if (!bypassads) face.showinterstitial();
            }
        } );

        bSaveAs.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog( z.selectnewlocation, "saveas", "dir", new String[]{}, tMenu );
            }
        } );

        bDiscord.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://discord.gg/pZBGBKr" );
            }
        } );

        bRusted.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://play.google.com/store/apps/details?id=com.corrodinggames.rts" );
            }
        } );

        bWardate.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://play.google.com/store/apps/details?id=com.Black.Label.Apps.Ruster.WarDate" );
            }
        } );

        bManual.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://www.mirwanda.com/2019/03/nottiled-manual-book.html?m=1" );
            }
        } );

        bFeedback.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "mailto:reza@mirwanda.com?subject=NotTiled%20feedback" );
            }
        } );

        bVideos.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://www.youtube.com/playlist?list=PLaZhehDwQZIKlNPsMKqR3YRYxvdWctWt9" );
            }
        } );

        bUIEditor.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                assemblymode=false;
                stamp=false;
                backToMap();
                loadInterface( "custominterface.json" );
                kartu = "editor";
            }
        } );

        bManualCN = new TextButton( z.checkupdate, skin );
        bManualCN.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (language) {
                    case "Chinese":
                        Gdx.net.openURI( "https://files.surely.my.id/index.php/s/9WD4DqmgeMoAgMe" );
                        break;
                    default:
                        Gdx.net.openURI( "https://play.google.com/store/apps/details?id=com.mirwanda.nottiled" );

                }
            }
        } );

        bWhatsapp.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI( "https://chat.whatsapp.com/LnZ74s758mTJnBu1ClKUA6" );
            }
        } );

        bPatreon.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //if (!face.buyadfree()) {
                    Gdx.net.openURI( "https://www.mirwanda.com" );
                //}
            }
        } );
        bPatreon.setColor( 0, 1, 0, 1 );

        bPatreon2.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //if (!face.buyadfree()) {
                    Gdx.net.openURI( "https://www.mirwanda.com" );
                //}
            }
        } );
        bPatreon2.setColor( 0, 1, 0, 1 );

        bTools.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( ttools );
            }
        } );

        bTutorial.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( tTutorial );
                ltutorial.setItems();

                java.util.List<String> spt = new ArrayList<String>();
                for (int i = 0; i < tutor.getT().size(); i++) {
                    spt.add( tutor.getT().get( i ).getName() );
                }
                ltutorial.setItems( spt.toArray( new String[0] ) );

            }
        } );

        bTutorOK.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();

                tutoring = true;
                activetutor = ltutorial.getSelectedIndex();
                tutor.getT().get( activetutor ).reset();
                cue( "start" );


            }
        } );

        pSaveAs = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }
                curdir = saveasdir;
                curfile = input;
                saveMap( saveasdir + "/" + input );
            }

            @Override
            public void canceled() {
            }

        };


        setMenuMap();
        setMapMap();
        setLinksMap();
    }

    private void copyToRW() {
        FileHandle fl = Gdx.files.absolute( rwpath );
        if (!fl.exists()) fl.mkdirs();
        FileHandle fla = Gdx.files.absolute( rwpath + "/maps" );
        if (!fla.exists()) {
            fla.mkdirs();
        }
        saveMap( curdir + "/" + curfile );
        FileHandle from = Gdx.files.absolute( curdir + "/" + curfile );
        from.copyTo( Gdx.files.absolute( rwpath + "/maps" ) );

        createRWthumbnail( curfile.substring( 0, curfile.length() - 4 ) + "_map" );
        FileHandle from2 = Gdx.files.absolute( curdir + "/" + curfile.substring( 0, curfile.length() - 4 ) + "_map.png" );
        if (from2.exists())
            from2.copyTo( Gdx.files.absolute( rwpath + "/maps" ) );

        status( z.mapsenttorustedwatfare ,2);
        cue( "copytorw" );
    }

    private void setLinksMap() {
        if (landscape) {
            tLinks = new Table();
            tLinks.setFillParent( true );
            tLinks.defaults().width( btnx ).height( btny + 2 ).padBottom( 2 );
            Table tLinks2 = new Table();
            tLinks2.defaults().width( btnx ).height( btny * 2 ).padBottom( 2 );
            Table tLinks1 = new Table();
            tLinks1.defaults().width( btnx ).height( btny * 2 ).padBottom( 2 );
            tLinks1.add( bLicense ).row();
            tLinks1.add( bManual ).row();
            tLinks1.add( bVideos ).row();
            tLinks1.add( bDiscord ).row();
            tLinks1.add( bWhatsapp ).row();
            tLinks1.add( bReload ).row();
            tLinks2.add( bCopyto ).row();

            tLinks2.add( new Label( z.thirdpartyapps, skin ) ).padTop( 10 ).row();
            tLinks2.add( bRusted ).row();
            tLinks2.add( bWardate ).row();
            tLinks2.add( bManualCN ).row();
            tLinks2.add( bBack3 );
            tLinks.add( tLinks1 );
            tLinks.add( tLinks2 );
        } else {
            tLinks = new Table();
            tLinks.setFillParent( true );
            tLinks.defaults().width( btnx ).height( btny + 2 ).padBottom( 2 );
            tLinks.add( bLicense ).row();
            tLinks.add( bManual ).row();
            tLinks.add( bVideos ).row();
            tLinks.add( bDiscord ).row();
            tLinks.add( bWhatsapp ).row();
            tLinks.add( bReload ).row();
            tLinks.add( bCopyto ).row();
            tLinks.add( bBack3 ).row();
            tLinks.add( new Label( z.thirdpartyapps, skin ) ).padTop( 10 ).row();
            tLinks.add( bRusted ).row();
            tLinks.add( bWardate ).row();
            tLinks.add( bManualCN ).row();
        }
    }


    private void setMapMap() {
        if (landscape) {
            tMap = new Table();
            tMap1 = new Table();
            tMap2 = new Table();
            tMap1.defaults().width( btnx ).height( btny * 2 );
            tMap2.defaults().width( btnx ).height( btny * 2 );
            tMap.setFillParent( true );
            tMap.defaults().width( btnx ).height( btny + 3 ).padBottom( 2 );
            //if (!face.ispro())
            tMap1.add( bPatreon2 ).row();
            tMap1.add( bFeedback ).row();
            tMap1.add( bUIEditor ).row();
            tMap1.add( bCollaboration ).row();
            tMap1.add( bProperties ).row();
            tMap1.add( bBackground ).row();

            tMap2.add( bTools ).row();
            tMap2.add( bAutoMgmt ).row();
            tMap2.add( bTileMgmt ).row();
            tMap2.add( bTsetMgmt ).row();
            tMap2.add( bBack2 ).row();
            tMap.add( tMap1 );
            tMap.add( tMap2 );
        } else {
            tMap = new Table();
            tMap.setFillParent( true );
            tMap.defaults().width( btnx ).height( btny + 3 ).padBottom( 2 );
            if (!face.ispro()) tMap.add( bPatreon2 ).row();
            tMap.add( bFeedback ).row();
            tMap.add( bUIEditor ).row();
            tMap.add( bCollaboration ).row();
            tMap.add( bProperties ).row();
            tMap.add( bBackground ).row();
            tMap.add( bTools ).row();
            tMap.add( bAutoMgmt ).row();
            tMap.add( bTileMgmt ).row();
            tMap.add( bTsetMgmt ).row();
            tMap.add( bBack2 ).row();
        }

    }

    private void setMenuMap() {
        if (landscape) {
            tMenu = new Table();
            tMenu1 = new Table();
            tMenu2 = new Table();
            tMenu.setFillParent( true );
            tMenu.defaults().width( btnx ).height( btny + 2 );
            tMenu1.defaults().width( btnx ).height( btny * 2 );
            tMenu2.defaults().width( btnx ).height( btny * 2 );

            if (!face.ispro()) tMenu1.add( bPatreon ).row();
            tMenu1.add( bNew ).row();
            tMenu1.add( bOpen ).row();
            tMenu1.add( bRecent ).row();
            tMenu1.add( bSave ).row();
            tMenu1.add( bSaveAs ).row();
            tMenu1.add( bExporter ).row();
            tMenu2.add( bTutorial ).row();
            tMenu2.add( bPreference ).row();
            tMenu2.add( bLinks ).row();
            tMenu2.add( credito ).row();
            tMenu2.add( bExit ).row();
            tMenu2.add( bBack );
            tMenu.add( tMenu1 );
            tMenu.add( tMenu2 );
        } else {
            tMenu = new Table();
            tMenu.setFillParent( true );
            tMenu.defaults().width( btnx ).height( btny + 2 );

            //if (!face.ispro())
                tMenu.add( bPatreon ).row();
            tMenu.add( bNew ).row();
            tMenu.add( bOpen ).row();
            tMenu.add( bRecent ).row();
            tMenu.add( bSave ).row();
            tMenu.add( bSaveAs ).row();
            tMenu.add( bExporter ).row();
            tMenu.add( bTutorial ).row();
            tMenu.add( bPreference ).row();
            tMenu.add( bLinks ).row();
            tMenu.add( credito ).row();
            tMenu.add( bExit ).row();
            tMenu.add( bBack );

        }
    }

    private void msgbox(String msg) {
        Gdx.input.setInputProcessor( stage );
        dialog = new Dialog( z.info, skin, "dialog" ) {
            @Override
            protected void result(Object object) {
                if (kartu.equalsIgnoreCase( "world" )) {
                    Gdx.input.setInputProcessor( im );
                }

                if (kartu.equalsIgnoreCase( "tile" )) {
                    Gdx.input.setInputProcessor( im );
                }
                cue( "next" );
            }
        };
        Label lab = new Label( msg, skin );

        lab.setWrap( true );

        dialog.add( lab ).width( btnx - 50 ).row();
        dialog.button( z.ok );
        dialog.show( stage );
    }

    Pixmap pbg, pts;
    java.util.List<Color> tscolor;

    private void traceBackground(){
        if (background==null){
            return;
        }

        pbg = new Pixmap( background.getWidth(), background.getHeight(), Pixmap.Format.RGBA8888 );
        pbg = pixmapfromtexture( background, "ff00ff" );
        pbg = resizepixmap(pbg,Tw*Tsw,Th*Tsh );
        pts = tilesets.get(seltset).getPixmap();


        tileset tts = tilesets.get(seltset);
        tscolor = new ArrayList<Color>();

        for (int yy =0; yy<tts.getHeight();yy++){
            for (int xx =0; xx<tts.getWidth();xx++){

                float tcell =0;
                float rr = 0, gg=0,bb=0,aa=0;

                for (int j=0;j<tts.getTileheight();j++){
                    for (int i=0;i<tts.getTilewidth();i++){
                        Color tc = new Color(pts.getPixel( xx * tts.getTilewidth()+i, yy *tts.getTileheight()+j));
                        //if (tc.a >0){
                        tcell++;
                        rr+=tc.r;
                        gg+=tc.g;
                        bb+=tc.b;
                        aa+=tc.a;

                        //  }

                    }
                }
                // if (aa/tcell<0.5f) continue;
                Color tc = new Color(rr/tcell,gg/tcell,bb/tcell,0);
                tscolor.add( tc );

            }
        }

        for (int x=0; x<Tw; x++){
            for (int y=0; y<Th; y++){
                traceCell(x,y);
            }
        }
        //background.dispose();
        //background=null;
        resetCaches();

    }

    private void traceCell(int x, int y){
        //Color bg = new Color(pbg.getPixel( x,y ));
        float tcell =0;
        float rr = 0, gg=0,bb=0,aa=0;
        for (int i=0;i<Tsw;i++){
            for (int j=0;j<Tsh;j++){
                Color tc = new Color(pbg.getPixel( (int) (x * Tsw) + i, (int) (y * Tsh) + j));
               // if (tc.a >0){
                    tcell++;
                    rr+=tc.r;
                    gg+=tc.g;
                    bb+=tc.b;
                    aa+=tc.a;
                //}

            }
        }
       // if (aa/tcell<0.5f) return;
        Color bg = new Color(rr/tcell,gg/tcell,bb/tcell,0);

        long cnum = 0;
        float cnumdiff=999999;

        tileset tts = tilesets.get(seltset);
        for (int xx =0; xx<tts.getWidth();xx++){
            for (int yy =0; yy<tts.getHeight();yy++){

                Color tc = tscolor.get(yy*tts.getWidth()+xx);

                float rdiff = Math.abs(tc.r - bg.r) +  Math.abs(tc.g - bg.g) +  Math.abs(tc.b - bg.b);
                if (rdiff < cnumdiff){
                    cnumdiff = rdiff;
                    cnum = (long) tts.getFirstgid()+(yy*tts.getWidth()+xx);
                }
            }
        }

        layers.get(selLayer).getStr().set( y * Tw + x, cnum );
        layers.get(selLayer).getTset().set( y * Tw + x, seltset);

    }

    private void loadTools() {
        ttools = new Table();
        ttools.setFillParent( true );
        TextButton hmirror = new TextButton( z.mirrorhorizontally, skin );
        TextButton vmirror = new TextButton( z.mirrorvertically, skin );
        TextButton hvmirror = new TextButton( z.mirrorboth, skin );
        TextButton hvmirrorrev = new TextButton( z.mirrorreverse, skin );
        TextButton randomize = new TextButton( z.randommap, skin );
        TextButton replacetiles = new TextButton( z.replacetiles, skin );
        TextButton tracebackground = new TextButton( "Trace Background", skin );
        TextButton toback = new TextButton( z.back, skin );

        //TextButton replacetile=new TextButton("Replace Tiles",skin);
        //TextButton cleartiles=new TextButton("Clear Tiles",skin);

        ttools.defaults().width( btnx ).padBottom( 2 );
        ttools.add( hmirror ).row();
        ttools.add( vmirror ).row();
        ttools.add( hvmirror ).row();
        ttools.add( hvmirrorrev ).row();
        ttools.add( randomize ).row();
        ttools.add( replacetiles ).row();
        ttools.add( tracebackground ).row();
        ttools.add( toback ).row();
        //ttools.add(replacetile).row();
        //ttools.add(cleartiles).row();

        hmirror.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runhmirror();
            }
        } );

        tracebackground.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                traceBackground();
            }
        } );

        vmirror.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runvmirror();
            }
        } );

        hvmirror.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runhvmirror();
            }
        } );

        hvmirrorrev.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runhvmirrorrev();
            }
        } );

        randomize.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( trandomgen );
            }
        } );

        replacetiles.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fprevstr.setText( curspr+"" );
                gotoStage( treplacetiles );
            }
        } );

        toback.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        } );

        trandomgen = new Table();
        trandomgen.setFillParent( true );
        trandomgen.defaults().width( btnx / 2 ).padBottom( 2 ).height( btny );
        ffirstgen = new TextField( "0.5", skin );
        slfirstgen = new Slider( .4f, .6f, .01f, false, skin );
        slfirstgen.setValue( .5f );
        fgencount = new TextField( "5", skin );
        fbirthlim = new TextField( "5", skin );
        fdeathlim = new TextField( "3", skin );
        flivestr = new TextField( "0", skin );
        flivetset = new TextField( "0", skin );
        fdeadstr = new TextField( "0", skin );
        fdeadtset = new TextField( "0", skin );
        TextButton runrandomize = new TextButton( z.randomize, skin );
        TextButton pickrnda = new TextButton( z.picktile1, skin );
        TextButton pickrndb = new TextButton( z.picktile2, skin );
        TextButton randback = new TextButton( z.back, skin );

        pickrnda.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile( "rnda" );
            }
        } );

        randback.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( ttools );
            }
        } );

        pickrndb.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile( "rndb" );
            }
        } );

        trandomgen.add( new Label( z.balance, skin ) );
        trandomgen.add( slfirstgen ).row();

        trandomgen.add( new Label( "GID 1", skin ) );
        trandomgen.add( flivestr ).row();
        trandomgen.add();
        trandomgen.add( pickrnda ).row();
        trandomgen.add( new Label( "GID 2", skin ) );
        trandomgen.add( fdeadstr ).row();
        trandomgen.add();
        trandomgen.add( pickrndb ).row();
        trandomgen.add( runrandomize ).width( btnx ).colspan( 2 ).row();
        trandomgen.add( randback ).width( btnx ).colspan( 2 );

        runrandomize.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float p1 = slfirstgen.getValue();// Float.parseFloat(ffirstgen.getText());
                int p2 = 5;//Integer.parseInt(fgencount.getText());
                int p3 = 5;//Integer.parseInt(fbirthlim.getText());
                int p4 = 3;//Integer.parseInt(fdeathlim.getText());
                long p5 = Long.parseLong( flivestr.getText() );
                int p6 = 0;
                long p7 = Long.parseLong( fdeadstr.getText() );
                int p8 = 0;

                for (int i = 0; i < tilesets.size(); i++) {
                    if (p5 >= tilesets.get( i ).getFirstgid() && p5 < tilesets.get( i ).getFirstgid() + tilesets.get( i ).getTilecount()) {
                        p6 = i;
                    }
                    if (p7 >= tilesets.get( i ).getFirstgid() && p7 < tilesets.get( i ).getFirstgid() + tilesets.get( i ).getTilecount()) {
                        p8 = i;
                    }
                }

                rundomize( p1, p2, p3, p4, p5, p6, p7, p8 );
            }
        } );


        treplacetiles = new Table();
        treplacetiles.setFillParent( true );
        treplacetiles.defaults().width( btnx / 2 ).padBottom( 2 ).height( btny );

        fprevstr = new TextField( "0", skin );
        fnextstr = new TextField( "0", skin );

        TextButton runreplace = new TextButton( z.replacetiles, skin );
        TextButton pickrepa = new TextButton( z.picktile1, skin );
        TextButton pickrepb = new TextButton( z.picktile2, skin );
        TextButton repback = new TextButton( z.back, skin );

        pickrepa.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile( "repa" );
            }
        } );

        repback.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage( ttools );
            }
        } );

        pickrepb.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile( "repb" );
            }
        } );

        treplacetiles.add( new Label( z.from, skin ) );
        treplacetiles.add( fprevstr ).row();
        treplacetiles.add();
        treplacetiles.add( pickrepa ).row();
        treplacetiles.add( new Label( z.to, skin ) );
        treplacetiles.add( fnextstr ).row();
        treplacetiles.add();
        treplacetiles.add( pickrepb ).row();
        treplacetiles.add( runreplace ).width( btnx ).colspan( 2 ).row();
        treplacetiles.add( repback ).width( btnx ).colspan( 2 );

        runreplace.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                long p1 = Long.parseLong( fprevstr.getText() );
                int p2 = 0;
                long p3 = Long.parseLong( fnextstr.getText() );
                int p4 = 0;

                for (int i = 0; i < tilesets.size(); i++) {
                    if (p1 >= tilesets.get( i ).getFirstgid() && p1 < tilesets.get( i ).getFirstgid() + tilesets.get( i ).getTilecount()) {
                        p2 = i;
                    }
                    if (p3 >= tilesets.get( i ).getFirstgid() && p3 < tilesets.get( i ).getFirstgid() + tilesets.get( i ).getTilecount()) {
                        p4 = i;
                    }
                }

                replacetiles( p1, p2, p3, p4 );
            }
        } );
    }

    private void replacetiles(long p1, int p2, long p3, int p4) {
        if (layers.get( selLayer ).getType() != layer.Type.TILE) return;

        for (int i = 0; i < layers.get( selLayer ).getStr().size(); i++) {
            long prev = layers.get( selLayer ).getStr().get( i );
            if (prev == p1) {
                layers.get( selLayer ).getStr().set( i, p3 );
                layers.get( selLayer ).getTset().set( i, p4 );
                updateCache( i );
            }
        }
    }

    private void exporttolua(String filenamenya) {
        StringBuilderPlus sb = new StringBuilderPlus();

        sb.wlo( "return{" );
        sb.wl( "version = \"1.2\"," );
        sb.wl( "luaversion = \"5.1\"," );
        sb.wl( "tiledversion = \"1.2.3\"," );
        sb.wl( "orientation = \"" + orientation + "\"," );
        sb.wl( "renderorder = \"" + renderorder + "\"," );
        sb.wl( "width = " + Tw + "," );
        sb.wl( "height = " + Th + "," );
        sb.wl( "tilewidth = " + Tsw + "," );
        sb.wl( "tileheight = " + Tsh + "," );
        sb.wl( "nextlayerid = " + (1 + layers.size()) + "," );
        sb.wl( "nextobjectid = " + curid + "," );
        sb.wprop( properties );

        sb.wlo( "tilesets = {" );
        for (int n = 0; n < tilesets.size(); n++) {
            tileset t = tilesets.get( n );
            sb.wlo( "{" );//tileset inside
            sb.wl( "name = \"" + t.getName() + "\"," );
            sb.wl( "firstgid = " + t.getFirstgid() + "," );
            sb.wl( "tilewidth = " + t.getTilewidth() + "," );
            sb.wl( "tileheight = " + t.getTileheight() + "," );
            sb.wl( "spacing = " + t.getSpacing() + "," );
            sb.wl( "margin = " + t.getMargin() + "," );
            sb.wl( "columns = " + t.getColumns() + "," );
            sb.wl( "tilecount = " + t.getTilecount() + "," );
            sb.wl( "image = \"" + t.getSource() + "\"," );
            sb.wl( "imagewidth = " + t.getOriginalwidth() + "," );
            sb.wl( "imageheight = " + t.getOriginalheight() + "," );
            sb.wl( "tileoffset = {\n        x = 0,\n        y = 0\n      }," );
            sb.wl( "grid = {\n        orientation = \"" + orientation + "\",\n        width = " + Tsw + ", \n        height = " + Tsh + "\n      }," );
            sb.wprop( tilesets.get( n ).getProperties() );
            sb.wl( "terrains = {}," );

            if (tilesets.get( n ).getTiles().size() != 0) {
                sb.wlo( "tiles = {" );//tiles
                for (int m = 0; m < tilesets.get( n ).getTiles().size(); m++) {
                    tile tt = tilesets.get( n ).getTiles().get( m );
                    sb.wlo( "{" );//tile
                    sb.wl( "id = " + tt.getTileID() + "," );
                    sb.wprop( tt.getProperties() );

                    if (tt.getAnimation().size() > 0) {
                        sb.wlo( "animation = {" );
                        for (int am = 0; am < tt.getAnimation().size(); am++) {
                            sb.wlo( "{" );
                            sb.wl( "tileid = " + tt.getAnimation().get( am ).getTileID() + "," );
                            sb.wl( "duration = " + tt.getAnimation().get( am ).getDuration() );

                            if (am != tt.getAnimation().size() - 1) {
                                sb.wlc( "}," );
                            } else {
                                sb.wlc( "}" );
                            }
                        }
                        sb.wlc( "}" );
                    }


                    if (m != tilesets.get( n ).getTiles().size() - 1) {
                        sb.wlc( "}," );
                    } else {
                        sb.wlc( "}" );
                    }
                }
                sb.wlc( "}" );
            } else {
                sb.wl( "tiles = {}" );
            }

            if (n != tilesets.size() - 1) {
                sb.wlc( "}," );//tileset inside
            } else {
                sb.wlc( "}" );//tileset inside
            }
        }
        sb.wlc( "}," ); //tileset;

        sb.wlo( "layers = {" );//layers


        //tilelayers
        for (int n = 0; n < layers.size(); n++) {
            if (layers.get( n ).getType() == layer.Type.TILE) {
                layer l = layers.get( n );
                sb.wlo( "{" );//tile layer
                sb.wl( "type = \"tilelayer\"," );
                sb.wl( "id = " + (n + 1) + "," );
                sb.wl( "name = \"" + l.getName() + "\"," );
                sb.wl( "x = 0," );
                sb.wl( "y = 0," );
                sb.wl( "width = " + Tw + "," );
                sb.wl( "height = " + Th + "," );
                sb.wl( "visible = true," );

                if (l.getOpacity() == 0) {
                    sb.wl( "opacity = 1," );
                } else {
                    sb.wl( "opacity = " + l.getOpacity() + "," );
                }

                sb.wl( "offsetx = 0," );
                sb.wl( "offsety = 0," );
                sb.wprop( layers.get( n ).getProperties() );
                sb.wl( "encoding = \"lua\"," );
                sb.wlo( "data = {" );

                for (int k = 0; k < Th; k++) {
                    sb.w( "        " );
                    for (int j = 0; j < Tw; j++) {
                        sb.append( l.getStr().get( k * Tw + j ) );
                        if (!(k == Th - 1 && j == Tw - 1)) sb.append( ", " );
                    }
                    sb.wl( "" );
                }
                sb.wlc( "}" );//data


            } else if (layers.get( n ).getType() == layer.Type.OBJECT) {
                layer l = layers.get( n );
                sb.wlo( "{" );//tile layer
                sb.wl( "type = \"objectgroup\"," );
                int nid = n + layers.size() + 1;
                sb.wl( "id = " + nid + "," );
                sb.wl( "name = \"" + l.getName() + "\"," );
                sb.wl( "visible = true," );
                sb.wl( "opacity = 1," );
                sb.wl( "offsetx = 0," );
                sb.wl( "offsety = 0," );
                sb.wl( "draworder = \"topdown\"," );
                sb.wl( "properties ={}," );

                if (l.getObjects().size() > 0) {
                    sb.wlo( "objects = {" );
                    for (int ao = 0; ao < l.getObjects().size(); ao++) {
                        obj oo = l.getObjects().get( ao );
                        sb.wlo( "{" );
                        sb.wl( "id = " + oo.getId() + "," );
                        sb.wl( "name = \"" + oo.getName() + "\"," );
                        sb.wl( "type = \"" + oo.getType() + "\"," );
                        if (oo.getShape() == "") {
                            sb.wl( "shape = \"rectangle\"," );

                        } else {
                            sb.wl( "shape = \"" + oo.getShape() + "\"," );
                        }
                        sb.wl( "x = " + oo.getX() + "," );
                        sb.wl( "y = " + oo.getY() + "," );

                        if (oo.getShape().equalsIgnoreCase( "point" )) {
                            sb.wl( "width = 0," );
                            sb.wl( "height = 0," );

                        } else {
                            sb.wl( "width = " + oo.getW() + "," );
                            sb.wl( "height = " + oo.getH() + "," );

                        }
                        sb.wl( "rotation = " + oo.getRotation() + "," );
                        sb.wl( "visible = true," );
                        if (oo.getShape().equalsIgnoreCase( "polygon" ) || oo.getShape().equalsIgnoreCase( "polyline" )) {
                            sb.wlo( oo.getShape() + " = {" );
                            for (int ok = 0; ok < oo.getPoints().size(); ok++) {
                                float xar = oo.getPoints().get( ok ).x;
                                float yar = oo.getPoints().get( ok ).y;
                                if (ok != oo.getPoints().size() - 1) {
                                    sb.wl( "{ x = " + xar + ", y = " + yar + " }," );
                                } else {
                                    sb.wl( "{ x = " + xar + ", y = " + yar + " }" );
                                }
                            }
                            sb.wlc( "}," );
                        }

                        sb.wprop( oo.getProperties() );

                        if (ao != l.getObjects().size()) {
                            sb.wlc( "}," );
                        } else {
                            sb.wlc( "}" );
                        }

                    }
                    sb.wlc( "}" );//objects
                } else {
                    sb.wl( "objects = {}" );
                }

            }

            if (n != layers.size() - 1) {
                sb.wlc( "}," );
            } else {
                sb.wlc( "}" );

            }

        }


        sb.wlc( "}" );//layers
        sb.wlc( "}" );//map

        FileHandle fh = Gdx.files.absolute( curdir + "/" + filenamenya + ".lua" );
        fh.writeString( sb.toString(), false );
        backToMap();
        msgbox( z.exportfinished );
    }

    private void exporttojson(String filenamenya) {
        StringBuilderPlus sb = new StringBuilderPlus();
        sb.wlo( "{" );
        sb.wl( "\"version\":1.2," );
        sb.wl( "\"type\":\"map\"," );
        sb.wl( "\"infinite\":false," );
        sb.wl( "\"tiledversion\":\"1.2.3\"," );
        sb.wl( "\"orientation\":\"" + orientation + "\"," );
        sb.wl( "\"renderorder\":\"" + renderorder + "\"," );
        sb.wl( "\"width\":" + Tw + "," );
        sb.wl( "\"height\":" + Th + "," );
        sb.wl( "\"tilewidth\":" + Tsw + "," );
        sb.wl( "\"tileheight\":" + Tsh + "," );
        sb.wl( "\"nextlayerid\":" + (1 + layers.size()) + "," );
        sb.wl( "\"nextobjectid\":" + curid + "," );
        sb.wpropj( properties );
        sb.wl( "," );


        sb.wlo( "\"tilesets\":[" );
        for (int n = 0; n < tilesets.size(); n++) {
            tileset t = tilesets.get( n );
            sb.wlo( "{" );//tileset inside
            sb.wl( "\"name\":\"" + t.getName() + "\"," );
            sb.wl( "\"firstgid\":" + t.getFirstgid() + "," );
            sb.wl( "\"tilewidth\":" + t.getTilewidth() + "," );
            sb.wl( "\"tileheight\":" + t.getTileheight() + "," );
            sb.wl( "\"spacing\":" + t.getSpacing() + "," );
            sb.wl( "\"margin\":" + t.getMargin() + "," );
            sb.wl( "\"columns\":" + t.getColumns() + "," );
            sb.wl( "\"tilecount\":" + t.getTilecount() + "," );
            if (t.getSource() != null) {
                sb.wl( "\"image\":\"" + t.getSource().replace( "/", "\\/" ) + "\"," );
            }
            sb.wl( "\"imagewidth\":" + t.getOriginalwidth() + "," );
            sb.wl( "\"imageheight\":" + t.getOriginalheight() + "," );


            if (tilesets.get( n ).getTiles().size() != 0) {
                sb.wlo( "\"tiles\":[" );//tiles
                for (int m = 0; m < tilesets.get( n ).getTiles().size(); m++) {
                    tile tt = tilesets.get( n ).getTiles().get( m );
                    sb.wlo( "{" );//tile
                    sb.wl( "\"id\":" + tt.getTileID() + "," );

                    if (tt.getAnimation().size() > 0) {
                        sb.wlo( "\"animation\":[" );
                        for (int am = 0; am < tt.getAnimation().size(); am++) {
                            sb.wlo( "{" );
                            sb.wl( "\"tileid\":" + tt.getAnimation().get( am ).getTileID() + "," );
                            sb.wl( "\"duration\":" + tt.getAnimation().get( am ).getDuration() );

                            if (am != tt.getAnimation().size() - 1) {
                                sb.wlc( "}," );
                            } else {
                                sb.wlc( "}" );
                            }
                        }
                        sb.wlc( "]," );//animation
                    }

                    sb.wpropj( tt.getProperties() );


                    if (m != tilesets.get( n ).getTiles().size() - 1) {
                        sb.wlc( "}," );
                    } else {
                        sb.wlc( "}" );
                    }
                }
                sb.wlc( "]," );//tiles
            } else {
                sb.wl( "\"tiles\":[]," );
            }
            sb.wpropj( tilesets.get( n ).getProperties() );
            if (n != tilesets.size() - 1) {
                sb.wlc( "}," );//tileset inside
            } else {
                sb.wlc( "}" );//tileset inside
            }
        }
        sb.wlc( "]," ); //tileset;

        sb.wlo( "\"layers\":[" );//layers

        //tilelayers
        for (int n = 0; n < layers.size(); n++) {
            if (layers.get( n ).getType() == layer.Type.TILE) {
                layer l = layers.get( n );
                sb.wlo( "{" );//tile layer
                sb.wl( "\"type\":\"tilelayer\"," );
                sb.wl( "\"id\":" + (n + 1) + "," );
                sb.wl( "\"name\":\"" + l.getName() + "\"," );
                sb.wl( "\"x\":0," );
                sb.wl( "\"y\":0," );
                sb.wl( "\"width\":" + Tw + "," );
                sb.wl( "\"height\":" + Th + "," );
                sb.wl( "\"visible\":true," );

                if (l.getOpacity() == 0) {
                    sb.wl( "\"opacity\":1," );
                } else {
                    sb.wl( "\"opacity\":" + l.getOpacity() + "," );
                }

                sb.wl( "\"offsetx\":0," );
                sb.wl( "\"offsety\":0," );

                sb.w( "\"data\":[" );

                for (int k = 0; k < Th; k++) {
                    for (int j = 0; j < Tw; j++) {
                        sb.append( l.getStr().get( k * Tw + j ) );
                        if (!(k == Th - 1 && j == Tw - 1)) sb.append( ", " );
                    }
                }
                sb.appendLine( "]," );//data
                sb.wpropj( layers.get( n ).getProperties() );


            }
            if (layers.get( n ).getType() == layer.Type.OBJECT) {
                layer l = layers.get( n );
                sb.wlo( "{" );//tile layer
                sb.wl( "\"type\":\"objectgroup\"," );
                int nid = n + layers.size() + 1;
                sb.wl( "\"id\":" + nid + "," );
                sb.wl( "\"name\":\"" + l.getName() + "\"," );
                sb.wl( "\"visible\":true," );
                sb.wl( "\"opacity\":1," );
                sb.wl( "\"offsetx\":0," );
                sb.wl( "\"offsety\":0," );
                sb.wl( "\"draworder\":\"topdown\"," );

                if (l.getObjects().size() > 0) {
                    sb.wlo( "\"objects\":[" );
                    for (int ao = 0; ao < l.getObjects().size(); ao++) {
                        obj oo = l.getObjects().get( ao );
                        sb.wlo( "{" );
                        sb.wl( "\"id\":" + oo.getId() + "," );
                        sb.wl( "\"name\":\"" + oo.getName() + "\"," );
                        sb.wl( "\"type\":\"" + oo.getType() + "\"," );
                        sb.wl( "\"x\":" + oo.getX() + "," );
                        sb.wl( "\"y\":" + oo.getY() + "," );

                        if (oo.getShape().equalsIgnoreCase( "point" )) {
                            sb.wl( "\"point\":true," );
                            sb.wl( "\"width\":0," );
                            sb.wl( "\"height\":0," );

                        } else {
                            sb.wl( "\"width\":" + oo.getW() + "," );
                            sb.wl( "\"height\":" + oo.getH() + "," );

                        }
                        sb.wl( "\"rotation\":" + oo.getRotation() + "," );

                        if (oo.getShape().equalsIgnoreCase( "polygon" ) || oo.getShape().equalsIgnoreCase( "polyline" )) {
                            sb.wlo( oo.getShape() + "\":[" );
                            for (int ok = 0; ok < oo.getPoints().size(); ok++) {
                                float xar = oo.getPoints().get( ok ).x;
                                float yar = oo.getPoints().get( ok ).y;
                                if (ok != oo.getPoints().size() - 1) {
                                    sb.wl( "\"{ \"x\":" + xar + ", y\":" + yar + " }," );
                                } else {
                                    sb.wl( "\"{ \"x\":" + xar + ", y\":" + yar + " }" );
                                }
                            }
                            sb.wlc( "}," );
                        }
                        sb.wl( "\"visible\":true," );
					/*
					if(oo.getProperties().size()>0)
					{
						sb.wl("\"visible\":true,");
					}else
					{
						sb.wl("\"visible\":true");
					}
					*/

                        sb.wpropj( oo.getProperties() );

                        if (ao != l.getObjects().size() - 1) {
                            sb.wlc( "}," );
                        } else {
                            sb.wlc( "}" );
                        }

                    }
                    sb.wlc( "]" );//objects
                } else {
                    sb.wl( "\"objects\":[]" );
                }

            }

            if (n != layers.size() - 1) {
                sb.wlc( "}," );//tilelayer
            } else {
                sb.wlc( "}" );//tilelayer
            }
        }


        sb.wlc( "]" );//layers
        sb.wlc( "}" );//map

        FileHandle fh = Gdx.files.absolute( curdir + "/" + filenamenya + ".json" );
        fh.writeString( sb.toString(), false );
        backToMap();
        msgbox( z.exportfinished );

    }

    private Pixmap createRWthumbnail(String filenamenya) {
        try {
            Gdx.gl.glEnable( GL20.GL_BLEND );
            int Tsw = 1;
            int Tsh = 1;
            Pixmap pm2 = new Pixmap( Tw, Th, Pixmap.Format.RGBA8888 );
            Pixmap pp = pixmapfromtexture( txresources, "ff00ff" );
            Pixmap pn = pixmapfromtexture( txnumbers, "ff0099" );

            int Tz = Math.max( Tw, Th );
            tilesetsize = tilesets.size();
            if (tilesetsize > 0 && !loadingfile) {
                int offsetx = 0, offsety = 0;
                int jon = 0, joni = 0;
                long ini;
                int total = Tw * Th;
                int startx = 0, stopx = Tw;
                int starty = 0, stopy = Th;

                int aa = 0, bb = 0, cc = 0, dd = 0;
                switch (renderorder) {
                    case "right-down":
                        aa = starty;
                        bb = stopy;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-down":
                        aa = starty;
                        bb = stopy;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                    case "right-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                }


                String flag;
                Long mm = null;
                flag = "00";

                for (int jo = 0; jo < layers.size(); jo++) {
                    if (layers.get( jo ).getType() == layer.Type.TILE && layers.get( jo ).isVisible()) {
                        java.util.List<drawer> drawers = new ArrayList<drawer>();
                        drawers.clear();
                        java.util.List<drawer> drawers2 = new ArrayList<drawer>();
                        drawers2.clear();
                        java.util.List<drawer> drawers3 = new ArrayList<drawer>();
                        drawers3.clear();
                        for (int a = aa; a < bb; a++) {
                            for (int b = cc; b < dd; b++) {
                                //position=(Math.abs(a)*Tw)+Math.abs(b);

                                position = (abs( a ) * Tw) + abs( b );
                                ini = layers.get( jo ).getStr().get( position );
                                initset = layers.get( jo ).getTset().get( position );
                                if (initset == -1) continue;
                                if (ini == 0) continue;//dont draw empty, amazing performance boost
                                xpos = position % Tw;
                                ypos = position / Tw;
                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    // offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                    //offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                }

                                mm = ini;
                                flag = "00";
                                if (ini > total) {
                                    hex = Long.toHexString( ini );
                                    trailer = "00000000" + hex;
                                    hex = trailer.substring( trailer.length() - 8 );
                                    flag = hex.substring( 0, 2 );
                                    mm = Long.decode( "#00" + hex.substring( 2, 8 ) );
                                }
                                tiles = tilesets.get( initset ).getTiles();
                                tilesize = tiles.size();

                                if (tilesize > 0) {
                                    for (int n = 0; n < tilesize; n++) {
                                        if (tiles.get( n ).getAnimation().size() > 0) {
                                            if (mm == tiles.get( n ).getTileID() + tilesets.get( initset ).getFirstgid()) {
                                                mm = (long) tiles.get( n ).getActiveFrameID() + tilesets.get( initset ).getFirstgid();
                                            }
                                        }
                                    }
                                }

                                sprX = (int) (mm - tilesets.get( initset ).getFirstgid()) % (tilesets.get( initset ).getWidth());
                                sprY = (int) (mm - tilesets.get( initset ).getFirstgid()) / (tilesets.get( initset ).getWidth());
                                margin = tilesets.get( initset ).getMargin();
                                spacing = tilesets.get( initset ).getSpacing();
                                Tswa = tilesets.get( initset ).getTilewidth();
                                Tsha = tilesets.get( initset ).getTileheight();

                                tempdrawer = new drawer();
                                tempdrawer.mm = mm;
                                int Tswad = 0;
                                int Tshad = 0;

                                Tswad = Tswa;
                                Tshad = Tsha;

                                switch (flag) {
                                    case "20"://diagonal flip
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, true );
                                        break;
                                    case "40"://flipy
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true );
                                        break;
                                    case "60"://270 degrees clockwise
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "80"://flipx
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false );
                                        break;
                                    case "a0"://90 degress cw
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "c0"://180 degrees cw
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                    case "00":
                                        tempdrawer.setdrawer( initset, xpos * Tsw - offsetx, ypos * Tsh - offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false );
                                        break;
                                }
                                drawers.add( tempdrawer );

                                ///////////////////
                                if (layers.get( jo ).getName().equalsIgnoreCase( "Units" )) {


                                    mm = layers.get( jo ).getStr().get( position );
                                    int mmo = layers.get( jo ).getTset().get( position );
                                    if (mmo == -1) continue;
                                    int xpos = position % Tw;
                                    int ypos = position / Tw;
                                    int maex = -1, maye = -1;
                                    for (tile t : tilesets.get( mmo ).getTiles()) {
                                        //log(mm+"P");

                                        if (t.getTileID() + tilesets.get( mmo ).getFirstgid() == mm) {
                                            boolean isCC = false;
                                            int team = -1;
                                            for (property p : t.getProperties()) {
                                                if (p.getName().equalsIgnoreCase( "unit" ) && p.getValue().equalsIgnoreCase( "commandCenter" )) {
                                                    isCC = true;
                                                    //log(isCC+"P");
                                                }
                                                if (p.getName().equalsIgnoreCase( "team" )) {
                                                    team = Integer.parseInt( p.getValue() );
                                                }
                                            }
                                            if (!isCC) continue;
                                            //log(isCC+"");
                                            switch (team) {
                                                case 0:
                                                    maex = 0;
                                                    maye = 0;
                                                    break;
                                                case 1:
                                                    maex = 1 * 56;
                                                    maye = 0;
                                                    break;
                                                case 2:
                                                    maex = 2 * 56;
                                                    maye = 0;
                                                    break;
                                                case 3:
                                                    maex = 3 * 56;
                                                    maye = 0;
                                                    break;
                                                case 4:
                                                    maex = 4 * 56;
                                                    maye = 0;
                                                    break;
                                                case 5:
                                                    maex = 0;
                                                    maye = 56;
                                                    break;
                                                case 6:
                                                    maex = 1 * 56;
                                                    maye = 56;
                                                    break;
                                                case 7:
                                                    maex = 2 * 56;
                                                    maye = 56;
                                                    break;
                                                case 8:
                                                    maex = 3 * 56;
                                                    maye = 56;
                                                    break;
                                                case 9:
                                                    maex = 4 * 56;
                                                    maye = 56;
                                                    break;

                                            }
                                        }
                                    }

                                    if (maex != -1) {
                                        drawer tempdrawer2 = new drawer();
                                        int widthy = 56;
                                        tempdrawer2.setdrawer( 0, xpos - (Tz / 12f), ypos - (Tz / 12f), maex, maye, widthy, widthy, 1f, 1f, 0f, maex, maye, 55, 55, false, false );
                                        drawers3.add( tempdrawer2 );
                                    }

                                }//if

                                ///////////////////
                                if (layers.get( jo ).getName().equalsIgnoreCase( "Items" )) {


                                    mm = layers.get( jo ).getStr().get( position );
                                    int mmo = layers.get( jo ).getTset().get( position );
                                    if (mmo == -1) continue;
                                    int xpos = position % Tw;
                                    int ypos = position / Tw;
                                    boolean isPool = false;
                                    for (tile t : tilesets.get( mmo ).getTiles()) {

                                        if (t.getTileID() + tilesets.get( mmo ).getFirstgid() == mm) {

                                            for (property p : t.getProperties()) {
                                                if (p.getName().equalsIgnoreCase( "res_pool" )) {
                                                    isPool = true;
                                                    //Gdx.app.log( "",isPool+"" );
                                                }
                                            }
                                            if (!isPool) continue;
                                        }
                                    }
                                    if (isPool) {
                                        drawer tempdrawer = new drawer();
                                        int widthy = 32;
                                        tempdrawer.setdrawer( 0, xpos - (Tz / 20f), ypos - (Tz / 20f), 0, 0, widthy, widthy, 1f, 1f, 0f, 0, 0, 32, 32, false, false );
                                        drawers2.add( tempdrawer );
                                        //tempdrawer.draw( batch, txresources );
                                    }

                                }


                            } //for  b
                        }//for a

                        java.util.Collections.sort( drawers );//fps hogger

                        for (drawer drawer : drawers) {
                            drawer.draw( pm2, tilesets, Tsw, Tsh );
                        }

                        for (drawer drawer : drawers2) {
                            drawer.draw( pm2, pp, (int) (Tz / 10f), (int) (Tz / 10f) );
                        }

                        for (drawer drawer : drawers3) {
                            drawer.draw( pm2, pn, (int) (Tz / 6f), (int) (Tz / 6f) );
                        }

                    }
                }//for jo


            }//no tileswt


            //
            if (filenamenya != "XOXXO") {
                FileHandle fh = Gdx.files.absolute( curdir + "/" + filenamenya + ".png" );
                PixmapIO.writePNG( fh, pm2 );
                backToMap();
                status( z.exportfinished, 3 );
                return null;
            } else {
                return pm2;
            }
        } catch (Exception e) {
            msgbox( z.error );
            ErrorBung( e, "errorlog.txt" );
            return null;
        }
    }

    private void exportastemplate(String filenamenya) {
        if (filenamenya == "") {
            return;
        }
        String exportpath = basepath+"NotTiled/sample/template/"+filenamenya;
        FileHandle fh = Gdx.files.absolute( exportpath );
        if (!fh.exists()) fh.mkdirs();
        saveMap( exportpath + "/" + "template.tmx" );
        backToMap();
        status(z.filesaved,2);
    }

    private void exporttopng(String filenamenya) {
        try {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Pixmap pm2 = new Pixmap(Tw * Tsw, Th * Tsh, Pixmap.Format.RGBA8888);
            tilesetsize = tilesets.size();
            if (tilesetsize > 0 && !loadingfile) {
                int offsetx = 0, offsety = 0;
                int jon = 0, joni = 0;
                long ini;
                int total = Tw * Th;
                int startx = 0, stopx = Tw;
                int starty = 0, stopy = Th;

                int aa = 0, bb = 0, cc = 0, dd = 0;
                switch (renderorder) {
                    case "right-down":
                        aa = starty;
                        bb = stopy;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-down":
                        aa = starty;
                        bb = stopy;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                    case "right-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                }


                String flag;
                Long mm = null;
                flag = "00";

                for (int jo = 0; jo < layers.size(); jo++) {
                    if (layers.get(jo).getType() == layer.Type.TILE && layers.get(jo).isVisible()) {
                        java.util.List<drawer> drawers = new ArrayList<drawer>();
                        drawers.clear();
                        for (int a = aa; a < bb; a++) {
                            for (int b = cc; b < dd; b++) {
                                //position=(Math.abs(a)*Tw)+Math.abs(b);

                                position = (abs(a) * Tw) + abs(b);
                                ini = layers.get(jo).getStr().get(position);
                                initset = layers.get(jo).getTset().get(position);
                                if (initset == -1) continue;
                                if (ini == 0) continue;//dont draw empty, amazing performance boost
                                xpos = position % Tw;
                                ypos = position / Tw;
                                if (orientation.equalsIgnoreCase("isometric")) {
                                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                }

                                mm = ini;
                                flag = "00";
                                if (ini > total) {
                                    hex = Long.toHexString(ini);
                                    trailer = "00000000" + hex;
                                    hex = trailer.substring(trailer.length() - 8);
                                    flag = hex.substring(0, 2);
                                    mm = Long.decode("#00" + hex.substring(2, 8));
                                }
                                tiles = tilesets.get(initset).getTiles();
                                tilesize = tiles.size();

                                if (tilesize > 0) {
                                    for (int n = 0; n < tilesize; n++) {
                                        if (tiles.get(n).getAnimation().size() > 0) {
                                            if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid()) {
                                                mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
                                            }
                                        }
                                    }
                                }

                                sprX = (int) (mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
                                sprY = (int) (mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth());
                                margin = tilesets.get(initset).getMargin();
                                spacing = tilesets.get(initset).getSpacing();
                                Tswa = tilesets.get(initset).getTilewidth();
                                Tsha = tilesets.get(initset).getTileheight();

                                tempdrawer = new drawer();
                                tempdrawer.mm = mm;
                                int Tswad = 0;
                                int Tshad = 0;
                                int ttx=0;
                                int tty=0;

                                Tswad = Tswa;
                                Tshad = Tsha;

                                switch (flag) {
                                    case "20"://diagonal flip 'THIS ONE"
                                        tempdrawer.setdrawer(initset, xpos * Tsw  + ttx - offsetx, ypos * Tsh + tty  - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "40"://flipy nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true);
                                        break;
                                    case "60"://270 degrees clockwise nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "80"://flipx nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "a0"://90 degress cw
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "c0"://180 degrees cw nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "e0"://180 degrees ccw "AND THIS ONE"
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "00":
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh +tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                }
                                drawers.add(tempdrawer);

                            } //for  b
                        }//for a

                        java.util.Collections.sort(drawers);//fps hogger

                        for (drawer drawer : drawers) {
                            drawer.draw(pm2, tilesets, Tsw, Tsh);
                        }
                    }
                }//for jo


            }//no tileswt


            //
            FileHandle fh = Gdx.files.absolute(curdir + "/" + filenamenya + ".png");
            PixmapIO.writePNG(fh, pm2);
            backToMap();
            status(z.exportfinished,3);
        } catch (Exception e) {
            msgbox(z.error);
            ErrorBung(e, "errorlog.txt");
        }
    }

    private void selectionastset(String filenamenya) {
        try {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            tileset t = new tileset();
            int posx = mapstartSelect % Tw;
            int posy = mapstartSelect / Tw;
            int posx2 = mapendSelect % Tw +1;
            int posy2 = mapendSelect / Tw +1;
            int widih = mapendSelect % Tw - mapstartSelect % Tw +1;
            int heih = mapendSelect / Tw - mapstartSelect / Tw +1;
            status(posx+"-"+posx2+","+posy+"-"+posy2+ "("+widih+","+heih+")",5);
            Pixmap pm2 = new Pixmap(widih * Tsw, heih * Tsh, Pixmap.Format.RGBA8888);
            tilesetsize = tilesets.size();
            if (tilesetsize > 0 && !loadingfile) {
                int offsetx = 0, offsety = 0;
                int jon = 0, joni = 0;
                long ini;
                int total = widih * heih;
                int startx = posx, stopx = posx2;
                int starty = posy, stopy = posy2;

                int aa = 0, bb = 0, cc = 0, dd = 0;
                switch (renderorder) {
                    case "right-down":
                        aa = starty;
                        bb = stopy;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-down":
                        aa = starty;
                        bb = stopy;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                    case "right-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                }

                String flag;
                Long mm = null;
                flag = "00";
                int jo = selLayer;
                    if (layers.get(jo).getType() == layer.Type.TILE && layers.get(jo).isVisible()) {
                        java.util.List<drawer> drawers = new ArrayList<drawer>();
                        drawers.clear();
                        for (int a = aa; a < bb; a++) {
                            for (int b = cc; b < dd; b++) {
                                //position=(Math.abs(a)*Tw)+Math.abs(b);

                                //num
                                position = (abs(a) * Tw) + abs(b);



                                ini = layers.get(jo).getStr().get(position);
                                initset = layers.get(jo).getTset().get(position);
                                if (initset == -1) continue;
                                if (ini == 0) continue;//dont draw empty, amazing performance boost
                                xpos = (b - posx);
                                ypos = (a - posy);



                                if (orientation.equalsIgnoreCase("isometric")) {
                                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                }

                                mm = ini;
                                flag = "00";
                                if (ini > total) {
                                    hex = Long.toHexString(ini);
                                    trailer = "00000000" + hex;
                                    hex = trailer.substring(trailer.length() - 8);
                                    flag = hex.substring(0, 2);
                                    mm = Long.decode("#00" + hex.substring(2, 8));
                                }
                                tiles = tilesets.get(initset).getTiles();
                                tilesize = tiles.size();
                                int poss =-1;
                                if (tilesize > 0) {
                                    for (int n = 0; n < tilesize; n++) {
                                        if (tiles.get(n).getAnimation().size() > 0) {
                                            if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid()) {
                                                mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
                                            }
                                        }

                                        if (tiles.get(n).getTileID()==ini - tilesets.get(initset).getFirstgid()){
                                            poss=n;
                                        }

                                    }

                                    if (poss>-1) {
                                        tile tt = new tile();
                                        tt.setTileID( ypos * widih + xpos );
                                        tt.setProperties( tiles.get( poss ).getProperties() );
                                        t.getTiles().add( tt );
                                    }

                                }


                                sprX = (int) (mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
                                sprY = (int) (mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth());
                                margin = tilesets.get(initset).getMargin();
                                spacing = tilesets.get(initset).getSpacing();
                                Tswa = tilesets.get(initset).getTilewidth();
                                Tsha = tilesets.get(initset).getTileheight();


                                tempdrawer = new drawer();
                                tempdrawer.mm = mm;
                                int Tswad = 0;
                                int Tshad = 0;
                                int ttx=0;
                                int tty=0;

                                Tswad = Tswa;
                                Tshad = Tsha;

                                switch (flag) {
                                    case "20"://diagonal flip 'THIS ONE"
                                        tempdrawer.setdrawer(initset, xpos * Tsw  + ttx - offsetx, ypos * Tsh + tty  - offsety, Tswad / 2f, Tshad / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "40"://flipy nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true);
                                        break;
                                    case "60"://270 degrees clockwise nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "80"://flipx nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "a0"://90 degress cw
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "c0"://180 degrees cw nd
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                    case "e0"://180 degrees ccw "AND THIS ONE"
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx  - offsetx, ypos * Tsh + tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                        break;
                                    case "00":
                                        tempdrawer.setdrawer(initset, xpos * Tsw + ttx   - offsetx, ypos * Tsh +tty - offsety, Tsw / 2f, Tsh / 2f, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                        break;
                                }
                                drawers.add(tempdrawer);

                            } //for  b
                        }//for a

                        //java.util.Collections.sort(drawers);//fps hogger

                        for (drawer drawer : drawers) {
                            drawer.draw(pm2, tilesets, Tsw, Tsh);
                        }
                    }


            }//no tileswt


            //save it to PNG
            FileHandle fh = Gdx.files.absolute(basepath+"NotTiled/Temp/"+filenamenya+".png");
            PixmapIO.writePNG(fh, pm2);

            //import it
            loadingfile = true;
            errors = " ";
            fImportWidth.setText( Tsw + "" );
            fImportHeight.setText( Tsh + "" );
            cImportEmbed.setChecked( true );
            fh = Gdx.files.absolute(basepath+"NotTiled/Temp/"+filenamenya+".png");
            addImageTset( fh );
            tilesets.get(tilesets.size()-1).setTiles( t.getTiles());
            CacheAllTset();
            loadingfile = false;
            seltset = tilesets.size() - 1;
            backToMap();
            onToPicker();
            recenterpick();
            cue( "importtilesetok" );
            status(z.exportfinished,3);
            fh.delete();





        } catch (Exception e) {
            msgbox(z.error);
            ErrorBung(e, "errorlog.txt");
        }
    }

    private void saveTsetAsPNG(int dep) {
        try {
            tileset t = tilesets.get(dep);
            String filenamenya = t.getName();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            int w = t.getOriginalwidth();
            int h = t.getOriginalheight();

            Pixmap pm2 = new Pixmap(w, h, Pixmap.Format.RGBA8888);

            for (int x = 0;x<w;x++){
                for (int y=0;y<h;y++){
                    Color c= new Color(t.getPixmap().getPixel(x,y));
                    pm2.drawPixel((int)x,(int)y,Color.rgba8888(c));
                }
            }

            FileHandle fh = Gdx.files.absolute(curdir + "/" + filenamenya + ".png");
            PixmapIO.writePNG(fh, pm2);

            msgbox(z.exportfinished);
        } catch (Exception e) {
            msgbox(z.error);
            ErrorBung(e, "errorlog.txt");
        }
    }

    private void exportastileset(String filenamenya) {
        try {
            double wide = Math.ceil(Math.sqrt(layers.size()));
            double tall = Math.ceil(layers.size()/wide);

            int subsetx = 0;
            int subsety = -1;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Pixmap pm2 = new Pixmap(Tw * Tsw*(int)wide, Th * Tsh * (int)tall, Pixmap.Format.RGBA8888);
            tilesetsize = tilesets.size();
            if (tilesetsize > 0 && !loadingfile) {
                int offsetx = 0, offsety = 0;
                int jon = 0, joni = 0;
                long ini;
                int total = Tw * Th;
                int startx = 0, stopx = Tw;
                int starty = 0, stopy = Th;

                int aa = 0, bb = 0, cc = 0, dd = 0;
                switch (renderorder) {
                    case "right-down":
                        aa = starty;
                        bb = stopy;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-down":
                        aa = starty;
                        bb = stopy;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                    case "right-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = startx;
                        dd = stopx;
                        break;
                    case "left-up":
                        aa = -stopy + 1;
                        bb = -starty + 1;
                        cc = -stopx + 1;
                        dd = -startx + 1;
                        break;
                }


                String flag;
                Long mm = null;
                flag = "00";



                for (int jo = 0; jo < layers.size(); jo++) {

                    if (jo % wide == 0) {
                        subsety += 1;
                        subsetx = 0;
                    }
                    if (layers.get(jo).getType()!=layer.Type.TILE) continue;
                    java.util.List<drawer> drawers = new ArrayList<drawer>();
                    drawers.clear();
                    for (int a = aa; a < bb; a++) {
                        for (int b = cc; b < dd; b++) {
                            //position=(Math.abs(a)*Tw)+Math.abs(b);

                            position = (abs(a) * Tw) + abs(b);
                            ini = layers.get(jo).getStr().get(position);
                            initset = layers.get(jo).getTset().get(position);
                            if (initset == -1) continue;
                            if (ini == 0) continue;//dont draw empty, amazing performance boost
                            xpos = position % Tw;
                            ypos = position / Tw;
                            if (orientation.equalsIgnoreCase("isometric")) {
                                offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                            }

                            mm = ini;
                            flag = "00";
                            if (ini > total) {
                                hex = Long.toHexString(ini);
                                trailer = "00000000" + hex;
                                hex = trailer.substring(trailer.length() - 8);
                                flag = hex.substring(0, 2);
                                mm = Long.decode("#00" + hex.substring(2, 8));
                            }
                            tiles = tilesets.get(initset).getTiles();
                            tilesize = tiles.size();

                            if (tilesize > 0) {
                                for (int n = 0; n < tilesize; n++) {
                                    if (tiles.get(n).getAnimation().size() > 0) {
                                        if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid()) {
                                            mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
                                        }
                                    }
                                }
                            }

                            sprX = (int) (mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
                            sprY = (int) (mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth());
                            margin = tilesets.get(initset).getMargin();
                            spacing = tilesets.get(initset).getSpacing();
                            Tswa = tilesets.get(initset).getTilewidth();
                            Tsha = tilesets.get(initset).getTileheight();

                            tempdrawer = new drawer();
                            tempdrawer.mm = mm;
                            int Tswad = 0;
                            int Tshad = 0;

                            Tswad = Tswa;
                            Tshad = Tsha;
                            int addsubsetx = subsetx*Tsw*Tw;
                            int addsubsety = subsety*Tsh*Th;
                            switch (flag) {
                                case "20"://diagonal flip
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, true);
                                    break;
                                case "40"://flipy
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, true);
                                    break;
                                case "60"://270 degrees clockwise
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                    break;
                                case "80"://flipx
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, true, false);
                                    break;
                                case "a0"://90 degress cw
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                    break;
                                case "c0"://180 degrees cw
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                    break;
                                case "00":
                                    tempdrawer.setdrawer(initset, xpos * Tsw - offsetx + addsubsetx, ypos * Tsh - offsety + addsubsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa + spacing)) + margin, (sprY * (Tsha + spacing)) + margin, Tswa, Tsha, false, false);
                                    break;
                            }
                            drawers.add(tempdrawer);
                        } //for  b
                    }//for a

                    java.util.Collections.sort(drawers);//fps hogger

                    for (drawer drawer : drawers) {
                        drawer.draw(pm2, tilesets, Tsw, Tsh);
                    }
                    subsetx+=1;
                }


            }//no tileswt

            //
            FileHandle fh = Gdx.files.absolute(curdir + "/" + filenamenya + ".png");
            PixmapIO.writePNG(fh, pm2);
            backToMap();
            msgbox(z.exportfinished);
        } catch (Exception e) {
            msgbox(z.error);
            ErrorBung(e, "errorlog.txt");
        }
    }


    private void rundomize(float firstgeneration, int generationcount, int birthlimit, int deathlimit, long livestr, int livetset, long deadstr, int deadtset) {
        if (layers.get(selLayer).getType() != layer.Type.TILE) return;

        float chanceToStartAlive = firstgeneration;
        snapWholeMapPhase1(selLayer);
        for (int x = 0; x < Tw * Th; x++) {

            if (Math.random() < chanceToStartAlive) {
                layers.get(selLayer).getStr().set(x, livestr);
                layers.get(selLayer).getTset().set(x, livetset);
                updateCache( x );

            } else {
                layers.get(selLayer).getStr().set(x, deadstr);
                layers.get(selLayer).getTset().set(x, deadtset);
                updateCache( x );

            }

        }
        for (int i = 0; i < generationcount; i++) {
            newgeneration(birthlimit, deathlimit, livestr, livetset, deadstr, deadtset);
        }
        snapWholeMapPhase2(selLayer);

    }

    public void newgeneration(int birthlimit, int deathlimit, long livestr, int livetset, long deadstr, int deadtset) {
        java.util.List<Long> strt = layers.get(selLayer).getStr();
        java.util.List<Integer> tsett = layers.get(selLayer).getTset();

        int total = Tw * Th;
        for (int k = 0; k < total; k++) {
            long nyei = layers.get(selLayer).getStr().get(k);
            int living = 0;
            if (k - Tw >= 0 && layers.get(selLayer).getStr().get(k - Tw) == livestr) living += 1;
            if (k - 1 >= 0 && k % Tw != 0 && layers.get(selLayer).getStr().get(k - 1) == livestr)
                living += 1;
            if (k + 1 < total && k % Tw != Tw - 1 && layers.get(selLayer).getStr().get(k + 1) == livestr)
                living += 1;
            if (k + Tw < total && layers.get(selLayer).getStr().get(k + Tw) == livestr) living += 1;
            if (k - Tw - 1 >= 0 && k % Tw != 0 && layers.get(selLayer).getStr().get(k - Tw - 1) == livestr)
                living += 1;
            if (k - Tw + 1 >= 0 && k % Tw != Tw - 1 && layers.get(selLayer).getStr().get(k - Tw + 1) == livestr)
                living += 1;
            if (k + Tw - 1 < total && k % Tw != 0 && layers.get(selLayer).getStr().get(k + Tw - 1) == livestr)
                living += 1;
            if (k + Tw + 1 < total && k % Tw != Tw - 1 && layers.get(selLayer).getStr().get(k + Tw + 1) == livestr)
                living += 1;

            if (nyei == livestr) {//living cell
                if (living <= deathlimit) {
                    strt.set(k, deadstr);
                    tsett.set(k, deadtset);
                    updateCache( k );

                }

            } else //dead cell
            {
                if (living >= birthlimit) {
                    strt.set(k, livestr);
                    tsett.set(k, livetset);
                    updateCache( k );

                }
            }

        }
        layers.get(selLayer).setStr(strt);
        layers.get(selLayer).setTset(tsett);
    }

    private void runhmirror() {
        if (layers.size()==0) return;
        if (layers.get(selLayer).getType() != layer.Type.TILE) return;
        redolayer.clear();
        for (int i = 0; i < Tw * Th; i++) {
            boolean follower = true;
            if (i == 0) follower = false;
            int x = i % Tw;
            int y = i / Tw;

            if (x < Tw / 2 && x != Tw / 2) {
                int location = i + (Tw / 2 - x) * 2 - 1;
                long from = layers.get(selLayer).getStr().get(location);
                long to = layers.get(selLayer).getStr().get(i);
                int oldtset = layers.get(selLayer).getTset().get(location);
                int newtset = layers.get(selLayer).getTset().get(i);
                int layer = selLayer;

                if (newtset!=-1) {
                    for (tile t : tilesets.get( newtset ).getTiles()) {
                        if (t.getTileID() + tilesets.get( newtset ).getFirstgid() == to && t.isTerrainForEditor()) {
                            int[] tdata = t.getTerrain();
                            int[] ndat = new int[]{tdata[1], tdata[0], tdata[3], tdata[2]};

                            java.util.List<Integer> lint = new ArrayList<Integer>();

                            for (int u = 0; u < tilesets.get( newtset ).getTiles().size(); u++) {
                                tile xo = tilesets.get( newtset ).getTiles().get( u );
                                if (xo.getTerrainString().equalsIgnoreCase( ndat[0] + "," + ndat[1] + "," + ndat[2] + "," + ndat[3] )) {
                                    //tile found with the selected terrain
                                    lint.add( u );
                                }
                            }
                            //if it is found.
                            if (lint.size() > 0) {
                                tile yo = tilesets.get( newtset ).getTiles().get( lint.get( (int) (Math.random() * lint.size()) ) );
                                to = yo.getTileID() + tilesets.get( newtset ).getFirstgid();
                            }
                            break;
                        }
                    }
                }

                layerhistory lh = new layerhistory(follower, from, to, location, layer, oldtset, newtset);
                undolayer.add(lh);

                layers.get(selLayer).getStr().set(location, to);
                layers.get(selLayer).getTset().set(location, newtset);
                updateCache( location );

            }
        }
        backToMap();
        //updateMinimap();

    }

    private void runvmirror() {
        if (layers.size()==0) return;
        if (layers.get(selLayer).getType() != layer.Type.TILE) return;

        redolayer.clear();
        for (int i = 0; i < Tw * Th; i++) {
            boolean follower = true;
            if (i == 0) follower = false;
            int x = i % Tw;
            int y = i / Tw;

            if (y < Th / 2 && y != Th / 2) {
                int location = i + ((Th / 2 - y) * 2 - 1) * Tw;

                long from = layers.get(selLayer).getStr().get(location);
                long to = layers.get(selLayer).getStr().get(i);
                int oldtset = layers.get(selLayer).getTset().get(location);
                int newtset = layers.get(selLayer).getTset().get(i);
                int layer = selLayer;


                if (newtset!=-1) {
                    for (tile t : tilesets.get( newtset ).getTiles()) {
                        if (t.getTileID() + tilesets.get( newtset ).getFirstgid() == to && t.isTerrainForEditor()) {
                            int[] tdata = t.getTerrain();
                            int[] ndat = new int[]{tdata[2], tdata[3], tdata[0], tdata[1]};

                            java.util.List<Integer> lint = new ArrayList<Integer>();

                            for (int u = 0; u < tilesets.get( newtset ).getTiles().size(); u++) {
                                tile xo = tilesets.get( newtset ).getTiles().get( u );
                                if (xo.getTerrainString().equalsIgnoreCase( ndat[0] + "," + ndat[1] + "," + ndat[2] + "," + ndat[3] )) {
                                    //tile found with the selected terrain
                                    lint.add( u );
                                }
                            }
                            //if it is found.
                            if (lint.size() > 0) {
                                tile yo = tilesets.get( newtset ).getTiles().get( lint.get( (int) (Math.random() * lint.size()) ) );
                                to = yo.getTileID() + tilesets.get( newtset ).getFirstgid();
                            }
                            break;
                        }
                    }
                }

                layerhistory lh = new layerhistory(follower, from, to, location, layer, oldtset, newtset);
                undolayer.add(lh);

                layers.get(selLayer).getStr().set(location, to);
                layers.get(selLayer).getTset().set(location, newtset);
                updateCache( location );
            }
        }
        backToMap();
        //updateMinimap();
    }

    private void runhvmirror() {
        if (layers.size()==0) return;
        if (layers.get(selLayer).getType() != layer.Type.TILE) return;

        runhmirror();
        runvmirror();
        backToMap();
        //updateMinimap();

    }


    private void runhvmirrorrev() {
        if (layers.size()==0) return;
        if (layers.get(selLayer).getType() != layer.Type.TILE) return;

        redolayer.clear();
        for (int i = 0; i < Tw * Th; i++) {
            boolean follower = true;
            if (i == 0) follower = false;
            int x = i % Tw;
            int y = i / Tw;

            if (y < Th / 2 && y != Th / 2) {
                int location = i + ((Th / 2 - y) * 2 - 1) * Tw;
                location = location + (Tw / 2 - x) * 2 - 1;
                long from = layers.get(selLayer).getStr().get(location);
                long to = layers.get(selLayer).getStr().get(i);
                int oldtset = layers.get(selLayer).getTset().get(location);
                int newtset = layers.get(selLayer).getTset().get(i);
                int layer = selLayer;

                if (newtset!=-1) {
                    for (tile t : tilesets.get( newtset ).getTiles()) {
                        if (t.getTileID() + tilesets.get( newtset ).getFirstgid() == to && t.isTerrainForEditor()) {
                            int[] tdata = t.getTerrain();
                            int[] ndat = new int[]{tdata[3], tdata[2], tdata[1], tdata[0]};

                            java.util.List<Integer> lint = new ArrayList<Integer>();

                            for (int u = 0; u < tilesets.get( newtset ).getTiles().size(); u++) {
                                tile xo = tilesets.get( newtset ).getTiles().get( u );
                                if (xo.getTerrainString().equalsIgnoreCase( ndat[0] + "," + ndat[1] + "," + ndat[2] + "," + ndat[3] )) {
                                    //tile found with the selected terrain
                                    lint.add( u );
                                }
                            }
                            //if it is found.
                            if (lint.size() > 0) {
                                tile yo = tilesets.get( newtset ).getTiles().get( lint.get( (int) (Math.random() * lint.size()) ) );
                                to = yo.getTileID() + tilesets.get( newtset ).getFirstgid();
                            }
                            break;
                        }
                    }
                }


                layerhistory lh = new layerhistory(follower, from, to, location, layer, oldtset, newtset);
                undolayer.add(lh);

                layers.get(selLayer).getStr().set(location, to);
                layers.get(selLayer).getTset().set(location, newtset);
            }
        }


        backToMap();
        resetCaches();

    }

    private void beg() {
		/*
		Gdx.input.setInputProcessor(stage);
		dialog = new Dialog(z.info,skin, "dialog")
		{
			@Override
			protected void result(Object object)
			{
				switch ((int) object)
				{
					case 1:
						Gdx.input.setInputProcessor(gd);
						activetutor=0;
						tutoring=true;
						cue("welcome");
						//prefs.putBoolean("tutorial", true).flush();

						break;
					case 2:
						prefs.putBoolean("tutorial", true).flush();
						Gdx.input.setInputProcessor(gd);
						break;
					case 3:
						msgbox(z.moretutorial);
						break;
				}
			}
		};
		dialog.button(z.starttutorial, 1);
		dialog.button(z.no,2);
		dialog.button(z.later,3);
		dialog.text(z.tutorialoffer);
		dialog.show(stage);
		*/

    }

    private void status(String msg, float timeout) {
        debugMe = msg;
        statustimeout = timeout;
    }

    public void loadLicense() {

        btiled = new TextButton("Visit Tiled Website", skin);
        btiled.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI("https://www.mapeditor.org");
            }
        });
        TextButton tLicenseBack = new TextButton(z.back, skin);
        tLicenseBack.addListener(listBack);
        Texture myTexture = new Texture(Gdx.files.internal("cc.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        bcc = new ImageButton(myTexRegionDrawable); //Set the button up

        bcc.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI("https://creativecommons.org/licenses/by-sa/3.0/");
            }
        });
        //safe
        Label title = new Label("NotTiled License", skin);
        Label label = new Label("NotTiled is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.", skin);
        Label label2 = new Label("NotTiled is based on Tiled by Thorbjorn Lindeijer.", skin);
        label.setWrap(true);
        label.setAlignment(com.badlogic.gdx.utils.Align.left);
        label2.setAlignment(com.badlogic.gdx.utils.Align.left);
        label2.setWrap(true);

        tLicense = new Table();
        tLicense.setFillParent(true);

        tLicense.add(title).padBottom(30).center().row();
        tLicense.add(label2).width(btnx).center().row();
        tLicense.add(btiled).padBottom(10).center().row();
        tLicense.add(label).width(btnx).center().row();

        tLicense.add(bcc).center().row();
        tLicense.add(tLicenseBack).width(btnx).center();
    }

    public void loadObjProp() {
        tla.add(new Label(z.id, skin));
        tla.add(new Label(z.x, skin));
        tla.add(new Label(z.y, skin));
        tla.add(new Label(z.width, skin));
        tla.add(new Label(z.height, skin));
        tla.add(new Label(z.name, skin));
        tla.add(new Label(z.type, skin));
        tla.add(new Label(z.rotation, skin));

        for (int i = 1; i <= 8; i++) {
            tf.add(new TextField("", skin));
            if (i <= 5 || i == 8) {
                tf.get(i - 1).setTextFieldFilter(tfffloat);
            }
        }
        tf.get(0).setDisabled(true);


        bApply = new TextButton(z.save, skin);

        bApply.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                obj oj=selobj;
                try {
                    oj.setX( Float.parseFloat( tf.get( 1 ).getText() ) );
                    oj.setY( Float.parseFloat( tf.get( 2 ).getText() ) );
                    oj.setW( Float.parseFloat( tf.get( 3 ).getText() ) );
                    oj.setH( Float.parseFloat( tf.get( 4 ).getText() ) );
                    oj.setRotation(Float.parseFloat(tf.get(7).getText()));
                }catch (Exception e){
                    msgbox("Invalid number format!");
                    return;
                }

                oj.setName(tf.get(5).getText());
                oj.setType(tf.get(6).getText());
                oj.destroyBody( world );
                oj.updateVertices(world, Tsh);
                backToMap();
            }
        });


        bCancel = new TextButton(z.cancel, skin);
        bProps = new TextButton(z.properties, skin);


        bRemove = new TextButton(z.remove, skin);
        bRemove.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selobj.destroyBody( world );
                layers.get(selLayer).getObjects().remove( selobj);

                backToMap();
            }
        });
        bCancel.addListener(listBack);


        tObjProp = new Table();
        tObjProp.setFillParent(true);
        tObjProp.top();
        tObjProp.add(new Label(z.object + " " + z.properties, skin)).colspan(2).padBottom(10);
        tObjProp.row();

        Table table = new Table();
        ScrollPane sp = new ScrollPane(table);

        for (int i = 0; i < 8; i++) {
            table.add(tla.get(i)).width(btnx / 2);
            table.add(tf.get(i)).padBottom(1).padLeft(5).width(btnx / 2 - 1);
            table.row();

        }
        tObjProp.add(sp).colspan(2).height(btny * 10).row();
        tObjProp.add(bProps).width(btnx / 2).padTop(10).padBottom(5);
        tObjProp.add(bApply).width(btnx / 2).padTop(10).padBottom(5).padLeft(5).row();
        tObjProp.add(bRemove).width(btnx / 2);
        tObjProp.add(bCancel).width(btnx / 2).padLeft(5).row();

    }

    obj selobj;
    public void loadPropEditor() {
		/*
		 Table tPropEditor;
		 TextField fPropName; SelectBox sbPropType,sbPropValbool;
		 TextField fPropVal; //str,int,float,color
		 TextButton bPropValfile, bPropApply, bPropCancel;

		*/


        bPropValfile = new TextButton(z.select, skin);

        bPropApply = new TextButton(z.ok, skin);
        bPropCancel = new TextButton(z.cancel, skin);
        bPropGid = new TextButton(z.tilepicker, skin);
        bProppng = new TextButton(z.loadpng, skin);
        bPropCp = new TextButton(z.copy, skin);

        com.badlogic.gdx.scenes.scene2d.ui.Stack stack = new com.badlogic.gdx.scenes.scene2d.ui.Stack();

        fPropName = new TextField("", skin);
        String[] types = new String[]{"String", "Integer", "Float", "Color", "Boolean", "File"};
        sbPropType = new SelectBox(skin);
        sbPropType.setItems((Object[]) types);
        fPropVal = new TextArea("", skin);
        fPropVal.setHeight(400);

        fPropVal.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                // Handle a newline properly. If not handled here, the TextField
                // will advance to the next field.
                if (c == '\n') {
                    int pos = textField.getCursorPosition();
                    String awalu = textField.getText().substring(0, pos);
                    String ahiru = textField.getText().substring(pos);
                    textField.setText(awalu + "\n" + ahiru);
                    stage.setKeyboardFocus(textField);
                    textField.setCursorPosition(pos + 1);
                    //textField.getOnscreenKeyboard().show(true);

                }
            }
        });

        sbPropValbool = new SelectBox(skin);
        sbPropValbool.setItems((Object[]) new String[]{"false", "true"});


        sbPropType.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fPropVal.setVisible(false);
                bPropValfile.setVisible(false);
                sbPropValbool.setVisible(false);
                switch (sbPropType.getSelected().toString()) {
                    case "String":
                        fPropVal.setVisible(true);
                        fPropVal.setText("");
                        fPropVal.setTextFieldFilter(null);
                        break;
                    case "Integer":
                        fPropVal.setVisible(true);
                        fPropVal.setText("");
                        fPropVal.setTextFieldFilter(tffint);
                        break;
                    case "Float":
                        fPropVal.setVisible(true);
                        fPropVal.setText("");
                        fPropVal.setTextFieldFilter(tfffloat);
                        break;
                    case "Color":
                        fPropVal.setVisible(true);
                        fPropVal.setText("");
                        fPropVal.setTextFieldFilter(tffcolor);
                        break;
                    case "Boolean":
                        sbPropValbool.setVisible(true);
                        break;
                    case "File":
                        bPropValfile.setVisible(true);
                        break;
                }
            }
        });
        bPropValfile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "propfile", "file", new String[]{}, tPropEditor);
            }
        });

        bProppng.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "proppng", "file", new String[]{".png"}, tPropEditor);
            }
        });

        bPropCp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            Gdx.app.getClipboard().setContents( fPropVal.getText() );
            }
        });

        bPropGid.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile("props");
            }
        });

        bPropApply.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                java.util.List<property> pp = new ArrayList<property>();

                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                String value;
                switch (sbPropType.getSelected().toString()) {

                    case "Boolean":
                        value = sbPropValbool.getSelected().toString();
                        break;
                    case "File":
                        value = bPropValfile.getText().toString();
                        break;
                    default:
                        value = fPropVal.getText();
                        break;
                }


                int dexo = lproplist.getSelectedIndex();
                String n, t, v;
                n = fPropName.getText();
                t = sbPropType.getSelected().toString();
                v = value;

                //////
                if (sender.equalsIgnoreCase( "tileadd")){
                    temproname = n;
                    temprotype = t;
                    temprovalue = v;
                for (int o = 0; o < massprops.size(); o++) {
                    boolean ada = false;
                    int ka = 0;
                    property tgt = null;
                    java.util.List<property> ppt = null;

                    for (int k = 0; k < tilesets.get(seltset).getTiles().size(); k++) {
                        if (tilesets.get(seltset).getTiles().get(k).getTileID() == o) {
                            ppt = tilesets.get(seltset).getTiles().get(k).getProperties();
                            if (ppt==null)
                            {
                                ada=false;
                            }else{
                            for (int j = 0; j < ppt.size(); j++) {
                                if (ppt.get(j).getName().equalsIgnoreCase(temproname)) {
                                    ada = true;
                                    tgt = ppt.get(j);
                                    ka = k;
                                }
                            }
                            }

                        }
                    }

                    if (ada) {
                        if (!massprops.get(o)) {
                            /*
                            ppt.remove(tgt);
                            tile tt = tilesets.get(seltset).getTiles().get(ka);
                            if (tt.getProperties().size() == 0 && tt.getAnimation().size() == 0) {
                                tilesets.get(seltset).getTiles().remove(tt);
                            }
                             */
                        } else {
                            tgt.setValue(temprovalue);
                        }
                    } else {
                        if (massprops.get(o)) {
                            boolean adatile = false;
                            for (int k = 0; k < tilesets.get(seltset).getTiles().size(); k++) {
                                if (tilesets.get(seltset).getTiles().get(k).getTileID() == o) {
                                    adatile = true;
                                    ka = k;
                                }
                            }

                            properties pt = new properties();
                            pt.getProperties().add(new property(temproname, temprotype, temprovalue));
                            if (!adatile) {
                                tile tile = new tile();
                                tile.setTileID(o);
                                tile.getProperties().add(new property(temproname, temprotype, temprovalue));
                                tilesets.get(seltset).getTiles().add(tile);
                            } else {
                                tile tile = tilesets.get(seltset).getTiles().get(ka);
                                tile.getProperties().add(new property(temproname, temprotype, temprovalue));
                            }
                        }
                    }


                }
                onToPicker();
                return;
            }

                if (sender == "mass") {
                    temproname = n;
                    temprotype = t;
                    temprovalue = v;
                    massprops.clear();
                    for (int i = 0; i < tilesets.get(selTsetID).getTilecount(); i++) {
                        boolean ada = false;

                        for (int k = 0; k < tilesets.get(selTsetID).getTiles().size(); k++) {
                            if (tilesets.get(selTsetID).getTiles().get(k).getTileID() == i) {
                                java.util.List<property> ppt = tilesets.get(selTsetID).getTiles().get(k).getProperties();
                                for (int j = 0; j < ppt.size(); j++) {
                                    if (ppt.get(j).getName().equalsIgnoreCase(n) && ppt.get(j).getValue().equalsIgnoreCase(v)) {
                                        ada = true;
                                    }
                                }

                            }
                        }
                        massprops.add(ada);

                    }
                    pickTile("massprops");
                    return;
                }
                ///
                if (n.equalsIgnoreCase("embedded_png")) {

                    String foredir = "", tempdir = "", combo = "";
                    try {
                        //status(n+v,5);

                        Texture bucket;
                        String base64 = v;
                        byte[] decodedBytes = Base64Coder.decode(base64);
                        bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));

                        tileset tempTset = tilesets.get(selTsetID);
                        tempTset.setTexture(bucket);

                        if (tempTset.getTrans() != null) {
                            tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                        }


                        tempTset.setOriginalwidth(bucket.getWidth());
                        tempTset.setOriginalheight(bucket.getHeight());
                        if (tempTset.getColumns() == 0) {
                            tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                            tempTset.setWidth(tempTset.getColumns());
                            tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                            tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                        }

                        templastID += tempTset.getWidth() * tempTset.getHeight();
                        fTsPropSource.setText("");
                        cbTsPropUseTsx.setChecked(false);
                        tempTset.setSource("");
                        alreadyloaded = true;
                    } catch (Exception e) {
                        ErrorBung(e, "okok.txt");
                    }


                }
                //////
                if (senderID != -1) { //edit

                    pp.get(dexo).setName(n);
                    pp.get(dexo).setType(t);
                    pp.get(dexo).setValue(v);

                } else {
                    pp.add(new property(n, t, v));
                }
                refreshProperties(pp);
					/*
					if (senderID !=-1){ //edit
						lproplist.setSelectedIndex(dexo);
					}else{
						lproplist.setSelectedIndex(saiz-1);
					}
					*/
                gotoStage(tPropsMgmt);


            }
        });

        bPropCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (sender) {
                    case "mass":
                        gotoStage(tTileMgmt);
                        break;
                    case "tileadd":
                        onToPicker();
                        break;
                    default:
                        gotoStage(tPropsMgmt);
                }

            }
        });


        stack.add(fPropVal);
        stack.add(sbPropValbool);
        stack.add(bPropValfile);
        tPropEditor = new Table();
        tPropEditor.defaults().width(btnx).height(btny);
        tPropEditor.setFillParent(true);
        tPropEditor.add(new Label(z.property + " " + z.name, skin)).padBottom(2).left().colspan(2).row();
        tPropEditor.add(fPropName).padBottom(5).colspan(2).left().row();
        tPropEditor.add(new Label(z.type, skin)).width(btnx / 2 - 3).left().padBottom(5);
        tPropEditor.add(sbPropType).padBottom(5).width(btnx / 2).row();
        tPropEditor.add(new Label(z.value, skin)).padBottom(2).left().colspan(2).row();
        tPropEditor.add(stack).colspan(2).left().height(btny * 4).row();
        tPropEditor.add(bPropGid).padTop(2).padBottom(5).colspan(2).left().row();
        tPropEditor.add(bProppng).padTop(2).padBottom(5).colspan(2).left().row();
        tPropEditor.add(bPropCp).padTop(2).padBottom(5).colspan(2).left().row();
        tPropEditor.add(bPropApply).padTop(20).padBottom(5).colspan(2).left().row();
        tPropEditor.add(bPropCancel).padBottom(5).colspan(2).left().row();
        bPropValfile.setVisible(false);
        bPropValfile.setHeight(0);
        sbPropValbool.setVisible(false);
        sbPropValbool.setHeight(0);

    }

    public void fillImageLayerData(){
        tfImageName.setText(layers.get(selLayer).getName());
        tfImageSource.setText(layers.get(selLayer).getImage());
        tfImageKey.setText(layers.get(selLayer).getTrans());
        tfImageOpacity.setText(Float.toString(layers.get(selLayer).getOpacity()));
        tfImageOffsetX.setText(Float.toString(layers.get(selLayer).getOffsetX()));
        tfImageOffsetY.setText(Float.toString(layers.get(selLayer).getOffsetY()));
    }

    public void loadImageLayer(){
        //    TextField tImageName, tImageSource, tImageKey, tImageOpacity, tImageOffsetX, tImageOffsetY;
        tImageLayer = new Table();
        tImageLayer.setFillParent(true);
        tImageLayer.defaults().width(btnx/2f).height(btny).pad(2);
        tfImageName = new TextField("",skin);
        tfImageSource = new TextField("",skin);
        tfImageKey = new TextField("",skin);
        tfImageOpacity = new TextField("",skin);
        tfImageOffsetX = new TextField("",skin);
        tfImageOffsetY = new TextField("",skin);

        tfImageKey.setTextFieldFilter(tffcolor);
        tfImageKey.setMaxLength(6);
        tfImageOpacity.setTextFieldFilter(tfffloat);
        tfImageOffsetX.setTextFieldFilter(tfffloat);
        tfImageOffsetY.setTextFieldFilter(tfffloat);

        TextButton tbSource = new TextButton(z.selectfile,skin);
        TextButton tbOK = new TextButton(z.ok,skin);
        TextButton tbCancel = new TextButton(z.cancel,skin);

        tbSource.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "imagesource", "file", new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"}, tImageLayer);
            }
        });

        tbOK.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tfImageName.getText()!="") layers.get(selLayer).setName(tfImageName.getText());
                layers.get(selLayer).setTrans(tfImageKey.getText());
                if (tfImageOpacity.getText()!="") layers.get(selLayer).setOpacity(Float.parseFloat(tfImageOpacity.getText()));
                if (tfImageOffsetX.getText()!="") layers.get(selLayer).setOffsetX(Float.parseFloat(tfImageOffsetX.getText()));
                if (tfImageOffsetY.getText()!="") layers.get(selLayer).setOffsetY(Float.parseFloat(tfImageOffsetY.getText()));

                if (tfImageSource.getText()!="")
                {
                    layers.get(selLayer).setImage(tfImageSource.getText());
                    FileHandle fh = Gdx.files.absolute(curdir + "/"+ tfImageSource.getText());
                    if (fh.exists()){
                        try {
                            Texture tmptx = new Texture(Gdx.files.absolute(curdir + "/"+ tfImageSource.getText()));
                            SimpleImageInfo s = new SimpleImageInfo(fh.file());
                            layers.get(selLayer).setImagewidth(s.getWidth());
                            layers.get(selLayer).setImageheight(s.getHeight());
                            layers.get(selLayer).setTexture(tmptx);
                            layers.get(selLayer).setPixmap(pixmapfromtexture(layers.get(selLayer).getTexture(), layers.get(selLayer).getTrans()));
                            if (layers.get(selLayer).getTrans() != null) {
                                layers.get(selLayer).setTexture(chromaKey(layers.get(selLayer).getTexture(), layers.get(selLayer).getTrans()));
                            }

                        }catch (Exception e) {}
                    }
                }

                backToMap();
            }
        });

        tbCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });


        tImageLayer.add(new Label(z.imagelayer,skin)).colspan(2).width(btnx).row();

        tImageLayer.add(new Label(z.name,skin));
        tImageLayer.add(tfImageName).row();

        tImageLayer.add(new Label(z.source,skin));
        tImageLayer.add(tfImageSource).row();

        tImageLayer.add(tbSource).colspan(2).width(btnx).row();

        tImageLayer.add(new Label(z.x,skin));
        tImageLayer.add(tfImageOffsetX).row();

        tImageLayer.add(new Label(z.y,skin));
        tImageLayer.add(tfImageOffsetY).row();

        tImageLayer.add(new Label(z.keycolor,skin));
        tImageLayer.add(tfImageKey).row();

        tImageLayer.add(new Label(z.setopacity,skin));
        tImageLayer.add(tfImageOpacity).row();

        tImageLayer.add(tbOK).colspan(2).width(btnx).row();
        tImageLayer.add(tbCancel).colspan(2).width(btnx).row();
    }

    public void loadPreferences() {
        swatches = prefs.getBoolean("swatches", true);
        nativefilechooser = prefs.getBoolean( "nativefc", false );
        rwpath = prefs.getString("rwpath", basepath+"RustedWarfare");
        if (rwpath.equalsIgnoreCase( "/RustedWarfare" )) rwpath = basepath+"RustedWarfare";
        autosaveInterval = prefs.getInteger("interval", 1);
        gridOpacity = prefs.getInteger("gridopacity", 5);
        lastpath = prefs.getString("lastpath", basepath+"NotTiled");
        isSampleReloaded = prefs.getBoolean("reloaded", false);
        if (lastpath.startsWith(Gdx.files.getExternalStoragePath()))
        {
            lastpath = lastpath.substring(Gdx.files.getExternalStoragePath().length());

        }
        //language= prefs.getString("language", "english");
        sShowGrid = prefs.getBoolean("grid", true);



        sEnableBlending = prefs.getBoolean("blending", true);
        sBgcolor = prefs.getString("background", "888888");
        bgr = (float) Integer.parseInt(sBgcolor.substring(0, 2), 16) / 256;
        bgg = (float) Integer.parseInt(sBgcolor.substring(2, 4), 16) / 256;
        bgb = (float) Integer.parseInt(sBgcolor.substring(4, 6), 16) / 256;

        sMinimap = prefs.getBoolean("minimap", false);
        sShowFPS = prefs.getBoolean("fps", false);
        sCustomUI = prefs.getBoolean("customui", false);
        if (sCustomUI){
            loadInterface("custominterface.json");
        }

        sAutoSave = prefs.getBoolean("autosave", false);
        sShowGID = prefs.getBoolean("gid", false);
        sShowGIDmap = prefs.getBoolean("gidmap", false);
        sShowCoords = prefs.getBoolean("coords", false);
        sSaveTsx = prefs.getBoolean("tsx", false);
        scrollspeed = prefs.getInteger("ss", 3);
        sShowCustomGrid = prefs.getBoolean("customgrid", false);
        sGridX = prefs.getInteger("gridx", 5);
        sResizeTiles = prefs.getBoolean("resize", false);
        sGridY = prefs.getInteger("gridy", 5);
        zoomTreshold = prefs.getInteger("zoom", 2);
        loadViewMode();

        sbLanguage = new SelectBox(skin);

        java.util.List<String> srr = new ArrayList<String>();
        srr.add("English");
        srr.add("Spanish");
        srr.add("Russian");
        srr.add("Chinese");
        srr.add("Japanese");
        srr.add("French");
        srr.add("Portuguese");
        srr.add("Tagalog");
        srr.add("Belarusian");
        srr.add("Turkish");
        srr.add("Ukranian");
        srr.add("Indonesian");

        sbLanguage.setItems((Object[])srr.toArray(new String[0]));
        bBack3 = new TextButton(z.back, skin);
        bBack3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });

        bPreference.addListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage(tPreference);
                cbMinimap.setChecked(sMinimap);
                cbnativefc.setChecked( nativefilechooser );
                cbShowGrid.setChecked(sShowGrid);
                cbShowFPS.setChecked(sShowFPS);
                cbCustomUI.setChecked(sCustomUI);
                cbAutoSave.setChecked(sAutoSave);
                cbEnableBlending.setChecked(sEnableBlending);
                sdScrollSpeed.setValue(scrollspeed);
                sdGridOpacity.setValue(gridOpacity);
                fAutoSaveInterval.setText(Integer.toString(autosaveInterval));
                cbShowGid.setChecked(sShowGID);
                cbShowCoords.setChecked(sShowCoords);
                fBgcolor.setText(sBgcolor);
                fFontsize.setText(Integer.toString(fontsize));
                tfCustomFont.setText(sCustomFont);
                frwpath.setText(rwpath);
                sbLanguage.setSelected(language);
                cbResize.setChecked(sResizeTiles);
                oldlang = language;
                oldfontsize = fontsize;
                oldcustomfont = sCustomFont;
                cbShowGidmap.setChecked(sShowGIDmap);
                cbShowCustomGrid.setChecked(sShowCustomGrid);
                fGridX.setText(Integer.toString(sGridX));
                fGridY.setText(Integer.toString(sGridY));
                fzoomtresh.setText(Integer.toString(zoomTreshold));

            }
        });

        cbMinimap = new CheckBox(z.minimap, skin);
        cbnativefc = new CheckBox(z.nativefilechooser, skin);
        cbCustomUI = new CheckBox(z.customui, skin);
        cbShowGrid = new CheckBox(z.showgrid, skin);
        cbShowCoords = new CheckBox(z.showcoords, skin);
        cbShowFPS = new CheckBox(z.showfps, skin);
        cbEnableBlending = new CheckBox(z.enableblending, skin);
        sdScrollSpeed = new Slider(1, 5, 1, false, skin);
        sdGridOpacity = new Slider(1, 10, 1, false, skin);

        cbShowGid = new CheckBox(z.showgidinpicker, skin);
        cbAutoSave = new CheckBox(z.autosaving, skin);
        cbResize = new CheckBox(z.resizetiles, skin);
        cbShowGidmap = new CheckBox(z.showgidinmap, skin);
        cbShowCustomGrid = new CheckBox(z.showcustomgrid, skin);
        fzoomtresh = new TextField(Integer.toString(zoomTreshold), skin);
        tfCustomFont = new TextField("",skin);
        fzoomtresh.setTextFieldFilter(tffint);
        fBgcolor = new TextField(sBgcolor, skin);
        TextButton tbcustomfont = new TextButton(z.selectfile,skin);
        fBgcolor.setTextFieldFilter(tffcolor);
        fFontsize = new TextField(Integer.toString(fontsize), skin);
        fFontsize.setTextFieldFilter(tffint);

        frwpath = new TextField(rwpath, skin);


        fAutoSaveInterval = new TextField(Integer.toString(autosaveInterval), skin);
        fAutoSaveInterval.setTextFieldFilter(tffint);

        fGridX = new TextField(Integer.toString(sGridX), skin);
        fGridX.setTextFieldFilter(tffint);
        fGridY = new TextField(Integer.toString(sGridY), skin);
        fGridY.setTextFieldFilter(tffint);

        tbcustomfont.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "customfont", "file", new String[]{".otf", ".ttf"}, tPreference);

            }
        });

        Button bSavePref = new TextButton(z.save, skin);
        bSavePref.addListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sShowGrid = cbShowGrid.isChecked();
                prefs.putBoolean("grid", sShowGrid).flush();
                sEnableBlending = cbEnableBlending.isChecked();
                prefs.putBoolean("blending", sEnableBlending).flush();
                sMinimap = cbMinimap.isChecked();
                prefs.putBoolean("minimap", sMinimap).flush();
                sShowFPS = cbShowFPS.isChecked();
                prefs.putBoolean("fps", sShowFPS).flush();
                sAutoSave = cbAutoSave.isChecked();
                prefs.putBoolean("autosave", sAutoSave).flush();
                nativefilechooser = cbnativefc.isChecked();
                prefs.putBoolean("nativefc", nativefilechooser).flush();

                sCustomFont = tfCustomFont.getText();
                prefs.putString("customfont", sCustomFont).flush();

                sCustomUI = cbCustomUI.isChecked();
                prefs.putBoolean("customui", sCustomUI).flush();
                sShowGID = cbShowGid.isChecked();
                prefs.putBoolean("gid", sShowGID).flush();

                sShowCoords = cbShowCoords.isChecked();
                prefs.putBoolean("coords", sShowCoords).flush();

                sShowGIDmap = cbShowGidmap.isChecked();
                prefs.putBoolean("gidmap", sShowGIDmap).flush();
                fontsize = Integer.parseInt(fFontsize.getText());
                prefs.putInteger("fontsize", fontsize).flush();

                autosaveInterval = Integer.parseInt(fAutoSaveInterval.getText());
                prefs.putInteger("interval", autosaveInterval).flush();

                rwpath = frwpath.getText();
                prefs.putString("rwpath", rwpath).flush();

                sResizeTiles = cbResize.isChecked();
                prefs.putBoolean("resize", sResizeTiles).flush();

                if (sCustomUI){
                    loadInterface("custominterface.json");
                }else{
                    guis gsu = new guis();
                    gui = gsu;
                }


                sBgcolor = fBgcolor.getText();
                if (sBgcolor.length() != 6) sBgcolor = "888888";
                prefs.putString("background", sBgcolor).flush();
                bgr = (float) Integer.parseInt(sBgcolor.substring(0, 2), 16) / 256;
                bgg = (float) Integer.parseInt(sBgcolor.substring(2, 4), 16) / 256;
                bgb = (float) Integer.parseInt(sBgcolor.substring(4, 6), 16) / 256;


                language = sbLanguage.getSelected().toString();

                prefs.putString("language", language).flush();
                //reloadLanguage();

                scrollspeed = (int) sdScrollSpeed.getValue();
                prefs.putInteger("ss", scrollspeed).flush();

                gridOpacity = (int) sdGridOpacity.getValue();
                prefs.putInteger("gridopacity", gridOpacity).flush();

                sShowCustomGrid = cbShowCustomGrid.isChecked();
                prefs.putBoolean("customgrid", sShowCustomGrid).flush();
                sGridX = Integer.parseInt(fGridX.getText());
                if (sGridX == 0) sGridX = 10;
                prefs.putInteger("gridx", sGridX).flush();

                zoomTreshold = Integer.parseInt(fzoomtresh.getText());
                if (zoomTreshold == 0) zoomTreshold = 4;
                prefs.putInteger("zoom", zoomTreshold).flush();

                sGridY = Integer.parseInt(fGridY.getText());
                if (sGridY == 0) sGridY = 10;
                prefs.putInteger("gridy", sGridY).flush();
                backToMap();
                if (!language.equalsIgnoreCase(oldlang) || fontsize != oldfontsize  || sCustomFont != oldcustomfont) {
                    msgbox(z.restart);
                }
            }


        });

        tPreference = new Table();
        tPreference.setFillParent(true);
        //tPreference.debug();
        Table tPreference2 = new Table();
        //tPreference2.debug();
        //tPreference2.setFillParent(true);
        tPreference2.defaults().height(btny * 4 / 5);

        ScrollPane sp123 = new ScrollPane(tPreference2);
        tPreference.add(sp123);

		/*
		cbShowGrid.getLabelCell().padLeft(5);
		cbShowFPS.getLabelCell().padLeft(5);
		cbShowCustomGrid.getLabelCell().padLeft(5);
		*/

        tPreference2.add(new Label(z.language, skin)).width(btnx / 2);
        tPreference2.add(sbLanguage).width(btnx / 2).padBottom(5).row();
        tPreference2.add(new Label(z.zoomlimit, skin)).width(btnx / 2);
        tPreference2.add(fzoomtresh).width(btnx / 2).padBottom(2).row();
        tPreference2.add(new Label(z.background, skin)).width(btnx / 2);
        tPreference2.add(fBgcolor).width(btnx / 2).padBottom(2).row();
        tPreference2.add(new Label("Custom Font", skin)).width(btnx / 2);
        tPreference2.add(tfCustomFont).width(btnx / 2).row();
        tPreference2.add(tbcustomfont).colspan(2).width(btnx).row();
        tPreference2.add(new Label(z.fontsize, skin)).width(btnx / 2);
        tPreference2.add(fFontsize).width(btnx / 2).padBottom(2).row();
        tPreference2.add(cbAutoSave).colspan(2).left().row();
        tPreference2.add(new Label(z.interval, skin)).width(btnx/2);
        tPreference2.add(fAutoSaveInterval).width(btnx/2).row();
        tPreference2.add(cbMinimap).colspan(2).left().row();
        tPreference2.add(cbnativefc).colspan(2).left().row();
        tPreference2.add(cbCustomUI).colspan(2).left().row();
        tPreference2.add(cbEnableBlending).colspan(2).left().row();
        tPreference2.add(cbShowGrid).colspan(2).left().row();
        tPreference2.add(new Label(z.gridopacity, skin)).colspan(2).width(btnx).row();
        tPreference2.add(sdGridOpacity).colspan(2).width(btnx).row();

        tPreference2.add(cbResize).colspan(2).left().row();

        tPreference2.add(cbShowFPS).colspan(2).left().row();
        tPreference2.add(cbShowCoords).colspan(2).left().row();
        tPreference2.add(cbShowGid).colspan(2).left().row();
        tPreference2.add(cbShowGidmap).colspan(2).left().row();
        tPreference2.add(new Label(z.scrollspeed, skin)).align(Align.left).width(btnx).colspan(2).left().row();

        tPreference2.add(sdScrollSpeed).align(Align.left).width(btnx).colspan(2).left().row();

        tPreference2.add(cbShowCustomGrid).align(Align.left).colspan(2).left().row();
        tPreference2.add(new Label(z.gridx, skin)).padBottom(5).width(btnx / 2);
        tPreference2.add(fGridX).width(btnx / 2).padBottom(5).row();

        tPreference2.add(new Label(z.gridy, skin)).padBottom(10).width(btnx / 2);
        tPreference2.add(fGridY).width(btnx / 2).padBottom(10).row();

        tPreference2.add(new Label(z.rwpath, skin)).colspan(2).width(btnx).row();
        tPreference2.add(frwpath).colspan(2).width(btnx).row();

        tPreference2.add(bSavePref).width(btnx).padBottom(5).height(btny).colspan(2).row();
        tPreference2.add(bBack3).width(btnx).padBottom(5).height(btny).colspan(2);


    }

    public void reloadLanguage() {
        Json json = new Json();
        FileHandle f = Gdx.files.internal("languages/" + language);
        z = json.fromJson(language.class, f);
        shapeName = z.rectangle;
        toolName = z.tile;
        viewModeName = z.stack;
        objViewModeName = z.all;
        magnetName = z.lock;
        try {
		/*
		json = new Json();
		f = Gdx.files.internal("tutorials/"+language);
		tutor = json.fromJson(Tutorials.class, f);
		face.changelanguage(language);
		*/
            generateTutorials();
            face.changelanguage("English");
        } catch (Exception e) {
            ErrorBung(e, "nyut.txt");
        }

    }

    public void generateTutorials(){
        tutorial a;

        a=new tutorial();
        a.setName(z.t100, "01 Read first please!!");
        a.addStep(z.t101,"start",  "These tutorials are intended for new NotTiled users.");
        a.addStep(z.t102, "next", "If you are an existing user, you will need to [Reload samples] first, otherwise these tutorial might not work as intended and cause you unnecessary confusion.");
        a.addStep(z.t103,"next","To reload samples, goto Menu > Links > Reload Samples.");
        a.addStep(z.t104,"next", "Thank you.");
        a.addStep("end", "");
        tutor.getT().add(a);

        a=new tutorial();
        a.setName(z.t200,"02 Creating empty files & adding tilesets");
        a.addStep(z.t201,"start", "Welcome to NotTiled, I will be your guide. Now, open the menu at the bottom left corner.");
        a.addStep("lockUI","");
        a.addStep(z.t202,"menu", "Click on New File");
        a.addStep(z.t203,"new", "As you can see, you can set quite a lot of things here.");
        a.addStep(z.t204,"next", "The top one is the filename, make sure to end your file with .tmx");
        a.addStep(z.t205,"next", "Then, you can select the folder where you want to save the file");
        a.addStep(z.t206,"next", "After that, tile width and height. They are the dimension of your tile in the map. Let's use 16x16.");
        a.addStep("settilesize", "16");
        a.addStep(z.t207,"next", "Map width and height are the total number of tile on each axis. We will use 30x30.");
        a.addStep("setmapsize", "30");
        a.addStep(z.t208,"next", "The rest are for advanced users, just ignore it for now.");
        a.addStep(z.t209,"next", "Okay, click on [OK]");
        a.addStep(z.t210,"usetemplate", "To create an empty file, click [Create empty file]");
        a.addStep(z.t211,"newmap", "Great! now you can see an empty map. Let's put a tileset. ");
        a.addStep(z.t212,"next", "A tileset is just a fancy word for collection of images in one file.");
        a.addStep(z.t213,"next", "Click the tile picker in the bottom center.");
        a.addStep(z.t214,"tilepick", "Click [add new tileset]");
        a.addStep(z.t215,"addtileset", "Let's use the four-season-tileset.png. If you cannot find it, it should be in /NotTiled/Sample/ folder.");
        a.addStep(z.t216,"import", "Just click ok...");
        a.addStep(z.t217,"tilesetadded", "Great, now click on any tile you want to use.");
        a.addStep(z.t218,"tilepickclick", "Click on the map.");
        a.addStep(z.t219,"tileclick", "Fantastic! let's try another one!");
        a.addStep(z.t220,"tilepick", "Pick whichever you like.");
        a.addStep(z.t221,"tilepickclick", "Put it on the map...");
        a.addStep(z.t222,"tileclick", "Very easy right? NotTiled have an autosave feature, but you can also save manually. Click on the quick save button.");
        a.addStep(z.t223,"quicksave", "Well done, you got the basic. See you on another tutorial.");
        a.addStep("end", "");
        tutor.getT().add(a);


        a=new tutorial();
        a.setName(z.t300, "03 Basic Rusted Warfare mapping");
        a.addStep(z.t301, "start", "Welcome to basic RW mapping tutorial. Let's go straight into action, shall we? open the menu.");
        a.addStep("lockUI","");
        a.addStep(z.t302, "menu","click on the new file");
        a.addStep(z.t303, "new", "Rusted Warfare uses 20x20 tile. So, make sure the tile width and tile height are both 20. We will set map width and map height to 50 and 50. When you are done, click [OK] button");
        a.addStep("settilesize", "20");
        a.addStep("setmapsize", "50");
        a.addStep(z.t304, "usetemplate", "Choose [Rusted Warfare Tutorial] and press OK.");
        a.addStep(z.t305, "usetemplateok", "Well done! just click Apply.");
        a.addStep(z.t306, "applytemplate", "Okay!! good job so far!");
        a.addStep(z.t307, "next", "Rusted Warfare map is very simple. It consists of 3 layers. Ground, Items, and Units.");
        a.addStep(z.t308, "next", "Ground is where you put the landscape, the battle field, or whatever you call it.");
        a.addStep(z.t309, "next", "Items layer is where you put resource pools among other things. ");
        a.addStep(z.t310, "next", "Units layer is for units (duh), put Command center or any units here.");
        a.addStep(z.t311, "next", "Putting wrong things on the wrong place will make your map unplayable, remember that.");
        a.addStep(z.t312, "next", "Now, click on the layer selection button on the bottom right corner.");
        a.addStep(z.t313, "layerpick", "See the eye icons? for now, let's hide eveything except the ground layer. When you are done, click on the ground layer.");
        a.addStep(z.t314, "layerselected", "Okay, I will give you a guidance. follow the drawings.");
        a.addStep("changebg","NotTiled/sample/template/Rusted Warfare Tutorial/export1.png");
        a.addStep(z.t315, "next", "To do so, you will need to pick a tile. Click on the tile selection button in the bottom center.");
        a.addStep(z.t316, "tilepick", "now, switch to the [Stone Lava - Ridge] Tileset by pressing the >> icon twice. After that, click on the rock tile (bottom left one)");
        a.addStep(z.t317, "tilepickclick", "now just follow the guide, please draw as identical as possible.");
        a.addStep(z.t318, "next", "To make things easier, tap and hold to create a rectangle. When you are finished, click on the redo button. ");
        a.addStep("unlockUI","");
        a.addStep(z.t319, "redo", "Okay, our map looks ugly, right? It's okay. We will fix it. I'll extend the guide. Just follow it. Press redo again when you are done.");
        a.addStep( "changebg","NotTiled/sample/template/Rusted Warfare Tutorial/export2.png");
        a.addStep(z.t320, "redo", "Well done, now let's connect them all. Follow the new guide. Press redo again when you are done.");
        a.addStep( "changebg","NotTiled/sample/template/Rusted Warfare Tutorial/export3.png");
        a.addStep(z.t321, "redo", "Well done, now fill the rest with lava. Select your lava first.");
        a.addStep(z.t322, "tilepick", "It is the one in the middle.");
        a.addStep(z.t323, "tilepickclick", "Now click on the fill tool.");
        a.addStep(z.t324, "tool3", "Well done, fill it on the map. Click redo when finished.");
        a.addStep(z.t325, "redo", "Fantastic! we are finished with the Ground layer, now let's go back to layer selection (bottom right).");
        a.addStep(z.t326, "layerpick", "Please hide the ground layer, and show Items layer (so you could see new guidance). When you are done, click the Items layer.");
        a.addStep(z.t327, "layerselected", "Okay follow the new guide.");
        a.addStep("changebg","NotTiled/sample/template/Rusted Warfare Tutorial/export5.png");
        a.addStep(z.t328, "tilepick", "Switch to the [misc] tileset, and tap and hold the top left part of the resource pool to enable the stamp mode, and then drag it to make a 3x3 selection");
        a.addStep(z.t329, "stamp", "Nicely done. follow the drawings, and hit redo button to continue.");
        a.addStep(z.t330, "redo", "Well done, now lets continue with the final layer. The Units layer. let's go back to layer selection");
        a.addStep(z.t331, "layerpick", "Please hide the items layer, and show Units layer (for the guidance). When you are done, click the Units layer.");
        a.addStep(z.t332, "layerselected", "Okay, you know what to do. Follow the guide.");
        a.addStep("changebg","NotTiled/sample/template/Rusted Warfare Tutorial/export6.png");
        a.addStep(z.t333, "tilepick", "Switch to the [units] tileset, and find the correct units.");
        a.addStep(z.t334, "tileclick", "That's it. follow the drawings, and as usual, hit redo button to continue.");
        a.addStep(z.t335, "redo", "Super! now you can go back to the layer selection and show all of the layers to see your masterpiece. Click redo when you are done.");
        a.addStep(z.t336, "redo", "Okay, let's take a screenshot so that you map will have a thumbnail on Rusted Warfare. Just zoom out until you see the button.");
        a.addStep(z.t337, "screenshot", "Nice! Just one more step! Copy the file to the Rusted Warfare folder. You can do it manually, but it is easier just to send it from NotTiled. Zoom back in to show the controls and Click the menu.");
        a.addStep(z.t338, "menu","click on Links");
        a.addStep(z.t339, "links","click Copy to Rusted Warfare");
        a.addStep(z.t340, "copytorw","Nicely done! now go to Rusted Warfare and enjoy your new map! I hope this tutorial helps you to understand the basic of RW mappping. Thank you and see you on another tutorial.");
        a.addStep("end","");
        tutor.getT().add(a);

        a=new tutorial();
        a.setName(z.t400, "04 RW Mapping using Macro");
        a.addStep(z.t401, "start", "Welcome back! Make sure you have taken the basic RW mapping tutorial first, otherwise just cancel this tutorial. Okay, now, open the menu.");
        a.addStep("lockUI","");
        a.addStep(z.t402, "menu","click on the new file");
        a.addStep(z.t403, "new", "Let's use the same tile size (20x20) and map size from before (50x50). When you are done, click [OK] button");
        a.addStep(z.t404, "usetemplate", "Now choose [Rusted Warfare] and press OK.");
        a.addStep(z.t405, "usetemplateok", "Well done, you can see that there are quite a lot of tilesets. Let it stays that way, scroll to the bottom and click Apply.");
        a.addStep(z.t406, "applytemplate", "Okay!! another empty map :)");
        a.addStep("unlockUI","");
        a.addStep(z.t407, "next", "You may notice that there are 2 additional button at the bottom");
        a.addStep(z.t408, "next", "The right one is the Autotile selection button, it is just like the tile selection, but for autotiles.");
        a.addStep(z.t409, "next", "The left one is the Refresh button, to apply autotiles to the map.");
        a.addStep(z.t410, "next", "Enough theories! let's start by clicking at our layer selection button (bottom right corner).");
        a.addStep(z.t411, "layerpick", "There are more layers right? The Set layer is where we put our autotiles, while the trigger layer... just ignore it for now :)");
        a.addStep(z.t412, "next", "As usual, I'd like you to hide all layers (for guidance), except the [Set] layer. We are going to use that. When you are done, select the Set layer.");
        a.addStep(z.t413, "layerselected", "Okay, here comes the guidance. Please follow it. Make sure you are on the [Set] layer.");
        a.addStep("changebg","NotTiled/sample/template/Rusted Warfare/export1a.png");
        a.addStep(z.t414, "next", "But first, you will need to pick an autotile. Click on the autotile selection button in the bottom.");
        a.addStep(z.t415, "autopick", "Let's use the [Grass] autotile. Click on it, and follow the guide. Press redo when you are done.");
        a.addStep(z.t416, "redo", "Okay, now fill the rest with [Shallow water]. Make sure to use [Shallow Water], not [water] (they do not mix).");
        a.addStep(z.t417, "redo", "Now, longpress (press and hold) the refresh button.");
        a.addStep(z.t418, "refresh", "Nothing happened? nope. Show your ground layer (and hide the Set layer, duh) and you will see the result.");
        a.addStep(z.t419, "redo", "Cool isn't it? Much easier than before! The power of autotiles! :P");
        a.addStep(z.t420, "next", "The next steps are the same (Items layer & Units), so I would not repeat it again.");
        a.addStep(z.t421, "next", "Take note that autotiles are not perfect. It cannot do anything about tiles that have no adjacent tiles.");
        a.addStep(z.t422, "next", "and not every autotiles can mix with other autotiles. For example, Water can only mix with Shallow Water and Deep Water.");
        a.addStep(z.t423, "next", "That's it, you can finish the map if you want :) See you on the next tutorial!");
        a.addStep("end","");
        tutor.getT().add(a);

        a=new tutorial();
        a.setName(z.t500, "05 Using NotTiled as a Pixel Editor");
        a.addStep(z.t501, "start", "Welcome back! This tutorial is a bit different, we will use NotTiled as a Pixel Editor.");
        a.addStep("lockUI","");
        a.addStep(z.t502, "menu","Click on the new file");
        a.addStep(z.t503, "new", "Pixel editors use 1x1 tile, I'll set it for you. I recommend map size of 16x16 for this tutorial. When you are done, click [OK] button");
        a.addStep("settilesize", "1");
        a.addStep(z.t504, "usetemplate", "Now choose [Pixel Editor] and press OK.");
        a.addStep(z.t505, "usetemplateok", "Click Apply.");
        a.addStep(z.t506, "applytemplate", "Okay!! very easy.");
        a.addStep("unlockUI","");
        a.addStep(z.t507, "next", "To draw something, pick a color from the picker (bottom center).");
        a.addStep(z.t508, "tilepick", "Pick any color you like.");
        a.addStep(z.t509, "tilepickclick", "Put it on the map.");
        a.addStep(z.t510, "tileclick", "Fantastic! to create your PNG, goto menu.");
        a.addStep(z.t511, "menu", "Click Export");
        a.addStep(z.t512, "export", "Put the filename (without extension), and click on Export to PNG");
        a.addStep(z.t513, "exporttopng", "Well done.");
        a.addStep(z.t514, "next", "You can also create an animation sheet by drawing on multiple Layers, and Export it as a Tileset.");
        a.addStep(z.t515, "next", "You can make the layers animate by longpressing the Redo button");
        a.addStep(z.t516, "next", "Oh, and to edit existing PNG, just import it as a tileset, and then stamp it on your layer.");
        a.addStep(z.t517, "next", "Thank you, see you on another tutorial.");
        a.addStep("end","");
        tutor.getT().add(a);

        a=new tutorial();
        a.setName(z.t600, "07 RW Mapping using Terrain Autotile");
    a.addStep(z.t601, "start", "Welcome back! Terrain autotile is a new feature that could help making maps easier. Now, open the menu.");
        a.addStep("lockUI","");
        a.addStep(z.t602, "menu","Click on the new file");
        a.addStep(z.t603, "new", "It uses 16x16 tile, I'll set it for you. The map size is up to you. When you are done, click [OK] button");
        a.addStep("settilesize", "16");
        a.addStep(z.t604, "usetemplate", "Now choose [NotTiled platformer] and press OK.");
        a.addStep(z.t605, "usetemplateok", "Click Apply.");
        a.addStep(z.t606, "applytemplate", "Okay!! very easy.");
        a.addStep("unlockUI","");
        a.addStep(z.t607, "next", "To draw something, pick an item from the picker (bottom center).");
        a.addStep(z.t608, "tilepick", "Pick any item you like.");
        a.addStep(z.t609, "next", "At the minimum, you will need the man as the player, and the girl as the finish line. (Cliche, I know. You can change it anyway)");
        a.addStep(z.t610, "next", "For other things, just try it yourself, okay? :)");
        a.addStep(z.t611, "tilepickclick", "Put it on the map.");
        a.addStep(z.t612, "tileclick", "Fantastico! to play the game, click the Play button on the left.");
        a.addStep("end","");
        tutor.getT().add(a);

        a=new tutorial();
        a.setName(z.t700, "07 RW Mapping using Terrain Autotile");
        a.addStep(z.t701, "start", "Welcome back! Terrain autotile is a new feature that could help making maps easier. Now, open the menu.");
        a.addStep("lockUI","");
        a.addStep(z.t702, "menu","Click on the new file");
        a.addStep(z.t703, "new", "I'll set tile and map size. Just click [OK] button");
        a.addStep("settilesize", "20");
        a.addStep("setmapsize", "50");
        a.addStep(z.t704, "usetemplate", "Now choose [Rusted Warfare V2] and press OK.");
        a.addStep(z.t705, "usetemplateok", "Click Apply.");
        a.addStep(z.t706, "applytemplate", "Okay!! good.");
        a.addStep("unlockUI","");
        a.addStep(z.t707, "next", "To draw something, pick an item from the picker (bottom center).");
        a.addStep(z.t708, "tilepick", "This time, transition tiles will be done automatically, so just pick any tile in the first tileset.");
        a.addStep(z.t709, "tilepickclick", "Use brush and see the magic");
        a.addStep(z.t710, "tileclick", "Fantastico! Remember that terrain autotiles does not work with fill tool. See you later.");
        a.addStep("end","");
        tutor.getT().add(a);


    }

    java.util.List<layerhistory> elha = new ArrayList<layerhistory>();

    public void snapWholeMapPhase1(int zeLayer){
        elha.clear();
        boolean follower = false;
        for (int i=0;i<Tw*Th;i++){
            layerhistory lh = new layerhistory(follower,layers.get(zeLayer).getStr().get(i),0,i,zeLayer,layers.get(zeLayer).getTset().get(i),0);
            elha.add(lh);
            follower=true;
        }
    }

    public void snapWholeMapPhase2(int zeLayer){
        for (int i=0;i<Tw*Th;i++){
            elha.get(i).setTo(layers.get(zeLayer).getStr().get(i));
            elha.get(i).setNewtset(layers.get(zeLayer).getTset().get(i));
            undolayer.add(elha.get(i));
        }
        resetCaches();
    }

    public void loadLayerManagement() {
        bAddLayer = new TextButton(z.addnew, skin);
        bRemoveLayer = new TextButton(z.remove, skin);
        bMoveLayer = new TextButton(z.moveup, skin);
        bLayerProperties = new TextButton(z.properties, skin);
        bEditLayer = new TextButton(z.rename, skin);
        bLayerDuplicate = new TextButton(z.duplicate, skin);
        bSetOpacity = new TextButton(z.setopacity, skin);

        llayerlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

        bBackLayer = new TextButton(z.back, skin);
        bBackLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });


        bTileMgmt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String[] srr = new String[layers.size()];
                for (int i = 0; i < layers.size(); i++) {
                    srr[i] = layers.get(i).getName();

                }
                llayerlist.setItems(srr);
                gotoStage(tLayerMgmt);
            }
        });

        bLayerDuplicate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dexo = llayerlist.getSelectedIndex();
                layer lay = new layer();
                Json json = new Json();

                String clip = json.toJson(layers.get(dexo));

                lay = json.fromJson(layer.class, clip);
                lay.setName(lay.getName()+" (copy)");
                layers.add(lay);

                String[] srr = new String[layers.size()];
                for (int i = 0; i < layers.size(); i++) {
                    srr[i] = layers.get(i).getName();
                }
                llayerlist.setItems(srr);
                llayerlist.setSelectedIndex(layers.size()-1);


            }
        });


        pNewLayer = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }

                layer newlayer = new layer();
                java.util.List<Long> newstr = new ArrayList<Long>();
                java.util.List<Integer> newtset = new ArrayList<Integer>();

                for (int i = 0; i < Tw * Th; i++) {
                    newstr.add((long) 0);
                    newtset.add(-1);
                }
                newlayer.setStr(newstr);
                newlayer.setTset(newtset);
                newlayer.setName(input);
                layers.add(newlayer);
                String[] srr = new String[layers.size()];
                for (int i = 0; i < layers.size(); i++) {
                    srr[i] = layers.get(i).getName();

                }
                llayerlist.setItems(srr);
                llayerlist.setSelectedIndex(layers.size() - 1);
            }

            @Override
            public void canceled() {
            }

        };

        pBrushSize = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") return;
                try{
                    int s = Integer.parseInt(input);
                    brushsize=s;
                    if (s <1 ) brushsize=1;
                    if (s >10 ) brushsize=10;

                }catch (Exception e){}



            }

            @Override
            public void canceled() {
            }

        };



        pNewLayerSC = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }

                layer newlayer = new layer();

                if (newLayerType==layer.Type.TILE) {
                    java.util.List<Long> newstr = new ArrayList<Long>();
                    java.util.List<Integer> newtset = new ArrayList<Integer>();

                    for (int i = 0; i < Tw * Th; i++) {
                        newstr.add((long) 0);
                        newtset.add(-1);
                    }
                    newlayer.setStr(newstr);
                    newlayer.setTset(newtset);
                    newlayer.setName(input);
                    newlayer.setType(layer.Type.TILE);

                } else if (newLayerType==layer.Type.OBJECT) {
                    newlayer.setName(input);
                    newlayer.setType(layer.Type.OBJECT);
                } else if (newLayerType==layer.Type.IMAGE) {
                    newlayer.setName(input);
                    newlayer.setType(layer.Type.IMAGE);
                }
                newlayer.setVisible(true);
                layers.add(newlayer);
                selLayer = layers.size() - 1;
            }

            @Override
            public void canceled() {
            }

        };

        pAddMacro = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }

                autotile tat = new autotile();
                tat.setName( input );

                property p = new property("type","assembly");
                tat.getProperties().add(p);

                int minx = mapstartSelect % Tw;
                int miny = mapstartSelect / Tw;
                int maxx = mapendSelect % Tw;
                int maxy = mapendSelect / Tw;
                int centerx = (minx+maxx)/2;
                int centery = (miny+maxy)/2;

                for (int yy = miny; yy <=maxy ;yy++) {
                    for (int xx = minx; xx <= maxx; xx++) {
                        String nm  = (xx-centerx)+","+(yy-centery);
                        int nnum = yy*Tw+xx;
                        Long val = layers.get(selLayer).getStr().get( nnum );

                        p = new property(nm,val+"");
                        tat.getProperties().add(p);
                    }
                }


                autotiles.add(tat);

                autotiles at = new autotiles(autotiles);
                Json json = new Json();
                writeThisAbs(curdir + "/auto.json", json.prettyPrint(at));


            }

            @Override
            public void canceled() {
            }

        };

        pEditLayer = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }
                int dexo = llayerlist.getSelectedIndex();
                layers.get(dexo).setName(input);

                String[] srr = new String[layers.size()];
                for (int i = 0; i < layers.size(); i++) {
                    srr[i] = layers.get(i).getName();
                }
                llayerlist.setItems(srr);
                llayerlist.setSelectedIndex(dexo);
            }

            @Override
            public void canceled() {
            }

        };

        pSetOpacity = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    input = "1";
                }
                try {
                    layers.get(llayerlist.getSelectedIndex()).setOpacity(Float.parseFloat(input));

                } catch (Exception e) {
                }


            }

            @Override
            public void canceled() {
            }

        };


        bAddLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage(tLayerNew);
            }
        });

        bEditLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(pEditLayer, z.edit + ", " + z.layer + " " + z.name, llayerlist.getSelected(), "");

            }
        });

        bSetOpacity.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (layers.size() > 0) {
                    Gdx.input.getTextInput(pSetOpacity, z.setopacity, Float.toString(layers.get(llayerlist.getSelectedIndex()).getOpacity()), "");
                }
            }
        });

        bMoveLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = llayerlist.getSelectedIndex();
                if (dex > 0) {
                    java.util.Collections.swap(layers, dex, dex - 1);
                    String[] srr = new String[layers.size()];
                    for (int i = 0; i < layers.size(); i++) {
                        srr[i] = layers.get(i).getName();

                    }
                    llayerlist.setItems(srr);
                    llayerlist.setSelectedIndex(dex - 1);
                }
            }
        });

        bRemoveLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (layers.size() > 1) {
                    int dex = llayerlist.getSelectedIndex();
                    layers.remove(dex);
                    selLayer = layers.size() - 1;
                    String[] srr = new String[layers.size()];
                    for (int i = 0; i < layers.size(); i++) {
                        srr[i] = layers.get(i).getName();

                    }
                    llayerlist.setItems(srr);
                    if (dex < 1) dex = 1;
                    llayerlist.setSelectedIndex(dex - 1);
                    updateObjectCollision();
                    resetCaches();

                }
            }
        });


        tLayerMgmt = new Table();
        tLayerMgmt.setFillParent(true);
        tLayerMgmt.defaults().width(btnx).height(btny);
        ScrollPane scrollPane = new ScrollPane(llayerlist);
        tLayerMgmt.add(new Label(z.layer, skin)).padBottom(5).row();
        tLayerMgmt.add(scrollPane).height(btny * 4).padBottom(5).row();
        tLayerMgmt.add(bAddLayer).padBottom(5).row();
        tLayerMgmt.add(bEditLayer).padBottom(5).row();
        tLayerMgmt.add(bLayerProperties).padBottom(5).row();
        tLayerMgmt.add(bLayerDuplicate).padBottom(5).row();
        tLayerMgmt.add(bSetOpacity).padBottom(5).row();
        tLayerMgmt.add(bMoveLayer).padBottom(5).row();
        tLayerMgmt.add(bRemoveLayer).padBottom(5).row();
        tLayerMgmt.add(bBackLayer).padBottom(5);
    }

    public void refreshProperties(java.util.List<property> at) {
        lproplist.setItems();
        int saiz = at.size();

        if (saiz > 0) {
            String[] srr = new String[saiz];
            for (int i = 0; i < saiz; i++) {
                if (at.get(i).getValue().contains("\n")) {
                    srr[i] = at.get(i).getName() + " : " + at.get(i).getValue().split("\n")[0] + " ...";
                } else {
                    srr[i] = at.get(i).getName() + " : " + at.get(i).getValue();
                }
            }
            lproplist.setItems(srr);
            lproplist.setSelectedIndex(saiz - 1);
        }
    }
    public boolean somethingisselected(){
        for (boolean b: massprops)
        {
            if (b) return true;
        }
        return false;
    }

    public void loadPropsManagement() {

        bAddProp = new TextButton(z.addnew, skin);
        bRemoveProp = new TextButton(z.remove, skin);
        bMoveProp = new TextButton(z.moveup, skin);
        bEditProp = new TextButton(z.edit, skin);
        bPropCopy = new TextButton(z.copyall, skin);
        bPropPaste = new TextButton(z.paste, skin);
        bMoveProp = new TextButton(z.moveup, skin);

        bPropExportAsTemplate = new TextButton(z.exportastemplate, skin);
        bPropTemplate = new TextButton(z.template, skin);
        bPropParse = new TextButton(z.parse, skin);
        lPropID = new Label(z.id, skin);
        lproplist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

        bBackProp = new TextButton(z.back, skin);
        bBackProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (sender) {
                    case "object":
                        gotoStage(tObjProp);
                        break;
                    case "tile":
                        gotoStage(tTileMgmt);
                        break;
                    case "tilesettings":
                        onToPicker();
                        break;
                    case "layer":
                        gotoStage(tLayerMgmt);
                        break;
                    case "tset":
                        gotoStage(tTsProp);
                        break;
                    case "map":
                        gotoStage(tProperties);
                        break;
                    case "auto":
                        gotoStage(tAutoMgmt);
                        break;
                }
            }
        });

        bPropExportAsTemplate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lproplist.getSelectedIndex();
                java.util.List<property> pp = new ArrayList<property>();
                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                properties at = new properties();
                at.setProperties(pp);
                Json json = new Json();
                clipProp = json.toJson(at);
                Gdx.input.getTextInput(psaveproptemplate, z.exportastemplate, "", "");

                //get the name.

            }
        });

        bPropCopy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lproplist.getSelectedIndex();
                java.util.List<property> pp = new ArrayList<property>();
                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                properties at = new properties();
                at.setProperties(pp);
                Json json = new Json();
                clipProp = json.toJson(at);


            }
        });

        bPropPaste.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                try {

                    properties at = new properties();
                    Json json = new Json();
                    at = json.fromJson(properties.class, clipProp);

                    switch (sender) {
                        case "object":
                            selobj.setProperties(at.getProperties());
                            break;
                        case "tile":
                            tilesets.get(selTsetID).getTiles().get(selTileID).setProperties(at.getProperties());
                            break;
                        case "tilesettings":
                            tilesets.get(seltset).getTiles().get(selTileID).setProperties(at.getProperties());
                            break;
                        case "tset":
                            tilesets.get(selTsetID).setProperties(at.getProperties());
                            break;
                        case "layer":
                            layers.get(selLayer).setProperties(at.getProperties());
                            break;
                        case "map":
                            properties = at.getProperties();
                            break;
                        case "auto":
                            autotiles.get(selat).setProperties(at.getProperties());
                            break;
                    }

                    refreshProperties(at.getProperties());

                } catch (Exception e) {
                    msgbox(z.error);
                }

            }
        });

        bPropParse.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    java.util.List<property> pp = new ArrayList<property>();
                    switch (sender) {

                        case "auto":
                            pp = autotiles.get(selat).getProperties();
                            break;
                        default:
                            return;
                    }

                    int saiz = pp.size();

                    String[] srr = new String[saiz];
                    int startingID = 0;
                    tileset t = tilesets.get(seltset);
                    int col = t.getColumns();
                    for (int i = 0; i < saiz; i++) {
                        switch (pp.get(i).getName()) {

                            case "source":
                                startingID = Integer.parseInt(pp.get(i).getValue());
                                break;
                            case "center":
                                pp.get(i).setValue(Integer.toString(startingID));
                                break;
                            case "topleft":
                                pp.get(i).setValue(Integer.toString(startingID - col - 1));
                                break;
                            case "topright":
                                pp.get(i).setValue(Integer.toString(startingID - col + 1));
                                break;
                            case "bottomleft":
                                pp.get(i).setValue(Integer.toString(startingID + col - 1));
                                break;
                            case "bottomright":
                                pp.get(i).setValue(Integer.toString(startingID + col + 1));
                                break;
                            case "top":
                                pp.get(i).setValue(Integer.toString(startingID - col));
                                break;
                            case "left":
                                pp.get(i).setValue(Integer.toString(startingID - 1));
                                break;
                            case "right":
                                pp.get(i).setValue(Integer.toString(startingID + 1));
                                break;
                            case "bottom":
                                pp.get(i).setValue(Integer.toString(startingID + col));
                                break;
                            case "bigtopleft":
                                pp.get(i).setValue(Integer.toString(startingID + col * 2 - 1));
                                break;
                            case "bigtopright":
                                pp.get(i).setValue(Integer.toString(startingID + col * 2 + 0));
                                break;
                            case "bigbottomleft":
                                pp.get(i).setValue(Integer.toString(startingID + col * 3 - 1));
                                break;
                            case "bigbottomright":
                                pp.get(i).setValue(Integer.toString(startingID + col * 3 + 0));
                                break;

                        }

                        srr[i] = pp.get(i).getName() + " : " + pp.get(i).getValue();
                    }
                    lproplist.setItems(srr);

                } catch (Exception e) {

                }
            }

        });

        bProps.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refreshProperties(selobj.getProperties());

                lPropID.setText(z.object + " " + z.properties + ": " + selobj.getId());
                sender = "object";
                gotoStage(tPropsMgmt);
            }
        });


        bPropsTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile("tileprop");
            }
        });

        bPropertiesMap.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refreshProperties(properties);

                lPropID.setText(z.map + z.customproperties);
                sender = "map";
                gotoStage(tPropsMgmt);
            }
        });

        bLayerProperties.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (layers.size()==0) return;
                selLayer=llayerlist.getSelectedIndex();
                refreshProperties(layers.get(selLayer).getProperties());
                lPropID.setText(z.customproperties + " ("+z.layer+")");
                sender = "layer";
                gotoStage(tPropsMgmt);
            }
        });

        bTsPropCustomProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refreshProperties(tilesets.get(selTsetID).getProperties());

                lPropID.setText(z.customproperties);
                sender = "tset";
                gotoStage(tPropsMgmt);
            }
        });


        bAutoprops.addListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (autotiles.size() > 0) {
                    int dex = lautolist.getSelectedIndex();

                    refreshProperties(autotiles.get(dex).getProperties());
                    selat = dex;
                    lPropID.setText(z.macro + " " + autotiles.get(dex).getName());
                    sender = "auto";
                    gotoStage(tPropsMgmt);
                }
            }
        });

        bAddProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                senderID = -1;
                bPropValfile.setText(z.select);
                sbPropValbool.setSelected("false");
                sbPropType.setSelected("String");
                fPropName.setText("");
                fPropVal.setText("");
                fPropVal.setVisible(true);
                bPropValfile.setVisible(false);
                sbPropValbool.setVisible(false);
                gotoStage(tPropEditor);

            }
        });

        bEditProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (lproplist.getItems().size == 0) return;
                int dex = lproplist.getSelectedIndex();
                java.util.List<property> pp = new ArrayList<property>();
                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                int saiz = pp.size();
                if (saiz >= 0) {
                    fPropName.setText(pp.get(dex).getName());
                    sbPropType.setSelected(pp.get(dex).getType());
                    fPropVal.setVisible(false);
                    bPropValfile.setVisible(false);
                    sbPropValbool.setVisible(false);
                    switch (sbPropType.getSelected().toString()) {
                        case "String":
                            fPropVal.setVisible(true);
                            fPropVal.setText("");
                            fPropVal.setTextFieldFilter(null);
                            fPropVal.setText(pp.get(dex).getValue());
                            break;
                        case "Integer":
                            fPropVal.setVisible(true);
                            fPropVal.setText("");
                            fPropVal.setTextFieldFilter(tffint);
                            fPropVal.setText(pp.get(dex).getValue());
                            break;
                        case "Float":
                            fPropVal.setVisible(true);
                            fPropVal.setText("");
                            fPropVal.setTextFieldFilter(tfffloat);
                            fPropVal.setText(pp.get(dex).getValue());
                            break;
                        case "Color":
                            fPropVal.setVisible(true);
                            fPropVal.setText("");
                            fPropVal.setTextFieldFilter(tffcolor);
                            fPropVal.setText(pp.get(dex).getValue());
                            break;
                        case "Boolean":
                            sbPropValbool.setVisible(true);
                            sbPropValbool.setSelected(pp.get(dex).getValue());
                            break;
                        case "File":
                            bPropValfile.setVisible(true);
                            bPropValfile.setText(pp.get(dex).getValue());
                            break;
                        default:
                            fPropVal.setVisible(true);
                            fPropVal.setText("");
                            fPropVal.setTextFieldFilter(null);
                            fPropVal.setText(pp.get(dex).getValue());
                            break;
                    }
                    senderID = saiz;
                    gotoStage(tPropEditor);
                }
            }
        });

        bMoveProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lproplist.getSelectedIndex();

                java.util.List<property> pp = new ArrayList<property>();
                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                int saiz = pp.size();
                if (dex > 0) {
                    java.util.Collections.swap(pp, dex, dex - 1);

                    refreshProperties(pp);

                    lproplist.setSelectedIndex(dex - 1);
                }
            }
        });

        bRemoveProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                java.util.List<property> pp = new ArrayList<property>();
                switch (sender) {
                    case "object":
                        pp = selobj.getProperties();
                        break;
                    case "tile":
                        pp = tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
                        break;
                    case "tilesettings":
                        pp = tilesets.get(seltset).getTiles().get(selTileID).getProperties();
                        break;
                    case "tset":
                        pp = tilesets.get(selTsetID).getProperties();
                        break;
                    case "layer":
                        pp = layers.get(selLayer).getProperties();
                        break;
                    case "map":
                        pp = properties;
                        break;
                    case "auto":
                        pp = autotiles.get(selat).getProperties();
                        break;
                }
                int saiz = pp.size();
                if (saiz > 0) {
                    int dex = lproplist.getSelectedIndex();
                    pp.remove(dex);
                    refreshProperties(pp);

						/*
						saiz -= 1;
						String[] srr = new String[saiz];
						for (int i=0;i < saiz;i++)
						{

							srr[i] = pp.get(i).getName() + " : " + pp.get(i).getValue();
						}
						if (srr != null) lproplist.setItems(srr);

						if (dex > 0)
						{
							if (saiz > 0) lproplist.setSelectedIndex(dex - 1);
						}
						else
						{
							if (saiz > 0) lproplist.setSelectedIndex(0);
						}

						*/
                }
            }
        });


        tPropsMgmt = new Table();
        tPropsMgmt.setFillParent(true);
        tPropsMgmt.defaults().width(btnx).height(btny);
        ScrollPane scrollPane7 = new ScrollPane(lproplist);
        tPropsMgmt.add(lPropID).padBottom(2).row();
        tPropsMgmt.add(scrollPane7).height(btny * 4).padBottom(2).row();
        tPropsMgmt.add(bAddProp).padBottom(2).row();
        tPropsMgmt.add(bEditProp).padBottom(2).row();
        tPropsMgmt.add(bMoveProp).padBottom(2).row();
        tPropsMgmt.add(bPropTemplate).padBottom(2).row();
        tPropsMgmt.add(bPropExportAsTemplate).padBottom(2).row();
        tPropsMgmt.add(bPropParse).padBottom(2).row();
        tPropsMgmt.add(bPropCopy).padBottom(2).row();
        tPropsMgmt.add(bPropPaste).padBottom(2).row();
        tPropsMgmt.add(bRemoveProp).padBottom(2).row();
        tPropsMgmt.add(bBackProp).padBottom(2);
    }

    public void refreshAutoMgmt() {
        String[] srr = new String[]{};
        if (autotiles.size() > 0) {
            srr = new String[autotiles.size()];
            for (int i = 0; i < autotiles.size(); i++) {
                srr[i] = autotiles.get(i).getName();
            }
        }

        lautolist.setItems(srr);
    }

    int assemblyid =-1;
    int assemblynum =-1;
    boolean assemblymode =false;

    private void loadAutotileList() {
        Table tblmain = new Table();
        Table tbl = new Table();
        ScrollPane sp = new ScrollPane(tbl);
        tblmain.add(sp);
        tblmain.setFillParent(true);
        tbl.defaults().width(btnx).height(btny).padBottom(2);
        //java.util.List<TextButton> at = new ArrayList<TextButton>();

        for (int i = 0; i < autotiles.size(); i++) {
            java.util.List<property> sz = autotiles.get(i).getProperties();
            String type = "";
            String name = "";
            boolean hidden = false;
            int num = 0;
            name = autotiles.get(i).getName();

            for (int j = 0; j < sz.size(); j++) {

                if (sz.get(j).getName().equalsIgnoreCase("type")) {
                    type = sz.get(j).getValue();

                    if (type.equalsIgnoreCase( "assembly" )){
                        num = -1;
                    }
                }
                if (sz.get(j).getName().equalsIgnoreCase("source")) {
                    num = Integer.parseInt(sz.get(j).getValue());
                }
                if (sz.get(j).getName().equalsIgnoreCase("hidden")) {
                    hidden = true;
                }

            }
            if (hidden) continue;
            if (num == 0) continue;
            if (type == "") continue;
            switch (type) {
                case "bitmask":
                case "completion":
                case "fill":
                case "assembly":
                    Button tat = new Button(skin);
                    tat.left();
                    int initset = 0;
                    for (int o = 0; o < tilesets.size(); o++) {
                        if (num >= tilesets.get(o).getFirstgid() && num < tilesets.get(o).getFirstgid() + tilesets.get(o).getTilecount()) {
                            initset = o;
                            break;
                        }
                    }
                    sprX = (num - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
                    sprY = (num - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth());
                    margin = tilesets.get(initset).getMargin();
                    spacing = tilesets.get(initset).getSpacing();
                    Tswa = tilesets.get(initset).getTilewidth();
                    Tsha = tilesets.get(initset).getTileheight();
                    TextureRegion region=null;
                    region = new TextureRegion(tilesets.get(initset).getTexture(), (sprX * (Tsw + spacing)) + margin, (sprY * (Tsh + spacing)) + margin, Tsw, Tsh);
                    if (type.equalsIgnoreCase( "assembly" )){
                        region = new TextureRegion(txautopick,96,96);
                    }
                    Image img2 = new Image(region);
                    tat.add(img2).padRight(10).padLeft(2).padTop(2).padBottom(2).width(ssx / 15f).height(ssx / 15f);
                    String nen = "";
                    if (sShowGID) {
                        nen = name + " (" + num + ")";
                    } else {
                        nen = name;
                    }
                    Label lbl = new Label(nen, skin);
                    lbl.setColor(1, 1, 1, 1);
                    lbl.setWrap(true);
                    tat.add(lbl).width(btnx - 55);

                    if (type.equalsIgnoreCase( "assembly" )){
                        tat.setName("ass"+i);
                    }else{
                        tat.setName(num + "");
                    }

                    tat.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            String neim = actor.getName();
                            if (neim.startsWith( "ass" )){
                                neim = neim.replace( "ass","" );
                                int a = Integer.parseInt(neim);
                                //status(autotiles.get( a ).getName(),5);
                                assemblymode=true;
                                assemblyid=a;

                            }else{
                                curspr = Integer.parseInt(actor.getName());
                                addRecentTile( curspr );
                                if (layers.size()>3){
                                    if (layers.get(3).getName().equalsIgnoreCase( "Set" )){
                                        selLayer=3;
                                        layers.get(3).setVisible( true );
                                    }
                                }
                                for (int i = 0; i < tilesets.size(); i++) {
                                    if (curspr >= tilesets.get(i).getFirstgid() && curspr < tilesets.get(i).getFirstgid() + tilesets.get(i).getTilecount()) {
                                        seltset = i;
                                        break;
                                    }
                                }
                            }
                            backToMap();
                        }
                    });
                    tbl.add(tat).height(btny * 1.5f).row();
                    break;
            }
        }
        TextButton tat = new TextButton(z.back, skin);
        tat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });
        tbl.add(tat).row();
        gotoStage(tblmain);
    }

    private void addRecentTile(int num){

        for (int x=0;x<6;x++){
            if (swatchValue.get(x)==num) return;
        }

        if (swatchValue.get( 0 ) ==0){
            swatchValue.set( 0,num );
            addSW( num,"sw1" );
            return;
        }
        if (swatchValue.get( 1 ) ==0){
            swatchValue.set( 1,num );
            addSW( num,"sw2" );
            return;
        }
        if (swatchValue.get( 2 ) ==0){
            swatchValue.set( 2,num );
            addSW( num,"sw3" );
            return;
        }
        if (swatchValue.get( 3 ) ==0){
            swatchValue.set( 3,num );
            addSW( num,"sw4" );
            return;
        }
        if (swatchValue.get( 4 ) ==0){
            swatchValue.set( 4,num );
            addSW( num,"sw5" );
            return;
        }
        if (swatchValue.get( 5 ) ==0){
            swatchValue.set( 5,num );
            addSW( num,"sw6" );
            return;
        }
    }


    private void loadList(String wla) {
        this.wl = wla;
        Table tblmain = new Table();
        Table tbl = new Table();
        ScrollPane sp = new ScrollPane(tbl);
        tblmain.add(sp);
        tblmain.setFillParent(true);
        tbl.defaults().width(btnx).height(btny*1.5f).padBottom(2);
        //java.util.List<TextButton> at = new ArrayList<TextButton>();
        int tt = 0;
        TextButton tit = new TextButton("+", skin);
        TextButton tut = new TextButton(z.back, skin);

        switch (wl) {
            case "layer":
                tt = layers.size();
                tbl.add(new Label(z.layer, skin)).colspan(4).row();
                tbl.add(tit).colspan(4).row();

                for (int i = tt-1; i >= 0; i--) {
                    ///////
                    Button moveup = new Button(skin);
                    final Image imoveup = new Image();
                    imoveup.setDrawable(new SpriteDrawable(new Sprite(txUp)));
                    moveup.add(imoveup);
                    moveup.setName(i + "");

                    tbl.add(moveup).width(btnx*0.125f);

                    moveup.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            //backToMap();
                            //////
                            int dex = Integer.parseInt(actor.getName());
                            if (dex < layers.size()-1) {
                                java.util.Collections.swap(layers, dex, dex + 1);
                                resetCaches();
                            }
                            //////
                            loadList( "layer" );
                        }
                    });

                    ///////
                    Button movedn = new Button(skin);
                    final Image imovedn = new Image();
                    imovedn.setDrawable(new SpriteDrawable(new Sprite(txDown)));
                    movedn.add(imovedn);
                    movedn.setName(i + "");

                    tbl.add(movedn).width(btnx*0.125f);

                    movedn.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            //backToMap();
                            //////
                            int dex = Integer.parseInt(actor.getName());
                            if (dex > 0) {
                                java.util.Collections.swap(layers, dex, dex - 1);
                                resetCaches();
                            }
                            //////
                            loadList( "layer" );
                        }
                    });
                    ///////
                    String name = "", moe = "";
                    Button layerName = new Button(skin);
                    Button visible = new Button(skin);
                    layerName.defaults().left();
                    int num = i;
                    Image img = null;

                    name = layers.get(i).getName();
                    if (layers.get(i).getType()==layer.Type.TILE)
                    {
                        img = new Image(txTypeTile);
                    }
                    else if (layers.get(i).getType()==layer.Type.OBJECT)
                    {
                        img = new Image(txTypeObject);
                    }
                    else if (layers.get(i).getType()==layer.Type.GROUP)
                    {
                        img = new Image(txTypeGroup);
                    }
                    else if (layers.get(i).getType()==layer.Type.IMAGE)
                    {
                        img = new Image(txTypeImage);
                    }
                    layerName.add(img).width(btnx*0.15f).padLeft(10);

                    Label lbl = new Label(name, skin);
                    lbl.setColor(1, 1, 1, 1);
                    lbl.setAlignment(Align.left);
                    lbl.setWrap(true);
                    layerName.add(lbl).width(btnx*0.35f);
                    layerName.setName(i + "");
                    tbl.add(layerName).width(btnx*0.50f).colspan(1);
                    ////
                    final Image vis = new Image();
                    if (layers.get(i).isVisible())
                    {
                        vis.setDrawable(new SpriteDrawable(new Sprite(txVisible)));
                    }
                    else
                    {
                        vis.setDrawable(new SpriteDrawable(new Sprite(txInvisible)));
                    }
                    visible.add(vis);
                    visible.setName(i + "");

                    tbl.add(visible).width(btnx*0.25f).colspan(1).row();

                    layerName.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            selLayer = Integer.parseInt(actor.getName());
                            updateObjectCollision();
                            adjustTileset();
                            backToMap();
                            cue("layerselected");
                        }
                    });

                    visible.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            layers.get(Integer.parseInt(actor.getName())).setVisible(!layers.get(Integer.parseInt(actor.getName())).isVisible());
                            viewMode=ViewMode.CUSTOM;
                            saveViewMode();
                            resetCaches();

                            if (layers.get(Integer.parseInt(actor.getName())).isVisible())
                            {
                                vis.setDrawable(new SpriteDrawable(new Sprite(txVisible)));
                            }
                            else
                            {
                                vis.setDrawable(new SpriteDrawable(new Sprite(txInvisible)));
                            }
                        }
                    });

                }

                tit.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        gotoStage(tLayerNew);
                    }
                });


                tut.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        backToMap();
                    }
                });


                tbl.add(tut).colspan(4).row();
                gotoStage(tblmain);
                cue("layerpick");


                break;

            case "tset":
                tt = tilesets.size();
                tbl.add(new Label(z.tileset, skin)).row();

                for (int i = 0; i < tt; i++) {
                    String name = "", moe = "";
                    Button tat = new Button(skin);
                    tat.defaults().left();
                    int num = i;
                    Image img = null;

                    name = tilesets.get(i).getName();
                    img = new Image(txTypeImage);
                    tat.add(img).width(btnx*0.15f).padLeft(10);

                    Label lbl = new Label(name, skin);
                    lbl.setColor(1, 1, 1, 1);
                    lbl.setAlignment(Align.left);
                    lbl.setWrap(true);
                    tat.add(lbl).width(btnx*0.85f);
                    tat.setName(i + "");
                    tbl.add(tat).width(btnx).row();


                    tat.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {

                            seltset = Integer.parseInt(actor.getName());
                            adjustPickAuto();
                            recenterpick();
                            resetMassprops();
                            onToPicker();

                        }
                    });

                }
                tit.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        FileDialog(z.selectfile, "quickaddtset", "file", new String[]{".tsx", ".png", ".jpg", ".jpeg", ".bmp", ".gif"}, nullTable);

                    }
                });


                tut.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        onToPicker();
                    }
                });

                tbl.add(tit).row();
                tbl.add(tut).row();
                gotoStage(tblmain);

                break;
            case "macro":
                //cakwe
                tbl.add(new Label(z.runmacro, skin)).row();

                int macrosize=0;
                for (int i = 0; i < autotiles.size(); i++) {
                    autotile al = autotiles.get( i );
                    automate am = new automate();

                    for (int j = 0; j < al.getProperties().size(); j++) {

                        property p = al.getProperties().get( j );
                        if (p.getName().equalsIgnoreCase( "name" )) {
                            ++macrosize;
                            am.name = p.getValue();
                            Button tat = new Button(skin);
                            tat.defaults().left();
                            Image img = null;

                            String name = p.getValue();
                            img = new Image(txplay);
                            tat.add(img).width(btnx*0.15f).padLeft(10);

                            Label lbl = new Label(name, skin);
                            lbl.setColor(1, 1, 1, 1);
                            lbl.setAlignment(Align.left);
                            lbl.setWrap(true);
                            tat.add(lbl).width(btnx*0.85f);
                            tat.setName(name);
                            tbl.add(tat).width(btnx).row();


                            tat.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    runAutoTiles( actor.getName() );
                                    backToMap();

                                }
                            });

                            continue;
                        }
                    }

                    if (macrosize==0) {
                        try {
                            runAutoTiles( "" );
                        }catch(Exception e){
                            msgbox(e.toString());
                        }
                        backToMap();
                        return;
                    }
                }


                tut.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        backToMap();
                    }
                });

                tbl.add(tut).row();
                gotoStage(tblmain);

                break;

            case "object":
                //cakwe
                tbl.add(new Label(z.object, skin)).row();

                int objectsize=0;
                for (int i = 0; i < tempObjx.size(); i++) {
                    obj oo = tempObjx.get( i );
                    Button tat = new Button(skin);
                    tat.defaults().left();
                    Image img = null;

                    String name = "["+oo.getId()+"] "+oo.getName();

                    img = new Image(txTypeObject);
                    tat.add(img).width(btnx*0.15f).padLeft(10);

                    Label lbl = new Label(name, skin);
                    lbl.setColor(1, 1, 1, 1);
                    lbl.setAlignment(Align.left);
                    lbl.setWrap(true);
                    tat.add(lbl).width(btnx*0.85f);
                    tat.setUserObject(oo);
                    tbl.add(tat).width(btnx).row();


                        tat.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                showPropBox2D( (obj) actor.getUserObject() );
                                //backToMap();

                            }
                        });

                }


                tut.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        backToMap();
                    }
                });

                tbl.add(tut).row();
                gotoStage(tblmain);

                break;



        }







    }

    private void saveViewMode(){
        int vm =0;
        switch (viewMode){

            case STACK:
                vm=1; break;
            case SINGLE:
                vm=2; break;
            case ALL:
                vm=3; break;
            case CUSTOM:
                vm=4; break;
        }
        prefs.putInteger("viewmode", vm).flush();
    }

    private void loadViewMode(){
        int vm = prefs.getInteger("viewmode", 4);

        switch (vm){
            case 1:
                viewMode=ViewMode.STACK; break;
            case 2:
                viewMode=ViewMode.SINGLE; break;
            case 3:
                viewMode=ViewMode.ALL; break;
            case 4:
                viewMode=ViewMode.CUSTOM; break;
        }
    }

    private void loadtoolList() {
        Table tblmain = new Table();
        Table tbl = new Table();
        ScrollPane sp = new ScrollPane(tbl);
        tblmain.add(sp);
        tblmain.setFillParent(true);
        tbl.defaults().width(btnx).padBottom(2);
        //java.util.List<TextButton> at = new ArrayList<TextButton>();
        tbl.add(new Label(z.tilelayer, skin)).row();
        for (int i = 0; i < 5; i++) {
            String name = "";
            Button tat = new Button(skin);
            tat.defaults().center();
            int num = i;
            switch (i) {
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

            }
            name = layers.get(i).getName();
            Label lbl = new Label(name, skin);
            lbl.setColor(0, 0, 0, 1);
            lbl.setAlignment(Align.center);
            lbl.setWrap(true);
            tat.add(lbl).width(btnx);
            tat.setName(num + "");
            tat.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selLayer = Integer.parseInt(actor.getName());
                    backToMap();
                }
            });
            tbl.add(tat).row();
        }

        TextButton tat = new TextButton(z.back, skin);
        tat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });
        tbl.add(tat).row();
        gotoStage(tblmain);
    }

    public void loadPropTemplate() {
        Label lPT = new Label(z.template, skin);
        final com.badlogic.gdx.scenes.scene2d.ui.List<String> lptlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
        TextButton bApplyPT = new TextButton(z.apply, skin);
        TextButton bDeletePT = new TextButton(z.remove, skin);
        TextButton bptback = new TextButton(z.back, skin);

        bPropTemplate.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                lptlist.setItems();
                java.util.List<String> srr = new ArrayList<String>();
                FileHandle dirHandle;
                dirHandle = Gdx.files.absolute(basepath+"NotTiled/sample/json/");
                for (FileHandle entry : dirHandle.list()) {

                    srr.add(entry.file().getName());
                }
                java.util.Collections.sort(srr);
                lptlist.setItems(srr.toArray(new String[0]));
                gotoStage(tpt);

            }
        });

        bDeletePT.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (lptlist.getSelectedIndex()==-1) return;
                FileHandle todel;
                todel = Gdx.files.absolute(basepath+"NotTiled/sample/json/"+lptlist.getSelected());
                todel.delete();

                java.util.List<String> srr = new ArrayList<String>();
                FileHandle dirHandle;
                dirHandle = Gdx.files.absolute(basepath+"NotTiled/sample/json/");
                for (FileHandle entry : dirHandle.list()) {

                    srr.add(entry.file().getName());
                }
                java.util.Collections.sort(srr);
                lptlist.setItems(srr.toArray(new String[0]));

                //   gotoStage(tPropsMgmt);

            }
        });

        bptback.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                gotoStage(tPropsMgmt);

            }
        });

        bApplyPT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                //try{

                properties at = new properties();
                Json json = new Json();
                FileHandle f = Gdx.files.absolute(basepath+"NotTiled/sample/json/" + lptlist.getSelected());
                at = json.fromJson(properties.class, f);

                switch (sender) {
                    case "object":
                        selobj.setProperties(at.getProperties());

                        break;
                    case "tile":
                        tilesets.get(selTsetID).getTiles().get(selTileID).setProperties(at.getProperties());

                        break;
                    case "map":
                        properties = at.getProperties();
                        break;

                    case "auto":
                        autotiles.get(selat).setProperties(at.getProperties());
                        break;
                }


                int saiz = at.getProperties().size();

                if (saiz > 0) {
                    String[] srr = new String[saiz];
                    for (int i = 0; i < saiz; i++) {
                        srr[i] = at.getProperties().get(i).getName() + " : " + at.getProperties().get(i).getValue();
                    }
                    lproplist.setItems(srr);
                    lproplist.setSelectedIndex(saiz - 1);
                }
                gotoStage(tPropsMgmt);
						/*
					}catch(Exception e)
					{
						msgbox("Error, dunno why...");
					}
					*/

            }
        });

        tpt = new Table();
        tpt.setFillParent(true);
        tpt.defaults().width(btnx).padBottom(2);
        tpt.add(lPT).row();
        ScrollPane spn = new ScrollPane(lptlist);
        tpt.add(spn).height(btnx).row();
        tpt.add(bApplyPT).row();
        tpt.add(bDeletePT).row();
        tpt.add(bptback).row();

    }

    public void loadInterface(String filename){
        try {
            guis at;
            Json json = new Json();
            FileHandle f = Gdx.files.absolute(basepath+"NotTiled/sample/"+filename);
            if (f.exists()) {
                at = json.fromJson(guis.class, f);
                gui = at;
            }else
            {
                guis gsu = new guis();
                gui = gsu;
            }
        } catch (Exception e) {
            guis gsu = new guis();
            gui = gsu;
        }
    }

    public void loadAutoManagement() {
        Label lAutotitle = new Label(z.macro, skin);
        lautolist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
        bAutoadd = new TextButton(z.addnew, skin);
        bAutorename = new TextButton(z.rename, skin);
        bAutoprops = new TextButton(z.properties, skin);
        bAutomoveup = new TextButton(z.moveup, skin);
        bAutoremove = new TextButton(z.remove, skin);
        bAutosave = new TextButton(z.save, skin);
        bAutoload = new TextButton(z.open, skin);
        bAutoback = new TextButton(z.back, skin);
        tAutoMgmt = new Table();
        tAutoMgmt.setFillParent(true);
        tAutoMgmt.defaults().width(btnx).height(btny).padBottom(2);
        tAutoMgmt.add(lAutotitle).row();
        ScrollPane spx = new ScrollPane(lautolist);
        tAutoMgmt.add(spx).height(btny * 4).row();
        tAutoMgmt.add(bAutoadd).row();
        tAutoMgmt.add(bAutorename).row();
        tAutoMgmt.add(bAutoprops).row();
        tAutoMgmt.add(bAutomoveup).row();
        tAutoMgmt.add(bAutoremove).row();
        tAutoMgmt.add(bAutosave).row();
        tAutoMgmt.add(bAutoload).row();
        tAutoMgmt.add(bAutoback).row();

        pAutoadd = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input != "") {
                    autotiles.add(new autotile(input));
                    refreshAutoMgmt();
                }
            }

            @Override
            public void canceled() {
            }

        };

        pAutorename = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input != "") {
                    autotiles.get(lautolist.getSelectedIndex()).setName(input);
                    refreshAutoMgmt();
                }
            }

            @Override
            public void canceled() {
            }

        };

        bAutoMgmt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gotoStage(tAutoMgmt);
                assemblymode=false;
                refreshAutoMgmt();
            }
        });

        bAutoadd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(pAutoadd, z.name, "", "");
            }
        });

        bAutorename.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lautolist.getSelectedIndex();
                if (dex >= 0) {
                    Gdx.input.getTextInput(pAutorename, z.rename, autotiles.get(dex).getName(), "");
                }
            }
        });

        bAutomoveup.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lautolist.getSelectedIndex();
                if (dex > 0) {
                    java.util.Collections.swap(autotiles, dex, dex - 1);
                    refreshAutoMgmt();
                }
            }
        });

        bAutoremove.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lautolist.getSelectedIndex();
                if (dex >= 0) {
                    autotiles.remove(dex);
                    refreshAutoMgmt();
                }
            }
        });

        bAutoload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {

                    autotiles at = new autotiles();
                    Json json = new Json();
                    FileHandle f = Gdx.files.absolute(curdir + "/auto.json");
                    at = json.fromJson(autotiles.class, f);
                    autotiles = at.getAutotiles();
                    refreshAutoMgmt();
                } catch (Exception e) {
                    msgbox("place auto.json on the same folder with the tmx file.");
                }
            }
        });

        bAutosave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                autotiles at = new autotiles(autotiles);
                Json json = new Json();
                writeThisAbs(curdir + "/auto.json", json.prettyPrint(at));
                msgbox("auto.json saved!");

            }
        });

        bAutoback.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });
    }

    public void loadautotiles() {
        try {
            autotiles.clear();
            autotiles at = new autotiles();
            Json json = new Json();
            FileHandle f = Gdx.files.absolute(curdir + "/auto.json");
            at = json.fromJson(autotiles.class, f);
            autotiles = at.getAutotiles();
            //refreshAutoMgmt();
        } catch (Exception e) {
        }
    }

    public void saveRecents() {
        Json json = new Json();
        FileHandle fh = Gdx.files.absolute(basepath+"NotTiled/recents.json");
        fh.writeString(json.prettyPrint(recents), false);
    }

    public void loadTsetManagement() {

        bAddTset = new TextButton(z.addimagetileset, skin);
        bRemoveTset = new TextButton(z.remove, skin);
        bMoveTset = new TextButton(z.moveup, skin);
        bTileSettingsMgmt = new TextButton(z.tiles, skin);
        bPropTset = new TextButton(z.properties, skin);
        bUseTsx = new TextButton(z.importtsxfile, skin);
        bImportFolder = new TextButton(z.importfolder, skin);
        int aaa=1;

        bTsPropSaveAsTsx = new TextButton(z.saveastsx, skin);
        bTsPropSaveAsTsx.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                int dep = ltsetlist.getSelectedIndex();
                if (dep > -1) {
                    tileset t = tilesets.get(dep);
                    //t.setUsetsx(true);
                    t.setTsxfile(t.getName() + ".tsx");
                    saveTsx(dep);
                    msgbox(z.filesaved);
                }

            }
        });

        bTsPropSaveAsPNG = new TextButton(z.exporttopng, skin);
        bTsPropSaveAsPNG.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                int dep = ltsetlist.getSelectedIndex();
                if (dep > -1) {
                    saveTsetAsPNG(dep);
                }

            }
        });


        bUseTsx.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "seltsx", "file", new String[]{".tsx"}, tTsetMgmt);
            }
        });

        bImportFolder.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfolder, "selfolder", "dir", new String[]{}, tTsetMgmt);
            }
        });


        ltsetlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

        bBackTset = new TextButton(z.back, skin);
        bBackTset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });


        bTsetMgmt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int saiz = tilesets.size();


                String[] srr = new String[saiz];
                for (int i = 0; i < saiz; i++) {
                    srr[i] = tilesets.get(i).getName();
                }
                ltsetlist.setItems(srr);


                gotoStage(tTsetMgmt);
                frompick = false;
            }
        });


        bAddTset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //add tileset
                FileDialog(z.selectfile, "addtset", "file", new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"}, tTsetMgmt);
            }
        });


        bPropTset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tilesets.size() > 0) {
                    int dex = ltsetlist.getSelectedIndex();
                    selTsetID = dex;
                    tileset ct = tilesets.get(dex);
                    fTsPropName.setText("");
                    fTsPropSource.setText("");
                    fTsPropTsxFile.setText("");
                    cbTsPropUseTsx.setChecked(false);
                    fTsPropTsw.setText("0");
                    fTsPropTsh.setText("0");
                    fTsPropMargin.setText("0");
                    fTsPropSpacing.setText("0");
                    fTsPropCols.setText("0");
                    fTsPropTc.setText("0");
                    fTsPropTrans.setText("");

                    if (ct.getName() != null) fTsPropName.setText(ct.getName());
                    if (ct.getSource() != null) fTsPropSource.setText(ct.getSource());
                    if (ct.getTsxfile() != null) fTsPropTsxFile.setText(ct.getTsxfile());
                    cbTsPropUseTsx.setChecked(ct.isUsetsx());
                    if (ct.getTilewidth() != 0)
                        fTsPropTsw.setText(Integer.toString(ct.getTilewidth()));
                    if (ct.getTileheight() != 0)
                        fTsPropTsh.setText(Integer.toString(ct.getTileheight()));
                    if (ct.getColumns() != 0)
                        fTsPropCols.setText(Integer.toString(ct.getColumns()));
                    if (ct.getTilecount() != 0)
                        fTsPropTc.setText(Integer.toString(ct.getTilecount()));
                    if (ct.getMargin() != 0)
                        fTsPropMargin.setText(Integer.toString(ct.getMargin()));
                    if (ct.getSpacing() != 0)
                        fTsPropSpacing.setText(Integer.toString(ct.getSpacing()));

                    if (ct.getColumns() != 0)
                        fTsPropCols.setText(Integer.toString(ct.getColumns()));
                    if (ct.getTrans() != null) fTsPropTrans.setText(ct.getTrans());

                    gotoStage(tTsProp);
                }
            }
        });


        bMoveTset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = ltsetlist.getSelectedIndex();
                int saiz = tilesets.size();

                if (dex > 0) {
                    java.util.Collections.swap(tilesets, dex, dex - 1);
                    CacheAllTset();
                    //reindexing
                    templastID = 1;
						/*
						for (int i=0;i<tilesets.size();i++){
							tilesets.get(i).setFirstgid(templastID);
							templastID+=tilesets.get(i).getTilecount();
						}
						*/
                    String[] srr = new String[saiz];
                    for (int i = 0; i < saiz; i++) {
                        srr[i] = tilesets.get(i).getName();
                    }
                    ltsetlist.setItems(srr);
                    ltsetlist.setSelectedIndex(dex - 1);
                }
            }
        });

        bRemoveTset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                int saiz = tilesets.size();
                if (saiz > 0) {
                    int dex = ltsetlist.getSelectedIndex();
                    tilesets.remove(dex);
                    java.util.List<Integer> nyot = new ArrayList<Integer>();
                    CacheAllTset();
                    if (seltset > 0) seltset -= 1;
                    //reindexing
                    templastID = 1;
						/*
						for (int i=0;i<tilesets.size();i++){
							tilesets.get(i).setFirstgid(templastID);
							templastID+=tilesets.get(i).getTilecount();
						}
						*/
                    saiz -= 1;
                    String[] srr = new String[saiz];
                    for (int i = 0; i < saiz; i++) {
                        srr[i] = tilesets.get(i).getName();
                    }
                    if (srr != null) ltsetlist.setItems(srr);

                    if (dex > 0) {
                        if (saiz > 0) ltsetlist.setSelectedIndex(dex - 1);
                    } else {
                        if (saiz > 0) ltsetlist.setSelectedIndex(0);
                    }
                }
            }
        });


        tTsetMgmt = new Table();
        tTsetMgmt.setFillParent(true);
        tTsetMgmt.defaults().width(btnx).height(btny);
        ScrollPane scrollPane8 = new ScrollPane(ltsetlist);
        tTsetMgmt.add(new Label(z.tileset, skin)).padBottom(2).row();
        tTsetMgmt.add(scrollPane8).height(btny * 4).padBottom(2).row();
        tTsetMgmt.add(bAddTset).padBottom(2).row();
        tTsetMgmt.add(bUseTsx).padBottom(2).row();
        tTsetMgmt.add(bImportFolder).padBottom(2).row();
        tTsetMgmt.add(bPropTset).padBottom(2).row();
        tTsetMgmt.add(bTileSettingsMgmt).padBottom(2).row();
        tTsetMgmt.add(bTsPropSaveAsTsx).padBottom(2).row();
        tTsetMgmt.add(bTsPropSaveAsPNG).padBottom(2).row();
        tTsetMgmt.add(bMoveTset).padBottom(2).row();
        tTsetMgmt.add(bRemoveTset).padBottom(2).row();
        tTsetMgmt.add(bBackTset).padBottom(2);
    }

    public void loadNewFile() {
		/*
		 TextField fNFilename, fNCurdir, fNTsw,fNTsh,fNTw,fNTh;
		 SelectBox sbNMapFormat;
		 TextButton bNSelDir, bNNew,bNCancel,bNAnew;
		*/
        com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter filter = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();
        bNSelDir = new TextButton(z.selectfolder, skin);
        bNNew = new TextButton(z.createnewfile, skin);
        bNNewplus = new TextButton(z.ok, skin);

        bNCancel = new TextButton(z.cancel, skin);

        bNCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!cue("newcancel") && lockUI) return;
                backToMap();
            }
        });

        bNSelDir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfolder, "newseldir", "dir", new String[]{}, tNewFile);
            }
        });

        bNNew.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!softcue("newmap") && lockUI) return;
                if (fNTsh.getText() == null) fNTsh.setText("32");
                if (fNTsw.getText() == null) fNTsw.setText("32");
                if (fNTh.getText() == null) fNTh.setText("20");
                if (fNTw.getText() == null) fNTw.setText("20");
                prefs.putString("Tsw", fNTsw.getText());
                prefs.putString("Tsh", fNTsh.getText());
                prefs.putString("Tw", fNTw.getText());
                prefs.putString("Th", fNTh.getText());
                prefs.flush();
                newtmxfile(true);
                backToMap();
                cue("newmap");

            }
        });

        bNNewplus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!softcue("usetemplate") && lockUI) return;
                gotoStage(tTemplate);
                refreshTemplate();
                cue("usetemplate");
            }
        });



        fNFilename = new TextField("Map01.tmx", skin);




        fNCurdir = new TextField(prefs.getString("curdir", basepath+"NotTiled"), skin);
        fNCurdir.setDisabled(true);
        fNTsw = new TextField(prefs.getString("Tsw", "20"), skin);
        fNTsw.setTextFieldFilter(filter);
        fNTsh = new TextField(prefs.getString("Tsh", "20"), skin);
        fNTsh.setTextFieldFilter(filter);
        fNTw = new TextField(prefs.getString("Tw", "20"), skin);
        fNTw.setTextFieldFilter(filter);
        fNTh = new TextField(prefs.getString("Th", "20"), skin);
        fNTh.setTextFieldFilter(filter);
        sbNMapFormat = new SelectBox(skin);
        sbNMapFormat.setItems((Object[]) new String[]{"csv", "base64", "base64-zlib", "base64-gzip", "xml"});
        sbNMapRenderOrder = new SelectBox(skin);
        sbNMapRenderOrder.setItems((Object[]) new String[]{"right-down", "left-down", "right-up", "left-up"});
        sbNMapOrientation = new SelectBox(skin);
        sbNMapOrientation.setItems((Object[]) new String[]{"orthogonal", "isometric"});

        tNewFile = new Table();
        tNewFile.defaults().width(btnx).height(btny);
        tNewFile.setFillParent(true);
        tNewFile.add(new Label(z.filename, skin)).width(btnx / 2 - 2);
        tNewFile.add(fNFilename).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.directory, skin)).colspan(2).row();
        tNewFile.add(fNCurdir).padBottom(1).colspan(2).row();
        tNewFile.add(bNSelDir).padBottom(1).colspan(2).row();
        tNewFile.add(new Label(z.tilewidth, skin)).width(btnx / 2 - 2);
        tNewFile.add(fNTsw).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.tileheight, skin)).width(btnx / 2 - 2);
        tNewFile.add(fNTsh).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.mapwidth, skin)).width(btnx / 2 - 2);
        tNewFile.add(fNTw).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.mapheight, skin)).width(btnx / 2 - 2);
        tNewFile.add(fNTh).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.mapformat, skin)).width(btnx / 2 - 2);
        tNewFile.add(sbNMapFormat).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.renderorder, skin)).width(btnx / 2 - 2);
        tNewFile.add(sbNMapRenderOrder).padBottom(1).width(btnx / 2).row();
        tNewFile.add(new Label(z.orientation, skin)).width(btnx / 2 - 2);
        tNewFile.add(sbNMapOrientation).padBottom(2).width(btnx / 2).row();
        tNewFile.add(bNNewplus).padBottom(1).colspan(2).row();


        tNewFile.add(bNCancel).padBottom(1).colspan(2).row();

    }

    public void loadMapProperties() {
		/*
		 TextField fFilename, fCurdir, fTsw,fTsh,fTw,fTh,fTsx;
		 SelectBox sbMapFormat;
		 CheckBox cbUseTsx;
		 TextButton bUseTsx,bApplyMP, bCancelMP;
		 */
        com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter filter = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();

        bApplyMP = new TextButton(z.apply, skin);
        bCancelMP = new TextButton(z.cancel, skin);
        bPropertiesMap = new TextButton(z.customproperties, skin);
        cb1 = new CheckBox(z.margin, skin);
        cb2 = new CheckBox(z.margin, skin);

        bCancelMP.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });

        bApplyMP.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //newtmxfile(true);
                try {
                    //loadingfile=true;
                    int nTh = Integer.parseInt(fTh.getText());
                    if (nTh > Th) {//expand

                        java.util.List<Long> tStrs = new ArrayList<Long>();
                        java.util.List<Integer> tTsets = new ArrayList<Integer>();

                        for (int k = 0; k < Tw; k++) {
                            tStrs.add((long) 0);
                            tTsets.add(-1);
                        }

                        for (int j = 0; j < layers.size(); j++) {
                            if (layers.get(j).getType()!=layer.Type.TILE) continue;
                            java.util.List<Long> strs = layers.get(j).getStr();
                            java.util.List<Integer> tsets = layers.get(j).getTset();
                            for (int i = 0; i < nTh - Th; i++) {

                                if (cb2.isChecked()) {
                                    strs.addAll( 0, tStrs );
                                    tsets.addAll( 0, tTsets );
                                }else{
                                    strs.addAll( tStrs );
                                    tsets.addAll( tTsets );

                                }
                            }
                        }
                    } else if (nTh < Th) {//shrink
                        for (int i = 0; i < Th - nTh; i++) {
                            for (int k = 0; k < Tw; k++) {
                                for (int j = 0; j < layers.size(); j++) {
                                    if (layers.get(j).getType()!=layer.Type.TILE) continue;
                                    layers.get(j).getStr().remove(layers.get(j).getStr().size() - 1);
                                    layers.get(j).getTset().remove(layers.get(j).getTset().size() - 1);

                                }
                            }
                        }
                    }
                    Th = nTh;

                    int nTw = Integer.parseInt(fTw.getText());
                    if (nTw > Tw) {//expand
                        java.util.List<Long> tStrs = new ArrayList<Long>();
                        java.util.List<Integer> tTsets = new ArrayList<Integer>();

                        for (int k = 0; k < nTw - Tw; k++) {
                            tStrs.add((long) 0);
                            tTsets.add(-1);
                        }

                        for (int j = 0; j < layers.size(); j++) {
                            if (layers.get(j).getType()!=layer.Type.TILE) continue;
                            java.util.List<Long> strs = layers.get(j).getStr();
                            java.util.List<Integer> tsets = layers.get(j).getTset();

                            for (int i = Th; i > 0; i--) {
                                int moyeng = i * Tw;
                                if (cb1.isChecked()) moyeng -=Tw;
                                strs.addAll(moyeng, tStrs);
                                tsets.addAll(moyeng, tTsets);
                            }
                        }
                    } else if (nTw < Tw) {//shrink
                        for (int i = Tw * Th; i > 0; i--) {
                            for (int j = 0; j < layers.size(); j++) {
                                if (layers.get(j).getType()!=layer.Type.TILE) continue;
                                if ((i) % Tw == 0) {
                                    for (int k = 1; k <= Tw - nTw; k++) {
                                        layers.get(j).getStr().remove(i - k);
                                        layers.get(j).getTset().remove(i - k);
                                    }
                                }
                            }
                        }
                    }
                    Tw = nTw;

                    Tsh = Integer.parseInt(fTsh.getText());
                    Tsw = Integer.parseInt(fTsw.getText());
                    mapFormat = sbMapFormat.getSelected().toString();
                    renderorder = sbMapRenderOrder.getSelected().toString();
                    orientation = sbMapOrientation.getSelected().toString();
                    resetCaches();
                    resetMinimap();

                    backToMap();
                    //loadingfile=false;


                } catch (Exception e) {
                    msgbox(z.error);
                    ErrorBung(e, "errorlog.txt");
                }
            }
        });

        bProperties.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fFilename.setText(curfile);
                fCurdir.setText(curdir);
                if (!tsxFile.equalsIgnoreCase("")) fTsx.setText(tsxFile);
                fTw.setText(Integer.toString(Tw));
                fTh.setText(Integer.toString(Th));
                fTsw.setText(Integer.toString(Tsw));
                fTsh.setText(Integer.toString(Tsh));
                cb1.setChecked( false );
                cb2.setChecked( false );

                sbMapFormat.setSelected(mapFormat);
                sbMapRenderOrder.setSelected(renderorder);
                sbMapOrientation.setSelected(orientation);
                gotoStage(tProperties);
            }
        });

        fFilename = new TextField("", skin);
        fFilename.setDisabled(true);
        fCurdir = new TextField("", skin);
        fCurdir.setDisabled(true);
        fTsw = new TextField("", skin);
        fTsw.setTextFieldFilter(filter);
        fTsh = new TextField("", skin);
        fTsh.setTextFieldFilter(filter);
        fTw = new TextField("", skin);
        fTw.setTextFieldFilter(filter);
        fTh = new TextField("", skin);
        fTh.setTextFieldFilter(filter);

        fTsx = new TextField("", skin);
        fTsx.setDisabled(true);

        sbMapFormat = new SelectBox(skin);
        sbMapFormat.setItems( (Object[])new String[]{"csv", "base64", "base64-zlib", "base64-gzip", "xml"});
        sbMapRenderOrder = new SelectBox(skin);
        sbMapRenderOrder.setItems((Object[]) new String[]{"right-down", "left-down", "right-up", "left-up"});
        sbMapOrientation = new SelectBox(skin);
        sbMapOrientation.setItems((Object[]) new String[]{"orthogonal", "isometric"});


        cbUseTsx = new CheckBox(z.usetsxfile, skin);

        tProperties = new Table();
        tProperties.setFillParent(true);
        tProperties.defaults().width(btnx).height(btny);
        tProperties.add(new Label(z.file, skin)).width(btnx / 2 - 2);
        tProperties.add(fFilename).padBottom(2).width(btnx / 2).row();
        tProperties.add(fCurdir).padBottom(2).colspan(2).row();

        tProperties.add(new Label(z.tilewidth, skin)).width(btnx / 2 - 2);
        tProperties.add(fTsw).padBottom(2).width(btnx / 2).row();
        tProperties.add(new Label(z.tileheight, skin)).width(btnx / 2 - 2);
        tProperties.add(fTsh).padBottom(2).width(btnx / 2).row();
        tProperties.add(new Label(z.mapwidth, skin)).width(btnx / 2 - 2);
        tProperties.add(fTw).padBottom(2).width(btnx / 2).row();

        tProperties.add().width(btnx / 2 - 2);
        tProperties.add(cb1).padBottom(2).width(btnx / 2).row();

        tProperties.add(new Label(z.mapheight, skin)).width(btnx / 2 - 2);
        tProperties.add(fTh).padBottom(2).width(btnx / 2).row();

        tProperties.add().width(btnx / 2 - 2);
        tProperties.add(cb2).padBottom(2).width(btnx / 2).row();

        tProperties.add(new Label(z.mapformat, skin)).width(btnx / 2 - 2);
        tProperties.add(sbMapFormat).padBottom(2).width(btnx / 2).row();
        tProperties.add(new Label(z.renderorder, skin)).width(btnx / 2 - 2);
        tProperties.add(sbMapRenderOrder).padBottom(2).width(btnx / 2).row();
        tProperties.add(new Label(z.orientation, skin)).width(btnx / 2 - 2);
        tProperties.add(sbMapOrientation).padBottom(5).width(btnx / 2).row();

        tProperties.add(bPropertiesMap).padBottom(2).colspan(2).row();
        tProperties.add(bApplyMP).padBottom(2).colspan(2).row();
        tProperties.add(bCancelMP).padBottom(2).colspan(2).row();

    }

    public void loadTileManagement() {

        bTileSettingsMgmt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ltsetlist.getSelectedIndex() != -1) {
                    selTsetID = ltsetlist.getSelectedIndex();
                    gotoStage(tTileMgmt);
                }

            }
        });

        pNewTerrain = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }


                tileset ts = tilesets.get(selTsetID);

                terrain tr = new terrain();
                tr.setName(input);
                tr.setTile(newTerrainID);
                ts.getTerrains().add(tr);
                ts.setSelTerrain(ts.getTerrains().size() - 1);

            }

            @Override
            public void canceled() {
            }

        };
        bAddTileLayer = new TextButton(z.addnew, skin);
        bMassAddProp = new TextButton(z.masstileproperties, skin);
        bTileCollision = new TextButton("Tile collision", skin);

        bRemoveTileLayer = new TextButton(z.remove, skin);
        bReplaceTileLayer = new TextButton(z.replace, skin);
        bMoveTileLayer = new TextButton(z.moveup, skin);
        bTileAnimations = new TextButton(z.animation, skin);
        bTerrainEditor = new TextButton(z.terraineditor, skin);
        bPropsTileLayer = new TextButton(z.customproperties, skin);
        ltilelist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
        bBackTileLayer = new TextButton(z.back, skin);
        bBackTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //gotoStage(tTsetMgmt);
                if (frompick) {
                    onToPicker();
                    stage.clear();
                    lastStage = nullTable;
                } else {
                    gotoStage(tTsetMgmt);
                }
            }
        });

        //anim
        bAddTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                pickTile("addanim");

            }
        });
        //terraineditor
        bTerrainEditor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                pickTile("terraineditor");

            }
        });
        //massprop
        bMassAddProp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lPropID.setText(z.customproperties);
                fPropName.setText("");
                fPropVal.setText("");
                sender = "mass";
                gotoStage(tPropEditor);

            }
        });

        bReplaceTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (ltilelist.getSelectedIndex() >= 0) pickTile("replaceanim");
            }
        });

        //not implemented yet
        bTileCollision.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile("collision");
            }
        });

        bMoveTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = ltilelist.getSelectedIndex();
                if (dex > 0) {


                    java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                    java.util.Collections.swap(tiles, dex, dex - 1);
                    String[] srr = new String[tiles.size()];
                    for (int i = 0; i < tiles.size(); i++) {
                        srr[i] = Integer.toString(tiles.get(i).getTileID());
                    }

                    ltilelist.setItems(srr);

                    ltilelist.setSelectedIndex(dex - 1);
                }
            }
        });

        bRemoveTileLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tilesets.size() > 0) {

                    java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                    if (tiles.size() > 0) {
                        int dex = ltilelist.getSelectedIndex();
                        tiles.remove(dex);

                        String[] srr = new String[tiles.size()];
                        for (int i = 0; i < tiles.size(); i++) {
                            srr[i] = Integer.toString(tiles.get(i).getTileID());
                        }

                        ltilelist.setItems(srr);

                        if (dex > 0) {
                            if (tiles.size() > 0) ltilelist.setSelectedIndex(dex - 1);
                        } else {
                            if (tiles.size() > 0) ltilelist.setSelectedIndex(0);
                        }
                    }
                }
            }
        });


        tTileMgmt = new Table();
        tTileMgmt.setFillParent(true);
        tTileMgmt.defaults().width(btnx).height(btny).padBottom(2);
        //ScrollPane scrollPane2 = new ScrollPane(ltilelist);
        tTileMgmt.add(new Label(z.tilesetting, skin)).row();
        //tTileMgmt.add(scrollPane2).height(btny*4).padBottom(5).row();
        //tTileMgmt.add(bAddTileLayer).row();
        tTileMgmt.add(bMassAddProp).row();
        tTileMgmt.add(bPropsTileLayer).row();
        tTileMgmt.add(bTileAnimations).row();

        //tTileMgmt.add(bTileCollision).row();

        tTileMgmt.add(bTerrainEditor).row();
        //tTileMgmt.add(bMoveTileLayer).row();
        //tTileMgmt.add(bReplaceTileLayer).row();
        //tTileMgmt.add(bRemoveTileLayer).row();
        tTileMgmt.add(bBackTileLayer);


    }

    public void pickTile(String sender) {
        //lastStage.setVisible(false);  <---- SI BANGKE YANG BIKIN ERROR!
        stage.clear(); // <---THE HERO WE DESERVED!!!!
        Gdx.input.setInputProcessor(im);
        kartu = "pickanim";
        tilePicker = sender;
    }

    public void loadTilesetProperties() {
		/*
		 TextButton bTsPropOK,bTsPropCancel,bTsPropSaveAsTsx;
		 TextField fTsPropName, fTsPropSource, fTsPropTrans;
		 TextField fTsPropSpacing,fTsPropMargin,fTsPropTsxFile;
		 TextField fTsPropTsw, fTsPropTsh, fTsPropTc, fTsPropCols;
		 CheckBox cbTsPropUseTsx;
		*/
        bTsPropOK = new TextButton(z.apply, skin);
        bTsPropCustomProp = new TextButton(z.customproperties, skin);
        bTsPropCancel = new TextButton(z.cancel, skin);

        bTsPropChangeSource = new TextButton(z.changesource, skin);
        cbTsPropUseTsx = new CheckBox(z.usetsxfile, skin);
        fTsPropName = new TextField("", skin);
        fTsPropSource = new TextField("", skin);
        fTsPropSource.setDisabled(true);
        fTsPropTrans = new TextField("", skin);
        fTsPropTrans.setTextFieldFilter(tffcolor);
        fTsPropSpacing = new TextField("", skin);
        fTsPropSpacing.setTextFieldFilter(tffint);
        fTsPropMargin = new TextField("", skin);
        fTsPropMargin.setTextFieldFilter(tffint);
        fTsPropTsxFile = new TextField("", skin);
        fTsPropTsxFile.setDisabled(true);
        fTsPropTsw = new TextField("", skin);
        fTsPropTsw.setTextFieldFilter(tffint);
        fTsPropTsh = new TextField("", skin);
        fTsPropTsh.setTextFieldFilter(tffint);
        fTsPropTc = new TextField("", skin);
        fTsPropTc.setTextFieldFilter(tffint);
        fTsPropCols = new TextField("", skin);
        fTsPropCols.setTextFieldFilter(tffint);
        Table tp = new Table();
        tp.defaults().width(btnx).height(btny);
        tTsProp = new Table();
        tTsProp.setFillParent(true);

        bTsPropCancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (frompick) {
                    onToPicker();
                    lastStage = nullTable;
                } else {
                    gotoStage(tTsetMgmt);
                }
            }
        });

        bTsPropOK.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tileset t = tilesets.get(selTsetID);
                if (fTsPropName.getText() != "") t.setName(fTsPropName.getText());
                if (!t.getSource().equalsIgnoreCase(fTsPropSource.getText())){

                    //Gdx.app.log( "LLL","LL" );
                    ///
                    String internalpath = "rusted_warfare/assets/tilesets";
                    String tempdiro = "", tempdiri = "", foredirint = "", foredirext = "";

                    String foredir, tempdir, combo;
                    foredir = curdir;//should be tsxpath to folloe tsx pathing but whatever!!
                    //errors+=tsxpath+"ole\n";
                    if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                        foredir = foredir.substring(0, foredir.length() - 1);
                    }
                    t.setSource( fTsPropSource.getText() );
                    tempdiro = t.getSource();
                    tempdiri = t.getSource();

                    foredirint = curdir;
                    foredirext = curdir;

                    while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                        tempdiro = tempdiro.substring(3);
                        if (foredirext.lastIndexOf("/")==-1){
                            foredirext = "";

                        }else
                        {
                            foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                        }
                    }

                    if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                        tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                        tempdiri = tempdiri.replace("tilesets/", "");
                    }


                    FileHandle filehand = Gdx.files.internal(foredirint + "/" + tempdiri);

                    if (!filehand.exists()) {
                        filehand = Gdx.files.absolute(foredirext + "/" + tempdiro);
                        if (!filehand.exists()) {
                            filehand = Gdx.files.absolute( tempdiro);
                            if (!filehand.exists()) {
                                filehand = Gdx.files.internal("empty.jpeg");
                            }
                        }
                    }

                    ///
                    t.setTexture(new Texture(filehand));
                    t.setPixmap(pixmapfromtexture(t.getTexture(), t.getTrans()));

                }

                if (fTsPropSource.getText() != "") t.setSource(fTsPropSource.getText());
                if (fTsPropTsxFile.getText() != "") t.setTsxfile(fTsPropTsxFile.getText());
                t.setUsetsx(cbTsPropUseTsx.isChecked());

                if (fTsPropCols.getText() != "0" && fTsPropCols.getText() != "") {
                    t.setWidth(Integer.parseInt(fTsPropCols.getText()));
                    t.setColumns(Integer.parseInt(fTsPropCols.getText()));

                }
                if (fTsPropTc.getText() != "0" && fTsPropTc.getText() != "") {
                    t.setTilecount(Integer.parseInt(fTsPropTc.getText()));
                    t.setHeight(Integer.parseInt(fTsPropTc.getText()) / t.getColumns());
                }
                if (fTsPropTsw.getText() != "0" && fTsPropTsw.getText() != "") {
                    int newWidth = Integer.parseInt(fTsPropTsw.getText());
                    if (t.getTilewidth() != newWidth) {
                        t.setTilewidth(newWidth);
                        t.setWidth(t.getOriginalwidth() / newWidth);
                        t.setColumns(t.getWidth());
                        t.setTilecount(t.getOriginalheight() / t.getHeight() * t.getWidth());
                    }
                }

                if (fTsPropTsh.getText() != "0" && fTsPropTsh.getText() != "") {
                    int newHeight = Integer.parseInt(fTsPropTsh.getText());
                    if (t.getTileheight() != newHeight) {
                        t.setTileheight(newHeight);
                        t.setHeight(t.getOriginalheight() / newHeight);
                        t.setTilecount(t.getHeight() * t.getWidth());
                    }
                    t.setTileheight(Integer.parseInt(fTsPropTsh.getText()));
                }


                if (fTsPropTrans.getText() != "") {
                    t.setTrans(fTsPropTrans.getText());
                } else {
                    t.setTrans(null);
                }
                if (fTsPropMargin.getText() != "") {
                    t.setMargin(Integer.parseInt(fTsPropMargin.getText()));
                } else {
                    t.setMargin(0);
                }

                if (fTsPropSpacing.getText() != "") {
                    t.setSpacing(Integer.parseInt(fTsPropSpacing.getText()));
                } else {
                    t.setSpacing(0);
                }
                try {
                    FileHandle f = Gdx.files.absolute(curdir + "/" + tilesets.get(ltsetlist.getSelectedIndex()).getSource());
                    if (f.exists()) {

                        tilesets.get(ltsetlist.getSelectedIndex()).setTexture(new Texture(Gdx.files.absolute(curdir + "/" + tilesets.get(ltsetlist.getSelectedIndex()).getSource())));
                        if (tilesets.get(ltsetlist.getSelectedIndex()).getTrans() != null) {
                            tilesets.get(ltsetlist.getSelectedIndex()).setTexture(chromaKey(tilesets.get(ltsetlist.getSelectedIndex()).getTexture(), tilesets.get(ltsetlist.getSelectedIndex()).getTrans()));
                        }
                    }
                } catch (Exception e) {
                }

                ////////////////////
                int dexo = ltsetlist.getSelectedIndex();
					/*
					templastID=1;
					for (int i=0;i<tilesets.size();i++){
						tilesets.get(i).setFirstgid(templastID);
						templastID+=tilesets.get(i).getTilecount();
					}
					*/
                int saiz = tilesets.size();
                String[] srr = new String[saiz];
                for (int i = 0; i < saiz; i++) {
                    srr[i] = tilesets.get(i).getName();
                }
                ltsetlist.setItems(srr);
                ltsetlist.setSelectedIndex(dexo);
                if (frompick) {
                    onToPicker();
                    lastStage = nullTable;
                } else {
                    gotoStage(tTsetMgmt);
                }
            }


        });


        bTsPropChangeSource.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileDialog(z.selectfile, "replacetset", "file", new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"}, tTsProp);

            }
        });

        tp.add(new Label(z.tileset + ": " + z.properties, skin)).colspan(2).padBottom(5).row();
        tp.add(new Label(z.name, skin)).width(btnx / 2);
        tp.add(fTsPropName).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.source, skin)).width(btnx / 2);
        tp.add(fTsPropSource).width(btnx / 2).padBottom(2).row();
        tp.add(bTsPropChangeSource).colspan(2).padBottom(2).row();
        tp.add(new Label(z.tsxfile, skin)).width(btnx / 2);
        tp.add(fTsPropTsxFile).width(btnx / 2).padBottom(2).row();
        tp.add().width(btnx / 2);
        tp.add(cbTsPropUseTsx).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.keycolor, skin)).width(btnx / 2);
        tp.add(fTsPropTrans).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.tilewidth, skin)).width(btnx / 2);
        tp.add(fTsPropTsw).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.tileheight, skin)).width(btnx / 2);
        tp.add(fTsPropTsh).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.tilecolumn, skin)).width(btnx / 2);
        tp.add(fTsPropCols).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.tilecount, skin)).width(btnx / 2);
        tp.add(fTsPropTc).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.spacing, skin)).width(btnx / 2);
        tp.add(fTsPropSpacing).width(btnx / 2).padBottom(2).row();
        tp.add(new Label(z.margin, skin)).width(btnx / 2);
        tp.add(fTsPropMargin).width(btnx / 2).padBottom(2).row();
        tp.add(bTsPropCustomProp).colspan(2).padBottom(2).row();
        tp.add(bTsPropOK).colspan(2).padBottom(2).row();
        tp.add(bTsPropCancel).colspan(2).padBottom(2).row();

        ScrollPane scrollPanen = new ScrollPane(tp);
        tTsProp.add(scrollPanen).top();


    }

    private void loadNewLayer(){
        tLayerNew = new Table();
        tLayerNew.setFillParent(true);
        Button bLayerTile = new Button(skin);
        Button bLayerObject = new Button(skin);
        Button bLayerImage = new Button(skin);
        blayerBack = new TextButton(z.back, skin);

        Image img1 = new Image(txTypeTile);
        bLayerTile.add(img1).width(btnx*0.15f).padLeft(20);
        bLayerTile.add(new Label(z.tilelayer,skin)).width(btnx*0.80f);

        Image img2 = new Image(txTypeObject);
        bLayerObject.add(img2).width(btnx*0.15f).padLeft(20);
        bLayerObject.add(new Label(z.objectgroup,skin)).width(btnx*0.80f);

        Image img3 = new Image(txTypeImage);
        bLayerImage.add(img3).width(btnx*0.15f).padLeft(20);
        bLayerImage.add(new Label(z.imagelayer,skin)).width(btnx*0.80f);

        tLayerNew.add(new Label(z.addnew, skin)).width(btnx).row();
        tLayerNew.add(bLayerTile).width(btnx).height(btny*2).row();
        tLayerNew.add(bLayerObject).width(btnx).height(btny*2).row();
        tLayerNew.add(bLayerImage).width(btnx).height(btny*2).row();
        tLayerNew.add(blayerBack).height(btny).width(btnx);

        blayerBack.addListener(listBack);


        bLayerTile.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newLayerType = layer.Type.TILE;
                Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                backToMap();
            }
        });

        bLayerObject.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newLayerType = layer.Type.OBJECT;
                Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                backToMap();
            }
        });


        bLayerImage.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newLayerType = layer.Type.IMAGE;
                Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                backToMap();
            }
        });

    }

    public void loadFrameManagement() {
		/*
		 com.badlogic.gdx.scenes.scene2d.ui.List<String> lanimlist;
		 com.badlogic.gdx.Input.TextInputListener pNewAnim;
		 TextButton bAddAnimLayer, bEditAnimLayer, bRemoveAnimLayer;

		 */
        bTileAnimations.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile("addanim");
                anime = true;
            }
        });

        pEditFrame = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }
                int dexo = lframelist.getSelectedIndex();

                java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                tiles.get(selTileID).getAnimation().get(dexo).setDuration(Integer.parseInt(input));

                String[] srr = new String[tiles.get(selTileID).getAnimation().size()];
                for (int i = 0; i < tiles.get(selTileID).getAnimation().size(); i++) {
                    String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                    String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                    srr[i] = aaa + " (" + bbb + "ms)";
                }
                lframelist.setItems(srr);

                lframelist.setSelectedIndex(dexo);
            }

            @Override
            public void canceled() {
            }

        };
        //iFrameView=new Image(skin);
        lFrameID = new Label(z.frame, skin);
        bAddFrameLayer = new TextButton(z.addnew, skin);
        bRemoveFrameLayer = new TextButton(z.remove, skin);
        bReplaceFrameLayer = new TextButton(z.replace, skin);
        bDuration = new TextButton(z.set, skin);
        bMoveFrameLayer = new TextButton(z.moveup, skin);
        fDurationframe = new TextField(prefs.getString("duration", "500"), skin);
        fDurationframe.setTextFieldFilter(tffint);
        bEditFrameLayer = new TextButton(z.edit + " " + z.duration, skin);
        lframelist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
        bBackFrameLayer = new TextButton(z.back, skin);
        bBackFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selTileID = -1;
                anime = false;
					/*
					java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();

					String[] srr = new String[tiles.size()];
					for (int i=0;i < tiles.size();i++)
					{
						srr[i] = Integer.toString(tiles.get(i).getTileID());
						ltilelist.setItems(srr);
					}
					*/
                gotoStage(tTileMgmt);
            }
        });

        bDuration.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fDurationframe.getText() == "") {
                    fDurationframe.setText("500");
                }
                if (Integer.parseInt(fDurationframe.getText()) == 0) {
                    fDurationframe.setText("500");
                }
                prefs.putString("duration", fDurationframe.getText());
                prefs.flush();
            }
        });


        bAddFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pickTile("addframe");
            }
        });

        bReplaceFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (lframelist.getSelectedIndex() >= 0) pickTile("replaceframe");
            }
        });

        bEditFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                if (tiles.get(selTileID).getAnimation().size() > 0) {
                    Gdx.input.getTextInput(pEditFrame, "Edit Duration", Integer.toString(tiles.get(selTileID).getAnimation().get(lframelist.getSelectedIndex()).getDuration()), "");
                }

            }
        });

        bMoveFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int dex = lframelist.getSelectedIndex();
                if (dex > 0) {

                    java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                    java.util.Collections.swap(tiles.get(selTileID).getAnimation(), dex, dex - 1);

                    int dexa = tiles.get(selTileID).getAnimation().size();
                    if (dexa > 0) {
                        String[] srr = new String[dexa];

                        for (int i = 0; i < dexa; i++) {
                            String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                            String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                            srr[i] = aaa + " (" + bbb + "ms)";
                        }
                        lframelist.setItems(srr);
                        lframelist.setSelectedIndex(dex - 1);
                    }
                }
            }
        });

        bRemoveFrameLayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deletinganim = true;
                java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();

                if (tiles.get(selTileID).getAnimation().size() > 0) {
                    int dex = lframelist.getSelectedIndex();
                    tiles.get(selTileID).getAnimation().remove(dex);
                    tiles.get(selTileID).setActiveFrameID(0);
                    tiles.get(selTileID).setTimer(0);

                    String[] srr = new String[tiles.get(selTileID).getAnimation().size()];
                    for (int i = 0; i < tiles.get(selTileID).getAnimation().size(); i++) {
                        String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                        String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                        srr[i] = aaa + " (" + bbb + "ms)";
                    }

                    lframelist.setItems(srr);

                    if (dex > 0) {
                        if (tiles.get(selTileID).getAnimation().size() > 0)
                            lframelist.setSelectedIndex(dex - 1);
                    } else {
                        if (tiles.get(selTileID).getAnimation().size() > 0)
                            lframelist.setSelectedIndex(0);
                    }
                }
                deletinganim = false;

            }
        });


        tFrameMgmt = new Table();
        tFrameMgmt.defaults().width(btnx).height(btny);
        tFrameMgmt.setFillParent(true);
        ScrollPane scrollPane3 = new ScrollPane(lframelist);
        tFrameMgmt.add(lFrameID).padBottom(5).colspan(2).row();
        tFrameMgmt.add(scrollPane3).height(btny * 4).padBottom(5).colspan(2).row();
        tFrameMgmt.add(new Label(z.default_ + " " + z.duration + "(ms)", skin)).padBottom(5).colspan(2).row();
        tFrameMgmt.add(fDurationframe).width(btnx / 2 - 2).padBottom(5);
        tFrameMgmt.add(bDuration).width(btnx / 2).padLeft(2).padBottom(5).row();
        tFrameMgmt.add(bAddFrameLayer).padBottom(5).colspan(2).row();
        tFrameMgmt.add(bEditFrameLayer).padBottom(5).colspan(2).row();
        tFrameMgmt.add(bMoveFrameLayer).padBottom(5).colspan(2).row();
        tFrameMgmt.add(bReplaceFrameLayer).padBottom(5).colspan(2).row();
        tFrameMgmt.add(bRemoveFrameLayer).padBottom(5).colspan(2).row();
        tFrameMgmt.add(bBackFrameLayer).padBottom(5).colspan(2);

    }

    public void FileDialog(String prompt, final String dialog, final String fileordir, String[] filter, final Table exitpoint) {
        if (nativefilechooser){
            switch (fileordir){
                case "file":
                    nativeOpen(dialog, exitpoint);
                    break;
                case "dir":
                    nativeDirectory(dialog, exitpoint);
                    break;
            }

            return;
        }


        FileChooser fc = new FileChooser(prompt, skin, "file", filter) {
            @Override
            protected void result(Object object) {
                if (object.equals("OK")) {
                    try {
                        FileHandle file;
                        exitDialog(exitpoint);
                        if (fileordir.equalsIgnoreCase("file")) {
                            file = getFile();

                            if (file.path().length() >= 0) {
                                openedfile = file.path();
                                lastpath = file.parent().path();
                                prefs.putString("lastpath", lastpath);
                                prefs.flush();
                                tujuanDialog(dialog, file);
                            }

                        } else {

                            file = getDirectory();

                            if (file.path().length() >= 0) {
                                lastpath = file.path();
                                prefs.putString("lastpath", lastpath);
                                prefs.flush();
                                tujuanDialog(dialog, file);
                            }

                        }


                    } catch (Exception e) {
                        exitDialog(exitpoint);
                    }
                } else {
                    exitDialog(exitpoint);
                }
            }
        };

        FileHandle fhtest = Gdx.files.absolute( lastpath );
        if (fhtest.exists()){
            fc.setDirectory(Gdx.files.absolute(lastpath));
        }else{
            fc.setDirectory(Gdx.files.absolute(basepath));
        }

        tOpen.clear();
        tOpen.add(fc).width(ssx).height(ssy * .7f).width( ssx*.9f );
        gotoStage(tOpen);
    }

    public void exitDialog(Table exitpoint) {
        if (exitpoint == null) {
            backToMap();
        } else if (exitpoint == nullTable) {
            onToPicker();
        } else {
            gotoStage(exitpoint);
        }
    }

    public void tujuanDialog(String dialog, FileHandle file) {
        openedfile = file.path();
        switch (dialog) {
            case "propfile":
                bPropValfile.setText(file.file().getName());
                break;
            case "proppng":
                byte[] fileContent = file.readBytes();
                String asu = android.util.Base64.encodeToString(fileContent, 0);
                fPropVal.setText(asu);
                break;
            case "open":
                if (file.extension().equalsIgnoreCase( "ntp" )){
                    backToMap();
                    curdir = file.parent().path();
                    FileHandle tmpfolder=Gdx.files.absolute(basepath+"NotTiled/Temp");
                    unzip(file,tmpfolder);
                    recents.addrecent( file.path() );
                    saveRecents();
                    playgame( basepath+"NotTiled/Temp", "index.tmx");
                }else{
                    curdir = file.parent().path();
                    loadtmx(openedfile);
                }

                break;
            case "background":
                background = new Texture(Gdx.files.absolute(openedfile));

                break;
            case "customfont":
                tfCustomFont.setText(openedfile);
                break;
            case "imagesource":
                String nn = file.name();
                FileHandle f = file;
                if (f.name().indexOf(".") > 0) {
                    nn = f.name().substring(0, f.name().lastIndexOf("."));
                }

                String[] tc = curdir.split("/");
                String[] ts = f.path().split("/");

                boolean git = false;
                String pre = "", post = "";
                for (int i = 0; i < tc.length; i++) {
                    if (git) {
                        pre += "../";
                        continue;
                    }
                    if (ts.length <= i) {
                        git = true;
                        pre += "../";
                        continue;
                    }
                    if (!ts[i].equalsIgnoreCase(tc[i])) {
                        git = true;
                        pre += "../";
                    }
                }

                git = false;
                for (int i = 0; i < ts.length; i++) {
                    if (git) {
                        post += ts[i];
                        if (i < ts.length - 1) post += "/";
                        continue;
                    }
                    if (tc.length <= i) {
                        git = true;
                        post += ts[i];
                        if (1 < ts.length - 1) post += "/";
                        continue;
                    }
                    if (!tc[i].equalsIgnoreCase(ts[i])) {
                        git = true;
                        post += ts[i];
                        if (i < ts.length - 1) post += "/";
                    }
                }
                String cocos = pre + post;
                if (cocos.endsWith("/")) cocos = cocos.substring(0, cocos.length() - 1);

                tfImageSource.setText(cocos);
                break;
            case "saveas":
                saveasdir = file.path();
                Gdx.input.getTextInput(pSaveAs, z.saveas, "new.tmx", "");

                break;

            case "selfolder":
                errors = "";
                String dir = file.path();
                FileHandle[] filehandles;
                filehandles = Gdx.files.absolute(dir).list();
                for (int i = 0; i < filehandles.length; i++) {

                    switch (filehandles[i].extension().toLowerCase()) {
                        case "tsx":
                            loadtsx(filehandles[i].path(), tilesets, curdir);
                            break;
                        case "png":
                        case "jpg":
                        case "jpeg":
                        case "gif":
                        case "bmp":
                            addImageTset(filehandles[i]);
                            break;

                    }
                }
                int saiz = tilesets.size();
                String[] srr = new String[saiz];
                for (int i = 0; i < saiz; i++) {
                    srr[i] = tilesets.get(i).getName();
                }
                ltsetlist.setItems(srr);
                ltsetlist.setSelectedIndex(saiz - 1);
                status(errors, 5);
                break;
            case "seltsx":
                errors = "";
                loadingfile = true;
                loadtsx(openedfile, tilesets, curdir);

                saiz = tilesets.size();
                srr = new String[saiz];
                for (int i = 0; i < saiz; i++) {
                    srr[i] = tilesets.get(i).getName();
                }
                ltsetlist.setItems(srr);
                ltsetlist.setSelectedIndex(saiz - 1);
                CacheAllTset();
                status(errors, 5);
                loadingfile = false;
                break;
            case "newseldir":
                String path = file.path();

                if (path.length() >= 0) {
                    prefs.putString("lastpath", path).flush();
                    fNCurdir.setText(path);
                }

                break;
            case "quickaddtset":
                thefile = file;
                Gdx.app.log("",file.extension()+"||||"+file.path());
                if (file.extension().equalsIgnoreCase( "tmx" )){
                    extractTSET( file.path() );
                    showtsetselection( tilesets2 );
                }else {
                    fImportWidth.setText( Tsw + "" );
                    fImportHeight.setText( Tsh + "" );
                    cImportEmbed.setChecked( false );
                    gotoStage( tImport );
                    cue( "import" );
                    resetcam( false );
                }
                return;

            case "addtset":
                thefile = file;
                fImportWidth.setText(Tsw + "");
                fImportHeight.setText(Tsh + "");
                cImportEmbed.setChecked(false);
                gotoStage(tImport);

                return;
            case "replacetset":
                File fd = file.file();
                fd = file.file();
                fTsPropSource.setText(fd.getAbsolutePath());
                CacheAllTset();


                break;
        }
    }

    public void addImageTset(FileHandle f) {
        try {
            SimpleImageInfo s = new SimpleImageInfo(f.file());
            tileset t = new tileset();
            String nn = f.name();
            if (f.name().indexOf(".") > 0) {
                nn = f.name().substring(0, f.name().lastIndexOf("."));
            }
            t.setName(nn);


            String[] tc = curdir.split("/");
            String[] ts = f.path().split("/");

            boolean git = false;
            String pre = "", post = "";
            for (int i = 0; i < tc.length; i++) {
                if (git) {
                    pre += "../";
                    continue;
                }
                if (ts.length <= i) {
                    git = true;
                    pre += "../";
                    continue;
                }
                if (!ts[i].equalsIgnoreCase(tc[i])) {
                    git = true;
                    pre += "../";
                }
            }

            git = false;
            for (int i = 0; i < ts.length; i++) {
                if (git) {
                    post += ts[i];
                    if (i < ts.length - 1) post += "/";
                    continue;
                }
                if (tc.length <= i) {
                    git = true;
                    post += ts[i];
                    if (1 < ts.length - 1) post += "/";
                    continue;
                }
                if (!tc[i].equalsIgnoreCase(ts[i])) {
                    git = true;
                    post += ts[i];
                    if (i < ts.length - 1) post += "/";
                }
            }
            String cocos = pre + post;
            if (cocos.endsWith("/")) cocos = cocos.substring(0, cocos.length() - 1);


            t.setSource(cocos);
            int Tswa = s.getWidth();
            int Tsha = s.getHeight();
            if (!fImportWidth.getText().equalsIgnoreCase( "" )) {
                Tswa = Integer.parseInt( fImportWidth.getText() );
                Tsha = Integer.parseInt( fImportHeight.getText() );
            }
            t.setOriginalwidth(s.getWidth());
            t.setOriginalheight(s.getHeight());
            t.setColumns(s.getWidth() / Tswa);
            t.setTilecount((s.getHeight() / Tswa) * (s.getWidth() / Tsha));
            if (t.getTilecount()==0) t.setTilecount( 1 );
            t.setWidth(s.getWidth() / Tswa);
            t.setHeight(s.getHeight() / Tsha);
            t.setTilewidth(Tswa);
            t.setTileheight(Tsha);
            t.setTrans("");
            t.setTexture(new Texture(f));
            t.setPixmap(pixmapfromtexture(t.getTexture(), t.getTrans()));

            t.setFirstgid(requestGid());

            if (cImportEmbed.isChecked()) {
                byte[] fileContent = f.readBytes();
                String asu = android.util.Base64.encodeToString(fileContent, 0);
                property po = new property("embedded_png", asu);
                t.getProperties().add(po);
                cImportEmbed.setChecked(false);
            }
            curspr = t.getFirstgid();
            tilesets.add(t);
            recenterpick();
        } catch (Exception e) {
            errors += "\nError: " + f.name();
            ErrorBung( e, "eRRROBUNG.txt" );
        }
    }

    public void backToMap() {
        kartu = "world";
        Gdx.input.setInputProcessor(im);
        resetMassprops();
        issettingtile=false;
        pickAuto=false;
        recenterpick();
        //updateMinimap();
        stage.clear(); //is this the bug?
        face.showbanner(false);
    }

    public void resetMassprops(){
        massprops.clear();
        if (tilesets.size()>0) {
            for (int i = 0; i < tilesets.get(seltset).getTilecount(); i++) {
                massprops.add(false);
            }
        }
    }

    public void onToPicker() {
        kartu = "tile";
        Gdx.input.setInputProcessor(im);
        stage.clear();
    }

    public void gotoStage(Table table) {
        stage.clear();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        kartu = "stage";
        lastStage = table;

        face.showbanner(false);
        if (!bypassads) {
            if (lastStage == tMenu) face.showbanner(true);
            if (lastStage == tMap) face.showbanner(true);
        }
    }

    //////////////////////////////////////////////////////
//            XML PROCESSOR
//////////////////////////////////////////////////////
    public void saveMap(String actualPath) {

        //Craete handle for the actual file
        FileHandle actualFile = Gdx.files.absolute(actualPath);
        recents.addrecent(actualPath);
        saveRecents();
        prefs.putString("lof", actualFile.path());
        prefs.flush();

        //Check and create the temp folder
        FileHandle fh2 = Gdx.files.absolute(basepath+"NotTiled/");
        if (!fh2.exists()) fh2.mkdirs();
        FileHandle fh3 = Gdx.files.absolute(basepath+"NotTiled/Temp");
        if (fh3.exists() && !fh3.isDirectory()) fh3.delete();
        if (!fh3.exists()) fh3.mkdirs();

        //Saving the file to the temp folder first
        String tempPath = basepath+"NotTiled/Temp/" + Integer.toString((int) (Math.random()*100000)) +".tmx";
        buildTMX(tempPath);
        FileHandle tempFile = Gdx.files.absolute(tempPath);

        //copy the temp file to the actual file
        tempFile.copyTo(actualFile);

        //delete the temp file.
        tempFile.delete();
    }

    public void buildTMX(String tempPath) {
        FileHandle tempfile = Gdx.files.absolute(tempPath);
        if (autotiles.size()>0) {
            autotiles at = new autotiles( autotiles );
            Json json = new Json();
            writeThisAbs( curdir + "/auto.json", json.prettyPrint( at ) );
        }
        ////

        try {
            if (tempfile.exists()) tempfile.delete();
            FileOutputStream fos = new FileOutputStream(tempfile.file());

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            XmlSerializer srz = factory.newSerializer();
            srz.setOutput(fos, "UTF-8");
            srz.startDocument(null, Boolean.valueOf(true));
            srz.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            srz.startTag(null, "map");
            srz.attribute("", "version", "1.2")
                    .attribute("", "orientation", orientation)
                    .attribute("", "renderorder", renderorder)
                    .attribute("", "width", Integer.toString(Tw))
                    .attribute("", "height", Integer.toString(Th))
                    .attribute("", "tilewidth", Integer.toString(Tsw))
                    .attribute("", "tileheight", Integer.toString(Tsh))
                    .attribute("", "nextobjectid", Integer.toString(curid));

            if (properties.size() > 0) {
                srz.startTag(null, "properties");
                for (int m = 0; m < properties.size(); m++) {
                    srz.startTag(null, "property");
                    if (properties.get(m).getName() != null)
                        srz.attribute("", "name", properties.get(m).getName());
                    String txx = properties.get(m).getValue();
                    if (txx != null) {
                        if (txx.contains("\n")) {
                            srz.text("\n" + txx + "\n");
                        } else {
                            if (properties.get(m).getType()=="boolean") {
                                srz.attribute( "", "value", txx );
                            }else{
                                srz.attribute( "", "value", txx );
                            }
                        }
                    }
                    srz.endTag(null, "property");
                }
                srz.endTag(null, "properties");
            }


            if (tilesets.size() > 0) {
                for (int i = 0; i < tilesets.size(); i++) {

                    tileset t = tilesets.get(i);
                    if (t.isUsetsx()) {
                        srz.startTag(null, "tileset");
                        srz.attribute("", "source", t.getTsxfile());
                        srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
                        srz.endTag(null, "tileset");
                        saveTsx(i);
                        continue;
                    }
                    srz.startTag(null, "tileset");
                    srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
                    srz.attribute("", "name", t.getName());
                    if (t.getMargin() != 0)
                        srz.attribute("", "margin", Integer.toString(t.getMargin()));
                    if (t.getSpacing() != 0)
                        srz.attribute("", "spacing", Integer.toString(t.getSpacing()));
                    srz.attribute("", "columns", Integer.toString(t.getColumns()));
                    srz.attribute("", "tilecount", Integer.toString(t.getTilecount()));
                    srz.attribute("", "tileheight", Integer.toString(t.getTileheight()));
                    srz.attribute("", "tilewidth", Integer.toString(t.getTilewidth()));

                    if (t.getProperties().size() > 0) {
                        srz.startTag(null, "properties");
                        for (int m = 0; m < t.getProperties().size(); m++) {
                            srz.startTag(null, "property");
                            if (t.getProperties().get(m).getName() != null)
                                srz.attribute("", "name", t.getProperties().get(m).getName());
                            //if (t.getProperties().get(m).getType()!=null) srz.attribute("", "type", t.getProperties().get(m).getType().toLowerCase());
                            if (t.getProperties().get(m).getValue() != null) {
                                if (t.getProperties().get(m).getName().equalsIgnoreCase("embedded_tileset")) {
                                    java.util.List<String> values = splitEqually(t.getProperties().get(m).getValue(), 100);
                                    for (int g = 0; g < values.size(); g++) {
                                        srz.text(values.get(g) + "\n");
                                    }

                                } else {
                                    String txx = t.getProperties().get(m).getValue();
                                    if (txx != null) {
                                        if (txx.contains("\n")) {
                                            srz.text("\n" + txx + "\n");
                                        } else {
                                            srz.attribute("", "value", txx);
                                        }
                                    }
                                }
                            }
                            srz.endTag(null, "property");
                        }
                        srz.endTag(null, "properties");
                    }
                    if (t.getSource() != null) {
                        srz.startTag(null, "image");
                        srz.attribute("", "source", t.getSource())
                                .attribute("", "width", Integer.toString(t.getOriginalwidth()))
                                .attribute("", "height", Integer.toString(t.getOriginalheight()));
                        if (t.getTrans() != null && t.getTrans() != "")
                            srz.attribute("", "trans", t.getTrans());
                        srz.endTag(null, "image");
                    }
                    if (t.getTerrains().size() > 0) {
                        srz.startTag(null, "terraintypes");
                        for (int ee = 0; ee < t.getTerrains().size(); ee++) {
                            srz.startTag(null, "terrain");
                            srz.attribute("", "name", t.getTerrains().get(ee).getName());
                            srz.attribute("", "tile", Integer.toString(t.getTerrains().get(ee).getTile()));
                            srz.endTag(null, "terrain");
                        }
                        srz.endTag(null, "terraintypes");
                    }
                    if (t.getTiles().size() > 0) {
                        for (int u = 0; u < t.getTiles().size(); u++) {
                            tile oj = t.getTiles().get(u);


                            srz.startTag(null, "tile");
                            srz.attribute("", "id", Integer.toString(t.getTiles().get(u).getTileID()));
                            if (!t.getTiles().get(u).getTerrainString().equalsIgnoreCase("-1,-1,-1,-1")) {
                                srz.attribute("", "terrain", t.getTiles().get(u).getTerrainString());

                            }


                            if (oj.getAnimation().size() > 0) {
                                srz.startTag(null, "animation");
                                for (int m = 0; m < oj.getAnimation().size(); m++) {
                                    srz.startTag(null, "frame");
                                    srz.attribute("", "tileid", Integer.toString(oj.getAnimation().get(m).getTileID()));
                                    srz.attribute("", "duration", Integer.toString(oj.getAnimation().get(m).getDuration()));
                                    srz.endTag(null, "frame");
                                }
                                srz.endTag(null, "animation");
                            }
                            if (oj.getProperties().size() > 0) {
                                srz.startTag(null, "properties");
                                for (int m = 0; m < oj.getProperties().size(); m++) {
                                    srz.startTag(null, "property");
                                    if (oj.getProperties().get(m).getName() != null)
                                        srz.attribute("", "name", oj.getProperties().get(m).getName());
                                    if (oj.getProperties().get(m).getType() != null & oj.getProperties().get(m).getType() != "") {
                                        if (!oj.getProperties().get(m).getType().equalsIgnoreCase("string")) {
                                            srz.attribute("", "type", oj.getProperties().get(m).getType().toLowerCase());

                                        }
                                    }

                                    String txx = oj.getProperties().get(m).getValue();
                                    if (txx != null) {
                                        if (txx.contains("\n")) {
                                            srz.text("\n" + txx + "\n");
                                        } else {
                                            srz.attribute("", "value", txx);
                                        }
                                    }
                                    srz.endTag(null, "property");
                                }
                                srz.endTag(null, "properties");
                            }

                            srz.endTag(null, "tile");
                        }
                    }

                    srz.endTag(null, "tileset");
                }
            }

            for (int j = 0; j < layers.size(); j++) {

                layer lay = layers.get(j);
                if (lay.getType() == layer.Type.TILE) {

                    srz.startTag(null, "layer");
                    srz.attribute("", "name", lay.getName());
                    if (!lay.isVisible()) srz.attribute("", "visible", "0");
                    if (lay.getOpacity() != 0)
                        srz.attribute("", "opacity", Float.toString(lay.getOpacity()));
                    srz.attribute("", "width", Integer.toString(Tw));
                    srz.attribute("", "height", Integer.toString(Th));

                    srz.startTag(null, "data");
                    switch (mapFormat) {
                        case "csv":
                            srz.attribute("", "encoding", "csv");
                            srz.text("\n" + savecsv(j) + "\n");
                            break;
                        case "base64":
                            srz.attribute("", "encoding", "base64");
                            srz.text("\n   " + decoder.savebase64(j, layers) + "\n  ");
                            break;
                        case "base64-zlib":
                            srz.attribute("", "encoding", "base64");
                            srz.attribute("", "compression", "zlib");
                            srz.text("\n   " + decoder.savebase64zlib(j, layers) + "\n  ");
                            break;
                        case "base64-gzip":
                            srz.attribute("", "encoding", "base64");
                            srz.attribute("", "compression", "gzip");
                            srz.text("\n   " + decoder.savebase64gzip(j, layers) + "\n  ");
                            break;
                        case "xml":
                            for (int n = 0; n < lay.getStr().size(); n++) {
                                srz.startTag(null, "tile");
                                srz.attribute("", "gid", Long.toString(lay.getStr().get(n)));
                                srz.endTag(null, "tile");
                            }
                            break;
                    }
                    srz.endTag(null, "data");
                    ///
                    if (lay.getProperties().size() > 0) {
                        srz.startTag(null, "properties");
                        for (int m = 0; m < lay.getProperties().size(); m++) {
                            srz.startTag(null, "property");
                            if (lay.getProperties().get(m).getName() != null)
                                srz.attribute("", "name", lay.getProperties().get(m).getName());
                            if (lay.getProperties().get(m).getType() != null & lay.getProperties().get(m).getType() != "") {
                                if (!lay.getProperties().get(m).getType().equalsIgnoreCase("string")) {
                                    srz.attribute("", "type", lay.getProperties().get(m).getType().toLowerCase());

                                }
                            }

                            String txx = lay.getProperties().get(m).getValue();
                            if (txx != null) {
                                if (txx.contains("\n")) {
                                    srz.text("\n" + txx + "\n");
                                } else {
                                    srz.attribute("", "value", txx);
                                }
                            }
                            srz.endTag(null, "property");
                        }
                        srz.endTag(null, "properties");
                    }

                    ///
                    srz.endTag(null, "layer");
                }else if (lay.getType() == layer.Type.IMAGE) {

                    srz.startTag(null, "imagelayer");
                    srz.attribute("", "id", Integer.toString(j));
                    srz.attribute("", "name", lay.getName());
                    srz.attribute("", "offsetx", Float.toString(lay.getOffsetX()));
                    srz.attribute("", "offsety",  Float.toString(lay.getOffsetY()));
                    int lckd = lay.isLocked() ? 1 : 0;
                    if (lckd==1) srz.attribute("", "locked",  Integer.toString(lckd));
                    if (!lay.isVisible()) srz.attribute("", "visible", "0");
                    if (lay.getOpacity() != 0)
                        srz.attribute("", "opacity", Float.toString(lay.getOpacity()));

                    srz.startTag(null, "image");
                    srz.attribute("", "source", lay.getImage());
                    srz.attribute("", "trans", lay.getTrans());
                    srz.attribute("", "width", Integer.toString(lay.getImagewidth()));
                    srz.attribute("", "height", Integer.toString(lay.getImageheight()));
                    srz.endTag(null, "image");

                    srz.endTag(null, "imagelayer");

                } else if (lay.getType() == layer.Type.OBJECT) {


                    srz.startTag(null, "objectgroup");
                    srz.attribute("", "name", lay.getName());
                    if (!lay.isVisible()) srz.attribute("", "visible", "false");

                    if (lay.getObjects().size() > 0) {
                        for (int l = 0; l < lay.getObjects().size(); l++) {
                            obj oj = lay.getObjects().get(l);
                            srz.startTag(null, "object");
                            if (oj.getName() != null && !oj.getName().equalsIgnoreCase( "" )) srz.attribute("", "name", oj.getName());
                            if (oj.getType() != null && !oj.getType().equalsIgnoreCase( "" )) srz.attribute("", "type", oj.getType());
                            srz.attribute("", "id", Integer.toString(oj.getId()));
                            String xx = "", yy = "", ww = "", hh = "", rorot = "";
                            Float tmpf;
                            tmpf = oj.getX();
                            if (tmpf % 1 == 0) {
                                xx = Integer.toString(tmpf.intValue());
                            } else {
                                xx = Float.toString(tmpf);
                            }

                            tmpf = oj.getY();
                            if (tmpf % 1 == 0) {
                                yy = Integer.toString(tmpf.intValue());
                            } else {
                                yy = Float.toString(tmpf);
                            }

                            tmpf = oj.getW();
                            if (tmpf != 0) {
                                if (tmpf % 1 == 0) {
                                    ww = Integer.toString(tmpf.intValue());
                                } else {
                                    ww = Float.toString(tmpf);
                                }
                            }

                            tmpf = oj.getH();
                            if (tmpf != 0) {
                                if (tmpf % 1 == 0) {
                                    hh = Integer.toString(tmpf.intValue());
                                } else {
                                    hh = Float.toString(tmpf);
                                }
                            }

                            tmpf = oj.getRotation();
                            if (tmpf != 0) {
                                if (tmpf % 1 == 0) {
                                    rorot = Integer.toString(tmpf.intValue());
                                } else {
                                    rorot = Float.toString(tmpf);
                                }
                                srz.attribute("", "rotation", rorot);
                            }

                            srz.attribute("", "x", xx);

                            if (oj.getGid() != 0)
                                srz.attribute("", "gid", Integer.toString(oj.getGid()));
                            if (oj.getShape() != null) {
                                switch (oj.getShape()) {
                                    case "ellipse":
                                        srz.attribute("", "y", yy);
                                        srz.attribute("", "width", ww);
                                        srz.attribute("", "height", hh);

                                        srz.startTag(null, "ellipse");
                                        srz.endTag(null, "ellipse");
                                        break;
                                    case "point":
                                        srz.attribute("", "y", yy);
                                        srz.startTag(null, "point");
                                        srz.endTag(null, "point");
                                        break;
                                    case "polygon":
                                        srz.attribute("", "y", yy);
                                        srz.startTag(null, "polygon");
                                        srz.attribute("", "points", oj.getPointsString());
                                        srz.endTag(null, "polygon");
                                        break;
                                    case "polyline":
                                        srz.attribute("", "y", yy);
                                        srz.startTag(null, "polyline");
                                        srz.attribute("", "points", oj.getPointsString());
                                        srz.endTag(null, "polyline");
                                        break;
                                    case "text":
                                        srz.attribute("", "y", yy);
                                        srz.attribute("", "width", ww);
                                        srz.attribute("", "height", hh);

                                        srz.startTag(null, "text");
                                        srz.attribute("", "wrap", Boolean.toString(oj.isWrap()));
                                        srz.text(oj.getText());
                                        srz.endTag(null, "text");
                                        break;
                                    case "image":
                                       // float newyy = Float.parseFloat(yy);
                                        //newyy += Tsh;
                                        //srz.attribute("", "y", Float.toString(newyy));
                                        srz.attribute("", "y", yy);
                                        srz.attribute("", "width", ww);
                                        srz.attribute("", "height", hh);

                                        break;
                                    default:
                                        srz.attribute("", "y", yy);
                                        srz.attribute("", "width", ww);
                                        srz.attribute("", "height", hh);


                                        break;
                                }
                            } else {
                                srz.attribute("", "width", ww);
                                srz.attribute("", "height", hh);

                            }
                            if (oj.getProperties().size() > 0) {
                                srz.startTag(null, "properties");
                                for (int m = 0; m < oj.getProperties().size(); m++) {
                                    srz.startTag(null, "property");
                                    if (oj.getProperties().get(m).getName() != null)
                                        srz.attribute("", "name", oj.getProperties().get(m).getName());
                                    //if (oj.getProperties().get(m).getType()!=null) srz.attribute("", "type", oj.getProperties().get(m).getType().toLowerCase());

                                    String txx = oj.getProperties().get(m).getValue();
                                    if (txx != null) {
                                        if (txx.contains("\n")) {
                                            srz.text("\n" + txx + "\n");
                                        } else {
                                            srz.attribute("", "value", txx);
                                        }
                                    }
                                    srz.endTag(null, "property");
                                }
                                srz.endTag(null, "properties");
                            }
                            srz.endTag(null, "object");
                        }
                    }
                    srz.endTag(null, "objectgroup");
                }
            }

            srz.endDocument();
            srz.flush();
            fos.close();


        } catch (Exception e) {
            FileHandle fil = Gdx.files.absolute(basepath+"errorlog.txt");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
            fil.writeString(exceptionAsString, false);


        }


    }

    public void saveTsx(int index) {
        FileHandle file = Gdx.files.absolute(curdir + "/" + tilesets.get(index).getTsxfile());

        try {
            FileOutputStream fos = new FileOutputStream(file.file());

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
            XmlSerializer srz = factory.newSerializer();
            srz.setOutput(fos, "UTF-8");
            srz.startDocument(null, Boolean.valueOf(true));
            srz.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            tileset t = tilesets.get(index);
            srz.startTag(null, "tileset");
            srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
            srz.attribute("", "name", t.getName());
            if (t.getMargin() != 0) srz.attribute("", "margin", Integer.toString(t.getMargin()));
            if (t.getSpacing() != 0) srz.attribute("", "spacing", Integer.toString(t.getSpacing()));
            srz.attribute("", "columns", Integer.toString(t.getColumns()));
            srz.attribute("", "tilecount", Integer.toString(t.getTilecount()));
            srz.attribute("", "tileheight", Integer.toString(t.getTileheight()));
            srz.attribute("", "tilewidth", Integer.toString(t.getTilewidth()));

            if (t.getProperties().size() > 0) {
                srz.startTag(null, "properties");
                for (int m = 0; m < t.getProperties().size(); m++) {
                    srz.startTag(null, "property");
                    if (t.getProperties().get(m).getName() != null)
                        srz.attribute("", "name", t.getProperties().get(m).getName());
                    //if (!t.getProperties().get(m).getType().equalsIgnoreCase("") && !t.getProperties().get(m).getType().equalsIgnoreCase("string"))
                    //if (!t.getProperties().get(m).getValue().isEmpty())
                        srz.attribute("", "value", t.getProperties().get(m).getValue());
                    srz.endTag(null, "property");
                }
                srz.endTag(null, "properties");
            }

            srz.startTag(null, "image");
            if (!t.getSource().isEmpty()) srz.attribute("", "source", t.getSource())
                    .attribute("", "width", Integer.toString(t.getOriginalwidth()))
                    .attribute("", "height", Integer.toString(t.getOriginalheight()));
            if (t.getTrans() != null && t.getTrans() != "")
                srz.attribute("", "trans", t.getTrans());
            srz.endTag(null, "image");
            if (t.getTerrains().size() > 0) {
                srz.startTag(null, "terraintypes");
                for (int ee = 0; ee < t.getTerrains().size(); ee++) {
                    srz.startTag(null, "terrain");
                    srz.attribute("", "name", t.getTerrains().get(ee).getName());
                    srz.attribute("", "tile", Integer.toString(t.getTerrains().get(ee).getTile()));
                    srz.endTag(null, "terrain");
                }
                srz.endTag(null, "terraintypes");
            }
            if (t.getTiles().size() > 0) {
                for (int u = 0; u < t.getTiles().size(); u++) {
                    tile oj = t.getTiles().get(u);


                    srz.startTag(null, "tile");
                    srz.attribute("", "id", Integer.toString(t.getTiles().get(u).getTileID()));
                    if (!t.getTiles().get(u).getTerrainString().equalsIgnoreCase("-1,-1,-1,-1")) srz.attribute("", "terrain", t.getTiles().get(u).getTerrainString());

                    if (oj.getAnimation().size() > 0) {
                        srz.startTag(null, "animation");
                        for (int m = 0; m < oj.getAnimation().size(); m++) {
                            srz.startTag(null, "frame");
                            srz.attribute("", "tileid", Integer.toString(oj.getAnimation().get(m).getTileID()));
                            srz.attribute("", "duration", Integer.toString(oj.getAnimation().get(m).getDuration()));
                            srz.endTag(null, "frame");
                        }
                        srz.endTag(null, "animation");
                    }
                    if (oj.getProperties().size() > 0) {
                        srz.startTag(null, "properties");
                        for (int m = 0; m < oj.getProperties().size(); m++) {
                            srz.startTag(null, "property");
                            if (oj.getProperties().get(m).getName() != null)
                                srz.attribute("", "name", oj.getProperties().get(m).getName());
                            if (!oj.getProperties().get(m).getType().equalsIgnoreCase("") && !oj.getProperties().get(m).getType().equalsIgnoreCase("string"))
                                srz.attribute("", "type", oj.getProperties().get(m).getType());
                            if (oj.getProperties().get(m).getValue() != null)
                                srz.attribute("", "value", oj.getProperties().get(m).getValue());
                            srz.endTag(null, "property");
                        }
                        srz.endTag(null, "properties");
                    }

                    srz.endTag(null, "tile");
                }
            }

            srz.endTag(null, "tileset");
            srz.endDocument();
            srz.flush();
            fos.close();

        } catch (Exception e) {
            FileHandle fil = Gdx.files.absolute(basepath+"errorlog.txt");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
            fil.writeString(exceptionAsString, false);


        }


    }

    public String savecsv(int seltile) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();

        for (int i = 0; i <= layers.get(seltile).getStr().size() - 1; i++) {
            int cek = (i + 1) % Tw;
            sb.append(layers.get(seltile).getStr().get(i));
            if (i != layers.get(seltile).getStr().size() - 1) {
                sb.append(",");
                if (cek == 0) {
                    sb.append("\n");
                }
            }


        }
        return sb.toString();
    }

    public void loadtmx(final String filepath) {
        loadingfile = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        loadmap(filepath);
                        log("tmx loaded.");


                    }
                });
            }
        }).start();
    }

    public void loadtmxnewplus(final String filepath) {
        loadingfile = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread
                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        log("here");
                        curdir = fNCurdir.getText();
                        curdir = curdir.replace("//", "/");
                        curfile = fNFilename.getText();


                        FileHandle from = Gdx.files.absolute( filepath +"/template.tmx");
                        FileHandle to = Gdx.files.absolute( curdir+"/"+curfile);
                        if (from.exists()) {
                            to.write( from.read(), false );
                        }else{
                            loadingfile=false;
                            backToMap();
                            msgbox("Error, template not found!");
                            return;
                        }
                        ////Copy assets, which might contain macro anyway...
                        FileHandle check = Gdx.files.absolute(filepath+"/assets.zip");
                        if (check.exists()) {
                            FileHandle check2 = Gdx.files.absolute(curdir+"/assets.zip");
                            if (!check2.exists()){
                                to = Gdx.files.absolute(curdir);
                                check.copyTo(to);
                                check2 = Gdx.files.absolute( curdir + "/assets.zip" );
                                unzip(check2, to);
                            }
                        }

                        //copy macro
                        check = Gdx.files.absolute(filepath+"/auto.json");
                        if (check.exists()) {
                            FileHandle check2 = Gdx.files.absolute(curdir+"/auto.json");
                            to = Gdx.files.absolute(curdir);
                            check.copyTo(to);
                        }

                        loadmap(curdir+"/"+curfile);
                        Tw = Integer.parseInt(fNTw.getText());
                        Th = Integer.parseInt(fNTh.getText());
                        log(Tw+"---"+Th);

                        undolayer.clear();
                        redolayer.clear();
                        int fill=-1;
                        for (property p: properties){
                            if (p.getName().equalsIgnoreCase( "fill" )){
                                fill = Integer.parseInt( p.getValue() );
                                curspr=fill;
                                setTsetFromCurspr();
                            }
                        }
                        for (int k = 0; k < layers.size(); k++) {
                            java.util.List<Long> ls = new ArrayList<Long>();
                            java.util.List<Integer> lts = new ArrayList<Integer>();

                            for (long i = 0; i < Tw * Th; i++) {
                                if (fill!=-1 && k==0){
                                    ls.add((long) curspr);
                                    lts.add(seltset);
                                }else{
                                    ls.add((long) 0);
                                    lts.add(-1);
                                }
                            }
                            layers.get(k).setStr(ls);
                            layers.get(k).setTset(lts);
                        }

                        kartu = "stage";
                        mode = "tile";
                        curspr = 0;
                        resetcam(false);


                        loadingfile = false;
                        showtsetselection(tilesets);
                        saveMap(curdir + "/" + curfile);

                        uicam.zoom = 0.5f;
                        uicam.update();
                        firstload = loadtime;

                        cue("usetemplateok");
                    }
                });
            }
        }).start();
    }

    public void loadmap(String filepath) {
        stamp=false;
        assemblymode=false;
        log("loading map :"+filepath);
        errors = "";
        recents.addrecent(filepath);
        saveRecents();
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myParser = xmlFactoryObject.newPullParser();
            FileHandle file = Gdx.files.absolute(filepath);
            String path = file.parent().path();

            FileInputStream stream = new FileInputStream(file.file());

            curdir = path;
            curfile = file.file().getName();
            myParser.setInput(stream, null);
            undolayer.clear();
            redolayer.clear();
            cliplayer =null;clipsource=0;
            int event = myParser.getEventType();
            background = null;
            encoding = "";
            tile tempTile = new tile();
            obj tempobj = new obj();
            String isi = "";
            String prName = "";
            String prValue = "";
            int lastPid = 0;
            selgroup = -1;
            selLayer = 0;
            templastID = 1;
            seltset = 0;
            String owner = "";
            layers.clear();
            tilesets.clear();
            properties.clear();
            int curgroupid = -1;
            int curobjid = -1;
            curid = 0;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();


                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (name.equals("map")) {
                            owner = "map";
                            if (myParser.getAttributeValue(null, "nextobjectid") != null) {
                                curid = Integer.parseInt(myParser.getAttributeValue(null, "nextobjectid"));
                            } else {
                                curid = 1;
                            }
                            Tw = Integer.parseInt(myParser.getAttributeValue(null, "width"));
                            Th = Integer.parseInt(myParser.getAttributeValue(null, "height"));
                            Tsw = Integer.parseInt(myParser.getAttributeValue(null, "tilewidth"));
                            Tsh = Integer.parseInt(myParser.getAttributeValue(null, "tileheight"));
                            if (myParser.getAttributeValue(null, "renderorder") != null) {
                                renderorder = myParser.getAttributeValue(null, "renderorder");
                            } else {
                                renderorder = "right-down";
                            }
                            orientation = myParser.getAttributeValue(null, "orientation");

                        }
                        if (name.equals("layer")) {
                            owner = "layer";
                            tempLayer = new layer();
                            tempLayer.setType(layer.Type.TILE);

                            if (myParser.getAttributeValue(null, "visible") != null) {
                                tempLayer.setVisible(Boolean.parseBoolean(myParser.getAttributeValue(null, "visible")));
                            } else {
                                tempLayer.setVisible(true);
                            }
                            if (myParser.getAttributeValue(null, "opacity") != null) {
                                tempLayer.setOpacity(Float.parseFloat(myParser.getAttributeValue(null, "opacity")));
                            }
                            tempLayer.setName(myParser.getAttributeValue(null, "name"));

                        }
                        if (name.equals("terrain")) {
                            terrain tr = new terrain();
                            tr.setName(myParser.getAttributeValue(null, "name"));
                            tr.setTile(Integer.parseInt(myParser.getAttributeValue(null, "tile")));
                            tempTset.getTerrains().add(tr);
                        }


                        if (name.equals("data")) {
                            isi = "";
                            switch (owner) {
                                case "layer":
                                    encoding = myParser.getAttributeValue(null, "encoding");
                                    if (encoding == null) {
                                        encoding = "xml";
                                        mapFormat = "xml";
                                        spr = new ArrayList<Long>();
                                    }
                                    compression = myParser.getAttributeValue(null, "compression");
                                    if (compression == null) {
                                        compression = "no compression";
                                    }
                                    break;
                            }

                        }
                        if (name.equals("tileset")) {
                            tempTset = new tileset();
                            String source = "";

                            source = myParser.getAttributeValue(null, "source");

                            owner = "tileset";
                            alreadyloaded = false;

                            if (myParser.getAttributeValue(null, "firstgid") != null) {
                                tempTset.setFirstgid(Integer.parseInt(myParser.getAttributeValue(null, "firstgid")));
                            } else {
                                tempTset.setFirstgid(requestGid());
                            }
                            if (source == null) {

                                tempTset.setName(myParser.getAttributeValue(null, "name"));
                                if (myParser.getAttributeValue(null, "columns") != null) {
                                    tempTset.setColumns(Integer.parseInt(myParser.getAttributeValue(null, "columns")));
                                    tempTset.setTilecount(Integer.parseInt(myParser.getAttributeValue(null, "tilecount")));
                                    tempTset.setWidth(tempTset.getColumns());
                                    tempTset.setHeight(tempTset.getTilecount() / tempTset.getColumns());
                                }

                                if (myParser.getAttributeValue(null, "margin") != null)
                                    tempTset.setMargin(Integer.parseInt(myParser.getAttributeValue(null, "margin")));
                                if (myParser.getAttributeValue(null, "spacing") != null)
                                    tempTset.setSpacing(Integer.parseInt(myParser.getAttributeValue(null, "spacing")));
                                if (myParser.getAttributeValue(null, "tilewidth") != null) {
                                    tempTset.setTilewidth(Integer.parseInt(myParser.getAttributeValue(null, "tilewidth")));
                                } else {
                                    tempTset.setTilewidth(Tsw);
                                }
                                if (myParser.getAttributeValue(null, "tileheight") != null) {
                                    tempTset.setTileheight(Integer.parseInt(myParser.getAttributeValue(null, "tileheight")));
                                } else {
                                    tempTset.setTileheight(Tsh);
                                }

                                tilesets.add(tempTset);
                            } else {


                                String foredir, tempdir;
                                foredir = curdir;
                                if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                    foredir = foredir.substring(0, foredir.length() - 1);
                                }
                                tempdir = source;

                                loadtsx(foredir + "/" + tempdir,tilesets, curdir);
                                loadingfile = true;
                            }
                        }

                        if (name.equals("image")) {
                            if (owner.equalsIgnoreCase("tileset")) {
                                String internalpath = "rusted_warfare/assets/tilesets";
                                tempTset = tilesets.get(tilesets.size() - 1);
                                //if (myParser.getAttributeValue(null, "trans")!=null) {
                                tempTset.setTrans(myParser.getAttributeValue(null, "trans"));
                                //}
                                if (myParser.getAttributeValue(null, "source") != null) {
                                    tempTset.setSource(myParser.getAttributeValue(null, "source"));
                                }


                                if (!alreadyloaded) {


                                    String foredirint, foredirext, foredir, tempdiro, tempdiri, combo;
                                    foredir = curdir;//should be tsxpath to folloe tsx pathing but whatever!!
                                    if (foredir!="") {
                                        if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                            foredir = foredir.substring(0, foredir.length() - 1);
                                        }
                                    }
                                    tempdiro = tempTset.getSource();
                                    tempdiri = tempTset.getSource();

                                    foredirint = internalpath;
                                    foredirext = curdir;


                                        while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                                            tempdiro = tempdiro.substring(3);
                                            if (foredirext.lastIndexOf("/")==-1){
                                                foredirext = "";

                                            }else
                                            {
                                                foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                                            }
                                        }


                                    if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                                        tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                                        tempdiri = tempdiri.replace("tilesets/", "");
                                    }

                                    FileHandle filehand = Gdx.files.internal(foredirint + "/" + tempdiri);

                                    if (!filehand.exists()) {

                                        filehand = Gdx.files.absolute(foredirext + "/" + tempdiro);
                                        if (!filehand.exists()) {
                                            filehand = Gdx.files.absolute(tempdiro);
                                            if (!filehand.exists()) {
                                                filehand = Gdx.files.internal("empty.jpeg");
                                            }
                                        }
                                    }

                                    try {

                                        tempTset.setTexture(new Texture(filehand));
                                        tempTset.setPixmap(pixmapfromtexture(tempTset.getTexture(), tempTset.getTrans()));
                                        if (tempTset.getTrans() != null) {
                                            tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                        }
                                        if (myParser.getAttributeValue(null, "width") != null) {
                                            tempTset.setOriginalwidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
                                            tempTset.setOriginalheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));
                                        } else {

                                            SimpleImageInfo sii;
                                            sii = new SimpleImageInfo(filehand.read());
                                            tempTset.setOriginalwidth(sii.getWidth());
                                            tempTset.setOriginalheight(sii.getHeight());
                                        }
                                    } catch (Exception e) {
                                        tempTset.setOriginalwidth(0);
                                        tempTset.setOriginalheight(0);
                                        errors += "Not Found: " + tempdiro + "\n" + e;
                                    }
                                }
                                if (tempTset.getColumns() == 0) {
                                    tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                    tempTset.setWidth(tempTset.getColumns());
                                    tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                    tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                                }

                                templastID += tempTset.getWidth() * tempTset.getHeight();
                            }
                            else if (owner.equalsIgnoreCase("imagelayer")) {
                                if (myParser.getAttributeValue(null, "trans") != null) {
                                    tempLayer.setTrans(myParser.getAttributeValue(null, "trans"));
                                }

                                if (myParser.getAttributeValue(null, "source") != null) {
                                    tempLayer.setImage(myParser.getAttributeValue(null, "source"));
                                }
                                tempLayer.setImagewidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
                                tempLayer.setImageheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));

                                if (tempLayer.getImage()!=null) {

                                    String foredirint, foredirext, foredir, tempdiro, tempdiri, combo;
                                    foredir = curdir;//should be tsxpath to folloe tsx pathing but whatever!!
                                    if (foredir!="") {
                                        if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                            foredir = foredir.substring(0, foredir.length() - 1);
                                        }
                                    }
                                    tempdiro = tempLayer.getImage();
                                    tempdiri = tempLayer.getImage();

                                    foredirext = curdir;


                                    while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                                        tempdiro = tempdiro.substring(3);
                                        if (foredirext.lastIndexOf("/")==-1){
                                            foredirext = "";

                                        }else
                                        {
                                            foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                                        }
                                    }


                                    if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                                        tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                                    }

                                        FileHandle filehand = Gdx.files.absolute(foredirext + "/" + tempdiro);
                                        if (!filehand.exists()) {
                                            filehand = Gdx.files.internal("empty.jpeg");
                                        }


                                    try {

                                        tempLayer.setTexture(new Texture(filehand));
                                        tempLayer.setPixmap(pixmapfromtexture(tempLayer.getTexture(), tempLayer.getTrans()));
                                        if (tempLayer.getTrans() != null) {
                                            tempLayer.setTexture(chromaKey(tempLayer.getTexture(), tempLayer.getTrans()));
                                        }

                                    } catch (Exception e) {
                                    }
                                }

                            }

                        }

                        if (name.equals("imagelayer")) {

                            owner = "imagelayer";
                            tempLayer = new layer();
                            tempLayer.setType(layer.Type.IMAGE);

                            if (myParser.getAttributeValue(null, "visible") != null) {
                                tempLayer.setVisible(Boolean.parseBoolean(myParser.getAttributeValue(null, "visible")));
                            } else {
                                tempLayer.setVisible(true);
                            }

                            if (myParser.getAttributeValue(null, "locked") != null) {
                                tempLayer.setLocked(Boolean.parseBoolean(myParser.getAttributeValue(null, "locked")));
                            } else {
                                tempLayer.setLocked(true);
                            }

                            if (myParser.getAttributeValue(null, "opacity") != null) {
                                tempLayer.setOpacity(Float.parseFloat(myParser.getAttributeValue(null, "opacity")));
                            }
                            if (myParser.getAttributeValue(null, "offsetx") != null) {
                                tempLayer.setOffsetX(Float.parseFloat(myParser.getAttributeValue(null, "offsetx")));
                            }
                            if (myParser.getAttributeValue(null, "offsety") != null) {
                                tempLayer.setOffsetY(Float.parseFloat(myParser.getAttributeValue(null, "offsety")));
                            }

                            tempLayer.setName(myParser.getAttributeValue(null, "name"));
                            layers.add(tempLayer);

                        }

                        if (name.equals("objectgroup")) {

                            owner = "objectgroup";
                            tempLayer = new layer();
                            tempLayer.setType(layer.Type.OBJECT);

                            if (myParser.getAttributeValue(null, "visible") != null) {
                                tempLayer.setVisible(Boolean.parseBoolean(myParser.getAttributeValue(null, "visible")));
                            } else {
                                tempLayer.setVisible(true);
                            }
                            if (myParser.getAttributeValue(null, "opacity") != null) {
                                tempLayer.setOpacity(Float.parseFloat(myParser.getAttributeValue(null, "opacity")));
                            }
                            tempLayer.setName(myParser.getAttributeValue(null, "name"));
                            layers.add(tempLayer);

                        }

                        if (name.equals("tile")) {
                            if (owner.equalsIgnoreCase("layer")) {
                                String gid = myParser.getAttributeValue(null, "gid");

                                if (gid != "") spr.add(Long.parseLong(gid));
                            }
                            if (owner.equalsIgnoreCase("tileset")) {
                                tempTile = new tile();
                                tempTile.setTileID(Integer.parseInt(myParser.getAttributeValue(null, "id")));
                                if (myParser.getAttributeValue(null, "terrain") != null) {
                                    tempTile.setTerrain(myParser.getAttributeValue(null, "terrain"));
                                }

                            }
                            oldowner = owner;
                            owner = "tile";


                        }
                        if (name.equals("frame")) {

                            tempTile.getAnimation().add(new frame(Integer.parseInt(myParser.getAttributeValue(null, "tileid")), Integer.parseInt(myParser.getAttributeValue(null, "duration"))));


                        }
                        if (name.equals("property")) {

                            String n = "", t = "", v = "";
                            isi = "";
                            if (myParser.getAttributeValue(null, "name") != null) {
                                n = myParser.getAttributeValue(null, "name");
                            }
                            if (myParser.getAttributeValue(null, "type") != null) {
                                t = myParser.getAttributeValue(null, "type");
                            }
                            if (myParser.getAttributeValue(null, "value") != null) {
                                v = myParser.getAttributeValue(null, "value");
                            }
                            tempe = new property(n, t, v);
                            isi = "";

                        }
                        if (name.equals("object")) {
                            tempobj = new obj();
                            if (myParser.getAttributeValue(null, "id") != null) {
                                int pID = Integer.parseInt(myParser.getAttributeValue(null, "id"));
                                lastPid = pID;
                            } else {
                                lastPid += 1;
                            }

                            tempobj.setId(lastPid);
                            String pName = "";
                            String pType = "";
                            pName = myParser.getAttributeValue(null, "name");
                            pType = myParser.getAttributeValue(null, "type");
                            tempobj.setName(pName);
                            tempobj.setType(pType);
                            tempobj.setX(Float.parseFloat(myParser.getAttributeValue(null, "x")));
                            tempobj.setY(Float.parseFloat(myParser.getAttributeValue(null, "y")));

                            if (myParser.getAttributeValue(null, "gid") != null) {
                                tempobj.setShape("image");
                                tempobj.setGid(Integer.parseInt(myParser.getAttributeValue(null, "gid")));
                                tempobj.setY(Float.parseFloat(myParser.getAttributeValue(null, "y")));
                            }

                            float pWidth, pHeight;
                            if (myParser.getAttributeValue(null, "width") != null) {
                                try {
                                    pWidth = Float.parseFloat(myParser.getAttributeValue(null, "width"));
                                    pHeight = Float.parseFloat(myParser.getAttributeValue(null, "height"));
                                } catch (Exception e) {
                                    pWidth = Tsw;
                                    pHeight = Tsh;
                                    tempobj.setShape("point");
                                }
                            } else {
                                pWidth = Tsw;
                                pHeight = Tsh;
                                tempobj.setShape("point");

                            }
                            tempobj.setW(pWidth);
                            tempobj.setH(pHeight);
                            tempobj.setupBox2D( world );
                            if (myParser.getAttributeValue(null, "rotation") != null)
                                tempobj.setRotation(Float.parseFloat(myParser.getAttributeValue(null, "rotation")));


                            owner = "object";

                        }
                        if (name.equals("polyline")) {
                            tempobj.setShape("polyline");
                            tempobj.setPointsFromString(myParser.getAttributeValue(null, "points"));

                        }
                        if (name.equals("polygon")) {
                            tempobj.setShape("polygon");
                            tempobj.setPointsFromString(myParser.getAttributeValue(null, "points"));

                        }

                        if (name.equals("ellipse")) {
                            tempobj.setShape("ellipse");

                        }
                        if (name.equals("point")) {
                            tempobj.setShape("point");

                        }
                        if (name.equals("text")) {
                            tempobj.setShape("text");
                            tempobj.setWrap(Boolean.parseBoolean(myParser.getAttributeValue(null, "wrap")));
                            isi = "";
                        }
                        break;
                    case XmlPullParser.TEXT:
                        isi += myParser.getText();


                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("object")) {

                            layers.get(layers.size() - 1).getObjects().add(tempobj);
                            curid=lastPid+1;
                        }
                        if (name.equals("tile")) {
                            if (oldowner.equalsIgnoreCase("tileset")) {
                                tempTset.getTiles().add(tempTile);

                            }
                            owner = oldowner;
                            oldowner = "";
                        }
                        if (name.equals("tileset")) {

                            tempTset = null;
                        }
                        if (name.equals("text")) {
                            tempobj.setText(isi);
                        }
                        if (name.equals("property")) {
                            String data = isi.replace("\n", "").trim();
                            String rawe = isi.trim();
                            if (tempe.getValue() != "") {
                                data = tempe.getValue();
                            } else {
                                if (data != "") tempe.setValue(rawe);
                            }

                            if (tempe.getName().equalsIgnoreCase("embedded_png")) {

                                String foredir = "", tempdir = "", combo = "";
                                try {

                                    Texture bucket;
                                    String base64 = data;
                                    byte[] decodedBytes = Base64Coder.decode(base64);
                                    bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));


                                    tempTset.setTexture(bucket);

                                    if (tempTset.getTrans() != null) {
                                        tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                    }

                                    tempTset.setPixmap(pixmapfromtexture(bucket, tempTset.getTrans()));


                                    tempTset.setOriginalwidth(bucket.getWidth());
                                    tempTset.setOriginalheight(bucket.getHeight());
                                    if (tempTset.getColumns() == 0) {
                                        tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                        tempTset.setWidth(tempTset.getColumns());
                                        tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                        tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                                    }

                                    templastID += tempTset.getWidth() * tempTset.getHeight();

                                    alreadyloaded = true;
                                } catch (Exception e) {
                                    ErrorBung(e, "okok.txt");
                                }


                            }

                            switch (owner) {
                                case "object":
                                    tempobj.getProperties().add(tempe);
                                    break;
                                case "tileset":
                                    tempTset.getProperties().add(tempe);
                                    break;
                                case "layer":
                                    tempLayer.getProperties().add(tempe);
                                    break;
                                case "tile":
                                    tempTile.getProperties().add(tempe);
                                    break;
                                case "map":
                                    properties.add(tempe);
                                    break;
                            }

                        }

                        if (name.equals("data")) {


                            if (!encoding.equalsIgnoreCase("xml")) spr = new ArrayList<Long>();

                            if (encoding.equalsIgnoreCase("csv")) {
                                mapFormat = "csv";
                                String data = isi.replace("\n", "");
                                data = data.replace(" ", "");


                                String[] parts = data.split(",");

                                for (int i = 0; i < Tw * Th; i++) {
                                    spr.add(Long.parseLong(parts[i]));
                                }

                            } else if (encoding.equalsIgnoreCase("base64") && compression.equalsIgnoreCase("zlib")) {
                                mapFormat = "base64-zlib";
                                String data = isi.replace("\n", "");
                                data = data.replace(" ", "");
                                spr = decoder.decodeZlib(data, Tw * Th * 4);


                            } else if (encoding.equalsIgnoreCase("base64") && compression.equalsIgnoreCase("gzip")) {
                                mapFormat = "base64-gzip";
                                String data = isi.replace("\n", "");
                                data = data.replace(" ", "");
                                spr = decoder.decodeGzip(data);
                                //writeThis(tempLayer.getName()+ "fkyu.txt",spr.toString());

                            } else if (encoding.equalsIgnoreCase("base64")) {
                                mapFormat = "base64";
                                String data = isi.replace("\n", "");
                                data = data.replace(" ", "");
                                spr = decoder.decode(data);

                            } else if (encoding.equalsIgnoreCase("xml")) {
                                mapFormat = "xml";
                            }

                            tempLayer.setStr(spr);
                            //tempLayer.setTset(cacheTset(spr));
                            layers.add(tempLayer);
                            //selLayer += 1;
                        }


                        break;
                }
                event = myParser.next();
            }

            resetcam(false);

            //uicam.position.set(120,-200,0);//center

            prefs.putString("lof", file.path());
            prefs.flush();

            kartu = "world";
            mode = "tile";
            status(errors, 10);

        } catch (Exception e) {
            newtmxfile(false);
            status("Error opening file.", 3);
            ErrorBung(e, "/maknyus.txt");
        }
        CacheAllTset();
        resetSwatches();
        updateObjectCollision();
        resetCaches();

        selLayer=0;
        //recenterpick();
        //if (layers.size()>0) selLayer = layers.size()-1;

        /*
        if (layers.size()>4)
        {
            if (layers.get(3).getName().equalsIgnoreCase("set"))
            {
                selLayer=3;
            }
        }
        */

        loadautotiles();
        uicam.zoom = 0.5f;
        uicam.update();
        loadingfile = false;
        //resize(ssx,ssy);
        //recenterUI();
        firstload = loadtime;


        if (isCreateRoom) {
            FileHandle ff = Gdx.files.absolute( curdir + "/" + curfile );

            String dat = ff.readString();



            ///////
            command cc = new command();
            cc.command = "startDataAll";
            cc.from = myID;
            cc.room = activeRoom;
            client.sendTCP( cc );

            int len = dat.length();
            for (int i=0;i<len;i+=BufferSize){
                String part = dat.substring(i, Math.min(len, i + BufferSize));
                cc = new command();
                cc.command = "dataAll";
                cc.from = myID;
                cc.room = activeRoom;
                cc.data = part;

                slowdown();
                client.sendTCP( cc );
            }
            ///////

            cc = new command();
            cc.command = "allReadThis";
            cc.room=activeRoom;
            cc.from = myID;
            client.sendTCP( cc );
            logNet( "[C] Opening new map..." );
        }
    }

    public void recenterUI(){
        if (landscape){
            uicam.position.set( ssy/2,ssx/2,0 );
            uicam.update();
        }else{
            uicam.position.set( ssx/2,ssy/2,0 );
            uicam.update();
        }
    }

    String tempcurdir;
    public void extractTSET(String filepath) {

        errors = "";
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myParser = xmlFactoryObject.newPullParser();
            FileHandle file;
            file = Gdx.files.absolute(filepath);
            String path = file.parent().path();

            if (!file.exists())
            {
                file = Gdx.files.absolute(filepath);
//                path = file.parent().path().substring(Gdx.files.getExternalStoragePath().length());
                path = file.parent().path();
            }
            FileInputStream stream = new FileInputStream(file.file());
            Gdx.app.log("",path);

            tempcurdir = path;
            //curfile = file.file().getName();
            myParser.setInput(stream, null);
            //undolayer.clear();
            //redolayer.clear();
            //cliplayer =null;clipsource=0;
            int event = myParser.getEventType();
            //background = null;
            //encoding = "";
            tile tempTile = new tile();
            obj tempobj = new obj();
            String isi = "";
            String prName = "";
            String prValue = "";
            int lastPid = 0;
            //selgroup = -1;
            //selLayer = 0;
            templastID = 1;
            //seltset = 0;
            String owner = "";
            //layers.clear();
            tilesets2.clear();
            //properties.clear();
            //int curgroupid = -1;
            //int curobjid = -1;
            curid = 0;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();


                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (name.equals("map")) { }
                        if (name.equals("layer")) { }
                        if (name.equals("terrain")) {
                            terrain tr = new terrain();
                            tr.setName(myParser.getAttributeValue(null, "name"));
                            tr.setTile(Integer.parseInt(myParser.getAttributeValue(null, "tile")));
                            tempTset.getTerrains().add(tr);
                        }


                        if (name.equals("data")) { }
                        if (name.equals("tileset")) {
                            tempTset = new tileset();
                            String source = "";

                            source = myParser.getAttributeValue(null, "source");

                            owner = "tileset";
                            alreadyloaded = false;

                            if (myParser.getAttributeValue(null, "firstgid") != null) {
                                tempTset.setFirstgid(Integer.parseInt(myParser.getAttributeValue(null, "firstgid")));
                            } else {
                                tempTset.setFirstgid(requestGid());
                            }
                            if (source == null) {

                                tempTset.setName(myParser.getAttributeValue(null, "name"));
                                if (myParser.getAttributeValue(null, "columns") != null) {
                                    tempTset.setColumns(Integer.parseInt(myParser.getAttributeValue(null, "columns")));
                                    tempTset.setTilecount(Integer.parseInt(myParser.getAttributeValue(null, "tilecount")));
                                    tempTset.setWidth(tempTset.getColumns());
                                    tempTset.setHeight(tempTset.getTilecount() / tempTset.getColumns());
                                }

                                if (myParser.getAttributeValue(null, "margin") != null)
                                    tempTset.setMargin(Integer.parseInt(myParser.getAttributeValue(null, "margin")));
                                if (myParser.getAttributeValue(null, "spacing") != null)
                                    tempTset.setSpacing(Integer.parseInt(myParser.getAttributeValue(null, "spacing")));
                                if (myParser.getAttributeValue(null, "tilewidth") != null) {
                                    tempTset.setTilewidth(Integer.parseInt(myParser.getAttributeValue(null, "tilewidth")));
                                } else {
                                    tempTset.setTilewidth(Tsw);
                                }
                                if (myParser.getAttributeValue(null, "tileheight") != null) {
                                    tempTset.setTileheight(Integer.parseInt(myParser.getAttributeValue(null, "tileheight")));
                                } else {
                                    tempTset.setTileheight(Tsh);
                                }

                                tilesets2.add(tempTset);
                            } else {


                                String foredir, tempdir;
                                foredir = tempcurdir;
                                if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                    foredir = foredir.substring(0, foredir.length() - 1);
                                }
                                tempdir = source;

                                loadtsx(foredir + "/" + tempdir,tilesets2, tempcurdir);
                                //loadingfile = true;
                            }
                        }

                        if (name.equals("image")) {
                            if (owner.equalsIgnoreCase("tileset")) {
                                String internalpath = "rusted_warfare/assets/tilesets";
                                tempTset = tilesets2.get(tilesets2.size() - 1);
                                //if (myParser.getAttributeValue(null, "trans")!=null) {
                                tempTset.setTrans(myParser.getAttributeValue(null, "trans"));
                                //}
                                if (myParser.getAttributeValue(null, "source") != null) {
                                    tempTset.setSource(myParser.getAttributeValue(null, "source"));
                                }


                                if (!alreadyloaded) {


                                    String foredirint, foredirext, foredir, tempdiro, tempdiri, combo;
                                    foredir = curdir;//should be tsxpath to folloe tsx pathing but whatever!!
                                    if (foredir!="") {
                                        if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                            foredir = foredir.substring(0, foredir.length() - 1);
                                        }
                                    }
                                    tempdiro = tempTset.getSource();
                                    tempdiri = tempTset.getSource();

                                    foredirint = internalpath;
                                    foredirext = tempcurdir;


                                    while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                                        tempdiro = tempdiro.substring(3);
                                        if (foredirext.lastIndexOf("/")==-1){
                                            foredirext = "";

                                        }else
                                        {
                                            foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                                        }
                                    }


                                    if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                                        tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                                        tempdiri = tempdiri.replace("tilesets/", "");
                                    }

                                    FileHandle filehand = Gdx.files.internal(foredirint + "/" + tempdiri);

                                    if (!filehand.exists()) {
                                        filehand = Gdx.files.absolute(foredirext + "/" + tempdiro);
                                        if (!filehand.exists()) {
                                            filehand = Gdx.files.internal("empty.jpeg");
                                        }
                                    }

                                    try {

                                        tempTset.setTexture(new Texture(filehand));
                                        tempTset.setPixmap(pixmapfromtexture(tempTset.getTexture(), tempTset.getTrans()));
                                        if (tempTset.getTrans() != null) {
                                            tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                        }
                                        if (myParser.getAttributeValue(null, "width") != null) {
                                            tempTset.setOriginalwidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
                                            tempTset.setOriginalheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));
                                        } else {

                                            SimpleImageInfo sii;
                                            sii = new SimpleImageInfo(filehand.read());
                                            tempTset.setOriginalwidth(sii.getWidth());
                                            tempTset.setOriginalheight(sii.getHeight());
                                        }
                                    } catch (Exception e) {
                                        tempTset.setOriginalwidth(0);
                                        tempTset.setOriginalheight(0);
                                        errors += "Not Found: " + tempdiro + "\n" + e;
                                    }
                                }
                                if (tempTset.getColumns() == 0) {
                                    tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                    tempTset.setWidth(tempTset.getColumns());
                                    tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                    tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                                }

                                templastID += tempTset.getWidth() * tempTset.getHeight();
                            }

                        }

                        if (name.equals("imagelayer")) {
                        }

                        if (name.equals("objectgroup")) {
                        }

                        if (name.equals("tile")) {
                            if (owner.equalsIgnoreCase("tileset")) {
                                tempTile = new tile();
                                tempTile.setTileID(Integer.parseInt(myParser.getAttributeValue(null, "id")));
                                if (myParser.getAttributeValue(null, "terrain") != null) {
                                    tempTile.setTerrain(myParser.getAttributeValue(null, "terrain"));
                                }

                            }
                            oldowner = owner;
                            owner = "tile";
                        }
                        if (name.equals("frame")) {

                            tempTile.getAnimation().add(new frame(Integer.parseInt(myParser.getAttributeValue(null, "tileid")), Integer.parseInt(myParser.getAttributeValue(null, "duration"))));


                        }
                        if (name.equals("property")) {

                            String n = "", t = "", v = "";
                            isi = "";
                            if (myParser.getAttributeValue(null, "name") != null) {
                                n = myParser.getAttributeValue(null, "name");
                            }
                            if (myParser.getAttributeValue(null, "type") != null) {
                                t = myParser.getAttributeValue(null, "type");
                            }
                            if (myParser.getAttributeValue(null, "value") != null) {
                                v = myParser.getAttributeValue(null, "value");
                            }
                            tempe = new property(n, t, v);
                            isi = "";

                        }
                        if (name.equals("object")) { }
                        if (name.equals("polyline")) {
                        }
                        if (name.equals("polygon")) {
                        }

                        if (name.equals("ellipse")) {
                        }
                        if (name.equals("point")) {
                        }
                        if (name.equals("text")) {
                        }
                        break;
                    case XmlPullParser.TEXT:
                        isi += myParser.getText();


                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("tile")) {
                            if (oldowner.equalsIgnoreCase("tileset")) {
                                tempTset.getTiles().add(tempTile);

                            }
                            owner = oldowner;
                            oldowner = "";
                        }
                        if (name.equals("tileset")) {

                            tempTset = null;
                        }
                        if (name.equals("text")) {
                        }
                        if (name.equals("property")) {
                            String data = isi.replace("\n", "").trim();
                            String rawe = isi.trim();
                            if (tempe.getValue() != "") {
                                data = tempe.getValue();
                            } else {
                                if (data != "") tempe.setValue(rawe);
                            }

                            if (tempe.getName().equalsIgnoreCase("embedded_png")) {

                                String foredir = "", tempdir = "", combo = "";
                                try {

                                    Texture bucket;
                                    String base64 = data;
                                    byte[] decodedBytes = Base64Coder.decode(base64);
                                    bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));


                                    tempTset.setTexture(bucket);

                                    if (tempTset.getTrans() != null) {
                                        tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                    }

                                    tempTset.setPixmap(pixmapfromtexture(bucket, tempTset.getTrans()));


                                    tempTset.setOriginalwidth(bucket.getWidth());
                                    tempTset.setOriginalheight(bucket.getHeight());
                                    if (tempTset.getColumns() == 0) {
                                        tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                        tempTset.setWidth(tempTset.getColumns());
                                        tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                        tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                                    }

                                    templastID += tempTset.getWidth() * tempTset.getHeight();

                                    alreadyloaded = true;
                                } catch (Exception e) {
                                    ErrorBung(e, "okok.txt");
                                }


                            }

                            switch (owner) {
                                case "tileset":
                                    tempTset.getProperties().add(tempe);
                                    break;
                                case "tile":
                                    tempTile.getProperties().add(tempe);
                                    break;
                            }

                        }

                        if (name.equals("data")) {
                       }


                        break;
                }
                event = myParser.next();
            }

            //uicam.position.set(120,-200,0);//center

            //prefs.putString("lof", file.path());
            //prefs.flush();

            kartu = "world";
            mode = "tile";
            status(errors, 10);

        } catch (Exception e) {
            status("Error opening file.", 500);
        }
    }

    public void resetMinimap() {

        if (landscape) {
            if (Tsw * Tw > Tsh * Th) {
                minicam.zoom = ((Tw * Tsw * ((Tsw * Tw) / (ssy / 8))) / (Tw * Tsw));
            } else {
                minicam.zoom = ((Th * Tsh * ((Tsh * Th) / (ssy / 8))) / (Th * Tsh));
            }

            minicam.position.set(0, 0, 0);
            minicam.update();
            Vector3 ve, vo;
            ve = uicam.project(new Vector3((gui.minimap.getXl() + 1f) / 100 * ssy, ssx - ((gui.minimap.getHl() - 1f) / 100 * ssx), 0));
            vo = minicam.unproject(ve);
            minicam.position.set(-vo.x, -vo.y, 0);
            if (orientation.equalsIgnoreCase("isometric")){
                minicam.position.set(minicam.position.x-(Tsw*Tw/2),minicam.position.y,minicam.position.z);
            }

        } else {
            if (Tsw * Tw > Tsh * Th) {
                minicam.zoom = ((Tw * Tsw * ((Tsw * Tw) / (ssy / 8))) / (Tw * Tsw));
            } else {
                minicam.zoom = ((Th * Tsh * ((Tsh * Th) / (ssy / 8))) / (Th * Tsh));
            }
            minicam.position.set(0, 0, 0);
            minicam.update();
            Vector3 ve, vo;
            ve = uicam.project(new Vector3((gui.minimap.getX() + 1f) / 100 * ssx, ssy - ((gui.minimap.getH() - 1f) / 100 * ssy), 0));
            vo = minicam.unproject(ve);
            minicam.position.set(-vo.x, -vo.y, 0);
            if (orientation.equalsIgnoreCase("isometric")){
                minicam.position.set(minicam.position.x-(Tsw*Tw/2),minicam.position.y,minicam.position.z);
            }
        }
        //	status(ve.x+" - "+ve.y,5f);
        //	minicam.update();

        //minicam.position.set(Tw*Tsw*3.5f,-Tw*Tsw*1.47f,0);
        //}else{
        //	minicam.zoom = ((Th * Tsh * ((Tsh * Th) / 200f)) / (Th * Tsh));
        //minicam.position.set(Th*Tsh*3.5f,-Th*Tsh*1.47f,0);
        //}

        minicam.update();

    }

    public void resetcam(boolean animate) {
        if (animate) {
            int onset=0;
            if (orientation.equalsIgnoreCase("isometric")){
                onset=Tsw*Tw/2;
            }
            panTo(Tsw * Tw / 2-onset, -Tsh * Th / 2, .25f, .5f);
        } else {
            int onset=0;
            if (orientation.equalsIgnoreCase("isometric")){
                onset=Tsw*Tw/2;
            }
            cam.position.set(Tsw * Tw / 2 - onset, -Tsh * Th / 2, 0);
            cam.zoom =  0.5f;
            cam.update();

            panTo(Tsw * Tw / 2-onset, -Tsh * Th / 2, Tsw/64f, 1f);
        }

//        gamecam.position.set(-100, -100, 0);
  //      gamecam.zoom =  0.5f;
    //    gamecam.update();

        resetMinimap();

        if (tilesets.size() > 0) {
            float ttsw = tilesets.get(seltset).getTilewidth();
            float ttsh = tilesets.get(seltset).getTileheight();
            float ttkw = tilesets.get(seltset).getWidth();
            float ttkh = tilesets.get(seltset).getHeight();

            tilecam.position.set((int) ttsw * ttkw / 2, (int) -ttsh * ttkh / 2, 0);
            tilecam.zoom = .25f;
            tilecam.update();
        }

    }

    public java.util.List<Integer> cacheTset(java.util.List<Long> spr) {
        tilesetsize = tilesets.size();
        java.util.List<Integer> nyot = new ArrayList<Integer>();
        for (int s = 0; s < spr.size(); s++) {
            hex = Long.toHexString(spr.get(s));
            trailer = "00000000" + hex;
            hex = trailer.substring(trailer.length() - 8);
            String flag = hex.substring(0, 2);
            Long mm = Long.decode("#00" + hex.substring(2, 8));
            boolean isi = false;
            for (int g = tilesetsize - 1; g >= 0; g--) {
                if (mm == 0) {
                    nyot.add(-1);
                    break;
                }
                if (mm >= tilesets.get(g).getFirstgid() && mm < tilesets.get(g).getFirstgid() + tilesets.get(g).getTilecount()) {
                    nyot.add(g);
                    isi = true;
                    break;
                }
                if (g == 0 && !isi) {
                    nyot.add(-1);
                    break;
                }
            }
        }

        return nyot;
    }

    public void CacheAllTset() {

        log("prepare caching tset...");
        //hog
        java.util.List<Integer> copy = new ArrayList<Integer>();
        for (int k = 0; k<Tw*Th;k++){
            copy.add(Integer.valueOf( -1 ));
        }
        log("caching tset...");
        for (int lay = 0; lay < layers.size(); lay++) {
            if (tilesets.size() >0) {
                log("layer");
                layers.get( lay ).setTset( cacheTset( layers.get( lay ).getStr() ) );
            }else{
                log("empty layer");
                layers.get( lay ).setTset(copy);
            }
        }
        log("caching tset ok");

    }

    public int requestGid() {
        try {
            if (tilesets.size() > 0) {
                int ct = 1;
                for (int i = 0; i < tilesets.size(); i++) {
                    ct = tilesets.get(i).getFirstgid() + tilesets.get(i).getTilecount();
                }
                return ct;
            }
        } catch (Exception e) {
            status("error loading file", 5);
        }
        return 1;
    }

    public void loadtsx(String source, java.util.List<tileset> tilesets, String curdir){
        //msgbox(source);
        //loadingfile = true;
        String tsxpath = "";
        String owner = "";
        String isi = "";
        String fname = "";
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            String internalpath = "rusted_warfare/assets/tilesets";
            String tempdiro = "", tempdiri = "", foredirint = "", foredirext = "";

            try {
                tempdiri = source.substring(curdir.length() + 1);
                foredirint = internalpath;
                if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                    tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                    tempdiri = tempdiri.replace("tilesets/", "");
                }
            } catch (Exception e) {
            }

            try {
                tempdiro = source.substring(curdir.length() + 1);
                foredirext = curdir;
                while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                    tempdiro = tempdiro.substring(3);
                    foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                }
            } catch (Exception e) {
            }

            FileHandle filehand = Gdx.files.internal(foredirint + "/" + tempdiri);

            if (!filehand.exists()) {
                filehand = Gdx.files.absolute(source);
            }
            tsxpath = filehand.path().substring(0, filehand.path().lastIndexOf("/"));

            fname = filehand.name();
            InputStream stream = filehand.read();


            myParser.setInput(stream, null);
            int event = myParser.getEventType();
            tile tempTile = new tile();
            templastID = 1;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();


                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equals("tileset")) {
                            owner = "tileset";
                            if (tempTset == null) {
                                tempTset = new tileset();
                                tempTset.setFirstgid(requestGid());
                            }
                            tempTset.setUsetsx(true);

                            String[] tc = curdir.split("/");
                            String[] ts = source.split("/");

                            boolean git = false;
                            String pre = "", post = "";
                            for (int i = 0; i < tc.length; i++) {
                                if (git) {
                                    pre += "../";
                                    continue;
                                }
                                if (ts.length <= i) {
                                    git = true;
                                    pre += "../";
                                    continue;
                                }
                                if (!ts[i].equalsIgnoreCase(tc[i])) {
                                    git = true;
                                    pre += "../";
                                }
                            }

                            git = false;
                            for (int i = 0; i < ts.length; i++) {
                                if (git) {
                                    post += ts[i];
                                    if (i < ts.length - 1) post += "/";
                                    continue;
                                }
                                if (tc.length <= i) {
                                    git = true;
                                    post += ts[i];
                                    if (1 < ts.length - 1) post += "/";
                                    continue;
                                }
                                if (!tc[i].equalsIgnoreCase(ts[i])) {
                                    git = true;
                                    post += ts[i];
                                    if (i < ts.length - 1) post += "/";
                                }
                            }
                            String cocos = pre + post;
                            if (cocos.endsWith("/")) cocos = cocos.substring(0, cocos.length() - 1);
                            tempTset.setTsxfile(cocos);


                            if (myParser.getAttributeValue(null, "columns") != null) {
                                tempTset.setColumns(Integer.parseInt(myParser.getAttributeValue(null, "columns")));
                                tempTset.setTilecount(Integer.parseInt(myParser.getAttributeValue(null, "tilecount")));
                                tempTset.setWidth(tempTset.getColumns());
                                tempTset.setHeight(tempTset.getTilecount() / tempTset.getColumns());
                            }

                            tempTset.setName(myParser.getAttributeValue(null, "name"));
                            if (myParser.getAttributeValue(null, "margin") != null)
                                tempTset.setMargin(Integer.parseInt(myParser.getAttributeValue(null, "margin")));
                            if (myParser.getAttributeValue(null, "spacing") != null)
                                tempTset.setSpacing(Integer.parseInt(myParser.getAttributeValue(null, "spacing")));

                            if (myParser.getAttributeValue(null, "tilewidth") != null) {
                                tempTset.setTilewidth(Integer.parseInt(myParser.getAttributeValue(null, "tilewidth")));
                            } else {
                                tempTset.setTilewidth(Tsw);
                            }
                            if (myParser.getAttributeValue(null, "tileheight") != null) {
                                tempTset.setTileheight(Integer.parseInt(myParser.getAttributeValue(null, "tileheight")));
                            } else {
                                tempTset.setTileheight(Tsh);
                            }
                            tilesets.add(tempTset);
                        }
                        if (name.equals("terrain")) {
                            terrain tr = new terrain();
                            tr.setName(myParser.getAttributeValue(null, "name"));
                            tr.setTile(Integer.parseInt(myParser.getAttributeValue(null, "tile")));
                            tempTset.getTerrains().add(tr);
                        }

                        if (name.equals("tile")) {
                            tempTile = new tile();
                            owner = "tile";
                            tempTile.setTileID(Integer.parseInt(myParser.getAttributeValue(null, "id")));
                            if (myParser.getAttributeValue(null, "terrain") != null) {
                                tempTile.setTerrain(myParser.getAttributeValue(null, "terrain"));
                            }

                        }
                        if (name.equals("frame")) {

                            tempTile.getAnimation().add(new frame(Integer.parseInt(myParser.getAttributeValue(null, "tileid")), Integer.parseInt(myParser.getAttributeValue(null, "duration"))));


                        }
                        if (name.equals("property")) {
                            String n = "", t = "", v = "";
                            isi = "";
                            n = myParser.getAttributeValue(null, "name");
                            if (myParser.getAttributeValue(null, "type") != null) {
                                t = myParser.getAttributeValue(null, "type");
                            }
                            v = myParser.getAttributeValue(null, "value");
                            tempe = new property(n, t, v);
                            isi = "";


                            if (tempe.getName().equalsIgnoreCase("embedded_png")) {

                                String foredir = "", tempdir = "", combo = "";
                                try {

                                    Texture bucket;
                                    String base64 = v;
                                    byte[] decodedBytes = Base64Coder.decode(base64);
                                    bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));


                                    tempTset.setTexture(bucket);

                                    if (tempTset.getTrans() != null) {
                                        tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                    }

                                    tempTset.setPixmap(pixmapfromtexture(bucket, tempTset.getTrans()));


                                    tempTset.setOriginalwidth(bucket.getWidth());
                                    tempTset.setOriginalheight(bucket.getHeight());
                                    if (tempTset.getColumns() == 0) {
                                        tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                        tempTset.setWidth(tempTset.getColumns());
                                        tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                        tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                                    }

                                    templastID += tempTset.getWidth() * tempTset.getHeight();

                                    alreadyloaded = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                   // ErrorBung(e, "okok.txt");
                                }


                            }



                        }
                        if (name.equals("image")) {
                            tempTset.setTrans(myParser.getAttributeValue(null, "trans"));
                            tempTset.setSource(myParser.getAttributeValue(null, "source"));


                            String foredir, tempdir, combo;
                            foredir = tsxpath;//should be tsxpath to folloe tsx pathing but whatever!!
                            //errors+=tsxpath+"ole\n";
                            if (foredir.substring(foredir.length() - 1).equalsIgnoreCase("/")) {
                                foredir = foredir.substring(0, foredir.length() - 1);
                            }
                            tempdiro = tempTset.getSource();
                            tempdiri = tempTset.getSource();

                            foredirint = tsxpath;
                            foredirext = tsxpath;

                            while (tempdiro.substring(0, 3).equalsIgnoreCase("../")) {
                                tempdiro = tempdiro.substring(3);
                                if (foredirext.lastIndexOf("/")==-1){
                                    foredirext = "";

                                }else
                                {
                                    foredirext = foredirext.substring(0, foredirext.lastIndexOf("/"));
                                }
                            }

                            if (tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1) != -1) {
                                tempdiri = tempdiri.substring(tempdiri.lastIndexOf("/", tempdiri.lastIndexOf("/") - 1));
                                tempdiri = tempdiri.replace("tilesets/", "");
                            }


                            filehand = Gdx.files.internal(foredirint + "/" + tempdiri);

                            if (!filehand.exists()) {
                                filehand = Gdx.files.absolute(foredirext + "/" + tempdiro);
                                if (!filehand.exists()) {
                                    filehand = Gdx.files.internal("empty.jpeg");
                                }
                            }
                            //errors+=filehand.path()+"\n";
                            try {

                                tempTset.setTexture(new Texture(filehand));
                                tempTset.setPixmap(pixmapfromtexture(tempTset.getTexture(), tempTset.getTrans()));

                                if (tempTset.getTrans() != null) {
                                    tempTset.setTexture(chromaKey(tempTset.getTexture(), tempTset.getTrans()));
                                }
                                if (myParser.getAttributeValue(null, "width") != null) {
                                    tempTset.setOriginalwidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
                                    tempTset.setOriginalheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));
                                } else {

                                    SimpleImageInfo sii;
                                    sii = new SimpleImageInfo(filehand.read());
                                    tempTset.setOriginalwidth(sii.getWidth());
                                    tempTset.setOriginalheight(sii.getHeight());
                                }
                            } catch (Exception e) {
                                tempTset.setOriginalwidth(0);
                                tempTset.setOriginalheight(0);
                                errors += "Not Found: " + tempdiro + "\n" + e;
                            }
                            if (tempTset.getColumns() == 0) {
                                tempTset.setColumns((tempTset.getOriginalwidth() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTilewidth() + tempTset.getSpacing()));
                                tempTset.setWidth(tempTset.getColumns());
                                tempTset.setHeight((tempTset.getOriginalheight() - tempTset.getMargin() * 2 + tempTset.getSpacing()) / (tempTset.getTileheight() + tempTset.getSpacing()));
                                tempTset.setTilecount(tempTset.getWidth() * tempTset.getHeight());
                            }

                            templastID += tempTset.getWidth() * tempTset.getHeight();

                        }

                        break;
                    case XmlPullParser.TEXT:
                        isi += myParser.getText();

                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("tile")) {
                            tempTset.getTiles().add(tempTile);
                            owner = "tileset";
                        }
                        if (name.equals("tileset")) {

                            tempTset = null;
                        }
                        if (name.equals("property")) {
                            String data = isi.replace("\n", "").trim();
                            if (data != "") tempe.setValue(data);

                            switch (owner) {
                                case "tileset":
                                    tempTset.getProperties().add(tempe);
                                    break;
                                case "tile":
                                    tempTile.getProperties().add(tempe);
                                    break;
                            }
                        }

                        break;
                }
                event = myParser.next();
            }
            tsxFile = source;
            tempTset = null;
            stream.close();
        } catch (Exception e) {

            ErrorBung(e, "loadingtsxerror.txt");
            errors += "Not Found: " + fname + "\n";

            loadingfile = false;

        }
        loadingfile = false;
    }

    //////////////////////////////////////////////////////
//            GESTURE DETECTOR
//////////////////////////////////////////////////////
    @Override
    public boolean touchDown(float p1, float p2, int p3, int p4) {
        if (kartu.equalsIgnoreCase("game") )
        {
            return true;
        }
        if (kartu.equalsIgnoreCase(  "editor" )){
            Vector3 touch2 = new Vector3();
            uicam.unproject(touch2.set(p1, p2, 0));

            if (tapEditorMenu(touch2)) return true;
        }

        velx = 0;
        vely = 0;
        if (kartu == "world") {
            initialZoom = cam.zoom;
        } else if (kartu == "tile" || kartu == "pickanim") {
            initialZoom = tilecam.zoom;
        }
        return false;
    }

    @Override
    public boolean tap(float p1, float p2, int p3, int p4) {
        //Vector3 ve,vo;
        //ve = uicam.project(new Vector3(p1,p2,0f));
        //status (ve.x+"==="+ve.y,5);
        //vo = minicam.unproject(ve);
        if (kartu.equalsIgnoreCase("game")){return true;}

        if (loadingfile) return true;
        Vector3 touch2 = new Vector3();
        uicam.unproject(touch2.set(p1, p2, 0));
        if (!drag) {

            switch (kartu) {
                case "editor":
                    if (tapEditorActions(touch2)) return true;
                    break;
                case "world":

                    if (!roll) if (tapWorldMenu(touch2)) return true;
                    if (tilesets.size() == 0) {
                        kartu = "tile";
                        return true;
                    }

                    Vector3 touch = new Vector3();
                    cam.unproject(touch.set(p1, p2, 0));


                    int ae = (int) touch.x;
                    int ab = (int) touch.y;

                    //this line is to sovlve problem of the first line not clicked on 1 x 1 tile.
                    //but cause error in other places.
                    //if (touch.y > 0) ab = 1;

                    boolean touched = false;

                    if (orientation.equalsIgnoreCase("orthogonal")) {
                        touched = touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw;
                    } else if (orientation.equalsIgnoreCase("isometric")) {
                        touched = true;
                    }
                    if (mode=="object") touched=true;
                    int num = 0;

                    if (touched) {


                        if (orientation.equalsIgnoreCase("orthogonal")) {

                            num = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                        } else if (orientation.equalsIgnoreCase("isometric")) {

                            //cool way to convert isometric to orthogonal, new iso tap detection
                            int newae = ae - ((Tsw/Tsh) * ab) + Tsw/2;
                            int newab = (ae / (Tsw/Tsh)) + ab -Tsh/2;
                            num = (Tw * ((-newab + Tsh) / Tsh) + (newae / Tsw));
                            //status(""+newae/2f+"|"+(newab-Tsh)+" num:"+num,1);
                            if (num<0 || num >Tw*Th) return true;
                            ae =  (int) (newae/2f);
                            ab = newab;
                            // check by the closest to a point, old iso tap detection.
                            /*
                            float closest = 9999;
                            for (int i = 0; i < Tw * Th; i++) {
                                int offsetx = 0, offsety = 0;
                                xpos = i % Tw;
                                ypos = i / Tw;
                                offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                                offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                                int drawx = xpos * Tsw - offsetx;
                                int drawy = -ypos * Tsh - offsety;
                                int x = ae - drawx;
                                int y = ab - drawy;
                                int Tsws = Tsw / 2;
                                int Tshs = Tsh / 2;
                                float dx = Math.abs(x - Tsws);
                                float dy = Math.abs(y - Tshs);
                                if (dx / Tsws + dy / Tshs < closest) {
                                    num = i;
                                    closest = dx / Tsws + dy / Tshs;
                                }
                            }

                             */
                        }
                    }

                    if (mode == "tile" && touched) {

                        tapTile(num, false, true,false,curspr);
                    } else if (mode == "object") {
                        tapObject(num, ae, ab);

                    } else if (mode == "newpoly") {
                        tapNewPoly(num, ae, ab);
                    }
                    break;
                case "tile":
                case "pickanim":
                default:
                    if (tapPickUI(p1, p2)) return true;
                    tapPick(p1, p2);
                    break;

            }
        }
        //updateMinimap();
        return false;

    }

    private boolean tapNewPoly(Integer num, int ae, int ab) {
        obj nyok = newobject;
        if (magnet == 1) {
            switch (nyok.getShape()) {
                case "polygon":
                case "polyline":
                    int a = ae - (int) nyok.getX();
                    int b = -ab - (int) nyok.getY() + Tsh;
                    if (a < 0) a -= Tsw;
                    if (b < 0) b -= Tsh;
                    int x = (a / Tsw) * Tsw;
                    int y = (b / Tsh) * Tsh;

                    nyok.addPoint(x, y);
                    break;

            }
        } else {
            nyok.addPoint((int) ae - nyok.getX(), -(int) ab + Tsh - nyok.getY());

        }
        return false;
    }

    private void recenterpick() {
        if (tilesets.size()==0) return;
        float ttsw = tilesets.get( seltset ).getTilewidth();
        float ttsh = tilesets.get( seltset ).getTileheight();
        float ttkw = tilesets.get( seltset ).getWidth();
        float ttkh = tilesets.get( seltset ).getHeight();

        if (pickAuto) {
            ttkw = 6;
            ttkh = tilesets.get( seltset ).getTerrains().size();
        }


        panTileTo((int) ttsw * ttkw / 2, (int) -(ttsh * ttkh / 2) + (ttsh) , .5f);
    }

    private boolean tapPickUI(float p1, float p2) {

        Vector3 touch2 = new Vector3();
        uicam.unproject(touch2.set(p1, p2, 0));
        if (tapped(touch2, gui.pickerback)) {
            if (kartu == "pickanim") {
                switch (tilePicker){
                    case "massprops":
                    case "tileprop":
                    case "addanim":
                    case "terraineditor":
                        gotoStage(tTileMgmt);
                        return true;
                    case "props":
                        gotoStage(tPropEditor);
                        return true;
                    case "newimgobj":
                        backToMap();
                        return true;
                    case "rnda": case "rndb":
                        gotoStage(trandomgen);
                        return true;
                    case "repa": case "repb":
                        gotoStage(treplacetiles);
                        return true;
                    case "sw1":
                    case "sw2":
                    case "sw3":
                    case "sw4":
                    case "sw5":
                    case "sw6":
                        backToMap();
                        return true;
                }
            }
            else if (kartu == "tile") {
                backToMap();
                return true;
            }
        }

        if (tapped(touch2, gui.newterrain)) {
            if (tilePicker == "terraineditor" && kartu == "pickanim") {
                pickTile("newterrain");
                return true;
            }
            if (tilePicker == "massprops" && kartu == "pickanim") {
                for (int o = 0; o < massprops.size(); o++) {
                    boolean ada = false;
                    int ka = 0;
                    property tgt = null;
                    java.util.List<property> ppt = null;

                    for (int k = 0; k < tilesets.get(selTsetID).getTiles().size(); k++) {
                        if (tilesets.get(selTsetID).getTiles().get(k).getTileID() == o) {
                            ppt = tilesets.get(selTsetID).getTiles().get(k).getProperties();
                            for (int j = 0; j < ppt.size(); j++) {
                                if (ppt.get(j).getName().equalsIgnoreCase(temproname) && ppt.get(j).getValue().equalsIgnoreCase(temprovalue)) {
                                    ada = true;
                                    tgt = ppt.get(j);
                                    ka = k;
                                }
                            }

                        }
                    }

                    if (ada) {
                        if (!massprops.get(o)) {
                            ppt.remove(tgt);
                            tile tt = tilesets.get(selTsetID).getTiles().get(ka);
                            if (tt.getProperties().size() == 0 && tt.getAnimation().size() == 0) {
                                tilesets.get(selTsetID).getTiles().remove(tt);
                            }
                        } else {
                            tgt.setValue(temprovalue);
                        }
                    } else {
                        if (massprops.get(o)) {
                            boolean adatile = false;
                            for (int k = 0; k < tilesets.get(selTsetID).getTiles().size(); k++) {
                                if (tilesets.get(selTsetID).getTiles().get(k).getTileID() == o) {
                                    adatile = true;
                                    ka = k;
                                }
                            }

                            properties pt = new properties();
                            pt.getProperties().add(new property(temproname, temprotype, temprovalue));
                            if (!adatile) {
                                tile tile = new tile();
                                tile.setTileID(o);
                                tile.getProperties().add(new property(temproname, temprotype, temprovalue));
                                tilesets.get(selTsetID).getTiles().add(tile);
                            } else {
                                tile tile = tilesets.get(selTsetID).getTiles().get(ka);
                                tile.getProperties().add(new property(temproname, temprotype, temprovalue));
                            }
                        }
                    }


                }
                java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();
                ltilelist.setItems();
                String[] srr = new String[tiles.size()];
                for (int i = 0; i < tiles.size(); i++) {
                    srr[i] = Integer.toString(tiles.get(i).getTileID());
                }

                ltilelist.setItems(srr);
                gotoStage(tTileMgmt);
            }

        }

        if (tilesets.size() > 0) {
            frompick = true;
            if (kartu.equalsIgnoreCase("tile")) {

                if (tapped(touch2, gui.tilemode) && tilesets.get( seltset ).getTerrains().size()>0) {
                    pickAuto =!pickAuto;
                    issettingtile=false;
                    resetMassprops();
                    recenterpick();

                    return true;
                }

                if (tapped(touch2, gui.tilewrite)) {
                    issettingtile=false;
                    adjustPickAuto();
                    resetMassprops();
                    recenterpick();
                    return true;
                }
                if (tapped(touch2, gui.tilesettings)) {
                    issettingtile=true;
                    pickAuto=false;
                    resetMassprops();
                    recenterpick();
                    return true;
                }
                if (tapped(touch2, gui.tileadd)) {
                    if (somethingisselected()){
                        sender = "tileadd";
                        senderID = -1;
                        bPropValfile.setText(z.select);
                        sbPropValbool.setSelected("false");
                        sbPropType.setSelected("String");
                        fPropName.setText("");
                        fPropVal.setText("");
                        fPropVal.setVisible(true);
                        bPropValfile.setVisible(false);
                        sbPropValbool.setVisible(false);
                        gotoStage(tPropEditor);

                        return true;
                    }
                }
                if (tapped(touch2, gui.tileremove)) {
                    if (somethingisselected()){
                        for (int i=0; i< massprops.size();i++)
                        {
                            if (massprops.get(i)) {
                                for (tile t : tilesets.get(seltset).getTiles()) {
                                    if (t.getTileID()==i) t.getProperties().clear();
                                }
                            }
                        }
                        return true;
                    }
                }
                if (tapped(touch2, gui.tileproperties)) {
                    if (somethingisselected()){

                        tile newanim = new tile();
                        tileset ts = tilesets.get(seltset);
                        int num = curspr;
                        boolean ada;
                        tile oldanim;

                        newanim.setTileID(num - ts.getFirstgid());

                        tiles = tilesets.get(seltset).getTiles();
                        ada = false;
                        for (int o = 0; o < tiles.size(); o++) {
                            if (tiles.get(o).getTileID() == num - ts.getFirstgid()) {
                                ada = true;
                                oldanim = tiles.get(o);
                                selTileID = o;
                            }
                        }
                        if (!ada) {
                            tiles.add(newanim);
                            selTileID = tiles.size() - 1;
                        }


                        ////
                       // if (tilesets.get(seltset).getTiles().get(selTileID).getProperties()!=null) {
                            refreshProperties( tilesets.get( seltset ).getTiles().get( selTileID ).getProperties() );

                            lPropID.setText( z.properties + ": " + tilesets.get( seltset ).getTiles().get( selTileID ).getTileID() );
                            sender = "tilesettings";
                            gotoStage( tPropsMgmt );
                            return true;
                      // }

                    }

                }
                //tileset properties
                if (tapped(touch2, gui.pickertool2)) {
                    selTsetID = seltset;
                    int saiz = tilesets.size();


                    String[] srr = new String[saiz];
                    for (int i = 0; i < saiz; i++) {
                        srr[i] = tilesets.get(i).getName();
                    }
                    ltsetlist.setItems(srr);


                    if (tilesets.size() > 0) {
                        tileset ct = tilesets.get(selTsetID);
                        fTsPropName.setText("");
                        fTsPropSource.setText("");
                        fTsPropTsxFile.setText("");
                        cbTsPropUseTsx.setChecked(false);
                        fTsPropTsw.setText("0");
                        fTsPropTsh.setText("0");
                        fTsPropMargin.setText("0");
                        fTsPropSpacing.setText("0");
                        fTsPropCols.setText("0");
                        fTsPropTc.setText("0");
                        fTsPropTrans.setText("");

                        if (ct.getName() != null) fTsPropName.setText(ct.getName());
                        if (ct.getSource() != null) fTsPropSource.setText(ct.getSource());
                        if (ct.getTsxfile() != null) fTsPropTsxFile.setText(ct.getTsxfile());
                        cbTsPropUseTsx.setChecked(ct.isUsetsx());
                        if (ct.getTilewidth() != 0)
                            fTsPropTsw.setText(Integer.toString(ct.getTilewidth()));
                        if (ct.getTileheight() != 0)
                            fTsPropTsh.setText(Integer.toString(ct.getTileheight()));
                        if (ct.getColumns() != 0)
                            fTsPropCols.setText(Integer.toString(ct.getColumns()));
                        if (ct.getTilecount() != 0)
                            fTsPropTc.setText(Integer.toString(ct.getTilecount()));
                        if (ct.getMargin() != 0)
                            fTsPropMargin.setText(Integer.toString(ct.getMargin()));
                        if (ct.getSpacing() != 0)
                            fTsPropSpacing.setText(Integer.toString(ct.getSpacing()));

                        if (ct.getColumns() != 0)
                            fTsPropCols.setText(Integer.toString(ct.getColumns()));
                        if (ct.getTrans() != null) fTsPropTrans.setText(ct.getTrans());

                        gotoStage(tTsProp);
                    }


                    return true;
                }

                if (tapped(touch2, gui.pickertool1)) {
                    pickAuto=false;
                    FileDialog(z.selectfile, "quickaddtset", "file", new String[]{".tmx",".tsx", ".png", ".jpg", ".jpeg", ".bmp", ".gif"}, nullTable);
                    return true;
                }
                //tile management
                if (tapped(touch2, gui.pickertool3)) {
                    pickAuto=false;
                    resetMassprops();
                    recenterpick();

                    int saiz = tilesets.size();


                    String[] srr = new String[saiz];
                    for (int i = 0; i < saiz; i++) {
                        srr[i] = tilesets.get(i).getName();
                    }
                    ltsetlist.setItems(srr);
                    selTsetID = seltset;
                    gotoStage(tTileMgmt);
                    return true;
                }
                //delete button
                if (tapped(touch2, gui.pickertool5)) {
                    resetMassprops();
                    tilesets.remove(seltset);
                    pickAuto=false;
                    if (tilesets.size() > 0) {
                        if (seltset != 0) seltset -= 1;
                        recenterpick();
                    } else {
                        seltset = 0;
                    }
                    CacheAllTset();
                    return true;
                }


            }
        }
        //pempek
            if (tilesets.size() > 0) {
                if (kartu.equalsIgnoreCase("tile")) {

                    //longpressing in a tile picker
                    if (tapped(touch2, gui.tilesetsmid)) {
                        loadList("tset");
                        return true;
                    }

                    if (tapped(touch2, gui.tilesetsright)) {
                        seltset += 1;
                        if (seltset >= tilesets.size()) {
                            seltset = 0;
                        }
                        adjustPickAuto();
                        recenterpick();
                        resetMassprops();
                        return true;

                    } else if (tapped(touch2, gui.tilesetsleft)) {

                        seltset -= 1;
                        if (seltset <= -1) {
                            seltset = tilesets.size() - 1;
                        }
                        adjustPickAuto();
                        recenterpick();
                        resetMassprops();
                        return true;

                    }

                } else if (kartu.equalsIgnoreCase("pickanim")) {
                    switch (tilePicker) {
                        case "newimgobj":
                        case "props":
                        case "rnda":
                        case "rndb":
                        case "repa":
                        case "repb":
                        case "sw1":
                        case "sw2":
                        case "sw3":
                        case "sw4":
                        case "sw5":
                        case "sw6":
                            if (tapped(touch2, gui.tilesetsright)) {
                                seltset += 1;
                                if (seltset >= tilesets.size()) {
                                    seltset = 0;
                                }
                                recenterpick();
                                return true;
                            } else if (tapped(touch2, gui.tilesetsleft)) {

                                seltset -= 1;
                                if (seltset <= -1) {
                                    seltset = tilesets.size() - 1;
                                }
                                recenterpick();
                                return true;
                            }
                            break;
                        case "terraineditor":
                            if (tapped(touch2, gui.tilesetsmid)) {
                                tileset tt = tilesets.get( selTsetID );
                                if (tt.getTerrains().size() > 0) {
                                    tt.setSelTerrain( tt.getSelTerrain() + 1 );
                                    if (tt.getSelTerrain() >= tt.getTerrains().size()) {
                                        tt.setSelTerrain( 0 );
                                    }
                                }
                                return true;
                            }
                    }
                }


            } else {
                FileDialog(z.selectfile, "quickaddtset", "file", new String[]{".tmx",".tsx", ".png", ".jpg", ".jpeg", ".bmp", ".gif"}, nullTable);
                cue("addtileset");
            }
            return false;
    }


    public void addSW(int num, String sws){
        boolean ada = false;
        for (property p: properties){
            if (p.getName().equalsIgnoreCase( sws )){
                p.setValue( Integer.toString(num));
                ada = true;
            }
        }

        if (!ada) {
            property p = new property(sws,Integer.toString(num));
            properties.add( p );
        }
    }

    private boolean tapPick(float p1, float p2) {
        if (loadingfile) return true;
        int seltset = 0;
        stamp = false;
        assemblymode=false;
        if (tilesets.size() == 0) return true;
        Vector3 touch = new Vector3();
        tilecam.unproject(touch.set(p1, p2, 0));
        if (kartu == "tile" || tilePicker == "newimgobj" || tilePicker == "props" || tilePicker == "rnda" || tilePicker == "rndb" || tilePicker == "repa" || tilePicker == "repb" || tilePicker == "sw1" || tilePicker == "sw2" || tilePicker == "sw3" || tilePicker == "sw4" || tilePicker == "sw5" || tilePicker == "sw6") {
            seltset = this.seltset;
        } else {
            seltset = this.selTsetID;
        }
        tileset ts = tilesets.get(seltset);
        int ae = (int) touch.x;
        int ab = (int) touch.y;
        //this line is to sovlve problem of the first line not clicked on 1 x 1 tile.
        if (touch.y > 0) ab = 1;

        if (kartu == "tile") {
            if (!issettingtile && pickAuto) {
                int heit=ts.getTerrains().size();
                if (touch.y < ts.getTileheight() && touch.y > -ts.getTileheight() * heit + ts.getTileheight() && touch.x > 0 && touch.x < ts.getTilewidth() * 6) {
                    Integer aaaa = ts.getTerrains().get(((-ab + ts.getTileheight()) / ts.getTileheight())).getTile();
                    Integer num = ts.getFirstgid()+aaaa;
                    curspr = num;
                    adjustLayer( ts );
                    addRecentTile( curspr );
                    kartu = "world";
                    if (activetool == 1) activetool = 0;
                    cue( "tilepickclick" );

                }
                return true;
            }
        }

        if (touch.y < ts.getTileheight() && touch.y > -ts.getTileheight() * ts.getHeight() + ts.getTileheight() && touch.x > 0 && touch.x < ts.getTilewidth() * ts.getWidth()) {
            Integer num = ts.getFirstgid() + (ts.getWidth() * ((-ab + ts.getTileheight()) / ts.getTileheight()) + (ae / ts.getTilewidth()));
            Integer numis = (ts.getWidth() * ((-ab + ts.getTileheight()) / ts.getTileheight()) + (ae / ts.getTilewidth()));

            if (kartu == "tile") {
                if (!issettingtile) {
                    curspr = num;
                    adjustLayer( ts );
                    addRecentTile( curspr );
                    kartu = "world";
                    if (activetool == 1) activetool = 0;
                    cue( "tilepickclick" );
                    return true;
                }else{
                    curspr = num;
                    int cont=0;
                    for (boolean b: massprops){
                        if (b) cont++;
                    }
                    if (cont<2) resetMassprops();
                    massprops.set(numis, !massprops.get(numis));
                    return true;
                }
            } else {
                //lastStage.setVisible(true);
                //ANOTHER STUPIDITY, A REALLY STUPID ONE
                switch (tilePicker) {
                    case "props":
                        //KEEPING THIS TO SHOW HOW STUPID I WAS
                        //kartu = "stage";
                        //Gdx.input.setInputProcessor(stage);
                        gotoStage(tPropEditor);
                        fPropVal.setText(Integer.toString(num));
                        break;
                    case "rnda":
                        gotoStage(trandomgen);
                        flivestr.setText(Integer.toString(num));
                        break;
                    case "rndb":
                        gotoStage(trandomgen);
                        fdeadstr.setText(Integer.toString(num));
                        break;
                    case "repa":
                        gotoStage(treplacetiles);
                        fprevstr.setText(Integer.toString(num));
                        break;
                    case "repb":
                        gotoStage(treplacetiles);
                        fnextstr.setText(Integer.toString(num));
                        break;
                    case "sw1":
                        backToMap();
                        swatchValue.set( 0,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw1" );

                        break;
                    case "sw2":
                        backToMap();
                        swatchValue.set( 1,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw2" );
                        break;
                    case "sw3":
                        backToMap();
                        swatchValue.set( 2,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw3" );

                        break;
                    case "sw4":
                        backToMap();
                        swatchValue.set( 3,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw4" );

                        break;
                    case "sw5":
                        backToMap();
                        swatchValue.set( 4,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw5" );

                        break;
                    case "sw6":
                        backToMap();
                        swatchValue.set( 5,num );
                        curspr = num;
                        setTsetFromCurspr();
                        addSW( num,"sw6" );

                        break;
                    case "addanim":
						/*
						int dex=ltsetlist.getSelectedIndex();
						selTsetID=dex;
						*/
                        tile newanim = new tile();

                        newanim.setTileID(num - ts.getFirstgid());

                        java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();
                        boolean ada = false;
                        tile oldanim;
                        for (int o = 0; o < tiles.size(); o++) {
                            if (tiles.get(o).getTileID() == num - ts.getFirstgid()) {
                                ada = true;
                                oldanim = tiles.get(o);
                                selTileID = o;
                            }
                        }
                        if (!ada) {
                            tiles.add(newanim);
                            selTileID = tiles.size() - 1;
                        }

                        lFrameID.setText(z.animation + " " + z.id + ": " + tiles.get(selTileID).getTileID());
                        int dexa = tiles.get(selTileID).getAnimation().size();

                        String[] srrA = new String[dexa];

                        lframelist.setItems();
                        for (int i = 0; i < dexa; i++) {

                            String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                            String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                            srrA[i] = aaa + " (" + bbb + "ms)";
                        }
                        lframelist.setItems(srrA);

                        gotoStage(tFrameMgmt);
                        break;

                    case "tileprop":
						/*
						dex=ltsetlist.getSelectedIndex();
						selTsetID=dex;
						*/
                        newanim = new tile();

                        newanim.setTileID(num - ts.getFirstgid());

                        tiles = tilesets.get(selTsetID).getTiles();
                        ada = false;
                        for (int o = 0; o < tiles.size(); o++) {
                            if (tiles.get(o).getTileID() == num - ts.getFirstgid()) {
                                ada = true;
                                oldanim = tiles.get(o);
                                selTileID = o;
                            }
                        }
                        if (!ada) {
                            tiles.add(newanim);
                            selTileID = tiles.size() - 1;
                        }

                        ////
                        refreshProperties(tilesets.get(selTsetID).getTiles().get(selTileID).getProperties());

                        lPropID.setText(z.properties + ": " + tilesets.get(selTsetID).getTiles().get(selTileID).getTileID());
                        sender = "tile";
                        gotoStage(tPropsMgmt);
                        ////

                        break;
                    case "replaceanim":
                        gotoStage(tTileMgmt);

                        int dex = ltilelist.getSelectedIndex();

                        tiles = tilesets.get(selTsetID).getTiles();

                        tiles.get(dex).setTileID(num - ts.getFirstgid());
                        String[] srr = new String[tiles.size()];
                        for (int i = 0; i < tiles.size(); i++) {
                            srr[i] = Integer.toString(tiles.get(i).getTileID());
                        }
                        ltilelist.setItems(srr);
                        ltilelist.setSelectedIndex(dex);
                        break;
                    case "addframe":
                        gotoStage(tFrameMgmt);

                        tempframeid = num - ts.getFirstgid();
                        tiles = tilesets.get(selTsetID).getTiles();

                        int aa = num - ts.getFirstgid();
                        String bb = prefs.getString("duration", "500");
                        int cc = Integer.parseInt(bb);
                        frame frame = new frame(aa, cc);
                        tiles.get(selTileID).getAnimation().add(frame);
                        prefs.putString("duration", bb);
                        prefs.flush();
                        String[] srra = new String[tiles.get(selTileID).getAnimation().size()];
                        for (int i = 0; i < tiles.get(selTileID).getAnimation().size(); i++) {
                            String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                            String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                            srra[i] = aaa + " (" + bbb + "ms)";
                        }
                        lframelist.setItems(srra);
                        lframelist.setSelectedIndex(tiles.get(selTileID).getAnimation().size() - 1);
                        break;

                    case "replaceframe":
                        gotoStage(tFrameMgmt);
                        dex = lframelist.getSelectedIndex();
                        int x = selTsetID;
                        tiles = tilesets.get(x).getTiles();

                        tiles.get(selTileID).getAnimation().get(dex).setTileID(num - ts.getFirstgid());
                        srr = new String[tiles.get(selTileID).getAnimation().size()];
                        for (int i = 0; i < tiles.get(selTileID).getAnimation().size(); i++) {
                            String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
                            String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
                            srr[i] = aaa + " (" + bbb + "ms)";
                        }
                        lframelist.setItems(srr);
                        lframelist.setSelectedIndex(dex);

                        break;

                    case "newimgobj":

                        newobject.setGid(num);
                        //gotoStage(tPropsMgmt);
                        backToMap();
                        break;
                    case "newterrain":
                        newTerrainID = num - ts.getFirstgid();
                        tilePicker = "terraineditor";
                        Gdx.input.getTextInput(pNewTerrain, "New Terrain Name", "", "");
                        kartu = "pickanim";
                        Gdx.input.setInputProcessor(im);
                        return true;
                    case "massprops":
                        if (massprops.get(num - ts.getFirstgid())) {
                            massprops.set(num - ts.getFirstgid(), false);
                        } else {
                            massprops.set(num - ts.getFirstgid(), true);
                        }
                        return true;
                    case "terraineditor":
                        //log(kartu+":"+tilePicker);
                        if (ts.getTerrains().size() == 0) return true;
                        tiles = ts.getTiles();
                        tile t = null;
                        int dst = num - ts.getFirstgid();
                        for (int n = 0; n < tiles.size(); n++) {
                            if (tiles.get(n).getTileID() == dst) {
                                t = tiles.get(n);
                                break;
                            }
                        }

                        if (t == null) {
                            tile tt = new tile();
                            tt.setTileID(dst);
                            tiles.add(tt);
                            t = tiles.get(tiles.size() - 1);
                        }
                        int[] cn = t.getTerrain();
                        //log(t.getTerrainString());
                        int a = cn[0], b = cn[1], c = cn[2], d = cn[3];
                        int e = tilesets.get(selTsetID).getSelTerrain();



                        //ada tile kosong
                        if ((a == -1) || (b == -1) || (c == -1) || (d == -1)) {
                            if (a != e && b != e && c != e && d != e) {
                                if (a == -1) a = e;
                                if (b == -1) b = e;
                                if (c == -1) c = e;
                                if (d == -1) d = e;
                                t.setTerrain(a, b, c, d);
                                return true;
                            }
                        }

                        //kalau tidak ada yang kosong, takeover semua
                        if ((a >= 0) && (b >= 0) && (c >= 0) && (d >= 0)) {
                            if (!(a == e && b == e && c == e && d == e)) {
                                a = e;
                                b = e;
                                c = e;
                                d = e;
                                t.setTerrain(a, b, c, d);
                                return true;
                            }

                        }

                        //sisanya
                        if ((a == -1) && (b == -1) && (c == -1) && (d == -1)) {
                            a = e;
                            b = e;
                            c = e;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }

                        if ((a == e) && (b == e) && (c == e) && (d == e)) {
                            a = -1;
                            b = -1;
                            c = -1;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }

                        if ((a == -1) && (b == -1) && (c == -1) && (d == e)) {
                            a = -1;
                            b = -1;
                            c = e;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == -1) && (c == e) && (d == e)) {
                            a = -1;
                            b = -1;
                            c = e;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == -1) && (c == e) && (d == -1)) {
                            a = e;
                            b = -1;
                            c = e;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == -1) && (c == e) && (d == -1)) {
                            a = e;
                            b = -1;
                            c = -1;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == -1) && (c == -1) && (d == -1)) {
                            a = e;
                            b = e;
                            c = -1;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == e) && (c == -1) && (d == -1)) {
                            a = -1;
                            b = e;
                            c = -1;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == e) && (c == -1) && (d == -1)) {
                            a = -1;
                            b = e;
                            c = -1;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == e) && (c == -1) && (d == e)) {
                            a = e;
                            b = e;
                            c = e;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == e) && (c == e) && (d == -1)) {
                            a = e;
                            b = e;
                            c = -1;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == e) && (c == -1) && (d == e)) {
                            a = -1;
                            b = e;
                            c = e;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == e) && (c == e) && (d == e)) {
                            a = e;
                            b = -1;
                            c = e;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == -1) && (c == e) && (d == e)) {
                            a = e;
                            b = -1;
                            c = -1;
                            d = e;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == e) && (b == -1) && (c == -1) && (d == e)) {
                            a = -1;
                            b = e;
                            c = e;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                        if ((a == -1) && (b == e) && (c == e) && (d == -1)) {
                            a = -1;
                            b = -1;
                            c = -1;
                            d = -1;
                            t.setTerrain(a, b, c, d);
                            return true;
                        }
                }
            }
        }
        return false;
    }

    public Body body;
    BodyDef bdef = new BodyDef();
    PolygonShape pshape;
    FixtureDef fdef = new FixtureDef();
    public Fixture fixture;
    private static final double DEGREES_TO_RADIANS = (double)(Math.PI/180);
    public obj.objecttype objType = obj.objecttype.POINTER;

    public void destroyPointer() {
        if (body != null) {
            if (world.getBodyCount() > 0) {
                world.destroyBody( body );
            }
        }
    }

    public void updatePointer(float x, float y){
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(x,-y);
        body = world.createBody(bdef);
        fdef.filter.categoryBits = game.PLAYER_BIT;
        fdef.filter.maskBits = game.DEFAULT_BIT;
        fdef.isSensor = true;

        pshape = new PolygonShape();
        pshape.setAsBox( 3,3 );
        fdef.shape = pshape;
        fixture = body.createFixture(fdef);
        objType = obj.objecttype.POINTER;
        fixture.setUserData(objType);
    }

    public void checkBox2D(obj ox){
        tempObjx.add( ox );
        loadList( "object" );
       // status( tempObjx.size()+"",1 );
    }

    public void showPropBox2D(obj ox){
        selobj=ox;
           // Gdx.app.log( "PP",activeobjtool+"");
        //cumi
        if (activeobjtool == 7) {
            obj oc = new obj();
            oc.setGid( ox.getGid() );
            oc.setProperties( ox.getProperties() );
            oc.setPoints( ox.getPoints() );
            oc.setW( ox.getW() );
            oc.setH( ox.getH() );
            oc.setX( ox.getX() );
            oc.setY(ox.getY());
            oc.setId( ox.getId() );
            oc.setName( ox.getName() );
            oc.setRotation( ox.getRotation() );
            oc.setShape( ox.getShape() );
            oc.setType( ox.getType() );
            oc.setText( ox.getText() );

            Json json = new Json();
            clipobjcpy = json.toJson(oc);
            backToMap();
            return;
        }
        else if (activeobjtool == 8) {
            ox.destroyBody( world );
            layers.get(selLayer).getObjects().remove(ox);
            backToMap();
            return;
        }

        gotoStage(tObjProp);
        tf.get(0).setText(Integer.toString(ox.getId()));
        tf.get(1).setText(Float.toString(ox.getX()));
        tf.get(2).setText(Float.toString(ox.getY()));
        tf.get(3).setText(Float.toString(ox.getW()));
        tf.get(4).setText(Float.toString(ox.getH()));
        if (ox.getName() != null) {
            tf.get(5).setText(ox.getName());
        } else {
            tf.get(5).setText("");
        }
        if (ox.getType() != null) {
            tf.get(6).setText(ox.getType());
        } else {
            tf.get(6).setText("");
        }

        if (Float.toString(ox.getRotation()) != null) {
            tf.get(7).setText(Float.toString(ox.getRotation()));
        } else {
            tf.get(7).setText("");
        }

    }

    java.util.List<obj> tempObjx = new ArrayList<obj>();

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    private boolean tapObject(Integer num, int ae, int ab) {

        /* experiment
        int xx = num % Tw;
        int yy = num / Tw;
        float newab = (ab * Tsw / 2) + (ae * Tsw / 2);
        float newae = (ab * Tsh / 2) - (ae * Tsh / 2);
        */
        tempObjx.clear();
        updatePointer(ae,-ab);
        return true;

        //return false;
    }

    private void longpressObject(float p1, float p2){
        obj nyok = new obj();

        String shapeNameX = "";

        switch (activeobjtool) {
            case 0:
                shapeNameX = "rectangle";
                break;
            case 1:
                shapeNameX = "ellipse";
                break;
            case 2:
                shapeNameX = "point";
                break;
            case 3:
                shapeNameX = "polygon";
                break;
            case 4:
                shapeNameX = "polyline";
                break;
            case 6:
                shapeNameX = "image";
                break;
            case 5:
                shapeNameX = "text";
                break;
            case 7:
                shapeNameX = "copypaste";
                break;
            case 8:
                shapeNameX = "eraser";
                break;
        }


        Vector3 touch = new Vector3();
        cam.unproject(touch.set(p1, p2, 0));
        int ae = (int) touch.x;
        int ab = (int) touch.y;

        if (activeobjtool == 7) {
            if (clipobjcpy == "") return;

            Json json = new Json();
            nyok = json.fromJson(obj.class, clipobjcpy);
            nyok.setId(curid);


            curid += 1;
            if (magnet == 1) {
                switch (nyok.getShape()) {
                    case "image":
                        nyok.setX((ae / Tsw) * Tsw);
                        nyok.setY(Tsh+((-ab + Tsh) / Tsh) * Tsh);
                        break;

                    case "rectangle":
                    case "ellipse":
                    case "polygon":
                    case "polyline":
                    case "text":


                    default:
                        if (orientation.equalsIgnoreCase( "isometric" )) {
                            //huft
                            ///
                            int newae = (int) (ae - ((Tsw / Tsh) * ab) );
                            int newab = (int) ((ae / (Tsw / Tsh)) + ab - Tsh/2f);
                            ae = newae/2;
                            ab = newab;

                            nyok.setX((float) (roundToHalf((float) ae/Tsw))*Tsw);
                            nyok.setY((( -ab + Tsh) / Tsh) * Tsh);
                            status(nyok.getX()+"+"+nyok.getY(),1);
                            //code for unlocked
                            /*
                            int newae = (ae - ((Tsw / Tsh) * ab) + Tsw/2);
                            int newab = ((ae / (Tsw / Tsh)) + ab - Tsh/2);
                            ae = newae/2;
                            ab = newab;

                            nyok.setX(((float) ae / Tsw ) * Tsw);
                            nyok.setY((((float) -ab + Tsh) / Tsh) * Tsh);

                             */


                        }else{
                            nyok.setX((ae / Tsw) * Tsw);
                            nyok.setY(((-ab + Tsh) / Tsh) * Tsh);

                        }

                        break;
                    case "point":
                        nyok.setX((ae / Tsw) * Tsw + (Tsw / 2));
                        nyok.setY(((-ab + Tsh) / Tsh) * Tsh - Tsh / 2 + Tsh);
                        break;




                }
            }else{
                if (nyok.getShape()!="image") {
                    nyok.setX( (int) ae );
                    nyok.setY( -(int) ab + Tsh );
                }else{
                    nyok.setX( (int) ae );
                    nyok.setY( -(int) ab + Tsh +Tsh );

                }
            }

            //I don't understand why I need this.. error if removed!
            switch (nyok.getShape()) {
                case "image":
                    nyok.setShape( "image" );
                    break;
                case "rectangle":
                    break; //bugfix
                case "ellipse":
                    nyok.setShape( "ellipse" );
                    break;
                case "polygon":
                    nyok.setShape( "polygon" );
                    break;
                case "polyline":
                    nyok.setShape( "polyline" );
                    break;
                case "text":
                    nyok.setShape( "text" );
                    break;
                case "point":
                    nyok.setShape( "point" );
                    break;

            }


            layers.get(selLayer).getObjects().add(nyok);
            updateObjectCollision();
            //cumi
            return;
        }

        if (magnet == 1) {
            switch (activeobjtool) {
                case 0:
                case 1:
                case 6:
                case 5:
                    if (orientation.equalsIgnoreCase("isometric")) {
                        //cool way to convert isometric to orthogonal, new iso tap detection
                        int newae = (int) (ae - ((Tsw/Tsh) * ab) + Tsw/2);
                        int newab = (int) ((ae / (Tsw/Tsh)) + ab -Tsh/2);
                        int numa = (Tw * ((-newab + Tsh) / Tsh) + (newae / Tsw));
                        //status(""+newae/2f+"|"+(newab-Tsh)+" num:"+numa,1);
                        if (numa<0 || numa >Tw*Th) return;
                        ae =  (int) (newae/2f);
                        ab = newab;

                        if (numa!=-1)
                        {
                            nyok.setX((int) (numa % Tw)*Tsh );
                            nyok.setY((int) (numa / Tw)*Tsh);

                            //create new obj.
                            nyok = new obj(curid, (int) (numa % Tw)*Tsh , (int) (numa / Tw)*Tsh, Tsh, Tsh, "", "",world);
                            nyok.setShape(shapeNameX);
                            nyok.updateVertices( world , Tsh);
                        }

                    }else {
                        int dd=0;
                        if (activeobjtool==6) dd=Tsh;

                        nyok = new obj(curid, (int) (ae / Tsw) * Tsw, (int) ((-ab + Tsh) / Tsh) * Tsh+dd, Tsw, Tsh, "", "",world);
                        nyok.setShape(shapeNameX);
                        nyok.updateVertices( world, Tsh );
                    }

                    break;
                case 2:
                    nyok = new obj(curid, (int) (ae / Tsw) * Tsw + (Tsw / 2), (int) ((-ab + Tsh) / Tsh) * Tsh - Tsh / 2 + Tsh, Tsw, Tsh, "", "",world);
                    nyok.setShape(shapeNameX);
                    nyok.updateVertices( world, Tsh);

                    break;
                case 3: case 4:
                    nyok = new obj(curid, (int) (ae / Tsw) * Tsw, (int) ((-ab + Tsh) / Tsh) * Tsh, Tsw, Tsh, "", "",world);
                    nyok.setShape(shapeNameX);

                    break;


            }
        } else {
            //Gdx.app.log("AA","AAAAA"+magnet);
            switch (activeobjtool) {
                case 0: case 1: case 5: case 6:
                    nyok = new obj(curid, (int) (ae-Tsw/2f), (int) (-ab+Tsh*0.5f), Tsw, Tsh, "", "",world);
                    nyok.setShape(shapeNameX);
                    nyok.updateVertices( world, Tsh );
                    break;
                case 2: case 3: case 4:
                    nyok = new obj(curid, (int) (ae), (int) (-ab+Tsh), Tsw, Tsh, "", "",world);
                    nyok.setShape(shapeNameX);
                    nyok.updateVertices( world, Tsh );
                    break;
            }


        }
        layers.get(selLayer).getObjects().add(nyok);

        curid += 1;

        switch (activeobjtool) {

            case 6:
                newobject = nyok;
                pickTile("newimgobj");
                break;

            case 5:
                newobject = nyok;
                Gdx.input.getTextInput(pnewtextobject, "Set Text", "", "");


                break;
            case 3:
            case 4:
                newobject = nyok;
                newobject.addPoint(0, 0);
                mode = "newpoly";
                break;


        }


    }

    public void tapTile(int num, boolean follower, boolean terra, boolean smartStamp, int curspr) {
        if (cammode == "View only") return;
        if (layers.size()==0) return;
        //the actual stamping process.
        if (assemblymode) {
            assemblynum=num;
            //status (assemblynum+"",1);
            return;
        }



        if (stamp && !roll && !smartStamp) {
            cue("stamp");
                //log("culprit");
                int widih = endSelect % tilesets.get( seltset ).getWidth() - startSelect % tilesets.get( seltset ).getWidth() + 1;
                int heih = endSelect / tilesets.get( seltset ).getWidth() - startSelect / tilesets.get( seltset ).getWidth() + 1;

                for (int yy = 0; yy < heih; yy++) {
                    for (int xx = 0; xx < widih; xx++) {
                        if ((num + xx + (yy * Tw)) < Th * Tw) {
                            if ((num + xx + (yy * Tw)) % Tw >= num % Tw) {


                                int onset = 0;
                                if (orientation.equalsIgnoreCase( "isometric" )) {
                                    onset = yy - xx * Tw;
                                }

                                int nyum = num + xx + (yy * Tw) + onset;
                                if (nyum < 0) return;
                                long oi = (long) curspr + xx + (yy * tilesets.get( seltset ).getWidth());

                                Long from = layers.get( selLayer ).getStr().get( nyum );
                                int tzet = layers.get( selLayer ).getTset().get( nyum );
                                int tzeto = seltset;

                                layerhistory lh2 = new layerhistory( follower, from, oi, nyum, selLayer, tzet, tzeto );


                                if (from != oi) {
                                    undolayer.add( lh2 );
                                    uploaddata( lh2 );
                                    redolayer.clear();

                                    layers.get( selLayer ).getStr().set( nyum, oi );
                                    layers.get( selLayer ).getTset().set( nyum, seltset );
                                    updateCache( nyum );
                                    follower = true;
                                }
                            }
                        }

                    }
                }

                //layers.get(selLayer).getStr().set(tilesets.get(seltset).getFirstgid()-1, (long) curspr);

                return;

            //so basically if not stamp and roll
        } else {

            //first, work with rotation.
            String hex = Long.toHexString(curspr);
            String trailer = "00000000" + hex;
            hex = trailer.substring(trailer.length() - 8);


            String spc = "";

            switch (rotate) {
                case 0://"20"://
                    spc = "00";
                    break;
                case 1://"40"://flipy
                    spc = "A0";
                    break;
                case 2://"60"://270 degrees clockwise
                    spc = "C0";
                    break;
                case 3://"80"://flipx
                    spc = "60";
                    break;
                case 4://"A0"://90 degress cw
                    spc = "80";
                    break;
                case 5://"C0"://180 degrees cw
                    spc = "E0";
                    break;
                case 6://diagonal flip
                    spc = "40";
                    break;
                case 7://diagonal flip2
                    spc = "20";
                    break;
            }
            long oi = Long.decode("#" + spc + hex.substring(2));
            if (eraser) oi=0;

            switch (activetool) {

                //fkin rectangle
                case 1:
                    oi = 0;
                case 0:
                    //brush
                case 4:
                    cue("tileclick");
                    long noi = Long.decode("#" + 00 + hex.substring(2)) - tilesets.get(seltset).getFirstgid();
                    Long from = layers.get(selLayer).getStr().get(num);
                    int tzet = layers.get(selLayer).getTset().get(num);

                    layerhistory lh2 = new layerhistory(follower, from, oi, num, selLayer, tzet, seltset);
                    updateCache(num);

                    if (from != oi) {
                        undolayer.add(lh2);
                        uploaddata(lh2);
                        redolayer.clear();
                    }
                    layers.get(selLayer).getStr().set(num, oi);
                    layers.get(selLayer).getTset().set(num, seltset);
                    tile t = null;

                    for (int k = 0; k < tilesets.get(seltset).getTiles().size(); k++) {
                        if (tilesets.get(seltset).getTiles().get(k).getTileID() == noi) {
                            t = tilesets.get(seltset).getTiles().get(k);
                        }
                    }

                    //OLD code for terrain
                    /*
                    if (t == null) return;
                    int[] aa = null;
                    if (t.isTerrain()) aa = t.getTerrain();

                    if (t.isTerrain() && t.isCenter()) {

                        //this loop detect the surrounding of a tile
                        for (int i = 0; i < 9; i++) {

                            //gogo is for detecting index of surrounding tile
                            int gogo = num;
                            switch (i) {
                                case 0:
                                    if (num <= Tw || num % Tw == 0) continue;
                                    gogo = num - Tw - 1;
                                    break;
                                case 1:
                                    if (num <= Tw) continue;
                                    gogo = num - Tw;
                                    break;
                                case 2:
                                    if (num <= Tw || num % Tw == Tw - 1) continue;
                                    gogo = num - Tw + 1;
                                    break;
                                case 3:
                                    if (num <= 1 || num % Tw == 0) continue;
                                    gogo = num - 1;
                                    break;
                                case 4:
                                    if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                                    gogo = num + 1;
                                    break;
                                case 5:
                                    if (num >= Tw * Th - Tw || num % Tw == 0) continue;
                                    gogo = num + Tw - 1;
                                    break;
                                case 6:
                                    if (num >= Tw * Th - Tw) continue;
                                    gogo = num + Tw;
                                    break;
                                case 7:
                                    if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                                    gogo = num + Tw + 1;
                                    break;
                                case 8:
                                    gogo = num;
                                    break;
                            }

                            //nyum is to find the str of gogo location
                            long nyum = layers.get(selLayer).getStr().get(gogo);
                            hex = Long.toHexString(nyum);
                            trailer = "00000000" + hex;
                            hex = trailer.substring(trailer.length() - 8);
                            noi = Long.decode("#" + 00 + hex.substring(2)) - tilesets.get(seltset).getFirstgid();

                            t = null;

                            for (int k = 0; k < tilesets.get(seltset).getTiles().size(); k++) {
                                if (tilesets.get(seltset).getTiles().get(k).getTileID() == noi) {
                                    t = tilesets.get(seltset).getTiles().get(k);
                                }
                            }
                            if (t == null) continue;
                            //so basically aa is for the mid, bb is for surrouding
                            int[] bb = t.getTerrain();
                            int[] cc = null;
                            if (bb != null) {
                                switch (i) {
                                    case 0:
                                        cc = new int[]{bb[0], bb[1], bb[2], aa[3]};
                                        break;
                                    case 1:
                                        cc = new int[]{bb[0], bb[1], aa[2], aa[3]};
                                        break;
                                    case 2:
                                        cc = new int[]{bb[0], bb[1], aa[2], bb[3]};
                                        break;
                                    case 3:
                                        cc = new int[]{bb[0], aa[1], bb[2], aa[3]};
                                        break;
                                    case 4:
                                        cc = new int[]{aa[0], bb[1], aa[2], bb[3]};
                                        break;
                                    case 5:
                                        cc = new int[]{bb[0], aa[1], bb[2], bb[3]};
                                        break;
                                    case 6:
                                        cc = new int[]{aa[0], aa[1], bb[2], bb[3]};
                                        break;
                                    case 7:
                                        cc = new int[]{aa[0], bb[1], bb[2], bb[3]};
                                        break;
                                    case 8:
                                        cc = new int[]{aa[0], aa[1], aa[2], aa[3]};
                                        break;
                                }
                                java.util.List<Integer> lint = new ArrayList<Integer>();

                                for (int u = 0; u < tilesets.get(seltset).getTiles().size(); u++) {
                                    tile x = tilesets.get(seltset).getTiles().get(u);
                                    if (x.getTerrainString().equalsIgnoreCase(cc[0] + "," + cc[1] + "," + cc[2] + "," + cc[3])) {
                                        lint.add(u);
                                    }
                                }
                                if (lint.size() > 0) {
                                    tile y = tilesets.get(seltset).getTiles().get(lint.get((int) (Math.random() * lint.size())));


                                    from = layers.get(selLayer).getStr().get(gogo);
                                    tzet = layers.get(selLayer).getTset().get(gogo);
                                    lh2 = new layerhistory(true, from, (long) y.getTileID() + tilesets.get(seltset).getFirstgid(), gogo, selLayer, tzet, seltset);


                                    if (from != (long) y.getTileID() + tilesets.get(seltset).getFirstgid()) {
                                        undolayer.add(lh2);
                                        uploaddata(lh2);
                                        redolayer.clear();
                                    }

                                    layers.get(selLayer).getStr().set(gogo, (long) y.getTileID() + tilesets.get(seltset).getFirstgid());

                                }


                            }
                        }


                    }


                     */
                    //// CODE FOR TERRAINS!!! (14 JUNE)

                    if (oi==0) return;
                    //updateAT();
                    //NEW code for terrain
                    if (t == null) return;
                    if (!terra) return;
                    if (smartStamp) return;

                    if (t.isTerrainForEditor() && t.isCenter()) {
                        //means aa will alwyas be the center
                        //this loop detect the surrounding of a tile
                        final int numa = num;
                        /*
                        Thread thread = new Thread(){
                            public void run(){

                         */
                                Terrainify(numa, t, new int[]{0,1,2,3,4,5,6,7,8},false);
                                //log(numanuma.size()+"");

                                while(!numanuma.isEmpty()) {
                                    int gogo = 0;
                                    int[] dir = new int[]{};
                                    switch ((int) numanuma.get( 0 ).dir) {
                                        case 0:
                                            dir = new int[]{0, 1, 3};
                                            break;
                                        case 1:
                                            dir = new int[]{1};
                                            break;
                                        case 2:
                                            dir = new int[]{2, 1, 4};
                                            break;
                                        case 3:
                                            dir = new int[]{3};
                                            break;
                                        case 4:
                                            dir = new int[]{4};
                                            break;
                                        case 5:
                                            dir = new int[]{5, 3, 6};
                                            break;
                                        case 6:
                                            dir = new int[]{6};
                                            break;
                                        case 7:
                                            dir = new int[]{7, 6, 4};
                                            break;
                                        case 8:
                                            dir = new int[]{8};
                                            break;
                                    }

                                        Terrainify( (int) numanuma.get( 0 ).num, numanuma.get( 0 ).t, dir, false );
                                    try {
                                        numanuma.remove( 0 );
                                    }catch (Exception e){}

                                }
                                /*
                                }
                        };

                        thread.start();

                                 */

                        /*
                        Terrainify(num, t, new int[]{0,1,2,3,4,5,6,7,8},false);
                        //log(numanuma.size()+"");

                        while(!numanuma.isEmpty()){
                            int gogo=0;
                            int[] dir = new int[]{};
                            switch ((int) numanuma.get(0).dir) {
                                case 0:
                                    dir = new int[]{0,1,3};
                                    break;
                                case 1:
                                    dir = new int[]{1};
                                    break;
                                case 2:
                                    dir = new int[]{2,1,4};
                                    break;
                                case 3:
                                    dir = new int[]{3};
                                    break;
                                case 4:
                                    dir = new int[]{4};
                                    break;
                                case 5:
                                    dir = new int[]{5,3,6};
                                    break;
                                case 6:
                                    dir = new int[]{6};
                                    break;
                                case 7:
                                    dir = new int[]{7,6,4};
                                    break;
                                case 8:
                                    dir = new int[]{8};
                                    break;
                            }


                            Terrainify((int) numanuma.get(0).num, numanuma.get(0).t, dir,false);
                            numanuma.remove(0);
                        }
                         */




                    }

                    //// END OF NEW TERRAIN CODE


                    break;

                case 2: //fill
                    if (roll) activetool = 0;
                    //oi = Long.decode("#" + spc + hex.substring(2));
                    from = layers.get(selLayer).getStr().get(num);
                    tzet = layers.get(selLayer).getTset().get(num);

                    lh2 = new layerhistory(false, from, oi, num, selLayer, tzet, seltset);
                    uploaddata(lh2);
                    undolayer.add(lh2);
                    redolayer.clear();

                    //layers.get(selLayer).getStr().set(num, oi);

                    fillthis(num, oi, from, 0);
                    break;
                case 3: //paste, at last.
                    if (movetool==selectTool.PICKER){
                        this.curspr=layers.get(selLayer).getStr().get(num).intValue();
                        addRecentTile( this.curspr );
                        setTsetFromCurspr();
                        break;
                    }

                    //status(mapstartSelect+"/"+mapendSelect,5);
                    if (cliplayer==null) return;
                    int widih = mapendSelect % Tw - mapstartSelect % Tw;
                    int heih = mapendSelect / Tw - mapstartSelect / Tw;
                    boolean followe = false;
                    int numa = mapstartSelect;

                    int newmapstartselect=-1, newmapendselect=-1; //for move tool

                        //loop for clearing (move tool)
                    for (int yy = 0; yy <= heih; yy++) {
                        for (int xx = 0; xx <= widih; xx++) {
                            if ((numa + xx + (yy * Tw)) < Th * Tw) {
                                if ((numa + xx + (yy * Tw)) % Tw >= numa % Tw) {

                                    int nyum = num + xx + (yy * Tw); //num is correct, thanks old me

                                    //nyum is the tap data remember
                                    //so the original is orinyum...
                                    if (nyum / Tw != (num + (yy * Tw)) / Tw) continue;
                                    if (nyum >= Tw * Th) continue;
                                    int orinyum = mapstartSelect + xx + (yy * Tw);


                                    //the previous data for undo
                                    from = layers.get(clipsource).getStr().get(orinyum);
                                    tzet = layers.get(clipsource).getTset().get(orinyum);

                                    if (movetool==selectTool.MOVE) {
                                        //if using move tool, then clear the data

                                        //clearing should be before.. so new loop?
                                        layers.get(clipsource).getStr().set(orinyum,(long) 0);
                                        layers.get(clipsource).getTset().set(orinyum,-1);
                                        if (newmapstartselect==-1) newmapstartselect=nyum;
                                        newmapendselect=nyum;
                                        lh2 = new layerhistory(followe, from, 0, orinyum, clipsource, tzet, -1);
                                        undolayer.add(lh2);
                                        updateCache( nyum );
                                        updateCache( orinyum );
                                        uploaddata(lh2);
                                        redolayer.clear();
                                        followe = true;
                                    }


                                }
                            }

                        }
                    }
                        //followe = false;
                        //loop for drawing
                    if (movetool!=selectTool.CLONE){
                        for (int yy = 0; yy <= heih; yy++) {
                            for (int xx = 0; xx <= widih; xx++) {
                                if ((numa + xx + (yy * Tw)) < Th * Tw) {
                                    if ((numa + xx + (yy * Tw)) % Tw >= numa % Tw) {

                                        int nyum = num + xx + (yy * Tw); //num is correct, thanks old me
                                        if (movetool==selectTool.FLIP) {
                                            nyum = num + (widih-xx) + (yy * Tw); //num is correct, thanks old me
                                        }
                                        //nyum is the tap data remember
                                        //so the original is orinyum...
                                        if (nyum / Tw != (num + (yy * Tw)) / Tw) continue;
                                        if (nyum >= Tw * Th) continue;
                                        int orinyum = mapstartSelect + xx + (yy * Tw);

                                        //get str from clipboard
                                        oi = cliplayer.getStr().get(orinyum);
                                        if (oi==0) continue;
                                        int oritzet = cliplayer.getTset().get(orinyum);

                                        //the previous data for undo
                                        from = layers.get(selLayer).getStr().get(nyum);
                                        tzet = layers.get(selLayer).getTset().get(nyum);


                                        lh2 = new layerhistory(followe, from, oi, nyum, selLayer, tzet, oritzet);

                                        //if (from != oi) {
                                            undolayer.add(lh2);
                                            uploaddata(lh2);
                                            redolayer.clear();
                                            layers.get(selLayer).getStr().set(nyum, oi);
                                            layers.get(selLayer).getTset().set(nyum, oritzet);
                                            followe = true;
                                        updateCache( nyum );
                                        updateCache( orinyum );

                                        //}


                                    }
                                }

                            }
                        }
                    }else{

                        //old copy code... should've saved it.... sigh.
                        for (int yy = 0; yy <= heih; yy++) {
                            for (int xx = 0; xx <= widih; xx++) {
                                if ((numa + xx + (yy * Tw)) < Th * Tw) {
                                    if ((numa + xx + (yy * Tw)) % Tw >= numa % Tw) {

                                        int nyum = num + xx + (yy * Tw); //num is correct, thanks old me
                                        //nyum is the tap data remember
                                        //so the original is orinyum...
                                        if (nyum / Tw != (num + (yy * Tw)) / Tw) continue;
                                        if (nyum >= Tw * Th) continue;
                                        int orinyum = mapstartSelect + xx + (yy * Tw);

                                        for (int i=0;i<layers.size();i++){

                                            layer l = layers.get(i);
                                            if (l.getType()== layer.Type.TILE) {
                                                //get str from clipboard
                                                oi = l.getStr().get( orinyum );
                                                int oritzet = l.getTset().get( orinyum );

                                                //the previous data for undo
                                                from = l.getStr().get( nyum );
                                                tzet = l.getTset().get( nyum );


                                                lh2 = new layerhistory( followe, from, oi, nyum, i, tzet, oritzet );

                                                //if (from != oi) {
                                                undolayer.add( lh2 );
                                                uploaddata( lh2 );
                                                redolayer.clear();
                                                layers.get( i ).getStr().set( nyum, oi );
                                                layers.get( i ).getTset().set( nyum, oritzet );
                                                followe = true;
                                                updateCache( nyum );
                                                updateCache( orinyum );

                                            }
                                        }


                                    }
                                }

                            }
                        }

                    }

                    if (movetool==selectTool.MOVE) {
                        mapstartSelect =  newmapstartselect;
                        mapendSelect = newmapendselect;

                        //reduplicate layer because everthing is taken from the clip
                        layer lay;
                        Json json = new Json();

                        String clip = json.toJson(layers.get(selLayer));

                        lay = json.fromJson(layer.class, clip);
                        lay.setName("CLIPBOARD"); //why not, lol.
                        cliplayer = lay;
                    }

            }

        }
        //pdateMinimap(); berat
    }

    ATGraph ATGraph;
    GraphPath<AutoTile> ATPath;

    private class tung{
        public long num;
        public tile t;
        public int dir;
        private tung(long num, tile t, int dir){
            this.num=num;
            this.t=t;
            this.dir=dir;
        }
    }

    private void updateAT(){
        if (tilesets.isEmpty()) return;

        ATGraph = new ATGraph();
        java.util.List<Vector2> history = new ArrayList<Vector2>();
        java.util.List<Integer> historyA = new ArrayList<Integer>();

        AutoTile myAT = new AutoTile(-1);
        historyA.add(-1);
        ATGraph.addAT(myAT);

        for (tile t: tilesets.get( seltset ).getTiles())
        {
            int cond=t.getTerrain()[0];
            if (t.isTerrainForEditor() && t.isCenter() && !historyA.contains(cond)){
                myAT = new AutoTile(cond);
                historyA.add(cond);
                ATGraph.addAT(myAT);
                //log(myAT.name+"");
            }
        }

        for (tile t: tilesets.get( seltset ).getTiles())
        {
            if (t.isTerrainForEditor() && t.isTransition()){
                Vector2 newHist = new Vector2(t.getTransA(), t.getTransB());
                boolean sdh=false;
                for (Vector2 v: history){
                    if (v.x ==t.getTransA() && v.y == t.getTransB())
                    {
                        sdh=true; break;
                    }
                }
                if (!sdh){
                    AutoTile AT1= ATGraph.getAT( (int) newHist.x );
                    AutoTile AT2= ATGraph.getAT( (int) newHist.y );
                    //log(newHist.x+"-----"+newHist.y); //cool comment
                    try {
                        ATGraph.connectAT( AT1, AT2 );
                        ATGraph.connectAT( AT2, AT1 );
                        history.add( newHist );
                    }catch (Exception e)
                    {

                    }
                }
            }
        }
    }

    private void findBestTile(int A, int B){
        try {
            AutoTile AT1 = ATGraph.getAT( A );
            AutoTile AT2 = ATGraph.getAT( B );
            ATPath = ATGraph.findPath( AT1, AT2 );
        }catch(Exception e){}
    }

    java.util.List<tung> numanuma = new ArrayList<tung>();

    private boolean Terrainify(int numx, tile nx, int[] directionsx, boolean fillx){
        int num = numx;
        tile n = nx;
        int[] directions = directionsx;
        boolean fill = fillx;

        boolean requestReterrain=true;
        int[] aa = null;
        Long from=null;
        int tzet = 0;
        layerhistory lh2=null;
        //if (n==null) return true;

        while (requestReterrain) {
            requestReterrain = false;

            if (n != null) {
                if (n.isTerrainForEditor()) {
                    aa = n.getTerrain();
                }
            }
            if (aa == null) aa = new int[]{-1, -1, -1, -1};


            for (int i : directions) { //check start here

                //gogo is for detecting index of surrounding tile
                int gogo = num;
                switch (i) {
                    case 0:
                        if (num <= Tw || num % Tw == 0) continue;
                        gogo = num - Tw - 1;
                        break;
                    case 1:
                        if (num <= Tw) continue;
                        gogo = num - Tw;
                        break;
                    case 2:
                        if (num <= Tw || num % Tw == Tw - 1) continue;
                        gogo = num - Tw + 1;
                        break;
                    case 3:
                        if (num <= 1 || num % Tw == 0) continue;
                        gogo = num - 1;
                        break;
                    case 4:
                        if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                        gogo = num + 1;
                        break;
                    case 5:
                        if (num >= Tw * Th - Tw || num % Tw == 0) continue;
                        gogo = num + Tw - 1;
                        break;
                    case 6:
                        if (num >= Tw * Th - Tw) continue;
                        gogo = num + Tw;
                        break;
                    case 7:
                        if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                        gogo = num + Tw + 1;
                        break;
                    case 8:
                        gogo = num;
                        break;
                }
                if (layers.get(selLayer).getStr().get( gogo )==layers.get(selLayer).getStr().get( num )) continue;

                //nyum is to find the str of gogo location
                long nyum = layers.get( selLayer ).getStr().get( gogo );
                hex = Long.toHexString( nyum );
                trailer = "00000000" + hex;
                hex = trailer.substring( trailer.length() - 8 );
                long noi = Long.decode( "#" + 00 + hex.substring( 2 ) ) - tilesets.get( seltset ).getFirstgid();

                tile t = null;

                for (int k = 0; k < tilesets.get( seltset ).getTiles().size(); k++) {
                    if (tilesets.get( seltset ).getTiles().get( k ).getTileID() == noi) {
                        t = tilesets.get( seltset ).getTiles().get( k );
                    }
                }
                int[] bb;
                if (t == null) {
                    bb = new int[]{-1, -1, -1, -1};
                } else {
                    bb = t.getTerrain();
                }
                //so basically aa is for the mid, bb is for surrouding

                int[] cc = null;

                if (bb != null) {
                    cc = terrainInt( i, aa, bb );
                    //log(cc[0]+","+cc[1]+","+cc[2]+","+cc[3]);
                    java.util.List<Integer> lint = new ArrayList<Integer>();

                    for (int u = 0; u < tilesets.get( seltset ).getTiles().size(); u++) {
                        tile x = tilesets.get( seltset ).getTiles().get( u );
                        if (x.getTerrainString().equalsIgnoreCase( cc[0] + "," + cc[1] + "," + cc[2] + "," + cc[3] )) {
                            //tile found with the selected terrain
                            lint.add( u );
                        }
                    }
                    //if it is found.
                    if (lint.size() > 0) {
                        tile y = tilesets.get( seltset ).getTiles().get( lint.get( (int) (Math.random() * lint.size()) ) );


                        from = layers.get( selLayer ).getStr().get( gogo );
                        tzet = layers.get( selLayer ).getTset().get( gogo );
                        lh2 = new layerhistory( true, from, (long) y.getTileID() + tilesets.get( seltset ).getFirstgid(), gogo, selLayer, tzet, seltset );

                        if (from != (long) y.getTileID() + tilesets.get( seltset ).getFirstgid()) {
                            undolayer.add( lh2 );
                            uploaddata( lh2 );
                            redolayer.clear();
                        }

                        layers.get( selLayer ).getStr().set( gogo, (long) y.getTileID() + tilesets.get( seltset ).getFirstgid() );
                        updateCache( gogo );
                        layers.get( selLayer ).getTset().set( gogo, seltset );

                        if ((cc[0] + "," + cc[1] + "," + cc[2] + "," + cc[3]).equalsIgnoreCase( "-1,-1,-1,-1" )) {
                            layers.get( selLayer ).getStr().set( gogo, (long) 0 );
                            layers.get( selLayer ).getTset().set( gogo, -1 );

                        }

                    } else {
                        //no AI for bad terrain...
                        // if (aa[0]==-1 && aa[1] ==-1 && aa[2]==-1 && aa[3]==-1) return true;
                        if (bb[0] == -1 && bb[1] == -1 && bb[2] == -1 && bb[3] == -1) return true;
                        int against = bb[0];
                        if (bb[0] == aa[0]) against = bb[1];
                        if (bb[1] == aa[0]) against = bb[2];
                        if (bb[2] == aa[0]) against = bb[3];

                        findBestTile( aa[0], against );
                        //log( ATPath.getCount() + "" );
                        if (ATPath.getCount() <= 1) continue;
                        int ATid = ATPath.get( 1 ).name;
                        //log("Result="+ATid+"");


                        for (int u = 0; u < tilesets.get( seltset ).getTiles().size(); u++) {

                            tile x = tilesets.get( seltset ).getTiles().get( u );
                            if (x.getTerrainString().equalsIgnoreCase( ATid + "," + ATid + "," + ATid + "," + ATid )) {
                                lint.add( u );
                                //log(u+"}"+ATid);
                            }
                        }

                        if (lint.size() > 0) {
                            tile y = tilesets.get( seltset ).getTiles().get( lint.get( (int) (Math.random() * lint.size()) ) );

                            from = layers.get( selLayer ).getStr().get( gogo );
                            tzet = layers.get( selLayer ).getTset().get( gogo );
                            lh2 = new layerhistory( true, from, (long) y.getTileID() + tilesets.get( seltset ).getFirstgid(), gogo, selLayer, tzet, seltset );

                            if (from != (long) y.getTileID() + tilesets.get( seltset ).getFirstgid()) {
                                undolayer.add( lh2 );
                                uploaddata( lh2 );
                                redolayer.clear();
                            }

                            layers.get( selLayer ).getStr().set( gogo, (long) y.getTileID() + tilesets.get( seltset ).getFirstgid() );
                            updateCache( gogo );

                            layers.get( selLayer ).getTset().set( gogo, seltset );
                            if (!fill) requestReterrain = true;

                            if (y.getTerrainString().equalsIgnoreCase( "-1.-1.-1.-1" )) {
                                layers.get( selLayer ).getStr().set( gogo, (long) 0 );
                                layers.get( selLayer ).getTset().set( gogo, -1 );
                                updateCache( gogo );

                                //requestReterrain=false;
                            }

                            switch (i) {
                                case 0:
                                    if (num <= Tw || num % Tw == 0) continue;
                                    gogo = num - Tw - 1;
                                    break;
                                case 1:
                                    if (num <= Tw) continue;
                                    gogo = num - Tw;
                                    break;
                                case 2:
                                    if (num <= Tw || num % Tw == Tw - 1) continue;
                                    gogo = num - Tw + 1;
                                    break;
                                case 3:
                                    if (num <= 1 || num % Tw == 0) continue;
                                    gogo = num - 1;
                                    break;
                                case 4:
                                    if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                                    gogo = num + 1;
                                    break;
                                case 5:
                                    if (num >= Tw * Th - Tw || num % Tw == 0) continue;
                                    gogo = num + Tw - 1;
                                    break;
                                case 6:
                                    if (num >= Tw * Th - Tw) continue;
                                    gogo = num + Tw;
                                    break;
                                case 7:
                                    if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                                    gogo = num + Tw + 1;
                                    break;
                            }
                            if (!fill) numanuma.add( new tung( gogo, y, i ) );


                        }
                    }


                }
            } //for each direction
        }//check ends here
        return false;
    }
    //private boolean singleTile;
    private void log(String s){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Gdx.app.log(dateFormat.format(date),s);
    }
    private class sirch{
        int distance;
        int id;
        boolean dest;
        boolean checked;
        java.util.List<Integer> hist = new ArrayList<Integer>();

    }
    java.util.List<sirch> sears = new ArrayList<sirch>();


    private boolean recurseSearch(int i, int[] tujuan){
        int check=0;
        for (int s=0; s<sears.size();s++){
            check+=1;
            if (sears.get(s).checked) continue;
            if (sears.get(s).dest) continue;

            tile x = tilesets.get(seltset).getTiles().get(sears.get(s).id);
            int[] cc=terrainInt( i,  x.getTerrain(),tujuan);
            if (x.getTerrainString().equalsIgnoreCase(cc[0] + "," + cc[1] + "," + cc[2] + "," + cc[3])) {
                //final destination achieved
                sears.get(s).dest=true;
            }else{

                /////
                for (int u = 0; u < tilesets.get(seltset).getTiles().size(); u++) {
                    tile xx = tilesets.get(seltset).getTiles().get(u);

                    int[] dd = xx.getTerrain();
                    if (dd != null) {

                      //  cc= terrainInt( i,aa,dd );
                    }

                    //creating the first generation of path
                    if (xx.getTerrainString().equalsIgnoreCase(cc[0] + "," + cc[1] + "," + cc[2] + "," + cc[3])) {
                        //tile found with the selected terrain
                        sirch ss = new sirch();
                        ss.distance+=ss.distance;
                    //    ss.hist.add( aa[0] );
                        ss.hist.add( dd[0] );
                        ss.id=u;
                        sears.add(ss);
                    }

                }

                /////

                //not the right one
            }
            sears.get(s).checked=true;
        }
        if (check==sears.size()) return true;
        return false;
    }




    private int[] terrainInt(int direction, int[] aa, int[] bb){
        if (aa==null) aa=new int[]{-1,-1,-1,-1};
        if (bb==null) bb=new int[]{-1,-1,-1,-1};
        int[] cc=null;
        switch (direction) {
            case 0:
                cc = new int[]{bb[0], bb[1], bb[2], aa[3]};
                break;
            case 1:
                cc = new int[]{bb[0], bb[1], aa[2], aa[3]};
                break;
            case 2:
                cc = new int[]{bb[0], bb[1], aa[2], bb[3]};
                break;
            case 3:
                cc = new int[]{bb[0], aa[1], bb[2], aa[3]};
                break;
            case 4:
                cc = new int[]{aa[0], bb[1], aa[2], bb[3]};
                break;
            case 5:
                cc = new int[]{bb[0], aa[1], bb[2], bb[3]};
                break;
            case 6:
                cc = new int[]{aa[0], aa[1], bb[2], bb[3]};
                break;
            case 7:
                cc = new int[]{aa[0], bb[1], bb[2], bb[3]};
                break;
            case 8:
                cc = new int[]{aa[0], aa[1], aa[2], aa[3]};
                break;
        }
        return cc;
    }

    private boolean tapped(Vector3 touch2, gui gui) {
        float x = 0, y = 0, w = 0, h = 0;
        if (landscape) {
            x = gui.getXl();
            y = gui.getYl();
            w = gui.getWl();
            h = gui.getHl();
        } else {
            x = gui.getX();
            y = gui.getY();
            w = gui.getW();
            h = gui.getH();
        }

        if (!landscape) {
            return touch2.y > y / 100 * ssy && touch2.y < h / 100 * ssy && touch2.x > x / 100 * ssx && touch2.x < w / 100 * ssx;
        } else {
            return touch2.y > y / 100 * ssx && touch2.y < h / 100 * ssx && touch2.x > x / 100 * ssy && touch2.x < w / 100 * ssy;
        }
    }

    Server server;
    TextButton tbHost;
    TextButton tbJoin;
    TextButton tbCreateRoom;
    TextButton tbJoinRoom;
    TextButton clearLog, pushUpdateBtn;

    TextArea netLog;
    TextField roomName;
    TextField uniqueID;
    Client client;
    Kryo serverkryo;
    Kryo clientkryo;
    TextField tfRemoteIP;
    TextField tfPort;
    String localIP;
    boolean isServer = false;
    boolean isClient = false;
    boolean isCreateRoom = false;
    boolean isJoinRoom = false;
    String activeRoom = "";
    String myID="";
    public java.util.List<actvClients> activeClients = new ArrayList<actvClients>();

    com.badlogic.gdx.Input.TextInputListener til;


    public void pushUpdate(){
        if (isCreateRoom || isJoinRoom) {
            saveMap( curdir + "/" + curfile );
            FileHandle ff = Gdx.files.absolute( curdir + "/" + curfile );
            String dat = ff.readString();

            ///////
            command cc = new command();
            cc.command = "startDataAll";
            cc.from = myID;
            cc.room = activeRoom;
            client.sendTCP( cc );

            int len = dat.length();
            for (int i=0;i<len;i+=BufferSize){
                String part = dat.substring(i, Math.min(len, i + BufferSize));
                cc = new command();
                cc.command = "dataAll";
                cc.from = myID;
                cc.room = activeRoom;
                cc.data = part;
                slowdown();
                client.sendTCP( cc );

            }
            ///////


            cc = new command();
            cc.command = "allReadThis";
            cc.room=activeRoom;
            cc.from = myID;
            client.sendTCP( cc );
            logNet( "[C] Pushing map update..." );
        }
    }

    String serverMapData="";
    String clientMapData="";
    final int BufferSize = 512;
    final int WaitTime = 20;

    private void loadKryonet(){
        server = new Server(9999999,9999999);
        serverkryo = server.getKryo();
        serverkryo.register(TextChat.class);
        serverkryo.register(layerhistory.class);
        serverkryo.register(PlayerState.class);
        serverkryo.register(command.class);

        client = new Client();
        clientkryo = client.getKryo();
        clientkryo.register(TextChat.class);
        clientkryo.register(layerhistory.class);
        clientkryo.register(PlayerState.class);
        clientkryo.register(command.class);

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof TextChat) {
                    TextChat request = (TextChat)object;
                    broadcast(request.text);
                    lcollabstatus.setText(request.text);


                } else if (object instanceof command) {
                    command cmd = (command) object;
                    switch (cmd.command){
                        case "registerID":
                            actvClients acv = new actvClients();
                            acv.id = cmd.data;
                            acv.room="";
                            activeClients.add(acv);
                            logNet("[S] Registration requested by: " + acv.id);
                            activeClients.add( acv );
                            command cc = new command();
                            cc.from=acv.id;
                            cc.command = "registered";
                            connection.sendTCP(cc);

                            break;
                        case "createRoom":
                            boolean roomok=true;
                            for (actvClients av : activeClients){
                                if (av.creator && av.room.equalsIgnoreCase( cmd.room )){
                                    roomok=false;
                                }
                            }

                            if (roomok){


                                for (actvClients av : activeClients){
                                    if (av.id.equalsIgnoreCase( cmd.from )){
                                        av.room = cmd.room;
                                        av.creator = true;
                                    }
                                }
                                cc = new command();
                                cc.command = "roomCreateOK";
                                connection.sendTCP(cc);
                                logNet("[S] Created room: " + cmd.room);
                                }
                            else
                            {
                                cc = new command();
                                cc.command = "roomCreateFailed";
                                connection.sendTCP(cc);
                                logNet("[S] Room not created: " + cmd.room);
                            }
                            break;
                        case "destroyRoom":
                            for (actvClients av : activeClients){
                                if (av.room.equalsIgnoreCase( cmd.room )){
                                    av.room = "";
                                    av.creator = false;
                                }
                            }
                            cc = new command();
                            cc.command = "roomDestroyed";
                            cc.room = cmd.room;
                            server.sendToAllTCP( cc );
                            logNet("[S] Broadcasted room destruction: " + cmd.room);
                            break;


                        case "joinRequest":
                            //check if the room is available
                            boolean isavail = false;
                            for (actvClients av : activeClients){
                                if (av.room.equalsIgnoreCase( cmd.room ) && av.creator){
                                    isavail=true;
                                }
                            }

                            if (isavail){
                                //room is available
                                for (actvClients av : activeClients){
                                    if (av.id.equalsIgnoreCase( cmd.from )){
                                        av.room = cmd.room;
                                    }
                                }

                                cc = new command();
                                cc.command = "joinRequestAccepted";
                                cc.room = cmd.room;
                                connection.sendTCP(cc);
                                logNet("[S] Join Request accepted for: " + cmd.from + " @ room:" + cmd.room);

                                logNet("[SB] broadcasting join information");
                                cc = new command();
                                cc.command = "joinInformation";
                                cc.room = cmd.room;
                                cc.data = cmd.from;
                                server.sendToAllTCP( cc );
                            } else{
                                //room is not available
                                cc = new command();
                                cc.command = "joinRequestRejected";
                                cc.room = cmd.room;
                                connection.sendTCP(cc);
                                logNet("[S] Join Request rejected for: " + cmd.from + " @ room:" + cmd.room);

                            }



                            break;
                        case "leaveRequest":
                            for (actvClients av : activeClients){
                                if (av.id.equalsIgnoreCase( cmd.from )){
                                    av.room = "";
                                }
                            }
                            logNet("[S] left room for: " +cmd.from +" @ "+ cmd.room);
                            cc = new command();
                            cc.command = "leaveRequestAccepted";
                            cc.room = cmd.room;
                            connection.sendTCP( cc );

                            logNet("[SB] broadcasting leave information");
                            cc = new command();
                            cc.command = "leaveInformation";
                            cc.room = cmd.room;
                            cc.data = cmd.from;
                            server.sendToAllTCP( cc );

                            break;
                        case "startdata":
                        case "data":
                        case "startDataAll":
                        case "dataAll":
                            server.sendToAllTCP( cmd );

                            break;
                        case "readThisBoy":
                            cc = new command();
                            cc.command = "mapInformation";
                            cc.from = cmd.from;
                            cc.room = cmd.room;
                            server.sendToAllTCP( cc );
                            logNet("[SB] broadcasting map information");


                            break;
                        case "allReadThis":
                            cc = new command();
                            cc.command = "mapInformationAll";
                            cc.from = cmd.from;
                            cc.room = cmd.room;
                            server.sendToAllTCP( cc );
                            logNet("[SB] broadcasting open map information");


                            break;
                        case "draw":
                            //try {
                                server.sendToAllTCP( cmd );
                            //}catch(Exception e){
                            //    ErrorBung( e,"MOMON.TXT" );
                            //}
                            break;
                        case "disconnect":
                            logNet("[S] Disconnect Request...");
                            int flag=-1;
                            for (int i=0;i<activeClients.size();i++){
                                if (activeClients.get( i ).id.equalsIgnoreCase( cmd.from )){

                                    flag=i;
                                }

                            }
                            if (flag!=-1){
                                activeClients.remove( flag );
                                logNet("[S] Client erased.");
                            }

                    }
                } else if (object instanceof layerhistory) {
                    layerhistory response = (layerhistory)object;
                    Gdx.app.log("hi","received from client");
                    pushdata(response);
                }
            }
        });

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if (object instanceof command) {
                    command cmd = (command) object;
                    switch (cmd.command) {
                        case "registered":
                            logNet( "[C] Registered as: " + cmd.from);
                            break;
                        case "roomCreateFailed":
                            logNet( "[C] Room already exist!");
                            break;
                        case "roomCreateOK":
                            isCreateRoom = true;
                            activeRoom = roomName.getText();
                            tbCreateRoom.setText("Destroy Room");
                            logNet("[C] Room created: " + roomName.getText());
                            break;
                        case "roomDestroyed":
                            if (cmd.room.equalsIgnoreCase( activeRoom )){
                                isCreateRoom = false;
                                isJoinRoom = false;
                                activeRoom = "";
                                tbCreateRoom.setText(z.createroom);
                                tbJoinRoom.setText(z.joinroom);
                                logNet("[C] Room destroyed: " + roomName.getText());
                            }
                            break;
                        case "joinRequestAccepted":
                            logNet( "[C] Joined room :"+cmd.room);
                            logNet( "[C] Loading map data...");
                            isJoinRoom = true;
                            activeRoom = cmd.room;
                            tbJoinRoom.setText( z.leaveroom );
                            break;
                        case "joinRequestRejected":
                            logNet( "[C] Failed to join room :"+cmd.room);
                            break;
                        case "leaveRequestAccepted":
                            logNet( "[C] Left room :"+cmd.room);
                            isJoinRoom = false;
                            activeRoom = "";
                            tbJoinRoom.setText( z.joinroom);

                            break;
                        case "leaveInformation":
                            if (cmd.room.equalsIgnoreCase(activeRoom)) {
                                logNet( "[CI] "+cmd.data+" left the room.");
                            }
                            break;
                        case "startData":
                            if (cmd.from.equalsIgnoreCase( myID )) {
                                clientMapData = "";
                            }
                            break;
                        case "data":
                            if (cmd.from.equalsIgnoreCase( myID )) {
                                clientMapData += cmd.data;
                            }
                            break;
                        case "startDataAll":
                            if (!cmd.from.equalsIgnoreCase( myID ) && cmd.room.equalsIgnoreCase(activeRoom)) {
                                clientMapData = "";
                            }
                            break;
                        case "dataAll":
                            if (!cmd.from.equalsIgnoreCase( myID ) && cmd.room.equalsIgnoreCase(activeRoom)) {
                                clientMapData += cmd.data;
                            }
                            break;
                        case "joinInformation":
                            if (cmd.room.equalsIgnoreCase(activeRoom) && !cmd.data.equalsIgnoreCase( myID )) {
                                logNet( "[CI] "+cmd.data+" entered the room.");
                                if (isCreateRoom){
                                    //send the map
                                    saveMap(curdir + "/" + curfile);
                                    //read map as text
                                    FileHandle ff = Gdx.files.absolute( curdir +"/"+ curfile );
                                    logNet( "[C] Sending map data...");

                                    String dat = ff.readString();
                                    ///////
                                    command cc = new command();
                                    cc.command = "startData";
                                    cc.from = cmd.data;
                                    cc.room = cmd.room;
                                    connection.sendTCP( cc );

                                    int len = dat.length();
                                    for (int i=0;i<len;i+=BufferSize){
                                        String part = dat.substring(i, Math.min(len, i + BufferSize));
                                        cc = new command();
                                        cc.command = "data";
                                        cc.room = cmd.room;
                                        cc.from = cmd.data;
                                        cc.data = part;
                                        slowdown();
                                        connection.sendTCP( cc );
                                    }
                                    ///////
                                    cc = new command();
                                    cc.command = "readThisBoy";
                                    cc.from = cmd.data;
                                    connection.sendTCP( cc );





                                }else
                                {
                                    //pause until that guy finished opening;
                                }
                            }
                            break;
                        case "mapInformation":
                            if (cmd.from.equalsIgnoreCase( myID )) {
                                logNet( "[CI] map data received!");
                                FileHandle fh = Gdx.files.absolute( basepath+"NotTiled/tempNetworkMap.tmx" );
                                fh.writeString( clientMapData,false);
                                clientMapData="";
                                backToMap();
                                loadtmx( basepath+"NotTiled/tempNetworkMap.tmx"  );
                            }
                            break;
                        case "mapInformationAll":
                            if (!cmd.from.equalsIgnoreCase( myID ) && cmd.room.equalsIgnoreCase(activeRoom)) {
                                logNet( "[CI] map data received!");
                                FileHandle fh = Gdx.files.absolute( curdir + "/" + curfile);

                                fh.writeString( clientMapData,false);
                                clientMapData="";
                                backToMap();
                                loadtmx( curdir + "/" + curfile );
                            }
                            break;
                        case "draw":
                            if (cmd.room.equalsIgnoreCase(activeRoom) && !cmd.from.equalsIgnoreCase( myID )) {
                                layerhistory h = (layerhistory) cmd.lh;
                                if (h.undo) {

                                    long frm = h.from;
                                    long toe = h.to;
                                    int frmts = h.oldtset;
                                    int toets = h.newtset;
                                    h.from = toe;
                                    h.to = frm;
                                    h.oldtset = toets;
                                    h.newtset = frmts;
                                    h.undo = false;

                                }
                                undolayer.add(h);
                                redolayer.clear();
                                layers.get(h.getLayer()).getStr().set(h.getLocation(), h.getTo());
                                layers.get(h.getLayer()).getTset().set(h.getLocation(), h.getNewtset());;
                            }
                            break;
                    }
                }

            }

        });

        tCollab = new Table();
        tCollab.setFillParent(true);
        tCollab.defaults().width(btnx).height(btny).padBottom(2);

        tCollab1 = new Table();
        tCollab1.defaults().width(btnx).height(btny).padBottom(2);

        tCollab2 = new Table();
        tCollab2.defaults().width(btnx).height(btny).padBottom(2);

        Label lTitle = new Label(z.collaboration,skin);
        tbHost = new TextButton(z.runserver,skin);
        Label lRemote = new Label(z.remoteip,skin);
        tfRemoteIP = new TextField("127.0.0.1",skin);
        tfPort = new TextField("11112",skin);
        tbJoin = new TextButton(z.join,skin);
        roomName = new TextField("room1", skin);
        uniqueID = new TextField("Steve", skin);
        tbCreateRoom = new TextButton(z.createroom,skin);
        tbJoinRoom = new TextButton(z.joinroom,skin);
        clearLog = new TextButton(z.clearlog,skin);
        pushUpdateBtn = new TextButton(z.pushupdate,skin);
        tbJoin = new TextButton(z.join,skin);
        netLog = new TextArea("",skin);
        TextButton tbBack = new TextButton(z.back,skin);
        lcollabstatus = new Label(z.status+": "+z.readytoconnect,skin);
        final TextField tfMessage = new TextField("",skin);
        TextButton tbSendMsg = new TextButton(z.sendmessage,skin);

        pushUpdateBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pushUpdate();
            }
        });

        tbSendMsg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (isServer){
                    broadcast( tfMessage.getText() );
                    tfMessage.setText( "" );
                }
                if (isClient){
                    talktoserver(tfMessage.getText());
                    tfMessage.setText("");
                }
            }
        });


        tbBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backToMap();
            }
        });

        til = new com.badlogic.gdx.Input.TextInputListener() {

            @Override
            public void input(String input) {
                if (input == "") {
                    return;
                }

                runServer(Integer.parseInt(input));
            }

            @Override
            public void canceled() {
            }

        };


        tbHost.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!isServer) {
                    runServer( Integer.parseInt( tfPort.getText() ) );
                }else
                {
                    stopServer();
                }
            }
        });

        tbJoin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!isClient) {
                    runClient(tfRemoteIP.getText(), Integer.parseInt(tfPort.getText()));
                }else
                {
                    stopClient();
                }
            }
        });

        tbCreateRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!isCreateRoom) {
                    createRoom();
                }else
                {
                    destroyRoom();
                }
            }
        });

        tbJoinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!isJoinRoom) {
                    joinRoom();
                }else
                {
                    leaveRoom();
                }
            }
        });

        clearLog.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                netLog.setText("");
            }
        });

        tCollab1.add(lTitle).colspan(2).row();
        tCollab1.add(new Label(z.port,skin)).width(btnx/2);
        tCollab1.add(tfPort).width(btnx/2).row();
        tCollab1.add(tbHost).colspan(2).row();
        tCollab1.add(lRemote).width(btnx/2);
        tCollab1.add(tfRemoteIP).width(btnx/2).row();
        tCollab1.add(new Label(z.uniqueid,skin)).width(btnx/2);
        tCollab1.add(uniqueID).width(btnx/2).row();
        tCollab1.add(tbJoin).colspan(2).row();

        tCollab1.add(new Label(z.room,skin)).width(btnx/2);
        tCollab1.add(roomName).width(btnx/2).row();
        tCollab1.add(tbCreateRoom).colspan(2).row();
        tCollab1.add(tbJoinRoom).colspan(2).row();
        //tCollab1.add(clearLog).colspan(2).row();
        tCollab1.add(pushUpdateBtn).colspan(2).row();

        tCollab1.add(tbBack).colspan(2).row();
        tCollab1.add(lcollabstatus).colspan(2).row();

        tCollab2.add(new Label(z.netlog,skin)).colspan(2).row();
        tCollab2.add(netLog).height(btny*5).colspan(2).row();
        tCollab.add( tCollab1 );
        //tCollab.add( tCollab2 );
    }


    public void logNet(String s){
        try {
           // netLog.setText(netLog.getText()+ s +"\n");
           // netLog.setCursorPosition( netLog.getText().length() );
            lcollabstatus.setText( s );
            status(s,5);
        }catch(Exception e){}
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private void broadcast(String text){
        try {
            TextChat sr = new TextChat();
            sr.text=text;
            server.sendToAllTCP(sr);

        }catch(Exception e){}
    }

    private void talktoserver(String text){
        try {
            TextChat request = new TextChat();
            request.text = text;
            client.sendTCP(request);
        }catch(Exception e){}
    }

    private void slowdown(){
        while(client.getTcpWriteBufferSize()>1024){
            try {
                sleep( 20 ); //20
            }catch(Exception e){}
        }
    }

    private void uploaddata(layerhistory lh){
        try {
            if (isClient && (isCreateRoom || isJoinRoom)) {
                command cc = new command();
                cc.command = "draw";
                cc.from = myID;
                cc.room = activeRoom;
                cc.lh = lh;
                slowdown();
                client.sendTCP(cc);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void pushdata(packet lh){
        try {
            server.sendToAllTCP(lh);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void stopServer() {
        try {
            server.stop();
            isServer = false;
            tbHost.setText(z.host);
            activeClients.clear();
            logNet("Server stopped");
         }catch(Exception e){}
    }

        private void stopClient() {
            try {
                if (isCreateRoom) {
                    destroyRoom();
                }
                if (isJoinRoom) {
                    leaveRoom();
                }

                command cc = new command();
                cc.command="disconnect";
                cc.from=myID;
                client.sendTCP( cc );
                client.close();
                client.stop();

                isClient = false;
                isCreateRoom = false;
                isJoinRoom = false;
                activeRoom = "";
                tbCreateRoom.setText(z.createroom);
                tbJoinRoom.setText(z.joinroom);
                tbJoin.setText( z.join );
                logNet("Client disconnected" );



            } catch (Exception e) {
            }
        }


    private void destroyRoom() {
        try {
            //server.stop();
            command c = new command();
            c.from = myID;
            c.room = roomName.getText();
            c.command = "destroyRoom";
            c.data = "";
            client.sendTCP(c);



        }catch(Exception e){}
    }

    private void leaveRoom() {
        try {
            //client.stop();

            logNet("Requesting to leave " + activeRoom);

            command c = new command();
            c.from = myID;
            c.room = activeRoom;
            c.command = "leaveRequest";
            c.data = "";
            client.sendTCP(c);

        } catch (Exception e) {
        }
    }

    private void createRoom() {
        try {
            //server.stop();
            if (!isClient){
                logNet("Not connected to server0" );
                return;
            }
            if (isJoinRoom){
                logNet("Cannot create, leave room first.");
                return;
            }
            if (roomName.getText().equalsIgnoreCase( "" )){
                logNet("Please enter room name.");
                return;
            }
            command c = new command();
            c.from = myID;
            c.room = roomName.getText();
            c.command = "createRoom";
            c.data = "";
            client.sendTCP(c);

        }catch(Exception e){}
    }

    private void joinRoom() {
        try {
            //client.stop();

            if (!isClient){
                logNet("Not connected to server");
                return;
            }

            if (isCreateRoom){
                logNet("Cannot join, destroy room first.");
                return;
            }

            if (roomName.getText().equalsIgnoreCase( "" )){
                logNet("Please enter room name.");
                return;
            }

            logNet("Requesting to join: " + roomName.getText());
            command c = new command();
            c.from = myID;
            c.command = "joinRequest";
            c.room = roomName.getText();
            client.sendTCP(c);

        } catch (Exception e) {
        }
    }

    private void registerID(){
        command c = new command();
        c.command = "registerID";
        c.data = uniqueID.getText() + "_" + Integer.toString((int) (Math.random() * 10000000)) ;
        myID=c.data;
        client.sendTCP(c);
    }

    private void checkConnectionStatus() {
        try {
            if (isClient){
                if (!client.isConnected()) stopClient();
            }
        } catch (Exception e) {
        }
    }

    private void runServer(int port){
        try {
            if (port <=0){
                logNet("Please enter port number.");
                return;
            }
            server.start();
            server.bind(port, 54777);
            if (mygame !=null){
                //mygame.server = server;
            }


            logNet("Server started on port :"+Integer.toString( port ));
            logNet(z.listeningon+" "+getLocalIpAddress());
            isServer = true;
            tbHost.setText(z.stopserver);
            localIP = getLocalIpAddress();
            tfRemoteIP.setText(getLocalIpAddress());


        }catch(Exception e){}
    }

    private void runClient(String aipi, int portNum){
        try {
            if (portNum <=0){
                logNet("Please enter port number.");
                return;
            }

            client.start();
            client.connect(5000, aipi, portNum , 54777);
            if (mygame !=null){
               // mygame.client = client;
            }



            lcollabstatus.setText(z.status+": "+z.connectedto + " "+aipi);
            logNet("Client connected to :"+aipi+":"+Integer.toString(portNum));
            tbJoin.setText( z.disconnectclient);
            isClient=true;
            localIP = getLocalIpAddress();
            registerID();


        }catch(Exception e){
            logNet(z.failedtoconnect);
            tbJoin.setText( z.join );
            isClient=false;

        }
    }

    public static class TextChat extends packet{
        public String text;
    }

    public static class command extends packet{
        public String from="";
        public String room="";
        public String command="";
        public String data="";
        public layerhistory lh=null;

    }

    public static class PlayerState extends packet{
        public float posx;
        public float posy;
    }

    public static class actvClients extends packet{
        public String id;
        public String room;
        public boolean creator;
    }

    private void setTsetFromCurspr(){
        for (int ii=0; ii<tilesets.size();ii++){
            tileset ts = tilesets.get(ii);
            if (this.curspr >= ts.getFirstgid() && this.curspr < ts.getFirstgid()+ts.getTilecount()){
                seltset= ii;
                break;
            }
        }

    }

    private int getTsetFromSpr(int i){
        for (int ii=0; ii<tilesets.size();ii++){
            tileset ts = tilesets.get(ii);
            if (i >= ts.getFirstgid() && i < ts.getFirstgid()+ts.getTilecount()){
                return ii;
            }
        }

        return -1;
    }

    private void adjustLayer(tileset ts){
        for (property p:ts.getProperties()){
            if (p.getName().equalsIgnoreCase( "layer" )){
                for (int l=0; l<layers.size();l++){
                    if (layers.get(l).getName().equalsIgnoreCase( p.getValue() )){
                        selLayer=l;
                        updateObjectCollision();

                        switch (viewMode) {
                            case STACK:
                            case SINGLE:
                                resetCaches();
                                break;
                        }

                        break;
                    }
                }
            }
        }

    }
    private void adjustTileset(){
        for (tileset t : tilesets){
            for (property p:t.getProperties()){

                if (layers.get(selLayer).getName().equalsIgnoreCase( p.getValue() )){
                    if (t.getTerrains().size()>0){
                        for (tile tl: t.getTiles()){
                            if (tl.isTerrain() && tl.isCenter()){
                                curspr= tl.getTileID()+t.getFirstgid();
                                break;
                            }
                        }

                    }else{
                        curspr=t.getFirstgid();
                    }
                    setTsetFromCurspr();
                    recenterpick();
                    return;
                }
            }
        }
    }

    private void updateObjectCollision(){
        if (layers.size()==0) return;
       // if (layers.get(selLayer).getType()!=layer.Type.OBJECT) return;

           // for (layer l: layers){
           // if (l.getType()==layer.Type.OBJECT){

                com.badlogic.gdx.utils.Array<Body> bds = new com.badlogic.gdx.utils.Array<Body>();
                world.getBodies( bds );
                for ( Body bd : bds){
                    if (world.getBodyCount()==0) break;
                    world.destroyBody( bd );
                }
            //}
        //}

        for (obj ob : layers.get(selLayer).getObjects()){
            ob.updateVertices( world ,Tsh);
        }

    }
    private boolean tapWorldMenu(Vector3 touch2) {
        if (cammode == "View only") {
            if (tapped(touch2, gui.center)) {
                resetcam(true);
                cammode = "";
                return true;
            }



            if (tapped(touch2, gui.screenshot)) {
                takingss = true;
                //Gdx.input.vibrate(100);

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        //Gdx.input.vibrate(200);
                        screenshot();
                        cue("screenshot");
                        takingss = false;
                    }
                }, 1f);

            }
            return true;
        }

        //layer selection (top mid)
        if (tapped(touch2, gui.layer)) {
            if (layers.size()==0) return true;
            selLayer += 1;
            if (layers.size() == selLayer) {
                selLayer = 0;
            }

            if (layers.get(selLayer).getType() == layer.Type.TILE) {
                mode = "tile";
            } else if (layers.get(selLayer).getType() == layer.Type.OBJECT) {
                mode = "object";
            } else if (layers.get(selLayer).getType() == layer.Type.IMAGE) {
                mode = "image";
            }

            switch (viewMode){
                case STACK: case SINGLE:
                    resetCaches(); break;
            }

            updateObjectCollision();
            adjustTileset();

            return true;
        }

        if (tapped(touch2, gui.layerpick)) {
            if (mode == "tile" || mode == "object"|| mode == "image") {
                if (!softcue("layerpick") && lockUI) return true;
                loadList("layer");
                return true;
            }
        }

        if (tapped(touch2, gui.canceltutorial) && tutoring) {
            tutoring=false;
            return true;
        }

        if (tapped(touch2, gui.play)) {
            for (property p : properties) {
                if (p.getName().equalsIgnoreCase("type") && p.getValue().equalsIgnoreCase("NotTiled platformer")) {
                    saveMap(curdir + "/" + curfile);
                    playgame(curdir, curfile);
                    return true;
                }
                else if (p.getName().equalsIgnoreCase("type") && p.getValue().equalsIgnoreCase("NotTiled music")) {
                    saveMap(curdir + "/" + curfile);
                    playmusic(playback.PLAY,"");
                    return true;
                }
                else if (p.getName().equalsIgnoreCase("type") && p.getValue().equalsIgnoreCase("Pixel Editor")) {
                    rotating=!rotating;
                    return true;
                }
                else if (p.getName().equalsIgnoreCase("tag") && p.getValue().equalsIgnoreCase("RW")) {
                    copyToRW();
                    return true;
                }

            }
        }

        if (tapped(touch2, gui.minimap) && sMinimap) {
            if (mode == "tile" || mode == "object" || mode == "image") {
                cacheTiles();
                //updateMinimap(); new minimap, worse.
                Vector3 ve = new Vector3();
                minicam.unproject(ve.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                //Direct designation
                //cam.position.set(ve.x,ve.y,ve.z);
                //cam.update();

                if (orientation.equalsIgnoreCase("isometric")) {
                    if (!(ve.x>=-Tsw * Tw / 2 && ve.x<=Tsw * Tw / 2)) return true;
                    if (!(ve.y<=0 && ve.y>=-Tsh * Th)) return true;
                }else{
                    if (!(ve.x>=0 && ve.x<=Tsw * Tw)) return true;
                    if (!(ve.y<=0 && ve.y>=-Tsh * Th)) return true;
                }
                panTo(ve.x,ve.y, cam.zoom, .5f);

                /*stupid new minimap
                int onset = 0;
                if (orientation.equalsIgnoreCase("isometric")) {
                    onset = Tsw * Tw / 2;
                }
                if (!landscape){
                    float eiys = (ve.y-gui.minimap.getY()/100f*ssy)/((gui.minimap.getH()-gui.minimap.getY())/100f*ssy);
                    float eiks = (ve.x-gui.minimap.getX()/100f*ssx)/((gui.minimap.getW()-gui.minimap.getX())/100f*ssx);
                    panTo((eiks)*Tw*Tsw, eiys*Th*Tsh-(Th*Tsh), cam.zoom, .5f);

                }else{
                    float eiks = (ve.x-gui.minimap.getXl()/100f*ssy)/((gui.minimap.getWl()-gui.minimap.getXl())/100f*ssy);
                    float eiys = (ve.y-gui.minimap.getYl()/100f*ssx)/((gui.minimap.getHl()-gui.minimap.getYl())/100f*ssx);
                    panTo((eiks)*Tw*Tsw, eiys*Th*Tsh-(Th*Tsh), cam.zoom, .5f);

                }

                 */
                return true;
            }
        }

        //tile/object selector (top left)
        if (tapped(touch2, gui.mode)) {
            gotoStage(tLayerNew);
            return true;
        }

        //useless eyesore
        if (tapped(touch2, gui.center)) {
            //resetcam(true);
            //runServer();
            //return true;
        }

        if (autotiles.size() > 0) {
            //autotile
            if (tapped(touch2, gui.autotile)) {
                if (mode == "tile") {
                    loadList("macro");
                    cacheTiles();
                    return true;
                }
            }

            //select auto tile
            if (tapped(touch2, gui.autopicker)) {
                if (mode == "tile") {
                    if (!softcue("autopick") && lockUI) return true;
                    loadAutotileList();
                    cue("autopick");
                    return true;
                }
            }
        }
        if (mode == "tile") {

            if (activetool==3 && !stamp && !assemblymode) {
                if (tapped( touch2, gui.addmacro )) {
                   if (mapstartSelect==mapendSelect) return true;
                    Gdx.input.getTextInput(pAddMacro, z.addnew+" :"+z.macro, "", "");
                   return true;
                }
            }

            if (swatches) {
                if (tapped( touch2, gui.sw1 )) {
                    if (tilesets.isEmpty()) return true;
                    if (swatchValue.get( 0 ) == 0) {
                        pickTile( "sw1" );
                    } else {
                        curspr = swatchValue.get( 0 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }

                if (tapped( touch2, gui.sw2 )) {
                    if (tilesets.isEmpty()) return true;

                    if (swatchValue.get( 1 ) == 0) {
                        pickTile( "sw2" );
                    } else {
                        curspr = swatchValue.get( 1 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }
                if (tapped( touch2, gui.sw3 )) {
                    if (tilesets.isEmpty()) return true;

                    if (swatchValue.get( 2 ) == 0) {
                        pickTile( "sw3" );
                    } else {
                        curspr = swatchValue.get( 2 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }
                if (tapped( touch2, gui.sw4 )) {
                    if (tilesets.isEmpty()) return true;

                    if (swatchValue.get( 3 ) == 0) {
                        pickTile( "sw4" );
                    } else {
                        curspr = swatchValue.get( 3 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }
                if (tapped( touch2, gui.sw5 )) {
                    if (tilesets.isEmpty()) return true;

                    if (swatchValue.get( 4 ) == 0) {
                        pickTile( "sw5" );
                    } else {
                        curspr = swatchValue.get( 4 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }
                if (tapped( touch2, gui.sw6 )) {
                    if (tilesets.isEmpty()) return true;

                    if (swatchValue.get( 5 ) == 0) {
                        pickTile( "sw6" );
                    } else {
                        curspr = swatchValue.get( 5 );
                        setTsetFromCurspr();
                    }
                    adjustLayer( tilesets.get( seltset ) );
                    stamp = false;
                    assemblymode=false;

                    return true;
                }
            }

            if (tapped(touch2, gui.tool1)) {
                if (!cue("tool1") && lockUI) return true;
                activetool = 0;
                stamp = false;
                assemblymode=false;
                return true;
            }
            if (tapped(touch2, gui.tool2)) {
                if (!cue("tool2") && lockUI) return true;
                eraser = !eraser;
                //activetool = 1;
                stamp = false;
                assemblymode=false;
                return true;
            }
            if (tapped(touch2, gui.tool3)) {
                if (!cue("tool3") && lockUI) return true;
                activetool = 2;
                stamp = false;
                assemblymode=false;
                return true;
            }
            if (tapped(touch2, gui.tool4)) {
                if (!cue("tool4") && lockUI) return true;
                activetool = 3;

                stamp = false;
                assemblymode=false;
                return true;
            }
            if (tapped(touch2, gui.tool5)) {
                if (!cue("tool5") && lockUI) return true;
                activetool = 4;
                updateAT();
                //celorit
                //brushsize=1;
                stamp = false;
                assemblymode=false;
                return true;
            }
        }

        //tool selector (btm right)
        if (tapped(touch2, gui.lock)) {

            if (mode == "object") {
                magnet += 1;
                if (magnet >= 2) magnet = 0;
                String nfo = "0";
                switch (magnet) {
                    case 0:
                        nfo = z.free;
                        break;
                    case 1:
                        nfo = z.lock;
                        break;
                }
                magnetName = nfo;
                return true;
            }
        }

        if (tapped(touch2, gui.tool)) {

            if (mode == "newpoly") {
                if (newobject.getPointsSize() == 1) {
                    newobject.setShape("point");
                }
                if (newobject.getPointsSize() == 2) {
                    newobject.setShape("polyline");
                }
                newobject.updateVertices( world,Tsh );
                mode = "object";
                return true;
            }

        }


        //undo
        if (tapped(touch2, gui.undo)) {


            if (mode == "tile") {
                try {
                    if (!cue("undo") && lockUI) return true;
                    if (undolayer.size() > 0) {
                        layerhistory lh;
                        long histcount = 0;
                        for (int n = undolayer.size() - 1; n >= 0; n--) {

                            lh = undolayer.get(n);
                            layers.get(lh.getLayer()).getStr().set(lh.getLocation(), lh.getFrom());
                            layers.get(lh.getLayer()).getTset().set(lh.getLocation(), lh.getoldTset());
                            updateCache(lh.getLocation());
                            redolayer.add(lh);
                            lh.undo=true;
                            uploaddata(lh);

                            histcount++;
                            if (!lh.isFollower()) {
                                java.util.List<layerhistory> templist = new ArrayList<layerhistory>();

                                for (int t = 0; t < undolayer.size() - histcount; t++) {
                                    templist.add(undolayer.get(t));
                                }
                                undolayer = new ArrayList<layerhistory>();
                                undolayer = templist;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    ErrorBung(e, "undo.txt");
                }
                //updateMinimap();
                //if (sMinimap) cacheTiles();
                return true;
            }
            if (mode == "newpoly") {

                newobject.undoPoint();
                return true;
            }

        }

        //redo
        if (tapped(touch2, gui.redo)) {

            if (mode == "tile") {
                if (!cue("redo") && lockUI) return true;
                if (redolayer.size() > 0) {
                    boolean pertamax = true;
                    long histcount = 0;
                    for (int n = redolayer.size() - 1; n >= 0; n--) {

                        layerhistory lh = redolayer.get(n);

                        if (!pertamax) {
                            if (!lh.isFollower()) {

                                java.util.List<layerhistory> templist = new ArrayList<layerhistory>();

                                for (int t = 0; t < redolayer.size() - histcount; t++) {
                                    templist.add(redolayer.get(t));
                                }
                                redolayer = new ArrayList<layerhistory>();
                                redolayer = templist;


                                break;
                            }
                        }
                        pertamax = false;
                        layers.get(lh.getLayer()).getStr().set(lh.getLocation(), lh.getTo());
                        layers.get(lh.getLayer()).getTset().set(lh.getLocation(), lh.getNewtset());
                        updateCache(lh.getLocation());

                        //status(lh.getTset()+"",5);
                        undolayer.add(lh);
                        uploaddata(lh);
                        histcount++;
                        //redolayer.remove(lh);
                    }
                }
                //updateMinimap();
                return true;
            }

        }

        //view mode selector (top roght)
        if (tapped(touch2, gui.viewmode)) {
            switch (viewMode)
            {
                case STACK:
                    viewMode=ViewMode.SINGLE;
                    break;
                case SINGLE:
                    viewMode=ViewMode.ALL;
                    break;
                case ALL:
                    viewMode=ViewMode.CUSTOM;
                    break;
                case CUSTOM:
                    viewMode=ViewMode.STACK;
                    break;
            }
            resetCaches();
            saveViewMode();
            return true;
        }

        //select tile button
        if (tapped(touch2, gui.picker)) {
            if (mode == "tile") {
                if (!cue("tilepick") && lockUI) return true;
                adjustPickAuto();

                stamp = false;
                kartu = "tile";
                tilePicker = "";
                return true;
            }


        }

        if (tapped(touch2, gui.objectpickermid)) {
            if (mode == "image") {
                fillImageLayerData();
                gotoStage(tImageLayer);

            }
            return true;
        }
        //object tool selector
        if (tapped(touch2, gui.objectpickerleft)) {
            if (mode == "object") {
                activeobjtool -= 1;
                if (activeobjtool < 0) activeobjtool = 8;
                String nfo = "";
                switch (activeobjtool) {
                    case 0:
                        nfo = z.rectangle;
                        break;
                    case 1:
                        nfo = z.ellipse;
                        break;
                    case 2:
                        nfo = z.point;
                        break;
                    case 3:
                        nfo = z.polygon;
                        break;
                    case 4:
                        nfo = z.polyline;
                        break;
                    case 5:
                        nfo = z.text;
                        break;
                    case 6:
                        nfo = z.image;
                        break;
                    case 7:
                        nfo = z.copypaste;
                        break;
                    case 8:
                        nfo = z.eraser;
                        break;
                }
                shapeName = nfo;
                return true;
            }
        }

        if (tapped(touch2, gui.objectpickerright)) {
            if (mode == "object") {

                if (touch2.x > 240 - 30) activeobjtool += 1;
                if (activeobjtool >= 9) activeobjtool = 0;
                String nfo = "";
                switch (activeobjtool) {
                    case 0:
                        nfo = z.rectangle;
                        break;
                    case 1:
                        nfo = z.ellipse;
                        break;
                    case 2:
                        nfo = z.point;
                        break;
                    case 3:
                        nfo = z.polygon;
                        break;
                    case 4:
                        nfo = z.polyline;
                        break;
                    case 5:
                        nfo = z.text;
                        break;
                    case 6:
                        nfo = z.image;
                        break;
                    case 7:
                        nfo = z.copypaste;
                        break;
                    case 8:
                        nfo = z.eraser;
                        break;
                }
                shapeName = nfo;
                return true;
            }
        }
        //rotation
        if (tapped(touch2, gui.rotation)) {
            if (mode == "tile") {

                rotate += 1;
                if (rotate == 8) {
                    rotate = 0;
                }
                String nfo = "0";
                switch (rotate) {
                    case 0:
                        nfo = "0";
                        break;
                    case 1:
                        nfo = "90";
                        break;
                    case 2:
                        nfo = "180";
                        break;
                    case 3:
                        nfo = "270";
                        break;
                    case 4:
                        nfo = "0*";
                        break;
                    case 5:
                        nfo = "90*";
                        break;
                    case 6:
                        nfo = "180*";
                        break;
                    case 7:
                        nfo = "270*";
                        break;
                }
                rotationName = nfo;
                return true;
            }

        }

        if (tapped(touch2, gui.menu)) {
            if (mode == "object" || mode == "tile"|| mode == "image") {
                setMenuMap();
                gotoStage(tMenu);
                cue("menu");
                return true;

            }

        }

        if (tapped(touch2, gui.save)) {
            if (mode == "object" || mode == "tile"|| mode == "image") {

                try {
                    autosavePool.execute( new Runnable(  ){
                        @Override
                        public void run() {
                            saveMap( curdir + "/" + curfile );
                            status(z.yourmaphasbeensaved, 2);
                            cue("quicksave");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;

            }
        }

        if (tapped(touch2, gui.map)) {
            if (mode == "object" || mode == "tile"|| mode == "image") {
                setMapMap();
                gotoStage(tMap);
                cue("map");
                return true;
            }

        }
        return false;
    }

    gui selectedGUI;
    boolean iseditGUI;

    private void editGUI(gui g){
        selectedGUI= g;
        iseditGUI=true;
    }

    private boolean tapEditorActions(Vector3 touch2) {
        if (tapped(touch2, gui.editorcancel)) {
            if (sCustomUI){
                loadInterface("custominterface.json");
            }else{
                guis gsu = new guis();
                gui = gsu;
            }

            backToMap();
            return true;
        }
        if (tapped(touch2, gui.editorsave)) {
            sCustomUI = true;
            prefs.putBoolean("customui", sCustomUI).flush();
            guis at = new guis();
            at=gui;
            Json json = new Json();
            writeThisAbs(Gdx.files.getExternalStoragePath()+ "NotTiled/sample/"+"custominterface.json", json.prettyPrint(at));
            msgbox(z.saved);

            backToMap();
            return true;
        }
        if (tapped(touch2, gui.editormode)) {
            switch(mode){
                case "tile":
                    mode="object";
                    break;
                case "object":
                    mode="image";
                    break;
                case "image":
                    mode="pick";
                    break;
                case "pick":
                    mode="tile";
                    break;
            }

            return true;
        }

        if (selectedGUI==null) return true;

        if (tapped(touch2, gui.editorleft)) {
            if (landscape){
                selectedGUI.wl -=1;
            }else{
                selectedGUI.w -=1;
            }
            return true;
        }
        if (tapped(touch2, gui.editorright)) {
            if (landscape){
                selectedGUI.wl +=1;
            }else{
                selectedGUI.w +=1;
            }
            return true;
        }
        if (tapped(touch2, gui.editorup)) {
            if (landscape){
                selectedGUI.yl -=1;
            }else{
                selectedGUI.y -=1;
            }
            return true;
        }
        if (tapped(touch2, gui.editordown)) {
            if (landscape){
                selectedGUI.yl +=1;
            }else{
                selectedGUI.y +=1;
            }
            return true;
        }

        return false;
    }

    private boolean tapEditorMenu(Vector3 touch2) {
        if (cammode == "View only") {
            if (tapped(touch2, gui.center)) {
                resetcam(true);
                cammode = "";
                return true;
            }



            if (tapped(touch2, gui.screenshot)) {
                takingss = true;
                //Gdx.input.vibrate(100);

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        //Gdx.input.vibrate(200);
                        screenshot();
                        cue("screenshot");
                        takingss = false;
                    }
                }, 1f);

            }
            return true;
        }

        if (mode=="pick"){

            if (tapped( touch2, gui.tilesetsmid )) {
                editGUI( gui.tilesetsmid );
                return true;
            }
            if (tapped( touch2, gui.tilesetsleft )) {
                editGUI( gui.tilesetsleft );
                return true;
            }
            if (tapped( touch2, gui.tilesetsright )) {
                editGUI( gui.tilesetsright );
                return true;
            }
            if (tapped( touch2, gui.pickerback )) {
                editGUI( gui.pickerback );
                return true;
            }

            if (tapped( touch2, gui.pickertool1 )) {
                editGUI( gui.pickertool1 );
                return true;
            }
            if (tapped( touch2, gui.pickertool2 )) {
                editGUI( gui.pickertool2 );
                return true;
            }
            if (tapped( touch2, gui.pickertool3 )) {
                editGUI( gui.pickertool3 );
                return true;
            }
            if (tapped( touch2, gui.pickertool4 )) {
                editGUI( gui.pickertool4 );
                return true;
            }
            if (tapped( touch2, gui.pickertool5 )) {
                editGUI( gui.pickertool5 );
                return true;
            }
            if (tapped( touch2, gui.tilewrite )) {
                editGUI( gui.tilewrite );
                return true;
            }
            if (tapped( touch2, gui.tilesettings )) {
                editGUI( gui.tilesettings );
                return true;
            }
            if (tapped( touch2, gui.tilesettings )) {
                editGUI( gui.tilesettings );
                return true;
            }
            if (tapped( touch2, gui.tilemode )) {
                editGUI( gui.tilemode );
                return true;
            }
            if (tapped( touch2, gui.tileproperties )) {
                editGUI( gui.tileproperties );
                return true;
            }
            if (tapped( touch2, gui.tileremove )) {
                editGUI( gui.tileremove );
                return true;
            }
            if (tapped( touch2, gui.tileadd )) {
                editGUI( gui.tileadd );
                return true;
            }
            if (tapped( touch2, gui.tileoverlay )) {
                editGUI( gui.tileoverlay );
                return true;
            }
        }

        else {
            //layer selection (top mid)
            if (tapped( touch2, gui.layer )) {
                editGUI( gui.layer );
                return true;
            }

            if (tapped( touch2, gui.play )) {
                editGUI( gui.play );
                return true;
            }

            if (tapped( touch2, gui.addmacro )) {
                editGUI( gui.addmacro );
                return true;
            }

            if (tapped( touch2, gui.info )) {
                editGUI( gui.info );
                return true;
            }

            if (swatches) {
                if (tapped( touch2, gui.sw1 )) {
                    editGUI( gui.sw1 );
                    return true;
                }

                if (tapped( touch2, gui.sw2 )) {
                    editGUI( gui.sw2 );
                    return true;
                }
                if (tapped( touch2, gui.sw3 )) {
                    editGUI( gui.sw3 );
                    return true;

                }
                if (tapped( touch2, gui.sw4 )) {
                    editGUI( gui.sw4 );
                    return true;

                }
                if (tapped( touch2, gui.sw5 )) {
                    editGUI( gui.sw5 );
                    return true;

                }
                if (tapped( touch2, gui.sw6 )) {
                    editGUI( gui.sw6 );
                    return true;

                }
            }

            if (tapped( touch2, gui.layerpick )) {
                editGUI( gui.layerpick );
                return true;

            }


            if (tapped( touch2, gui.minimap ) && sMinimap) {
                editGUI( gui.minimap );
                return true;
            }

            //tile/object selector (top left)
            if (tapped( touch2, gui.mode )) {
                editGUI( gui.mode );
                return true;

            }


            //if (autotiles.size() > 0) {
                //autotile
                if (tapped( touch2, gui.autotile )) {
                    if (mode == "tile") {
                        editGUI( gui.autotile );
                        return true;

                    }
                }

                //select auto tile
                if (tapped( touch2, gui.autopicker )) {
                    if (mode == "tile") {
                        editGUI( gui.autopicker );
                        return true;
                    }
                }
           // }
            if (mode == "tile") {
                if (tapped( touch2, gui.tool1 )) {
                    editGUI( gui.tool1 );
                    return true;

                }
                if (tapped( touch2, gui.tool2 )) {
                    editGUI( gui.tool2 );
                    return true;

                }
                if (tapped( touch2, gui.tool3 )) {
                    editGUI( gui.tool3 );
                    return true;

                }
                if (tapped( touch2, gui.tool4 )) {
                    editGUI( gui.tool4 );
                    return true;

                }
                if (tapped( touch2, gui.tool5 )) {
                    editGUI( gui.tool5 );
                    return true;

                }
            }

            //tool selector (btm right)
            if (tapped( touch2, gui.lock )) {

                if (mode == "object") {
                    editGUI( gui.lock );
                    return true;

                }
            }

            if (tapped( touch2, gui.tool )) {

                if (mode == "newpoly") {
                    editGUI( gui.tool );
                    return true;

                }

            }


            //undo
            if (tapped( touch2, gui.undo )) {


                if (mode == "tile") {
                    editGUI( gui.undo );
                    return true;
                }
                if (mode == "newpoly") {
                    editGUI( gui.undo );
                    return true;
                }

            }

            //redo
            if (tapped( touch2, gui.redo )) {

                if (mode == "tile") {
                    editGUI( gui.redo );
                    return true;
                }

            }

            //view mode selector (top roght)
            if (tapped( touch2, gui.viewmode )) {
                editGUI( gui.viewmode );
                return true;
            }

            //select tile button
            if (tapped( touch2, gui.picker )) {
                if (mode == "tile") {
                    editGUI( gui.picker );
                    return true;
                }


            }

            if (tapped( touch2, gui.objectpickermid )) {
                if (mode == "image" || mode=="object") {
                    editGUI( gui.objectpickermid );
                    return true;
                }
            }

            //object tool selector
            if (tapped( touch2, gui.objectpickerleft )) {
                if (mode == "object") {
                    editGUI( gui.objectpickerleft );
                    return true;
                }
            }

            if (tapped( touch2, gui.objectpickerright )) {
                if (mode == "object") {
                    editGUI( gui.objectpickerright );
                    return true;
                }
            }
            //rotation
            if (tapped( touch2, gui.rotation )) {
                if (mode == "tile") {

                    editGUI( gui.rotation );
                    return true;
                }

            }

            if (tapped( touch2, gui.menu )) {
                if (mode == "object" || mode == "tile" || mode == "image") {
                    editGUI( gui.menu );
                    return true;

                }

            }

            if (tapped( touch2, gui.save )) {
                if (mode == "object" || mode == "tile" || mode == "image") {
                    editGUI( gui.save );
                    return true;

                }
            }

            if (tapped( touch2, gui.map )) {
                if (mode == "object" || mode == "tile" || mode == "image") {
                    editGUI( gui.map );
                    return true;
                }

            }

            if (tapped( touch2, gui.fps )) {
                if (mode == "object" || mode == "tile" || mode == "image") {
                    editGUI( gui.fps );
                    return true;
                }

            }

            if (tapped( touch2, gui.status )) {
                if (mode == "object" || mode == "tile" || mode == "image") {
                    editGUI( gui.status );
                    return true;
                }

            }
        }
        return false;
    }

    private boolean cue(String p0) {
        if (tutoring) {
            if (tutor.getT().get(activetutor).checkcue(p0) == true) {
                //face.speak(tutor.getT().get(activetutor).getCurrent());



                if (tutor.getT().get(activetutor).isEnded()) {
                    tutoring = false;
                }
                else{
                    msgbox(tutor.getT().get(activetutor).getCurrent());
                }


                tutor.getT().get(activetutor).next();
                if (tutor.getT().get(activetutor).getCurrentCue().equalsIgnoreCase("changebg")) {
                    try {
                        background = new Texture(Gdx.files.absolute(basepath+tutor.getT().get(activetutor).getCurrent()));
                    }catch(Exception e){}
                    tutor.getT().get(activetutor).next();
                }
                if (tutor.getT().get(activetutor).getCurrentCue().equalsIgnoreCase("lockUI")) {
                    lockUI=true;
                    tutor.getT().get(activetutor).next();
                }
                if (tutor.getT().get(activetutor).getCurrentCue().equalsIgnoreCase("settilesize")) {
                    fNTsh.setText(tutor.getT().get(activetutor).getCurrent());
                    fNTsw.setText(tutor.getT().get(activetutor).getCurrent());
                    tutor.getT().get(activetutor).next();
                }
                if (tutor.getT().get(activetutor).getCurrentCue().equalsIgnoreCase("setmapsize")) {
                    fNTh.setText(tutor.getT().get(activetutor).getCurrent());
                    fNTw.setText(tutor.getT().get(activetutor).getCurrent());
                    tutor.getT().get(activetutor).next();
                }
                if (tutor.getT().get(activetutor).getCurrentCue().equalsIgnoreCase("unlockUI")) {
                    lockUI=false;
                    tutor.getT().get(activetutor).next();
                }

                if (tutor.getT().get(activetutor).isEnded()) {
                    tutoring = false;
                }
                return true;
            }else{
                //msgbox(tutor.getT().get(activetutor).getPrevious());
                return false;
            }
        }
        return true;

    }

    private void adjustPickAuto(){
        if (tilesets.size()>0){
            if (tilesets.get(seltset).getTerrains().size() >0) {
                for (property p : tilesets.get( seltset ).getProperties()) {
                    if (p.getName().equalsIgnoreCase( "forced_autotile" )) {
                        pickAuto = true;
                        recenterpick();
                        break;
                    }
                    if (p.getName().equalsIgnoreCase( "forced_tile" )) {
                        pickAuto = false;
                        recenterpick();
                        break;
                    }
                }
            }else {
                pickAuto=false;
            }
        }
    }
    private boolean softcue(String p0) {
        if (tutoring) {
            if (tutor.getT().get(activetutor).checkcue(p0) == true) {
                return true;
            }else{
                return false;
            }
        }
        return true;

    }

    private Color vis(String p0) {
        if (tutoring) {
            if (tutor.getT().get(activetutor).checkcue(p0) == true) {
                return new Color(0f, 1f, 0f, 1f);
            }
        }
        return null;
    }

    private void screenshot() {


        //status("screenshot?",5);
        int ax = 1, ay = Tsh, az = 0;
        int bx = Tw * Tsw, by = Th * Tsh + Tsh - 1, bz = 0;
        Vector3 va = new Vector3(ax, ay, az);
        Vector3 vb = new Vector3(bx, by, bz);
        Vector3 upva = cam.project(va);
        Vector3 upvb = cam.project(vb);


        FileHandle fh = Gdx.files.absolute(curdir + "/" + curfile.substring(0, curfile.length() - 4) + "_map.png");
        Pixmap data = getScreenshot((int) upva.x, (int) upva.y - (int) (upvb.y - upva.y), (int) (upvb.x - upva.x), (int) (upvb.y - upva.y), true);
        //if (layers.size()>2 && tilesets.size()>0){
        //if (layers.get(2).getName().equalsIgnoreCase("Units") && tilesets.get(0).getName().equalsIgnoreCase("units")){
        //data = resizepixmap(data,Tsw*Tw,Tsh*Th);
        //}}
        PixmapIO.writePNG(fh, data);

        status(z.screenshotsaved,2);
    }

    public Pixmap resizepixmap(Pixmap pixmap200, int w, int h) {
        Pixmap pixmap100 = new Pixmap(w, h, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        pixmap200.dispose();
        return pixmap100;
    }

    public Texture getScreenshot2(){

        Texture texture = new Texture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGB888);

        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        texture.bind();
        Gdx.gl.glCopyTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGB, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
        Gdx.gl.glDisable(GL20.GL_TEXTURE_2D);

        return texture;
    }
    public Pixmap getScreenshot(int ox, int oy, int ow, int oh, boolean flipY) {
        int bw = Gdx.graphics.getBackBufferWidth();
        int bh = Gdx.graphics.getBackBufferHeight();
        int x = ox;
        int y = oy;
        int w = ow;
        int h = oh;

        if (x < 0) {
            x = 0;
            w = w + x;
        }
        if (w + x > bw) w = bw - x;
        if (y < 0) {
            y = 0;
            h = h + y;
        }
        if (y + h > bh) h = bh - y;

        //status(x+"!"+y+"!"+w+"!"+h+"!"+bw+"!"+bh,10);
        byte[] pixels = ScreenUtils.getFrameBufferPixels(x, y, w, h, flipY);

        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        return pixmap;
    }

    public void runAutoTiles(String name) {
        String currentName="";
        int autoundolayer = 1;
        autoed.clear();
        for (int i = 0; i < Tw * Th; i++) {
            autoed.add(false);
        }

        snapWholeMapPhase1(0);
		/* //This code works but slow.
		java.util.List<layer> prevlayers = new ArrayList<layer>();
		java.util.List<layer> nextlayers = new ArrayList<layer>();

		for (int j=0;j<autoundolayer;j++)
		{
			Json json = new Json();
			String myclip; layer mylayer;
			myclip=json.toJson(layers.get(j));
			mylayer = json.fromJson(layer.class, myclip);
		}
		*/
        for (int i = 0; i < autotiles.size(); i++) {
            autotile al = autotiles.get(i);
            automate am = new automate();
            am.setObjectfill("null");
            am.setType("null");
            for (int j = 0; j < al.getProperties().size(); j++) {

                property p = al.getProperties().get(j);
                if (p.getName().equalsIgnoreCase("type")) {
                    am.setType(p.getValue());
                    continue;
                } else if (p.getName().equalsIgnoreCase("name")) {
                    currentName=p.getValue();
                    continue;
                } else if (p.getName().equalsIgnoreCase("source")) {
                    am.setSource(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("hit")) {
                    am.setHit(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("x")) {
                    am.setPosx(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("y")) {
                    am.setPosy(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("width")) {
                    am.setWidth(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("height")) {
                    am.setHeight(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against")) {
                    am.setAgainst(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against2")) {
                    am.setAgainst2(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against3")) {
                    am.setAgainst3(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against4")) {
                    am.setAgainst4(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against5")) {
                    am.setAgainst5(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("against6")) {
                    am.setAgainst6(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("beside")) {
                    am.setBeside(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("sourcelayer")) {
                    am.setSourcelayer(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("destlayer")) {
                    am.setDestlayer(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("objectfill")) {
                    am.setObjectfill(p.getValue());
                    continue;
                } else if (p.getName().equalsIgnoreCase("objectgroup")) {
                    am.setObjectgroup(Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("objectname")) {
                    am.setObjectname(p.getValue());
                    continue;
                } else if (p.getName().equalsIgnoreCase("bottomright")) {
                    am.setCell(3, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bottomleft")) {
                    am.setCell(5, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("topright")) {
                    am.setCell(10, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("topleft")) {
                    am.setCell(12, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bottom")) {
                    am.setCell(7, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("right")) {
                    am.setCell(11, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("left")) {
                    am.setCell(13, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("top")) {
                    am.setCell(14, Integer.parseInt(p.getValue()));
                    continue;
                }
                //////
                else if (p.getName().equalsIgnoreCase("topwest")) {
                    am.setCell(31, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("topeast")) {
                    am.setCell(32, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("leftnorth")) {
                    am.setCell(33, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("leftsouth")) {
                    am.setCell(34, Integer.parseInt(p.getValue()));
                    continue;
                }
                //////
                else if (p.getName().equalsIgnoreCase("rightnorth")) {
                    am.setCell(35, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("rightsouth")) {
                    am.setCell(36, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bottomwest")) {
                    am.setCell(37, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bottomeast")) {
                    am.setCell(38, Integer.parseInt(p.getValue()));
                    continue;
                }
                //////
                else if (p.getName().equalsIgnoreCase("center")) {
                    am.setCell(15, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bigtopleft")) {
                    am.setCell(16, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bigtopright")) {
                    am.setCell(17, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bigbottomleft")) {
                    am.setCell(19, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("bigbottomright")) {
                    am.setCell(23, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("topleftbottomright")) {
                    am.setCell(20, Integer.parseInt(p.getValue()));
                    continue;
                } else if (p.getName().equalsIgnoreCase("toprightbottomleft")) {
                    am.setCell(25, Integer.parseInt(p.getValue()));
                    continue;
                }
                try {
                    am.setCell(Integer.parseInt(p.getName()), Integer.parseInt(p.getValue()));
                    continue;
                } catch (Exception e) {
                }
                try {
                    String[] sdc = p.getName().split(",");
                    am.addCoord(Integer.parseInt(sdc[0]), Integer.parseInt(sdc[1]), Integer.parseInt(p.getValue()));
                } catch (Exception e) {
                }

            }//properties run

            if (currentName!=name) continue;
            int repNum=1;
            switch (am.getType()) {
                case "clearobjects":
                    try {
                        layers.get(am.getObjectgroup()).getObjects().clear();
                    } catch (Exception e) {
                    }
                    break;
                case "hit":
                    selLayer=am.getDestlayer();
                    curspr=am.getSource();
                    setTsetFromCurspr();
                    int px=0;
                    int py=0;
                    int wi=100;
                    int he=100;
                    if (am.getPosx()!=-1){
                        px= am.getPosx();
                        wi = am.getWidth();
                    }
                    if (am.getPosy()!=-1){
                        py= am.getPosy();
                        he = am.getHeight();
                    }
                    for (int si=0; si<am.hit; si++){
                        //Gdx.app.log("SOSO",px+"");

                        int xpos = (int) ((px/100f*Tw) + (Math.random()*wi/100f*Tw));
                        int ypos = (int) (py/100f*Th) + (int) (Math.random()*he/100f*Th);

                        int nuhum = ypos*Tw+xpos;

                        tapTile(nuhum, (si==0) ,true, false,curspr);
                    }
                    break;
                case "fill":
                    for (int k = 0; k < Tw * Th; k++) {
                        layers.get(am.getDestlayer()).getStr().set(k, (long) am.getSource());
                        if (am.getSource()==0){
                            layers.get(am.getDestlayer()).getTset().set(k, -1);
                            break;
                        }
                        for (int l = 0; l < tilesets.size(); l++) {
                            if (am.getSource() >= tilesets.get(l).getFirstgid() && am.getSource() < tilesets.get(l).getFirstgid() + tilesets.get(l).getTilecount()) {
                                layers.get(am.getDestlayer()).getTset().set(k, l);
                                break;
                            }
                        }
                    }
                    break;
                case "cleanup":
                    //Fix the map before autotiling
                    for (int x=0;x<3;x++) {
                        for (int k = 0; k < Tw * Th; k++) {
                            int dong = 0;
                            long strs = -1;

                            long lcen = -1, lleft = -1, ltop = -1, lright = -1, lbottom = -1;
                            lcen = layers.get( am.getSourcelayer() ).getStr().get( k );
                            if (k - Tw >= 0)
                                ltop = layers.get( am.getSourcelayer() ).getStr().get( k - Tw );
                            if (k + Tw < Tw * Th)
                                lbottom = layers.get( am.getSourcelayer() ).getStr().get( k + Tw );
                            if (k - 1 >= 0 && k % Tw != 0)
                                lleft = layers.get( am.getSourcelayer() ).getStr().get( k - 1 );
                            if (k + 1 < Tw * Th && k % Tw != Tw - 1)
                                lright = layers.get( am.getSourcelayer() ).getStr().get( k + 1 );

                            int tgt = -1;

                            if (ltop != -1 && ltop != lcen) {
                                dong++;
                                strs = ltop;
                            }
                            if (lleft != -1 && lleft != lcen) {
                                dong++;
                                strs = lleft;
                            }
                            if (lright != -1 && lright != lcen) {
                                dong++;
                                strs = lright;
                            }
                            if (lbottom != -1 && lbottom != lcen) {
                                dong++;
                                strs = lbottom;
                            }

                            if (dong > 2) {
                                layers.get( am.getSourcelayer() ).getStr().set( k, strs );

                                for (int l = 0; l < tilesets.size(); l++) {
                                    if (strs >= tilesets.get( l ).getFirstgid() && strs < tilesets.get( l ).getFirstgid() + tilesets.get( l ).getTilecount()) {
                                        layers.get( am.getSourcelayer() ).getTset().set( k, l );
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "bitmask":


                    for (int k = 0; k < Tw * Th; k++) {
                        if (layers.get(am.getSourcelayer()).getStr().get(k) != am.getSource())
                            continue;
                        int count = 0;
                        int total = Tw * Th;
                        Long num1 = (long) 0, num2 = (long) 0, num4 = (long) 0, num8 = (long) 0, numa = (long) 0, numb = (long) 0, numc = (long) 0, numd = (long) 0;
                        if (k - Tw >= 0) //top
                            num1 = layers.get(am.getSourcelayer()).getStr().get(k - Tw);
                        if (k - 1 >= 0 && k % Tw != 0) //left
                            num2 = layers.get(am.getSourcelayer()).getStr().get(k - 1);
                        if (k + 1 < total && k % Tw != Tw - 1) //right
                            num4 = layers.get(am.getSourcelayer()).getStr().get(k + 1);
                        if (k + Tw < total) //bottom
                            num8 = layers.get(am.getSourcelayer()).getStr().get(k + Tw);
                        if (k - Tw - 1 >= 0 && k % Tw != 0) //tl
                            numa = layers.get(am.getSourcelayer()).getStr().get(k - Tw - 1);
                        if (k - Tw + 1 >= 0 && k % Tw != Tw - 1) //tr
                            numb = layers.get(am.getSourcelayer()).getStr().get(k - Tw + 1);
                        if (k + Tw - 1 < total && k % Tw != 0) //bl
                            numc = layers.get(am.getSourcelayer()).getStr().get(k + Tw - 1);
                        if (k + Tw + 1 < total && k % Tw != Tw - 1) //br
                            numd = layers.get(am.getSourcelayer()).getStr().get(k + Tw + 1);

                        if (!am.getAgainst(num1)) count += 1;
                        if (!am.getAgainst(num2)) count += 2;
                        if (!am.getAgainst(num4)) count += 4;
                        if (!am.getAgainst(num8)) count += 8;

                        if (count == 15) {
                            if (am.getAgainst(numa)) count += 1;
                            if (am.getAgainst(numb)) count += 2;
                            if (am.getAgainst(numc)) count += 4;
                            if (am.getAgainst(numd)) count += 8;
                        }
                        if (am.getBeside() != 0) {
                            if (count == 14) {
                                if (num2 == am.getBeside()) count = 31;
                                if (num4 == am.getBeside()) count = 32;
                            }
                            if (count == 13) {
                                if (num1 == am.getBeside()) count = 33;
                                if (num8 == am.getBeside()) count = 34;
                            }
                            if (count == 11) {
                                if (num1 == am.getBeside()) count = 35;
                                if (num8 == am.getBeside()) count = 36;
                            }
                            if (count == 7) {
                                if (num2 == am.getBeside()) count = 37;
                                if (num4 == am.getBeside()) count = 38;
                            }
                        }

                        try {
                            switch (am.objectfill) {
                                case "all":
                                    obj tt = new obj();
                                    tt.setName(am.getObjectname());
                                    tt.setX(Tsw * (k % Tw));
                                    tt.setY(Tsh * (k / Tw));
                                    tt.setW(Tsw);
                                    tt.setH(Tsh);
                                    layers.get(am.getObjectgroup()).getObjects().add(tt);
                                    break;
                                case "edge":
                                    if (count != 15) {
                                        tt = new obj();
                                        tt.setName(am.getObjectname());
                                        tt.setX(Tsw * (k % Tw));
                                        tt.setY(Tsh * (k / Tw));
                                        tt.setW(Tsw);
                                        tt.setH(Tsh);
                                        layers.get(am.getObjectgroup()).getObjects().add(tt);
                                    }
                                    break;
                            }
                        } catch (Exception e) {
                            ErrorBung(e, "autobug1.txt");

                        }
                        try {
                            if (!autoed.get(k)) {
                                layers.get(am.getDestlayer()).getStr().set(k, (long) am.getCell(count));
                                for (int l = 0; l < tilesets.size(); l++) {
                                    if (am.getCell(count) >= tilesets.get(l).getFirstgid() && am.getCell(count) < tilesets.get(l).getFirstgid() + tilesets.get(l).getTilecount()) {
                                        layers.get(am.getDestlayer()).getTset().set(k, l);
                                        break;
                                    }
                                }
                                if (count != 15 && am.getCell(count) != 0) autoed.set(k, true);
                            }
                        } catch (Exception e) {
                            ErrorBung(e, "autobug2.txt");

                        }
                    }

                        break;
                case "completion":
                    for (int k = 0; k < Tw * Th; k++) {

                        if (layers.get(am.getSourcelayer()).getStr().get(k) != am.getSource())
                            continue;
                        try {
                            switch (am.objectfill) {
                                case "single":
                                    obj tt = new obj();
                                    tt.setName(am.getObjectname());
                                    tt.setX(Tsw * (k % Tw));
                                    tt.setY(Tsh * (k / Tw));
                                    tt.setW(Tsw);
                                    tt.setH(Tsh);
                                    layers.get(am.getObjectgroup()).getObjects().add(tt);
                                    break;
                            }
                        } catch (Exception e) {
                            ErrorBung(e, "autobug3.txt");

                        }
                        for (int m = 0; m < am.getX().size(); m++) {
                            int tgt = k + am.getX().get(m) + (am.getY().get(m) * Tw);
                            if (am.getX().get(m) < 0 && k % Tw < tgt % Tw) continue;
                            if (am.getX().get(m) > 0 && k % Tw > tgt % Tw) continue;
                            if (tgt < 0 || tgt >= Tw * Th) continue;

                            if (am.against!=0) {
                                if (layers.get( am.getDestlayer() ).getStr().get( tgt ) != am.against)
                                    continue;
                            }

                            layers.get(am.getDestlayer()).getStr().set(tgt, (long) am.getZ().get(m));
                            try {
                                switch (am.objectfill) {
                                    case "all":
                                        obj tt = new obj();
                                        tt.setName(am.getObjectname());
                                        tt.setX(Tsw * (tgt % Tw));
                                        tt.setY(Tsh * (tgt / Tw));
                                        tt.setW(Tsw);
                                        tt.setH(Tsh);
                                        layers.get(am.getObjectgroup()).getObjects().add(tt);
                                        break;
                                }
                            } catch (Exception e) {
                                ErrorBung(e, "autobug4.txt");
                            }
                            for (int l = 0; l < tilesets.size(); l++) {
                                if (am.getZ().get(m) >= tilesets.get(l).getFirstgid() && am.getZ().get(m) < tilesets.get(l).getFirstgid() + tilesets.get(l).getTilecount()) {
                                    layers.get(am.getDestlayer()).getTset().set(tgt, l);
                                    break;
                                }//if
                            }//l
                        }//m
                    }//k
                    break;
                case "replacement":
                    for (int k = 0; k < Tw * Th; k++) {

                        if (layers.get(am.getSourcelayer()).getStr().get(k) != am.getSource())
                            continue;
                        try {
                            switch (am.objectfill) {
                                case "single":
                                    obj tt = new obj();
                                    tt.setName(am.getObjectname());
                                    tt.setX(Tsw * (k % Tw));
                                    tt.setY(Tsh * (k / Tw));
                                    tt.setW(Tsw);
                                    tt.setH(Tsh);
                                    layers.get(am.getObjectgroup()).getObjects().add(tt);
                                    break;
                            }
                        } catch (Exception e) {
                            ErrorBung(e, "autobug3.txt");

                        }

                            int tgt = k;
                            layers.get(am.getDestlayer()).getStr().set(tgt, (long) am.getCell(repNum));
                            repNum+=1;

                        try {
                                switch (am.objectfill) {
                                    case "all":
                                        obj tt = new obj();
                                        tt.setName(am.getObjectname());
                                        tt.setX(Tsw * (tgt % Tw));
                                        tt.setY(Tsh * (tgt / Tw));
                                        tt.setW(Tsw);
                                        tt.setH(Tsh);
                                        layers.get(am.getObjectgroup()).getObjects().add(tt);
                                        break;
                                }
                            } catch (Exception e) {
                                ErrorBung(e, "autobug4.txt");
                            }
                            for (int l = 0; l < tilesets.size(); l++) {
                                if (am.getCell(repNum) >= tilesets.get(l).getFirstgid() && am.getCell(repNum) < tilesets.get(l).getFirstgid() + tilesets.get(l).getTilecount()) {
                                    layers.get(am.getDestlayer()).getTset().set(tgt, l);
                                    break;
                                }//if
                            }//l
                    }//k
                    break;

            }//switch


        }//autotile run


			/* //This code works but slow
			for (int j=0;j<autoundolayer;j++)
			{
				Json json = new Json();
				String myclip; layer mylayer;
				myclip=json.toJson(layers.get(j));
				mylayer = json.fromJson(layer.class, myclip);
				nextlayers.add(mylayer);
			}

		boolean follower=false;
		for (int i=0;i<autoundolayer;i++)
		{
			for (int j=0;j<layers.get(i).getStr().size();j++)
			{
				long prevstr = prevlayers.get(i).getStr().get(j);
				long nextstr = nextlayers.get(i).getStr().get(j);
				int prevtset= prevlayers.get(i).getTset().get(j);
				int nexttset = nextlayers.get(i).getTset().get(j);

				layerhistory lh=new layerhistory(follower,prevstr,nextstr,j,i,prevtset,nexttset);

				if(prevstr!=nextstr){
					undolayer.add(lh);
					redolayer.clear();
					follower=true;
				}
			}
		}
		*/
        snapWholeMapPhase2(0);
    }

    public void fillthisold(int num, long oi, long from, int direction) {

        if (num < 0) return;
        if (num >= Tw * Th) return;
        switch (direction) {
            case 1://up
                break;
            case 2://down
                break;
            case 3://left
                if (num % Tw == 0) return;
                break;
            case 4://rigjt
                if (num % Tw == 1) return;
                break;
        }


        long fromnew = layers.get(selLayer).getStr().get(num);
        if (from != fromnew) return;
        if (oi == from) return;

        int tzet = layers.get(selLayer).getTset().get(num);

        layerhistory lh2 = new layerhistory(true, fromnew, oi, num, selLayer, tzet, seltset);
        if (fromnew != oi) {
            undolayer.add(lh2);
            redolayer.clear();
        }

        layers.get(selLayer).getStr().set(num, oi);

        fillthis(num - Tw, oi, from, 1);
        fillthis(num + Tw, oi, from, 2);
        fillthis(num - 1, oi, from, 3);
        fillthis(num + 1, oi, from, 4);

    }

    public void fillthis(int numa, long oia, long froma, int directiona) {
        java.util.Queue<floodfill> q = new LinkedList<floodfill>();
        q.add(new floodfill(numa, oia, froma, directiona));
        while (!q.isEmpty()) {
            floodfill n = q.remove();
            int num = n.getNum();
            long oi = n.getOi();
            long from = n.getFrom();
            int direction = n.getDirection();

            if (num < 0) continue;
            if (num >= Tw * Th) continue;
            switch (direction) {
                case 1://up
                    break;
                case 2://down
                    break;
                case 3://left
                    if (num % Tw == (Tw - 1)) continue;
                    break;
                case 4://rigjt
                    if (num % Tw == 0) continue;
                    break;
            }


            long fromnew = layers.get(selLayer).getStr().get(num);
            if (from != fromnew) continue;
            if (oi == from) continue;

            int tzet = layers.get(selLayer).getTset().get(num);

            layerhistory lh2 = new layerhistory(true, fromnew, oi, num, selLayer, tzet, seltset);

            if (fromnew != oi) {
                undolayer.add(lh2);
                uploaddata(lh2);
                redolayer.clear();
            }

            layers.get(selLayer).getStr().set(num, oi);
            layers.get(selLayer).getTset().set(num, seltset);
            updateCache(num);

            q.add(new floodfill(num - Tw, oi, from, 1));
            q.add(new floodfill(num + Tw, oi, from, 2));
            q.add(new floodfill(num - 1, oi, from, 3));
            q.add(new floodfill(num + 1, oi, from, 4));
        }


    }
    public byte[] get16BitPcm(short[] data) {
        byte[] datax = new byte[data.length * 2]; //create a byte array to hold the data passed (short = 2 bytes)
        ByteBuffer.wrap(datax).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(data); // cast a short array to byte array
        return datax;
    }

    public void PCMtoFile(OutputStream os, short[] pcmdata, int srate, int channel, int format) throws IOException {
        byte[] header = new byte[44];
        byte[] data = get16BitPcm(pcmdata);

        long totalDataLen = data.length + 36;
        long bitrate = srate * channel * format;

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = (byte) format;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channel;
        header[23] = 0;
        header[24] = (byte) (srate & 0xff);
        header[25] = (byte) ((srate >> 8) & 0xff);
        header[26] = (byte) ((srate >> 16) & 0xff);
        header[27] = (byte) ((srate >> 24) & 0xff);
        header[28] = (byte) ((bitrate / 8) & 0xff);
        header[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
        header[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
        header[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
        header[32] = (byte) ((channel * format) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (data.length  & 0xff);
        header[41] = (byte) ((data.length >> 8) & 0xff);
        header[42] = (byte) ((data.length >> 16) & 0xff);
        header[43] = (byte) ((data.length >> 24) & 0xff);

        os.write(header, 0, 44);
        os.write(data);
        os.close();
    }

    /*
    public void readandsaveAudio(){
        FileHandle fh1 = Gdx.files.absolute("test.wav");
        FileHandle fh2 = Gdx.files.absolute("test2.wav");
        final File file1 = fh1.file();
        final File file2 = fh2.file();
        try {
            final AudioInputStream in1 = getAudioInputStream(file1);
            //get audio format for targetted sound
            final AudioFormat inFormat = getOutFormat(in1.getFormat());

            //get the target file audio stream using file format
            final AudioInputStream in2 = getAudioInputStream(inFormat, in1);
//write the audio file in targeted pitch file
            AudioSystem.write(in2, AudioFileFormat.Type.WAVE, file2);
        }catch(Exception e){
            ErrorBung(e, "errorpcm.txt");
        }
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        int ch = inFormat.getChannels();
        float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, 72000, 16, ch, ch * 2, rate,
                inFormat.isBigEndian());
    }
*/


    public void removeSW(int num, String sws){
        property todel=null;
        for(property p : properties){
            if (p.getName().equalsIgnoreCase( sws )){
               todel=p;
            }
        }
        properties.remove( todel );
    }


    boolean assemblying = false;

    @Override
    public boolean longPress(float p1, float p2) {
        if (loadingfile) return true;
        if (layers.size()==0) return true;
        if (kartu.equalsIgnoreCase("game")){return true;}

        //Gdx.input.vibrate(50);
        Vector3 touch = new Vector3();
        cam.unproject(touch.set(p1, p2, 0));
        Vector3 touch2 = new Vector3();
        uicam.unproject(touch2.set(p1, p2, 0));
        Vector3 touch3 = new Vector3();
        tilecam.unproject(touch3.set(p1, p2, 0));

        if (kartu == "world") {
            //autotile
            if (tapped(touch2, gui.autotile) && autotiles.size()>0) {
                if (mode == "tile" || mode == "object") {
                    //msgbox("Longpress to run Auto tiles");
                    if (!softcue("refresh") && lockUI) return true;
                    loadList( "macro" );
                    cue("refresh");
                    return true;
                }
            }
            if (mode == "object" || mode == "tile" || mode == "image") {

                if (assemblymode && assemblynum!=-1) {
                    assemblying=true;
                    boolean follower = false;
                    for (property p : autotiles.get( assemblyid ).getProperties()) {

                        if (p.getName().contains( "," )) {
                            String[] ass = p.getName().split( "," );
                            int axpos = Integer.parseInt( ass[0] );
                            int aypos = Integer.parseInt( ass[1] );

                            int aspr = Integer.parseInt( p.getValue() );
                            int atst = getTsetFromSpr( aspr );
                            int newnum = assemblynum + (axpos) + (aypos*Tw);
                            ////////////

                            long oi = aspr;
                            int num = newnum;
                                if (num <0) continue;
                                if (num >= Tw*Th) continue;
                                Long from = layers.get( selLayer ).getStr().get( num );
                                int tzet = layers.get( selLayer ).getTset().get( num );

                                layerhistory lh2 = new layerhistory( follower, from, oi, num, selLayer, tzet, seltset );
                                follower=true;
                                updateCache( num );

                                if (from != oi) {
                                    undolayer.add( lh2 );
                                    uploaddata( lh2 );
                                    redolayer.clear();
                                }
                                layers.get( selLayer ).getStr().set( num, oi );
                                layers.get( selLayer ).getTset().set( num, atst );

                            ////////////

                        } //comma
                    } // properties

                return true;
                }

                if (swatches && mode == "tile" ) {
                    if (tapped( touch2, gui.sw1 )) {
                        removeSW( swatchValue.get( 0 ), "sw1" );
                        swatchValue.set( 0,0 );
                        return true;
                    }

                    if (tapped( touch2, gui.sw2 )) {
                        removeSW( swatchValue.get( 1 ), "sw2" );
                        swatchValue.set( 1,0 );
                        return true;
                    }
                    if (tapped( touch2, gui.sw3 )) {
                        removeSW( swatchValue.get( 2 ), "sw3" );
                        swatchValue.set( 2,0 );
                        return true;
                    }
                    if (tapped( touch2, gui.sw4 )) {
                        removeSW( swatchValue.get( 3 ), "sw4" );
                        swatchValue.set( 3,0 );
                        return true;
                    }
                    if (tapped( touch2, gui.sw5 )) {
                        removeSW( swatchValue.get( 4 ), "sw5" );
                        swatchValue.set( 4,0 );
                        return true;
                    }
                    if (tapped( touch2, gui.sw6 )) {
                        removeSW( swatchValue.get( 5 ), "sw6" );
                        swatchValue.set( 5,0 );
                        return true;
                    }
                }

                if (tapped(touch2, gui.save)) {
                    sAutoSave = !sAutoSave;
                    prefs.putBoolean("autosave", sAutoSave).flush();
                    return true;
                }

                if (tapped(touch2, gui.layer) || tapped(touch2, gui.layerpick) ) {
                    selLayer -= 1;
                    if (selLayer <= -1) {
                        selLayer = layers.size() - 1;
                    }
                    updateObjectCollision();
                    resetCaches();
                    adjustTileset();
                    return true;
                    //'812398BB9
                }

                if (tapped(touch2, gui.viewmode)) {
                    layers.get(selLayer).setVisible(!layers.get(selLayer).isVisible());
                    resetCaches();
                    return true;
                }
                if (tapped(touch2, gui.tool2) || tapped(touch2, gui.tool3)) {
                    //Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                    //do nothing, just to prevent longpressing.
                    return true;
                }
                if (tapped(touch2, gui.mode)) { //actually add new layer...
                    //Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                    //do nothing, just to prevent longpressing.
                    return true;
                }
                if (tapped(touch2, gui.play)) { //actually add new layer...
                    //Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                    //do nothing, just to prevent longpressing.
                    return true;
                }
                if (tapped(touch2, gui.redo)) {
                    //Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                    //rotating = !rotating;
                    return true;
                }
                if (sMinimap && tapped(touch2, gui.minimap)) {
                    return true;
                }
                if (tapped(touch2, gui.undo)) {
                    //Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer + " " + (layers.size() + 1), "");
                    //loadInterface("custom1.json");
                        /*
                        Json json = new Json();
                        writeThisAbs(curdir + "/auto.json", json.prettyPrint(gui));
                        msgbox("auto.json saved!");
                         */
                    return true;
                }

                if (tapped(touch2, gui.menu)) {
                    FileDialog(z.opentmxfile, "open", "file", new String[]{".tmx"}, null);
                    return true;
                }
            }


            if (mode == "tile") {


                int ae = (int) touch.x;
                int ab = (int) touch.y;
                if (touch.y > 0) ab = 1;
                if (tapped(touch2, gui.tool5)) {
                    //kucrut
                    Gdx.input.getTextInput(pBrushSize, z.tilesize + " ["+brushsize+"]", "", "1-10");

                    activetool=4;
                    return true;
                }

                //switch between copy tool and move tool
                if (tapped(touch2, gui.tool4)) {
                    switch (movetool){
                        case PICKER:
                            movetool=selectTool.COPY;
                            break;
                        case COPY:
                            movetool=selectTool.MOVE;
                            break;
                        case MOVE:
                            movetool=selectTool.FLIP;
                            break;
                        case FLIP:
                            movetool=selectTool.CLONE;
                            break;
                        case CLONE:
                            movetool=selectTool.PICKER;
                            break;
                    }
                    activetool=3;
                    return true;
                }

                //switch between tool shape
                if (tapped(touch2, gui.tool1)) {
                    switch (currentShape)
                    {
                        case RECTANGLE:
                            currentShape=ShapeTool.CIRCLE;
                            break;
                        case CIRCLE:
                            currentShape=ShapeTool.LINE;
                            break;
                        case LINE:
                            currentShape=ShapeTool.RECTANGLE;
                            break;
                    }
                    activetool=0;
                    return true;
                }



                if (tapped(touch2, gui.rotation)) {
                    if (mode == "tile") {

                        rotate -= 1;
                        if (rotate == -1) {
                            rotate = 7;
                        }
                        String nfo = "0";
                        switch (rotate) {
                            case 0:
                                nfo = "0";
                                break;
                            case 1:
                                nfo = "90";
                                break;
                            case 2:
                                nfo = "180";
                                break;
                            case 3:
                                nfo = "270";
                                break;
                            case 4:
                                nfo = "0*";
                                break;
                            case 5:
                                nfo = "90*";
                                break;
                            case 6:
                                nfo = "180*";
                                break;
                            case 7:
                                nfo = "270*";
                                break;
                        }
                        rotationName = nfo;
                        return true;
                    }

                }






                if (tapped(touch2, gui.picker)) {
                    //FileDialog(z.selectfile, "quickaddtset", "file", new String[]{".tsx", ".png", ".jpg", ".jpeg", ".bmp", ".gif"}, nullTable);
                    swatches = !swatches;
                    prefs.putBoolean("swatches", swatches).flush();
                    return true;
                }
                if (tilesets.size() == 0) return true;

                //active tool 0-4 (rectangle 0 , eraser 1, ..... 2, copy paste 3, brush 4
                //longpressing fill makes no sense, that is why just change it to brush.
                if (activetool == 2) activetool = 4;

                //the main idea of longpressing is just this line.
                roll = true;

                //isometric tile selection is so complicated, how did I make this? lol
                if (orientation.equalsIgnoreCase("orthogonal")) {
                    if (touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw) {
                        mapstartSelect = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                    }
                } else if (orientation.equalsIgnoreCase("isometric")) {


                    //cool way to convert isometric to orthogonal, new iso tap detection
                    int newae = ae - ((Tsw/Tsh) * ab) + Tsw/2;
                    int newab = (ae / (Tsw/Tsh)) + ab -Tsh/2;
                    mapstartSelect = (Tw * ((-newab + Tsh) / Tsh) + (newae / Tsw));

                    if (mapstartSelect<0 || mapstartSelect >Tw*Th) return true;

                    /* old way
                    float closest = 9999;
                    for (int i = 0; i < Tw * Th; i++) {
                        int offsetx = 0, offsety = 0;
                        xpos = i % Tw;
                        ypos = i / Tw;
                        offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                        offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                        int drawx = xpos * Tsw - offsetx;
                        int drawy = -ypos * Tsh - offsety;
                        int x = ae - drawx;
                        int y = ab - drawy;
                        int Tsws = Tsw / 2;
                        int Tshs = Tsh / 2;
                        float dx = Math.abs(x - Tsws);
                        float dy = Math.abs(y - Tshs);
                        if (dx / Tsws + dy / Tshs < closest) {
                            mapstartSelect = i;
                            closest = dx / Tsws + dy / Tshs;
                        }
                    }

                     */
                }
                //mapstartSelect = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));

                //code for brush, so basically draw around
                if (activetool == 4) {
                    tapTile(mapstartSelect, false, false,false,curspr);
                    int num=mapstartSelect;
                    for (int bx=0; bx<brushsize;bx++) {
                        for (int by = 0; by < brushsize; by++) {
                            //celor2
                            int curx = num % Tw;
                            int cury = num / Tw;
                            int locx = curx - brushsize / 2 + bx;
                            int locy = cury - brushsize / 2 + by;
                            // Gdx.app.log( "", cury + "/" + curx + "/" + locy + "/" + locx );
                            if (locx < 0) continue;
                            if (locy < 0) continue;
                            if (locx >= Tw) continue;
                            if (locy >= Th) continue;
                            boolean terrar =false;

                            // this code halves the needed taptile with terrain true.
                            if (bx==0 && by==0) terrar = true;
                            if (bx==brushsize-1 && by==0) terrar = true;
                            if (bx==brushsize-1 && by==brushsize-1) terrar = true;
                            if (by==brushsize-1 && bx==0) terrar = true;
                            if (bx==0 && by % 2==0) terrar = true;
                            if (by==0 && bx % 2==0) terrar = true;
                            if (bx==brushsize-1 && by % 2==0) terrar = true;
                            if (by==brushsize-1 && bx % 2==0) terrar = true;

                            final boolean ft = terrar;
                            //log(ft+"");
                            //if (!ft) continue;

                            tapTile( locy * Tw + locx, true, terrar,false,curspr);
                        }
                    }
                }

                //code for the rest, so basically longpressing is just flagging "roll"
                mapendSelect = mapstartSelect;
                mapinitialSelect = mapstartSelect;

                // you will need this, otherwise the app will crash because it relies on panStop to
                // duplicate to cliplayer

                if (activetool == 3)
                {
                    layer lay;
                    Json json = new Json();

                    String clip = json.toJson(layers.get(selLayer));
                    clipsource=selLayer;
                    lay = json.fromJson(layer.class, clip);
                    lay.setName("CLIPBOARD"); //why not, lol.
                    cliplayer = lay;

                    //now I have the clipboard layer, just put it on tap...
                }



            } else if (mode == "object") {
                if (tapped(touch2, gui.layer)) {
                    loadList("obj");
                    return true;
                }
                if (tapped(touch2, gui.mode)) {
                   // Gdx.input.getTextInput(pNewObjLayerSC, z.addnew, z.object + " " + (layers.size() + 1), "");
                    return true;
                }
                if (tapped(touch2, gui.objectpickermid)) {
                    //status("Object picker",1);
                    return true;
                }
                longpressobj(p1, p2);
                return true;
            }
        } else if (kartu == "tile") {
            if (tapped(touch2, gui.tilesetsright)) {
                return true;
            }
            if (tapped(touch2, gui.tilesetsleft)) {
                return true;
            }
            //longpressing in a tile picker
            if (tapped(touch2, gui.tilesetsmid)) {
                loadList("tset");
                return true;
            }

            if (pickAuto) {
                return true;
            }
            stamp = true;
            int ae = (int) touch3.x;
            int ab = (int) touch3.y;
            //this line is to sovlve problem of the first line not clicked on 1 x 1 tile.
            if (touch3.y > 0) ab = 1;
            int Tsh = tilesets.get(seltset).getTileheight();
            int Tsw = tilesets.get(seltset).getTilewidth();
            if (touch3.y < Tsh && touch3.y > -Tsh * tilesets.get(seltset).getHeight() + Tsh && touch3.x > 0 && touch3.x < Tsw * tilesets.get(seltset).getWidth()) {
                startSelect = (tilesets.get(seltset).getWidth() * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                adjustLayer( tilesets.get(seltset) );
                endSelect = startSelect;
                initialSelect = startSelect;
            }
        } else if (kartu == "pickanim") {
            switch (tilePicker) {
                case "massprops":
                    stamp = true;
                    int ae = (int) touch3.x;
                    int ab = (int) touch3.y;
                    int Tsh = tilesets.get(selTsetID).getTileheight();
                    int Tsw = tilesets.get(selTsetID).getTilewidth();
                    if (touch3.y < Tsh && touch3.y > -Tsh * tilesets.get(selTsetID).getHeight() + Tsh && touch3.x > 0 && touch3.x < Tsw * tilesets.get(selTsetID).getWidth()) {
                        startSelect = (tilesets.get(selTsetID).getWidth() * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                        endSelect = startSelect;
                        initialSelect = startSelect;
                    }
                    break;


                case "terraineditor":

                    if (tapped(touch2, gui.tilesetsmid)) {
                        tileset tt = tilesets.get( selTsetID );
                        if (tt.getTerrains().size() > 0) {
                            tt.setSelTerrain( tt.getSelTerrain() - 1 );
                            if (tt.getSelTerrain() < 0) {
                                tt.setSelTerrain( tt.getTerrains().size()-1 );
                            }
                        }
                        return true;
                    }

                    //laksan
                    tileset ts = tilesets.get( selTsetID );
                    ae = (int) touch3.x;
                    ab = (int) touch3.y;
                    Integer numf = ts.getFirstgid() + (ts.getWidth() * ((-ab + ts.getTileheight()) / ts.getTileheight()) + (ae / ts.getTilewidth()));
                    int num = numf - ts.getFirstgid();

                    int Tw=ts.getWidth();
                    int Th=ts.getHeight();
                    if (ts.getTerrains().size() == 0) return true;

                    tiles = ts.getTiles();
                    tile t = null;
                    for (int n = 0; n < tiles.size(); n++) {
                        if (tiles.get( n ).getTileID() == num) {
                            t = tiles.get( n );
                            break;
                        }
                    }

                    if (t == null) {
                        tile tt = new tile();
                        tt.setTileID( num );
                        tiles.add( tt );
                        t = tiles.get( tiles.size() - 1 );
                    }
                    int[] cn = t.getTerrain();
                    int a = cn[0], b = cn[1], c = cn[2], d = cn[3];
                    int e = tilesets.get( selTsetID ).getSelTerrain();

                    if (a==-1 && b==-1 && c==-1 && d==e) {
                    }else if (a==e && b==-1 && c==-1 && d==-1) {

                    }else {
                        if (!(a==-1 && b==-1 && c==-1 && d==-1)){
                            t.setTerrain( -1, -1, -1, -1 );
                            return true;
                        }

                    }

                    if (a == -1 && b == -1 && c == -1 && d == -1) {
                        for (int i = 0; i < 9; i++) {
                            //gogo is for detecting index of surrounding tile
                            int gogo = num;
                            switch (i) {
                                case 0:
                                    if (num <= Tw || num % Tw == 0) continue;
                                    gogo = num - Tw - 1;
                                    break;
                                case 1:
                                    if (num <= Tw) continue;
                                    gogo = num - Tw;
                                    break;
                                case 2:
                                    if (num <= Tw || num % Tw == Tw - 1) continue;
                                    gogo = num - Tw + 1;
                                    break;
                                case 3:
                                    if (num <= 1 || num % Tw == 0) continue;
                                    gogo = num - 1;
                                    break;
                                case 4:
                                    if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                                    gogo = num + 1;
                                    break;
                                case 5:
                                    if (num >= Tw * Th - Tw || num % Tw == 0) continue;
                                    gogo = num + Tw - 1;
                                    break;
                                case 6:
                                    if (num >= Tw * Th - Tw) continue;
                                    gogo = num + Tw;
                                    break;
                                case 7:
                                    if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                                    gogo = num + Tw + 1;
                                    break;
                                case 8:
                                    gogo = num;
                                    break;
                            }


                            tiles = ts.getTiles();
                            t = null;
                            for (int n = 0; n < tiles.size(); n++) {
                                if (tiles.get( n ).getTileID() == gogo) {
                                    t = tiles.get( n );
                                    break;
                                }
                            }

                            if (t == null) {
                                tile tt = new tile();
                                tt.setTileID( gogo );
                                tiles.add( tt );
                                t = tiles.get( tiles.size() - 1 );
                            }
                            cn = t.getTerrain();
                            a = cn[0];
                            b = cn[1];
                            c = cn[2];
                            d = cn[3];
                            e = tilesets.get( selTsetID ).getSelTerrain();




                                switch (i) {
                                    case 0:
                                        t.setTerrain( -1, -1, -1, e );
                                        break;
                                    case 1:
                                        t.setTerrain( -1, -1, e, e );
                                        break;
                                    case 2:
                                        t.setTerrain( -1, -1, e, -1 );
                                        break;
                                    case 3:
                                        t.setTerrain( -1, e, -1, e );
                                        break;
                                    case 4:
                                        t.setTerrain( e, -1, e, -1 );
                                        break;
                                    case 5:
                                        t.setTerrain( -1, e, -1, -1 );
                                        break;
                                    case 6:
                                        t.setTerrain( e, e, -1, -1 );
                                        break;
                                    case 7:
                                        t.setTerrain( e, -1, -1, -1 );
                                        break;
                                    case 8:
                                        t.setTerrain( e, e, e, e );
                                        break;
                                }

                            }


                        return true;
                    }

                    if (a == -1 && b == -1 && c == -1 && d == e) {
                        for (int i = 0; i < 4; i++) {
                            //gogo is for detecting index of surrounding tile
                            int gogo = num;
                            switch (i) {
                                case 1:
                                    if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                                    gogo = num + 1;
                                    break;
                                case 2:
                                    if (num >= Tw * Th - Tw) continue;
                                    gogo = num + Tw;
                                    break;
                                case 3:
                                    if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                                    gogo = num + Tw + 1;
                                    break;
                                case 0:
                                    gogo = num;
                                    break;
                            }


                            tiles = ts.getTiles();
                            t = null;
                            for (int n = 0; n < tiles.size(); n++) {
                                if (tiles.get( n ).getTileID() == gogo) {
                                    t = tiles.get( n );
                                    break;
                                }
                            }

                            if (t == null) {
                                tile tt = new tile();
                                tt.setTileID( gogo );
                                tiles.add( tt );
                                t = tiles.get( tiles.size() - 1 );
                            }
                            cn = t.getTerrain();
                            a = cn[0];
                            b = cn[1];
                            c = cn[2];
                            d = cn[3];
                            e = tilesets.get( selTsetID ).getSelTerrain();




                            switch (i) {
                                case 0:
                                    t.setTerrain( -1, -1, -1, e );
                                    break;
                                case 1:
                                    t.setTerrain( -1, -1, e, -1 );
                                    break;
                                case 2:
                                    t.setTerrain( -1, e, -1, -1 );
                                    break;
                                case 3:
                                    t.setTerrain( e, -1, -1, -1 );
                                    break;
                            }

                        }


                        return true;
                    }

                    if (a == e && b == -1 && c == -1 && d == -1) {
                        for (int i = 0; i < 4; i++) {
                            //gogo is for detecting index of surrounding tile
                            int gogo = num;
                            switch (i) {
                                case 1:
                                    if (num >= Tw * Th || num % Tw == Tw - 1) continue;
                                    gogo = num + 1;
                                    break;
                                case 2:
                                    if (num >= Tw * Th - Tw) continue;
                                    gogo = num + Tw;
                                    break;
                                case 3:
                                    if (num >= Tw * Th - Tw || num % Tw == Tw - 1) continue;
                                    gogo = num + Tw + 1;
                                    break;
                                case 0:
                                    gogo = num;
                                    break;
                            }


                            tiles = ts.getTiles();
                            t = null;
                            for (int n = 0; n < tiles.size(); n++) {
                                if (tiles.get( n ).getTileID() == gogo) {
                                    t = tiles.get( n );
                                    break;
                                }
                            }

                            if (t == null) {
                                tile tt = new tile();
                                tt.setTileID( gogo );
                                tiles.add( tt );
                                t = tiles.get( tiles.size() - 1 );
                            }
                            cn = t.getTerrain();
                            a = cn[0];
                            b = cn[1];
                            c = cn[2];
                            d = cn[3];
                            e = tilesets.get( selTsetID ).getSelTerrain();




                            switch (i) {
                                case 0:
                                    t.setTerrain( e, -1, -1, -1 );
                                    break;
                                case 1:
                                    t.setTerrain( -1, e, -1, -1 );
                                    break;
                                case 2:
                                    t.setTerrain( -1, -1, e, -1 );
                                    break;
                                case 3:
                                    t.setTerrain( -1, -1, -1, e );
                                    break;
                            }

                        }


                        return true;
                    }




            }


        }

        return false;
    }

    boolean midiplaying =false;
    int tempo=120;
    Thread play;
//composer c = new composer("V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s [PEDAL_HI_HAT]s Ri L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri");// V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs [PEDAL_HI_HAT]s Rs L1 Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri V9 L0 [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri [BASS_DRUM]i Ri [ACOUSTIC_SNARE]i [BASS_DRUM]i Ri [BASS_DRUM]i [ACOUSTIC_SNARE]i Ri L1 [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri [PEDAL_HI_HAT]s Rs Ri");

    private void exporttomidi(String filenya){
        // importing midi code, do not delete.
        Gdx.app.log("ASU",filenya);
        if (filenya.startsWith( "[i]" )) {
            try {
                Pattern pattern = MidiFileManager.loadPatternFromMidi( Gdx.files.absolute( basepath+"Pictures/" + filenya.substring( 3 )).file() );
                System.out.println( pattern );
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        /**/

        boolean ismusic=false;
        for (property p : properties) {
            if (p.getName().equalsIgnoreCase("type") && p.getValue().equalsIgnoreCase("NotTiled music")) {
                ismusic=true;
            }

        }

        if (ismusic) {
            playmusic( playback.MIDI, filenya );
            backToMap();
            status( z.exportfinished, 3 );
        }



    }

    private void recordwav(String filenya){
        boolean ismusic=false;
        for (property p : properties) {
            if (p.getName().equalsIgnoreCase("type") && p.getValue().equalsIgnoreCase("NotTiled music")) {
                ismusic=true;
            }

        }

        if (ismusic) {
            playmusic( playback.WAV, filenya );
            backToMap();
            status( z.recording, 3 );
        }
    }

    public enum playback{PLAY,MIDI,WAV}
    public Music composerPlayer;

    private void playmusic(final playback pbt, final String filenya){

        switch(Gdx.app.getType()) {
            case Android:
                if (composerPlayer!=null){
                    composerPlayer.stop();
                    composerPlayer=null;
                    midiplaying = false;
                    return;
                }
                break;
            // android specific code
            case Desktop:
                if (pbt==playback.PLAY) {
                    try {
                        if (play != null) {
                            play.interrupt();
                            new Player().play( "" );
                            play = null;
                            midiplaying = false;
                            return;
                        }
                    }catch(Exception e){}
                    if (composerPlayer!=null){
                        composerPlayer.stop();
                        return;
                    }
                }
                if (pbt==playback.WAV) {
                    if (play != null) {
                        play.interrupt();
                    }
                }

                //Gdx.app.log("SEQ", seq.toString());
                break;
            // desktop specific code
            case iOS:
                if (composerPlayer!=null){
                    composerPlayer.stop();
                }

                break;
            // android specific code
        }


        midiplaying=true;
        play=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int wavwidth=0;

                    int xstart=0;
                    int xstop=Tw-1;
                    int ystart=0;
                    int ystop=Th-1;
                    if (activetool==3){
                        xstart=mapstartSelect % Tw;
                        xstop=mapendSelect % Tw;
                        ystart=mapstartSelect /Tw;
                        ystop=mapendSelect / Tw;
                    }

                    wavwidth = (2+(xstop-xstart))*4;
                    Pattern seq = new Pattern();
                    int index=-1; boolean indexup;
                for (int y=ystart;y<=ystop;y++){
                    indexup=false;
                    for (int x=xstart;x<=xstop;x++){
                        int num = y*Tw+x;
                        Long spr = layers.get( selLayer ).getStr().get( num );
                        int tst = layers.get( selLayer ).getTset().get( num );
                        if (tst==-1) { continue;}

                        Long cspr = spr-tilesets.get(tst).getFirstgid();

                        for (tile t: tilesets.get(tst).getTiles()){
                            if (t.getTileID()==cspr){
                                for (property p: t.getProperties()){
                                    if (p.getName().equalsIgnoreCase( "chords" )){
                                        if (!indexup) {index++; indexup=true;}
                                        ChordProgression cp = new ChordProgression(p.getValue());
                                        Pattern tp = cp.getPattern();
                                        seq.add( tp );
                                    }
                                    if (p.getName().equalsIgnoreCase( "pattern" )){
                                        if (!indexup) {index++; indexup=true;}
                                        Pattern tp = new Pattern(p.getValue().replace( "!",""+(x-xstart) ));
                                        //if (!tp.toString().contains( "[" )) tp.setVoice( index );
                                        //voice setting sendiri -_-!
                                        seq.add( tp );
                                    }
                                    if (p.getName().equalsIgnoreCase( "rhythm" )){
                                        if (!indexup) {index++; indexup=true;}
                                        String ss[] = p.getValue().split( "\\n" );
                                        Rhythm r=new Rhythm();
                                        for (String s:ss){
                                            r.addLayer( s );
                                        }

                                        seq.add(r.getPattern());
                                    }
                                }
                            }
                        }
                    }

                }

                int tempo=120;
                for (property p : properties){
                    if (p.getName().equalsIgnoreCase( "tempo" )){
                        tempo=Integer.parseInt( p.getValue() );
                    }
                }
                seq.setTempo(tempo);

                //Gdx.app.log( "ASD",seq.toString() );
                switch (pbt){
                    case MIDI:
                        composer c = new composer(seq.toString());
                        c.save( curdir+"/"+filenya+".mid" );
                        MidiFileManager.savePatternToMidi(seq , Gdx.files.absolute(curdir+"/"+filenya+"v2.midi").file());
                        break;
                    case PLAY:
                        switch(Gdx.app.getType()) {
                            case Android:
                                c = new composer(seq.toString());
                                c.save( "NotTiled/Temp/composer.mid" );
                                composerPlayer = Gdx.audio.newMusic( Gdx.files.absolute( basepath+"NotTiled/Temp/composer.mid" ) );
                                composerPlayer.play();
                                break;
                                // android specific code
                            case Desktop:
                                //Gdx.app.log("SEQ", seq.toString());
                                new Player().play( seq );
                                break;
                                // desktop specific code
                            case iOS:
                                c = new composer(seq.toString());
                                c.save( "NotTiled/Temp/composer.mid" );
                                composerPlayer = Gdx.audio.newMusic( Gdx.files.absolute( basepath+"NotTiled/Temp/composer.mid" ) );
                                composerPlayer.play();
                                break;
                                // android specific code
                        }
                        break;
                    case WAV:
                        recordAudio(wavwidth,filenya);
                        switch(Gdx.app.getType()) {
                            case Android:
                                Music m = Gdx.audio.newMusic( Gdx.files.absolute( curdir + "/" + filenya + ".mid" ) );
                                m.play();
                                break;
                            // android specific code
                            case Desktop:
                                new Player().play( seq );
                                midiplaying=false;
                                play=null;
                                break;
                            // desktop specific code
                            case iOS:
                                m = Gdx.audio.newMusic( Gdx.files.absolute( curdir + "/" + filenya + ".mid" ) );
                                m.play();
                                break;
                            // android specific code
                        }
                        break;

                }
                } catch (Exception e) {
                    e.printStackTrace();
                    //msgbox(e.printStackTrace(););
                }

                /*
                        ChordProgression cp = new ChordProgression("I IV V I I IV V III II I II i");
                        Pattern p3 = new Pattern(cp.getPattern()).setVoice( 3 );
                        Rhythm rhythm = new Rhythm()
                                .addLayer("O..oO...O..oOO..")
                                .addLayer("..S...S...S...S.")
                                .addLayer("````````````````")
                                .addLayer("...............+");

                        Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
                        Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
                        Player player = new Player();
                        Pattern sequence = new Pattern(p1, p2, p3, rhythm.getPattern().repeat(2));

                        player.play(sequence);


                 */



            }
        });

        play.start();


    }

    private void previewpattern(final playback pbt, final String filenya){

        if (pbt==playback.PLAY) {
            try {
                if (play != null) {
                    play.interrupt();
                    new Player().play( "" );
                    play = null;
                    midiplaying = false;
                    return;
                }
            }catch(Exception e){}
            if (composerPlayer!=null){
                composerPlayer.stop();
                return;
            }
        }
        if (pbt==playback.WAV) {
            if (play != null) {
                play.interrupt();
            }
        }

        midiplaying=true;
        play=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int wavwidth=0;

                    int xstart=0;
                    int xstop=Tw-1;
                    int ystart=0;
                    int ystop=Th-1;
                    if (activetool==3){
                        xstart=mapstartSelect % Tw;
                        xstop=mapendSelect % Tw;
                        ystart=mapstartSelect /Tw;
                        ystop=mapendSelect / Tw;
                    }

                    wavwidth = (2+(xstop-xstart))*4;
                    Pattern seq = new Pattern();
                    int index=-1; boolean indexup;
                    for (int y=ystart;y<=ystop;y++){
                        indexup=false;
                        for (int x=xstart;x<=xstop;x++){
                            int num = y*Tw+x;
                            Long spr = layers.get( selLayer ).getStr().get( num );
                            int tst = layers.get( selLayer ).getTset().get( num );
                            if (tst==-1) { continue;}

                            Long cspr = spr-tilesets.get(tst).getFirstgid();

                            for (tile t: tilesets.get(tst).getTiles()){
                                if (t.getTileID()==cspr){
                                    for (property p: t.getProperties()){
                                        if (p.getName().equalsIgnoreCase( "chords" )){
                                            if (!indexup) {index++; indexup=true;}
                                            ChordProgression cp = new ChordProgression(p.getValue());
                                            Pattern tp = cp.getPattern();
                                            seq.add( tp );
                                        }
                                        if (p.getName().equalsIgnoreCase( "pattern" )){
                                            if (!indexup) {index++; indexup=true;}
                                            Pattern tp = new Pattern(p.getValue().replace( "!",""+(x-xstart) ));
                                            //if (!tp.toString().contains( "[" )) tp.setVoice( index );
                                            //voice setting sendiri -_-!
                                            seq.add( tp );
                                        }
                                        if (p.getName().equalsIgnoreCase( "rhythm" )){
                                            if (!indexup) {index++; indexup=true;}
                                            String ss[] = p.getValue().split( "\\n" );
                                            Rhythm r=new Rhythm();
                                            for (String s:ss){
                                                r.addLayer( s );
                                            }

                                            seq.add(r.getPattern());
                                        }
                                    }
                                }
                            }
                        }

                    }

                    int tempo=120;
                    for (property p : properties){
                        if (p.getName().equalsIgnoreCase( "tempo" )){
                            tempo=Integer.parseInt( p.getValue() );
                        }
                    }
                    seq.setTempo(tempo);

                    //Gdx.app.log( "ASD",seq.toString() );
                    switch (pbt){
                        case MIDI:
                            composer c = new composer(seq.toString());
                            c.save( curdir+"/"+filenya+".mid" );
                            MidiFileManager.savePatternToMidi(seq , Gdx.files.absolute(curdir+"/"+filenya+"v2.midi").file());
                            break;
                        case PLAY:
                            switch(Gdx.app.getType()) {
                                case Android:
                                    c = new composer(seq.toString());
                                    c.save( "NotTiled/Temp/composer.mid" );
                                    composerPlayer = Gdx.audio.newMusic( Gdx.files.absolute( basepath+"NotTiled/Temp/composer.mid" ) );
                                    composerPlayer.play();
                                    break;
                                // android specific code
                                case Desktop:
                                    //Gdx.app.log("SEQ", seq.toString());
                                    new Player().play( seq );
                                    break;
                                // desktop specific code
                                case iOS:
                                    c = new composer(seq.toString());
                                    c.save( "NotTiled/Temp/composer.mid" );
                                    composerPlayer = Gdx.audio.newMusic( Gdx.files.absolute( basepath+"NotTiled/Temp/composer.mid" ) );
                                    composerPlayer.play();
                                    break;
                                // android specific code
                            }
                            break;
                        case WAV:
                            recordAudio(wavwidth,filenya);
                            switch(Gdx.app.getType()) {
                                case Android:
                                    Music m = Gdx.audio.newMusic( Gdx.files.absolute( curdir + "/" + filenya + ".mid" ) );
                                    m.play();
                                    break;
                                // android specific code
                                case Desktop:
                                    new Player().play( seq );
                                    break;
                                // desktop specific code
                                case iOS:
                                    m = Gdx.audio.newMusic( Gdx.files.absolute( curdir + "/" + filenya + ".mid" ) );
                                    m.play();
                                    break;
                                // android specific code
                            }
                            break;

                    }
                    midiplaying=false;
                    play=null;
                } catch (Exception e) {
                    e.printStackTrace();
                    //msgbox(e.printStackTrace(););
                }

                /*
                        ChordProgression cp = new ChordProgression("I IV V I I IV V III II I II i");
                        Pattern p3 = new Pattern(cp.getPattern()).setVoice( 3 );
                        Rhythm rhythm = new Rhythm()
                                .addLayer("O..oO...O..oOO..")
                                .addLayer("..S...S...S...S.")
                                .addLayer("````````````````")
                                .addLayer("...............+");

                        Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
                        Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
                        Player player = new Player();
                        Pattern sequence = new Pattern(p1, p2, p3, rhythm.getPattern().repeat(2));

                        player.play(sequence);


                 */



            }
        });

        play.start();


    }


    private com.mirwanda.nottiled.platformer.game mygame;


    private void playgame(final String curdir, final String filex){

        try{
            mygame = new com.mirwanda.nottiled.platformer.game();
            mygame.initialise(curdir, filex);
            kartu="game";
            Gdx.input.setInputProcessor(im);
            gamecam.zoom = 0.2f;
            gamecam.position.set(mygame.player.body.getPosition().x,mygame.player.body.getPosition().y,0);
            gamecam.update();
        }catch(Exception e){
            msgbox(e.toString().substring( e.toString().indexOf( ":" )+2 ));
        }

    }




    private boolean longpressobj(float p1, float p2) {



        longpressObject( p1,p2 );
        return true;
        /*
        Vector3 touch = new Vector3();
        cam.unproject(touch.set(p1, p2, 0));

        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).getObjects().size() > 0 && layers.get(i).getType() == layer.Type.OBJECT) {
                if (i == selgroup || objviewMode == 0) {
                    for (int j = 0; j < layers.get(i).getObjects().size(); j++) {
                        obj ox = layers.get(i).getObjects().get(j);
                        float offx = 0, offy = 0;
                        if (ox.getShape() != null) {
                            switch (ox.getShape()) {

                                case "point":
                                case "polygon":
                                case "polyline":
                                    offx = ox.getWa();
                                    offy = ox.getHa();
                                default:


                                    break;
                            }
                        }
                        if (touch.y <= -(ox.getY() - ox.getH()) - ox.getH() + Tsh + offy && touch.y >= -(ox.getY() - ox.getH()) - ox.getH() - ox.getH() + Tsh + offy && touch.x >= ox.getX() - offx && touch.x <= ox.getX() + ox.getW() - offx) {
                            if (ox.getShape() == "image") {
                                newobject = ox;
                                pickTile("newimgobj");
                                return true;
                            }
                            if (ox.getShape() == "text") {
                                newobject = ox;
                                Gdx.input.getTextInput(pnewtextobject, z.addnew, ox.getText(), "");
                                return true;
                            }
                            if (ox.getShape() == "polygon" || ox.getShape() == "polyline") {
                                newobject = ox;
                                mode = "newpoly";
                                return true;
                            }

                        }//if atas
                    }
                }
            }
        }


        return false;

         */
    }

    @Override
    public boolean fling(float p1, float p2, int p3) {
        if (loadingfile) return true;
        if (nofling > 0) return true;

        velx = p1;
        vely = p2;
        if (velx > 4000) velx = 4000;
        if (velx < -4000) velx = -4000;
        if (vely > 4000) vely = 4000;
        if (vely < -4000) vely = -4000;
        //status(p1+"/"+p2+"/"+p3,5);

        return false;
    }

    long prevnum =0;
    @Override
    public boolean pan(float p1, float p2, float p3, float p4) {
        if (loadingfile) return true;
        if (kartu.equalsIgnoreCase("game")){return true;}
        if (kartu.equalsIgnoreCase( "editor" ) && iseditGUI){



            Vector3 touch2 = new Vector3();
            uicam.unproject(touch2.set(p1, p2, 0));

            if (landscape){
                float width=selectedGUI.getWl()-selectedGUI.getXl();
                float height=selectedGUI.getHl()-selectedGUI.getYl();
                selectedGUI.setXl( (int) touch2.x *100 / ssy - width/2);
                selectedGUI.setWl( (int)  touch2.x *100 / ssy +width/2 );
                selectedGUI.setYl( (int)  touch2.y *100 / ssx -height/2);
                selectedGUI.setHl( (int)  touch2.y *100 / ssx +height/2 );
            }else{
                float width=selectedGUI.getW()-selectedGUI.getX();
                float height=selectedGUI.getH()-selectedGUI.getY();
                selectedGUI.setX( touch2.x  *100 / ssx-width/2);
                selectedGUI.setW( touch2.x *100 / ssx +width/2 );
                selectedGUI.setY( touch2.y  *100 / ssy -height/2);
                selectedGUI.setH( touch2.y *100 / ssy +height/2 );
            }
           // Gdx.app.log( "HEHE",p1+"/"+ ssx+" : "+ p2+"/"+ssy);

            return true;
        }
        //status(p1+"/"+p2+"/"+p3+"/"+p4,5);

        //this is for tile pickers
        if (stamp && (kartu == "tile" || (kartu == "pickanim" && tilePicker == "massprops"))) {
            Vector3 touch = new Vector3();
            tilecam.unproject(touch.set(p1, p2, 0));
            int ae = (int) touch.x;
            int ab = (int) touch.y;
            if (touch.y > 0) ab = 1;
            int posx = 0;
            int posy = 0;
            int initx = 0;
            int inity = 0;
            Integer num = -1;
            int usedtset;
            if (kartu == "tile") {
                usedtset = seltset;
            } else {
                usedtset = selTsetID;
            }
            int Tha = tilesets.get(usedtset).getHeight();
            int Twa = tilesets.get(usedtset).getWidth();
            int Tsh = tilesets.get(usedtset).getTileheight();
            int Tsw = tilesets.get(usedtset).getTilewidth();
            if (touch.y < Tsh && touch.y > -Tsh * Tha + Tsh && touch.x > 0 && touch.x < Tsw * Twa) {
                num = (Twa * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                posx = ae / Tsw;
                posy = ((-ab + Tsh) / Tsh);
                initx = initialSelect % Twa;
                inity = initialSelect / Twa;
            }
            if (num == -1) return true;
            //kanan bawah
            if (posx >= initx && posy >= inity) {
                startSelect = initialSelect;
                endSelect = num;

            }
            //kanan atas
            if (posx >= initx && posy < inity) {
                startSelect = posy * Twa + initx;
                endSelect = inity * Twa + posx;
            }
            //kiri bawah
            if (posx < initx && posy >= inity) {
                startSelect = inity * Twa + posx;
                endSelect = posy * Twa + initx;
            }
            //kiri atas
            if (posx < initx && posy < inity) {
                startSelect = num;
                endSelect = initialSelect;
            }
            return true;
        }

        //this is for map drawing
        if (roll) {
            usetool=true;
            Vector3 touch = new Vector3();
            cam.unproject(touch.set(p1, p2, 0));
            int ae = (int) touch.x;
            int ab = (int) touch.y;
            if (touch.y > 0) ab = 1;
            int posx = 0;
            int posy = 0;
            int initx = 0;
            int inity = 0;
            Integer num = -1;

            //so basically, find the tile id.
            if (orientation.equalsIgnoreCase("orthogonal")) {
                if (touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw) {
                    num = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
                }
            } else if (orientation.equalsIgnoreCase("isometric")) {

                //cool way to convert isometric to orthogonal, new iso tap detection
                int newae = ae - ((Tsw/Tsh) * ab) + Tsw/2;
                int newab = (ae / (Tsw/Tsh)) + ab -Tsh/2;
                num = (Tw * ((-newab + Tsh) / Tsh) + (newae / Tsw));
                if (num<0 || num >Tw*Th) return true;

                    /* old way

                float closest = 9999;
                for (int i = 0; i < Tw * Th; i++) {
                    int offsetx = 0, offsety = 0;
                    xpos = i % Tw;
                    ypos = i / Tw;
                    offsetx = (xpos * Tsw / 2) + (ypos * Tsw / 2);
                    offsety = (xpos * Tsh / 2) - (ypos * Tsh / 2);
                    int drawx = xpos * Tsw - offsetx;
                    int drawy = -ypos * Tsh - offsety;
                    int x = ae - drawx;
                    int y = ab - drawy;
                    int Tsws = Tsw / 2;
                    int Tshs = Tsh / 2;
                    float dx = Math.abs(x - Tsws);
                    float dy = Math.abs(y - Tshs);
                    if (dx / Tsws + dy / Tshs < closest) {
                        num = i;
                        closest = dx / Tsws + dy / Tshs;
                    }
                }

                     */
            }

            //if the tile ID cannot be found, just return.
            if (num == -1) return true;

            //brushing, basically other tools are not using pan,
            //remember that longpressing fill will turn it into brush?
            //log(prevnum+"|"+num);
            if (activetool == 4 & num != -1 & prevnum!=num) {
                prevnum=num;
                //small brush
                //tapTile(num, false, fal\size+"");
                for (int bx=0; bx<brushsize;bx++) {
                    for (int by = 0; by < brushsize; by++) {
                        //celorasu=in
                        int curx = num % Tw;
                        int cury = num / Tw;
                        final int locx = curx - brushsize / 2 + bx;
                        final int locy = cury - brushsize / 2 + by;
                        // Gdx.app.log( "", cury + "/" + curx + "/" + locy + "/" + locx );
                        if (locx < 0) continue;
                        if (locy < 0) continue;
                        if (locx >= Tw) continue;
                        if (locy >= Th) continue;
                        boolean terrar =false;
                        //if (bx==0 || by ==0 || bx==brushsize-1 || by==brushsize-1) terrar=true;

                        // this code halves the needed taptile with terrain true.
                        if (bx==0 && by==0) terrar = true;
                        if (bx==brushsize-1 && by==0) terrar = true;
                        if (bx==brushsize-1 && by==brushsize-1) terrar = true;
                        if (by==brushsize-1 && bx==0) terrar = true;
                        if (bx==0 && by % 2==0) terrar = true;
                        if (by==0 && bx % 2==0) terrar = true;
                        if (bx==brushsize-1 && by % 2==0) terrar = true;
                        if (by==brushsize-1 && bx % 2==0) terrar = true;

                        final boolean ft = terrar;
                        //log(ft+"");
                        //if (!ft) continue;

                        try {
                            autotilePool.execute( new Runnable(  ){
                                @Override
                                public void run() {
                                    tapTile(locy *Tw+locx, true,ft,false,curspr);

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //threadPool.waitUntilAllTasksFinished();
                    }
                }

                nofling=0.4f;
                velx=0;
                vely=0;
                return true;
            }

            //now finding pos x and y
            posx = num % Tw;
            posy = num / Tw;
            initx = mapinitialSelect % Tw;
            inity = mapinitialSelect / Tw;

            //kanan bawah
            if (posx >= initx && posy >= inity) {
                mapstartSelect = mapinitialSelect;
                mapendSelect = num;
                rising=false;
            }
            //kanan atas
            if (posx >= initx && posy < inity) {
                mapstartSelect = posy * Tw + initx;
                mapendSelect = inity * Tw + posx;
                rising=true;
            }
            //kiri bawah
            if (posx < initx && posy >= inity) {
                mapstartSelect = inity * Tw + posx;
                mapendSelect = posy * Tw + initx;
                rising=true;
            }
            //kiri atas
            if (posx < initx && posy < inity) {
                mapstartSelect = num;
                mapendSelect = mapinitialSelect;
                rising=false;
            }
            mapfinalSelect=num;
            return true;
        } else {

            //if not "roll", just move the screen then.
            drag = true;
            int buffer = Tsw + Tsh;
            float panspeed = (float) scrollspeed / 2;

            switch (kartu) {

                case "world":

                    float x = p3 * panspeed * cam.zoom;
                    float y = p4 * panspeed * cam.zoom;

                    cam.translate(-x, y);
                    //uicam.translate(-x, y);

                    int onset=0;
                    if (orientation.equalsIgnoreCase("isometric")){
                        onset=Tsw*Tw/2;
                    }
                    if (!orientation.equalsIgnoreCase("isometric")){
                    if (cam.position.x < 0-onset) cam.position.x = 0-onset;
                    if (cam.position.x > Tsw * Tw -onset) cam.position.x = Tsw * Tw -onset;
                    if (cam.position.y < -Tsh * Th) cam.position.y = -Tsh * Th;
                    if (cam.position.y > 0) cam.position.y = 0;
                    }

                    cam.update();
                    //uicam.update();
                    break;
                case "tile":
                case "pickanim":
                    if (tilesets.size() == 0) return true;
                    x = p3 * panspeed * tilecam.zoom;
                    y = p4 * panspeed * tilecam.zoom;
                    tilecam.translate(-x, y);
                    tileset t = tilesets.get(seltset);
                    if (kartu != "tile") {
                        switch (tilePicker) {
                            case "newimgobj":
                            case "props":
                            case "rnda":
                            case "rndb":
                            case "repa":
                            case "repb":

                                t = tilesets.get(seltset);
                        }
                    }

                    if (pickAuto){
                        if (tilecam.position.x < 0) tilecam.position.x = 0;
                        if (tilecam.position.x > 6 * t.getTilewidth())
                            tilecam.position.x = 6* t.getTilewidth();
                        if (tilecam.position.y > t.getTileheight())
                            tilecam.position.y = t.getTileheight();
                        if (tilecam.position.y < -(t.getTerrains().size() * t.getTileheight()) + t.getTileheight())
                            tilecam.position.y = -(t.getTerrains().size()  * t.getTileheight()) + t.getTileheight();

                    }else {
                        if (tilecam.position.x < 0) tilecam.position.x = 0;
                        if (tilecam.position.x > t.getWidth() * t.getTilewidth())
                            tilecam.position.x = t.getWidth() * t.getTilewidth();
                        if (tilecam.position.y > t.getTileheight())
                            tilecam.position.y = t.getTileheight();
                        if (tilecam.position.y < -(t.getHeight() * t.getTileheight()) + t.getTileheight())
                            tilecam.position.y = -(t.getHeight() * t.getTileheight()) + t.getTileheight();
                    }


                    tilecam.update();
            }
        }
        return true;
    }

    @Override
    public boolean panStop(float p1, float p2, int p3, int p4) {

        if (loadingfile) return true;
        if (kartu=="game") return true;
        //stop moving screen
        drag = false;

        //after finishing stamp selection, go to map
        if (stamp && kartu == "tile") {
            if (!issettingtile) {
                curspr = startSelect + tilesets.get(seltset).getFirstgid();
                kartu = "world";
                activetool = 0;
            }else{
                stamp = false;
                tileset ts = tilesets.get(seltset);
                int widih = endSelect % tilesets.get(seltset).getWidth() - startSelect % tilesets.get(seltset).getWidth() + 1;
                int heih = endSelect / tilesets.get(seltset).getWidth() - startSelect / tilesets.get(seltset).getWidth() + 1;

                for (int yy = 0; yy < heih; yy++) {
                    for (int xx = 0; xx < widih; xx++) {
                        int num = startSelect;
                        int nyum = num + xx + (yy * ts.getWidth());
                        if (nyum < ts.getHeight() * ts.getWidth()) {
                            if (nyum % ts.getWidth() >= num % ts.getWidth()) {
                                massprops.set(nyum, !massprops.get(nyum));
                            }
                        }

                    }
                }

            }
        }

        //mass image properties, I am not dealing with this now.
        if (stamp && kartu == "pickanim" && tilePicker == "massprops") {
            stamp = false;
            tileset ts = tilesets.get(selTsetID);
            int widih = endSelect % tilesets.get(selTsetID).getWidth() - startSelect % tilesets.get(selTsetID).getWidth() + 1;
            int heih = endSelect / tilesets.get(selTsetID).getWidth() - startSelect / tilesets.get(selTsetID).getWidth() + 1;

            for (int yy = 0; yy < heih; yy++) {
                for (int xx = 0; xx < widih; xx++) {
                    int num = startSelect;
                    int nyum = num + xx + (yy * ts.getWidth());
                    if (nyum < ts.getHeight() * ts.getWidth()) {
                        if (nyum % ts.getWidth() >= num % ts.getWidth()) {
                            if (massprops.get(nyum)) {
                                massprops.set(nyum, false);
                            } else {
                                massprops.set(nyum, true);
                            }

                        }
                    }

                }
            }
        }

        // after we roll the paint...
        if (roll) {

            //rectangle (0) and eraser (1) (as they are basically the same tool...)
            //they are drawn on panstop
            //while other tools:
            //brush (4) is on pan
            //fill (2) is on tap
            //copy (3) have their mapend and mapstart on pan, and click on tap

            // I think
            // copy should start here, end of pan, copy things to clipboard.
            // put it into some kind of clipboard instead just marking it


            //let's try, if I am using a copy tool and stop panning
            if (activetool == 3)
            {
                layer lay = new layer();
                Json json = new Json();

                String clip = json.toJson(layers.get(selLayer));

                lay = json.fromJson(layer.class, clip);
                lay.setName("CLIPBOARD"); //why not, lol.
                cliplayer = lay;
                clipsource=selLayer;

                //now I have the clipboard layer, just put it on tap...
            }

            //no more activetool =1
            if (activetool == 0) {

                int awidih = 0, aheih = 0, anum = 0;
                int widih = mapendSelect % Tw - mapstartSelect % Tw;
                int heih = mapendSelect / Tw - mapstartSelect / Tw;
                boolean follower = false;
                int num = mapstartSelect;
                int[] pon = new int[9];
                if (stamp) {

                    //get data from tileset
                    anum = startSelect + tilesets.get(seltset).getFirstgid();
                    int aTw = tilesets.get(seltset).getWidth();
                    awidih = endSelect % aTw - startSelect % aTw; //width of stamp
                    aheih = endSelect / aTw - startSelect / aTw; //height of stamp

                    //this stupidly weird code is to find the edges of stamp, for smart stamp
                    //stored in pon

                    for (int yy = 0; yy <= aheih; yy++) {
                        for (int xx = 0; xx <= awidih; xx++) {
                            int kodok = anum + xx + (yy * aTw);
                            if (xx == 0 && yy == 0) {
                                //topleft
                                pon[0] = kodok;
                                pon[1] = kodok;
                                pon[3] = kodok;
                            }
                            if (xx > 0 && xx < awidih && yy == 0) {
                                pon[1] = kodok;
                                pon[4] = kodok;
                            }
                            if (xx == awidih && yy == 0) {
                                pon[2] = kodok;
                                pon[5] = kodok;
                            }

                            if (xx == 0 && yy > 0 && yy < aheih) {
                                pon[3] = kodok;
                                pon[4] = kodok;
                            }
                            if (xx > 0 && xx < awidih && yy > 0 && yy < aheih) pon[4] = kodok;
                            if (xx == awidih && yy > 0 && yy < aheih) pon[5] = kodok;

                            if (xx == 0 && yy == aheih) {
                                pon[6] = kodok;
                                pon[7] = kodok;
                            }
                            if (xx > 0 && xx < awidih && yy == aheih) pon[7] = kodok;
                            if (xx == awidih && yy == aheih) pon[8] = kodok;

                        }
                    }
                   // status(pon[0]+"-"+pon[1]+"-"+pon[2]+"-"+pon[3]+"-"+pon[4]+"-"+pon[5]+"-"+pon[6]+"-"+pon[7]+"-"+pon[8],5);
                }

                //and then, draw the stamp here, check this later for stamp on isometric...
                //tag: isometric stamp, stamp iso, iso stamp, whatever...

                boolean firstone=true;
                for (int yy = 0; yy <= heih; yy++) {
                    for (int xx = 0; xx <= widih; xx++) {
                        if ((num + xx + (yy * Tw)) < Th * Tw) {
                            if ((num + xx + (yy * Tw)) % Tw >= num % Tw) {
                                int oldcurspr = curspr;
                                //ini buat yg smart stamp rect
                                if (stamp) {

                                    if (xx == 0 && yy == 0) curspr = pon[0];
                                    if (xx > 0 && xx < widih && yy == 0) curspr = pon[1];
                                    if (xx == widih && yy == 0) curspr = pon[2];

                                    if (xx == 0 && yy > 0 && yy < heih) curspr = pon[3];
                                    if (xx > 0 && xx < widih && yy > 0 && yy < heih)
                                        curspr = pon[4];
                                    if (xx == widih && yy > 0 && yy < heih) curspr = pon[5];

                                    if (xx == 0 && yy == heih) curspr = pon[6];
                                    if (xx > 0 && xx < widih && yy == heih) curspr = pon[7];
                                    if (xx == widih && yy == heih) curspr = pon[8];
                                }
                                    log(curspr+"");

                                switch (currentShape)
                                {
                                    case RECTANGLE:

                                            boolean terrar = false;
                                            //if (xx==0 || yy ==0 || xx==widih-1 || yy==heih-1) terrar=true;

                                            // this code halves the needed taptile with terrain true.

                                            if (xx == 0 && yy == 0) terrar = true;
                                            if (xx == widih && yy == 0) terrar = true;
                                            if (xx == widih && yy == heih) terrar = true;
                                            if (yy == heih && xx == 0) terrar = true;
                                            if (xx == 0 && yy % 2 == 0) terrar = true;
                                            if (yy == 0 && xx % 2 == 0) terrar = true;
                                            if (xx == widih && yy % 2 == 0) terrar = true;
                                            if (yy == heih && xx % 2 == 0) terrar = true;

                                            final boolean ft = terrar;
                                            final boolean firstonex = firstone;
                                            final int numa = num;
                                            final int xxx = xx;
                                            final int yyy = yy;
                                            final int Ttw = Tw;
                                            final int newcurspr = curspr;

                                        try {
                                                autotilePool.execute( new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        tapTile( numa + xxx + (yyy * Ttw), !firstonex, ft ,true,newcurspr);

                                                    }
                                                } );
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        //threadPool.waitUntilAllTasksFinished();

                                        if (firstone) firstone = false;
                                        break;
                                    case CIRCLE:
                                        float midx = widih/2+0.5f;
                                        float midy = heih/2+0.5f;
                                        float radx = Math.abs(midx-xx);
                                        float rady = Math.abs(midy-yy);
                                        double myrad =0;
                                        myrad = (midx > midy) ? midy : midx;
                                        double skor = (radx*radx+rady*rady);
                                        //double skor = Math.sqrt(Math.pow(radx,2)*Math.pow(rady,2));
                                        //skor = Math.pow(radx+rady,2)/Math.pow(myrad,2); //DIAMOND
                                       if (skor <=myrad*myrad) tapTile(num + xx + (yy * Tw), !firstone, true,false,curspr);
                                        if (firstone) firstone = false;
                                        break;

                                }

                                curspr = oldcurspr;
							/*
							int nyum=mapstartSelect + xx + (yy * Tw);
							if (activetool==0) long oi = curspr;
							if (activetool==1) long oi = 0;
							Long from = layers.get(selLayer).getStr().get(nyum);
							int tzet = layers.get(selLayer).getTset().get(nyum);

							layerhistory lh2=new layerhistory(follower,from,oi,nyum,selLayer,tzet);

							if(from!=oi){
								undolayer.add(lh2);
								redolayer.clear();
							}


							layers.get(selLayer).getStr().set(nyum, oi);
							layers.get(selLayer).getTset().set(nyum, seltset);
							follower=true;
							*/
                            }
                        }
                    }
                }//

                if (currentShape==ShapeTool.LINE) {
                    //backhere

                    boolean vert = heih > widih ? true : false;

                    if (vert)
                    {
                        firstone=true;
                        if (rising) {
                            for (int yy = 0; yy <= heih; yy++) {
                                int xx = widih-Math.round((float) yy / (float) heih * (float) widih);
                                tapTile(num + xx + (yy * Tw), !firstone, true,false,curspr);
                                if (firstone) firstone = false;
                            }
                        }else
                        {
                            for (int yy = 0; yy <= heih; yy++) {
                                int xx = Math.round((float) yy / (float) heih * (float) widih);
                                tapTile(num + xx + (yy * Tw), !firstone, true,false,curspr);
                                if (firstone) firstone = false;
                            }
                        }
                    }
                    else {
                        firstone=true;
                        if (rising) {
                            for (int xx = 0; xx <= widih; xx++) {
                                int yy = heih-Math.round((float) xx / (float) widih * (float) heih);
                                tapTile(num + xx + (yy * Tw), !firstone, true,false,curspr);
                                if (firstone) firstone = false;
                            }
                        }else
                        {
                            for (int xx = 0; xx <= widih; xx++) {
                                int yy = Math.round((float) xx / (float) widih * (float) heih);
                                tapTile(num + xx + (yy * Tw), !firstone, true,false,curspr);
                                if (firstone) firstone = false;
                            }
                        }
                    }

                }

            }
            roll = false;
            //updateMinimap();
        }
        if (activetool == 4) roll = false;
        return false;
    }

    @Override
    public boolean zoom(float p1, float p2) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
        if (kartu.equalsIgnoreCase("game")){return true;}
        nofling = .3f;
        velx = 0;
        vely = 0;
        float pa1 = p1.dst(p2);
        float pa2 = p3.dst(p4);
        float newx = (p3.x + p4.x) / 2;
        float newy = (p3.y + p4.y) / 2;
        if (prevx != 0) pan(newx, newy, newx - prevx, newy - prevy);
        prevx = (p3.x + p4.x) / 2;
        prevy = (p3.y + p4.y) / 2;
        //pan((p1.x+p2.x)/2,(p1.y+p2.y)/2,((p1.x+p2.x)/2)/((p3.x+p4.x)/2),((p1.y+p2.y)/2)/((p3.y+p4.y)/2));
        //zooming=true;
        if (cammode == "View only") {
            if (cam.zoom < zoomTreshold) {
                cammode = "";
            }
        } else {
            if (cam.zoom > zoomTreshold) {
                cammode = "View only";
                cacheTiles();
            }
        }
        if (kartu == "world") {
            cam.zoom = initialZoom * pa1 / pa2;

            if (cam.zoom > Tsw * 2) {
                cam.zoom = Tsw * 2;
            }
            if (cam.zoom < Tsw / 320f)//zoom in
            {
                cam.zoom = Tsw / 320f;
            }
            cam.update();
        } else if (kartu == "tile" || kartu == "pickanim") {
            tilecam.zoom = initialZoom * pa1 / pa2;

            if (tilecam.zoom > Tsw * 2) {
                tilecam.zoom = Tsw * 2;
            }
            if (tilecam.zoom < Tsw / 320f) {
                tilecam.zoom = Tsw / 320f;
            }
            tilecam.update();
        }
        // TODO: Implement this method
        //zooming=false;
        return false;
    }

    public Pixmap pixmapfromtexture(Texture texture, String key) {


        Texture tex = texture;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        TextureData textureData = tex.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = tex.getTextureData().consumePixmap();
        int ww = pixmap.getWidth();
        int hh = pixmap.getHeight();
        if (!isPowerOf2(pixmap.getWidth())) {
            //ww=powerize(ww);
        }
        if (!isPowerOf2(pixmap.getHeight())) {
            //hh=powerize(hh);
        }
        Pixmap pm2 = new Pixmap(ww, hh, Pixmap.Format.RGBA8888);

        if (key != "") {
            for (int y = 0; y < hh; y++) {
                for (int x = 0; x < ww; x++) {

                    com.badlogic.gdx.graphics.Color color = new com.badlogic.gdx.graphics.Color();
                    com.badlogic.gdx.graphics.Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
                    //	if (keycolor==pixmap.getPixel(x, y)){
                    if (key != null) {
                        if ((key + "ff").equalsIgnoreCase(color.toString())) {
                            //pm2.setBlending(Pixmap.Blending.None);
                            //pixmap.setColor(new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 0f));
                            //pixmap.fillRectangle(x, y, 1, 1);
                        } else {
                            pm2.setColor(color);
                            pm2.fillRectangle(x, y, 1, 1);
                        }
                    } else {
                        pm2.setColor(color);
                        pm2.fillRectangle(x, y, 1, 1);
                    }

                }
            }
            return pm2;
        } else {
            return pixmap;
        }

    }

    public Texture chromaKey(Texture texture, String key) {


        Texture tex = texture;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        TextureData textureData = tex.getTextureData();

        textureData.prepare();

        Pixmap pixmap = tex.getTextureData().consumePixmap();
        int ww = pixmap.getWidth();
        int hh = pixmap.getHeight();
        if (!isPowerOf2(pixmap.getWidth())) {
            //ww=powerize(ww);
        }
        if (!isPowerOf2(pixmap.getHeight())) {
            //hh=powerize(hh);
        }
        Pixmap pm2 = new Pixmap(ww, hh, Pixmap.Format.RGBA8888);
        for (int y = 0; y < hh; y++) {
            for (int x = 0; x < ww; x++) {

                com.badlogic.gdx.graphics.Color color = new com.badlogic.gdx.graphics.Color();
                com.badlogic.gdx.graphics.Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
                //	if (keycolor==pixmap.getPixel(x, y)){
                if (key != null) {
                    if ((key + "ff").equalsIgnoreCase(color.toString())) {
                        //pm2.setBlending(Pixmap.Blending.None);
                        //pixmap.setColor(new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 0f));
                        //pixmap.fillRectangle(x, y, 1, 1);
                    } else {
                        pm2.setColor(color);
                        pm2.fillRectangle(x, y, 1, 1);
                    }
                } else {
                    pm2.setColor(color);
                    pm2.fillRectangle(x, y, 1, 1);
                }

            }
        }

        tex = new Texture(pm2);
        pixmap.dispose();
        pm2.dispose();
        textureData.disposePixmap();
        return tex;
    }

    private int powerize(int ww) {
        if (ww < 16) {
            return 16;
        } else if (ww > 16 && ww < 32) {
            return 32;
        } else if (ww > 32 && ww < 64) {
            return 64;
        } else if (ww > 64 && ww < 128) {
            return 128;
        } else if (ww > 128 && ww < 256) {
            return 256;
        } else if (ww > 256 && ww < 512) {
            return 512;
        } else if (ww > 512 && ww < 1024) {
            return 1024;
        } else if (ww > 1024 && ww < 2048) {
            return 2048;
        } else if (ww > 2048 && ww < 4096) {
            return 4096;
        } else if (ww > 4096) {
            return 8192;
        }
        return 0;
    }

    int createPixel(int r, int g, int b) {
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }



}
