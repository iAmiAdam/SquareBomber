package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Block;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Opponent;
import info.adamjsmith.squarebomber.objects.Player;

import java.nio.ByteBuffer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.nextpeer.libgdx.NextpeerPlugin;

public class MultiplayerUpdater {
	
	private SquareBomber game;
	
	public World world;
	public Opponent[] opponents;
	public Player player;
	public Array<Crate> crates = new Array<Crate>();
	public Array<Block> blocks = new Array<Block>();
	
	private float lastMessage;
	private static float ppt;
	private static Rectangle rectangle;
	
	private Vector2[] spawns = {
			new Vector2(2.5f, 2.5f),
			new Vector2(15.5f, 12.5f),
			new Vector2(2.5f, 15.5f),
			new Vector2(15.5f, 15.5f),
			new Vector2(4.5f, 9.5f),
			new Vector2(13.5f, 10.5f)
	};
	
	
	public MultiplayerUpdater(SquareBomber game, String playerId) {
		this.game = game;
		createWorld();
		opponents = new Opponent[5];
		
		player = new Player(world, 3.5f, 2.5f);
		player.playerId = playerId;
		
		lastMessage = TimeUtils.nanoTime();
		
		sendSpawn();
	}
	
	public void update() {
		
		if ((TimeUtils.nanoTime() - lastMessage) / 1000000000.0 > 0.06f) {
			lastMessage = TimeUtils.nanoTime();
			sendUpdate();
		}
		player.update();
		world.step(1/45f, 4, 6);
	}	
	
	private void createWorld() {
		
		ppt = 128f;
		
		world = new World(new Vector2(0f,0f), false);
		
		MapObjects objects = game.assets.map.getLayers().get("BlockObjects").getObjects();
		
		for(MapObject object : objects) {
				Shape shape = getRectangle((RectangleMapObject)object);
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				bd.position.set((rectangle.x * 0.5f) / ppt, (rectangle.y * 0.5f) / ppt);
				Body body = world.createBody(bd);
				body.createFixture(shape, 1).setUserData(4);
				Block block = new Block(rectangle.x / ppt, rectangle.y / ppt);
				body.setUserData(block);
				blocks.add(block);
				shape.dispose();
				
		}
		
		objects = game.assets.map.getLayers().get("CrateObjects").getObjects();
		
		for(MapObject object : objects) {
			Shape shape = getRectangle((RectangleMapObject)object);
			
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			bd.position.set((rectangle.x * 0.5f) / ppt, (rectangle.y * 0.5f) / ppt);
			Body body = world.createBody(bd);
			body.createFixture(shape, 1).setUserData(1);
			Crate crate = new Crate(body);
			crate.x = rectangle.x / ppt;
			crate.y = rectangle.y / ppt;
			body.setUserData(crate);
			crates.add(crate);
			shape.dispose();
		}	
		
	}
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2(((rectangle.x + rectangle.width) * 0.5f) / ppt, ((rectangle.y + rectangle.height) * 0.5f) / ppt);
		polygon.setAsBox((rectangle.width * 0.5f) / ppt, (rectangle.height * 0.5f) / ppt, size, 0.0f);		
		return polygon;
	}
	
	private void sendSpawn() {
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(0).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public void sendUpdate() {
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(1).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.unreliablePushDataToOtherPlayers(message);
	}
	
	public void sendBomb() {
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(1).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public Opponent[] getOpponents() {
		return opponents;
	}
	
	public Array<Crate> getCrates() {
		return crates;
	}
}