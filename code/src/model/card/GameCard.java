package model.card;

import model.Player;

/**
 * Represents the game card class. Holds all Influence, naming, scoring, and values for the cards.
 */
public class GameCard implements Card {

  private final String name;

  private final int cost;

  private final int value;

  private final Influence[][] influenceArray;

  private Player owner;

  /**
   * Represents the constructor of the GameCard class to initalize a card.
   *
   * @param name           the name of the card.
   * @param influenceArray infleuence grid of card.
   * @param cost           cost of card.
   * @param value          value of card.
   */
  public GameCard(String name, Influence[][] influenceArray, int cost, int value) {
    if (name == null || influenceArray == null || cost <= 0 || cost > 3 || value <= 0) {
      throw new IllegalArgumentException("Illegal arguments for GameCard");
    }
    if (influenceArray.length != 5 || influenceArray[0].length != 5) {
      throw new IllegalArgumentException("Influence array must be a 5x5 grid.");
    }
    for (int i = 0; i < influenceArray.length; i++) {
      if (influenceArray[i] == null || influenceArray[i].length != 5) {
        throw new IllegalArgumentException(
            "Each row in the influence array must have exactly 5 columns.");
      }
    }
    this.name = name;
    this.influenceArray = copyInfluenceArray(influenceArray);
    this.cost = cost;
    this.value = value;
    this.owner = null;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Player getOwner() {
    if (owner == null) {
      throw new IllegalArgumentException("the owner cannot be null");
    }
    return owner;
  }

  @Override
  public void setOwner(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("The owner cannot be null.");
    }
    if (this.owner != null) {
      throw new IllegalStateException("The card already has an owner.");
    }
    this.owner = player;
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public Influence[][] getInfluenceArray() {
    return copyInfluenceArray(influenceArray);
  }

  /**
   * Helper to copy 5x5 grid to not expose internal array.
   *
   * @param grid the influence grid.
   * @return the copy of the 5x5 grid.
   */
  public Influence[][] copyInfluenceArray(Influence[][] grid) {

    Influence[][] copy = new Influence[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        copy[r][c] = grid[r][c];
      }
    }
    return copy;
  }
}

