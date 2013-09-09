package com.me.based;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Vector;

import javax.swing.JFrame;

import com.me.based.entity.Entity;
import com.me.based.entity.mob.Oppo;
import com.me.based.entity.mob.Player;
import com.me.based.graphics.Render2D;
import com.me.based.graphics.Render3D;
import com.me.based.graphics.Screen;
import com.me.based.graphics.Sprite;
import com.me.based.input.Keyboard;
import com.me.based.input.Mouse;
import com.me.based.level.Level;
import com.me.based.level.RandomLevel;
import com.me.based.level.SpawnLevel;
import com.me.based.level.TileCoordinate;
import com.me.based.level.tile.Tile;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static String title = "Based";
	public static int scale = 1;
	private static int width = 550*2;
	private static int height = width / 16 * 9;

	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Mouse mouse;
	private Level level;
	private Player player;
	private Oppo oppo;
	private Oppo field1;
	private Oppo field2;

	private int updated = 0;

	private boolean running = false;

	private Render3D screen;
	private Render2D screen2;

	//an image with an accessible buffer
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//this converts the image object into an array of integers
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);

		frame = new JFrame();

		//listen for keyboard inputs
		key = new Keyboard();
		//listen for mouse input
		mouse = new Mouse();

		//inits level
		level = Level.spawn;
		level.set_spawn(40, 35);
		level.set_tile(39, 34, Tile.col_mob);
		//sets the player spawn
		TileCoordinate player_spawn = new TileCoordinate(2020 / 16, 3532 / 16);
		//inits the player
		player = new Player(key, player_spawn.get_x(), player_spawn.get_y(), 0);
		player.init_level(level);
		level.add(player);
		//inits the mob
		oppo = new Oppo(player_spawn.get_x(), player_spawn.get_y() - 370, 1);
		oppo.init_level(level);
		level.add(oppo);
		
		screen = new Render3D(width, height);
		screen.render_floor();
		screen.render_ballpark();
		/*
		field1 = new Oppo(50,50,2);
		field1.init_level(level);
		level.add(field1);
		
		field2 = new Oppo(50,50,3);
		field2.init_level(level);
		level.add(field2);
		 */
		this.addKeyListener(key);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}

	public static int get_width() {
		return width * scale;
	}

	public static int get_height() {
		return height * scale;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// will loop forever as long as the thread is active
	public void run() {

		//gets focus for the window
		this.requestFocus();

		long last_time = System.nanoTime();
		long timer = System.currentTimeMillis();
		//1000000000 is 1 second in nano seconds
		//ns divides up that second into x number of frames
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - last_time) / ns;
			last_time = now;
			// update is only called x times per second per ns variable
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			// render is called as fast as can be handled
			render();
			frames++;

			//this is only run every 1000 milliseconds = 1 second
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " FPS");
				updates = 0;
				frames = 0;
				updated = (updated += 45) % 360;
			}
		}
		stop();
	}

	public void update() {
		key.update();
		//updates everything existing in the level
		level.update();
	}

	public void render() {
		// assign the bs of the canvas to bs
		BufferStrategy bs = this.getBufferStrategy();

		// if the bufferstrategy doesn't exist, create it
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		screen.clear();

		//keeps track of current target mob
		oppo = (Oppo) level.get_current_oppo();
		
		//screen.load_rendered_floor();
		screen.render_floor();
		screen.render_ballpark();
		
		screen.render_distance_limiter();
		screen.draw(0, 0);

		//renders the level and sets the player in the centre
		level.render(player.get_x() - (width / 2), player.get_y() - (height / 2), screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		/* all drawing to screen happens between here and dispose() method
		*
		*
		*/
		//draws image onto the screen using contents of pixels array
		g.drawImage(image, 0, 0, get_width(), get_height(), null);

		//displays players coordinates on screen
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 50));
		g.drawString("x " + player.get_x() + "  y " + player.get_y(), get_width() - 400, get_height() - 70);

		//displays the mouse coordinates on screen
		g.drawString(Integer.toString(Mouse.getx()), get_width() - 400, get_height() - 170);
		g.drawString(Integer.toString(Mouse.gety()), get_width() - 250, get_height() - 170);

		//displays health bars on screen
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(player.get_health()), 20, 50);
		g.drawRect(20, 50, 100, 20);
		g.setColor(Color.WHITE);
		g.fillRect(20, 50, player.get_health(), 20);

		g.setColor(Color.RED);

		g.drawString(Integer.toString(oppo.get_health()), get_width() - 120, 50);
		g.setColor(Color.BLACK);
		g.drawRect(get_width() - 120, 50, 100, 20);
		g.setColor(Color.RED);
		g.fillRect(get_width() - 120, 50, oppo.get_health(), 20);

		g.setColor(Color.WHITE);
		g.drawRect(700, 470, 90, 90);

		/*
		int[] val = calc_bezier_values();
		
		for (int i = 0; i < val.length; i += 2) {
			try {
				g.fillOval(val[i], val[i + 1], 15, 15);
				//System.out.println(val[i] + "|" + val[i+1]);
				
						
			} catch (Exception e) {
				//g.drawLine(val[i], val[i + 1], width, height);
			}
		}
		*/

		//drawing the arrow
		draw_arrow(g);

		/*dispose of graphics and display on the physical screen
		 * 
		 */
		g.dispose();
		bs.show();
	}

	private int[] calc_bezier_values() {
		int[] values = new int[6];
		double xp;
		double yp;
		int num_lines = 4;
		for (double i = 0; i < num_lines + 1; i += 2) {
			xp = calc_bezier_point(i / num_lines, 0, 696, 696, width-20);
			yp = calc_bezier_point(i / num_lines, 450, 670, 670, 450);
			values[(int) i] = (int) xp;
			values[(int) i + 1] = (int) yp;
			//System.out.println(values[(int)i] + "|" + values[(int)i+1]);
		}
		//System.out.println("---");
		return values;
	}

	private double calc_bezier_point(double t, double p0, double p1, double p2, double p3) {

		double u = 1 - t;
		double tt = t * t;
		double uu = u * u;
		double uuu = uu * u;
		double ttt = tt * t;

		double p = uuu * p0;
		p += 3 * uu * t * p1;
		p += 3 * u * tt * p2;
		p += ttt * p3;

		return p;

	}

	//draws arrow on screen which points to current oppo
	public void draw_arrow(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawRoundRect(16, get_height() - 100, 90, 90, 10, 10);
		g.setColor(Color.RED);
		int seg = 8;
		int ox = 97;
		int oy = Game.get_height() - 57;

		int[] xp = { ox, ox - seg * 3, ox - seg * 3, ox - seg * 9, ox - seg * 9, ox - seg * 3, ox - seg * 3 };
		int[] yp = { oy, oy + seg * 3, oy + seg, oy + seg, oy - seg, oy - seg, oy - seg * 3 };
		Polygon poly = new Polygon(xp, yp, 7);

		double dy = oppo.get_y() - player.get_y();
		double dx = oppo.get_x() - player.get_x();

		//fix: only need to call calc_centroid once
		g.rotate(Math.atan2(dy, dx), calc_centroid(poly, true), calc_centroid(poly, false));
		g.fillPolygon(poly);
	}

	//calcuates the centre point of an polygon
	public int calc_centroid(Polygon poly, Boolean x) {
		int[] points;
		int centre = 0;
		if (x) points = poly.xpoints;
		else points = poly.ypoints;

		for (int i = 0; i < points.length; i++)
			centre += points[i];

		return centre / poly.xpoints.length;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}

}
