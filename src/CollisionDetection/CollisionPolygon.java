package CollisionDetection;

import LinearMath.Vector;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class CollisionPolygon extends CollisionData {
    public List<Vector> rect;
    public Vector normalVec;
    public CollisionPolygon(List<Vector> rect){
        this.type = CollisionType.POLYGON;
        this.rect = rect;
        this.normalVec = this.createNormal();
    }
    @Override
    public CollisionData clone(){
        List<Vector> newRect = new ArrayList<>();
        for (Vector vec:rect) {
            newRect.add(vec.clone());
        }
        return new CollisionPolygon(newRect);
    }
    private Vector createNormal(){
        Vector a = this.rect.get(1).minus(this.rect.get(0));
        Vector b = this.rect.get(2).minus(this.rect.get(0));
        Vector c = a.crossPruduct(b);
        return c;
    }

    @Override
    public void draw(GL2 gl) {
        float[] p1 = {(float)this.rect.get(0).getVec()[0],(float)this.rect.get(0).getVec()[1],(float)this.rect.get(0).getVec()[2]};
        float[] p2 = {(float)this.rect.get(1).getVec()[0],(float)this.rect.get(1).getVec()[1],(float)this.rect.get(1).getVec()[2]};
        float[] p3 = {(float)this.rect.get(2).getVec()[0],(float)this.rect.get(2).getVec()[1],(float)this.rect.get(2).getVec()[2]};
        float[] p4 = {(float)this.rect.get(3).getVec()[0],(float)this.rect.get(3).getVec()[1],(float)this.rect.get(3).getVec()[2]};

        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor3f(1,1,1);
        gl.glVertex3f(p1[0],p1[1],p1[2]);
        gl.glVertex3f(p2[0],p2[1],p2[2]);
        gl.glVertex3f(p2[0],p2[1],p2[2]);
        gl.glVertex3f(p3[0],p3[1],p3[2]);
        gl.glVertex3f(p3[0],p3[1],p3[2]);
        gl.glVertex3f(p4[0],p4[1],p4[2]);
        gl.glVertex3f(p4[0],p4[1],p4[2]);
        gl.glVertex3f(p1[0],p1[1],p1[2]);
        gl.glEnd();
    }
}
