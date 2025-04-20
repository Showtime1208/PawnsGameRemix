package provider.view;


/**
 * A view for PawnsBoard, displays the game board and provides visual interface for users.
 */
public interface PBGUIView {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Makes the view visible to start the game session.
   */
  void makeVisible();

  /**
   * set the controller to handle  events in the view.
   *
   * @param listener the controller
   */
  void addListener(PlayerAction listener);


  /**
   * sets the focus of the JFrame.
   */
  void resetFocus();

  /**
   * creates a panel displaying a message.
   *
   * @param msg the message to be displayed
   */
  void showMessage(String msg);
}
