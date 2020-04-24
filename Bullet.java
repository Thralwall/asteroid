public class Bullet extends Sprite {
    private int speed = 150;
    private boolean offScreen;

    public Bullet(double posX, double posY, double velX, double velY, double theta) {
        super("bullet.png");
        radius = 2;

        // calculate bullet's initial position and velocity from the ship position and velocity
        this.posX = posX;
        this.posY = posY;

        this.velX = velX + speed*Math.cos(theta);
        this.velY = velY + speed*Math.sin(theta);
    }

    public boolean isOffScreen() { return offScreen; }

    @Override
    public void updateState(int width, int height, double dt) {
        // bullets will not bounce off of the screen
		posX = posX + velX*dt;
        posY = posY + velY*dt;

        if((posX > width + radius) || (posX < 0 - radius) || (posY > height + radius) || (posY < 0 - radius)) {
			offScreen = true;
		}
    }
}