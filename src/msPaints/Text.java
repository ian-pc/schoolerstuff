package msPaints;

import java.awt.Color;
import java.awt.Graphics;
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
		this.textSize = textSize;
		this.font = new Font("Helvetica", Font.PLAIN, textSize);
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Text(this.text, this.x1, this.y1, this.color, this.textSize);
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
		
		//affinetransform allows fontrendercontext to find the drawn bounds of the text. 
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
	
	public String write() {
		// TODO Auto-generated method stub
		String returnVal = ("Text" + "a" + Integer.toString(this.x1) + "a" + Integer.toString(this.y1) + "a" + 
							Integer.toString(this.color.getRed()) + "a" + Integer.toString(this.color.getBlue()) + "a" + 
							Integer.toString(this.color.getGreen()) + "a" + Integer.toString(this.textSize));
		
		//all characters are written as their ascii code. 
		for (int i = 0; i < this.text.length(); i++) {
			returnVal += "a" + Integer.toString((int) this.text.charAt(i));
		}
		
		return returnVal;
	}
	

	@Override
	protected void read(String[] tempText) {
		// TODO Auto-generated method stub
		
	}
	
}