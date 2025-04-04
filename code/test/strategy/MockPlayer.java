package strategy;

import java.util.ArrayList;
import java.util.List;
import model.Player;
import model.card.Card;

/**
 * A simple mock Player. You can store a hand of mock Cards if you like, or keep track of which
 * cards have been drawn, etc.
 */
public class MockPlayer implements Player {

  private final StringBuilder log;
  private final boolean isRed;
  private final List<Card> hand;
  private List<Card> deck;

  /**
   * Constructor for the mockPlayer.
   *
   * @param log   the log.
   * @param isRed what color.
   * @param deck  the deck.
   */
  public MockPlayer(StringBuilder log, boolean isRed, List<Card> deck) {
    this.log = log;
    this.isRed = isRed;
    this.hand = new ArrayList<>(5);
    this.deck = deck;
    for (Card card : deck) {
      card.setOwner(this);
    }
    for (int i = 0; i < 5; i++) {
      hand.add(this.deck.remove(0));
    }
  }

  @Override
  public List<Card> getDeck() {
    log.append("getDeck called\n");
    return new ArrayList<>();
  }

  @Override
  public void setDeck(List<Card> cardList) {
    log.append("setDeck called\n");
    this.deck.addAll(cardList);

  }

  @Override
  public boolean getIsRed() {
    log.append("getIsRed called\n");
    return isRed;
  }

  @Override
  public List<Card> getHand() {
    log.append("getHand called\n");
    return hand;
  }

  @Override
  public Card getCardAt(int index) {
    log.append("getCardAt called with index=")
        .append(index)
        .append("\n");
    return hand.get(index);
  }

  @Override
  public void drawFromDeckToHand() {
    log.append("drawFromDeckToHand called\n");
    hand.add(deck.remove(0));
  }

  @Override
  public int getHandSize() {
    log.append("getHandSize called\n");
    return hand.size();
  }
}