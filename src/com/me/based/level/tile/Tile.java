package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	//static reference to a grass tile through Tile.grass
	public static Tile grass = new GrassTile(Sprite.grass);
	//static reference to a void tile through Tile.void_tile
	public static Tile void_tile = new VoidTile(Sprite.void_sprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	//allows each tile to call screen.render themselves
	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

}
