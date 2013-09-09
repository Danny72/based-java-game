package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class ColourTile extends Tile {
	
	private int colour;

	public ColourTile(Sprite sprite, int colour) {
		super(sprite);
		this.colour = colour;
	}

	public int get_colour() {
		return colour;
	}
}
