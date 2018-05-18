import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LevelMaker implements KeyListener {
	static Brick[][] wall = new Brick[Constants.wallWidth][Constants.wallHeight];
	static EmptyBrick[][] emptyWall = new EmptyBrick[Constants.wallWidth][Constants.wallHeight];
	private int currentI, currentJ;
	private boolean rightPressed = false, leftPressed = false, upPressed = false, downPressed = false, enterPressed = false;
	private boolean spacePressed;
	static boolean starting = false;
	private static Color textColor;
	
	public LevelMaker() {
		for(int i = 0; i < emptyWall.length; i++) {
			for(int j = 0; j < emptyWall[i].length; j++) {
				emptyWall[i][j] = new EmptyBrick(i, j);
			}
		}
		
		currentI = 0;
		currentJ = 0;
		
		emptyWall[currentI][currentJ].select();
		textColor = new Color(220, 220, 220);
	}
	
	public static void draw(Graphics2D win) {
		win.setColor(Color.BLACK);
		win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
		for(int i = 0; i < emptyWall.length; i++) {
			for(int j = 0; j < emptyWall[i].length; j++) {
				if(wall[i][j] != null) {
					wall[i][j].draw(win, Comp.balls);
				}
				emptyWall[i][j].draw(win);
			}
		}
		
		win.setColor(textColor);
		Constants.setFont(win, 30);
		win.drawString("Press '=' to add a brick or raise its health ", 100, 340);
		win.drawString("and '-' to delete a brick or lower its health.", 90, 390);
		win.drawString("Use the arrowkeys to navigate.", 170, 440);
		win.drawString("Press SPACE to start the level.", 180, 490);
		
	}

	public static void start() {
		Comp.reset();
		starting = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed && !starting) {
				spacePressed = true;
				start();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_MINUS && !enterPressed && !starting) {
				enterPressed = true;
				if(wall[currentI][currentJ] != null) {
					if(wall[currentI][currentJ].getHealth() == 1) {
						wall[currentI][currentJ] = null;
						Score.numberOfBricks--;
					} else {
						wall[currentI][currentJ].loseLife();
					}
				}
			}
		
			
			if(e.getKeyCode() == KeyEvent.VK_EQUALS && !enterPressed && !starting) {
				enterPressed = true;
				System.out.println("entered");
				if(wall[currentI][currentJ] == null) {
					wall[currentI][currentJ] = new Brick(currentI, currentJ, 1, true, true);
					Score.numberOfBricks++;
				} else {
					wall[currentI][currentJ].addLife();
				}
			}
		if(!starting) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					if(!rightPressed) {
						rightPressed = true;
						if(currentI < emptyWall.length - 2) {
							emptyWall[currentI][currentJ].deselect();
							emptyWall[currentI + 1][currentJ].select();
							currentI += 1;
						}
					}
					break;
				case KeyEvent.VK_LEFT:
					if(!leftPressed) {
						leftPressed = true;
						if(currentI > 0) {
							emptyWall[currentI][currentJ].deselect();
							emptyWall[currentI - 1][currentJ].select();
							currentI -= 1;
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if(!downPressed) {
						downPressed = true;		
						if(currentJ < emptyWall[currentI].length - 1) {
							emptyWall[currentI][currentJ].deselect();
							emptyWall[currentI][currentJ + 1].select();
							currentJ += 1;
						}
					}
					break;
				case KeyEvent.VK_UP:
					if(!upPressed) {
						upPressed = true;
						if(currentJ > 0) {
							emptyWall[currentI][currentJ].deselect();
							emptyWall[currentI][currentJ - 1].select();
							currentJ -= 1;
						}
					}
					break;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_MINUS) {
			enterPressed = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				downPressed = false;
				break;
			case KeyEvent.VK_UP:
				upPressed = false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
