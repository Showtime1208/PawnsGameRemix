package cs3500.queensblood.model;
import java.util.List;

/**
 * Defines a modifiable player entity in the Pawns Board game.
 * Extends {@link ImmutableParticipant} by adding capabilities
 * for managing the player's card deck and hand interactions.
 */
public interface Participant extends ImmutableParticipant {

  /**
   * Transfers the top card from the player's deck into their hand.
   * If the deck is empty, the behavior may be to skip or signal an error,
   * based on implementation specifics.
   */
  void drawCard();

  /**
   * Discards a card from the player's hand based on its index.
   *
   * @param index index of the card to discard (zero-based)
   * @throws IllegalArgumentException if the index is invalid
   */
  void removeCard(int index);

  /**
   * Retrieves the player's current deck of cards.
   * This list may change as cards are drawn during gameplay.
   *
   * @return list of remaining {@link GameCard} objects in the deck
   */
  List<GameCard> getDeck();
}
