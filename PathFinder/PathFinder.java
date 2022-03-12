/*
THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
A TUTOR OR CODE WRITTEN BY OTHER STUDENTS.  Samantha Lin
*/

// Homework: revise this file, WRITE YOUR NAME UPSTAIRS, see TODO items below.

// Given a maze of size N, the method findPath(maze) finds an open
// path from (0,0) to (N-1,N-1), if such a path exists.
//
// See main() comments for its command line usage.

// DONETODO: the current solution is recursive (dfs() calls itself).  You
// need to make it non-recursive.  One way is to use an explicit stack
// instead of the runtime stack.  You do not need to find exactly the
// same open paths as found by the given code.

// DONETODO(EC): modify findPath so it finds a *shortest* open path from
// (0,0) to (N-1,N-1), when one exists.  You can read about a method
// for this, "breadth first search", in Section 4.1 of your book.

// DONETODO(EC): define method findWallPath (it currently returns null).
// When findPath fails to find a path, there should be a "blocking"
// path of walls separating S and T.  This path can start at any wall
// on the top row or right column of the maze (i==0 or j==N-1), and
// end at any wall at the bottom row or left column of the maze
// (i==N-1 or j==0).  Two walls can be adjacent by a cardinal step OR
// a diagonal step (so, each wall has 8 potential neighbors).  Again,
// recursion is not allowed here.

// DONETODO(EC): Finding a wall path is good, finding a shortest wall path
// is even better.  Note that if S (or T) is a wall, it is a wall path
// of size one, the smallest possible.

// DONETODO: Write your name in the header!

// For grading, we ignore the main() method, so do what you like with
// that.  We only test your findPath and findWallPath methods.

import java.io.IOException;
import java.util.LinkedList;


public class PathFinder
{
    // Any data fields here should be private and static.  They exist
    // only as a convenient way to share search context between your
    // static methods here.   It should be possible to call your
    // findPath() method more than once, on different mazes, and
    // to get valid results for each maze.

    // The maze we are currently searching, and its size.
    private static Maze m;      // current maze
    private static int N;       // its size (N by N)

    private static boolean[][] visited; // array to keep track of marked nodes in dfs/bfs
    // The parent array:
    private static Position[][] parent;
    private static LinkedDeque<Position[][]> parent2;

    // In the path-finding routines: for each position p, as soon as
    // we find a route to p, we set parent[p.i][p.j] to be a position
    // one step closer to the start (i.e., the parent discovered p).

    // Get parent of p (assumes p in range)
    static Position getParent(Position p) { return parent[p.i][p.j]; }

    // Set parent of p, if not yet set.  Value indicates success.
    static boolean setParent(Position p, Position par) {
        if (getParent(p) != null)
            return false;       // p already has a parent
        parent[p.i][p.j] = par;
        return true;
    }

    public static Deque<Position> findPath(Maze maze)
    {
        m = maze;                           // save the maze
        N = m.size();                       // save size (maze is N by N)
        parent = new Position[N][N];        // initially all null
        Position S = new Position(0,0);     // start of open path
        Position T = new Position(N-1,N-1); // end of open path

        // If either of these is a wall, there is no open path.
        if (!m.isOpen(S)) return null;
        if (!m.isOpen(T)) return null;

        // GOAL: for each reachable open position p, parent[p.i][p.j]
        // should be an open position one step closer to S.  That is,
        // it is the position that first discovered a route to p.
        // These parent links will form a tree, rooted at S.

        // Compute parent for each position reachable from S.
        // Since S is the root, we will let S be its own parent.

        // Compute parent links, by recursive dfs
        //dfs(S, S);

        // Compute parent links, by non-recursive depth-first-search!
        // dfs(S);

        // Compute parent links, shortest path by breadth-first-search!
        bfs(S);

        // If T has no parent, it is not reachable, so no path.
        if (getParent(T)==null)
            return null;
        // Otherwise, we can reconstruct a path from S to T.
        Deque<Position> path = new LinkedDeque<Position>();
        for (Position u=T; !u.equals(S); u=getParent(u))
            path.addFirst(u);
        path.addFirst(S);
        return path;
    }

    // recursive depth-first-search: set parent for each newly reachable p.
    /*private static void dfs(Position p, Position from)
    {
        if (!m.inRange(p) || !m.isOpen(p) || getParent(p) != null)
            return;
        // System.out.println("found " + p + " via parent " + from);
        setParent(p, from);
        // Now recursively try the four neighbors of p.
        for (int dir=0; dir<4; ++dir)
            dfs(p.neighbor(dir), p);
    }*/
    // a non-recursive DFS 1#
    private static void dfs(Position p)
    {
        if (!m.inRange(p) || !m.isOpen(p) || getParent(p) != null)
            return;
        LinkedDeque<Position> st = new LinkedDeque<>();
        visited = new boolean[N][N];
        st.addLast(p); // push p to stack
        setParent(p, p); // start parent is start itself

        while (!st.isEmpty()) {
            Position cur = st.removeLast();
            visited[cur.i][cur.j] = true; // mark spot as visited
            for (int dir=0; dir<4; ++dir) {// Now iteratively add the four neighbors of p to the stack.
                // System.out.println("found " + p + " via parent " + from);
                // add all unvisited in range, open neighbors
                Position neigh = cur.neighbor(dir);
                if (m.inRange(neigh) && m.isOpen(neigh) && !visited[neigh.i][neigh.j]) {
                    setParent(neigh,cur); // set cur as parent of neighbors
                    st.addLast(neigh);
                }
            }
        }
    }
    // BFS
    private static void bfs(Position s)
    {
        if (!m.inRange(s) || !m.isOpen(s) || getParent(s) != null)
            return;
        LinkedDeque<Position> q = new LinkedDeque<>();
        int[] dist = new int[N*N];
        visited = new boolean[N][N];
        q.addFirst(s); // push p to queue FIFO
        setParent(s, s); // start parent is start itself
        visited[0][0] = true; // mark starting spot as visited
        dist[0] = 0;
        while (!q.isEmpty()) {
            Position cur = q.removeFirst(); // FIFO
            for (int dir=0; dir<4; ++dir) {// Now iteratively add the four neighbors of p to the queue.
                // System.out.println("found " + p + " via parent " + from);
                // add all unvisited in range, open neighbors
                Position neigh = cur.neighbor(dir);
                if (m.inRange(neigh) && m.isOpen(neigh) && !visited[neigh.i][neigh.j]) {
                    q.addLast(neigh); // add neighbor to queue
                    setParent(neigh,cur); // set cur as parent of neighbors
                    visited[neigh.i][neigh.j] = true; // set as visited
                    dist[(neigh.i * N) + neigh.j] = dist[cur.i * N + cur.j] + 1;
                }
            }
        }
    }

    // Return a wall path separating S and T, or null.
    /*DONETODO Wall start first row or right column (i==0 or j==N-1)
    wall ends at a position in the last row or left column (i==N-1 or j=0).*/
    public static Deque<Position> findWallPath(Maze maze)
    {
        m = maze;                           // save the maze
        N = m.size();                       // save size (maze is N by N)
        parent = new Position[N][N]; // all null initially
        parent2 = new LinkedDeque<>();        // initially all null, queue of 2D parent position grids
        LinkedDeque<Position> S = new LinkedDeque<>();     // start area of wall path
        LinkedDeque<Position> T = new LinkedDeque<>(); // end area of wall path
        LinkedDeque<Integer> dist = new LinkedDeque<>();     // length of wall path
        int smallestDist = N*N + 1;
        Deque<Position> shortestPath = null; // shortest path

        // Compute parent links, shortest path by breadth-first-search!
        for (int j = 0; j < N; j++) { // find all valid starts first row and mark as visited
            Position start = new Position(0, j);
            if (m.isWall(start)){
                parent = new Position[N][N]; // all null initially
                if (start.j == 0){ // edge case of top left corner start/end area
                    shortestPath = new LinkedDeque<>();
                    shortestPath.addFirst(start);
                    return shortestPath;
                }
                S.addLast(start);
                int[] temp = bfsWall(maze,start);
                if (temp != null) { // if we get a coordinate that's not null
                    T.addLast(new Position(temp[0], temp[1])); // add new position of end point
                    dist.addLast(temp[2]); // add distance it takes to reach endpoint from start
                } else { // else it's null (end not reachable from start)
                    T.addLast(null);
                    dist.addLast(-1);
                }
                visited[start.i][start.j] = true;
            }
        }
        for (int i = 1; i < N; i++) { // find all valid starts rightmost column and mark as visited
            Position start = new Position(i, N - 1);
            if (m.isWall(start)) {
                parent = new Position[N][N]; // all null initially
                // The same as the above loop but start on the rightmost column
                if (start.i == N-1){ // edge case of bottom right corner start/end area
                    shortestPath = new LinkedDeque<>();
                    shortestPath.addFirst(start);
                    return shortestPath;
                }
                S.addLast(start);
                int[] temp = bfsWall(maze,start);
                if (temp != null) {
                    T.addLast(new Position(temp[0], temp[1]));
                    dist.addLast(temp[2]);
                } else {
                    T.addLast(null);
                    dist.addLast(-1);
                }
                visited[start.i][start.j] = true;
            }
        }

        //System.out.println("S "+S);
        //System.out.println("T "+ T);
        //System.out.println("Dist "+ dist);
        //System.out.println("PArents2 "+ parent2);

        /*for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                System.out.print(parent[i][j]+"   ");
            }
            System.out.println();
        }*/

        while (!T.isEmpty()) { // traverse through all possible ends with corresponding starts
            Position curT = T.removeFirst(); // get the an end point
            Position curS = S.removeFirst(); // get the corresponding start point
            int curDist = dist.removeFirst(); // get the distance from start to end
            parent = parent2.removeFirst(); // get the parent grid for the corresponding endpoint
            // System.out.println();
            // System.out.println("CURS "+ curS);
            // System.out.println("CURT "+ curT);
            if (curT != null) { // if T is null, then start didn't reach end
                Deque<Position> path = new LinkedDeque<Position>(); // our current path
                for (Position u=curT; !u.equals(curS); u=getParent(u)){ // construct path from end to start
                    path.addFirst(u);
                     // System.out.println("CURS "+curS);
                     // System.out.println("U "+u);
                     /*if (shortestPath != null && path.size() > shortestPath.size()) { // fix for when curS never equals u
                         break; //not needed now that we have parent grid for each endpoint
                     }*/
                }
                path.addFirst(curS); // add start point to path

                if (shortestPath == null || (curDist != -1 && curDist < smallestDist && path.size() != 0)) {
                    // Check if our current path is shorter than what we have, and update shortestPath and smallestDist
                    shortestPath = path;
                    smallestDist = curDist;
                    // System.out.println("SHOrt size "+shortestPath.size()+" curdist "+curDist);
                }
            }
        }
        if (shortestPath == null || shortestPath.size() == 0) { // no wall path found, return null
            return null;
        }
        // Otherwise, we can have a (shortest) path from S to T.
        return shortestPath;
    }
    private static int[] bfsWall(Maze maze, Position s)
    {
        int[] coordinates = new int[3]; // row, col, distance
        if (!m.inRange(s) || m.isOpen(s)) // check if in range wall
            return null;

        LinkedDeque<Position> q = new LinkedDeque<>();
        int[] dist = new int[N*N];
        visited = new boolean[N][N];
        q.addFirst(s); // push p to queue FIFO
        setParent(s, s); // start parent is start itself
        visited[s.i][s.j] = true; // mark starting spot as visited
        dist[(s.i * N) + s.j] = 0;
        if (s.i == N-1|| s.j == 0) { // if we start already at the end wall area
            coordinates[0] = s.i;
            coordinates[1] = s.j;
            coordinates[2] = 1;
            return coordinates;
        }
        while (!q.isEmpty()) {
            Position cur = q.removeFirst(); // FIFO
            for (int dir=0; dir<8; ++dir) {// Now iteratively add the 8 adjacent neighbors of cur to the queue.
                // add all unvisited in range, wall neighbors
                Position neigh = cur.neighbor(dir);
                if (m.inRange(neigh) && !m.isOpen(neigh) && !visited[neigh.i][neigh.j]) { // check if in range wall
                    q.addLast(neigh); // add neighbor to queue
                    setParent(neigh,cur); // set cur as parent of neighbors
                    // System.out.println("found " + neigh + " via parent " + cur);
                    visited[neigh.i][neigh.j] = true; // set as visited
                    dist[(neigh.i * N) + neigh.j] = dist[cur.i * N + cur.j] + 1;
                }

                if ((neigh.i == N-1 || neigh.j == 0) && m.isWall(neigh)) { // reached the end area at position
                    // System.out.println("DISTANCE: "+dist[(neigh.i * N) + neigh.j]);
                    coordinates[0] = neigh.i;
                    coordinates[1] = neigh.j;
                    coordinates[2] = dist[(neigh.i * N) + neigh.j];
                    parent2.addLast(parent);
                    return coordinates;
                }
            }
        }
        parent2.addLast(null);
        return null; // starting spot doesn't reach end area
    }

    // Command-line usage:
    //
    //    java PathFinder ARGS...
    //
    // Constructs maze (using same rules as Maze.main()), prints it,
    // finds the paths (open path and/or wall path), and reprints the
    // maze with the path highlighted.
    public static void main(String[] args) throws IOException {
        Maze m = new Maze("maze1.txt");
        System.out.println(m);
        Deque<Position> oPath = findPath(m);
        if (oPath != null)
            System.out.println("findPath() found an open path of size "
                    + oPath.size());
        Deque<Position> wPath = findWallPath(m);
        if (wPath != null)
            System.out.println("findWallPath() found a wall path of size "
                    + wPath.size());
        if (oPath==null && wPath==null)  {
            System.out.println("WARNING: neither path was found");
            // This may be OK, if you are not doing findWallPath (EC).
            // No point in reprinting the map.
            return;
        }
        if (oPath != null && wPath != null) // crossing?
            System.out.println("WARNING: cannot have both paths!");

        // Copy map of maze, and mark oPath with 'o', wPath with 'w'.
        char[][] map = m.copyArray();
        if (oPath != null)
            for (Position p: oPath)
                map[p.i][p.j] = 'o';
        if (wPath != null)
            for (Position p: wPath)
                map[p.i][p.j] = 'w';
        // Now print the marked map.
        System.out.println(Maze.toString(map));
    }

    // Java "defensive programming": we should not instantiate this
    // class.  To enforce that, we give it a private constructor.
    private PathFinder() {}
}
