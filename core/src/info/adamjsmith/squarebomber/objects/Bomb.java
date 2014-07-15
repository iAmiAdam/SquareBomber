package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class Bomb extends StaticObject {
	private float placed;
	public int power;
	public boolean exploded;
	
	public Bomb(int x, int y, int power) {
		this.width = 1f;
		this.height = 1f;
		this.x = x;
		Gdx.app.log("x", String.valueOf(x));
		this.y = y;
		Gdx.app.log("y", String.valueOf(y));
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
