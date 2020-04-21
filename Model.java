import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	private static int count = 0;
	
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    Model() throws IOException {
		sprites.add(new Ship());
    }

    public void update(Graphics g) {
    	synchronized(sprites) {
			for(Sprite sprite : sprites) {
				sprite.updateImage(g);
			}
    	}
    }
    
    public void addSprite(int x, int y) {
        sprites.add(new Asteroid());
        sprites.get(sprites.size()-1).setX(x);
        sprites.get(sprites.size()-1).setY(y);
    }
    
    public void updateScene(int width, int height) {
    	// synchronized(sprites) {
	    // 	Iterator<Sprite> iter = sprites.iterator();
	    // 	while (iter.hasNext()) {
	    // 	    Sprite s = iter.next();
	    // 	    if((s instanceof RobberCar) && ((RobberCar)s).hasEscaped()) {
	    // 	    	iter.remove();
	    // 	    	System.out.println("I'm free!");
	    // 	    }
	    // 	}
	    // 	for(Sprite sprite : sprites) {
		// 		sprite.updateState(width, height);
		// 		if(sprite instanceof CopCar) {
		// 			for(Sprite robber : sprites) {
		// 				if((robber instanceof RobberCar) && sprite.overlaps(robber)) {
		// 					((RobberCar)robber).captured();
		// 				}
		// 			}
		// 		}
		// 	}
    	// }
    }
    
    public void initialize() {
    	sprites.clear();
    	sprites.add(new Ship());
    	
    }
}
