package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Represents a Swing View implementation of PresElectionView.
 */
public class PresElectionSwingView extends JFrame implements PresElectionView {

  private SelectionPanel picker;
  private JTextArea comparisonLabel;
  private JTextArea firstLabel;
  private JTextArea secondLabel;

  public PresElectionSwingView() {

    this.setTitle("Presidential Election Database");

    this.picker = new SelectionPanel();
    this.comparisonLabel = new JTextArea("", 100, 12);
    this.firstLabel = new JTextArea("", 100, 12);
    this.secondLabel = new JTextArea("", 100, 12);
    this.comparisonLabel.setEditable(false);
    this.firstLabel.setEditable(false);
    this.secondLabel.setEditable(false);
    this.comparisonLabel.setBackground(Color.WHITE);
    this.firstLabel.setBackground(Color.WHITE);
    this.secondLabel.setBackground(Color.WHITE);
    this.comparisonLabel.setLineWrap(true);
    this.comparisonLabel.setWrapStyleWord(true);
    this.firstLabel.setLineWrap(true);
    this.firstLabel.setWrapStyleWord(true);
    this.secondLabel.setLineWrap(true);
    this.secondLabel.setWrapStyleWord(true);

    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.setSize(new Dimension(500, 500));
    this.add(this.picker);
    this.add(this.comparisonLabel);
    this.add(this.firstLabel);
    this.add(this.secondLabel);
    this.picker.setBackground(Color.WHITE);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setBackground(Color.LIGHT_GRAY);

  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void setActionListener(ActionListener listener, ItemListener itemListener) {
    this.picker.setActionListener(listener, itemListener);
  }

  @Override
  public void setStateList(String[] stateList) {
    this.picker.setStates(stateList);
  }

  @Override
  public void setCountyOneList(String[] countyList) {
    this.picker.setCountyOne(countyList);
  }

  @Override
  public void setCountyTwoList(String[] countyList) {
    this.picker.setCountyTwo(countyList);
  }

  @Override
  public void setMetricList(String[] metrics) {
    this.picker.setMetrics(metrics);
  }

  @Override
  public String getStateOne() {
    return picker.getStateOne();
  }

  @Override
  public String getStateTwo() {
    return picker.getStateTwo();
  }

  @Override
  public String getCountyOne() {
    return picker.getCountyOne();
  }

  @Override
  public String getCountyTwo() {
    return picker.getCountyTwo();
  }

  @Override
  public String getMetric() {
    return picker.getMetric();
  }

  @Override
  public void setComparisonLabel(String text) {
    this.comparisonLabel.setText(text);
  }

  @Override
  public void setFirstLabel(String text) {
    this.firstLabel.setText(text);
  }

  @Override
  public void setSecondLabel(String text) {
    this.secondLabel.setText(text);
  }

  @Override
  public int getYearOne() {
    return picker.getYearOne();
  }

  @Override
  public int getYearTwo() {
    return picker.getYearTwo();
  }



}
