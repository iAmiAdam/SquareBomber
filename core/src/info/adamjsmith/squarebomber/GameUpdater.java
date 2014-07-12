package info.adamjsmith.squarebomber;

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
	
	public GameUpdater(SquareBomber game) {
		this.game = game;
		createWorld();
	}
	
	public void update() {
		
	}
	
	private void createWorld() {
		world = new World(new Vector2(0f,0f), false);
		
		MapObjects objects = game.assets.map.getLayers().get("Blocks").getObjects();
		
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : objects) {
			if (object instanceof TextureMapObject) {
				Shape shape = getRectangle((RectangleMapObject)object);
				
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);
				body.createFixture(shape, 1);
				bodies.add(body);
				
				shape.dispose();
			}
		}
	}
	
	private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x + rectangle.width), (rectangle.y + rectangle.height));
		polygon.setAsBox(rectangle.width, rectangle.height, size, 0.0f);		
		return polygon;
	}

}
