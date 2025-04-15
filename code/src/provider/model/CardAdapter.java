package provider.model;

import model.card.Card;
import model.card.Influence;
import provider.model.CardOwner;
import provider.model.ProviderCard;

public class CardAdapter implements ProviderCard {
  private final Card card;
  private boolean isFlipped;

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
    return card.getCost();  // Use  card's cost
  }

  @Override
  public int getValue() {
    return card.getValue();  // Use  card's value
  }

  @Override
  public void flipInfluence() {
    this.isFlipped = !this.isFlipped;
  }
}