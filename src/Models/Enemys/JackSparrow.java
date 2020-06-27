package Models.Enemys;

import LinearMath.Vector;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import Models.Weapons.Weapon;

import javax.media.opengl.GL2;

public class JackSparrow extends Enemy {
    private long rotDuration = 100;
    private long attackDuration = 2500;
    private long startTimeRot = System.currentTimeMillis();
    private long startTimeAtt = System.currentTimeMillis();
    private String path;

    public JackSparrow(String path) {
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
        weapon.setAngle(angle);
        // calculate when to attack.
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - startTimeAtt;
        if(diff > attackDuration){
            startTimeAtt = currentTime;
            attack();
        }
        float[] pos = {startPos[0],startPos[1],startPos[2]};
        weapon.setPos(pos);
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glRotatef(angle,0,1,0);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPushMatrix();
        gl.glTranslatef(4f,0,0);
        this.weapon.draw(gl);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

}
