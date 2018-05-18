package breakout;


import java.awt.Color;
import java.awt.Graphics2D;

public class Score {
	
	static int score = 0;
	boolean live = true;
	static int numberOfBalls = 1;
	static int numberOfBricks = 0;
	static int lives = Constants.startingLives;
	
	public Score() {
		score = 0;
		live = true;
		numberOfBalls = 1;
		numberOfBricks = 0;
		lives = Constants.startingLives;
	}
	
	public void draw(Graphics2D win, Ball[] balls, Paddle paddle) {
		for(int i = 0; i < balls.length; i++) {
			if(balls[i] != null && balls[i].isDead()) {
				balls[i] = null;
				numberOfBalls--;
				System.out.println("Balls: " + numberOfBalls);
			}
		}
		
		if(numberOfBalls == 0) {
			paddle.reset();
			for(int i = 0; i < 5; i++) {
				balls[i] = null;
			}
			balls[0] = new Ball(paddle);
			balls[0].reset(paddle);
			numberOfBalls = 1;
			Comp.sound.play(2);
			lives--;
			if(lives == 0) {
				WinLose.won = false;
				Comp.gameState = 3;
				
			}
		}
		
		if(numberOfBricks == 0 && Comp.gameState != 2) {
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
		} else if(numberOfBricks == 0) {
			Comp.gameState = 3;
			LevelMaker.starting = false;
		}
		
		//Draws the score
		win.setColor(Color.GRAY);
		Constants.setFont(win, 50);
		win.drawString(String.format("%010d", score), 500, 550);
		
		//Draws the number of lives
		win.setColor(Color.GRAY);
		Constants.setFont(win, 50);
		win.drawString("x" + lives, 30, 545);
		
	}
	
	public static void lowerBrickCount() {
		numberOfBricks -= 1;
		System.out.println(numberOfBricks);
	}
	
}
