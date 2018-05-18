package breakout;


import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Comp extends GameDriverV3 implements KeyListener {
	
	/**
	 * This class houses the game loop
	 */
	private static final long serialVersionUID = 1L;
	
	Frame gameFrame;
	
	boolean added = false;
	static Ball[] balls = new Ball[5];
	static Brick[][] wall = new Brick[Constants.wallWidth][Constants.wallHeight];
	static Paddle paddle = new Paddle();
	PauseMenu menu = new PauseMenu();
	Countdown countdown = new Countdown();
	Effects[] effects = new Effects[5];
	LevelMaker maker = new LevelMaker();
	static PowerUp[] powerups = new PowerUp[3];
	static BallPowerUp[] ballPowerups = new BallPowerUp[3];
	Score score = new Score();	
	Splash splash = new Splash();
	WinLose winLose = new WinLose();
	boolean pressed = false;
	static boolean gameStarting = true;
	static boolean cheating = false;

	static int gameState; // 0 - splash screen; 1 - main game; 2 - level adder; 3 - Win/Lose screen; 4 - Information Screen;
	static boolean isPaused;
	static int level = 1;
	static int count = 3;
	static SoundDriver sound;
	static int moveOnCount = 0;
	
	static Brick[][][] customLevels = new Brick[Constants.wallWidth][Constants.wallHeight][5];
	
	private int comicSansCount = 0;
	private boolean isC = false, isN = false;
	
	private boolean enterPressed = false;		//Used to start the next level in the info screen
		
	/* Every time the level progresses, display a screen displaying the level information. 
	 * Then, when you click enter, the game starts. 
	 * 
	 */
	
	public Comp(Frame frame) {
		super();
		
		this.addKeyListener(this);
		this.addKeyListener(splash);
		
		gameState = 0;
		
		balls[0] = new Ball(paddle);
		
		isPaused = false;
		
		String[] soundFiles = new String[4];
		
		soundFiles[0] = "PaddleHit.wav";
		soundFiles[1] = "BrickHit.wav";
		soundFiles[2] = "YouLose.wav";
		soundFiles[3] = "Powerup.wav";
		sound = new SoundDriver(soundFiles);
		
		gameFrame = frame;
		
	}

	@Override
	public void draw(Graphics2D win) {		
		if(gameState == 0) {
			Splash.draw(win);
		}
		if(gameState == 1) {
			win.setColor(Color.BLACK);
			win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
		
			score.draw(win, balls, paddle);
			
			for(int i = 0; i < wall.length; i++) {
				for(int j = 0; j < wall[i].length; j++) {
					if(wall[i][j] != null) {
						wall[i][j].draw(win, balls);
					}
				}
			}
		
			paddle.moveAndDraw(win, balls);
			for(int i = 0; i < powerups.length; i++) {				
				if(powerups[i] != null) {
					powerups[i].draw(win, paddle, balls, this);
					if(powerups[i].checkIfAlive() == false) {
						powerups[i] = null;
					}
				}
			}
			
			for(int i = 0; i < ballPowerups.length; i++) {
				if(ballPowerups[i] != null) {
					ballPowerups[i].draw(win, balls, paddle, this);
					if(ballPowerups[i].checkIfAlive() == false) {
						ballPowerups[i] = null;
					}
				}
			}
			
			for(int i = 0; i < balls.length; i++) {
				if(balls[i] != null) {
					balls[i].moveAndDraw(win);
				}
			}
			
			for(int i = 0; i < effects.length; i++) {
				if(effects[i] != null) {
					effects[i].moveAndDraw(win);
					if(!effects[i].isAlive()) {
						effects[i] = null;
					}
				}
			}
			
			menu.draw(win);
			countdown.draw(win);
		}
		if(gameState == 2) {
			
			if(LevelMaker.starting) {
				if(!added) {
					this.addKeyListener(paddle);
					added = false;
				}
				
				win.setColor(Color.BLACK);
				win.fillRect(0, 0, Constants.windowWidth, Constants.windowHeight);
			
				score.draw(win, balls, paddle);
				
				for(int i = 0; i < LevelMaker.wall.length; i++) {
					for(int j = 0; j < LevelMaker.wall[i].length; j++) {
						if(LevelMaker.wall[i][j] != null) {
							LevelMaker.wall[i][j].draw(win, balls);
						}
					}
				}
			
				paddle.moveAndDraw(win, balls);
				for(int i = 0; i < powerups.length; i++) {				
					if(powerups[i] != null) {
						powerups[i].draw(win, paddle, balls, this);
						if(powerups[i].checkIfAlive() == false) {
							powerups[i] = null;
						}
					}
				}
				
				for(int i = 0; i < ballPowerups.length; i++) {
					if(ballPowerups[i] != null) {
						ballPowerups[i].draw(win, balls, paddle, this);
						if(ballPowerups[i].checkIfAlive() == false) {
							ballPowerups[i] = null;
						}
					}
				}
				
				for(int i = 0; i < balls.length; i++) {
					if(balls[i] != null) {
						balls[i].moveAndDraw(win);
					}
				}
				
				for(int i = 0; i < effects.length; i++) {
					if(effects[i] != null) {
						effects[i].moveAndDraw(win);
					}
				}
				
				menu.draw(win);
				countdown.draw(win);
			} else {
				LevelMaker.draw(win);
			}
			
		}
		if(gameState == 3) {
			WinLose.draw(win);
		}
		if(gameState == 4) {
			LevelInfo.draw(win);
			if(enterPressed) {
				gameState = 1;
			}
		}
		
	}
	
	public static void reset() {
		paddle.reset();
		balls = new Ball[5];
		wall = new Brick[Constants.wallWidth][Constants.wallHeight];
		balls[0] = new Ball(paddle);
		Score.numberOfBalls = 1;
		powerups = new PowerUp[3];
		ballPowerups = new BallPowerUp[3];
		if(gameState != 2) {
			Score.numberOfBricks = 0;
		}
		
		gameStarting = true;
		Timer timer = new Timer();
		
		count = 4;
		
		timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					count--;
					if(count < 1) {
						gameStarting = false;
						timer.cancel();
					}
				}
				
			}, 0, 1000);		
	}
	
	public static void spawnPowerup(int x, int y) {
		for(int i = 0; i < powerups.length; i++) {
			if(powerups[i] == null) {
				powerups[i] = new PowerUp(x, y);
				break;
			}
		}
	}
	
	public void createEffects(int x, int y, Color color) {
		for(int i = 0; i < effects.length; i++) {
			if(effects[i] == null) {
				effects[i] = new Effects(x, y, color);
				break;
			}
		}
	}
	
	public static void resetGame() {
		Score.lives = Constants.startingLives;
		Score.score = 0;
		level = 1;
		isPaused = false;
		paddle.width = Constants.paddleDimensions[0];
		Ball.extraSpeed = 0;
	}
	
	public static void spawnBallPowerup(int x, int y) {
		for(int i = 0; i < ballPowerups.length; i++) {
			if(ballPowerups[i] == null) {
				ballPowerups[i] = new BallPowerUp(x, y);
				break;
			}
		}
		System.out.println("Spawned");
	}
	
	public static void changeLevel() {
		switch(level) {
			case 1:
				reset();
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 3; j < wall[i].length - 1; j++) {
						wall[i][j] = new Brick(i, j, 1, false, false);
						Score.numberOfBricks += 1;
					}
				}
				break;
			case 2:
				reset();
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 3; j < wall[i].length - 2; j++) {
						wall[i][j] = new Brick(i, j, 5 - j, false, false);
						Score.numberOfBricks += 1;
					}
				}
				break;
			case 3:
				reset();
				//keep the total health constant, but randomize who gets what. 
				Random random = new Random();
				int totalHealth = 20;
				while(totalHealth > 0) {
					int i = random.nextInt(wall.length - 1);
					int j = random.nextInt(wall[i].length);
					int health = Math.min(random.nextInt(4) + 1, totalHealth);
					if(wall[i][j] == null) {
						wall[i][j] = new Brick(i, j, health, true, false);
						Score.numberOfBricks++;
						totalHealth -= health;
					}
				}
				paddle.giveStickyPowerup();
				break;
			case 4:
				reset();
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						if((i + j)%2 == 0) {
							wall[i][j] = new Brick(i, j, (int) (4 - j/2.0), true, false);
							Score.numberOfBricks++;
						}
					}
				}
				break;
			case 5:
				reset();
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						wall[i][j] = new Brick(i, j, (int) (4 - j/2.0), true, true);
						Score.numberOfBricks++;
					}
				}
				break;
			case 6:
				reset();
				//The layout array is the design of the level
				int[][] layout = {{1, 1, 1, 1, 1, 1, 1, 1, 1}, 
								  {0, 0, 0, 0, 2, 0, 0, 0, 0},
								  {0, 2, 0, 2, 3, 2, 0, 2, 0},
								  {2, 5, 2, 0, 2, 0, 2, 5, 2},
								  {0, 2, 0, 0, 0, 0, 0, 2, 0},
								  {0, 0, 0, 0, 0, 0, 0, 0, 0},
								  {3, 3, 3, 3, 3, 3, 3, 3, 3}};
				
				//Iterates through the layout array to generate the level
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						if(layout[j][i] != 0) {
							wall[i][j] = new Brick(i, j, layout[j][i], true, true);
							Score.numberOfBricks++;
						}
					}
				}
				break;
			case 7:
				reset();
				random = new Random();
				int health;
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						health = random.nextInt(5);
						if(health != 0) {
							wall[i][j] = new Brick(i, j, random.nextInt(4) + 1, true, true);
							Score.numberOfBricks++;
						}
					}
				}
				break;
			case 8:
				Ball.extraSpeed = 2;
				paddle.width -= 40;
				reset();
				
				int[][] layout8 ={{5, 0, 0, 0, 0, 0, 0, 0, 5}, 
								  {4, 5, 0, 0, 0, 0, 0, 5, 4},
								  {4, 4, 5, 0, 0, 0, 5, 4, 4},
								  {3, 4, 4, 5, 0, 5, 4, 4, 3},
								  {2, 3, 4, 4, 5, 4, 4, 3, 2},
								  {2, 2, 3, 4, 4, 4, 3, 2, 2},
								  {1, 1, 1, 1, 1, 1, 1, 1, 1}};
		
				//Iterates through the layout array to generate the level
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						if(layout8[j][i] != 0) {
							wall[i][j] = new Brick(i, j, layout8[j][i], true, true);
							Score.numberOfBricks++;
						}
					}
				}
				break;
			case 9:
				reset();
				int[][] layout9 =  {{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{1, 1, 1, 1, 1, 1, 1, 1, 1},
									{1, 1, 1, 1, 1, 1, 1, 1, 1}};
				
				//Iterates through the layout array to generate the level
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						if(layout9[j][i] != 0) {
							wall[i][j] = new Brick(i, j, layout9[j][i], true, true);
							Score.numberOfBricks++;
						}
					}
				}
				break;
			case 10:
				reset();
				int[][] layout10 = {{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 5, 0, 5, 0, 5, 0, 5, 0},
									{5, 1, 5, 0, 5, 0, 5, 1, 5},
									{0, 0, 0, 0, 0, 0, 0, 0, 0},
									{0, 0, 0, 0, 0, 0, 0, 0, 0}};
				
				//Iterates through the layout array to generate the level
				for(int i = 0; i < wall.length - 1; i++) {
					for(int j = 0; j < wall[i].length; j++) {
						if(layout10[j][i] != 0) {
							wall[i][j] = new Brick(i, j, layout10[j][i], true, true);
							Score.numberOfBricks++;
						}
					}
				}
			default:
				WinLose.won = true;
				gameState = 3;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_N && Comp.isPaused && !isN) {
			isN = true;
			moveOnCount++;
			
			if(moveOnCount > 5) {
				PauseMenu.moveOn(this, paddle);
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER && gameState == 0 && !pressed) {
			pressed = true;
			if(Splash.selected == 0) {				//Starts the game
				gameState = 4;
				this.removeKeyListener(splash);
				this.addKeyListener(paddle);
			} else if(Splash.selected == 1) {
				this.removeKeyListener(splash);		//Opens the rules screen
				this.addKeyListener(maker);
				gameState = 2;
			} else {
				System.out.println("Splash.selected = " + Splash.selected);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S && gameState == 2 && !pressed) {
			gameState = 0;
			this.addKeyListener(splash);
			this.removeKeyListener(maker);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P && (gameState == 1 || gameState == 2) && !pressed) {
			pressed = true;
			if(isPaused) {
				isPaused = false;
				this.addKeyListener(paddle);
			} else {
				isPaused = true;
				this.removeKeyListener(paddle);
				paddle.pause();
			}
			
			System.out.println("Paused: " + isPaused);
		}
		if(e.getKeyCode() == KeyEvent.VK_C && gameState == 0 && !isC) {
			comicSansCount++;
			isC = true;
			if(comicSansCount == 5) {
				Constants.defaultFont = "Comic Sans MS";
				cheating = true;
			}
		}
		
		if(gameState == 3 && e.getKeyCode() == KeyEvent.VK_ENTER) {
			gameState = 0;
			resetGame();
			this.addKeyListener(splash);
			this.addKeyListener(this);
			pressed = true;
		}
		
		if(gameState == 4 && e.getKeyCode() == KeyEvent.VK_ENTER && !pressed) {
			changeLevel();
			gameState = 1;
			pressed = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_N) {
			isN = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			pressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			pressed = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_C) {
			isC = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
