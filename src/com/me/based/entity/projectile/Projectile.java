package com.me.based.entity.projectile;

import com.me.based.entity.Entity;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final int xorigin, yorigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double newx, newy;
	protected double speed, range, damage;
	protected double distance;
	protected int owner;
	
	public static final int FIRE_RATE = 10; //higher is slower

	public Projectile(int x, int y, double angle, int owner) {
		xorigin = x;
		yorigin = y;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.owner = owner;
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
	
	public double get_damage() {
		return damage;
	}
	

	protected void move() {

	}

	public void render(Screen screen) {

	}

}
