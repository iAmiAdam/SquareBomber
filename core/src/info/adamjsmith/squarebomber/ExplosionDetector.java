package info.adamjsmith.squarebomber;

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
	
	private Vector2 p1;
	
	public ExplosionDetector(int reach) {
		this.i = 0;
		sides = new float[4];
		this.reach = reach;
		for (int j = 0; j < 4; j++) {
			sides[j] = reach;
		}
	}
	
	public void setVars(Vector2 p1) {
		this.p1 = p1;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		float r = 0;
		Body body = fixture.getBody();
		switch((Integer)fixture.getUserData()) {
		case 0:
			sides[i] = 1;
			//TODO kill player and get x and y
			r=1;
			break;
		case 1:
			GameObject data = (GameObject) body.getUserData();
			data.exists = false;
			if(p1.x != point.x) {
				sides[i] = point.x - p1.x;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				if (sides[i] < 2) sides[i] = 1;
			} else {
				sides[i] = point.y - p1.y;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				if (sides[i] < 2) sides[i] = 1;
			}
			r = 0; 
			break;
		case 2: 
			r = 1;
			break;
		case 3:
			r = 1;
			break;
		case 4:
			if((point.x - p1.x) - 1 > reach ) {
				sides[i] = point.x - p1.x;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);

			} else if((point.y - p1.y) - 1 > reach) {
				sides[i] = point.y - p1.y;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
			} else {
				sides[i] = 0;
			}
			r = 0;
			break;
		}
		return r;
	}
}