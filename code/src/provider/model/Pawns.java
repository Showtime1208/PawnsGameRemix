package provider.model;

/**
 * Represents the number of pawns on a board. Can be 0, for no pawns, or 1, 2, or 3.
 */
public enum Pawns {

  ZERO, ONE, TWO, THREE;

  @Override
  public String toString() {
    if (this == ZERO) {
      return "0";
    } else if (this == ONE) {
      return "1";
    } else if (this == TWO) {
      return "2";
    }
    return "3";
  }

  /**
   * Returns the corresponding int value of the pawn count.
   *
   * @return 0 if ZERO, 1 if ONE, 2 if TWO, 3 if THREE
   */
  public int toInt() {
    if (this == ZERO) {
      return 0;
    } else if (this == ONE) {
      return 1;
    } else if (this == TWO) {
      return 2;
    }
    return 3;
  }

  /**
   * Increments the pawn value. Pawn ZERO will return ONE, ONE returns TWO, TWO returns THREE, and
   * THREE throws an error.
   *
   * @return the next pawn value up.
   * @throws IllegalArgumentException if the value is at the maximum pawn number (THREE).
   */
  public Pawns increment() {
    switch (this) {
      case ZERO:
        return ONE;
      case ONE:
        return TWO;
      case TWO:
        return THREE;
      case THREE:
      default:
        throw new IllegalArgumentException("Pawn count cannot be incremented.");
    }
  }
}
