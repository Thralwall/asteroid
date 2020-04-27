import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ship extends Sprite {
    private transient BufferedImage ogImage;
    private int maxSpeed = 70;
    private int rotationSpeed = 60;
    private int acceleration = 20;
    private int ammo = 20;

    public Ship(double posX, double posY) {
        super("ship.png");
        ogImage = getImage();
        this.posX = posX;
        this.posY = posY;
        radius = 12;
    }

    public double getAngle() { return theta; }

    public void rotate(double deg, double dt) {
        double rad = Math.toRadians(deg);
        theta += rad*dt;
        setImage(rotateSprite(ogImage, theta));
    }

    public void accelerate(double acceleration, double dt) {
        //works if sprite if faceing towards the positive x axis
        double accX = acceleration*Math.cos(theta);
        double accY = acceleration*Math.sin(theta);
        velX = velX + accX*dt;
        velY = velY + accY*dt;
        // FIX: do trig to find velX velY if speed is too great
        if(velX > maxSpeed)
            velX = maxSpeed;
        if(velY > maxSpeed)
            velY = maxSpeed;
        if(velX < -maxSpeed)
            velX = -maxSpeed;
        if(velY < -maxSpeed)
            velY = -maxSpeed;
    }

    @Override
    public void updateState(int width, int height, double dt) {
        if(Controller.accelerate == true)
            accelerate(acceleration,dt);
        if(Controller.decelerate == true)
            accelerate(-acceleration,dt);
        if(Controller.rotateCCW == true)
            rotate(-rotationSpeed,dt);
        if(Controller.rotateCW == true)
            rotate(rotationSpeed,dt);
        
        super.updateState(width, height, dt);
    }

    public boolean shoot() {
        if(ammo > 0) {
            ammo -= 1;
            return true;
        }
        else
            return false;
    }

    public void addAmmo() {
        ammo += 10;
    }

    public int getAmmo() {
        return ammo;
    }
}