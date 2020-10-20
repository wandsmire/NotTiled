package com.mirwanda.nottiled;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.speech.tts.*;
import android.view.*;
import android.widget.*;
import com.badlogic.gdx.backends.android.*;
import com.google.android.gms.ads.*;//
import java.util.*;
import javax.annotation.*;
import org.solovyev.android.checkout.*;

import com.mirwanda.nottiled.BuildConfig;//



public class MainActivity extends AndroidApplication implements Interface
{

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
	
	
	private ActivityCheckout mCheckout;
	private static final String AD_FREE = "adfree";
	boolean proVersion = false;
	/**/
	public AdView adView;//
	private InterstitialAd mInterstitialAd;//
	/**/
	
	@Override
	public void showinterstitial(){
		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (proVersion) return;
					mInterstitialAd.show();//
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
						adView.setVisibility(View.GONE);
						return;
					}
					if (show){
						adView.setVisibility(View.VISIBLE);
					}else
					{
						adView.setVisibility(View.GONE);
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
					mCheckout.startPurchaseFlow(ProductTypes.IN_APP, AD_FREE, null, new PurchaseListener());
				}
			});
		return true;
	}
	
	
	
	
	
	
	

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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCheckout.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
            }
		}
		String intend="";

		if (getPackageName().equalsIgnoreCase("com.mirwanda.nottiled"))
		{
		}
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
		
		final Billing billing = Aplikasi.get().getBilling();
        mCheckout = Checkout.forActivity(this, billing);
        mCheckout.start();
        mCheckout.loadInventory(Inventory.Request.create().loadAllPurchases(), new InventoryCallback());
		
		Intent intent = getIntent();
		if (intent.getData()!=null) intend=intent.getData().toString();
		
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		//initialize(new MyGdxGame(intend,this), cfg);
		/**/
		
		RelativeLayout layout = new RelativeLayout(this);

        // Do the stuff that initialize() would do for you
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		
		
        // Create the libgdx View
        View gameView = initializeForView(new MyGdxGame(intend,this));
		layout.addView(gameView);
		
		
		//Create and setup interstitial
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
      //  adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(adView, adParams);
		
        // Hook it all up
        setContentView(layout);
		/**/
		
    }
}
