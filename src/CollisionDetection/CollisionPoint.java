package CollisionDetection;

import LinearMath.Vector;

public class CollisionPoint extends CollisionData {
    public Vector point;
    public CollisionPoint(Vector point){
        super();
        this.point = point;
    }

    @Override
    public void setStartPos(float[] startPos){
        double[] posD= {startPos[0],startPos[1],startPos[2],0};
        this.point = new Vector(posD,4);
    }

    @Override
    public void move(Vector move) {

    }
}
