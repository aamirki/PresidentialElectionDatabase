package view;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public interface PresElectionView {

  /**
   * Displays the view to the user.
   */
  void display();

  /**
   * Sets the action and item listener to the user
   * @param listener the action listener
   * @param itemListener the item listener
   */
  void setActionListener(ActionListener listener, ItemListener itemListener);

  /**
   * Sets the lists of states.
   *
   * @param stateList the list of states
   */
  void setStateList(String[] stateList);

  /**
   * Sets the list of counties for the first section.
   *
   * @param countyList the list of counties for the first section.
   */
  void setCountyOneList(String[] countyList);

  /**
   * Sets the list of counties for the second section.
   *
   * @param countyList the list of counties for the second section.
   */
  void setCountyTwoList(String[] countyList);

  /**
   * Sets the metrics of the application comparison feature.
   *
   * @param metrics the metrics of the application comparison feature.
   */
  void setMetricList(String[] metrics);

  /**
   * Retrieves the state selected in the first section.
   *
   * @return the state selected in the first section
   */
  String getStateOne();

  /**
   * Retrieves the state selected in the second section.
   *
   * @return the state selected in the second section
   */
  String getStateTwo();

  /**
   * Retrieves the county selected in the first section.
   *
   * @return the county selected in the first section
   */
  String getCountyOne();

  /**
   * Retrieves the county selected in the second section.
   *
   * @return the county selected in the second section
   */
  String getCountyTwo();

  /**
   * Retrieves the currently selected metric.
   *
   * @return the currently selected metric
   */
  String getMetric();

  /**
   * Sets the text of the comparison label.
   *
   * @param text the text to set
   */
  void setComparisonLabel(String text);

  /**
   * Sets the text of the first section label.
   *
   * @param text the text to set
   */
  void setFirstLabel(String text);

  /**
   * Sets the text of the second section label.
   *
   * @param text the text to set
   */
  void setSecondLabel(String text);

  /**
   * Retrieves the year selected in the first section.
   *
   * @return the year selected in the first section
   */
  int getYearOne();

  /**
   * Retrieves the year selected in the second section.
   *
   * @return the year selected in the second section
   */
  int getYearTwo();
}
