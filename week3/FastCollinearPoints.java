import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int MIN_NUMBER_OF_POINTS = 4;
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        checkNoNull(points);

        Point[] copiedPoints = new Point[points.length];
        System.arraycopy(points, 0, copiedPoints, 0, points.length);
        Arrays.sort(copiedPoints);
        checkNoRepeated(copiedPoints);

        segments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; ++i) {
            Point origin = points[i];
            Arrays.sort(copiedPoints, origin.slopeOrder());

            int first = 1;
            int second = first + 1;
            while (first < copiedPoints.length && second < copiedPoints.length) {
                double firstSlope = origin.slopeTo(copiedPoints[first]);
                while (second < copiedPoints.length && origin.slopeTo(copiedPoints[second]) == firstSlope) ++second;
                // plus 1 for origin
                if (second - first + 1 >= MIN_NUMBER_OF_POINTS) {
                    addSegmentToResult(segments, copiedPoints, first, second, origin);
                }
                first = second;
                second = first + 1;
            }
        }
    }
    
    private void checkNoRepeated(Point[] points) {
        for (int i = 1; i < points.length; ++i) {
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkNoNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void addSegmentToResult(ArrayList<LineSegment> result, Point[] points, int begin, int end, Point origin) {
        Point max = origin;
        Point min = origin;
        for (int i = begin; i != end; ++i) {
            if (points[i].compareTo(min) < 0) {
                min = points[i];
            }
            if (points[i].compareTo(max) > 0) {
                max = points[i];
            }
        }

        if (min.compareTo(origin) == 0) {
            result.add(new LineSegment(min, max));
        }
    }


    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
