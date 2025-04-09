package provider.view;

//observer actions interface
public interface PlayerAction {

  /**
   * Handles the action taken place when a move is chosen (the enter key is pressed).
   * @param handIdx the index within the hand of the card to be placed
   * @param row the row on the board where the card will be placed, 0-indexed.
   * @param col the column on the board where the card will be placed, 0-indexed.
   */
  void moveChosen(int handIdx, int row, int col);

  /**
   * Passes the turn to the next player (the backspace key is pressed).
   */
  void passTurn();
}
