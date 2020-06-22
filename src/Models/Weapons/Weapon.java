package Models.Weapons;

import Models.Model;
enum WeaponType {GUN,SWORD};
public abstract class Weapon extends Model {
    protected int ammu;
    protected boolean picked = false;
    protected WeaponType weapontype;
    protected boolean firstDraw = false;
    public abstract void attack();
    public abstract void reload();
    public abstract void addAmmu();
    public void drawUnpicked(){
        rotate(0.2f,'x');
        rotate(0.4f,'y');
        rotate(0.3f,'z');
    }
}
