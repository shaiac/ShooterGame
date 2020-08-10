package Models.Objects;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Gold extends Model {
    private String path;
    //Constructor
    public Gold(String path) {
        this.path = path;
    }

    public Gold(String path, LoaderFactory factory){
        this.data = factory.create(path);
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.01f,0.01f,0.01f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
