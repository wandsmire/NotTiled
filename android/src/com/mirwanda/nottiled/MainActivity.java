package com.mirwanda.nottiled;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;
import android.provider.OpenableColumns;
import android.speech.tts.*;

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
	Uri currentMAP = null;
	SharedPreferences myPrefs;
	SharedPreferences.Editor prefs;
	TextToSpeech tts;
	PackageInfo pInfo;
	String version;
	@Override
	public void speak(final String s)
	{
		
			
		tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
		
		// TODO: Implement this method
	}
	
	@Override
	public void changelanguage(String lang)
	{

	switch (lang)
	{
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
	public String getFilename() {
		return SAFfilename;
	}

	@Override
	public String getStatus() {
		return SAFstatus;
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


	public void requestAccess(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
		}else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.R ) {
			if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
			}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		requestAccess();
		runGDX();


		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
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

	byte[] SAFdata;
	String SAFfilename="";
	String SAFstatus="";
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
					if (SAFfilename.endsWith( "tmx" )){
						currentMAP = resultData.getData();
						prefs.putString("url", currentMAP.toString());
						prefs.commit();
					}
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
			} else if (requestCode == OPEN_REQUEST_CODE) {

				if (resultData != null) {

					try {
						Uri uri = resultData.getData();
						SAFdata = readBytes( uri );
						SAFfilename = getFileName( uri );
						if (SAFfilename.endsWith( "tmx" )){
							currentMAP = uri;
							prefs.putString("url", currentMAP.toString());
							prefs.commit();
						}

						SAFstatus = "OK";
					} catch (IOException e) {
						SAFstatus = "error";
					}
				}
			} else if (requestCode == REGAIN_ACCESS_CODE) {

				if (resultData != null) {
					currentMAP = resultData.getData();
					writeFileContent( currentMAP, tmpdata);
				}
			}else{
			}

		}
		super.onActivityResult(requestCode, resultCode, resultData);
	}

	private void writeFileContent(Uri uri, String data)
	{
		try{
			ParcelFileDescriptor pfd =
					this.getContentResolver().
							openFileDescriptor(uri, "w");

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
							openFileDescriptor(uri, "w");

			FileOutputStream fileOutputStream =
					new FileOutputStream(
							pfd.getFileDescriptor());

			fileOutputStream.write( data );
			fileOutputStream.close();
			pfd.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private byte[] readBytes(Uri uri) throws IOException {

		InputStream inputStream =
				getContentResolver().openInputStream(uri);


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
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(
						inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String currentline;
		while ((currentline = reader.readLine()) != null) {
			stringBuilder.append(currentline + "\n");
		}
		inputStream.close();
		return stringBuilder.toString();
	}

	public String getFileName(Uri uri) {
		String result = null;
		if (uri.getScheme().equals("content")) {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					result = cursor.getString(cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME));
				}
			} finally {
				cursor.close();
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
}
