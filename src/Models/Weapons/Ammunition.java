/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

public class Ammunition {
    private int ammo;
    private TextRenderer renderer;
    public Ammunition(int ammo) {
        this.ammo = ammo;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 40));
    }

    public void reduceAmmo(int num) {
        ammo -= num;
    }
    public void addAmmo(int num) {
        ammo += num;
    }

    public void draw() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1f, 1f, 0f, 0.8f);
        renderer.draw("Ammo: " + String.valueOf(ammo), 0, 50);
        renderer.endRendering();
    }
    public int getAmmo() {
        return ammo;
    }
}
