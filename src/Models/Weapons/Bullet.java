package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javax.media.opengl.GL2;

public class Bullet extends Model {
    private String path;
    private float move;
    public Bullet(String inPath) {
        this.path = inPath;
        this.move = 0;
    }
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.data = loader.LoadModelToGL(path, gl);
        this.translate(1f,0f,0f);
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        move -= 0.001;
        gl.glTranslatef(startPos[0],startPos[1],startPos[2] + move);
        //gl.glScalef(0.05f, 0.05f, 0.05f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }
}
