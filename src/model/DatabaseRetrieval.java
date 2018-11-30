package model;

import java.sql.*;
import java.util.Arrays;

public class DatabaseRetrieval {

  private Connection conn;

  public DatabaseRetrieval() {

    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/presidentialElection",
          "root", "pass");
    } catch (SQLException ex) {
      throw new IllegalStateException("Could not connect to database. Error stack:\n"
          + Arrays.toString(ex.getStackTrace()));
    }

  }

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

  public String[] getMetrics() {
    return new String[] {
      "Election Results",
      "Ethnicity"
    };
  }





}
