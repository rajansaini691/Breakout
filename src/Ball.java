

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Ball extends Rectangle {

	/**
	 * This class controls the Ball object
	 */
	private static final long serialVersionUID = 1L;

	private int dx, dy, speed;
	private boolean dead = false;
	private boolean started = false;
	private boolean isMetal = false;
	private boolean isBomb = false;
	public static int extraSpeed = 0;
	
	public Ball(Paddle paddle) {
		super(Constants.windowWidth / 2, Constants.windowHeight * 1 / 2, Constants.ballDimensions[0], Constants.ballDimensions[1]);
		reset(paddle);
	}


	public void moveAndDraw(Graphics2D win) {
		if(!dead) {
			if(x > Constants.windowWidth - width) {
				bounce("left");
				Comp.sound.play(1);
			}
			
			if(x < 0) {
				bounce("right");
				Comp.sound.play(1);
			}
			
			if(y < 0) {
				bounce("down");
				Comp.sound.play(1);
			}
			
			if(y > Constants.paddleHeight + dy + Comp.paddle.height) {
				dead = true;
			}
			
			win.setColor(Constants.ballColor);
			if(isMetal && isBomb) {
				win.setColor(new Color(212,175,55));
			} else if(isMetal) {
				win.setColor(Color.GRAY);
			} else if(isBomb) {
				win.setColor(new Color(255,215,0));
			}
			win.fillArc(x, y, width, height, 0, 360);
			if(!Comp.isPaused && !Comp.gameStarting) {
				this.translate(dx, dy);
			}
		}
	}

	public void bounce(String direction) {
		switch (direction) {
		case "left":
			dx = -Math.abs(dx);
			break;
		case "right":
			dx = Math.abs(dx);
			break;
		case "up":
			dy = -Math.abs(dy);
			break;
		case "down":
			dy = Math.abs(dy);
			break;
		}
	}
	
	public boolean checkIfMetal() {
		return isMetal;
	}
	
	public void stick(Paddle paddle) {
		this.dx = paddle.getDx();
		this.y = (int) paddle.getY() - height;
	}
	
	public void releaseFromPaddle(double horizontalDistance, int paddleWidth) {
		double theta = (2 * Constants.maxFiringAngle * (horizontalDistance + width))/(paddleWidth + width) - Constants.maxFiringAngle;
		dx = (int) ((double) speed * Math.sin(theta));
		dy = (int) -Math.abs((double) speed * Math.cos(theta));
		Comp.sound.play(1);
	}
	
	public void reset(Paddle paddle) {
		x = (int) (paddle.getX() + paddle.getWidth()/2);
		y = (int) paddle.getY() - this.height;
		speed = Constants.ballSpeed + extraSpeed;
		dx = 0;
		dy = speed;
	}
	
	public void makeMetal() {
		isMetal = true;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isMetal = false;
			}
		}, 2000);
	}
	
	public boolean checkIfBomb() {
		return isBomb;
	}
	
	public void makeBomb() {
		isBomb = true;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				isBomb = false;
			}
		}, 2000);
	}
	
	public String toString() {
		return "dead: " + dead;
	}
	
	public boolean isDead() {
		return dead;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getSpeed() {
		return speed;
	}

	public void accelerate(int speed) {
		this.speed += speed;
	}
	
	public boolean hasStarted () {
		return started;
	}
	
	public void start() {
		started = true;
	}
	
}
