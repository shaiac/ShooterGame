import Models.Weapons.Weapon;
import com.jogamp.newt.event.KeyEvent;

import javax.media.opengl.GL2;
import java.util.*;

public class Character {
    private GL2 gl;
    private CoordinateSystem cooSystem;
    private Queue<Weapon> weapons;
    private int ammu;
    private Weapon currentWeapon;
    private double totalRotation = 0;

    public Character(Weapon startWeapon,CoordinateSystem cooSystem,GL2 gl) {
        this.cooSystem = cooSystem;
        weapons = new ArrayDeque<>();
        currentWeapon = startWeapon;
        this.gl = gl;
    }

    public void changeWeapon(){
        if(weapons.isEmpty()){
            return;
        }
        weapons.add(currentWeapon);
        currentWeapon = weapons.poll();

    }
    public void draw(){
        gl.glPushMatrix();
        gl.glRotatef((float) Math.toDegrees(totalRotation),0,1,0);
        gl.glTranslatef((float) cooSystem.getOrigin().getVec()[0],(float) cooSystem.getOrigin().getVec()[1],(float) cooSystem.getOrigin().getVec()[2]);
        gl.glTranslatef(0,0,-2);


        currentWeapon.draw(gl);
        gl.glPopMatrix();
    }
    public void attack(){
        currentWeapon.attack();

    }
    public void addAmmu(int quantity){
        ammu += quantity;
    }
    public void walk(int keyPressed){
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
                this.totalRotation += angle;
                break;
            case KeyEvent.VK_A:
                cooSystem.rotate('y', -angle);
                this.totalRotation -=angle;
                break;


        }
    }
}
