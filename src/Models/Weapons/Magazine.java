package Models.Weapons;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.IModel;

import javax.media.opengl.GL2;
import java.util.List;

public class Magazine {
    private int bullets;
    private ObjectLoader objectLoader;
    private GL2 gl;
    //private List<ObjData> objData;
    public Magazine(ObjectLoader objectLoader, GL2 gl) {
        //objData = objectLoader.LoadModelToGL("objects/Bullet/lowpolybullet.obj",gl);
        bullets = 5;
        this.objectLoader = objectLoader;
        this.gl = gl;
    }

    public Bullet shotBullet() {
        Bullet bullet = new Bullet("objects/Bullet/lowpolybullet.obj");
        float[] bulletlPos = {0,0f,0f};
        bullet.create(objectLoader,gl,bulletlPos);
        bullets--;
        return bullet;
    }

    public int getNumOFBullets() {
        return bullets;
    }
}
