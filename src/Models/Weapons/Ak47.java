package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.IModel;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Ak47 extends Weapon{
    private String path;
    private GL2 gl;
    private ObjectLoader loader;
    private Magazine magazine;
    private List<Bullet> bulletsFired;
    public Ak47(String inPath) {
        this.path = inPath;
        ammu = 20;
        weapontype = WeaponType.GUN;
        this.bulletsFired = new ArrayList<>();
    }

    public void create(ObjectLoader loader, GL2 gl,float[] startPos){
        data = loader.LoadModelToGL(path,gl);
        this.startPos = startPos;
        this.magazine = new Magazine(loader, gl);
    }

    public void drawAllBulletsFired(GL2 gl) {
        for (Bullet bullet : bulletsFired) {
            bullet.draw(gl);
        }
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        if (bulletsFired.size() != 0) {
            drawAllBulletsFired(gl);
        }
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
        bulletsFired.add(magazine.shotBullet());
    }

    @Override
    public void reload() {

    }

    @Override
    public void addAmmu() {

    }
}
