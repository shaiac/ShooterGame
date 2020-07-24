package CollisionDetection;

import LinearMath.Vector;

import java.util.List;

public class CollisionPolygon extends CollisionData {
    public List<Vector> rect;
    public Vector normalVec;
    public CollisionPolygon(List<Vector> rect){
        super();
        this.rect = rect;
        this.normalVec = this.createNormal();
    }
    private Vector createNormal(){
        Vector a = this.rect.get(1).minus(this.rect.get(0));
        Vector b = this.rect.get(2).minus(this.rect.get(0));
        Vector c = a.crossPruduct(b);
        return c;
    }
}
