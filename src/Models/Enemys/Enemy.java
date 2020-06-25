package Models.Enemys;

import Levels.Life;
import LinearMath.Vector;
import Models.Model;
import Models.Weapons.Weapon;

public abstract class Enemy extends Model {
    protected Life life;
    protected Weapon weapon;
    protected Vector charOrigin;

    public void updateOrigin(Vector origin){
        double[] fixGround = {0,5,0};
        this.charOrigin = origin.minus(new Vector(fixGround,3));

    }
    public void attack(){
        weapon.attack();
    }
    public void Hit(){
        life.reduceLife(10);
        //kill if life=0
    }
}
