package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class CannonBall extends Model {
    private String path;
    private float move = 0;
    private long startTime = System.currentTimeMillis();
    public CannonBall(String path) {
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        this.translate(1f,0f,0f);
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        long endTime = System.currentTimeMillis();
        long timePassed = endTime- startTime;
        move -= 0.01f*(float)timePassed;
        startTime = System.currentTimeMillis();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(270,0,1,0);
        gl.glTranslatef(-4f,3.6f,move - 6);
        gl.glScalef(4f,4f,4f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
