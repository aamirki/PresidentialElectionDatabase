package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PresElectionView extends JFrame {

  private SelectionPanel picker;
  private ActionListener listener;

  public PresElectionView() {

    this.picker = new SelectionPanel();

    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.setSize(new Dimension(500, 500));
    this.add(this.picker);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

  }

  public void display() {
    this.setVisible(true);
  }

  public void setActionListener(ActionListener listener) {
    this.listener = listener;
    this.picker.setActionListener(this.listener);
  }

  public void setStateList(String[] stateList) {
    this.picker.setStates(stateList);
  }

  public void setCountyOneList(String[] countyList) {
    this.picker.setCountyOne(countyList);
  }

  public void setCountyTwoList(String[] countyList) {
    this.picker.setCountyTwo(countyList);
  }

  public void setMetricList(String[] metrics) {
    this.picker.setMetrics(metrics);
  }

}
