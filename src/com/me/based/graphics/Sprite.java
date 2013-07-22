package com.me.based.graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;

	//LEVEL TEXTURE SPRITES
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite path = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite water = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite spawn = new Sprite(16, 5, 0, SpriteSheet.tiles);
	public static Sprite test = new Sprite(16, 0, 1, SpriteSheet.tiles);
	//creates static sprite that is 100% pink
	public static Sprite void_sprite = new Sprite(16, 0xFF00FF);
	
	//PLAYER ANIMATION SPRITES
	public static Sprite player_down = new Sprite(32, 0, 1, SpriteSheet.tiles);
	public static Sprite player_down_a = new Sprite(32, 0, 2, SpriteSheet.tiles);
	public static Sprite player_up = new Sprite(32, 1, 1, SpriteSheet.tiles);
	public static Sprite player_up_a = new Sprite(32, 1, 2, SpriteSheet.tiles);
	public static Sprite player_side = new Sprite(32, 2, 1, SpriteSheet.tiles);
	public static Sprite player_side_a1 = new Sprite(32, 2, 2, SpriteSheet.tiles);
	public static Sprite player_side_a2 = new Sprite(32, 2, 3, SpriteSheet.tiles);
	
	//OPPO ANIMATION SPRITES
	public static Sprite oppo_down = new Sprite(32, 3, 1, SpriteSheet.tiles);
	public static Sprite oppo_down_a = new Sprite(32, 3, 2, SpriteSheet.tiles);
	public static Sprite oppo_up = new Sprite(32, 4, 1, SpriteSheet.tiles);
	public static Sprite oppo_up_a = new Sprite(32, 4, 2, SpriteSheet.tiles);
	public static Sprite oppo_side = new Sprite(32, 5, 1, SpriteSheet.tiles);
	public static Sprite oppo_side_a1 = new Sprite(32, 5, 2, SpriteSheet.tiles);
	public static Sprite oppo_side_a2 = new Sprite(32, 5, 3, SpriteSheet.tiles);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		//sets target sprite coords on sprite sheet
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.sheet = sheet;
		load_sprite();

	}

	//constructor for creating a single colour sprite
	public Sprite(int size, int colour) {
		SIZE = size;
		pixels = new int[size * size];
		set_colour(colour);
	}

	//will fill pixel array with a single colour
	private void set_colour(int colour) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = colour;
		}
	}

	//extracts the spite from the spritesheet using the offsets to get the correct sprite
	private void load_sprite() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE];
			}
		}
	}
}
