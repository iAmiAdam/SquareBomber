package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.math.Vector2;

public class ExplosionPart {
	public Vector2 position;
	public int type;
	public int direction;
	public boolean flip;
	public ExplosionPart(float x, float y, int type, int direction, boolean flip) {
		this.position = new Vector2(x, y);
		this.type = type;
		this.direction = direction;
		this.flip = flip;
	}
}