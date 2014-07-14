package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	private int reach;
	private float created;
	public int[] sides;
	private int i;
	public boolean over;
	
	public Explosion(float x, float y, int power) {
		this.x = x;
		this.y = y;
		this.reach = power;
		this.created = TimeUtils.nanoTime();
		this.over = false;
		sides = new int[4];
		i = 0;
	}
	
	public void draw() {
		
	}
	
	public void update() {
		if(TimeUtils.nanoTime() - this.created / 1000000000.0f > 5f) {
			this.over = true;
		}
	}
	
	public RayCastCallback callback = new RayCastCallback() {

		@Override
		public float reportRayFixture(Fixture fixture, Vector2 point,
				Vector2 normal, float fraction) {
			Body body = fixture.getBody();
			Vector2 pos = body.getPosition();
			switch((Integer)fixture.getUserData()) {
			case 0:
				sides[i] = 1;
				//TODO kill player and get x and y
				break;
				
			case 1:
				
				//TODO smash crate
				sides[i] = 1;
				if(x != pos.x) {
					sides[i] = (int) (point.x - x);
					sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				} else {
					sides[i] = (int) (point.y - 1 - y);
					sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				}
				break;
				
			case 2: 
				sides[i] = 1;
				//TODO explode other bomb
				break;
				
			case 3:
				sides[i] = 1;
				break;
				
			case 4:
				//TODO y
				if(x != pos.x) {
					sides[i] = (int) (point.x - x);
					sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				} else {
					sides[i] = (int) (point.y - 1 - y);
					sides[i] = (sides[i] < 0 ? -sides[i] : sides[i]);
				}
				break;
			default:
				sides[i] = 1;
			}
			return 0;
		}
	};
	
	public void rayCast(World world) {
		Vector2 p1 = new Vector2(this.x, this.y + 1f);
		Vector2 p2 = new Vector2(this.x, this.y + this.reach);
		world.rayCast(callback, p1, p2);
		i++;
		p1 = new Vector2(this.x + 1f, this.y);
		p2 = new Vector2(this.x + this.reach, this.y + 1f);
		world.rayCast(callback, p1, p2);
		i++;
		p1 = new Vector2(this.x, this.y);
		p2 = new Vector2(this.x + 1f, this.y - this.reach);
		world.rayCast(callback, p1, p2);
		i++;
		p1 = new Vector2(this.x, this.y);
		p2 = new Vector2(this.x - this.reach, this.y + 1);
		world.rayCast(callback, p1, p2);
		
	}
	
}
