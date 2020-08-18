/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package CollisionDetection;

import LinearMath.Vector;

public abstract class CollisionData implements Cloneable{
    CollisionType type;
    public boolean checkCollision = true;
    public void setStartPos(float[] startPos){}
    public void setRotate(float[] angle){}
    public void setScale(float[] sf){}
    public void move(Vector move) {}
    public void transAfterRotate(float[] trans){}
    public float[] getCenter(){return new float[]{0, 0, 0};
    }
    public void init(){}
    @Override
    public CollisionData clone(){
        return this;
    }
}
