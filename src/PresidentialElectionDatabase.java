import model.DatabaseRetrieval;
import view.PresElectionView;

import java.util.ArrayList;

public class PresidentialElectionDatabase {

  public static void main(String[] args) {
    PresElectionView view = new PresElectionView();
    DatabaseRetrieval dbR = new DatabaseRetrieval();
    String[] states = dbR.getStates();
    view.setStateList(states);
    view.display();
  }

}
