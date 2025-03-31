package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Represents a mutable version of the game board used in Pawns Board.
 * Extends {@link ImmutableGrid} to allow card placements and application of influence effects.
 * Intended to be used by the game logic during a player's turn.
 */
public interface PawnsBoard extends ImmutableGrid {

  /**
   * Places a card onto the board at the specified location and updates surrounding
   * cells based on the card's influence pattern.
   *
   * @param row   the row index where the card is to be placed
   * @param col   the column index where the card is to be placed
   * @param card  the card being played
   * @param owner the player performing the placement
   * @throws IllegalArgumentException if the placement is invalid
   */
  void placeCard(int row, int col, GameCard card, Ownership owner);
}
