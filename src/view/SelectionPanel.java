package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class SelectionPanel extends JPanel {

  private JComboBox<String> stateOne;
  private JComboBox<String> stateTwo;
  private JComboBox<String> countyOne;
  private JComboBox<String> countyTwo;
  private JComboBox<String> comparisonMetric;
  private JButton compareButton;

  SelectionPanel() {
    this.stateOne = new JComboBox<>();
    this.stateTwo = new JComboBox<>();
    this.countyOne = new JComboBox<>();
    this.countyTwo = new JComboBox<>();
    this.comparisonMetric = new JComboBox<>();
    compareButton = new JButton("Compare");
    BoxLayout overallLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
    this.setLayout(overallLayout);

    JPanel pickerOne = new JPanel();
    pickerOne.setLayout(new FlowLayout());
    pickerOne.add(stateOne);
    pickerOne.add(countyOne);

    JPanel pickerTwo = new JPanel();
    pickerTwo.setLayout(new FlowLayout());
    pickerTwo.add(stateTwo);
    pickerTwo.add(countyTwo);

    this.add(pickerOne);
    this.add(pickerTwo);

    JPanel selector = new JPanel();
    selector.setLayout(new FlowLayout());
    selector.add(comparisonMetric);
    selector.add(compareButton);

    this.add(selector);
  }

  private void setElements(String[] elementList, JComboBox<String> dropDown) {
    for (String element : elementList) {
      dropDown.addItem(element);
    }
  }

  void setStates(String[] stateList) {
    this.setElements(stateList, stateOne);
    this.setElements(stateList, stateTwo);
  }


  void setCountyOne(String[] countyList) {
    this.setElements(countyList, countyOne);
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

  void setActionListener(ActionListener listener) {
    compareButton.setActionCommand("compare");
    compareButton.addActionListener(listener);
  }

}
