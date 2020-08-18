/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Game;

import CollisionDetection.AABB;
import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import Levels.Level;
import Levels.Life;
import LinearMath.Vector;
import Models.Objects.IObstacle;
import Models.Weapons.Ammunition;
import Models.Weapons.Weapon;
import Models.goods.IGood;
import SoundEffects.SoundEffect;

import javax.media.opengl.GL2;
import java.util.*;

public class Character implements ICollisionObj {
    private GL2 gl;
    private CoordinateSystem cooSystem;
    private Queue<Weapon> weapons;
    private Ammunition ammo;
    private Weapon currentWeapon;
    private double totalRotation = 0;
    private Life life;
    private Level currentLevel;
    private CollisionData collisionData;
    private boolean hit;
    private boolean collided = false;
    private Vector lastMove;
    private SoundEffect sound;
    private boolean alive = true;
    private long hitTime = 0;
    private int inRoomNumber = 0;


    public Character(Weapon startWeapon,CoordinateSystem cooSystem,GL2 gl) {
        this.cooSystem = cooSystem;
        weapons = new ArrayDeque<>();
        currentWeapon = startWeapon;
        currentWeapon.weaponPicked();
        currentWeapon.setCoordinateSystem(cooSystem);
        this.gl = gl;
        this.life = new Life();
        this.ammo = new Ammunition(50);
        createAABB();
        Vector currnetPos = cooSystem.getOrigin();
        double[] move = {currnetPos.get(0), currnetPos.get(1) - 5, currnetPos.get(2), 0};
        lastMove = new Vector(move,4);
        this.sound = new SoundEffect();
    }

    public int whichRoom() {
        return inRoomNumber;
    }

    boolean getAlive() {
        return alive;
    }

    private void createAABB() {
        float[] min ={-1.5f, 0, -1.5f};
        float[] max = {1.5f, 9, 1.5f};
        double[] minD = {min[0],min[1],min[2],1};
        LinearMath.Vector minVec = new LinearMath.Vector(minD,4);
        double[] maxD = {max[0],max[1],max[2],1};
        LinearMath.Vector maxVec = new Vector(maxD,4);
        collisionData = new AABB(minVec,maxVec);
    }

    void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    void changeWeapon(){
        if(weapons.isEmpty()){
            return;
        }
        weapons.add(currentWeapon);
        currentWeapon = weapons.poll();
    }
    public void draw(){
        findInWhichRoom();
        if(hit){
            drawHit();
            hitTime+= FPS.timePassed;
            if(hitTime > 500){
                hitTime = 0;
                hit = false;
            }
        }
        float deg = (float) Math.toDegrees(totalRotation);
        currentWeapon.setAngle(deg);
        ammo.draw();
        life.draw();
        gl.glPushMatrix();
        gl.glTranslatef((float) cooSystem.getOrigin().getVec()[0],(float) cooSystem.getOrigin().getVec()[1],
                (float) cooSystem.getOrigin().getVec()[2]);
        gl.glRotatef(deg,0,1,0);
        gl.glTranslatef(0,0,-1.8f);
        currentWeapon.draw(gl);
        gl.glPopMatrix();
    }

    void step(double[] movement){
        if(movement[0] != 0 || movement[2] != 0){
            double[] move = {movement[0]*FPS.fpsFactor,0,movement[2]*FPS.fpsFactor};

            cooSystem.moveStep(move);
            Vector currentPos = cooSystem.getOrigin();
            double[] moveA = {currentPos.get(0), currentPos.get(1) - 5, currentPos.get(2), 0};
            Vector toMove = new Vector(moveA, 4);
            collisionData.move(toMove);
            checkCollision();
            if(collided){
                collided = false;
            } else {
                lastMove = toMove;
            }
        }
    }
    public void attack(){
        currentWeapon.attack();
    }
    public void addAmmo(int quantity){
        ammo.addAmmo(quantity);
    }
    public void addLife(int quantity){
        life.addLife(quantity);
    }

    public void hit(int reduceLife){
        sound.play("resources/SoundEffects/Character_get_hit.wav");
        life.reduceLife(reduceLife);
        hit = true;
        if (life.getRemainLife() <= 0) {
            alive = false;
        }
    }


    public void rotate(char axis, double angle){
        cooSystem.rotate(axis,angle);
        this.totalRotation = (this.totalRotation -angle);
    }

    public void addWeapon(Weapon newWeapon) {
        newWeapon.weaponPicked();
        newWeapon.setCoordinateSystem(cooSystem);
        weapons.add(newWeapon);
    }

    public void reload() {
        if (ammo.getAmmo() != 0) {
            int ammoReloaded = currentWeapon.reload();
            if (ammo.getAmmo() < ammoReloaded) {
                ammo.reduceAmmo(ammo.getAmmo());
            } else {
                ammo.reduceAmmo(ammoReloaded);
            }
        }
    }
    @Override
    public void collide(ICollisionObj obj){
        if(obj instanceof IGood){
            ((IGood) obj).pick(this);
        }else if(obj instanceof Weapon){
            addWeapon((Weapon)obj);
        } else if(obj instanceof IObstacle) {
            collided = true;
            cooSystem.revertStep();
            collisionData.move(lastMove);
        }
    }

    @Override
    public CollisionData getCollisionData() {
        return collisionData;
    }

    private void checkCollision() {
        currentLevel.checkCollision(this);
    }
    private void drawHit(){
        gl.glDisable(GL2.GL_LIGHTING);
        float width = 4;
        float hieght = 10;

        gl.glTranslatef((float)this.cooSystem.getOrigin().getVec()[0],
                (float)this.cooSystem.getOrigin().getVec()[1],(float)this.cooSystem.getOrigin().getVec()[2]);
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(1,0,0,0.2f);
        //front
        gl.glVertex3f(-width,-hieght,-width);
        gl.glVertex3f(-width,hieght,-width);
        gl.glVertex3f(width,hieght,-width);
        gl.glVertex3f(width,-hieght,-width);
        //back
        gl.glVertex3f(-width,-hieght,width);
        gl.glVertex3f(-width,hieght,width);
        gl.glVertex3f(width,hieght,width);
        gl.glVertex3f(width,-hieght,width);
        //left
        gl.glVertex3f(-width,-hieght,-width);
        gl.glVertex3f(-width,hieght,-width);
        gl.glVertex3f(-width,hieght,width);
        gl.glVertex3f(-width,-hieght,width);
        //right
        gl.glVertex3f(width,-hieght,-width);
        gl.glVertex3f(width,hieght,-width);
        gl.glVertex3f(width,hieght,width);
        gl.glVertex3f(width,-hieght,width);
        gl.glEnd();

        gl.glEnable(GL2.GL_LIGHTING);
    }

    public int findInWhichRoom() {
        float[] pos = {(float)cooSystem.getOrigin().get(0),(float)cooSystem.getOrigin().get(1),(float)cooSystem.getOrigin().get(2)};
        inRoomNumber = this.currentLevel.getRoom(pos);
        return inRoomNumber;
    }
}
