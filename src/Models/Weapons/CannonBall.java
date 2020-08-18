/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Game.FPS;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.Enemys.Enemy;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class CannonBall extends Model implements IDamage {
    private float move = 0;
    private long startTime = System.currentTimeMillis();
    private float rot = 0;
    private float scaleFactor = 1;
    private float[] posAfterRot = {0,0,0};
    private CollisionData collisionData;
    private float[] trans = {0,0,0};
    private int roomNum;

    public CannonBall(String path, LoaderFactory factory, Level level){
        Pair<List<ObjData>,CollisionData> data = factory.create(path,CollisionType.BS);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    public void setRoomNum(int roomNum){
        this.roomNum = roomNum;
    }

    public void setScaleFactor(float sf){
        this.scaleFactor = sf;
    }
    private void destroy(){
        this.level.removeModel(this,this.roomNum);
    }
    @Override
    public void draw(GL2 gl) {
        checkCollision();
        gl.glPushMatrix();

        //calculate fps
        long endTime = System.currentTimeMillis();
        long timePassed = endTime - startTime;
        move -= 0.02f * (float) FPS.timePassed;
        startTime = System.currentTimeMillis();

        //update collision data
        this.collisionData.init();
        float[] moveArr = {0, 0, move};
        float[] scale = {this.scaleFactor * 2, this.scaleFactor * 2, this.scaleFactor * 2};

        this.collisionData.transAfterRotate(this.posAfterRot);
        this.collisionData.transAfterRotate(moveArr);
        this.collisionData.setScale(scale);
        this.collisionData.transAfterRotate(this.trans);
        int roomNum = this.level.getRoom(this.collisionData.getCenter());
        if(this.roomNum != roomNum && roomNum != -1){
            this.level.removeModel(this,this.roomNum);
            this.roomNum = roomNum;
            this.level.addModel(this,this.roomNum);
        }

        gl.glTranslatef(startPos[0], startPos[1], startPos[2]);
        gl.glRotatef(270 + rot, 0, 1, 0);
        gl.glTranslatef(posAfterRot[0], posAfterRot[1], move + posAfterRot[2]);
        gl.glScalef(4f, 4f, 4f);
        gl.glScalef(scaleFactor, scaleFactor, scaleFactor);

        gl.glTranslatef(1f,0f,0f);
        for (ObjData obj : data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
        if (Math.abs(move) > 100) {
            this.destroy();
        }

    }
    public void setRot(float angle){
        rot = angle;
        float[] rotate = {0,270+rot,0};
        this.collisionData.setRotate(rotate);
    }

    public void setPosAfterRot(float[] posAfterRot) {
        this.posAfterRot = posAfterRot;
    }
    public void setTransAfterRot(float[] trans){
        this.trans = trans;
    }


    @Override
    public void collide(ICollisionObj obj) {
        if(!(obj instanceof Enemy)) {
            this.level.removeModel(this,this.roomNum);
            if (obj instanceof Character) {
                int demage = 10;
                ((Character) obj).hit(demage);
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
