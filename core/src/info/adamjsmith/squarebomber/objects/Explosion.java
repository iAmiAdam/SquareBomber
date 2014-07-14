package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

public class Explosion extends StaticObject {
	
	private float reach;
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
				//TODO y
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
	};
	
	public void rayCast(World world) {
		sides[i] = reach;
		p1 = new Vector2(this.x + 0.5f, this.y + 0.95f);
		p2 = new Vector2(this.x + 0.5f, this.y + 0.95f + this.reach);
		world.rayCast(callback, p1, p2);
		i++;
		sides[i] = reach;
		p1 = new Vector2(this.x + 0.9f, this.y + 0.5f);
		p2 = new Vector2(this.x + 0.9f + reach, this.y + 0.5f);
		world.rayCast(callback, p1, p2);
		i++;
		sides[i] = reach;
		p1 = new Vector2(this.x + 0.5f, this.y + 0.1f);
		p2 = new Vector2(this.x + 0.5f, (this.y + 0.1f) - this.reach);
		world.rayCast(callback, p1, p2);
		i++;
		sides[i] = reach;
		p1 = new Vector2(this.x + 0.1f,  this.y + 0.5f);
		p2 = new Vector2((this.x + 0.1f) - this.reach, this.y + 0.5f);
		world.rayCast(callback, p1, p2);		
	}
}