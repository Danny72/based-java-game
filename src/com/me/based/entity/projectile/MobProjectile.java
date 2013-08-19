package com.me.based.entity.projectile;

import com.me.based.graphics.Sprite;

public class MobProjectile extends Projectile {
	
	public MobProjectile(int x, int y, double angle, int owner) {
		super(x, y, angle, owner);
		range = 500;
		damage = 20;
		speed = 20;
		sprite = Sprite.projectile_player;
		owner = 0;

		newx = speed * Math.cos(angle);
		newy = speed * Math.sin(angle);
	}
}
