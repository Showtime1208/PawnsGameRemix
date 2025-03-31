package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Pawns Board game.
 * Manages player-specific attributes including their hand, deck, and identity.
 * Implements the {@link Participant} interface to support in-game actions.
 */
public class GameParticipant implements Participant {

  // This participant's assigned color (RED or BLUE)
  private final Ownership color;

  // The private deck from which this participant draws cards
  private final List<GameCard> deck;

  // The participant's current hand
  private final List<GameCard> hand;

  // Maximum number of cards this player can hold at once
  private final int maxHandSize;

  /**
   * Initializes a new participant with a designated color, a starting deck,
   * and a defined hand limit. A number of cards are immediately drawn from
   * the deck up to the initial hand size.
   *
   * @param color    the assigned team color for the player
   * @param deck     the list of cards available in the player's deck
   * @param handSize the maximum number of cards the hand can hold
   */
  public GameParticipant(Ownership color, List<? extends GameCard> deck, int handSize) {
    this.color = color;
    this.deck = new ArrayList<>(deck); // Defensive copy of the initial deck
    this.hand = new ArrayList<>();
    this.maxHandSize = handSize;

    // Fill hand up to its limit
    for (int i = 0; i < handSize && !this.deck.isEmpty(); i++) {
      drawCard();
    }
  }

  /**
   * Draws the top card from the deck and adds it to the player's hand,
   * provided the hand is not already at its maximum size.
   */
  public void drawCard() {
    if (!deck.isEmpty() && hand.size() < maxHandSize) {
      hand.add(deck.remove(0));
    }
  }

  /**
   * Removes the card at the specified index from the hand.
   * An exception is thrown if the index is invalid.
   *
   * @param cardIndex index of the card to be removed
   * @throws IllegalArgumentException if the index is out of bounds
   */
  public void removeCard(int cardIndex) {
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index: " + cardIndex);
    }
    hand.remove(cardIndex);
  }

  /**
   * Returns a snapshot of the player's current hand.
   * The returned list is a copy to ensure immutability from the outside.
   *
   * @return list of cards in the hand
   */
  public List<GameCard> getHand() {
    return new ArrayList<>(hand);
  }

  /**
   * Returns the team color associated with this player.
   *
   * @return ownership color (RED or BLUE)
   */
  public Ownership getColor() {
    return color;
  }

  /**
   * Returns a snapshot of the remaining cards in this player's deck.
   *
   * @return list of undealt cards
   */
  public List<GameCard> getDeck() {
    return new ArrayList<>(deck);
  }
}
