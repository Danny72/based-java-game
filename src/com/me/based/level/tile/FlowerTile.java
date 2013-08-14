package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
		tile_type = "flower";
	}
	
	public void render(int x, int y, Screen screen) {
		screen.render_tile(x, y, this);
	}

}
