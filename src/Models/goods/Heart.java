/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.goods;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Heart extends Model implements IGood {
    private CollisionData collisionData;
    private int life = 20;


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

    @Override
    public void draw(GL2 gl) {
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
        sound.play("resources/SoundEffects/heal.wav");
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
