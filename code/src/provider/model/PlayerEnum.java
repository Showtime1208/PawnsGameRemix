package provider.model;

/**
 * Represents a player ownership of a cell.
 * The cell can be owner by player Red, player Blue, or no player (None)
 */
public enum PlayerEnum {
  Red("R"), Blue("B"), None("_");

  private final String owner;

  /**
   * Represents a player ownership of a cell.
   * The cell can be owner by player Red, player Blue, or no player (None)
   * @param owner the owner of the cell.
   */
  PlayerEnum(final String owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return this.owner;
  }
}
