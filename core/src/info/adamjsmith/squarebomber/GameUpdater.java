package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Player;

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
	
	public GameUpdater(SquareBomber game) {
		this.game = game;
		createWorld();
	}
	
	public void update() {
		player.update();
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
				body.createFixture(shape, 1);
				shape.dispose();
		}
		
		objects = game.assets.map.getLayers().get("CrateObjects").getObjects();
		
		for(MapObject object : objects) {
			Shape shape = getRectangle((RectangleMapObject)object);
			
			BodyDef bd = new BodyDef();
			bd.type = BodyType.StaticBody;
			bd.position.set((rectangle.x * 0.5f) / ppt, (rectangle.y * 0.5f) / ppt);
			Body body = world.createBody(bd);
			body.createFixture(shape, 1);
			Crate crate = new Crate(body);
			crate.x = rectangle.x / ppt;
			crate.y = rectangle.y / ppt;
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
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2(((rectangle.x + rectangle.width) * 0.5f) / ppt, ((rectangle.y + rectangle.height) * 0.5f) / ppt);
		polygon.setAsBox((rectangle.width * 0.5f) / ppt, (rectangle.height * 0.5f) / ppt, size, 0.0f);		
		return polygon;
	}
	
	public void placeBomb() {
		bombs.add(new Bomb((int)player.getX(), (int)player.getY(), player.power));
	}

}
