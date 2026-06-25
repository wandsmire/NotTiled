package com.mirwanda.nottiled;

import android.*;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.*;
import android.content.pm.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.speech.tts.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.*;
//import com.google.android.gms.ads.*;//

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
//import javax.annotation.*;
//import org.solovyev.android.checkout.*;

//import com.mirwanda.nottiled.BuildConfig;//


public class MainActivity extends AndroidApplication implements Interface
{

	private static final int CREATE_REQUEST_CODE = 40;
	private static final int OPEN_REQUEST_CODE = 41;
	private static final int REGAIN_ACCESS_CODE = 44;
	private static final int SAVE_REQUEST_CODE = 42;
	private static final int SAVEAS_REQUEST_CODE = 43;
	private static final int BINARY_CREATE_CODE = 39;
	private static final int REQUEST_TREE_CODE = 45;
	private static final int EDIT_NOT2PIX_CODE = 46;
	Uri currentMAP = null;
	SharedPreferences myPrefs;
	SharedPreferences.Editor prefs;
	//TextToSpeech tts;
	PackageInfo pInfo;
	String version;
	@Override
	public void speak(final String s)
	{
		
			
		//tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
		
		// TODO: Implement this method
	}

	@Override
	public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config){
	return new AsynchronousAndroidAudio( context, config );
	}

	@Override
	public void changelanguage(String lang)
	{

	switch (lang)
	{
		/*
		case "English":
			tts.setLanguage(Locale.ENGLISH);
			break;
		case "Bahasa Indonesia":
			tts.setLanguage(new Locale("in_ID"));
			break;
		case "Spanish":
			tts.setLanguage(new Locale("es_ES"));
			break;
		case "French":
			tts.setLanguage(new Locale("fr_FR"));
			break;
		case "Chinese":
			tts.setLanguage(new Locale("cmn_CN"));
			break;
		case "Japanese":
			tts.setLanguage(new Locale("ja_JP"));
			break;
		case "Russian":
			tts.setLanguage(new Locale("ru_RU"));
			break;
		case "Portuguese":
			tts.setLanguage(new Locale("pt_PT"));
			break;
		case "Tagalog":
			tts.setLanguage(new Locale("fil_PH"));
			break;

		 */
	}
		
		// TODO: Implement this method
	}
	
	
	@Override
	public boolean ispro()
	{
		return proVersion;
	}

	@Override
	public String getVersione()
	{
		return version;
	}





	@Override
	public byte[] getData() {
		return SAFdata;
	}

	@Override
	public java.util.List<byte[]> getDatas() {
		return SAFdatas;
	}

	@Override
	public String getFilename() {
		return SAFfilename;
	}

	@Override
	public String getUri() {
		return SAFuri;
	}

	@Override
	public java.util.List<String> getFilenames() {
		return SAFfilenames;
	}

	@Override
	public String getStatus() {
		return SAFstatus;
	}

	@Override
	public String getOS() {
		return vers;
	}


	//private ActivityCheckout mCheckout;
	private static final String AD_FREE = "adfree";
	boolean proVersion = true;
	/**/
	//public AdView adView;//
	//private InterstitialAd mInterstitialAd;//
	/**/
	
	@Override
	public void showinterstitial(){
		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (proVersion) return;
					//mInterstitialAd.show();//
				}
			});
	}
	
	@Override
	public void showbanner(final boolean show)
	{
		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					/**/
					if (proVersion) 
					{
						//adView.setVisibility(View.GONE);
						return;
					}
					if (show){
						//adView.setVisibility(View.VISIBLE);
					}else
					{
						//adView.setVisibility(View.GONE);
					}
					/**/
				}
			});
	}

	@Override
	public boolean buyadfree()
	{
		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//mCheckout.startPurchaseFlow(ProductTypes.IN_APP, AD_FREE, null, new PurchaseListener());
				}
			});
		return true;
	}
	
	
	
	
	
	
	
/*
    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(@Nonnull Purchase purchase) {
          	proVersion=true;
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(@Nonnull Inventory.Products products) {
            final Inventory.Product product = products.get(ProductTypes.IN_APP);
            if (!product.supported) {
                return;
            }
            if (product.isPurchased(AD_FREE)) {
				proVersion= true;
                return;
            }
        }
    }

 */

	private boolean isPermissionDeclared(String permission) {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
			if (info.requestedPermissions != null) {
				for (String p : info.requestedPermissions) {
					if (p.equals(permission)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501; // Any value
	public String vers;
	public void requestAccess(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			vers="android10+";
			if (isPermissionDeclared("android.permission.MANAGE_EXTERNAL_STORAGE")) {
				if (!Environment.isExternalStorageManager()) {
					try {
						Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
						intent.addCategory("android.intent.category.DEFAULT");
						intent.setData(Uri.parse(String.format("package:%s", getPackageName())));
						startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
					} catch (Exception e) {
						Intent intent = new Intent();
						intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
						startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
					}
				}
			}
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.R ) {
			vers="android9-";
			if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
			}
		}else{
			vers="android9-";
		}
	}

	public void runGDX(){
		String intend="";
		Intent intent = getIntent();
		if (intent.getData()!=null) intend=intent.getData().toString();

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(intend,this), cfg);

	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myPrefs = getSharedPreferences("NotTiled", 0);
		prefs = myPrefs.edit();

		try {
			currentMAP = Uri.parse(myPrefs.getString("url", ""));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// Restore saved folder-tree URI and rebuild index in background
		String savedTree = myPrefs.getString("treeUri", "");
		if (!savedTree.isEmpty()) {
			try {
				Uri treeUri = Uri.parse(savedTree);
				// Verify we still hold the permission (read+write)
				getContentResolver().takePersistableUriPermission(treeUri,
						Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
				savedTreeUri = treeUri;
				new Thread(new Runnable() {
					@Override
					public void run() {
						buildTreeIndex(savedTreeUri);
					}
				}).start();
			} catch (Exception e) {
				// Permission was revoked — clear saved URI
				prefs.remove("treeUri").commit();
			}
		}

		//throw new RuntimeException("Test Crash"); // Force a crash
		requestAccess();
		runGDX();


		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		/*
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

				@Override
				public void onInit(int status) {
					if(status != TextToSpeech.ERROR) {
						tts.setLanguage(Locale.UK);
						tts.setPitch(1f);
						tts.setSpeechRate(1f);
						//   tts.speak(SC_str, TextToSpeech.QUEUE_FLUSH, null,null);
					}
				}
			});

		 */


//		final Billing billing = Aplikasi.get().getBilling();
 //       mCheckout = Checkout.forActivity(this, billing);
  //      mCheckout.start();
   //     mCheckout.loadInventory(Inventory.Request.create().loadAllPurchases(), new InventoryCallback());

		/**/

		//RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
		/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
     */


        // Create the libgdx View
        //View gameView = initializeForView(new MyGdxGame(intend,this));
		//layout.addView(gameView);


		//Create and setup interstitial

		/*
		mInterstitialAd = new InterstitialAd(this);
		String ads;
		if (BuildConfig.DEBUG) {
			ads = "ca-app-pub-3940256099942544/1033173712";
		}else{
			ads = "ca-app-pub-0329741361926795/8939201077";
		}

		if (!proVersion){
			mInterstitialAd.setAdUnitId(ads);
			mInterstitialAd.loadAd(new AdRequest.Builder().build());
			mInterstitialAd.setAdListener(new AdListener() {
					@Override
					public void onAdClosed() {
						mInterstitialAd.loadAd(new AdRequest.Builder().build());
					}
				});
		}

        // Create and setup the Banner
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
		if (BuildConfig.DEBUG){
			adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111"); // Put in your secret key here
		}else
		{
        	adView.setAdUnitId("ca-app-pub-0329741361926795/9130772767"); // Put in your secret key here
		}

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
		adView.setVisibility(View.GONE);
        // Add the libgdx view

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams =
        	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
											RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

		 */

        // Hook it all up
        //setContentView(layout);
		/**/

    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged( hasFocus );



		/* black bar.
		if (hasFocus){
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
		}

		 */
	}

	@Override
	public void newFile()
	{
		SAFstatus="";
		SAFdata=null;
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		intent.putExtra(Intent.EXTRA_TITLE, "newfile.tmx");
		startActivityForResult(intent, CREATE_REQUEST_CODE);
	}

	@Override
	public void openFile()
	{
		SAFstatus="";
		SAFdata=null;

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.addFlags(
						Intent.FLAG_GRANT_READ_URI_PERMISSION
								| Intent.FLAG_GRANT_WRITE_URI_PERMISSION
								| Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
								| Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
				intent.setType("*/*");
				startActivityForResult(intent, OPEN_REQUEST_CODE);
			}
		});
	}

	@Override
	public void selectFolder()
	{
		SAFstatus="";
		SAFdata=null;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
				startActivityForResult(intent, REQUEST_TREE_CODE);
			}
		});
	}

	@Override
	public String getdatafromURI(String URI)
	{
		currentMAP = Uri.parse(URI);
		//
		prefs.putString("url", currentMAP.toString());
		prefs.commit();
		try {
			String s = readFileContent( currentMAP );
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setOrientation(int ori) {
		switch (ori){
			case 0: //both (ignore lock)
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
				break;
			case 1: //landscape
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				break;
			case 2: //portrait
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				break;
			case 3: //auto (respect lock)
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
				} else {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
				}
				break;
		}
	}

	@Override
	public void saveFile(String data)
	{
		if (currentMAP ==null) {
			SAFstatus="";
			SAFdata=null;
			tmpdata = data;

			Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.addFlags(
					Intent.FLAG_GRANT_READ_URI_PERMISSION
							| Intent.FLAG_GRANT_WRITE_URI_PERMISSION
							| Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
							| Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
			intent.setType("*/*");
			startActivityForResult(intent, REGAIN_ACCESS_CODE);

		}else{
			writeFileContent( currentMAP, data);
		}
	}

	String tmpdata;
	byte[] bytedata;
	@Override
	public void saveasFile(String data, String filenamesuggestion)
	{
		tmpdata = data;
		SAFdata = null;
		SAFstatus="";
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		intent.putExtra(Intent.EXTRA_TITLE, filenamesuggestion);
		startActivityForResult(intent, CREATE_REQUEST_CODE);
	}

	@Override
	public void saveasFile(byte[] data, String filenamesuggestion)
	{
		bytedata = data;
		SAFdata = null;
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		intent.putExtra(Intent.EXTRA_TITLE, filenamesuggestion);
		startActivityForResult(intent, BINARY_CREATE_CODE);
	}


	java.util.List<byte[]> SAFdatas = new ArrayList<byte[]>();
	java.util.List<String> SAFfilenames = new ArrayList<String>();

	volatile byte[] SAFdata;
	volatile String SAFfilename="";
	volatile String SAFuri="";
	volatile String SAFstatus="";

	// Persistent document-tree for smart asset resolution
	private Uri savedTreeUri = null;
	private final java.util.HashMap<String, Uri> treeIndex = new java.util.HashMap<>();

	@SuppressLint("WrongConstant")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
		//mCheckout.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK)
		{
			if (requestCode == CREATE_REQUEST_CODE)
			{
				if (resultData != null) {
					Uri uri = resultData.getData();
					writeFileContent( uri, tmpdata);
					SAFuri = uri.toString();
					SAFfilename = getFileName( uri );

					if (SAFfilename.endsWith( "tmx" )){
						currentMAP = resultData.getData();
						prefs.putString("url", currentMAP.toString());
						prefs.commit();

						final int takeFlags = resultData.getFlags()
								& (Intent.FLAG_GRANT_READ_URI_PERMISSION
								| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
						getContentResolver().takePersistableUriPermission(uri, takeFlags);
						SAFstatus="ok";
					}

				}else{
					SAFstatus="cancel";
				}
			}
			if (requestCode == BINARY_CREATE_CODE)
			{
				if (resultData != null) {
					Uri uri = resultData.getData();
					writeByte( uri, bytedata);
				}
			}
			else if (requestCode == SAVEAS_REQUEST_CODE) {

				if (resultData != null) {
					currentMAP = resultData.getData();
					writeFileContent( currentMAP, "test");
				}
			}
			else if (requestCode == REQUEST_TREE_CODE) {

				if (resultData != null) {
					final Uri treeUri = resultData.getData();
					SAFstatus = "";
					SAFfilenames.clear();
					SAFfilename = "";

					// Take persistable read+write permission and save URI
					try {
						final int takeFlags = resultData.getFlags()
								& (Intent.FLAG_GRANT_READ_URI_PERMISSION
								| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
						getContentResolver().takePersistableUriPermission(treeUri,
								takeFlags != 0 ? takeFlags : Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
						savedTreeUri = treeUri;
						prefs.putString("treeUri", treeUri.toString()).commit();
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Build index in background; set SAFstatus = "OK" when done
					new Thread(new Runnable() {
						@Override
						public void run() {
							buildTreeIndex(treeUri);
							SAFstatus = "OK";
						}
					}).start();
				} else {
					SAFstatus = "cancel";
				}
			}
			else if (requestCode == OPEN_REQUEST_CODE) {

				if (resultData != null) {

					try {
						Uri uri = resultData.getData();
						SAFdata = readBytes( uri );
						SAFfilename = getFileName( uri );
						SAFuri = uri.toString();

						if (SAFfilename.endsWith( "tmx" )){
							currentMAP = uri;
							prefs.putString("url", currentMAP.toString());
							prefs.commit();
							final int takeFlags = resultData.getFlags()
									& (Intent.FLAG_GRANT_READ_URI_PERMISSION
									| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
							getContentResolver().takePersistableUriPermission(uri, takeFlags);

						}

						SAFstatus = "OK";
					} catch (Exception e) {
						e.printStackTrace();
						SAFstatus = "error";
					}
				} else {
					SAFstatus = "cancel";
				}
			} else if (requestCode == REGAIN_ACCESS_CODE) {

				if (resultData != null) {
					currentMAP = resultData.getData();
					SAFuri = currentMAP.toString();
					writeFileContent( currentMAP, tmpdata);
				}
			}else{
			}

		} else {
			// Result was not RESULT_OK (user cancelled, or provider returned a
			// non-OK code — common with cloud/Drive sources). The background
			// spin-loop in nativeOpenSAF waits on SAFstatus; if we never set it,
			// it hangs forever and "nothing happens". Always release the wait.
			if (requestCode == OPEN_REQUEST_CODE
					|| requestCode == REQUEST_TREE_CODE
					|| requestCode == CREATE_REQUEST_CODE
					|| requestCode == SAVEAS_REQUEST_CODE) {
				SAFstatus = "cancel";
			}
		}
		if (requestCode == EDIT_NOT2PIX_CODE && not2pixEditPath != null) {
			// Not2Pix returned — reload the tileset texture
			final String path = not2pixEditPath;
			not2pixEditPath = null;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					((MyGdxGame) getApplicationListener()).reloadTilesetByPath(path);
				}
			});
		}
		super.onActivityResult(requestCode, resultCode, resultData);
	}

	private void writeFileContent(Uri uri, String data)
	{
		try{
			ParcelFileDescriptor pfd =
					this.getContentResolver().
							openFileDescriptor(uri, "rwt");
			if (pfd == null) throw new FileNotFoundException("Cannot open file descriptor for: " + uri);

			FileOutputStream fileOutputStream =
					new FileOutputStream(
							pfd.getFileDescriptor());

			String textContent = data;

			fileOutputStream.write(textContent.getBytes());

			fileOutputStream.close();
			pfd.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeByte(Uri uri, byte[] data)
	{
		try{
			ParcelFileDescriptor pfd =
					this.getContentResolver().
							openFileDescriptor(uri, "rwt");
			if (pfd == null) throw new IOException("Cannot open file descriptor for: " + uri);

			FileOutputStream fileOutputStream =
					new FileOutputStream(
							pfd.getFileDescriptor());

			fileOutputStream.write( data );
			fileOutputStream.close();
			pfd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private byte[] readBytes(Uri uri) throws IOException {

		InputStream inputStream =
				getContentResolver().openInputStream(uri);
		if (inputStream == null) throw new IOException("Cannot open stream for: " + uri);

		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		int len = 0;

		while ((len = inputStream.read(buffer)) != -1) {
			byteBuffer.write(buffer, 0, len);
		}
		inputStream.close();
		return byteBuffer.toByteArray();
	}


	private String readFileContent(Uri uri) throws IOException {

		InputStream inputStream =
				getContentResolver().openInputStream(uri);
		if (inputStream == null) throw new IOException("Cannot open stream for: " + uri);
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(
						inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String currentline;
		try {
			while ((currentline = reader.readLine()) != null) {
				stringBuilder.append(currentline + "\n");
			}
		} finally {
			reader.close();
		}
		return stringBuilder.toString();
	}

	public String getFileName(Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					int dx = cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME);
					if(dx!=-1) result = cursor.getString(dx);
				}
			} finally {
				if (cursor != null) cursor.close();
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private List<Uri> readFiles(Context context, Intent intent) {
		List<Uri> uriList = new ArrayList<>();

		// the uri returned by Intent.ACTION_OPEN_DOCUMENT_TREE
		Uri uriTree = intent.getData();
		// the uri from which we query the files
		Uri uriFolder = DocumentsContract.buildChildDocumentsUriUsingTree(uriTree, DocumentsContract.getTreeDocumentId(uriTree));

		Cursor cursor = null;
		try {
			// let's query the files
			cursor = context.getContentResolver().query(uriFolder,
					new String[]{DocumentsContract.Document.COLUMN_DOCUMENT_ID},
					null, null, null);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					// build the uri for the file
					Uri uriFile = DocumentsContract.buildDocumentUriUsingTree(uriTree, cursor.getString(0));
					//add to the list
					uriList.add(uriFile);

				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			// TODO: handle error
		} finally {
			if (cursor!=null) cursor.close();
		}

		//return the list
		return uriList;
	}

	@Override
	public String getFilesDirPath() {
		try {
			java.io.File dir = getExternalFilesDir(null);
			if (dir != null) {
				return dir.getAbsolutePath() + "/";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getApkPath() {
		return getPackageCodePath();
	}

	@Override
	public boolean isAccessAllFilesGranted() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			return Environment.isExternalStorageManager();
		}
		return true;
	}

	@Override
	public String getExternalStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	}

	private String not2pixEditPath = null;

	@Override
	public boolean isNot2PixInstalled() {
		try {
			Intent intent = new Intent("com.mirwanda.not2pix.EDIT_TILESET");
			if (intent.resolveActivity(getPackageManager()) != null) {
				return true;
			}
			// Fallback: check if the package exists
			getPackageManager().getApplicationInfo("com.mirwanda.not2pix", 0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void editInNot2Pix(final String absolutePath) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				not2pixEditPath = absolutePath;
				try {
					java.io.File file = new java.io.File(absolutePath);
					// Use FileProvider for Android 7+ (content:// URI with rw grant)
					Uri uri;
					if (Build.VERSION.SDK_INT >= 24) {
						uri = androidx.core.content.FileProvider.getUriForFile(
							MainActivity.this,
							getPackageName() + ".fileprovider",
							file);
					} else {
						uri = Uri.fromFile(file);
					}
					Intent intent = new Intent("com.mirwanda.not2pix.EDIT_TILESET");
					intent.setDataAndType(uri, "image/png");
					intent.putExtra("file_path", absolutePath);
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
					startActivityForResult(intent, EDIT_NOT2PIX_CODE);
				} catch (Exception e) {
					not2pixEditPath = null;
				}
			}
		});
	}

	private String mainTmxRelativePath = null;

	private void copyDocumentTree(Uri rootUri, String rootDocId, java.io.File destDir) {
		deleteRecursive(destDir);
		destDir.mkdirs();
		mainTmxRelativePath = null;
		traverseAndCopy(rootUri, rootDocId, destDir, "");
	}

	private void traverseAndCopy(Uri treeUri, String parentDocId, java.io.File destDir, String relativePath) {
		Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, parentDocId);
		Cursor cursor = null;
		try {
			cursor = getContentResolver().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_MIME_TYPE,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME
			}, null, null, null);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					String docId = cursor.getString(0);
					String mimeType = cursor.getString(1);
					String displayName = cursor.getString(2);

					if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
						java.io.File newDestDir = new java.io.File(destDir, displayName);
						newDestDir.mkdirs();
						traverseAndCopy(treeUri, docId, newDestDir,
								relativePath.isEmpty() ? displayName : relativePath + "/" + displayName);
					} else {
						java.io.File destFile = new java.io.File(destDir, displayName);
						Uri fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, docId);
						copyFile(fileUri, destFile);

						String relFilePath = relativePath.isEmpty() ? displayName : relativePath + "/" + displayName;
						SAFfilenames.add(relFilePath);

						if (displayName.toLowerCase().endsWith(".tmx") && mainTmxRelativePath == null) {
							mainTmxRelativePath = relFilePath;
						}
					}
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private void copyFile(Uri srcUri, java.io.File destFile) throws IOException {
		InputStream in = null;
		FileOutputStream out = null;
		try {
			in = getContentResolver().openInputStream(srcUri);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}
	}

	private void deleteRecursive(java.io.File fileOrDirectory) {
		if (fileOrDirectory.isDirectory()) {
			java.io.File[] children = fileOrDirectory.listFiles();
			if (children != null) {
				for (java.io.File child : children) {
					deleteRecursive(child);
				}
			}
		}
		fileOrDirectory.delete();
	}

	// ─── Document-tree index ────────────────────────────────────────────────────

	/** Walk the tree rooted at treeUri and populate treeIndex with
	 *  (tree-relative-path → document-Uri) entries. */
	private void buildTreeIndex(Uri treeUri) {
		synchronized (treeIndex) {
			treeIndex.clear();
		}
		String rootDocId = DocumentsContract.getTreeDocumentId(treeUri);
		walkTree(treeUri, rootDocId, "");
	}

	private void walkTree(Uri treeUri, String parentDocId, String parentRelPath) {
		Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, parentDocId);
		android.database.Cursor cursor = null;
		try {
			cursor = getContentResolver().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_MIME_TYPE,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME
			}, null, null, null);
			if (cursor == null) return;
			while (cursor.moveToNext()) {
				String docId    = cursor.getString(0);
				String mimeType = cursor.getString(1);
				String name     = cursor.getString(2);
				String relPath  = parentRelPath.isEmpty() ? name : parentRelPath + "/" + name;
				if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
					walkTree(treeUri, docId, relPath);
				} else {
					Uri docUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, docId);
					synchronized (treeIndex) {
						treeIndex.put(relPath, docUri);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
	}

	/** Look up a filename (case-insensitive) anywhere in the tree and return its URI.
	 *  Prefers an exact relative-path match, then falls back to filename-only match. */
	private Uri resolveInTree(String relPath) {
		if (relPath == null || relPath.isEmpty()) return null;
		synchronized (treeIndex) {
			// Normalise separators
			String norm = relPath.replace('\\', '/');
			Uri exact = treeIndex.get(norm);
			if (exact != null) return exact;
			// Case-insensitive exact
			for (java.util.Map.Entry<String, Uri> e : treeIndex.entrySet()) {
				if (e.getKey().equalsIgnoreCase(norm)) return e.getValue();
			}
			// Filename-only fallback
			String filename = norm.contains("/") ? norm.substring(norm.lastIndexOf('/') + 1) : norm;
			for (java.util.Map.Entry<String, Uri> e : treeIndex.entrySet()) {
				String key = e.getKey();
				String keyName = key.contains("/") ? key.substring(key.lastIndexOf('/') + 1) : key;
				if (keyName.equalsIgnoreCase(filename)) return e.getValue();
			}
		}
		return null;
	}

	/** Find the tree-relative path of the document whose URI matches tmxSafUri.
	 *  Matches by document-ID component in the URI. */
	private String findRelPathForUri(String tmxSafUri) {
		if (tmxSafUri == null || tmxSafUri.isEmpty()) return null;
		// Extract document ID from content:// URI (last path segment after "document/")
		try {
			Uri u = Uri.parse(tmxSafUri);
			String docId = DocumentsContract.getDocumentId(u);
			if (docId == null) return null;
			// docId for external storage looks like "primary:Maps/town.tmx"
			// Strip the storage-volume prefix (everything up to and including the colon)
			String pathPart = docId.contains(":") ? docId.substring(docId.indexOf(':') + 1) : docId;
			pathPart = pathPart.replace('\\', '/');
			synchronized (treeIndex) {
				for (java.util.Map.Entry<String, Uri> e : treeIndex.entrySet()) {
					String edocId = null;
					try { edocId = DocumentsContract.getDocumentId(e.getValue()); } catch (Exception ex) {}
					if (edocId != null) {
						String ep = edocId.contains(":") ? edocId.substring(edocId.indexOf(':') + 1) : edocId;
						if (ep.equalsIgnoreCase(pathPart)) return e.getKey();
					}
				}
			}
			// Fallback: match by filename
			String tmxName = pathPart.contains("/") ? pathPart.substring(pathPart.lastIndexOf('/') + 1) : pathPart;
			synchronized (treeIndex) {
				for (java.util.Map.Entry<String, Uri> e : treeIndex.entrySet()) {
					String keyName = e.getKey().contains("/") ? e.getKey().substring(e.getKey().lastIndexOf('/') + 1) : e.getKey();
					if (keyName.equalsIgnoreCase(tmxName)) return e.getKey();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** Resolve a relative path (possibly containing ".." segments) against a base dir. */
	private static String resolveRelativePath(String base, String relative) {
		String combined = (base.isEmpty() ? "" : base + "/") + relative.replace('\\', '/');
		// Resolve ".." segments
		String[] parts = combined.split("/");
		java.util.ArrayDeque<String> stack = new java.util.ArrayDeque<>();
		for (String p : parts) {
			if (p.equals("..")) {
				if (!stack.isEmpty()) stack.pollLast();
			} else if (!p.equals(".") && !p.isEmpty()) {
				stack.addLast(p);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String s : stack) {
			if (sb.length() > 0) sb.append('/');
			sb.append(s);
		}
		return sb.toString();
	}

	// ─── Interface: fetchTmxAssets ───────────────────────────────────────────────

	@Override
	public void fetchTmxAssets(final String tmxContent, final String tmxUriStr) {
		synchronized (treeIndex) {
			if (savedTreeUri == null || treeIndex.isEmpty()) {
				SAFstatus = "no_tree";
				return;
			}
		}
		SAFstatus = "";
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					java.io.File extDir = getExternalFilesDir(null);
					if (extDir == null) throw new IOException("External storage unavailable");

					// Locate TMX in tree → determine its tree-relative directory
					String tmxRelPath = findRelPathForUri(tmxUriStr);
					String tmxRelDir = "";
					if (tmxRelPath != null && tmxRelPath.contains("/")) {
						tmxRelDir = tmxRelPath.substring(0, tmxRelPath.lastIndexOf('/'));
					}

					// Copy TMX into Temp at its tree-relative location
					java.io.File tmxDestFile;
					if (tmxRelPath != null && !tmxRelPath.isEmpty()) {
						tmxDestFile = new java.io.File(extDir, "NotTiled/Temp/" + tmxRelPath);
					} else {
						// TMX not found in tree — place at Temp root
						String name = SAFfilename.isEmpty() ? "map.tmx" : SAFfilename;
						tmxDestFile = new java.io.File(extDir, "NotTiled/Temp/" + name);
						tmxRelDir = "";
					}
					tmxDestFile.getParentFile().mkdirs();
					java.io.FileOutputStream fos = new java.io.FileOutputStream(tmxDestFile);
					fos.write(tmxContent.getBytes("UTF-8"));
					fos.close();

					// Compute the base Temp dir that mirrors the TMX's parent in the tree
					String tempBaseAbs = new java.io.File(extDir, "NotTiled/Temp").getAbsolutePath()
							+ (tmxRelDir.isEmpty() ? "" : "/" + tmxRelDir);

					// Collect all image/TSX sources from TMX
					java.util.List<String> sources = extractSources(tmxContent);

					// Also parse referenced TSX files
					java.util.List<String> extraSources = new java.util.ArrayList<>();
					for (String src : sources) {
						if (src.toLowerCase().endsWith(".tsx")) {
							String tsxRelInTree = resolveRelativePath(tmxRelDir, src);
							Uri tsxUri = resolveInTree(tsxRelInTree);
							if (tsxUri == null) tsxUri = resolveInTree(src);
							if (tsxUri != null) {
								String tsxContent = readUriString(tsxUri);
								if (tsxContent != null) {
									String tsxDir = tsxRelInTree.contains("/")
											? tsxRelInTree.substring(0, tsxRelInTree.lastIndexOf('/')) : "";
									java.util.List<String> tsxImages = extractImageSources(tsxContent);
									for (String img : tsxImages) {
										// TSX-relative image path → make TMX-relative
										String absImg = resolveRelativePath(tsxDir, img);
										String tmxRelImg = makeRelative(tmxRelDir, absImg);
										extraSources.add(tmxRelImg);
									}
									// Also copy the TSX itself
									copyTreeFileToTemp(tsxUri, extDir, tsxRelInTree);
								}
							}
						}
					}
					sources.addAll(extraSources);

					// Copy each image from tree to Temp using same path arithmetic as convertToAbsolutepath
					for (String src : sources) {
						if (src.toLowerCase().endsWith(".tsx")) continue; // already handled above
						// Resolve source relative to tmxRelDir to get tree-relative path
						String treeRelPath = resolveRelativePath(tmxRelDir, src);
						Uri imgUri = resolveInTree(treeRelPath);
						if (imgUri == null) imgUri = resolveInTree(src); // flat fallback
						if (imgUri == null) continue;

						// Destination = same path that convertToAbsolutepath(src, tempBaseAbs) would produce
						String destPath = convertToAbsolutepathStatic(src, tempBaseAbs);
						java.io.File destFile = new java.io.File(destPath);
						destFile.getParentFile().mkdirs();
						copyFile(imgUri, destFile);
					}

					// Expose tree-relative path of TMX so caller can build FileHandle
					SAFfilename = tmxRelPath != null ? tmxRelPath : tmxDestFile.getName();
					SAFstatus = "OK";
				} catch (Exception e) {
					e.printStackTrace();
					SAFstatus = "error";
				}
			}
		}).start();
	}

	@Override
	public boolean hasTreeAccess() {
		// A granted tree URI is enough to write into — the index may be empty for a
		// freshly-granted folder (e.g. saving a brand-new file into an empty folder).
		return savedTreeUri != null;
	}

	/** Extract all source="" attribute values from TMX/TSX XML (image and tileset references). */
	private java.util.List<String> extractSources(String xml) {
		java.util.List<String> result = new java.util.ArrayList<>();
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("source=\"([^\"]+)\"");
		java.util.regex.Matcher m = p.matcher(xml);
		while (m.find()) {
			result.add(m.group(1));
		}
		return result;
	}

	/** Extract only image source="" values (skip .tsx references). */
	private java.util.List<String> extractImageSources(String xml) {
		java.util.List<String> result = new java.util.ArrayList<>();
		for (String s : extractSources(xml)) {
			String lower = s.toLowerCase();
			if (!lower.endsWith(".tsx") && !lower.endsWith(".tx")) result.add(s);
		}
		return result;
	}

	/** Make pathAbs relative to baseDir (both are tree-relative). Simple version. */
	private String makeRelative(String baseDir, String pathAbs) {
		if (baseDir.isEmpty()) return pathAbs;
		if (pathAbs.startsWith(baseDir + "/")) return pathAbs.substring(baseDir.length() + 1);
		// Build "../" prefixed path
		String[] baseParts = baseDir.split("/");
		String[] absParts = pathAbs.split("/");
		int common = 0;
		while (common < baseParts.length && common < absParts.length
				&& baseParts[common].equals(absParts[common])) common++;
		StringBuilder sb = new StringBuilder();
		for (int i = common; i < baseParts.length; i++) sb.append("../");
		for (int i = common; i < absParts.length; i++) {
			if (i > common) sb.append('/');
			sb.append(absParts[i]);
		}
		return sb.toString();
	}

	/** Read the content of a document URI as a UTF-8 string. */
	private String readUriString(Uri uri) {
		try {
			java.io.InputStream is = getContentResolver().openInputStream(uri);
			if (is == null) return null;
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			int n;
			while ((n = is.read(buf)) != -1) baos.write(buf, 0, n);
			is.close();
			return baos.toString("UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/** Copy a tree URI document into Temp at treeRelPath. */
	private void copyTreeFileToTemp(Uri srcUri, java.io.File extDir, String treeRelPath) throws IOException {
		java.io.File dest = new java.io.File(extDir, "NotTiled/Temp/" + treeRelPath);
		dest.getParentFile().mkdirs();
		copyFile(srcUri, dest);
	}

	/** Mirrors the path arithmetic of MyGdxGame.convertToAbsolutepath for use in this layer. */
	private static String convertToAbsolutepathStatic(String relativePath, String basePath) {
		if (relativePath == null || relativePath.isEmpty()) return basePath;
		String rp = relativePath.replace('\\', '/');
		String bp = basePath.replace('\\', '/');
		if (!bp.endsWith("/")) bp += "/";
		while (rp.startsWith("../")) {
			rp = rp.substring(3);
			int slash = bp.lastIndexOf('/', bp.length() - 2);
			if (slash >= 0) bp = bp.substring(0, slash + 1);
		}
		return bp + rp;
	}

	// ─── Interface: saveFileToTree ────────────────────────────────────────────────

	@Override
	public boolean saveFileToTree(String relPath, byte[] data) {
		if (savedTreeUri == null || relPath == null || relPath.isEmpty() || data == null) return false;
		try {
			Uri existing = resolveInTree(relPath);
			if (existing != null) {
				ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(existing, "rwt");
				if (pfd == null) return false;
				FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
				fos.write(data);
				fos.close();
				pfd.close();
				return true;
			}
			// Create new file in tree
			String norm = relPath.replace('\\', '/');
			String[] parts = norm.split("/");
			String parentDocId = DocumentsContract.getTreeDocumentId(savedTreeUri);
			for (int i = 0; i < parts.length - 1; i++) {
				String childDocId = findChildDocId(savedTreeUri, parentDocId, parts[i]);
				if (childDocId == null) {
					Uri parentUri = DocumentsContract.buildDocumentUriUsingTree(savedTreeUri, parentDocId);
					Uri newDir = DocumentsContract.createDocument(getContentResolver(), parentUri,
							DocumentsContract.Document.MIME_TYPE_DIR, parts[i]);
					if (newDir == null) return false;
					parentDocId = DocumentsContract.getDocumentId(newDir);
				} else {
					parentDocId = childDocId;
				}
			}
			String fileName = parts[parts.length - 1];
			// Keep the exact filename. Android's FileSystemProvider rewrites the
			// name when the supplied extension isn't the canonical one for the MIME
			// type (e.g. "text/xml" turns "map.tmx" into "map.tmx.xml"), which makes
			// the saved file vanish from the user's point of view. ".tmx"/".tsx" are
			// not registered extensions, so "application/octet-stream" leaves them
			// untouched. ".json"/".png" map cleanly, so keep their real types.
			String mimeType = "application/octet-stream";
			if (fileName.endsWith(".json")) mimeType = "application/json";
			else if (fileName.endsWith(".png")) mimeType = "image/png";
			Uri parentUri = DocumentsContract.buildDocumentUriUsingTree(savedTreeUri, parentDocId);
			Uri newFile = DocumentsContract.createDocument(getContentResolver(), parentUri, mimeType, fileName);
			if (newFile == null) return false;
			// Some providers still rewrite the extension (e.g. "map.tmx" -> "map.tmx.xml").
			// If the created display name doesn't match what we asked for, rename it back.
			String actualName = getFileName(newFile);
			if (actualName != null && !actualName.equals(fileName)) {
				try {
					Uri renamed = DocumentsContract.renameDocument(getContentResolver(), newFile, fileName);
					if (renamed != null) newFile = renamed;
				} catch (Exception re) {
					re.printStackTrace();
				}
			}
			ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(newFile, "rwt");
			if (pfd == null) return false;
			FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
			fos.write(data);
			fos.close();
			pfd.close();
			synchronized (treeIndex) {
				treeIndex.put(norm, newFile);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String findChildDocId(Uri treeUri, String parentDocId, String childName) {
		Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(treeUri, parentDocId);
		Cursor cursor = null;
		try {
			cursor = getContentResolver().query(childrenUri, new String[]{
					DocumentsContract.Document.COLUMN_DOCUMENT_ID,
					DocumentsContract.Document.COLUMN_DISPLAY_NAME
			}, null, null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					if (childName.equalsIgnoreCase(cursor.getString(1))) {
						return cursor.getString(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) cursor.close();
		}
		return null;
	}

	@Override
	public boolean copyTreeFileToLocal(String relPath, String localPath) {
		if (savedTreeUri == null || relPath == null || relPath.isEmpty()) return false;
		Uri srcUri = resolveInTree(relPath);
		if (srcUri == null) return false;
		try {
			java.io.File dest = new java.io.File(localPath);
			dest.getParentFile().mkdirs();
			copyFile(srcUri, dest);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public java.util.List<String> listTreeFiles(String[] extensions) {
		java.util.List<String> result = new java.util.ArrayList<>();
		if (savedTreeUri == null || extensions == null) return result;
		synchronized (treeIndex) {
			for (String path : treeIndex.keySet()) {
				String lower = path.toLowerCase();
				for (String ext : extensions) {
					if (lower.endsWith(ext.toLowerCase())) {
						result.add(path);
						break;
					}
				}
			}
		}
		java.util.Collections.sort(result);
		return result;
	}

	private class CopyTreeRunnable implements Runnable {
		private final Uri treeUri;
		private final String treeDocumentId;

		CopyTreeRunnable(Uri treeUri, String treeDocumentId) {
			this.treeUri = treeUri;
			this.treeDocumentId = treeDocumentId;
		}

		@Override
		public void run() {
			try {
				java.io.File extDir = getExternalFilesDir(null);
				if (extDir == null) throw new IOException("External storage unavailable");
				java.io.File destDir = new java.io.File(extDir, "NotTiled/Temp");
				copyDocumentTree(treeUri, treeDocumentId, destDir);

				SAFfilename = mainTmxRelativePath != null ? mainTmxRelativePath : "";
				SAFstatus = "OK";
			} catch (Exception e) {
				e.printStackTrace();
				SAFstatus = "error";
			}
		}
	}
}
