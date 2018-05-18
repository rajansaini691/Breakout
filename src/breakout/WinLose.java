package breakout;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class WinLose {
	static boolean won = true;
	
	public static void draw(Graphics2D win) {
		win.setColor(Color.WHITE);
		win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
		win.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(won) {
			int x = 20;
			int y = -40;
			Constants.setFont(win, 100);
			win.setColor(Color.BLACK);
			win.drawString("You win!", 180, 100);
			Constants.setFont(win, 30);
			win.drawString("Your score was " + Score.score, 230, 150);
			
			//Draws the happy face
			win.setColor(Color.YELLOW);
			win.fillArc(260 + x, 260 + y, 210, 210, 0, 360);
			
			win.setColor(Color.BLACK);
			win.fillArc(310 + x, 310 + y, 30, 30, 0, 360);
			win.fillArc(390 + x, 310 + y, 30, 30, 0, 360);
			win.fillOval(315 + x, 380 + y, 100, 50);
			
			win.setColor(Color.YELLOW);
			win.fillOval(305 + x, 370 + y, 120, 40);
			
			//Draws "Press ENTER to start over"
			Constants.setFont(win, 38);
			win.setColor(Color.BLACK);
			win.drawString("Press ENTER to return to title", 150, 500);
		}
		if(won == false) {
			int x = 20;
			int y = -40;
			Constants.setFont(win, 100);
			win.setColor(Color.BLACK);
			win.drawString("You lose", 180, 100);
			Constants.setFont(win, 30);
			win.drawString("Your score was " + Score.score, 230, 150);
			
			//Draws the sad face
			win.setColor(Color.YELLOW);
			win.fillArc(260 + x, 260 + y, 210, 210, 0, 360);
			
			win.setColor(Color.BLACK);
			win.fillArc(305 + x, 305 + y, 30, 30, 0, 360);
			win.fillArc(395 + x, 305 + y, 30, 30, 0, 360);
			win.fillOval(320 + x, 400 + y, 100, 50);
			
			win.setColor(Color.YELLOW);
			win.fillOval(310 + x, 415 + y, 120, 40);
			
			//Draws "Press ENTER to start over"
			Constants.setFont(win, 38);
			win.setColor(Color.BLACK);
			win.drawString("Press ENTER to return to title", 150, 500);
		}
		
	}
}
