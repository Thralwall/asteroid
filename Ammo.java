public class Ammo extends Sprite {
    public Ammo(double posX, double posY) {
        super("ammo.png");
        radius = getImage().getWidth()/2;
        this.posX = posX;
        this.posY = posY;
    }
}