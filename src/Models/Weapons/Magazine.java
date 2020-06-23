package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.IModel;

import javax.media.opengl.GL2;
import java.util.List;

public class Magazine {
    private int maxBulletsNum;
    private int bullets;
    private ObjectLoader objectLoader;
    private GL2 gl;
    private List<ObjData> objData;
    public Magazine(ObjectLoader objectLoader, GL2 gl, int maxBulletsNum) {
        objData = objectLoader.LoadModelToGL("objects/Bullet/lowpolybullet.obj",gl);
        bullets = maxBulletsNum;
        this.objectLoader = objectLoader;
        this.gl = gl;
        this.maxBulletsNum = maxBulletsNum;
    }

    public Bullet shotBullet() {
        Bullet bullet = new Bullet(objData);
        float[] bulletlPos = {0,0f,0f};
        bullet.create(objectLoader,gl,bulletlPos);
        bullets--;
        return bullet;
    }

    public int getNumOFBullets() {
        return bullets;
    }

    public int reload() {
        int reloaded = maxBulletsNum - bullets;
        bullets = maxBulletsNum;
        return reloaded;
    }

    public boolean isEmpty() {
       return (bullets == 0);
    }
}
