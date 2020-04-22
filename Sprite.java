import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


class Sprite
{
	private String jpgName;
	protected double posX;
	protected double posY;
    protected double velX = 1;
    protected double velY = 1;
    protected double accX;
    protected double accY;
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
	
	public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}
	public Image getImage() { return image; }	
	
	public void updateImage(Graphics g) { //FIX
		g.drawImage(getImage(), (int) posX, (int) posY, width, height, null);
	}
	
	public void updateState(int width, int height, double dt) {}
	
	public boolean overlaps(Sprite s) { //FIX
		return false;
	}
}