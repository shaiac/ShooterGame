package CollisionDetection;

import LinearMath.Vector;

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
}
