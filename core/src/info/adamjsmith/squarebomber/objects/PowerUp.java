package info.adamjsmith.squarebomber.objects;

import java.util.Random;

public class PowerUp extends StaticObject{
	
	public int ID = GameObject.IDPowerUp;
	public int type;
	
	public PowerUp(float x, float y) {
		Random rand = new Random();
		this.type = rand.nextInt((3 - 1) + 1 ) + 1;
		this.x = x;
		this.y = y;
	}
	
	public void givePower(Player player) {
		switch (this.type) {
		case 1:
			player.bombs++;
		case 2:
			player.power++;
		case 3:
			player.speed += 0.5;
		}
	}
}
