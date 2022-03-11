package schoolstuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Robot {
public final int Height = 600, Width = 600; 
public int robotX = Height/2 - 50, robotY = Width/2;
	public Robot() {
		
		JFrame frame = new JFrame();
		frame.setSize(Width, Height);
		//closes the window when closed and stops the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(255, 0, 0));
				g.fillRect(robotX, robotY, 100, 100);	
			}
		};
		
		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				System.out.println("HI");
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					robotY += 10;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					robotY -= 10;
				} 
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					robotX += 10;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					robotX -= 10;
				}
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					System.out.println("YE");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
			
		});
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				robotX = e.getPoint().x;
				robotY = e.getPoint().y;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		canvas.setPreferredSize(new Dimension(Width, Height));
		frame.add(canvas);
		frame.setVisible(true);
		while(true) {

			canvas.requestFocus();
			frame.getContentPane().repaint();
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		new Robot();
	}

}
