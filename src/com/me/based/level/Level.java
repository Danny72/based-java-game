package com.me.based.level;

import com.me.based.graphics.Screen;
import com.me.based.level.tile.Tile;

public class Level {

	protected int width, height;
	//each integer stored is an ID for a tile
	protected int[] tiles;

	//constructor for random level generator
	public Level(int w, int h) {
		width = w;
		height = h;
		tiles = new int[width * height];
		generate_level();
	}
	
	//constructor for image based level
	public Level(String path) {
		load_level(path);
	}

	protected void generate_level() {

	}

	private void load_level(String path) {

	}
	
	public void update() {
		
	}
	
	private void time() {
		
	}
	
	public void render(int xscroll, int yscroll, Screen screen) {
		//sets offsets to the location of the player
		screen.set_offset(xscroll, yscroll);
		
		//this defines the render region of the screen using corner pins
		//converts pixel precision to tile precision
		//+16 renders one extra tile for horiz and vert to prevent flickering
		int x0 = xscroll >> 4;
		int x1 = (xscroll + screen.get_width() + 16) >> 4;
		int y0 = yscroll >> 4;
		int y1 = (yscroll + screen.get_height() + 16) >> 4;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				get_tile(x, y).render(x << 4, y << 4, screen);
			}
		}
	}
	
	public Tile get_tile(int x, int y) {
		//if (x < 0 || y < 0 || x >= width || y >= height) return Tile.void_tile;
		if (x < 0 || x >= width) x = x & 63;
		if (y < 0 || y >= height) y = y & 63;
		if (tiles[x+y*width] == 0) return Tile.grass;
		return Tile.void_tile;
	}

}
