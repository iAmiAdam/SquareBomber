package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import java.nio.ByteBuffer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nextpeer.android.Nextpeer;
import com.nextpeer.android.NextpeerTournamentStartData;
import com.nextpeer.libgdx.NextpeerPlugin;

public class MultiplayerUpdater {
	
	public World world;
	public Player[] opponents;
	public Player player;
	
	public MultiplayerUpdater(SquareBomber game) {
		world = new World(new Vector2(0, 0), false);
		
		//sendSpawn();
		
		player = new Player(world, 2.5f, 2.5f);
		player.playerId = startData.currentPlayer.playerId;
	}
	
	public void update() {
		sendUpdate();
		world.step(1/45f, 4, 6);
	}	
	
	public void sendSpawn() {
		byte[] message;
		message = ByteBuffer.allocate(12).putInt(0).putFloat(player.x).putFloat(player.y).array();
		NextpeerPlugin.instance();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public void sendUpdate() {
		byte[] message;
		message = ByteBuffer.allocate(8).putFloat(player.x).putFloat(player.y).array();
		
		NextpeerPlugin.unreliablePushDataToOtherPlayers(message);
	}
}