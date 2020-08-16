package CollisionDetection;

import LinearMath.Matrix;
import LinearMath.Transformation3D;
import LinearMath.Vector;

import javax.media.opengl.GL2;

public class AABB extends CollisionData {
    public Vector min,max;
    public Vector startMin, startMax;
    public Vector startPos;
    public Matrix rotate;
    public Matrix scale;
    private boolean flag = true;
    public AABB(Vector min, Vector max){
        this.type = CollisionType.AABB;
        this.min = min;
        this.max = max;
        this.startMin = min;
        this.startMax = max;
    }
    @Override
    public void setStartPos(float[] startPos){
       double[] posD= {startPos[0],startPos[1],startPos[2],0};
       this.startPos = new Vector(posD,4);
        this.min = this.min.Add(this.startPos);
        this.max = this.max.Add(this.startPos);
    }
    @Override
    public void setRotate(float[] angle){
        double[] min = this.min.getVec();
        double[] max = this.max.getVec();
        double dx = max[0]- min[0];
        double dy = max[1] - min[1];
        double dz = max[2] - min[2];
        double[] minV = {0-dx/2,0,0-dy/2,1};
        double[] maxV = {dx/2,dz,dy/2,1};
        this.min = new Vector(minV,4);
        this.max = new Vector(maxV,4);

        /*Matrix rotateX = Transformation3D.rotate(angle[0],'x');
        Matrix rotateY = Transformation3D.rotate(angle[1],'y');
        Matrix rotateZ = Transformation3D.rotate(angle[2],'z');
        this.rotate = rotateX.Multiply(rotateY.Multiply(rotateZ));

        this.min = this.min.Multiply(this.rotate);
        this.max = this.max.Multiply(this.rotate);*/
    }
    @Override
    public void setScale(float[] sf){
        this.scale = Transformation3D.scale(sf[0],sf[1],sf[2]);

        this.min = this.min.Multiply(this.scale);
        this.max = this.max.Multiply(this.scale);
    }
    @Override
    public void draw(GL2 gl){
        /*if (flag) {
            if(this.rotate != null){
                //rotate
                this.min = this.min.Multiply(this.rotate);
                this.max = this.max.Multiply(this.rotate);
            }
            if(this.scale != null){
                //scale
                this.min = this.min.Multiply(this.scale);
                this.max = this.max.Multiply(this.scale);
            }
            if(this.startPos != null){
                //translate
                this.min = this.min.Add(this.startPos);
                this.max = this.max.Add(this.startPos);
            }
            flag = false;
        }*/

//        float[] minf = {(float)min.get(0),(float)min.get(1),(float)min.get(2)};
//        float[] maxf = {(float)max.get(0),(float)max.get(1),(float)max.get(2)};
//        //float[] minf = {2.409f,3.067f,1.838f};
//        //float[] maxf = {-0.831f,6.499f,-1.702f};
//        gl.glBegin(GL2.GL_LINE_STRIP);
//        gl.glColor3f(1,1,1);
//        //bottom
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//        //gl.glVertex3f(minf[0],minf[1],maxf[2]);
//        gl.glVertex3f(minf[0],minf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],minf[1],minf[2]);
//        gl.glVertex3f(maxf[0],minf[1],minf[2]);
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//
//        //top
//        gl.glVertex3f(minf[0],maxf[1],minf[2]);
//        //gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        gl.glVertex3f(minf[0],maxf[1],minf[2]);
//
//        //back
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//        //gl.glVertex3f(minf[0],maxf[1],minf[2]);
//        gl.glVertex3f(minf[0],maxf[1],minf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        //gl.glVertex3f(maxf[0],minf[1],minf[2]);
//        gl.glVertex3f(maxf[0],minf[1],minf[2]);
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//
//        //front
//        gl.glVertex3f(minf[0],minf[1],maxf[2]);
//        //gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        gl.glVertex3f(minf[0],minf[1],maxf[2]);
//
//        //right
//        gl.glVertex3f(maxf[0],minf[1],minf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],minf[2]);
//        //gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],minf[1],maxf[2]);
//        gl.glVertex3f(maxf[0],minf[1],minf[2]);
//
//        //left
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//        //gl.glVertex3f(minf[0],maxf[1],minf[2]);
//        gl.glVertex3f(minf[0],maxf[1],minf[2]);
//        //gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
//        //gl.glVertex3f(minf[0],minf[1],maxf[2]);
//        gl.glVertex3f(minf[0],minf[1],maxf[2]);
//        gl.glVertex3f(minf[0],minf[1],minf[2]);
//        gl.glEnd();
    }

    @Override
    public void move(Vector move) {
        this.min = this.startMin.Add(move);
        this.max = this.startMax.Add(move);
    }
    @Override
    public CollisionData clone(){
        return new AABB(min.clone(),max.clone());
    }


}
