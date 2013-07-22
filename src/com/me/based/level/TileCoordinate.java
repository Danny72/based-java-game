package com.me.based.level;

//helper class for sorting out player spawn and coordinates
public class TileCoordinate {
	
	private int x, y;
	private final int TILE_SIZE = 16;
	
	public TileCoordinate(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}
	
	public int get_x() {
		return x;
	}

	public int get_y() {
		return y;
	}
	
	public int[] get_xy() {
		int[] res = new int[2];
		res[0] = x;
		res[1] = y;
		return res;
	}
	
	
}
