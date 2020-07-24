package CollisionDetection;

import LinearMath.Vector;

public class AABB extends CollisionData {
    public Vector min,max;
    public AABB(Vector min, Vector max){
        super();
        this.min = min;
        this.max = max;
    }

}
