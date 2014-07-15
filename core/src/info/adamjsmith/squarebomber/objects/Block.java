package info.adamjsmith.squarebomber.objects;

public class Block extends StaticObject {
	
	public float x, y;
	
	public Block(float x, float y) {
		this.ID = GameObject.IDBlock;
		this.x = x;
		this.y = y;
	}
}