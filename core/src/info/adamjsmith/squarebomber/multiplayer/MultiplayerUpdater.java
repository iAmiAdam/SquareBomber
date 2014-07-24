package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MultiplayerUpdater {
	
	Vector2[] spawns = {
			new Vector2(2.5f, 2.5f),
			new Vector2(3.5f, 3.5f)
	};
	
	public Player player;
	
	
	public MultiplayerUpdater(SquareBomber game) {
		//super(game);
	}
	
	
	public void update() {
		
	}	
}