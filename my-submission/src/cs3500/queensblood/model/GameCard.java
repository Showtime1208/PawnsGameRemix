package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.InfluenceType;
/**
 * Defines the contract for a playable card in the Pawns Board game.
 * Each card includes metadata and mechanics that affect gameplay,
 * such as its identity, placement requirements, and board influence behavior.
 *
 * <p>A GameCard contains the following attributes:
 * <ul>
 *   <li><b>Name:</b> A unique identifier for the card.</li>
 *   <li><b>Cost:</b> The number of pawns required to place the card on the board (1–3).</li>
 *   <li><b>Value Score:</b> A point value contributing to a player's score.</li>
 *   <li><b>Influence Grid:</b> A 5x5 matrix that specifies the zones affected when the card is placed.</li>
 * </ul>
 */
public interface GameCard {

  /**
   * Retrieves the name of the card.
   *
   * @return unique card name
   */
  String getName();

  /**
   * Returns the number of pawns required to play this card.
   * The value is expected to be between 1 and 3.
   *
   * @return the placement cost of the card
   */
  int getCost();

  /**
   * Gets the score value associated with this card.
   * This contributes to the owning player’s score when the card is played.
   *
   * @return non-negative point value
   */
  int getValueScore();

  /**
   * Provides the influence map for this card.
   * This 5x5 matrix defines how the card affects surrounding tiles upon placement.
   *
   * @return two-dimensional grid of influence types
   */
  InfluenceType[][] getInfluences();

  /**
   * Checks whether this card is equal to another object.
   * Equality is based on matching name, cost, value, and identical influence layout.
   *
   * @param other the object to compare to
   * @return {@code true} if both represent the same card; {@code false} otherwise
   */
  boolean equals(Object other);

  /**
   * Returns a string representation of the card,
   * typically including its name, stats, or influence preview.
   *
   * @return formatted string describing the card
   */
  String toString();
}
