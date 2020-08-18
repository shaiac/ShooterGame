/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package CollisionDetection;

import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

public class CollisionPoint extends CollisionData {
    Vector point;
    private Vector startPoint;
    public Matrix rotate;
    public CollisionPoint(Vector point){
        this.type = CollisionType.POINT;
        this.point = point;
        this.startPoint = point;
    }
    @Override
    public CollisionData clone(){
        return new CollisionPoint(point.clone());
    }
    public void init(){
        this.point = this.startPoint;
    }

    @Override
    public void setStartPos(float[] startPos){
        double[] posD= {startPos[0],startPos[1],startPos[2],0};
        Vector startPosition = new Vector(posD,4);
        this.point = this.point.Add(startPosition);
        this.startPoint = this.startPoint.Add(startPosition);
    }
    @Override
    public float[] getCenter(){
        return new float[] {(float)point.get(0),(float)point.get(1),(float)point.get(2)};
    }
    public void setRotate(float[] angle){
        Matrix rotateX = Transformation3D.rotate(angle[0],'x');
        Matrix rotateY = Transformation3D.rotate(angle[1],'y');
        Matrix rotateZ = Transformation3D.rotate(angle[2],'z');
        this.rotate = rotateX.Multiply(rotateY.Multiply(rotateZ));

    }
    public void transAfterRotate(float[] trans){
        double[] posAfterD= {trans[0],trans[1],trans[2],1};
        Vector afterPos = new Vector(posAfterD,4);
        Vector toTrans = afterPos.Multiply(rotate);
        this.point = this.point.Add(toTrans).fixLast();
    }

    @Override
    public void move(Vector move) {
        this.point = this.point.Add(move);
    }
}
