import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


class Model implements Serializable
{
	private static long time = System.nanoTime();
    private Ship ship = new Ship();
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Asteroid> toAdd = new ArrayList<Asteroid>();
    private int asteroidsShot;
    private int numAsteroids;
    private double gameTime;

    Model() throws IOException {
        Random rand = new Random();
        for(int i = 0; i < 6; i++) {
		    asteroids.add(new Asteroid(-24,rand.nextInt(800*2+1)-800, 3));
            numAsteroids++;
        }
        for(int i = 0; i < 6; i++) {
            asteroids.add(new Asteroid(rand.nextInt(800*2+1)-800,-24, 3));
            numAsteroids++;
        }
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
            System.out.println(this);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.PLAIN, 18));
        g.drawString("Number of Asteroids: " + numAsteroids, 12 , 12);
        g.drawString("Number of Asteroids Destroyed: " + asteroidsShot, 12, 32);
        String s = String.format("Time: %.2f", gameTime);
        g.drawString(s, 12, 54);
    }
    
    public void shoot() {
        if(!Controller.paused){
            synchronized(bullets) {
                bullets.add(new Bullet(ship.getPosX(), ship.getPosY(), ship.getVelX(), ship.getVelY(), ship.getAngle()));
            }
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
        if(!Controller.paused) {
            synchronized(asteroids) {
                synchronized(bullets) {
                    synchronized(ship) {
                        double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
                        gameTime += dt/2.0;
                        ship.updateState(width, height, dt);
                        updateBullets(width, height, dt);
                        updateAsteroids(width, height, dt);
                        time = System.nanoTime();
                        
                    System.out.println(this);
                    }
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

    public void loadSprites() {
        ship.loadSprite();
        for(Asteroid asteroid : asteroids)
            asteroid.loadSprite();
        for(Bullet bullet : bullets)
            bullet.loadSprite();
    }
    
    public void initialize() {
    	asteroids.clear();
    	asteroids.add(new Asteroid(0,0, 3));
    	
    }

    public void setTime() {
        time = System.nanoTime();
    }
}
