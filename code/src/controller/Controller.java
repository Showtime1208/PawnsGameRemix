package controller;


import view.CommandListener;
import view.PawnsBoardViewListener;

/**
 * Controller Interface for the game. Delegates between the view and model when the player is a
 * human, and delegates between the strategy and the model if the player is AI.
 */
public interface Controller extends PawnsBoardViewListener, CommandListener {

  /**
   * Called clearly when a cell on the board is clicked.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  void handleCellClick(int row, int col);

  /**
   * Called clearly when a card in the player's hand is selected.
   *
   * @param cardIndex the index of the selected card
   */
  void handleCardClick(int cardIndex);

  /**
   * Called clearly when the player decides to pass their turn.
   */
  void handlePassTurn();

  /**
   * Processes a command entered by the user.
   *
   * @param command the command string (expected to be "c" or "p")
   */
  void processCommand(String command);

  /**
   * Processes the AI command based on the current board state.
   */
  void processAICommand();

  /**
   * Plays the game for the AI.
   */
  void playGame();


}
