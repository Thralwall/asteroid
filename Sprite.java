import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.awt.Color;


class Sprite implements Serializable
{
	private String jpgName;
	protected double posX;
	protected double posY;
    protected double velX;
    protected double velY;
	protected double theta; // angle of rotation in radians
	protected int width;
	protected int height;
	protected int radius;
	private transient BufferedImage image;

	public Sprite(String jpgName)
	{
		setImage(jpgName);
		this.jpgName = jpgName;
        this.width = image.getWidth();
        this.height = image.getHeight();
	}
	
	public void setImage(String imagePath) {
        try {
			this.jpgName = imagePath;
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file.");
        }
	}

	public void setImage(BufferedImage image) {
			this.image = image;
	}

	public BufferedImage getImage() { return image; }	

	public double getPosX() { return posX; }
	public double getPosY() { return posY; }

	public double getVelX() { return velX; }
	public double getVelY() { return velY; }
	
	public void updateImage(Graphics g) {
		g.drawImage(getImage(), (int)posX - width/2, (int)posY - height/2, null);
	}
		
	public void updateState(int width, int height, double dt) {
		posX = posX + velX*dt;
        posY = posY + velY*dt;

        if(posX > width + radius - 8) {
			posX = 0 - radius + 8;
		}
        if(posX < 0 - radius + 8) {
            posX = width + radius - 8;
        }
		if(posY > height + radius - 8) {
			posY = 0 - radius + 8 + 10;
		}
        if(posY < 0 - radius + 8 + 10) {
            posY = height + radius - 8;
		}
	}
	
	public boolean collides(Sprite s) {
		if(Math.sqrt(Math.pow(s.posX - posX, 2)+Math.pow(s.posY-posY,2)) < (s.radius + radius)) {
			return true;
		}
		return false;
	}

	public BufferedImage rotateSprite(BufferedImage image, double theta) {
		// adapted from a stack overflow post to rotate an image without cropping: https://stackoverflow.com/questions/44086310/how-to-rotate-a-buffered-image-without-cropping-it-is-there-any-way-to-rotate-a
		int width = image.getWidth();
		int height = image.getHeight();

		// calculate the new image's dimensions
		int transWidth = (int)(height * Math.abs(Math.sin(theta)) + width * Math.abs(Math.cos(theta)));
		int transHeight = (int)(width * Math.abs(Math.sin(theta)) + height * Math.abs(Math.cos(theta)));
		this.width = transWidth;
		this.height = transHeight;

		// Create new image with rotated image embedded
		BufferedImage rotated = new BufferedImage(transWidth , transHeight , BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotated.createGraphics();
		g2d.setColor(new Color(0f, 0f, 0f, 0f)); // set transparent background
		g2d.fillRect(0,0,transWidth,transHeight);
		g2d.translate(transWidth/2,transHeight/2);
		g2d.rotate(theta);
		g2d.translate(-width/2, -height/2);
		g2d.drawImage(image,0,0,null);
		g2d.dispose();
		return rotated;
	}

	public void loadSprite() {
		setImage(jpgName);
		setImage(rotateSprite(image, theta));
	}
}