package com.me.based.entity.mob;

import com.me.based.entity.Entity;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;
import com.me.based.input.Mouse;

public abstract class Mob extends Entity {

	protected static final int MOVE_SPEED = 4;

	protected Sprite sprite;
	protected Sprite hsprite;
	protected int dir = 2;
	protected int looking = 2;
	protected boolean moving = false;
	protected Keyboard input;
	protected int anim = 0;
	protected int move_speed = MOVE_SPEED;
	protected int xb, yb;
	protected int health;
	protected boolean jumping;
	protected int y_offset;
	protected int fire_rate = 0;
	protected int tick = 0;
	protected int jump_offset = 0;

	//an ArrayList of this players projectiles

	public void move(int newx, int newy) {

		if (current_tile("slow")) move_speed = MOVE_SPEED / 2;
		else move_speed = MOVE_SPEED;

		if (current_tile("solid") && !jumping) y_offset = -25;
		else y_offset = 0;

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

		if (current_tile("solid") && !jumping) {
			y_offset = -25;
			x += newx * move_speed;
			y += newy * move_speed;
		} else {
			if (!collision(newx, newy) || jumping) {
				x += newx * move_speed;
				y += newy * move_speed;
			}
		}
	}

	protected int calc_jump() {
		int y = (int) (-0.02 * Math.pow(tick, 2) + 3.2 * tick);
		if (y < 0) tick = 0;
		else tick += 5;
		return y;
	}

	public void update() {
	}

	protected void update_shooting() {
	}

	protected void clear() {
		for (int i = 0; i < level.get_projectiles().size(); i++) {
			Projectile p = level.get_projectiles().get(i);
			if (p.is_removed()) {
				remove_projectile(p);
			}
		}
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

	public void set_health(double damage) {
		//System.out.println("hit for " + (int) damage + " damage");
		health -= damage;
		if (health <= 0) {
			level.remove(this);
			level.spawn_new_mob(1);
		}
	}

	protected void remove_projectile(Projectile p) {
		level.remove_projectile(p);
	}

	protected void shoot(int x, int y, double dir) {	
	}

	public void render(Screen screen) {
	}

	//returns the coords of tile player is currently facing i.e. will move into
	protected Object[] next_tile_coords() {
		int xx = 0;
		int yy = 0;
		if (dir == 0) yy -= 1;
		if (dir == 1) xx += 1;
		if (dir == 2) yy += 1;
		if (dir == 3) xx -= 1;

		return new Object[] { x / 64 + xx, y / 64 + yy };
	}

	//returns true or false regarding the tile stood upon and the query
	protected boolean current_tile(String query) {
		for (int i = 0; i < 4; i++) {
			int xc = ((x) + i % 2 * 7 - 4) >> 6;
			int yc = ((y) + i / 2 * 7 + 20) >> 6;
			if (query == "slow") if (level.get_tile(xc, yc).slow_move()) return true;
			if (query == "solid") if (level.get_tile(xc, yc).solid()) return true;
		}
		return false;
	}

	//if any of the 4 corners of the tile we're moving into collide, don't allow movement
	private boolean collision(int newx, int newy) {
		boolean solid = false;
		//loop for checking each corner of tile
		for (int i = 0; i < 4; i++) {
			int xc = ((x + newx) + i % 2 * 7 - 4) >> 6;
			int yc = ((y + newy) + i / 2 * 7 + 20) >> 6;
			if (level.get_tile(xc, yc).solid()) solid = true;
		}
		return solid;
	}

}
