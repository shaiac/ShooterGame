/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Objects;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class Skull extends Model {
    public Skull(String path) {
        this.path = path;
    }
    public Skull(String path, LoaderFactory factory){
        this.data = factory.create(path);
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
