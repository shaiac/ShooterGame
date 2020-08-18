/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import Game.CoordinateSystem;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;

import javax.media.opengl.GL2;

public class Cannon extends Weapon {
    private float[] pos = {0,0,0};
    boolean fire = false;
    private float angle;
    private LoaderFactory factory;
    public Cannon(String path, Level level) {
        this.level = level;
        this.path = path;
    }

    public Cannon(String path, Level level, LoaderFactory factory){
        this.data = factory.create(path);
        this.translate(1f,0f,0f);
        this.factory = factory;
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos =startPos;
    }


    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.06f,0.06f,0.06f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
        if (fire) {
            dofire();
            fire = false;
        }
    }

    @Override
    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {

    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void attack() {
        fire = true;
    }

    @Override
    public int reload() {
        return 0;
    }

    public void setPos(float[] pos){
        this.pos = pos;
    }
    private void dofire() {
        CannonBall ball= new CannonBall("objects/ball/uploads_files_2078589_sphere.obj",this.factory,this.level);
        ball.setStartPos(pos);
        ball.setRot(angle);
        float[] afterPos = {-4f,3.6f,-6f};
        ball.setPosAfterRot(afterPos);
        float[] trans = {4,0,0};
        ball.setTransAfterRot(trans);
        int roomNum = this.level.getRoom(pos);
        ball.setRoomNum(roomNum);
        level.addModel(ball,roomNum);
    }
}
