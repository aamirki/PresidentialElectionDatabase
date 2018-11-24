package view;
import javax.swing.*;
import java.awt.event.ActionListener;

public class PresElectionView extends JFrame {

  private StateCountyPicker picker;
  private ActionListener listener;

  public PresElectionView() {

    this.picker = new StateCountyPicker();


  }

  public void display() {
    this.setVisible(true);
  }

  public void setActionListener(ActionListener listener) {
    this.listener = listener;
    this.picker.setActionListener(this.listener);
  }

}
