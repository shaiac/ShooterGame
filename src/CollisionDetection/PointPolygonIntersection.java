package CollisionDetection;

import LinearMath.Vector;

import java.util.List;

public class PointPolygonIntersection {

    public boolean checkIntersection(Vector point, List<Vector> rectangle) {
        double[] arrVec1 = {rectangle.get(0).get(0) - point.get(0), rectangle.get(0).get(1) - point.get(1),
        rectangle.get(0).get(2) - point.get(2)}; //to top left point
        Vector vec1 = new Vector(arrVec1, 3);
        double[] arrVec2 = {rectangle.get(1).get(0) - point.get(0), rectangle.get(1).get(1) - point.get(1),
                rectangle.get(1).get(2) - point.get(2)}; //to top right point
        Vector vec2 = new Vector(arrVec2, 3);
        double[] arrVec3 = {rectangle.get(2).get(0) - point.get(0), rectangle.get(2).get(1) - point.get(1),
                rectangle.get(2).get(2) - point.get(2)}; // to bottom right point
        Vector vec3 = new Vector(arrVec3, 3);
        double[] arrVec4 = {rectangle.get(3).get(0) - point.get(0), rectangle.get(3).get(1) - point.get(1),
                rectangle.get(3).get(2) - point.get(2)}; //to bottom left point
        Vector vec4 = new Vector(arrVec4, 3);
        double anglesSum = vec1.GetAngle(vec2) + vec2.GetAngle(vec3) + vec3.GetAngle(vec4) +
                vec4.GetAngle(vec1);
        return anglesSum >= 359;
    }

    /**public boolean checkIntersection(Point point, List<Point> rectangle) {
        Line line1 = new Line(point, rectangle.get(0)); //to top left point
        Line line2 = new Line(point, rectangle.get(1)); //to top right point
        Line line3 = new Line(point, rectangle.get(2)); //to bottom right
        Line line4 = new Line(point, rectangle.get(3)); //to bottom left
        double anglesSum = line1.getAngleBetweenLines(line2) + line2.getAngleBetweenLines(line3) +
                line3.getAngleBetweenLines(line4) + line4.getAngleBetweenLines(line1);
        return anglesSum >= 350;
    }*/
}
