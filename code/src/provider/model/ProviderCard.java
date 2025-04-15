package provider.model;

/**
 * Represents a card for the game PawnsBoardModel.
 */
public interface ProviderCard {

  /**
   * gets the name of this Card.
   * @return the name of this Card.
   */
  String getName();

  /**
   * gets the influence grid of the card, represented as a 2D array, with the first set
   * representing rows and the second columns.
   * @return the influence grid of the cards.
   */
  CardOwner[][] getInfluence();

  /**
   * Gets the cost of the card.
   * @return the cost of the card.
   */
  int getCost();

  /**
   * Gets the value of the card.
   * @return the value of the card.
   */
  int getValue();

  /**
   * Flips the influence of the card, so if the card is controlled by the blue player
   * the columns shown are reversed to accurately reflect the board influence.
   */
  void flipInfluence();
}
