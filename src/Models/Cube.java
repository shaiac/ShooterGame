/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL2;

public class Cube implements Model {
    private float x,y,z;
    private float width;
    private Texture texture;

    public Cube(float x, float y,float z,float width){
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(GL2 gl) {
        texture.bind(gl);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_QUADS);
        // front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x, y, z+width);
        gl.glTexCoord2f(2f, 0.0f);
        gl.glVertex3f(x, y+width, z+width);
        gl.glTexCoord2f(2f, 1.0f);
        gl.glVertex3f(x+width, y+width, z+width);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x+width, y, z+width);
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x, y, z);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x, y+width, z);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x+width, y+width, z);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x+width, y, z);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x, y+width, z);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x+width, y+width, z);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x+width, y+width, z+width);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x, y+width, z+width);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x, y, z);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x+width, y, z);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x+width, y, z+width);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x, y, z+width);
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x+width, y, z);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x+width, y+width, z);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x+width, y+width, z+width);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x+width, y, z+width);
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(x, y, z);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(x, y+width, z);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(x, y+width, z+width);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(x, y, z+width);
        gl.glEnd();
    }
}
