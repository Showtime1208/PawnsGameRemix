package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * A cell in the grid that cannot be modified.
 */
public interface ImmutableTile {

  /**
   * Gets the card currently in this cell, if any.
   *
   * @return the card, or null if none
   */
  GameCard getCard();

  /**
   * Checks whether a card is currently placed on this tile.
   *
   * @return true if a card exists, false otherwise
   */
  boolean containsCard();

  /**
   * Indicates the current ownership of this tile.
   *
   * @return an enum representing the owner
   */
  Ownership tileOwner();

  /**
   * Reports the amount of players present on the tile.
   *
   * @return influence points applied to the tile
   */
  int participantCount();

}