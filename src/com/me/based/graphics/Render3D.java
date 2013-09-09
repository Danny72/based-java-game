package com.me.based.graphics;

import java.awt.Polygon;

import com.me.based.input.Mouse;
import com.me.based.level.tile.Tile;

public class Render3D extends Screen {

	public final int width;
	public final int height;
	

	private double[] zbuffer;
	private double render_distance = 60000;
	private double forward, right, up, cosine, sine;
	double head_movement;
	private int sprite_sheet_w = 256;
	private int anim = 0;
	private boolean swinging = false;
	private Sprite rend_sprite;

	public int[] bezier_values;

	public Render3D(int width, int height) {
		super(width, height);
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		floor_pixels = new int[width * height];
		floor_pixels[0] = 58008;
		System.out.println(floor_pixels[0]);
		zbuffer = new double[width * height];
		rend_sprite = Sprite.player_static;
		forward = 0;
		up = 0;
		right = 0;
		double rotation = 0;//game.player.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);
	}

	public void render_tile(int xp, int yp, Tile tile, boolean flip) {

	}

	public void render_sprite(double x, double y, double z, double h_off, Sprite sp) {
		double up_correct = -0.062;
		double right_correct = 0.062;
		double forward_correct = 0.062;
		double walk_correct = 0.062;

		//adjusts the x,y,z for various movements
		double xc = ((x / 2) - (right * right_correct)) * 2 + h_off;
		double yc = ((y / 2) - (up * up_correct) + (head_movement * walk_correct)) * 2 + h_off;
		double zc = ((z / 2) - (forward * forward_correct)) * 2;

		//Calculates the rotation on the sprite for x,y,z
		double rot_x = xc * cosine - zc * sine;
		double rot_y = yc;
		double rot_z = zc * cosine + xc * sine;

		//works out the centre point
		double x_centre = width / 2;
		double y_centre = height / 2;

		//fix: height instead of width
		double xpixel = rot_x / rot_z * height + x_centre;
		double ypixel = rot_y / rot_z * height + y_centre;

		double x_pixell;
		double x_pixelr;
		if (sp.get_width() / sp.get_height() == 1) {
			x_pixell = xpixel - (height / 2) / rot_z;
			x_pixelr = xpixel + (height / 2) / rot_z;
		} else {
			x_pixell = xpixel - (height) / rot_z;
			x_pixelr = xpixel + (height) / rot_z;
		}
		double y_pixell = ypixel - (height / 2) / rot_z;
		double y_pixelr = ypixel + (height / 2) / rot_z;

		int xpl = (int) x_pixell;
		int xpr = (int) x_pixelr;
		int ypl = (int) y_pixell;
		int ypr = (int) y_pixelr;

		if (xpl < 0) {
			xpl = 0;
		}
		if (xpr > width) {
			xpr = width;
		}
		if (ypl < 0) {
			ypl = 0;
		}
		if (ypr > height) {
			ypr = height;
		}

		rot_z *= 8;

		for (int yp = ypl; yp < ypr; yp++) {
			// formats a variable for accepting a texture
			double pixel_rotationy = (y_pixelr - yp) / (y_pixell - y_pixelr);
			int ytexture = (int) (pixel_rotationy * sp.get_height());

			for (int xp = xpl; xp < xpr; xp++) {

				double pixel_rotationx = (x_pixelr - xp) / (x_pixell - x_pixelr);
				int xtexture = (int) (pixel_rotationx * sp.get_width());

				if (zbuffer[xp + yp * width] > rot_z) {
					if (((xtexture & 127)) + ((ytexture & 63)) * sprite_sheet_w >= 90112) continue;
					int colour = sp.pixels[((xtexture & sp.get_width() - 1)) + ((ytexture & sp.get_height() - 1)) * sp.get_width()];

					//transparency using pink and ARGB
					if (colour != 0xFFFF00FF) {
						pixels[xp + yp * width] = colour;
						zbuffer[xp + yp * width] = rot_z;
					}

				}
			}
		}

	}

	public void draw(int xOffset, int yOffset) {
		for (int y = 0; y < this.height; y++) {

			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= this.height) continue;

			for (int x = 0; x < this.width; x++) {

				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= this.width) continue;

				int alpha = pixels[x + y * this.width];

				if (alpha > 0) {
					pixels[xPix + yPix * width] = pixels[x + y * this.width];
					//System.out.println("X: " + (xPix + yPix * width));
				}
			}
		}
		//

	}

	public void render_floor() {
		// Math.sin(game.time % 1000.0 / 50);
		double rotation = 0;//game.player.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		int floor_pos = 8;
		int ceiling_pos = 150;

		forward = 0;
		right = 0;
		up = 0;
		head_movement = 0;

		bezier_values = calc_bezier_values();

		double ceiling;
		double z;
		double depth;

		for (int y = 0; y < height; y++) {

			ceiling = (y - height / 2.0) / height;
			z = (floor_pos + up) / ceiling;

			if (ceiling < 0) z = (ceiling_pos - up) / -ceiling;

			for (int x = 0; x < width; x++) {

				depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;

				//shift to right
				int xpix = (int) ((xx + right) * 8) + 64;
				int ypix = (int) ((yy + forward) * 8) - 64;

				//if (z > 0) System.out.println(x + "|" + y);
				zbuffer[x + y * width] = z;

				//if less than equator, use sky texture, else use floor
				if (x + y * width < (width * height / 2)) {
					pixels[x + y * width] = Sprite.water.pixels[(xpix & 63) + (ypix & 63) * Sprite.water.get_width()];
				} else {
					//all drawing on floor takes place here

					int[] xpo = { 650, width, width, 651};
					int[] ypo = { 460, 343, 470, 730 };
					Polygon poly = new Polygon(xpo, ypo, 4);

					//draw batting mound with bezier curve
					
					double yp = calc_bezier_point((double) x / (double) width, height, 420, 420, height);
					if (y > yp) {
						pixels[x + y * width] = Sprite.path.pixels[(xpix & 63) + (ypix & 63) * Sprite.path.get_width()];
					}
	
					else {
					
						pixels[x + y * width] = Sprite.grass.pixels[(xpix & 63) + (ypix & 63) * Sprite.grass.get_width()];
					}

					//draw mound under pitcher
					if (xpix >= 0 && xpix < 127 && ypix >= 384 && ypix < 512) {
						int color = Sprite.mound.pixels[(xpix & 127) + (ypix & 127) * Sprite.mound.get_width()];
						if (color != 0xFFFF00FF) {
							pixels[x + y * width] = color;
						}
					}
					/*
					if (x >= 630 && x < width && y >= 346 && y < 700) {
						if (poly.contains(x, y)) {
							pixels[x + y * width] = Sprite.path.pixels[(xpix & 63) + (ypix & 63) * Sprite.path.get_width()];
						}
					}
					*/
				}
			}
		}

		//render player
		//render_sprite(-0.5, 0.0, 2.5, 0.5, Sprite.bb_grass);
	}
	
	public void load_rendered_floor() {
		//System.out.println(floor_pixels[0]);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = floor_pixels[i];
		}
		//System.out.println("fff");
	}

	private int[] calc_bezier_values() {
		int[] values = new int[60];
		double xp;
		double yp;
		int num_lines = 60;
		for (double i = 0; i < num_lines; i += 2) {
			xp = calc_bezier_point(i / num_lines, 0, 691, 691, width);
			yp = calc_bezier_point(i / num_lines, height, 500, 500, height);
			values[(int) i] = (int) xp;
			values[(int) i + 1] = (int) yp;
		}
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

	private void render_tile(int x, int y, int xpix, int ypix, Sprite sp) {
		int color = sp.pixels[(xpix & 127) + (ypix & 127) * sp.get_width()];
		if (color != 0xFFFF00FF) pixels[x + y * width] = color;

	}

	//renders the ballpark background
	public void render_ballpark() {
		
		
		int z_point = 35;
		int space = 2;
		int multiple = 2;

		//back
		for (int i = -6; i < 5; i += 2) {
			render_wall(i, i + 2, z_point, z_point, 0);
			render_wall(i, i + 2, z_point, z_point, 1);
			render_wall(i, i + 2, z_point, z_point, 2);
			render_wall(i, i + 2, z_point, z_point, 3);
		}
		//left
		space = 6;
		for (int i = -2; i > -10; i -= 2) {
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 0);
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 1);
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 2);
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 3);

		}
		//right
		for (int i = 2; i < 10; i += 2) {
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 0);
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 1);
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 2);
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 3);
		}
		//left vert
		multiple = -8;
		for (int i = -8; i > -20; i -= 2) {
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 0);
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 1);
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 2);
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 3);

		}
		//right vert
		multiple = 8;
		for (int i = 8; i < 20; i += 2) {
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 0);
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 1);
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 2);
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 3);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			floor_pixels[i] = pixels[i];
		}

	}

	public void render_wall(double xleft, double xright, double zdistance_left, double zdistance_right, double yheight) {

		double up_correct = 0.062;
		double right_correct = 0.062;
		double forward_correct = 0.062;
		double walk_correct = 0.062;

		// as each block is 0.5, need to half wall co-ordinates
		//xleft /= 2; xright /= 2; zdistance_left /= 2; zdistance_right /= 2;
		yheight /= 2;

		double xc_left = ((xleft) - (right * right_correct)) * 2;
		double zc_left = ((zdistance_left) - (forward * forward_correct)) * 2;

		double rot_left_side_x = xc_left * cosine - zc_left * sine;
		double ycorner_tl = ((-yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double ycorner_bl = ((+0.5 - yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double rot_left_side_z = zc_left * cosine + xc_left * sine;

		double xc_right = ((xright) - (right * right_correct)) * 2;
		double zc_right = ((zdistance_right) - (forward * forward_correct)) * 2;

		double rot_right_side_x = xc_right * cosine - zc_right * sine;
		double ycorner_tr = ((-yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double ycorner_br = ((+0.5 - yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double rot_right_side_z = zc_right * cosine + xc_right * sine;

		// Clipping

		double tex30 = 0;
		double tex40 = 32;
		double clip = 0.5;

		if (rot_left_side_z < clip && rot_right_side_z < clip) {
			return;
		}

		if (rot_left_side_z < clip) {
			double clip0 = (clip - rot_left_side_z) / (rot_right_side_z - rot_left_side_z);
			rot_left_side_z = rot_left_side_z + (rot_right_side_z - rot_left_side_z) * clip0;
			rot_left_side_x = rot_left_side_x + (rot_right_side_x - rot_left_side_x) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}

		if (rot_right_side_z < clip) {
			double clip0 = (clip - rot_left_side_z) / (rot_right_side_z - rot_left_side_z);
			rot_right_side_z = rot_left_side_z + (rot_right_side_z - rot_left_side_z) * clip0;
			rot_right_side_x = rot_left_side_x + (rot_right_side_x - rot_left_side_x) * clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}

		double xpixel_left = (rot_left_side_x / rot_left_side_z * height + width / 2);
		double xpixel_right = (rot_right_side_x / rot_right_side_z * height + width / 2);

		if (xpixel_left >= xpixel_right) {
			return;
		}

		int xpixel_left_int = (int) (xpixel_left);
		int xpixel_right_int = (int) (xpixel_right);

		if (xpixel_left_int < 0) {
			xpixel_left_int = 0;
		}

		if (xpixel_right_int > width) {
			xpixel_right_int = width;
		}

		double ypixel_left_top = ycorner_tl / rot_left_side_z * height + height / 2.0;
		double ypixel_left_bot = ycorner_bl / rot_left_side_z * height + height / 2.0;
		double ypixel_right_top = ycorner_tr / rot_right_side_z * height + height / 2.0;
		double ypixel_right_bot = ycorner_br / rot_right_side_z * height + height / 2.0;

		double tex1 = 1 / rot_left_side_z;
		double tex2 = 1 / rot_right_side_z;
		double tex3 = tex30 / rot_left_side_z;
		double tex4 = tex40 / rot_right_side_z - tex3;

		// keep rendering wall until the x is inverted
		for (int x = xpixel_left_int; x < xpixel_right_int; x++) {

			double pixel_rotationx = (x - xpixel_left) / (xpixel_right - xpixel_left);
			//int xtexture = (int) ((tex3 + tex4 * pixel_rotationx) / (tex1 + (tex2 - tex1) * pixel_rotationx));
			int xtexture = (int) (64 * pixel_rotationx);

			double ypixel_top = ypixel_left_top + (ypixel_right_top - ypixel_left_top) * pixel_rotationx;
			double ypixel_bot = ypixel_left_bot + (ypixel_right_bot - ypixel_left_bot) * pixel_rotationx;

			int ypixel_top_int = (int) (ypixel_top);
			int ypixel_bot_int = (int) (ypixel_bot);

			// ?
			if (ypixel_top_int < 0) {
				ypixel_top_int = 0;
			}

			if (ypixel_bot_int > height) {
				ypixel_bot_int = height;
			}

			for (int y = ypixel_top_int; y < ypixel_bot_int; y++) {
				double pixel_rotationy = (y - ypixel_top) / (ypixel_bot - ypixel_top);
				int ytexture = (int) (64 * pixel_rotationy);

				double brightness = render_distance / (1 / (tex1 + (tex2 - tex1) * pixel_rotationx) * 8);
				if (brightness > (render_distance / zbuffer[x + y * width])) {
					pixels[x + y * width] = Sprite.fence.pixels[((xtexture & 63)) + ((ytexture & 63)) * Sprite.fence.get_width()];
					zbuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixel_rotationx) * 8;
				} else {
					continue;
				}

			}
		}
	}

	public void render_distance_limiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];

			int brightness = (int) (render_distance / zbuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
		
	}
}
