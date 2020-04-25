public class Drawer extends Thread {
    Model model;
    View view;
    boolean stop = false;
    public Drawer(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        while(!stop) {
            view.repaint();
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {}
        }

    }
    public void stopIt() {
        stop = true;
    }
}