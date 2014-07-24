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
import com.nextpeer.libgdx.NextpeerPlugin;

public class MultiplayerMenu implements Screen {
private SquareBomber game;
	
	private Stage stage;
	private Table table;
	private SpriteBatch batch;
	
	public MultiplayerMenu(SquareBomber game) {
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
		TextButton createGame = new TextButton("Create Quick Game", game.assets.uiSkin, "big");
		TextButton inviteGame = new TextButton("Create Private Game", game.assets.uiSkin, "big");
		
		createGame.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("NextPeer", "Launched");
				if (NextpeerPlugin.isAvailable()) {
					NextpeerPlugin.launch();
				}
				return false;
			}
		});
		
		inviteGame.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		

		

		table = new Table();
		stage.addActor(table);
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		table.add(title);
		table.row();
		table.add(createGame).width(createGame.getPrefWidth() * density).height(createGame.getPrefHeight() * density).pad(10);
		table.row();
		table.add(inviteGame).width(createGame.getPrefWidth() * density).height(createGame.getPrefHeight() * density).pad(10);		
	}
}