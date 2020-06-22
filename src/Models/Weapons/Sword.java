package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class Sword extends Weapon {
    private String path;
    private boolean attackMode = false;
    private float fps;
    private float duration;
    private float time;
    private float change;
    private float rChange;
    private long startTime;
    private long endTime;
    public Sword(String inPath){

        this.path = inPath;
        ammu = 0;
        weapontype = WeaponType.SWORD;
        duration = 1000f;
        fps = 60f;
        change = 0.5f/fps;
        rChange = 45f/fps;

    }
    public void create(ObjectLoader loader,GL2 gl){
        data = loader.LoadModelToGL(path,gl);
    }
    @Override
    public void draw(GL2 gl) {
        if(!picked){
            //drawUnpicked();
        }
        if(attackMode){
            endTime = System.currentTimeMillis();
            long milliseconds = endTime- startTime;
            change = (1f/2000f)*milliseconds;
            rChange = (90f/2000f)*milliseconds;
            for (ObjData obj:data) {
                moveSword();
            }
            time += milliseconds;
            /*try {

                Thread.sleep((long) (1/fps*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            if(time >= duration){
                attackMode = false;
                time = 0;
            }
            startTime = endTime;
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }

    }
    private void moveSword() {
        if(time <= duration/2){
            translate(-change,-change,0f);
            rotate(-rChange,'x');
            rotate(rChange,'y');
        }else{
            translate(change, change,0f);
            rotate(rChange,'x');
            rotate(-rChange,'y');
        }
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
}
