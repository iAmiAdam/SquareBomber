package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

public class MultiplayerRenderer {
	
	private MultiplayerUpdater world;
	private SquareBomber game;
	private Player player;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private Matrix4 uiMatrix;
	
	private float stateTime;
	private TextureRegion currentFrame;
	
	public MultiplayerRenderer(SquareBomber game, MultiplayerUpdater world, String myID) {
		this.world = world;
		this.game = game;
		this.player = world.getCurrentPlayer(myID);
		this.batch = new SpriteBatch();
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
		camera.position.set(2.5f, 2.5f, 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		Gdx.app.log("In The", "Renderer");
		
		
	}
	
	private void drawPlayers() {
		Player[] players = world.getPlayers();
		for (Player player : players) {
			switch(player.direction) {
			case STOP:
				batch.draw(game.assets.playerStop, player.getX() - (player.getWidth() / 2), player.getY() - (player.getHeight() / 2), 
						player.getWidth() / 2, player.getHeight() / 2,
						player.getWidth(), player.getHeight(),
						1f, 1f, player.rotation);
				stateTime = 0;
				break;
			default:
				currentFrame = game.assets.playerWalk.getKeyFrame(stateTime, true);
				batch.draw(currentFrame, player.getX() - (player.getWidth() / 2), player.getY() - (player.getHeight() / 2), 
						player.getWidth() / 2, player.getHeight() / 2,
						player.getWidth(), player.getHeight(),
						1f, 1f, player.rotation);
				break;
		}
		}
	}
}
