package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.gpgs.ActionResolver;
import info.adamjsmith.squarebomber.screens.LoadingScreen;
import info.adamjsmith.squarebomber.screens.MultiplayerGame;
import info.adamjsmith.squarebomber.screens.MultiplayerMenu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nextpeer.libgdx.NextpeerPlugin;
import com.nextpeer.libgdx.Tournaments;
import com.nextpeer.libgdx.TournamentsCallback;


public class SquareBomber extends Game implements ApplicationListener{
	public Assets assets = new Assets();
	public ActionResolver actionResolver;
	public Tournaments tournaments;
	
	public SquareBomber(ActionResolver actionResolver, Tournaments tournaments) {
		this.actionResolver = actionResolver;
		if (tournaments != null && tournaments.isSupported()) {
	        this.tournaments = tournaments;
	        this.tournaments.setTournamentsCallback(mNextpeerTournamentsCallback);
	    }
	}
	
	@Override
	public void create () {
		if(this.tournaments != null) {
			NextpeerPlugin.load(this.tournaments);
		}
		setScreen(new LoadingScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();	
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void pause() {
		super.pause();
	}
	
	private TournamentsCallback mNextpeerTournamentsCallback = new TournamentsCallback() {
		@Override
		public void onTournamentStart(long tournamentRandomSeed) {
			NextpeerPlugin.instance().lastKnownTournamentRandomSeed = tournamentRandomSeed;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					setScreen(new MultiplayerGame(SquareBomber.this));
				}
			});
		}

		@Override
		public void onTournamentEnd() {
			NextpeerPlugin.instance().lastKnownTournamentRandomSeed = 0;
			setScreen(new MultiplayerMenu(SquareBomber.this));
		}
	};
}