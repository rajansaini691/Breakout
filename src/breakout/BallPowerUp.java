package breakout;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class BallPowerUp extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private Color color;
	private boolean isAlive = true;

	//When the ball hits this powerup, the ball either turns into a bomb or becomes metal
	public BallPowerUp(int x, int y) {
		super(x, y, Constants.brickWidth, Constants.brickHeight);
		Random random = new Random();
		if(Comp.level >= 5) {
			type = (random.nextBoolean())? "bomb" : "metal";
			switch(type) {
				case "bomb": 
					color = new Color(212,175,55);
					break;
				case "metal":
					color = Color.GRAY;
			}
		} else {
			type = "bomb";
		}
		
		
	}
	
	public void draw(Graphics2D win, Ball[] balls, Paddle paddle, Comp comp) {
		if(isAlive) {
			
			win.setColor(color);
			win.fill(this);
			
			if(!Comp.isPaused) {
				this.translate(0, 5);
			}
			
			if(y > Constants.windowHeight) {
				isAlive = false;
			}

			if(this.intersects(paddle)) {
				Comp.sound.play(3);
				
				comp.createEffects(x, y, Color.RED);
				
				for(int i = 0; i < balls.length; i++) {
					if(balls[i] != null) {
						switch(type) {
							case "bomb":
								balls[i].makeBomb();
								break;
							case "metal":
								balls[i].makeMetal();
								break;
							default: System.out.println("no such powerup");
						}
					}
				}
				
				isAlive = false;
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
