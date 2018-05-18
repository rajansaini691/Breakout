package breakout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class EmptyBrick extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int i, j;
	private boolean selected;
	private RenderingHints qualityHints;
	
	public EmptyBrick(int i, int j) {
		super(i * Constants.brickWidth + (i+1) * Constants.padding, j * Constants.brickHeight + (j+1) * Constants.padding, Constants.brickWidth, Constants.brickHeight);
		qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		
		this.i = i;
		this.j = j;
		selected = false;
		
		
	}
	
	public void draw(Graphics2D win) {
		win.setRenderingHints(qualityHints);
		win.setColor(Color.YELLOW);
		win.setStroke(new BasicStroke(2));
		win.draw(new RoundRectangle2D.Float(x, y, width, height, 10, 10));
		if(selected) {
			win.setColor(Color.WHITE);
			win.setStroke(new BasicStroke(10));
			win.draw(new RoundRectangle2D.Float(x, y, width, height, 10, 10));
		}
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
}
