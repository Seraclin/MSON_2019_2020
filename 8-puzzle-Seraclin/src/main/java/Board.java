import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Board {
    private int[][] board;
    private int n;
    public Board(int[][] blocks) {
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                board[i][j] = blocks[i][j];
            }
        }
        n = blocks.length;
    }

    /**
     * Finds the number that should be in the final goal board spot.
     * @param row of this element
     * @param col of this element
     * @return the number that should be in that board spot.
     */
    private int getCorrect(int row, int col) {
        if (row == n - 1 && col == n - 1) {
            return 0;
        } else return row * dimension() + col + 1;
    }

    /**
     * Method returns the dimension of the n x n board.
     * @return side length n of the board nxn.
     */
    public int dimension() {
      return n;
    }

    /**
     * Method finds the number of blocks out of place from goal state.
     * @return the number of blocks out of place.
     */
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((board[i][j] != 0) && (board[i][j] != getCorrect(i, j))) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Finds the sum of the Manhattan distances (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     * @return the sum of Manhattan distances.
     */
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j];
                if (value != 0) {
                    manhattan += Math.abs(i - (value - 1) / board.length) + Math.abs(j - (value - 1) % board.length);
                }
            }
        }      return manhattan;
    }

    /**
     * Method checks if board equals goal board.
     * The goal board is ordered from increasing left to right with the last slot being a zero.
     * @return whether the board is the goal board.
     */
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((board[i][j] != getCorrect(i, j))) {
                    return false;
                }
            }
        }
      return true;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks.
     * @return a board with exchanged tiles
     */
    public Board twin() {
        Board twin = new Board(board);
        if (board[0][0] != 0 && board[0][1] != 0) {
            swap(twin, 0, 0, 0, 1);
        } else {
            swap(twin, 1, 0, 1, 1);
        }

        return twin;
    }

    /**
     * Checks if two boards are equal if they are have the same size
     * and their corresponding tiles are in the same positions.
     * @param y the board to compare to.
     * @return whether the two boards are equal.
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for (int i = 0; i < board.length; i++) {
            if (!Arrays.equals(board[i], that.board[i])) {
                return false;
            }
        }
      return true;
    }

    /**
     * Returns an iterable containing the neighbors of board.
     * @return An iterable containing the neighbors of board.
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbor = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    if (i < n - 1) { // down
                        Board neigh = new Board(board);
                        swap(neigh, i, j, i + 1, j);
                        neighbor.add(neigh);
                    }
                    if (i > 0) { // up
                        Board neigh = new Board(board);
                        swap(neigh, i, j, i - 1, j);
                        neighbor.add(neigh);
                    }
                    if (j < n - 1) { // right
                        Board neigh = new Board(board);
                        swap(neigh, i, j, i, j + 1);
                        neighbor.add(neigh);
                    }
                    if (j > 0) { // left
                        Board neigh = new Board(board);
                        swap(neigh, i, j, i, j - 1);
                        neighbor.add(neigh);
                    }
                }
            }
        }
      return neighbor;
    }

    /**
     * Method returns string composed of n + 1 lines. The first line contains the board size n.
     * The remaining n lines contains the n-by-n grid of tiles in row-major order,
     * using 0 to designate the blank square.
     * @return the String representation of the board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Method swaps two tiles on the board.
     * @param a the board
     * @param r1 row of the 1st tile
     * @param c1 column of the 1st tile
     * @param r2 row of the 2nd tile
     * @param c2 column of the 2nd tile
     * @return a Board with (a1, a2) swapped with (b1, b2)
     */
    private Board swap (Board a, int r1, int c1, int r2, int c2) {
        int temp = a.board[r1][c1];
        a.board[r1][c1] = a.board[r2][c2];
        a.board[r2][c2] = temp;
        return a;
    }

    public static void main(String[] args) {

    }
}