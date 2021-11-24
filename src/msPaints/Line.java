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

		
		double theta = Math.atan2((this.y2 - this.y1), (this.x2 - this.x1));
		double o = Math.sin(theta)*(this.lineWidth/2);
		double n = Math.cos(theta)*(this.lineWidth/2);
		
		double tempx1, tempx2, tempy1, tempy2;

		tempx1 = this.x1 - n;
		tempx2 = this.x2 + n;
		tempy1 = this.y1 - o;
		tempy2 = this.y2 + o;
		
		double m = ((double)(this.y2 - this.y1)/(double)(this.x2 - this.x1));
		double b = (this.y1 - m * this.x1);
		double pm = -1/m;
		double pb1 = tempy1 - pm * tempx1;
		double pb2 = tempy2 - pm * tempx2;
		if (pb2 < pb1) {
			double temp = pb2;
			pb2 = pb1;
			pb1 = temp;
		}
		double dist = Math.abs(Math.sin(90)*((this.lineWidth)/Math.sin(90 - theta)));
		double b1 = b - dist;
		double b2 = b + dist;

		
//		System.out.println("m " + m);
//		System.out.println("b1 " + b1);
//		System.out.println("b2 " + b2);
//		System.out.println("pm " + pm);
//		System.out.println("pb1 " + pb1);
//		System.out.println("pb2 " + pb2);
//		System.out.println("dist " + dist);
//		System.out.println();

		int boundx1, boundx2;
		if (this.x1 < this.x2) {
			boundx1 = this.x1;
			boundx2 = this.x2;
		} else {
			boundx1 = this.x2;
			boundx2 = this.x1;
		}
		int boundy1, boundy2;
		if (this.y1 < this.y2) {
			boundy1 = this.y1;
			boundy2 = this.y2;
		} else {
			boundy1 = this.y2;
			boundy2 = this.y1;
		}
		if (boundx2 - boundx1 < this.lineWidth * 2) {
			if (x > boundx1 - this.lineWidth/2 && x < boundx2 + this.lineWidth/2) {
				if (y > boundy1 - this.lineWidth/2 && y < boundy2 + this.lineWidth/2) {
					return true;
				}
			}
		} else if (boundy2 - boundy1 < this.lineWidth * 2) {
			if (y > boundy1 - this.lineWidth/2 && y < boundy2 + this.lineWidth/2) {
				if (x > boundx1 - this.lineWidth/2 && x < boundx2 + this.lineWidth/2) {
					return true;
				}
			}
		} else {
			if (y > pm*x + pb1 && y < pm*x + pb2) {
//			System.out.println("ye");
				if (y > m*x + b1 && y < m*x + b2) {
	//				System.out.println("ye2");
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