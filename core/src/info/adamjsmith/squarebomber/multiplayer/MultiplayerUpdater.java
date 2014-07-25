package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nextpeer.android.NextpeerTournamentStartData;

public class MultiplayerUpdater {
	
	World world;
	public Player[] players;
	public Player player;
	
	public MultiplayerUpdater(SquareBomber game, NextpeerTournamentStartData startData) {
		world = new World(new Vector2(0, 0), false);
		player = new Player(world, 2.5f, 2.5f);
		player.playerId = startData.currentPlayer.playerId;
	}
	
	public void update() {
		world.step(1/45f, 4, 6);
	}	
}