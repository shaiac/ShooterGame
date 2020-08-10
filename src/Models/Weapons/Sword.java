package Models.Weapons;

import CollisionDetection.ICollisionObj;
import Game.CoordinateSystem;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Sword extends Weapon implements IDamage {
    private List<float[]> colPoints= new ArrayList<>();
    private String path;
    private boolean attackMode = false;
    private float duration = 250f;
    private float time;
    private float change = 0;
    private float rChange = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long milliseconds;
    public Sword(String inPath){

        this.path = inPath;
        ammu = 0;
        weapontype = WeaponType.SWORD;
    }
    public Sword(String path, LoaderFactory factory){
        this.data = factory.create(path);
        this.translate(1f,0f,0f);
        this.scale(0.01f,0.01f,0.01f);
        this.rotate(45,'x');
        this.rotate(-45,'y');
        this.rotate(45,'z');
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
    }
    public void create(ObjectLoader loader,GL2 gl,float[] startPos){
        data = loader.LoadModelToGL(path,gl);
        this.translate(1f,0f,0f);
        this.scale(0.01f,0.01f,0.01f);
        this.rotate(45,'x');
        this.rotate(-45,'y');
        this.rotate(45,'z');
        this.startPos = startPos;

    }
    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        if(!picked){
            gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
            drawUnpicked(gl);
        }
        if(attackMode){
            endTime = System.currentTimeMillis();
            milliseconds = endTime- startTime;
            change = (0.5f/duration)*milliseconds;

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

        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();

    }
    private void moveSword(GL2 gl) {
        if(time <= duration/2){
            rChange += (30f/duration)*milliseconds;
            //translate(-change,-change,0f);
            gl.glTranslatef(-0.5f,-0.5f,0.5f);
            gl.glRotatef(-rChange,1,0,0);
            gl.glRotatef(rChange,0,1,0);
            gl.glTranslatef(0.5f,0.5f,-0.5f);
            //rotate(-rChange,'x');
            //rotate(rChange,'y');
        }else{
            rChange -= (30f/duration)*milliseconds;
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
    public int reload() {
        return 0;
    }

    @Override
    public void setAngle(float angle) {

    }
    @Override
    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {

    }

    @Override
    public void collide(ICollisionObj obj) {

    }
}
