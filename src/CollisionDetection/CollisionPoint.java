package CollisionDetection;

import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

import javax.media.opengl.GL2;

public class CollisionPoint extends CollisionData {
    public Vector point;
    public Vector startPoint;
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
    public void setRotate(float[] angle){
        //this.angle = angle;
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
    public void draw(GL2 gl){
//        float[] cen = {(float)point.getVec()[0],(float)point.getVec()[1],(float)point.getVec()[2]};
//        float rad = 0.1f;
//        gl.glBegin(GL2.GL_LINE_STRIP);
//        gl.glColor3f(1,1,1);
//        //side 1
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//
//        //side2
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//
//        //side3
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//        //side 4
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//        //side 5
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
//        //side 6
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
//        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
//        gl.glEnd();

    }
}
