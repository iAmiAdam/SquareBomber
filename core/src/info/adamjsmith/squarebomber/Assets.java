package info.adamjsmith.squarebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
	
	public AssetManager manager;
	private Texture player;
	
	public TiledMap map;
	public Texture crate;
	public Texture bomb;
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
		manager.finishLoading();
		
		player = manager.get("player.png", Texture.class);
		crate = manager.get("crate.png", Texture.class);
		bomb = manager.get("bomb.png", Texture.class);
		
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
	}
	
	public void dispose() {
		//manager.dispose();
	}
}