package model;

import java.util.List;
import model.card.Card;

/**
 * Interface for the Players in the game. Holds the deck and the hand of the player.
 */
public interface Player {

  /**
   * Gets the deck of the player.
   *
   * @return the list of Card of the player
   */
  public List<Card> getDeck();

  /**
   * Sets the hand of the player.
   *
   * @throws IllegalArgumentException if the cardList is empty or null.
   */
  public void setDeck(List<Card> cardList);

  /**
   * Gets the color of the player.
   *
   * @return the boolean value of the color of the player
   */
  public boolean getIsRed();

  /**
   * Gets the hand of the player.
   *
   * @return the list of cards that is the hand of the player.
   */
  public List<Card> getHand();

  /**
   * Gets the card at the provided index.
   *
   * @param index the index of the card you want.
   * @return the Card in the Player's Hand.
   * @throws IllegalArgumentException if the hand index is invalid.
   */
  public Card getCardAt(int index);

  /**
   * Takes a card from the deck and adds it to the hand. Will primarily be played after each move,
   * so could be redundant but also good helper.
   */
  public void drawFromDeckToHand();

  /**
   * Gets the handSize of the player.
   *
   * @return the handsize.
   */
  public int getHandSize();

}
