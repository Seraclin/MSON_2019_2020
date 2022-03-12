import static org.junit.Assert.*;

import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;

public class OutcastTest {
  
  private WordNet wordNet;
  @Before
  public void setUp() throws Exception {
    wordNet = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
  }

  @Test
  public void testOutcast() {
    Outcast out = new Outcast(wordNet);
    In in = new In("wordnet-test-files/outcast10.txt");
    String[] input = in.readAllLines();
    assertEquals("albatross", out.outcast(input));
  }

  @Test
  public void testOutcast1() {
    Outcast out = new Outcast(wordNet);
    In in = new In("wordnet-test-files/outcast29.txt");
    String[] input = in.readAllLines();
    assertEquals("acorn", out.outcast(input));
  }
  @Test
  public void testOutcast2() {
    Outcast out = new Outcast(wordNet);
    In in = new In("wordnet-test-files/outcast5.txt");
    String[] input = in.readAllLines();
    assertEquals("table", out.outcast(input));
  }
}
