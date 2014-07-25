package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import java.nio.ByteBuffer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nextpeer.libgdx.NextpeerPlugin;

public class MultiplayerUpdater {
	
	public World world;
	public Player[] opponents;
	public Player player;
	
	public MultiplayerUpdater(SquareBomber game, String playerId) {
		world = new World(new Vector2(0, 0), false);
		opponents = new Player[5];
		
		player = new Player(world, 3.5f, 2.5f);
		player.playerId = playerId;
		
		sendSpawn();
	}
	
	public void update() {
		//sendUpdate();
		world.step(1/45f, 4, 6);
	}	
	
	public void sendSpawn() {
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(4).putInt(0).array();
		message = ByteBuffer.allocate(4).putFloat(player.getX()).array();
		message = ByteBuffer.allocate(4).putFloat(player.getY()).array();
		//NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public void sendUpdate() {
		byte[] message;
		message = ByteBuffer.allocate(8).putFloat(player.x).putFloat(player.y).array();
		
		NextpeerPlugin.unreliablePushDataToOtherPlayers(message);
	}
}