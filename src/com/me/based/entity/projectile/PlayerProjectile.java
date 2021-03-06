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
}
