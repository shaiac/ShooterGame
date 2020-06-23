package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class Shotgun extends Weapon {
    private String path;

    public Shotgun(String inPath) {
        this.path = inPath;
        ammu = 20;
        weapontype = WeaponType.GUN;
    }

    public void create(ObjectLoader loader, GL2 gl,float[] pos){
        data = loader.LoadModelToGL(path,gl);
    }

    @Override
    public void draw(GL2 gl) {
        if(!picked){
            //drawUnpicked();
        }
        gl.glPushMatrix();
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void attack() {


    }

    @Override
    public void reload() {

    }

    @Override
    public void addAmmu() {

    }
}
