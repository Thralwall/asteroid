public class Drawer extends Thread {
    Model model;
    View view;
    public Drawer(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        while(true) {
            view.repaint();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {}
        }

    }
}