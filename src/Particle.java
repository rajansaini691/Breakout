import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Particle extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isAlive;
	private Color color;

	private int dy;

	private int dx;
	
	public Particle(int x, int y, Color color) {
		super(x, y, 3, 3);
		Random random = new Random();
		isAlive = true;
		this.color = color;
		dy = -random.nextInt(10);
		dx = random.nextInt(10) - 6;
	}
	
	public void moveAndDraw(Graphics2D win) {
		if(isAlive) {
			dy += 1;
			this.translate(dx, dy);
			win.setColor(color);
			win.fill(this);
			if(y > Constants.windowHeight || x > Constants.windowWidth) {
				isAlive = false;
			}
		}
	}
}
