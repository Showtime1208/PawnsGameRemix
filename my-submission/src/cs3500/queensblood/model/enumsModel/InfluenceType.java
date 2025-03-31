package cs3500.queensblood.model.enumsModel;

/**
 * Represents types of influence a card has on surrounding cells when placed on the board.
 */
public enum InfluenceType {
  NONE('x'),
  INFLUENCE('I'),
  CENTER('C');

  private final char symbol;

  InfluenceType(char symbol) {
    this.symbol = symbol;
  }

  public char getSymbol() {
    return symbol;
  }
}
