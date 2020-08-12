package Game;

import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.GL2;
import java.awt.*;

public class Help {
    private GL2 gl;
    private TextRenderer renderer;
    Help(GL2 gl) {
        this.gl = gl;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 20));
    }

    public void showHelp() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw("(ESC) Return to game", 450, 550);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("Keys:", 20, 550);
        renderer.draw("(W) move forward", 20, 520);
        renderer.draw("(S) move back", 20, 490);
        renderer.draw("(A) move left", 20, 460);
        renderer.draw("(D) move right", 20, 430);
        renderer.draw("Mouse moving- rotate camera", 20, 400);
        renderer.draw("Mouse left click- fire", 20, 370);
        renderer.draw("Mouse scroll wheel- change weapon", 400, 400);
        renderer.draw("Mouse right click- reload", 400, 370);
        renderer.draw("(R) reload", 200, 520);
        renderer.draw("(Q) change weapon", 200, 490);
        renderer.draw("(SPACE) fire", 200, 460);
        renderer.draw("(ARROWS) rotate camera", 200, 430);
        renderer.draw("Game Rules:", 20, 320);
        renderer.draw("You're a pirate in a pirate ship, your starting weapon is sword. ", 20, 290);
        renderer.draw("You can collect more weapons, ammo and life that scattered around the rooms", 20, 260);
        renderer.draw("by clashing into them.", 20, 230);
        renderer.draw("Your goal is to kill all the enemies that are in this level, after that find", 20, 200);
        renderer.draw("and collect the treasure boc to finish the level.", 20, 170);
        renderer.draw("Good Luck!", 200, 100);
        renderer.endRendering();
    }
}
