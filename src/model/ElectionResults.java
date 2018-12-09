package model;

/**
 * Represents the results of a presidential election in some jurisdiction in the United States.
 */
public final class ElectionResults {

  private final int totalVotes;
  private final int repVotes;
  private final int demVotes;
  private final int otherVotes;

  /**
   * Initializes this ElectionResults with the given vote totals.
   *
   * @param repVotes the number of votes for the Republican candidate
   * @param demVotes the number of votes for the Democrat candidate
   * @param otherVotes the number of votes for third parties and independent candidates
   * @param totalVotes the total number of votes in this election
   */
  public ElectionResults(int repVotes, int demVotes, int otherVotes, int totalVotes) {
    this.repVotes = repVotes;
    this.demVotes = demVotes;
    this.otherVotes = otherVotes;
    this.totalVotes = totalVotes;
  }

  /**
   * Retrieves the Republican votes in this election.
   *
   * @return the Republican votes in this election
   */
  public int getRepVotes() {
    return this.repVotes;
  }

  /**
   * Retrieves the Democrat votes in this election.
   *
   * @return the Democrat votes in this election
   */
  public int getDemVotes() {
    return this.demVotes;
  }

  /**
   * Retrieves the third party/independent votes in this election.
   *
   * @return the the third party/independent votes in this election.
   */
  public int getOtherVotes() {
    return this.otherVotes;
  }

  /**
   * Retrieves the total votes in this election.
   *
   * @return the total votes in this election
   */
  public int getTotalVotes() {
    return this.totalVotes;
  }

}