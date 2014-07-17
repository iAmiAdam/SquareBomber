package info.adamjsmith.squarebomber.multiplayer;

import info.adamjsmith.squarebomber.GameRenderer;
import info.adamjsmith.squarebomber.SquareBomber;
import info.adamjsmith.squarebomber.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MultiplayerRenderer extends GameRenderer {
	
	private MultiplayerUpdater world;
	private SquareBomber game;
	
	public MultiplayerRenderer(SquareBomber game, MultiplayerUpdater world) {
		super(game, world);
		this.world = world;
		this.game = game;
	}
	
	@Override 
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
		drawPowerUps();
		drawPlayers();
		drawBombs();
		drawExplosions();
		batch.setProjectionMatrix(uiMatrix);
		batch.draw(game.assets.controller, 6f, 0f, 1f, 1f);
		batch.end();
		
		stateTime += Gdx.graphics.getDeltaTime();
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
