package info.adamjsmith.squarebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameRenderer {
	
	private SquareBomber game;
	private GameUpdater world;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public GameRenderer(SquareBomber game, GameUpdater world) {
		this.game = game;
		this.world = world;
		
		renderer = new OrthogonalTiledMapRenderer(game.assets.map, 1/64f);
		camera = new OrthographicCamera(20f, 12f);
		camera.position.set(7f,5f, 0);
	}
	
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(camera);
		renderer.render();
		
		debugRenderer.render(world.world, camera.combined);
	}
}