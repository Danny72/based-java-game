package com.me.based.level;

import com.me.based.graphics.Screen;

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
		
	}

}
