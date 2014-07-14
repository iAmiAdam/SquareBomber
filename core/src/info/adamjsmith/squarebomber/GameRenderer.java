package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.objects.Bomb;
import info.adamjsmith.squarebomber.objects.Crate;
import info.adamjsmith.squarebomber.objects.Explosion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class GameRenderer {
	
	private SquareBomber game;
	private GameUpdater world;
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private float stateTime;
	private TextureRegion currentFrame = new TextureRegion();
	private Box2DDebugRenderer debugRenderer =  new Box2DDebugRenderer();
	
	public GameRenderer(SquareBomber game, GameUpdater world) {
		this.world = world;
		this.game = game;
		
		batch = new SpriteBatch();
		renderer = new OrthogonalTiledMapRenderer(game.assets.map, 1/128f);
		camera = new OrthographicCamera(19f, 19f);
		camera.viewportWidth = 7f;
		camera.viewportHeight = 5f;
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
		drawPlayer();
		drawBombs();
		drawExplosions();
		batch.end();
		
		debugRenderer.render(world.world, camera.combined);
		stateTime += Gdx.graphics.getDeltaTime();
		
	}
	
	private void drawCrates() {
		Array<Crate> crates = world.getCrates();
		for(Crate crate: crates) {
			batch.draw(game.assets.crate, crate.getX() + 0.025f, crate.getY() + 0.025f, 0.95f, 0.95f);
		}
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
	
	private void drawBombs() {
		Array<Bomb> bombs = world.getBombs();
		for(Bomb bomb: bombs) {
			batch.draw(game.assets.bomb, bomb.getX(), bomb.getY(), 1f, 1f);
		}
	}
	
	private void drawExplosions() {
		
		float x = 0;
		float y = 0;
		float rotation = 0;
		Array<Explosion> explosions = world.getExplosions();
		for (Explosion explosion: explosions) {
			batch.draw(game.assets.exStart, explosion.getX(), explosion.getY(), 1f, 1f);
			for(int i = 0; i < 4; i++) {
				switch(i) {
				case 0:
					x = explosion.getX();
					y = explosion.getY();
					rotation = 90;
					break;
				case 1:
					x = explosion.getX();
					y = explosion.getY();
					rotation = 0;
					break;
				case 2:
					x = explosion.getX();
					y = explosion.getY();
					rotation = 270;
					break;
				case 3:
					x = explosion.getX();
					y = explosion.getY();
					rotation = 0;
				}
				
				
				
				if (explosion.sides[i] > 0) {
					for(int j = 1; j <= explosion.sides[i]; j++) {
						if(i == 0) {
							if(j == explosion.sides[i]) {
								batch.draw(game.assets.exEndUp, x, y + j, 1f, 1f, 0, 0, 128, 128, false, false);
							} else {
								batch.draw(game.assets.exMidUp, x, y + j, 1f, 1f, 0, 0, 128, 128, false, false);
							}
						}
						if (i == 1) {
							if (j == explosion.sides[i]) {
								batch.draw(game.assets.exEnd, x + j, y, 1f, 1f, 0, 0, 128, 128, true, false);
							} else {
								batch.draw(game.assets.exMid, x + j, y, 1f, 1f, 0, 0, 128, 128, false, false);
							}
						}
						if (i == 2) {
							
							if (j == explosion.sides[i]) {
								batch.draw(game.assets.exEndUp, x, y - j, 1f, 1f, 0, 0, 128, 128, true, true);
							} else {
								batch.draw(game.assets.exMidUp, x, y - j, 1f, 1f, 0, 0, 128, 128, true, true);
							}
						}
						if (i == 3) {
							if(j == explosion.sides[i]) {
								batch.draw(game.assets.exEnd, x - j, y, 1f, 1f);
							} else {
								batch.draw(game.assets.exMid, x - j, y, 1f, 1f);
							}
						}						
					}
				}
			}
		}
	}
}