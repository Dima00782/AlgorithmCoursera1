import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private final int n;
    private final int top;
    private final int bottom;
    private int kOpenSites;
    private boolean[][] field;
    private final WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        n = size;
        field = new boolean[n][n];

        uf = new WeightedQuickUnionUF(2 * n * n + 2);
        top = 2 * n * n;
        bottom = top + 1;
        kOpenSites = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        guardParams(row, col);
        if (isOpen(row, col)) {
            return;
        }
        --row;
        --col;
        field[row][col] = true;
        ++kOpenSites;

        Cell[] neighborhood = new Cell[4];

        neighborhood[0] = new Cell(row - 1, col);
        neighborhood[1] = new Cell(row + 1, col);
        neighborhood[2] = new Cell(row, col - 1);
        neighborhood[3] = new Cell(row, col + 1);

        int currentId = row * n + col;
        for (int i = 0; i < neighborhood.length; ++i) {
            Cell cell = neighborhood[i];
            if (cell.check(n) && isOpen(cell.getRow() + 1, cell.getCol() + 1)) {
                int id = cell.getRow() * n + cell.getCol();
                uf.union(id, currentId);
                uf.union(n * n + id, n * n + currentId);
            }
        }

        if (row == n - 1) {
            uf.union(currentId, bottom);
        }

        if (row == 0) {
            uf.union(currentId, top);
            uf.union(n * n + currentId, top);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        guardParams(row, col);
        --row;
        --col;
        return field[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        guardParams(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        --row;
        --col;
        int id = row * n + col;
        return uf.connected(n * n + id, top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return kOpenSites;
    }

    public boolean percolates() {
        if (n == 1 && kOpenSites == 0) {
            return false;
        }
        return uf.connected(bottom, top);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();

            perc.open(p, q);
        }
    }

    private void guardParams(int row, int col) {
        if (!boundCheck(row) || !boundCheck(col)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean boundCheck(int x) {
        return x >= 1 && x <= n;
    }

    private class Cell {
        private final int row;
        private final int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public boolean check(int max) {
            return row >= 0 && col >= 0 && row < max && col < max;
        }
    }
}
