package cs3500.queensblood.strategy;

public class Move {
  public final int cardIndex;
  public final int row;
  public final int col;
  public final boolean isPass;

  public Move(int cardIndex, int row, int col) {
    this.cardIndex = cardIndex;
    this.row = row;
    this.col = col;
    this.isPass = false;
  }

  public static Move pass() {
    return new Move(-1, -1, -1, true);
  }

  private Move(int cardIndex, int row, int col, boolean isPass) {
    this.cardIndex = cardIndex;
    this.row = row;
    this.col = col;
    this.isPass = isPass;
  }
}
