package cs3500.pawnsboard.model;

/**
 * A Card used in  a game of PawnsBoard.
 */
public interface Card {

  /**
   * Gets the name of this card.
   */
  String getName();

  /**
   * Gets the cost of this card.
   *
   * @return the cost of the card.
   */
  int getCost();

  /**
   * Gets the value of this card.
   *
   * @return the value for scoring of the card.
   */
  int getValueScore();

  /**
   * Sets the owner of this card.
   *
   */
  void setPlayerOwnedBy(Player player);

  /**
   * Gets the grid of influence of this card.
   *
   * @return the influence grid of the card.
   */
  boolean[][] getInfluenceGrid();

  /**
   * Gets the owner of this card.
   *
   * @return the owner of the card.
   */
  Player getPlayerOwnedBy();
}