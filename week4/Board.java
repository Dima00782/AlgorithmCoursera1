import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Board {
    private final int[][] field;
    private int numberOfMoves;
    private int blankI;
    private int blankJ;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        field = new int[blocks.length][];
        for (int i = 0; i < field.length; ++i) {
            field[i] = new int[blocks[i].length];
            for (int j = 0; j < blocks[i].length; ++j) {
                field[i][j] = blocks[i][j];
                if (field[i][j] == 0) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }
    }

    private void swap(int[][] blocks, int i1, int j1, int i2, int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;
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
        int[][] blocks = new int[field.length][];
        for (int i = 0; i < blocks.length; ++i) {
            blocks[i] = Arrays.copyOf(field[i], field[i].length);
        }

        int i = StdRandom.uniform(0, blocks.length);
        int j = StdRandom.uniform(0, blocks.length);

        int k = StdRandom.uniform(0, blocks.length);
        int l = StdRandom.uniform(0, blocks.length);
        while (k == i && l == j) {
            k = StdRandom.uniform(0, blocks.length);
            l = StdRandom.uniform(0, blocks.length);
        }
        swap(blocks, i, j, k, l);

        return new Board(blocks);
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
        ArrayList<Board> boards = new ArrayList<Board>();
        if (blankI > 0) {
            Board moveUp = new Board(field);
            swap(moveUp.field, blankI, blankJ, blankI - 1, blankJ);
            boards.add(moveUp);
        }

        if (blankI < field.length - 1) {
            Board moveDown = new Board(field);
            swap(moveDown.field, blankI, blankJ, blankI + 1, blankJ);
            boards.add(moveDown);
        }

        if (blankJ > 0) {
            Board moveLeft = new Board(field);
            swap(moveLeft.field, blankI, blankJ, blankI - 1, blankJ);
            boards.add(moveLeft);
        }

        if (blankJ < field.length - 1) {
            Board moveRight = new Board(field);
            swap(moveRight.field, blankI, blankJ, blankI, blankJ + 1);
            boards.add(moveRight);
        }

        return boards;
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
        StdOut.println();
        StdOut.println();

        for (Board b : initial.neighbors()) {
            StdOut.println(b);
            StdOut.println();
        }
    }
}
