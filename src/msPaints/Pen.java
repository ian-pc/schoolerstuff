package msPaints;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Pen extends Shape {
	
		public class Pixel {
			int x, y;
			public Pixel(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}
		
		public ArrayList<Pixel> pen;
		
		public Pen(Color c, int lineWidth, ArrayList<Pixel> pen) {
			super(c, lineWidth, pen);
			// TODO Auto-generated constructor stub
			this.color = c;
			this.lineWidth = lineWidth;
			this.pen = pen;
		}
		

		@Override
		public Shape copy() {
			// TODO Auto-generated method stub
			return new Pen(this.color, this.lineWidth, this.pen);
		}


		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			for (int i = 1; i < pen.size(); i++) {
//				g.setColor(color);
//				g.fillRect(pen.get(i).x - 2, pen.get(i).y - 2, 4, 4);
				g.setColor(this.color);
				Graphics2D g2 = (Graphics2D) g;
		        g2.setStroke(new BasicStroke(this.lineWidth));
		        if (pen.size() > 1) {
		        	g2.draw(new Line2D.Float(pen.get(i).x, pen.get(i).y, pen.get(i - 1).x, pen.get(i - 1).y));
		        }
		    }
		}
 
		@Override
		public boolean isOn(int x, int y) {
			// TODO Auto-generated method stub
			for (int i = 1; i < pen.size(); i++) {
				if (x > pen.get(i).x && x < pen.get(i).x + 4) {
					if (y > pen.get(i).y && y < pen.get(i).y + 4) {
						return true;
					}
				}
				int tempx1, tempx2;
				if (pen.get(i).x < pen.get(i - 1).x) {
					tempx1 = pen.get(i).x;
					tempx2 = pen.get(i - 1).x;
				} else {
					tempx1 = pen.get(i - 1).x;
					tempx2 = pen.get(i).x;
				} 
				double m = ((double)(pen.get(i - 1).y) - pen.get(i).y)/(double)(pen.get(i - 1).x - pen.get(i).x);
				double b = (pen.get(i).y - m * pen.get(i).x);
				//System.out.println(tempx1 + " " + tempx2);
				if (x > tempx1 && x < tempx2 ) {
//					System.out.println(tempx2 - tempx1);
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
			}
			
			
			return false;
		}

		@Override
		public void resize(int x1, int y1, int x2, int y2) {
			// TODO Auto-generated method stub
			pen.add(new Pixel(x1, y1));
		}

		
	}