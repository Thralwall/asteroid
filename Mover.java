public class Mover extends Thread {
    Model model;
    View view;
    boolean stop = false;
    public Mover(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while(!stop) {
            model.updateScene(view.getWidth(), view.getHeight());
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