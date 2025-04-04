package model.card;

import model.Player;

/**
 * represents the card interface with all the public methods for a Card.
 */
public interface Card {

  /**
   * Returns the name of the Card.
   *
   * @return the Card's name.
   */
  public String getName();

  /**
   * Returns the Player owner of the card.
   *
   * @return the owner.
   */
  public Player getOwner();

  /**
   * \ Sets the value of the owner.
   *
   * @param player the player that will be set to the new owner.
   */
  public void setOwner(Player player);

  /**
   * Returns the cost of the card.
   *
   * @return the cost value of the Card.
   */
  public int getCost();

  /**
   * Returns the value of the card.
   *
   * @return the value of the card.
   */
  public int getValue();

  /**
   * Returns the 2D Array of Influence for the card.
   *
   * @return the card's InfluenceArray.
   */
  public Influence[][] getInfluenceArray();

}
