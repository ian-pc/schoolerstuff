package msPaints;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

		private int radius;
		public Circle(int x1, int y1, int x2, int y2, Color c) {
			super(x1, y1, x2, y2, c);
			// TODO Auto-generated constructor stub
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = c;
		}

		@Override
		public Shape copy() {
			// TODO Auto-generated method stub
			return new Circle(this.x1, this.y1, this.x2, this.y2, this.color);
		}

		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			g.setColor(this.color);
			
			//using Pythagorean theorem to find the radius of the circle. 
			radius = (int) Math.sqrt((this.x2 - this.x1)*(this.x2 - this.x1) + (this.y2 - this.y1)*(this.y2 - this.y1));
			g.fillOval(this.x1 - radius, this.y1 - radius, 2 * radius, 2 * radius);
		}
 
		@Override
		public boolean isOn(int x, int y) {
			// TODO Auto-generated method stub

			//if the distance between the center of the circle and the mouse is lesser than the radius, the mouse is on the circle
			double distance = 0; //distance from center using Pythagorean theorem
			distance = Math.sqrt((x - this.x1)*(x - this.x1) + (y - this.y1)*(y - this.y1));
			if (distance <= radius) {
				return true;
			}
			return false;
		}

		@Override
		public void resize(int x1, int y1, int x2, int y2) {
			// TODO Auto-generated method stub
			this.x2 = x2;
			this.y2 = y2;
		}
	
		public String write() {
			return ("Circle" + "a" + Integer.toString(this.x1) + "a" + Integer.toString(this.y1) + "a" + Integer.toString(this.x2) + "a" +
					Integer.toString(this.y2) + "a" + Integer.toString(this.color.getRed()) 
					+ "a" + Integer.toString(this.color.getBlue()) + "a" + Integer.toString(this.color.getGreen()));
		}

		@Override
		protected void read(String[] tempText) {
			// TODO Auto-generated method stub
			
		}
		
	}