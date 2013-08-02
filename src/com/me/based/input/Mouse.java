package com.me.based.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	
	private static int mousex = -1;
	private static int mousey = -1;
	private static int mouseb = -1;
	
	public static int getx() {
		return mousex;
	}
	
	public static int gety() {
		return mousey;
	}
	
	public static int getb() {
		return mouseb;
	}

	public void mouseDragged(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mouseb = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mouseb = -1;
	}
}
