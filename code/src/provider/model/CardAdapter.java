package provider.model;

import model.card.Card;
import model.card.Influence;

/**
 * Adapter Class for the Cards. Adapts the current Card interface and SimpleCard implementation into
 * the ProviderCard format for compatibility with the view and model.
 */
public class CardAdapter implements ProviderCard {

  private final Card card;
  private boolean isFlipped;

  /**
   * Constructor for the card adapter. Takes the current card and turns it into ProviderCard form.
   *
   * @param card the Card that will be adapted.
   */
  public CardAdapter(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.card = card;
    this.isFlipped = false;
  }

  @Override
  public String getName() {
    return card.getName();
  }

  @Override
  public CardOwner[][] getInfluence() {
    Influence[][] yourGrid = card.getInfluenceArray();
    CardOwner[][] providerGrid = new CardOwner[5][5];

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (yourGrid[i][j].getInfluence()) {  // If there's influence
          providerGrid[i][j] = card.getOwner().getIsRed() ?
              CardOwner.ORANGE : CardOwner.CYAN;
        } else {
          providerGrid[i][j] = CardOwner.NONE;  // No influence
        }
      }
    }

    // If flipped, reverse the grid horizontally
    if (isFlipped) {
      CardOwner[][] flipped = new CardOwner[5][5];
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          flipped[i][4 - j] = providerGrid[i][j];
        }
      }
      return flipped;
    }

    return providerGrid;
  }

  @Override
  public int getCost() {
    return card.getCost();
  }

  @Override
  public int getValue() {
    return card.getValue();
  }

  @Override
  public void flipInfluence() {
    this.isFlipped = !this.isFlipped;
  }

  @Override
  public String toString() {
    return getName() + "\nCost: " + getCost() + "\nValue: " + getValue();
  }
}