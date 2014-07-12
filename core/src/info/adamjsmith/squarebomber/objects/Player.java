package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	
	private float ppt = 128;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	public Direction direction;
	
	public float speed;
	public int bombs;
	public int power;
	
	
	public Player(World world, float x, float y) {
		this.width = 0.75f;
		this.height = 0.75f;
		this.x =  x;
		this.y = y;
		
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((this.width * 0.5f) / ppt, (this.height * 0.5f) / ppt);
		polygon.setAsBox(this.width * 0.5f, this.height * 0.5f, size, 0.0f);
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(this.x, this.y);
		Body body = world.createBody(bd);
		body.createFixture(polygon, 1);
	}
}