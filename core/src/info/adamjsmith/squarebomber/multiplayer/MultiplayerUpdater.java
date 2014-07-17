package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.utils.Array;

public class MultiplayerUpdater extends GameUpdater {
	
	public Array<Player> players;
	
	//public void GameUpdater(SquareBomber game) {
		
	//}
	
	public MultiplayerUpdater(SquareBomber game) {
		super(game);
		
	}

}