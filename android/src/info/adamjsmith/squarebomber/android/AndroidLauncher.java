package info.adamjsmith.squarebomber.android;

import info.adamjsmith.squarebomber.SquareBomber;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements info.adamjsmith.squarebomber.gpgs.ActionResolver {
	SquareBomber game;
	protected static AdView adView;
	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	private AndroidTournaments mTournaments = null;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RelativeLayout layout = new RelativeLayout(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		
		mTournaments = new AndroidTournaments(this);
		
		game = new SquareBomber(this, mTournaments);
		
		View gameView = initializeForView(game, config);
		layout.addView(gameView);
		
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-5708097368765164/9423093735");
		
		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice("EFDE8B52D744910BE7EB01DEC797353A")
		.build();
		
		adView.loadAd(adRequest);
		
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		showAds(true);
		layout.addView(adView, adParams);
		
		setContentView(layout);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
	
	protected static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_ADS: 
				adView.setVisibility(View.VISIBLE);
				break;
			case HIDE_ADS:
				adView.setVisibility(View.GONE);
				break;
			}
		}
	};
	
	@Override
	protected void onStart() {
	    super.onStart();

	    // Notify the beginning of a user session.
    	if (mTournaments != null) {
    		mTournaments.onStart();
    	}
	}
	
	@Override 
	public void onStop() {
	    super.onStop(); 

	    // If there is an on-going tournament make sure to forfeit it 
    	if (mTournaments != null && mTournaments.isCurrentlyInTournament()) {
    		mTournaments.reportForfeitForCurrentTournament();
    	}
	}
	
	 @Override
	    public void onBackPressed() {
		    // If the game is in tournament mode -> forfeit the tournament.
	    	if (mTournaments != null && mTournaments.isCurrentlyInTournament()) {
	    		mTournaments.reportForfeitForCurrentTournament();
	    	}

			super.onBackPressed();
	    }
}
