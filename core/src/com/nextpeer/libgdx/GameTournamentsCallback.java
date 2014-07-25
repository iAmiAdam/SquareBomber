package com.nextpeer.libgdx;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerUpdater;
import info.adamjsmith.squarebomber.objects.Player;

import java.io.ByteArrayInputStream;

import com.nextpeer.android.NextpeerTournamentCustomMessage;
import com.nextpeer.android.NextpeerTournamentStartData;

public class GameTournamentsCallback extends TournamentsCallback {
	
	SquareBomber game;
	MultiplayerUpdater world;
	
	public GameTournamentsCallback(SquareBomber game, MultiplayerUpdater world) {
		this.game = game;
		this.world = world;
	}

	@Override
	public void onTournamentStart(NextpeerTournamentStartData startData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTournamentEnd() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onReceiveTournamentCustomMessage(NextpeerTournamentCustomMessage message) {
		ByteArrayInputStream bArray = new ByteArrayInputStream(message.customMessage);
		int type = bArray.read(message.customMessage, 0, 4);
		switch (type) {
		case 0:
			float x = bArray.read(message.customMessage, 4, 4);
			float y = bArray.read(message.customMessage, 8, 4);
			world.opponents[world.opponents.length+1] = new Player(world.world, x, y);
			world.opponents[world.opponents.length+1].playerId = message.playerId;
		}
		
	}
	
	@Override
	public void onReceiveUnreliableTournamentCustomMessage(NextpeerTournamentCustomMessage message) {
		
	}
	
	@Override
	public void onReceiveSynchronizedEvent(String name) {
		
	}
	
}
