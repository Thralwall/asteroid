import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class View extends JFrame implements ActionListener {

    private class MyPanel extends JPanel {
        Controller controller;

        MyPanel(Controller c) {
            controller = c;
            addMouseListener(c);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            controller.updateImage(g);
            revalidate();
        }
    }


    public View(Controller c) throws Exception{
        MyPanel panel = new MyPanel(c);
        panel.setBackground(Color.black);
        setTitle("Asteroids+");
        setSize(800, 800);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(c);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.add(new JMenuItem("Puase"));
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("Load"));
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }
}