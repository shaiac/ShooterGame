package Game;

import java.awt.*;

public abstract class GamePage {
    int height;
    int width;
    boolean reSized;
    String backgroundPath;
    Graphics g;
    public GamePage(int height, int width, String backgroundPath, Graphics g) {
        this.height = height;
        this.width = width;
        this.backgroundPath = backgroundPath;
        this.g = g;
        this.reSized = false;
    }

    public void setSize(Dimension dim) {
        height = dim.height;
        width = dim.width;
    }

    public void reSized() {
        this.reSized = true;
    }
}
