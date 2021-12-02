package testing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Sound {
	
	JFrame window;
	Container con;
	JPanel buttonPanel;
	JButton soundButton;
	String clickSound;
	ButtonHandler bHandler = new ButtonHandler();
	SoundEffect se = new SoundEffect();
	

	public static void main(String[] args) {
		
		new Sound();

	}
	
	public Sound(){
		
		window = new JFrame();
		window.setSize(800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		con = window.getContentPane();
		
		buttonPanel = new JPanel();
		buttonPanel.setBounds(300, 300, 200, 100);
		buttonPanel.setBackground(Color.black);
		con.add(buttonPanel);
		
		soundButton = new JButton("Sound Effect");
		soundButton.setFocusPainted(false);
		soundButton.addActionListener(bHandler);
		soundButton.setActionCommand("soundB");
		buttonPanel.add(soundButton);
				
		window.setVisible(true);
			
        // Type your audio file name in the res folder
		clickSound = "Hot-Milk.wav";
		
		

	}
	
	public class SoundEffect {
		
		Clip clip;
		
		public void setFile(String soundFileName){
			
			try{
				File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);	
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			catch(Exception e){
				
			}
		}
		
		public void play(){
			
			clip.setFramePosition(0);
			clip.start();
		}

	}
	
	public class ButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			
			se.setFile(clickSound);
			se.play();							
		}
	}

}