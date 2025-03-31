package strategy;

import model.Player;
import model.card.Card;
import model.card.Influence;

/**
 * Mock card class for testing. Allows cards to be read in, but there are no rules.
 */
public class MockCard implements Card {
  private final String name;
  private final int value;
  private final int cost;
  private Player owner;
  private final Influence[][] influence;

  /**
   * Constructor for the mock card. can take in and copy influence arrays.
   * @param name the name.
   * @param value the value.
   * @param cost the cost.
   * @param influence the influence.
   */
  public MockCard(String name, int value, int cost, Influence[][] influence) {
    this.influence = copyInfluenceArray(influence);
    this.name = name;
    this.value = value;
    this.owner = null;
    this.cost = cost;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public void setOwner(Player player) {
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
    return copyInfluenceArray(influence);
  }

  /**
   * Helper to copy 5x5 grid to not expose internal array.
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
