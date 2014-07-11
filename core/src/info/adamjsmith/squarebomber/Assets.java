package info.adamjsmith.squarebomber;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {
	
	private AssetManager manager;
	public TiledMap map;
	
	public Assets() {
		manager = new AssetManager();
	}

	public void load() {
		map = new TmxMapLoader().load("map/map.tmx");
	}
	
	public void dispose() {
		manager.dispose();
	}
}