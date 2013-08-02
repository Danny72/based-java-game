package com.me.based.graphics;

import java.util.Random;

import com.me.based.entity.mob.Player;
import com.me.based.entity.projectile.Projectile;
import com.me.based.level.tile.Tile;

public class Screen {

	private int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;

	public int xoffset, yoffset;

	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	private Random random = new Random();

	public Screen(int w, int h) {
		width = w;
		height = h;
		pixels = new int[width * height];

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}

	//this clears the pixel array of anything previously added to buffer
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}
	}
	
	//renders a specified entity onto the screen
		public void render_projectile(int xp, int yp, Projectile pro) {
			//flips the move x y direction
			xp -= xoffset;
			yp -= yoffset;
			for (int y = 0; y < pro.get_sprite_size(); y++) {
				int y_abs = y + yp;
				for (int x = 0; x < pro.get_sprite_size(); x++) {
					int x_abs = x + xp;
					//if a tile is completely off the screen, don't render it
					//x_abs allows us to partially render tiles on the x-axis at 0
					if (x_abs < -pro.get_sprite_size() || x_abs >= width || y_abs < 0 || y_abs >= height) break;
					if (x_abs < 0) x_abs = 0;
					int colour = pro.get_sprite().pixels[x + y * pro.get_sprite_size()];
					if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;
					//pixels[x_abs + (y_abs * width)] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				}
			}
		}

	//renders a specified tile onto the screen
	public void render_tile(int xp, int yp, Tile tile) {
		//flips the move x y direction
		xp -= xoffset;
		yp -= yoffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int y_abs = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int x_abs = x + xp;
				//if a tile is completely off the screen, don't render it
				//x_abs allows us to partially render tiles on the x-axis at 0
				if (x_abs < -tile.sprite.SIZE || x_abs >= width || y_abs < 0 || y_abs >= height) break;
				if (x_abs < 0) x_abs = 0;
				int colour = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;
				//pixels[x_abs + (y_abs * width)] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	public void render_player(int xp, int yp, Sprite sprite, boolean flip) {
		xp -= xoffset;
		yp -= yoffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int y_abs = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int x_abs = x + xp;
				
				//this flips the sprite along the x-axis if flip
				int xsprite = x;
				if (flip) xsprite = (sprite.SIZE - 1) - x;
				
				//if a tile is completely off the screen, don't render it
				//x_abs allows us to partially render tiles on the x-axis at 0
				if (x_abs < -sprite.SIZE || x_abs >= width || y_abs < 0 || y_abs >= height) break;
				if (x_abs < 0) x_abs = 0;

				//this only renders if the pixel isn't alpha pink
				int colour = sprite.pixels[xsprite + y * sprite.SIZE];
				if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;

			}
		}
	}

	public void set_offset(int newx_offset, int newy_offset) {
		xoffset = newx_offset;
		yoffset = newy_offset;
	}
	
	public int get_xoffset() {
		return xoffset;
	}
	
	public int get_yoffset() {
		return yoffset;
	}

}
