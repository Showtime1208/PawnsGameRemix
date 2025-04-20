package view;

import java.io.IOException;
import java.util.List;
import model.Board;
import model.Cell;
import model.Player;
import model.UpdatedGameBoard;
import model.card.Card;
import model.card.Pawn;

public class UpdatedTextualView implements TextualView {
  private final Board board;

  public UpdatedTextualView(UpdatedGameBoard model) {
    if (model == null) {
      throw new IllegalArgumentException("The board cannot be null");
    }
    this.board = model;
  }

  @Override
  public void render(Appendable out) throws IOException {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        sb.append(squareString(board.getCell(row, col)));
        if (col < board.getWidth() - 1) {
          sb.append("  ");
        }
      }
      sb.append('\n');
    }
    out.append(sb.toString());
  }


  private String squareString(Cell cell) {
    Card card = cell.getCard();
    if (card != null) {
      char color = card.getOwner().getIsRed() ? 'R' : 'B';
      int base = card.getValue();
      int mod = cell.getValueModifier();
      if (mod == 0) {
        return String.format("%c%d", color, base);
      } else {
        return String.format("%c%d(%+d)", color, base, mod);
      }
    }
    List<Pawn> pawns = cell.getPawns();
    if (!pawns.isEmpty()) {
      Player owner = pawns.get(0).getOwner();
      char color = owner.getIsRed() ? 'R' : 'B';
      return String.format("%c%d", color, pawns.size());
    }
    return "__";
  }
}
