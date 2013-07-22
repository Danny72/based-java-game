package com.me.based.level.tile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	//static references to tiles
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile path = new PathTile(Sprite.path);
	public static Tile water = new WaterTile(Sprite.water);
	public static Tile spawn = new VoidTile(Sprite.spawn);
	public static Tile test = new VoidTile(Sprite.test);
	public static Tile void_tile = new VoidTile(Sprite.void_sprite);
	
	//STATIC COLOURS FOR TILES
	public final static int col_grass = 0xFF00FF00;
	public final static int col_flower = 0xFFFFFF00;
	public final static int col_rock = 0xFF000000;
	public final static int col_path = 0xFFFF0000;
	public final static int col_water = 0XFF3586FF;
	public final static int col_spawn = 0XFFFFAAFF;
	public final static int col_test = 0XFFFFFFAA;

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
