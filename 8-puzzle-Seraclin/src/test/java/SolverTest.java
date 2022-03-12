import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class SolverTest {

    Board board;
    Solver solver;
    @Before
    public void setUp() throws Exception {
        // board = generateBoard("puzzle3x3-02.txt");
        board = generateBoard("puzzle3x3-unsolvable.txt");
    }
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
        solver = new Solver(initial);

        //print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        return initial;
    }

    @Test
    public void testDimension() {
        assertEquals("Should be length of 3", 3, board.dimension());

    }

    @Test
    public void moves() { // uses "puzzle3x3-02.txt"
        assertTrue("Should be solvable", solver.isSolvable());
        assertEquals("Should take 2 moves", 2, solver.moves());
    }
    @Test
    public void unsolvable(){ // uses "puzzle3x3-unsolvable.txt"
        assertFalse("Should not be solvable", solver.isSolvable());
        assertEquals("Should be not solvable", -1, solver.moves());
        assertNull(solver.solution());
    }

}
