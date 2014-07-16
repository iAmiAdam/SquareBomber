package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends DynamicObject {
	
	private BodyDef bd;
	private Body body;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT, STOP
	}
	public Direction direction;
	public int rotation;
	
	public float speed;
	public int bombs;
	public int power;
	
	
	public Player(World world, float x, float y) {
		this.width = 0.75f;
		this.height = 0.75f;
		this.x =  x;
		this.y = y;
		this.speed = 5f;
		this.bombs = 1;
		this.power = 1;
		
		CircleShape circle = new CircleShape();
		circle.setRadius(0.4f);
		
		bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(this.x, this.y);
		body = world.createBody(bd);
		body.setFixedRotation(true);
		body.createFixture(circle, 1).setUserData(GameObject.IDPlayer);
		body.setUserData(this);
		
		this.direction = Direction.STOP;
	}
	
	
	public void update() {
		Vector2 pos = body.getPosition();
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public void move(Direction direction) {
		switch (direction) {
		case UP:
			this.direction = Direction.UP;
			body.setLinearVelocity(0f, speed);
			rotation = 0;
			break;
		case DOWN:
			this.direction = Direction.DOWN;
			body.setLinearVelocity(0f, -speed);
			rotation = 180;
			break;
		case LEFT:
			this.direction = Direction.LEFT;
			body.setLinearVelocity(-speed, 0f);
			rotation = 270;
			break;
		case RIGHT:
			this.direction = Direction.RIGHT;
			body.setLinearVelocity(speed, 0f);
			rotation = 90;
			break;
		case STOP:
			this.direction = Direction.STOP;
			body.setLinearVelocity(0f, 0f);
		}
	}
}