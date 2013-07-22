package com.me.based.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.me.based.level.tile.Tile;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void load_level(String path) {
		try {
			//loads in the level_image as a bufferedimage
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			//breaks down the image into component pixels, storing the colour
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//this will convert the array of pixels into tiles
	protected void generate_level() {
		/*
		int pix;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//assign a tile to an index based on pixel colour
				//if no tile matches, a void will be used
				pix = tiles[x + y * width];
				if (pix == Tile.col_grass) tiles[x + y * width] = 1;
				if (pix == Tile.col_flower) tiles[x + y * width] = 2;
				if (pix == Tile.col_rock) tiles[x + y * width] = 3;
				if (pix == Tile.col_path) tiles[x + y * width] = 4;
				if (pix == Tile.col_water) tiles[x + y * width] = 5;
				if (pix == Tile.col_spawn) tiles[x + y * width] = 6;
			}
		}
		*/
	}

}
