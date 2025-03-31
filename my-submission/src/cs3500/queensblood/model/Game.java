package cs3500.queensblood.model;

/**
 * A mutable game model, extending the read-only interface with operations
 * for playing a card, passing turn, and starting the game.
 */
public interface Game extends ImmutableGame {

  /**
   * Initializes and starts the game (e.g., deals initial hands).
   */
  void startGame();

  /**
   * Attempts to place the current player's card at (row, col).
   *
   * @param row  0-based row index
   * @param col  0-based col index
   * @param card the card to place
   * @return true if placed successfully, false if illegal
   */
  boolean placeCard(int row, int col, GameCard card);

  /**
   * Passes the current player's turn without placing a card.
   */
  void passTurn();
}
