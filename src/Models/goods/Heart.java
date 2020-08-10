package Models.goods;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Heart extends Model implements IGood {
    private String path;
    private CollisionData collisionData;
    private Level level;
    private int life = 50;

    public Heart(String path) {
        this.path = path;
    }
    public Heart(String path, Level level, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path,CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();

        float[] rotate = {0, 90, 0};
        this.collisionData.setRotate(rotate);
        float[] scale = {0.8f,0.8f,0.8f};
        this.collisionData.setScale(scale);
        this.level = level;

    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl, CollisionType.AABB);
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
        float[] rotate = {0, 90, 0};
        this.collisionData.setRotate(rotate);
        float[] scale = {0.8f,0.8f,0.8f};
        this.collisionData.setScale(scale);
    }

    @Override
    public void draw(GL2 gl) {
        collisionData.draw(gl);
        gl.glPushMatrix();
        gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
        gl.glScalef(0.8f,0.8f,0.8f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void collide(ICollisionObj obj) {

    }

    @Override
    public CollisionData getCollisionData() {
        return collisionData;
    }

    @Override
    public void pick(Character character) {
        character.addLife(life);
        this.level.removeModel(this);
    }
}
