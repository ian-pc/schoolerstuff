package msPaints;

import java.awt.Color;
import java.awt.Graphics;


public class Rectangle extends Shape {

		public Rectangle(int x1, int y1, int x2, int y2, Color c) {
			super(x1, y1, x2, y2, c);
			// TODO Auto-generated constructor stub
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			if (x1 > x2) {
				this.x1 = x2;
				this.x2 = x1;
			}
			if (y1 > y2) {
				this.y1 = y2;
				this.y2 = y1;
			}
			this.color = c;
		}

		@Override
		public Shape copy() {
			// TODO Auto-generated method stub
			return new Rectangle(this.x1, this.y1, this.x2, this.y2, this.color);
		}

		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			
			g.setColor(this.color);
			g.fillRect(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
			
		}

		@Override
		public boolean isOn(int x, int y) {
			// TODO Auto-generated method stub
			if (x > this.x1 && x < this.x2) {
				if (y > this.y1 && y < this.y2) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void resize(int x1, int y1, int x2, int y2) {
			// TODO Auto-generated method stub

			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			if (x1 > x2) {
				this.x1 = x2;
				this.x2 = x1;
			}
			if (y1 > y2) {
				this.y1 = y2;
				this.y2 = y1;
			}
			
		}
		
		public String write() {
			return ("Rectngle" + "a" + Integer.toString(this.x1)  + "a" + Integer.toString(this.y1) + "a" + Integer.toString(this.x2) + "a" +
					Integer.toString(this.y2) + "a" + Integer.toString(this.color.getRed()) + "a" 
					+ Integer.toString(this.color.getBlue()) + "a" + Integer.toString(this.color.getGreen()));
			
		}

		@Override
		protected void read(String[] tempText) {
			// TODO Auto-generated method stub
			
		}
		
	}
