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
    private boolean attackMode = false;
    private float duration = 500f;
    private float time;
    private float change = 0;
    private float rChange = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long milliseconds;
    private boolean shot = false;
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
        gl.glTranslatef(0, -0.5f ,-2f);
        if (bulletsFired.size() != 0) {
            drawAllBulletsFired(gl);
        }
        gl.glPopMatrix();
        gl.glPushMatrix();
        if(!picked){
            //drawUnpicked();
        }
        if(attackMode){
            endTime = System.currentTimeMillis();
            milliseconds = endTime- startTime;
            change = (1f/duration)*milliseconds;

            for (ObjData obj:data) {
                moveGun(gl);
            }
            time += milliseconds;
            if(time >= duration){
                shot = false;
                attackMode = false;
                time = 0;
                rChange = 0f;
            }
            startTime = endTime;
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void attack() {
        attackMode = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void reload() {

    }

    @Override
    public void addAmmu() {

    }

    private void moveGun(GL2 gl) {
        if(time <= duration/2){
            rChange -= (5f/duration)*milliseconds;
            gl.glRotatef(rChange,0,1,0);
            //gl.glTranslatef(0.5f,0.5f,-0.5f);
        }else{
            if (!shot) {
                shot = true;
                bulletsFired.add(magazine.shotBullet());
            }
            rChange += (5f/duration)*milliseconds;
            gl.glRotatef(rChange,0,1,0);

        }
    }
}
