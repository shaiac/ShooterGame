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

public class AABB extends CollisionData {
    public Vector min, max;
    public Vector startMin, startMax;
    public Vector startPos;
    public Matrix rotate;
    public Matrix scale;

    public AABB(Vector min, Vector max) {
        this.type = CollisionType.AABB;
        this.min = min;
        this.max = max;
        this.startMin = min;
        this.startMax = max;
    }

    @Override
    public void setStartPos(float[] startPos) {
        double[] posD = {startPos[0], startPos[1], startPos[2], 0};
        this.startPos = new Vector(posD, 4);
        this.min = this.min.Add(this.startPos);
        this.max = this.max.Add(this.startPos);
    }

    @Override
    public void setRotate(float[] angle) {
        double[] min = this.min.getVec();
        double[] max = this.max.getVec();
        double dx = max[0] - min[0];
        double dy = max[1] - min[1];
        double dz = max[2] - min[2];
        double[] minV = {0 - dx / 2, 0, 0 - dy / 2, 1};
        double[] maxV = {dx / 2, dz, dy / 2, 1};
        this.min = new Vector(minV, 4);
        this.max = new Vector(maxV, 4);
    }

    @Override
    public void setScale(float[] sf) {
        this.scale = Transformation3D.scale(sf[0], sf[1], sf[2]);

        this.min = this.min.Multiply(this.scale);
        this.max = this.max.Multiply(this.scale);
    }

    @Override
    public void move(Vector move) {
        this.min = this.startMin.Add(move);
        this.max = this.startMax.Add(move);
    }

    @Override
    public CollisionData clone() {
        return new AABB(min.clone(), max.clone());
    }

    @Override
    public float[] getCenter() {
        Vector sum = this.min.Add(this.max);
        return new float[]{(float) sum.get(0) / 2, (float) sum.get(1) / 2, (float) sum.get(2) / 2};
    }
}
