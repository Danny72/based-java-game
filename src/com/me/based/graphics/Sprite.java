package com.me.based.graphics;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

	private int SIZE;
	public int x, y;
	private int width, height;
	public int[] pixels;
	private SpriteSheet sheet;

	private List<Sprite> head_sprites = new ArrayList<Sprite>();

	//LEVEL TEXTURE SPRITES
	public static Sprite grass = new Sprite(64, 0, 0, SpriteSheet.textures);
	public static Sprite flower = new Sprite(64, 3, 0, SpriteSheet.textures);
	public static Sprite rock = new Sprite(64, 4, 0, SpriteSheet.textures);
	public static Sprite path = new Sprite(64, 2, 0, SpriteSheet.textures);
	public static Sprite water = new Sprite(64, 1, 0, SpriteSheet.textures);
	public static Sprite spawn = new Sprite(16, 5, 0, SpriteSheet.textures);
	public static Sprite mob = new Sprite(64, 0x00000000);
	//creates static sprite that is 100% pink
	public static Sprite void_sprite = new Sprite(64, 64, 0xFF00FF);

	//PLAYER ANIMATION SPRITES

	public static Sprite player_down = new Sprite(64, 0, 0, SpriteSheet.player);
	public static Sprite player_down_a = new Sprite(64, 0, 1, SpriteSheet.player);
	public static Sprite player_up = new Sprite(64, 1, 0, SpriteSheet.player);
	public static Sprite player_up_a = new Sprite(64, 1, 1, SpriteSheet.player);
	public static Sprite player_side = new Sprite(64, 2, 0, SpriteSheet.player);
	public static Sprite player_side_a1 = new Sprite(64, 2, 1, SpriteSheet.player);
	public static Sprite player_side_a2 = new Sprite(64, 2, 2, SpriteSheet.player);

	//OPPO ANIMATION SPRITES
	public static Sprite oppo_down = new Sprite(64, 0, 0, SpriteSheet.oppo);
	public static Sprite oppo_down_a = new Sprite(64, 0, 1, SpriteSheet.oppo);
	public static Sprite oppo_up = new Sprite(64, 1, 0, SpriteSheet.oppo);
	public static Sprite oppo_up_a = new Sprite(64, 1, 1, SpriteSheet.oppo);
	public static Sprite oppo_side = new Sprite(64, 2, 0, SpriteSheet.oppo);
	public static Sprite oppo_side_a1 = new Sprite(64, 2, 1, SpriteSheet.oppo);
	public static Sprite oppo_side_a2 = new Sprite(64, 2, 2, SpriteSheet.oppo);

	//PROJECTILE SPRITES
	public static Sprite projectile_player = new Sprite(16, 0, 0, SpriteSheet.projectile);
	//public static Sprite projectile_oppo = new Sprite(16, 0, 2, SpriteSheet.projectile);
	public static Sprite projectile_oppo = new Sprite(32, 16, 0, 1, SpriteSheet.projectile);

	//constructor for creating a square dimension sprite
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		//sets target sprite coords on sprite sheet
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		load_sprite();
	}

	//constructor for creating a single colour square sprite
	public Sprite(int size, int colour) {
		width = size;
		height = size;
		pixels = new int[size * size];
		set_colour(colour);
	}

	//constructor for creating a rectangle dimension sprite
	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		pixels = new int[this.width * this.height];
		//sets target sprite coords on sprite sheet
		this.x = x * this.width;
		this.y = y * this.height;
		this.sheet = sheet;
		load_sprite();
	}

	public Sprite(int width, int height, int colour) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		set_colour(colour);
	}

	public int get_size() {
		return width * height;
	}

	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}

	//will fill pixel array with a single colour
	private void set_colour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}

	//extracts the sprite from the spritesheet using the offsets to get the correct sprite
	private void load_sprite() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				pixels[x + y * width] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE];
			}
		}
	}
}
