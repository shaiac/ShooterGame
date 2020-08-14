package Game;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.*;

class GameOver {
    private TextRenderer renderer;
    GameOver() {
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 40));
    }

    void endGamePage() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw("You're Dead!!", 300, 500);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("(1) Try Again ", 250, 400);
        renderer.draw("(2) Quit", 250, 350);
        renderer.endRendering();
    }
}
