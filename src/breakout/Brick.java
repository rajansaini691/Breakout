package breakout;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Brick extends Rectangle {
	
	/**
	 * Each brick object in the game is an instance of this class
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int i, j, lives, startingLives;
	private boolean destroyed = false;
	private boolean hasPowerup;
	private boolean hasBallPowerup;
	private RenderingHints qualityHints;
	
	public Brick(int i, int j, int lives, boolean powerupsAllowed, boolean ballPowerupsAllowed) {
		super(i * Constants.brickWidth + (i+1) * Constants.padding, j * Constants.brickHeight + (j+1) * Constants.padding, Constants.brickWidth, Constants.brickHeight);
		qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		
		this.i = i;
		this.j = j;
		this.startingLives = lives;
	
		Random random = new Random();
		if(powerupsAllowed) {
			hasPowerup = random.nextDouble() > 0.8;
		}
		if(ballPowerupsAllowed && !hasPowerup) {
			hasBallPowerup = random.nextDouble() > 0.8;
		}
		if((Comp.level == 3 || Comp.level == 4) && Comp.gameState != 2) {
			hasPowerup = true;
		} else if(Comp.level == 5) {
			hasPowerup = false;
			hasBallPowerup = true;
		} else if(Comp.level == 9) {
			hasPowerup = random.nextBoolean();
		}
		
		this.lives = lives;
		
		if(Comp.level == 10 && Comp.gameState != 2) {
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						addLife();
						if(destroyed) {
							timer.cancel();
						}
					}
					
				}, 0, 10000);
		}
		
	}
	
	public String toString() {
		return "i: " + i + "j: " + j;
	}
	
	public void draw(Graphics2D win, Ball[] balls) {
		if(!destroyed) {						
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null && this.intersects(balls[i])) {
					ballCollision(balls[i]);
				}
			}
			
			
			win.setRenderingHints(qualityHints);
			win.setColor(Constants.brickColor[(lives > 0)? lives : 0]);
			if(hasPowerup && Comp.cheating) {
				win.setColor(Color.green);
			}
			if(hasBallPowerup && Comp.cheating) {
				win.setColor(Color.blue);
			}
			win.fill(new RoundRectangle2D.Float(x, y, width, height, 10, 10));
		}
	}
	
	private void ballCollision(Ball ball) {
		int previousXPos = (int)ball.getX() - ball.getDx();
		int previousYPos = (int)ball.getY() - ball.getDy();
		int height = (int)ball.getHeight();
		int width = (int)ball.getWidth();
		
		if(!ball.checkIfMetal()) {
			if(previousYPos + height <= this.getY() && ball.getY() + height >= this.getY()) {
				// intersects from top
				ball.bounce("up");
			} else if (previousXPos + width <= this.getX() && ball.getX() + width >= this.getX()) {
				// intersects from left
				ball.bounce("left");
			} else if (previousYPos >= this.getY() + this.height && ball.getY() <= this.getY() + this.height) {
				// intersects from bottom
				ball.bounce("down");
			} else {
				//Defaults to right
				ball.bounce("right");
			}
		}
		Score.score += Constants.scoreIncrement;
		Comp.sound.play(0);
		
		if(ball.checkIfBomb()) {
			if(i + 1 < Comp.wall.length && Comp.wall[i + 1][j] != null) {
				Comp.wall[i + 1][j].loseLife();
			}
			
			if(i - 1 >= 0 && Comp.wall[i - 1][j] != null) {
				Comp.wall[i - 1][j].loseLife();
			}
			
			if(j - 1 >= 0 && Comp.wall[i][j - 1] != null) {
				Comp.wall[i][j - 1].loseLife();
			}
		}
		
		loseLife();
	}
	
	public void addLife() {
		if(lives < startingLives && Comp.gameState != 2) {
			lives++;
		} else if(Comp.gameState == 2 && lives < 5) {
			lives++;
		}
	}
	
	public int getHealth() {
		return lives;
	}
	
	public void loseLife() {
		lives--;
		if(lives == 0) {
			destroy();
		}
		
	}
	
	public void destroy() {
		destroyed = true;
		if(hasPowerup) {
			Comp.spawnPowerup(this.x, this.y);
		} else if(hasBallPowerup) {
			Comp.spawnBallPowerup(this.x, this.y);
		}
		Score.lowerBrickCount();
	}
	
	public void revive() {
		destroyed = false;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
}
