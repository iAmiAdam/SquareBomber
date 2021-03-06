package info.adamjsmith.squarebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	
	public AssetManager manager;
	private Texture player;
	
	public TiledMap map;
	public Texture crate;
	public Texture bomb;
	public Texture exMid;
	public Texture exMidUp;
	public Texture exStart;
	public Texture exEnd;
	public Texture exEndUp;
	public Texture bombUp;
	public Texture speedUp;
	public Texture powerUp;
	public Texture uiBg;
	public Texture controller;
	public BitmapFont font16;
	public BitmapFont font24;
	public BitmapFont font32;
	
	public Skin uiSkin;
	
	
	public Animation playerWalk;
	public TextureRegion playerStop;
	public TextureRegion test;
	
	public Assets() {
		manager = new AssetManager();
	}

	public void load() {
		map = new TmxMapLoader().load("map/map.tmx");
		manager.load("player.png", Texture.class);
		manager.load("crate.png", Texture.class);
		manager.load("bomb.png", Texture.class);
		manager.load("explosionmid.png", Texture.class);
		manager.load("explosionmidup.png", Texture.class);
		manager.load("explosionstart.png", Texture.class);
		manager.load("explosionend.png", Texture.class);
		manager.load("explosionendup.png", Texture.class);
		manager.load("bombup.png", Texture.class);
		manager.load("speedup.png", Texture.class);
		manager.load("powerup.png", Texture.class);
		manager.load("uibg.png", Texture.class);
		manager.load("controller.png", Texture.class);
		manager.finishLoading();
		
		player = manager.get("player.png", Texture.class);
		crate = manager.get("crate.png", Texture.class);
		bomb = manager.get("bomb.png", Texture.class);
		exMid = manager.get("explosionmid.png", Texture.class);
		exMidUp = manager.get("explosionmidup.png", Texture.class);
		exStart = manager.get("explosionstart.png", Texture.class);
		exEnd = manager.get("explosionend.png", Texture.class);
		exEndUp = manager.get("explosionendup.png", Texture.class);
		bombUp = manager.get("bombup.png",Texture.class);
		speedUp = manager.get("speedup.png", Texture.class);
		powerUp = manager.get("powerup.png", Texture.class);
		uiBg = manager.get("uibg.png", Texture.class);
		controller = manager.get("controller.png", Texture.class);
			
		TextureRegion[][] tmp = TextureRegion.split(player, 88, 93);
		playerStop = tmp[0][0];
		
		TextureRegion[] walkFrames = new TextureRegion[8]; 
		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		Gdx.app.log("size", String.valueOf(walkFrames.length));
		test = walkFrames[3];
		playerWalk = new Animation(0.1f, walkFrames);
		
		font16 = new BitmapFont(Gdx.files.internal("font/16.fnt"), Gdx.files.internal("font/16.png"), false);
		font16.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		font24 = new BitmapFont(Gdx.files.internal("font/24.fnt"), Gdx.files.internal("font/24.png"), false);
		font24.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		font32 = new BitmapFont(Gdx.files.internal("font/32.fnt"), Gdx.files.internal("font/32.png"), false);
		font32.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		uiSkin.getFont("header-font").setMarkupEnabled(true);
		uiSkin.getFont("button-font").setMarkupEnabled(true);
	}
	
	public void dispose() {
		//manager.dispose();
	}
}