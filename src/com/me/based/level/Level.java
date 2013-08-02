package com.me.based.level;

import java.util.ArrayList;
import java.util.List;

import com.me.based.Game;
import com.me.based.entity.Entity;
import com.me.based.entity.mob.Mob;
import com.me.based.entity.mob.Oppo;
import com.me.based.entity.projectile.PlayerProjectile;
import com.me.based.entity.projectile.Projectile;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.level.tile.Tile;

public class Level {

	protected int width, height;
	//each integer stored is an ID for a tile
	protected int[] tiles;
	private int tile_precision = 6;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

	public static Level spawn = new SpawnLevel("/levels/spawn.png");

	//constructor for random level generator
	public Level(int w, int h) {
		width = w;
		height = h;
		tiles = new int[width * height];
		generate_level();
	}

	//constructor for image based level
	public Level(String path) {
		tiles = new int[64 * 64 + 2];
		load_level(path);
		generate_level();
	}

	public void set_spawn(int x, int y) {
		tiles[x + y * width] = Tile.col_spawn;
	}

	public void set_tile(int x, int y, int tile_type) {
		tiles[x + y * width] = tile_type;
	}

	protected void generate_level() {

	}

	protected void load_level(String path) {

	}

	public void update() {
		//System.out.println(Game.get_width() + " | " + Game.get_height());
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	private void time() {

	}

	/*x, y is the position of the entity that might collide with a tile
	 * xa, ya is the direction the entity is moving in
	 * size is the size of the entity moving
	*/
	public boolean tile_collision(double x, double y, double xa, double ya, int size) {
		boolean solid = false;

		//loop for checking each corner of tile
		for (int i = 0; i < 4; i++) {
			int xc = (((int) x + (int) xa) + i % 2 * size - 5) / 64;
			int yc = (((int) y + (int) ya) + i / 2 * size - 3) / 64;
			if (get_tile(xc, yc).solid()) solid = true;
		}
		return solid;
	}

	public void projectile_hit(Projectile p) {

		for (Entity e : entities) {
			if (e instanceof Oppo) {
				int x = (int) e.get_x();
				int y = (int) e.get_y();
				double px = p.get_prox();
				double py = p.get_proy();
				if (x - 20 < px && x + 20 > px && y - 30 < py && y + 30 > py) {
					((Oppo) e).set_health(10);
					p.remove();
				}
			}
		}

	}

	public void add(Entity e) {
		entities.add(e);
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	public void add_projectile(Projectile p) {
		p.init_level(this);
		projectiles.add(p);
	}

	public void remove_projectile(Projectile p) {
		projectiles.remove(p);
	}

	public List<Projectile> get_projectiles() {
		return projectiles;
	}

	public void render(int xscroll, int yscroll, Screen screen) {
		//sets offsets to the location of the player
		screen.set_offset(xscroll, yscroll);

		//this defines the render region of the screen using corner pins
		//converts pixel precision to tile precision
		//+16 renders one extra tile for horiz and vert to prevent flickering
		int x0 = xscroll >> tile_precision;
		int x1 = (xscroll + screen.get_width() + 64) >> tile_precision;
		int y0 = yscroll >> tile_precision;
		int y1 = (yscroll + screen.get_height() + 64) >> tile_precision;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				get_tile(x, y).render(x << tile_precision, y << tile_precision, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			//System.out.println(p.get_owner());
			p.render(screen);
		}
	}

	//check e55 for alternative method
	public Tile get_tile(int x, int y) {
		//if (x < 0 || x >= width || y < 0 || y >= height) return Tile.void_tile;
		if (x < 0 || x >= width) x = x & 63;
		if (y < 0 || y >= height) y = y & 63;
		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass;
		if (tiles[x + y * width] == Tile.col_flower) return Tile.flower;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock;
		if (tiles[x + y * width] == Tile.col_path) return Tile.path;
		if (tiles[x + y * width] == Tile.col_water) return Tile.water;
		if (tiles[x + y * width] == Tile.col_spawn) return Tile.void_tile;
		if (tiles[x + y * width] == Tile.col_mob) return Tile.mob;
		return Tile.void_tile;
	}

}
