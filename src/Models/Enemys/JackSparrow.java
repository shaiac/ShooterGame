package Models.Enemys;

import LinearMath.Vector;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import Models.Weapons.Weapon;

import javax.media.opengl.GL2;

public class JackSparrow extends Enemy {
    private String path;

    public JackSparrow(String path, Weapon weapon) {
        this.weapon = weapon;
        this.path = path;
    }

    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        this.data = loader.LoadModelToGL(path,gl);
        this.startPos = startPos;
        this.scale(0.03f,0.03f,0.03f);
        this.rotate(90,'y');
    }

    @Override
    public void draw(GL2 gl) {
        //rotate around the origin
        double m = ((-startPos[2] + charOrigin.getVec()[2])/(startPos[0] - charOrigin.getVec()[0]));
        float angle = (float) Math.toDegrees( Math.atan(m));
        if(charOrigin.getVec()[0]-startPos[0]   <0){
            angle += 180;
        }


        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(angle,0,1,0);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

}
