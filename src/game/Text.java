package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Text {

	private String text;
	private Font font;
	private Color color;
	int x, y, h, w;
	public Text (Graphics g, String text, int x, int y, Font f, Color c) {

		this.text = text;
		this.font = f;
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
		this.w = (int)(font.getStringBounds(text, frc).getWidth());
		this.h = (int)(font.getStringBounds(text, frc).getHeight());
		this.x = x - this.w/2;
		this.y = y + this.h/2 - 10;
		this.color = c;
		g.setColor(this.color);
		g.setFont(this.font);
		g.drawString(this.text, this.x, this.y);
	}
	
	public Text (Graphics g, String text, int x, int y, Font f, Color c, boolean a) {

		this.text = text;
		this.font = f;
		AffineTransform affinetransform = new AffineTransform();    
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);  
		this.h = (int)(font.getStringBounds(text, frc).getHeight());
		this.x = x;
		this.y = y + this.h;
		this.color = c;
		g.setColor(this.color);
		g.setFont(this.font);
		g.drawString(this.text, this.x, this.y);
	}
	
}
