package CollisionDetection;

import LinearMath.Vector;

import javax.media.opengl.GL2;
import java.lang.reflect.Type;

public abstract class CollisionData{
    public String type;
    public CollisionData() {
        type = getClass().getTypeName();
    }
    public void draw(GL2 gl){

    }
    public void setStartPos(float[] startPos){}
    public void setRotate(float[] angle){}
    public void setScale(float[] sf){}
    public void setMinMax(Vector move) {}
}
