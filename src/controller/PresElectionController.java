package controller;

import view.PresElectionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PresElectionController implements ActionListener {

  PresElectionView view;

  public PresElectionController(PresElectionView view) {
    this.view = view;
    this.view.setActionListener(this);
  }


  private void performAction(String command) {
    switch (command) {
      case "compare":
        break;
      default:
        throw new IllegalStateException("Unknown command: \"" + command + "\"");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.performAction(e.getActionCommand());
  }
}
