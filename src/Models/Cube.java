/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import CollisionDetection.AABB;
import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import LinearMath.Vector;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL2;

public class Cube extends Model implements ICollisionObj {
    private float x,y,z;
    private float width;
    private int list;
    private ObjData data = new ObjData();
    private CollisionData collisionData;

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

        double[] min = {Math.min(x,x+width),Math.min(y,y+width),Math.min(z,z+width),1};
        Vector minVec = new Vector(min,4);
        double[] max = {Math.max(x,x+width),Math.max(y,y+width),Math.max(z,z+width),1};
        Vector maxVec = new Vector(max,4);
        this.collisionData = new AABB(minVec,maxVec);
    }
    @Override
    public void draw(GL2 gl) {
        this.collisionData.draw(gl);
        data.draw(gl);
    }

    @Override
    public void collide() {

    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }
}
