/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class TargetSymbol extends Model {

    public TargetSymbol(String path, LoaderFactory factory){
        Pair<List<ObjData>, CollisionData> data = factory.create(path, null);
        this.data = data.getKey();
        this.translate(1,0,0);
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }
    //old
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        this.translate(1f,0f,0f);
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.05f,0.05f,0.05f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
