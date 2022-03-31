package problems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GPS {

	private class Location {
		int x, y;
		String name;

		public Location(int x, int y, String name) {
			this.x = x;
			this.y = y;
			this.name = name;
		}
	}

	private ArrayList<Location> points = new ArrayList<>();

	private int Width = 820, Height = 820;

	private int tempX, tempY;

	private GPS() throws IOException {

		JFrame frame = new JFrame();

		frame.setSize(Width, Height);
		// closes the window when closed and stops the program.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JFrame PtopBox = new JFrame();

		PtopBox.setSize(new Dimension(250, 75));
		PtopBox.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		PtopBox.setLocationRelativeTo(null);

		JTextField TFlocation = new JTextField();
		TFlocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.out.println(TFlocation.getText());
				points.add(new Location(tempX, tempY, TFlocation.getText()));
				PtopBox.setVisible(false);
				TFlocation.setText("");
				frame.getContentPane().repaint();
			}

		});
		Font bigFont = TFlocation.getFont().deriveFont(Font.PLAIN, 40);
		TFlocation.setFont(bigFont);

		BufferedImage mapImage = ImageIO.read(new File("Files/FortniteMap.jpg"));
		JLabel map = new JLabel(new ImageIcon(mapImage));

		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.RED);
				AffineTransform affinetransform = new AffineTransform();     
				FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
				for (int i = 0; i < points.size(); i++) {
					g.fillPolygon(
							new int[] { points.get(i).x, points.get(i).x + 10, points.get(i).x + 10,
									points.get(i).x - 10, points.get(i).x - 10 },
							new int[] { points.get(i).y, points.get(i).y - 5, points.get(i).y - 15, 
									points.get(i).y - 15, points.get(i).y - 5}, 5);
					g.drawString(points.get(i).name, points.get(i).x - (int)((new JLabel().getFont()).getStringBounds(points.get(i).name, frc).getWidth())/2, points.get(i).y - 20);
				}
			}
		};

		canvas.add(map);

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				PtopBox.setVisible(true);
				tempX = e.getX();
				tempY = e.getY();
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

		PtopBox.add(TFlocation);

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new GPS();
	}
}
