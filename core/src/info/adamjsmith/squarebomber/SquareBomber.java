package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.screens.GameScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class SquareBomber extends Game implements ApplicationListener{
	public Assets assets;
	
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();	
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void pause() {
		super.pause();
	}
}
