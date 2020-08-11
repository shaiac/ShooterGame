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
import com.jogamp.newt.event.KeyEvent;

import javax.media.opengl.GL2;
import java.util.*;

public class Character implements ICollisionObj {
    private GL2 gl;
    private CoordinateSystem cooSystem;
    private Queue<Weapon> weapons;
    private Ammunition ammo;
    private Weapon currentWeapon;
    private double totalRotation = 0;
    private float deg;
    private Life life;
    private Level currentLevel;
    private CollisionData collisionData;
    private Vector toMove;
    private boolean hit;
    private boolean collided = false;
    private Vector lastMove;

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

    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    public void changeWeapon(){
        if(weapons.isEmpty()){
            return;
        }
        weapons.add(currentWeapon);
        currentWeapon = weapons.poll();
    }
    public void draw(){
        collisionData.draw(gl);
        deg = (float) Math.toDegrees(totalRotation);
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
        life.reduceLife(reduceLife);
        hit = true;
        //TODO kill when life = 0
    }

    public void walk(int keyPressed){
        float step = 0.5f;
        double angle = Math.PI/36;

        Vector currentPos;
        switch (keyPressed) {
            case KeyEvent.VK_W:
                cooSystem.moveStep('z', -step);
                currentPos = cooSystem.getOrigin();
                double[] moveW = {currentPos.get(0), currentPos.get(1) - 5, currentPos.get(2), 0};
                toMove = new Vector(moveW, 4);
                collisionData.move(toMove);
                checkCollision();
                if(collided){
                    collided = false;
                } else {
                    lastMove = toMove;
                }
                break;
            case KeyEvent.VK_A:
                cooSystem.moveStep('x', -step);
                currentPos = cooSystem.getOrigin();
                double[] moveA = {currentPos.get(0), currentPos.get(1) - 5, currentPos.get(2), 0};
                toMove = new Vector(moveA, 4);
                collisionData.move(toMove);
                checkCollision();
                if(collided){
                    collided = false;
                } else {
                    lastMove = toMove;
                }
                break;
            case KeyEvent.VK_D:
                cooSystem.moveStep('x', step);
                currentPos = cooSystem.getOrigin();
                double[] moveD = {currentPos.get(0), currentPos.get(1) - 5, currentPos.get(2), 0};
                toMove = new Vector(moveD, 4);
                collisionData.move(toMove);
                checkCollision();
                if(collided){
                    collided = false;
                } else {
                    lastMove = toMove;
                }
                break;
            case KeyEvent.VK_S:
                cooSystem.moveStep('z', step);
                currentPos = cooSystem.getOrigin();
                double[] moveZ = {currentPos.get(0), currentPos.get(1) - 5, currentPos.get(2), 0};
                toMove = new Vector(moveZ, 4);
                collisionData.move(toMove);
                checkCollision();
                if(collided){
                    collided = false;
                } else {
                    lastMove = toMove;
                }
                break;
            case KeyEvent.VK_RIGHT:
                cooSystem.rotate('y', angle);
                this.totalRotation -= angle;
                break;
            case KeyEvent.VK_LEFT:
                cooSystem.rotate('y', -angle);
                this.totalRotation += angle;
                break;
            case KeyEvent.VK_R:
                reload();
                break;
            case KeyEvent.VK_L:
                currentLevel.levelEnded();
                break;
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
}
