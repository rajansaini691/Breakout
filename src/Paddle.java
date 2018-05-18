

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends Rectangle implements KeyListener {
	
	/**
	 * This class maintains the paddle 
	 */
	private static final long serialVersionUID = 1L;
		
	private int speed, dy, dx;
	private boolean isLeft, isRight, isSpace;
	private boolean sticky, hasStickyPowerup; 
	
	public Paddle() {
		super(Constants.windowWidth/2 - Constants.paddleDimensions[0]/2, Constants.paddleHeight, Constants.paddleDimensions[0], Constants.paddleDimensions[1]);
		this.height = Constants.paddleDimensions[1];
		this.width = Constants.paddleDimensions[0];
		this.speed = 10;
		sticky = false;
		hasStickyPowerup = false;
	}
	
	public void moveAndDraw(Graphics2D win, Ball[] balls) {
		
		win.setColor(Constants.paddleColor);
		win.fill(this);
		if(sticky) {
			win.setColor(Color.GREEN);
			win.fill(new Rectangle(this.x, this.y, this.width, 3));
		}
		
		if(isLeft && this.x > dx) {
			dx = -speed;
		} else if(isRight  && this.x < Constants.windowWidth - this.width) {
			dx = speed;
		} else {
			dx = 0;
		}
		
		for(int i = 0; i < balls.length; i++) {
			if(balls[i] != null && this.intersects(balls[i])) {
				if(sticky) {
					balls[i].stick(this);
					if(isSpace) {
						if(!hasStickyPowerup) {
							sticky = false;
						}
						balls[i].releaseFromPaddle(balls[i].getX() - x, width);
					}
				} else if(!balls[i].hasStarted()) {
					balls[i].stick(this);
					if(isSpace) {
						balls[i].start();
					}
					balls[i].x = this.x + this.width/2;
				} else {
					balls[i].releaseFromPaddle(balls[i].getX() - x, width);
				}
			}
		}
		
		if(!Comp.gameStarting && !Comp.isPaused) {
			this.translate(dx, dy);
		}
		
	}
	
	public boolean isSticky() {
		return sticky;
	}
	
	public void giveStickyPowerup() {
		hasStickyPowerup = true;
		sticky = true;
	}
	
	public void reset() {
		this.speed = 10;
		isRight = (isLeft = false);
		dx = 0;
		sticky = false;
		hasStickyPowerup = false;
		x = Constants.windowWidth/2 - Constants.paddleDimensions[0]/2;
		y = Constants.windowHeight*7/8;
	}
	
	public void addBall(Ball[] balls) {
		int firstAvailable = balls.length - 1;
		for(int i = 0; i < balls.length; i++) {
			if(balls[i] == null) {
				firstAvailable = i; 
				break;
			}
		}
		
		balls[firstAvailable] = new Ball(this);
		Score.numberOfBalls ++;
	}
	
	public int getDx() {
		return dx;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(!Comp.gameStarting && !Comp.isPaused) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				isLeft = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				isRight = true;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				isSpace = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(!Comp.gameStarting && !Comp.isPaused) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				isLeft = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				isRight = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				isSpace = false;
			}
		}
	}
	
	public void pause() {
		isRight = false;
		isLeft = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
