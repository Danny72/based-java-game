package com.me.based.entity.projectile;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.level.tile.Tile;

public class PlayerProjectile extends Projectile {

	public PlayerProjectile(int x, int y, double angle, int owner) {
		super(x, y, angle, owner);
		range = 500;
		damage = 20;
		speed = 20;
		sprite = Sprite.projectile_oppo;
		owner = 0;

		newx = speed * Math.cos(angle);
		newy = speed * Math.sin(angle);
	}

	public void update() {
		if (level.tile_collision(x, y, newx, newy, 8)) remove();
		else move();
	}

	public void move() {
		x += newx;
		y += newy;
		level.projectile_hit(this);
		if (calc_distance() > range) {
			removed = true;
		}

	}

	private double calc_distance() {
		double dist = 0;
		//calc the hypot using pythag
		dist = Math.sqrt(Math.abs(Math.pow(xorigin - x, 2)) + Math.pow(yorigin - y, 2));
		return dist;

	}

	public void render(Screen screen) {
		screen.render_projectile((int) x - 8, (int) y - 8, this);
	}
}
