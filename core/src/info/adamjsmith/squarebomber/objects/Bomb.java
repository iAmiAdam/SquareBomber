package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.utils.TimeUtils;

public class Bomb extends StaticObject {
	private float placed;
	private int power;
	public boolean exploded;
	
	public Bomb(int x, int y, int power) {
		this.width = 1f;
		this.height = 1f;
		this.x = x;
		this.y = y;
		this.power = power;
		this.placed = TimeUtils.nanoTime();
		this.exploded = false;
	}
	
	public void update() {
		if ((TimeUtils.nanoTime() - this.placed) / 1000000000.0 > 4.5) {
			this.exploded = true;
		}
	}
}
