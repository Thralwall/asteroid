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

    Controller() throws IOException, Exception {
        model = new Model();
        view = new View(this);
    }

    public void update(Graphics g) {
        model.update(g);
    }

    public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Gets here is left mouse button was clicked
			model.addSprite(e.getX(), e.getY());
		} else if (SwingUtilities.isRightMouseButton(e))  {
			// Gets here if right mouse button was clicked
			//model.updateScene(view.getWidth(), view.getHeight());
		}
		view.repaint();
    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }

    public static void main(String[] args) throws Exception {
        //  Use the following line to determine which directory your program
        //  is being executed from, since that is where the image files will
        //  need to be.
    	//System.out.println("cwd=" + System.getProperty("user.dir"));
        new Controller();
    }

	public void keyTyped(KeyEvent e) {
		// RobberCar car = new RobberCar();
		// if(e.getKeyChar() == 'n' || e.getKeyChar() == 'N')
		// 	System.out.printf("%d robbers have been captured and %d robbers have escaped\n",car.getCaptured(),car.getEscaped());
		// else if(e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
		// 	model.initialize();
		// 	view.repaint();
		// }
		// else if(e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
		// 	Thread t = new Thread(new SpriteMover(model,view));
		// 	t.start();
		// }
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}

