package Game;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThread implements Runnable {
    private LoadingPage loadingPage;
    private Thread worker;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval = 0;

    public ControlSubThread(Canvas canvas) {
        this.loadingPage = new LoadingPage(canvas);
        interval = 1000;
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println(
                        "Thread was interrupted, Failed to complete operation");
            }
            loadingPage.changeI();
            loadingPage.draw();
        }
    }

}
