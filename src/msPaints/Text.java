package msPaints;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Text extends Shape {
	
	private String text = "";
	private int textSize;

	private Font font;
	
	public Text(String text, int x1, int y1, Color c, int textSize) {
		super(text, x1, y1, c, textSize);
		// TODO Auto-generated constructor stub
		this.x1 = x1;
		this.y1 = y1;
		this.color = c;
		this.text = text;
		this.font = new Font("Helvetica", Font.PLAIN, textSize);
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.color);
		g.setFont(this.font);
		g.drawString(this.text, this.x1, this.y1);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
		int textWidth = (int)(font.getStringBounds(text, frc).getWidth());
		int textHeight = (int)(font.getStringBounds(text, frc).getHeight());
		
		if (x > this.x1 && x < this.x1 + textWidth) {
			if (y > this.y1 - textHeight && y < this.y1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	
}