import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
    private Digraph graph;
   public SAP(Digraph G) {
       if (G == null) {
           throw new IllegalArgumentException("Argument is null");
       }
     graph = new Digraph(G);
   }
    /**
     * The length of shortest ancestral path between v and w; -1 if no such path.
     * @param v an integer that represents a vertex
     * @param w an integer that represents a vertex
     * @return the shortest path between them; -1 if none exists
     */
   public int length(int v, int w) {
       if (v < 0 || v > graph.V() -1 || w < 0 || w > graph.V() - 1) {
           throw new IllegalArgumentException();
       }
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

       return bfsLength(bfsV, bfsW);
   }

    /**
     * Finds a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path.
     * @param v the first word
     * @param w the second word
     * @return the common ancestor with the shortest ancestal path of v and w.
     */
   public int ancestor(int v, int w) {
       if (v < 0 || v > graph.V() -1 || w < 0 || w > graph.V() - 1) {
           throw new IllegalArgumentException();
       }
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

       return bfsAncestor(bfsV, bfsW);
   }

    /**
     * Finds the length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     * @param v the first vertex as an iterable
     * @param w the second vertex as an iterable
     * @return the length of the shortest ancestral path
     */
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
       if (v == null || w == null || !validIndex(v) || !validIndex(w)) {
           throw new IllegalArgumentException("Argument is null");
       }
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

       return bfsLength(bfsV, bfsW);
   }

    /**
     * Finds a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path.
     * @param v the first vertex as an iterable
     * @param w the second vertex as an iterable
     * @return the common ancestor of v and w.
     */
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       if (v == null || w == null || !validIndex(v) || !validIndex(w)) {
           throw new IllegalArgumentException("Argument is null");
       }
       BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
       BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);

       return bfsAncestor(bfsV, bfsW);
   }

    /**
     * Helper method for ancestor BFS.
     * @param v1 first bfs
     * @param w1 second bfs
     * @return the common ancestor with the shortest path
     */
   private int bfsAncestor(BreadthFirstDirectedPaths v1, BreadthFirstDirectedPaths w1) {
       if (v1 == null || w1 == null) {
           throw new IllegalArgumentException();
       }
       int currentSmallestDistance = -1;
       int ancestor = -1;
       for (int i = 0; i < graph.V(); i++) {
           if (v1.hasPathTo(i) && w1.hasPathTo(i)) {
               int dist = v1.distTo(i) + w1.distTo(i);
               if (dist < currentSmallestDistance || currentSmallestDistance == -1) {
                   ancestor = i;
                   currentSmallestDistance = dist;
               }
           }
       }
       return ancestor;
   }
    /**
     * Helper method for length BFS.
     * @param v1 first bfs
     * @param w1 second bfs
     * @return the common ancestor with the shortest path
     */
    private int bfsLength(BreadthFirstDirectedPaths v1, BreadthFirstDirectedPaths w1) {
        if (v1 == null || w1 == null) {
            throw new IllegalArgumentException();
        }
        int currentSmallestDistance = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (v1.hasPathTo(i) && w1.hasPathTo(i)) {
                int dist = v1.distTo(i) + w1.distTo(i);
                if (dist < currentSmallestDistance || currentSmallestDistance == -1) {
                    currentSmallestDistance = dist;
                }
            }
        }
        return currentSmallestDistance;
    }

    /**
     * Checks if vertices is in bounds
     * @param vertices the vertices to check
     * @return whether the vertices are in bounds
     */
    private boolean validIndex(Iterable<Integer> vertices) {
        if (vertices == null) {
            return false;
        }
        for (Integer vertex : vertices) {
            if (vertex == null || vertex < 0 || vertex > graph.V() -1) {
                return false;
            }
        }
        return true;
    }

   // do unit testing of this class
   public static void main(String[] args) {
     In in = new In(args[0]);
     Digraph G = new Digraph(in);
     SAP sap = new SAP(G);
     while (!StdIn.isEmpty()) {
         int v = StdIn.readInt();
         int w = StdIn.readInt();
         int length   = sap.length(v, w);
         int ancestor = sap.ancestor(v, w);
         StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
     }
   }
}