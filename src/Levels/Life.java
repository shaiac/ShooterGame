/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Levels;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Life {
    private int life;
    private TextRenderer renderer;
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
        renderer.draw("Life:" + life, 0, 0);
        renderer.endRendering();
    }
    public void addLife(int amount){
        this.life += amount;
    }
    public int getRemainLife() {
        return life;
    }

}
