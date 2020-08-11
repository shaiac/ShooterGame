package CollisionDetection;

import LinearMath.Vector;

import java.util.List;

public class CollisionDetector {
    public boolean CheckCollision(CollisionData obj1, CollisionData obj2){
        boolean collide = false;
        //AABB to AABB
        if(obj1.type == CollisionType.AABB && obj2.type == CollisionType.AABB){
            collide = AABBToAABB((AABB) obj1,(AABB) obj2);
        }
        // point with AABB
        else if(obj1.type == CollisionType.AABB && obj2.type == CollisionType.POINT){
            collide = pointToAABB((CollisionPoint) obj2,(AABB) obj1);
        }else if(obj2.type == CollisionType.AABB && obj1.type == CollisionType.POINT){
            collide = pointToAABB((CollisionPoint) obj1,(AABB) obj2);
        }
        //BS to AABB
        else if(obj1.type == CollisionType.AABB && obj2.type == CollisionType.BS){
            collide = BSToAABB((BoundingSphere) obj2,(AABB) obj1);
        }else if(obj2.type == CollisionType.AABB && obj1.type == CollisionType.BS){
            collide = BSToAABB((BoundingSphere) obj1,(AABB) obj2);
        }
        //AABB to Polygon
        else if(obj1.type == CollisionType.AABB && obj2.type == CollisionType.POLYGON) {
            collide = AABBToPolygon( (AABB) obj1, (CollisionPolygon) obj2);
        }
        //BS to polygon
        else if(obj1.type == CollisionType.POLYGON && obj2.type == CollisionType.BS){
            collide = BSToPolygon((BoundingSphere) obj2,(CollisionPolygon) obj1);
        }else if(obj2.type == CollisionType.POLYGON && obj1.type == CollisionType.BS){
            collide = BSToPolygon((BoundingSphere) obj1,(CollisionPolygon) obj2);
        }
        //Point to Polygon
        else if(obj1.type == CollisionType.POINT && obj2.type == CollisionType.POLYGON){
            collide = pointToPolygon((CollisionPoint) obj1,(CollisionPolygon) obj2);
        }else if(obj2.type == CollisionType.POINT && obj1.type == CollisionType.POLYGON){
            collide = pointToPolygon((CollisionPoint) obj2,(CollisionPolygon) obj1);
        }
        return collide;
    }

    private boolean AABBToPolygon(AABB obj1, CollisionPolygon obj2) {
        double[] point = obj1.min.Add(obj1.max).Multiply(0.5).getVec();
        List<Vector> rectangle = obj2.rect;
        double[] arrVec1 = {rectangle.get(0).get(0) - point[0], rectangle.get(0).get(1) - point[1],
                rectangle.get(0).get(2) - point[2]}; //to top left point
        Vector vec1 = new Vector(arrVec1, 3);
        double[] arrVec2 = {rectangle.get(1).get(0) - point[0], rectangle.get(1).get(1) - point[1],
                rectangle.get(1).get(2) - point[2]}; //to top right point
        Vector vec2 = new Vector(arrVec2, 3);
        double[] arrVec3 = {rectangle.get(2).get(0) - point[0], rectangle.get(2).get(1) - point[1],
                rectangle.get(2).get(2) - point[2]}; // to bottom right point
        Vector vec3 = new Vector(arrVec3, 3);
        double[] arrVec4 = {rectangle.get(3).get(0) - point[0], rectangle.get(3).get(1) - point[1],
                rectangle.get(3).get(2) - point[2]}; //to bottom left point
        Vector vec4 = new Vector(arrVec4, 3);
        double anglesSum = vec1.GetAngle(vec2) + vec2.GetAngle(vec3) + vec3.GetAngle(vec4) +
                vec4.GetAngle(vec1);
        return anglesSum >= 359;
    }

    private boolean AABBToAABB(AABB obj1, AABB obj2){
        double[] minA = obj1.min.getVec();
        double[] maxA = obj1.max.getVec();
        double[] minB = obj2.min.getVec();
        double[] maxB = obj2.max.getVec();

        return (minA[0] <= maxB[0] && maxA[0] >= minB[0]) &&
                (minA[1] <= maxB[1] && maxA[1] >= minB[1]) &&
                (minA[2] <= maxB[2] && maxA[2] >= minB[2]);
    }
    private boolean pointToAABB(CollisionPoint obj1, AABB obj2){
        double[] point = obj1.point.getVec();
        double[] min = obj2.min.getVec();
        double[] max = obj2.max.getVec();
        if(point[0]> max[0] || point[0]<min[0]){
            return false;
        }
        if(point[1]> max[1] || point[1]<min[1]){
            return false;
        }
        if(point[2]> max[2] || point[2]<min[2]){
            return false;
        }
        return true;
    }
    private boolean pointToPolygon(CollisionPoint obj1, CollisionPolygon obj2){
        double[] point = obj1.point.getVec();
        List<Vector> rectangle = obj2.rect;
        double[] arrVec1 = {rectangle.get(0).get(0) - point[0], rectangle.get(0).get(1) - point[1],
                rectangle.get(0).get(2) - point[2]}; //to top left point
        Vector vec1 = new Vector(arrVec1, 3);
        double[] arrVec2 = {rectangle.get(1).get(0) - point[0], rectangle.get(1).get(1) - point[1],
                rectangle.get(1).get(2) - point[2]}; //to top right point
        Vector vec2 = new Vector(arrVec2, 3);
        double[] arrVec3 = {rectangle.get(2).get(0) - point[0], rectangle.get(2).get(1) - point[1],
                rectangle.get(2).get(2) - point[2]}; // to bottom right point
        Vector vec3 = new Vector(arrVec3, 3);
        double[] arrVec4 = {rectangle.get(3).get(0) - point[0], rectangle.get(3).get(1) - point[1],
                rectangle.get(3).get(2) - point[2]}; //to bottom left point
        Vector vec4 = new Vector(arrVec4, 3);
        double anglesSum = vec1.GetAngle(vec2) + vec2.GetAngle(vec3) + vec3.GetAngle(vec4) +
                vec4.GetAngle(vec1);
        return anglesSum >= 359;
    }

    private boolean BSToPolygon(BoundingSphere obj1, CollisionPolygon obj2){
        double D = -(obj2.rect.get(0).Multiply(obj2.normalVec));
        double d = obj1.center.Multiply(obj2.normalVec) + D;
        return Math.abs(d)<obj1.radius;
    }

    private boolean BSToAABB(BoundingSphere obj1, AABB obj2){
        double[] center = obj1.center.getVec();
        double[] min = obj2.min.getVec();
        double[] max = obj2.max.getVec();
        double s,d=0;
        for(int i = 0; i<3;i++){
            if(center[i] < min[i]){
                s= center[i] - min[i];
                d+=s*s;
            } else if(center[i] > max[i]){
                s= center[i] - max[i];
                d+=s*s;
            }
        }
        double r2 =Math.pow(obj1.radius,2);
        return d<r2;
    }
}
