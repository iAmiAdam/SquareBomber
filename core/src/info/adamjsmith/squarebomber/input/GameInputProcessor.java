package info.adamjsmith.squarebomber.input;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.objects.Player;
import info.adamjsmith.squarebomber.objects.Player.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {
	
	GameUpdater world;

	public GameInputProcessor(GameUpdater world) {
		this.world = world;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(screenY < Gdx.graphics.getHeight() / 2 && screenX > Gdx.graphics.getWidth() / 4 && screenX < (Gdx.graphics.getWidth() / 4) + (Gdx.graphics.getWidth() / 2)) {
			world.player.move(Direction.UP);
		}
		if(screenY > Gdx.graphics.getHeight() / 2 && screenX > Gdx.graphics.getWidth() / 4 && screenX < (Gdx.graphics.getWidth() / 4) + (Gdx.graphics.getWidth() / 2)) {
			world.player.move(Direction.DOWN);
		}
		if(screenX < Gdx.graphics.getWidth() / 4) {
			world.player.move(Direction.LEFT);
		}
		if(screenX > (Gdx.graphics.getWidth() / 4) + (Gdx.graphics.getWidth() / 2)) {
			world.player.move(Direction.RIGHT);
		}
		if(screenX < (Gdx.graphics.getHeight() / 8) && screenY < (Gdx.graphics.getWidth() / 8)) {
			world.placeBomb();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		world.player.move(Direction.STOP);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
