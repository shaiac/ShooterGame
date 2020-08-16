package Game;

import javax.media.opengl.GL2;

class GameOver extends GamePage{
    GameOver(GL2 gl) {
        super(gl);
    }

    void draw(String title) {
        data.draw(gl);
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(title, 250, 500);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("(1) Play Again ", 250, 400);
        renderer.draw("(2) Quit", 250, 350);
        renderer.endRendering();
    }
}
