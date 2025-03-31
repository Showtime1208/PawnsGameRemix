package cs3500.pawnsboard.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation of a card in the Pawns Board game.
 */
public class CardUse implements Card {
  private final String name;
  private final int cost;
  private final int valueScore;
  private final boolean[][] influenceGrid;
  private Player playerOwnedBy;

  /**
   * Creates a new card.
   *
   * @param name the name of the card
   * @param cost the cost of the card (1-3)
   * @param valueScore the value score of the card
   * @param influenceGrid the 5x5 influence grid
   * @throws IllegalArgumentException if any parameters are invalid
   */
  public CardUse(String name, int cost, int valueScore, boolean[][] influenceGrid) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("Cost must be between 1 and 3");
    }
    if (valueScore <= 0) {
      throw new IllegalArgumentException("Value score must be positive");
    }
    if (influenceGrid == null || influenceGrid.length != 5) {
      throw new IllegalArgumentException("Influence grid must be 5x5");
    }
    for (boolean[] row : influenceGrid) {
      if (row == null || row.length != 5) {
        throw new IllegalArgumentException("Influence grid must be 5x5");
      }
    }

    this.name = name;
    this.cost = cost;
    this.valueScore = valueScore;
    this.playerOwnedBy = null;

    // Make a defensive copy of the influence grid
    this.influenceGrid = new boolean[5][5];
    for (int i = 0; i < 5; i++) {
      this.influenceGrid[i] = Arrays.copyOf(influenceGrid[i], 5);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getValueScore() {
    return valueScore;
  }

  @Override
  public Player getPlayerOwnedBy() {
    return playerOwnedBy;
  }

  @Override
  public void setPlayerOwnedBy(Player player) {
    this.playerOwnedBy = player;
  }

  @Override
  public boolean[][] getInfluenceGrid() {
    // Return a defensive copy
    boolean[][] copy = new boolean[5][5];
    for (int i = 0; i < 5; i++) {
      copy[i] = Arrays.copyOf(influenceGrid[i], 5);
    }
    return copy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardUse card = (CardUse) o;
    return cost == card.cost &&
            valueScore == card.valueScore &&
            Objects.equals(name, card.name) &&
            Arrays.deepEquals(influenceGrid, card.influenceGrid);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(name, cost, valueScore);
    result = 31 * result + Arrays.deepHashCode(influenceGrid);
    return result;
  }

  @Override
  public String toString() {
    return name + " " + cost + " " + valueScore;
  }
}