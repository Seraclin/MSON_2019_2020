import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Name:    Samantha Lin
 *  Compilation:  javac-algs4 Permutation.java
 *  Execution:    java-algs4 Permutation k < <filename>
 *  Dependencies: RandomizedQueue.java StdIn.java StdOut.java
 *  Description:  Takes an integer k as a command-line argument; reads in a sequence of strings from standard input using StdIn.readString(); and prints exactly k of them, uniformly at random. 
 *  Prints each item from the sequence at most once. 
 ******************************************************************************/
public class Permutation {

  public static void main(String[] args) {
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      rq.enqueue(StdIn.readString());
    }
    int cur = Integer.parseInt(args[0]);
    for (int i = 0; i < cur; i++) {
      StdOut.println(rq.dequeue());
    }

  }

}
