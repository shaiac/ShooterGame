package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Bullet extends Model implements ICollisionObj {
    private float angle;
    private float move = 0;
    private float[] bulletPos = {0,0,0};
    private long startTime = System.currentTimeMillis();
    private CollisionData collisionData;
    public Bullet( List<ObjData> objData) {
        this.data = objData;
        this.move = 0;
    }
    public Bullet(String path, LoaderFactory factory){
        Pair<List<ObjData>, CollisionData> data = factory.create(path, CollisionType.POINT);
        this.data = data.getKey();
        this.collisionData = data.getValue();
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }

    public void setBulletPos(float[] bulletPos) {
        this.bulletPos = bulletPos;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        long endTime = System.currentTimeMillis();
        long timePassed = endTime- startTime;
        move -= 0.03f*(float)timePassed;
        startTime = System.currentTimeMillis();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);

        gl.glRotatef(angle,0,1,0);
        // y = -0.48f ,z = -3.9
        gl.glTranslatef(bulletPos[0],bulletPos[1],bulletPos[2]+ move);

        gl.glRotatef(180, 0, 1, 0);
        gl.glScalef(0.08f, 0.08f, 0.08f);
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
        return this.collisionData;
    }
}
