package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {
	
	private SquareBomber game;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public GameScreen(SquareBomber game) {
		this.game = game;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		camera.update();
	}

	@Override
	public void show() {
		
		renderer = new OrthogonalTiledMapRenderer(game.assets.map, 1/64f);
		
		camera = new OrthographicCamera(20f, 12f);
		camera.position.set(7f,5f, 0);
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
		renderer.dispose();
	}
}