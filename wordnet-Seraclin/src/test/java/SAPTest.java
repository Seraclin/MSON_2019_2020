import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPTest {

  private SAP sapDigraph1;
  @Before
  public void setup() {
    In in = new In("wordnet-test-files/digraph1.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    sapDigraph1 = new SAP(G);
  }

  @Test
  public void testSAPImmutable() {
    In in = new In("wordnet-test-files/digraph1.txt");
    Digraph G = new Digraph(in);
    SAP sapDigraph2 = new SAP(G);
    SAP sapDigraph3 = sapDigraph2;
    sapDigraph2 = new SAP(G);
    assertNotEquals(sapDigraph2, sapDigraph3);
  }

  @Test
  public void testLengthIntInt() {
    assertEquals(4, sapDigraph1.length(3, 11));
  }

  @Test
  public void testLengthIntIntAmbigousAncestor() {
    In in = new In("wordnet-test-files/digraph-ambiguous-ancestor.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    assertEquals(1, sapDigraph2.length(3, 2));
  }
  @Test
  public void testLengthIntIntDigraph2() {
    In in = new In("wordnet-test-files/digraph2.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    assertEquals(2, sapDigraph2.length(1, 5));
  }

  @Test
  public void testAncestorIntInt() {
    assertEquals(1, sapDigraph1.ancestor(3, 11));
  }

  @Test
  public void testAncestorIntIntDigraph2() {
    In in = new In("wordnet-test-files/digraph2.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    assertEquals(0, sapDigraph2.ancestor(5, 1));
    assertEquals(0, sapDigraph2.ancestor(4, 1));
  }

  @Test
  public void testAncestorIntIntAmbig() {
    In in = new In("wordnet-test-files/digraph-ambiguous-ancestor.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    assertEquals(2, sapDigraph2.ancestor(0, 4));
    assertEquals(7, sapDigraph2.ancestor(6, 8));
  }

  @Test
  public void testLengthIterableIterable() {
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(7);
    verticesV.add(4);
    verticesV.add(9);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(11);
    verticesW.add(2);
    assertEquals(3,sapDigraph1.length(verticesV, verticesW));
  }

  @Test
  public void testAncestorIterableIterable() {
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(7);
    verticesV.add(4);
    verticesV.add(9);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(11);
    verticesW.add(2);
    assertEquals(0, sapDigraph1.ancestor(verticesV, verticesW)); // i'm pretty sure it's zero
  }
  @Test
  public void testAncestorIterableIterableAmbig() {
    In in = new In("wordnet-test-files/digraph-ambiguous-ancestor.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(7);
    verticesV.add(8);
    verticesV.add(9);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(6);
    assertEquals(7, sapDigraph2.ancestor(verticesV, verticesW));
  }
  @Test
  public void testAncestorIterableIterable2() {
    In in = new In("wordnet-test-files/digraph2.txt");
    Digraph G = new Digraph(in);
    System.out.println(G);
    SAP sapDigraph2 = new SAP(G);
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(5);
    verticesV.add(4);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(1);
    assertEquals(0, sapDigraph2.ancestor(verticesV, verticesW));
  }
  @Test(expected = IllegalArgumentException.class)
  public void testNullIterableIterable2() {
    In in = new In("wordnet-test-files/digraph1.txt");
    Digraph G = new Digraph(in);
    SAP sapDigraph2 = new SAP(G);
    ArrayList<Integer> verticesV = new ArrayList<Integer>();
    verticesV.add(2);
    verticesV.add(null);
    verticesV.add(3);
    ArrayList<Integer> verticesW = new ArrayList<Integer>();
    verticesW.add(1);
    sapDigraph2.ancestor(verticesV, verticesW);
    sapDigraph2.length(verticesV, verticesW);

  }
}
