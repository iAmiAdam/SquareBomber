package info.adamjsmith.squarebomber;

import info.adamjsmith.squarebomber.gpgs.ActionResolver;
import info.adamjsmith.squarebomber.screens.LoadingScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;


public class SquareBomber extends Game implements ApplicationListener{
	public Assets assets = new Assets();
	public ActionResolver actionResolver;
	public WarpClient warpClient;
	
	public SquareBomber(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {

		setScreen(new LoadingScreen(this));
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