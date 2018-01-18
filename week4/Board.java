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
        return 0;
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
        return false;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        return null;
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
    }
}
