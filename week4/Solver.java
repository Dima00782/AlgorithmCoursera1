import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private final boolean isSolved;
    private final ArrayList<Board> path = new ArrayList<Board>();

    private static class SearchNode {
        private Board board;
        private SearchNode predecessor;
        private int numberOfMoves;
        private int manhattan;

        public SearchNode(Board other, SearchNode previous, int kmoves) {
            board = other;
            numberOfMoves = kmoves;
            if (previous == null) {
                predecessor = this;
            } else {
                predecessor = previous;
            }
            manhattan = board.manhattan();
        }

        public int metric() {
            return manhattan + numberOfMoves;
        }

        public int getMoves() {
            return numberOfMoves;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPredecessor() {
            return predecessor;
        }

        public Board getPredecessorBoard() {
            return predecessor.getBoard();
        }

        public boolean equals(Object y) {
            if (this == y) return true;
            if (y == null) return false;
            if (getClass() != y.getClass()) return false;

            SearchNode obj = (SearchNode) y;

            return obj.board.equals(board);
        }
    }

    private static class ManhattanComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode lhs, SearchNode rhs) {
            return lhs.metric() - rhs.metric();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Comparator<SearchNode> comparator = new ManhattanComparator();
        MinPQ<SearchNode> pqInit = new MinPQ<SearchNode>(comparator);
        MinPQ<SearchNode> pqTwinned = new MinPQ<SearchNode>(comparator);

        SearchNode init = new SearchNode(initial, null, 0);
        Board twin = initial.twin();
        SearchNode twinned = new SearchNode(twin, null, 0);

        pqInit.insert(init);
        pqTwinned.insert(twinned);

        while (!init.getBoard().isGoal() && !twinned.getBoard().isGoal()) {
            init = pqInit.delMin();

            // System.out.println(init.getBoard());
            // System.out.println("manhattan = " + init.getBoard().manhattan());
            // System.out.println("priority = " + init.metric());
            // System.out.println();
            twinned = pqTwinned.delMin();

            // System.out.println("compare:");
            // System.out.println(init);
            for (Board neighboor : init.getBoard().neighbors()) {
                // System.out.println("It's neighboor");
                if (init.getPredecessor() == init || !init.getPredecessorBoard().equals(neighboor)) {
                    // System.out.println("ADD: ");
                    // System.out.println(neighboor);
                    pqInit.insert(new SearchNode(neighboor, init, init.getMoves() + 1));
                }
            }
            // System.out.println("end neighboor");

            for (Board neighboor : twinned.getBoard().neighbors()) {
                if (twinned.getPredecessor() == twinned || !twinned.getPredecessorBoard().equals(neighboor)) {
                    pqTwinned.insert(new SearchNode(neighboor, twinned, twinned.getMoves() + 1));
                }
            }
        }

        isSolved = init.getBoard().isGoal();
        if (isSolved) {
            while (init.getPredecessor() != init) {
                path.add(init.getBoard());
                init = init.getPredecessor();
            }
            path.add(initial);

            for (int i = 0; i < path.size() / 2; ++i) {
                Board tmp = path.get(i);
                path.set(i, path.get(path.size() - i - 1));
                path.set(path.size() - i - 1, tmp);
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        int size = path.size();
        return size == 0 ? 0 : (path.size() - 1);
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable() ? path : null;
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
