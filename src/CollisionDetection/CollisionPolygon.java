/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package CollisionDetection;

import LinearMath.Vector;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class CollisionPolygon extends CollisionData {
    public List<Vector> rect;
    public Vector normalVec1;
    public Vector normalVec2;
    public CollisionPolygon(List<Vector> rect){
        this.type = CollisionType.POLYGON;
        this.rect = rect;
        this.normalVec1 = this.createNormal();
        double[] normalArr2 = {-normalVec1.get(0),-normalVec1.get(1),-normalVec1.get(2),1};
        this.normalVec2 = new Vector(normalArr2,4);
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
        Vector c = a.crossPruduct(b).normal();
        return c;
    }
}
