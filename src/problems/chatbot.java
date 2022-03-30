package problems;

import java.awt.*;  
import java.awt.event.*;  
import java.awt.Dimension;
import java.awt.Graphics; 

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class chatbot {
	public final int Height = 600, Width = 600; 
	public JFrame frame;
	public JButton button;
	public JTextField textField;
	public String text = "";
	
	public boolean enter = false;
	public chatbot() {
		
		frame = new JFrame();
		button = new JButton("Enter");
		textField = new JTextField(16);
		
		frame.setSize(Width, Height);
		//closes the window when closed and stops the program. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//button.setBounds(0, 0, 100, 500);	
		
		
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);

				g.drawString(text, 200, 400);
					
			}
		};
		
	    button.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				enter = true;
				text = ("That's cool, by the way would you like " + (int) (Math.random() * 100) + " bananas?");
				textField.setText("");
			}
	    });
		

		
		
		canvas.add(button);
		canvas.add(textField);
		
		frame.add(canvas);

		canvas.setSize(Width, Height);
		frame.setLayout(null);
		frame.setVisible(true);
		while(true) {
			frame.getContentPane().repaint();
		}

	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new chatbot();
		
	}
	
 

}
