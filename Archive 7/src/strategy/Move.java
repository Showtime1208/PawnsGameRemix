package strategy;

/**
 * Represents the move strategy.
 */
public class Move {
  private final int row;
  private final int col;
  private final boolean pass;
  private final int cardIdx;

  /**
   * The move constructor.
   * @param row the row.
   * @param col the col.
   * @param pass the pass.
   * @param cardIdx the card index.
   */
  public Move(int row, int col, boolean pass, int cardIdx) {
    this.row = row;
    this.col = col;
    this.pass = pass;
    this.cardIdx = cardIdx;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public boolean isPass() {
    return pass;
  }

  public int getCardIdx() {
    return cardIdx;
  }

  @Override
  public String toString() {
    return "" + row + col + pass + cardIdx;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Move move = (Move) o;
    return move.getRow() == row && move.getCol() == col && move.isPass() == pass
        && move.getCardIdx() == cardIdx;
  }

  @Override
  public int hashCode() {
    int result = Integer.hashCode(row);
    result = 31 * result + Integer.hashCode(col);
    result = 31 * result + Boolean.hashCode(pass);
    result = 31 * result + Integer.hashCode(cardIdx);
    return result;
  }
}
