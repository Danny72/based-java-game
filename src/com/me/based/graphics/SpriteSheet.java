package com.me.based.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	
	//creates static sprite sheet that can be referenced by SpriteSheet.tiles
	public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 256);

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load_image();
	}

	private void load_image() {
		try {
			//loads in the spritesheet as a bufferedimage
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			//breaks down the image into component pixels, storing the colour
			pixels = image.getRGB(0, 0, w, h, null, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
