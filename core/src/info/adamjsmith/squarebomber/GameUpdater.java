package info.adamjsmith.squarebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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
	
	public World world;
	
	private static float ppt;
	
	public GameUpdater(SquareBomber game) {
		this.game = game;
		createWorld();
	}
	
	public void update() {
		world.step(1/45f, 6, 2);
	}
	
	private void createWorld() {
		
		ppt = 64f;
		
		world = new World(new Vector2(0f,0f), false);
		
		MapObjects objects = game.assets.map.getLayers().get("BlockObjects").getObjects();
		
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : objects) {
				Shape shape = getRectangle((RectangleMapObject)object);
				Gdx.app.log("size", "works");
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);
				body.createFixture(shape, 1);
				bodies.add(body);
				
				shape.dispose();
		}
	}
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width) / ppt, (rectangle.y + rectangle.height) / ppt);
		polygon.setAsBox(rectangle.width / ppt, rectangle.height / ppt, size, 0.0f);		
		Gdx.app.log("size", String.valueOf( size));
		return polygon;
	}

}
