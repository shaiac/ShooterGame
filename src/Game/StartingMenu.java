package Game;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartingMenu {
    private TextRenderer renderer;
    private GL2 gl;
    Texture texture;
    public StartingMenu(GL2 gl) {
        this.gl = gl;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 50));
        setTexture();
    }

    private void setTexture() {
        try {
            String filename="resources/starting_menu.jpg"; // the FileName to open
            texture= TextureIO.newTexture(new File( filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        renderer.beginRendering(800, 600);
        renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.draw("(Enter) Start Game", 150, 450);
        renderer.draw("(F1) Instructions", 150, 350);
        renderer.draw("(Q) Quit Game", 150, 250);
        renderer.endRendering();
        texture.bind(gl);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        float texwidth = 800;
        float texhieght = 600;
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0.0f,0.0f);
        gl.glVertex3f(0,0,0);

        gl.glTexCoord2f(0f,texhieght);
        gl.glVertex3f(0,600,0);

        gl.glTexCoord2f(texwidth,texhieght);
        gl.glVertex3f(800,600,0);

        gl.glTexCoord2f(texwidth,0f);
        gl.glVertex3f(800,0,0);
        gl.glEnd();
    }
}
