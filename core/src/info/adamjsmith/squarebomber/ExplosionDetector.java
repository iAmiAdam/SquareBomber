package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;
import info.adamjsmith.squarebomber.objects.GameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class ExplosionDetector implements RayCastCallback {
	
	public float[] sides;
	public int i;
	public boolean over;
	private int reach;
	private float x;
	private float y;
	
	Vector2 p1;
	
	public ExplosionDetector(Explosion explosion) {
		this.i = 0;
		sides = new float[4];
		this.reach = explosion.reach;
		this.x = explosion.x;
		this.y = explosion.y;
		for (int j = 0; j < 4; j++) {
			sides[j] = this.reach;
		}
	}
	
	public void setVar(Vector2 p1) {
		this.p1 = p1;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		float r = 0;
		Body body = fixture.getBody();
		Vector2 pos = body.getPosition();
		switch((Integer)fixture.getUserData()) {
		case 0:
			
			//TODO kill player and get x and y
			r=1;
			break;
		case 1:
			Crate crate = (Crate) body.getUserData();
			crate.exists = false;
			switch(i) {
			case 0:
				sides[i] = crate.y - y;
				break;
			case 1:
				sides[i] = crate.x - x;
				break;
			case 2:
				sides[i] = y - crate.y;
				break;
			case 3:
				sides[i] = x - crate.x;
				break;
			}
			r = fraction; 
			break;
		case 2: 
			r = 1;
			break;
		case 3:
			r = 1;
			break;
		case 4:
			if (pos.x > x) {
				sides[i] = pos.x - x;
			} else if(pos.x < x) {
				sides[i] = x - pos.x;
			} else if(pos.y > y) {
				sides[i] = pos.y - y;
			} else if(pos.y < y) {
				sides[i] = y - pos.y;
			}
			sides[i] =- 1;
			r = 0;
			break;
		}
		return r;
	}
}