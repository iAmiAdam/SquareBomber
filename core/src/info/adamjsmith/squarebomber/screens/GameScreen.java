package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.GameRenderer;
import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.input.GameInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	private SquareBomber game;
	
	private GameUpdater updater;
	private GameRenderer renderer;
	
	public GameScreen(SquareBomber game) {
		this.game = game;
		
		updater = new GameUpdater(game);
		renderer = new GameRenderer(game, updater);
	}
	@Override
	public void render(float delta) {
		renderer.render();
		updater.update();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GameInputProcessor(updater.player));
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
	}
}