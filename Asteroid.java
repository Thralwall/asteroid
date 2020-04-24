import java.util.Random;

public class Asteroid extends Sprite {
    private int size;

    public Asteroid(double posX, double posY, int size) {
        super("Asteroid.png");
        this.size = size;
        this.posX = posX;
        this.posY = posY;
        Random rand = new Random();
        velX = (rand.nextDouble()*11)-5;
        velY = (rand.nextDouble()*11)-5;
        radius = 22;
        // set asteroid image to random rotation
        setImage(rotateSprite(getImage(), (rand.nextDouble() * 2.0 * Math.PI)));
    }

    public int getSize(){ return size; }

    @Override
    public void updateState(int width, int height, double dt) {
        super.updateState(width, height, dt);
    }
}