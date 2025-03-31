package cs3500.queensblood.view;

import cs3500.queensblood.model.GameCard;
import cs3500.queensblood.model.PawnsGameStructure;
import cs3500.queensblood.model.ImmutableGrid;
import cs3500.queensblood.model.ImmutableTile;
import cs3500.queensblood.model.enumsModel.InfluenceType;
import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * A simple textual view of the Pawns Board game.
 * This class is responsible for printing the board and card influence grids to the console
 * in a human-readable format.
 */
public class TextualView {
  private final PawnsGameStructure game;

  /**
   * Creates a textual view tied to the given game instance.
   *
   * @param game the active {@code PawnsGameStructure} to observe
   */
  public TextualView(PawnsGameStructure game) {
    this.game = game;
  }

  /**
   * Prints the current board state to standard output.
   * Each tile displays either:
   * <ul>
   *   <li>the card's value score if it contains a card</li>
   *   <li>the pawn count and owner ("r" for RED, "b" for BLUE) if occupied</li>
   *   <li>"_" if empty</li>
   * </ul>
   * Also prints row scores and total scores for both players.
   */
  public void displayBoard() {
    ImmutableGrid board = game.getReadonlyBoard();
    System.out.println("\nCurrent Board State:");

    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        ImmutableTile cell = board.getTileAt(row, col);

        if (cell.containsCard()) {
          System.out.print(cell.getCard().getValueScore() + " ");
        } else if (cell.participantCount() > 0) {
          String ownerSymbol = (cell.tileOwner() == Ownership.RED) ? "r" : "b";
          System.out.print(cell.participantCount() + ownerSymbol + " ");
        } else {
          System.out.print("_ ");
        }
      }

      int redRowScore = board.getRowScore(row, Ownership.RED);
      int blueRowScore = board.getRowScore(row, Ownership.BLUE);
      System.out.println("| ROW SCORE: RED=" + redRowScore + ", BLUE=" + blueRowScore);
    }

    int redTotalScore = board.getTotalScore(Ownership.RED);
    int blueTotalScore = board.getTotalScore(Ownership.BLUE);
    System.out.println("TOTAL SCORE: RED=" + redTotalScore + ", BLUE=" + blueTotalScore);
  }

  /**
   * Prints a 5x5 influence grid for a given card to standard output.
   * Symbols represent the influence type at each cell:
   * <ul>
   *   <li>'C' for CENTER</li>
   *   <li>'I' for INFLUENCE</li>
   *   <li>'X' for NONE</li>
   * </ul>
   *
   * @param card the card whose influence pattern will be displayed
   */
  public static void displayCardGrid(GameCard card) {
    System.out.println("Influence Grid for: " + card.getName());

    InfluenceType[][] grid = card.getInfluences();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j].getSymbol() + " ");
      }
      System.out.println();
    }
  }
}
