package model;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Represents a class of static methods to generate Strings to show comparisons between different
 * states/counties across different metrics.
 */
public class ComparisonResults {

  /**
   * Makes a String explaining the democratic percentage difference between the first and
   * second jurisdiction.
   *
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @param otherP1 the votes for other candidates in the first jurisdiction
   * @param otherP2 the votes for other candidates in the second jurisdiction
   * @return a String representing a comparision between the first and second jurisdiction's
   *         democratic percentage
   */
  public static String democraticPercentage(String name1, String name2, int year1, int year2,
                                            int demP1, int demP2, int repP1, int repP2, int otherP1, int otherP2) {

    String comparison = comparisonBeginning(name1, name2, year1, year2, demP1, demP2, repP1, repP2);

    double demPercentage1 = ((double) demP1 / (demP1 + repP1 + otherP1)) * 100;
    double demPercentage2 = ((double) demP2 / (demP2 + repP2 + otherP2)) * 100;

    double demPChange = percentageChange(demPercentage2, demPercentage1);
    String changeString = getRoundedString(Math.abs(demPChange));
    if (demPChange < 0) {
      comparison += "\nThe Democrats' vote percentage was " + changeString + "% higher in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    } else if (demPChange > 0) {
      comparison += "\nThe Democrats' vote percentage was " + changeString + "% lower in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    }
    return comparison;
  }

  /**
   * Makes a String explaining the republican percentage difference between the first and
   * second jurisdiction.
   *
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @param otherP1 the votes for other candidates in the first jurisdiction
   * @param otherP2 the votes for other candidates in the second jurisdiction
   * @return a String representing a comparision between the first and second jurisdiction's
   *         republican percentage
   */
  public static String republicanPercentage(String name1, String name2, int year1, int year2,
                                            int demP1, int demP2, int repP1, int repP2, int otherP1, int otherP2) {

    String comparison = comparisonBeginning(name1, name2, year1, year2, demP1, demP2, repP1, repP2);

    double repPercentage1 = ((double) repP1 / (demP1 + repP1 + otherP1)) * 100;
    double repPercentage2 = ((double) repP2 / (demP2 + repP2 + otherP2)) * 100;

    double repPChange = percentageChange(repPercentage2, repPercentage1);
    String changeString = getRoundedString(Math.abs(repPChange));
    if (repPChange < 0) {
      comparison += "\nThe Republicans' vote percentage was " + changeString + "% higher in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    } else if (repPChange > 0) {
      comparison += "\nThe Republicans' vote percentage was " + changeString + "% lower in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    }
    return comparison;
  }

  /**
   * Makes a String explaining the democratic vote total difference between the first and
   * second jurisdiction.
   *
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @return a String representing a comparision between the first and second jurisdiction's
   *         democratic vote total
   */
  public static String democraticVote(String name1, String name2, int year1, int year2,
                                      int demP1, int demP2, int repP1, int repP2) {
    String comparison = comparisonBeginning(name1, name2, year1, year2, demP1, demP2, repP1, repP2);
    int demVoteChange = demP2 - demP1;
    if (demVoteChange > 0) {
      comparison += "\nThe Democrats' vote count was " + demVoteChange + " higher in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    } else if (demVoteChange < 0) {
      comparison += "\nThe Democrats' vote count was " + Math.abs(demVoteChange) + " lower in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    }
    return comparison;
  }

  /**
   * Makes a String explaining the republican vote total difference between the first and
   * second jurisdiction.
   *
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @return a String representing a comparision between the first and second jurisdiction's
   *         republican vote total
   */
  public static String republicanVote(String name1, String name2, int year1, int year2,
                                      int demP1, int demP2, int repP1, int repP2) {
    String comparison = comparisonBeginning(name1, name2, year1, year2, demP1, demP2, repP1, repP2);
    int repVoteChange = repP2 - repP1;
    if (repVoteChange > 0) {
      comparison += "\nThe Republicans' vote count was " + repVoteChange + " higher in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    } else if (repVoteChange < 0) {
      comparison += "\nThe Republicans' vote count was " + Math.abs(repVoteChange) + " lower in " + name2
          + " in " + year2 + " compared to in " + name1 + " in " + year1;
    }
    return comparison;
  }

  /**
   * Returns a String showing who won in the the first and second jurisdictions.
   *
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @return a String showing who won in the first and second jurisdiction
   */
  private static String comparisonBeginning(String name1, String name2, int year1, int year2,
                                           int demP1, int demP2, int repP1, int repP2) {
    String comparison = "";

    String winner1;
    if (demP1 > repP1) {
      winner1 = Party.DEMOCRATIC.getName();
    } else {
      winner1 = Party.REPUBLICAN.getName();
    }
    comparison += "The " + winner1 + " won in " + name1 + " in " + year1 + ". ";
    String winner2;
    if (demP2 > repP2) {
      winner2 = Party.DEMOCRATIC.getName();
    } else {
      winner2 = Party.REPUBLICAN.getName();
    }
    comparison += "\nThe " + winner2 + " won in " + name2 + " in " + year2 + ". ";

    return comparison;
  }

  /**
   * Returns the vote total and percentage String for each party for the given jurisdiction.
   *
   * @param name the name of the jurisdiction
   * @param year the year of the election
   * @param demV the vote total for the Democrats
   * @param repV the vote total for the Republicans
   * @param otherV the vote total for other candidates
   * @return the vote total and percentage String for each party for the given jurisdiction
   */
  public static String getResults(String name, int year, int demV, int repV, int otherV) {
    String complete = "Presidential Election Results in " + name + " - " + year;
    int total = demV + repV + otherV;
    String demP = getRoundedString(((double) demV / total) * 100);
    String repP = getRoundedString(((double) repV / total) * 100);
    String otherP = getRoundedString(((double) otherV / total) * 100);
    if (demV > repV) {
      complete += "\nDemocrat Votes: " + demV + " (" + demP + "%)";
      complete += "\nRepublican Votes: " + repV + " (" + repP + "%)";
      complete += "\nOther Votes: " + otherV + " (" + otherP + "%)";
    } else {
      complete += "\nRepublican Votes: " + repV + " (" + repP + "%)";
      complete += "\nDemocrat Votes: " + demV + " (" + demP + "%)";
      complete += "\nOther Votes: " + otherV + " (" + otherP + "%)";
    }
    return complete;
  }

  /**
   * Returns a String representing the demographic difference between the given demographics in
   * the first jurisdiction vs the second jurisdiction.
   *
   * @param demo the demographic to compare
   * @param name1 the name of the first jurisdiction
   * @param name2 the name of the second jurisdiction
   * @param year1 the year of the election in the first jurisdiction
   * @param year2 the year of the election in the second jurisdiction
   * @param demoP1 the percentage of demo in the first jurisdiction
   * @param demoP2 the percentage of demo in the second jurisdiction
   * @param demP1 the votes for Democrats in the first jurisdiction
   * @param demP2 the votes for Democrats in the second jurisdiction
   * @param repP1 the votes for Republicans in the first jurisdiction
   * @param repP2 the votes for Republicans in the second jurisdiction
   * @return a String representing the demographic difference between the given demographics in
   *         the first jurisdiction vs the second jurisdiction
   */
  public static String demographicPercentage(String demo, String name1, String name2,
                                             int year1, int year2, float demoP1, float demoP2,
                                             int demP1, int demP2, int repP1, int repP2) {
    String comparison = comparisonBeginning(name1, name2, year1, year2, demP1, demP2, repP1, repP2);
    if (demoP1 < demoP2) {
      comparison += "\nThe " + demo + " percentage in " + name2 + " ("
          + getRoundedString((double) demoP1) + "%) is "
          + getRoundedString(Math.abs(demoP2 - demoP1)) + "% less than in "
          + name1 + " ("
          + getRoundedString((double) demoP2) + "%)";
    } else if (demoP1 > demoP2) {
      comparison += "\nThe " + demo + " percentage in " + name2 + " ("
          + getRoundedString((double) demoP1) + "%) is "
          + getRoundedString(Math.abs(demoP1 - demoP2))
          + "% greater than in " + name1 + " ("
          + getRoundedString((double) demoP2) + "%)";
    }
    return comparison;
  }

  /**
   * Returns the given double as a String rounded to the third decimal place.
   *
   * @param val the double value to round
   * @return the given double as a String rounded to the third decimal place
   */
  private static String getRoundedString(double val) {
    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.CEILING);
    return df.format(val);
  }

  /**
   * Returns the percentage change between p1 and p2
   * @param p1 the original value
   * @param p2 the new value
   * @return the percentage change between p1 and p2
   */
  private static double percentageChange(double p1, double p2) {
    return ((p2 - p1) / p1) * 100;
  }


}

/**
 * Represents the name of the parties.
 */
enum Party {

  DEMOCRATIC("Democrats"), REPUBLICAN("Republicans"), OTHER("Third Parties and Others");

  private String name;

  Party(String name) {
    this.name = name;
  }

  String getName() {
    return this.name;
  }
}
