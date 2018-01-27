import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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

    private boolean pointBelongRectangle(Point2D point, RectHV rect) {
        return point.x() >= rect.xmin() && point.x() <= rect.xmax() 
            && point.y() >= rect.ymin() && point.y() <= rect.ymax();
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
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.75, 0.0));
        tree.insert(new Point2D(0.75, 0.25));
        tree.insert(new Point2D(0.5, 0.25));
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.75, 0.75));
        tree.insert(new Point2D(1.0, 0.0));
        tree.insert(new Point2D(0.5, 0.25));
        tree.insert(new Point2D(0.75, 0.25));
        tree.insert(new Point2D(0.75, 0.75));

        System.out.println(tree.size());
    }
}
