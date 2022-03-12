import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class BoardTest {
  
  Board board;
  Board board2;
  @Before
  public void setUp() throws Exception {
    board = generateBoard("puzzle3x3-01.txt");
    int[][] boardf = new int [3][3];
    for (int i = 0; i < 3; i++) {
      boardf[0][i] = i + 4;
    }
    for (int i = 0; i < 2; i++) {
      boardf[1][i] = i + 7;
    }
    boardf[1][2] = 0;
    for (int i = 0; i < 3; i++) {
      boardf[2][i] = i + 1;
    }
    board2 = new Board(boardf);
  }
  /*
  board 1
 1  2  3
 4  5  0
 7  8  6

 board 2
4 5 6
7 8 0
1 2 3
   */
  private Board generateBoard(String filename) {
 // create initial board from file
    In in = new In("8puzzle-test-files/" + filename);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
//    if (!solver.isSolvable())
//        StdOut.println("No solution possible");
//    else {
//        StdOut.println("Minimum number of moves = " + solver.moves());
//        for (Board board : solver.solution())
//            StdOut.println(board);
//    }
    return initial;
  }

  @Test
  public void testDimension() {
    assertEquals("Should be length of 3", 3, board.dimension());
    assertEquals("Should be length of 3", 3, board2.dimension());

  }

  @Test
  public void testSegments() {
    //fail("Not yet implemented");
  }
  @Test
  public void testHamming() {
    assertEquals("Should be 1 out of place", 1, board.hamming());
    assertEquals("Should be 8 out of place", 8, board2.hamming());
  }
  @Test
  public void testManhattan() {
    assertEquals("Should be sum of 1", 1, board.manhattan());
    assertEquals("Should be sum of 11", 11, board2.manhattan());
  }
  @Test
  public void testisGoal() {
    assertFalse("Should not be goal", board.isGoal());
    assertFalse("Should not be goal", board2.isGoal());
  }
  @Test
  public void testisEqual() {
    assertFalse("Should not be equal", board2.equals(board));
  }

}
