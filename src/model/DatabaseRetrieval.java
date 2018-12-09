package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Locale;

/**
 * Represents a class to retrieve data from the Presidential Election Database or from the
 * corresponding CSV files to fill up the database.
 */
public class DatabaseRetrieval {

  private Connection conn;
  private File file;
  private String pass;

  /**
   * Initializes this DatabaseRetrieval and the election result CSV file and connection to the
   * database.
   */
  public DatabaseRetrieval() {

    file = new File("resources/1972 to 2016 County and State.csv");
    this.pass = "pass";

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/presidentialElection",
          "root", pass);
    } catch (SQLException ex) {
      throw new IllegalStateException("Could not connect to database. Error stack:\n"
          + Arrays.toString(ex.getStackTrace()));
    }

  }

  /**
   * Sets the password for the root connection database
   * @param pass the password
   */
  public void setPass(String pass) {
    this.pass = pass;
  }

  /**
   * Retrieves the demographic of the given state in the closest census year of the year given. It
   * does this by parsing through a CSV file for the state. DO NOT use this method unless the
   * database must be initialized. Use the specific getter methods for each demographic group
   * instead.
   *
   * @param state the name of the state. MUST be in proper title case,
   *              ex: Alabama, New Hampshire, New York, District of Columbia
   * @param year the year that the election has taken place in
   * @return the demographic data of the given state in the census year closest to the given
   *         year
   */
  DemographicData getDemographicDataFromCSV(String state, int year) {
    int censusYear;
    if (year < 1975) {
      censusYear = 1970;
    } else if (year < 1985) {
      censusYear = 1980;
    } else if (year < 1995) {
      censusYear = 1990;
    } else if (year < 2005) {
      return this.get21stDemographicsCSV(state, 2000);
    } else {
      return this.get21stDemographicsCSV(state, 2010);
    }

    Scanner scan;
    try {
      scan = new Scanner(new File("resources/DemographicData/" + state + ".csv"));
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("Could not find file");
    }

    int totalPop = 0;
    int whitePop = 0;
    int blackPop = 0;
    int nativePop = 0;
    int asianPop = 0;
    int otherPop = 0;
    int hispanicPop = 0;

    scan.useDelimiter("\n");

    while (scan.hasNext()) {
      String[] line = scan.next().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      if (line[0].startsWith(Integer.toString(censusYear))) {
        try {
          totalPop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[1].replace("\"", "")).intValue();
          whitePop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[2].replace("\"", "")).intValue();
          blackPop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[3].replace("\"", "")).intValue();
          nativePop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[4].replace("\"", "")).intValue();
          asianPop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[5].replace("\"", "")).intValue();
          otherPop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[6].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          ex.printStackTrace();
          throw new IllegalStateException("Could not load data for race by state");
        }
        try {
          hispanicPop =
              NumberFormat.getNumberInstance(Locale.US).parse(line[7].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          System.out.println("Could not load data for ethnicity by state");
        }

        DemographicData data =
            new DemographicData(whitePop, blackPop, asianPop, nativePop, otherPop, totalPop);
        data.setEthnicity(hispanicPop);
        return data;
      }
    }
    throw new IllegalStateException("Could not find any demographic data");
  }

  /**
   * Using their CSV files, retrieves demographic data from the given state in the given year(either
   * 2000 or 2010)
   *
   * @param state the state to get the demographic information from
   * @param year the year, either 2000 or 2010
   * @return demographic information for the given census year in the given state
   * @throws IllegalArgumentException if the year given is not either 2000 or 2010
   * @throws IllegalStateException if the data could not be found in the CSV file
   */
  private DemographicData get21stDemographicsCSV(String state, int year) {

    Scanner scan;
    try {
      if (year == 2000) {
        scan = new Scanner(new File("resources/DemographicData/census2000demographics.csv"));
      } else if (year == 2010) {
        scan = new Scanner(new File("resources/DemographicData/census2010demographics.csv"));
      } else {
        throw new IllegalArgumentException("Invalid year: Must be either 2000 or 2010.");
      }
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("Could not find file");
    }

    int totalPop = 0;
    int whitePop = 0;
    int blackPop = 0;
    int nativePop = 0;
    int asianPop = 0;
    int otherPop = 0;
    int hispanicPop = 0;

    scan.useDelimiter("\n");

    while (scan.hasNext()) {
      String[] line = scan.next().split(",");
      if (line[0].equalsIgnoreCase(state)) {
        try {
          totalPop = Integer.parseInt(line[1]);
          whitePop = Integer.parseInt(line[2]);
          blackPop = Integer.parseInt(line[3]);
          asianPop = Integer.parseInt(line[4]);
          otherPop = Integer.parseInt(line[5]);
          hispanicPop = Integer.parseInt(line[6]);
          nativePop = Integer.parseInt(line[8]);
        } catch (NumberFormatException ex) {
          ex.printStackTrace();
          throw new IllegalStateException("Could not load data for race by state");
        }
        DemographicData data =
            new DemographicData(whitePop, blackPop, asianPop, nativePop, otherPop, totalPop);
        data.setEthnicity(hispanicPop);
        return data;
      }
    }
    throw new IllegalStateException("Could not find any demographic data");
  }

  /**
   * From the database, retrieves the array of the names of all the states.
   *
   * @return the array of the state names in the database
   */
  public String[] getStates() {
    try {
      Statement statement = conn.createStatement();
      ResultSet results = statement.executeQuery("SELECT * FROM state");
      String[] states = new String[51];
      int currentState = 0;
      while (results.next()) {
        states[currentState] = results.getString("state_name");
        System.out.println(results.getString("state_name"));
        currentState++;
      }
      results.close();
      return states;
    } catch (SQLException ex) {
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * From the database, retrieves the state_id that corresponds with the given state name.
   *
   * @param stateName the name of the state
   * @return the ID of the given state
   */
  public int getStateID(String stateName) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results
          = statement.executeQuery("SELECT * FROM state WHERE state_name = " + stateName);
      results.next();
      int state_id = results.getInt("state_id");
      results.close();
      return state_id;
    } catch (SQLException ex) {
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * From the database, retrieves the state_name value that corresponds with the given state_id.
   *
   * @param state_id the ID of the state
   * @return the state_name that matches with the state_id given
   */
  public String getStateName(int state_id) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results
          = statement.executeQuery("SELECT * FROM state WHERE state_id = " + state_id);
      results.next();
      String stateName = results.getString("state_name");
      results.close();
      return stateName;
    } catch (SQLException ex) {
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * From the database, retrieves the county_id value that corresponds with the given
   * state_id and countyName.
   *
   * @param countyName the name of the county
   * @param state_id the ID of the state the county is in
   * @return the county_id that matches with the county name and state_id given
   */
  public int getCountyID(String countyName, int state_id) {
    try {
      Statement statement = conn.createStatement();
      if (countyName.contains("'")) {
        countyName = countyName.replace("'", "''");
      }
      ResultSet results
          = statement.executeQuery("SELECT * FROM county WHERE county_name = '" + countyName
          + "' AND state_id = " + state_id);
      results.next();
      return results.getInt("county_id");
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * Retrieves the metrics that can be used to compare data.
   *
   * @return a String array of the comparison metrics supported by the application
   */
  public String[] getMetrics() {
    return new String[] {
        "Democratic Percentage",
        "Republican Percentage",
        "Democratic Vote",
        "Republican Vote",
        "White Percentage",
        "Black Percentage",
        "Asian Percentage",
        "Native Percentage",
        "Other Race Percentage",
        "Hispanic Percentage"
    };
  }

  /**
   * Retrieves the names of the counties from the database from the given state.
   *
   * @param state_id the state which the counties are in
   * @return an array containing the names of the counties from this state
   */
  public String[] getCountiesFromDatabase(int state_id) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results
          = statement.executeQuery("SELECT * FROM county WHERE state_id = " + state_id);
      ArrayList<String> x = new ArrayList<>();
      while (results.next()) {
        x.add(results.getString("county_name"));
      }
      results.close();
      return x.toArray(new String[0]);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * Retrieves the counties of the given state in a String array. It retrieves this from a CSV
   * file, use getCountiesFromDatabase to more quickly retrieve this data from the initialized
   * database.
   * @param state the name of the state
   * @return an array of the name of the counties of the given state
   */
  String[] getCounties(String state) {
    Scanner scan;
    try {
      scan = new Scanner(file);
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("Could not find file");
    }

    scan.useDelimiter("\n");

    boolean notRepeat = true;

    ArrayList<String> countyList = new ArrayList<>();

    while (scan.hasNext() && notRepeat) {
      String[] line = scan.next().split(",");
      if (line[1].equalsIgnoreCase("PRESIDENT") && line[2].equalsIgnoreCase(state)) {
        String countyName = line[5];
        if (countyList.contains(countyName)) {
          notRepeat = false;
        } else {
          countyList.add(countyName);
        }
      }
    }

    String[] countyArray = new String[countyList.size()];

    for (int i = 0; i < countyList.size(); i++) {
      countyArray[i] = countyList.get(i);
    }

    return countyArray;
  }

  /**
   * Retrieves the state-wide election results in the given year in the given state from the
   * database.
   *
   * @param state_id the ID of the state to get the results from
   * @param year the year to get the results from
   * @return the election results of the given state in the given year
   */
  public ElectionResults getStateTotalFromDatabase(int state_id, int year) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results
          = statement.executeQuery("SELECT * FROM `state election results` WHERE state_id = "
          + state_id + " AND election_year = " + year);
      results.next();
      int demVotes = results.getInt("democratic_votes");
      int repVotes = results.getInt("republican_votes");
      int otherVotes = results.getInt("third_party_votes");
      results.close();
      return new ElectionResults(repVotes, demVotes, otherVotes, repVotes + demVotes + otherVotes);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * Retrieves the county-wide election results in the given year in the given county from the
   * database.
   *
   * @param county_id the ID of the county to get the results from
   * @param year the year to get the results from
   * @return the election results of the given county in the given year
   */
  public ElectionResults getCountyTotalFromDatabase(int county_id, int year) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results
          = statement.executeQuery("SELECT * FROM `county election results` WHERE county_id = "
          + county_id + " AND election_year = " + year);
      results.next();
      int demVotes = results.getInt("democratic_votes");
      int repVotes = results.getInt("republican_votes");
      int otherVotes = results.getInt("third_party_votes");
      results.close();
      return new ElectionResults(repVotes, demVotes, otherVotes, repVotes + demVotes + otherVotes);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * Retrieves the state-wide election results from the given state in the given year from
   * the CSV file. This method is NOT meant to be used after the database has been created, as
   * it is very slow due to it manually parsing through the CSV file. Use getStateTotalFromDatabase
   * to make a direct call to the database instead once the database has been initialized with
   * data.
   *
   * @param state the name of the state
   * @param year the election year
   * @return the election results from the given state in the given election year
   */
  ElectionResults getStateTotalFromCSV(String state, int year) {
    Scanner scan;
    try {
      scan = new Scanner(file);
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("Could not find file");
    }

    int totalVotes = 0;
    int redVotes = 0;
    int blueVotes = 0;
    int otherVotes = 0;

    scan.useDelimiter("\n");

    while (scan.hasNext()) {
      String[] line = scan.next().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      if (line[0].equals(Integer.toString(year)) && line[1].equalsIgnoreCase("N/A")
          && line[2].equalsIgnoreCase(state)) {
        try {
          totalVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[3].replace("\"", "")).intValue();
          redVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[4].replace("\"", "")).intValue();
          blueVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[5].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          ex.printStackTrace();
          throw new IllegalStateException("Could not load data for vote totals by state");
        }
        try {
          otherVotes
              += NumberFormat.getNumberInstance(Locale.US).parse(line[6].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          System.out.println("Third Party had a non-valid vote total");
        }
        try {
          otherVotes += NumberFormat.getNumberInstance(Locale.US).parse(line[7].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          System.out.println("Other had a non-valid vote total");
        }

        return new ElectionResults(redVotes, blueVotes, otherVotes, totalVotes);

      }
    }
    return new ElectionResults(redVotes, blueVotes, otherVotes, totalVotes);
  }

  /**
   * Retrieves the county-wide election results from the given state in the given county
   * in the given year from the CSV file. This method is NOT meant to be used after the database
   * has been created, as it is very slow due to it manually parsing through the CSV file.
   * Use getCountyTotalFromDatabase to make a direct call to the database instead once the
   * database has been initialized with data.
   *
   * @param state the name of the state
   * @param county the name of the county
   * @param year the election year
   * @return the election results from the given county in the given
   *         state in the given election year
   */
  ElectionResults getCountyTotalFromCSV(String state, String county, int year) {
    Scanner scan;
    try {
      scan = new Scanner(file);
    } catch (FileNotFoundException ex) {
      throw new IllegalStateException("Could not find file");
    }

    int totalVotes = 0;
    int redVotes = 0;
    int blueVotes = 0;
    int otherVotes = 0;

    scan.useDelimiter("\n");

    while (scan.hasNext()) {
      String[] line = scan.next().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      if (line[0].equals(Integer.toString(year)) && line[1].equalsIgnoreCase("PRESIDENT")
          && line[2].equalsIgnoreCase(state) && line[5].equalsIgnoreCase(county)) {
        try {
          if (line[7].equalsIgnoreCase("N/A")
              || line[8].equalsIgnoreCase("N/A")
              || line[11].equalsIgnoreCase("N/A")) {
            return new ElectionResults(redVotes, blueVotes, otherVotes, totalVotes);
          }
          totalVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[7].replace("\"", "")).intValue();
          redVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[8].replace("\"", "")).intValue();
          blueVotes
              = NumberFormat.getNumberInstance(Locale.US).parse(line[11].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          ex.printStackTrace();
          throw new IllegalStateException("Could not load data for vote totals by state");
        }
        try {
          otherVotes
              += NumberFormat.getNumberInstance(Locale.US).parse(line[15].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          otherVotes += 0;
        }
        try {
          otherVotes += NumberFormat.getNumberInstance(Locale.US).parse(line[18].replace("\"", "")).intValue();
        } catch (ParseException ex) {
          otherVotes += 0;
        }

        return new ElectionResults(redVotes, blueVotes, otherVotes, totalVotes);

      }
    }
    return new ElectionResults(redVotes, blueVotes, otherVotes, totalVotes);
  }

  /**
   * Retrieves the given demographic percentage from the database in the given state in the
   * given year's closest census year.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @param demographic the name of the demographic, must be one of:
   *                    white_percentage, black_percentage, asian_percentage,
   *                    native_american_percentage, other_race_percentage,
   *                    hispanic_percentage, or non_hispanic_percentage
   * @return the demographic percentage of the given demographic in the given state in the
   *         census year closest to the given year
   */
  private float getDemographicPercentage(int state_id, int year, String demographic) {
    try {
      Statement statement = conn.createStatement();
      ResultSet results = statement.executeQuery("SELECT * FROM `state demographics` "
          + "WHERE census_year = " + year + " AND state_id = " + state_id);
      results.next();
      float percentage = results.getFloat(demographic);
      results.close();
      return percentage;
    } catch (SQLException ex) {
      throw new IllegalStateException("SQL Exception: " + ex.getSQLState());
    }
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially, white
   * in the census year closest to the given year. This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the white percentage in the given state in the census year closest to the given year
   */
  public float getWhitePercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "white_percentage");
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially, black
   * in the census year closest to the given year. This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the black percentage in the given state in the census year closest to the given year
   */
  public float getBlackPercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "black_percentage");
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially, asian
   * in the census year closest to the given year. This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the asian percentage in the given state in the census year closest to the given year
   */
  public float getAsianPercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "asian_percentage");
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially:
   * Native American, Hawaiian, or Alaskan in the census year closest to the given year.
   * This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the native percentage in the given state in the census year closest to the given year
   */
  public float getNativePercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "native_american_percentage");
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially, a race
   * other than white, black, asian, native american, native alaskan, or native hawaiian
   * in the census year closest to the given year. This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the white percentage in the given state in the census year closest to the given year
   */
  public float getOtherRacePercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "other_race_percentage");
  }

  /**
   * Retrieves the percentage of the given state that identifies as, at least partially,
   * hispanic (of any race) in the census year closest to the given year.
   * This operation is done from the database.
   *
   * @param state_id the ID of the state
   * @param year the year of the election
   * @return the white percentage in the given state in the census year closest to the given year
   */
  public float getHispanicPercentage(int state_id, int year) {
    return this.getDemographicPercentage(state_id, year, "hispanic_percentage");
  }

}