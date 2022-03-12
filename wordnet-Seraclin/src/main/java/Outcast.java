import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
  private WordNet words;
  public Outcast(WordNet wordnet) {
    words = wordnet;
  }
  /**
   * Finds the outcast from an array of WordNet nouns.
   * @param nouns an array of nouns
   * @return the outcast from the array
   */
  public String outcast(String[] nouns) {
    int[] dist = new int [nouns.length];
    int maxdist = 0;
    int outcastPos = 0;
    for (int i = 0; i < nouns.length; i++) {
      for (int j = 0; j < nouns.length; j++) {
        dist[i] += words.distance(nouns[i], nouns[j]);
      }
      if (dist[i] > maxdist) {
        outcastPos = i;
        maxdist = dist[i];
      }
    }
    return nouns[outcastPos];
  }

  // see test client below
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}