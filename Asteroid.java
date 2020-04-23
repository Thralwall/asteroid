import java.util.Random;

public class Asteroid extends Sprite {
    // public enum Size { LARGE, MEDIUM, SMALL };
    // private Size size;
    public Asteroid(double posX, double posY) {
        super("Asteroid.png");
        this.posX = posX;
        this.posY = posY;
        Random rand = new Random();
        velX = (rand.nextDouble()*11)-5;
        velY = (rand.nextDouble()*11)-5;
        radius = 42;
        // set asteroid image to random rotation
        setImage(rotateSprite(getImage(), (rand.nextDouble() * 2.0 * Math.PI)));
    }
    @Override
    public void updateState(int width, int height, double dt) {
        super.updateState(width, height, dt);
    }
}