package cs3500.queensblood.model;
import cs3500.queensblood.model.enumsModel.Ownership;

import java.util.List;


/**
 * Read-only access to a player's state: score, hand, and name.
 */
public interface ImmutableParticipant {

  /**
   * Retrieves all cards currently held by the player.
   *
   * @return list of cards in the player's hand
   */
  List<GameCard> getHand();

  /**
   * Returns the total score this player has accumulated.
   *
   * @return the player's score
   */
  Ownership getColor();

  /**
   * Gets this player's identifying name.
   *
   * @return name string of the player
   */
 // String getIdentifier();
}
