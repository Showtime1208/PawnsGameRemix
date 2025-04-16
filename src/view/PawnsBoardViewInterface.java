package view;

import model.Board;

/**
 * The view interface for the PawnsBoard game. It defines methods for updating the board, handling
 * selections, displaying messages, and initializing the GUI.
 */
public interface PawnsBoardViewInterface {

  /**
   * Set the listener for view events (e.g., mouse clicks, key presses). Typically, the listener is
   * implemented by the controller.
   *
   * @param listener an object implementing PawnsBoardViewListener
   */
  void setViewListener(PawnsBoardViewListener listener);

  /**
   * Refresh the board display to show the current board.
   */
  void refreshBoard(Board board);

  /**
   * highlight a specified cell on the board.
   *
   * @param row the row index of the cell
   * @param col the col index of the cell
   */
  void highlightCell(int row, int col);

  /**
   * Highlights a specified card in the player's hand.
   *
   * @param cardId the index of the card to be selected
   */
  void highlightCard(int cardId);

  /**
   * Display a message or status update to the user.
   *
   * @param message the message to display
   */
  void displayMessage(String message);

  /**
   * Initialize and show the GUI.
   */
  void makeVisible();
}
