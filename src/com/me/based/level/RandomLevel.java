package com.me.based.level;

import java.util.Random;

public class RandomLevel extends Level {
	
	private final Random random = new Random();

	public RandomLevel(int w, int h) {
		super(w, h);
	}
	
	protected void generate_level() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (random.nextInt(3) == 2) {
					tiles[x + (y * width)] = 1;
				}
				else {
					tiles[x + (y * width)] = 0;
				}
			}
		}
	}

}
