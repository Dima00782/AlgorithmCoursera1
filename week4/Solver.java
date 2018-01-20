import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private Board init;
    private Board twinned;
    private final ArrayList<Board> pathInit = new ArrayList<Board>();
    private final ArrayList<Board> pathTwinned = new ArrayList<Board>();
    private final MinPQ<Board> pqInit;
    private final MinPQ<Board> pqTwinned;
    private final boolean isSolved;

    public static class ManhattanComparator implements Comparator<Board> {
        @Override
        public int compare(Board lhs, Board rhs) {
            return lhs.manhattan() - rhs.manhattan();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Comparator<Board> comparator = new ManhattanComparator();
        pqInit = new MinPQ<Board>(comparator);
        pqTwinned = new MinPQ<Board>(comparator);

        init = initial;
        twinned = init.twin();

        pqInit.insert(init);
        pqTwinned.insert(twinned);

        while (!init.isGoal() && !twinned.isGoal()) {
            init = pqInit.delMin();
            pathInit.add(init);

            // System.out.println("BEST: ");
            // System.out.println(init);
            twinned = pqTwinned.delMin();
            pathTwinned.add(twinned);

            // System.out.println("It's neighboor");
            for (Board neighboor : init.neighbors()) {
                if (checkUnique(pathInit, neighboor)) {
                    // System.out.println(neighboor);
                    pqInit.insert(neighboor);
                }
            }
            // System.out.println("end neighboor");

            for (Board neighboor : twinned.neighbors()) {
                if (checkUnique(pathTwinned, neighboor)) {
                    pqTwinned.insert(neighboor);
                }
            }
        }

        isSolved = init.isGoal();
    }

    private boolean checkUnique(ArrayList<Board> path, Board target) {
        for (Board board : path) {
            if (board.equals(target)) {
                return false;
            }
        }
        return true;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? (pathInit.size() - 1) : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? pathInit : null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
            Board initial = new Board(blocks);

            // solve the puzzle
            Solver solver = new Solver(initial);

            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
    }
}
