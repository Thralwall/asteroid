import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


class Sprite
{
	private String jpgName;
	private double posX;
	private double posY;
    private double velX;
    private double velY;
    private double accX;
    private double accY;
	private Image image;
    private int width;
    private int height;

	public Sprite(String jpgName, int width, int height)
	{
		setImage(jpgName);
		posX = 0;
		posY = 0;
        this.width = width;
        this.height = height;
	}
	
	public int getX() {	return (int) posX; }
	public int getY() {	return (int) posY; }
	public void setX(int x) { posX = x; }
	public void setY(int y) { posY = y; }
	
	public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	public Image getImage() { return image; }	
	
	public void updateImage(Graphics g) { //FIX
		g.drawImage(getImage(), getX(), getY(), width, height, null);
	}
	
	public void updateState(int width, int height) {}
	
	public boolean overlaps(Sprite s) { //FIX
		return false;
	}
}