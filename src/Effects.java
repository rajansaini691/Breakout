import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

public class Effects {
	private Particle[] particles;
	private boolean isAlive = true;
	
	public Effects(int x, int y, Color color) {
		particles = new Particle[100];
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(x, y, color);
		}
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isAlive = false;
			}
			
		}, 3000);
		
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void moveAndDraw(Graphics2D win) {
		
			for(int i = 0; i < particles.length; i++) {
				particles[i].moveAndDraw(win);
			}
	}
	
	
	
}
