package msPaints;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	protected int x1, y1, x2, y2;
	protected Color color;
	protected String type;
	protected String text;
	protected int textSize;
	protected int lineWidth;

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

	public void move(int x1, int y1, int x2, int y2) {
		//x = x2 - x1;
		//y = y2 - y1;
		this.x1 += (x2 - x1);
		this.y1 += (y2 - y1);
		this.x2 += (x2 - x1);
		this.y2 += (y2 - y1);
	}

	public abstract Shape copy();

	public abstract void draw(Graphics g);

	public abstract boolean isOn(int x, int y);

	public abstract void resize(int x1, int y1, int x2, int y2);
}
