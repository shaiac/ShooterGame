package Models.goods;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class AmmoBox extends Model implements ICollisionObj {
    private String path;
    private CollisionData collisionData;

    public AmmoBox(String path) {
        this.path = path;
    }
    public AmmoBox(String Path, Level level, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path, CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        float[] scale = {0.2f,0.2f,0.2f};
        this.collisionData.setScale(scale);

    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl, CollisionType.AABB);
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
        float[] scale = {0.2f,0.2f,0.2f};
        this.collisionData.setScale(scale);

    }

    @Override
    public void draw(GL2 gl) {
        collisionData.draw(gl);
        gl.glPushMatrix();
        //gl.glRotatef(rotate, 0, 1, 0);
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.2f,0.2f,0.2f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void collide() {

    }

    @Override
    public CollisionData getCollisionData() {
        return collisionData;
    }
}
