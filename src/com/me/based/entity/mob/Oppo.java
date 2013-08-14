package com.me.based.entity.mob;

import java.util.Random;

import com.me.based.Game;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class Oppo extends Mob {

	Random rand;
	private int rand_num;

	public Oppo(int x, int y, int type) {
		this.x = x;
		this.y = y;
		move_speed = 4;
		rand = new Random();
		this.type = type;
		health = 100;
		fire_rate = Projectile.FIRE_RATE;
	}
	
	public void update() {
		//values for hit box
		xb = x - (x - Game.get_width() / 2) - 20;
		yb = y - (y - Game.get_height() / 2) - 32;

		//decrements this.fire_rate so mob only fires when 
		//the fixed fire rate has dropped to 0 i.e. higher = slower
		if (fire_rate > 0) fire_rate--;

		//resets anim to 0 after 125 seconds
		anim = (++anim) % 7500;

		//changes direction every 40 updates
		if (anim % 40 >= 39) rand_num = rand.nextInt(4);

		int newx = 0, newy = 0;
		if (rand_num == 0) newy--;
		if (rand_num == 1) newy++;
		if (rand_num == 2) newx--;
		if (rand_num == 3) newx++;

		if (newx != 0 || newy != 0) {
			move(newx, newy);
			moving = true;
		} else {
			moving = false;
		}
		
		clear();
		update_shooting();
	}
	
	protected void update_shooting() {
		
		if (rand.nextInt(100) < 3 && fire_rate <= 0) {
			double angle = 0;
			if (dir == 1) angle = 0.0;
			if (dir == 2) angle = Math.PI/2;
			if (dir == 3) angle = Math.PI;
			if (dir == 0) angle = Math.PI + Math.PI/2;
			shoot(x, y - jump_offset, angle, 1);
			fire_rate = Projectile.FIRE_RATE;
		}
	}


	public void render(Screen screen) {
		//values for hit box
		//need the screen class for the moment
		xb = x - screen.get_xoffset() - 25;
		yb = y - screen.get_yoffset() - 29;

		boolean flip = false;
		//facing up
		if (dir == 0) {
			if (moving) {
				sprite = Sprite.oppo_up_a;
				if (anim % 20 > 10) flip = true;
			} else sprite = Sprite.oppo_up;
		}
		//facing down
		if (dir == 2) {
			if (moving) {
				sprite = Sprite.oppo_down_a;
				if (anim % 20 > 10) flip = true;
			} else sprite = Sprite.oppo_down;
		}
		//facing left
		if (dir == 3) {
			if (moving) {
				if (anim % 20 > 10) sprite = Sprite.oppo_side_a1;
				else sprite = Sprite.oppo_side_a2;
			} else sprite = Sprite.oppo_side;
		}
		//facing right
		if (dir == 1) {
			flip = true;
			if (moving) {
				if (anim % 20 > 10) sprite = Sprite.oppo_side_a1;
				else sprite = Sprite.oppo_side_a2;
			} else sprite = Sprite.oppo_side;
		}
		//calls the render method for rendering the player using the correct sprite
		screen.render_player(x - 32, y - 32, sprite, null, flip, 0);
	}
}
