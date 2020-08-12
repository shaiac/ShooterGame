/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Enemys;

import Levels.Life;
import LinearMath.Vector;
import Models.Model;
import Models.Weapons.Weapon;
import SoundEffects.SoundEffect;

public abstract class Enemy extends Model {
    Life life;
    protected Weapon weapon = null;
    Vector charOrigin;
    boolean hit;
    long startHit = 0;
    private SoundEffect sound = new SoundEffect();

    public void addWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    public void updateOrigin(Vector origin){
        double[] fixGround = {0,5,0};
        this.charOrigin = origin.minus(new Vector(fixGround,3));
    }
    public void attack(){
        weapon.attack();
    }
    public void hit(int reduceLife){
        life.reduceLife(reduceLife);
        hit = true;
        startHit = System.currentTimeMillis();
        if (life.getRemainLife() <= 0) {
            dead();
        }
    }
    private void dead(){
        sound.play("resources/SoundEffects/enemyDie.mp3");
        level.removeModel(this);
    }
}
