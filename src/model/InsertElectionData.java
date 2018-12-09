package model;

import java.io.File;
import java.sql.*;

/**
 * A script to insert all the data into the tables of the Presidential Election Database. The public
 * method initEntireDatabase should only be called when the database has been created and the state
 * table has been properly filled up.
 */
public class InsertElectionData {

  private File resultsFile;
  private DatabaseRetrieval retrieval;
  private static int county_id = 1;
  private String pass;


  /**
   * Initializes this InsertElectionData object by initializing the DatabaseRetrieval object
   * and the resultsFile.
   */
  public InsertElectionData() {

    // Results File sourced from the CQ Voting and Elections Collection
    this.resultsFile = new File("resources/1972 to 2016 County and State.csv");
    this.retrieval = new DatabaseRetrieval();
    this.pass = "pass";
  }

  /**
   * Sets the password for the root connection database
   * @param pass the password
   */
  public void setPass(String pass) {
    this.pass = pass;
  }

  /**
   * Fills in the county table, the state results table, the county results table, and the
   * state demographics table. This operation will take a long time because it requires
   * parsing a very large csv file. The state table MUST be filled in before this is called.
   */
  public void initEntireDatabase() {

    // Inserts all the counties into the county table
    for (int state_id = 1; state_id < 52; state_id++) {
      this.insertCounties(state_id);
    }

    // Inserts all the state and county results into their respective tables, as well as state
    // demographics information
    for (int year = 1972; year <= 2016; year += 4) {
      this.insertStateResult(year);
      for (int state_id = 1; state_id < 52; state_id++) {
        this.insertCountyResult(state_id, year);
        this.insertDemographicData(state_id, year);
      }
    }
  }

  /**
   * Inserts all the counties to the county table of the given state.
   *
   * @param state_id the ID of the state of which to insert the counties
   */
  private void insertCounties(int state_id) {
    String stateName = retrieval.getStateName(state_id);
    String[] countyNames = retrieval.getCounties(stateName);


    for (String county_name : countyNames) {
      try {
        Connection conn
            = DriverManager.getConnection("jdbc:mysql://localhost:3306/presidentialElection",
            "root", pass);
        PreparedStatement statement
            = conn.prepareStatement("INSERT INTO county (county_id, state_id, county_name) "
            + "VALUES (?, ?, ?)");
        statement.setInt(1, county_id);
        statement.setInt(2, state_id);
        statement.setString(3, county_name);
        statement.execute();
        statement.close();
        conn.close();
        county_id++;
      } catch (SQLException ex) {
        ex.printStackTrace();
        throw new IllegalStateException("SQL Error: " + ex.getSQLState());
      }

    }
  }

  /**
   * Inserts all the state-wide election results from the given year.
   *
   * @param year the election year, must be between 1972 and 2016, inclusive, and must be
   *             divisible by four
   * @throws IllegalArgumentException if the year is less than 1972, greater than 2016, or
   *                                  not divisible by four.
   */
  private void insertStateResult(int year) {
    if (year > 2016 || year < 1972 || year % 4 != 0) {
      throw new IllegalArgumentException("Not a valid election year. Must be a presidential"
          + "election year between 1972 and 2016");
    }
    try {
      Connection conn
          = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/presidentialElection?useServerPrepStmts=false"
              + "&rewriteBatchedStatements=true",
          "root", pass);
      PreparedStatement statement
          = conn.prepareStatement("INSERT INTO `state election results` (election_year, state_id, "
          + "republican_votes, democratic_votes, third_party_votes) VALUES (?, ?, ?, ?, ?)");

      for (int i = 1; i < 52; i++) {
        int state_id = i;
        String state_name = retrieval.getStateName(state_id);
        statement.setInt(1, year);
        statement.setInt(2, state_id);
        ElectionResults results = retrieval.getStateTotalFromCSV(state_name, year);
        statement.setInt(3, results.getRepVotes());
        statement.setInt(4, results.getDemVotes());
        statement.setInt(5, results.getOtherVotes());
        statement.addBatch();
        System.out.println("Inserting state " + state_id + " in year " + year);
      }
      statement.executeBatch();
      statement.close();
      conn.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Error: " + ex.getSQLState());
    }
  }

  /**
   * Inserts all the county-wide results within the given state in the given year.
   *
   * @param state_id the ID of the state where the county is within
   * @param year the election year, must be between 1972 and 2016, inclusive, and must be
   *             divisible by four
   */
  public void insertCountyResult(int state_id, int year) {

    if (year > 2016 || year < 1972 || year % 4 != 0) {
      throw new IllegalArgumentException("Not a valid election year. Must be a presidential"
          + "election year between 1972 and 2016");
    }

    String state_name = retrieval.getStateName(state_id);
    try {
      Connection conn
          = DriverManager.getConnection(
              "jdbc:mysql://localhost:3306/presidentialElection?useServerPrepStmts=false"
              + "&rewriteBatchedStatements=true",
          "root", pass);

      PreparedStatement statement
          = conn.prepareStatement("INSERT INTO `county election results`(election_year, county_id, "
          + "republican_votes, democratic_votes, third_party_votes) VALUES (?, ?, ?, ?, ?) ");

      String[] countyList = retrieval.getCounties(state_name);

      for (int i = 0; i < countyList.length; i++) {
        String county_name = countyList[i];
        int countyID = retrieval.getCountyID(county_name, state_id);
        statement.setInt(1, year);
        statement.setInt(2, countyID);
        ElectionResults results = retrieval.getCountyTotalFromCSV(state_name, county_name, year);
        statement.setInt(3, results.getRepVotes());
        statement.setInt(4, results.getDemVotes());
        statement.setInt(5, results.getOtherVotes());
        statement.addBatch();
        System.out.println("Inserting county " + countyID + " in year " + year
            + " from state " + state_name);
      }
      statement.executeBatch();
      statement.close();
      conn.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Error: " + ex.getSQLState());
    }

  }

  /**
   * Inserts the demographic info of the given state in the census year closest to the given year.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   */
  public void insertDemographicData(int state_id, int year) {
    String state_name = retrieval.getStateName(state_id);
    DemographicData demographicData = retrieval.getDemographicDataFromCSV(state_name, year);
    try {
      Connection conn
          = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/presidentialElection?useServerPrepStmts=false"
              + "&rewriteBatchedStatements=true",
          "root", pass);

      PreparedStatement statement
          = conn.prepareStatement("INSERT INTO `state demographics` (census_year, state_id, "
          + "white_percentage, black_percentage, asian_percentage, native_american_percentage, "
          + "other_race_percentage, hispanic_percentage) "
          + "VALUE (?, ?, ?, ?, ?, ?, ?, ?)");
      statement.setInt(1, year);
      statement.setInt(2, state_id);
      statement.setFloat(3, demographicData.getWhitePercentage());
      statement.setFloat(4, demographicData.getBlackPercentage());
      statement.setFloat(5, demographicData.getAsianPercentage());
      statement.setFloat(6, demographicData.getNativePercentage());
      statement.setFloat(7, demographicData.getOtherPercentage());
      statement.setFloat(8, demographicData.getHispanicPercentage());
      statement.execute();
      statement.close();
      System.out.println("Inserted demographic info for " + year + " in " + state_name);
      conn.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Error: " + ex.getSQLState());
    }
  }


}
