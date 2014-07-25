package info.adamjsmith.squarebomber.objects;

public class Opponent extends DynamicObject {
	
	public String playerId;
	
	public Opponent(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = 0.75f;
		this.height = 0.75f;
		this.ID = GameObject.IDPlayer;
	}
}
