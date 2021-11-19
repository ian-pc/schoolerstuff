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
		return new Line(this.x1, this.y1, this.x2, this.y2, this.color, this.lineWidth);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.color);
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(this.lineWidth));
        g2.draw(new Line2D.Float(this.x1, this.y1, this.x2, this.y2));
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		int tempx1, tempx2;
		if (this.x1 < this.x2) {
			tempx1 = this.x1;
			tempx2 = this.x2;
		} else {
			tempx1 = this.x2;
			tempx2 = this.x1;
		}
		
//		System.out.println(tempx1);
//		System.out.println(tempx2);
//		System.out.println(tempy1);
//		System.out.println(tempy2);
		
		double m = ((double)(this.y2 - this.y1)/(double)(this.x2 - this.x1));
		double b = (this.y1 - m * this.x1);
		
//		double pm = -1 * 1/m;
//		double pb1 = (tempy1 - pm * tempx1);
//		double pb2 = (tempy2 - pm * tempx2);
//		
//
//		if (y < m*x + b1 && y > m*x + b2) {
//			System.out.println("ye");
//			//System.out.println(m);
//			//System.out.println(y);
//			if (y < pm*x + pb1 && y > pm*x + pb2) {
//				System.out.println("ye2");
//				return true;
//			}
//		}
		
		//System.out.println(tempx1 + " " + tempx2);
		
		if (x > tempx1 && x < tempx2 ) {
//			System.out.println(tempx2 - tempx1);
			if (Math.abs(m*x + b - y) < this.lineWidth * 0.5) {
				return true;
			}
		} else if ((tempx2 - tempx1) < this.lineWidth ){
			if (x > this.x1 - this.lineWidth && x < this.x1 + this.lineWidth) {
				if ((y > this.y2 && y < this.y1) || (y < this.y2 && y > this.y1)) {
					return true;
				}
			}
			
		}
		
		
		
		
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		this.x2 = x2;
		this.y2 = y2;
	}
	
}