import controller.PresElectionController;
import controller.PresElectionControllerInterface;
import model.DatabaseRetrieval;
import model.InsertElectionData;
import view.PresElectionSwingView;
import view.PresElectionView;

/**
 * Represents the launch point of the application. If the first argument given is "initDatabase"
 * in the command line, the database will be initialized with data. Otherwise, the Swing view will
 * run and allow the user to interact with it.
 *
 */
public class PresidentialElectionDatabase {

  public static void main(String[] args) {

    PresElectionView view = new PresElectionSwingView();
    DatabaseRetrieval dbR = new DatabaseRetrieval();


    if (args.length > 0 && args[0].equalsIgnoreCase("initDatabase")) {
      InsertElectionData insert = new InsertElectionData();
      if (args.length >= 2) {
        insert.setPass(args[1]);
      }
      insert.initEntireDatabase();
    } else if (args.length > 0 && args[0].equalsIgnoreCase("rootPass")) {
      PresElectionControllerInterface controller = new PresElectionController(view, dbR, args[1]);
      String[] states = dbR.getStates();
      view.setStateList(states);
      controller.start();
    } else {
      PresElectionControllerInterface controller = new PresElectionController(view, dbR, "pass");
      String[] states = dbR.getStates();
      view.setStateList(states);
      controller.start();
    }

  }

}
