package com.me.based.graphics;

import java.util.Random;

import com.me.based.entity.mob.Player;
import com.me.based.entity.projectile.Projectile;
import com.me.based.input.Mouse;
import com.me.based.level.tile.Tile;
import com.me.based.graphics.Render3D;


public abstract class Screen {

	protected int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] floor_pixels;

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
	
	public void render_tile(int xp, int yp, Tile tile, boolean flip) {
		
	}
	
	public void render_projectile(int xp, int yp, Projectile pro) {
		
	}
	
	public void render_floor() {
		
	}
	
	public void render_player(int xp, int yp, Sprite bspr, Sprite head, boolean flip, int jump_offset) {
		xp -= xoffset;
		yp -= yoffset;
		yp -= jump_offset;
		for (int y = 0; y < bspr.get_height(); y++) {
			int y_abs = y + yp;
			for (int x = 0; x < bspr.get_width(); x++) {
				int x_abs = x + xp;

				//this flips the sprite along the x-axis if flip
				int xsprite = x;
				if (flip) xsprite = (bspr.get_width() - 1) - x;

				//if a tile is completely off the screen, don't render it
				//x_abs allows us to partially render tiles on the x-axis at 0
				if (x_abs < -bspr.get_width() || x_abs >= width || y_abs < 0 || y_abs >= height) break;
				if (x_abs < 0) x_abs = 0;

				//this only renders if the pixel isn't alpha pink
				int colour = bspr.pixels[xsprite + y * bspr.get_width()];
				if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;
				/*
				if (head != null) {
					xsprite = x;
					colour = head.pixels[xsprite + y * head.SIZE];
					if (colour != 0xFFFF00FF) pixels[x_abs + ((y_abs) * width)] = colour;
				}
				*/

			}
		}
	}
	
	public void render_sprite(double x, double y, double z, double h_off, Sprite sp) {
		
	}

}
