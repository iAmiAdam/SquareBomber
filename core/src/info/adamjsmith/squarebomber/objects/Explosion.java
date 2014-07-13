package info.adamjsmith.squarebomber.objects;

public class Explosion extends StaticObject {
	
	private int reach;
	
	public Explosion(int x, int y, int power) {
		this.x = x;
		this.y = y;
		this.reach = power;
		
		
	}
	
	public void draw() {
		
	}
}
