package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Crate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class GameRenderer {
	
	private SquareBomber game;
	private GameUpdater world;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public GameRenderer(SquareBomber game, GameUpdater world) {
		this.world = world;
		this.game = game;
		
		batch = new SpriteBatch();
		renderer = new OrthogonalTiledMapRenderer(game.assets.map, 1/128f);
		camera = new OrthographicCamera(19f, 19f);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(world.player.getX(),world.player.getY(), 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawCrates();
		batch.draw(game.assets.playerStop, world.player.getX() - (world.player.getWidth() / 2), world.player.getY() - (world.player.getHeight() / 2), world.player.getWidth() + 0.1f, world.player.getHeight() + 0.1f);
		batch.end();
		
		debugRenderer.render(world.world, camera.combined);
	}
	
	private void drawCrates() {
		Array<Crate> crates = world.getCrates();
		for(Crate crate: crates) {
			batch.draw(game.assets.crate, crate.getX(), crate.getY(), 1f, 1f);
		}
	}
}