package info.adamjsmith.squarebomber;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
	
	private AssetManager manager;
	public TiledMap map;
	private Texture player;
	public Animation playerWalk;
	public TextureRegion playerStop;
	
	public Assets() {
		manager = new AssetManager();
	}

	public void load() {
		map = new TmxMapLoader().load("map/map.tmx");
		manager.load("player.png", Texture.class);
		
		player = manager.get("player.png", Texture.class);
		
		TextureRegion tmp[][] = TextureRegion.split(player, 128, 128);
		
		TextureRegion[] walkFrames = new TextureRegion[8]; 
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j++) {
				walkFrames[i] = tmp[i][j];
			}
		}
		
		playerWalk = new Animation(0.2f, walkFrames);
		playerStop = tmp[0][0];
	}
	
	public void dispose() {
		manager.dispose();
	}
}