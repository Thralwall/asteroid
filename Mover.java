public class Mover extends Thread {
    Model model;
    View view;
    public Mover(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while(true) {
            model.updateScene(view.getWidth(), view.getHeight());
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {}
        }

    }
}