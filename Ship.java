import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ship extends Sprite {
    private double theta; // angle in radians
    private BufferedImage ogImage;

    public Ship() {
        super("Ship.png",25,25);
        ogImage = getImage();
    }

    public void rotate(double deg) {
        double rad = Math.toRadians(deg);
        theta += rad;
        setImage(rotateSprite(ogImage, theta));
    }

    @Override
    public void updateState(int width, int height, double dt) {
        rotate(1);
    }
}