package com.me.based.graphics;

import com.me.based.entity.projectile.Projectile;
import com.me.based.level.tile.Tile;

public class Render2D extends Screen {

	public Render2D(int w, int h) {
		super(w, h);

	}

	

	//renders a specified entity onto the screen
	public void render_projectile(int xp, int yp, Projectile pro) {
		xp -= xoffset;
		yp -= yoffset;
		for (int y = 0; y < pro.get_sprite_height(); y++) {
			int y_abs = y + yp;
			for (int x = 0; x < pro.get_sprite_width(); x++) {
				int x_abs = x + xp;

				//if a tile is completely off the screen, don't render it
				//x_abs allows us to partially render tiles on the x-axis at 0
				if (x_abs < -pro.get_sprite_width() || x_abs >= width || y_abs < 0 || y_abs >= height) break;
				if (x_abs < 0) x_abs = 0;
				//System.out.println("newx: " + newx + " | newy: " + newy);
				int colour = pro.get_sprite().pixels[x + y * pro.get_sprite_width()];
				if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;
			}
		}
	}

	//renders a specified tile onto the screen
	public void render_tile(int xp, int yp, Tile tile, boolean flip) {
		xp -= xoffset;
		yp -= yoffset;
		for (int y = 0; y < tile.sprite.get_width(); y++) {
			int y_abs = y + yp;
			for (int x = 0; x < tile.sprite.get_width(); x++) {
				int x_abs = x + xp;

				int xsprite = x;
				if (flip) xsprite = (tile.sprite.get_width() - 1) - x;

				//if a tile is completely off the screen, don't render it
				//x_abs allows us to partially render tiles on the x-axis at 0
				if (x_abs < -tile.sprite.get_width() || x_abs >= width || y_abs < 0 || y_abs >= height) break;
				if (x_abs < 0) x_abs = 0;
				//System.out.println(tile.sprite.pixels.length);
				int colour = tile.sprite.pixels[xsprite + y * tile.sprite.get_width()];
				if (colour != 0xFFFF00FF) pixels[x_abs + (y_abs * width)] = colour;
				//pixels[x_abs + (y_abs * width)] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	

}
