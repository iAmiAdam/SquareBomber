package info.adamjsmith.squarebomber;

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
	
	public World world;
	public Player player;
	
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
		
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : objects) {
				Shape shape = getRectangle((RectangleMapObject)object);
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);
				body.createFixture(shape, 1);
				bodies.add(body);
				shape.dispose();
		}
		
		player = new Player(world, 4.5f, 4.5f);
		
		
	}
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt, (rectangle.y + rectangle.height * 0.5f) / ppt);
		polygon.setAsBox(rectangle.width * 0.5f / ppt, rectangle.height * 0.5f / ppt, size, 0.0f);		
		return polygon;
	}

}
