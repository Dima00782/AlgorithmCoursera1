import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> set = new SET<Point2D>();

    // construct an empty set of points
    public PointSET() {}

    private void nullGuard(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    private boolean pointBelongRectangle(Point2D point, RectHV rect) {
        return point.x() >= rect.xmin() && point.x() <= rect.xmax() 
            && point.y() >= rect.ymin() && point.y() <= rect.ymax();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        nullGuard(p);
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        nullGuard(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        nullGuard(rect);
        ArrayList<Point2D> result = new ArrayList<Point2D>();
        for (Point2D p : set) {
            if (pointBelongRectangle(p, rect)) {
                result.add(p);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        nullGuard(p);
        Point2D min = null;
        for (Point2D current : set) {
            if (min == null) {
                min = current;
            } else if (p.distanceSquaredTo(current) < p.distanceSquaredTo(min)) {
                min = current;
            }
        }

        return min;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
