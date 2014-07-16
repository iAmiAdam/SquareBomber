package info.adamjsmith.squarebomber.screens;

import info.adamjsmith.squarebomber.SquareBomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private SpriteBatch batch;
	
	public MainMenuScreen(SquareBomber game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(game.assets.uiBg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		createStage();		
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
		stage.dispose();	
		batch.dispose();
	}
	
	private void createStage() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		float density = Gdx.graphics.getDensity();
		Label title = new Label("Square Bomber", game.assets.uiSkin, "big");
		TextButton singlePlayer = new TextButton("Single Player", game.assets.uiSkin, "big");
		TextButton multiPlayer = new TextButton("Multi Player", game.assets.uiSkin, "big");
		TextButton leaderboards = new TextButton("Leaderboards", game.assets.uiSkin, "big");
		TextButton achievements = new TextButton("Achievements", game.assets.uiSkin, "big");
		
		singlePlayer.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new GameScreen(game));
				return false;
			}
		});
		
		multiPlayer.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.actionResolver.startQuickGame();
				return false;
			}
		});
		
		leaderboards.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.actionResolver.getLeaderboardGPGS();
				return false;
			}
		});
		
		achievements.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.actionResolver.getAchievementsGPGS();
				return false;
			}
		});
		

		table = new Table();
		stage.addActor(table);
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table.add(title).colspan(2);
		table.row();
		table.add(singlePlayer).width(singlePlayer.getPrefWidth() * density).height(singlePlayer.getPrefHeight() * density).pad(10);
		table.add(multiPlayer).width(singlePlayer.getPrefWidth() * density).height(singlePlayer.getPrefHeight() * density).pad(10);
		table.row();
		table.add(leaderboards).width(singlePlayer.getPrefWidth() * density).height(singlePlayer.getPrefHeight() * density).pad(10);
		table.add(achievements).width(singlePlayer.getPrefWidth() * density).height(singlePlayer.getPrefHeight() * density).pad(10);
		
	}

}
