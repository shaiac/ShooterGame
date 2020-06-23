package Models.Weapons;

import Models.Model;

import javax.media.opengl.GL2;

enum WeaponType {GUN,SWORD};
public abstract class Weapon extends Model {
    protected int ammu;
    protected boolean picked = false;
    protected WeaponType weapontype;
    protected float[] rotate = {0,0,0};
    protected boolean firstDraw = false;
    public abstract void attack();
    public abstract void reload();
    public abstract void addAmmu();

    public void weaponPicked() {
        this.picked = true;
    }

    public void drawUnpicked(GL2 gl){
        rotate[2] += 0.3f;
        gl.glRotatef(rotate[2],1,0,1);

        /*rotate(0.2f,'x');
        rotate(0.4f,'y');
        rotate(0.3f,'z');*/
    }
}
