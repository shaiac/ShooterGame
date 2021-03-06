package Models;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class PirateShip extends Model{
    private String path;
    public PirateShip(String path) {
        this.path = path;
    }

    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl);
        this.translate(1f,0f,0f);
        this.startPos = startPos;
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.05f,0.05f,0.05f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();

    }
}
