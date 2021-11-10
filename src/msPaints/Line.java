	package msPaints;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Line extends Shape {
	
	private int lineWidth = 1;

	public Line(int x1, int y1, int x2, int y2, Color c, int lineWidth) {
		super(x1, y1, x2, y2, c, lineWidth);
		// TODO Auto-generated constructor stub
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = c;
		this.lineWidth = lineWidth;
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
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(lineWidth));
        g2.draw(new Line2D.Float(this.x1, this.y1, this.x2, this.y2));
//		g.drawLine(this.x1, this.y1, this.x2, this.y2);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		int tempx1 = this.x1, tempx2 = this.x2, tempy1 = this.y1, tempy2 = this.y2;
		if (x1 > x2) {
			tempx1 = x2;
			tempx2 = x1;
		}
		if (y1 > y2) {
			tempy1 = y2;
			tempy2 = y1;
		}
		
		if (x > tempx1 && x < tempx2) {
			if (y > tempy1 && y < tempy2) {
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