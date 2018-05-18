package breakout;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Splash implements KeyListener {
	
	static int triangleX = 200, triangleY = 290, triangleHeight = 20, triangleWidth = 20;
	static int selected = 0, numberOfButtons = 2;
	static boolean pressing = false;
	
	public static void draw(Graphics2D win) {
		//Draws background
		win.setColor(Constants.introBackgroundColor);
		win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
		
		//Draws BREAKOUT
		Constants.setFont(win, 140);
		win.setColor(Constants.introTextColor);
		win.drawString("B", 115, 160);
		win.drawString("r", 190, 160);
		
		win.setColor(Color.BLACK);
		win.drawString("a", 280, 160);
		
		win.setColor(Color.BLACK);
		win.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		win.fillArc(237, 100, 45, 45, 0, (int) 360);
		win.fillRect(260, 127, 35, 7);
		
		win.drawString("u", 480, 160);
		
		win.setColor(Color.WHITE);
		win.drawString("e", 215, 160);
		
		win.setColor(Color.black);
		win.drawString("k", 360, 160);
		win.setColor(Color.WHITE);
		win.drawString("o", 415, 160);
		win.setColor(Color.black);
		win.fillOval(435, 100, 47, 50);
		
		win.drawString("t", 560, 160);
		
		//Draws "By: Rajan Saini"
		Constants.setFont(win, 40);
		win.drawString("By: Rajan Saini", 230, 230);
		
		//Draws PLAY
		Constants.setFont(win, 60);
		win.drawString("PLAY", 310, 350);
		
		//Draws "Rules"
		Constants.setFont(win, 40);
		win.drawString("Level Maker", 270, 430);
		
		switch(selected) {
			case 0: 
				triangleX = 270;
				triangleY = 315;
				break;
			case 1: 
				triangleX = 225;
				triangleY = 405;
				break;
			default:
				System.out.println("Selected = " + selected);
				break;
		}
		
		//Draws the triangle selector
		win.fillPolygon(new int[] {0 + triangleX, 0 + triangleX, triangleWidth + triangleX},
				new int[] {0 + triangleY, triangleHeight + triangleY, triangleHeight/2 + triangleY}, 
				3);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP && !pressing) {
			selected++;
			selected %= numberOfButtons;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN && !pressing) {
			selected -= 1;
			selected %= numberOfButtons;
			if(selected < 0) {
				selected = numberOfButtons - 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
