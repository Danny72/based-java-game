package com.me.based.entity.mob;

import com.me.based.Game;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;
import com.me.based.input.Mouse;

public class Player extends Mob {

	private int fire_rate = 0;

	public Player(Keyboard input, int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.input = input;
		fire_rate = PlayerProjectile.FIRE_RATE;
		move_speed = 4;
		this.type = type;
		health = 100;
	}

	public Player(Keyboard input) {
		this.input = input;
	}

	public void update() {

		//values for hit box
		xb = x - (x - Game.get_width() / 2) - 27;
		yb = y - (y - Game.get_height() / 2) - 35;

		if (fire_rate > 0) fire_rate--;

		//resets anim to 0 after 125 seconds
		anim = (++anim) % 7500;
		int newx = 0, newy = 0;
		if (input.up) newy--;
		if (input.down) newy++;
		if (input.left) newx--;
		if (input.right) newx++;

		if (newx != 0 || newy != 0) {
			move(newx, newy);
			moving = true;
		} else {
			moving = false;
		}

		clear();
		update_shooting();
	}

	private void clear() {
		for (int i = 0; i < level.get_projectiles().size(); i++) {
			Projectile p = level.get_projectiles().get(i);
			if (p.is_removed()) {
				remove_projectile(p);
			}
		}
	}

	private void update_shooting() {
		if (Mouse.getb() == 1 && fire_rate <= 0) {
			double dx = Mouse.getx() - Game.get_width() / 2;
			double dy = Mouse.gety() - Game.get_height() / 2;
			shoot(x, y, Math.atan2(dy, dx), 0);
			fire_rate = PlayerProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {

		boolean flip = false;
		//facing up
		if (dir == 0) {
			if (moving) {
				sprite = Sprite.player_up_a;
				if (anim % 20 > 10) flip = true;
			} else sprite = Sprite.player_up;
		}
		//facing down
		if (dir == 2) {
			if (moving) {
				sprite = Sprite.player_down_a;
				if (anim % 20 > 10) flip = true;
			} else sprite = Sprite.player_down;
		}
		//facing left
		if (dir == 3) {
			if (moving) {
				if (anim % 20 > 10) sprite = Sprite.player_side_a1;
				else sprite = Sprite.player_side_a2;
			} else sprite = Sprite.player_side;
		}
		//facing right
		if (dir == 1) {
			flip = true;
			if (moving) {
				if (anim % 20 > 10) sprite = Sprite.player_side_a1;
				else sprite = Sprite.player_side_a2;
			} else sprite = Sprite.player_side;
		}
		//calls the render method for rendering the player using the correct sprite
		screen.render_player(x - 32, y - 32, sprite, flip);
	}
}
