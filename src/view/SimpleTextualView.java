package view;

import java.io.IOException;
import model.Board;
import model.Cell;

/**
 * represents the SimpleTextualView class for visualization of the card game for the user.
 */
public class SimpleTextualView implements TextualView {

  private final Board model;

  /**
   * Represents the constructor for the SimpleTextualView and initializes the model.
   *
   * @param model represents the model of the game.
   * @throws IllegalArgumentException if the model is null.
   */
  public SimpleTextualView(Board model) {
    if (model == null) {
      throw new IllegalArgumentException("The board cannot be null");
    }
    this.model = model;
  }

  @Override
  public void render(Appendable out) throws IOException {
    try {
      out.append(this.toString());
    } catch (IOException e) {
      throw new IOException("Error while rendering the board to the output.", e);
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int height = model.getHeight();
    int width = model.getWidth();

    for (int row = 0; row < height; row++) {
      int player1Score = model.getRowScore(model.getP1(), row);

      // Append player 1's score
      sb.append(player1Score).append(" ");
      for (int col = 0; col < width; col++) {

        Cell cell = model.getCell(row, col);

        // The cell is empty
        if (cell.getCard() == null & cell.getPawns().isEmpty()) {
          sb.append("_");
          // The cell has 1, 2 or 3 pawns
        } else if (cell.getCard() != null) {
          // check if owned by red player or owned by blue player
          if (cell.getCard().getOwner().getIsRed()) {
            sb.append("R");
          } else {
            sb.append("B");
          }
        } else if (!cell.getPawns().isEmpty()) {
          sb.append(cell.getPawns().size());

          // the cell has a card owned by some player.
        }
      }
      int player2Score = model.getRowScore(model.getP2(), row);
      sb.append(" ").append(player2Score).append("\n");
    }
    return sb.toString();
  }
}
