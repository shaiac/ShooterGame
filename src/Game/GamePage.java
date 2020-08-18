/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Game;

import Models.DataAndLoader.ObjData;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class GamePage {
    TextRenderer renderer;
    GL2 gl;
    ObjData data = new ObjData();

    public GamePage(GL2 gl) {
        this.gl = gl;
        renderer = new TextRenderer(new Font("Helvetica", Font.PLAIN, 50));
        create();
        setTexture();
    }

    private void setTexture() {
        try {
            String filename="resources/starting_menu.jpg"; // the FileName to open
            Texture texture = TextureIO.newTexture(new File(filename), true);
            data.setTexture(texture);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(){
        float width = 10;
        float hieght = 6;

        int list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0.0f,0.0f);
        gl.glVertex3f(-width,-hieght,-6);

        gl.glTexCoord2f(0f,1);
        gl.glVertex3f(-width,hieght,-6);

        gl.glTexCoord2f(1,1);
        gl.glVertex3f(width,hieght,-6);

        gl.glTexCoord2f(1,0f);
        gl.glVertex3f(width,-hieght,-6);
        gl.glEnd();
        gl.glEndList();
        data.setList(list);

    }


}
