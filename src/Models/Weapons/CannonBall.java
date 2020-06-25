package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;

import javax.media.opengl.GL2;

public class CannonBall extends Model {
    private String path;
    private float move = 0;
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
        move -= 0.3;
        gl.glRotatef(270,0,1,0);
        gl.glTranslatef(startPos[0] - 80,startPos[1] + 4,startPos[2] + move);
        gl.glScalef(6f,6f,6f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
