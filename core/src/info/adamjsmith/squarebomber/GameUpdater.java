package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Block;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;
import info.adamjsmith.squarebomber.objects.ExplosionPart;
import info.adamjsmith.squarebomber.objects.GameObject;
import info.adamjsmith.squarebomber.objects.Player;
import info.adamjsmith.squarebomber.objects.PowerUp;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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

public class GameUpdater {
	
	private SquareBomber game;
	private static float ppt;
	private static Rectangle rectangle;
	private Array<Block> blocks = new Array<Block>();
	
	public World world;
	public Player player;
	
	public Array<Crate> crates = new Array<Crate>();
	public Array<Bomb> bombs = new Array<Bomb>();
	public Array<Explosion> explosions = new Array<Explosion>();
	public Array<PowerUp> powerUps = new Array<PowerUp>();
	
	public GameUpdater(SquareBomber game) {
		this.game = game;
		createWorld();
		createPlayer();
	}
	
	public void update() {
		player.update();
		updateCrates();
		updateBombs();
		updateExplosions();
		sweepDeadBodies();
		checkPowers();
		world.step(1/45f, 6, 2);
	}
	
	public void createWorld() {
		
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
	
	public void createPlayer() {
		player = new Player(world, 2.5f, 2.5f);
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
	
	public Array<PowerUp> getPowerUps() {
		return powerUps;
	}
	
	public void updateBombs() {
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
	
	public void updateExplosions() {
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
	
	public void updateCrates() {
		Iterator<Crate> iter = crates.iterator();
		while(iter.hasNext()) {
			Crate crate = iter.next();
			if(crate.exists == false) {
				if(crate.explode(400)) {
					placePowerUp(crate.x, crate.y);
				}
				iter.remove();
			}
		}
	}
	
	public static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2(((rectangle.x + rectangle.width) * 0.5f) / ppt, ((rectangle.y + rectangle.height) * 0.5f) / ppt);
		polygon.setAsBox((rectangle.width * 0.5f) / ppt, (rectangle.height * 0.5f) / ppt, size, 0.0f);		
		return polygon;
	}
	
	public void placeBomb() {
		if(player.bombs > 0) {
			bombs.add(new Bomb((int)player.getX(), (int)player.getY(), player.power));
			player.bombs--;
		}
	}
	
	private void placePowerUp(float x, float y) {
		PowerUp powerUp = new PowerUp(x, y);
		powerUps.add(powerUp);
	}
	
	public void checkPowers() {
		Iterator<PowerUp> iter = powerUps.iterator();
		while(iter.hasNext()) {
			PowerUp powerUp = iter.next();
			if(player.getX() > powerUp.getX() && player.getX() < powerUp.getX() + 1f && player.getY() > powerUp.getY() && player.getY() < powerUp.getY() + 1f ||
			   player.getX() + 1f > powerUp.getX() && player.getX() + 1f < powerUp.getX() + 1f && player.getY() + 1f > powerUp.getY() && player.getY() + 1f < powerUp.getY() +1f ) {
				powerUp.givePower(player);
				iter.remove();
				powerUp = null;
			}
		}
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
					Gdx.app.log("blockx", String.valueOf(block.x));
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
	
	public void dispose() {
		world.dispose();
	}

}
