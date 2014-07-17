package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.math.Vector2;

public class MultiplayerUpdater extends GameUpdater {
	
	Vector2[] spawns = {
			new Vector2(2.5f, 2.5f),
			new Vector2(3.5f, 3.5f)
	};
	
	public Player[] players;
	
	public MultiplayerUpdater(SquareBomber game, Player[] players, String myID) {
		super(game);
		this.players = players;
		createPlayers();
	}
	
	public void receiveMessage(byte[] message) {
		
	}
	
	private void createPlayers() {
		int i = 0;
		for (Player player: players) {
			player.create(world, spawns[i]);
			i++;
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
}