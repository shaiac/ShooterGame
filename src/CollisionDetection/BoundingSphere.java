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
    private float[] angle;
    private boolean first= true;
    private Matrix rotate;
    public BoundingSphere(Vector center, double radius) {
        this.type = CollisionType.BS;
        this.center = center;
        this.startCenter = center;
        this.radius = radius;
        this.startRadius = radius;
    }
    @Override
    public CollisionData clone(){
        return new BoundingSphere(center.clone(),radius);
    }
    public void init(){
        this.center = this.startCenter;
        this.radius = this.startRadius;
    }
    public void setStartPos(float[] startPos){
        double[] posD= {startPos[0],startPos[1],startPos[2],0};
        Vector startPosition = new Vector(posD,4);
        this.center = this.center.Add(startPosition);
        this.startCenter = this.startCenter.Add(startPosition);

    }
    public void setScale(float[] sf){
        this.radius *= sf[0];

    }
    public void setRotate(float[] angle){
        this.angle = angle;
        Matrix rotateX = Transformation3D.rotate(angle[0],'x');
        Matrix rotateY = Transformation3D.rotate(angle[1],'y');
        Matrix rotateZ = Transformation3D.rotate(angle[2],'z');
        this.rotate = rotateX.Multiply(rotateY.Multiply(rotateZ));

    }
    public void transAfterRotate(float[] trans){
        double[] posAfterD= {trans[0],trans[1],trans[2],1};
        Vector afterPos = new Vector(posAfterD,4);
        Vector toTrans = afterPos.Multiply(rotate);
        this.center = this.center.Add(toTrans).fixLast();
    }
    public void move(float[] step){
        double[] moveD = {step[0],step[1],step[2],0};
        Vector moveVec = new Vector(moveD,4);
        this.center = this.center.Add(moveVec);

    }
    public void draw(GL2 gl){
        float[] cen = {(float)this.center.getVec()[0],(float)this.center.getVec()[1],(float)this.center.getVec()[2]};
        float rad = (float) this.radius;
        //System.out.println("Center : " + cen[0] +" , " + cen[1] +" , " + cen[2] +" , ");
        //System.out.println("Radius : " + rad);
        /*gl.glBegin(GL2.GL_LINE);
        gl.glVertex3f(cen[0] - rad,cen[1],cen[2]);
        gl.glVertex3f(cen[0] + rad,cen[1],cen[2]);
        gl.glVertex3f(cen[0] ,cen[1],cen[2] - rad);
        gl.glVertex3f(cen[0] ,cen[1],cen[2] + rad);
        gl.glVertex3f(cen[0] ,cen[1] - rad,cen[2]);
        gl.glVertex3f(cen[0] ,cen[1] + rad,cen[2]);
        gl.glEnd();*/


        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor3f(1,1,1);
        //side 1
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);

        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);

        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);

        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);

        //side2
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);

        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);

        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);

        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);

        //side3
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        //side 4
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        //side 5
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        //side 6
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glEnd();

        /*gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);

        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);

        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);

        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);

        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] - rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] - rad,cen[1] - rad,cen[2] + rad);

        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] - rad);
        gl.glVertex3f(cen[0] + rad,cen[1] + rad,cen[2] + rad);
        gl.glVertex3f(cen[0] + rad,cen[1] - rad,cen[2] + rad);

        gl.glEnd();*/
    }


}
