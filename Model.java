import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	private static int count = 0;
    private static long time = System.nanoTime();
    private Ship ship = new Ship();
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

    Model() throws IOException {
		asteroids.add(new Asteroid(0,0));
    }

    public void update(Graphics g) {
    	synchronized(asteroids) {
            synchronized(ship) {
                ship.updateImage(g);
                for(Sprite asteroid : asteroids) {
                    asteroid.updateImage(g);
                }
    	    }
        }
    }
    
    public void addSprite(double x, double y) {
        asteroids.add(new Asteroid(x,y));
    }
    
    public void updateScene(int width, int height) {
        synchronized(asteroids) {
            synchronized(ship) {
                double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
                ship.updateState(width, height, dt);
                for(Sprite asteroid : asteroids) {
                    asteroid.updateState(width, height, dt);  
                }
                time = System.nanoTime();
            }
        }
    }
    
    public void initialize() {
    	asteroids.clear();
    	asteroids.add(new Asteroid(0,0));
    	
    }
}
