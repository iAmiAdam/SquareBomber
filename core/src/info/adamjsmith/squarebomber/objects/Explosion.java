package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	private int reach;
	private float created;
	public boolean over;
	
	public BodyDef bd;
	public PolygonShape shape;
	public Body body;
	
	public Explosion(int x, int y, int power) {
		this.x = x;
		this.y = y;
		this.reach = power;
		this.created = TimeUtils.nanoTime();
		this.over = false;
		
		bd = new BodyDef();
	    bd.type = BodyType.StaticBody;
		bd.position.set(this.x, this.y);
		
		shape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[9];
		vertices[0] = new Vector2(0f, 0f);
		vertices[1] = new Vector2(-this.reach, 0f);
		vertices[2] = new Vector2(-this.reach, 1f);
		vertices[3] = new Vector2(0f, 1f);
		vertices[4] = new Vector2(0f, 1f + this.reach);
		vertices[5] = new Vector2(1f, 1f + this.reach);
		vertices[6] = new Vector2(1f, 1f);
		vertices[7] = new Vector2(1f + this.reach, 1f);
		vertices[8] = new Vector2(1f + this.reach, 0f);	
		
		shape.set(vertices);
		
	}
	
	public void draw() {
		
	}
	
	public void update() {
		if(TimeUtils.nanoTime() - this.created / 1000000000.0f > 1f) {
			this.over = true;
		}
	}
}
