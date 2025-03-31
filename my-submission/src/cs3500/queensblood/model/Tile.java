package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Represents a fully mutable cell in the Pawns Board game.
 * Extends {@link ImmutableTile} to include all mutator operations.
 */
public interface Tile extends ImmutableTile {

  /**
   * Sets the owner of this cell.
   *
   * @param owner the new ownership (RED, BLUE, or NONE)
   */
  void setOwner(Ownership owner);

  /**
   * Sets the number of pawns occupying this cell (0–3).
   *
   * @param count the new pawn count
   * @throws IllegalArgumentException if count is out of 0–3
   */
  void setPawnCount(int count);

  /**
   * Adds one pawn to this cell (if not already at 3 pawns).
   *
   * @throws IllegalStateException if adding a pawn would exceed 3
   */
  void addPawn();

  /**
   * Removes all pawns from this cell, typically used after placing a card.
   */


  /**
   * Places a card in this cell.
   *
   * @param card  the card to place
   * @param owner the ownership color placing this card
   * @throws IllegalStateException if the cell already contains a card
   */
  void placeCard(GameCard card, Ownership owner);
}
