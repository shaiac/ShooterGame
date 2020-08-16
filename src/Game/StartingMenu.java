package Game;


import javax.media.opengl.GL2;

public class StartingMenu extends GamePage{
    public StartingMenu(GL2 gl) {
        super(gl);
    }

    public void draw() {
        data.draw(gl);
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("(Enter) Start Game", 150, 450);
        renderer.draw("(F1) Instructions", 150, 350);
        renderer.draw("(Q) Quit Game", 150, 250);
        renderer.endRendering();
    }
}
