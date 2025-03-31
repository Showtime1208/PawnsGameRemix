package model;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Player Interface. Holds all logic for manipulating the deck and
 * hand.
 */
public class SimplePlayer implements Player {

  private List<Card> deck;
  private List<Card> hand;
  public final int handSize;
  private final boolean isRed;

  /**
   * Constructor for the SimplePlayer class.
   * @param handSize the size of the hand for the Player.
   * @param isRed what color the user is playing as.
   */
  public SimplePlayer(int handSize, boolean isRed) {
    if (handSize <= 0) {
      throw new IllegalArgumentException("The hand size cannot be equal or less than 0");
    }
    this.handSize = handSize;
    this.isRed = isRed;
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
  }

  @Override
  public void setDeck(List<Card> cardList) {

    if (cardList == null || cardList.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be null or empty.");
    }
    for (Card card: cardList) {
      card.setOwner(this);
    }
    this.deck = new ArrayList<>(cardList);
    this.hand.clear();
  }

  @Override
  public List<Card> getDeck() {
    return new ArrayList<>(deck);
  }

  @Override
  public List<Card> getHand() {
    return hand;
  }

  @Override
  public Card getCardAt(int index) {
    if (index < 0 || index >= hand.size()) {
      throw new IllegalArgumentException("The index cannot be less than 0 or more " +
              "than the size of the hand");
    }
    return hand.get(index);
  }

  @Override
  public void drawFromDeckToHand() {
    if (deck.isEmpty()) {
      throw new IllegalArgumentException("Cannot draw from an empty deck");
    }
    hand.add(deck.remove(0));
  }

  @Override
  public int getHandSize() {
    return handSize;
  }

  @Override
  public boolean getIsRed() {
    return isRed;
  }
}
