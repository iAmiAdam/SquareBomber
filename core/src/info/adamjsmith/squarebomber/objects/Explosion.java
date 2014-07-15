package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	public int reach;
	private float created;
	public float[] sides;
	public boolean over;
	public Array<ExplosionPart> parts = new Array<ExplosionPart>();
	
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