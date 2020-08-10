package Game;

import CollisionDetection.AABB;
import CollisionDetection.CollisionData;
import CollisionDetection.CollisionDetector;
import CollisionDetection.ICollisionObj;
import Levels.Level;
import Levels.Life;
import Levels.Room;
import LinearMath.Vector;
import Models.IModel;
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
    //private float[] pos = {0, 0, -5};
    private Vector toMove;
    private boolean hit;
    private int lastKeyPressed = 0;
    private boolean collided = false;

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
    }

    private void createAABB() {
        float[] min ={-2.5f, 0, -2.5f};
        float[] max = {2.5f, 10, 2.5f};
        double[] minD = {min[0],min[1],min[2],1};
        LinearMath.Vector minVec = new LinearMath.Vector(minD,4);
        double[] maxD = {max[0],max[1],max[2],1};
        LinearMath.Vector maxVec = new Vector(maxD,4);
        collisionData = new AABB(minVec,maxVec);
        //collisionData.setStartPos(pos);
        //double[] move = {0, 0, 0, 0};
        //toMove = new Vector(move, 4);
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
        Vector currnetPos = cooSystem.getOrigin();
        double[] move = {currnetPos.get(0), currnetPos.get(1) - 5, currnetPos.get(2), 0};
        toMove = new Vector(move, 4);
        collisionData.move(toMove);
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
        //kill if life=0
    }

    public void walk(int keyPressed){
        checkCollision();
        float step = 0.5f;
        double angle = Math.PI/36;
        if (!(collided && keyPressed == lastKeyPressed)) {
            collided = false;
            switch (keyPressed) {
                case KeyEvent.VK_W:
                    cooSystem.moveStep('z', -step);
                    break;
                case KeyEvent.VK_A:
                    cooSystem.moveStep('x', -step);
                    break;
                case KeyEvent.VK_D:
                    cooSystem.moveStep('x', step);
                    break;
                case KeyEvent.VK_S:
                    cooSystem.moveStep('z', step);
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
        this.lastKeyPressed = keyPressed;
    }
    public void rotate(char axis, double angle){
        cooSystem.rotate(axis,angle);
        this.totalRotation = (this.totalRotation -angle);
    }

    public void AddWeapon(Weapon newWeapon) {
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
            ((Weapon) obj).weaponPicked();
            this.weapons.add((Weapon)obj);
        } else {
            collided = true;
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
