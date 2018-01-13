import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int MIN_NUMBER_OF_POINTS = 4;
    private LineSegment[] lineSegments;

    private void checkNoRepeated(Point[] points) {
        for (int i = 1; i < points.length; ++i) {
            if (points[i - 1] == null || points[i] == null || points[i - 1].compareTo(points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void addSegmentToResult(ArrayList<LineSegment> result, Point[] points, int begin, int end, Point origin, boolean[] used) {
        Point max = origin;
        Point min = origin;
        for (int i = begin; i != end; ++i) {
            if (points[i].compareTo(min) < 0) {
                min = points[i];
            }
            if (points[i].compareTo(max) > 0) {
                max = points[i];
            }
            used[i] = true;
        }

        result.add(new LineSegment(min, max));
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        checkNoRepeated(points);

        boolean[] used = new boolean[points.length];
        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        int size = points.length;
        for (int i = 0; i + (MIN_NUMBER_OF_POINTS - 1) < points.length; ++i) {
            Point origin = points[i];
            if (used[i]) {
                continue;
            }
            Arrays.sort(points, i + 1, points.length, origin.slopeOrder());

            // skip first because it is a origin because a.slopeTo(a) == -inf
            int first = i + 1;
            int second = first + 1;
            while (first < points.length && second < points.length) {
                double firstSlope = origin.slopeTo(points[first]);
                while (second < points.length && origin.slopeTo(points[second]) == firstSlope) ++second;
                // plus 1 for origin
                if (second - first + 1 >= MIN_NUMBER_OF_POINTS) {
                    addSegmentToResult(segments, points, first, second, origin, used);
                }
                first = second;
                second = first + 1;
            }
        }

        lineSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
    	return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
    	return lineSegments;
    }
}
