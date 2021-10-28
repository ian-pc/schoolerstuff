package problems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class G {
public final int Height = 600, Width = 600; 
	public G() {
		
		JFrame frame = new JFrame();
		frame.setSize(Width, Height);
		//closes the window when closed and stops the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				
				for (int i = 0; i < Math.ceil(Math.random() * 1000000); i++) {
					int shape = (int) Math.round(Math.random() * 1.7);
					System.out.println(shape);
					if (shape == 0) {
						g.setColor(new Color((int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 200)));
						g.fillRect((int) Math.ceil(Math.random() * G.this.Width), (int) Math.ceil(Math.random() * G.this.Height), (int) Math.ceil(Math.random() * G.this.Width/4), (int) Math.ceil(Math.random() * G.this.Width/4));
					}
					else if (shape == 1) {
						g.setColor(new Color((int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 200)));
						g.fillOval((int) Math.ceil(Math.random() * G.this.Width), (int) Math.ceil(Math.random() * G.this.Height), (int) Math.ceil(Math.random() * G.this.Width/4), (int) Math.ceil(Math.random() * G.this.Width/4));
					} 
					else if (shape == 2) {
						g.setColor(new Color((int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 255), (int) Math.ceil(Math.random() * 200)));
						g.drawLine((int) Math.ceil(Math.random() * G.this.Width), (int) Math.ceil(Math.random() * G.this.Height), (int) Math.ceil(Math.random() * G.this.Width/4), (int) Math.ceil(Math.random() * G.this.Width/4));
					}
				}
			}
		};
		
		canvas.setPreferredSize(new Dimension(Width, Height/2));
		
		frame.add(canvas);
		
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		new G();
	}

}
