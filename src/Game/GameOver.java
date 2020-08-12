package Game;

import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.GL2;
import java.awt.*;

class GameOver {
    private GL2 gl;
    private TextRenderer renderer;
    GameOver(GL2 gl) {
        this.gl = gl;
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
