package com.nextpeer.libgdx;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.screens.MultiplayerGame;
import info.adamjsmith.squarebomber.screens.MultiplayerMenu;

import com.badlogic.gdx.Gdx;
import com.nextpeer.android.NextpeerTournamentStartData;

public class MenuTournamentsCallback extends TournamentsCallback {
	
	SquareBomber game;
	
	public MenuTournamentsCallback(SquareBomber game) {
		this.game = game;
	}

	@Override
	public void onTournamentStart(final NextpeerTournamentStartData startData) {
		NextpeerPlugin.instance().lastKnownTournamentRandomSeed = startData.tournamentRandomSeed;
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				
				game.setScreen(new MultiplayerGame(game, startData));
			}
		});
	}

	@Override
	public void onTournamentEnd() {
		NextpeerPlugin.instance().lastKnownTournamentRandomSeed = 0;
		game.setScreen(new MultiplayerMenu(game));
	}

}
