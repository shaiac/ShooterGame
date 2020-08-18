/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Game;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlSubThread implements Runnable {
    private LoadingPage loadingPage;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int interval = 0;

    ControlSubThread(Canvas canvas, LoadingPage loadingPage) {
        this.loadingPage = loadingPage;
        interval = 1000;
    }

    void start() {
        Thread worker = new Thread(this);
        worker.start();
    }

    void stop() {
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
