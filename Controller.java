import java.awt.Graphics;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

class Controller implements MouseListener, KeyListener
{
    Model model;
    View view;
    Thread move;
    Thread draw;
    static public boolean accelerate;
    static public boolean decelerate;
    static public boolean rotateCW;
    static public boolean rotateCCW;

    Controller() throws IOException, Exception {
        model = new Model();
        view = new View(this);
        move = new Mover(model, view);
        draw = new Drawer(view);
        move.start();
        draw.start();
    }

    public void updateImage(Graphics g) {
        model.updateImage(g);
    }

    public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Gets here is left mouse button was clicked
			//model.addSprite(e.getX(), e.getY());
		} else if (SwingUtilities.isRightMouseButton(e))  {
			// Gets here if right mouse button was clicked
			model.updateScene(view.getWidth(), view.getHeight());
		}
		view.repaint();
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    public static void main(String[] args) throws Exception {
        new Controller();
    }

	public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == ' ') 
            model.shoot();
    }
	public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'W' || e.getKeyChar() == 'w')
			accelerate = true;
		if(e.getKeyChar() == 'S' || e.getKeyChar() == 's') 
            decelerate = true;
		if(e.getKeyChar() == 'A' || e.getKeyChar() == 'a')
            rotateCCW = true;
        if(e.getKeyChar() == 'D' || e.getKeyChar() == 'd')
            rotateCW = true;
    }
	public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'W' || e.getKeyChar() == 'w')
			accelerate = false;
		if(e.getKeyChar() == 'S' || e.getKeyChar() == 's') 
            decelerate = false;
		if(e.getKeyChar() == 'A' || e.getKeyChar() == 'a')
            rotateCCW = false;
        if(e.getKeyChar() == 'D' || e.getKeyChar() == 'd')
            rotateCW = false;
    }
}

