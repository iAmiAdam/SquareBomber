package info.adamjsmith.squarebomber.android;

import info.adamjsmith.squarebomber.SquareBomber;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, info.adamjsmith.squarebomber.gpgs.ActionResolver {
	private GameHelper gameHelper;
	protected static AdView adView;
	private final static int SHOW_ADS = 1;
	private final static int HIDE_ADS = 0;
	
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
		
		View gameView = initializeForView(new SquareBomber(this), config);
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
		
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);
		
		gameHelper.setup(this);

		setContentView(layout);
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
				
			});
		} catch (final Exception e) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		if(!getSignedInGPGS()) {
			loginGPGS();
		} 
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkIrdGHoOILEAIQAQ", score);
		getLeaderboardGPGS();
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);		
	}

	@Override
	public void getLeaderboardGPGS() {
		if(!getSignedInGPGS()) {
			loginGPGS();
		} 
		startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),"CgkIrdGHoOILEAIQAQ"), 100);		
	}

	@Override
	public void getAchievementsGPGS() {
		if(!getSignedInGPGS()) {
			loginGPGS();
		} 
		startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void onSignInFailed() {
		Toast.makeText(this, "Could not log into Google Game Services", Toast.LENGTH_LONG).show();		
	}

	@Override
	public void onSignInSucceeded() {
		Toast.makeText(this, "Signed into Google Play Games", Toast.LENGTH_LONG).show();		
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
}
