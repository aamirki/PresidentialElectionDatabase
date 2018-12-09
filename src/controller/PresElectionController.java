package controller;

import model.ComparisonResults;
import model.DatabaseRetrieval;
import model.ElectionResults;
import view.PresElectionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Represents the controller for the Presidential Election Controller application, which acts
 * as an ActionListener and ItemListener to respond to action events. It will also give database
 * data to the view.
 *
 */
public class PresElectionController implements PresElectionControllerInterface,
    ActionListener, ItemListener {

  PresElectionView view;
  DatabaseRetrieval retrieval;

  /**
   * Initializes this Controller with the given view and DatabaseRetrieval objects, as well
   * as setting the metrics of the view and the view's action listener and item listener
   * as this.
   * @param view the View object where everything is displayed
   * @param retrieval the DatabaseRetrieval object
   */
  public PresElectionController(PresElectionView view, DatabaseRetrieval retrieval, String pass) {
    this.view = view;
    this.retrieval = retrieval;
    this.retrieval.setPass(pass);
    this.view.setMetricList(retrieval.getMetrics());
    this.view.setActionListener(this, this);
  }


  /**
   * Performs the action based on the command given
   * @param command the action command
   */
  private void performAction(String command) {
    int stateID1;
    int stateID2;
    switch (command) {
      case "compare":
        String comparisonMetric = this.view.getMetric();
        String name1 = view.getCountyOne() + ", " + view.getStateOne();
        String name2 = view.getCountyTwo() + ", " + view.getStateTwo();
        int year1 = view.getYearOne();
        int year2 = view.getYearTwo();
        stateID1 = retrieval.getStateID(view.getStateOne());
        stateID2 = retrieval.getStateID(view.getStateTwo());
        ElectionResults results1 =
            retrieval.getCountyTotalFromDatabase(retrieval.getCountyID(view.getCountyOne(),
                stateID1), year1);
        ElectionResults results2 =
            retrieval.getCountyTotalFromDatabase(retrieval.getCountyID(view.getCountyTwo(),
                stateID2), year2);
        setComparison(comparisonMetric, name1, name2, year1, year2, results1, results2);
        this.view.setFirstLabel(ComparisonResults.getResults(name1, year1,
            results1.getDemVotes(), results1.getRepVotes(), results1.getOtherVotes()));
        this.view.setSecondLabel(ComparisonResults.getResults(name2, year2,
            results2.getDemVotes(), results2.getRepVotes(), results2.getOtherVotes()));
        break;
      case "state one selected":
        view.setCountyOneList(retrieval.getCountiesFromDatabase(retrieval
            .getStateID(view.getStateOne())));
        break;
      case "state two selected":
        view.setCountyTwoList(retrieval
            .getCountiesFromDatabase(retrieval.getStateID(view.getStateTwo())));
        break;
      case "compare entire state":
        String comparisonMetric2 = this.view.getMetric();
        String stateName1 = view.getStateOne();
        String stateName2 = view.getStateTwo();
        int stateYear1 = view.getYearOne();
        int stateYear2 = view.getYearTwo();
        stateID1 = retrieval.getStateID(stateName1);
        stateID2 = retrieval.getStateID(stateName2);
        ElectionResults stateResults1 =
            retrieval
                .getStateTotalFromDatabase(retrieval.getStateID(view.getStateOne()), stateYear1);
        ElectionResults stateResults2 = retrieval
                .getStateTotalFromDatabase(retrieval.getStateID(view.getStateTwo()), stateYear2);
        setComparison(comparisonMetric2, stateName1,
            stateName2, stateYear1, stateYear2, stateResults1, stateResults2);
        if (comparisonMetric2.equalsIgnoreCase("white percentage")) {
          this.view.setComparisonLabel(ComparisonResults.demographicPercentage("White", stateName1,
              stateName2, stateYear1, stateYear2,
              retrieval.getWhitePercentage(stateID1, stateYear1),
              retrieval.getWhitePercentage(stateID2, stateYear2), stateResults1.getDemVotes(),
              stateResults2.getDemVotes(),
              stateResults1.getRepVotes(), stateResults2.getDemVotes()));
        } else if (comparisonMetric2.equalsIgnoreCase("black percentage")) {
          this.view.setComparisonLabel(ComparisonResults.demographicPercentage("Black", stateName1,
              stateName2, stateYear1, stateYear2,
              retrieval.getBlackPercentage(stateID1, stateYear1),
              retrieval.getBlackPercentage(stateID2, stateYear2), stateResults1.getDemVotes(),
              stateResults2.getDemVotes(),
              stateResults1.getRepVotes(), stateResults2.getDemVotes()));
        } else if (comparisonMetric2.equalsIgnoreCase("asian percentage")) {
          this.view.setComparisonLabel(ComparisonResults.demographicPercentage("Asian", stateName1,
              stateName2, stateYear1, stateYear2,
              retrieval.getAsianPercentage(stateID1, stateYear1),
              retrieval.getAsianPercentage(stateID2, stateYear2), stateResults1.getDemVotes(),
              stateResults2.getDemVotes(),
              stateResults1.getRepVotes(), stateResults2.getDemVotes()));
        } else if (comparisonMetric2.equalsIgnoreCase("native percentage")) {
          this.view.setComparisonLabel(ComparisonResults
                  .demographicPercentage("Native American/Hawaiian/Alaskan",
              stateName1, stateName2, stateYear1, stateYear2,
                      retrieval.getNativePercentage(stateID1, stateYear1),
              retrieval.getNativePercentage(stateID2, stateYear2),
                      stateResults1.getDemVotes(),
              stateResults2.getDemVotes(), stateResults1.getRepVotes(),
                      stateResults2.getDemVotes()));
        } else if (comparisonMetric2.equalsIgnoreCase("other race percentage")) {
          this.view.setComparisonLabel(ComparisonResults
              .demographicPercentage("Other race", stateName1,
              stateName2, stateYear1, stateYear2,
                  retrieval.getOtherRacePercentage(stateID1, stateYear1),
              retrieval.getOtherRacePercentage(stateID2, stateYear2),
                  stateResults1.getDemVotes(),
              stateResults2.getDemVotes(), stateResults1.getRepVotes(),
                  stateResults2.getDemVotes()));
        } else if (comparisonMetric2.equalsIgnoreCase("hispanic percentage")) {
          this.view.setComparisonLabel(ComparisonResults
              .demographicPercentage("Hispanic", stateName1,
              stateName2, stateYear1, stateYear2,
                  retrieval.getHispanicPercentage(stateID1, stateYear1),
              retrieval.getHispanicPercentage(stateID2, stateYear2),
                  stateResults1.getDemVotes(),
              stateResults2.getDemVotes(), stateResults1.getRepVotes(),
                  stateResults2.getDemVotes()));
        }
        this.view.setFirstLabel(ComparisonResults.getResults(stateName1, stateYear1,
            stateResults1.getDemVotes(),
            stateResults1.getRepVotes(), stateResults1.getOtherVotes()));
        this.view.setSecondLabel(ComparisonResults.getResults(stateName2, stateYear2,
            stateResults2.getDemVotes(),
            stateResults2.getRepVotes(), stateResults2.getOtherVotes()));
        break;
      default:
        throw new IllegalStateException("Unknown command: \"" + command + "\"");
    }
  }

  /**
   * Sets the comparison label.
   */
  private void setComparison(String comparisonMetric, String name1, String name2, int year1,
                             int year2, ElectionResults results1, ElectionResults results2) {
    if (comparisonMetric.equalsIgnoreCase("Democratic Percentage")) {
      this.view.setComparisonLabel(ComparisonResults.democraticPercentage(name1, name2, year1,
          year2, results1.getDemVotes(),
          results2.getDemVotes(), results1.getRepVotes(), results2.getRepVotes(),
          results1.getOtherVotes(), results2.getOtherVotes()));
    } else if (comparisonMetric.equalsIgnoreCase("Republican Percentage")) {
      this.view.setComparisonLabel(ComparisonResults.republicanPercentage(name1, name2, year1,
          year2, results1.getDemVotes(),
          results2.getDemVotes(), results1.getRepVotes(), results2.getRepVotes(),
          results1.getOtherVotes(), results2.getOtherVotes()));
    } else if (comparisonMetric.equalsIgnoreCase("Democratic Vote")) {
      this.view.setComparisonLabel(ComparisonResults.democraticVote(name1, name2, year1,
          year2, results1.getDemVotes(), results2.getDemVotes(),
          results1.getRepVotes(), results2.getRepVotes()));
    } else if (comparisonMetric.equalsIgnoreCase("Republican Vote")) {
      this.view.setComparisonLabel(ComparisonResults.republicanVote(name1, name2, year1,
          year2, results1.getDemVotes(), results2.getDemVotes(),
          results1.getRepVotes(), results2.getRepVotes()));
    } else {
      this.view.setComparisonLabel("County level data not yet available for Demographics");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Action performed");
    this.performAction(e.getActionCommand());
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    this.performAction(e.paramString());
  }


  public void start() {
    view.display();
  }
}
