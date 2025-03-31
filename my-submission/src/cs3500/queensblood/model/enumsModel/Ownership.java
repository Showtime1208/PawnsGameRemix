package cs3500.queensblood.model.enumsModel;

/**
 * Represents possible ownership states for cells or pawns, each with a unique symbol.
 */
public enum Ownership {
  RED('R'),
  BLUE('B'),
  NONE('x');

  private final char symbol;

  Ownership(char symbol) {
    this.symbol = symbol;
  }
  /**
   * Determines and returns the opponent of the current owner.
   * If the owner is NONE, it returns NONE.
   *
   * @return the opposite Ownership value
   */
  public Ownership getOpponent() {
    return this == RED ? BLUE : (this == BLUE ? RED : NONE);
  }

  /**
   * Gets the symbol representing this ownership.
   *
   * @return character for the owner
   */
  public char getSymbol() {
    return this.symbol;
  }
}

