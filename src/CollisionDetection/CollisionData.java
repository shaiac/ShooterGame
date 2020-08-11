package CollisionDetection;

import LinearMath.Vector;

import javax.media.opengl.GL2;
import java.lang.reflect.Type;


;
public abstract class CollisionData implements Cloneable{
    public CollisionType type;
    public boolean checkCollision = true;

    public void draw(GL2 gl){

    }
    public void setStartPos(float[] startPos){}
    public void setRotate(float[] angle){}
    public void setScale(float[] sf){}
    public void move(float[] move) {}
    public void move(Vector move) {}
    public void setPosAfterRot(float[] pos,float[] posAfterRot){}
    public void transAfterRotate(float[] trans){}
    public void init(){}
    @Override
    public CollisionData clone(){
        return this;
    }
}
