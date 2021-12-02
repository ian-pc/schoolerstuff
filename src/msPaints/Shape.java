package msPaints;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import msPaints.Pen.Pixel;

public abstract class Shape {
	protected int x1, y1, x2, y2;
	protected Color color;
	protected String type;
	protected String text;
	protected int textSize;
	
	//pen and line variables are needed so that they can be interacted with outside of the pen/line function especially in move
	protected int lineWidth;
	protected ArrayList<Pixel> pen;
	protected ArrayList<Pixel> distFrom0;
	
	public Shape(int x1, int y1, int x2, int y2, Color c) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = c;
	}
	
	public Shape(int x1, int y1, int x2, int y2, Color c, int lineWidth) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = c;
		this.lineWidth = lineWidth;
	}

	public Shape(String text, int x1, int y1, Color c, int textSize) {
		// TODO Auto-generated constructor stub		
		this.textSize = textSize;
		this.text = text;
		this.x1 = x1;
		this.y1 = y1;
		this.color = c;
	}

	public Shape(Color c, int lineWidth, ArrayList<Pixel> pen) {
		this.color = c;
		this.lineWidth = lineWidth;
		this.pen = pen;
	}
	
	public void move(int x_r, int y_r, int x1, int y1) {
		int width = Math.abs(this.x2 - this.x1);
		int height = Math.abs(this.y2 - this.y1);
		this.x1 = x1 - x_r;
		this.y1 = y1 - y_r;
		this.x2 = this.x1 + width;
		this.y2 = this.y1 + height;
		
		if (this.pen != null) {
			for (int i = 0; i < pen.size(); i++) {
				pen.get(i).x = -distFrom0.get(i).x + x1 - x_r; 
				pen.get(i).y = -distFrom0.get(i).y + y1 - y_r;
			}
		}
	}

	public abstract Shape copy();

	public abstract void draw(Graphics g);

	public abstract boolean isOn(int x, int y);

	public abstract void resize(int x1, int y1, int x2, int y2);
	
	public abstract String write();

	protected abstract void read(String[] tempText);
	
}
