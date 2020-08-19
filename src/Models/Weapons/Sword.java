/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionPoint;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Game.CoordinateSystem;
import Levels.Level;
import LinearMath.Vector;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Enemys.Enemy;

import javax.media.opengl.GL2;

public class Sword extends Weapon implements IDamage {
    private boolean attackMode = false;
    private float duration = 250f;
    private float time;
    private float rChange = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long milliseconds;
    private CollisionData collisionData;
    private CoordinateSystem coordinateSystem;
    private boolean attack = false;

    public Sword(String path, Level level, LoaderFactory factory){
        this.data = factory.create(path);
        this.translate(1f,0f,0f);
        this.scale(0.01f,0.01f,0.01f);
        this.rotate(45,'x');
        this.rotate(-45,'y');
        this.rotate(45,'z');
        this.level = level;
        double[] coll = {0,0,0,1};
        Vector collisionVector = new Vector(coll,4);
        this.collisionData = new CollisionPoint(collisionVector);
        this.weapontype = WeaponType.SWORD;
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
            float change = (0.5f / duration) * milliseconds;

            for (ObjData obj:data) {
                moveSword(gl);
            }
            time += milliseconds;
            if(time >= duration){
                attack = false;
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
            gl.glTranslatef(-0.5f,-0.5f,0.5f);
            gl.glRotatef(-rChange,1,0,0);
            gl.glRotatef(rChange,0,1,0);
            gl.glTranslatef(0.5f,0.5f,-0.5f);
        }else{
            if(!attack){
                attack = true;
                this.collisionData.init();
                double[] move = {coordinateSystem.getOrigin().get(0),
                        coordinateSystem.getOrigin().get(1) - 1.5,coordinateSystem.getOrigin().get(2),1};
                Vector toMove = new Vector(move,4);
                this.collisionData.move(toMove);
                float[] afterRot = {0,0,-5};
                this.collisionData.transAfterRotate(afterRot);
                this.level.checkCollision(this);
            }
            rChange -= (30f/duration)*milliseconds;
            gl.glTranslatef(-0.5f,-0.5f,0.5f);
            gl.glRotatef(-rChange,1,0,0);
            gl.glRotatef(rChange,0,1,0);
            gl.glTranslatef(0.5f,0.5f,-0.5f);
        }
    }
    @Override
    public void attack() {
        sound.play("SoundEffects/sword_swing.mp3");
        attackMode = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public int reload() {
        return 0;
    }

    @Override
    public void setAngle(float angle) {
        float[] rot = {0,angle,0};
        this.collisionData.setRotate(rot);
    }
    @Override
    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    @Override
    public void collide(ICollisionObj obj) {
        if(!(obj instanceof Character)) {
            if (obj instanceof Enemy) {
                int demage = 30;
                ((Enemy) obj).hit(demage);
            }
        }
    }
    public CollisionData getCollisionData(){
        return this.collisionData;
    }

}
