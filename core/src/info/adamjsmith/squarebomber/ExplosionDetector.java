package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.GameObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class ExplosionDetector implements RayCastCallback {
	
	private GameUpdater world;
	
	public float[] sides;
	private float x;
	private float y;
	private int i;
	public boolean over;
	private int reach;
	
	private Vector2 p1;
	
	public ExplosionDetector(GameUpdater world) {
		this.world = world;
		this.i = -1;
	}
	
	public void setVars(Vector2 p1) {
		this.p1 = p1;
	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		Body body = fixture.getBody();
		Vector2 pos = body.getPosition();
		i++;
		switch((Integer)fixture.getUserData()) {
		case 0:
			sides[i] = 1;
			//TODO kill player and get x and y
			break;
			
		case 1:
			GameObject data = (GameObject) body.getUserData();
			data.exists = false;
			if(x != point.x) {
				sides[i] = pos.x - p1.x;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				if (sides[i] < 2) sides[i] = 1;
			} else {
				sides[i] = pos.y - p1.y;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				if (sides[i] < 2) sides[i] = 1;
			}
			break;
			
		case 2: 
			//TODO explode other bomb
			break;
			
		case 3:
			break;
			
		case 4:
			if((pos.x - p1.x) - 1 > reach) {
				sides[i] = pos.x - p1.x;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);

			} else if((point.y - p1.y) - 1 > reach) {
				sides[i] = pos.y - p1.y;
				sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
			} else {
				sides[i] = 0;
			}
			break;
		default:
			sides[i] = reach;
		}
		return 0;
	}
}