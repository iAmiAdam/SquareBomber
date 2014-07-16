package info.adamjsmith.squarebomber.input;

import info.adamjsmith.squarebomber.GameUpdater;
import info.adamjsmith.squarebomber.objects.Player.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {
	
	private GameUpdater world;
	
	private float x = (Gdx.graphics.getWidth() / 7) * 6;
	private float y = (Gdx.graphics.getHeight() / 5) * 4;
	private float widthSegment = (Gdx.graphics.getWidth() / 7) / 3;
	private float heightSegment = (Gdx.graphics.getHeight() / 5) / 3;
	
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
		if(screenX < x && screenY < y) {
			world.placeBomb();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(screenX > x && screenY > y) {
			world.player.move(Direction.STOP);
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(screenX > x && screenX < x + widthSegment && screenY > y) {
			world.player.move(Direction.LEFT);
		} else if(screenX > x + widthSegment + widthSegment && screenY > y) {
			world.player.move(Direction.RIGHT);
		} else if(screenY > y + heightSegment && screenX > x) {
			world.player.move(Direction.DOWN);
		} else if(screenY < y + heightSegment && screenY > y && screenX > x) {
			world.player.move(Direction.UP);
		} else {
			world.player.move(Direction.STOP);
		}
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
