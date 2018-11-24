package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class StateCountyPicker extends JPanel {

  private JComboBox<String> stateOne;
  private JComboBox<String> stateTwo;
  private JComboBox<String> countyOne;
  private JComboBox<String> countyTwo;
  private JButton compareButton;

  StateCountyPicker() {
    this.stateOne = new JComboBox<>();
    this.stateTwo = new JComboBox<>();
    this.countyOne = new JComboBox<>();
    this.countyTwo = new JComboBox<>();
    compareButton = new JButton("Compare");
    FlowLayout flowLayout = new FlowLayout(); // top to bottom
    this.setLayout(flowLayout);

    JPanel pickerOne = new JPanel();
    FlowLayout springLayout1 = new FlowLayout();
    pickerOne.setLayout(springLayout1);
    pickerOne.add(stateOne);
    pickerOne.add(countyOne);

    JPanel pickerTwo = new JPanel();
    FlowLayout springLayout2 = new FlowLayout();
    pickerTwo.setLayout(springLayout2);
    pickerTwo.add(stateTwo);
    pickerTwo.add(countyTwo);

    JPanel bothPicker = new JPanel();
    bothPicker.setLayout(new BoxLayout(bothPicker, BoxLayout.Y_AXIS));
    bothPicker.add(pickerOne);
    bothPicker.add(pickerTwo);

    this.add(bothPicker);
    this.add(compareButton);

  }

  private void setElements(ArrayList<String> elementList, JComboBox<String> dropDown) {
    for (String element : elementList) {
      dropDown.addItem(element);
    }
  }

  void setStates(ArrayList<String> stateList) {
    this.setElements(stateList, stateOne);
    this.setElements(stateList, stateTwo);
  }

  void setCountyOne(ArrayList<String> countyList) {
    this.setElements(countyList, countyOne);
  }

  void setCountyTwo(ArrayList<String> countyList) {
    this.setElements(countyList, countyTwo);
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

  void setActionListener(ActionListener listener) {
    compareButton.setActionCommand("compare");
    compareButton.addActionListener(listener);
  }

}
