package Models.Weapons;

import CollisionDetection.CollisionData;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.IModel;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Magazine {
    private int maxBulletsNum;
    private int bullets;
    private ObjectLoader objectLoader;
    private GL2 gl;
    private List<ObjData> objData;
    private LoaderFactory factory;
    public Magazine(ObjectLoader objectLoader, GL2 gl, int maxBulletsNum) {
        objData = objectLoader.LoadModelToGL("objects/Bullet/lowpolybullet.obj",gl);
        bullets = maxBulletsNum;
        this.objectLoader = objectLoader;
        this.gl = gl;
        this.maxBulletsNum = maxBulletsNum;
    }
    public Magazine(LoaderFactory factory,int maxBulletsNum){
        this.factory = factory;
        this.bullets = maxBulletsNum;
        this.maxBulletsNum = maxBulletsNum;
    }
    public Bullet shotBullet(float[] pos1, float[] pos2, String bulletPath, Level level){
        Bullet bullet = new Bullet(bulletPath,factory,level);
        bullet.setStartPos(pos1);
        bullet.setBulletPos(pos2);
        bullets--;
        return  bullet;
    }
    public Bullet shotBullet(float[] pos1,float[] pos2) {
        Bullet bullet = new Bullet(objData);
        //float[] bulletlPos = {0,5f,0f};
        bullet.create(objectLoader,gl,pos1);
        bullet.setBulletPos(pos2);
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
