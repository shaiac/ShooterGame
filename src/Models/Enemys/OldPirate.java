package Models.Enemys;

import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class OldPirate extends Enemy implements ICollisionObj {
    private String path;
    private long rotDuration = 100;
    private long attackDuration = 2500;
    private long startTimeRot = System.currentTimeMillis();
    private long startTimeAtt = System.currentTimeMillis();
    private CollisionData collisionData;

    public OldPirate(String path) {
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.data = loader.LoadModelToGL(path,gl,"AABB");
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
        this.scale(0.1f,0.1f,0.1f);
        float[] scale = {0.1f,0.1f,0.1f};
        this.collisionData.setScale(scale);
        this.rotate(-90,'x');
        this.rotate(90,'z');
        float[] angle = {90,0,0};
        this.collisionData.setRotate(angle);
    }

    @Override
    public void draw(GL2 gl) {
        collisionData.draw(gl);
        double m = ((-startPos[2] + charOrigin.getVec()[2])/(startPos[0] - charOrigin.getVec()[0]));
        float angle = (float) Math.toDegrees( Math.atan(m));
        if(charOrigin.getVec()[0]-startPos[0]   <0){
            angle += 180;
        }
        // calculate when to attack.
        if(weapon!= null){
            weapon.setAngle(angle);
            long currentTime = System.currentTimeMillis();
            long diff = currentTime - startTimeAtt;
            if(diff > attackDuration){
                startTimeAtt = currentTime;
                attack();
            }
            float[] pos = {startPos[0],startPos[1],startPos[2]};
            weapon.setPos(pos);
        }
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(angle,0,1,0);
        for (ObjData obj:data) {
            obj.draw(gl);
        }

        gl.glPushMatrix();
        //gl.glScalef(0.07f,0.07f,0.07f);
        gl.glTranslatef(2.5f,2.8f,-2.3f);
        gl.glRotatef(-7f,0,1,1.2f);
        //gl.glRotatef();
        if(weapon!= null){
            this.weapon.draw(gl);
        }
        gl.glPopMatrix();
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
