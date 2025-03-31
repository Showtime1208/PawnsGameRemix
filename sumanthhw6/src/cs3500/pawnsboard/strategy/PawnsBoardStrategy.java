package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyPawnsBoardModel;

/**
 * Interface for strategies in the Pawns Board game.
 * A strategy determines the best move for a player in a given game state.
 */
public interface PawnsBoardStrategy {

  /**
   * Determines the best move for the given player in the current game state.
   *
   * @param model the current game state
   * @param player the player for whom to find the best move
   * @return the best move according to this strategy, or null if no valid move is found
   * @throws IllegalArgumentException if the model or player is null
   */
  Move findBestMove(ReadOnlyPawnsBoardModel<?> model, Player player);

  /**
   * Represents a move in the Pawns Board game.
   * A move consists of a card index and a position on the board.
   */
  class Move {
    private final int cardIndex;
    private final int row;
    private final int col;

    /**
     * Creates a new move.
     *
     * @param cardIndex the index of the card in the player's hand
     * @param row the row to place the card
     * @param col the column to place the card
     */
    public Move(int cardIndex, int row, int col) {
      this.cardIndex = cardIndex;
      this.row = row;
      this.col = col;
    }

    /**
     * Gets the card index.
     *
     * @return the index of the card in the player's hand
     */
    public int getCardIndex() {
      return cardIndex;
    }

    /**
     * Gets the row.
     *
     * @return the row to place the card
     */
    public int getRow() {
      return row;
    }

    /**
     * Gets the column.
     *
     * @return the column to place the card
     */
    public int getCol() {
      return col;
    }

    @Override
    public String toString() {
      return "Move{cardIndex=" + cardIndex + ", row=" + row + ", col=" + col + "}";
    }
  }
}