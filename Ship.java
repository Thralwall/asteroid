import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ship extends Sprite {
    private double theta; // angle in radians
    private BufferedImage ogImage;

    public Ship() {
        super("Ship.png",25,25);
        ogImage = getImage();
        posX = 100;
        posY = 100;
    }

    public void rotate(double deg) {
        double rad = Math.toRadians(deg);
        theta += rad;
        setImage(rotateSprite(ogImage, theta));
    }

    public void accelerate(double acceleration, double dt) {
        //works if sprite if faceing towards the positive x axis
        double accX = acceleration*Math.cos(theta);
        double accY = acceleration*Math.sin(theta);
        velX = velX + accX*dt;
        velY = velY + accY*dt;
    }

    @Override
    public void updateState(int width, int height, double dt) {
        if(Controller.accelerate == true)
            accelerate(10,dt);
        if(Controller.decelerate == true)
            accelerate(-20,dt);
        if(Controller.rotateCCW == true)
            rotate(-1);
        if(Controller.rotateCW == true)
            rotate(1);
        
        super.updateState(width, height, dt);
    }
}