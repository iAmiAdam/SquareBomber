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
	
	public Player player;
	
	public Player[] players;
	
	public MultiplayerUpdater(SquareBomber game) {
		super(game);
		this.players = players;
		createPlayers();
	}
	
	public void update() {
		updatePlayers();
		updateCrates();
		updateBombs();
		updateExplosions();
		sweepDeadBodies();
		checkPowers();
		world.step(1/45f, 6, 2);
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
	
	private void updatePlayers() {
		for (Player player: players) {
			player.update();
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Player getCurrentPlayer(String ID) {
		for(Player player: players) {
			if(player.mid == ID) {
				return player;
			}
		}
		return player;
	}
}