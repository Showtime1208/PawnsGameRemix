package cs3500.queensblood.view;

/**
 * Interface representing a general view in the Pawns Board game.
 * Defines high-level operations that all graphical or textual views must support.
 */
public interface GameView {

  /**
   * Updates the display to reflect the latest state of the game model.
   * Should trigger any visual or textual refresh needed to represent current data.
   */
  void refresh();

  /**
   * Displays a message to the user when the game concludes.
   * This might include declaring the winner or stating a draw.
   *
   * @param message the message to be shown when the game ends
   */
  void showGameOver(String message);

  /**
   * Makes the game window or interface visible to the user.
   * Typically corresponds to calling {@code setVisible(true)} on a frame or panel.
   */
  void makeVisible();
}
