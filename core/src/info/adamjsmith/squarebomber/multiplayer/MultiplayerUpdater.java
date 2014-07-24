package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.nextpeer.android.NextpeerTournamentStartData;

public class MultiplayerUpdater extends GameUpdater {
	
	public Player[] players;
	public Player player;
	
	public MultiplayerUpdater(SquareBomber game, NextpeerTournamentStartData startData) {
		super(game);
		player = new Player(world, 2.5f, 2.5f);
		player.playerId = startData.currentPlayer.playerId;
	}
	
	@Override
	public void update() {
		world.step(1/45f, 4, 6);
	}	
}