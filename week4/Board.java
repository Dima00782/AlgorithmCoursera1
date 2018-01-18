import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {
    private final int[][] field;
    private int numberOfMoves;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        field = new int[blocks.length][];
        for (int i = 0; i < field.length; ++i) {
            field[i] = Arrays.copyOf(blocks[i], blocks[i].length);
        }
    }

    // board dimension n
    public int dimension() {
        return field.length;
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[i].length; ++j) {
                int goal = i * field[i].length + j + 1;
                if (goal == field.length * field.length) {
                    goal = 0;
                }
                distance += goal != field[i][j] ? 1 : 0;
            }
        }
        return numberOfMoves + distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() - numberOfMoves) == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;

        Board board = (Board) y;
        if (board.dimension() != dimension()) return false;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[i].length; ++j) {
                if (field[i][j] != board.field[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder(dimension() + "\n");
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[i].length; ++j) {
                sb.append(" " + field[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        StdOut.println(initial.hamming());
        StdOut.println(initial);
    }
}
