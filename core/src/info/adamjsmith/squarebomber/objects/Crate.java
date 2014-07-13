package info.adamjsmith.squarebomber.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Crate extends StaticObject {
	
	public Body body;
	
	public Crate(Body body) {
		this.body = body;
		Vector2 pos = body.getPosition();
		this.x = pos.x;
		this.y = pos.y;
		Gdx.app.log("x", String.valueOf(x));
	}
}
