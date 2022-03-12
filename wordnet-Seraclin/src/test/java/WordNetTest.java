import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class WordNetTest {

  private WordNet wordNet6TwoAncestors;
  @Before
  public void setUp() throws Exception {
    wordNet6TwoAncestors = new WordNet("wordnet-test-files/synsets6.txt", "wordnet-test-files/hypernyms6TwoAncestors.txt");
  }

  @Test (timeout = 5000, expected = IllegalArgumentException.class)
  public void testWordNetNullSysnets() {
    WordNet w = new WordNet(null, "wordnet-test-files/hypernyms.txt");
  }
  
  @Test (timeout = 5000, expected = IllegalArgumentException.class)
  public void testWordNetNullHypernyms() {
    WordNet w = new WordNet("wordnet-test-files/sysnets.txt", null);
  }
  
  //TODO Create test case that throws IllegalArgumentException if there is a cycle
  
  //TODO Create test case that throws IllegalArgumentException if there are two roots

  @Test
  public void testNouns() {
    String[] expectedNounsArray = {"a", "b", "c", "d", "e", "f"};
    ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
    for(String actualNoun: wordNet6TwoAncestors.nouns()) {
      assertTrue(expectedNouns.contains(actualNoun));
    }
  }
  @Test
  public void testNouns3() {
    WordNet wordNet6TwoAncestors2 = new WordNet("wordnet-test-files/synsets3.txt", "wordnet-test-files/hypernyms3InvalidCycle.txt");
    String[] expectedNounsArray = {"a", "b", "c"};
    ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
    for(String actualNoun: wordNet6TwoAncestors2.nouns()) {
      assertTrue(expectedNouns.contains(actualNoun));
    }
  }
  @Test
  public void testNouns15() {
    WordNet wordNet6TwoAncestors2 = new WordNet("wordnet-test-files/synsets15.txt", "wordnet-test-files/hypernyms15Path.txt");
    String[] expectedNounsArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    ArrayList<String> expectedNouns = new ArrayList<String>(Arrays.asList(expectedNounsArray));
    for(String actualNoun: wordNet6TwoAncestors2.nouns()) {
      assertTrue(expectedNouns.contains(actualNoun));
    }
  }

  @Test
  public void testIsNoun() {
    assertTrue(wordNet6TwoAncestors.isNoun("a"));
  }

  @Test
  public void testIsNoun1() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
    assertTrue(wordNet.isNoun("'hood"));
    assertTrue(wordNet.isNoun("ibis"));
    assertTrue(wordNet.isNoun("zooid"));
    assertTrue(wordNet.isNoun("persona"));
    assertFalse(wordNet.isNoun("imadethiswordupgecko"));
  }

  @Test
  public void testIsNounFalse() {
    assertFalse(wordNet6TwoAncestors.isNoun("g"));
  }
  @Test
  public void testIsNoun2() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets.txt", "wordnet-test-files/hypernyms.txt");
    assertTrue(wordNet.isNoun("Benedict"));
    assertTrue(wordNet.isNoun("coronary-artery_disease"));
    assertTrue(wordNet.isNoun("American_shrew_mole"));
    assertTrue(wordNet.isNoun("18-karat_gold"));
    assertFalse(wordNet.isNoun(" "));
  }

  @Test
  public void testDistance() {
    assertEquals(3, wordNet6TwoAncestors.distance("e", "b"));
  }

  @Test
  public void testDistanceAmb11() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
    assertEquals(4, wordNet.distance("k", "g"));
    assertEquals(3, wordNet.distance("a", "e"));
  }

  @Test
  public void testDistanceManyPaths() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
    assertEquals(4, wordNet.distance("a", "k"));
    assertEquals(2, wordNet.distance("d", "h"));
  }

  @Test
  public void testSap() {
    assertEquals("a", wordNet6TwoAncestors.sap("e", "b")); // ties are okay
  }


  @Test
  public void testSapAmb11() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernyms11AmbiguousAncestor.txt");
    assertEquals("h", wordNet.sap("k", "g"));
    assertEquals("c", wordNet.sap("a", "e"));
  }

  @Test
  public void testSapManyPaths() {
    WordNet wordNet = new WordNet("wordnet-test-files/synsets11.txt", "wordnet-test-files/hypernymsManyPathsOneAncestor.txt");
    assertEquals("f", wordNet.sap("a", "k"));
    assertEquals("f", wordNet.sap("d", "h"));
  }
}
