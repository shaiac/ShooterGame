package LinearMath;

public class Line {
    /**
     * A geometry.Line class.
     */
    private Point start;
    private Point end;
    private double factor;

    /**
     * Construct a line given the starting point and the ending point.
     *
     * @param start the starting point of the line
     * @param end   the ending point of the line
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
        CalFactor();
    }

    /**
     * Construct a line given the coordinates of the starting point.
     * and the coordinates of the ending point.
     *
     * @param x1 the x coordinates of the starting point
     * @param y1 the y coordinates of the starting point
     * @param x2 the x coordinates of the ending point
     * @param y2 the y coordinates of the ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * length method returns the length of this line.
     *
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(this.end);
    }

    private void CalFactor() {
        this.factor = (this.end().getY() - this.start().getY())
                / (this.end().getX() - this.start().getX());
    }

    /**
     * start method returns the starting point of the line.
     *
     * @return the starting point.
     */
    public Point start() {
        return this.start;
    }

    /**
     * end method returns the ending point of the line.
     *
     * @return the ending point.
     */
    public Point end() {
        return this.end;
    }

    public double getAngleBetweenLines(Line line) {
        double angle;
        double tan = (this.factor - line.factor) / (1+ this.factor * line.factor);
        return Math.atan(tan);
    }
}

