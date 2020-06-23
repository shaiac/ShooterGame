package Levels;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Life {
    int life;
    TextRenderer renderer;
    public Life() {
        life = 100;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 40));
    }

    public void reduceLife(int num) {
        life -= num;
    }

    public void draw() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(String.valueOf(life), 0, 0);
        renderer.endRendering();
    }

}
