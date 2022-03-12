import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    /**
     * Node object for traversing Board.
     * Each node contains the current board, previous board state, and whether if a twin.
     */
    private class BoardNode {
        private Board currentBoard;
        private BoardNode previous;
        private boolean isTwin;
        private int numMoves;
        private int gValue;
        public BoardNode (Board c, BoardNode p, boolean iT) {
            currentBoard = c;
            previous = p;
            isTwin = iT;
            if (previous == null) {
                numMoves = 0;
            } else {
                numMoves = previous.numMoves + 1;
            }
            gValue = numMoves + c.manhattan();
        }
    }
    private class NodeOrder implements Comparator<BoardNode> {
        @Override
        public int compare(BoardNode s1, BoardNode s2) {
            int s1Priority = s1.gValue;
            int s2Priority = s2.gValue;

            if (s1Priority < s2Priority) {
                return -1;
            }
            if (s1Priority > s2Priority) {
                return 1;
            }

            if (s1Priority - s1.numMoves < s2Priority - s2.numMoves) {
                return -1;
            }
            if (s1Priority - s1.numMoves > s2Priority - s2.numMoves) {
                return 1;
            }
            return 0;
        }
    }
    private MinPQ<BoardNode> pq;
    private BoardNode goalNode;
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board is null");
        }
        pq = new MinPQ<>(new NodeOrder());

        pq.insert(new BoardNode(initial, null, false));
        pq.insert(new BoardNode(initial.twin(), null, true)); // twin of initial
        getGoalNode();
    }
    /**
     * Helper method to find a solution to the initial board using the A* algorithm.
     */
    private BoardNode getGoalNode() {
        while (!pq.isEmpty()) {
            BoardNode cur = pq.delMin();
            if (cur.currentBoard.isGoal()) { // if we get our goal;
                goalNode = cur;
                pq = null;
                return goalNode;
            } else {
                for (Board neighbor: cur.currentBoard.neighbors()) {
                    if (cur.previous == null || !neighbor.equals(cur.previous.currentBoard)) {
                        pq.insert(new BoardNode(neighbor, cur, cur.isTwin));
                    }
                }
            }
        }
        return null;
    }
    /**
     * Checks whether the initial board is solvable.
     * @return true if the board can be solved, false if not.
     */
    public boolean isSolvable() { // // if we get our goal, but is a twin then it can't be solved
      return !goalNode.isTwin;
    }

    /**
     * Finds the minimum number of moves to solve the initial board.
     * @return the number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        } else {
            return goalNode.numMoves;
        }
    }
    /**
     * The sequence of boards in a shortest solution.
     * @return an iterator of the shortest board solutions; null if unsolvable.
     */
    public Iterable<Board> solution() {
      if (!isSolvable()) {
          return null;
      }
      ArrayList<Board> solution = new ArrayList<>();
      BoardNode cur = goalNode;
      while (cur != null) {
          solution.add(0, cur.currentBoard);
          cur = cur.previous;
      }
      return solution;
    }
    public static void main(String[] args) {
      // solve a slider puzzle (given below)
    }
}