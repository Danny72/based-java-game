package com.me.based.graphics;

import java.util.Random;

import com.me.based.level.tile.Tile;

public class Screen {

	private int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	private Random random = new Random();

	public Screen(int w, int h) {
		width = w;
		height = h;
		pixels = new int[width * height];
		
		for (int i =0 ; i < MAP_SIZE*MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	//this clears the pixel array of anything previously added to buffer
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}

	//loads up the buffer with whatever is to be drawn onto the screen
	public void render(int xoffset, int yoffset) {

		for (int y = 0; y < height; y++) {
			int yp = y + yoffset;
			if (yp < 0 || yp >= height) continue;
			for (int x = 0; x < width; x++) {
				int xp = x + xoffset;
				if (xp < 0 || xp >= width) continue;
				pixels[xp + (yp * width)] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
			}
		}
	}
	
	//renders a specified tile onto the screen
	public void render_tile(int xp, int yp, Tile tile) {
		
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int y_abs = y + yp;
		}
	}

}
