package Models.Enemys;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Levels.Level;
import Levels.Life;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class OldPirate extends Enemy implements ICollisionObj {
    private String path;
    private long attackDuration = 2500;
    private long startTimeAtt = System.currentTimeMillis();
    private CollisionData collisionData;

    public OldPirate(String path) {
        this.path = path;
    }
    public OldPirate(String path, Level level, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path, CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.scale(0.1f,0.1f,0.1f);
        float[] scale = {0.1f,0.1f,0.1f};
        this.collisionData.setScale(scale);
        this.rotate(-90,'x');
        this.rotate(90,'z');
        float[] angle = {90,0,0};
        this.collisionData.setRotate(angle);
        this.life = new Life();
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.data = loader.LoadModelToGL(path,gl,CollisionType.AABB);
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
        if(hit){
            float[] color = {0f, 1f, 0f, 1f};
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,color,1);
            if(System.currentTimeMillis() - startHit > 1000){
                hit = false;
            }
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        float[] color = {1f, 1, 1, 1};
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE,color,1);
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
    public void collide(ICollisionObj obj) {

    }

    @Override
    public CollisionData getCollisionData() {
        return collisionData;
    }
}
