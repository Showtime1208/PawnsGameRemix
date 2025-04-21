package provider.view;

/**
 * Player Action interface. Hey TA this is the customer, they didn't send this part of the
 * documentation and I didn't realize until right now so ill just guess as to what it does. Listener
 * class for the controller. Deals with Player Action.
 */
public interface PlayerAction {

  /**
   * Handles the action taken place when a move is chosen (the enter key is pressed).
   *
   * @param handIdx the index within the hand of the card to be placed
   * @param row     the row on the board where the card will be placed, 0-indexed.
   * @param col     the column on the board where the card will be placed, 0-indexed.
   */
  void moveChosen(int handIdx, int row, int col);

  /**
   * Passes the turn to the next player (the backspace key is pressed).
   */
  void passTurn();
}
