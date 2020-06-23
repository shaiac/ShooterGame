package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Sword extends Weapon {
    private List<float[]> colPoints= new ArrayList<>();
    private String path;
    private boolean attackMode = false;
    private float fps;
    private float duration;
    private float time;
    private float change;
    private float rChange;
    private long startTime;
    private long endTime;
    private long milliseconds;
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
            milliseconds = endTime- startTime;
            change = (1f/2000f)*milliseconds;

            for (ObjData obj:data) {
                moveSword(gl);
            }
            time += milliseconds;
            if(time >= duration){
                attackMode = false;
                time = 0;
                rChange = 0f;
            }
            startTime = endTime;
        }
        gl.glPushMatrix();
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();

    }
    private void moveSword(GL2 gl) {
        if(time <= duration/2){
            rChange += (60f/2000f)*milliseconds;
            //translate(-change,-change,0f);
            gl.glTranslatef(-0.5f,-0.5f,0.5f);
            gl.glRotatef(-rChange,1,0,0);
            gl.glRotatef(rChange,0,1,0);
            gl.glTranslatef(0.5f,0.5f,-0.5f);
            //rotate(-rChange,'x');
            //rotate(rChange,'y');
        }else{
            rChange -= (60f/2000f)*milliseconds;
            //translate(change, change,0f);
            gl.glTranslatef(-0.5f,-0.5f,0.5f);
            gl.glRotatef(-rChange,1,0,0);
            gl.glRotatef(rChange,0,1,0);
            gl.glTranslatef(0.5f,0.5f,-0.5f);
            //rotate(rChange,'x');
            //rotate(-rChange,'y');
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