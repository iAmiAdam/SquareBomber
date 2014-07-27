package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Block;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;
import info.adamjsmith.squarebomber.objects.ExplosionPart;
import info.adamjsmith.squarebomber.objects.GameObject;
import info.adamjsmith.squarebomber.objects.Opponent;
import info.adamjsmith.squarebomber.objects.Player;
import info.adamjsmith.squarebomber.objects.PowerUp;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Random;

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
	private long seed;
	
	public String playerId;
	
	public World world;
	public Opponent[] opponents;
	public Player player;
	public Array<Block> blocks = new Array<Block>();
	public Array<Bomb> bombs = new Array<Bomb>();
	public Array<Crate> crates = new Array<Crate>();
	public Array<Explosion> explosions = new Array<Explosion>();
	public Array<PowerUp> powerUps = new Array<PowerUp>();
	
	private float lastMessage;
	private static float ppt;
	private static Rectangle rectangle;
	
	public Vector2[] spawns = {
			new Vector2(2.5f, 2.5f),
			new Vector2(15.5f, 12.5f),
			new Vector2(2.5f, 15.5f),
			new Vector2(15.5f, 15.5f),
			new Vector2(4.5f, 9.5f),
			new Vector2(13.5f, 10.5f)
	};
	
	
	public MultiplayerUpdater(SquareBomber game, String playerId, long seed) {
		this.game = game;
		this.seed = seed;
		this.playerId = playerId;
		
		createWorld();
		opponents = new Opponent[5];
		
		lastMessage = TimeUtils.nanoTime();
		spawn();
	}
	
	public void setUp() {
		NextpeerPlugin.registerToSynchronizedEvent("spawn", 15000);
	}
	
	private void spawn() {
		sendSpawn();
		//player.playerId = playerId;
	}
	
	public void update() {
		
		if ((TimeUtils.nanoTime() - lastMessage) / 1000000000.0 > 0.06f) {
			lastMessage = TimeUtils.nanoTime();
			sendUpdate();
		}
		player.update();
		updateCrates();
		updateBombs();
		updateExplosions();
		sweepDeadBodies();
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
		
		//for (int i = 0; i < spawns.length; i++) {
			//if (opponents[i] == null) {
				//player = new Player(world, spawns[i].x, spawns[i].y);
				//break;
			//}
		//}
		
		player = new Player(world, 2.5f, 2.5f);
		
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(0).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	private void sendUpdate() {
		byte[] message = new byte[12];
		message = ByteBuffer.allocate(12).putInt(1).putFloat(player.getX()).putFloat(player.getY()).array();
		NextpeerPlugin.unreliablePushDataToOtherPlayers(message);
	}
	
	private void sendBomb() {
		byte[] message = new byte[16];
		message = ByteBuffer.allocate(16).putInt(1).putFloat((int)player.getX()).putFloat((int)player.getY()).putInt(player.power).array();
		NextpeerPlugin.pushDataToOtherPlayers(message);
	}
	
	public void placeBomb() {
		if(player.bombs > 0) {
			bombs.add(new Bomb((int)player.getX(), (int)player.getY(), player.power));
			sendBomb();
			player.bombs--;
		}
	}
	
	private void updateBombs() {
		Iterator<Bomb> iter = bombs.iterator();
		while(iter.hasNext()) {
			Bomb bomb =  iter.next();
			bomb.update();
			if(bomb.exploded) {
				iter.remove();
				player.bombs++;
				Explosion ex = new Explosion(bomb.getX(), bomb.getY(), bomb.power);
				explosions.add(ex);
				ExplosionBuilder(ex);
				bomb = null;
			}
		}
	}
	
	private void updateExplosions() {
		Iterator<Explosion> iter = explosions.iterator();
		while(iter.hasNext()) {
			Explosion explosion = iter.next();
			explosion.update();
			if(explosion.over == true) {
				iter.remove();
				explosion = null;
			}
		}
	}
	
	private void updateCrates() {
		Iterator<Crate> iter = crates.iterator();
		while(iter.hasNext()) {
			Crate crate = iter.next();
			if(crate.exists == false) {
				if(crate.explode()) {
					placePowerUp(crate.x, crate.y);
				}
				iter.remove();
			}
		}
	}
	
	private void placePowerUp(float x, float y) {
		PowerUp powerUp = new PowerUp(x, y);
		powerUps.add(powerUp);
	}
	
	public Opponent[] getOpponents() {
		return opponents;
	}
	
	public Array<Crate> getCrates() {
		return crates;
	}
	
	public Array<Bomb> getBombs() {
		return bombs;
	}
	
	public Array<Explosion> getExplosions() {
		return explosions;
	}
	
	private void ExplosionBuilder(Explosion explosion) {
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		
		explosion.parts.add(new ExplosionPart(explosion.x, explosion.y, 0, 0, false));
		
		for (int i = 1; i <= explosion.reach; i++){
			Iterator<Block> blocksIter = blocks.iterator();
			while(blocksIter.hasNext()) {
				Block block = blocksIter.next();
				if (up == false) {
					if(block.x == explosion.x && block.y == explosion.y + i) {
						up = true;
					}
				}
				if (down == false) {
					if(block.x == explosion.x && block.y == explosion.y - i) {
						down = true;
					}
				}
				if (left == false) {
					if(block.x == explosion.x - 1 && block.y == explosion.y) {
						left = true;
					}
				}
				
				if (right == false) {
					if(block.x == explosion.x + 1 && block.y == explosion.y) {
						right = true;
					}
				}
			}
			Iterator<Crate> cratesIter = crates.iterator();
			while(cratesIter.hasNext()) {
				Crate crate = cratesIter.next();
				if (up == false){
					if(crate.x == explosion.x && crate.y == explosion.y + i) {
						up = true;
						crate.exists = false;
						explosion.parts.add(new ExplosionPart(explosion.x, explosion.y + i, 2, 1, false));
					}
				}
				if (down == false){
					if(crate.x == explosion.x && crate.y == explosion.y - i) {
						down = true;
						crate.exists = false;
						explosion.parts.add(new ExplosionPart(explosion.x, explosion.y - i, 2, 1, true));
					}
				}
				if (left == false){
					if(crate.x == explosion.x - i && crate.y == explosion.y) {
						left = true;
						crate.exists = false;
						explosion.parts.add(new ExplosionPart(explosion.x - i, explosion.y, 2, 0, false));
					}
				}
				if (right == false){
					if(crate.x == explosion.x + i && crate.y == explosion.y) {
						right = true;
						crate.exists = false;
						explosion.parts.add(new ExplosionPart(explosion.x + i, explosion.y, 2, 0, true));
					}
				}
			}
			if(up == false) {
				if(i == explosion.reach) {
					explosion.parts.add(new ExplosionPart(explosion.x, explosion.y + i, 2, 1, false));
				} else {
					explosion.parts.add(new ExplosionPart(explosion.x, explosion.y + i, 1, 1, false));
				}
			}
			if(down == false) {
				if(i == explosion.reach) {
					explosion.parts.add(new ExplosionPart(explosion.x, explosion.y - i, 2, 1, true));
				} else {
					explosion.parts.add(new ExplosionPart(explosion.x, explosion.y - i, 1, 1, true));
				}
			}
			if(left == false) {
				if(i == explosion.reach) {
					explosion.parts.add(new ExplosionPart(explosion.x - i, explosion.y , 2, 0, false));
				} else {
					explosion.parts.add(new ExplosionPart(explosion.x - i, explosion.y, 1, 0, false));
				}
			}
			if(right == false) {
				if(i == explosion.reach) {
					explosion.parts.add(new ExplosionPart(explosion.x + i, explosion.y, 2, 0, true));
				} else {
					explosion.parts.add(new ExplosionPart(explosion.x + i, explosion.y, 1, 0, true));
				}
			}
		}
	}
	
	public void sweepDeadBodies() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		Iterator<Body> iter = bodies.iterator(); 
		while(iter.hasNext()) {
			Body body = iter.next();
			if(body != null) {
				GameObject data = (GameObject) body.getUserData();
				if(!data.exists) {
					world.destroyBody(body);
					body.setUserData(null);
					body = null;
					data = null;
				}
			}
		}
	}
}