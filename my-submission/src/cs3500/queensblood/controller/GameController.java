package cs3500.queensblood.controller;

/**
 * Represents the controller for the Pawns Board game.
 * Handles user interactions and mediates between the view and the model.
 */
public interface GameController {

  /**
   * Starts the game, sets up listeners, and prepares the initial state.
   */
  void startGame();

  /**
   * Attempts to place the selected card at the given cell position.
   *
   * @param row the row to place the card in
   * @param col the column to place the card in
   */
  void handleCellClick(int row, int col);

  /**
   * Updates the selected card index (usually from a click on a hand panel).
   *
   * @param cardIndex the index of the selected card
   */
  void handleCardSelection(int cardIndex);

  void handleConfirmKey();
  void handlePassKey();

  void handleAIMove();

}
