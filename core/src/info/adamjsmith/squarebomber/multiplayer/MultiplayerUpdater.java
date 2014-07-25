package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Opponent;
import info.adamjsmith.squarebomber.objects.Player;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nextpeer.libgdx.NextpeerPlugin;

public class MultiplayerUpdater {
	
	public World world;
	public Opponent[] opponents;
	public Player player;
	
	public MultiplayerUpdater(SquareBomber game, String playerId) {
		world = new World(new Vector2(0, 0), false);
		opponents = new Opponent[5];
		
		player = new Player(world, 2.5f, 2.5f);
		player.playerId = playerId;
		
		sendSpawn();
	}
	
	public void update() {
		//sendUpdate();
		world.step(1/45f, 4, 6);
	}	
	
	public void sendSpawn() {
		
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(0).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public void sendUpdate() {
		byte[] message;
		message = ByteBuffer.allocate(8).putFloat(player.x).putFloat(player.y).array();
		
		NextpeerPlugin.unreliablePushDataToOtherPlayers(message);
	}
	
	public Opponent[] getOpponents() {
		return opponents;
	}
}