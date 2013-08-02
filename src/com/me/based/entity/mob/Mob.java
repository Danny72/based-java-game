package com.me.based.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.me.based.Game;
import com.me.based.entity.Entity;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;
	protected Keyboard input;
	protected int anim = 0;
	protected int move_speed;
	protected int xb, yb;
	protected int health;

	//an ArrayList of this players projectiles

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
			x += newx * move_speed;
			y += newy * move_speed;
		}
	}

	public void update() {
	}
	
	public int get_xb() {
		return xb;
	}

	public int get_yb() {
		return yb;
	}
	
	public int get_health() {
		return health;
	}
	
	public void set_health(int damage) {
		health -= damage;
	}
	
	protected void remove_projectile(Projectile p) {
		level.remove_projectile(p);
	}

	protected void shoot(int x, int y, double dir, int owner) {
		Projectile p = new PlayerProjectile(x, y, dir, owner);
		level.add_projectile(p);
	}

	public void render(Screen screen) {
	}

	//if any of the 4 corners of the tile we're moving into collide, don't allow movement
	private boolean collision(int newx, int newy) {
		boolean solid = false;
		//loop for checking each corner of tile
		for (int i = 0; i < 4; i++) {
			int xc = ((x + newx) + i % 2 * 8 - 1) >> 6;
			int yc = ((y + newy) + i / 2 * 7 + 20) >> 6;
			if (level.get_tile(xc, yc).solid()) solid = true;
		}
		return solid;
	}
}
