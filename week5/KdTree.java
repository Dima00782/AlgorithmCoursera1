import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

public class KdTree {
    private class Node {
        public Node left;
        public Node right;
        public Point2D point;

        public Node(Point2D p) {
            point = p;
        }
    }

    private Node root;
    private int numberOfElements;

    // construct an empty set of points
    public KdTree() {
    }

    private Node put(Node current, Point2D p, int step) {
        if (current == null) return new Node(p);
        double cmp = 0;
        if (step % 2 == 0) cmp = p.x() - current.point.x(); // vertical
        else cmp = p.y() - current.point.y(); // horizontal

        if (cmp <= 0) current.left = put(current.left, p, step + 1);
        else if (cmp > 0) current.right = put(current.right, p, step + 1);

        return current;
    }

    private Node get(Node root, Point2D p) {
        Node x = root;
        int step = 0;
        while (x != null) {
            double cmp = 0;
            if (step % 2 == 0) cmp = p.x() - x.point.x(); // vertical
            else cmp = p.y() - x.point.y(); // horizontal

            if (cmp <= 0) {
                if (x.point.equals(p)) {
                    return x;
                }
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            }
            ++step;
        }

        return null;
    }

    private void range(Node x, RectHV rect, int step, ArrayList<Point2D> result) {
        if (x == null) return;
        if (rect.contains(x.point)) result.add(x.point);

        if (step % 2 == 0) {
            if (rect.xmin() <= x.point.x()) {
                range(x.left, rect, step + 1, result);
            }

            if (rect.xmax() > x.point.x()) {
                range(x.right, rect, step + 1, result);
            }
        } else {
            if (rect.ymin() <= x.point.y()) {
                range(x.left, rect, step + 1, result);
            }

            if (rect.ymax() > x.point.y()) {
                range(x.right, rect, step + 1, result);
            }
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return numberOfElements;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p)) return;
        root = put(root, p, 0);
        ++numberOfElements;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node found = get(root, p);
        return found != null && p.equals(found.point);
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> result = new ArrayList<Point2D>();
        range(root, rect, 0, result);
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(7, 2));
        tree.insert(new Point2D(5, 4));
        tree.insert(new Point2D(2, 3));
        tree.insert(new Point2D(4, 7));
        tree.insert(new Point2D(9, 6));

        for (Point2D p : tree.range(new RectHV(0, 4, 10, 10))) {
            System.out.println(p);
        }
        System.out.println();
    }
}
