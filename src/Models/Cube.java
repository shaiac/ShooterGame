/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL2;

public class Cube extends Model {
    private float x,y,z;
    private float width;
    private int list;
    private ObjData data = new ObjData();

    public Cube(float x, float y,float z,float width){
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
    }

    public void setTexture(Texture texture) {
        data.setTexture(texture);
        data.setTextureWrap(GL2.GL_REPEAT);
    }
    public void create(ObjectLoader loader,GL2 gl,float[] pos){
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);
        gl.glBegin(GL2.GL_QUADS);
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
        gl.glEndList();
        data.setList(list);
    }
    @Override
    public void draw(GL2 gl) {
        data.draw(gl);
    }
}
