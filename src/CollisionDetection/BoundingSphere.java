package CollisionDetection;

import LinearMath.Vector;

public class BoundingSphere extends CollisionData {
    public Vector center;
    public double radius;
    public BoundingSphere(Vector center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }
    /*//Checking collision of Point and BS
    public boolean checkIntersectionWithPoint(Vector point) {
        double distance = Math.pow((center.get(0) - point.get(0)), 2) + Math.pow((center.get(1) - point.get(1)), 2) +
                Math.pow((center.get(2) - point.get(2)), 2);
        return distance < Math.pow(radius, 2);
    }

    public boolean checkIntersectionWithBS(BoundingSphere bs) {
        double distance = Math.pow((center.get(0) - bs.getCenter().get(0)), 2) + Math.pow((center.get(1) -
                getCenter().get(1)), 2) + Math.pow((center.get(2) - getCenter().get(2)), 2);
        return distance < (Math.pow(radius, 2) + Math.pow(bs.getRadius(), 2));
    }*/

//    public boolean checkIntersectionWithPlane(BoundingSphere bs) {
//
//    }

}
