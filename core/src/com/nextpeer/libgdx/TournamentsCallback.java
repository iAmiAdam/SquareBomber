//
//  Nextpeer libGDX plugin
//  http://www.nextpeer.com
//
//  Created by Nextpeer development team.
//  Copyright (c) 2014 Innobell, Ltd. All rights reserved.
//

package com.nextpeer.libgdx;

import com.badlogic.gdx.Gdx;

import info.adamjsmith.squarebomber.multiplayer.MultiplayerUpdater;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Opponent;

/**
 * Nextpeer platform Callback class allow you to get notified of Nextpeer-related events.
 */
public abstract class TournamentsCallback {
	
	public MultiplayerUpdater world;
	
	public void setWorld(MultiplayerUpdater world) {
		this.world = world;
	}
	
	
	/**
	 * Called when the tournament is supposed to start.
	 * Here is the place to the launch the game's activity.
	 * @param playerId 
	 * @param tournamentRandomSeed Used when the game 'generate' the level with its random function -> so all players will have the same world
	 * @param opponents 
	 * @param numberOfPlayers 
	 * @param currentPlayer 
	 */
    public abstract void onTournamentStart(long TournamentRandomSeed, String playerId);

    /**
     * This method is invoked whenever the current tournament has finished 
	 * @note Should switch to the main menu scene
     */
    public abstract void onTournamentEnd();
    
	/**
	 * This method will be called when Nextpeer has received a buffer from another player.
 	 * You can use these buffers to create custom notifications and events while engaging the other players
 	 * that are currently playing. The container that is passed contains the sending user's name and image as well as the message being sent.
	 * @param message The custom message {@link NextpeerTournamentCustomMessage}
	 */
	public void onReceiveTournamentCustomMessage(float x, float y, String playerId) {
		world.opponents[0] = new Opponent(x, y, playerId);
	}
    
	/**
	 * This method will be called when Nextpeer has received a buffer from another player.
 	 * The message would have come from the unreliable data channel. This channel is mostly used to provide game state updates between players.
 	 * The container that is passed contains the sending user's name and image as well as the message being sent.
	 * @param message The custom message {@link NextpeerTournamentCustomMessage}
	 */
	public void onReceiveUnreliableTournamentCustomMessage(float x, float y, String playerId) {
		for(int i = 0; i < world.opponents.length; i++) {
			if(world.opponents[i] != null) {
				if (world.opponents[i].playerId.equals(playerId)) {
					world.opponents[i].x = x;
					world.opponents[i].y = y;
				}
			}
		}
	}
	
	public void onReceiveTournamentCustomMessage(float x, float y, int power) {
		world.bombs.add(new Bomb((int)x, (int)y, power));
	}
    
	/**
	 * This method is invoked when a synchronized event has triggered by the platform.
	 * @param name The name of the synchronized event as it was registered in {@code Tournaments#registerToSynchronizedEvent(String, int)}.
	 */
	public void onReceiveSynchronizedEvent(String name) {}
    
    /**
     * Guide the SDK whether the game supports the given tournament. This method s called once for each tournament UUID that is present in the dashboard.
     * If this particular game version doesn't support a certain tournament, use this method to tell the SDK to disable it.
     * @param tournamentUuid The tournament identifier as it available on Nextpeer's developers dashboard (https://developers.nextpeer.com) for the game.
     * @return true if you support this tournament, false otherwise (default true).
     */
    public boolean onSupportsTournament(String tournamentUuid) { return true; }

}
