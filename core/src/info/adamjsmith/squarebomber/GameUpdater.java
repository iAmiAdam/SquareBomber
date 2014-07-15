package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Block;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;
import info.adamjsmith.squarebomber.objects.GameObject;
import info.adamjsmith.squarebomber.objects.Player;
import info.adamjsmith.squarebomber.objects.PowerUp;

import java.util.Iterator;

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
	
	public World world;
	public Player player;
	
	public Array<Crate> crates = new Array<Crate>();
	public Array<Bomb> bombs = new Array<Bomb>();
	public Array<Explosion> explosions = new Array<Explosion>();
	public Array<PowerUp> powerUps = new Array<PowerUp>();
	
	public GameUpdater(SquareBomber game) {
		this.game = game;
		createWorld();
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
				body.setUserData(new Block());
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
	
	private void updateBombs() {
		Iterator<Bomb> iter = bombs.iterator();
		while(iter.hasNext()) {
			Bomb bomb =  iter.next();
			bomb.update();
			if(bomb.exploded) {
				iter.remove();
				player.bombs++;
				Explosion ex = new Explosion((int)bomb.getX(), (int)bomb.getY(), bomb.power);
				explosions.add(ex);
				rayCast(ex);
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
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
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
	
	private void checkPowers() {
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
	
	private void rayCast(Explosion explosion) {
		ExplosionDetector callback = new ExplosionDetector((int)explosion.reach);
		Vector2 p1, p2;
		float x = explosion.x;
		float y = explosion.y;
		float reach = explosion.reach;
		p1 = new Vector2(x + 0.5f, y + 0.95f);
		p2 = new Vector2(x + 0.5f, y + 0.95f + reach);
		callback.setVars(p1);
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.9f, y + 0.5f);
		p2 = new Vector2(x + 0.9f + reach, y + 0.5f);
		callback.setVars(p1);
		callback.i++;
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.5f, y + 0.1f);
		p2 = new Vector2(x + 0.5f, (y + 0.1f) - reach);
		callback.setVars(p1);
		callback.i++;
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.1f,  y + 0.5f);
		p2 = new Vector2((x + 0.1f) - reach, y + 0.5f);
		callback.setVars(p1);
		callback.i++;
		world.rayCast(callback, p1, p2);	
		explosion.sides = callback.sides;
		callback = null;
		
	}
	
	private void sweepDeadBodies() {
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
