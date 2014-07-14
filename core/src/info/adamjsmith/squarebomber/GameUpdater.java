package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Block;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;
import info.adamjsmith.squarebomber.objects.GameObject;
import info.adamjsmith.squarebomber.objects.Player;

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
				rayCast(ex.reach, ex.x, ex.y, ex);
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
			if(crate == null) {
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
	
	private void rayCast(float reach, float x, float y, Explosion explosion) {
		ExplosionDetector callback = new ExplosionDetector(this);
		Vector2 p1, p2;
		p1 = new Vector2(x + 0.5f, y + 0.95f);
		p2 = new Vector2(x + 0.5f, y + 0.95f + reach);
		callback.setVars(p1);
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.9f, y + 0.5f);
		p2 = new Vector2(x + 0.9f + reach, y + 0.5f);
		callback.setVars(p1);
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.5f, y + 0.1f);
		p2 = new Vector2(x + 0.5f, (y + 0.1f) - reach);
		callback.setVars(p1);
		world.rayCast(callback, p1, p2);
		p1 = new Vector2(x + 0.1f,  y + 0.5f);
		p2 = new Vector2((x + 0.1f) - reach, y + 0.5f);
		callback.setVars(p1);
		world.rayCast(callback, p1, p2);	
		explosion.sides = callback.sides;
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
				}
			}
		}
	}

}
