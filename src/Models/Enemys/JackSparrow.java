package Models.Enemys;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Levels.Level;
import Levels.Life;
import LinearMath.Vector;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import Models.Weapons.Weapon;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class JackSparrow extends Enemy implements ICollisionObj {
    private long attackDuration = 2500;
    private long startTimeAtt = System.currentTimeMillis();
    public String path;
    private CollisionData collisionData;

    public JackSparrow(String path, LoaderFactory factory, Level level, int inRoomNumber){
        super(inRoomNumber);
        Pair<List<ObjData>,CollisionData> data = factory.create(path,CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.scale(0.03f,0.03f,0.03f);
        this.rotate(90,'y');
        float[] scale = {0.03f,0.03f,0.03f};
        this.collisionData.setScale(scale);
        this.life = new Life();
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.data = loader.LoadModelToGL(path,gl, CollisionType.AABB);
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
        this.scale(0.03f,0.03f,0.03f);
        this.rotate(90,'y');
        float[] scale = {0.03f,0.03f,0.03f};
        this.collisionData.setScale(scale);
        float[] angle = {0,90,0};
        this.collisionData.setRotate(angle);
    }

    @Override
    public void draw(GL2 gl) {
        collisionData.draw(gl);
        //rotate around the origin
        double m = ((-startPos[2] + charOrigin.getVec()[2])/(startPos[0] - charOrigin.getVec()[0]));
        float angle = (float) Math.toDegrees( Math.atan(m));
        if(charOrigin.getVec()[0]-startPos[0]   <0){
            angle += 180;
        }
        weapon.setAngle(angle);
        // calculate when to attack.
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - startTimeAtt;
        if(diff > attackDuration){
            startTimeAtt = currentTime;
            attack();
        }
        float[] pos = {startPos[0],startPos[1],startPos[2]};
        weapon.setPos(pos);
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(angle,0,1,0);
        if(hit){
            gl.glDisable(GL2.GL_LIGHTING);
            gl.glColor3f(1,0,0);
            if(System.currentTimeMillis() - startHit > 500){
                hit = false;
            }
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glEnable(GL2.GL_LIGHTING);

        gl.glPushMatrix();
        gl.glTranslatef(4f,0,0);
        this.weapon.draw(gl);
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
