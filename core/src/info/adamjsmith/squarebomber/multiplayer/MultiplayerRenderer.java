package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameRenderer;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

public class MultiplayerRenderer extends GameRenderer {
	
	private MultiplayerUpdater world;
	private SquareBomber game;
	//private Player player;
	
	//private SpriteBatch batch;
	//private OrthographicCamera camera;
	//private OrthogonalTiledMapRenderer renderer;
	//private Matrix4 uiMatrix;
	
	private float stateTime;
	private TextureRegion currentFrame;
	
	public MultiplayerRenderer(SquareBomber game, MultiplayerUpdater world) {
		super(game, world);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(2.5f, 2.5f, 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();	
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawPlayer();
		batch.setProjectionMatrix(uiMatrix);
		batch.draw(game.assets.controller, 6f, 0f, 1f, 1f);
		batch.end();
	}
}
