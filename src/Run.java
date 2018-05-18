

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Run {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		//Adds icon
				try {
					frame.setIconImage(ImageIO.read(new File("res/Breakout Icon.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		frame.setTitle("Breakout!");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		Constants.setConstants(785, 561);
		
		frame.add(new Comp(frame));
		
		frame.setVisible(true);
		
		
	}

}
