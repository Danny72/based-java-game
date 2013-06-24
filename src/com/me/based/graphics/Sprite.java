package com.me.based.graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	//creates static sprite that can be referenced by Sprite.grass
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		//sets target sprite coords on sprite sheet
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		load_sprite();

	}

	//extracts the spite from the spritesheet using the offsets to get the correct sprite
	private void load_sprite() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y ) * sheet.SIZE];
			}
		}
	}
}
