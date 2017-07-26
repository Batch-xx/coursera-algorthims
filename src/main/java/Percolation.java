import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Percolation {
    private int[] openSite;
    private int totalOpenSites;
    private final int sideLength;
    private final int srcSite;
    private final int sinkSite;
    private final WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException("n is less than 0");

        sideLength = n;
        int num = n * n + 2;
        openSite = new int[num];
        srcSite = 0;
        openSite[srcSite] = 1;
        sinkSite = 1;
        openSite[sinkSite] = 1;
        uf = new WeightedQuickUnionUF(num);
    }

    public void open(int row, int col) {
        if(row > sideLength || col > sideLength) {
            throw new IllegalArgumentException("either row or col exceed limit: " + row + ", " + col);
        }
        row--;
        col--;
        if(openSite[matrixToArray(row, col)] == 0) {
            int idx = matrixToArray(row, col);
            openSite[idx] = 1;
            connectOpenNeighbors(row, col);
            totalOpenSites++;
        }
    }

    public boolean isOpen(int row, int col) {
        row--;
        col--;
        if(row > sideLength || col > sideLength) {
            throw new IllegalArgumentException("either row or coll exceed limit");
        }
        int idx = matrixToArray(row, col);
        boolean isOpen = false;
        try{
            isOpen = openSite[idx] == 1;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("row: " + row + ", " + "col: " + col + " index: " + idx);
        }
        return isOpen;
    }

    public boolean isFull(int row, int col) {
        row--;
        col--;
        if(row > sideLength || col > sideLength) {
            throw new IllegalArgumentException("either row or coll exceed limit");
        }
        if(isOpen(row, col)) {
            int idx = matrixToArray(row, col);
            return uf.connected(srcSite, idx);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    public boolean percolates() {
        return uf.connected(srcSite, sinkSite);
    }

    private int matrixToArray(int row, int col) {
        return row * sideLength + col + 2;
    }

    private void connectOpenNeighbors(int row, int col) {
        int current = matrixToArray(row, col);
        if(isLeft(col) && isTop(row)) { //Top-left
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            uf.union(current, srcSite);
        } else if(isRight(col) && isTop(row)) { //Top- right
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            uf.union(current, srcSite);
        } else if(isLeft(col) && isBottom(row)) { //Bottom - left
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
            uf.union(current, sinkSite);
        } else if(isRight(col) && isBottom(row)) { //Bottom - right
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
            uf.union(current, sinkSite);
        } else if(isLeft(col)) {
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
        } else if(isRight(col)) {
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
        } else if(isTop(row)) {
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            uf.union(current, srcSite);
        } else if(isBottom(row)) {
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
            uf.union(current, sinkSite);
        } else {
            if(openSite[matrixToArray(toBottom(row), col)] == 1) {
                uf.union(current, matrixToArray(toBottom(row), col));
            }
            if(openSite[matrixToArray(toTop(row), col)] == 1) {
                uf.union(current, matrixToArray(toTop(row), col));
            }
            if(openSite[matrixToArray(row, toRight(col))] == 1) {
                uf.union(current, matrixToArray(row, toRight(col)));
            }
            if(openSite[matrixToArray(row, toLeft(col))] == 1) {
                uf.union(current, matrixToArray(row, toLeft(col)));
            }
        }
    }

    private boolean isLeft(int col) {
        return col == 0;
    }

    private boolean isRight(int col) {
        return col == (sideLength - 1);
    }

    private boolean isTop(int row) {
        return row == 0;
    }

    private boolean isBottom(int row) {
        return row == (sideLength - 1);
    }

    private int toRight(int col) {
        return col + 1;
    }

    private int toLeft(int col) {
        return col - 1;
    }

    private int toBottom(int row) {
        return row + 1;
    }

    private int toTop(int row) {
        return row - 1;
    }


    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
        Scanner scanner = new Scanner(new File(args[0]));
        int N = scanner.nextInt();
        Percolation percolation = new Percolation(N);
        while (scanner.hasNext()) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            percolation.open(row, col);
            percolation.isOpen(row, col);
            if (percolation.percolates()) {
                System.out.println("Is Percolating");
                return;
            }
            percolation.isFull(row, col);
        }
        System.out.println("Does not Percolate");
    }
}
