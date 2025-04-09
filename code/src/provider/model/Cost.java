package provider.model;

/**
 * Represents the cost of a card.
 * Can be ONE (1), TWO (2), or THREE (3).
 */
public enum Cost {
  ONE, TWO, THREE;

  /**
   * Returns the corresponding int value of the enum.
   * @return 1 if ONE, 2 if TWO, 3 if THREE
   */
  public int toInt() {
    switch (this) {
      case ONE:
        return 1;
      case TWO:
        return 2;
      default:
        return 3;
    }
  }
}
