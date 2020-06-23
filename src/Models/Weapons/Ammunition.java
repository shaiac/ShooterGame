package Models.Weapons;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Ammunition {
    int ammu;
    TextRenderer renderer;
    public Ammunition(int ammu) {
        this.ammu = ammu;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 40));
    }

    public void reduceAmmu(int num) {
        ammu -= num;
    }
    public void addAmmu(int num) {
        ammu += num;
    }

    public void draw() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1f, 1f, 0f, 0.8f);
        renderer.draw("Ammu: " + String.valueOf(ammu), 0, 50);
        renderer.endRendering();
    }
}
