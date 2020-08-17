package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Game.FPS;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Enemys.Enemy;
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
    private int demage = 10;
    private int roomNum;


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
    public void setRoomNum(int roomNum){
        this.roomNum = roomNum;
    }
    private void destroy(){
        this.dead = true;
        this.level.removeModel(this,this.roomNum);
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {

        long endTime = System.currentTimeMillis();
        long timePassed = endTime- startTime;
        move -= 0.03f*(float) FPS.timePassed;
        //move -= 1.5f;
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
        int roomNum = this.level.getRoom(this.collisionData.getCenter());
        if(this.roomNum != roomNum && roomNum != -1){
            this.level.removeModel(this,this.roomNum);
            this.roomNum = roomNum;
            this.level.addModel(this,this.roomNum);
        }

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
        checkCollision();
    }


    @Override
    public void collide(ICollisionObj obj) {
        if(!(obj instanceof Character)) {
            this.level.removeModel(this,this.roomNum);
            if (obj instanceof Enemy) {
                ((Enemy) obj).hit(demage);
            }
        }
    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }

    private void checkCollision() {
        level.checkCollision(this,this.roomNum);
    }

}
