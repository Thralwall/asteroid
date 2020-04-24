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
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Asteroid> toAdd = new ArrayList<Asteroid>();

    Model() throws IOException {
		asteroids.add(new Asteroid(0,0, 3));
    }

    public void update(Graphics g) {
    	synchronized(asteroids) {
            synchronized(bullets){
                synchronized(ship) {
                    ship.updateImage(g);
                    for(Asteroid asteroid : asteroids) {
                        asteroid.updateImage(g);
                    }
                    for(Bullet bullet : bullets) {
                        bullet.updateImage(g);
                    }
                }
            }
        }
    }
    
    public void shoot() {
        bullets.add(new Bullet(ship.getPosX(), ship.getPosY(), ship.getVelX(), ship.getVelY(), ship.getRotation()));
    }
    
    public void destroy(double posX, double posY, int size) {
        if(size > 1) {
            toAdd.add(new Asteroid(posX, posY, size-1));
            toAdd.add(new Asteroid(posX, posY, size-1));
        }
    }
    
    public void updateScene(int width, int height) {
        synchronized(asteroids) {
            synchronized(bullets) {
                synchronized(ship) {
                    double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
                    ship.updateState(width, height, dt);
                    Iterator<Bullet> iterB = bullets.iterator();
                    while(iterB.hasNext()) {
                        Bullet bullet = iterB.next();
                        bullet.updateState(width, height, dt);
                        if(bullet.isOffScreen())
                            iterB.remove();
                    }
                    Iterator<Asteroid> iterA = asteroids.iterator();
                    while(iterA.hasNext()) {
                        Asteroid asteroid = iterA.next();
                        asteroid.updateState(width, height, dt);
                        iterB = bullets.iterator();
                        while(iterB.hasNext()) {
                            Bullet bullet = iterB.next();
                            if(asteroid.collides(bullet)) {
                                destroy(asteroid.getPosX(), asteroid.getPosY(), asteroid.getSize());
                                iterA.remove();
                                iterB.remove();
                            }
                        }
                        asteroid.collides(ship);
                    }
                    for(Asteroid asteroid : toAdd) {
                        asteroids.add(asteroid);
                    }
                    toAdd.clear();
                    time = System.nanoTime();
                }
            }
        }
    }
    
    public void initialize() {
    	asteroids.clear();
    	asteroids.add(new Asteroid(0,0, 3));
    	
    }
}
