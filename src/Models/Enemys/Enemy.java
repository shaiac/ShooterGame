package Models.Enemys;

import Levels.Life;
import Models.Model;
import Models.Weapons.Weapon;

public abstract class Enemy extends Model {
    protected Life life;
    protected Weapon weapon;
    public void attack(){
        weapon.attack();
    }
    public void Hit(){
        life.reduceLife(10);
        //kill if life=0
    }
}
