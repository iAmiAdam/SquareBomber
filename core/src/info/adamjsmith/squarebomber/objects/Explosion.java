package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	public float reach;
	private float created;
	public float[] sides;
	private int i;
	public boolean over;
	
	private Vector2 p1, p2;
	
	public Explosion(float x, float y, int power) {
		this.x = x;
		this.y = y;
		this.reach = power;
		this.created = TimeUtils.nanoTime();
		this.over = false;
		sides = new float[4];
		i = 0;
	}
	
	public void draw() {
		
	}
	
	public void update() {
		if((TimeUtils.nanoTime() - this.created) / 1000000000.0f > 1f) {
			this.over = true;
		}
	}
}