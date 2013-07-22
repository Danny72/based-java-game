package com.me.based.entity.mob;

import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;
import com.me.based.input.Mouse;

public class Player extends Mob {

	
	public Player(Keyboard input, int x, int y) {
		this.x = x;
		this.y = y;
		this.input = input;
	}

	public Player(Keyboard input) {
		this.input = input;
	}

	public void update() {
		//resets anim to 0 after 125 seconds
		anim = (++anim) % 7500;
		int newx = 0, newy = 0;
		if (input.up) newy -= 2;
		if (input.down) newy += 2;
		if (input.left) newx -= 2;
		if (input.right) newx += 2;
		
		if (newx != 0 || newy != 0) {
			move(newx, newy);
			moving = true;
		} else {
			moving = false;
		}
		
		update_shooting();
	}

	private void update_shooting() {
		
		if (Mouse.getb() == 1) {
			double dx = Mouse.getx() - 300/2;
			double dy = Mouse.gety() - 168/2;
			//System.out.println(Math.atan2(dy,dx));
			shoot(x, y, Math.atan2(dy,dx));
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
		screen.render_player(x - 16, y - 16, sprite, flip);
	}
}
