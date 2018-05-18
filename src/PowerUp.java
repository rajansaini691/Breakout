

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class PowerUp extends Rectangle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean isAlive = true;
	private String type;
	private int numberOfPowerUpTypes = 2;
	private Color color;
	
	public PowerUp(int x, int y) {
		super(x, y, Constants.brickWidth, Constants.brickHeight);
		Random random = new Random();
	
		if(Comp.level == 9) {
			type = "life";
			color = Color.pink;
		} else if(Comp.level > 3 || Comp.gameState == 2) {
			switch(random.nextInt(numberOfPowerUpTypes)) {
				case 0:
					type = "ballAdder";
					color = Color.white;
					break;
				case 1:
					type = "sticky";
					color = Color.green;
					break;
			}
		} else if(Comp.level == 3) {
			type = "sticky";
		}
		
	}
	
	public void draw(Graphics2D win, Paddle paddle, Ball[] balls, Comp comp) {
		
		win.setColor(color);
			
		win.fill(this);
		if(!Comp.isPaused) {
			this.translate(0, 5);
		}
		
		if(y > Constants.windowHeight) {
			isAlive = false;
		}
		
		if(this.intersects(paddle)) {
			isAlive = false;
			
			Comp.sound.play(3);
			
			comp.createEffects(x, y, Color.yellow);
			
			switch(type) {
				case "ballAdder":
					paddle.addBall(balls);
					break;
				case "sticky":
					paddle.giveStickyPowerup();
					break;
				case "life":
					Score.lives++;
					break;
				default:
					System.out.println("Powerup type doesn't exist. Type = " + type);
			}
			
		}
		
	}
	
	public boolean checkIfAlive() {
		return isAlive;
	}
	
	public void destroy() {
		isAlive = false;
	}
	
}
