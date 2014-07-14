package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	public float reach;
	private float created;
	public float[] sides;
	public boolean over;
	
	public Explosion(float x, float y, int power) {
		this.x = x;
		this.y = y;
		this.reach = power;
		this.created = TimeUtils.nanoTime();
		this.over = false;
		sides = new float[4];
	}
	
	public void update() {
		if((TimeUtils.nanoTime() - this.created) / 1000000000.0f > 1f) {
			this.over = true;
		}
	}
}