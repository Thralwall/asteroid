import java.awt.Graphics;
import java.util.Random;

public class Asteroid extends Sprite {
    // public enum Size { LARGE, MEDIUM, SMALL };
    // private Size size;
    public Asteroid(double posX, double posY) {
        super("Asteroid.png",40,40);
        this.posX = posX;
        this.posY = posY;
        Random rand = new Random();
        velX = (rand.nextDouble()*11)-5;
        velY = (rand.nextDouble()*11)-5;
    }
    @Override
    public void updateState(int width, int height, double dt) {
        posX = posX + velX*dt;
        posY = posY + velY*dt;

        if(posX > width) {
			posX = 0;
		}
        if(posX < 0) {
            posX = width;
        }
		if(posY > height) {
			posY = 0;
		}
        if(posY < 0) {
            posY = height;
        }
    }
}