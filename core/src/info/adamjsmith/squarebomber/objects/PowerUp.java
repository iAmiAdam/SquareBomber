package info.adamjsmith.squarebomber.objects;

import java.util.Random;

public class PowerUp extends StaticObject{
	
	public int ID = GameObject.IDPowerUp;
	public int type;
	
	public PowerUp() {
		Random rand = new Random();
		type = rand.nextInt((3 - 1) + 1 ) + 1;
	}
}
