package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.input.MultiplayerInputProcessor;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerRenderer;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerUpdater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;


public class MultiplayerGame implements Screen {
	SquareBomber game;
	MultiplayerRenderer renderer;
	MultiplayerUpdater updater;
	
	public MultiplayerGame(SquareBomber game, String playerId, long seed) {
		this.game = game;
		Gdx.app.log("In The", "screen");
		updater = new MultiplayerUpdater(game, playerId, seed);
		renderer = new MultiplayerRenderer(game, updater);
		game.mNextpeerTournamentsCallback.setWorld(updater);
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
		Gdx.input.setInputProcessor(new MultiplayerInputProcessor(updater));
		
	}

	@Override
	public void hide() {
		dispose();
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
		updater.dispose();
		renderer.dispose();
	}

}
