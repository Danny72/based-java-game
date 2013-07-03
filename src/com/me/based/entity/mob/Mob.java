package com.me.based.entity.mob;

import com.me.based.entity.Entity;
import com.me.based.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;

	public void move(int newx, int newy) {
		//for direction 0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
		if (newx > 0) dir = 1;
		if (newx < 0) dir = 3;
		if (newy > 0) dir = 2;
		if (newy < 0) dir = 0;
		
		if (!collision()) {
			x += newx;
			y += newy;
		}
	}

	public void update() {

	}

	public void render() {

	}

	private boolean collision() {
		return false;
	}
}
