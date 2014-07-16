package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScreen implements Screen {
	
	private SquareBomber game;
	
	private Stage stage;
	private Table table;
	
	public MainMenuScreen(SquareBomber game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void createStage() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Label title = new Label("Square Bomber", game.assets.uiSkin);
		TextButton singlePlayer = new TextButton("Single Player", game.assets.uiSkin);
		TextButton multiPlayer = new TextButton("Multi Player", game.assets.uiSkin);
		TextButton leaderboards = new TextButton("Leaderboards", game.assets.uiSkin);
		TextButton achievements = new TextButton("Achievements", game.assets.uiSkin);
		
		singlePlayer.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
				return false;
			}
		});
		
		
	}

}
