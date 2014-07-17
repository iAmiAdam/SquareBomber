package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerRenderer;
import info.adamjsmith.squarebomber.multiplayer.MultiplayerUpdater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MultiplayerGame implements Screen {
	SquareBomber game;
	MultiplayerRenderer renderer;
	MultiplayerUpdater updater;
	
	public MultiplayerGame(SquareBomber game) {
		this.game = game;
		Gdx.app.log("Multiplayer Game", "At the screen");
		updater = new MultiplayerUpdater(game);
		renderer = new MultiplayerRenderer(game, updater);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
