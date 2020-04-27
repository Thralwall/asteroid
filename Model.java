import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


class Model implements Serializable
{
	private static long time = System.nanoTime();
    private Ship ship = new Ship(400,400);
    private Ammo ammo;
    private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Asteroid> toAdd = new ArrayList<Asteroid>();
    private int asteroidsShot;
    private double gameTime;
    private int nextTime = 8;
    public boolean endGame = false;

    Model() throws IOException {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
		    asteroids.add(new Asteroid(-15,rand.nextDouble()*800, 3));
        }
        for(int i = 0; i < 4; i++) {
            asteroids.add(new Asteroid(rand.nextDouble()*800,-7, 3));
        }
        ammo = new Ammo((rand.nextDouble()*800*0.75) + 800*.125, (rand.nextDouble()*800*0.75) + 800*.125);
    }

    public void updateImage(Graphics g) {
        synchronized(asteroids) {
            synchronized(bullets){
                synchronized(ship) {
                    synchronized(ammo) {
                        if(!endGame)
                            ship.updateImage(g);
                        ammo.updateImage(g);
                        for(Asteroid asteroid : asteroids) {
                            asteroid.updateImage(g);
                        }
                        for(Bullet bullet : bullets) {
                            bullet.updateImage(g);
                        }
                        g.setColor(new Color(168,45,0));
                        g.setFont(new Font("Courier New", Font.BOLD, 18));
                        String time = String.format("Time: %.2f", gameTime);
                        String s = String.format("%12s%28s%18s", "Metors Mined: " + asteroidsShot, "Ammunition Remaining: " + ship.getAmmo(), time);
                        g.drawString(s, 12, 24);
                        if(endGame) {
                            g.setFont(new Font("Courier New", Font.BOLD, 40));
                            g.drawString("GAME OVER", 275, 300);
                        }
                    }
                }
            }
        }
    }
    
    public void shoot() {
        if(!Controller.paused){
            synchronized(bullets) {
                synchronized(ship) {
                    if(ship.shoot())
                        bullets.add(new Bullet(ship.getPosX(), ship.getPosY(), ship.getVelX(), ship.getVelY(), ship.getAngle()));
                }
            }
        }
    }
    
    public void destroy(double posX, double posY, int size) {
        if(size > 1) {
            toAdd.add(new Asteroid(posX, posY, size-1));
            toAdd.add(new Asteroid(posX, posY, size-1));
        }
    }
    
    public void updateScene(int width, int height) {
        if(!Controller.paused) { // The downside of having everything in seperate arrays: lots of synchronized statments
            synchronized(asteroids) {
                synchronized(bullets) {
                    synchronized(ship) {
                        synchronized(ammo) { // The upside: collision detection takes less time :)
                            double dt = ((double)(System.nanoTime()-time)/(double)1e9)*2;
                            if(!endGame)
                                gameTime += dt/2.0;
                            updateShip(width, height, dt);
                            updateBullets(width, height, dt);
                            updateAsteroids(width, height, dt);
                            time = System.nanoTime();
                        }
                    }
                }
            }
        }
    }

    private void updateShip(int width, int height, double dt) {
        ship.updateState(width, height, dt);
        if(ship.collides(ammo)) {
            Random rand = new Random();
            ship.addAmmo();
            ammo = new Ammo((rand.nextDouble()*width*0.75) + width*.125, (rand.nextDouble()*height*0.75) + height*.125);
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
                    asteroidsShot++;
                }
            }
            if(asteroid.collides(ship))
                endGame = true;
        }
        if(gameTime > nextTime) {
            Random rand = new Random();
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            while(Math.sqrt( Math.pow(ship.getPosX() - x, 2) + Math.pow(ship.getPosY() - y,2) ) < 150) {
                x = rand.nextDouble() * width;
                y = rand.nextDouble() * height;
            }
            toAdd.add(new Asteroid(x, y, 3));
            nextTime += 8;
        }
        for(Asteroid asteroid : toAdd) {
            asteroids.add(asteroid);
        }
        toAdd.clear();
    }

    public void loadSprites() {
        ship.loadSprite();
        ammo.loadSprite();
        for(Asteroid asteroid : asteroids)
            asteroid.loadSprite();
        for(Bullet bullet : bullets)
            bullet.loadSprite();
    }

    public void setTime() {
        time = System.nanoTime();
    }

    public void reset(int width, int height) {
        time = System.nanoTime();
        ship = new Ship(width/2, height/2);
        asteroids.clear();
        bullets.clear();
        toAdd.clear();
        asteroidsShot = 0;
        gameTime = 0;
        nextTime = 8;
        endGame = false;

        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
		    asteroids.add(new Asteroid(-15,rand.nextDouble()*800, 3));
        }
        for(int i = 0; i < 4; i++) {
            asteroids.add(new Asteroid(rand.nextDouble()*800,-7, 3));
        }
        ammo = new Ammo((rand.nextDouble()*width*0.75) + width*.125, (rand.nextDouble()*height*0.75) + height*.125);
    }
}
