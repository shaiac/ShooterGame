package Models.Weapons;

import CollisionDetection.CollisionData;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class CannonBall extends Model {
    private String path;
    private float move = 0;
    private long startTime = System.currentTimeMillis();
    private float rot = 0;
    private float scaleFactor = 1;
    private float[] posAfterRot = {0,0,0};
    private CollisionData collisionData;
    public CannonBall(String path) {
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl,"BS");
        this.collisionData = loader.getCollisionData();
        this.translate(1f,0f,0f);
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    public void setScaleFactor(float sf){
        this.scaleFactor = sf;
        float[] scale = {sf,sf,sf};
        this.collisionData.setScale(scale);
    }
    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        long endTime = System.currentTimeMillis();
        long timePassed = endTime- startTime;
        move -= 0.02f*(float)timePassed;
        startTime = System.currentTimeMillis();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(270+rot,0,1,0);
        gl.glTranslatef(posAfterRot[0],posAfterRot[1],move + posAfterRot[2]);
        gl.glScalef(4f,4f,4f);
        gl.glScalef(scaleFactor,scaleFactor,scaleFactor);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
    public void setRot(float angle){
        rot = angle;
    }

    public void setPosAfterRot(float[] posAfterRot) {
        this.posAfterRot = posAfterRot;
    }
}
