package Tests;

import CollisionDetection.*;
import LinearMath.Vector;

import java.util.ArrayList;
import java.util.List;

public class CollisionTests {

    public void test(){
        CollisionDetector detector = new CollisionDetector();
        //AABB
        double[] min = {0,0,0,1};
        Vector Min = new Vector(min,4);
        double[] max = {1,1,1,1};
        Vector Max = new Vector(max,4);
        AABB aabb1 = new AABB(Min,Max);

        double[] min2 = {1,1,1,1};
        Vector Min2 = new Vector(min2,4);
        double[] max2 = {3,3,3,1};
        Vector Max2 = new Vector(max2,4);
        AABB aabb2 = new AABB(Min2,Max2);

        double[] min3 = {-3,-3,-3,1};
        Vector Min3 = new Vector(min3,4);
        double[] max3 = {-1,-2,-1,1};
        Vector Max3 = new Vector(max3,4);
        AABB aabb3 = new AABB(Min3,Max3);


        //Point
        double[] p = {0,0,0,1};
        Vector po = new Vector(p,4);
        CollisionPoint point1 = new CollisionPoint(po);

        double[] pf = {0,0,-0.1,1};
        Vector po2 = new Vector(pf,4);
        CollisionPoint point2 = new CollisionPoint(po2);

        double[] pf1 = {0.5,0.5,0,1};
        Vector po3 = new Vector(pf1,4);
        CollisionPoint point3 = new CollisionPoint(po3);

        //Polygon
        double[] p0 = {0,0,0,1};
        Vector vec0 = new Vector(p0,4);
        double[] p1 = {0,1,0,1};
        Vector vec1 = new Vector(p1,4);
        double[] p2 = {1,1,0,1};
        Vector vec2 = new Vector(p2,4);
        double[] p3 = {1,0,0,1};
        Vector vec3 = new Vector(p3,4);
        List<Vector> rect= new ArrayList<Vector>();
        rect.add(vec0);
        rect.add(vec1);
        rect.add(vec2);
        rect.add(vec3);
        CollisionPolygon polygon1 = new CollisionPolygon(rect);

        double[] p01 = {0,0,5,1};
        Vector vec01 = new Vector(p01,4);
        double[] p11 = {0,1,5,1};
        Vector vec11 = new Vector(p11,4);
        double[] p21 = {1,1,5,1};
        Vector vec21 = new Vector(p21,4);
        double[] p31 = {1,0,5,1};
        Vector vec31 = new Vector(p31,4);
        List<Vector> rect1= new ArrayList<Vector>();
        rect1.add(vec01);
        rect1.add(vec11);
        rect1.add(vec21);
        rect1.add(vec31);
        CollisionPolygon polygon2 = new CollisionPolygon(rect1);

        //BS
        double[] center = {0,0,0.5,1};
        Vector cen = new Vector(center,4);
        BoundingSphere bs1 = new BoundingSphere(cen,0.51);
        BoundingSphere bs2 = new BoundingSphere(cen,0.49);

        double[] center3 = {1,1,1.5,1};
        Vector cen3 = new Vector(center3,4);
        BoundingSphere bs3 = new BoundingSphere(cen3,0.51);
        BoundingSphere bs4 = new BoundingSphere(cen3,0.49);



        //Tests
        /*boolean pass = true;
        //BS to Poly true
        if(detector.CheckCollision(bs1,polygon1)){
            System.out.println("test 1 success");
        } else {
            System.out.println("**************** test 1 failed **************");
            pass = false;
        }
        //BS to Poly false
        if(!detector.CheckCollision(bs2,polygon1)){
            System.out.println("test 2 success");
        } else {
            System.out.println("**************** test 2 failed **************");
            pass = false;
        }
        //BS to Poly false
        if(!detector.CheckCollision(bs1,polygon2)){
            System.out.println("test 3 success");
        } else {
            System.out.println("**************** test 3 failed **************");
            pass = false;
        }

        //point to AABB
        if(detector.CheckCollision(point1,aabb1)){
            System.out.println("test 4 success");
        } else {
            System.out.println("**************** test 4 failed **************");
            pass = false;
        }
        if(!detector.CheckCollision(point2,aabb1)){
            System.out.println("test 5 success");
        } else {
            System.out.println("**************** test 5 failed **************");
            pass = false;
        }

        //point to polygon
        if(detector.CheckCollision(point3,polygon1)){
            System.out.println("test 6 success");
        } else {
            System.out.println("**************** test 6 failed **************");
            pass = false;
        }
        if(!detector.CheckCollision(point3,polygon2)){
            System.out.println("test 7 success");
        } else {
            System.out.println("**************** test 7 failed **************");
            pass = false;
        }


        //bs to aabb
        if(detector.CheckCollision(bs3,aabb1)){
            System.out.println("test 7 success");
        } else {
            System.out.println("**************** test 7 failed **************");
            pass = false;
        }
        if(!detector.CheckCollision(bs4,aabb1)){
            System.out.println("test 8 success");
        } else {
            System.out.println("**************** test 8 failed **************");
            pass = false;
        }

        //aabb to aabb
        if(detector.CheckCollision(aabb2,aabb1)){
            System.out.println("test 9 success");
        } else {
            System.out.println("**************** test 7 failed **************");
            pass = false;
        }
        if(!detector.CheckCollision(aabb3,aabb1)){
            System.out.println("test 10 success");
        } else {
            System.out.println("**************** test 8 failed **************");
            pass = false;
        }

        if(pass){
            System.out.println("All Test Pass");
        } else{
            System.out.println("FAILED");
        }*/
    }

}
