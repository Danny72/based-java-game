package com.me.based.entity.mob;

import com.me.based.entity.Entity;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;
	protected Keyboard input;
	protected int anim = 0;

	public void move(int newx, int newy) {

		//if moving on both axis, use 2 separate recursive calls
		if (newx != 0 && newy != 0) {
			move(newx, 0);
			move(0, newy);
			return;
		}

		//for direction 0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
		if (newx > 0) dir = 1;
		if (newx < 0) dir = 3;
		if (newy > 0) dir = 2;
		if (newy < 0) dir = 0;

		if (!collision(newx, newy)) {
			x += newx;
			y += newy;
		}
	}

	public void update() {
	}
	
	protected void shoot(int x, int y, double dir) {
		
	}

	public void render(Screen screen) {
	}

	//if any of the 4 corners of the tile we're moving into collide, don't allow movement
	private boolean collision(int newx, int newy) {
		boolean solid = false;
		//loop for checking each corner of tile
		for (int i = 0; i < 4; i++) {
			int xc = ((x + newx) + i % 2 * 7 - 4) >> 4;
			int yc = ((y + newy) + i / 2 * 7 + 6) >> 4;
			if (level.get_tile(xc, yc).solid()) solid = true;
		}
		return solid;
	}
}
