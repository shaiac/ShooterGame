/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import Game.CoordinateSystem;
import Models.Model;
import SoundEffects.SoundEffect;

import javax.media.opengl.GL2;

enum WeaponType {GUN,SWORD};
public abstract class Weapon extends Model implements ICollisionObj {
    protected boolean picked = false;
    protected WeaponType weapontype;
    protected float[] rotate = {0,0,0};
    protected float[] pos ={0,0,0};
    SoundEffect sound;
    public Weapon() {
        sound = new SoundEffect();
    }
    public abstract void attack();
    public abstract int reload();
    public abstract void setAngle(float angle);

    public abstract void setCoordinateSystem(CoordinateSystem coordinateSystem);

    public void weaponPicked() {
        this.picked = true;
        if(weapontype != WeaponType.SWORD){
            this.level.removeModel(this);
        }

    }

    public void drawUnpicked(GL2 gl){
        rotate[2] += 0.5f;
        gl.glRotatef(rotate[2],0.4f,0f,0f);
    }
    public void setPos(float[] pos){
        this.pos = pos;
    }

    @Override
    public void collide(ICollisionObj obj){}
    @Override
    public CollisionData getCollisionData(){
        return null;
    }
}
