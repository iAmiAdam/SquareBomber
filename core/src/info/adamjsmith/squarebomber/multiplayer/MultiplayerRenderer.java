package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Opponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class MultiplayerRenderer {
	
	private MultiplayerUpdater world;
	private SquareBomber game;
	//private Player player;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private Matrix4 uiMatrix;
	
	private float stateTime = 0;
	private TextureRegion currentFrame;
	
	public MultiplayerRenderer(SquareBomber game, MultiplayerUpdater world) {
		this.game = game;
		this.world = world;
		
		batch = new SpriteBatch();
		renderer = new OrthogonalTiledMapRenderer(game.assets.map, 1/128f);
		camera = new OrthographicCamera(19f, 19f);
		camera.viewportWidth = 7f;
		camera.viewportHeight = 5f;
		
		uiMatrix = camera.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, 7f, 5f);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(world.player.getX(), world.player.getY(), 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();	
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawPlayer();
		drawOpponents();
		drawCrates();
		batch.setProjectionMatrix(uiMatrix);
		batch.draw(game.assets.controller, 6f, 0f, 1f, 1f);
		batch.end();
		
		stateTime += Gdx.graphics.getDeltaTime();
	}
	
	private void drawPlayer() {
		switch(world.player.direction) {
		case STOP:
			batch.draw(game.assets.playerStop, world.player.getX() - (world.player.getWidth() / 2), world.player.getY() - (world.player.getHeight() / 2), 
					world.player.getWidth() / 2, world.player.getHeight() / 2,
					world.player.getWidth(), world.player.getHeight(),
					1f, 1f, world.player.rotation);
			stateTime = 0;
			break;
		default:
			currentFrame = game.assets.playerWalk.getKeyFrame(stateTime, true);
			batch.draw(currentFrame, world.player.getX() - (world.player.getWidth() / 2), world.player.getY() - (world.player.getHeight() / 2), 
					world.player.getWidth() / 2, world.player.getHeight() / 2,
					world.player.getWidth(), world.player.getHeight(),
					1f, 1f, world.player.rotation);
			break;
		}
	}
	
	private void drawOpponents() {
		Opponent[] opponents = world.getOpponents();
		
		for (Opponent o: opponents) {
			if (o != null) {
				batch.draw(game.assets.playerStop, o.getX() - (o.getWidth() / 2), o.getY() - (o.getHeight() / 2), 0.75f, 0.75f);
			}
		}
	}
	
	private void drawCrates() {
		Array<Crate> crates = world.getCrates();
		
		for (Crate c: crates) {
			batch.draw(game.assets.crate, c.getX(), c.getY(), 0.99f, 0.99f);
		}
	}
	
	private void drawBombs() {
		Array<Bomb> bombs = world.getBombs();
		
		for (Bomb b: bombs) {
			batch.draw(game.assets.bomb, b.getX(), b.getY(), 1f, 1f);
		}
	}
}
