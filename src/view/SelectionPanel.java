package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class SelectionPanel extends JPanel {

  private JComboBox<String> stateOne;
  private JComboBox<String> stateTwo;
  private JComboBox<String> countyOne;
  private JComboBox<String> countyTwo;
  private JComboBox<Integer> yearOne;
  private JComboBox<Integer> yearTwo;
  private JComboBox<String> comparisonMetric;
  private JButton compareButton;
  private JButton compareEntireState;

  SelectionPanel() {
    this.stateOne = new JComboBox<>();
    this.stateTwo = new JComboBox<>();
    this.countyOne = new JComboBox<>();
    this.countyTwo = new JComboBox<>();
    this.yearOne = new JComboBox<>();
    this.yearTwo = new JComboBox<>();
    this.comparisonMetric = new JComboBox<>();
    compareButton = new JButton("Compare");
    compareEntireState = new JButton("Compare Entire State");
    BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
    this.setLayout(overallLayout);

    JPanel pickerOne = new JPanel();
    pickerOne.setLayout(new FlowLayout());
    pickerOne.add(stateOne);
    pickerOne.add(countyOne);
    pickerOne.add(yearOne);

    JPanel pickerTwo = new JPanel();
    pickerTwo.setLayout(new FlowLayout());
    pickerTwo.add(stateTwo);
    pickerTwo.add(countyTwo);
    pickerTwo.add(yearTwo);

    this.add(pickerOne);
    this.add(pickerTwo);

    JPanel selector = new JPanel();
    selector.setLayout(new FlowLayout());
    selector.add(comparisonMetric);
    selector.add(compareButton);
    selector.add(compareEntireState);

    this.add(selector);

    Integer[] years = {
      2016, 2012, 2008, 2004, 2000, 1996, 1992, 1988, 1984, 1980, 1976, 1972
    };

    this.setElements(years, yearOne);
    this.setElements(years, yearTwo);



  }

  private <T> void setElements(T[] elementList, JComboBox<T> dropDown) {
    dropDown.removeAllItems();
    for (T element : elementList) {
      dropDown.addItem(element);
    }
  }

  void setStates(String[] stateList) {
    this.setElements(stateList, stateOne);
    this.setElements(stateList, stateTwo);
  }


  void setCountyOne(String[] countyList) {
    this.setElements(countyList, countyOne);
    System.out.println("Set elements");
  }

  void setCountyTwo(String[] countyList) {
    this.setElements(countyList, countyTwo);
  }

  void setMetrics(String[] metricList) {
    this.setElements(metricList, comparisonMetric);
  }

  String getStateOne() {
    return (String) this.stateOne.getSelectedItem();
  }

  String getStateTwo() {
    return (String) this.stateTwo.getSelectedItem();
  }

  String getCountyOne() {
    return (String) this.countyOne.getSelectedItem();
  }

  String getCountyTwo() {
    return (String) this.countyTwo.getSelectedItem();
  }

  String getMetric() {
    return (String) this.comparisonMetric.getSelectedItem();
  }

  public int getYearOne() {
    try {
      return (Integer) this.yearOne.getSelectedItem();
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("Fatal error");
    }
  }

  public int getYearTwo() {
    try {
      return (Integer) this.yearTwo.getSelectedItem();
    } catch (NullPointerException ex) {
      ex.printStackTrace();
      throw new IllegalStateException("Fatal error");
    }
  }

  void setActionListener(ActionListener listener, ItemListener itemListener) {
    compareButton.setActionCommand("compare");
    compareButton.addActionListener(listener);
    compareEntireState.setActionCommand("compare entire state");
    compareEntireState.addActionListener(listener);
    stateOne.setActionCommand("state one selected");
    stateOne.addActionListener(listener);
    stateTwo.setActionCommand("state two selected");
    stateTwo.addActionListener(listener);
  }

}
