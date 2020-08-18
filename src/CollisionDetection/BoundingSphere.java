/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package CollisionDetection;

import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

import javax.media.opengl.GL2;

public class BoundingSphere extends CollisionData {
    public Vector center;
    private Vector startCenter;
    public double radius;
    private double startRadius;
    private Matrix rotate;

    public BoundingSphere(Vector center, double radius) {
        this.type = CollisionType.BS;
        this.center = center;
        this.startCenter = center;
        this.radius = radius;
        this.startRadius = radius;
    }

    @Override
    public CollisionData clone() {
        return new BoundingSphere(center.clone(), radius);
    }

    public void init() {
        this.center = this.startCenter;
        this.radius = this.startRadius;
    }

    public void setStartPos(float[] startPos) {
        double[] posD = {startPos[0], startPos[1], startPos[2], 0};
        Vector startPosition = new Vector(posD, 4);
        this.center = this.center.Add(startPosition);
        this.startCenter = this.startCenter.Add(startPosition);

    }

    public void setScale(float[] sf) {
        this.radius *= sf[0];

    }

    public void setRotate(float[] angle) {
        Matrix rotateX = Transformation3D.rotate(angle[0], 'x');
        Matrix rotateY = Transformation3D.rotate(angle[1], 'y');
        Matrix rotateZ = Transformation3D.rotate(angle[2], 'z');
        this.rotate = rotateX.Multiply(rotateY.Multiply(rotateZ));

    }

    @Override
    public float[] getCenter() {
        return new float[]{(float) center.get(0), (float) center.get(1), (float) center.get(2)};
    }

    public void transAfterRotate(float[] trans) {
        double[] posAfterD = {trans[0], trans[1], trans[2], 1};
        Vector afterPos = new Vector(posAfterD, 4);
        Vector toTrans = afterPos.Multiply(rotate);
        this.center = this.center.Add(toTrans).fixLast();
    }
}
