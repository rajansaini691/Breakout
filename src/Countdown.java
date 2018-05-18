

import java.awt.Color;
import java.awt.Graphics2D;

public class Countdown {
	private Color pauseColor;
	
	public Countdown() {
		pauseColor = new Color(0, 0, 0, Constants.pauseMenuTransparency);
	}
	
	public void draw(Graphics2D win) {
		if(Comp.gameStarting) {
			win.setPaint(pauseColor);
			win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
			
			win.setColor(Color.WHITE);
			Constants.setFont(win, 100);
			win.drawString("" + Comp.count, 360, 310);
		}
	}
	
}
