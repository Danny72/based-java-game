package com.me.based.entity.mob;

import java.util.Random;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public class Oppo extends Mob {
	
	Random rand;
	private int rand_num;

	public Oppo(int x, int y) {
		this.x = x;
		this.y = y;
		
		rand = new Random();
		
	}

	public void update() {
		
		//resets anim to 0 after 125 seconds
		anim = (++anim) % 7500;
		//changes direction every 40 updates
		if (anim % 40 >= 39) rand_num = rand.nextInt(4);
		
		int newx = 0, newy = 0;
		if (rand_num == 0) newy -= 2;
		if (rand_num == 1) newy += 2;
		if (rand_num == 2) newx -= 2;
		if (rand_num == 3) newx += 2;

		if (newx != 0 || newy != 0) {
			move(newx, newy);
			moving = true;
		} else {
			moving = false;
		}
		
	}

	public void render(Screen screen) {
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
		screen.render_player(x - 16, y - 16, sprite, flip);
	}
}
