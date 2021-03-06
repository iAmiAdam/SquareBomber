package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
	private SquareBomber game;
	
	public LoadingScreen(SquareBomber game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if(game.assets.manager.update()) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		game.assets.load();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		game.assets.dispose();
	}

}