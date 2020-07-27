package CollisionDetection;

import LinearMath.Vector;

import javax.media.opengl.GL2;

public class AABB extends CollisionData {
    public Vector min,max;
    public AABB(Vector min, Vector max){
        super();
        this.min = min;
        this.max = max;
    }
    public void setStartPos(float[] startPos){

    }
    @Override
    public void draw(GL2 gl){

        //float[] minf = {(float)min.get(0)/10,(float)min.get(1)/10,(float)min.get(2)/10};
        //float[] maxf = {(float)max.get(0)/10,(float)max.get(1)/10,(float)max.get(2)/10};
        float[] minf = {2.409f,3.067f,1.838f};
        float[] maxf = {-0.831f,6.499f,-1.702f};
        gl.glBegin(GL2.GL_QUADS);
        //gl.glColor3f(1,1,1);
        //bottom
        gl.glVertex3f(minf[0],minf[1],minf[2]);
        gl.glVertex3f(minf[0],minf[1],maxf[2]);
        gl.glVertex3f(maxf[0],minf[1],maxf[2]);
        gl.glVertex3f(maxf[0],minf[1],minf[2]);

        //top
        gl.glVertex3f(minf[0],maxf[1],minf[2]);
        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
        gl.glVertex3f(maxf[0],maxf[1],minf[2]);

        //back
        gl.glVertex3f(minf[0],minf[1],minf[2]);
        gl.glVertex3f(minf[0],maxf[1],minf[2]);
        gl.glVertex3f(maxf[0],maxf[1],minf[2]);
        gl.glVertex3f(maxf[0],minf[1],minf[2]);

        //front
        gl.glVertex3f(minf[0],minf[1],maxf[2]);
        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
        gl.glVertex3f(maxf[0],minf[1],maxf[2]);

        //right
        gl.glVertex3f(maxf[0],minf[1],minf[2]);
        gl.glVertex3f(maxf[0],maxf[1],minf[2]);
        gl.glVertex3f(maxf[0],maxf[1],maxf[2]);
        gl.glVertex3f(maxf[0],minf[1],maxf[2]);

        //left
        gl.glVertex3f(minf[0],minf[1],minf[2]);
        gl.glVertex3f(minf[0],maxf[1],minf[2]);
        gl.glVertex3f(minf[0],maxf[1],maxf[2]);
        gl.glVertex3f(minf[0],minf[1],maxf[2]);
        gl.glEnd();
    }

}
