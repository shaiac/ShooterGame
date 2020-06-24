package Game;

import CollisionDetection.PointPolygonIntersection;
import Levels.Level;
import Levels.Life;
import Models.Wall;
import Models.Weapons.Ammunition;
import Models.Weapons.Weapon;
import com.jogamp.newt.event.KeyEvent;

import javax.media.opengl.GL2;
import java.util.*;

public class Character {
    private int keyPressedWhileInter = -1;
    private boolean lastTimeInter = false;
    private GL2 gl;
    private CoordinateSystem cooSystem;
    private Queue<Weapon> weapons;
    private Ammunition ammu;
    private Weapon currentWeapon;
    private double totalRotation = 0;
    private float deg;
    private Life life;
    private Level currentLevel;
    private PointPolygonIntersection ppi;

    public Character(Weapon startWeapon,CoordinateSystem cooSystem,GL2 gl) {
        this.cooSystem = cooSystem;
        weapons = new ArrayDeque<>();
        currentWeapon = startWeapon;
        currentWeapon.weaponPicked();
        currentWeapon.setCoordinateSystem(cooSystem);
        this.gl = gl;
        this.life = new Life();
        this.ammu = new Ammunition(50);
        ppi = new PointPolygonIntersection();
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
        currentWeapon.setAngle(deg);
        ammu.draw();
        life.draw();
        gl.glPushMatrix();
        deg = (float) Math.toDegrees(totalRotation);
        gl.glTranslatef((float) cooSystem.getOrigin().getVec()[0],(float) cooSystem.getOrigin().getVec()[1],
                (float) cooSystem.getOrigin().getVec()[2]);
        gl.glRotatef(deg,0,1,0);
        gl.glTranslatef(0,0,-1.9f);
        currentWeapon.draw(gl);
        gl.glPopMatrix();
    }
    public void attack(){
        currentWeapon.attack();
    }
    public void addAmmu(int quantity){
        ammu.addAmmu(quantity);
    }

    private boolean checkIntersectionWithLevelWalls() {
        for (Wall wall : currentLevel.getLevelWalls()) {
            if (ppi.checkIntersection(cooSystem.getOrigin(), wall.getRectangle())) {
                return true;
            }
        }
        return false;
    }

    public void walk(int keyPressed){
//        if (lastTimeInter && keyPressed == keyPressedWhileInter) {
//            return;
//        } else if (!lastTimeInter) {
//            if (checkIntersectionWithLevelWalls()) {
//                keyPressedWhileInter = keyPressed;
//                lastTimeInter = true;
//                return;
//            }
//        }
//        lastTimeInter = false;
//        keyPressedWhileInter = -1;
        float step = 0.5f;
        double angle = 0.05;
        switch (keyPressed) {
            case KeyEvent.VK_UP:
                cooSystem.moveStep('z', -step);
                break;
            case KeyEvent.VK_LEFT:
                cooSystem.moveStep('x', -step);
                break;
            case KeyEvent.VK_RIGHT:
                cooSystem.moveStep('x', step);
                break;
            case KeyEvent.VK_DOWN:
                cooSystem.moveStep('z', step);
                break;
            case KeyEvent.VK_D:
                cooSystem.rotate('y', angle);
                this.totalRotation -= angle;
                break;
            case KeyEvent.VK_A:
                cooSystem.rotate('y', -angle);
                this.totalRotation +=angle;
                break;
            case KeyEvent.VK_R:
                reload();
                break;
        }
    }

    public void AddWeapon(Weapon newWeapon) {
        newWeapon.weaponPicked();
        newWeapon.setCoordinateSystem(cooSystem);
        weapons.add(newWeapon);
    }

    public void reload() {
        int ammuReloaded = currentWeapon.reload();
        if (ammu.getAmmu() < ammuReloaded) {
            ammu.reduceAmmu(ammu.getAmmu());
        } else {
            ammu.reduceAmmu(ammuReloaded);
        }
    }
}