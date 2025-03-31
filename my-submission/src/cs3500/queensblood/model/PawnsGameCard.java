package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.InfluenceType;
import java.util.Arrays;

/**
 * Represents a concrete implementation of a card used in the Pawns Board game.
 * Each card has a name, placement cost, point value, and a 5x5 influence grid that determines
 * how the card affects surrounding tiles when placed on the board.
 */
public class PawnsGameCard implements GameCard {

  private final String name;
  private final int cost;
  private final int valueScore;
  private final InfluenceType[][] influenceGrid;

  /**
   * Constructs a card with the specified attributes.
   *
   * @param name           the unique identifier for this card
   * @param cost           number of pawns required to place this card (must be between 1–3)
   * @param valueScore     point value contributed when the card is placed
   * @param influenceGrid  a 5x5 grid defining the influence pattern of the card
   * @throws IllegalArgumentException if arguments are invalid (e.g., null name, invalid dimensions)
   */
  public PawnsGameCard(String name, int cost, int valueScore, InfluenceType[][] influenceGrid) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Card name must not be null or empty.");
    }
    if (cost < 1 || cost > 3) {
      throw new IllegalArgumentException("Card cost must be between 1 and 3.");
    }
    if (valueScore <= 0) {
      throw new IllegalArgumentException("Card value must be greater than zero.");
    }
    if (influenceGrid == null || influenceGrid.length != 5 || influenceGrid[0].length != 5) {
      throw new IllegalArgumentException("Influence grid must be a 5x5 matrix.");
    }

    this.name = name;
    this.cost = cost;
    this.valueScore = valueScore;
    this.influenceGrid = deepCopyGrid(influenceGrid);
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
  public InfluenceType[][] getInfluences() {
    return deepCopyGrid(influenceGrid);
  }

  /**
   * Creates a deep copy of a 5x5 InfluenceType grid to preserve immutability.
   *
   * @param grid the grid to copy
   * @return a new copy of the grid
   */
  private InfluenceType[][] deepCopyGrid(InfluenceType[][] grid) {
    InfluenceType[][] copy = new InfluenceType[5][5];
    for (int i = 0; i < 5; i++) {
      System.arraycopy(grid[i], 0, copy[i], 0, 5);
    }
    return copy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof PawnsGameCard)) return false;

    PawnsGameCard other = (PawnsGameCard) obj;
    return cost == other.getCost()
            && valueScore == other.getValueScore()
            && name.equals(other.getName())
            && Arrays.deepEquals(influenceGrid, other.getInfluences());
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + cost;
    result = 31 * result + valueScore;
    result = 31 * result + Arrays.deepHashCode(influenceGrid);
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s (Cost: %d, Value: %d)", name, cost, valueScore);
  }
}
