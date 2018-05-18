package breakout;


import java.awt.Color;
import java.awt.Graphics2D;

public class PauseMenu {
	
	Color pauseColor;
	
	public PauseMenu() {
		pauseColor = new Color(0, 0, 0, Constants.pauseMenuTransparency);
	}
	
	public void draw(Graphics2D win) {
		if(Comp.isPaused) {
			win.setPaint(pauseColor);
			win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
			
			win.setColor(Color.WHITE);
			Constants.setFont(win, 100);
			win.drawString("PAUSED", 185, 300);
		}
	}
	
	public static void moveOn(Comp comp, Paddle paddle) {
		comp.addKeyListener(paddle);
		Comp.moveOnCount = 0;
		Comp.level++;
		System.out.println(Comp.level);
		if(Comp.level < 11) {
			Comp.gameState = 4;
		} else {
			Comp.gameState = 3;
		}
		for(int i = 0; i < Math.max(Comp.powerups.length, Comp.ballPowerups.length); i++) {
			if(Comp.powerups[i] != null) {
				Comp.powerups[i].destroy();
			}
			if(Comp.ballPowerups[i] != null) {
				Comp.ballPowerups[i].destroy();
			}
		}
		Comp.isPaused = false;
	}
	
}
