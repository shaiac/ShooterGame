package Models.Enemys;

import Levels.Life;
import LinearMath.Vector;
import Models.Model;
import Models.Weapons.Weapon;

public abstract class Enemy extends Model {
    protected Life life;
    protected Weapon weapon = null;
    protected Vector charOrigin;
    protected boolean hit;
    protected long startHit = 0;
    protected long stopHit = 0;
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
        level.removeModel(this);
    }
}
