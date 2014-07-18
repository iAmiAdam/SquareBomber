package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.input.GameInputProcessor;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerRenderer;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerUpdater;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MultiplayerGame implements Screen {
	SquareBomber game;
	MultiplayerRenderer renderer;
	MultiplayerUpdater updater;
	
	public MultiplayerGame(SquareBomber game, Player[] players, String myID, String roomID) {
		this.game = game;
		Gdx.app.log("Multiplayer Game", "At the screen");
		updater = new MultiplayerUpdater(game, players, myID);
		renderer = new MultiplayerRenderer(game, updater, myID);
	}

	@Override
	public void render(float delta) {
		updater.update();
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GameInputProcessor(updater));
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
