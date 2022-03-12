import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.HashMap;

public class WordNet {
    private SAP sap;
    private ST<String, Bag<Integer>> nouns; // symbol table, bag keeps track of the synonyms' ids
    private HashMap<Integer, String> idsToSynset;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("Null arguments");
        }
        idsToSynset = new HashMap<>();
        nouns = new ST<>();
        In reader = new In(synsets);
        int lines = 0;
        while (reader.hasNextLine()) {
            lines++;
            String line = reader.readLine();
            String[] split = line.split(","); // id, synset, definition (not needed)
            int id = Integer.parseInt(split[0]);
            String synset = split[1];
            String[] synsetList = synset.split(" ");
            idsToSynset.put(id, synset);
            for (String syn : synsetList) {
                if (!nouns.contains(syn)) { // if does not contain noun already
                    Bag<Integer> ids = new Bag<>();
                    ids.add(id);
                    nouns.put(syn, ids); // String key, Bag value)
                } else {
                    nouns.get(syn).add(id);
                }
            }
        }
        reader = new In(hypernyms);
        Digraph graph = new Digraph(lines);
        while (reader.hasNextLine()) { // directed edge v→w represents that w is a hypernym of v
            String line = reader.readLine(); // e.g. 53,29509,62089
            String[] ids = line.split(",");
            int id = Integer.parseInt(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                graph.addEdge(id, Integer.parseInt(ids[i]));
            }
        }

        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle() || !rootedDAG(graph)) {
            throw new IllegalArgumentException("The input does not correspond to a rooted DAG!");
        }

        sap = new SAP(graph);
    }

    /**
     * Checks if digraph is a rooted DAG
     * @param g the digraph to check
     * @return whether it is a rooted DAG
     */
    private boolean rootedDAG(Digraph g) {
        int roots = 0;
        for (int i = 0; i < g.V(); i++) {
            if (!g.adj(i).iterator().hasNext()) {
                roots++;
                if (roots > 1) {
                    return false;
                }
            }
        }
        return roots == 1;
    }

    /**
     * Returns all Wordnet nouns.
     *
     * @return an Iterable of the Wordnet nouns
     */
    public Iterable<String> nouns() {
        return nouns.keys();
    }

    /**
     * Checks whether the word is a WordNet noun.
     *
     * @param word the word to check
     * @return whether that word is a Wordnet noun
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null Arguments");
        }
        return nouns.contains(word);
    }

    /**
     * Finds the distance between two nouns.
     *
     * @param nounA the first noun
     * @param nounB the second noun
     * @return the distance between the two nouns.
     */
    public int distance(String nounA, String nounB) {
        if (!nouns.contains(nounA) || !nouns.contains(nounB) || nounA == null || nounB == null) {
            throw new IllegalArgumentException("Null Argument or not a Wordnet noun");
        }
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    /**
     * Finds a synset (second field of synsets.txt) that is a common ancestor of nounA and nounB
     * in the shortest ancestral path.
     *
     * @param nounA the first noun
     * @param nounB the second noun
     * @return A synset that is the common ancestor of nounA and nounB
     */
    public String sap(String nounA, String nounB) {
        if (!nouns.contains(nounA) || !nouns.contains(nounB) || nounA == null || nounB == null) {
            throw new IllegalArgumentException("Null Argument or not a Wordnet noun");
        }
        return idsToSynset.get(sap.ancestor(nouns.get(nounA), nouns.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String line = "53,29509,62089";
        String[] split = line.split(",");
        for (String s : split) {
            System.out.println(s);
        }

    }
}