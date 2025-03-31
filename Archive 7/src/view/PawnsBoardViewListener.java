package view;

/**
 *  Listener interface for view events.
 * The stub controller implements these methods for teting.
 */
public interface PawnsBoardViewListener {
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
}
