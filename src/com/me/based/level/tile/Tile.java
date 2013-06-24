package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	//static reference to a grass tile through Tile.grass
	public static Tile grass = new GrassTile(Sprite.grass);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

}
