package Models.Weapons;

import Levels.Level;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class Cannon extends Model {
    private String path;
    private ObjectLoader loader;
    private GL2 gl;
    boolean fire = false;
    public Cannon(String path, Level level) {
        this.observer = level;
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        this.translate(1f,0f,0f);
        this.startPos = startPos;
        this.loader = loader;
        this.gl = gl;
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
    public void fire() {
        fire = true;
    }

    private void dofire() {
        CannonBall ball = new CannonBall("objects/ball/uploads_files_2078589_sphere.obj");
        ball.create(loader,gl,startPos);
        observer.addModel(ball);
    }
}
