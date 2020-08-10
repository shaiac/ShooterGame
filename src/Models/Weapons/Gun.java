package Models.Weapons;

import Game.CoordinateSystem;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class Gun extends Weapon{
    private String path;
    private ObjectLoader loader;
    private GL2 gl;
    private float[] pos = {0,0,0};
    boolean fire = false;
    private float angle;
    private LoaderFactory factory;
    //old
    public Gun(String path, Level level) {
        this.level = level;
        this.path = path;
    }
    public Gun(String path, Level level, LoaderFactory factory){
        this.data = factory.create(path);
        this.factory = factory;
        this.level = level;
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        //this.translate(1f,0f,0f);
        this.startPos = startPos;
        this.loader = loader;
        this.gl = gl;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.07f,0.07f,0.07f);
        gl.glTranslatef(1f,0f,0f);
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
        /*if(ball == null){
            this.ball = new CannonBall("objects/ball/uploads_files_2078589_sphere.obj");
            this.ball.create(loader,gl,pos);
        }
        CannonBall newBall = this.ball.create(pos);*/
        CannonBall ball = new CannonBall("objects/ball/uploads_files_2078589_sphere.obj",this.factory,this.level);
        ball.setStartPos(pos);
        ball.setRot(angle-2f);
        ball.setScaleFactor(0.5f);
        float[] afterPos = {-3.5f,4.5f,-4f};
        ball.setPosAfterRot(afterPos);
        float[] trans = {2,0,0};
        ball.setTransAfterRot(trans);
        level.addModel(ball);
    }
}
