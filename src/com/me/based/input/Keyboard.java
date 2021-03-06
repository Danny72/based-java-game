package com.me.based.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	//array of possible input keys for program
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, jump;
	public boolean shift;
	

	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		jump = keys[KeyEvent.VK_SPACE];
		shift = keys[KeyEvent.VK_SHIFT];
		/*
		for (int i = 0 ; i < keys.length; i++) {
			if (keys[i]) System.out.println((char) i );
		}
		*/

	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}
