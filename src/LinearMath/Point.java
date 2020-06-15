package LinearMath;

/**
 * A geometry.Point class.
 */
public class Point {
    private double x;
    private double y;
    /**
     * Construct a point given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance method returns the distance of this point to the other given point.
     * @param otherPt the point that we want to check the distance with.
     * @return the distance between 2 points
     */
    public double distance(Point otherPt) {
        double dis = 0;
        //calculating the distance of the two points
        dis = ((this.x - otherPt.getX()) * (this.x - otherPt.getX()))
                + ((this.y - otherPt.getY()) * (this.y - otherPt.getY()));
        dis = Math.sqrt(dis);
        return dis;
    }

    /**
     * hashCode for the equals method.
     * @return 1
     */
    public int hashCode() {
        return 1;
    }

    /**
     * equals method returns true if the points are equal, false otherwise.
     * @param otherPt the point that we want to check if equals
     * @return true if the points are equals, false otherwise
     */
    public boolean equals(Point otherPt) {
        if (this.x == otherPt.getX() &&  this.y == otherPt.getY()) {
            return true;
        }
        return false;
    }

    /**
     * @return the x coordinate of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y coordinate of the point
     */
    public double getY() {
        return this.y;
    }
}