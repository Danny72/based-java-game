package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class LineTile extends Tile {
	
	public LineTile(Sprite sprite) {
		super(sprite);
	}

	public LineTile(Sprite sprite, boolean flip) {
		super(sprite);
		this.flip = flip;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.render_tile(x, y, this, flip);
	}

}
