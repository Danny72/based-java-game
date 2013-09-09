package com.me.based.entity.projectile;

import com.me.based.Game;
import com.me.based.entity.Entity;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Mouse;

public abstract class Projectile extends Entity {

	protected final int xorigin, yorigin;
	protected double angle;
	protected Sprite sprite;
	protected double newx, newy;
	protected double speed, range, damage;
	protected double distance;
	protected int owner;
	private boolean flip = false;
	protected boolean connect;
	protected boolean hit;
	protected int tick = 0;
	protected double ball_height = 0;

	public static final int FIRE_RATE = 50; //higher is slower

	public Projectile(int x, int y, double angle, int owner) {
		xorigin = x;
		yorigin = y;
		this.x = x;
		this.y = y;
		this.z = 35.5;
		this.angle = angle;
		this.owner = owner;
		this.connect = false;
		this.hit = false;
		sprite = Sprite.projectile_player;
	}

	public void change_direction(int ball_dir) {
		connect = true;
		hit = true;
		//System.out.println(ball_dir);
		//System.out.println("------");
		//angle = calc_angle();
		//newx = speed * Math.cos(angle);
		//newy = speed * Math.sin(angle);
	}

	public double calc_angle() {
		double dx = Mouse.getx() - Game.get_width() / 2;
		double dy = Mouse.gety() - Game.get_height() / 2;
		return Math.atan2(dy, dx);
	}

	protected double calc_distance() {
		double dist = 0;
		//calc the hypot using pythag
		dist = Math.sqrt(Math.abs(Math.pow(xorigin - x, 2)) + Math.pow(yorigin - y, 2));
		return dist;
	}

	public double calc_jump() {
		double y = (int) (-0.001 * Math.pow(tick, 2) + 4.0 * tick / 10);
		if (y < 0) tick = 0;
		else tick += 5;;
		return -(y / 5);
	}

	protected void move() {
		x += newx;
		
		if (hit) z += newy;
		else z -= newy;

		if (connect) {
			double y_jump = calc_jump();
			if (y_jump >= 0) {
				connect = false;
				ball_height = 0;
			} else {
				ball_height = y_jump;
			}
		}

		level.projectile_hit(this);
		if (calc_distance() > range) {
			removed = true;
		}

	}

	public void update() {
		if (level.tile_collision(x, y, newx, newy, 8) || z < 0) remove();
		else move();
	}

	public int get_owner() {
		return owner;
	}

	public Sprite get_sprite() {
		return sprite;
	}

	public int get_sprite_width() {
		return sprite.get_width();
	}

	public int get_sprite_height() {
		return sprite.get_height();
	}

	public double get_prox() {
		return x;
	}

	public double get_proy() {
		return y;
	}

	public double get_proz() {
		return z;
	}

	public double get_damage() {
		return damage;
	}

	public void render(Screen screen) {
		//screen.render_projectile((int) x - 8, (int) y - 8, this);
		//screen.render_sprite(-0.25, 0.2, z, 0.5, sprite);
		//screen.render_sprite((int) x - 8, 0, (int) y - 8, 0.5, this.sprite);
		//System.out.println(ball_height);
		screen.render_sprite(-0.25, ball_height, z, 0.5, sprite);
	}

}
