package CollisionDetection;

import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

import javax.media.opengl.GL2;

public class BoundingSphere extends CollisionData {
    public Vector center;
    public double radius;
    public BoundingSphere(Vector center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    public void setStartPos(float[] startPos){
        double[] posD= {startPos[0],startPos[1],startPos[2],0};
        Vector startPosition = new Vector(posD,4);
        this.center = this.center.Add(startPosition);

    }
    public void setScale(float[] sf){
        this.radius *= sf[0];
    }
    public void move(float[] step){
        double[] moveD = {step[0],step[1],step[2],0};
        Vector moveVec = new Vector(moveD,4);
        this.center = this.center.Add(moveVec);

    }
    public void draw(GL2 gl){
        float[] cen = {(float)this.center.getVec()[0],(float)this.center.getVec()[0],(float)this.center.getVec()[0]};
        float rad = (float) this.radius;
        gl.glBegin(GL2.GL_LINE);
        gl.glVertex3f(cen[0] - rad,cen[1],cen[2]);
        gl.glVertex3f(cen[0] + rad,cen[1],cen[2]);
        gl.glVertex3f(cen[0] ,cen[1],cen[2] - rad);
        gl.glVertex3f(cen[0] ,cen[1],cen[2] + rad);
        gl.glVertex3f(cen[0] ,cen[1] - rad,cen[2]);
        gl.glVertex3f(cen[0] ,cen[1] + rad,cen[2]);
        gl.glEnd();
    }


}
