package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class Ak47 extends Weapon{
    private String path;

    public Ak47(String inPath) {
        this.path = inPath;
        ammu = 20;
        weapontype = WeaponType.GUN;
    }

    public void create(ObjectLoader loader, GL2 gl,float[] startPos){
        data = loader.LoadModelToGL(path,gl);
        this.startPos = startPos;
    }
    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        if(!picked){
            //drawUnpicked();
        }

        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void attack() {
        //shoot a bullet
    }

    @Override
    public void reload() {

    }

    @Override
    public void addAmmu() {

    }
}
