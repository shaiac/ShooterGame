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
    public void Hit(int reduceLife){
        life.reduceLife(reduceLife);
        hit = true;
        //kill if life=0
    }
    public void dead(){
    }
}
