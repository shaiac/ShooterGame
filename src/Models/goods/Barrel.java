package Models.goods;

import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class Barrel extends Model implements ICollisionObj {
    private String path;
    private CollisionData collisionData;

    public Barrel(String path) {
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl, "AABB");
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
        float[] scale = {0.05f,0.05f,0.05f};
        this.collisionData.setScale(scale);
    }

    @Override
    public void draw(GL2 gl) {
        collisionData.draw(gl);
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.05f,0.05f,0.05f);
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
