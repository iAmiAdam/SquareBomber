package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	
	private float ppt = 128;
	private BodyDef bd;
	private Body body;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT, STOP
	}
	public Direction direction;
	
	public float speed;
	public int bombs;
	public int power;
	
	
	public Player(World world, float x, float y) {
		this.width = 0.65f;
		this.height = 0.45f;
		this.x =  x;
		this.y = y;
		this.speed = 5f;
		this.bombs = 1;
		this.power = 1;
		
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((this.width * 0.5f) / ppt, (this.height * 0.5f) / ppt);
		polygon.setAsBox(this.width * 0.5f, this.height * 0.5f, size, 0.0f);
		
		bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(this.x, this.y);
		body = world.createBody(bd);
		body.setFixedRotation(true);
		body.createFixture(polygon, 1);
	}
	
	
	public void update() {
		Vector2 pos = body.getPosition();
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public void move(Direction direction) {
		switch (direction) {
		case UP:
			body.setLinearVelocity(0f, speed);
			break;
		case DOWN:
			body.setLinearVelocity(0f, -speed);
			break;
		case LEFT:
			body.setLinearVelocity(-speed, 0f);
			break;
		case RIGHT:
			body.setLinearVelocity(speed, 0f);
			break;
		case STOP:
			body.setLinearVelocity(0f, 0f);
		}
	}
}