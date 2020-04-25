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
    private int asteroidsShot;
    private int numAsteroids;
    private double gameTime;

    Model() throws IOException {
		asteroids.add(new Asteroid(0,0, 3));
    }

    public void updateImage(Graphics g) {
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
        synchronized(bullets) {
            bullets.add(new Bullet(ship.getPosX(), ship.getPosY(), ship.getVelX(), ship.getVelY(), ship.getAngle()));
        }
    }
    
    public void destroy(double posX, double posY, int size) {
        if(size > 1) {
            toAdd.add(new Asteroid(posX, posY, size-1));
            toAdd.add(new Asteroid(posX, posY, size-1));
            numAsteroids += 2;
        }
    }
    
    public void updateScene(int width, int height) {
        synchronized(asteroids) {
            synchronized(bullets) {
                synchronized(ship) {
                    double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
                    gameTime += dt;
                    ship.updateState(width, height, dt);
                    updateBullets(width, height, dt);
                    updateAsteroids(width, height, dt);
                    time = System.nanoTime();
                }
            }
        }
    }

    private void updateBullets(int width, int height, double dt) {
        Iterator<Bullet> iter = bullets.iterator();
            while(iter.hasNext()) {
                Bullet bullet = iter.next();
                bullet.updateState(width, height, dt);
                if(bullet.isOffScreen())
                    iter.remove();
            }
    }

    private void updateAsteroids(int width, int height, double dt) {
        Iterator<Asteroid> iterA = asteroids.iterator();
            while(iterA.hasNext()) {
                Asteroid asteroid = iterA.next();
                asteroid.updateState(width, height, dt);
                Iterator<Bullet> iterB = bullets.iterator();
                while(iterB.hasNext()) {
                    Bullet bullet = iterB.next();
                    if(asteroid.collides(bullet)) {
                        destroy(asteroid.getPosX(), asteroid.getPosY(), asteroid.getSize());
                        iterA.remove();
                        iterB.remove();
                        numAsteroids--;
                        asteroidsShot++;
                    }
                }
                asteroid.collides(ship);
            }
            for(Asteroid asteroid : toAdd) {
                asteroids.add(asteroid);
            }
            toAdd.clear();
    }
    
    public void initialize() {
    	asteroids.clear();
    	asteroids.add(new Asteroid(0,0, 3));
    	
    }
}
