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
		
		
		public Pen(Color c, int lineWidth, ArrayList<Pixel> pen) {
			super(c, lineWidth, pen);
			// TODO Auto-generated constructor stub
			this.color = c;
			this.lineWidth = lineWidth;
			this.pen = pen;
			this.distFrom0 = new ArrayList<>();
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

				this.x1 = pen.get(0).x;
				this.y1 = pen.get(0).y;
		    }
		}
 
		@Override
		public boolean isOn(int x, int y) {
			// TODO Auto-generated method stub
			for (int i = 1; i < pen.size(); i++) {
				double theta = Math.atan2((pen.get(i - 1).y - pen.get(i).y), (pen.get(i - 1).x - pen.get(i).x));
				double o = Math.sin(theta)*(this.lineWidth/2);
				double n = Math.cos(theta)*(this.lineWidth/2);
				
				double tempx1, tempx2, tempy1, tempy2;

				tempx1 = pen.get(i).x - n;
				tempx2 = pen.get(i - 1).x + n;
				tempy1 = pen.get(i).y - o;
				tempy2 = pen.get(i - 1).y + o;
				
				double m = ((double)(pen.get(i - 1).y - pen.get(i).y)/(double)(pen.get(i - 1).x - pen.get(i).x));
				double b = (pen.get(i).y - m * pen.get(i).x);
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

				int boundx1, boundx2;
				if (pen.get(i).x < pen.get(i - 1).x) {
					boundx1 = pen.get(i).x;
					boundx2 = pen.get(i - 1).x;
				} else {
					boundx1 = pen.get(i - 1).x;
					boundx2 = pen.get(i).x;
				}
				int boundy1, boundy2;
				if (pen.get(i).y < pen.get(i - 1).y) {
					boundy1 = pen.get(i).y;
					boundy2 = pen.get(i - 1).y;
				} else {
					boundy1 = pen.get(i - 1).y;
					boundy2 = pen.get(i).y;
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
//					System.out.println("ye");
						if (y > m*x + b1 && y < m*x + b2) {
			//				System.out.println("ye2");
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
			distFrom0.add(new Pixel(pen.get(0).x - x1, pen.get(0).y - y1));
		}

		
	}