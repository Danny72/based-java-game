package com.me.based.entity;

import java.util.Random;

import com.me.based.graphics.Screen;
import com.me.based.level.Level;

public abstract class Entity {

	protected int x, y;
	protected double z;
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected int type;

	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean is_removed() {
		return removed;
	}
	
	public void init_level(Level level) {
		this.level = level;
	}
	
	public int get_x() {
		return x;
	}
	
	public int get_y() {
		return y;
	}
	
	public int get_type() {
		return type;
	}
	
	
}
