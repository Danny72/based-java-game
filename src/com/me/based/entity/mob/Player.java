package com.me.based.entity.mob;

import com.me.based.Game;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;
import com.me.based.input.Mouse;

public class Player extends Mob {

	private boolean place_block;
	private boolean swinging;
	private int swing_state = 0;
	public double rotation, xa, za, rotationa;

	public Player(Keyboard input, int x, int y, int type) {
		this.x = 0;
		this.y = y;
		this.input = input;
		fire_rate = PlayerProjectile.FIRE_RATE;
		this.type = type;
		health = 100;
		jumping = false;
		place_block = false;
		dir = 0;
		swinging = false;
	}

	public Player(Keyboard input) {
		this.input = input;
	}

	public int get_swing_state() {
		return swing_state;
	}

	public int calc_jump() {
		int y = (int) (-0.02 * Math.pow(tick, 2) + 3.2 * tick);
		if (y < 0) tick = 0;
		else tick += 5;
		return y;
	}

	public void update() {
		int xa = 0;
		int za = 0;

		//values for hit box
		xb = x - (x - Game.get_width() / 2) - 20;
		yb = y - (y - Game.get_height() / 2) - 32;

		//decrements this.fire_rate so mob only fires when 
		//the fixed fire rate has dropped to 0 i.e. higher = slower
		if (fire_rate > 0) fire_rate--;

		//resets anim to 0 after 125 seconds
		anim = (++anim) % 7500;

		int newx = 0, newy = 0;
		if (input.up) newy--;
		if (input.down) newy++;
		if (input.left) newx--;
		if (input.right) newx++;

		//rotation += rotationa;
		//rotationa *= 0.2;

		if (input.jump) jumping = true;
		if (input.shift) place_block = true;
		if (Mouse.getb() == 1) {
			swinging = true;
			anim = 0;
		}

		//if jump key pressed, calcuate offset for jumping
		if (jumping) {
			int y_jump = calc_jump();
			if (y_jump <= 0) {
				jumping = false;
				jump_offset = 0;
				//only offset y if player is standing on something solid
				if (current_tile("solid")) y_offset = -25;
			} else {
				jump_offset = y_jump;
			}
		}

		//if place block key pressed, get coords of next tile and place a block there
		if (place_block) {
			int x = (Integer) next_tile_coords()[0];
			int y = (Integer) next_tile_coords()[1];
			System.out.println("x: " + x + " | y: " + y);
			level.set_tile(x, y, 0xFFFFFFFF);
			place_block = false;
		}

		//figures out which direction player is LOOKING
		if (Mouse.getx() >= get_xb() + (Game.get_width() / 4)) looking = 1;
		else if (Mouse.getx() <= get_xb() - (Game.get_width() / 5)) looking = 3;
		else if (Mouse.gety() < get_yb()) looking = 0;
		else looking = 2;

		if (newx != 0 || newy != 0) {
			move(newx, newy);
			moving = true;
		} else {
			moving = false;
		}

		clear();
		update_shooting();
	}

	protected void shoot(int x, int y, double dir) {
		Projectile p = new PlayerProjectile(x, y, dir, 0);
		level.add_projectile(p);
	}

	protected void update_shooting() {
		if (Mouse.getb() == 0 && fire_rate <= 0) {
			double dx = Mouse.getx() - Game.get_width() / 2;
			double dy = Mouse.gety() - Game.get_height() / 2;
			shoot(x, y - jump_offset, Math.atan2(dy, dx));
			fire_rate = PlayerProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		int num = 16;
		int divisor = num / 8;
		boolean flip = false;

		if (swinging) {
			if (anim % num >= divisor * 1 && anim % num < divisor * 2) sprite = Sprite.player_swing1;
			if (anim % num >= divisor * 2 && anim % num < divisor * 3) sprite = Sprite.player_swing2;
			if (anim % num >= divisor * 3 && anim % num < divisor * 4) sprite = Sprite.player_swing3;
			if (anim % num >= divisor * 4 && anim % num < divisor * 5) sprite = Sprite.player_swing4;
			if (anim % num >= divisor * 5 && anim % num < divisor * 6) sprite = Sprite.player_swing5;
			if (anim % num >= divisor * 6 && anim % num < divisor * 7) sprite = Sprite.player_swing6;
			if (anim % num >= divisor * 7 && anim % num < divisor * 8) sprite = Sprite.player_swing7;
			if (anim >= num && anim < num + 10) sprite = Sprite.player_swing7;
			if (anim > num + 10) swinging = false;
		} else sprite = Sprite.player_static;

		//takes note of where in the swing player is
		swing_state = Integer.parseInt(sprite.get_name().split("swing")[1]);

		//calls the render method for rendering the player using the correct sprite
		//screen.render_player(x - 32, y - 32, sprite, hsprite, flip, jump_offset - y_offset);

		screen.render_sprite(-0.75, 0.0, 2.5, 0.5, sprite);

	}
}
