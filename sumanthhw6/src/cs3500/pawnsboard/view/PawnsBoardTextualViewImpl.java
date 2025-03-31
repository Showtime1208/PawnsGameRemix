package cs3500.pawnsboard.view;

import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyPawnsBoardModel;

import java.io.IOException;
import java.util.Map;

/**
 * Implementation of the textual view for the Pawns Board game.
 * Renders the board with row scores for both players.
 */
public class PawnsBoardTextualViewImpl implements PawnsBoardTextualView {
  private final ReadOnlyPawnsBoardModel<?> model;
  private final int rows;
  private final int cols;

  /**
   * Creates a new textual view for the Pawns Board game.
   *
   * @param model the game model to render
   * @throws IllegalArgumentException if model is null or dimensions are invalid
   */
  public PawnsBoardTextualViewImpl(ReadOnlyPawnsBoardModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.rows = model.getNumberOfRows();
    this.cols = model.getNumberOfColumns();
  }

  @Override
  public void render(Appendable out) throws IOException {
    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }

    // Get row scores for both players
    Map<Integer, Integer> redScores = model.getRowScores(Player.RED);
    Map<Integer, Integer> blueScores = model.getRowScores(Player.BLUE);

    // For each row
    for (int row = 0; row < rows; row++) {
      // Add red score at the beginning of the row
      out.append(String.valueOf(redScores.getOrDefault(row, 0))).append(" ");

      // Render the row
      for (int col = 0; col < cols; col++) {
        Player owner = model.getPlayerAt(row, col);

        if (owner == null) {
          int redPawns = -1;
          int bluePawns = -1;
          if (model.getPawnsAt(row, col) == null) {
            redPawns = 0;
            bluePawns = 0;
          }

          // Check if there are pawns
          else if (model.getPawnsAt(row, col).getOwner() == Player.RED) {
            redPawns = model.getPawnsAt(row, col).getNumPawns();
          } else if (model.getPawnsAt(row, col).getOwner() == Player.BLUE) {
            bluePawns = model.getPawnsAt(row, col).getNumPawns();
          }
          if (redPawns > 0) {
            // Red pawns
            out.append(String.valueOf(redPawns));
          } else if (bluePawns > 0) {
            // Blue pawns
            out.append(String.valueOf(bluePawns));
          } else {
            // Empty cell
            out.append("_");
          }

        } else if (owner == Player.RED) {
          // Red card
          out.append("R");
        } else {
          // Blue card
          out.append("B");
        }
      }

      out.append(" ").append(String.valueOf(blueScores.getOrDefault(row, 0)));

      if (row < rows - 1) {
        out.append("\n");
      }
    }
    out.append("\n" + "\n");
  }

  /**
   * Returns a string representation of the board.
   *
   * @return a string representation of the board
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    try {
      render(builder);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected error rendering view", e);
    }
    return builder.toString();
  }
}