package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Bullet extends Model implements IDamage {
    private float angle;
    private float move = 0;
    private float[] bulletPos = {0,0,0};
    private long startTime = System.currentTimeMillis();
    private CollisionData collisionData;
    private boolean dead = false;
    private Level level;


    public Bullet( List<ObjData> objData) {
        this.data = objData;
        this.move = 0;
    }
    public Bullet(String path, LoaderFactory factory,Level level){
        Pair<List<ObjData>, CollisionData> data = factory.create(path, CollisionType.POINT);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }

    public void setBulletPos(float[] bulletPos) {
        this.bulletPos = bulletPos;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        float[] rotate = {0,angle,0};
        this.collisionData.setRotate(rotate);
    }
    private void destroy(){
        this.dead = true;
        this.level.removeModel(this);
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {

        long endTime = System.currentTimeMillis();
        long timePassed = endTime- startTime;
        move -= 0.03f*(float)timePassed;
        startTime = System.currentTimeMillis();

        //update collision data
        this.collisionData.init();
        float[] moveArr = {0, 0, move};
        //float[] scale = {0.04f,0.04f,0.04f};
        float[] trans = {0,0, -0.4f};

        this.collisionData.transAfterRotate(bulletPos);
        this.collisionData.transAfterRotate(moveArr);
        this.collisionData.transAfterRotate(trans);
        //this.collisionData.setScale(scale);
        this.collisionData.draw(gl);

        gl.glPushMatrix();
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
        if (Math.abs(move) > 100) {
            this.destroy();
        }
    }


    @Override
    public void collide(ICollisionObj obj) {
        this.level.removeModel(this);
        if(obj instanceof Character){

        }
    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }
}
