

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Constants {
	
	private Constants() {}
	
	public static int ballSpeed;
	public static int brickWidth;
	public static int brickHeight;
	public static String defaultFont;
	public static double maxFiringAngle;
	public static int padding;
	public static int paddleHeight;
	public static float pauseMenuTransparency;
	public static int scoreThreshold;
	public static int scoreIncrement;
	public static int startingLives;
	public static int windowWidth;
	public static int windowHeight;
	public static int wallWidth;
	public static int wallHeight;
	
	public static Color ballColor;
	public static Color[] brickColor;
	public static Color introBackgroundColor;
	public static Color introTextColor;
	public static Color paddleColor;
	
	public static int[] ballDimensions = new int[2];
	public static int[] paddleDimensions = new int[2];
	
	public static void setConstants(int wWidth, int wHeight) {
		padding = 10;
		windowWidth = wWidth;
		windowHeight = wHeight;
		wallWidth = 10;							// Sets number of bricks horizontally
		wallHeight = 7;						// Sets number of bricks vertically
		
		ballColor = Color.WHITE;
		ballDimensions[0] = 15;
		ballDimensions[1] = 15;
		ballSpeed = 8;
		maxFiringAngle = Math.PI/3;					//Sets the maximum angle the paddle can shoot at in degrees
		
		//Sets the color scheme for the brick layout, with the highest level on the right. 
		brickColor = new Color[]{new Color(0x2962FF), new Color(0xD3DFB8), new Color(0xF2E86D), new Color(0xC6A15B), new Color(0xA38560), new Color(0x574D68)};
		brickHeight = (windowHeight) / (wallHeight * 3);
		brickWidth = (windowWidth - 2*padding) / wallWidth;
		
		defaultFont = "Century Gothic";
		
		introBackgroundColor = Color.WHITE;
		introTextColor = Color.BLACK;
		
		paddleColor = new Color(0x29B6F6);		
		paddleDimensions[0] = 200; 				// Sets paddle width
		paddleDimensions[1] = 10; 				// Sets paddle height
		paddleHeight = windowHeight * 7/8;
		
		pauseMenuTransparency = 0.5f; 			//A percent value that gives the weight of transparency.
		
		scoreThreshold = 30;
		scoreIncrement = 50;
		
		startingLives = 3;
	}
	
	//Makes it easier to change the fonts
	public static void setFont(Graphics2D win, int fontSize) {
			win.setFont(new Font(defaultFont, Font.BOLD, fontSize));
			win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
}
