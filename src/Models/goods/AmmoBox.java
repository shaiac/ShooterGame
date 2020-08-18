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

public class AmmoBox extends Model implements IGood {
    private CollisionData collisionData;
    private int ammu = 24;

    public AmmoBox(String path, Level level, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path, CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        float[] scale = {0.2f,0.2f,0.2f};
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
        gl.glScalef(0.2f,0.2f,0.2f);
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
    }

    @Override
    public void collide(ICollisionObj obj) {
        sound.play("resources/SoundEffects/shotgun_reload.wav");
    }

    @Override
    public CollisionData getCollisionData() {
        return collisionData;
    }

    @Override
    public void pick(Character character) {
        character.addAmmo(ammu);
        level.removeModel(this);
    }
}
