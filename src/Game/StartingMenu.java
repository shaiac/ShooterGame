package Game;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.*;

public class StartingMenu {
    private TextRenderer renderer;
    public StartingMenu() {
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 50));
    }

    public void draw() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("(Enter) Start Game", 150, 450);
        renderer.draw("(F1) Instructions", 150, 350);
        renderer.draw("(Q) Quit Game", 150, 250);
        renderer.endRendering();
    }
}
