package provider.model;

/**
 * An interface for an observer of the model. Allows an observer to gain information anytime the
 * model reaches a certain state.
 */
public interface ModelStatus {

  /**
   * Notifies the observer that a certain player has a turn or that the turn has changed.
   */
  void yourTurn();

  /**
   * Notifies both players that the game is over.
   */
  void gameOver();

  /**
   * Refreshes the view to update to the current game state.
   */
  void refreshView();
}

