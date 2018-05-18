

import java.awt.Color;
import java.awt.Graphics2D;

public class LevelInfo {
	public static void draw(Graphics2D win) {
		win.setColor(Color.BLACK);
		win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
		win.setColor(new Color(152, 152, 152));
		
		//Draws the score
		Constants.setFont(win, 50);
		win.drawString("Score: " + String.format("%010d", Score.score), 160, 80);
		
		win.setColor(Color.WHITE);
		//Draws the level-specific information
		Constants.setFont(win, 30);
		switch(Comp.level) {
			case 1: 
				win.drawString("Use the LEFT and RIGHT arrow keys to move, ", 80, 300);
				win.drawString("and SPACEBAR to start the level. ", 150, 350);
				win.drawString("Press P to pause the game at any time", 100, 400);
				break;
			case 2: 
				win.drawString("Some bricks have more health than others.", 80, 300);
				break;
			case 3:
				int y = 20;
				int distance = 40;
				win.drawString("Use a green powerup to make the paddle sticky;", 40, 290 - y);
				win.drawString("press SPACEBAR to release the ball.", 130, 290 + distance - y);
				win.drawString("I gave it to you already,", 210, 290 + distance * 2 - y);
				win.drawString("But catch them before they fall", 170, 290 + distance * 3 - y);
				break;
			case 4:
				win.drawString("The white powerups add another ball into play.", 55, 300);
				break;
			case 5:
				y = 20;
				win.drawString("The gray powerups make your ball metal,", 90, 300 - y);
				win.drawString("while the gold powerups let you blow up", 95, 350 - y);
				win.drawString("sections of the wall.", 250, 400 - y);
				break;
			case 6:
				win.drawString("Time to test out your skills! The powerups", 95, 300);
				win.drawString("are randomized and less likely to spawn.", 95, 340);
				break;
			case 7:
				win.drawString("CRAAAAZYYY!!!!!!!", 250, 300);
				break;
			case 8:
				win.drawString("The ball just got faster and the ", 170, 300);
				win.drawString("paddle got thinner. Good luck!", 160, 340);
				break;
			case 9:
				win.drawString("Have some free lives! This is the calm...", 95, 300);
		}
		
		//Draws the level number
		win.setColor(new Color(152, 152, 152));
		Constants.setFont(win, 100);
		win.drawString("LEVEL " + Comp.level, 210, 200);
		
		win.setColor(Color.WHITE);
		Constants.setFont(win, 40);
		win.drawString("Press ENTER to continue", 170, 500);
		
	}
}
