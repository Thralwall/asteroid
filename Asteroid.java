import java.util.Random;

public class Asteroid extends Sprite {
    private int size;

    public Asteroid(double posX, double posY, int size) {
        super("Asteroid"+size+".png");
        // Only 3 sizes of asteroids. 3 being the largest, 1 being the smallest
        if(size > 3) {
            this.size = 3;
            setImage("Asteroid"+size+".png");
        }
        else if(size < 1){
            this.size = 1;
            setImage("Asteroid"+size+".png");
        }
        else
            this.size = size;
        this.posX = posX;
        this.posY = posY;

        // set random velocity
        Random rand = new Random();
        velX = (rand.nextDouble()*15)-7.5;
        velY = (rand.nextDouble()*15)-7.5;
        radius = getImage().getWidth()/2 - 1;
        // set asteroid image to random rotation
        setImage(rotateSprite(getImage(), (rand.nextDouble() * 2.0 * Math.PI)));
    }

    public int getSize(){ return size; }

    @Override
    public void updateState(int width, int height, double dt) {
        super.updateState(width, height, dt);
    }
}