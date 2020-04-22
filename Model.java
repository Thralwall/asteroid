import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	private static int count = 0;
    private static long time = System.nanoTime();
	
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    Model() throws IOException {
		sprites.add(new Asteroid(0,0));
    }

    public void update(Graphics g) {
    	synchronized(sprites) {
			for(Sprite sprite : sprites) {
				sprite.updateImage(g);
			}
    	}
    }
    
    public void addSprite(double x, double y) {
        sprites.add(new Asteroid(x,y));
    }
    
    public void updateScene(int width, int height) {
        synchronized(sprites) {
            double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
            for(Sprite sprite : sprites) {
                sprite.updateState(width, height, dt);  
            }
            time = System.nanoTime();
        }
    }
    
    public void initialize() {
    	sprites.clear();
    	sprites.add(new Ship());
    	
    }
}
