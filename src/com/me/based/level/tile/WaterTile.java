package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class WaterTile extends Tile {

	public WaterTile(Sprite sprite) {
		super(sprite);
		tile_type = "water";
	}

	public void render(int x, int y, Screen screen) {
		screen.render_tile(x, y, this, flip);
	}
	
	public boolean slow_move() {
		return true;
	}
	
	

}
