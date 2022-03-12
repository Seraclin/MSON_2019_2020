import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import java.util.ArrayList;

public class BaseballElimination {
  // create a baseball division from given filename in format specified below
  private ST<String, int[]> division;
  private final int TEAM_NUMBER_POSITION = 0;
  private final int WINS_POSITION = 1;
  private final int LOSSES_POSITION = 2;
  private final int REMAINING_GAMES_POSITION = 3;
  private final int REMAINING_DIVISION_START_POSITION = 4;
  private String[] teams;
  public BaseballElimination(String filename) {
      division = new ST<>();
      int count = 0;
      In reader = new In(filename);
      int numTeams = reader.readInt();
      teams = new String[numTeams];
      reader.readLine();
      while (reader.hasNextLine()) {
          String input = reader.readLine();
          String[] line = (input.trim()).split("\\s+");
          int[] stats = new int[numTeams + 4]; // count, win, loss, remaining, division
          stats[0] = count;
          for (int i = 1; i < numTeams + 4; i++) {
              stats[i] = Integer.parseInt(line[i]);
          }
          division.put(line[0], stats);
          teams[count] = line[0];
          count++;
      }
  }
    /**
     * Return the number of teams.
     * @return the number of teams
     */
  public int numberOfTeams() {
    return division.size();
  }
    /**
     * A list of all teams in the division
     * @return a string iterable of the teams
     */
  public Iterable<String> teams() {
    return division.keys();
  }
    /**
     * Returns the number of wins for a given team.
     * @param team the team to find number of wins
     * @return the number of wins
     */
  public int wins(String team) {
      if (!division.contains(team)) {
          throw new IllegalArgumentException("Invalid team");
      }
      int[] stats = division.get(team);
    return stats[WINS_POSITION];
  }
    /**
     * Returns the number of losses for a given team.
     * @param team the team to find losses
     * @return the number of losses
     */
  public int losses(String team)  {
      if (!division.contains(team)) {
          throw new IllegalArgumentException("Invalid team");
      }
      int[] stats = division.get(team);
      return stats[LOSSES_POSITION];
  }
    /**
     * Returns the number of remaining games for a given team.
     * @param team the team to find remaining games.
     * @return the number of remaining games
     */
  public int remaining(String team) {
      if (!division.contains(team)) {
          throw new IllegalArgumentException("Invalid team");
      }
      int[] stats = division.get(team);
      return stats[REMAINING_GAMES_POSITION];
  }
    /**
     * Returns the number of remaining games between team1 and team2.
     * @param team1 the first team
     * @param team2 the second team
     * @return the number of remaining games between team1 and team2.
     */
  public int against(String team1, String team2) {
      if (!division.contains(team1) || !division.contains(team2)) {
          throw new IllegalArgumentException("Invalid team");
      }
      int[] stats1 = division.get(team1);
      int[] stats2 = division.get(team2);

      return stats1[REMAINING_DIVISION_START_POSITION + stats2[0]];
  }
    /**
     * Checks if the team is trivially eliminated
     * @param team the team to check
     * @return whether the team is eliminated
     */
  private boolean isTriviallyEliminated(String team) {
      // w[team] + r[team] < w[i] we eliminate
      for (String i: division.keys()) {
          if (division.get(team)[WINS_POSITION] + division.get(team)[REMAINING_GAMES_POSITION] < division.get(i)[WINS_POSITION]) {
              return true;
          }
      }
      return false;
  }
    /**
     * Checks whether the given team is eliminated.
     * @param team the team to check
     * @return whether it is eliminated
     */
  public boolean isEliminated(String team) {
      if (!division.contains(team)) {
          throw new IllegalArgumentException("Invalid team");
      }
      if (isTriviallyEliminated(team)) {
          return true;
      }
      FlowNetwork flowNetwork = getFlowNetwork(team);
      FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
      for (FlowEdge edge: flowNetwork.adj(0)) {
          if (edge.flow() != edge.capacity()) {
              return true;
          }
      }
    return false;
  }
  private FlowNetwork getFlowNetwork(String team) {
      int matchups = ((numberOfTeams() - 1) * numberOfTeams()) / 2;
      FlowNetwork flowNetwork = new FlowNetwork(matchups + numberOfTeams() + 2);
      int game = 1; // 0 is start node
      int maxWins = division.get(team)[WINS_POSITION] + division.get(team)[REMAINING_GAMES_POSITION];
      for (int x = 0; x < numberOfTeams(); x++) {
          String team1 = teams[x];
          for (int y = 0; y < x; y++) {
              String team2 = teams[y];
              flowNetwork.addEdge(new FlowEdge(0, game, against(team1, team2)));
              flowNetwork.addEdge(new FlowEdge(game, matchups + 1 + division.get(team1)[TEAM_NUMBER_POSITION], Double.POSITIVE_INFINITY));
              flowNetwork.addEdge(new FlowEdge(game, matchups + 1 + division.get(team2)[TEAM_NUMBER_POSITION], Double.POSITIVE_INFINITY));
              game++;
          }
          flowNetwork.addEdge(new FlowEdge(matchups + 1 + division.get(team1)[TEAM_NUMBER_POSITION], flowNetwork.V() - 1, maxWins - wins(team1)));
      }
      return flowNetwork;
  }
    /**
     * Subset R of teams that eliminates given team; null if not eliminated
     * @param team the team to check
     * @return a list of R teams that cause elimination of given team, null if not eliminated.
     */
  public Iterable<String> certificateOfElimination(String team) {
      if (!isEliminated(team)) {
          return null;
      }
      ArrayList<String> r = new ArrayList<>();
      if (isTriviallyEliminated(team)) {
          int maxWin = -1;
          String divLeader = "";
          for (String a: teams) {
              if (division.get(a)[WINS_POSITION] > maxWin) {
                  divLeader = a;
                  maxWin = division.get(a)[WINS_POSITION];
              }
          }
          r.add(divLeader);
          return r;
      }
      FlowNetwork flowNetwork = getFlowNetwork(team);
      FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
      int matchups = ((numberOfTeams() - 1) * numberOfTeams()) / 2;

      for (String a: teams) {
          if (fordFulkerson.inCut(matchups + 1 + division.get(a)[TEAM_NUMBER_POSITION])) {
              r.add(a);
          }
      }
      return r;
  }
  /**
   * Reads in a sports division from an input file and
   * prints whether each team is mathematically eliminated
   * and a certificate of elimination for each team that is eliminated.
   * @param args
   */
  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination("baseball-testing-files/teams4.txt");
    for (String team : division.teams()) {
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team)) {
                StdOut.print(t + " ");
            }
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }
    }
}
}
