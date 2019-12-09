package com.mirwanda.nottiled;

import android.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.input.*;
import com.badlogic.gdx.input.GestureDetector.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.bitfire.postprocessing.*;
import com.bitfire.postprocessing.effects.*;
import com.bitfire.postprocessing.filters.*;
import com.bitfire.utils.*;
import com.mirwanda.libgdx.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import javax.microedition.khronos.opengles.*;
import org.xmlpull.v1.*;
import android.os.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import android.view.animation.*;
import android.speech.tts.*;
import com.badlogic.gdx.utils.async.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.assets.loaders.resolvers.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.Net.*;

public class MyGdxGame extends ApplicationAdapter implements GestureListener
{
	public CheckBox newcb[];
	public Tutorials tutor=new Tutorials();
	public Interface face;
	public guis gui=new guis();
	Boolean pv; String oldlang; Integer oldfontsize;
	public language z= new language();
	public drawer tempdrawer = new drawer();
	private int scrollspeed;
	public Bloom bloom;
    public Curvature curvature;
    public Zoomer zoomer;
    public CrtMonitor crt;
    public Vignette vignette;
    PostProcessor postProcessor;
	private static final boolean isDesktop = false;//(Gdx.app.getType() == Application.ApplicationType.Desktop);
	public boolean frompick;
	private Slider sdScrollSpeed;
	private SelectBox sbLanguage;
	private Online templates = new Online();
	private java.util.List<SpriteCache> caches = new ArrayList<SpriteCache>();
	private java.util.List<Integer> cacheIDs = new ArrayList<Integer>();
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
	private Texture background;
	private boolean sEnableBlending;

	private CheckBox cbEnableBlending;

	private String cammode="";
	private java.util.List<Boolean> massprops = new ArrayList<Boolean>();
	private int mapstartSelect;

	private int mapendSelect;

	private int mapinitialSelect;

	private String errors;

	private boolean sShowGIDmap;

	private boolean caching;

	private TextButton bPropTemplate;
	private TextButton bPropParse;

	private Table tpt;

	private int brushsize=1;

	private String language;

	private boolean landscape;

	private int redux;

	private int reduy;

	private TextButton bLinks;

	private TextButton bVideos;

	private String wl;

	private TextButton bTsPropCustomProp;

	private String oldowner;

	private property tempe;

	private boolean propertystart;

	private boolean alreadyloaded;

	private String intend;

	private recents recents;

	private Texture txnumbers;

	private FrameBuffer fbo;

	private boolean takingss;

	private Stage hstage;

	private Stage vstage;

	private Dialog dialog;

	private Texture txrectangle;
	private Texture txmenu,txmap,txundo,txredo,txtile,txauto,txlayer,txautopick,txcenter;
	private Texture txstamp,txadd,txdelete,txinfo, txtiles;

	private TextButton bMassAddProp, bTileCollision;

	private int zoomTreshold;

	private TextField fzoomtresh;

	private CheckBox cball;

	private Table bigman;

	private boolean zooming;
	private float autosave=0f;

	private Table trandomgen,treplacetiles;

	private TextField ffirstgen;
	Slider slfirstgen;

	private TextField fgencount;

	private TextField fbirthlim;

	private TextField fdeathlim;

	private TextField flivestr,fprevstr;

	private TextField flivetset;

	private TextField fdeadstr,fnextstr;

	private TextField fdeadtset;

	private boolean firstrun=true;

	private Texture txlauncher;

	private String sBgcolor;

	private float prevx=0;

	private float prevy=0;

	private float velx;

	private float vely;

	private float velredx;

	private float velredy;

	private int fontsize;

	private TextField fFontsize;

	private float nofling;
	//Animation<TextureRegion> animation;
    float elapsed;
	String temproname="";
	String temprotype="";
	String temprovalue="";
	
	//private AdView ads;
	public MyGdxGame(String intend,Interface face)//, AdView ads)
	{
		this.intend = intend;
		this.face=face;
		//this.ads= ads;
	}
	@Override
	public void pinchStop()
	{
		prevx=0;
		prevy=0;
		// TODO: Implement this method
	}

//////////////////////////////////////////////////////
//            VARIABLES
//////////////////////////////////////////////////////
	float delta;
	String debugMe=" ", debugYou=" ";Table lastStage;
	String sender; int senderID; //custom properties
	int selTsetID; //tiles
	String fps="";float keydown=0f;boolean backing;
	String shapeName="rectangle",rotationName="0",toolName="Tile",viewModeName="Stack",objViewModeName="All";
	int magnet=1;String magnetName="lock";
	int activetool=0,activeobjtool=0;
	float blink=0;boolean turun=false;
	String info; obj copyobj=null;
	boolean sShowGrid=true, sShowFPS, sAutoSave, sSaveTsx=false, sShowGID=false;
	boolean sShowCustomGrid=false;
	boolean sResizeTiles=false;
	int sGridX,sGridY;
	boolean anime=false,deletinganim=false;
	float bgr,bgg,bgb;
	String renderorder="right-down"; String orientation="orthogonal";
	java.util.List<Long> spr;
	int viewMode; int objviewMode; int selTileID=-1; int tempframeid;
	int templastID;
	int Tsw =32, Tsh=32, Tw=5, Th=5; 
	int rotate=0;
	int ssx =480; int ssy=800;
	int btnx=440, btny=50;
	int jon,joni;
	int selat;
	int startSelect,endSelect,initialSelect;
	boolean tutoring=false;
	int activetutor=0;
	String mapFormat="csv",tsxFile="",activeFilename;
	String kartu="",mode,lastpath,openedfile,tilePicker="",saveasdir;
	int curspr, curid; obj curobj; String curgroup="default";
	int selgroup=0, seltile=0, oedit=0, ogroup=0, seltset=0;
	String encoding="";String compression="";
	String texFile ="",curdir="/sdcard/Assets/", curfile="";
	SpriteBatch batch,ui;
	myShapeRenderer uis;
	ShapeRendererPlus sr;
	OrthographicCamera cam,uicam,tilecam;
	GestureDetector gd; 
	boolean drag, roll,stamp=false;
	BitmapFont str1;
	decoder decoder=new decoder();
	private Stage stage;
	Skin skin;
	java.util.List<property>properties  = new ArrayList<property>();
	java.util.List<objgroup> objgroups = new ArrayList<objgroup>();
	java.util.List<layer> layers = new ArrayList<layer>();
	java.util.List<tileset> tilesets = new ArrayList<tileset>();
	java.util.List<drawer> drawers = new ArrayList<drawer>(); 
	java.util.List<autotile> autotiles = new ArrayList<autotile>();
	
	java.util.List<TextField> tf= new ArrayList<TextField>();
	java.util.List<Label> tla = new ArrayList<Label>();
	java.util.List<layerhistory> undolayer = new ArrayList<layerhistory>();
	java.util.List<layerhistory> redolayer = new ArrayList<layerhistory>();
	private Texture txpencil;
	private Texture txeraser;
	private Texture txfill;
	private Texture txcopy;
	private Texture txbrush;
	private Texture txlock;
	private Texture txunlock;
	
	layer tempLayer; tileset tempTset;
	float initialZoom;

	CheckBox cbShowGrid,cbShowFPS,cbShowGid, cbAutoSave, cbShowGidmap, cbResize; TextButton bBack3;
	CheckBox cbShowCustomGrid;
	TextField fGridX,fGridY,fBgcolor;
	Table tObjProp;
	TextButton bApply,bCancel,bRemove,bProps;
	
	Table tNF, tMP;
	com.badlogic.gdx.Input.TextInputListener pSaveAs;
	
	//nf
	TextField fNFilename, fNCurdir, fNTsw,fNTsh,fNTw,fNTh;
	SelectBox sbNMapFormat;
	SelectBox sbNMapRenderOrder;
	SelectBox sbNMapOrientation;
	TextButton bNSelDir, bNNew,bNNewplus,bNCancel;
	
	//mp
	TextField fFilename, fCurdir, fTsw,fTsh,fTw,fTh,fTsx;
	SelectBox sbMapFormat;
	SelectBox sbMapRenderOrder;
	SelectBox sbMapOrientation;
	CheckBox cbUseTsx;
	TextButton bUseTsx,bApplyMP, bCancelMP, bPropertiesMap;
	
	ChangeListener listBack; 
	Table tMenu,tOpen,tNewFile,tSaveAs,tLicense,tTutorial;
	TextButton bNew,bOpen,bSave,bSaveAs,bExit,bBack,bLicense,bReload,bTutorial;
	ImageButton bcc; TextButton btiled; 
	TextButton bTutorOK,bTutorBack;
	
	Table tMap,tLayerMgmt,tTileMgmt,tObjMgmt,tFrameMgmt,tPropsMgmt,tPreference,tProperties,tTsetMgmt,tAutoMgmt,tAutoform;
	TextButton bTileMgmt,bTileSettingsMgmt,bObjMgmt,bPreference,bProperties,bTsetMgmt,bBack2,bAutoMgmt;
	Table tRecent;
	TextButton bRecent, bRecentOpen,bRecentBack;
	com.badlogic.gdx.scenes.scene2d.ui.List<String> lrecentlist;
	com.badlogic.gdx.scenes.scene2d.ui.List<String> ltutorial;
	
	Table tTemplate, tOnline;
	com.badlogic.gdx.scenes.scene2d.ui.List<String> ltemplate;
	com.badlogic.gdx.scenes.scene2d.ui.List<String> lonline;
	TextButton bTmplOK, bTmplBack, bTmplDownload;
	TextButton bOnlineDownload, bOnlineBack, bOnlineRefresh;
	
	Table tImport;
	TextField fImportWidth;
	TextField fImportHeight;
	CheckBox cImportEmbed;
	TextButton bImportOK;
	
	
	Table tLinks;
	TextButton bRusted,bWardate,bManual;
	com.badlogic.gdx.scenes.scene2d.ui.List<String> lautolist;
	TextButton bAutoadd, bAutoprops, bAutoload, bAutosave, bAutorename, bAutoremove, bAutomoveup, bAutoback;
	com.badlogic.gdx.Input.TextInputListener pAutoadd;
	com.badlogic.gdx.Input.TextInputListener pAutorename;
	
	
	Table tPropEditor;
	TextField fPropName; SelectBox sbPropType,sbPropValbool;
	TextArea fPropVal; //str,int,float,color
	TextButton bPropValfile, bPropApply, bPropCancel,bPropCopy,bPropPaste,bPropGid,bProppng;
	String clipProp="",clipobjcpy="";
	
	com.badlogic.gdx.scenes.scene2d.ui.List<String> llayerlist;
	com.badlogic.gdx.Input.TextInputListener pNewLayer;
	com.badlogic.gdx.Input.TextInputListener pNewLayerSC;
	
	com.badlogic.gdx.Input.TextInputListener pEditLayer;
	com.badlogic.gdx.Input.TextInputListener pSetOpacity;
	TextButton bAddLayer, bRemoveLayer,bMoveLayer, bEditLayer,bBackLayer,bSetOpacity;

	com.badlogic.gdx.scenes.scene2d.ui.List<String> lproplist;
	com.badlogic.gdx.Input.TextInputListener pNewProp;String tempNameNew;
	com.badlogic.gdx.Input.TextInputListener pEditProp;String tempNameEdit;
	com.badlogic.gdx.Input.TextInputListener pNewProp2;Label lPropID;
	com.badlogic.gdx.Input.TextInputListener pEditProp2;
	TextButton bAddProp, bRemoveProp,bMoveProp, bEditProp,bBackProp;

	com.badlogic.gdx.scenes.scene2d.ui.List<String> ltsetlist;
	TextButton bAddTset, bRemoveTset,bMoveTset, bBackTset,bPropTset,bImportFolder;

	com.badlogic.gdx.scenes.scene2d.ui.List<String> lobjlist;
	com.badlogic.gdx.Input.TextInputListener pNewObjLayer;
	com.badlogic.gdx.Input.TextInputListener pNewObjLayerSC;
	
	com.badlogic.gdx.Input.TextInputListener pEditObjLayer;
	TextButton bAddObjLayer, bRemoveObjLayer,bMoveObjLayer, bEditObjLayer,bBackObjLayer;

	com.badlogic.gdx.scenes.scene2d.ui.List<String> ltilelist;
	TextButton bAddTileLayer, bTileAnimations,bReplaceTileLayer, bRemoveTileLayer, bMoveTileLayer,bBackTileLayer,bPropsTileLayer,bTerrainEditor;
	com.badlogic.gdx.Input.TextInputListener pNewTerrain;
	
	com.badlogic.gdx.scenes.scene2d.ui.List<String> lframelist;
	com.badlogic.gdx.Input.TextInputListener pNewFrame;
	com.badlogic.gdx.Input.TextInputListener pEditFrame;
	TextField fDurationframe;
	TextButton bAddFrameLayer, bEditFrameLayer, bReplaceFrameLayer, bRemoveFrameLayer, bMoveFrameLayer,bBackFrameLayer, bDuration;
	Image iFrameView;Label lFrameID;

	Table tTsProp;
	TextButton bTsPropOK,bTsPropCancel,bTsPropSaveAsTsx,bTsPropChangeSource;
	TextField fTsPropName, fTsPropSource, fTsPropTrans;
	TextField fTsPropSpacing,fTsPropMargin,fTsPropTsxFile;
	TextField fTsPropTsw, fTsPropTsh, fTsPropTc, fTsPropCols;
	CheckBox cbTsPropUseTsx;
	
	
	
	FileChooser fcOpen,fcSaveAs;
	private XmlPullParserFactory xmlFactoryObject;
	private XmlPullParser myParser;
	Preferences prefs;
	String extRoot="/sdcard/";
	boolean loadingfile;
	boolean bypassads=false;

	//fastah mastah
	java.util.List <tile> tiles;
	int sprX,sprY,margin,spacing;
	int xpos,ypos;
	float camA,camB,camC,camD;
	String hex,trailer;
	
	AsyncExecutor asyncExecutor = new AsyncExecutor(10);
	AsyncResult<Void> task;
	
	final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tffint= new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();
	final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tfffloat = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter (){
			public boolean acceptChar(TextField p1,char c){
				if (Character.toString(c).matches("[0-9.-]+")){
					return true;
				}
				return false;
			}
		};

	final com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter tffcolor = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter (){
			public boolean acceptChar(TextField p1,char c){
				if (Character.toString(c).matches("[a-fA-F0-9#]+")){
					return true;
				}
				return false;
			}
		};
	
	obj newobject=new obj();
    com.badlogic.gdx.Input.TextInputListener pnewtextobject = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				newobject.setText(input);
				
			}
			@Override
			public void canceled()
			{}

	};

	private float statustimeout;

	private int newTerrainID;
	private boolean n;
	AssetManager manager = new AssetManager();
	String vers;
	
	
//////////////////////////////////////////////////////
//            APP CYCLE
//////////////////////////////////////////////////////
	@Override
	public void create()
	{
		nullTable=new Table();
		vers=face.getVersione();
		prefs = Gdx.app.getPreferences("My Preferences");
		language=prefs.getString("language","English");
		fontsize=prefs.getInteger("fontsize",0);
		
		
		try{
			reloadLanguage();

		}catch(Exception e)
		{
			ErrorBung(e,"langeror.txt");
			language="English";
			prefs.putString("language","English");
			reloadLanguage();
		}
		gd=new GestureDetector(this);
		initSD();
		loadGdxStuff();
		loadMenuMap();
		loadPreferences();
		initErrorHandling();
		loadOpen();
		loadLicense();
		loadTsetManagement();
		loadLayerManagement();
		loadObjectManagement();
		loadTileManagement();
		loadFrameManagement();
		loadAutoManagement();
		loadTools();
		loadImport();
		loadObjProp();
		loadMapProperties();
		loadTilesetProperties();
		loadPropsManagement();
		loadOnline();
		loadTemplate();
		loadNewFile();
		loadPropEditor();
		loadPropTemplate();
		initializePostProcessor();
		//animation = GifDecoder.loadGIFAnimation(2, Gdx.files.external("loading.gif").read());
		manager.setLoader(
            TmxMap.class,
            new mapLoader(
				new ExternalFileHandleResolver()
            )
		);
		
	}
	
	
	
	private void asyncinitiation()
	{
		loadingfile=true;
		//manager.load(new AssetDescriptor<TmxMap>( "data/colors.txt", TmxMap.class, new mapLoader.mapParameter()));
		
		
		task = asyncExecutor.submit(new com.badlogic.gdx.utils.async.AsyncTask<Void>() {
				public Void call() {
					initallthings();
					return null;
				} 
			});
		
	}
	
	private void initallthings()
	{
		try
		{
			
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
			
		}
		catch (Exception e)
		{
			ErrorBung(e,"errorloginit.txt");
		}
	}

	
	
	
	public void showCredits()
	{
		Table boss=new Table();
		Table table=new Table();
		table.center();
		table.defaults().height(btny);
		boss.setWidth(btnx);
		boss.setFillParent(true);
		ScrollPane spp = new ScrollPane(table);


		FileHandle handle = Gdx.files.internal("credits.txt");

		String text = handle.readString();
		String []wordsArray = text.replace("\r\n","\n").replace("\r","\n").split("\n",-1);

		for(int i=0;i<wordsArray.length;i++)
		{
			String[] h = wordsArray[i].split(">");
			switch (h[0])
			{
				case "l":
					Label lbl=new Label(h[1],skin);
					lbl.setWrap(true);
					lbl.setColor(1,1,0,1);
					lbl.setAlignment(Align.center);
					table.add(lbl).width(btnx).padBottom(10).row();
					break;
				case "m":
					lbl=new Label(h[1],skin);
					lbl.setWrap(true);
					lbl.setColor(1,1,0,1);
					lbl.setAlignment(Align.center);
					table.add(lbl).width(btnx).padTop(5).row();
					break;
				case "s":
					lbl=new Label(h[1],skin);
					lbl.setWrap(true);
					lbl.setAlignment(Align.center);
					table.add(lbl).width(btnx).row();
					break;
			}
		}

		TextButton back=new TextButton(z.back,skin);
		back.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();

				}
			});
		table.add(back).padTop(10).row();
		boss.add(spp);
		gotoStage(boss);
	}
	public void defaultlang(language lang){
		lang.menu="???";
		lang.map="???";
		lang.select="???";
		lang.rotate="???";
		lang.flip="???";
		lang.layer="???";
		lang.activelayer="???";
		lang.visibility="???";
		lang.tile="???";
		lang.object="???";
		lang.all="???";
		lang.stack="???";
		lang.single="???";
		lang.free="???";
		lang.lock="???";
		lang.activeobjecttool="???";
		lang.rectangle="???";
		lang.ellipse="???";
		lang.point="???";
		lang.polygon="???";
		lang.polyline="???";
		lang.text="???";
		lang.image="???";
		lang.copypaste="???";
		lang.eraser="???";

		//menu
		lang.supportnottiled="???";
		lang.newfile="???";
		lang.open="???"; 
		lang.save="???";
		lang.saveas="???";
		lang.discordserver="???";
		lang.whatsappgroup="???";
		lang.license="???";
		lang.reloadsamples="???";
		lang.copytorustedwarfare="???";
		lang.exit="???";
		lang.back="???";

		//map
		lang.preferences="???";
		lang.background="???";
		lang.manage="???";
		lang.management="???";

		//new
		lang.filename="???";
		lang.directory="???";
		lang.selectfolder="???";
		lang.mapformat="???";
		lang.renderorder="???";
		lang.orientation="???";
		lang.createnewfile="???";
		lang.cancel="???";
		lang.opentmxfile="???";
		lang.ok="???";

		//messages
		lang.info="???";
		lang.yourmaphasbeensaved="???";
		lang.selectnewlocation="???";
		lang.sampleshasbeenreloaded="???";
		lang.mapsenttorustedwatfare="???";

		//prefs
		lang.enableblending="???";
		lang.showgrid="???";
		lang.showfps="???";
		lang.showgidinpicker="???";
		lang.showgidinmap="???";
		lang.scrollspeed="???";
		lang.showcustomgrid="???";
		lang.gridx="???";
		lang.gridy="???";
		lang.openimagefile="???";
		lang.customproperties="???";
		lang.apply="???";

		//base
		lang.autotile="???";
		lang.tileset="???";
		lang.tilelayer="???";
		lang.objectgroup="???";
		lang.property="???";
		lang.tiles="???";
		lang.tilesetting="???";
		lang.animation="???";
		lang.terraineditor="???";
		lang.addnew="???";
		lang.rename="???";
		lang.properties="???";
		lang.moveup="???";
		lang.remove="???";
		lang.name="???";

		lang.template="???";
		lang.type="???";
		lang.value="???";
		lang.tilepicker="???";
		lang.copyall="???";
		lang.setopacity="???";

		lang.addimagetileset="???";
		lang.importtsxfile="???";
		lang.importfolder="???";
		lang.saveastsx="???";

		lang.set="???";
		lang.duration="???";
		lang.frame="???";
		lang.replace="???";
		lang.edit="???";
		lang.default_="???";

		lang.source="???";
		lang.tsxfile="???";
		lang.usetsxfile="???";
		lang.keycolor="???";
		lang.tilecolumn="???";
		lang.tilecount="???";
		lang.spacing="???";
		lang.margin="???";

		lang.id="???";
		lang.x="???";
		lang.y="???";
		lang.width="???";
		lang.height="???";
		lang.rotation="???";
		lang.language="???";
		
	}
	
	float timeToPan, panTargetX, panTargetY, panTargetZoom, panOriginX, panOriginY, panOriginZoom, panDuration;
	float timeToPan2, panTargetZoom2, panOriginZoom2, panDuration2;
	
	int panType=0;
	
	public void panTileTo (float x, float y, float duration){
		panOriginX = tilecam.position.x;
		panOriginY = tilecam.position.y;
		panOriginZoom = panTargetZoom = tilecam.zoom;
		panTargetX=x;
		panTargetY=y;
		timeToPan = panDuration = duration;
		panType=1;
	}

	public void panTo (float x, float y, float z, float duration){
		panOriginX = cam.position.x;
		panOriginY = cam.position.y;
		panOriginZoom=cam.zoom;
		panTargetX=x;
		panTargetY=y;
		panTargetZoom=z;
		timeToPan = panDuration = duration;
		panType=0;
	}
	
	public void uiAnim (){
		panOriginZoom2=.5f;
		panTargetZoom2=1;
		timeToPan2 = panDuration2 = 1f;
	}
	
	@Override
	public void render()
	{        
		try
		{
			if (firstrun)
			{
				asyncinitiation();
				firstrun=false;
			}
			if (loadingfile){
				//if(!task.isDone()) {
				Gdx.gl.glEnable(GL10.GL_BLEND);
				Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				
				Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				ui.setProjectionMatrix(uicam.combined);
				ui.begin();
				if (landscape)
				{
					ui.draw(txlauncher,(ssy/2)-(156/2),(ssx/2)-(156/2)+50,156,156);
					str1.draw(ui, z.loadingfiles, 0,(ssx/2)-80+50,ssy,Align.center,true);
					str1.draw(ui, vers, 0,(ssx/2)-480+50,ssy,Align.center,true);
					
				}else{
					
					ui.draw(txlauncher,(ssx/2)-(156/2),(ssy/2)-(156/2)+50,156,156);
					str1.draw(ui, z.loadingfiles, 0,(ssy/2)-80+50,ssx,Align.center,true);
					str1.draw(ui, vers, 0,(ssy/2)-280+50,ssx,Align.center,true);
					
				}
				
				ui.end();
				
			}else
			{
				
				Gdx.gl.glClearColor(bgr,bgg,bgb,1f);
			clsEnter();
				if (firstload>0) 
				{
					firstload-=1;
					Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
					return;
				}
				if (firstload==0)
				{
					uiAnim();
					firstload=-1;
				}
			
				if (timeToPan >= 0){
					timeToPan -= delta;
					float progress = timeToPan < 0 ? 1 : 1f - timeToPan / panDuration;
					OrthographicCamera cammy;
					switch (panType)
					{
						case 1:
						cammy=tilecam;
						break;
						case 2:
						cammy = uicam;
						break;
						default:
						cammy=cam;
					}
					cammy.position.x = Interpolation.fade.apply(panOriginX, panTargetX, progress);     
					cammy.position.y = Interpolation.fade.apply(panOriginY, panTargetY, progress);     
					cammy.zoom = Interpolation.fade.apply(panOriginZoom, panTargetZoom, progress);     
					cammy.update();
				}
				
				
			autosave+=delta;
			
			if (autosave >60f)
			{
				autosave=0;
				if (sAutoSave)
				{
					saveMap(curdir+"/"+curfile);
					status(z.autosaving,1);
				}
			}
			animate(delta);
			if (nofling>0) nofling-=delta;
			switch (kartu)
			{
				case "world":
					//crt.setEnabled(false);
					bloom.setEnabled(false);
					Gdx.gl.glEnable(GL10.GL_BLEND);
					Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					
					postProcessor.capture();
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
					batch.totalRenderCalls=0;
					drawTiles();
					drawGrid();
					drawObjects();
					drawObjectsInfo();
					postProcessor.render();
					drawWorldUI();
					
					drawstage(delta);
					break;
				case "stage":
					//crt.setEnabled(true);
					bloom.setEnabled(true);
					
					Gdx.gl.glEnable(GL10.GL_BLEND);
					Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					
					postProcessor.capture();
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
					batch.totalRenderCalls=0;
					drawTiles();
					//drawGrid();
					postProcessor.render();
					//drawObjects();
					//drawObjectsInfo();
					//drawWorldUI();
					drawstage(delta);
					break;
				case "tile":case "pickanim":case "pick":
					Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
					drawpicker();
					break;

				//case "stage":
					
					//break;
			}

			keyinput(delta);
				if (timeToPan2 >= 0){
					timeToPan2 -= delta;
					float progress = timeToPan2 < 0 ? 1 : 1f - timeToPan2 / panDuration2;
					uicam.zoom = Interpolation.fade.apply(panOriginZoom2, panTargetZoom2, progress);    

					uicam.update();
					uis.setProjectionMatrix(uicam.combined);
					uis.begin(ShapeRenderer.ShapeType.Filled);
					Gdx.gl.glEnable(GL10.GL_BLEND);
					Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

					float alfa = timeToPan2;
					Color cc = new Color(0,0,0,alfa);
					uis.rect (0f,0f,(float)ssx,(float)ssy,cc,cc,cc,cc);
					uis.end();

				}
				
				if (firstload>0) 
				{
					uis.setProjectionMatrix(uicam.combined);
					uis.begin(ShapeRenderer.ShapeType.Filled);
					Gdx.gl.glEnable(GL10.GL_BLEND);
					Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

					Color cc = new Color(0,0,0,1);
					uis.rect (0f,0f,(float)ssx,(float)ssy,cc,cc,cc,cc);
					uis.end();
					firstload-=1;
				}
				
				
			}
		}
		catch(Exception e)
		{
			
			if (batch.isDrawing()) batch.end();
			if (ui.isDrawing()) ui.end();
			if (sr.isDrawing()) sr.end();
			if (uis.isDrawing()) uis.end();
			
			ErrorBung(e,"errorlog.txt");
			status("Error loading tmx file",5);
			
			//newtmxfile(false);
		}
	    
	}

	void initializePostProcessor() { //I DONT EVEN KNOW WHAT THESE MEANS LOL
	ShaderLoader.BasePath = "data/shaders/";
	postProcessor = new PostProcessor(false, true, isDesktop);

	postProcessor.setClearColor(.5f,.5f,.5f,1);
        int vpW = Gdx.graphics.getWidth();
        int vpH = Gdx.graphics.getHeight();
        // create the effects you want
        bloom = new Bloom((int) (Gdx.graphics.getWidth() * 0.25f), (int) (Gdx.graphics.getHeight() * 0.25f));
	curvature = new Curvature();
	zoomer = new Zoomer(vpW, vpH, isDesktop ? RadialBlur.Quality.VeryHigh : RadialBlur.Quality.Low);
	int effects = CrtScreen.Effect.TweakContrast.v | CrtScreen.Effect.PhosphorVibrance.v | CrtScreen.Effect.Scanlines.v | CrtScreen.Effect.Tint.v;
        crt = new CrtMonitor(vpW, vpH, false, false, CrtScreen.RgbMode.ChromaticAberrations, effects);
		crt.setTint(0.8f,0.8f,.8f);
        Combine combine = crt.getCombinePass();
        combine.setSource1Intensity(0f);
        combine.setSource2Intensity(1f);
        combine.setSource1Saturation(0f);
			combine.setSource2Saturation(1f);
			vignette = new Vignette(vpW, vpH, false);
			// add them to the postprocessor
			postProcessor.addEffect(curvature);
        postProcessor.addEffect(zoomer);
			postProcessor.addEffect(vignette);
        postProcessor.addEffect(crt);
        postProcessor.addEffect(bloom);
        bloom.setBaseIntesity(0.0f);
		bloom.setBlurAmount(5);
	zoomer.setBlurStrength(-0.1f);
	zoomer.setOrigin(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	curvature.setZoom(1f);
        vignette.setIntensity(1f);
			bloom.setEnabled(false);
        crt.setEnabled(false);
			vignette.setEnabled(false);
        curvature.setEnabled(false);
        zoomer.setEnabled(false);
			}
			
	private void clsEnter()
	{
		delta = Gdx.graphics.getDeltaTime();
		fps = Integer.toString(Gdx.graphics.getFramesPerSecond());
		
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

	public void animate(float delta){
		if (turun){
			blink-=0.02f;
		}else{
			blink+=0.02f;
		}
		if (blink>=1) {
			blink=1;
			turun=true;
		}
		if (blink <=0){
			blink=0;
			turun=false;
		}

		if (tilesets.size()>0 && !deletinganim){
			for (int g=0;g<tilesets.size();g++){
				tiles = tilesets.get(g).getTiles();
				if (tiles.size() > 0)
				{
					for (int i=0;i < tiles.size();i++)
					{
						if (tiles.get(i).getAnimation().size() > 0)
						{
							tiles.get(i).addTimer(delta);
							int actFrame=tiles.get(i).getActiveFrameIndex();
							if (tiles.get(i).getTimer() >= (float) tiles.get(i).getActiveFrameDuration() / 1000)
							{
								tiles.get(i).setTimer(0);
								tiles.get(i).setActiveFrameIndex(tiles.get(i).getActiveFrameIndex() + 1);
								if (tiles.get(i).getActiveFrameIndex() >= tiles.get(i).getAnimation().size())
								{
									tiles.get(i).setActiveFrameIndex(0);
								}
								int newActframe=tiles.get(i).getActiveFrameIndex();
								if (!deletinganim) tiles.get(i).setActiveFrameID(tiles.get(i).getAnimation().get(newActframe).getTileID());
								if (!deletinganim) tiles.get(i).setActiveFrameDuration(tiles.get(i).getAnimation().get(newActframe).getDuration());

							}
						}
					}
				}//if
			}//for
		}//if 
	}
	
	void keyinput(float delta){
		keydown -= delta;
		if (keydown <= 0) keydown = 0;
		if (keydown == 0 && lastStage != null && backing)
		{
			if (lastStage == tFrameMgmt)
			{
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
				selTileID = -1;anime=false;
				gotoStage(tTileMgmt);

			}
			else if (lastStage == tPropsMgmt)
			{
				switch (sender){
					case "object":
						gotoStage(tObjProp);
						break;
					case "tile":
						gotoStage(tTileMgmt);
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
			else if (lastStage == tTsProp)
			{
				int dexo=ltsetlist.getSelectedIndex();
				int saiz=tilesets.size();
				String[] srr = new String[saiz];
				for (int i=0;i < saiz;i++)
				{
					srr[i] = tilesets.get(i).getName();
				}
				ltsetlist.setItems(srr);
				ltsetlist.setSelectedIndex(dexo);
				if (frompick)
				{
					onToPicker();
					lastStage=null;
				}else{
					gotoStage(tTsetMgmt);
				}
				//gotoStage(tTsetMgmt);
			}
			else if (lastStage == tTileMgmt)
			{
				if (frompick)
				{
					onToPicker();
					frompick=false;
					lastStage=null;
				}else{
					gotoStage(tTsetMgmt);
				}
			}
			else if (lastStage == trandomgen)
			{
				gotoStage(ttools);
			}
			else if (lastStage == tpt)
			{
				gotoStage(tPropsMgmt);
			}else
			{
				backToMap();
				lastStage = null;
			}

			backing = false;
			return;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.BACK))
		{
			if (mode=="newpoly") {
				if(newobject.getPointsSize()==1){
					newobject.setShape("point");
				}
				if(newobject.getPointsSize()==2){
					newobject.setShape("polyline");
				}
				mode="object";
			}
			if (lastStage != null)
			{
				keydown = 0.1f;
				backing = true;
			}
			if (kartu.equalsIgnoreCase("tile")){
				backToMap();
			}
			if (kartu.equalsIgnoreCase("world")){
				roll=false;stamp=false;
			}
		}
		
		if (velx>10) velx=velx/1.2f;
		if (velx<-10) velx=velx/1.2f;
		if (vely>10) vely=vely/1.2f;
		if (vely<-10) vely=vely/1.2f;
		if (velx<=10&&velx>=-10) velx=0;
		if (vely<=10&&vely>=-10) vely=0;
		if (!(velx==0&&vely==0)) 
		{
			pan(0,0,velx/50,vely/50);
		}else
		{
			drag=false;
		}
			
		
		
	}
	private void drawstage(float delta)
	{
		stage.act(delta);
		stage.draw();
		ui.setProjectionMatrix(uicam.combined);
		ui.begin();
		long ini =selTileID;

		if (selTileID != -1 && tilesets.size()>0 && anime)
		{
			if (tilesets.size()>0){
					tileset ts=tilesets.get(selTsetID);
					tiles = tilesets.get(selTsetID).getTiles();
					if (tiles.get(selTileID).getAnimation().size() > 0)
					{

						ini = tiles.get(selTileID).getActiveFrameID();
						
						int xpos = 160;
						int ypos = -100;
						int xpos2 = (int) ini % ts.getWidth();
						int ypos2 = (int) ini / ts.getWidth();
						
						ui.draw(ts.getTexture(), xpos, ypos, xpos2 * ts.getTilewidth(), ypos2 * ts.getTileheight(), ts.getTilewidth(), ts.getTileheight());	

					}
					else
					{
						//ini = layers.get(selAnim).getStr() .get(animations.get(selAnim).getActiveFrameID());
					}
			}}
		ui.end();
	}

	private void drawpicker()
	{
		if (sEnableBlending){
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batch.setColor(1,1,1,1);
		}
		
		int seltset=0;
		batch.setProjectionMatrix(tilecam.combined);

		if (tilesets.size()>0){
			batch.begin();
			tileset ts=null;
			if (kartu=="tile")
			{
				seltset=this.seltset;
			}
			else
			{
				switch (tilePicker)
				{
					case "newimgobj":
						seltset=this.seltset;
					break;
					case "props":case "rnda": case "rndb":case "repa": case "repb":
						seltset=this.seltset;
						break;
					default:
						seltset=this.selTsetID;
					break;
				}
			}
			
			ts=tilesets.get(seltset);
			
			for (int i=0;i < ts.getTilecount();i++)
			{
				
				long ini =i;//+tilesets.get(seltset.getFirstgid();
				boolean terrain=false,autotl=false;
						
				java.util.List<terrain> tr=tilesets.get(seltset).getTerrains();
				if (tr.size() > 0)
				{
					for (int n =0;n < tr.size();n++)
					{
						if (ini == tr.get(n).getTile()){
							terrain=true;
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
						if (tiles.size() > 0)
						{
							for (int n =0;n < tiles.size();n++)
							{
								if (ini == tiles.get(n).getTileID()){
									
									
										if (tiles.get(n).getAnimation().size() > 0)
										{
											ini = tiles.get(n).getActiveFrameID();
										}
									
								}
							}
						}
				
				long ist = ini;//-tilesets.get(seltset).getFirstgid();
				int wd = ts.getWidth();
				int xpos = (i) % wd;
				int ypos = (i) / wd;
				int xpos2 = (int) (ist)% wd;
				int ypos2 = (int) (ist) / wd;
				int margin=ts.getMargin();
				int spacing =ts.getSpacing();
				

				batch.draw(ts.getTexture(), xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), (xpos2 * (ts.getTilewidth()+spacing))+margin, (ypos2 * (ts.getTileheight()+spacing))+margin, ts.getTilewidth(), ts.getTileheight());	
				if (sShowGID)
				{
					str1.getData().setScale(0.1f);
					str1.draw(batch,Long.toString(ini+tilesets.get(seltset).getFirstgid()),(float) xpos * ts.getTilewidth()+2, (float)-ypos * ts.getTileheight()+ts.getTileheight()-2,ts.getTilewidth(),Align.left,false);
					str1.draw(batch,Long.toString(ini),(float) xpos * ts.getTilewidth()+2, (float)-ypos * ts.getTileheight()+ts.getTileheight()-10,ts.getTilewidth(),Align.left,false);
					
					str1.getData().setScale(1f);
				}
				str1.getData().setScale(.2f);
				if (terrain) str1.draw(batch, "Tr", xpos * ts.getTilewidth(), -ypos * ts.getTileheight()+Tsh*3/4,Tsw,Align.center,false);
				if (autotl) str1.draw(batch, "A", xpos * ts.getTilewidth(), -ypos * ts.getTileheight()+Tsh*3/4,Tsw,Align.center,false);
				str1.getData().setScale(1f);
			}
			batch.end();



			//GRID FOR TILES SELECTOR
			sr.setProjectionMatrix(tilecam.combined);
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			
			sr.begin(ShapeRenderer.ShapeType.Filled);

			sr.setColor(0, 0, 0, 0.5f);
			int weight =1,cool=1;
			if (Tsw >=64) weight=2;
			int Tswa=tilesets.get(seltset).getTilewidth();
			int Tsha=tilesets.get(seltset).getTileheight();
			for (int i=0;i <= tilesets.get(seltset).getWidth();i++)
			{
				//if (i%5==0){cool=2;}else{cool=1;}
				
				sr.rectLine((Tswa * i), Tsha, (i * Tswa), -Tsha * tilesets.get(seltset).getHeight() + Tsha, weight*cool);
			}
			for (int j=-1;j < tilesets.get(seltset).getHeight();j++)
			{
				//if ((j+1)%5==0){cool=2;}else{cool=1;}
				sr.rectLine(0, -(Tsha * j), Tswa * tilesets.get(seltset).getWidth(), -(j * Tsha), weight*cool);
			}
			//debugMe=tilesets.get(seltset).getWidth() + "-" + tilesets.get(seltset).getHeight();
			if (kartu == "tile"|| tilePicker=="massprops")
			{
				if (stamp)
				{
					sr.setColor(1, 0, 0, 0.5f);
					int SprW=tilesets.get(seltset).getWidth();
					
					sr.rect((startSelect % SprW) * Tswa, -(startSelect / SprW) * Tsha + Tsha, ((endSelect % SprW) * Tswa + Tswa) - (startSelect % SprW) * Tswa, -(endSelect / SprW) * Tsha + (startSelect / SprW) * Tsha - Tsha);
					sr.setColor(0, 0, 0, 1);
				}
			}
			
			if (kartu=="pickanim" && tilePicker=="massprops"){
				sr.setColor(0,1,0,.5f);
				for (int i=0;i < ts.getTilecount();i++)
				{
					int wd = ts.getWidth();
					int xpos = (i) % wd;
					int ypos = (i) / wd;
					if (massprops.get(i))
					{
						sr.rect(xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), ts.getTilewidth(), ts.getTileheight());	
					}
				}
			}
			if (kartu == "pickanim" && tilePicker=="terraineditor"){
			for (int i=0;i < ts.getTilecount();i++)
			{

				long ini =i;//+tilesets.get(seltset.getFirstgid();
				boolean terrain=false;
				int curTerrain=0;
				int[] curNodes=null;
				tiles = ts.getTiles();
				if (tiles.size() > 0)
				{
					for (int n =0;n < tiles.size();n++)
					{
						if (ini == tiles.get(n).getTileID()){

							if (tiles.get(n).isTerrain())
							{
								terrain=true;
								curNodes=tiles.get(n).getTerrain();
							}
								
							if (tiles.get(n).getAnimation().size() > 0)
							{
								ini = tiles.get(n).getActiveFrameID();
							}

						}
					}
				}

				long ist = ini;//-tilesets.get(seltset).getFirstgid();
				int wd = ts.getWidth();
				int xpos = (i) % wd;
				int ypos = (i) / wd;
				int xpos2 = (int) (ist)% wd;
				int ypos2 = (int) (ist) / wd;
				int margin=ts.getMargin();
				int spacing =ts.getSpacing();

				sr.setColor(1,0,0,.5f);
				if (terrain) 
				{
					curTerrain=tilesets.get(selTsetID).getSelTerrain();
					if (curNodes[0]==curTerrain) sr.rect(xpos * ts.getTilewidth(), -ypos * ts.getTileheight()+ts.getTileheight()/2, ts.getTilewidth()/2, ts.getTileheight()/2);	
					if (curNodes[1]==curTerrain) sr.rect(xpos * ts.getTilewidth()+ts.getTilewidth()/2, -ypos * ts.getTileheight()+ts.getTileheight()/2, ts.getTilewidth()/2, ts.getTileheight()/2);	
					if (curNodes[2]==curTerrain) sr.rect(xpos * ts.getTilewidth(), -ypos * ts.getTileheight(), ts.getTilewidth()/2, ts.getTileheight()/2);	
					if (curNodes[3]==curTerrain) sr.rect(xpos * ts.getTilewidth()+ts.getTilewidth()/2, -ypos * ts.getTileheight(), ts.getTilewidth()/2, ts.getTileheight()/2);	
					
						
				}
				
			
			}
			}
			sr.end();
			if (sEnableBlending)
			{
				Gdx.gl.glDisable(GL10.GL_BLEND);
			}
		}
		//nothing should apper if tileset is empty.

		//BACKGROUND FOR UI
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		uis.setProjectionMatrix(uicam.combined);
		uis.begin(ShapeRenderer.ShapeType.Filled);
		uis.setColor(0f, 0f, 0, 0.4f);
		Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		uicam.unproject(mouse); // mousePos is now in world coordinates
		

		if (tilesets.size()==0) 
		{
			//uisrect(0, 100,50,60);
		}else{
			if (kartu=="pickanim"){
			if (tilePicker=="terraineditor") 
			{
				uisrect(gui.newterrain,mouse,null);//tool switch
			}
			else if (tilePicker=="massprops") 
			{
				uisrect(gui.newterrain,mouse,null);
			}
			}
			
		}
		uisrect(gui.tilesets,mouse,null);
		
		if (tilesets.size()!=0 && kartu == "tile") 
		{
			uisrect(gui.tool1,mouse,null);
			uisrect(gui.tool2,mouse,null);
			uisrect(gui.tool3,mouse,null);
			uisrect(gui.tool5,mouse,null);
		}
		uis.end();
		ui.setProjectionMatrix(uicam.combined);
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
		
		if (tilesets.size()==0) 
		{
			//str1draw(ui, "Tileset is empty.",0,100,50);
			str1draw(ui, z.addnew+" "+z.tileset, gui.tilesets);
		}else
		{
			
			if (kartu=="tile")
			{
				str1draw(ui, "<<", gui.tilesetslefticon);
				str1draw(ui, ">>", gui.tilesetrighticon);
				str1draw(ui, tilesets.get(seltset).getName(),gui.tilesetsmid);
				uidraw(txadd,gui.tool1,2);
				uidraw(txinfo,gui.tool2,2);
				uidraw(txtiles,gui.tool3,2);
				uidraw(txdelete,gui.tool5,2);
			}
			else
			{
				switch (tilePicker)
				{
					case "props":case "rnda":case "rndb":case "repa": case "repb":
						str1draw(ui, "<<", gui.tilesetslefticon);
						str1draw(ui, ">>", gui.tilesetrighticon);
						str1draw(ui, tilesets.get(seltset).getName(),gui.tilesetsmid);
						
						break;
					case "newimgobj":
						str1draw(ui, "<<", gui.tilesetslefticon);
						str1draw(ui, ">>", gui.tilesetrighticon);
						str1draw(ui, tilesets.get(seltset).getName(),gui.tilesetsmid);
						
						break;
					case "terraineditor":
						if (tilesets.get(seltset).getTerrains().size()>0)str1draw(ui, "Terrain: "+tilesets.get(seltset).getTerrains().get(tilesets.get(seltset).getSelTerrain()).getName(), gui.tilesets);
						str1draw(ui, "New...", gui.newterrain);
						
						break;
					case "massprops":
						str1draw(ui, z.tileset+": "+tilesets.get(selTsetID).getName(), gui.tilesets);
						str1draw(ui, z.save, gui.newterrain);
						break;
						
					default:
						str1draw(ui, z.tileset+": "+tilesets.get(selTsetID).getName(), gui.tilesets);
						
						break;
				}
			}
		}
		str1.setColor(1, 1, 1, 1);
		ui.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	private void drawGrid()
	{
		if (cammode!="View only"){
		//GRID IN MAIN VIEW
		if (sEnableBlending)
		{
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		}
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeRenderer.ShapeType.Filled);
		
		if (roll && activetool!=4)
		{
			sr.setColor(1, 0, 0, 0.5f);
			int SprW=Tw;
			if (orientation.equalsIgnoreCase("isometric"))
			{
				int offsetx=0,offsety=0;
				xpos = mapstartSelect % Tw;
				ypos = mapstartSelect / Tw;
				offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
				offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
				int xpos2 = mapendSelect % Tw;
				int ypos2 = mapendSelect / Tw;
				int offsetx2=(xpos2*Tsw/2)+(ypos2*Tsw/2);
				int offsety2=(xpos2*Tsh/2)-(ypos2*Tsh/2);
				int offsetx3=(xpos2*Tsw/2)+(ypos*Tsw/2);
				int offsety3=(xpos2*Tsh/2)-(ypos*Tsh/2);
				int offsetx4=(xpos*Tsw/2)+(ypos2*Tsw/2);
				int offsety4=(xpos*Tsh/2)-(ypos2*Tsh/2);
				sr.polygon(new float[]{xpos*Tsw+(Tsw/2)-offsetx,-ypos*Tsh+Tsh-offsety,xpos2*Tsw+Tsw-offsetx3,-ypos*Tsh+(Tsh/2)-offsety3,(xpos2*Tsw)+(Tsw/2)-offsetx2,-ypos2*Tsh-offsety2,xpos*Tsw-offsetx4,-ypos2*Tsh+(Tsh/2)-offsety4});
				//sr.rect((mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh);
				
			}else if (orientation.equalsIgnoreCase("orthogonal"))
			{
				sr.rect((mapstartSelect % SprW) * Tsw, -(mapstartSelect / SprW) * Tsh + Tsh, ((mapendSelect % SprW) * Tsw + Tsw) - (mapstartSelect % SprW) * Tsw, -(mapendSelect / SprW) * Tsh + (mapstartSelect / SprW) * Tsh - Tsh);
			}
			sr.setColor(0, 0, 0, 1);
		}
		
		Gdx.gl20.glLineWidth(1);//average
		sr.setColor(0, 0, 0, .5f);
		
			int offsetx=0,offsety=0;
			if (orientation.equalsIgnoreCase("isometric"))
			{
				offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
				offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
			}
			
			for (int i=0;i <= Tw;i++)//vertical
			{
				
				if (orientation.equalsIgnoreCase("isometric"))
				{
					sr.rectLine(Tsw/2+i*Tsw/2, -i*Tsh/2+Tsh,Tsw/2+i*Tsw/2-Tsw*Tw/2, -i*Tsh/2-Tsh*Th/2+Tsh, Tsw/16f);
				}
				else
				{
					if (!landscape){
						if(i * Tsw+Tsw <cam.position.x-redux*cam.zoom) continue;
						if(i * Tsw >cam.position.x+redux*cam.zoom) continue;
					}
					
					if (sShowGrid || i==0 || i==Tw){
						sr.rectLine((Tsw * i), Tsh, (i * Tsw), -Tsh * Th + Tsh, Tsw/16f);
					}
					
				}
			}
			for (int j=-1;j < Th;j++)//horizontal
			{
				
				if (orientation.equalsIgnoreCase("isometric"))
				{
					sr.rectLine(0-j*Tsw/2, Tsh/2-(Tsh * j/2), (0-j*Tsw/2)+Tsw*Tw/2,Tsh/2-(Tsh * j/2)-Tsh*Th/2, Tsw/16f);
				}
				else
				{
					
					if(-Tsh*j+Tsh <cam.position.y-reduy*cam.zoom) continue;
					if((-Tsh*j)-2*Tsh >cam.position.y+(reduy+Tsh)*cam.zoom) continue;
					
					if (sShowGrid || j==-1 || j==Th-1){
					sr.rectLine(0, -(Tsh * j), Tsw * Tw, -(j * Tsh), Tsw/16f);
					}
				}
			}
		
		
		if (sShowCustomGrid)
		{
			sr.setColor(1, 0, 0, 1f);
			offsetx=0;offsety=0;
			if (orientation.equalsIgnoreCase("isometric"))
			{
				offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
				offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
			}

			for (int i=0;i <= Tw;i++)//vertical
			{
				
					
				if (i % sGridX ==0)
				{
				
				if (orientation.equalsIgnoreCase("isometric"))
				{
					sr.rectLine(Tsw/2+i*Tsw/2, -i*Tsh/2+Tsh,Tsw/2+i*Tsw/2-Tsw*Tw/2, -i*Tsh/2-Tsh*Th/2+Tsh, Tsw/16f);
				}
				else
				{
					if(i * Tsw+Tsw <cam.position.x-ssx*cam.zoom) continue;
					if(i * Tsw >cam.position.x+ssx*cam.zoom) continue;

					sr.rectLine((Tsw * i), Tsh, (i * Tsw), -Tsh * Th + Tsh, Tsw/8f);


				}
				}
			}
			for (int j=-1;j < Th;j++)//horizontal
			{
				
				if ((j+1) % sGridY ==0)
				{
						
				if (orientation.equalsIgnoreCase("isometric"))
				{
					sr.rectLine(0-j*Tsw/2, Tsh/2-(Tsh * j/2), (0-j*Tsw/2)+Tsw*Tw/2,Tsh/2-(Tsh * j/2)-Tsh*Th/2, Tsw/16f);
				}
				else
				{
					if(-Tsh*j+Tsh <cam.position.y-ssy*cam.zoom) continue;
					if(-Tsh*j >cam.position.y+ssy*cam.zoom) continue;

					sr.rectLine(0, -(Tsh * j), Tsw * Tw, -(j * Tsh), Tsw/8f);
				}
				}
			}
		}
		sr.setColor(0,0,0,0.5f);
		
		sr.end();
		if (sEnableBlending){
			Gdx.gl.glDisable(GL10.GL_BLEND);
		}
		}
	}
	
	private void drawTiles()
	{	
		redux=ssx/2;
		reduy=ssy/2;
		if (cam.zoom <zoomTreshold){
		//Gdx.gl.glDisable(GL10.GL_BLEND);
		batch.setColor(1,1,1,1);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		try{
		batch.draw(background,0,-Tsh*Th+Tsh,Tsw*Tw,Tsh*Th,0,0,background.getWidth(),background.getHeight(),false,false);
		}
		catch(Exception e){}
		camA=cam.position.x-ssx*cam.zoom;
		camB=cam.position.x+ssx*cam.zoom;
		camC=cam.position.y-ssy*cam.zoom;
		camD=cam.position.y+ssy*cam.zoom;
			
		tilesetsize=tilesets.size();
		if (tilesetsize>0 && !loadingfile){
			int offsetx=0,offsety=0;
			int jon=0,joni=0;
			long ini;
			int total=Tw*Th;
			int startx=0, stopx=Tw;
			int starty=0,stopy=Th;
			//no optimization for iso map yet
			if (orientation.equalsIgnoreCase("orthogonal"))
			{

				
				if (landscape){
					redux=ssy/2;reduy=ssx/2;
				}
				for (int i=0;i <= Tw;i++)//vertical
				{

					if(i * Tsw <cam.position.x-redux*cam.zoom) 
					{
						startx=i;
						continue;
					}
				
					if(i * Tsw > cam.position.x+redux*cam.zoom){
						stopx=i;
						break;
					}

				}
				for (int j=0;j < Th;j++)//horizontal
				{
					
					if((-Tsh*j)+Tsh <cam.position.y-reduy*cam.zoom){
						stopy=j;
						break;
					}
					//starts from
					
					if((-Tsh*j)-2*Tsh >cam.position.y+((reduy+Tsh)*cam.zoom)) {
						starty=j;
						continue;
					}

				}
			}
			int aa=0,bb=0,cc=0,dd=0;
			switch (renderorder){
				case "right-down":
					aa=starty;bb=stopy;
					cc=startx;dd=stopx;
					break;
				case "left-down":
					aa=starty;bb=stopy;
					cc=-stopx +1;dd=-startx+1;
					break;
				case "right-up":
					aa=-stopy+1;bb=-starty+1;
					cc=startx;dd=stopx;
					break;
				case "left-up":
					aa=-stopy+1;bb=-starty+1;
					cc=-stopx+1;dd=-startx+1;
					break;
			}
			
			
			switch (viewMode)
			{
				case 0://stack
					jon = 0;joni = seltile;
					break;
				case 1://single
					jon = seltile;joni = seltile;
					break;
				case 2://all
					jon = 0;joni = layers.size() - 1;
					break;
			}

			String flag;Long mm=null; flag = "00";
			
			for (int jo=jon;jo <= joni;jo++)
			{
				
				if (layers.get(jo).getOpacity()!=0 && sEnableBlending){
					Gdx.gl.glEnable(GL10.GL_BLEND);
					Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
					batch.setColor(1,1,1,layers.get(jo).getOpacity());
				}
				drawers.clear();
				for (int a=aa;a < bb;a++){
					for(int b=cc;b<dd;b++){
						//position=(Math.abs(a)*Tw)+Math.abs(b);
						
						position=(abs(a)*Tw)+abs(b);
						ini =layers.get(jo).getStr().get(position);
						initset =layers.get(jo).getTset().get(position);
						if (initset==-1) continue;
						if (ini==0) continue;//dont draw empty, amazing performance boost
						xpos = position % Tw;
						ypos = position / Tw;
						if (orientation.equalsIgnoreCase("isometric"))
						{
							offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
							offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
						}

						mm = ini;flag="00";
						if (ini > total)
						{
							hex=Long.toHexString(ini);
							trailer="00000000" + hex;
							hex = trailer.substring(trailer.length() - 8);
							flag = hex.substring(0, 2);
							mm = Long.decode("#00" + hex.substring(2, 8));
						}
							tiles = tilesets.get(initset).getTiles();
							tilesize=tiles.size();

							if (tilesize > 0)
							{
								for (int n =0;n < tilesize;n++)
								{
									if (tiles.get(n).getAnimation().size() > 0)
									{
										if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid())
										{
											mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
										}
									}
								}
							}
					
						sprX = (int)(mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
						sprY = (int)(mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth()) ;
						margin=tilesets.get(initset).getMargin();
						spacing =tilesets.get(initset).getSpacing();
						Tswa=tilesets.get(initset).getTilewidth();
						Tsha=tilesets.get(initset).getTileheight();
						
						tempdrawer=new drawer();
						tempdrawer.mm=mm;
						int Tswad=0;
						int Tshad=0;
						if (sResizeTiles)
						{
							Tswad=Tsw;
							Tshad=Tsh;
						}else
						{
							Tswad=Tswa;
							Tshad=Tsha;
						}
						switch (flag)
						{
							case "20"://diagonal flip
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, true);
								break;
							case "40"://flipy
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, true);
								break;
							case "60"://270 degrees clockwise
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
								break;
							case "80"://flipx
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, false);
								break;
							case "a0"://90 degress cw
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
								break;
							case "c0"://180 degrees cw
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
								break;
							case "00":
								tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
								break;
						}
						drawers.add(tempdrawer);
					} //for  b
				}//for a
				
				java.util.Collections.sort(drawers);//fps hogger
				
				for(drawer drawer : drawers)
				{
					drawer.draw(batch,tilesets);
					
					if (sShowGIDmap) 
					{
						//str1.getData().setScale(.1f);
						str1.getData().setScale(0.0025f+Tsw/160f);
						drawer.write(str1,batch);
						str1.getData().setScale(1f);
					}
						
				}
				
				if (layers.get(jo).getOpacity()!=0 && sEnableBlending){
					Gdx.gl.glDisable(GL10.GL_BLEND);
					batch.setColor(1,1,1,1);
				}
				
			}//for jo
			
			
			
		}//no tileswt

		batch.end();
	}
	else
	{
		//fbo.begin();
		batch.begin();
		try{
			batch.draw(background,0,-Tsh*Th+Tsh,Tsw*Tw,Tsh*Th,0,0,background.getWidth(),background.getHeight(),false,false);
		}
		catch(Exception e){}
		batch.end();
		if (!caching){
		for (int i=0;i<caches.size();i++)
		{
			SpriteCache cache=caches.get(i);
			int myid=cacheIDs.get(i);
			cache.setProjectionMatrix(cam.combined);
			Gdx.gl.glEnable(GL10.GL_BLEND); 
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 
			
			cache.begin();
			cache.draw(myid); //call our cache with cache ID and draw it
			cache.end();
		}
		}
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		if (layers.size()>2 && tilesets.size()>0){
		if (layers.get(2).getName().equalsIgnoreCase("Units") && tilesets.get(0).getName().equalsIgnoreCase("units")){
				
		for (int aa=0;aa<Tw*Th;aa++)
		{
			
				long mm=layers.get(2).getStr().get(aa);
				int xpos=aa%Tw;
				int ypos=aa/Tw;
				int maex=-1,maye=-1;
				if (mm==7){ maex=0;maye=0;}
				if (mm==52){ maex=1*56;maye=0;}
				if (mm==88){ maex=2*56;maye=0;}
				if (mm==106){ maex=3*56;maye=0;}
				if (mm==142){ maex=4*56;maye=0;}
				if (mm==155){ maex=0;maye=56;}
				if (mm==157){ maex=1*56;maye=56;}
				if (mm==159){ maex=2*56;maye=56;}
				if (mm==161){ maex=3*56;maye=56;}
				if (mm==179){ maex=4*56;maye=56;}

				if (maex !=-1)
				{
					drawer tempdrawer2=new drawer();
					int widthy=(int) (cam.zoom*60);
					tempdrawer2.setdrawer(0, xpos * Tsw-widthy/2, -ypos * Tsh-widthy/2, Tsw / 2, Tsh / 2, widthy, widthy, 1f, 1f, 0f, maex, maye, 55, 55, false, false);
					tempdrawer2.draw(batch,txnumbers);
				}

		 }//for

		}//if
		}// if
		batch.end();
		//fbo.end();
		
	}
	}
	
	private int abs(int i)
	{
		return (i + (i >> 31)) ^ (i >> 31);
	}
	private int mod(int i,int len){
		return ((i < len) ? i : 0);
	}
	
	private void cacheTiles()
	{
		 caching=true;
		 caches.clear();
		 cacheIDs.clear();
		 
		 tilesetsize=tilesets.size();
		 if (tilesetsize>0){
		 int offsetx=0,offsety=0;
		 int jon=0,joni=0;
		 long ini;
		 int total=Tw*Th;
		 
		 //no optimization for iso map yet
		 /*
		 if (orientation.equalsIgnoreCase("orthogonal"))
		 {
		
		 for (int i=0;i <= Tw;i++)//vertical
		 {

		 if(i * Tsw <cam.position.x-120*cam.zoom) 
		 {
		 startx=i;
		 continue;
		 }

		 if(i * Tsw > cam.position.x+120*cam.zoom){
		 stopx=i;
		 break;
		 }

		 }
		 for (int j=0;j < Th;j++)//horizontal
		 {

		 if((-Tsh*j)+Tsh <cam.position.y-200*cam.zoom){
		 stopy=j;
		 break;
		 }
		 //starts from

		 if((-Tsh*j)-2*Tsh >cam.position.y+((200+Tsh)*cam.zoom)) {
		 starty=j;
		 continue;
		 }

		 }
		 }
		 */
		 int startx=0, stopx=Tw;
		 int starty=0,stopy=Th;
		 int aa=0,bb=0,cc=0,dd=0;
		 switch (renderorder){
		 case "right-down":
		 aa=starty;bb=stopy;
		 cc=startx;dd=stopx;
		 break;
		 case "left-down":
		 aa=starty;bb=stopy;
		 cc=-stopx +1;dd=-startx+1;
		 break;
		 case "right-up":
		 aa=-stopy+1;bb=-starty+1;
		 cc=startx;dd=stopx;
		 break;
		 case "left-up":
		 aa=-stopy+1;bb=-starty+1;
		 cc=-stopx+1;dd=-startx+1;
		 break;
		 }


		 switch (viewMode)
		 {
		 case 0://stack
		 jon = 0;joni = seltile;
		 break;
		 case 1://single
		 jon = seltile;joni = seltile;
		 break;
		 case 2://all
		 jon = 0;joni = layers.size() - 1;
		 break;
		 }

		 String flag;Long mm=null; flag = "00";

		 for (int jo=jon;jo <= joni;jo++)
		 {

		 if (layers.get(jo).getOpacity()!=0 && sEnableBlending){
		 Gdx.gl.glEnable(GL10.GL_BLEND);
		 Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 batch.setColor(1,1,1,layers.get(jo).getOpacity());
		 }
		 java.util.List<drawer> drawers=new ArrayList<drawer>();
		 drawers.clear();
		 for (int a=aa;a < bb;a++){
		 for(int b=cc;b<dd;b++){
		 //position=(Math.abs(a)*Tw)+Math.abs(b);

		 int position=(abs(a)*Tw)+abs(b);
		 ini =layers.get(jo).getStr().get(position);
		 int initset =layers.get(jo).getTset().get(position);
		 if (initset==-1) continue;
		 if (ini==0) continue;//dont draw empty, amazing performance boost
		 int xpos = position % Tw;
		 int ypos = position / Tw;
		 if (orientation.equalsIgnoreCase("isometric"))
		 {
		 offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
		 offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
		 }

		 mm = ini;flag="00";
		 if (ini > total)
		 {
		 hex=Long.toHexString(ini);
		 trailer="00000000" + hex;
		 hex = trailer.substring(trailer.length() - 8);
		 flag = hex.substring(0, 2);
		 mm = Long.decode("#00" + hex.substring(2, 8));
		 }
		 tiles = tilesets.get(initset).getTiles();
		 tilesize=tiles.size();

		 if (tilesize > 0)
		 {
		 for (int n =0;n < tilesize;n++)
		 {
		 if (tiles.get(n).getAnimation().size() > 0)
		 {
		 if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid())
		 {
		 mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
		 }
		 }
		 }
		 }

		 sprX = (int)(mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
		 sprY = (int)(mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth()) ;
		 margin=tilesets.get(initset).getMargin();
		 spacing =tilesets.get(initset).getSpacing();
		 Tswa=tilesets.get(initset).getTilewidth();
		 Tsha=tilesets.get(initset).getTileheight();
		 int Tswad=0;
		 int Tshad=0;
			 if (sResizeTiles)
			 {
				 Tswad=Tsw;
				 Tshad=Tsh;
			 }else
			 {
				 Tswad=Tswa;
				 Tshad=Tsha;
			 }
		 drawer tempdrawer=new drawer();
		 switch (flag)
		 {
		 case "20"://diagonal flip
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, true);
		 break;
		 case "40"://flipy
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, true);
		 break;
		 case "60"://270 degrees clockwise
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
		 break;
		 case "80"://flipx
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, false);
		 break;
		 case "a0"://90 degress cw
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
		 break;
		 case "c0"://180 degrees cw
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
		 break;
		 case "00":
		 tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, -ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
		 break;
		 }
		 
		 drawers.add(tempdrawer);
		 
		 
		 } //for  b
		 }//for a

		 java.util.Collections.sort(drawers);//fps hogger
		 int counting=0;
		 SpriteCache cache = new SpriteCache(8000,true);
		  cache.beginCache();
		 for(drawer drawer : drawers)
		 {
			 
			 
		 counting +=1;
		 
			drawer.add(cache,tilesets);
		 
		 if (counting==8000)
		 {
			 int id = cache.endCache();
			 cacheIDs.add(id);
			 caches.add(cache);
			 
			 cache=new SpriteCache(8000,true);
			 
			 cache.beginCache();
			 counting=0;
		 }
		 }
			int id = cache.endCache();
			 cacheIDs.add(id);
			 caches.add(cache);
			 
		 if (layers.get(jo).getOpacity()!=0 && sEnableBlending){
		 Gdx.gl.glDisable(GL10.GL_BLEND);
		 }

		 }//for jo



		 }//no tileswt
	caching=false;
		
	}
	
	private void drawObjects()
	{
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);

		if (mode == "object" || mode=="newpoly")
		{
			Gdx.gl20.glLineWidth(10);//average
			sr.setColor(1, blink, 1, 1f);
			jon = 0;joni = 0;

			switch (objviewMode)
			{

				case 1://single
					jon = selgroup;joni = selgroup;
					break;
				case 0://all
					jon = 0;joni = objgroups.size() - 1;
					break;
			}
			if (objgroups.size() > 0)
			{
				for (int i=jon;i <= joni;i++)
				{
					if (objgroups.get(i).getObjects().size() > 0)
					{


						for (int j=0;j < objgroups.get(i).getObjects().size();j++)
						{

							obj ox= objgroups.get(i).getObjects().get(j);
							/*
							 if(ox.getX() <cam.position.x-SsX*cam.zoom) continue;
							 if(ox.getX() >cam.position.x+SsX*cam.zoom) continue;
							 if(-ox.getY() +Tsh <cam.position.y-SsY*cam.zoom) continue;
							 if(-ox.getY() >cam.position.y+SsY*cam.zoom) continue;
							 */
							
							if (ox.getShape()!=null){
								switch (ox.getShape()){
									case "ellipse":
										sr.ellipse(ox.getX(), -ox.getY()+Tsh-ox.getH(), ox.getW(), ox.getH());

										break;
									case "point":
										sr.ellipse(ox.getXa()+Tsh/4, -ox.getYa()+Tsh-Tsh*3/4, Tsw/2, Tsh/2);
									
										sr.rect(ox.getXa()+Tsh/4, -ox.getYa()+Tsh-Tsh*3/4, Tsw/2, Tsh/2);
										
										break;
									case "polygon":
										sr.ellipse(ox.getXa()+Tsh/4, -ox.getYa()+Tsh-Tsh*3/4, Tsw/2, Tsh/2);
										sr.ellipse(ox.getXa()+Tsh*3/8, -ox.getYa()+Tsh-Tsh*5/8, Tsw/4, Tsh/4);
										
										
										float[] f=ox.getVertices(Tsh);
										if (ox.getPointsSize()==2){
										
											f=ox.getVertices(Tsh);
											Polyline polyline =new Polyline(f);
											polyline.setOrigin(ox.getX(),-ox.getY()+Tsh);
											polyline.rotate(360-ox.getRotation());
											sr.polyline(polyline.getTransformedVertices());
											
										}else if (ox.getPointsSize()>=3){
											Polygon polygon = new Polygon();
											polygon.setVertices(f);
											polygon.setOrigin(ox.getX(),-ox.getY()+Tsh);
											polygon.rotate(360-ox.getRotation());
											sr.polygon(polygon.getTransformedVertices());
										}
										
										break;
									case "polyline":
										sr.ellipse(ox.getXa()+Tsh/4, -ox.getYa()+Tsh-Tsh*3/4, Tsw/2, Tsh/2);
										sr.ellipse(ox.getXa()+Tsh*3/8, -ox.getYa()+Tsh-Tsh*5/8, Tsw/4, Tsh/4);
										
										if (ox.getPointsSize()>=2){
											f=ox.getVertices(Tsh);
											Polyline polyline =new Polyline(f);
											polyline.setOrigin(ox.getX(),-ox.getY()+Tsh);
											polyline.rotate(360-ox.getRotation());
											sr.polyline(polyline.getTransformedVertices());
										}
										break;
									case "text":
										sr.rect(ox.getX(), ox.getYantingelag(Tsh)-ox.getH(), 0,0, ox.getW(), ox.getH(),1,1,ox.getRotation());
										//str1.draw(batch, ox.getText(), ox.getX(), -ox.getYantingelag(Tsh));

										break;
									default:
										sr.rect(ox.getX(), ox.getYantingelag(Tsh)-ox.getH(), 0,0, ox.getW(), ox.getH(),1,1,360-ox.getRotation());
										//str1.draw(batch, j+"", 0, -j);



										break;
								}


							}else{
								sr.rect(ox.getX(), ox.getYantingelag(Tsh)-ox.getH(), ox.getW(), ox.getH());
								
							}
							
							

						}



					}
				}
			}
		}
		sr.end();
	}

	public void drawObjectsInfo(){
	
		batch.setProjectionMatrix(cam.combined);
				batch.begin();
				str1.getData().setScale(Tsw/200f);
				if (objgroups.size() > 0 && mode=="object")
					{
						for (int i=jon;i <=joni ;i++)
						{
							if (objgroups.get(i).getObjects().size() > 0)
							{


								for (int j=0;j < objgroups.get(i).getObjects().size();j++)
								{
									
								obj ox= objgroups.get(i).getObjects().get(j);
								
									if (ox.getGid()!=0)
									{
										int kyut=0;
										for (int c=0;c < tilesets.size();c++)
										{
											if ( ox.getGid()>= tilesets.get(c).getFirstgid() && ox.getGid()< tilesets.get(c).getFirstgid() + tilesets.get(c).getTilecount())
											{
												kyut = c; break;
											}
										}
										
										if (ox.getGid()!=0){
										int sprX = (ox.getGid() - tilesets.get(kyut).getFirstgid()) % (tilesets.get(kyut).getWidth());
										int sprY = (ox.getGid() - tilesets.get(kyut).getFirstgid()) / (tilesets.get(kyut).getWidth()) ;
										int margin=tilesets.get(kyut).getMargin();
										int spacing =tilesets.get(kyut).getSpacing();
										TextureRegion region= new TextureRegion(tilesets.get(kyut).getTexture(), (sprX * (Tsw+spacing))+margin, (sprY * (Tsh+spacing))+margin, Tsw, Tsh);
										batch.draw(region, ox.getX(), ox.getYantingelag(Tsh)-ox.getH()-ox.getH()+Tsh, ox.getW(),ox.getH());	
										}

									}
								String kucing = "";
								if (ox.getName()!=null) kucing= ox.getName();
								str1.draw(batch, kucing, ox.getX(), ox.getYantingelag(Tsh));
								if (ox.getShape()!=null){
									switch (ox.getShape()){
										case "text":
											str1.getData().setScale(0.2f+Tsw/160f);
											if(ox.getText()!=null) 
											{
												if (ox.isWrap()){
													str1.draw(batch, ox.getText(), ox.getX(), ox.getYantingelag(Tsh), ox.getW(),com.badlogic.gdx.utils.Align.left,true);
												}else{
													str1.draw(batch, ox.getText(), ox.getX(), ox.getYantingelag(Tsh));
												}
											}
											str1.getData().setScale(0.2f+Tsw/160f);
											
										break;
									}
								}
								
								
								
								
								}
							}
						}
					}
				str1.getData().setScale(1f);
				batch.end();
	}
	@Override
	public void dispose()
	{
	}

	@Override
	public void resize(int width, int height)
	{
		float tx,ty,tz;
		float tx1,ty1,tz1;
		tx=cam.position.x;
		ty=cam.position.y;
		tz=cam.position.z;
		tx1=tilecam.position.x;
		ty1=tilecam.position.y;
		tz1=tilecam.position.z;
		
		//cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (width>height)
		{
			stage=hstage;
			//stage.addActor(hstage.getRoot());
			cam.setToOrtho(false, ssy, ssx);
			tilecam.setToOrtho(false, ssy, ssx);
			uicam.setToOrtho(false, ssy, ssx);
			landscape=true;
		}else
		{
			stage=vstage;
			//stage.addActor(vstage.getRoot());
			cam.setToOrtho(false, ssx, ssy);
			tilecam.setToOrtho(false, ssx, ssy);
			uicam.setToOrtho(false, ssx, ssy);
			landscape=false;
		}
		stage.getViewport().setScreenSize(width, height);
		if (kartu=="stage") 
		{gotoStage(lastStage);}
		else
		{
			if (dialog !=null) 
			{
			dialog.hide();
			backToMap();
			}
		}
		cam.position.set(tx,ty,tz);
		cam.update();
		tilecam.position.set(tx1,ty1,tz1);
		tilecam.update();
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
	
	public void uisrect(gui gui, Vector3 mousepos, Color color)
	{
	
		float x=0,y=0,w=0,h=0;
		if (landscape){
			x=gui.getXl();
			y=gui.getYl();
			w=gui.getWl();
			h=gui.getHl();
		}else{
			x=gui.getX();
			y=gui.getY();
			w=gui.getW();
			h=gui.getH();
		}
		
		if (Gdx.input.isTouched() && mousepos.x>=x/100*ssx && mousepos.x<=w/100*ssx && mousepos.y>=y/100*ssy && mousepos.y<=h/100*ssy && drag==false)
		{
				uis.setColor(0,0.4f,1,0.4f);
		}else
		{
			if (color==null)
			{
				uis.setColor(0,0,0,0.4f);
			}else if(color.toString().equalsIgnoreCase("00ff00ff"))
			{
				uis.setColor(0f,blink,0f,.4f);
			}else{
				uis.setColor(color);
			}
			
		}
		
		if (!landscape)
		{
			float xredux=.5f; //if (w==100||x==0) xredux=0; 
			float yredux=.3f; //if (h==100||y==0) yredux=0;
			uis.roundedRect((x+xredux)/100*ssx,(y+yredux)/100*ssy,(w-x-xredux*2)/100*ssx,(h-y-yredux*2)/100*ssy,30f);
		}else{
			float xredux=.2f; //if (w==100||x==0) xredux=0; 
			float yredux=.5f; //if (h==100||y==0) yredux=0;
			
			uis.roundedRect((x+xredux)/100*ssy,(y+yredux)/100*ssx,(w-x-xredux*2)/100*ssy,(h-y-yredux*2)/100*ssx,30f);
			
		}
	}
	
	public void str1drawlabel(SpriteBatch ui, String strin,gui gui)
	{
		float x=0,y=0,w=0;
		if (landscape){
			x=gui.getXl();
			y=gui.getYl()+3;
			w=gui.getWl();

		}else{
			x=gui.getX();
			y=gui.getY()+3;
			w=gui.getW();

		}
		if (!landscape)
		{
			str1.draw(ui, strin, x/100*ssx,(y+6)/100*ssy,(w-x)/100*ssx,Align.center,true);
		}else
		{
			str1.draw(ui, strin, x/100*ssy,(y+6)/100*ssx,(w-x)/100*ssy,Align.center,true);
		}

	}
	public void str1draw(SpriteBatch ui, String strin,gui gui)
	{
		float x=0,y=0,w=0;
		if (landscape){
			x=gui.getXl();
			y=gui.getYl();
			w=gui.getWl();
			
		}else{
			x=gui.getX();
			y=gui.getY();
			w=gui.getW();
		
		}
		if (!landscape)
		{
			str1.draw(ui, strin, x/100*ssx,(y+6)/100*ssy,(w-x)/100*ssx,Align.center,true);
		}else
		{
			str1.draw(ui, strin, x/100*ssy,(y+6)/100*ssx,(w-x)/100*ssy,Align.center,true);
		}
		
	}
	
	public void uidraw(Texture tx,gui gui,int margin)
	{
		float x=0,y=0,w=0,h=0;
		if (landscape){
			x=gui.getXl()+margin+1;
			y=gui.getYl()+margin;
			w=gui.getWl()-margin-1;
			h=gui.getHl()-margin;
		}else{
			x=gui.getX()+margin+1;
			y=gui.getY()+margin;
			w=gui.getW()-margin-1;
			h=gui.getH()-margin;
		}
		if (!landscape)
		{
			ui.draw(tx,x/100*ssx,y/100*ssy,(w-x)/100*ssx,(h-y)/100*ssy);
		}else
		{
			ui.draw(tx,x/100*ssy,y/100*ssx,(w-x)/100*ssy,(h-y)/100*ssx);
		}

	}
	
	public void uidraw(Texture tx,gui gui,int margin, int srcx,int srcy,int srcw,int srch)
	{
		float x=0,y=0,w=0,h=0;
		if (landscape){
			x=gui.getXl()+margin+1;
			y=gui.getYl()+margin;
			w=gui.getWl()-margin-1;
			h=gui.getHl()-margin;
		}else{
			x=gui.getX()+margin+1;
			y=gui.getY()+margin;
			w=gui.getW()-margin-1;
			h=gui.getH()-margin;
		}
		float roti=0; boolean flipx=false,flipy=false;
		switch (rotate)
		{
			case 0://"20"://diagonal flip
				roti=0;flipx=false;flipy=false;
				break;
			case 1://"40"://flipy
				roti=270f;flipx=false;flipy=false;
				break;
			case 2://"60"://270 degrees clockwise
				roti=180f;flipx=false;flipy=false;
				break;
			case 3://"80"://flipx
				roti=90f;flipx=false;flipy=false;
				break;
			case 4://"A0"://90 degress cw
				roti=0f;flipx=true;flipy=false;
				break;
			case 5://"C0"://180 degrees cw
				roti=0f;flipx=false;flipy=true;
				break;
			case 6://
				roti=0f;flipx=true;flipy=true;
				break;
		}
		
		if (!landscape)
		{
			ui.draw(tx,x/100*ssx,y/100*ssy,(w-x)/100*ssx/2,(h-y)/100*ssy/2,(w-x)/100*ssx,(h-y)/100*ssy,1,1,roti,srcx,srcy,srcw,srch,flipx,flipy);
		}else
		{
			ui.draw(tx,x/100*ssy,y/100*ssx,(w-x)/100*ssy/2,(h-y)/100*ssx/2,(w-x)/100*ssy,(h-y)/100*ssx,1,1,roti,srcx,srcy,srcw,srch,flipx,flipy);
		}

	}
	
	
	public void drawWorldUI(){
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		uicam.unproject(mouse); // mousePos is now in world coordinates
		
		//status(Integer.toString(batch.totalRenderCalls),5);
		//status(Float.toString(cam.zoom),1);
		uis.setProjectionMatrix(uicam.combined);
		uis.begin(ShapeRenderer.ShapeType.Filled);
		uis.setColor(0f, 0f, 0, 0.4f);
		
		if (cammode=="View only"){
			if (!takingss) 
			{
				uisrect(gui.screenshot,mouse,null);//tile/obj switch
				uisrect(gui.center,mouse,vis("center"));//map props. button
				
			}
				uis.end();
			ui.setProjectionMatrix(uicam.combined);
			ui.begin();
			if (!takingss) 
			{
				uidraw(txcenter, gui.center,2);
				str1draw(ui, z.screenshot,gui.screenshot);
				
			}
				
			ui.end();
		}
		else if (cammode!="View only")
		{
			
		
		if (roll || stamp) uisrect(gui.info,mouse,null);//info
			
		if (mode=="tile"||mode=="object")
		{
			uisrect(gui.mode,mouse,vis("mode"));//tile/obj switch
			uisrect(gui.layer,mouse,vis("layerlist"));//layer switch
			uisrect(gui.viewmode,mouse,vis("viewmode"));//viewmode switch
			uisrect(gui.center,mouse,vis("center"));//map props. button
			uisrect(gui.menu,mouse,vis("menu"));//main menu button
			uisrect(gui.map,mouse,vis("map"));//map props. button
		
		}
		
		if (mode == "tile")
		{
			if (sShowFPS) uisrect(gui.fps,mouse,vis("FPS"));//tile/obj switch
			
			uisrect(gui.rotation,mouse,vis("rotation"));//rotation switch
			uisrect(gui.undo,mouse,vis("undo"));//undo
			uisrect(gui.redo,mouse,vis("redo"));//redo
			uisrect(gui.layerpick,mouse,vis("layerpick"));//layerpicker
			
			uisrect(gui.picker,mouse,vis("tilepick"));//select tile
			if (autotiles.size()>0) uisrect(gui.autopicker,mouse,vis("autotilepick"));//select auto tile
			if (autotiles.size()>0) uisrect(gui.autotile,mouse,vis("refresh"));//tool switch
			
		
			Color c1=null,c2=null,c3=null,c4=null,c5=null;
			switch(activetool)
			{
				case 0: c1=new Color(1f,1f,0f,.4f);break;
				case 1: c2=new Color(1f,1f,0f,.4f);break;
				case 2: c3=new Color(1f,1f,0f,.4f);break;
				case 3: c4=new Color(1f,1f,0f,.4f);break;
				case 4: c5=new Color(1f,1f,0f,.4f);break;
			}
			uisrect(gui.tool1,mouse,c1);
			uisrect(gui.tool2,mouse,c2);
			uisrect(gui.tool3,mouse,c3);
			uisrect(gui.tool4,mouse,c4);
			uisrect(gui.tool5,mouse,c5);
		}
		
		if (mode=="object")
		{
			uisrect(gui.objectpicker,mouse,null);//objtools switch
			uisrect(gui.tool,mouse,null);//tool switch
			
		}
		if (mode=="newpoly")
		{
			uisrect(gui.undo,mouse,null);//undo
			uisrect(gui.tool,mouse,null);//tool switch
		}
		
		uis.end();
	
		ui.setProjectionMatrix(uicam.combined);
		ui.begin();
		//////////
			if (tilesets.size()>0 && mode=="tile" && curspr!=0){
			long num=curspr;
			int initset=0;
			for (int o=0;o<tilesets.size();o++)
			{
				if (num >= tilesets.get(o).getFirstgid() && num < tilesets.get(o).getFirstgid()+tilesets.get(o).getTilecount())
				{
					initset=o;
					break;
				}
			}
			sprX = (int)(num - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
			sprY = (int)(num - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth()) ;
			margin=tilesets.get(initset).getMargin();
			spacing =tilesets.get(initset).getSpacing();
			Tswa=tilesets.get(initset).getTilewidth();
			Tsha=tilesets.get(initset).getTileheight();
			//TextureRegion region= new TextureRegion(tilesets.get(initset).getTexture(), );
				uidraw(tilesets.get(initset).getTexture(),gui.pickerbg,1,(sprX * (Tsw+spacing))+margin, (sprY * (Tsh+spacing))+margin, Tsw, Tsh);
			}
		
		/*
			
			*/
		
		//////////
		
		
		//normal font
		if (statustimeout >0) 
		{
			statustimeout-=delta;
			str1.setColor(1,0,0,1);
			if (!takingss) str1draw(ui, debugMe, gui.status);
			str1.setColor(1,1,1,1);
		}
			
		if (cammode!="View only"){
		if (mode=="tile"|| mode=="object")
		{
			
			uidraw(txmenu, gui.menu,2);
			uidraw(txmap, gui.map,2);
			uidraw(txcenter, gui.center,2);
			
			
			
			if (stamp)
			{
				if (roll) 
				{
					str1draw(ui, z.smartstampmode, gui.info);
				}
				else
				{
					str1draw(ui, z.stampmode, gui.info);
				}
			}else{
				if (roll) 
				{
					if (activetool==0){
						str1draw(ui, z.rectangle, gui.info);
					}else if (activetool==1){
						str1draw(ui, z.eraser, gui.info);
					}else if (activetool==3){
						str1draw(ui, z.select, gui.info);
					}else if (activetool==4){
						str1draw(ui, z.paintbrush, gui.info);
					}
				}
			}
			
		}
		
		if (mode == "tile")
		{
			if (sShowFPS) str1draw(ui, fps, gui.fps);
			str1draw(ui, z.tile, gui.mode);
			str1draw(ui, layers.get(seltile).getName(),gui.layer);
			str1draw(ui, rotationName, gui.rotation);
			uidraw(txtile, gui.picker,0);
			if (autotiles.size()>0) 
			{
				uidraw(txautopick, gui.autopicker,2);
				uidraw(txauto, gui.autotile,2);
			}
				
			uidraw(txrectangle,gui.tool1,3);
			uidraw(txeraser,gui.tool2,3);
			uidraw(txfill,gui.tool3,3);
			uidraw(txcopy,gui.tool4,3);
			if (brushsize==1){
				uidraw(txbrush,gui.tool5,3);
			}else{
				uidraw(txpencil,gui.tool5,3);
			}
			
			str1draw(ui, viewModeName, gui.viewmode);
			
			uidraw(txundo, gui.undo,3);
			uidraw(txredo, gui.redo,3);
			uidraw(txlayer, gui.layerpick,2);
		}
		
		else if (mode=="object")
		{
			str1draw(ui, z.object, gui.mode);
			str1draw(ui, objgroups.get(selgroup).getName(), gui.layer);	
			str1draw(ui, objViewModeName, gui.viewmode);
			str1draw(ui, shapeName, gui.objectpicker);
			str1draw(ui, "<<", gui.objectpickerlefticon);
			str1draw(ui, ">>", gui.objectpickerrighticon);
			if (magnet==0)
			{
				uidraw(txunlock,gui.tool,3);
			}else{
				uidraw(txlock,gui.tool,3);
			}
			//str1draw(ui, magnetName, 85,100,10);
			
		}
		else if (mode=="newpoly")
		{
			uidraw(txundo, gui.undo,3);
			str1draw(ui, z.ok, gui.tool);
		}
		
		//smaller yellow font for label
		
		str1.getData().setScale(.7f);
		str1.setColor(1, 1, 0, 1);
		if (mode=="tile"|| mode=="object")
		{
			str1drawlabel(ui, z.layer,gui.mode);
			str1drawlabel(ui, z.activelayer,gui.layer);
			str1drawlabel(ui, z.visibility,gui.viewmode);
			
		}
		
		
		if (mode == "tile")
		{
			if (sShowFPS) str1drawlabel(ui, "FPS",gui.fps);
			
			if (rotate < 4)
			{str1drawlabel(ui, z.rotate,gui.rotation);}
			else
			{str1drawlabel(ui, z.flip, gui.rotation);}
		}
		
		if (mode=="object") 
		{
			str1drawlabel(ui, z.activeobjecttool,gui.objectpicker);
			
		}
		
		}
		//turn it back to normal and end.
		str1.setColor(1, 1, 1, 1);
		str1.getData().setScale(1f);
		ui.end();
		}
			Gdx.gl.glDisable(GL10.GL_BLEND);
	}

//////////////////////////////////////////////////////
//            APPLICATION VOID
//////////////////////////////////////////////////////
	private void loadOpen()
	{
		tOpen = new Table(); tOpen.setFillParent(true);
	}

	private void initSD()
	{
		boolean isExtAvailable = Gdx.files.isExternalStorageAvailable();
		if (isExtAvailable)extRoot = Gdx.files.getExternalStoragePath();
		
		
		
		
		
		// TODO: Implement this method
	}
	
	private void initErrorHandling(){
	Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
	public void uncaughtException(Thread t, Throwable e)
	{
		ErrorBung((Exception) e,"errorlog.txt");
		Gdx.net.openURI("https://www.mirwanda.com/p/nottiled-crashed.html?m=1");
		System.exit(1);
	}
	});
	}
	

	private void ErrorBung(Exception e, String filenya)
	{
		FileHandle file = Gdx.files.absolute(extRoot + "/" + filenya);
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		System.out.println(exceptionAsString);
		file.writeString(exceptionAsString, false);
		prefs.putString("lof",extRoot+"NotTiled/"+"sample/island.tmx");
		prefs.putString("lastpath",extRoot+"NotTiled/");
		prefs.flush();
		
	}
	
	private void writeThis(String path, String isi){
		FileHandle file = Gdx.files.absolute(extRoot + "/"+ path);
		StringWriter sw = new StringWriter();
		file.writeString(isi, false);
	}
	private void writeThisAbs(String path, String isi){
		FileHandle file = Gdx.files.absolute(path);
		StringWriter sw = new StringWriter();
		file.writeString(isi, false);
	}

	private void loadGdxStuff()
	{
		Gdx.input.setCatchBackKey(true);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		Gdx.input.setInputProcessor(gd);
		gd.setLongPressSeconds(.4f);
		int assx=Gdx.graphics.getWidth();
		int assy=Gdx.graphics.getHeight();
		if (assx<assy){
			ssx=assx;
			ssy=assy;
		}else
		{
			ssx=assy;
			ssy=assx;
		}
		btnx=(int)(ssx*.9f);
		
		if (ssx<ssy){
			btny=100*ssx/1080;
		}else{
			btny=100*ssy/1080;
		}
		
		txpencil=new Texture(Gdx.files.internal("images/pencil.png"));
		txfill=new Texture(Gdx.files.internal("images/fill.png"));
		txcopy=new Texture(Gdx.files.internal("images/copy.png"));
		txeraser=new Texture(Gdx.files.internal("images/eraser.png"));
		txbrush=new Texture(Gdx.files.internal("images/brush.png"));
		txlock=new Texture(Gdx.files.internal("images/lock.png"));
		txunlock=new Texture(Gdx.files.internal("images/unlock.png"));
		txnumbers=new Texture(Gdx.files.internal("images/numbers.png"));
		txrectangle=new Texture(Gdx.files.internal("images/rectangle.png"));
		txmenu=new Texture(Gdx.files.internal("images/menu.png"));
		txcenter=new Texture(Gdx.files.internal("images/center.png"));
		txadd=new Texture(Gdx.files.internal("images/add.png"));
		txdelete=new Texture(Gdx.files.internal("images/delete.png"));
		txtiles=new Texture(Gdx.files.internal("images/tile96.png"));
		txinfo=new Texture(Gdx.files.internal("images/info.png"));
		txmap=new Texture(Gdx.files.internal("images/map.png"));
		txlayer=new Texture(Gdx.files.internal("images/layer.png"));
		txundo=new Texture(Gdx.files.internal("images/undo.png"));
		txredo=new Texture(Gdx.files.internal("images/redo.png"));
		txtile=new Texture(Gdx.files.internal("images/tile.png"));
		txauto=new Texture(Gdx.files.internal("images/autotile.png"));
		txautopick=new Texture(Gdx.files.internal("images/autopick.png"));
		
		txstamp=new Texture(Gdx.files.internal("images/stamp.png"));
		txlauncher=new Texture(Gdx.files.internal("images/ic_launcher.png"));
		
		if (ssx<ssy){
		hstage= new Stage(new StretchViewport(ssy*1.5f,ssx*1.5f));
		vstage=new Stage(new StretchViewport(ssx,ssy));
		stage = new Stage(new StretchViewport(ssx,ssy));
		tilecam = new OrthographicCamera(ssx, ssy);
		uicam = new OrthographicCamera(ssx, ssy);
		cam = new OrthographicCamera(ssx, ssy);
		}else
		{
			hstage= new Stage(new StretchViewport(ssx*1.5f,ssy*1.5f));
			vstage=new Stage(new StretchViewport(ssy,ssx));
			stage = new Stage(new StretchViewport(ssy,ssx));
			tilecam = new OrthographicCamera(ssy, ssx);
			uicam = new OrthographicCamera(ssy, ssx);
			cam = new OrthographicCamera(ssy, ssx);
			
		}
		
		
		
		batch = new SpriteBatch(8191);
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, ssx,ssy,false);
		ui = new SpriteBatch(); uis = new myShapeRenderer();
		sr = new ShapeRendererPlus();
		//dialog=new Dialog(z.info,skin,"");
		//str1 = new BitmapFont(); 
		
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		FileHandle fileHandl = Gdx.files.internal("languages/characters");
		Map<String, String> vars = new HashMap<String, String>();
		String allstr = fileHandl.readString();
		String[] cumi = allstr.split("\r\n");
		for (int ad =0;ad<cumi.length;ad++)
		{
			String[] cuma = cumi[ad].split(":");
			vars.put(cuma[0],cuma[1]);
		}
		
		parameter.characters=FreeTypeFontGenerator.DEFAULT_CHARS+vars.get(language);
		parameter.borderColor =new Color(.5f,.5f,.5f,.9f);
		parameter.borderWidth=2;
		if (ssx<ssy){
			if (fontsize==0) fontsize=48*ssx/1080;
			
		}else{
			if (fontsize==0) fontsize=48*ssy/1080;
			
		}
		parameter.size = fontsize;
		
		parameter.shadowColor=new Color(0f,0f,0f,.9f);
		parameter.shadowOffsetY=4;
		
		String filenam="font.ttf";
		if (language.equalsIgnoreCase("Japanese")||language.equalsIgnoreCase("Chinese")) filenam="Japanese.otf";
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(filenam));
		str1 = generator.generateFont(parameter);
		generator.dispose();
		
		skin = new Skin();
		skin.add("font", str1, BitmapFont.class);
		//FileHandle fileHandle = Gdx.files.internal("skins/metal/metal-ui.json");
		//FileHandle atlasFile = fileHandle.sibling("metal-ui.atlas");

		FileHandle fileHandle = Gdx.files.internal("skins/holo/Holo-dark-hdpi.json");
		FileHandle atlasFile = fileHandle.sibling("Holo-dark-hdpi.atlas");
		
		if (atlasFile.exists()) {
			skin.addRegions(new TextureAtlas(atlasFile));
		}

		skin.load(fileHandle);
	}

	public void initializeThings()
	{
		//newtmxfile(false);
		File fle=new File(extRoot+"NotTiled/noads.txt");
		if (fle.exists()) 
		{
			bypassads=true;
		}
		File fl=new File(extRoot+"NotTiled");
		if (!fl.exists()) fl.mkdir();
		File fla=new File(extRoot+"NotTiled/sample");
		if (!fla.exists()) 
		{
		fla.mkdir();
			FileHandle from = Gdx.files.internal("sample/");
			from.copyTo(Gdx.files.absolute(extRoot+"NotTiled/"));
			prefs.putString("lof",extRoot+"NotTiled/"+"sample/island.tmx");
			prefs.putString("lastpath",extRoot+"NotTiled/");
			lastpath=extRoot+"NotTiled/sample";
			prefs.flush();
		}
		
		
		String lf = prefs.getString("lof",extRoot+"NotTiled/"+"sample/island.tmx");
		File tst = new File(lf);
		if (!intend.isEmpty())
		{
			String raw=intend.substring(7);
			try
			{
				raw = java.net.URLDecoder.decode(raw, StandardCharsets.UTF_8.name());
			}
			catch (UnsupportedEncodingException e)
			{}
			loadtmx(raw);
			return;
		}
		
		if (tst.exists()){
			loadtmx(lf);
		}else{
			File axs = new File(extRoot+"NotTiled/"+"sample/island.tmx");
			if (axs.exists()){
			loadtmx(extRoot+"NotTiled/"+"sample/island.tmx");
			}else{
				fl=new File(extRoot+"NotTiled");
				if (!fl.exists()) fl.mkdir();
				fla=new File(extRoot+"NotTiled/sample");
				if (!fla.exists()) fla.mkdir();
					FileHandle from = Gdx.files.internal("sample/");
					from.copyTo(Gdx.files.absolute(extRoot+"NotTiled/"));
					prefs.putString("lof",extRoot+"NotTiled/"+"sample/island.tmx");
					prefs.putString("lastpath",extRoot+"NotTiled/");
					lastpath=extRoot+"NotTiled/sample";
					prefs.flush();
				loadtmx(extRoot+"NotTiled/"+"sample/island.tmx");
				
			}
		}
		
		
	}

	public void newtmxfile(boolean user){
		loadingfile=true;
		background=null;
		if (user){
			curdir = fNCurdir.getText();
			curdir=curdir.replace("//","/");
			curfile = fNFilename.getText();
			Tw=Integer.parseInt(fNTw.getText());
			Th=Integer.parseInt(fNTh.getText());
			Tsw=Integer.parseInt(fNTsw.getText());
			Tsh=Integer.parseInt(fNTsh.getText());
			renderorder=sbNMapRenderOrder.getSelected().toString();
			mapFormat=sbNMapFormat.getSelected().toString();
			orientation=sbNMapOrientation.getSelected().toString();
		}else{
			curdir = extRoot;
			curfile = "map01.tmx";
			Tw=8;
			Th=8;
			Tsw=32;
			Tsh=32;
			mapFormat="csv";
			renderorder="right-down";
			orientation="orthogonal";
		}
		undolayer.clear(); redolayer.clear();
		
		String isi = "";String prName=""; String prValue="";int lastPid=0;
		selgroup = 0;seltile = 0;templastID = 1;seltset=0;
		objgroups.clear();layers.clear();tilesets.clear();
		autotiles.clear();
		int curgroupid=-1;int curobjid=-1;curid = 1;
		layer l = new layer();
		
		l.setName(z.layer+" 1");
		java.util.List<Long> ls=new ArrayList<Long>();
		java.util.List<Integer> lts=new ArrayList<Integer>();
		
		for(long i=0;i<Tw*Th;i++){
			ls.add((long) 0);
			lts.add(-1);
		}
		
		
		l.setStr(ls);
		l.setTset(lts);
		layers.add(l);
		objgroup og = new objgroup();
		og.setName(z.object+" 1");
		objgroups.add(og);
		
		kartu = "world";mode = "tile";
		curspr = 0; 
		cam.position.set(Tsw*Tw/2,-Tsh*Th/2,0);
		cam.zoom=.5f;
		cam.update();
		panTo(Tsw*Tw/2,-Tsh*Th/2,.25f,1f);
		
		
		loadingfile=false;
		loadautotiles();
		cacheTiles();
		uicam.zoom=0.5f;
		uicam.update();
		firstload=loadtime;
	}
	
	public void newtmxfileplus(boolean user){
		loadingfile=true;
		/*
		FileHandle fh= Gdx.files.absolute(extRoot+"NotTiled/sample/template/rw.tmx");
		if (!fh.exists())
		{
			FileHandle from = Gdx.files.internal("sample/");
			from.copyTo(Gdx.files.absolute(extRoot+"NotTiled/"));
		}else{
			FileHandle from = Gdx.files.internal("sample/template/rw.tmx");
			from.copyTo(Gdx.files.absolute(extRoot+"NotTiled/sample/template/rw.tmx"));
			FileHandle from2 = Gdx.files.internal("sample/template/auto.json");
			from2.copyTo(Gdx.files.absolute(extRoot+"NotTiled/sample/template/auto.json"));
			
		}
		*/
		String faths=extRoot+"NotTiled/sample/template/"+ltemplate.getSelected().toString()+"/template.tmx";
		FileHandle fh= Gdx.files.absolute(faths);
		if (!fh.exists()) 
		{
			msgbox("Template loading error");
			return;
		}
		
		loadtmxnewplus(faths);
			
	}

	private void showtsetselection()
	{
		bigman=new Table();
		bigman.setFillParent(true);
		Table tsetsel=new Table();
		tsetsel.defaults().width(btnx).height(btny).padBottom(2);
		ScrollPane sps=new ScrollPane(tsetsel);
		bigman.add(sps);
		tsetsel.add(new Label(z.selecttilesets,skin)).row();
		cball=new CheckBox(z.selectall,skin);
		cball.setChecked(true);
		cball.align(Align.left);
		tsetsel.add(cball).width(btnx).left().align(Align.left).row();
		newcb = new CheckBox[tilesets.size()];
		
		for (int i=0;i<tilesets.size();i++)
		{
			newcb[i]=new CheckBox(tilesets.get(i).getName(),skin);
			newcb[i].setChecked(true);
			newcb[i].align(Align.left);
			tsetsel.add(newcb[i]).width(btnx).left().align(Align.left).row();
		}
		TextButton okey= new TextButton(z.apply,skin);
		
		okey.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					for (int i=tilesets.size()-1;i>=0;i--)
					{
						if (!newcb[i].isChecked())
						{
							tilesets.remove(i);
						}
					}
					backToMap();
				}
			});
		tsetsel.add(okey).row();
		
		cball.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (cball.isChecked()){
						for (int i=0;i<tilesets.size();i++)
						{
							newcb[i].setChecked(true);
						}
					}else
					{
						for (int i=0;i<tilesets.size();i++)
						{
							newcb[i].setChecked(false);
						}
					}
					CacheAllTset();
				}
			});
		
		gotoStage(bigman);
		
	}
	
	public void loadTemplate()
	{
		//
		bTmplBack = new TextButton(z.back,skin);
		bTmplOK = new TextButton(z.ok,skin);
		bTmplDownload = new TextButton(z.download,skin);
		ltemplate = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		ScrollPane spok=new ScrollPane(ltemplate);
		
		bTmplBack.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tNewFile);
				}
			});
			
		bTmplOK.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (ltemplate.getSelectedIndex()<0) return;
					if (fNTsh.getText()==null) fNTsh.setText("20");
					if (fNTsw.getText()==null) fNTsw.setText("20");
					if (fNTh.getText()==null) fNTh.setText("20");
					if (fNTw.getText()==null) fNTw.setText("20");
					prefs.putString("Tsw",fNTsw.getText());
					prefs.putString("Tsh",fNTsh.getText());
					prefs.putString("Tw",fNTw.getText());
					prefs.putString("Th",fNTh.getText());
					prefs.flush();
					newtmxfileplus(true);
				}
			});
			
		bTmplDownload.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tOnline);
					refreshOnline();
				}
			});
			
		tTemplate = new Table();
		tTemplate.setFillParent(true);
		tTemplate.defaults().width(btnx).padBottom(2);
		tTemplate.add(new Label(z.selecttemplate,skin)).padTop(10).row();
		tTemplate.add(spok).height(0.45f*ssy).row();
		tTemplate.add(bTmplOK).row();
		tTemplate.add(bTmplDownload).row();
		tTemplate.add(bTmplBack).row();
	}
	
	public void refreshTemplate()
	{
		String[] srr= new String[]{};
		FileHandle del=Gdx.files.absolute(extRoot+"NotTiled/sample/template/");
		
		FileHandle handle[] = del.list();

		if (handle.length > 0)
		{
			int ia=0;
			for(FileHandle file: handle) {
				if (file.isDirectory())
				{
					ia+=1;
				}
			}
			srr = new String[ia];
			int i=0;
			for(FileHandle file: handle) {
				if (file.isDirectory())
				{
					srr[i] = file.name();
					i+=1;
				}
			}
			
		}
		ltemplate.setItems(srr);
		
		if (ltemplate.getItems().size>0)
		{
			ltemplate.setSelectedIndex(0);
		}
	}
	
	public void refreshOnline()
	{
		String[] srr= new String[]{};
		lonline.setItems(srr);
		Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
		request.setUrl("https://www.dropbox.com/s/d114sswkkv64vs6/test.txt?dl=1");
		Gdx.net.sendHttpRequest(request, new com.badlogic.gdx.Net.HttpResponseListener() {

				@Override
				public void handleHttpResponse (Net.HttpResponse httpResponse) {
					final FileHandle tmpFile = FileHandle.tempFile("model");
					tmpFile.write(httpResponse.getResultAsStream(), false);
					Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run () {
								//String sss= tmpFile.readString();
								//msgbox(sss);
								try{
									templates=new Online();
									
									Json json = new Json();
									templates = json.fromJson(Online.class, tmpFile);
	
									String[] srr = new String[templates.getTemplates().size()];
									for (int i=0;i<templates.getTemplates().size();i++)
									{
										String nem =templates.getTemplates().get(i).getName();
										FileHandle del=Gdx.files.absolute(Gdx.files.getExternalStoragePath()+"/NotTiled/sample/template/"+nem);
										if (del.exists())
										{
											srr[i]=nem + " ("+z.saved+")";
										}else
										{
											srr[i]=nem + " *"+z.newnew+"*";
										}
									}
										
									lonline.setItems(srr);
									if (lonline.getItems().size>0)
									{
										lonline.setSelectedIndex(0);
									}
									
								}catch(Exception e)
								{
									ErrorBung(e,"enyoh.txt");
								}
							}
						});
				}

				@Override
				public void failed (Throwable t) {
					msgbox(z.checkinternet);
				}

				@Override
				public void cancelled () {
					msgbox(z.downloadcancel);
				}
			});
		
	}
	
	public void downloadTemplate()
	{
		if (lonline.getSelectedIndex()<0) return;
		final String foldname = templates.getTemplates().get(lonline.getSelectedIndex()).getName();
		String lonk1= templates.getTemplates().get(lonline.getSelectedIndex()).getTemplate();
		String lonk2= templates.getTemplates().get(lonline.getSelectedIndex()).getAuto();
		
		FileHandle fh= Gdx.files.absolute(extRoot+"NotTiled/sample/template/"+foldname);
		if (!fh.exists()) fh.mkdirs();
		
		if (!lonk2.equalsIgnoreCase("")){
		Net.HttpRequest request2 = new Net.HttpRequest(Net.HttpMethods.GET);
		request2.setUrl(lonk2);
		Gdx.net.sendHttpRequest(request2, new com.badlogic.gdx.Net.HttpResponseListener() {

				@Override
				public void handleHttpResponse (Net.HttpResponse httpResponse) {
					final FileHandle tmpFile = FileHandle.tempFile("mode3");
					tmpFile.write(httpResponse.getResultAsStream(), false);
					Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run () {
								String sss= tmpFile.readString();

								try{
									templates=new Online();
									FileHandle fh= Gdx.files.absolute(extRoot+"NotTiled/sample/template/"+foldname+"/auto.json");
									fh.writeString(sss,false);

								}catch(Exception e)
								{
									ErrorBung(e,"enyoh.txt");
								}
							}
						});
				}

				@Override
				public void failed (Throwable t) {
					msgbox(z.checkinternet);
				}

				@Override
				public void cancelled () {
					msgbox(z.downloadcancel);
				}
			});
		}
			
		Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
		request.setUrl(lonk1);
		Gdx.net.sendHttpRequest(request, new com.badlogic.gdx.Net.HttpResponseListener() {

				@Override
				public void handleHttpResponse (Net.HttpResponse httpResponse) {
					final FileHandle tmpFile = FileHandle.tempFile("mode2");
					tmpFile.write(httpResponse.getResultAsStream(), false);
					Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run () {
								String sss= tmpFile.readString();

								try{
									templates=new Online();
									FileHandle fh= Gdx.files.absolute(extRoot+"NotTiled/sample/template/"+foldname+"/template.tmx");
									fh.writeString(sss,false);
									refreshOnline();
									msgbox(z.downloadcomplete);
									downloading=false;

								}catch(Exception e)
								{
									ErrorBung(e,"enyoh.txt");
								}
							}
						});
				}

				@Override
				public void failed (Throwable t) {
					msgbox(z.checkinternet);
					downloading=false;
				}

				@Override
				public void cancelled () {
					msgbox(z.downloadcancel);
					downloading=false;
				}
			});
	}
	
	public void loadImport()
	{
		
		bImportOK = new TextButton(z.ok,skin);
		bImportOK.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					try{
					loadingfile=true;
					errors=" ";
					if (fImportWidth.getText().equalsIgnoreCase(""))
					{
						fImportWidth.setText(Tsw+"");
					}
					if (fImportHeight.getText().equalsIgnoreCase(""))
					{
						fImportHeight.setText(Tsh+"");
					}
					if (thefile.file().getName().toLowerCase().contains(".tsx"))
					{
						loadtsx(openedfile);
					}else
					{
						File f=thefile.file();
						addImageTset(f);
						
						
					}

					CacheAllTset();
					loadingfile=false;
					if (errors !=" ") 
					{
						status(errors,5);
					}else
					{
						cue("tilesetadded");
					}

					seltset=tilesets.size()-1;
					onToPicker();
					recenterpick();
					}
					catch(Exception e)
					{
						loadingfile=false;
						onToPicker();
					}
				}
			});
		
		fImportWidth=new TextField("",skin);
		fImportHeight=new TextField("",skin);
		fImportWidth.setTextFieldFilter(tffint);
		fImportHeight.setTextFieldFilter(tffint);
		cImportEmbed = new CheckBox(z.embedtileset,skin);
		
		tImport = new Table();
		tImport.setFillParent(true);
		tImport.defaults().width(btnx).padBottom(2);
		tImport.add(new Label(z.importtileset,skin)).padTop(10).colspan(2).row();
		tImport.add(new Label(z.tilewidth,skin)).width(btnx/2).padTop(10);
		tImport.add(fImportWidth).width(btnx/2).row();
		tImport.add(new Label(z.tileheight,skin)).width(btnx/2).padTop(10);
		tImport.add(fImportHeight).width(btnx/2).row();
		tImport.add(cImportEmbed).colspan(2).row();
		tImport.add(bImportOK).colspan(2).row();

		
	}
	boolean downloading = false;
	public void loadOnline()
	{
		bOnlineBack = new TextButton(z.back,skin);
		bOnlineRefresh = new TextButton(z.refresh,skin);
		bOnlineDownload = new TextButton(z.download,skin);
		lonline = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		ScrollPane spok=new ScrollPane(lonline);
		
		bOnlineBack.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tTemplate);
					refreshTemplate();
				}
			});
			
		bOnlineRefresh.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					lonline.clearItems();
					refreshOnline();
				}
			});

		bOnlineDownload.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (downloading) {
						msgbox(z.downloading);
						return;
						}
					downloading=true;
					downloadTemplate();
					//gotoStage(tOnline);
				}
			});

		tOnline = new Table();
		tOnline.setFillParent(true);
		tOnline.defaults().width(btnx).padBottom(2);
		tOnline.add(new Label(z.onlinetemplate,skin)).padTop(10).row();
		tOnline.add(spok).height(0.5f*ssy).row();
		tOnline.add(bOnlineDownload).row();
		tOnline.add(bOnlineRefresh).row();
		tOnline.add(bOnlineBack).row();
		
	}
	
	public void loadMenuMap()
	{
		bLicense = new TextButton(z.license, skin);
		bNew = new TextButton(z.newfile, skin);
		bOpen = new TextButton(z.open, skin);
		bSave = new TextButton(z.save, skin);
		bSaveAs = new TextButton(z.saveas, skin);
		bTutorial = new TextButton(z.tutorial,skin);
		bTutorBack = new TextButton(z.back,skin);
		bTutorOK = new TextButton(z.ok,skin);
		
		
		TextButton bDiscord = new TextButton(z.discordserver, skin);
		TextButton bWhatsapp = new TextButton(z.whatsappgroup, skin);
		TextButton bPatreon = new TextButton(z.supportnottiled, skin);
		TextButton bPatreon2 = new TextButton(z.supportnottiled, skin);
		TextButton credito = new TextButton(z.credits, skin);
		TextButton bBackground = new TextButton(z.background,skin);
		bReload=new TextButton(z.reloadsamples,skin);
		TextButton bCopyto = new TextButton(z.copytorustedwarfare, skin);
		bRusted = new TextButton("Rusted Warfare", skin);
		bWardate = new TextButton("Rusted WarDate", skin);
		bManual = new TextButton(z.manualbook, skin);
		bVideos = new TextButton(z.videotutorials, skin);
		
		tRecent=new Table();
		tRecent.setFillParent(true);
		tRecent.defaults().width(btnx).padBottom(2);
		bRecent= new TextButton(z.recentfile,skin);
		bRecentOpen= new TextButton(z.open,skin);
		bRecentBack= new TextButton(z.back,skin);
		lrecentlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		ScrollPane spok=new ScrollPane(lrecentlist);
		tRecent.add(spok).height(0.7f*ssy).row();
		tRecent.add(bRecentOpen).row();
		tRecent.add(bRecentBack);
		try{
			recents=new recents();
			Json json = new Json();
			FileHandle f = Gdx.files.local("recents.json");
			recents = json.fromJson(recents.class, f);
		}catch(Exception e){}
		
		tTutorial=new Table();
		tTutorial.setFillParent(true);
		tTutorial.defaults().width(btnx).padBottom(2);
		ltutorial = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		ScrollPane spoki=new ScrollPane(ltutorial);
		tTutorial.add(spoki).height(0.7f*ssy).row();
		tTutorial.add(bTutorOK).row();
		tTutorial.add(bTutorBack);
		
		bLinks = new TextButton(z.links, skin);
		
		bExit = new TextButton(z.exit, skin);
		bBack = new TextButton(z.back, skin);

		bPreference = new TextButton(z.preferences, skin);
		bProperties = new TextButton(z.mapproperties, skin);
		bTileMgmt = new TextButton(z.tilelayer, skin);
		bObjMgmt = new TextButton(z.objectgroup, skin);
		bTsetMgmt = new TextButton(z.tileset, skin);
		bAutoMgmt= new TextButton(z.autotile,skin);
		TextButton bTools= new TextButton(z.tools,skin);
		
		
		listBack = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				backToMap();
			}
		};
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
		
		bRecentBack.addListener(listBack);
		bTutorBack.addListener(listBack);

		bBackground.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.openimagefile,"background","file",new String[]{".png",".bmp",".jpg",".jpeg",".gif"},null);
				}
			});
			
		credito.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Json json = new Json();
					writeThis("testtut.json",json.prettyPrint(tutor));
					showCredits();
				}
			});
		
		bReload.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				FileHandle from = Gdx.files.internal("sample/");
				from.copyTo(Gdx.files.absolute(extRoot+"NotTiled/"));
				prefs.putString("lof",extRoot+"NotTiled/"+"sample/island.tmx");
				prefs.putString("lastpath",extRoot+"NotTiled/");
				
				msgbox(z.sampleshasbeenreloaded);
				
			}
		});
		
		bCopyto.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					File fl=new File(extRoot+"/RustedWarfare");
					if (!fl.exists()) fl.mkdir();
					File fla=new File(extRoot+"/RustedWarfare/maps");
					if (!fla.exists()) 
					{
						fla.mkdir();
					}
					saveMap(curdir+"/"+curfile);
					FileHandle from = Gdx.files.absolute(curdir+"/"+curfile);
					from.copyTo(Gdx.files.absolute(extRoot+"/RustedWarfare/maps"));
					
					FileHandle from2 = Gdx.files.absolute(curdir+"/"+curfile.substring(0,curfile.length()-4)+"_map.png");
					if (from2.exists()) from2.copyTo(Gdx.files.absolute(extRoot+"/RustedWarfare/maps"));
					
					msgbox(z.mapsenttorustedwatfare);

				}
			});
		
		
		bBack.addListener(listBack);
		bBack2 = new TextButton(z.back, skin);
		bBack2.addListener(listBack);
		TextButton bBack3 = new TextButton(z.back, skin);
		bBack3.addListener(listBack);
		bExit.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.app.exit();
				}
			});

		bLinks.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tLinks);
				}
			});
			
		bTutorial.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tTutorial);
				}
			});
			
		bOpen.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.opentmxfile,"open","file",new String[]{".tmx"},null);
				}
			});

		bLicense.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tLicense);
				}
			});

		bRecent.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tRecent);
					lrecentlist.setItems(new String[]{});
					
					java.util.List<String> spt = new ArrayList<String>();
					for (int i=recents.getPaths().size() -1;i>=0;i--)
					{
						spt.add(recents.getPaths().get(i));
					}
					lrecentlist.setItems(spt.toArray(new String[0]));
				}
			});

		bRecentOpen.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					loadtmx(lrecentlist.getSelected());
					backToMap();
				}
			});
		
			
		bSave.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					saveMap(curdir+"/"+curfile);
					cue("save");
					msgbox(z.yourmaphasbeensaved);
					face.showinterstitial();
				}
			});

		bSaveAs.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectnewlocation,"saveas","dir",new String[]{},tMenu);
				}
			});
		
		bDiscord.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://discord.gg/pZBGBKr");
				}
			});
			
		bRusted.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.corrodinggames.rts");
				}
			});
		
		bWardate.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.fantasy.final.apps.rustedwardate");
				}
			});
			
		bManual.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://www.mirwanda.com/2019/03/nottiled-manual-book.html?m=1");
				}
			});
		
		bVideos.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://www.youtube.com/playlist?list=PLaZhehDwQZIKlNPsMKqR3YRYxvdWctWt9");
				}
			});
		
		bWhatsapp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://chat.whatsapp.com/LnZ74s758mTJnBu1ClKUA6");
				}
			});
		
		bPatreon.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					face.buyadfree();
				}
			});
		bPatreon.setColor(0,1,0,1);
		
		bPatreon2.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					face.buyadfree();
				}
			});
		bPatreon2.setColor(0,1,0,1);
		
		bTools.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(ttools);
				}
			});
			
		bTutorial.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tTutorial);
					ltutorial.setItems(new String[]{});

					java.util.List<String> spt = new ArrayList<String>();
					for (int i=0;i<tutor.getT().size();i++)
					{
						spt.add(tutor.getT().get(i).getName());
					}
					ltutorial.setItems(spt.toArray(new String[0]));
					
				}
			});
			
		bTutorOK.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
					
					tutoring=true;
					activetutor=ltutorial.getSelectedIndex();
					tutor.getT().get(activetutor).reset();
					cue("welcome");
				}
			});
		
		pSaveAs = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				curdir=saveasdir; curfile=input;
				saveMap(saveasdir+"/"+input);
			}
			@Override
			public void canceled()
			{}

		};
		tMap = new Table();
		tMap.setFillParent(true);
		tMap.defaults().width(btnx).height(btny+3).padBottom(2);
		if (!face.ispro()) tMap.add(bPatreon2).row();
		tMap.add(bProperties).row();
		tMap.add(bBackground).row();
		tMap.add(bTools).row();
		tMap.add(bAutoMgmt).row();
		tMap.add(bObjMgmt).row();
		tMap.add(bTileMgmt).row();
		tMap.add(bTsetMgmt).row();
		tMap.add(bBack2).row();

		tLinks=new Table();
		tLinks.setFillParent(true);
		tLinks.defaults().width(btnx).height(btny+2).padBottom(2);
		tLinks.add(bManual).row();
		tLinks.add(bVideos).row();
		tLinks.add(bDiscord).row();
		tLinks.add(bWhatsapp).row();
		tLinks.add(bReload).row();
		tLinks.add(bCopyto).row();
		tLinks.add(bBack3).row();
		tLinks.add(new Label(z.thirdpartyapps,skin)).padTop(10).row();
		tLinks.add(bRusted).row();
		tLinks.add(bWardate).padBottom(10).row();
		
		
		tMenu = new Table();
		tMenu.setFillParent(true);
		tMenu.defaults().width(btnx).height(btny+2);
		if (!face.ispro()) tMenu.add(bPatreon).row();
		tMenu.add(bNew).row();
		tMenu.add(bOpen).row();
		tMenu.add(bRecent).row();
		tMenu.add(bSave).row();
		tMenu.add(bSaveAs).row();
		tMenu.add(bTutorial).row();
		tMenu.add(bPreference).row();
		tMenu.add(bLinks).row();
		tMenu.add(bLicense).row();
		tMenu.add(credito).row();
		tMenu.add(bExit).row();
		tMenu.add(bBack);
	}

	private void msgbox(String msg)
	{
		Gdx.input.setInputProcessor(stage);
		dialog = new Dialog(z.info,skin, "dialog"){
			@Override
			protected void result(Object object)
			{
				if (kartu.equalsIgnoreCase("world"))
				{
					Gdx.input.setInputProcessor(gd);
					
				}
			}
		};
		dialog.button(z.ok);
		dialog.text(msg);
		dialog.show(stage);
	}
	private void loadTools()
	{
		ttools=new Table();
		ttools.setFillParent(true);
		TextButton hmirror=new TextButton(z.mirrorhorizontally,skin);
		TextButton vmirror=new TextButton(z.mirrorvertically,skin);
		TextButton hvmirror=new TextButton(z.mirrorboth,skin);
		TextButton randomize=new TextButton(z.randommap,skin);
		TextButton replacetiles=new TextButton(z.replacetiles,skin);
		
		TextButton topng=new TextButton(z.exporttopng,skin);
		TextButton tolua=new TextButton(z.exporttolua,skin);
		TextButton tojson=new TextButton(z.exporttojson,skin);
		TextButton toback=new TextButton(z.back,skin);
		
		//TextButton replacetile=new TextButton("Replace Tiles",skin);
		//TextButton cleartiles=new TextButton("Clear Tiles",skin);
		
		ttools.defaults().width(btnx).padBottom(2);
		ttools.add(hmirror).row();
		ttools.add(vmirror).row();
		ttools.add(hvmirror).row();
		ttools.add(randomize).row();
		ttools.add(replacetiles).row();
		ttools.add(topng).row();
		ttools.add(tolua).row();
		ttools.add(tojson).row();
		ttools.add(toback).row();
		//ttools.add(replacetile).row();
		//ttools.add(cleartiles).row();
		
		hmirror.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					runhmirror();
				}
			});
			
		vmirror.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					runvmirror();
				}
			});
			
		hvmirror.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					runhvmirror();
				}
			});
			
		randomize.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(trandomgen);
				}
			});
			
		replacetiles.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(treplacetiles);
				}
			});
			
		topng.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					exporttopng();
				}
			});
		
		tolua.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					exporttolua();
				}
			});
		
		tojson.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					exporttojson();
				}

				
			});
			
		toback.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
			
		trandomgen=new Table();
		trandomgen.setFillParent(true);
		trandomgen.defaults().width(btnx/2).padBottom(2).height(btny);
		ffirstgen=new TextField("0.5",skin);
		slfirstgen=new Slider(.4f,.6f,.01f,false,skin);
		slfirstgen.setValue(.5f);
		fgencount=new TextField("5",skin);
		fbirthlim=new TextField("5",skin);
		fdeathlim=new TextField("3",skin);
		flivestr=new TextField("0",skin);
		flivetset=new TextField("0",skin);
		fdeadstr=new TextField("0",skin);
		fdeadtset=new TextField("0",skin);
		TextButton runrandomize=new TextButton(z.randomize,skin);
		TextButton pickrnda=new TextButton(z.picktile1,skin);
		TextButton pickrndb=new TextButton(z.picktile2,skin);
		TextButton randback=new TextButton(z.back,skin);
		
		pickrnda.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("rnda");
				}
			});
			
		randback.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(ttools);
				}
			});
			
		pickrndb.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("rndb");
				}
			});
		
		trandomgen.add(new Label(z.balance,skin));
		trandomgen.add(slfirstgen).row();
	
		trandomgen.add(new Label("GID 1",skin));
		trandomgen.add(flivestr).row();
		trandomgen.add();
		trandomgen.add(pickrnda).row();
		trandomgen.add(new Label("GID 2",skin));
		trandomgen.add(fdeadstr).row();
		trandomgen.add();
		trandomgen.add(pickrndb).row();
		trandomgen.add(runrandomize).width(btnx).colspan(2).row();
		trandomgen.add(randback).width(btnx).colspan(2);
		
		runrandomize.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					float p1=slfirstgen.getValue();// Float.parseFloat(ffirstgen.getText());
					int p2=5;//Integer.parseInt(fgencount.getText());
					int p3=5;//Integer.parseInt(fbirthlim.getText());
					int p4=3;//Integer.parseInt(fdeathlim.getText());
					long p5=Long.parseLong(flivestr.getText());
					int p6=0;
					long p7=Long.parseLong(fdeadstr.getText());
					int p8=0;
					
					for (int i=0;i<tilesets.size();i++)
					{
						if (p5>=tilesets.get(i).getFirstgid() && p5< tilesets.get(i).getFirstgid()+tilesets.get(i).getTilecount())
						{
							p6=i;
						}
						if (p7>=tilesets.get(i).getFirstgid() && p7< tilesets.get(i).getFirstgid()+tilesets.get(i).getTilecount())
						{
							p8=i;
						}
					}
					
					rundomize(p1,p2,p3,p4,p5,p6,p7,p8);
				}
			});
			
			
		treplacetiles=new Table();
		treplacetiles.setFillParent(true);
		treplacetiles.defaults().width(btnx/2).padBottom(2).height(btny);

		fprevstr=new TextField("0",skin);
		fnextstr=new TextField("0",skin);

		TextButton runreplace=new TextButton(z.replacetiles,skin);
		TextButton pickrepa=new TextButton(z.picktile1,skin);
		TextButton pickrepb=new TextButton(z.picktile2,skin);
		TextButton repback=new TextButton(z.back,skin);

		pickrepa.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("repa");
				}
			});

		repback.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(ttools);
				}
			});

		pickrepb.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("repb");
				}
			});

		treplacetiles.add(new Label(z.from,skin));
		treplacetiles.add(fprevstr).row();
		treplacetiles.add();
		treplacetiles.add(pickrepa).row();
		treplacetiles.add(new Label(z.to,skin));
		treplacetiles.add(fnextstr).row();
		treplacetiles.add();
		treplacetiles.add(pickrepb).row();
		treplacetiles.add(runreplace).width(btnx).colspan(2).row();
		treplacetiles.add(repback).width(btnx).colspan(2);

		runreplace.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					long p1=Long.parseLong(fprevstr.getText());
					int p2=0;
					long p3=Long.parseLong(fnextstr.getText());
					int p4=0;

					for (int i=0;i<tilesets.size();i++)
					{
						if (p1>=tilesets.get(i).getFirstgid() && p1< tilesets.get(i).getFirstgid()+tilesets.get(i).getTilecount())
						{
							p2=i;
						}
						if (p3>=tilesets.get(i).getFirstgid() && p3< tilesets.get(i).getFirstgid()+tilesets.get(i).getTilecount())
						{
							p4=i;
						}
					}

					replacetiles(p1,p2,p3,p4);
				}
			});
	}
	
	private void replacetiles(long p1, int p2, long p3, int p4)
	{
		for (int i=0;i<layers.get(seltile).getStr().size();i++)
		{
			long prev=layers.get(seltile).getStr().get(i);
			if (prev==p1)
			{
				layers.get(seltile).getStr().set(i,p3);
				layers.get(seltile).getTset().set(i,p4);
			}
		}
	}
	private void exporttolua()
	{
		StringBuilderPlus sb=new StringBuilderPlus();
		sb.wlo("return{");
		sb.wl("version = \"1.2\",");
		sb.wl("luaversion = \"5.1\",");
		sb.wl("tiledversion = \"1.2.3\",");
		sb.wl("orientation = \""+orientation+"\",");
		sb.wl("renderorder = \""+renderorder+"\",");
		sb.wl("width = "+Tw+",");
		sb.wl("height = "+Th+",");
		sb.wl("tilewidth = "+Tsw+",");
		sb.wl("tileheight = "+Tsh+",");
		sb.wl("nextlayerid = "+(1+layers.size()+objgroups.size())+",");
		sb.wl("nextobjectid = "+curid+",");
		sb.wprop(properties);
		
		sb.wlo("tilesets = {");
		for(int n=0;n<tilesets.size();n++)
		{
			tileset t=tilesets.get(n);
			sb.wlo("{");//tileset inside
			sb.wl("name = \""+t.getName()+"\",");
			sb.wl("firstgid = "+t.getFirstgid()+",");
			sb.wl("tilewidth = "+t.getTilewidth()+",");
			sb.wl("tileheight = "+t.getTileheight()+",");
			sb.wl("spacing = "+t.getSpacing()+",");
			sb.wl("margin = "+t.getMargin()+",");
			sb.wl("columns = "+t.getColumns()+",");
			sb.wl("tilecount = "+t.getTilecount()+",");
			sb.wl("image = \""+t.getSource()+"\",");
			sb.wl("imagewidth = "+t.getOriginalwidth()+",");
			sb.wl("imageheight = "+t.getOriginalheight()+",");
			sb.wl("tileoffset = {\n        x = 0,\n        y = 0\n      },");
			sb.wl("grid = {\n        orientation = \""+orientation+"\",\n        width = "+Tsw+", \n        height = "+Tsh+"\n      },");
			sb.wprop(tilesets.get(n).getProperties());
			sb.wl("terrains = {},");
			
			if(tilesets.get(n).getTiles().size()!=0)
			{
				sb.wlo("tiles = {");//tiles
				for (int m=0;m<tilesets.get(n).getTiles().size();m++)
				{
					tile tt=tilesets.get(n).getTiles().get(m);
					sb.wlo("{");//tile
					sb.wl("id = "+tt.getTileID()+",");
					sb.wprop(tt.getProperties());
					
					if (tt.getAnimation().size()>0)
					{
						sb.wlo("animation = {");
						for (int am=0;am<tt.getAnimation().size();am++)
						{
							sb.wlo("{");
							sb.wl("tileid = "+tt.getAnimation().get(am).getTileID()+",");
							sb.wl("duration = "+tt.getAnimation().get(am).getDuration());
							
							if (am!=tt.getAnimation().size()-1){
								sb.wlc("},");
							}else{
								sb.wlc("}");
							}
						}
						sb.wlc("}");
					}
					
					
					if (m!=tilesets.get(n).getTiles().size()-1){
						sb.wlc("},");
					}else{
						sb.wlc("}");
					}
				}
				sb.wlc("}");
			}else{
			sb.wl("tiles = {}");}
			
			if (n!=tilesets.size()-1) {
				sb.wlc("},");//tileset inside
			}else{
				sb.wlc("}");//tileset inside
			}
		}
		sb.wlc("},"); //tileset;
		
		sb.wlo("layers = {");//layers
		
		//tilelayers
		for(int n=0;n<layers.size();n++)
		{
			layer l=layers.get(n);
			sb.wlo("{");//tile layer
			sb.wl("type = \"tilelayer\",");
			sb.wl("id = "+(n+1)+",");
			sb.wl("name = \""+l.getName()+"\",");
			sb.wl("x = 0,");
			sb.wl("y = 0,");
			sb.wl("width = "+Tw+",");
			sb.wl("height = "+Th+",");
			sb.wl("visible = true,");
				
			if (l.getOpacity()==0){
				sb.wl("opacity = 1,");
			}else{
				sb.wl("opacity = "+l.getOpacity()+",");
			}
			
			sb.wl("offsetx = 0,");
			sb.wl("offsety = 0,");
			sb.wprop(layers.get(n).getProperties());
			sb.wl("encoding = \"lua\",");
			sb.wlo("data = {");
			
			for (int k=0;k<Th;k++)
			{
				sb.w("        ");
				for (int j=0;j<Tw;j++)
				{
					sb.append(l.getStr().get(k*Tw+j));
					if (!(k==Th-1 && j==Tw-1)) sb.append(", ");
				}
				sb.wl("");
			}
			sb.wlc("}");//data
			
			if (n!=layers.size()-1) {
				sb.wlc("},");//tilelayer
			}else{
				if (objgroups.size()>0){
				sb.wlc("},");//tilelayer
				}else
				{
					sb.wlc("}");//tilelayer
				}
			}
		}
		
		//objectgroups
		for(int n=0;n<objgroups.size();n++)
		{
			objgroup l=objgroups.get(n);
			sb.wlo("{");//tile layer
			sb.wl("type = \"objectgroup\",");
			int nid=n+layers.size()+1;
			sb.wl("id = "+nid+",");
			sb.wl("name = \""+l.getName()+"\",");
			sb.wl("visible = true,");
			sb.wl("opacity = 1,");
			sb.wl("offsetx = 0,");
			sb.wl("offsety = 0,");
			sb.wl("draworder = \"topdown\",");
			sb.wl("properties ={},");
			
			if (l.getObjects().size()>0)
			{
				sb.wlo("objects = {");
				for (int ao=0;ao<l.getObjects().size();ao++)
				{
					obj oo=l.getObjects().get(ao);
					sb.wlo("{");
					sb.wl("id = "+oo.getId()+",");
					sb.wl("name = \""+oo.getName()+"\",");
					sb.wl("type = \""+oo.getType()+"\",");
					if (oo.getShape()=="")
					{
						sb.wl("shape = \"rectangle\",");
						
					}else
					{
						sb.wl("shape = \""+oo.getShape()+"\",");
					}
					sb.wl("x = "+oo.getX()+",");
					sb.wl("y = "+oo.getY()+",");
					
					if (oo.getShape().equalsIgnoreCase("point"))
					{
						sb.wl("width = 0,");
						sb.wl("height = 0,");
						
					}else
					{
						sb.wl("width = "+oo.getW()+",");
						sb.wl("height = "+oo.getH()+",");
						
					}
					sb.wl("rotation = "+oo.getRotation()+",");
					sb.wl("visible = true,");
					if (oo.getShape().equalsIgnoreCase("polygon")||oo.getShape().equalsIgnoreCase("polyline"))
					{
						sb.wlo(oo.getShape() + " = {");
						for (int ok=0;ok<oo.getPoints().size();ok++)
						{
							float xar = oo.getPoints().get(ok).getX();
							float yar = oo.getPoints().get(ok).getY();
							if (ok!=oo.getPoints().size()-1){
								sb.wl("{ x = "+xar+", y = "+yar+" },");
							}else{
								sb.wl("{ x = "+xar+", y = "+yar+" }");
							}
						}
						sb.wlc("},");
					}
					
					sb.wprop(oo.getProperties());
					
					if (ao !=l.getObjects().size())
					{
						sb.wlc("},");
					}else
					{
						sb.wlc("}");
					}
					
				}
				sb.wlc("}");//objects
			}else
			{
				sb.wl("objects = {}");
			}
			

			if (n!=objgroups.size()-1) {
				sb.wlc("},");//tilelayer
			}else{
				sb.wlc("}");//tilelayer
			}
		}
		
		
		sb.wlc("}");//layers
		sb.wlc("}");//map
		
		FileHandle fh = Gdx.files.absolute(curdir+"/"+curfile.substring(0,curfile.length()-4)+".lua");
		fh.writeString(sb.toString(),false);
		backToMap();
		msgbox(z.exportfinished);
	}
	private void exporttojson()
	{
		StringBuilderPlus sb=new StringBuilderPlus();
		sb.wlo("{");
		sb.wl("\"version\":1.2,");
		sb.wl("\"type\":\"map\",");
		sb.wl("\"infinite\":false,");
		sb.wl("\"tiledversion\":\"1.2.3\",");
		sb.wl("\"orientation\":\""+orientation+"\",");
		sb.wl("\"renderorder\":\""+renderorder+"\",");
		sb.wl("\"width\":"+Tw+",");
		sb.wl("\"height\":"+Th+",");
		sb.wl("\"tilewidth\":"+Tsw+",");
		sb.wl("\"tileheight\":"+Tsh+",");
		sb.wl("\"nextlayerid\":"+(1+layers.size()+objgroups.size())+",");
		sb.wl("\"nextobjectid\":"+curid+",");
		sb.wpropj(properties);
		sb.wl(",");
		

		sb.wlo("\"tilesets\":[");
		for(int n=0;n<tilesets.size();n++)
		{
			tileset t=tilesets.get(n);
			sb.wlo("{");//tileset inside
			sb.wl("\"name\":\""+t.getName()+"\",");
			sb.wl("\"firstgid\":"+t.getFirstgid()+",");
			sb.wl("\"tilewidth\":"+t.getTilewidth()+",");
			sb.wl("\"tileheight\":"+t.getTileheight()+",");
			sb.wl("\"spacing\":"+t.getSpacing()+",");
			sb.wl("\"margin\":"+t.getMargin()+",");
			sb.wl("\"columns\":"+t.getColumns()+",");
			sb.wl("\"tilecount\":"+t.getTilecount()+",");
			if (t.getSource()!=null){
			sb.wl("\"image\":\""+t.getSource().replace("/","\\/")+"\",");
			}
			sb.wl("\"imagewidth\":"+t.getOriginalwidth()+",");
			sb.wl("\"imageheight\":"+t.getOriginalheight()+",");
			

			if(tilesets.get(n).getTiles().size()!=0)
			{
				sb.wlo("\"tiles\":[");//tiles
				for (int m=0;m<tilesets.get(n).getTiles().size();m++)
				{
					tile tt=tilesets.get(n).getTiles().get(m);
					sb.wlo("{");//tile
					sb.wl("\"id\":"+tt.getTileID()+",");
					
					if (tt.getAnimation().size()>0)
					{
						sb.wlo("\"animation\":[");
						for (int am=0;am<tt.getAnimation().size();am++)
						{
							sb.wlo("{");
							sb.wl("\"tileid\":"+tt.getAnimation().get(am).getTileID()+",");
							sb.wl("\"duration\":"+tt.getAnimation().get(am).getDuration());

							if (am!=tt.getAnimation().size()-1){
								sb.wlc("},");
							}else{
								sb.wlc("}");
							}
						}
						sb.wlc("],");//animation
					}
					
					sb.wpropj(tt.getProperties());

					


					if (m!=tilesets.get(n).getTiles().size()-1){
						sb.wlc("},");
					}else{
						sb.wlc("}");
					}
				}
				sb.wlc("],");//tiles
			}else{
				sb.wl("\"tiles\":[],");}
			sb.wpropj(tilesets.get(n).getProperties());
			if (n!=tilesets.size()-1) {
				sb.wlc("},");//tileset inside
			}else{
				sb.wlc("}");//tileset inside
			}
		}
		sb.wlc("],"); //tileset;

		sb.wlo("\"layers\":[");//layers

		//tilelayers
		for(int n=0;n<layers.size();n++)
		{
			layer l=layers.get(n);
			sb.wlo("{");//tile layer
			sb.wl("\"type\":\"tilelayer\",");
			sb.wl("\"id\":"+(n+1)+",");
			sb.wl("\"name\":\""+l.getName()+"\",");
			sb.wl("\"x\":0,");
			sb.wl("\"y\":0,");
			sb.wl("\"width\":"+Tw+",");
			sb.wl("\"height\":"+Th+",");
			sb.wl("\"visible\":true,");

			if (l.getOpacity()==0){
				sb.wl("\"opacity\":1,");
			}else{
				sb.wl("\"opacity\":"+l.getOpacity()+",");
			}

			sb.wl("\"offsetx\":0,");
			sb.wl("\"offsety\":0,");
			
			sb.w("\"data\":[");

			for (int k=0;k<Th;k++)
			{
				for (int j=0;j<Tw;j++)
				{
					sb.append(l.getStr().get(k*Tw+j));
					if (!(k==Th-1 && j==Tw-1)) sb.append(", ");
				}
			}
			sb.appendLine("],");//data
			sb.wpropj(layers.get(n).getProperties());

			if (n!=layers.size()-1) {
				sb.wlc("},");//tilelayer
			}else{
				if (objgroups.size()>0){
					sb.wlc("},");//tilelayer
				}else
				{
					sb.wlc("}");//tilelayer
				}
			}
		}

		//objectgroups
		for(int n=0;n<objgroups.size();n++)
		{
			objgroup l=objgroups.get(n);
			sb.wlo("{");//tile layer
			sb.wl("\"type\":\"objectgroup\",");
			int nid=n+layers.size()+1;
			sb.wl("\"id\":"+nid+",");
			sb.wl("\"name\":\""+l.getName()+"\",");
			sb.wl("\"visible\":true,");
			sb.wl("\"opacity\":1,");
			sb.wl("\"offsetx\":0,");
			sb.wl("\"offsety\":0,");
			sb.wl("\"draworder\":\"topdown\",");

			if (l.getObjects().size()>0)
			{
				sb.wlo("\"objects\":[");
				for (int ao=0;ao<l.getObjects().size();ao++)
				{
					obj oo=l.getObjects().get(ao);
					sb.wlo("{");
					sb.wl("\"id\":"+oo.getId()+",");
					sb.wl("\"name\":\""+oo.getName()+"\",");
					sb.wl("\"type\":\""+oo.getType()+"\",");
					sb.wl("\"x\":"+oo.getX()+",");
					sb.wl("\"y\":"+oo.getY()+",");

					if (oo.getShape().equalsIgnoreCase("point"))
					{
						sb.wl("\"point\":true,");
						sb.wl("\"width\":0,");
						sb.wl("\"height\":0,");

					}else
					{
						sb.wl("\"width\":"+oo.getW()+",");
						sb.wl("\"height\":"+oo.getH()+",");

					}
					sb.wl("\"rotation\":"+oo.getRotation()+",");
					
					if (oo.getShape().equalsIgnoreCase("polygon")||oo.getShape().equalsIgnoreCase("polyline"))
					{
						sb.wlo(oo.getShape() + "\":[");
						for (int ok=0;ok<oo.getPoints().size();ok++)
						{
							float xar = oo.getPoints().get(ok).getX();
							float yar = oo.getPoints().get(ok).getY();
							if (ok!=oo.getPoints().size()-1){
								sb.wl("\"{ \"x\":"+xar+", y\":"+yar+" },");
							}else{
								sb.wl("\"{ \"x\":"+xar+", y\":"+yar+" }");
							}
						}
						sb.wlc("},");
					}
					sb.wl("\"visible\":true,");
					/*
					if(oo.getProperties().size()>0)
					{
						sb.wl("\"visible\":true,");
					}else
					{
						sb.wl("\"visible\":true");
					}
					*/
					
					sb.wpropj(oo.getProperties());
					
					if (ao !=l.getObjects().size()-1)
					{
						sb.wlc("},");
					}else
					{
						sb.wlc("}");
					}

				}
				sb.wlc("]");//objects
			}else
			{
				sb.wl("\"objects\":[]");
			}


			if (n!=objgroups.size()-1) {
				sb.wlc("},");//tilelayer
			}else{
				sb.wlc("}");//tilelayer
			}
		}


		sb.wlc("]");//layers
		sb.wlc("}");//map

		FileHandle fh = Gdx.files.absolute(curdir+"/"+curfile.substring(0,curfile.length()-4)+".json");
		fh.writeString(sb.toString(),false);
		backToMap();
		msgbox(z.exportfinished);

	}
	private void exporttopng()
	{
		try
		{
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Pixmap pm2 = new Pixmap(Tw*Tsw,Th*Tsh,Pixmap.Format.RGBA8888);
			tilesetsize=tilesets.size();
			if (tilesetsize>0 && !loadingfile){
				int offsetx=0,offsety=0;
				int jon=0,joni=0;
				long ini;
				int total=Tw*Th;
				int startx=0, stopx=Tw;
				int starty=0,stopy=Th;
				
				int aa=0,bb=0,cc=0,dd=0;
				switch (renderorder){
					case "right-down":
						aa=starty;bb=stopy;
						cc=startx;dd=stopx;
						break;
					case "left-down":
						aa=starty;bb=stopy;
						cc=-stopx +1;dd=-startx+1;
						break;
					case "right-up":
						aa=-stopy+1;bb=-starty+1;
						cc=startx;dd=stopx;
						break;
					case "left-up":
						aa=-stopy+1;bb=-starty+1;
						cc=-stopx+1;dd=-startx+1;
						break;
				}


				switch (viewMode)
				{
					case 0://stack
						jon = 0;joni = seltile;
						break;
					case 1://single
						jon = seltile;joni = seltile;
						break;
					case 2://all
						jon = 0;joni = layers.size() - 1;
						break;
				}

				String flag;Long mm=null; flag = "00";

				for (int jo=jon;jo <= joni;jo++)
				{
					java.util.List<drawer> drawers = new ArrayList<drawer>(); 
					drawers.clear();
					for (int a=aa;a < bb;a++){
						for(int b=cc;b<dd;b++){
							//position=(Math.abs(a)*Tw)+Math.abs(b);

							position=(abs(a)*Tw)+abs(b);
							ini =layers.get(jo).getStr().get(position);
							initset =layers.get(jo).getTset().get(position);
							if (initset==-1) continue;
							if (ini==0) continue;//dont draw empty, amazing performance boost
							xpos = position % Tw;
							ypos = position / Tw;
							if (orientation.equalsIgnoreCase("isometric"))
							{
								offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
								offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
							}

							mm = ini;flag="00";
							if (ini > total)
							{
								hex=Long.toHexString(ini);
								trailer="00000000" + hex;
								hex = trailer.substring(trailer.length() - 8);
								flag = hex.substring(0, 2);
								mm = Long.decode("#00" + hex.substring(2, 8));
							}
							tiles = tilesets.get(initset).getTiles();
							tilesize=tiles.size();

							if (tilesize > 0)
							{
								for (int n =0;n < tilesize;n++)
								{
									if (tiles.get(n).getAnimation().size() > 0)
									{
										if (mm == tiles.get(n).getTileID() + tilesets.get(initset).getFirstgid())
										{
											mm = (long) tiles.get(n).getActiveFrameID() + tilesets.get(initset).getFirstgid();
										}
									}
								}
							}

							sprX = (int)(mm - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
							sprY = (int)(mm - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth()) ;
							margin=tilesets.get(initset).getMargin();
							spacing =tilesets.get(initset).getSpacing();
							Tswa=tilesets.get(initset).getTilewidth();
							Tsha=tilesets.get(initset).getTileheight();

							tempdrawer=new drawer();
							tempdrawer.mm=mm;
							int Tswad=0;
							int Tshad=0;
			
								Tswad=Tswa;
								Tshad=Tsha;

							switch (flag)
							{
								case "20"://diagonal flip
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, true);
									break;
								case "40"://flipy
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, true);
									break;
								case "60"://270 degrees clockwise
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 90f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
									break;
								case "80"://flipx
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, true, false);
									break;
								case "a0"://90 degress cw
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 270f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
									break;
								case "c0"://180 degrees cw
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 180f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
									break;
								case "00":
									tempdrawer.setdrawer(initset, xpos * Tsw-offsetx, ypos * Tsh-offsety, Tsw / 2, Tsh / 2, Tswad, Tshad, 1f, 1f, 0f, (sprX * (Tswa+spacing))+margin, (sprY * (Tsha+spacing))+margin, Tswa, Tsha, false, false);
									break;
							}
							drawers.add(tempdrawer);
						} //for  b
					}//for a

					java.util.Collections.sort(drawers);//fps hogger

					for(drawer drawer : drawers)
					{
						drawer.draw(pm2,tilesets,Tsw,Tsh);
					}

				}//for jo



			}//no tileswt

		
		
		
		//
		
		FileHandle fh = Gdx.files.absolute(curdir+"/"+curfile.substring(0,curfile.length()-4)+"_export.png");
		PixmapIO.writePNG(fh,pm2);
		backToMap();
		msgbox(z.exportfinished);
		}catch(Exception e){
			msgbox(z.error);
			ErrorBung(e,"errorlog.txt");
		}
	}
	
	private void rundomize(float firstgeneration, int generationcount, int birthlimit, int deathlimit,long livestr, int livetset, long deadstr, int deadtset){
		float chanceToStartAlive = firstgeneration;

		for(int x=0; x<Tw*Th; x++){
			
			if(Math.random() < chanceToStartAlive){
				layers.get(seltile).getStr().set(x,livestr);
				layers.get(seltile).getTset().set(x,livetset);
			}else{
				layers.get(seltile).getStr().set(x,deadstr);
				layers.get(seltile).getTset().set(x,deadtset);
			}
			
		}
		for (int i=0;i<generationcount;i++){
			newgeneration(birthlimit,deathlimit,livestr,livetset,deadstr,deadtset);
		}
		cacheTiles();
		
	}
	
	public void newgeneration(int birthlimit, int deathlimit, long livestr, int livetset, long deadstr, int deadtset)
	{
		java.util.List<Long> strt = layers.get(seltile).getStr();
		java.util.List<Integer> tsett = layers.get(seltile).getTset();

		int total=Tw*Th;
		for(int k=0; k<total; k++){
			long nyei = layers.get(seltile).getStr().get(k);
			int living=0;
			if (k-Tw>=0 && layers.get(seltile).getStr().get(k-Tw)==livestr) living+=1;
			if (k-1>=0 && k%Tw!=0 && layers.get(seltile).getStr().get(k-1)==livestr) living+=1;
			if (k+1<total && k%Tw!=Tw-1 && layers.get(seltile).getStr().get(k+1)==livestr) living+=1;
			if (k+Tw<total && layers.get(seltile).getStr().get(k+Tw)==livestr) living+=1;
			if (k-Tw-1>=0 && k%Tw!=0 && layers.get(seltile).getStr().get(k-Tw-1)==livestr) living+=1;
			if (k-Tw+1>=0 && k%Tw!=Tw-1 && layers.get(seltile).getStr().get(k-Tw+1)==livestr) living+=1;
			if (k+Tw-1<total && k%Tw!=0 && layers.get(seltile).getStr().get(k+Tw-1) ==livestr) living+=1;
			if (k+Tw+1<total && k%Tw!=Tw-1 && layers.get(seltile).getStr().get(k+Tw+1) ==livestr) living+=1;
			
			if (nyei==livestr){//living cell
				if (living <= deathlimit) 
				{
					strt.set(k,deadstr);
					tsett.set(k,deadtset);
				}
				
			}else //dead cell
			{
				if (living >= birthlimit) 
				{
					strt.set(k,livestr);
					tsett.set(k,livetset);
				}
			}
			
		}
		layers.get(seltile).setStr(strt);
		layers.get(seltile).setTset(tsett);
	}
	
	private void runhmirror()
	{
		redolayer.clear();
		for (int i=0;i<Tw*Th;i++)
		{
			boolean follower=true;
			if (i==0) follower=false;
			int x=i%Tw;
			int y=i/Tw;
			
			if (x<Tw/2 && x!=Tw/2)
			{
				int location=i+(Tw/2-x)*2-1;
				long from=layers.get(seltile).getStr().get(location);
				long to=layers.get(seltile).getStr().get(i);
				int oldtset=layers.get(seltile).getTset().get(location);
				int newtset=layers.get(seltile).getTset().get(i);
				int layer=seltile;
			
				layerhistory lh= new layerhistory(follower,from,to,location,layer,oldtset,newtset);
				undolayer.add(lh);
				
				layers.get(seltile).getStr().set(location,to);
				layers.get(seltile).getTset().set(location,newtset);
			}
		}
		backToMap();
	}
	private void runvmirror()
	{
		redolayer.clear();
		for (int i=0;i<Tw*Th;i++)
		{
			boolean follower=true;
			if (i==0) follower=false;
			int x=i%Tw;
			int y=i/Tw;

			if (y<Th/2 && y!=Th/2)
			{
				int location=i+((Th/2-y)*2-1)*Tw;
				long from=layers.get(seltile).getStr().get(location);
				long to=layers.get(seltile).getStr().get(i);
				int oldtset=layers.get(seltile).getTset().get(location);
				int newtset=layers.get(seltile).getTset().get(i);
				int layer=seltile;

				layerhistory lh= new layerhistory(follower,from,to,location,layer,oldtset,newtset);
				undolayer.add(lh);

				layers.get(seltile).getStr().set(location,to);
				layers.get(seltile).getTset().set(location,newtset);
			}
		}
		backToMap();
	}
	private void runhvmirror()
	{
		runhmirror();
		runvmirror();
		backToMap();
	}
	private void beg()
	{
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
	
	private void status(String msg,float timeout)
	{
		debugMe=msg;
		statustimeout=timeout;
	}
	
	public void loadLicense()
	{

		btiled = new TextButton("Visit Tiled Website", skin);
		btiled.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.net.openURI("https://www.mapeditor.org");
				}
			});

		Texture myTexture = new Texture(Gdx.files.internal("cc.png"));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        bcc = new ImageButton(myTexRegionDrawable); //Set the button up

		bcc.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
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

	}
	public void loadObjProp()
	{
		tla.add(new Label(z.id, skin));
		tla.add(new Label(z.x, skin));
		tla.add(new Label(z.y, skin));
		tla.add(new Label(z.width, skin));
		tla.add(new Label(z.height, skin));
		tla.add(new Label(z.name, skin));
		tla.add(new Label(z.type, skin));
		tla.add(new Label(z.rotation, skin));

		for (int i = 1;i <= 8;i++)
		{
			tf.add(new TextField("", skin)); 
			if (i<=5 || i==8){
				tf.get(i-1).setTextFieldFilter(tfffloat);
			}
		}
		tf.get(0).setDisabled(true);


		bApply = new TextButton(z.save, skin);

		bApply.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					obj oj= objgroups.get(ogroup).getObjects().get(oedit);
					oj.setX(Float.parseFloat(tf.get(1).getText()));
					oj.setY(Float.parseFloat(tf.get(2).getText()));
					oj.setW(Float.parseFloat(tf.get(3).getText()));
					oj.setH(Float.parseFloat(tf.get(4).getText()));
					oj.setName(tf.get(5).getText());
					oj.setType(tf.get(6).getText());
					oj.setRotation(Float.parseFloat(tf.get(7).getText()));
					backToMap();
				}
			});



		bCancel = new TextButton(z.cancel, skin);
		bProps = new TextButton(z.properties, skin);


		bRemove = new TextButton(z.remove, skin);
		bRemove.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					objgroups.get(ogroup).getObjects().remove(oedit);
					backToMap();
				}
			});
		bCancel.addListener(listBack);



		tObjProp = new Table();
		tObjProp.setFillParent(true);
		tObjProp.top();
		tObjProp.add(new Label(z.object+" "+z.properties, skin)).colspan(2).padBottom(10);
		tObjProp.row();

		Table table = new Table();
		ScrollPane sp= new ScrollPane(table);

		for (int i = 0;i < 8;i++)
		{
			table.add(tla.get(i)).width(btnx/2);
			table.add(tf.get(i)).padBottom(1).padLeft(5).width(btnx/2-1);
			table.row();

		}
		tObjProp.add(sp).colspan(2).height(btny*10).row();
		tObjProp.add(bProps).width(btnx/2).padTop(10).padBottom(5);
		tObjProp.add(bApply).width(btnx/2).padTop(10).padBottom(5).padLeft(5).row();
		tObjProp.add(bRemove).width(btnx/2);
		tObjProp.add(bCancel).width(btnx/2).padLeft(5).row();

	}

	public void loadPropEditor(){
		/*
		 Table tPropEditor;
		 TextField fPropName; SelectBox sbPropType,sbPropValbool;
		 TextField fPropVal; //str,int,float,color
		 TextButton bPropValfile, bPropApply, bPropCancel;
		
		*/
		
		
		bPropValfile=new TextButton(z.select,skin);
		
		bPropApply=new TextButton(z.ok,skin);
		bPropCancel=new TextButton(z.cancel,skin);
		bPropGid=new TextButton(z.tilepicker,skin);
		bProppng=new TextButton(z.loadpng,skin);
		
		com.badlogic.gdx.scenes.scene2d.ui.Stack stack = new com.badlogic.gdx.scenes.scene2d.ui.Stack();
		
		fPropName= new TextField("",skin);
		String[] types = new String[]{"String","Integer","Float","Color","Boolean","File"};
		sbPropType=new SelectBox(skin);
		sbPropType.setItems(types);
		fPropVal=new TextArea("",skin);
		fPropVal.setHeight(400);
		
		fPropVal.setTextFieldListener(new TextField.TextFieldListener()
			{
				@Override
				public void keyTyped(TextField textField, char c)
				{
					// Handle a newline properly. If not handled here, the TextField
					// will advance to the next field.
					if (c == '\n')
					{
						int pos = textField.getCursorPosition();
						String awalu = textField.getText().substring(0,pos);
						String ahiru = textField.getText().substring(pos,textField.getText().length());
						textField.setText(awalu+"\n"+ahiru);
						stage.setKeyboardFocus(textField);
						textField.setCursorPosition(pos+1);
						//textField.getOnscreenKeyboard().show(true);
						
					}
				}
			});
			
		sbPropValbool= new SelectBox(skin);
		sbPropValbool.setItems(new String[]{"false","true"});
		
		
		
		
		sbPropType.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					fPropVal.setVisible(false);
					bPropValfile.setVisible(false);
					sbPropValbool.setVisible(false);
					switch(sbPropType.getSelected().toString()){
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
		bPropValfile.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfile,"propfile","file",new String[]{},tPropEditor);
				}
			});
		
		bProppng.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfile,"proppng","file",new String[]{".png"},tPropEditor);
				}
			});
			
		bPropGid.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("props");
				}
			});
			
		bPropApply.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					java.util.List<property> pp = new ArrayList<property>();
					
					switch (sender){
						case "object":
							pp=objgroups.get(ogroup).getObjects().get(oedit).getProperties();
							break;
						case "tile":
							pp=tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
							break;
						case "tset":
							pp=tilesets.get(selTsetID).getProperties();
							break;
						case "map":
							pp=properties;
							break;
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
					}
					String value;
					switch(sbPropType.getSelected().toString()){

						case "Boolean":
							value=sbPropValbool.getSelected().toString();
							break;
						case "File":
							value=bPropValfile.getText().toString();
							break;
						default:
							value=fPropVal.getText().toString();
							break;
					}
					

					int dexo=lproplist.getSelectedIndex();
					String n,t,v;
					n=fPropName.getText().toString();
					t=sbPropType.getSelected().toString();
					v=value;
					
					//////
					if (sender=="mass")
					{
						temproname=n;
						temprotype=t;
						temprovalue=v;
						massprops.clear();
						for (int i=0;i<tilesets.get(selTsetID).getTilecount();i++)
						{
							boolean ada=false;
							
							for (int k=0; k<tilesets.get(selTsetID).getTiles().size();k++){
							if (tilesets.get(selTsetID).getTiles().get(k).getTileID()==i)
							{
								java.util.List<property> ppt = tilesets.get(selTsetID).getTiles().get(k).getProperties();
								for (int j=0;j<ppt.size();j++)
								{
									if (ppt.get(j).getName().equalsIgnoreCase(n) && ppt.get(j).getValue().equalsIgnoreCase(v))
									{
										ada=true;
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
					if (n.equalsIgnoreCase("embedded_png"))
					{

						String foredir="", tempdir="", combo="";
						try{
							//status(n+v,5);
							
							Texture bucket;
							String base64 = v;
							byte[] decodedBytes = Base64Coder.decode(base64);
							bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));

							tileset tempTset = tilesets.get(selTsetID);
							tempTset.setTexture(bucket);

							if (tempTset.getTrans() !=null){
								tempTset.setTexture(chromaKey(tempTset.getTexture(),tempTset.getTrans()));
							}



							tempTset.setOriginalwidth(bucket.getWidth());
							tempTset.setOriginalheight(bucket.getHeight());
							if (tempTset.getColumns()==0)
							{
								tempTset.setColumns((tempTset.getOriginalwidth()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTilewidth()+tempTset.getSpacing()));
								tempTset.setWidth(tempTset.getColumns());
								tempTset.setHeight((tempTset.getOriginalheight()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTileheight()+tempTset.getSpacing()));
								tempTset.setTilecount(tempTset.getWidth()*tempTset.getHeight());
							}

							templastID += tempTset.getWidth() * tempTset.getHeight();
							fTsPropSource.setText("");
							cbTsPropUseTsx.setChecked(false);
							tempTset.setSource("");
							alreadyloaded=true;
						}catch(Exception e){
							ErrorBung(e,"okok.txt");
						}



					}
					//////
					if (senderID !=-1){ //edit
						
						pp.get(dexo).setName(n);
						pp.get(dexo).setType(t);
						pp.get(dexo).setValue(v);
						
					}else{
						pp.add(new property(n,t,v));
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
			
		bPropCancel.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					switch (sender)
					{
						case "mass":
							gotoStage(tTileMgmt);
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
		tPropEditor.add(new Label(z.property+" "+z.name, skin)).padBottom(2).left().colspan(2).row();
		tPropEditor.add(fPropName).padBottom(5).colspan(2).left().row();
		tPropEditor.add(new Label(z.type, skin)).width(btnx/2-3).left().padBottom(5);
		tPropEditor.add(sbPropType).padBottom(5).width(btnx/2).row();
		tPropEditor.add(new Label(z.value, skin)).padBottom(2).left().colspan(2).row();
		tPropEditor.add(stack).colspan(2).left().height(btny*4).row();
		tPropEditor.add(bPropGid).padTop(2).padBottom(5).colspan(2).left().row();
		tPropEditor.add(bProppng).padTop(2).padBottom(5).colspan(2).left().row();
		tPropEditor.add(bPropApply).padTop(20).padBottom(5).colspan(2).left().row();
		tPropEditor.add(bPropCancel).padBottom(5).colspan(2).left().row();
		bPropValfile.setVisible(false);
		bPropValfile.setHeight(0);
		sbPropValbool.setVisible(false);
		sbPropValbool.setHeight(0);
		
	}
	
	
	public void loadPreferences()
	{
		//prefs.putString("lastpath", extRoot);
		//prefs.flush();
		lastpath = prefs.getString("lastpath", extRoot);
		//language= prefs.getString("language", "english");
		sShowGrid = prefs.getBoolean("grid", true);
		sEnableBlending = prefs.getBoolean("blending", true);
		sBgcolor = prefs.getString("background","888888");
		bgr=(float) Integer.parseInt(sBgcolor.substring(0,2),16)/256;
		bgg=(float) Integer.parseInt(sBgcolor.substring(2,4),16)/256;
		bgb=(float) Integer.parseInt(sBgcolor.substring(4,6),16)/256;
		
		
		sShowFPS = prefs.getBoolean("fps", false);
		sAutoSave= prefs.getBoolean("autosave", true);
		sShowGID = prefs.getBoolean("gid", false);
		sShowGIDmap = prefs.getBoolean("gidmap", false);
		sSaveTsx = prefs.getBoolean("tsx", false);
		scrollspeed=prefs.getInteger("ss",3);
		sShowCustomGrid = prefs.getBoolean("customgrid", false);
		sGridX = prefs.getInteger("gridx", 5);
		sResizeTiles = prefs.getBoolean("resize", false);
		sGridY = prefs.getInteger("gridy", 5);
		zoomTreshold = prefs.getInteger("zoom", 2);
		
		sbLanguage=new SelectBox(skin);
		
		java.util.List<String> srr = new ArrayList<String>();
		FileHandle dirHandle;
		dirHandle = Gdx.files.internal("languages/");
		for (FileHandle entry: dirHandle.list()) {
			if (!entry.file().getName().equalsIgnoreCase("characters")){
			srr.add(entry.file().getName());
			}
		}
		sbLanguage.setItems(srr.toArray(new String[0]));
		bBack3 = new TextButton(z.back, skin);
		bBack3.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});

		bPreference.addListener(new ChangeListener(){

				

				
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tPreference);
					cbShowGrid.setChecked(sShowGrid);
					cbShowFPS.setChecked(sShowFPS);
					cbAutoSave.setChecked(sAutoSave);
					cbEnableBlending.setChecked(sEnableBlending);
					sdScrollSpeed.setValue(scrollspeed);
					cbShowGid.setChecked(sShowGID);
					fBgcolor.setText(sBgcolor);
					fFontsize.setText(Integer.toString(fontsize));
					sbLanguage.setSelected(language);
					cbResize.setChecked(sResizeTiles);
					oldlang=language;
					oldfontsize=fontsize;
					cbShowGidmap.setChecked(sShowGIDmap);
					cbShowCustomGrid.setChecked(sShowCustomGrid);
					fGridX.setText(Integer.toString(sGridX));
					fGridY.setText(Integer.toString(sGridY));
					fzoomtresh.setText(Integer.toString(zoomTreshold));
					
				}
			});

		
		cbShowGrid = new CheckBox(z.showgrid, skin);
		cbShowFPS = new CheckBox(z.showfps, skin);
		cbEnableBlending = new CheckBox(z.enableblending, skin);
		sdScrollSpeed= new Slider(1, 5, 1, false, skin);
		cbShowGid = new CheckBox(z.showgidinpicker, skin);
		cbAutoSave = new CheckBox(z.autosaving, skin);
		cbResize = new CheckBox(z.resizetiles,skin);
		cbShowGidmap = new CheckBox(z.showgidinmap, skin);
		cbShowCustomGrid = new CheckBox(z.showcustomgrid, skin);
		fzoomtresh = new TextField(Integer.toString(zoomTreshold),skin);
		fzoomtresh.setTextFieldFilter(tffint);
		fBgcolor=new TextField(sBgcolor,skin);
		fBgcolor.setTextFieldFilter(tffcolor);
		fFontsize=new TextField(Integer.toString(fontsize),skin);
		fFontsize.setTextFieldFilter(tffint);
		fGridX = new TextField(Integer.toString(sGridX),skin);
		fGridX.setTextFieldFilter(tffint);
		fGridY = new TextField(Integer.toString(sGridY),skin);
		fGridY.setTextFieldFilter(tffint);
		
		Button bSavePref = new TextButton(z.save, skin);
		bSavePref.addListener(new ChangeListener(){

				
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					sShowGrid = cbShowGrid.isChecked();
					prefs.putBoolean("grid", sShowGrid).flush();
					sEnableBlending = cbEnableBlending.isChecked();
					prefs.putBoolean("blending", sEnableBlending).flush();
					sShowFPS = cbShowFPS.isChecked();
					prefs.putBoolean("fps", sShowFPS).flush();
					sAutoSave = cbAutoSave.isChecked();
					prefs.putBoolean("autosave", sAutoSave).flush();
					sShowGID = cbShowGid.isChecked();
					prefs.putBoolean("gid", sShowGID).flush();
					sShowGIDmap = cbShowGidmap.isChecked();
					prefs.putBoolean("gidmap", sShowGIDmap).flush();
					fontsize=Integer.parseInt(fFontsize.getText());
					prefs.putInteger("fontsize",fontsize).flush();
					sResizeTiles = cbResize.isChecked();
					prefs.putBoolean("resize", sResizeTiles).flush();
					
					sBgcolor=fBgcolor.getText();
					if (sBgcolor.length()!=6) sBgcolor="888888";
					prefs.putString("background", sBgcolor).flush();
					bgr=(float) Integer.parseInt(sBgcolor.substring(0,2),16)/256;
					bgg=(float) Integer.parseInt(sBgcolor.substring(2,4),16)/256;
					bgb=(float) Integer.parseInt(sBgcolor.substring(4,6),16)/256;

					
					language=sbLanguage.getSelected().toString();
					
					prefs.putString("language", language).flush();
					//reloadLanguage();
					
					scrollspeed = (int) sdScrollSpeed.getValue();
					prefs.putInteger("ss", scrollspeed).flush();
					
					sShowCustomGrid = cbShowCustomGrid.isChecked();
					prefs.putBoolean("customgrid", sShowCustomGrid).flush();
					sGridX = Integer.parseInt(fGridX.getText());
					if (sGridX==0) sGridX=10;
					prefs.putInteger("gridx", sGridX).flush();
					
					zoomTreshold = Integer.parseInt(fzoomtresh.getText());
					if (zoomTreshold==0) zoomTreshold=4;
					prefs.putInteger("zoom", zoomTreshold).flush();
					
					sGridY = Integer.parseInt(fGridY.getText());
					if (sGridY==0) sGridY=10;
					prefs.putInteger("gridy", sGridY).flush();
					backToMap();
					if (!language.equalsIgnoreCase(oldlang) || fontsize!=oldfontsize)
					{
						msgbox(z.restart);
					}
				}

				
			});

		tPreference = new Table();
		tPreference.setFillParent(true);
	
		tPreference.defaults().width(btnx).height(btny*4/5);
		/*
		cbShowGrid.getLabelCell().padLeft(5);
		cbShowFPS.getLabelCell().padLeft(5);
		cbShowCustomGrid.getLabelCell().padLeft(5);
		*/
		tPreference.add(new Label(z.language,skin)).width(btnx/2);
		tPreference.add(sbLanguage).width(btnx/2).padBottom(5).row();
		tPreference.add(new Label(z.zoomlimit,skin)).width(btnx/2);
		tPreference.add(fzoomtresh).width(btnx/2).padBottom(2).row();
		tPreference.add(new Label(z.background,skin)).width(btnx/2);
		tPreference.add(fBgcolor).width(btnx/2).padBottom(2).row();
		
		tPreference.add(new Label(z.fontsize,skin)).width(btnx/2);
		tPreference.add(fFontsize).width(btnx/2).padBottom(2).row();
		tPreference.add(cbAutoSave).align(Align.left).colspan(2).width(btnx).row();
		
		tPreference.add(cbEnableBlending).align(Align.left).colspan(2).width(btnx).row();
		tPreference.add(cbShowGrid).width(btnx).left().colspan(2).row();
		tPreference.add(cbResize).align(Align.left).left().colspan(2).width(btnx).row();
		
		tPreference.add(cbShowFPS).colspan(2).align(Align.left).width(btnx).left().row();
		tPreference.add(cbShowGid).align(Align.left).colspan(2).left().width(btnx).row();
		tPreference.add(cbShowGidmap).align(Align.left).colspan(2).left().width(btnx).row();
		tPreference.add(new Label(z.scrollspeed,skin)).align(Align.left).width(btnx).colspan(2).left().row();
		
		tPreference.add(sdScrollSpeed).align(Align.left).width(btnx).colspan(2).left().row();
		
		tPreference.add(cbShowCustomGrid).align(Align.left).colspan(2).left().row();
		tPreference.add(new Label(z.gridx,skin)).padBottom(5).width(btnx/2);
		tPreference.add(fGridX).width(btnx/2).padBottom(5).row();
		
		tPreference.add(new Label(z.gridy,skin)).padBottom(10).width(btnx/2);
		tPreference.add(fGridY).width(btnx/2).padBottom(10).row();
		
		tPreference.add(bSavePref).width(btnx).padBottom(5).height(btny).colspan(2).row();
		tPreference.add(bBack3).width(btnx).padBottom(5).height(btny).colspan(2);

	}
	public void reloadLanguage()
	{
		Json json = new Json();
		FileHandle f = Gdx.files.internal("languages/"+language);
		z = json.fromJson(language.class, f);
		shapeName=z.rectangle;
		toolName=z.tile;
		viewModeName=z.stack;
		objViewModeName=z.all;
		magnetName=z.lock;
		try
		{
		/*
		json = new Json();
		f = Gdx.files.internal("tutorials/"+language);
		tutor = json.fromJson(Tutorials.class, f);
		face.changelanguage(language);
		*/
		tutor.generatesample();
		face.changelanguage("English");
		}
		catch(Exception e)
		{
			ErrorBung(e,"nyut.txt");
		}
		
	}
	
	public void loadLayerManagement()
	{
		bAddLayer = new TextButton(z.addnew+" "+z.layer, skin);
		bRemoveLayer = new TextButton(z.remove+" "+z.layer, skin);
		bMoveLayer = new TextButton(z.moveup, skin);
		bEditLayer = new TextButton(z.rename+" "+z.layer, skin);
		bSetOpacity = new TextButton(z.setopacity,skin);

		llayerlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

		bBackLayer = new TextButton(z.back, skin);
		bBackLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});


		bTileMgmt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					String[] srr = new String[layers.size()];
					for (int i=0;i < layers.size();i++)
					{
						srr[i] = layers.get(i).getName();

					}
					llayerlist.setItems(srr);
					gotoStage(tLayerMgmt);
				}
			});

		pNewLayer = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}

				layer newlayer = new layer();
				java.util.List<Long> newstr= new ArrayList<Long>();
				java.util.List<Integer> newtset= new ArrayList<Integer>();
				
				for (int i=0;i < Tw * Th;i++)
				{
					newstr.add((long) 0);
					newtset.add(-1);
				}
				newlayer.setStr(newstr);
				newlayer.setTset(newtset);
				newlayer.setName(input);
				layers.add(newlayer);
				String[] srr = new String[layers.size()];
				for (int i=0;i < layers.size();i++)
				{
					srr[i] = layers.get(i).getName();

				}
				llayerlist.setItems(srr);
				llayerlist.setSelectedIndex(layers.size() - 1);
			}
			@Override
			public void canceled()
			{}

		};

		pNewLayerSC = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}

				layer newlayer = new layer();
				java.util.List<Long> newstr= new ArrayList<Long>();
				java.util.List<Integer> newtset= new ArrayList<Integer>();

				for (int i=0;i < Tw * Th;i++)
				{
					newstr.add((long) 0);
					newtset.add(-1);
				}
				newlayer.setStr(newstr);
				newlayer.setTset(newtset);
				newlayer.setName(input);
				layers.add(newlayer);
				seltile=layers.size()-1;
			}
			@Override
			public void canceled()
			{}

		};
		pEditLayer = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				int dexo=llayerlist.getSelectedIndex();
				layers.get(dexo).setName(input);

				String[] srr = new String[layers.size()];
				for (int i=0;i < layers.size();i++)
				{
					srr[i] = layers.get(i).getName();
				}
				llayerlist.setItems(srr);
				llayerlist.setSelectedIndex(dexo);
			}
			@Override
			public void canceled()
			{}

		};

		pSetOpacity = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{input="1";}
				try{
				layers.get(llayerlist.getSelectedIndex()).setOpacity(Float.parseFloat(input));
				}catch (Exception e){
				}
				
				
			}
			@Override
			public void canceled()
			{}

		};
		
		
		bAddLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.input.getTextInput(pNewLayer, z.layer+ " " + z.name, "Layer" + (layers.size() + 1),"");
				}
			});

		bEditLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.input.getTextInput(pEditLayer, z.edit+", "+z.layer+" "+z.name, llayerlist.getSelected().toString(),"");

				}
			});	

		bSetOpacity.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					
					if (layers.size()>0)
					{
					Gdx.input.getTextInput(pSetOpacity, z.setopacity, Float.toString(layers.get(llayerlist.getSelectedIndex()).getOpacity()),"");
					}
				}
			});
			
		bMoveLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = llayerlist.getSelectedIndex();
					if (dex > 0)
					{
						java.util.Collections.swap(layers, dex, dex - 1);
						String[] srr = new String[layers.size()];
						for (int i=0;i < layers.size();i++)
						{
							srr[i] = layers.get(i).getName();

						}
						llayerlist.setItems(srr);
						llayerlist.setSelectedIndex(dex - 1);
					}
				}
			});	

		bRemoveLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (layers.size() > 1)
					{
						int dex=llayerlist.getSelectedIndex();
						layers.remove(dex);
						seltile = layers.size() - 1;
						String[] srr = new String[layers.size()];
						for (int i=0;i < layers.size();i++)
						{
							srr[i] = layers.get(i).getName();

						}
						llayerlist.setItems(srr);
						if (dex < 1) dex = 1;
						llayerlist.setSelectedIndex(dex - 1);
					}
				}
			});


		tLayerMgmt = new Table();
		tLayerMgmt.setFillParent(true);
		tLayerMgmt.defaults().width(btnx).height(btny);
		ScrollPane scrollPane = new ScrollPane(llayerlist);
		tLayerMgmt.add(new Label(z.tilelayer, skin)).padBottom(5).row();
		tLayerMgmt.add(scrollPane).height(btny*4).padBottom(5).row();
		tLayerMgmt.add(bAddLayer).padBottom(5).row();
		tLayerMgmt.add(bEditLayer).padBottom(5).row();
		tLayerMgmt.add(bSetOpacity).padBottom(5).row();
		tLayerMgmt.add(bMoveLayer).padBottom(5).row();
		tLayerMgmt.add(bRemoveLayer).padBottom(5).row();
		tLayerMgmt.add(bBackLayer).padBottom(5);
	}
	public void refreshProperties(java.util.List<property> at)
	{
		lproplist.setItems(new String[]{});
		int saiz=at.size();

		if (saiz>0)
		{
			String[] srr = new String[saiz];
			for (int i=0;i < saiz;i++)
			{
				if (at.get(i).getValue().contains("\n"))
				{
					srr[i] = at.get(i).getName() + " : " + at.get(i).getValue().split("\n")[0] + " ...";
				}else{
					srr[i] = at.get(i).getName() + " : " + at.get(i).getValue();
				}
			}
			lproplist.setItems(srr);
			lproplist.setSelectedIndex(saiz - 1);
		}
	}
	
	public void loadPropsManagement()
	{

		bAddProp = new TextButton(z.addnew, skin);
		bRemoveProp = new TextButton(z.remove, skin);
		bMoveProp = new TextButton(z.moveup, skin);
		bEditProp = new TextButton(z.edit, skin);
		bPropCopy = new TextButton(z.copyall, skin);
		bPropPaste= new TextButton(z.paste, skin);
		
		bPropTemplate = new TextButton(z.template,skin);
		bPropParse = new TextButton(z.parse,skin);
		lPropID = new Label(z.id, skin);
		lproplist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

		bBackProp = new TextButton(z.back, skin);
		bBackProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					switch (sender){
						case "object":
							gotoStage(tObjProp);
							break;
						case "tile":
							gotoStage(tTileMgmt);
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
		bPropCopy.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex=lproplist.getSelectedIndex();
					java.util.List<property> pp = new ArrayList<property>();
					switch (sender){
						case "object":
							pp=objgroups.get(ogroup).getObjects().get(oedit).getProperties();
							break;
						case "tile":
							pp=tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
							break;
						case "tset":
							pp=tilesets.get(selTsetID).getProperties();
							break;
						case "map":
							pp=properties;
							break;
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
					}
					properties at=new properties();
					at.setProperties(pp);
					Json json = new Json();
					clipProp=json.toJson(at);
					
					
				}
			});
		
		bPropPaste.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					
					try{

						properties at=new properties();
						Json json = new Json();
						at = json.fromJson(properties.class, clipProp);
						
						switch (sender){
							case "object":
								objgroups.get(ogroup).getObjects().get(oedit).setProperties(at.getProperties());

								break;
							case "tile":
								tilesets.get(selTsetID).getTiles().get(selTileID).setProperties(at.getProperties());

								break;
							case "tset":
								tilesets.get(selTsetID).setProperties(at.getProperties());

								break;
							case "map":
								properties=at.getProperties();
								break;
							case "auto":
								autotiles.get(selat).setProperties(at.getProperties());
								break;
						}

						refreshProperties(at.getProperties());
						
					}catch(Exception e)
					{
						msgbox(z.error);
					}
					
				}
			});
		
		bPropParse.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					try{
					java.util.List<property> pp = new ArrayList<property>();
					switch (sender){
						
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
						default:
							return;
					}
					
					int saiz=pp.size();
						
					String[] srr = new String[saiz];
					int startingID=0;
					tileset t = tilesets.get(seltset);
					int col = t.getColumns();
						for (int i=0;i < saiz;i++)
						{
							switch (pp.get(i).getName())
							{
								
								case "source":
									startingID=Integer.parseInt(pp.get(i).getValue());
									break;
								case "center":
									pp.get(i).setValue(Integer.toString(startingID));
									break;
								case "topleft":
									pp.get(i).setValue(Integer.toString(startingID-col-1));
									break;
								case "topright":
									pp.get(i).setValue(Integer.toString(startingID-col+1));
									break;
								case "bottomleft":
									pp.get(i).setValue(Integer.toString(startingID+col-1));
									break;
								case "bottomright":
									pp.get(i).setValue(Integer.toString(startingID+col+1));
									break;
								case "top":
									pp.get(i).setValue(Integer.toString(startingID-col));
									break;
								case "left":
									pp.get(i).setValue(Integer.toString(startingID-1));
									break;
								case "right":
									pp.get(i).setValue(Integer.toString(startingID+1));
									break;
								case "bottom":
									pp.get(i).setValue(Integer.toString(startingID+col));
									break;
								case "bigtopleft":
									pp.get(i).setValue(Integer.toString(startingID+col*2-1));
									break;
								case "bigtopright":
									pp.get(i).setValue(Integer.toString(startingID+col*2+0));
									break;
								case "bigbottomleft":
									pp.get(i).setValue(Integer.toString(startingID+col*3-1));
									break;
								case "bigbottomright":
									pp.get(i).setValue(Integer.toString(startingID+col*3+0));
									break;
								
							}
							
							srr[i] = pp.get(i).getName() + " : " + pp.get(i).getValue();
						}
						lproplist.setItems(srr);
					
					}catch(Exception e)
					{
						
					}
				}
				
			});
			
		bProps.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					refreshProperties(objgroups.get(ogroup).getObjects().get(oedit).getProperties());
					
					lPropID.setText(z.object+" "+z.properties+": "+ objgroups.get(ogroup).getObjects().get(oedit).getId());
					sender="object";
					gotoStage(tPropsMgmt);
				}
			});
			
			
		bPropsTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("tileprop");
				}
			});
		
		bPropertiesMap.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					refreshProperties(properties);
					
					lPropID.setText(z.map+z.customproperties);
					sender="map";
					gotoStage(tPropsMgmt);
				}
			});
			
		bTsPropCustomProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					refreshProperties(tilesets.get(selTsetID).getProperties());
					
					lPropID.setText(z.customproperties);
					sender="tset";
					gotoStage(tPropsMgmt);
				}
			});
		

		bAutoprops.addListener(new ChangeListener(){

				
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (autotiles.size()>0) {
					int dex=lautolist.getSelectedIndex();
					
					refreshProperties(autotiles.get(dex).getProperties());
					selat=dex;
					lPropID.setText(z.autotile +" " + autotiles.get(dex).getName());
					sender="auto";
					gotoStage(tPropsMgmt);
					}
				}
			});

		bAddProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					senderID=-1;
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

		bEditProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (lproplist.getItems().size==0) return;
					int dex=lproplist.getSelectedIndex();
					java.util.List<property> pp = new ArrayList<property>();
					switch (sender){
						case "object":
							pp=objgroups.get(ogroup).getObjects().get(oedit).getProperties();
							break;
						case "tset":
							pp=tilesets.get(selTsetID).getProperties();
							break;
						case "tile":
							pp=tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
							break;
						case "map":
							pp=properties;
							break;
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
					}
					int saiz=pp.size();
					if (saiz >= 0)
					{
						fPropName.setText(pp.get(dex).getName());
						sbPropType.setSelected(pp.get(dex).getType());
						fPropVal.setVisible(false);
						bPropValfile.setVisible(false);
						sbPropValbool.setVisible(false);
						switch(sbPropType.getSelected().toString()){
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
						senderID=saiz;
						gotoStage(tPropEditor);
					}
				}
			});	

		bMoveProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = lproplist.getSelectedIndex();
					
					java.util.List<property> pp = new ArrayList<property>();
					switch (sender){
						case "object":
							pp=objgroups.get(ogroup).getObjects().get(oedit).getProperties();
							break;
						case "tile":
							pp=tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
							break;
						case "tset":
							pp=tilesets.get(selTsetID).getProperties();
							break;
						case "map":
							pp=properties;
							break;
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
					}
					int saiz=pp.size();
					if (dex > 0)
					{
						java.util.Collections.swap(pp, dex, dex - 1);
						
						refreshProperties(pp);
						
						lproplist.setSelectedIndex(dex - 1);
					}
				}
			});	

		bRemoveProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					java.util.List<property> pp = new ArrayList<property>();
					switch (sender){
						case "object":
							pp=objgroups.get(ogroup).getObjects().get(oedit).getProperties();
							break;
						case "tile":
							pp=tilesets.get(selTsetID).getTiles().get(selTileID).getProperties();
							break;
						case "tset":
							pp=tilesets.get(selTsetID).getProperties();
							break;
						case "map":
							pp=properties;
							break;
						case "auto":
							pp=autotiles.get(selat).getProperties();
							break;
					}
					int saiz=pp.size();
					if (saiz > 0)
					{
						int dex=lproplist.getSelectedIndex();
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
		tPropsMgmt.add(scrollPane7).height(btny*4).padBottom(2).row();
		tPropsMgmt.add(bAddProp).padBottom(2).row();
		tPropsMgmt.add(bEditProp).padBottom(2).row();
		tPropsMgmt.add(bMoveProp).padBottom(2).row();
		tPropsMgmt.add(bPropTemplate).padBottom(2).row();
		tPropsMgmt.add(bPropParse).padBottom(2).row();
		tPropsMgmt.add(bPropCopy).padBottom(2).row();
		tPropsMgmt.add(bPropPaste).padBottom(2).row();
		tPropsMgmt.add(bRemoveProp).padBottom(2).row();
		tPropsMgmt.add(bBackProp).padBottom(2);
	}

	public void refreshAutoMgmt()
	{
		String[] srr= new String[]{};
		if (autotiles.size() > 0)
		{
			srr = new String[autotiles.size()];
			for (int i=0;i < autotiles.size();i++)
			{
				srr[i] = autotiles.get(i).getName();
			}
		}
		lautolist.setItems(srr);
	}
	
	private void loadAutotileList()
	{
		Table tblmain = new Table();
		Table tbl = new Table();
		ScrollPane sp= new ScrollPane(tbl);
		tblmain.add(sp);
		tblmain.setFillParent(true);
		tbl.defaults().width(btnx).height(btny).padBottom(2);
		//java.util.List<TextButton> at = new ArrayList<TextButton>();
		
		for (int i=0;i<autotiles.size();i++)
		{
			java.util.List<property> sz = autotiles.get(i).getProperties();
			String type="";String name="";boolean hidden=false;
			int num=0;
			name=autotiles.get(i).getName();
		
			for (int j=0;j<sz.size();j++)
			{
				
				if (sz.get(j).getName().equalsIgnoreCase("type"))
				{
					type=sz.get(j).getValue();
				}
				if (sz.get(j).getName().equalsIgnoreCase("source"))
				{
					num=Integer.parseInt(sz.get(j).getValue());
				}
				if (sz.get(j).getName().equalsIgnoreCase("hidden"))
				{
					hidden=true;
				}
				
			}
			if (hidden) continue;
			if (num==0) continue;
			if (type=="") continue;
			switch (type)
			{
				case "bitmask":case "completion":case "fill":
					Button tat=new Button(skin);
					tat.left();
					int initset=0;
					for (int o=0;o<tilesets.size();o++)
					{
						if (num >= tilesets.get(o).getFirstgid() && num < tilesets.get(o).getFirstgid()+tilesets.get(o).getTilecount())
						{
							initset=o;
							break;
						}
					}
					sprX = (int)(num - tilesets.get(initset).getFirstgid()) % (tilesets.get(initset).getWidth());
					sprY = (int)(num - tilesets.get(initset).getFirstgid()) / (tilesets.get(initset).getWidth()) ;
					margin=tilesets.get(initset).getMargin();
					spacing =tilesets.get(initset).getSpacing();
					Tswa=tilesets.get(initset).getTilewidth();
					Tsha=tilesets.get(initset).getTileheight();
					TextureRegion region= new TextureRegion(tilesets.get(initset).getTexture(), (sprX * (Tsw+spacing))+margin, (sprY * (Tsh+spacing))+margin, Tsw, Tsh);
					Image img2 = new Image(region);
					tat.add(img2).padRight(10).padLeft(2).padTop(2).padBottom(2).width(ssx/15f).height(ssx/15f);
					String nen="";
					if (sShowGID) 
					{
						nen=name+" ("+num+")";
					}else
					{
						nen=name;
					}
					Label lbl = new Label(nen,skin);
					lbl.setColor(1,1,1,1);
					lbl.setWrap(true);
					tat.add(lbl).width(btnx-55);
					tat.setName(num+"");
					tat.addListener(new ChangeListener(){
							@Override
							public void changed(ChangeEvent event, Actor actor)
							{
								curspr=Integer.parseInt(actor.getName());
								for (int i=0;i<tilesets.size();i++)
								{
									if (curspr >= tilesets.get(i).getFirstgid() && curspr < tilesets.get(i).getFirstgid()+tilesets.get(i).getTilecount())
									{
										seltset=i;
										break;
									}
								}
								backToMap();
							}
						});
					tbl.add(tat).height(btny*1.5f).row();
					break;
			}
		}
		TextButton tat=new TextButton(z.back,skin);
		tat.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
		tbl.add(tat).row();
		gotoStage(tblmain);
	}
	
	private void loadList(String wla)
	{
		this.wl=wla;
		Table tblmain = new Table();
		Table tbl = new Table();
		ScrollPane sp= new ScrollPane(tbl);
		tblmain.add(sp);
		tblmain.setFillParent(true);
		tbl.defaults().width(btnx).padBottom(2);
		//java.util.List<TextButton> at = new ArrayList<TextButton>();
		int tt=0;
		switch (wl)
		{
			case "layer":
				tt=layers.size();
				tbl.add(new Label(z.tilelayer,skin)).row();
				break;
			case "obj":
				tt=objgroups.size();
				tbl.add(new Label(z.objectgroup,skin)).row();
				break;
			case "tset":
				tt=tilesets.size();
				tbl.add(new Label(z.tileset,skin)).row();
				break;
		}
		
		for (int i=0;i<tt;i++)
		{
			String name="",moe="";
			Button tat=new Button(skin);
			tat.defaults().center();
			int num=i;
			switch (wl)
			{
				case "layer":
					name=layers.get(i).getName();
					break;
				case "obj":
					name=objgroups.get(i).getName();
					break;
				case "tset":
					name=tilesets.get(i).getName();
					break;
			}
			
			Label lbl = new Label(name,skin);
			lbl.setColor(1,1,1,1);
			lbl.setAlignment(Align.center);
			lbl.setWrap(true);
			tat.add(lbl).width(btnx);
			tat.setName(i+"");
			
			tat.addListener(new ChangeListener(){
							@Override
							public void changed(ChangeEvent event, Actor actor)
							{
								switch (wl)
								{
									case "layer":
										seltile=Integer.parseInt(actor.getName());
										backToMap();
										break;
									case "obj":
										selgroup=Integer.parseInt(actor.getName());
										backToMap();
										break;
									case "tset":
										seltset=Integer.parseInt(actor.getName());
										onToPicker();
										break;
								}
								
								
							}
						});
			tbl.add(tat).row();
			}
		
		TextButton tit=new TextButton("+",skin);
		tit.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					switch (wl)
					{
						case "layer":
							Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer +" "+ (layers.size() + 1),"");
							backToMap();
							break;
						case "obj":
							Gdx.input.getTextInput(pNewObjLayerSC, z.addnew, z.object+" " + (objgroups.size() + 1),"");
							backToMap();
							break;
						case "tset":
							FileDialog(z.selectfile,"quickaddtset","file",new String[]{".tsx",".png",".jpg",".jpeg",".bmp",".gif"},nullTable);
							break;
					}
				}
			});
		tbl.add(tit).row();
		
		TextButton tut=new TextButton(z.back,skin);
		tut.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
		tbl.add(tut).row();
		gotoStage(tblmain);
	}
	
	
	private void loadtoolList()
	{
		Table tblmain = new Table();
		Table tbl = new Table();
		ScrollPane sp= new ScrollPane(tbl);
		tblmain.add(sp);
		tblmain.setFillParent(true);
		tbl.defaults().width(btnx).padBottom(2);
		//java.util.List<TextButton> at = new ArrayList<TextButton>();
		tbl.add(new Label(z.tilelayer,skin)).row();
		for (int i=0;i<5;i++)
		{
			String name="";
			Button tat=new Button(skin);
			tat.defaults().center();
			int num=i;
			switch (i)
			{
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
			name=layers.get(i).getName();
			Label lbl = new Label(name,skin);
			lbl.setColor(0,0,0,1);
			lbl.setAlignment(Align.center);
			lbl.setWrap(true);
			tat.add(lbl).width(btnx);
			tat.setName(num+"");
			tat.addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor)
					{
						seltile=Integer.parseInt(actor.getName());
						backToMap();
					}
				});
			tbl.add(tat).row();
		}

		TextButton tat=new TextButton(z.back,skin);
		tat.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
		tbl.add(tat).row();
		gotoStage(tblmain);
	}
	
	public void loadPropTemplate()
	{
		Label lPT = new Label(z.template,skin);
		final com.badlogic.gdx.scenes.scene2d.ui.List<String> lptlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		TextButton bApplyPT = new TextButton(z.apply, skin);
		TextButton bptback = new TextButton(z.back, skin);
		
		bPropTemplate.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor)
				{

						lptlist.setItems(new String[]{});
						java.util.List<String> srr = new ArrayList<String>();
						FileHandle dirHandle;
						dirHandle = Gdx.files.internal("template/");
						for (FileHandle entry: dirHandle.list()) {
							
							srr.add(entry.file().getName());
						}
						lptlist.setItems(srr.toArray(new String[0]));
						gotoStage(tpt);

				}
			});
			
		bptback.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor)
				{

					gotoStage(tPropsMgmt);

				}
			});
			
		bApplyPT.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{

					//try{

						properties at=new properties();
						Json json = new Json();
						FileHandle f = Gdx.files.internal("template/"+lptlist.getSelected().toString());
						at = json.fromJson(properties.class, f);

						switch (sender){
							case "object":
								objgroups.get(ogroup).getObjects().get(oedit).setProperties(at.getProperties());

								break;
							case "tile":
								tilesets.get(selTsetID).getTiles().get(selTileID).setProperties(at.getProperties());

								break;
							case "map":
								properties=at.getProperties();
								break;
							case "auto":
								autotiles.get(selat).setProperties(at.getProperties());
								break;
						}


						int saiz=at.getProperties().size();

						if (saiz>0)
						{
							String[] srr = new String[saiz];
							for (int i=0;i < saiz;i++)
							{
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
		ScrollPane spn=new ScrollPane(lptlist);
		tpt.add(spn).height(btnx).row();
		tpt.add(bApplyPT).row();
		tpt.add(bptback).row();
		
	}
	
	public void loadAutoManagement()
	{
		Label lAutotitle = new Label(z.autotile,skin);
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
		ScrollPane spx=new ScrollPane(lautolist);
		tAutoMgmt.add(spx).height(btny*4).row();
		tAutoMgmt.add(bAutoadd).row();
		tAutoMgmt.add(bAutorename).row();
		tAutoMgmt.add(bAutoprops).row();
		tAutoMgmt.add(bAutomoveup).row();
		tAutoMgmt.add(bAutoremove).row();
		tAutoMgmt.add(bAutosave).row();
		tAutoMgmt.add(bAutoload).row();
		tAutoMgmt.add(bAutoback).row();
		
		pAutoadd = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input != "")
				{
					autotiles.add(new autotile(input));
					refreshAutoMgmt();
				}
			}
			@Override
			public void canceled()
			{}

		};
		
		pAutorename = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input != "")
				{
					autotiles.get(lautolist.getSelectedIndex()).setName(input);
					refreshAutoMgmt();
				}
			}
			@Override
			public void canceled()
			{}

		};
		
		bAutoMgmt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tAutoMgmt);
					refreshAutoMgmt();
				}
			});
		
		bAutoadd.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.input.getTextInput(pAutoadd, "New Autotile Name", "","");
				}
			});
			
		bAutorename.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex=lautolist.getSelectedIndex();
					if (dex>=0)
					{
						Gdx.input.getTextInput(pAutorename, "Rename Autotile", autotiles.get(dex).getName(),"");
					}
				}
			});
			
		bAutomoveup.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex=lautolist.getSelectedIndex();
					if (dex>0)
					{
						java.util.Collections.swap(autotiles, dex, dex - 1);
						refreshAutoMgmt();
					}
				}
			});
			
		bAutoremove.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex=lautolist.getSelectedIndex();
					if (dex>=0)
					{
						autotiles.remove(dex);
						refreshAutoMgmt();
					}
				}
			});
			
		bAutoload.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
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
				}
			});
			
		bAutosave.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					autotiles at=new autotiles(autotiles);
					Json json = new Json();
					writeThisAbs(curdir+"/auto.json",json.prettyPrint(at));
					msgbox("auto.json saved!");
				
				}
			});
			
		bAutoback.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
	}
	public void loadautotiles()
	{
		try{
			autotiles.clear();
			autotiles at=new autotiles();
			Json json = new Json();
			FileHandle f = new FileHandle(curdir+"/auto.json");
			at = json.fromJson(autotiles.class, f);
			autotiles=at.getAutotiles();
			//refreshAutoMgmt();
		}catch(Exception e)
		{
		}
	}
	
	public void saveRecents()
	{
		Json json = new Json();
		FileHandle fh= Gdx.files.local("recents.json");
		fh.writeString(json.prettyPrint(recents),false);
	}
	public void loadTsetManagement()
	{

		bAddTset = new TextButton(z.addimagetileset, skin);
		bRemoveTset = new TextButton(z.remove, skin);
		bMoveTset = new TextButton(z.moveup, skin);
		bTileSettingsMgmt = new TextButton(z.tiles, skin);
		bPropTset = new TextButton(z.properties, skin);
		bUseTsx = new TextButton(z.importtsxfile, skin);
		bImportFolder = new TextButton(z.importfolder, skin);
		
		bTsPropSaveAsTsx=new TextButton(z.saveastsx,skin);
		bTsPropSaveAsTsx.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					
					int dep=ltsetlist.getSelectedIndex();
					if (dep>-1)
					{
						tileset t= tilesets.get(dep);
						t.setUsetsx(true);
						t.setTsxfile(t.getName()+".tsx");
						saveTsx(dep);
						msgbox(z.filesaved);
					}
					
				}
			});
		
		
		bUseTsx.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfile,"seltsx","file",new String[]{".tsx"},tTsetMgmt);
				}
			});
		
		bImportFolder.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfolder,"selfolder","dir",new String[]{},tTsetMgmt);
				}
			});
		
		
		ltsetlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

		bBackTset = new TextButton(z.back, skin);
		bBackTset.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});


		bTsetMgmt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int saiz=tilesets.size();
					
					
						String[] srr = new String[saiz];
						for (int i=0;i < saiz;i++)
						{
							srr[i] = tilesets.get(i).getName();
						}
						ltsetlist.setItems(srr);
					

					gotoStage(tTsetMgmt);
					frompick=false;
				}
			});

		
		

		bAddTset.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					//add tileset
					FileDialog(z.selectfile,"addtset","file",new String[]{".png",".jpg",".jpeg",".bmp",".gif"},tTsetMgmt);
				}
			});

		

		bPropTset.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (tilesets.size()>0){
						int dex=ltsetlist.getSelectedIndex();
						selTsetID=dex;
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
						
						if (ct.getName()!=null) fTsPropName.setText(ct.getName());
						if (ct.getSource()!=null) fTsPropSource.setText(ct.getSource());
						if (ct.getTsxfile()!=null) fTsPropTsxFile.setText(ct.getTsxfile());
						cbTsPropUseTsx.setChecked(ct.isUsetsx());
						if (ct.getTilewidth()!=0) fTsPropTsw.setText(Integer.toString(ct.getTilewidth()));
						if (ct.getTileheight()!=0) fTsPropTsh.setText(Integer.toString(ct.getTileheight()));
						if (ct.getColumns()!=0) fTsPropCols.setText(Integer.toString(ct.getColumns()));
						if (ct.getTilecount()!=0) fTsPropTc.setText(Integer.toString(ct.getTilecount()));
						if (ct.getMargin()!=0) fTsPropMargin.setText(Integer.toString(ct.getMargin()));
						if (ct.getSpacing()!=0) fTsPropSpacing.setText(Integer.toString(ct.getSpacing()));
						
						if (ct.getColumns()!=0) fTsPropCols.setText(Integer.toString(ct.getColumns()));
						if (ct.getTrans()!=null) fTsPropTrans.setText(ct.getTrans());
						
						gotoStage(tTsProp);
					}
				}
			});	
		
		
		bMoveTset.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = ltsetlist.getSelectedIndex();
					int saiz=tilesets.size();
					
					if (dex > 0)
					{
						java.util.Collections.swap(tilesets, dex, dex - 1);
						CacheAllTset();
						//reindexing
						templastID=1;
						/*
						for (int i=0;i<tilesets.size();i++){
							tilesets.get(i).setFirstgid(templastID);
							templastID+=tilesets.get(i).getTilecount();
						}
						*/
						String[] srr = new String[saiz];
						for (int i=0;i < saiz;i++)
						{
							srr[i] = tilesets.get(i).getName();
						}
						ltsetlist.setItems(srr);
						ltsetlist.setSelectedIndex(dex - 1);
					}
				}
			});	

		bRemoveTset.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					
					int saiz=tilesets.size();
					if (saiz > 0)
					{
						int dex=ltsetlist.getSelectedIndex();
						tilesets.remove(dex);
						java.util.List<Integer> nyot=new ArrayList<Integer>();
						CacheAllTset();
						if (seltset >0) seltset-=1;
						//reindexing
						templastID=1;
						/*
						for (int i=0;i<tilesets.size();i++){
							tilesets.get(i).setFirstgid(templastID);
							templastID+=tilesets.get(i).getTilecount();
						}
						*/
						saiz -= 1;
						String[] srr = new String[saiz];
						for (int i=0;i < saiz;i++)
						{
							srr[i] = tilesets.get(i).getName();
						}
						if (srr != null) ltsetlist.setItems(srr);

						if (dex > 0)
						{
							if (saiz > 0) ltsetlist.setSelectedIndex(dex - 1);
						}
						else
						{
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
		tTsetMgmt.add(scrollPane8).height(btny*4).padBottom(2).row();
		tTsetMgmt.add(bAddTset).padBottom(2).row();
		tTsetMgmt.add(bUseTsx).padBottom(2).row();
		tTsetMgmt.add(bImportFolder).padBottom(2).row();
		tTsetMgmt.add(bPropTset).padBottom(2).row();
		tTsetMgmt.add(bTileSettingsMgmt).padBottom(2).row();
		tTsetMgmt.add(bTsPropSaveAsTsx).padBottom(2).row();
		tTsetMgmt.add(bMoveTset).padBottom(2).row();
		tTsetMgmt.add(bRemoveTset).padBottom(2).row();
		tTsetMgmt.add(bBackTset).padBottom(2);
	}

	public void loadObjectManagement()
	{
		bAddObjLayer = new TextButton(z.addnew, skin);
		bRemoveObjLayer = new TextButton(z.remove, skin);
		bMoveObjLayer = new TextButton(z.moveup, skin);
		bEditObjLayer = new TextButton(z.rename, skin);
		lobjlist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);

		bBackObjLayer = new TextButton(z.back, skin);
		bBackObjLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});

		bObjMgmt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					String[] srr = new String[objgroups.size()];
					for (int i=0;i < objgroups.size();i++)
					{
						srr[i] = objgroups.get(i).getName();
						
					}
					lobjlist.setItems(srr);
					gotoStage(tObjMgmt);
				}
			});

		pNewObjLayer = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				
				if (input == "")
				{return;}

				objgroup newlayer = new objgroup();
				newlayer.setName(input);
				objgroups.add(newlayer);
				
				String[] srr = new String[objgroups.size()];
				for (int i=0;i < objgroups.size();i++)
				{
					srr[i] = objgroups.get(i).getName();
					
				}
				lobjlist.setItems(srr);
				lobjlist.setSelectedIndex(objgroups.size() - 1);
				
			}
			@Override
			public void canceled()
			{}

		};

		pNewObjLayerSC = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{

				if (input == "")
				{return;}

				objgroup newlayer = new objgroup();
				newlayer.setName(input);
				objgroups.add(newlayer);
				selgroup=objgroups.size()-1;
				
			}
			@Override
			public void canceled()
			{}

		};
		
		pEditObjLayer = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				int dexo=lobjlist.getSelectedIndex();
				objgroups.get(dexo).setName(input);

				String[] srr = new String[objgroups.size()];
				for (int i=0;i < objgroups.size();i++)
				{
					srr[i] = objgroups.get(i).getName();
				}
				lobjlist.setItems(srr);
				lobjlist.setSelectedIndex(dexo);
			}
			@Override
			public void canceled()
			{}

		};

		bAddObjLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.input.getTextInput(pNewObjLayer, z.addnew, "Objects " + (objgroups.size() + 1),"");
				}
			});

		bEditObjLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					Gdx.input.getTextInput(pEditObjLayer, z.edit, lobjlist.getSelected().toString(),"");
				}
			});	

		bMoveObjLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = lobjlist.getSelectedIndex();
					if (dex > 0)
					{
						java.util.Collections.swap(objgroups, dex, dex - 1);
						String[] srr = new String[objgroups.size()];
						for (int i=0;i < objgroups.size();i++)
						{
							srr[i] = objgroups.get(i).getName();

						}
						lobjlist.setItems(srr);
						lobjlist.setSelectedIndex(dex - 1);
					}
				}
			});	

		bRemoveObjLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (objgroups.size() > 1)
					{
						int dex=lobjlist.getSelectedIndex();
						objgroups.remove(dex);
						selgroup = objgroups.size() - 1;
						String[] srr = new String[objgroups.size()];
						for (int i=0;i < objgroups.size();i++)
						{
							srr[i] = objgroups.get(i).getName();

						}
						lobjlist.setItems(srr);
						if (dex < 1) dex = 1;
						lobjlist.setSelectedIndex(dex - 1);
					}
				}
			});


		tObjMgmt = new Table();
		tObjMgmt.setFillParent(true);
		tObjMgmt.defaults().width(btnx).height(btny);
		ScrollPane scrollPane4 = new ScrollPane(lobjlist);
		tObjMgmt.add(new Label(z.objectgroup, skin)).padBottom(5).row();
		tObjMgmt.add(scrollPane4).height(btny*4).padBottom(5).row();
		tObjMgmt.add(bAddObjLayer).padBottom(5).row();
		tObjMgmt.add(bEditObjLayer).padBottom(5).row();
		tObjMgmt.add(bMoveObjLayer).padBottom(5).row();
		tObjMgmt.add(bRemoveObjLayer).padBottom(5).row();
		tObjMgmt.add(bBackObjLayer).padBottom(5);

	}


	public void loadNewFile(){
		/*
		 TextField fNFilename, fNCurdir, fNTsw,fNTsh,fNTw,fNTh;
		 SelectBox sbNMapFormat;
		 TextButton bNSelDir, bNNew,bNCancel,bNAnew;
		*/
		com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter filter = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();
		bNSelDir = new TextButton(z.selectfolder, skin);
		bNNew= new TextButton(z.createnewfile, skin);
		bNNewplus= new TextButton(z.usetemplate, skin);
		
		bNCancel = new TextButton(z.cancel, skin);
		
		bNCancel.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});

		bNSelDir.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfolder,"newseldir","dir",new String[]{},tNewFile);
				}
			});
		
		bNNew.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (fNTsh.getText()==null) fNTsh.setText("32");
					if (fNTsw.getText()==null) fNTsw.setText("32");
					if (fNTh.getText()==null) fNTh.setText("20");
					if (fNTw.getText()==null) fNTw.setText("20");
					prefs.putString("Tsw",fNTsw.getText());
					prefs.putString("Tsh",fNTsh.getText());
					prefs.putString("Tw",fNTw.getText());
					prefs.putString("Th",fNTh.getText());
					prefs.flush();
					newtmxfile(true);
					backToMap();
					cue("newmap");
				}
			});
			
		bNNewplus.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					gotoStage(tTemplate);
					refreshTemplate();
				}
			});
			
		bNew.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					fNCurdir.setText(prefs.getString("lastpath",extRoot));
					fNTsw.setText(prefs.getString("Tsw","20"));
					fNTsh.setText(prefs.getString("Tsh","20"));
					fNTw.setText(prefs.getString("Tw","20"));
					fNTh.setText(prefs.getString("Th","20"));
					gotoStage(tNewFile);
					cue("new");
				}
			});
			
		fNFilename= new TextField("Map01.tmx", skin); 
		fNCurdir= new TextField(prefs.getString("curdir",extRoot), skin); 
		fNCurdir.setDisabled(true);
		fNTsw = new TextField(prefs.getString("Tsw","20"),skin);
		fNTsw.setTextFieldFilter(filter);
		fNTsh = new TextField(prefs.getString("Tsh","20"),skin);
		fNTsh.setTextFieldFilter(filter);
		fNTw = new TextField(prefs.getString("Tw","20"),skin);
		fNTw.setTextFieldFilter(filter);
		fNTh = new TextField(prefs.getString("Th","20"),skin);
		fNTh.setTextFieldFilter(filter);
		sbNMapFormat= new SelectBox(skin);
		sbNMapFormat.setItems(new String[]{"csv","base64","base64-zlib","base64-gzip","xml"});
		sbNMapRenderOrder= new SelectBox(skin);
		sbNMapRenderOrder.setItems(new String[]{"right-down","left-down","right-up","left-up"});
		sbNMapOrientation= new SelectBox(skin);
		sbNMapOrientation.setItems(new String[]{"orthogonal","isometric"});
		
		tNewFile=new Table();
		tNewFile.defaults().width(btnx).height(btny);
		tNewFile.setFillParent(true);
		tNewFile.add(new Label(z.filename,skin)).width(btnx/2-2);
		tNewFile.add(fNFilename).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.directory,skin)).colspan(2).row();
		tNewFile.add(fNCurdir).padBottom(1).colspan(2).row();
		tNewFile.add(bNSelDir).padBottom(1).colspan(2).row();
		tNewFile.add(new Label(z.tilewidth,skin)).width(btnx/2-2);
		tNewFile.add(fNTsw).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.tileheight,skin)).width(btnx/2-2);
		tNewFile.add(fNTsh).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.mapwidth,skin)).width(btnx/2-2);
		tNewFile.add(fNTw).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.mapheight,skin)).width(btnx/2-2);
		tNewFile.add(fNTh).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.mapformat,skin)).width(btnx/2-2);
		tNewFile.add(sbNMapFormat).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.renderorder,skin)).width(btnx/2-2);
		tNewFile.add(sbNMapRenderOrder).padBottom(1).width(btnx/2).row();
		tNewFile.add(new Label(z.orientation,skin)).width(btnx/2-2);
		tNewFile.add(sbNMapOrientation).padBottom(2).width(btnx/2).row();
		
		tNewFile.add(bNNew).padBottom(1).colspan(2).row();
		tNewFile.add(bNNewplus).padBottom(1).colspan(2).row();
		
		tNewFile.add(bNCancel).padBottom(1).colspan(2).row();
	
	}
	
	public void loadMapProperties(){
		/*
		 TextField fFilename, fCurdir, fTsw,fTsh,fTw,fTh,fTsx;
		 SelectBox sbMapFormat;
		 CheckBox cbUseTsx;
		 TextButton bUseTsx,bApplyMP, bCancelMP;
		 */
		com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter filter = new com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter.DigitsOnlyFilter();
		
		bApplyMP= new TextButton(z.apply, skin);
		bCancelMP = new TextButton(z.cancel, skin);
		bPropertiesMap= new TextButton(z.customproperties, skin);

		bCancelMP.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					backToMap();
				}
			});
		
		bApplyMP.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					//newtmxfile(true);
					try{
					
					int nTh=Integer.parseInt(fTh.getText());
					if (nTh > Th){//expand
						for (int i=0;i<nTh-Th;i++){
							for (int k=0;k<Tw;k++){
							for(int j=0;j<layers.size();j++){
							layers.get(j).getStr().add((long)0);
							layers.get(j).getTset().add(-1);
							}
							}
						}
					}else if (nTh < Th){//shrink
						for (int i=0;i<Th-nTh;i++){
							for (int k=0;k<Tw;k++){
							for(int j=0;j<layers.size();j++){
								layers.get(j).getStr().remove(layers.get(j).getStr().size()-1);
								layers.get(j).getTset().remove(layers.get(j).getTset().size()-1);
								
							}
							}
						}
					}
					Th=nTh;
					int nTw=Integer.parseInt(fTw.getText());
					if (nTw > Tw){//expand
						for (int i=Tw*Th;i>0;i--){
							for(int j=0;j<layers.size();j++){
								if ((i)%Tw==0 ){
									for (int k=0;k<nTw-Tw;k++){
									layers.get(j).getStr().add(i,(long)0);
									layers.get(j).getTset().add(i,-1);
									}
								}
							}
						}
					}else if (nTw < Tw){//shrink
						for (int i=Tw*Th;i>0;i--){
							for(int j=0;j<layers.size();j++){
								if ((i)%Tw==0){
									for (int k=1;k<=Tw-nTw;k++){
										layers.get(j).getStr().remove(i-k);
										layers.get(j).getTset().remove(i-k);
									}
								}
							}
						}
					}
					Tw=nTw;
					Tsh=Integer.parseInt(fTsh.getText());
					Tsw=Integer.parseInt(fTsw.getText());
					mapFormat=sbMapFormat.getSelected().toString();
					renderorder=sbMapRenderOrder.getSelected().toString();
					orientation=sbMapOrientation.getSelected().toString();
					backToMap();
					
					}catch(Exception e)
					{
						msgbox(z.error);
						ErrorBung(e,"errorlog.txt");
					}
				}
			});

		bProperties.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					fFilename.setText(curfile);
					fCurdir.setText(curdir);
					if(!tsxFile.equalsIgnoreCase("")) fTsx.setText(tsxFile);
					fTw.setText(Integer.toString(Tw));
					fTh.setText(Integer.toString(Th));
					fTsw.setText(Integer.toString(Tsw));
					fTsh.setText(Integer.toString(Tsh));
					
					sbMapFormat.setSelected(mapFormat);
					sbMapRenderOrder.setSelected(renderorder);
					sbMapOrientation.setSelected(orientation);
					gotoStage(tProperties);
				}
			});

		fFilename= new TextField("", skin); 
		fFilename.setDisabled(true);
		fCurdir= new TextField("", skin); 
		fCurdir.setDisabled(true);
		fTsw = new TextField("",skin);
		fTsw.setTextFieldFilter(filter);
		fTsh = new TextField("",skin);
		fTsh.setTextFieldFilter(filter);
		fTw = new TextField("",skin);
		fTw.setTextFieldFilter(filter);
		fTh = new TextField("",skin);
		fTh.setTextFieldFilter(filter);
		
		fTsx = new TextField("",skin);
		fTsx.setDisabled(true);
		
		sbMapFormat= new SelectBox(skin);
		sbMapFormat.setItems(new String[]{"csv","base64","base64-zlib","base64-gzip","xml"});
		sbMapRenderOrder= new SelectBox(skin);
		sbMapRenderOrder.setItems(new String[]{"right-down","left-down","right-up","left-up"});
		sbMapOrientation= new SelectBox(skin);
		sbMapOrientation.setItems(new String[]{"orthogonal","isometric"});
		
		
		cbUseTsx=new CheckBox(z.usetsxfile,skin);
		
		tProperties=new Table();
		tProperties.setFillParent(true);
		tProperties.defaults().width(btnx).height(btny);
		tProperties.add(new Label(z.file,skin)).width(btnx/2-2);
		tProperties.add(fFilename).padBottom(2).width(btnx/2).row();
		tProperties.add(fCurdir).padBottom(2).colspan(2).row();
		
		tProperties.add(new Label(z.tilewidth,skin)).width(btnx/2-2);
		tProperties.add(fTsw).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.tileheight,skin)).width(btnx/2-2);
		tProperties.add(fTsh).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.mapwidth,skin)).width(btnx/2-2);
		tProperties.add(fTw).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.mapheight,skin)).width(btnx/2-2);
		tProperties.add(fTh).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.mapformat,skin)).width(btnx/2-2);
		tProperties.add(sbMapFormat).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.renderorder,skin)).width(btnx/2-2);
		tProperties.add(sbMapRenderOrder).padBottom(2).width(btnx/2).row();
		tProperties.add(new Label(z.orientation,skin)).width(btnx/2-2);
		tProperties.add(sbMapOrientation).padBottom(5).width(btnx/2).row();
		
		tProperties.add(bPropertiesMap).padBottom(2).colspan(2).row();
		tProperties.add(bApplyMP).padBottom(2).colspan(2).row();
		tProperties.add(bCancelMP).padBottom(2).colspan(2).row();

	}
	public void loadTileManagement()
	{
		/*
		 com.badlogic.gdx.scenes.scene2d.ui.List<String> lanimlist;
		 com.badlogic.gdx.Input.TextInputListener pNewAnim;
		 TextButton bAddAnimLayer, bEditAnimLayer, bRemoveAnimLayer;

		 */
		bTileSettingsMgmt.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (ltsetlist.getSelectedIndex()!=-1)
					{
					selTsetID=ltsetlist.getSelectedIndex();
					gotoStage(tTileMgmt);
					}
					
				}
			});

		pNewTerrain = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				

				tileset ts = tilesets.get(selTsetID);
				
				terrain tr= new terrain();
				tr.setName(input);
				tr.setTile(newTerrainID);
				ts.getTerrains().add(tr);
				ts.setSelTerrain(ts.getTerrains().size()-1);
				
			}
			@Override
			public void canceled()
			{}

		};
		bAddTileLayer = new TextButton(z.addnew, skin);
		bMassAddProp = new TextButton("Mass tile properties", skin);
		bTileCollision = new TextButton("Tile collision", skin);
		
		bRemoveTileLayer = new TextButton(z.remove, skin);
		bReplaceTileLayer = new TextButton(z.replace, skin);
		bMoveTileLayer = new TextButton(z.moveup, skin);
		bTileAnimations = new TextButton(z.animation, skin);
		bTerrainEditor = new TextButton(z.terraineditor, skin);
		bPropsTileLayer = new TextButton(z.customproperties, skin);
		ltilelist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		bBackTileLayer = new TextButton(z.back, skin);
		bBackTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					//gotoStage(tTsetMgmt);
					if (frompick)
					{
						onToPicker();
						lastStage=nullTable;
					}else{
						gotoStage(tTsetMgmt);
					}
				}
			});


		bAddTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("addanim");
				}
			});
			
		bTerrainEditor.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{ 
					pickTile("terraineditor");
				}
			});

		bMassAddProp.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{ 
					lPropID.setText(z.customproperties);
					fPropName.setText("");
					fPropVal.setText("");
					sender="mass";
					gotoStage(tPropEditor);
				}
			});
		
		bReplaceTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (ltilelist.getSelectedIndex() >= 0) pickTile("replaceanim");
				}
			});

		bTileCollision.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("collision");
				}
			});

		bMoveTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = ltilelist.getSelectedIndex();
					if (dex > 0)
					{
						
							
						java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
								
						java.util.Collections.swap(tiles, dex, dex - 1);
						String[] srr = new String[tiles.size()];
						for (int i=0;i < tiles.size();i++)
						{
							srr[i] = Integer.toString(tiles.get(i).getTileID());
						}
						
						ltilelist.setItems(srr);
						
						ltilelist.setSelectedIndex(dex - 1);
					}
				}
			});	

		bRemoveTileLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (tilesets.size()>0){
					
					java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
							
					if (tiles.size() > 0)
					{
						int dex=ltilelist.getSelectedIndex();
						tiles.remove(dex);

						String[] srr = new String[tiles.size()];
						for (int i=0;i < tiles.size();i++)
						{
							srr[i] = Integer.toString(tiles.get(i).getTileID());
						}

						ltilelist.setItems(srr);

						if (dex > 0)
						{
							if (tiles.size() > 0) ltilelist.setSelectedIndex(dex - 1);
						}
						else
						{
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

	public void pickTile(String sender)
	{
		Gdx.input.setInputProcessor(gd);
		kartu = "pickanim";
		tilePicker = sender;	
	}
	
	public void loadTilesetProperties(){
		/*
		 TextButton bTsPropOK,bTsPropCancel,bTsPropSaveAsTsx;
		 TextField fTsPropName, fTsPropSource, fTsPropTrans;
		 TextField fTsPropSpacing,fTsPropMargin,fTsPropTsxFile;
		 TextField fTsPropTsw, fTsPropTsh, fTsPropTc, fTsPropCols;
		 CheckBox cbTsPropUseTsx;
		*/
		bTsPropOK=new TextButton(z.apply,skin);
		bTsPropCustomProp=new TextButton(z.customproperties,skin);
		bTsPropCancel=new TextButton(z.cancel,skin);
		
		bTsPropChangeSource=new TextButton(z.changesource,skin);
		cbTsPropUseTsx=new CheckBox(z.usetsxfile,skin);
		fTsPropName=new TextField("",skin);
		fTsPropSource=new TextField("",skin);
		fTsPropSource.setDisabled(true);
		fTsPropTrans=new TextField("",skin);
		fTsPropTrans.setTextFieldFilter(tffcolor);
		fTsPropSpacing=new TextField("",skin);
		fTsPropSpacing.setTextFieldFilter(tffint);
		fTsPropMargin=new TextField("",skin);
		fTsPropMargin.setTextFieldFilter(tffint);
		fTsPropTsxFile=new TextField("",skin);
		fTsPropTsxFile.setDisabled(true);
		fTsPropTsw=new TextField("",skin);
		fTsPropTsw.setTextFieldFilter(tffint);
		fTsPropTsh=new TextField("",skin);
		fTsPropTsh.setTextFieldFilter(tffint);
		fTsPropTc=new TextField("",skin);
		fTsPropTc.setTextFieldFilter(tffint);
		fTsPropCols=new TextField("",skin);
		fTsPropCols.setTextFieldFilter(tffint);
		Table tp= new Table();
		tp.defaults().width(btnx).height(btny);
		tTsProp=new Table();
		tTsProp.setFillParent(true);
		
		bTsPropCancel.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (frompick)
					{
						onToPicker();
						lastStage=nullTable;
					}else{
						gotoStage(tTsetMgmt);
					}
				}
			});
		
		bTsPropOK.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					tileset t=tilesets.get(selTsetID);
					if (fTsPropName.getText()!="") t.setName(fTsPropName.getText());
					if (fTsPropSource.getText()!="") t.setSource(fTsPropSource.getText());
					if (fTsPropTsxFile.getText()!="") t.setTsxfile(fTsPropTsxFile.getText());
					t.setUsetsx(cbTsPropUseTsx.isChecked());
					
					if (fTsPropCols.getText()!="0" && fTsPropCols.getText()!="") 
					{
						t.setWidth(Integer.parseInt(fTsPropCols.getText()));
						t.setColumns(Integer.parseInt(fTsPropCols.getText()));

					}
					if (fTsPropTc.getText()!="0" && fTsPropTc.getText()!="") 
					{
						t.setTilecount(Integer.parseInt(fTsPropTc.getText()));
						t.setHeight(Integer.parseInt(fTsPropTc.getText())/t.getColumns());
					}
					if (fTsPropTsw.getText()!="0" && fTsPropTsw.getText()!="") 
					{
						int newWidth = Integer.parseInt(fTsPropTsw.getText());
						if (t.getTilewidth()!=newWidth)
						{
							t.setTilewidth(newWidth);
							t.setWidth((int) (t.getOriginalwidth() / newWidth));
							t.setColumns(t.getWidth());
							t.setTilecount((int) t.getOriginalheight() / t.getHeight() * t.getWidth());
						}
					}
						
					if (fTsPropTsh.getText()!="0" && fTsPropTsh.getText()!="") 
					{
						int newHeight = Integer.parseInt(fTsPropTsh.getText());
						if (t.getTileheight()!=newHeight)
						{
							t.setTileheight(newHeight);
							t.setHeight((int) (t.getOriginalheight() / newHeight));
							t.setTilecount((int) t.getHeight() * t.getWidth());
						}
						t.setTileheight(Integer.parseInt(fTsPropTsh.getText()));
					}
							
							
					
					if (fTsPropTrans.getText()!="") 
					{
						t.setTrans(fTsPropTrans.getText());
					}
					else
					{
						t.setTrans(null);
					}
					if (fTsPropMargin.getText()!="") 
					{
						t.setMargin(Integer.parseInt(fTsPropMargin.getText()));
					}
					else
					{
						t.setMargin(0);
					}

					if (fTsPropSpacing.getText()!="") 
					{
						t.setSpacing(Integer.parseInt(fTsPropSpacing.getText()));
					}
					else
					{
						t.setSpacing(0);
					}
					try{
					File f = new File(curdir + "/" + tilesets.get(ltsetlist.getSelectedIndex()).getSource());
					if (f.exists()){

						tilesets.get(ltsetlist.getSelectedIndex()).setTexture( new Texture(Gdx.files.absolute(curdir + "/" + tilesets.get(ltsetlist.getSelectedIndex()).getSource())));
						if (tilesets.get(ltsetlist.getSelectedIndex()).getTrans() !=null){
							tilesets.get(ltsetlist.getSelectedIndex()).setTexture(chromaKey(tilesets.get(ltsetlist.getSelectedIndex()).getTexture(),tilesets.get(ltsetlist.getSelectedIndex()).getTrans()));
						}
					}
					}catch(Exception e){}

					if(!fTsPropSource.getText().equalsIgnoreCase(tilesets.get(selTsetID).getSource()))
					{
						try
						{


							t = tilesets.get(selTsetID);
							t.setSource(fTsPropSource.getText());
							File f= new File(curdir+"/"+fTsPropSource.getText());
							File cek = new File(curdir+"/"+t.getSource());

							if (!cek.exists()){
								decoder.copyFile(f,cek);
							}
							SimpleImageInfo s =new SimpleImageInfo(cek);
							t.setColumns(s.getWidth()/Tsw);
							t.setTilecount((s.getHeight()/Tsw)*(s.getWidth()/Tsh));
							t.setTilewidth(Tsw);
							t.setTileheight(Tsh);
							t.setWidth(s.getWidth()/Tsw);
							t.setHeight(s.getHeight()/Tsh);
							t.setOriginalwidth(s.getWidth());
							t.setOriginalheight(s.getHeight());
							t.setTexture( new Texture(Gdx.files.absolute(curdir + "/" + t.getSource())));


						}
						catch (IOException e)
						{}
					}
					////////////////////
					int dexo=ltsetlist.getSelectedIndex();
					/*
					templastID=1;
					for (int i=0;i<tilesets.size();i++){
						tilesets.get(i).setFirstgid(templastID);
						templastID+=tilesets.get(i).getTilecount();
					}
					*/
					int saiz=tilesets.size();
					String[] srr = new String[saiz];
					for (int i=0;i < saiz;i++)
					{
						srr[i] = tilesets.get(i).getName();
					}
					ltsetlist.setItems(srr);
					ltsetlist.setSelectedIndex(dexo);
					if (frompick)
					{
						onToPicker();
						lastStage=nullTable;
					}else{
						gotoStage(tTsetMgmt);
					}
				}

				
			});
		
		
		bTsPropChangeSource.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					FileDialog(z.selectfile,"replacetset","file",new String[]{".png",".jpg",".jpeg",".bmp",".gif"},tTsProp);
					
				}
			});
		
		tp.add(new Label(z.tileset+": "+z.properties,skin)).colspan(2).padBottom(5).row();
		tp.add(new Label(z.name,skin)).width(btnx/2);
		tp.add(fTsPropName).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.source,skin)).width(btnx/2);
		tp.add(fTsPropSource).width(btnx/2).padBottom(2).row();
		tp.add(bTsPropChangeSource).colspan(2).padBottom(2).row();
		tp.add(new Label(z.tsxfile,skin)).width(btnx/2);
		tp.add(fTsPropTsxFile).width(btnx/2).padBottom(2).row();
		tp.add().width(btnx/2);
		tp.add(cbTsPropUseTsx).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.keycolor,skin)).width(btnx/2);
		tp.add(fTsPropTrans).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.tilewidth,skin)).width(btnx/2);
		tp.add(fTsPropTsw).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.tileheight,skin)).width(btnx/2);
		tp.add(fTsPropTsh).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.tilecolumn,skin)).width(btnx/2);
		tp.add(fTsPropCols).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.tilecount,skin)).width(btnx/2);
		tp.add(fTsPropTc).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.spacing,skin)).width(btnx/2);
		tp.add(fTsPropSpacing).width(btnx/2).padBottom(2).row();
		tp.add(new Label(z.margin,skin)).width(btnx/2);
		tp.add(fTsPropMargin).width(btnx/2).padBottom(2).row();
		tp.add(bTsPropCustomProp).colspan(2).padBottom(2).row();
		tp.add(bTsPropOK).colspan(2).padBottom(2).row();
		tp.add(bTsPropCancel).colspan(2).padBottom(2).row();
		
		ScrollPane scrollPanen = new ScrollPane(tp);
		tTsProp.add(scrollPanen).top();
		
		
		
		
	}
	public void loadFrameManagement()
	{
		/*
		 com.badlogic.gdx.scenes.scene2d.ui.List<String> lanimlist;
		 com.badlogic.gdx.Input.TextInputListener pNewAnim;
		 TextButton bAddAnimLayer, bEditAnimLayer, bRemoveAnimLayer;

		 */
		bTileAnimations.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("addanim");
					anime=true;
				}
			});

		pEditFrame = new com.badlogic.gdx.Input.TextInputListener(){

			@Override
			public void input(String input)
			{
				if (input == "")
				{return;}
				int dexo=lframelist.getSelectedIndex();
				
				java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
						
				tiles.get(selTileID).getAnimation().get(dexo).setDuration(Integer.parseInt(input));

				String srr[] = new String[tiles.get(selTileID).getAnimation().size()];
				for (int i=0;i < tiles.get(selTileID).getAnimation().size();i++)
				{
					String aaa= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
					String bbb= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
					srr[i] = aaa + " (" + bbb + "ms)";
				}
				lframelist.setItems(srr);
				
				lframelist.setSelectedIndex(dexo);
			}
			@Override
			public void canceled()
			{}

		};
		//iFrameView=new Image(skin);
		lFrameID = new Label(z.frame, skin);
		bAddFrameLayer = new TextButton(z.addnew, skin);
		bRemoveFrameLayer = new TextButton(z.remove, skin);
		bReplaceFrameLayer = new TextButton(z.replace, skin);
		bDuration = new TextButton(z.set, skin);
		bMoveFrameLayer = new TextButton(z.moveup, skin);
		fDurationframe=new TextField(prefs.getString("duration","500"),skin);
		fDurationframe.setTextFieldFilter(tffint);
		bEditFrameLayer = new TextButton(z.edit+" "+z.duration, skin);
		lframelist = new com.badlogic.gdx.scenes.scene2d.ui.List<String>(skin);
		bBackFrameLayer = new TextButton(z.back, skin);
		bBackFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					selTileID = -1;
					anime=false;
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

		bDuration.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (fDurationframe.getText().toString()==""){
						fDurationframe.setText("500");
					}
					if (Integer.parseInt(fDurationframe.getText())==0){
						fDurationframe.setText("500");
					}
					prefs.putString("duration",fDurationframe.getText());prefs.flush();
				}
			});
		
			
		bAddFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					pickTile("addframe");
				}
			});

		bReplaceFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					if (lframelist.getSelectedIndex() >= 0) pickTile("replaceframe");
				}
			});

		bEditFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
				
					java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
							
					if (tiles.get(selTileID).getAnimation().size() > 0)
					{
						Gdx.input.getTextInput(pEditFrame, "Edit Duration", Integer.toString(tiles.get(selTileID).getAnimation().get(lframelist.getSelectedIndex()).getDuration()),"");
					}
					
				}
			});	

		bMoveFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					int dex = lframelist.getSelectedIndex();
					if (dex > 0)
					{
					
						java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
								
						java.util.Collections.swap(tiles.get(selTileID).getAnimation(), dex, dex - 1);
						
						int dexa=tiles.get(selTileID).getAnimation().size();
						if (dexa > 0)
						{
							String[] srr = new String[dexa];

							for (int i=0;i < dexa;i++)
							{
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

		bRemoveFrameLayer.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor)
				{
					deletinganim=true;
					java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
							
					if (tiles.get(selTileID).getAnimation().size() > 0)
					{
						int dex=lframelist.getSelectedIndex();
						tiles.get(selTileID).getAnimation().remove(dex);
						tiles.get(selTileID).setActiveFrameID(0);
						tiles.get(selTileID).setTimer(0);

						String[] srr = new String[tiles.get(selTileID).getAnimation().size()];
						for (int i=0;i < tiles.get(selTileID).getAnimation().size();i++)
						{
							String aaa = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
							String bbb = Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
							srr[i] = aaa + " (" + bbb + "ms)";
						}

						lframelist.setItems(srr);

						if (dex > 0)
						{
							if (tiles.get(selTileID).getAnimation().size() > 0) lframelist.setSelectedIndex(dex - 1);
						}
						else
						{
							if (tiles.get(selTileID).getAnimation().size() > 0) lframelist.setSelectedIndex(0);
						}
					}
					deletinganim=false;

				}
			});


		tFrameMgmt = new Table();
		tFrameMgmt.defaults().width(btnx).height(btny);
		tFrameMgmt.setFillParent(true);
		ScrollPane scrollPane3 = new ScrollPane(lframelist);
		tFrameMgmt.add(lFrameID).padBottom(5).colspan(2).row();
		tFrameMgmt.add(scrollPane3).height(btny*4).padBottom(5).colspan(2).row();
		tFrameMgmt.add(new Label(z.default_+" "+z.duration+"(ms)",skin)).padBottom(5).colspan(2).row();
		tFrameMgmt.add(fDurationframe).width(btnx/2-2).padBottom(5);
		tFrameMgmt.add(bDuration).width(btnx/2).padLeft(2).padBottom(5).row();
		tFrameMgmt.add(bAddFrameLayer).padBottom(5).colspan(2).row();
		tFrameMgmt.add(bEditFrameLayer).padBottom(5).colspan(2).row();
		tFrameMgmt.add(bMoveFrameLayer).padBottom(5).colspan(2).row();
		tFrameMgmt.add(bReplaceFrameLayer).padBottom(5).colspan(2).row();
		tFrameMgmt.add(bRemoveFrameLayer).padBottom(5).colspan(2).row();
		tFrameMgmt.add(bBackFrameLayer).padBottom(5).colspan(2);

	}
	
	public void FileDialog(String prompt,final String dialog, final String fileordir,String[] filter, final Table exitpoint)
	{
		FileChooser fc = new FileChooser(prompt, skin, "file",filter) {
			@Override
			protected void result(Object object)
			{
				if (object.equals("OK"))
				{
					try
					{
						FileHandle file;
						exitDialog(exitpoint);
						if (fileordir.equalsIgnoreCase("file")){
						file = getFile();
							
							if (file.file().getAbsolutePath().length()>=extRoot.length()){
								openedfile = file.file().getAbsolutePath();
								lastpath = file.file().getParent();
								prefs.putString("lastpath", lastpath);
								prefs.flush();
								tujuanDialog(dialog,file);
							}
							
						}else{
							
						file = getDirectory();
						
							if (file.file().getAbsolutePath().length()>=extRoot.length()){
								lastpath = file.file().getAbsolutePath();
								prefs.putString("lastpath", lastpath);
								prefs.flush();
								tujuanDialog(dialog,file);
							}
							
						}
						
						
						
					}
					catch (Exception e)
					{
						exitDialog(exitpoint);
					}
				}
				else
				{
					exitDialog(exitpoint);
				}
			}
		};
		fc.setDirectory(Gdx.files.absolute(lastpath));
		tOpen.clear();
		tOpen.add(fc).width(ssx).height(ssy*.8f);
		gotoStage(tOpen);
	}
	
	public void exitDialog(Table exitpoint){
		if (exitpoint==null){
			backToMap();
		}else if (exitpoint==nullTable){
			onToPicker();
		}else{
			gotoStage(exitpoint);
		}
	}
	
	FileHandle thefile;
	
	public void tujuanDialog(String dialog,FileHandle file){
		switch (dialog){
			case "propfile":
				bPropValfile.setText(file.file().getName());
				break;
			case "proppng":
				byte[] fileContent = file.readBytes();
				String asu = android.util.Base64.encodeToString(fileContent,0);
				fPropVal.setText(asu);
				break;
			case "open":
				curdir=file.file().getParent();
				
				
				loadtmx(openedfile);
				break;
			case "background":
				background= new Texture(Gdx.files.absolute(openedfile));
				break;
			case "saveas":
				saveasdir=file.file().getAbsolutePath();
				Gdx.input.getTextInput(pSaveAs, "Set new filename", "new.tmx","");
				
				break;
			
			case "selfolder":
				errors="";
				String dir=file.file().getAbsolutePath();
				FileHandle [] filehandles;
				filehandles=Gdx.files.absolute(dir).list();
				for (int i=0;i<filehandles.length;i++)
				{
					Log.w("momos",filehandles[i].path());
					switch(filehandles[i].extension().toLowerCase())
					{
						case "tsx":
							loadtsx(filehandles[i].path());
							break;
						case "png":case "jpg":case "jpeg":case "gif":case "bmp":
							addImageTset(filehandles[i].file());
							break;
						
					}
				}
				int saiz=tilesets.size();
				String[] srr = new String[saiz];
				for (int i=0;i < saiz;i++)
				{
					srr[i] = tilesets.get(i).getName();
				}
				ltsetlist.setItems(srr);
				ltsetlist.setSelectedIndex(saiz-1);
				status(errors,5);
				break;
			case "seltsx":
				errors="";
				loadingfile=true;
				loadtsx(openedfile);
				
				saiz=tilesets.size();
				srr = new String[saiz];
				for (int i=0;i < saiz;i++)
				{
					srr[i] = tilesets.get(i).getName();
				}
				ltsetlist.setItems(srr);
				ltsetlist.setSelectedIndex(saiz-1);
				CacheAllTset();
					status(errors,5);
				loadingfile=false;
				break;
			case "newseldir":
				String path = file.file().getAbsolutePath();
				
				if (path.length()>=extRoot.length()){
					prefs.putString("lastpath",path).flush();
					fNCurdir.setText(path);
				}
				
				break;
			case "quickaddtset":
				thefile=file;
				fImportWidth.setText(Tsw+"");
				fImportHeight.setText(Tsh+"");
				cImportEmbed.setChecked(false);
				gotoStage(tImport);
				
				return;
				
			case "addtset":
				loadingfile=true;
				errors=" ";
				File f=file.file();
				addImageTset(f);

				saiz=tilesets.size();
				srr = new String[saiz];
				for (int i=0;i < saiz;i++)
				{
					srr[i] = tilesets.get(i).getName();
				}
				ltsetlist.setItems(srr);
				ltsetlist.setSelectedIndex(saiz - 1);
				CacheAllTset();
				if (errors!=" ") msgbox(errors);

				loadingfile=false;
				
				break;
			case "replacetset":
					f=file.file();
					fTsPropSource.setText(f.getName());
				CacheAllTset();
				
				
				break;
		}
	}
	
	public void addImageTset(File f)
	{
		try
		{
		SimpleImageInfo s =new SimpleImageInfo(f);
		tileset t = new tileset();
		String nn=f.getName();
		if (f.getName().indexOf(".")>0)
		{
			nn=f.getName().substring(0,f.getName().lastIndexOf("."));
		}
		t.setName(nn);

		String tc[] =curdir.split("/");
		String ts[] =f.getAbsolutePath().split("/");

		boolean git=false;
		String pre="",post="";
		for (int i=0;i<tc.length;i++)
		{
			if (git)
			{
				pre+="../";
				continue;
			}
			if (ts.length<=i)
			{
				git=true;
				pre+="../";
				continue;
			}
			if (!ts[i].equalsIgnoreCase(tc[i]))
			{
				git=true;
				pre+="../";
			}
		}

		git=false;
		for (int i=0;i<ts.length;i++)
		{
			if (git)
			{
				post+=ts[i];
				if (i<ts.length-1) post+="/";
				continue;
			}
			if (tc.length<=i)
			{
				git=true;
				post+=ts[i];
				if (1<ts.length-1) post+="/";
				continue;
			}
			if (!tc[i].equalsIgnoreCase(ts[i]))
			{
				git=true;
				post+=ts[i];
				if (i<ts.length-1) post+="/";
			}
		}
		String cocos =pre+post;
		if (cocos.endsWith("/")) cocos=cocos.substring(0,cocos.length()-1);
			

		t.setSource(cocos);

		int Tswa=Integer.parseInt(fImportWidth.getText());
		int Tsha=Integer.parseInt(fImportHeight.getText());
		t.setOriginalwidth(s.getWidth());
		t.setOriginalheight(s.getHeight());
		t.setColumns(s.getWidth()/Tswa);
		t.setTilecount((s.getHeight()/Tswa)*(s.getWidth()/Tsha));
		t.setWidth(s.getWidth()/Tswa);
		t.setHeight(s.getHeight()/Tsha);
		t.setTilewidth(Tswa);
		t.setTileheight(Tsha);
		t.setTrans("");
		t.setTexture( new Texture(Gdx.files.absolute(curdir + "/" + t.getSource())));
		t.setPixmap(pixmapfromtexture(t.getTexture(),t.getTrans()));
			
		t.setFirstgid(requestGid());
		
		if (cImportEmbed.isChecked())
		{
			byte[] fileContent = thefile.readBytes();
			String asu = android.util.Base64.encodeToString(fileContent,0);
			property po= new property("embedded_png",asu);
			t.getProperties().add(po);
			cImportEmbed.setChecked(false);
		}
		curspr=t.getFirstgid();
		tilesets.add(t);
		recenterpick();
		}catch(Exception e)
		{
			errors +="\nError: "+f.getName();
		}
	}
	public void backToMap()
	{
		kartu = "world";
		Gdx.input.setInputProcessor(gd);
		stage.clear();
		face.showbanner(false);
	}
	public void onToPicker()
	{
		kartu = "tile";
		Gdx.input.setInputProcessor(gd);
		stage.clear();
	}

	public void gotoStage(Table table)
	{
			stage.clear();
			stage.addActor(table);
			Gdx.input.setInputProcessor(stage);
			kartu = "stage";
			lastStage = table;
			
			face.showbanner(false);
			if (!bypassads){
			if (lastStage==tMenu) face.showbanner(true);
			if (lastStage==tMap) face.showbanner(true);
			}
	}

//////////////////////////////////////////////////////
//            XML PROCESSOR
//////////////////////////////////////////////////////
	public void saveMap(String allpath)
	{
		File file = new File(allpath);
		recents.addrecent(allpath);
		saveRecents();
		prefs.putString("lof",file.getAbsolutePath());
		prefs.flush();
		///
		autotiles at=new autotiles(autotiles);
		Json json = new Json();
		writeThisAbs(curdir+"/auto.json",json.prettyPrint(at));
		////

		try
		{
			if (file.exists()) file.delete();
			FileOutputStream fos = new FileOutputStream(file);


			XmlSerializer srz = Xml.newSerializer();
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
			
			if(properties.size()>0){
				srz.startTag(null, "properties");
				for (int m=0;m<properties.size();m++){
					srz.startTag(null, "property");
					if (properties.get(m).getName()!=null) srz.attribute("", "name", properties.get(m).getName());
					//if (properties.get(m).getType()!=null) srz.attribute("", "type", properties.get(m).getType().toLowerCase());
					if (properties.get(m).getValue()!=null) srz.attribute("", "value", properties.get(m).getValue());
					String txx=properties.get(m).getValue();
					if (txx!=null) 
					{
						if (txx.contains("\n"))
						{
							srz.text("\n"+txx+"\n");
						}else
						{
							srz.attribute("", "value", txx);
						}
					}
					srz.endTag(null, "property");
				}
				srz.endTag(null, "properties");
			}

		
				if (tilesets.size()>0){
					for (int i=0;i<tilesets.size();i++){
						
						tileset t = tilesets.get(i);
						if (t.isUsetsx())
						{
							srz.startTag(null, "tileset");
							srz.attribute("", "source", t.getTsxfile());
							srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
							srz.endTag(null,"tileset");
							continue;
						}
						srz.startTag(null, "tileset");
						srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
						srz.attribute("", "name", t.getName());
						if (t.getMargin()!=0) srz.attribute("", "margin", Integer.toString(t.getMargin()));
						if (t.getSpacing()!=0) srz.attribute("", "spacing", Integer.toString(t.getSpacing()));
						srz.attribute("", "columns", Integer.toString(t.getColumns()));
						srz.attribute("", "tilecount", Integer.toString(t.getTilecount()));
						srz.attribute("", "tileheight", Integer.toString(t.getTileheight()));
						srz.attribute("", "tilewidth", Integer.toString(t.getTilewidth()));
					 
						if(t.getProperties().size()>0){
							srz.startTag(null, "properties");
							for (int m=0;m<t.getProperties().size();m++){
								srz.startTag(null, "property");
								if (t.getProperties().get(m).getName()!=null) srz.attribute("", "name", t.getProperties().get(m).getName());
								//if (t.getProperties().get(m).getType()!=null) srz.attribute("", "type", t.getProperties().get(m).getType().toLowerCase());
								if (t.getProperties().get(m).getValue()!=null) 
								{
									if (t.getProperties().get(m).getName().toString().equalsIgnoreCase("embedded_tileset"))
									{
										java.util.List<String> values = splitEqually(t.getProperties().get(m).getValue(),100);
										for (int g=0;g<values.size();g++)
										{
											srz.text(values.get(g)+"\n");
										}
										
									}else{
										String txx=t.getProperties().get(m).getValue();
										if (txx!=null) 
										{
											if (txx.contains("\n"))
											{
												srz.text("\n"+txx+"\n");
											}else
											{
												srz.attribute("", "value", txx);
											}
										}
									}
								}
									srz.endTag(null, "property");
							}
							srz.endTag(null, "properties");
						}
						if (t.getSource()!=null){
						 srz.startTag(null, "image");
						 srz.attribute("", "source", t.getSource())
						 .attribute("", "width", Integer.toString(t.getOriginalwidth()))
						 .attribute("", "height", Integer.toString(t.getOriginalheight()));
						if (t.getTrans()!=null && t.getTrans()!="") srz.attribute("", "trans", t.getTrans());
						srz.endTag(null, "image");
						}
						if (t.getTerrains().size()>0)
						{
							srz.startTag(null,"terraintypes");
							for (int ee=0;ee<t.getTerrains().size();ee++)
							{
								srz.startTag(null,"terrain");
								srz.attribute("", "name", t.getTerrains().get(ee).getName());
								srz.attribute("","tile",Integer.toString(t.getTerrains().get(ee).getTile()));
								srz.endTag(null,"terrain");
							}
							srz.endTag(null,"terraintypes");
						}
						 if (t.getTiles().size()>0){
							for (int u=0;u<t.getTiles().size();u++){
							tile oj= t.getTiles().get(u);
							
						 
						 srz.startTag(null,"tile");
						srz.attribute("", "id", Integer.toString(t.getTiles().get(u).getTileID()));
								if (!t.getTiles().get(u).getTerrainString().equalsIgnoreCase("-1,-1,-1,-1"))
								{
									srz.attribute("","terrain",t.getTiles().get(u).getTerrainString());
									
								}
								
						 
								  if(oj.getAnimation().size()>0){
								  srz.startTag(null, "animation");
								  for (int m=0;m<oj.getAnimation().size();m++){
								  srz.startTag(null, "frame");
								  srz.attribute("", "tileid", Integer.toString(oj.getAnimation().get(m).getTileID()));
								  srz.attribute("", "duration", Integer.toString(oj.getAnimation().get(m).getDuration()));
								  srz.endTag(null, "frame");
								  }
								  srz.endTag(null, "animation");
								  }
						if(oj.getProperties().size()>0){
							srz.startTag(null, "properties");
							for (int m=0;m<oj.getProperties().size();m++){
								srz.startTag(null, "property");
								if (oj.getProperties().get(m).getName()!=null) srz.attribute("", "name", oj.getProperties().get(m).getName());
								if (oj.getProperties().get(m).getType()!=null) 
								{
									if (oj.getProperties().get(m).getType()!="string"){
										srz.attribute("", "type", oj.getProperties().get(m).getType().toLowerCase());
										
									}
								}
								
								String txx=oj.getProperties().get(m).getValue();
								if (txx!=null) 
								{
									if (txx.contains("\n"))
									{
										srz.text("\n"+txx+"\n");
									}else
									{
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
			
			for (int j=0;j<layers.size();j++){
				layer l = layers.get(j);
				srz.startTag(null, "layer");
				srz.attribute("", "name",l.getName());
				if (l.getOpacity()!=0) srz.attribute("", "opacity", Float.toString(l.getOpacity()));
				srz.attribute("", "width", Integer.toString(Tw));
				srz.attribute("", "height", Integer.toString(Th));

				srz.startTag(null, "data");
				switch (mapFormat){
					case "csv":
						srz.attribute("", "encoding", "csv");
						srz.text("\n"+savecsv(j)+"\n");
						break;
					case "base64":
						srz.attribute("", "encoding", "base64");
						srz.text("\n   "+decoder.savebase64(j,layers)+"\n  ");
						break;
					case "base64-zlib":
						srz.attribute("", "encoding", "base64");
						srz.attribute("", "compression", "zlib");
						srz.text("\n   "+decoder.savebase64zlib(j,layers)+"\n  ");
						break;
					case "base64-gzip":
						srz.attribute("", "encoding", "base64");
						srz.attribute("", "compression", "gzip");
						srz.text("\n   "+decoder.savebase64gzip(j,layers)+"\n  ");
						break;
					case "xml":
						for (int n=0;n<l.getStr().size();n++){
							srz.startTag(null, "tile");
							srz.attribute("", "gid", Long.toString(l.getStr().get(n)));
							srz.endTag(null, "tile");
						}
						break;
				}
				srz.endTag(null, "data");
				srz.endTag(null, "layer");
			}
			
			
			 for (int k=0;k<objgroups.size();k++){
				 objgroup og=objgroups.get(k);
				 srz.startTag(null, "objectgroup");
				 srz.attribute("", "name", og.getName());
				 
				 if (og.getObjects().size()>0){
					 for (int l=0;l<og.getObjects().size();l++){
						 obj oj=og.getObjects().get(l);
						 srz.startTag(null, "object");
						 if (oj.getName() != null) srz.attribute("", "name", oj.getName());
						 if (oj.getType() != null) srz.attribute("", "type", oj.getType());
						 srz.attribute("", "id", Integer.toString(oj.getId()));
						 String xx="",yy="",ww="",hh="",rorot="";
						 Float tmpf;
						 tmpf=oj.getX();
						 if(tmpf % 1 == 0){ xx=Integer.toString(tmpf.intValue()); }else{xx=Float.toString(tmpf);}
						 
						 tmpf=oj.getY();
						 if(tmpf % 1 == 0){ yy=Integer.toString(tmpf.intValue()); }else{yy=Float.toString(tmpf);}
						 
						 tmpf=oj.getW();
						 if (tmpf!=0){
						 	if(tmpf % 1 == 0){ ww=Integer.toString(tmpf.intValue()); }else{ww=Float.toString(tmpf);}
						 }
						 
						 tmpf=oj.getH();
						 if (tmpf!=0){
							 if(tmpf % 1 == 0) { hh=Integer.toString(tmpf.intValue()); }else{hh=Float.toString(tmpf);}
						 }
						 
						 tmpf=oj.getRotation();
						 if (tmpf!=0){
							 if(tmpf % 1 == 0){ rorot=Integer.toString(tmpf.intValue()); }else{rorot=Float.toString(tmpf);}
							 srz.attribute("", "rotation", rorot);
						 }
						 
						 srz.attribute("", "x", xx);
						 
						 if (oj.getGid() != 0) srz.attribute("", "gid", Integer.toString(oj.getGid()));
						 if (oj.getShape()!=null){
						 switch (oj.getShape()){
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
								 int newyy= Integer.parseInt(yy);
								 newyy+=Tsh;
								 srz.attribute("", "y",Integer.toString(newyy) );
								 srz.attribute("", "width", ww);
								 srz.attribute("", "height", hh);
								 
								 break;
							default:
									 srz.attribute("", "y", yy);
									 srz.attribute("", "width", ww);
									 srz.attribute("", "height", hh);
								 
								 
								break;
							 }
						 }else{
							 srz.attribute("", "width", ww);
							 srz.attribute("", "height", hh);
							 
						 }
						 if(oj.getProperties().size()>0){
							 srz.startTag(null, "properties");
							 for (int m=0;m<oj.getProperties().size();m++){
								 srz.startTag(null, "property");
								 if (oj.getProperties().get(m).getName()!=null) srz.attribute("", "name", oj.getProperties().get(m).getName());
								 //if (oj.getProperties().get(m).getType()!=null) srz.attribute("", "type", oj.getProperties().get(m).getType().toLowerCase());
								 
								 String txx=oj.getProperties().get(m).getValue();
								 if (txx!=null) 
								 {
									 if (txx.contains("\n"))
									 {
										 srz.text("\n"+txx+"\n");
									 }else
									 {
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

			srz.endDocument();
			srz.flush();
			fos.close();
			

		}
		catch (Exception e)
		{
			FileHandle fil = Gdx.files.absolute(extRoot + "errorloge.txt");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
			fil.writeString(exceptionAsString, false);
			
			
		}
			

	}
	
	public static java.util.List<String> splitEqually(String text, int size) {
		// Give the list the right capacity to start with. You could use an array
		// instead if you wanted.
		java.util.List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

		for (int start = 0; start < text.length(); start += size) {
			ret.add(text.substring(start, Math.min(text.length(), start + size)));
		}
		return ret;
	}
	
	public void saveTsx(int index)
	{
		File file = new File(curdir+"/"+tilesets.get(index).getTsxfile());

		try
		{
			FileOutputStream fos = new FileOutputStream(file);


			XmlSerializer srz = Xml.newSerializer();
			srz.setOutput(fos, "UTF-8");
			srz.startDocument(null, Boolean.valueOf(true));
			srz.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

						tileset t = tilesets.get(index);
						srz.startTag(null, "tileset");
						srz.attribute("", "firstgid", Integer.toString(t.getFirstgid()));
						srz.attribute("", "name", t.getName());
						if (t.getMargin()!=0) srz.attribute("", "margin", Integer.toString(t.getMargin()));
						if (t.getSpacing()!=0) srz.attribute("", "spacing", Integer.toString(t.getSpacing()));
						srz.attribute("", "columns", Integer.toString(t.getColumns()));
						srz.attribute("", "tilecount", Integer.toString(t.getTilecount()));
						srz.attribute("", "tileheight", Integer.toString(t.getTileheight()));
						srz.attribute("", "tilewidth", Integer.toString(t.getTilewidth()));

			if(t.getProperties().size()>0){
				srz.startTag(null, "properties");
				for (int m=0;m<t.getProperties().size();m++){
					srz.startTag(null, "property");
					if (t.getProperties().get(m).getName()!=null) srz.attribute("", "name", t.getProperties().get(m).getName());
					//if (t.getProperties().get(m).getType()!=null) srz.attribute("", "type", t.getProperties().get(m).getType());
					if (t.getProperties().get(m).getValue()!=null) srz.attribute("", "value", t.getProperties().get(m).getValue());
					srz.endTag(null, "property");
				}
				srz.endTag(null, "properties");
			}
						
						srz.startTag(null, "image");
						srz.attribute("", "source", t.getSource())
							.attribute("", "width", Integer.toString(t.getOriginalwidth()))
							.attribute("", "height", Integer.toString(t.getOriginalheight()));
						if (t.getTrans()!=null && t.getTrans()!="") srz.attribute("", "trans", t.getTrans());
						srz.endTag(null, "image");
						if (t.getTerrains().size()>0)
						{
							srz.startTag(null,"terraintypes");
							for (int ee=0;ee<t.getTerrains().size();ee++)
							{
								srz.startTag(null,"terrain");
								srz.attribute("", "name", t.getTerrains().get(ee).getName());
								srz.attribute("","tile",Integer.toString(t.getTerrains().get(ee).getTile()));
								srz.endTag(null,"terrain");
							}
							srz.endTag(null,"terraintypes");
						}
						if (t.getTiles().size()>0){
							for (int u=0;u<t.getTiles().size();u++){
								tile oj= t.getTiles().get(u);


								srz.startTag(null,"tile");
								srz.attribute("", "id", Integer.toString(t.getTiles().get(u).getTileID()));
								srz.attribute("","terrain",t.getTiles().get(u).getTerrainString());

								if(oj.getAnimation().size()>0){
									srz.startTag(null, "animation");
									for (int m=0;m<oj.getAnimation().size();m++){
										srz.startTag(null, "frame");
										srz.attribute("", "tileid", Integer.toString(oj.getAnimation().get(m).getTileID()));
										srz.attribute("", "duration", Integer.toString(oj.getAnimation().get(m).getDuration()));
										srz.endTag(null, "frame");
									}
									srz.endTag(null, "animation");
								}
								if(oj.getProperties().size()>0){
									srz.startTag(null, "properties");
									for (int m=0;m<oj.getProperties().size();m++){
										srz.startTag(null, "property");
										if (oj.getProperties().get(m).getName()!=null) srz.attribute("", "name", oj.getProperties().get(m).getName());
										if (oj.getProperties().get(m).getType()!=null) srz.attribute("", "type", oj.getProperties().get(m).getType());
										if (oj.getProperties().get(m).getValue()!=null) srz.attribute("", "value", oj.getProperties().get(m).getValue());
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

		}
		catch (Exception e)
		{
			FileHandle fil = Gdx.files.absolute(extRoot + "errorloge.txt");
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
			fil.writeString(exceptionAsString, false);


		}


	}

	public String savecsv(int seltile){
		java.lang.StringBuilder sb = new java.lang.StringBuilder();
		
		for (int i=0;i <= layers.get(seltile).getStr().size() - 1;i++)
		{
			int cek = (i + 1) % Tw;
			sb.append(layers.get(seltile).getStr().get(i));
			if (i != layers.get(seltile).getStr().size() - 1)
			{
				sb.append(",");
				if (cek==0){ sb.append("\n");}
			}
			
			
		}
		return sb.toString();
	}
	
	public void loadtmx(final String filepath)
	{
		loadingfile=true;
		new Thread(new Runnable() {
				@Override
				public void run() {
					// do something important here, asynchronously to the rendering thread
					// post a Runnable to the rendering thread that processes the result
					Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								
								loadmap(filepath);
								
							}
						});
				}
			}).start();
	}
	
	public void loadtmxnewplus(final String filepath)
	{
		loadingfile=true;
		new Thread(new Runnable() {
				@Override
				public void run() {
					// do something important here, asynchronously to the rendering thread
					// post a Runnable to the rendering thread that processes the result
					Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {

								loadmap(filepath);
								curdir = fNCurdir.getText();
								curdir=curdir.replace("//","/");
								curfile = fNFilename.getText();
								Tw=Integer.parseInt(fNTw.getText());
								Th=Integer.parseInt(fNTh.getText());

								undolayer.clear(); redolayer.clear();

								for(int k=0; k<layers.size();k++){
									java.util.List<Long> ls=new ArrayList<Long>();
									java.util.List<Integer> lts=new ArrayList<Integer>();

									for(long i=0;i<Tw*Th;i++){
										ls.add((long) 0);
										lts.add(-1);
									}
									layers.get(k).setStr(ls);
									layers.get(k).setTset(lts);
								}

								kartu = "stage";mode = "tile";
								curspr = 0; 
								resetcam(false);


								loadingfile=false;
								showtsetselection();
								saveMap(curdir+"/"+curfile);
								cacheTiles();
								uicam.zoom=0.5f;
								uicam.update();
								firstload=loadtime;
							}
						});
				}
			}).start();
	}
	
	int firstload=-1;
	int loadtime=10;
	public void loadmap(String filepath)
	{
		
		errors="";
		recents.addrecent(filepath);
		saveRecents();
		try{
			xmlFactoryObject = XmlPullParserFactory.newInstance();
			myParser = xmlFactoryObject.newPullParser();
			File file = new File(filepath);
			FileInputStream stream = new FileInputStream(file);
			String path = file.getParent();
			curdir = path;curfile=file.getName();
			myParser.setInput(stream, null);
			undolayer.clear();redolayer.clear();
			int event = myParser.getEventType();
			background=null;
			encoding="";tile tempTile=new tile();obj tempobj=new obj();
			String isi = "";String prName=""; String prValue="";int lastPid=0;
			selgroup = -1;seltile = -1;templastID = 1;seltset=0;String owner="";
			objgroups.clear();layers.clear();tilesets.clear();properties.clear();
			int curgroupid=-1;int curobjid=-1;curid = 0;
			while (event != XmlPullParser.END_DOCUMENT)
			{
				String name=myParser.getName();


				switch (event)
				{
					case XmlPullParser.START_TAG:
						Log.w("logger", name);
						if (name.equals("map"))
						{
							owner="map";
							if (myParser.getAttributeValue(null, "nextobjectid")!=null){
								curid=Integer.parseInt(myParser.getAttributeValue(null, "nextobjectid"));
							}
							else
							{
								curid=1;
							}
							Tw = Integer.parseInt(myParser.getAttributeValue(null, "width"));
							Th = Integer.parseInt(myParser.getAttributeValue(null, "height"));
							Tsw = Integer.parseInt(myParser.getAttributeValue(null, "tilewidth"));
							Tsh = Integer.parseInt(myParser.getAttributeValue(null, "tileheight"));
							if (myParser.getAttributeValue(null, "renderorder")!=null)
							{
								renderorder = myParser.getAttributeValue(null, "renderorder");
							}
							else
							{
								renderorder="right-down";
							}
							orientation=myParser.getAttributeValue(null, "orientation");
							
						}
						if (name.equals("layer"))
						{
							owner="layer";
							tempLayer = new layer();
							if (myParser.getAttributeValue(null, "opacity")!=null){
								tempLayer.setOpacity(Float.parseFloat(myParser.getAttributeValue(null, "opacity")));
							}
							tempLayer.setName(myParser.getAttributeValue(null, "name"));
							
						}
						if (name.equals("terrain"))
						{
							terrain tr=new terrain();
							tr.setName(myParser.getAttributeValue(null, "name"));
							tr.setTile(Integer.parseInt(myParser.getAttributeValue(null, "tile")));
							tempTset.getTerrains().add(tr);
						}
						
						if (name.equals("image"))
						{
							
							String internalpath = "rusted_warfare/assets/tilesets";
							
							tempTset.setTrans(myParser.getAttributeValue(null, "trans"));
							if (!alreadyloaded) {
							tempTset.setSource(myParser.getAttributeValue(null, "source"));

							
							String foredirint, foredirext, foredir, tempdiro, tempdiri, combo;
							foredir=curdir;//should be tsxpath to folloe tsx pathing but whatever!!
							if (foredir.substring(foredir.length()-1,foredir.length()).equalsIgnoreCase("/"))
							{
								foredir= foredir.substring(0,foredir.length()-1);
							}
							tempdiro=tempTset.getSource();
							tempdiri=tempTset.getSource();
							
							foredirint=internalpath;
							foredirext=curdir;
							
							while (tempdiro.substring(0,3).equalsIgnoreCase("../"))
							{
								tempdiro=tempdiro.substring(3,tempdiro.length());
								foredirext=foredirext.substring(0,foredirext.lastIndexOf("/"));
								//foredirint=foredirint.substring(0,foredirint.lastIndexOf("/"));
							}
							
								if (tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1)!=-1)
								{
									tempdiri=tempdiri.substring(tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1));
									tempdiri=tempdiri.replace("tilesets/","");
								}
								
							FileHandle filehand = Gdx.files.internal(foredirint+"/"+tempdiri);

							if (!filehand.exists()){
								filehand = Gdx.files.absolute(foredirext+"/"+tempdiro);
								if (!filehand.exists()){
									filehand = Gdx.files.internal("empty.jpeg");
								}
							}
							
							try{

								tempTset.setTexture(new Texture(filehand));
								tempTset.setPixmap(pixmapfromtexture(tempTset.getTexture(),tempTset.getTrans()));
								if (tempTset.getTrans() !=null){
									tempTset.setTexture(chromaKey(tempTset.getTexture(),tempTset.getTrans()));
								}
								if (myParser.getAttributeValue(null, "width")!=null){
									tempTset.setOriginalwidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
									tempTset.setOriginalheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));
								} else
								{

									SimpleImageInfo sii;
									sii=new SimpleImageInfo(filehand.read());
									tempTset.setOriginalwidth(sii.getWidth());
									tempTset.setOriginalheight(sii.getHeight());
								}
							}catch(Exception e){
								tempTset.setOriginalwidth(0);
								tempTset.setOriginalheight(0);
								errors+="Not Found: "+tempdiro+"\n"+e;
							}
							}
							if (tempTset.getColumns()==0)
							{
								tempTset.setColumns((tempTset.getOriginalwidth()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTilewidth()+tempTset.getSpacing()));
								tempTset.setWidth(tempTset.getColumns());
								tempTset.setHeight((tempTset.getOriginalheight()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTileheight()+tempTset.getSpacing()));
								tempTset.setTilecount(tempTset.getWidth()*tempTset.getHeight());
							}

							templastID += tempTset.getWidth() * tempTset.getHeight();

						}
						
						
						if (name.equals("data"))
						{
							isi="";
							switch (owner)
							{
							case "layer":
								encoding = myParser.getAttributeValue(null, "encoding");
								if (encoding ==null){
									encoding ="xml";
									mapFormat="xml";
									spr = new ArrayList<Long>();
								}
								compression = myParser.getAttributeValue(null, "compression");
								if (compression==null){
									compression="no compression";
								}
								break;
							}
						
						}
						if (name.equals("tileset"))
						{
							tempTset = new tileset();
							String source ="";
							
							source= myParser.getAttributeValue(null, "source");
							
							owner="tileset";
							alreadyloaded=false;
							
							if (myParser.getAttributeValue(null, "firstgid")!=null)
							{
								tempTset.setFirstgid(Integer.parseInt(myParser.getAttributeValue(null, "firstgid")));
							}
							else
							{
								tempTset.setFirstgid(requestGid());
							}
							if (source == null)
							{
								
								tempTset.setName(myParser.getAttributeValue(null, "name"));
								if (myParser.getAttributeValue(null, "columns")!=null) 
								{
									tempTset.setColumns(Integer.parseInt(myParser.getAttributeValue(null, "columns")));
									tempTset.setTilecount(Integer.parseInt(myParser.getAttributeValue(null, "tilecount")));
									tempTset.setWidth(tempTset.getColumns());
									tempTset.setHeight(tempTset.getTilecount()/tempTset.getColumns());
								}
								
								if (myParser.getAttributeValue(null, "margin")!=null) tempTset.setMargin(Integer.parseInt(myParser.getAttributeValue(null, "margin")));
								if (myParser.getAttributeValue(null, "spacing")!=null) tempTset.setSpacing(Integer.parseInt(myParser.getAttributeValue(null, "spacing")));
								if (myParser.getAttributeValue(null, "tilewidth")!=null) 
								{
									tempTset.setTilewidth(Integer.parseInt(myParser.getAttributeValue(null, "tilewidth")));
								}
								else
								{
									tempTset.setTilewidth(Tsw);
								}
								if (myParser.getAttributeValue(null, "tileheight")!=null)
								{
									tempTset.setTileheight(Integer.parseInt(myParser.getAttributeValue(null, "tileheight")));
								}
								else
								{
									tempTset.setTileheight(Tsh);
								}

								tilesets.add(tempTset);
							}
							else
							{
								
								
								String foredir, tempdir;
								foredir=curdir;
								if (foredir.substring(foredir.length()-1,foredir.length()).equalsIgnoreCase("/"))
								{
									foredir= foredir.substring(0,foredir.length()-1);
								}
								tempdir=source;
								
								loadtsx(foredir+"/"+tempdir);
								loadingfile=true;
							}
						}
						if (name.equals("objectgroup"))
						{
							curgroupid += 1;curobjid = -1;selgroup += 1;
							objgroups.add(new objgroup());
							curgroup = myParser.getAttributeValue(null, "name");
							objgroups.get(curgroupid).setName(curgroup);


						}

						if (name.equals("tile"))
						{
							if (owner.equalsIgnoreCase("layer")){
								String gid = myParser.getAttributeValue(null, "gid");
								
								if (gid !="") spr.add(Long.parseLong(gid));
							}
							if (owner.equalsIgnoreCase("tileset")){
								tempTile = new tile();
								tempTile.setTileID(Integer.parseInt(myParser.getAttributeValue(null, "id")));
								if (myParser.getAttributeValue(null, "terrain")!=null) {tempTile.setTerrain(myParser.getAttributeValue(null, "terrain"));}
							}
							oldowner=owner;
							owner="tile";
							
						}
						if (name.equals("frame"))
						{

							tempTile.getAnimation().add(new frame(Integer.parseInt(myParser.getAttributeValue(null, "tileid")),Integer.parseInt(myParser.getAttributeValue(null, "duration"))));


						}
						if (name.equals("property"))
						{
							
							String n="",t="",v="";isi="";
							if (myParser.getAttributeValue(null, "name")!=null) {n=myParser.getAttributeValue(null, "name");}
							if (myParser.getAttributeValue(null, "type")!=null) {t=myParser.getAttributeValue(null, "type");}
							if (myParser.getAttributeValue(null, "value")!=null) {v=myParser.getAttributeValue(null, "value");}
							tempe= new property(n,t,v);
							isi="";
			
						}
						if (name.equals("object"))
						{
							tempobj=new obj();
							if (myParser.getAttributeValue(null, "id")!=null)
							{
								int pID = Integer.parseInt(myParser.getAttributeValue(null, "id"));
								lastPid = pID;
							}
							else
							{
								lastPid+=1;
							}
							
							tempobj.setId(lastPid);
							String pName =""; String pType="";
							pName = myParser.getAttributeValue(null, "name");
							pType = myParser.getAttributeValue(null, "type");
							tempobj.setName(pName);
							tempobj.setType(pType);
							tempobj.setX(Float.parseFloat(myParser.getAttributeValue(null, "x")));
							tempobj.setY(Float.parseFloat(myParser.getAttributeValue(null, "y")));
							
							if (myParser.getAttributeValue(null, "gid")!=null) 
							{
								tempobj.setShape("image");
								tempobj.setGid(Integer.parseInt(myParser.getAttributeValue(null, "gid")));
								tempobj.setY(Float.parseFloat(myParser.getAttributeValue(null, "y"))-Tsh);
							}
								if (myParser.getAttributeValue(null, "rotation")!=null) tempobj.setRotation(Float.parseFloat(myParser.getAttributeValue(null, "rotation")));
							
							float pWidth,pHeight;
							if (myParser.getAttributeValue(null, "width") != null)
							{
								try{
								pWidth = Float.parseFloat(myParser.getAttributeValue(null, "width"));
								pHeight = Float.parseFloat(myParser.getAttributeValue(null, "height"));
								}
								catch(Exception e)
								{
									pWidth = Tsw;pHeight = Tsh;
									tempobj.setShape("point");
								}
							}
							else
							{
								pWidth = Tsw;pHeight = Tsh;
								tempobj.setShape("point");
								
							}
							tempobj.setW(pWidth);tempobj.setH(pHeight);
							
							
							owner="object";

						}
						if (name.equals("polyline"))
						{
							tempobj.setShape("polyline");
							tempobj.setPointsFromString(myParser.getAttributeValue(null, "points"));
							
						}
						if (name.equals("polygon"))
						{
							tempobj.setShape("polygon");
							tempobj.setPointsFromString(myParser.getAttributeValue(null, "points"));
							
						}
						
						if (name.equals("ellipse"))
						{
							tempobj.setShape("ellipse");

						}
						if (name.equals("point"))
						{
							tempobj.setShape("point");

						}
						if (name.equals("text"))
						{
							tempobj.setShape("text");
							tempobj.setWrap(Boolean.parseBoolean(myParser.getAttributeValue(null, "wrap")));
							isi="";
						}
						break;
					case XmlPullParser.TEXT:
						isi += myParser.getText();
						

						break;
					case XmlPullParser.END_TAG:
						if (name.equals("object")){
							
							objgroups.get(curgroupid).getObjects().add(tempobj);
							curobjid += 1;//untuk looping id
						}
						if (name.equals("tile"))
						{
							if (owner.equalsIgnoreCase("tile")){
							tempTset.getTiles().add(tempTile);
							
							}
							owner=oldowner;
							oldowner="";
						}
						if (name.equals("tileset"))
						{
							
							tempTset=null;
						}
						if (name.equals("text"))
						{
							tempobj.setText(isi);
						}
						if (name.equals("property"))
						{
							
							
							String data = isi.replace("\n", "").trim();
							String rawe = isi.trim();
							if (data!="") tempe.setValue(rawe);
							
							if (tempe.getName().equalsIgnoreCase("embedded_png"))
							{
								
								String foredir="", tempdir="", combo="";
								try{
								
									Texture bucket;
									String base64 = data;
									byte[] decodedBytes = Base64Coder.decode(base64);
									bucket = new Texture(new Pixmap(decodedBytes, 0, decodedBytes.length));
									

									tempTset.setTexture(bucket);
									
									if (tempTset.getTrans() !=null){
										tempTset.setTexture(chromaKey(tempTset.getTexture(),tempTset.getTrans()));
									}

									tempTset.setPixmap(pixmapfromtexture(bucket,tempTset.getTrans()));
									
										
										tempTset.setOriginalwidth(bucket.getWidth());
										tempTset.setOriginalheight(bucket.getHeight());
									if (tempTset.getColumns()==0)
									{
										tempTset.setColumns((tempTset.getOriginalwidth()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTilewidth()+tempTset.getSpacing()));
										tempTset.setWidth(tempTset.getColumns());
										tempTset.setHeight((tempTset.getOriginalheight()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTileheight()+tempTset.getSpacing()));
										tempTset.setTilecount(tempTset.getWidth()*tempTset.getHeight());
									}

									templastID += tempTset.getWidth() * tempTset.getHeight();
									
									alreadyloaded=true;
								}catch(Exception e){
									ErrorBung(e,"okok.txt");
								}

								
								
							}
							
							switch (owner)
							{
								case "object":
									tempobj.getProperties().add(tempe);
									break;
								case "tileset":
									tempTset.getProperties().add(tempe);
									break;
								case "tile":
									tempTile.getProperties().add(tempe);
									break;
								case "map":
									properties.add(tempe);
									break;
							}
							
						}
						
						if (name.equals("data"))
						{
							
								
					
							if (!encoding.equalsIgnoreCase("xml")) spr = new ArrayList<Long>();
							Log.w("nyuuu", encoding);
							if (encoding.equalsIgnoreCase("csv"))
							{
								mapFormat="csv";
								String data = isi.replace("\n", "");
								data = data.replace(" ", "");

								Log.w("nyu", data);
								String[] parts = data.split(",");

								for (int i=0;i < Tw * Th;i++)
								{
									spr.add(Long.parseLong(parts[i]));
								}
							
							}
							else if (encoding.equalsIgnoreCase("base64") && compression.equalsIgnoreCase("zlib"))
							{
								mapFormat="base64-zlib";
								String data = isi.replace("\n", "");
								data = data.replace(" ", "");
								spr = decoder.decodeZlib(data,Tw*Th*4);
								
								
							}
							else if (encoding.equalsIgnoreCase("base64") && compression.equalsIgnoreCase("gzip"))
							{
								mapFormat="base64-gzip";
								String data = isi.replace("\n", "");
								data = data.replace(" ", "");
								spr = decoder.decodeGzip(data);
								//writeThis(tempLayer.getName()+ "fkyu.txt",spr.toString());
								
							}
							else if (encoding.equalsIgnoreCase("base64"))
							{
								mapFormat="base64";
								String data = isi.replace("\n", "");
								data = data.replace(" ", "");
								spr = decoder.decode(data);
								
							}
							else  if (encoding.equalsIgnoreCase("xml"))
							{
								mapFormat="xml";
							}

							tempLayer.setStr(spr);
							//tempLayer.setTset(cacheTset(spr));
							layers.add(tempLayer);
							seltile += 1;
							}
		

						


						break;
				}		 
				event = myParser.next(); 					
			}

			if (selgroup == -1) 
			{
				objgroup oge = new objgroup();
				oge.setName(z.object+" 1");
				objgroups.add(oge);
				selgroup = 0;
			}
			
			resetcam(false);
			
			//uicam.position.set(120,-200,0);//center
			
			prefs.putString("lof",file.getAbsolutePath());
			prefs.flush();
			
			kartu = "world";mode = "tile";
			
			
			
		}
		catch (Exception e)
		{
			newtmxfile(false);
			status("cannot load tmx file",5);
			ErrorBung(e,"/maknyus.txt");
		}
		CacheAllTset();
		cacheTiles();
		
		status(errors,10);
		loadautotiles();
		uicam.zoom=0.5f;
		uicam.update();
		loadingfile=false;
		firstload=loadtime;
	}

	public void resetcam(boolean animate)
	{
		if (animate)
		{
			panTo(Tsw*Tw/2,-Tsh*Th/2,.25f,.5f);
		}else
		{
			cam.position.set(Tsw*Tw/2,-Tsh*Th/2,0);
			cam.zoom=.5f;
			cam.update();
			panTo(Tsw*Tw/2,-Tsh*Th/2,.25f,1f);
		}
		
		float ttsw = tilesets.get(seltset).getTilewidth();
		float ttsh = tilesets.get(seltset).getTileheight();
		float ttkw = tilesets.get(seltset).getWidth();
		float ttkh = tilesets.get(seltset).getHeight();

		tilecam.position.set((int)ttsw*ttkw/2, (int)-ttsh*ttkh/2,0);
		tilecam.zoom=.25f;
		tilecam.update();
		
	}
	public java.util.List<Integer> cacheTset(java.util.List<Long> spr)
	{
		tilesetsize=tilesets.size();
		java.util.List<Integer> nyot=new ArrayList<Integer>();
		for (int s=0;s<spr.size();s++){
			hex=Long.toHexString(spr.get(s));
			trailer="00000000" + hex;
			hex = trailer.substring(trailer.length() - 8);
			String flag = hex.substring(0, 2);
			Long mm = Long.decode("#00" + hex.substring(2, 8));
			boolean isi=false;
		for (int g=tilesetsize-1;g>=0;g--){
			if (mm==0)
			{
				nyot.add(-1);
				break;
			}
			if (mm >= tilesets.get(g).getFirstgid() && mm < tilesets.get(g).getFirstgid() + tilesets.get(g).getTilecount())
			{
				nyot.add(g);
				isi=true;
				break;
			}
			if (g==0 && !isi)
			{
				nyot.add(-1);
				break;
			}
		}
		}
		
		return nyot;
	}
	public void CacheAllTset()
	{
		for (int lay=0;lay<layers.size();lay++)
		{
		layers.get(lay).setTset(cacheTset(layers.get(lay).getStr()));
		}
	}
	
	public int requestGid()
	{
		try
		{
		if (tilesets.size()>0)
		{
			int ct=1;
			for (int i=0;i<tilesets.size();i++)
			{
				ct=tilesets.get(i).getFirstgid()+ tilesets.get(i).getTilecount();
			}
			return ct;
		}
		}catch(Exception e){
			status("error loading file",5);
		}
		return 1;
	}
	
	public void loadtsx(String source)
	{
		//msgbox(source);
		loadingfile=true;
		String tsxpath = "";
		String owner="";
		String isi="";
		String fname="";
		try
		{
			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
			XmlPullParser myParser = xmlFactoryObject.newPullParser();
			String internalpath = "rusted_warfare/assets/tilesets";
			String tempdiro="",tempdiri="",foredirint="",foredirext="";
			
			try{
			tempdiri=source.substring(curdir.length()+1);
			foredirint=internalpath;
			if (tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1)!=-1)
			{
				tempdiri=tempdiri.substring(tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1));
				tempdiri=tempdiri.replace("tilesets/","");
			}
			}catch(Exception e){}
			
			try{
			tempdiro=source.substring(curdir.length()+1);
			foredirext=curdir;
			while (tempdiro.substring(0,3).equalsIgnoreCase("../"))
			{
				tempdiro=tempdiro.substring(3,tempdiro.length());
				foredirext=foredirext.substring(0,foredirext.lastIndexOf("/"));
			}
			}catch(Exception e){}
			
			FileHandle filehand = Gdx.files.internal(foredirint+"/"+tempdiri);
			/*
			if (!filehand.exists()){
				filehand = Gdx.files.absolute(foredirext+"/"+tempdiro);
			}
			*/
			if (!filehand.exists()){
				filehand = Gdx.files.absolute(source);
			}
			tsxpath = filehand.path().substring(0,filehand.path().lastIndexOf("/"));
			
			fname=filehand.name();
			InputStream stream = filehand.read();
			
			myParser.setInput(stream, null);
			int event = myParser.getEventType();
			tile tempTile= new tile();
			templastID=1;
			while (event != XmlPullParser.END_DOCUMENT)
			{
				String name=myParser.getName();


				switch (event)
				{
					case XmlPullParser.START_TAG:
						if (name.equals("tileset"))
						{
							owner="tileset";
							if (tempTset==null) 
							{
								tempTset = new tileset();
								tempTset.setFirstgid(requestGid());
							}
							tempTset.setUsetsx(true);
							
							String tc[] =curdir.split("/");
							String ts[] =source.split("/");
							
							boolean git=false;
							String pre="",post="";
							for (int i=0;i<tc.length;i++)
							{
								if (git)
								{
									pre+="../";
									continue;
								}
								if (ts.length<=i)
								{
									git=true;
									pre+="../";
									continue;
								}
								if (!ts[i].equalsIgnoreCase(tc[i]))
								{
									git=true;
									pre+="../";
								}
							}
							
							git=false;
							for (int i=0;i<ts.length;i++)
							{
								if (git)
								{
									post+=ts[i];
									if (i<ts.length-1) post+="/";
									continue;
								}
								if (tc.length<=i)
								{
									git=true;
									post+=ts[i];
									if (1<ts.length-1) post+="/";
									continue;
								}
								if (!tc[i].equalsIgnoreCase(ts[i]))
								{
									git=true;
									post+=ts[i];
									if (i<ts.length-1) post+="/";
								}
							}
							String cocos =pre+post;
							if (cocos.endsWith("/")) cocos=cocos.substring(0,cocos.length()-1);
							tempTset.setTsxfile(cocos);
							
								
							if (myParser.getAttributeValue(null, "columns")!=null) 
							{
								tempTset.setColumns(Integer.parseInt(myParser.getAttributeValue(null, "columns")));
								tempTset.setTilecount(Integer.parseInt(myParser.getAttributeValue(null, "tilecount")));
								tempTset.setWidth(tempTset.getColumns());
								tempTset.setHeight(tempTset.getTilecount()/tempTset.getColumns());
							}
							
							tempTset.setName(myParser.getAttributeValue(null, "name"));
							if (myParser.getAttributeValue(null, "margin")!=null) tempTset.setMargin(Integer.parseInt(myParser.getAttributeValue(null, "margin")));
							if (myParser.getAttributeValue(null, "spacing")!=null) tempTset.setSpacing(Integer.parseInt(myParser.getAttributeValue(null, "spacing")));
							
							if (myParser.getAttributeValue(null, "tilewidth")!=null) 
							{
								tempTset.setTilewidth(Integer.parseInt(myParser.getAttributeValue(null, "tilewidth")));
							}
							else
							{
								tempTset.setTilewidth(Tsw);
							}
							if (myParser.getAttributeValue(null, "tileheight")!=null)
							{
								tempTset.setTileheight(Integer.parseInt(myParser.getAttributeValue(null, "tileheight")));
							}
							else
							{
								tempTset.setTileheight(Tsh);
							}
							tilesets.add(tempTset);
						}
						if (name.equals("terrain"))
						{
							terrain tr=new terrain();
							tr.setName(myParser.getAttributeValue(null, "name"));
							tr.setTile(Integer.parseInt(myParser.getAttributeValue(null, "tile")));
							tempTset.getTerrains().add(tr);
						}
						
						if (name.equals("tile"))
						{
							tempTile = new tile();
							owner="tile";
							tempTile.setTileID(Integer.parseInt(myParser.getAttributeValue(null, "id")));
							if (myParser.getAttributeValue(null, "terrain")!=null) {tempTile.setTerrain(myParser.getAttributeValue(null, "terrain"));}
							
						}
						if (name.equals("frame"))
						{

							tempTile.getAnimation().add(new frame(Integer.parseInt(myParser.getAttributeValue(null, "tileid")),Integer.parseInt(myParser.getAttributeValue(null, "duration"))));
							

						}
						if (name.equals("property"))
						{
							String n="",t="",v="";isi="";
							n=myParser.getAttributeValue(null, "name");
							if (myParser.getAttributeValue(null, "type")!=null) {t=myParser.getAttributeValue(null, "type");}
							v=myParser.getAttributeValue(null, "value");
							tempe= new property(n,t,v);
							isi="";
						}
						if (name.equals("image"))
						{
							tempTset.setTrans(myParser.getAttributeValue(null, "trans"));
							tempTset.setSource(myParser.getAttributeValue(null, "source"));
						

							String foredir, tempdir, combo;
							foredir=tsxpath;//should be tsxpath to folloe tsx pathing but whatever!!
							//errors+=tsxpath+"ole\n";
							if (foredir.substring(foredir.length()-1,foredir.length()).equalsIgnoreCase("/"))
							{
								foredir= foredir.substring(0,foredir.length()-1);
							}
							tempdiro=tempTset.getSource();
							tempdiri=tempTset.getSource();
							
							foredirint=tsxpath;
							foredirext=tsxpath;
							while (tempdiro.substring(0,3).equalsIgnoreCase("../"))
							{
								tempdiro=tempdiro.substring(3,tempdiro.length());
								foredirext=foredirext.substring(0,foredirext.lastIndexOf("/"));
								//foredirint=foredirint.substring(0,foredirint.lastIndexOf("/"));
							}
							
							if (tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1)!=-1)
							{
								tempdiri=tempdiri.substring(tempdiri.lastIndexOf("/",tempdiri.lastIndexOf("/")-1));
								tempdiri=tempdiri.replace("tilesets/","");
							}
							
							

							filehand = Gdx.files.internal(foredirint+"/"+tempdiri);

							if (!filehand.exists()){
								filehand = Gdx.files.absolute(foredirext+"/"+tempdiro);
								if (!filehand.exists()){
									filehand = Gdx.files.internal("empty.jpeg");
								}
							}
							//errors+=filehand.path()+"\n";
							try{
								
								tempTset.setTexture(new Texture(filehand));
								tempTset.setPixmap(pixmapfromtexture(tempTset.getTexture(),tempTset.getTrans()));
								
								if (tempTset.getTrans() !=null){
									tempTset.setTexture(chromaKey(tempTset.getTexture(),tempTset.getTrans()));
								}
								if (myParser.getAttributeValue(null, "width")!=null){
									tempTset.setOriginalwidth(Integer.parseInt(myParser.getAttributeValue(null, "width")));
									tempTset.setOriginalheight(Integer.parseInt(myParser.getAttributeValue(null, "height")));
								} else
								{
									
									SimpleImageInfo sii;
									sii=new SimpleImageInfo(filehand.read());
									tempTset.setOriginalwidth(sii.getWidth());
									tempTset.setOriginalheight(sii.getHeight());
								}
							}catch(Exception e){
								tempTset.setOriginalwidth(0);
								tempTset.setOriginalheight(0);
								errors+="Not Found: "+tempdiro+"\n"+e;
							}
							if (tempTset.getColumns()==0)
							{
								tempTset.setColumns((tempTset.getOriginalwidth()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTilewidth()+tempTset.getSpacing()));
								tempTset.setWidth(tempTset.getColumns());
								tempTset.setHeight((tempTset.getOriginalheight()-tempTset.getMargin()*2+tempTset.getSpacing())/(tempTset.getTileheight()+tempTset.getSpacing()));
								tempTset.setTilecount(tempTset.getWidth()*tempTset.getHeight());
							}
								
							templastID += tempTset.getWidth() * tempTset.getHeight();
			
						}

						break;
					case XmlPullParser.TEXT:
						isi += myParser.getText();

						break;
					case XmlPullParser.END_TAG:
						if (name.equals("tile"))
						{
							tempTset.getTiles().add(tempTile);
							owner="tileset";
						}
						if (name.equals("tileset"))
						{
							
							tempTset=null;
						}
						if (name.equals("property"))
						{
							String data = isi.replace("\n", "").trim();
							if (data!="") tempe.setValue(data);
							
							switch (owner)
							{
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
		tsxFile=source;
		tempTset=null;
		}
		catch (Exception e)
		{

			ErrorBung(e,"loadingtsxerror.txt");
			errors+="Not Found: "+fname+"\n";
			
			loadingfile=false;
			
		}
	loadingfile=false;
	}
//////////////////////////////////////////////////////
//            GESTURE DETECTOR
//////////////////////////////////////////////////////
	@Override
	public boolean touchDown(float p1, float p2, int p3, int p4)
	{
		velx=0;vely=0;
		if (kartu == "world")
		{
			initialZoom = cam.zoom;
		}
		else if (kartu == "tile" || kartu == "pickanim")
		{
			initialZoom = tilecam.zoom;
		}
	
		return false;
	}

	@Override
	public boolean tap(float p1, float p2, int p3, int p4)
	{
		if (loadingfile) return true;
		Vector3 touch2 = new Vector3();
		uicam.unproject(touch2.set(p1, p2, 0));
		if (!drag)
		{
			
			switch (kartu)
			{
				
				case "world":
					
					if (!roll) if (tapWorldMenu(touch2)) return true;
					if (tilesets.size()==0){kartu = "tile";return true;}
					
					Vector3 touch = new Vector3();
					cam.unproject(touch.set(p1, p2, 0));
					int ae= (int) touch.x;
					int ab = (int) touch.y;
					boolean touched=false;
					
					if (orientation.equalsIgnoreCase("orthogonal"))
					{
						touched=touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw;
					}
					else if (orientation.equalsIgnoreCase("isometric"))
					{
						touched=true;
					}
						
					if (touched)
					{
						
						int num=0;
						if (orientation.equalsIgnoreCase("orthogonal"))
						{
							num = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
						}
						else if (orientation.equalsIgnoreCase("isometric"))
						{
							float closest=9999;
							for (int i=0;i<Tw*Th;i++)
							{
								int offsetx=0,offsety=0;
								xpos = i % Tw;
								ypos = i / Tw;
								offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
								offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
								int drawx=xpos*Tsw-offsetx;
								int drawy=-ypos*Tsh-offsety;							
								int x=ae-drawx;
								int y=ab-drawy;		
								int Tsws=Tsw/2;
								int Tshs=Tsh/2;
								float dx=Math.abs(x-Tsws);
								float dy=Math.abs(y-Tshs);
								if (dx/Tsws+dy/Tshs<closest)
								{
									num=i;
									closest=dx/Tsws+dy/Tshs;
								}
							}
						}
					
						if (mode == "tile")
						{
							tapTile(num,false);
						}
						else if (mode== "object")
						{
							tapObject(num,touch,ae,ab);

						}
						else if (mode=="newpoly")
						{
							tapNewPoly(num,touch,ae,ab);
						}
					}
					break;
				case "tile": case "pickanim": default:
					if (tapPickUI(p1,p2)) return true;
					tapPick(p1,p2);
					break;

			}}
		//cacheTiles(); //using buffer
		return false;

	}

	private boolean tapNewPoly(Integer num, Vector3 touch, int ae, int ab)
	{
		obj nyok=newobject;
		if (magnet==1){
			switch (nyok.getShape()){
				case "polygon": case "polyline":
					int a=ae-(int) nyok.getX();
					int b=-ab -(int) nyok.getY()+ Tsh;
					if (a<0) a-=Tsw;
					if (b<0) b-=Tsh;
					int x=(a/ Tsw) * Tsw;
					int y= (b/ Tsh) * Tsh;
					
					nyok.addPoint(x,y);
					break;

			}
		}else{
			nyok.addPoint((int) touch.x-nyok.getX(),-(int)touch.y+Tsh-nyok.getY());
		
		}
		return false;
	}
	private void recenterpick()
	{
						float ttsw = tilesets.get(seltset).getTilewidth();
					float ttsh = tilesets.get(seltset).getTileheight();
					float ttkw = tilesets.get(seltset).getWidth();
					float ttkh = tilesets.get(seltset).getHeight();
					
					panTileTo((int)ttsw*ttkw/2, (int)-ttsh*ttkh/2,.5f);
	}
	private boolean tapPickUI(float p1, float p2){
		
		Vector3 touch2 = new Vector3();
		uicam.unproject(touch2.set(p1, p2, 0));
		if (tapped(touch2,gui.newterrain))
		{
			if (tilePicker=="terraineditor" && kartu =="pickanim")
			{
				pickTile("newterrain");
				return true;
			}
			if (tilePicker=="massprops" && kartu == "pickanim")
			{
				for (int o=0;o<massprops.size();o++)
				{
					boolean ada=false;int ka=0;
					property tgt=null;
					java.util.List<property> ppt=null;
					
					for (int k=0; k<tilesets.get(selTsetID).getTiles().size();k++){
						if (tilesets.get(selTsetID).getTiles().get(k).getTileID()==o)
						{
							ppt = tilesets.get(selTsetID).getTiles().get(k).getProperties();
							for (int j=0;j<ppt.size();j++)
							{
								if (ppt.get(j).getName().equalsIgnoreCase(temproname) && ppt.get(j).getValue().equalsIgnoreCase(temprovalue))
								{
									ada=true;
									tgt=ppt.get(j);
									ka=k;
								}
							}

						}
					}
					
					if (ada)
					{
						if (!massprops.get(o))
						{
							ppt.remove(tgt);
							tile tt = tilesets.get(selTsetID).getTiles().get(ka);
							if (tt.getProperties().size()==0 && tt.getAnimation().size()==0)
							{
								tilesets.get(selTsetID).getTiles().remove(tt);
							}
						}else
						{
							tgt.setValue(temprovalue);
						}
					}else
					{
						if (massprops.get(o))
						{
							boolean adatile=false;
							for (int k=0; k<tilesets.get(selTsetID).getTiles().size();k++){
								if (tilesets.get(selTsetID).getTiles().get(k).getTileID()==o)
								{
									adatile=true;
									ka=k;
								}
							}
							
							properties pt= new properties();
							pt.getProperties().add(new property(temproname,temprotype,temprovalue));
							if (!adatile)
							{
								tile tile = new tile();
								tile.setTileID(o);
								tile.getProperties().add(new property(temproname,temprotype,temprovalue));
								tilesets.get(selTsetID).getTiles().add(tile);
							}
							else
							{
								tile tile = tilesets.get(selTsetID).getTiles().get(ka);
								tile.getProperties().add(new property(temproname,temprotype,temprovalue));
							}
						}
					}
					
					
					
				}
				java.util.List <tile> tiles = tilesets.get(selTsetID).getTiles();
				ltilelist.setItems(new String[]{});
				String[] srr = new String[tiles.size()];
				for (int i=0;i < tiles.size();i++)
				{
					srr[i] = Integer.toString(tiles.get(i).getTileID());
				}

				ltilelist.setItems(srr);
				gotoStage(tTileMgmt);
			}

		}
		
		if (tilesets.size()>0){
			frompick=true;
			if (kartu.equalsIgnoreCase("tile")){
				if (tapped(touch2,gui.tool1)){
					FileDialog(z.selectfile,"quickaddtset","file",new String[]{".tsx",".png",".jpg",".jpeg",".bmp",".gif"},nullTable);
					return true;
				}
			
				if (tapped(touch2,gui.tool2)){
					selTsetID=seltset;
					int saiz=tilesets.size();


					String[] srr = new String[saiz];
					for (int i=0;i < saiz;i++)
					{
						srr[i] = tilesets.get(i).getName();
					}
					ltsetlist.setItems(srr);
					
					
					if (tilesets.size()>0){
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

						if (ct.getName()!=null) fTsPropName.setText(ct.getName());
						if (ct.getSource()!=null) fTsPropSource.setText(ct.getSource());
						if (ct.getTsxfile()!=null) fTsPropTsxFile.setText(ct.getTsxfile());
						cbTsPropUseTsx.setChecked(ct.isUsetsx());
						if (ct.getTilewidth()!=0) fTsPropTsw.setText(Integer.toString(ct.getTilewidth()));
						if (ct.getTileheight()!=0) fTsPropTsh.setText(Integer.toString(ct.getTileheight()));
						if (ct.getColumns()!=0) fTsPropCols.setText(Integer.toString(ct.getColumns()));
						if (ct.getTilecount()!=0) fTsPropTc.setText(Integer.toString(ct.getTilecount()));
						if (ct.getMargin()!=0) fTsPropMargin.setText(Integer.toString(ct.getMargin()));
						if (ct.getSpacing()!=0) fTsPropSpacing.setText(Integer.toString(ct.getSpacing()));

						if (ct.getColumns()!=0) fTsPropCols.setText(Integer.toString(ct.getColumns()));
						if (ct.getTrans()!=null) fTsPropTrans.setText(ct.getTrans());

						gotoStage(tTsProp);
					}
					
					
					
					
					return true;
				}
		
				if (tapped(touch2,gui.tool3)){
					
					int saiz=tilesets.size();


					String[] srr = new String[saiz];
					for (int i=0;i < saiz;i++)
					{
						srr[i] = tilesets.get(i).getName();
					}
					ltsetlist.setItems(srr);
					selTsetID=seltset;
					gotoStage(tTileMgmt);
					return true;
				}
				
				if (tapped(touch2,gui.tool5)){
					tilesets.remove(seltset);
					if(tilesets.size()>0)
					{
						if (seltset!=0) seltset-=1;
						recenterpick();
					}else
					{
						seltset=0;
					}
					CacheAllTset();
					return true;
				}
			
			
			
			
			}
		}
		
		if (tapped(touch2,gui.tilesets))
		{
			if (tilesets.size()>0){
				if (kartu.equalsIgnoreCase("tile"))
				{
					
					if (tapped(touch2,gui.tilesetsright))
					{
						seltset+=1;
						if (seltset>=tilesets.size())
							{
							seltset=0;
						}
					}else
					{
						seltset-=1;
						if (seltset<=-1)
						{
							seltset=tilesets.size()-1;
						}
					}
					recenterpick();
					
				}else if (kartu.equalsIgnoreCase("pickanim")){
					switch (tilePicker){
						case "newimgobj":case "props":case "rnda":case "rndb":case "repa": case "repb":
							if (tapped(touch2,gui.tilesetsright))
							{
								seltset+=1;
								if (seltset>=tilesets.size())
								{
									seltset=0;
								}
								recenterpick();
							}else
							{
								seltset-=1;
								if (seltset<=-1)
								{
									seltset=tilesets.size()-1;
								}
								recenterpick();
							}
							break;
						case "terraineditor":
							tileset tt=tilesets.get(selTsetID);
							if (tt.getTerrains().size()>0)
							{
								tt.setSelTerrain(tt.getSelTerrain()+1);
								if (tt.getSelTerrain() >= tt.getTerrains().size())
								{
									tt.setSelTerrain(0);
								}
							}
							return true;
					}
				}
				
				
			}else{
				FileDialog(z.selectfile,"quickaddtset","file",new String[]{".tsx",".png",".jpg",".jpeg",".bmp",".gif"},nullTable);
				cue("addtileset");
			}
			return true;
		}
		return false;
	}
	private boolean tapPick(float p1,float p2)
	{
		if (loadingfile) return true;
		int seltset=0;
		stamp = false;
		if (tilesets.size()==0) return true;
		Vector3 touch = new Vector3();
		tilecam.unproject(touch.set(p1, p2, 0));
		if (kartu=="tile" || tilePicker=="newimgobj"||tilePicker=="props"||tilePicker=="rnda"||tilePicker=="rndb"||tilePicker=="repa"||tilePicker=="repb")
		{
			seltset=this.seltset;
		}
		else
		{
			seltset=this.selTsetID;
		}
		tileset ts=tilesets.get(seltset);
		int ae = (int) touch.x;
		int ab = (int) touch.y;
		if (touch.y < ts.getTileheight() && touch.y > -ts.getTileheight() * ts.getHeight() + ts.getTileheight() && touch.x > 0 && touch.x < ts.getTilewidth() * ts.getWidth())
		{
			Integer num = ts.getFirstgid()+(ts.getWidth() * ((-ab + ts.getTileheight()) / ts.getTileheight()) + (ae / ts.getTilewidth()));
			
			if (kartu == "tile")
			{
				curspr = num;                                           
				kartu = "world";
				cue("tilepickclick");
			}
			else
			{
				
				switch (tilePicker)
				{
					case "props":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						fPropVal.setText(Integer.toString(num));
						break;
					case "rnda":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						flivestr.setText(Integer.toString(num));
						break;
					case "rndb":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						fdeadstr.setText(Integer.toString(num));
						break;
					case "repa":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						fprevstr.setText(Integer.toString(num));
						break;
					case "repb":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						fnextstr.setText(Integer.toString(num));
						break;
					case "addanim":
						/*
						int dex=ltsetlist.getSelectedIndex();
						selTsetID=dex;
						*/
						tile newanim = new tile();

						newanim.setTileID(num - ts.getFirstgid());
						
						java.util.List<tile> tiles = tilesets.get(selTsetID).getTiles();
						boolean ada=false;
						tile oldanim;
						for (int o=0;o<tiles.size();o++)
						{
							if (tiles.get(o).getTileID()==num-ts.getFirstgid())
							{
								ada=true;
								oldanim=tiles.get(o);
								selTileID=o;
							}
						}
						if (!ada){
							tiles.add(newanim);
							selTileID=tiles.size()-1;
						}
						
							lFrameID.setText(z.animation+" "+ z.id + ": "+tiles.get(selTileID).getTileID());
							int dexa=tiles.get(selTileID).getAnimation().size();

							String[] srrA = new String[dexa];
						
							lframelist.setItems(new String[]{});
							for (int i=0;i < dexa;i++)
							{

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
						ada=false;
						for (int o=0;o<tiles.size();o++)
						{
							if (tiles.get(o).getTileID()==num-ts.getFirstgid())
							{
								ada=true;
								oldanim=tiles.get(o);
								selTileID=o;
							}
						}
						if (!ada){
							tiles.add(newanim);
							selTileID=tiles.size()-1;
						}

						////
						refreshProperties(tilesets.get(selTsetID).getTiles().get(selTileID).getProperties());
						
						lPropID.setText(z.properties+": " + tilesets.get(selTsetID).getTiles().get(selTileID).getTileID());
						sender="tile";
						gotoStage(tPropsMgmt);
						////
						
						break;
					case "replaceanim":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						int dex=ltilelist.getSelectedIndex();
						
						tiles = tilesets.get(selTsetID).getTiles();

						tiles.get(dex).setTileID(num - ts.getFirstgid());
						String[] srr = new String[tiles.size()];
						for (int i=0;i < tiles.size();i++)
						{
							srr[i] = Integer.toString(tiles.get(i).getTileID());
						}
						ltilelist.setItems(srr);
						ltilelist.setSelectedIndex(dex);
						break;
					case "addframe":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						tempframeid = num - ts.getFirstgid() ;
						tiles = tilesets.get(selTsetID).getTiles();

						int aa= num -ts.getFirstgid();
						String bb=prefs.getString("duration","500");
						int cc=Integer.parseInt(bb);
						frame frame=new frame(aa,cc);
						tiles.get(selTileID).getAnimation().add(frame);
						prefs.putString("duration", bb);prefs.flush();
						String srra[] = new String[tiles.get(selTileID).getAnimation().size()];
						for (int i=0;i < tiles.get(selTileID).getAnimation().size();i++)
						{
							String aaa= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
							String bbb= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
							srra[i] = aaa + " (" + bbb + "ms)";
						}
						lframelist.setItems(srra);
						lframelist.setSelectedIndex(tiles.get(selTileID).getAnimation().size() - 1);
						break;

					case "replaceframe":
						kartu = "stage";
						Gdx.input.setInputProcessor(stage);
						dex = lframelist.getSelectedIndex();
						int x=selTsetID;
						tiles = tilesets.get(x).getTiles();

						tiles.get(selTileID).getAnimation().get(dex).setTileID(num - ts.getFirstgid()  );
						srr = new String[tiles.get(selTileID).getAnimation().size()];
						for (int i=0;i < tiles.get(selTileID).getAnimation().size();i++)
						{
							String aaa= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getTileID());
							String bbb= Integer.toString(tiles.get(selTileID).getAnimation().get(i).getDuration());
							srr[i] = aaa + " (" + bbb + "ms)";
						}
						lframelist.setItems(srr);
						lframelist.setSelectedIndex(dex);

						break;

					case "newimgobj":

						newobject.setGid(num);
						Gdx.input.setInputProcessor(gd);
						kartu="world";

						break;
					case "newterrain":
						newTerrainID = num - ts.getFirstgid() ;
						tilePicker="terraineditor";
						Gdx.input.getTextInput(pNewTerrain, "New Terrain Name", "","");
						kartu = "pickanim";
						Gdx.input.setInputProcessor(gd);
						return true;
					case "massprops":
						if (massprops.get(num-ts.getFirstgid()))
						{
							massprops.set(num-ts.getFirstgid(),false);
						}else{
							massprops.set(num-ts.getFirstgid(),true);
						}
						return true;
					case "terraineditor":
						if (ts.getTerrains().size()==0) return true;
						tiles = ts.getTiles();
						tile t=null;
						int dst=num - ts.getFirstgid();
						for (int n =0;n < tiles.size();n++)
						{
							if (tiles.get(n).getTileID()== dst){
								t=tiles.get(n);
								break;
							}
						}
						
						if (t==null)
						{
							tile tt = new tile();
							tt.setTileID(dst);
							tiles.add(tt);
							t=tiles.get(tiles.size()-1);
						}
							int[] cn=t.getTerrain();
							
							int a=cn[0],b=cn[1],c=cn[2],d=cn[3];
							int e =tilesets.get(selTsetID).getSelTerrain();
							
							//ada tile kosong
							if ((a==-1) ||  (b==-1) || (c==-1) || (d==-1))
							{
								if (a!=e&&b!=e&&c!=e&&d!=e)
								{
									if (a==-1) a=e;
									if (b==-1) b=e;
									if (c==-1) c=e;
									if (d==-1) d=e;
									t.setTerrain(a,b,c,d);
									return true;
								}
							}
							
						//kalau tidak ada yang kosong, takeover semua
						if ((a>=0) && (b>=0) && (c>=0) && (d>=0))
						{
							if (!(a==e&&b==e&&c==e&&d==e))
							{
								a=e;b=e;c=e;d=e;
								t.setTerrain(a,b,c,d);
								return true;
							}
							
						}
						
						//sisanya
						if ((a==-1) && (b==-1) && (c==-1) && (d==-1))
						{
							a=e;b=e;c=e;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						
						if ((a==e) && (b==e) && (c==e) && (d==e))
						{
							a=-1;b=-1;c=-1;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						
						if ((a==-1) && (b==-1) && (c==-1) && (d==e))
						{
							a=-1;b=-1;c=e;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==-1) && (c==e) && (d==e))
						{
							a=-1;b=-1;c=e;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==-1) && (c==e) && (d==-1))
						{
							a=e;b=-1;c=e;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==-1) && (c==e) && (d==-1))
						{
							a=e;b=-1;c=-1;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==-1) && (c==-1) && (d==-1))
						{
							a=e;b=e;c=-1;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==e) && (c==-1) && (d==-1))
						{
							a=-1;b=e;c=-1;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==e) && (c==-1) && (d==-1))
						{
							a=-1;b=e;c=-1;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==e) && (c==-1) && (d==e))
						{
							a=e;b=e;c=e;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==e) && (c==e) && (d==-1))
						{
							a=e;b=e;c=-1;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==e) && (c==-1) && (d==e))
						{
							a=-1;b=e;c=e;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==e) && (c==e) && (d==e))
						{
							a=e;b=-1;c=e;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==-1) && (c==e) && (d==e))
						{
							a=e;b=-1;c=-1;d=e;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==e) && (b==-1) && (c==-1) && (d==e))
						{
							a=-1;b=e;c=e;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
						if ((a==-1) && (b==e) && (c==e) && (d==-1))
						{
							a=-1;b=-1;c=-1;d=-1;
							t.setTerrain(a,b,c,d);
							return true;
						}
				}
			}
		}        
		return false;
	}

	private boolean tapObject(Integer num,Vector3 touch,int ae,int ab)
	{
		try
		{
			if (objgroups.size() > 0)
			{
				for (int i=0;i < objgroups.size();i++)
				{
					if (objgroups.get(i).getObjects().size() > 0)
					{
						if (i == selgroup || objviewMode == 0)
						{
							for (int j=0;j < objgroups.get(i).getObjects().size();j++)
							{
								obj ox= objgroups.get(i).getObjects().get(j);
								float offx=0,offy=0;
								if (ox.getShape()!=null){
									switch (ox.getShape()){

										case "point":
										case "polygon":
										case "polyline":
											offx=ox.getWa();
											offy=ox.getHa();
										default:



											break;
									}
								}
								if (touch.y <= -(ox.getY() - ox.getH())-ox.getH()+Tsh+offy && touch.y >= -(ox.getY() - ox.getH()) - ox.getH()-ox.getH()+Tsh+offy  && touch.x >= ox.getX()-offx && touch.x <= ox.getX() + ox.getW()-offx)
								{
									if(activeobjtool==7){

										Json json = new Json();
										clipobjcpy=json.toJson(ox);

										return true;
									}
									if(activeobjtool==8){
										objgroups.get(i).getObjects().remove(j);

										return true;
									}
									oedit = j;ogroup = i;
									curobj = ox;
									gotoStage(tObjProp);
									tf.get(0).setText(Integer.toString(ox.getId()));
									tf.get(1).setText(Float.toString(ox.getX()));
									tf.get(2).setText(Float.toString(ox.getY()));
									tf.get(3).setText(Float.toString(ox.getW()));
									tf.get(4).setText(Float.toString(ox.getH()));
									if (ox.getName() != null)
									{tf.get(5).setText(ox.getName());}
									else
									{tf.get(5).setText("");}
									if (ox.getType() != null)
									{tf.get(6).setText(ox.getType());}
									else
									{tf.get(6).setText("");}
									
									if (Float.toString(ox.getRotation()) != null)
									{tf.get(7).setText(Float.toString(ox.getRotation()));}
									else
									{tf.get(7).setText("");}
									return true;
								}//if atas
							}
						}


					}
				}
			}


			obj nyok=new obj();
			

			if(activeobjtool==7){
				if (clipobjcpy==null) return true;
				
				Json json = new Json();
				nyok = json.fromJson(obj.class, clipobjcpy);
				nyok.setId(curid);


				curid += 1;

				if (magnet==1){
					switch (nyok.getShape()){
						case "rectangle":case "ellipse":case "polygon": case "polyline":
						case "image": case "text":default:
							nyok.setX((ae / Tsw) * Tsw);
							nyok.setY(((-ab + Tsh) / Tsh) * Tsh);
							break;
						case "point": 
							nyok.setX((ae / Tsw) * Tsw+(Tsw/2));
							nyok.setY(((-ab + Tsh) / Tsh) * Tsh-Tsh/2+Tsh);
							break;


					}
				}else{
					nyok.setX((int) touch.x);
					nyok.setY(-(int)touch.y+Tsh);
				}
				objgroups.get(selgroup).getObjects().add(nyok);

				return true;
			}
			
			String shapeNameX="";

			if (magnet==1){
				switch (activeobjtool){
					case 0:case 1:
					case 6: case 5:case 3: case 4:
						nyok = new obj(curid, (ae / Tsw) * Tsw, ((-ab + Tsh) / Tsh) * Tsh, Tsw, Tsh, "", "");
						
						
						break;
					case 2: 
						nyok = new obj(curid, (ae / Tsw) * Tsw+(Tsw/2), ((-ab + Tsh) / Tsh) * Tsh-Tsh/2+Tsh, Tsw, Tsh, "", "");
						
						break;


				}
			}else{
				nyok = new obj(curid, (int) touch.x, -(int)touch.y+Tsh, Tsw, Tsh, "", "");
				
			}
			switch (activeobjtool)
			{
				case 0:
					shapeNameX="rectangle";
					break;
				case 1:
					shapeNameX="ellipse";
					break;
				case 2:
					shapeNameX="point";
					break;
				case 3:
					shapeNameX="polygon";
					break;
				case 4:
					shapeNameX="polyline";
					break;
				case 6:
					shapeNameX="image";
					break;
				case 5:
					shapeNameX="text";
					break;
				case 7:
					shapeNameX="copypaste";
					break;
				case 8:
					shapeNameX="eraser";
					break;
			}
			
			nyok.setShape(shapeNameX);
			objgroups.get(selgroup).getObjects().add(nyok);

			curid += 1;

			switch (activeobjtool){

				case 6: 
					newobject=nyok;
					pickTile("newimgobj");
					break;

				case 5:
					newobject=nyok;
					Gdx.input.getTextInput(pnewtextobject, "Set Text", "","");


					break;
				case 3: case 4:
					newobject=nyok;
					newobject.addPoint(0,0);
					mode="newpoly";
					break;


			}

		}
		catch (Exception e)
		{}
		return false;
	}

	private void tapTile(int num,boolean follower)
	{
		if (cammode =="View only") return;
		if (stamp && !roll)
		{
			int widih = endSelect % tilesets.get(seltset).getWidth() - startSelect % tilesets.get(seltset).getWidth()+1;
			int heih = endSelect / tilesets.get(seltset).getWidth() - startSelect / tilesets.get(seltset).getWidth()+1;
			
			for (int yy = 0;yy < heih;yy++)
			{
				for (int xx = 0;xx < widih;xx++)
				{
					if ((num + xx + (yy * Tw)) < Th * Tw)
					{
						if ((num + xx + (yy * Tw)) % Tw >= num % Tw)
						{

							int nyum=num + xx + (yy * Tw);
							long oi = (long) curspr + xx + (yy * tilesets.get(seltset).getWidth());

							Long from = layers.get(seltile).getStr().get(nyum);
							int tzet = layers.get(seltile).getTset().get(nyum);
							int tzeto = seltset;
							
							layerhistory lh2=new layerhistory(follower,from,oi,nyum,seltile,tzet,tzeto);

							if(from!=oi){
								undolayer.add(lh2);
								redolayer.clear();
							
							layers.get(seltile).getStr().set(nyum, oi);
							layers.get(seltile).getTset().set(nyum, seltset);
							follower=true;
							}
						}
					}

				}
			}
			//layers.get(seltile).getStr().set(tilesets.get(seltset).getFirstgid()-1, (long) curspr);
		}
		else
		{

			
			
			String hex=Long.toHexString(curspr);
			String trailer="00000000" + hex;
			hex = trailer.substring(trailer.length() - 8);


			String spc="";

			switch (rotate)
			{
				case 0://"20"://diagonal flip
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
					spc = "40";
					break;
				case 6://
					spc = "20";
					break;
			}
			long oi = Long.decode("#" + spc + hex.substring(2));
			switch (activetool){
				case 1:
					oi = 0;
				case 0:case 4:
					cue("tileclick");
					long noi = Long.decode("#" + 00 + hex.substring(2))-tilesets.get(seltset).getFirstgid();
					Long from = layers.get(seltile).getStr().get(num);
					int tzet = layers.get(seltile).getTset().get(num);
					
					layerhistory lh2=new layerhistory(follower,from,oi,num,seltile,tzet,seltset);
					if(from!=oi){
						undolayer.add(lh2);
						redolayer.clear();
					}
					layers.get(seltile).getStr().set(num, oi);
					layers.get(seltile).getTset().set(num, seltset);
					tile t =null;
					
					for (int k=0;k<tilesets.get(seltset).getTiles().size();k++)
					{
						if (tilesets.get(seltset).getTiles().get(k).getTileID()==noi)
						{
							t =tilesets.get(seltset).getTiles().get(k);
						}
					}
					if (t==null) return;
					int aa[]=null;
					if (t.isTerrain()) aa=t.getTerrain();
					
					if (t.isTerrain() && t.isCenter())
					{
						
						for (int i=0;i<9;i++)
						{
							int gogo=num;
							switch (i)
							{
								case 0:
									if (num <= Tw || num % Tw==0) continue;
									gogo=num-Tw-1;
									break;
								case 1:
									if (num <= Tw) continue;
									gogo=num-Tw;
									break;
								case 2:
									if (num <= Tw || num % Tw==Tw-1) continue;
									gogo=num-Tw+1;
									break;
								case 3:
									if (num <= 1 || num % Tw==0) continue;
									gogo=num-1;
									break;
								case 4:
									if (num >= Tw*Th || num % Tw==Tw-1) continue;
									gogo=num+1;
									break;
								case 5:
									if (num >= Tw*Th-Tw|| num % Tw==0) continue;
									gogo=num+Tw-1;
									break;
								case 6:
									if (num >= Tw*Th-Tw) continue;
									gogo=num+Tw;
									break;
								case 7:
									if (num >= Tw*Th-Tw || num % Tw==Tw-1) continue;
									gogo=num+Tw+1;
									break;
								case 8:
								
									gogo=num;
									break;
							}
							long nyum=layers.get(seltile).getStr().get(gogo);
							hex=Long.toHexString(nyum);
							trailer="00000000" + hex;
							hex = trailer.substring(trailer.length() - 8);
							noi = Long.decode("#" + 00 + hex.substring(2))-tilesets.get(seltset).getFirstgid();
							
							t =null;

							for (int k=0;k<tilesets.get(seltset).getTiles().size();k++)
							{
								if (tilesets.get(seltset).getTiles().get(k).getTileID()==noi)
								{
									t =tilesets.get(seltset).getTiles().get(k);
								}
							}
							if (t==null) continue;
							
							int bb[]=t.getTerrain();
							int cc[]=null;
							if (bb!=null)
							{
								switch (i)
								{
									case 0:
										cc = new int[]{bb[0],bb[1],bb[2],aa[3]};
										break;
									case 1:
										cc = new int[]{bb[0],bb[1],aa[2],aa[3]};
										break;
									case 2:
										cc = new int[]{bb[0],bb[1],aa[2],bb[3]};
										break;
									case 3:
										cc = new int[]{bb[0],aa[1],bb[2],aa[3]};
										break;
									case 4:
										cc = new int[]{aa[0],bb[1],aa[2],bb[3]};
										break;
									case 5:
										cc = new int[]{bb[0],aa[1],bb[2],bb[3]};
										break;
									case 6:
										cc = new int[]{aa[0],aa[1],bb[2],bb[3]};
										break;
									case 7:
										cc = new int[]{aa[0],bb[1],bb[2],bb[3]};
										break;
									case 8:
										cc = new int[]{aa[0],aa[1],aa[2],aa[3]};
										break;
								}
								java.util.List<Integer> lint = new ArrayList<Integer>();
								
								for(int u=0;u<tilesets.get(seltset).getTiles().size();u++)
								{
									tile x=tilesets.get(seltset).getTiles().get(u);
									if (x.getTerrainString().equalsIgnoreCase(cc[0]+","+cc[1]+","+cc[2]+","+cc[3]))
									{
										lint.add(u);
									}
								}
								if (lint.size()>0){
								tile y=tilesets.get(seltset).getTiles().get(lint.get((int) (Math.random()*lint.size())));
								
									
									from = layers.get(seltile).getStr().get(gogo);
									tzet = layers.get(seltile).getTset().get(gogo);
									lh2=new layerhistory(true,from,(long) y.getTileID() + tilesets.get(seltset).getFirstgid(),gogo,seltile,tzet,seltset);
									if(from!=(long) y.getTileID() + tilesets.get(seltset).getFirstgid()){
										undolayer.add(lh2);
										redolayer.clear();
									}
								
								layers.get(seltile).getStr().set(gogo, (long) y.getTileID() + tilesets.get(seltset).getFirstgid());
								
								}
							
								
							}
						}
						
						
					}
					break;

				case 2:
					if (roll) activetool=0;
					oi = Long.decode("#" + spc + hex.substring(2));
					from = layers.get(seltile).getStr().get(num);
					tzet = layers.get(seltile).getTset().get(num);
					
					lh2=new layerhistory(false,from,oi,num,seltile,tzet,seltset);

					undolayer.add(lh2);
					redolayer.clear();

					//layers.get(seltile).getStr().set(num, oi);

					fillthis(num,oi,from,0);
					break;
				case 3:
					//status(mapstartSelect+"/"+mapendSelect,5);
					int widih = mapendSelect % Tw - mapstartSelect % Tw ;
					int heih = mapendSelect / Tw - mapstartSelect / Tw ;
					boolean followe=false;
					int numa=mapstartSelect;
					switch (viewMode)
					{
						case 0://stack
							jon = 0;joni = seltile;
							break;
						case 1://single
							jon = seltile;joni = seltile;
							break;
						case 2://all
							jon = 0;joni = layers.size() - 1;
							break;
					}

					

					for (int jo=jon;jo <= joni;jo++){
					
					for (int yy = 0;yy <= heih;yy++)
					{
						for (int xx = 0;xx <= widih;xx++)
						{
							if ((numa + xx + (yy * Tw)) < Th * Tw)
							{
								if ((numa + xx + (yy * Tw)) % Tw >= numa % Tw)
								{
									
									 int nyum= num + xx + (yy * Tw); //num is correct
									 if ((int)nyum/Tw!=(int)(num+(yy*Tw))/Tw) continue;
									 if (nyum >= Tw*Th) continue;
									 int orinyum= mapstartSelect + xx + (yy * Tw);
									 oi= layers.get(jo).getStr().get(orinyum);
									 int oritzet = layers.get(jo).getTset().get(orinyum);

									 from = layers.get(jo).getStr().get(nyum);
									 tzet = layers.get(jo).getTset().get(nyum);

									 lh2=new layerhistory(followe,from,oi,nyum,jo,tzet,oritzet);

									 if(from!=oi){
									 undolayer.add(lh2);
									 redolayer.clear();
										 layers.get(jo).getStr().set(nyum, oi);
										 layers.get(jo).getTset().set(nyum, oritzet);
										 followe=true;
									 }

									
									 
								}
							}

						}
					}
					
					}
			}

		}
	}

	private boolean tapped(Vector3 touch2,gui gui)
	{
		float x=0,y=0,w=0,h=0;
		if (landscape){
			x=gui.getXl();
			y=gui.getYl();
			w=gui.getWl();
			h=gui.getHl();
		}else{
			x=gui.getX();
			y=gui.getY();
			w=gui.getW();
			h=gui.getH();
		}
		
		if (!landscape)
		{
			if (touch2.y > y/100*ssy && touch2.y < h/100*ssy && touch2.x > x/100*ssx && touch2.x < w/100*ssx)
			{
				return true;
			}
		}else
		{
			if (touch2.y > y/100*ssx && touch2.y < h/100*ssx && touch2.x > x/100*ssy && touch2.x < w/100*ssy)
			{
				return true;
			}
		}
		return false;
	}
	private boolean tapWorldMenu(Vector3 touch2)
	{
		if (cammode=="View only") {
			if (tapped(touch2,gui.center))
			{
				resetcam(true);
				cammode="";
				return true;
			}
			
			if (tapped(touch2,gui.screenshot))
			{
				takingss=true;
				//Gdx.input.vibrate(100);
				
				com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task(){
					@Override
					public void run(){
						//Gdx.input.vibrate(200);
						screenshot();
						takingss=false;
					}
				},1f);
				
			}
			return true;
		}
		
		//layer selection (top mid)
		if (tapped(touch2,gui.layer))
		{
			if (mode == "tile")
			{
				seltile += 1;
				if (layers.size() == seltile)
				{
					seltile = 0;
				}
				return true;
			}
			else if (mode=="object")
			{
				selgroup += 1;
				if (objgroups.size() == selgroup)
				{
					selgroup = 0;
				}
				return true;
			}
		}
		
		if (tapped(touch2,gui.layerpick))
		{
			if (mode == "tile")
			{
			loadList("layer");
			return true;
			}
		}
		
		//tile/object selector (top left)
		if (tapped(touch2,gui.mode))
		{
			if (mode == "tile")
			{
				mode = "object";
				return true;
			}
			else if (mode=="object")
			{
				mode = "tile";
				return true;
			}
		}
		
		if (tapped(touch2,gui.center))
		{
			resetcam(true);
			return true;
		}

		if (autotiles.size()>0){
		//autotile
		if (tapped(touch2,gui.autotile))
		{
			if (mode == "tile")
			{
				msgbox(z.longpress);
				//runAutoTiles();
				return true;
			}
		}
		
		//select auto tile
		if (tapped(touch2,gui.autopicker))
		{
			if (mode == "tile")
			{
				loadAutotileList();
				return true;
			}
		}
		}
		if (mode == "tile"){
			if (tapped(touch2,gui.tool1)) {activetool=0;return true;}
			if (tapped(touch2,gui.tool2)) {activetool=1;return true;}
			if (tapped(touch2,gui.tool3)) {activetool=2;return true;}
			if (tapped(touch2,gui.tool4)) {activetool=3;return true;}
			if (tapped(touch2,gui.tool5)) {activetool=4;return true;}
		}
		
		//tool selector (btm right)
		if (tapped(touch2,gui.tool))
		{
			
			if (mode == "object")
			{
				magnet+=1;
				if (magnet>=2) magnet=0;
				String nfo= "0";
				switch (magnet)
				{
					case 0:
						nfo = z.free; break;
					case 1:
						nfo = z.lock; break;
				}
				magnetName=nfo;
				return true;
			}
			
			if (mode == "newpoly")
			{
				if(newobject.getPointsSize()==1){
					newobject.setShape("point");
				}
				if(newobject.getPointsSize()==2){
					newobject.setShape("polyline");
				}
				mode="object";
				return true;
			}

		}

		//undo
		if (tapped(touch2,gui.undo))
		{
		
			
			if (mode == "tile"){
				try{
					
				if (undolayer.size()>0){
					layerhistory lh;
					long histcount=0;
					for (int n=undolayer.size()-1;n>=0;n--){
						
						lh= undolayer.get(n);
						layers.get(lh.getLayer()).getStr().set(lh.getLocation(),lh.getFrom());
						layers.get(lh.getLayer()).getTset().set(lh.getLocation(),lh.getoldTset());
						redolayer.add(lh);
						histcount++;
						if (!lh.isFollower()){
							java.util.List<layerhistory> templist=new ArrayList<layerhistory>();
							
							for (int t=0;t<undolayer.size()-histcount;t++)
							{
								templist.add(undolayer.get(t));
							}
							undolayer = new ArrayList<layerhistory>();
							undolayer=templist;
							break;
						}
					}
				}
				}catch(Exception e)
				{
					ErrorBung(e,"undo.txt");
				}
				return true;
			}
			if (mode == "newpoly"){
				
				newobject.undoPoint();
				return true;
			}

		}
		
		//redo
		if (tapped(touch2,gui.redo))
		{
			
			if (mode == "tile"){
				
				if (redolayer.size()>0){
					boolean pertamax =true;
					long histcount=0;
					for (int n=redolayer.size()-1; n>=0;n--){

						layerhistory lh= redolayer.get(n);

						if (!pertamax){
							if (!lh.isFollower()){
								
									java.util.List<layerhistory> templist=new ArrayList<layerhistory>();

									for (int t=0;t<redolayer.size()-histcount;t++)
									{
										templist.add(redolayer.get(t));
									}
									redolayer = new ArrayList<layerhistory>();
									redolayer=templist;
								
								
								break;
							}
						}
						pertamax=false;
						layers.get(lh.getLayer()).getStr().set(lh.getLocation(),lh.getTo());
						layers.get(lh.getLayer()).getTset().set(lh.getLocation(),lh.getNewtset());
						//status(lh.getTset()+"",5);
						undolayer.add(lh);
						histcount++;
						//redolayer.remove(lh);
					}
				}
				return true;
			}

		}

		//view mode selector (top roght)
		if (tapped(touch2,gui.viewmode))
		{

			if (mode == "tile")
			{
				viewMode += 1; 
				if (viewMode == 3)
				{viewMode = 0;}
				String sss="";
				switch (viewMode)
				{
					case 0://stack
						sss = z.stack;
						break;
					case 1://single
						sss = z.single;
						break;
					case 2://all
						sss = z.all;
						break;
				}
				viewModeName=sss;
				return true;
			}
			else if (mode=="object")
			{
				objviewMode += 1; 
				if (objviewMode == 2)
				{objviewMode = 0;}
				String sss="";
				switch (objviewMode)
				{
					case 0://stack
						sss = z.all;
						break;
					case 1://single
						sss = z.single;
						break;
				}
				objViewModeName=sss;
				return true;
			}

		}

		//select tile button
		if (tapped(touch2,gui.picker))

		{
			if (mode == "tile")
			{
				stamp=false;
				kartu = "tile";
				tilePicker="";
				cue("tilepick");
				return true;
			}


		}
		
		//object tool selector
		if (tapped(touch2,gui.objectpickerleft))

		{
			if (mode == "object")
			{
				activeobjtool-=1;
				if (activeobjtool<0) activeobjtool=8;
				String nfo="";
				switch (activeobjtool)
				{
					case 0:
						nfo = z.rectangle; break;
					case 1:
						nfo = z.ellipse; break;
					case 2:
						nfo = z.point; break;
					case 3:
						nfo = z.polygon; break;
					case 4:
						nfo = z.polyline; break;
					case 5:
						nfo = z.text; break;
					case 6:
						nfo = z.image; break;
					case 7:
						nfo = z.copypaste; break;
					case 8:
						nfo = z.eraser; break;
				}
				shapeName=nfo;
				return true;
			}
		}

		if (tapped(touch2,gui.objectpickerright))

		{
			if (mode == "object")
			{
				
				if (touch2.x>240-30) activeobjtool+=1;
				if (activeobjtool>=9) activeobjtool=0;
				
				String nfo="";
				switch (activeobjtool)
				{
					case 0:
						nfo = z.rectangle; break;
					case 1:
						nfo = z.ellipse; break;
					case 2:
						nfo = z.point; break;
					case 3:
						nfo = z.polygon; break;
					case 4:
						nfo = z.polyline; break;
					case 5:
						nfo = z.text; break;
					case 6:
						nfo = z.image; break;
					case 7:
						nfo = z.copypaste; break;
					case 8:
						nfo = z.eraser; break;
				}
				shapeName=nfo;
				return true;
			}
		}
		//rotation
		if (tapped(touch2,gui.rotation))

		{
			if (mode=="tile")
			{
				
				rotate += 1;
				if (rotate == 7)
				{
					rotate = 0;
				}
				String nfo= "0";
				switch (rotate)
				{
					case 0:
						nfo = "0"; break;
					case 1:
						nfo = "90"; break;
					case 2:
						nfo = "180"; break;
					case 3:
						nfo = "270"; break;
					case 4:
						nfo = z.x;break;
					case 5:
						nfo = z.y;break;
					case 6:
						nfo = z.x+z.y;break;
				}
				rotationName=nfo;
				return true;
			}
			
		}

		if (tapped(touch2,gui.menu))
		{
			if (mode=="object"||mode=="tile")
			{
				gotoStage(tMenu);
				cue("menu");
				return true;
				
			}
			
		}

		if (tapped(touch2,gui.map))
		{
			if (mode=="object"||mode=="tile")
			{
				gotoStage(tMap);
				cue("map");
				return true;
			}
			
		}
		return false;
	}

	private void cue(String p0)
	{
		if (tutoring)
		{
			if (tutor.getT().get(activetutor).checkcue(p0)==true)
			{
				face.speak(tutor.getT().get(activetutor).getCurrent());
				tutor.getT().get(activetutor).next();
				if (tutor.getT().get(activetutor).isEnded())
				{
					tutoring=false;
				}
			}
		}
		
	}
	
	private Color vis(String p0)
	{
		if (tutoring)
		{
			if (tutor.getT().get(activetutor).checkcue(p0)==true)
			{
				return new Color(0f,1f,0f,1f);
			}
		}
		return null;
	}

	private void screenshot()
	{
		
		
		//status("screenshot?",5);
		int ax=1,ay=Tsh,az=0;
		int bx=Tw*Tsw,by=Th*Tsh+Tsh-1,bz=0;
		Vector3 va = new Vector3(ax,ay,az);
		Vector3 vb = new Vector3(bx,by,bz);
		Vector3 upva = cam.project(va);
		Vector3 upvb = cam.project(vb);
		
		
		FileHandle fh = Gdx.files.absolute(curdir+"/"+curfile.substring(0,curfile.length()-4)+"_map.png");
		Pixmap data=getScreenshot((int) upva.x,(int) upva.y - (int) (upvb.y-upva.y),(int) (upvb.x-upva.x), (int) (upvb.y-upva.y),true);
		//if (layers.size()>2 && tilesets.size()>0){
		//if (layers.get(2).getName().equalsIgnoreCase("Units") && tilesets.get(0).getName().equalsIgnoreCase("units")){
		//data = resizepixmap(data,Tsw*Tw,Tsh*Th);
		//}}
		PixmapIO.writePNG(fh,data);
		
		msgbox(z.screenshotsaved);
	}
	public Pixmap resizepixmap(Pixmap pixmap200,int w,int h)
	{
		Pixmap pixmap100 = new Pixmap(w, h, pixmap200.getFormat());
		pixmap100.drawPixmap(pixmap200,
							 0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
							 0, 0, pixmap100.getWidth(), pixmap100.getHeight()
							 );
		Texture texture = new Texture(pixmap100);
		pixmap200.dispose();
		return pixmap100;
	}
	public Pixmap getScreenshot(int ox, int oy, int ow, int oh, boolean flipY) {
		int bw =Gdx.graphics.getBackBufferWidth();
		int bh =Gdx.graphics.getBackBufferHeight();
		int x=ox;
		int y=oy;
		int w=ow;
		int h=oh;
		
		if (x<0) {x=0;w=w+x;}
		if (w+x>bw) w=bw-x;
		if (y<0) {y=0;h=h+y;}
		if (y+h>bh) h=bh-y;
		
		//status(x+"!"+y+"!"+w+"!"+h+"!"+bw+"!"+bh,10);
		byte[] pixels = ScreenUtils.getFrameBufferPixels(x, y, w, h, flipY);
		
		for(int i = 4; i < pixels.length; i += 4) {
			pixels[i - 1] = (byte) 255;
		}

		Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		return pixmap;
	}
	private void runAutoTiles()
	{
		int autoundolayer=1;
		autoed.clear();
		for (int i=0;i<Tw*Th;i++){
			autoed.add(false);
		}
		
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
		for (int i=0;i<autotiles.size();i++)
		{
			autotile al=autotiles.get(i);
			automate am=new automate();
			am.setObjectfill("null");
			am.setType("null");
			for(int j=0;j<al.getProperties().size();j++)
			{
				
				property p=al.getProperties().get(j);
				if (p.getName().equalsIgnoreCase("type"))
				{
					am.setType(p.getValue());
					continue;
				}
				else if (p.getName().equalsIgnoreCase("source"))
				{
					am.setSource(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("against"))
				{
					am.setAgainst(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("against2"))
				{
					am.setAgainst2(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("against3"))
				{
					am.setAgainst3(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("against4"))
				{
					am.setAgainst4(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("against5"))
				{
					am.setAgainst5(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("beside"))
				{
					am.setBeside(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("sourcelayer"))
				{
					am.setSourcelayer(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("destlayer"))
				{
					am.setDestlayer(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("objectfill"))
				{
					am.setObjectfill(p.getValue());
					continue;
				}
				else if (p.getName().equalsIgnoreCase("objectgroup"))
				{
					am.setObjectgroup(Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("objectname"))
				{
					am.setObjectname(p.getValue());
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bottomright"))
				{
					am.setCell(3,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bottomleft"))
				{
					am.setCell(5,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("topright"))
				{
					am.setCell(10,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("topleft"))
				{
					am.setCell(12,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bottom"))
				{
					am.setCell(7,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("right"))
				{
					am.setCell(11,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("left"))
				{
					am.setCell(13,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("top"))
				{
					am.setCell(14,Integer.parseInt(p.getValue()));
					continue;
				}
				//////
				else if (p.getName().equalsIgnoreCase("topwest"))
				{
					am.setCell(31,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("topeast"))
				{
					am.setCell(32,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("leftnorth"))
				{
					am.setCell(33,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("leftsouth"))
				{
					am.setCell(34,Integer.parseInt(p.getValue()));
					continue;
				}
				//////
				else if (p.getName().equalsIgnoreCase("rightnorth"))
				{
					am.setCell(35,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("rightsouth"))
				{
					am.setCell(36,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bottomwest"))
				{
					am.setCell(37,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bottomeast"))
				{
					am.setCell(38,Integer.parseInt(p.getValue()));
					continue;
				}
				//////
				else if (p.getName().equalsIgnoreCase("center"))
				{
					am.setCell(15,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bigtopleft"))
				{
					am.setCell(16,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bigtopright"))
				{
					am.setCell(17,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bigbottomleft"))
				{
					am.setCell(19,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("bigbottomright"))
				{
					am.setCell(23,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("topleftbottomright"))
				{
					am.setCell(20,Integer.parseInt(p.getValue()));
					continue;
				}
				else if (p.getName().equalsIgnoreCase("toprightbottomleft"))
				{
					am.setCell(25,Integer.parseInt(p.getValue()));
					continue;
				}
				try{
				am.setCell(Integer.parseInt(p.getName()),Integer.parseInt(p.getValue()));
				continue;
				}catch (Exception e)
				{}
				try{
				String[] sdc = p.getName().split(",");
				am.addCoord(Integer.parseInt(sdc[0]),Integer.parseInt(sdc[1]),Integer.parseInt(p.getValue()));
				}catch (Exception e)
				{}
				
			}//properties run
			
			switch (am.getType())
			{
				case "clearobjects":
					try{
					objgroups.get(am.getObjectgroup()).getObjects().clear();
					}catch(Exception e){}
					break;
				case "fill":
					for (int k=0;k<Tw*Th;k++)
					{
						layers.get(am.getDestlayer()).getStr().set(k,(long) am.getSource());
						for (int l=0;l<tilesets.size();l++)
						{
							if (am.getSource()>=tilesets.get(l).getFirstgid() && am.getSource() < tilesets.get(l).getFirstgid()+tilesets.get(l).getTilecount())
							{
								layers.get(am.getDestlayer()).getTset().set(k,l);
								break;
							}
						}
					}
				break;
				case "bitmask":
				for (int k=0;k<Tw*Th;k++)
				{
					if (layers.get(am.getSourcelayer()).getStr().get(k)!=am.getSource()) continue;
					int count=0; int total=Tw*Th;
					Long num1=(long) 0,num2=(long)0,num4=(long)0,num8=(long)0,numa=(long)0,numb=(long)0,numc=(long)0,numd=(long)0;
					if (k-Tw>=0) num1 = layers.get(am.getSourcelayer()).getStr().get(k-Tw);
					if (k-1>=0 && k%Tw!=0) num2 = layers.get(am.getSourcelayer()).getStr().get(k-1);
					if (k+1<total && k%Tw!=Tw-1) num4 = layers.get(am.getSourcelayer()).getStr().get(k+1);
					if (k+Tw<total) num8 = layers.get(am.getSourcelayer()).getStr().get(k+Tw);
					if (k-Tw-1>=0 && k%Tw!=0) numa = layers.get(am.getSourcelayer()).getStr().get(k-Tw-1);
					if (k-Tw+1>=0 && k%Tw!=Tw-1) numb = layers.get(am.getSourcelayer()).getStr().get(k-Tw+1);
					if (k+Tw-1<total && k%Tw!=0) numc = layers.get(am.getSourcelayer()).getStr().get(k+Tw-1);
					if (k+Tw+1<total && k%Tw!=Tw-1) numd = layers.get(am.getSourcelayer()).getStr().get(k+Tw+1);
					
					if (!am.getAgainst(num1)) count+=1;
					if (!am.getAgainst(num2)) count+=2;
					if (!am.getAgainst(num4)) count+=4;
					if (!am.getAgainst(num8)) count+=8;
					
					if (count==15)
					{
						if (am.getAgainst(numa)) count+=1;
						if (am.getAgainst(numb)) count+=2;
						if (am.getAgainst(numc)) count+=4;
						if (am.getAgainst(numd)) count+=8;
					}
					if (am.getBeside()!=0){
						if (count==14){
							if (num2==am.getBeside()) count=31;
							if (num4==am.getBeside()) count=32;
						}
						if (count==13){
							if (num1==am.getBeside()) count=33;
							if (num8==am.getBeside()) count=34;
						}
						if (count==11){
							if (num1==am.getBeside()) count=35;
							if (num8==am.getBeside()) count=36;
						}
						if (count==7){
							if (num2==am.getBeside()) count=37;
							if (num4==am.getBeside()) count=38;
						}
					}
					
					try{
					switch (am.objectfill)
					{
						case "all":
							obj tt=new obj();
							tt.setName(am.getObjectname());
							tt.setX(Tsw*(k%Tw));
							tt.setY(Tsh*(k/Tw));
							tt.setW(Tsw);
							tt.setH(Tsh);
							objgroups.get(am.getObjectgroup()).getObjects().add(tt);
							break;
						case "edge":
							if (count !=15)
							{
								tt=new obj();
								tt.setName(am.getObjectname());
								tt.setX(Tsw*(k%Tw));
								tt.setY(Tsh*(k/Tw));
								tt.setW(Tsw);
								tt.setH(Tsh);
								objgroups.get(am.getObjectgroup()).getObjects().add(tt);
							}
							break;
					}
					}catch(Exception e){
						ErrorBung(e,"autobug1.txt");
						
					}
					try{
					if (!autoed.get(k))
					{
					layers.get(am.getDestlayer()).getStr().set(k,(long) am.getCell(count));
					for (int l=0;l<tilesets.size();l++)
					{
						if (am.getCell(count)>=tilesets.get(l).getFirstgid() && am.getCell(count) < tilesets.get(l).getFirstgid()+tilesets.get(l).getTilecount())
						{
							layers.get(am.getDestlayer()).getTset().set(k,l);
							break;
						}
					}
						if (count !=15 && am.getCell(count)!=0) autoed.set(k,true);
					}
					}catch (Exception e){
						ErrorBung(e,"autobug2.txt");
						
					}
				}
				break;
				case "completion":
					for (int k=0;k<Tw*Th;k++)
					{
						
						if (layers.get(am.getSourcelayer()).getStr().get(k)!=am.getSource()) continue;
						try{
							switch (am.objectfill)
							{
								case "single":
									obj tt=new obj();
									tt.setName(am.getObjectname());
									tt.setX(Tsw*(k%Tw));
									tt.setY(Tsh*(k/Tw));
									tt.setW(Tsw);
									tt.setH(Tsh);
									objgroups.get(am.getObjectgroup()).getObjects().add(tt);
									break;
							}
						}catch(Exception e){
							ErrorBung(e,"autobug3.txt");
							
						}
						for (int m=0;m<am.getX().size();m++)
						{
							int tgt=k+am.getX().get(m)+(am.getY().get(m)*Tw);
							if (am.getX().get(m)<0 && k%Tw<tgt%Tw) continue;
							if (am.getX().get(m)>0 && k%Tw>tgt%Tw) continue;
							if (tgt<0 || tgt>=Tw*Th) continue;
							
							layers.get(am.getDestlayer()).getStr().set(tgt,(long) am.getZ().get(m));
							try{
							switch (am.objectfill)
							{
								case "all":
									obj tt=new obj();
									tt.setName(am.getObjectname());
									tt.setX(Tsw*(tgt%Tw));
									tt.setY(Tsh*(tgt/Tw));
									tt.setW(Tsw);
									tt.setH(Tsh);
									objgroups.get(am.getObjectgroup()).getObjects().add(tt);
									break;
							}
							}catch(Exception e){
								ErrorBung(e,"autobug4.txt");
							}
							for (int l=0;l<tilesets.size();l++)
							{
								if (am.getZ().get(m)>=tilesets.get(l).getFirstgid() && am.getZ().get(m) < tilesets.get(l).getFirstgid()+tilesets.get(l).getTilecount())
								{
									layers.get(am.getDestlayer()).getTset().set(tgt,l);
									break;
								}//if
							}//l
						}//m
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
		
	}

	public void fillthisold(int num, long oi,long from, int direction){
		
		if (num < 0) return;
		if (num>=Tw*Th) return;
		switch (direction){
			case 1://up
				break;
			case 2://down
				break;
			case 3://left
			if (num % Tw==0) return;
				break;
			case 4://rigjt
			if (num % Tw==1) return;
				break;
		}
	
		
		long fromnew = layers.get(seltile).getStr().get(num);
		if (from!=fromnew) return;
		if(oi==from) return;
		
		int tzet = layers.get(seltile).getTset().get(num);
		
		layerhistory lh2=new layerhistory(true,fromnew,oi,num,seltile,tzet,seltset);
		if(fromnew!=oi){
			undolayer.add(lh2);
			redolayer.clear();
		}
		
		layers.get(seltile).getStr().set(num, oi);
		
		fillthis(num-Tw,oi,from,1);
		fillthis(num+Tw,oi,from,2);
		fillthis(num-1,oi,from,3);
		fillthis(num+1,oi,from,4);
		
	}
	
	public void fillthis(int numa, long oia,long froma, int directiona){
		java.util.Queue<floodfill> q= new LinkedList<floodfill>();
		q.add(new floodfill(numa,oia,froma,directiona));
		while (!q.isEmpty()){
			floodfill n = q.remove();
			int num=n.getNum();
			long oi=n.getOi();
			long from=n.getFrom();
			int direction=n.getDirection();
			
			if (num < 0) continue;
			if (num>=Tw*Th) continue;
			switch (direction){
				case 1://up
					break;
				case 2://down
					break;
				case 3://left
					if (num % Tw==(Tw-1)) continue;
					break;
				case 4://rigjt
					if (num % Tw==0) continue;
					break;
			}


			long fromnew = layers.get(seltile).getStr().get(num);
			if (from!=fromnew) continue;
			if(oi==from) continue;

			int tzet = layers.get(seltile).getTset().get(num);

			layerhistory lh2=new layerhistory(true,fromnew,oi,num,seltile,tzet,seltset);
			if(fromnew!=oi){
				undolayer.add(lh2);
				redolayer.clear();
			}

			layers.get(seltile).getStr().set(num, oi);
			layers.get(seltile).getTset().set(num, seltset);
			
			q.add(new floodfill(num-Tw,oi,from,1));
			q.add(new floodfill(num+Tw,oi,from,2));
			q.add(new floodfill(num-1,oi,from,3));
			q.add(new floodfill(num+1,oi,from,4));
		}
		

	}
	
	@Override
	public boolean longPress(float p1, float p2)
	{
		if (loadingfile) return true;
		
		//Gdx.input.vibrate(50);
		Vector3 touch = new Vector3();
		cam.unproject(touch.set(p1, p2, 0));
		Vector3 touch2 = new Vector3();
		uicam.unproject(touch2.set(p1, p2, 0));
		Vector3 touch3 = new Vector3();
		tilecam.unproject(touch3.set(p1, p2, 0));
		
		if (kartu == "world") 
		{
			//autotile
			if (tapped(touch2,gui.autotile))
			{
				if (mode == "tile"||mode=="object")
				{
					//msgbox("Longpress to run Auto tiles");
					runAutoTiles();
					return true;
				}
			}
			if (mode=="tile")
			{
				

				
				int ae= (int) touch.x;
				int ab = (int) touch.y;
				
				if (tapped(touch2,gui.tool5)) 
				{
					brushsize+=1;
					if (brushsize==2) brushsize=0;
					return true;
				}
				
				
				if (tapped(touch2,gui.map))
				{
					if (mode=="object"||mode=="tile")
					{
						saveMap(curdir+"/"+curfile);
						msgbox(z.yourmaphasbeensaved);
						return true;
					}

				}
				
				if (tapped(touch2,gui.menu))
				{
					if (mode=="object"||mode=="tile")
					{
						FileDialog(z.opentmxfile,"open","file",new String[]{".tmx"},null);
						return true;
					}

				}
				
				if (tapped(touch2,gui.rotation))

				{
					if (mode=="tile")
					{

						rotate -= 1;
						if (rotate == -1)
						{
							rotate = 6;
						}
						String nfo= "0";
						switch (rotate)
						{
							case 0:
								nfo = "0"; break;
							case 1:
								nfo = "90"; break;
							case 2:
								nfo = "180"; break;
							case 3:
								nfo = "270"; break;
							case 4:
								nfo = z.x;break;
							case 5:
								nfo = z.y;break;
							case 6:
								nfo = z.x+z.y;break;
						}
						rotationName=nfo;
						return true;
					}

				}
				
				if (tapped(touch2,gui.layer))
				{
					loadList("layer");
					return true;
				}
				if (tapped(touch2,gui.viewmode))
				{
					uiAnim();
					return true;
				}
				if (tapped(touch2,gui.mode))
				{
					Gdx.input.getTextInput(pNewLayerSC, z.addnew, z.layer +" " + (layers.size() + 1),"");
					return true;
				}
				if (tapped(touch2,gui.picker))
				{
					FileDialog(z.selectfile,"quickaddtset","file",new String[]{".tsx",".png",".jpg",".jpeg",".bmp",".gif"},nullTable);
					return true;
				}
					if (tilesets.size()==0) return true;
					if (activetool==2) activetool=4;
					
				roll = true;

					
						if (orientation.equalsIgnoreCase("orthogonal"))
						{
							if (touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw)
							{
							mapstartSelect = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
							}
						}
						else if (orientation.equalsIgnoreCase("isometric"))
						{
							float closest=9999;
							for (int i=0;i<Tw*Th;i++)
							{
								int offsetx=0,offsety=0;
								xpos = i % Tw;
								ypos = i / Tw;
								offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
								offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
								int drawx=xpos*Tsw-offsetx;
								int drawy=-ypos*Tsh-offsety;							
								int x=ae-drawx;
								int y=ab-drawy;		
								int Tsws=Tsw/2;
								int Tshs=Tsh/2;
								float dx=Math.abs(x-Tsws);
								float dy=Math.abs(y-Tshs);
								if (dx/Tsws+dy/Tshs<closest)
								{
									mapstartSelect=i;
									closest=dx/Tsws+dy/Tshs;
								}
							}
						}
						//mapstartSelect = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
					
						if (activetool==4) 
						{
							tapTile(mapstartSelect,false);
							if (brushsize>0)
							{
							int num=mapstartSelect;
							if((num-1)%Tw!=Tw-1 && num-1>=0) tapTile(num-1,true);
							if((num+1)%Tw!=0 && num+1<Tw*Th) tapTile(num+1,true);
							if(num>Tw) tapTile(num-Tw,true);
							if(num<Tw*(Th-1)) tapTile(num+Tw,true);

							if((num-1)%Tw!=Tw-1 && num>Tw) tapTile(num-Tw-1,true);
							if((num+1)%Tw!=0 && num>Tw) tapTile(num-Tw+1,true);
							if(num%Tw!=0 && num<Tw*(Th-1)) tapTile(num+Tw-1,true);
							if(num%Tw!=Tw-1 && num<Tw*(Th-1)) tapTile(num+Tw+1,true);
							}
						}
						mapendSelect = mapstartSelect;
						mapinitialSelect = mapstartSelect;
					
				
			}
			else if (mode=="object")
			{
				if (tapped(touch2,gui.layer))
				{
					loadList("obj");
					return true;
				}
				if (tapped(touch2,gui.mode))
				{
					Gdx.input.getTextInput(pNewObjLayerSC, z.addnew, z.object+" " + (objgroups.size() + 1),"");
					return true;
				}
				if (tapped(touch2,gui.objectpicker))
				{
					//status("Object picker",1);
					return true;
				}
				longpressobj(p1,p2);
				return true;
			}
		}
			
		else if (kartu == "tile")
		{
			if (tapped(touch2,gui.tilesets))
			{
				loadList("tset");
				return true;
			}
			stamp = true;
			int ae= (int) touch3.x;
			int ab = (int) touch3.y;
			int Tsh=tilesets.get(seltset).getTileheight();
			int Tsw=tilesets.get(seltset).getTilewidth();
			if (touch3.y < Tsh && touch3.y > -Tsh * tilesets.get(seltset).getHeight() + Tsh && touch3.x > 0 && touch3.x < Tsw * tilesets.get(seltset).getWidth())
			{
				startSelect = (tilesets.get(seltset).getWidth() * ((-ab + Tsh) / Tsh) + (ae / Tsw));
				endSelect = startSelect;
				initialSelect = startSelect;
			}
		}
		else if (kartu == "pickanim")
		{
		switch (tilePicker)
		{
		case "massprops":
				stamp = true;
				int ae= (int) touch3.x;
				int ab = (int) touch3.y;
				int Tsh=tilesets.get(selTsetID).getTileheight();
				int Tsw=tilesets.get(selTsetID).getTilewidth();
				if (touch3.y < Tsh && touch3.y > -Tsh * tilesets.get(selTsetID).getHeight() + Tsh && touch3.x > 0 && touch3.x < Tsw * tilesets.get(selTsetID).getWidth())
				{
					startSelect = (tilesets.get(selTsetID).getWidth() * ((-ab + Tsh) / Tsh) + (ae / Tsw));
					endSelect = startSelect;
					initialSelect = startSelect;
				}
				break;
			/*
		case "terraineditor":
			if (ts.getTerrains().size()==0) return true;
			tiles = ts.getTiles();
			tile t=null;
			int dst=num - ts.getFirstgid();
			for (int n =0;n < tiles.size();n++)
			{
				if (tiles.get(n).getTileID()== dst){
					t=tiles.get(n);
					break;
				}
			}

			if (t==null)
			{
				tile tt = new tile();
				tt.setTileID(dst);
				tiles.add(tt);
				t=tiles.get(tiles.size()-1);
			}
			int[] cn=t.getTerrain();

			int a=cn[0],b=cn[1],c=cn[2],d=cn[3];
			int e =tilesets.get(selTsetID).getSelTerrain();

			//ada tile kosong
			if ((a==-1) ||  (b==-1) || (c==-1) || (d==-1))
			{
				if (a!=e&&b!=e&&c!=e&&d!=e)
				{
					if (a==-1) a=e;
					if (b==-1) b=e;
					if (c==-1) c=e;
					if (d==-1) d=e;
					t.setTerrain(a,b,c,d);
					return true;
				}
			}

			//kalau tidak ada yang kosong, takeover semua
			if ((a>=0) && (b>=0) && (c>=0) && (d>=0))
			{
				if (!(a==e&&b==e&&c==e&&d==e))
				{
					a=e;b=e;c=e;d=e;
					t.setTerrain(a,b,c,d);
					return true;
				}

			}

			//sisanya
			if ((a==-1) && (b==-1) && (c==-1) && (d==-1))
			{
				a=e;b=e;c=e;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}

			if ((a==e) && (b==e) && (c==e) && (d==e))
			{
				a=-1;b=-1;c=-1;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}

			if ((a==-1) && (b==-1) && (c==-1) && (d==e))
			{
				a=-1;b=-1;c=e;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==-1) && (c==e) && (d==e))
			{
				a=-1;b=-1;c=e;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==-1) && (c==e) && (d==-1))
			{
				a=e;b=-1;c=e;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==-1) && (c==e) && (d==-1))
			{
				a=e;b=-1;c=-1;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==-1) && (c==-1) && (d==-1))
			{
				a=e;b=e;c=-1;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==e) && (c==-1) && (d==-1))
			{
				a=-1;b=e;c=-1;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==e) && (c==-1) && (d==-1))
			{
				a=-1;b=e;c=-1;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==e) && (c==-1) && (d==e))
			{
				a=e;b=e;c=e;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==e) && (c==e) && (d==-1))
			{
				a=e;b=e;c=-1;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==e) && (c==-1) && (d==e))
			{
				a=-1;b=e;c=e;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==e) && (c==e) && (d==e))
			{
				a=e;b=-1;c=e;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==-1) && (c==e) && (d==e))
			{
				a=e;b=-1;c=-1;d=e;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==e) && (b==-1) && (c==-1) && (d==e))
			{
				a=-1;b=e;c=e;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			if ((a==-1) && (b==e) && (c==e) && (d==-1))
			{
				a=-1;b=-1;c=-1;d=-1;
				t.setTerrain(a,b,c,d);
				return true;
			}
			*/
		}
		
		
		}

		return false;
	}

	private boolean longpressobj(float p1, float p2)
	{
		Vector3 touch = new Vector3();
		cam.unproject(touch.set(p1, p2, 0));

					for (int i=0;i < objgroups.size();i++)
					{
						if (objgroups.get(i).getObjects().size() > 0)
						{
							if (i == selgroup || objviewMode == 0)
							{
								for (int j=0;j < objgroups.get(i).getObjects().size();j++)
								{
									obj ox= objgroups.get(i).getObjects().get(j);
									float offx=0,offy=0;
									if (ox.getShape()!=null)
									{
										switch (ox.getShape())
										{

											case "point":
											case "polygon":
											case "polyline":
												offx=ox.getWa();
												offy=ox.getHa();
											default:



												break;
										}
									}
									if (touch.y <= -(ox.getY() - ox.getH())-ox.getH()+Tsh+offy && touch.y >= -(ox.getY() - ox.getH()) - ox.getH()-ox.getH()+Tsh+offy  && touch.x >= ox.getX()-offx && touch.x <= ox.getX() + ox.getW()-offx)
									{
										if(ox.getShape()=="image")
										{
											newobject=ox;
											pickTile("newimgobj");
											return true;
										}
										if(ox.getShape()=="text")
										{
											newobject=ox;
											Gdx.input.getTextInput(pnewtextobject, z.addnew, ox.getText(),"");
											return true;
										}
										if(ox.getShape()=="polygon"||ox.getShape()=="polyline")
										{
											newobject=ox;
											mode="newpoly";
											return true;
										}
										
									}//if atas
								}
							}
						}
					}
				
		
		return false;
	}

	@Override
	public boolean fling(float p1, float p2, int p3)
	{
		if (loadingfile) return true;
		if (nofling>0) return true;
		
		velx=p1;
		vely=p2;
		if (velx>4000) velx=4000;
		if (velx<-4000) velx=-4000;
		if (vely>4000) vely=4000;
		if (vely<-4000) vely=-4000;
		//status(p1+"/"+p2+"/"+p3,5);
		
		return false;
	}

	@Override
	public boolean pan(float p1, float p2, float p3, float p4)
	{
		if (loadingfile) return true;
		//status(p1+"/"+p2+"/"+p3+"/"+p4,5);
		if (stamp && (kartu == "tile" || (kartu=="pickanim" && tilePicker=="massprops")))
		{
			Vector3 touch = new Vector3();
			tilecam.unproject(touch.set(p1, p2, 0));
			int ae= (int) touch.x;
			int ab = (int) touch.y;
			int posx = 0;int posy = 0;
			int initx = 0;int inity = 0;
			Integer num=-1;
			int usedtset;
			if (kartu=="tile")
			{
				usedtset=seltset;
			}else
			{
				usedtset=selTsetID;
			}
			int Tha=tilesets.get(usedtset).getHeight();
			int Twa=tilesets.get(usedtset).getWidth();
			int Tsh=tilesets.get(usedtset).getTileheight();
			int Tsw=tilesets.get(usedtset).getTilewidth();
			if (touch.y < Tsh && touch.y > -Tsh * Tha + Tsh && touch.x > 0 && touch.x < Tsw * Twa)
			{
				num = (Twa * ((-ab + Tsh) / Tsh) + (ae / Tsw));
				posx=ae/Tsw;
				posy=((-ab + Tsh) / Tsh);
				initx=initialSelect % Twa;
				inity=initialSelect /Twa;
			}
			if (num==-1) return true;
			//kanan bawah
			if (posx>=initx && posy >=inity)
			{
				startSelect=initialSelect;
				endSelect = num;
			}
			//kanan atas
			if (posx>=initx && posy <inity)
			{
				startSelect=posy*Twa+initx;
				endSelect = inity*Twa+posx;
			}
			//kiri bawah
			if (posx<initx && posy >=inity)
			{
				startSelect=inity*Twa+posx;
				endSelect = posy*Twa+initx;
			}
			//kiri atas
			if (posx<initx && posy <inity)
			{
				startSelect=num;
				endSelect = initialSelect;
			}
			return true;
		}
		if (roll)
		{
			Vector3 touch = new Vector3();
			cam.unproject(touch.set(p1, p2, 0));
			int ae= (int) touch.x;
			int ab = (int) touch.y;
			int posx = 0;int posy = 0;
			int initx = 0;int inity = 0;
			Integer num=-1;
			
				if (orientation.equalsIgnoreCase("orthogonal"))
				{
					if (touch.y < Tsh && touch.y > -Tsh * Th + Tsh && touch.x > 0 && touch.x < Tsw * Tw)
					{
					num = (Tw * ((-ab + Tsh) / Tsh) + (ae / Tsw));
					}
				}
				else if (orientation.equalsIgnoreCase("isometric"))
				{
					float closest=9999;
					for (int i=0;i<Tw*Th;i++)
					{
						int offsetx=0,offsety=0;
						xpos = i % Tw;
						ypos = i / Tw;
						offsetx=(xpos*Tsw/2)+(ypos*Tsw/2);
						offsety=(xpos*Tsh/2)-(ypos*Tsh/2);
						int drawx=xpos*Tsw-offsetx;
						int drawy=-ypos*Tsh-offsety;							
						int x=ae-drawx;
						int y=ab-drawy;		
						int Tsws=Tsw/2;
						int Tshs=Tsh/2;
						float dx=Math.abs(x-Tsws);
						float dy=Math.abs(y-Tshs);
						if (dx/Tsws+dy/Tshs<closest)
						{
							num=i;
							closest=dx/Tsws+dy/Tshs;
						}
					}
				}
				if (num ==-1) return true;
				if (activetool==4 & num !=-1)
				{
					
					tapTile(num,true);
					if (brushsize>0){
					if((num-1)%Tw!=Tw-1 && num-1>=0) tapTile(num-1,true);
					if((num+1)%Tw!=0 && num+1<Tw*Th) tapTile(num+1,true);
					if(num>Tw) tapTile(num-Tw,true);
					if(num<Tw*(Th-1)) tapTile(num+Tw,true);
					
					if((num-1)%Tw!=Tw-1 && num>Tw) tapTile(num-Tw-1,true);
					if((num+1)%Tw!=0 && num>Tw) tapTile(num-Tw+1,true);
					if(num%Tw!=0 && num<Tw*(Th-1)) tapTile(num+Tw-1,true);
					if(num%Tw!=Tw-1 && num<Tw*(Th-1)) tapTile(num+Tw+1,true);
					}
					
					
					
					return true;
				}
				posx=num%Tw;
				posy=num/Tw;
				initx=mapinitialSelect % Tw;
				inity=mapinitialSelect /Tw;
			
			//kanan bawah
			if (posx>=initx && posy >=inity)
			{
				mapstartSelect=mapinitialSelect;
				mapendSelect = num;
			}
			//kanan atas
			if (posx>=initx && posy <inity)
			{
				mapstartSelect=posy*Tw+initx;
				mapendSelect = inity*Tw+posx;
			}
			//kiri bawah
			if (posx<initx && posy >=inity)
			{
				mapstartSelect=inity*Tw+posx;
				mapendSelect = posy*Tw+initx;
			}
			//kiri atas
			if (posx<initx && posy <inity)
			{
				mapstartSelect=num;
				mapendSelect = mapinitialSelect;
			}
			
			return true;
		}
		else
		{
			drag = true;int buffer=Tsw + Tsh;
			float panspeed=(float) scrollspeed/2;
			
			switch (kartu)
			{
				
				case "world":
					
					float x = p3 * panspeed * cam.zoom;
					float y = p4 * panspeed * cam.zoom;

					cam.translate(-x, y);
					//uicam.translate(-x, y);
		
					if (cam.position.x < 0) cam.position.x = 0;
					if (cam.position.x > Tsw*Tw) cam.position.x = Tsw*Tw;
					if (cam.position.y < -Tsh*Th) cam.position.y = -Tsh*Th;
					if (cam.position.y > 0) cam.position.y = 0;
					
				
					cam.update();
					//uicam.update();
					break;
				case "tile":case "pickanim":
					if (tilesets.size()==0) return true;
					x = p3 * panspeed * tilecam.zoom;
					y = p4 * panspeed * tilecam.zoom;
					tilecam.translate(-x, y);
					tileset t= tilesets.get(seltset);
					if (kartu!="tile")
					{
					switch (tilePicker){
					case "newimgobj":case "props":case "rnda":case "rndb":case "repa": case "repb":
						
						t= tilesets.get(selTsetID);
					}
					}
					
					if (tilecam.position.x < 0) tilecam.position.x = 0;
					if (tilecam.position.x > t.getWidth()*t.getTilewidth()) tilecam.position.x = t.getWidth()*t.getTilewidth();
					if (tilecam.position.y > 0) tilecam.position.y = 0;
					if (tilecam.position.y < -t.getHeight()*t.getTileheight()) tilecam.position.y = -t.getHeight()*t.getTileheight();
					
					
				
					tilecam.update();
			}
		}
		return true;
	}

	@Override
	public boolean panStop(float p1, float p2, int p3, int p4)
	{
		if (loadingfile) return true;
		drag = false;
		
		if (stamp && kartu == "tile")
		{
			curspr = startSelect+tilesets.get(seltset).getFirstgid();
			kartu = "world";
			activetool=0;
		}
		if (stamp && kartu == "pickanim" && tilePicker=="massprops")
		{
			stamp=false;
			tileset ts= tilesets.get(selTsetID);
			int widih = endSelect % tilesets.get(selTsetID).getWidth() - startSelect % tilesets.get(selTsetID).getWidth()+1;
			int heih = endSelect / tilesets.get(selTsetID).getWidth() - startSelect / tilesets.get(selTsetID).getWidth()+1;

			for (int yy = 0;yy < heih;yy++)
			{
				for (int xx = 0;xx < widih;xx++)
				{
					int num = startSelect;
					int nyum=num + xx + (yy * ts.getWidth());
					if (nyum < ts.getHeight() * ts.getWidth())
					{
						if (nyum % ts.getWidth() >= num % ts.getWidth())
						{
							if (massprops.get(nyum))
							{
								massprops.set(nyum,false);
							}else{
								massprops.set(nyum,true);
							}

						}
					}

				}
			}	
		}
		
		if (roll)
		{
			if (activetool==0||activetool==1){
			
			int awidih=0,aheih=0,anum=0;
			int widih = mapendSelect % Tw - mapstartSelect % Tw ;
			int heih = mapendSelect / Tw - mapstartSelect / Tw ;
			boolean follower=false;
			int num=mapstartSelect;
			int[] pon = new int[9];
			if (stamp)
			{
				anum=startSelect+tilesets.get(seltset).getFirstgid();
				int aTw=tilesets.get(seltset).getWidth();
				awidih = endSelect % aTw - startSelect % aTw ;
				aheih = endSelect / aTw - startSelect / aTw ;
				for (int yy = 0;yy <= aheih;yy++)
				{
					for (int xx = 0;xx <= awidih;xx++)
					{
						int kodok=anum + xx + (yy * aTw);
						if (xx==0 && yy==0) {pon[0]=kodok;pon[1]=kodok;pon[3]=kodok;}
						if (xx>0 && xx<awidih && yy==0) {pon[1]= kodok;pon[4]=kodok;}
						if (xx==awidih && yy==0) {pon[2]= kodok;pon[5]=kodok;}
						

						if (xx==0 && yy>0 && yy<aheih) {pon[3]= kodok;pon[4]=kodok;}
						if (xx>0 && xx<awidih && yy>0 && yy<aheih) pon[4]= kodok;
						if (xx==awidih && yy>0 && yy<aheih) pon[5]= kodok;
	
						if (xx==0 && yy==aheih) {pon[6]= kodok;pon[7]=kodok;}
						if (xx>0 && xx<awidih && yy==aheih) pon[7]= kodok;
						if (xx==awidih && yy==aheih) pon[8]= kodok;
						
					}
				}
				//status(pon[0]+"-"+pon[1]+"-"+pon[2]+"-"+pon[3]+"-"+pon[4]+"-"+pon[5]+"-"+pon[6]+"-"+pon[7]+"-"+pon[8],5);
			}
			for (int yy = 0;yy <= heih;yy++)
			{
				for (int xx = 0;xx <= widih;xx++)
				{
					if ((num + xx + (yy * Tw)) < Th * Tw)
					{
						if ((num + xx + (yy * Tw)) % Tw >= num % Tw)
						{
							int oldcurspr=curspr;
							if (stamp){
								
							if (xx==0 && yy==0) curspr=pon[0];
							if (xx>0 && xx<widih && yy==0) curspr=pon[1];
							if (xx==widih && yy==0) curspr=pon[2];

							if (xx==0 && yy>0 && yy<heih) curspr=pon[3];
							if (xx>0 && xx<widih && yy>0 && yy<heih) curspr=pon[4];
							if (xx==widih && yy>0 && yy<heih) curspr=pon[5];

							if (xx==0 && yy==heih) curspr=pon[6];
							if (xx>0 && xx<widih && yy==heih) curspr=pon[7];
							if (xx==widih && yy==heih) curspr=pon[8];
							}
							if (xx==0 && yy==0)
							{
								tapTile(num + xx + (yy * Tw),false);
								
							}else
							{
								tapTile(num + xx + (yy * Tw),true);	
							}
							
							curspr=oldcurspr;
							/*
							int nyum=mapstartSelect + xx + (yy * Tw);
							if (activetool==0) long oi = curspr;
							if (activetool==1) long oi = 0;
							Long from = layers.get(seltile).getStr().get(nyum);
							int tzet = layers.get(seltile).getTset().get(nyum);

							layerhistory lh2=new layerhistory(follower,from,oi,nyum,seltile,tzet);

							if(from!=oi){
								undolayer.add(lh2);
								redolayer.clear();
							}
							
						
							layers.get(seltile).getStr().set(nyum, oi);
							layers.get(seltile).getTset().set(nyum, seltset);
							follower=true;
							*/
						}
					}
				}
				}
			
			}
			roll=false;
		}
		if (activetool==4) roll=false;
		return false;
	}

	@Override
	public boolean zoom(float p1, float p2)
	{
		return false;
	}

	@Override
	public boolean pinch(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4)
	{
		nofling=.3f;
		velx=0;vely=0;
		float pa1=p1.dst(p2);
		float pa2=p3.dst(p4);
		float newx=(p3.x+p4.x)/2;
		float newy=(p3.y+p4.y)/2;
		if (prevx!=0) pan(newx,newy,newx-prevx,newy-prevy);
		prevx=(p3.x+p4.x)/2;
		prevy=(p3.y+p4.y)/2;
		//pan((p1.x+p2.x)/2,(p1.y+p2.y)/2,((p1.x+p2.x)/2)/((p3.x+p4.x)/2),((p1.y+p2.y)/2)/((p3.y+p4.y)/2));
		//cacheTiles();
		//zooming=true;
		if (cammode=="View only"){
			if (cam.zoom < zoomTreshold){
				cammode="";
			}
		}else{
			if (cam.zoom > zoomTreshold){
				cammode="View only";
				cacheTiles();
			}
		}
		if (kartu == "world")
		{
			cam.zoom = initialZoom * pa1 / pa2;

			if (cam.zoom > Tsw*2)
			{cam.zoom = Tsw*2;}
			if (cam.zoom < Tsw/320f)//zoom in
			{cam.zoom = Tsw/320f;}
			cam.update();
		}
		else if (kartu == "tile" || kartu == "pickanim")
		{
			tilecam.zoom = initialZoom * pa1 / pa2;

			if (tilecam.zoom > Tsw*2)
			{tilecam.zoom = Tsw*2;}
			if (tilecam.zoom < Tsw/320f)
			{tilecam.zoom = Tsw/320f;}
			tilecam.update();
		}
		// TODO: Implement this method
		//zooming=false;
		return false;
	}

	public Pixmap pixmapfromtexture(Texture texture, String key){



		Texture tex = texture;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		TextureData textureData = tex.getTextureData();
		if (!textureData.isPrepared()){
		textureData.prepare();
		}
		Pixmap pixmap = tex.getTextureData().consumePixmap();
		int ww=pixmap.getWidth();
		int hh=pixmap.getHeight();
		if (!isPowerOf2(pixmap.getWidth()))
		{
			//ww=powerize(ww);
		}
		if (!isPowerOf2(pixmap.getHeight()))
		{
			//hh=powerize(hh);
		}
		Pixmap pm2 = new Pixmap(ww,hh,Pixmap.Format.RGBA8888);
		
		if (key !=""){
		for (int y = 0; y < hh; y++) {
			for (int x = 0; x < ww; x++) {

				com.badlogic.gdx.graphics.Color color = new com.badlogic.gdx.graphics.Color();
				com.badlogic.gdx.graphics.Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
				//	if (keycolor==pixmap.getPixel(x, y)){
				if (key!=null){
					if ((key+"ff").equalsIgnoreCase(color.toString())){
						//pm2.setBlending(Pixmap.Blending.None);
						//pixmap.setColor(new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 0f));
						//pixmap.fillRectangle(x, y, 1, 1);
					}else{
						pm2.setColor(color);
						pm2.fillRectangle(x, y, 1, 1);
					}
				}else
				{
					pm2.setColor(color);
					pm2.fillRectangle(x, y, 1, 1);
				}

			}
		}
			return pm2;
		}else
		{
			return pixmap;
		}
		
	}
	public Texture chromaKey(Texture texture, String key){
		
	
		
		Texture tex = texture;
		Gdx.gl.glEnable(GL10.GL_BLEND);
		TextureData textureData = tex.getTextureData();
		
		textureData.prepare();

		Pixmap pixmap = tex.getTextureData().consumePixmap();
		int ww=pixmap.getWidth();
		int hh=pixmap.getHeight();
		if (!isPowerOf2(pixmap.getWidth()))
		{
			//ww=powerize(ww);
		}
		if (!isPowerOf2(pixmap.getHeight()))
		{
			//hh=powerize(hh);
		}
		Pixmap pm2 = new Pixmap(ww,hh,Pixmap.Format.RGBA8888);
		for (int y = 0; y < hh; y++) {
			for (int x = 0; x < ww; x++) {

	com.badlogic.gdx.graphics.Color color = new com.badlogic.gdx.graphics.Color();
			com.badlogic.gdx.graphics.Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
			//	if (keycolor==pixmap.getPixel(x, y)){
				if (key!=null){
				if ((key+"ff").equalsIgnoreCase(color.toString())){
					//pm2.setBlending(Pixmap.Blending.None);
					//pixmap.setColor(new com.badlogic.gdx.graphics.Color(0f, 0f, 0f, 0f));
					//pixmap.fillRectangle(x, y, 1, 1);
					}else{
					pm2.setColor(color);
					pm2.fillRectangle(x, y, 1, 1);
				}
				}else
				{
					pm2.setColor(color);
					pm2.fillRectangle(x, y, 1, 1);
				}

			}
		}

		tex = new Texture(pm2);
		pixmap.dispose();pm2.dispose();textureData.disposePixmap();
		return tex;
	}
	private int powerize(int ww)
	{
		if (ww<16){
			return 16;
		}else if (ww>16 && ww<32){
			return 32;
		}else if (ww>32 && ww<64){
			return 64;
		}else if (ww>64 && ww<128){
			return 128;
		}else if (ww>128 && ww<256){
			return 256;
		}else if (ww>256 && ww<512){
			return 512;
		}else if (ww>512 && ww<1024){
			return 1024;
		}else if (ww>1024 && ww<2048){
			return 2048;
		}else if (ww>2048 && ww<4096){
			return 4096;
		}else if (ww>4096){
			return 8192;
		}
		return 0;
	}
	private static boolean isPowerOf2(final int n) {
		if (n <= 0) {
			return false;
		}
		return Integer.bitCount(n) == 1;
    }
	
	int createPixel(int r, int g, int b)
	{
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}
	
	
	
}
