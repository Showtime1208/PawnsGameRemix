package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.InfluenceType;
import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Mutable implementation of the Pawns Board, representing the game grid,
 * ownership state, pawn positions, and card interactions.
 * Implements the {@link PawnsBoard} interface and serves as the game model’s core.
 */
public class PawnsBoardModel implements PawnsBoard {

  private final int rows;
  private final int cols;
  private final Tile[][] grid;

  /**
   * Constructs a new game board of the given size.
   * The first and last columns are initialized with pawns for RED and BLUE respectively.
   *
   * @param rows number of rows (must be > 0)
   * @param cols number of columns (must be odd and > 1)
   * @throws IllegalArgumentException if dimensions are invalid
   */
  public PawnsBoardModel(int rows, int cols) {
    if (rows <= 0 || cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Board must have positive rows and odd columns > 1");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new Tile[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        grid[r][c] = new BoardTile();
        if (c == 0) {
          grid[r][c].setOwner(Ownership.RED);
          grid[r][c].setPawnCount(1);
        } else if (c == cols - 1) {
          grid[r][c].setOwner(Ownership.BLUE);
          grid[r][c].setPawnCount(1);
        }
      }
    }
  }

  @Override
  public int getHeight() {
    return rows;
  }

  @Override
  public int getWidth() {
    return cols;
  }

  @Override
  public Tile getTileAt(int row, int col) {
    if (!isInBounds(row, col)) {
      throw new IllegalArgumentException("Coordinates out of board bounds");
    }
    return grid[row][col];
  }

  private boolean isInBounds(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  @Override
  public boolean isValidPlacement(int row, int col, GameCard card, Ownership owner) {
    if (!isInBounds(row, col)) return false;
    Tile cell = grid[row][col];
    return !cell.containsCard()
            && cell.tileOwner() == owner
            && cell.participantCount() >= card.getCost();
  }

  @Override
  public void placeCard(int row, int col, GameCard card, Ownership owner) {
    if (!isValidPlacement(row, col, card, owner)) {
      throw new IllegalArgumentException("Placement not allowed at given location");
    }
    Tile cell = grid[row][col];
    cell.setPawnCount(0);
    cell.placeCard(card, owner);
    applyInfluence(card.getInfluences(), row, col, owner);
  }

  private void applyInfluence(InfluenceType[][] influenceMap, int baseRow, int baseCol, Ownership owner) {
    for (int i = 0; i < influenceMap.length; i++) {
      for (int j = 0; j < influenceMap[i].length; j++) {
        if (influenceMap[i][j] != InfluenceType.INFLUENCE) continue;
        int r = baseRow + i - 2;
        int c = baseCol + j - 2;
        if (isInBounds(r, c)) updateInfluenceAt(r, c, owner);
      }
    }
  }

  private void updateInfluenceAt(int row, int col, Ownership owner) {
    Tile cell = grid[row][col];
    if (cell.containsCard()) return;
    if (cell.tileOwner() != owner && cell.participantCount() > 0) {
      cell.setOwner(owner);
      cell.setPawnCount(1);
    } else if (cell.participantCount() < 3) {
      cell.addPawn();
      cell.setOwner(owner);
    }
  }

  @Override
  public int getRowScore(int row, Ownership owner) {
    if (row < 0 || row >= rows) {
      throw new IllegalArgumentException("Invalid row index: " + row);
    }
    int score = 0;
    for (int c = 0; c < cols; c++) {
      Tile cell = grid[row][c];
      if (cell.containsCard() && cell.tileOwner() == owner) {
        score += cell.getCard().getValueScore();
      }
    }
    return score;
  }

  @Override
  public int getTotalScore(Ownership owner) {
    int score = 0;
    Ownership rival = owner.getOpponent();
    for (int r = 0; r < rows; r++) {
      int myScore = getRowScore(r, owner);
      int theirScore = getRowScore(r, rival);
      if (myScore > theirScore) score += myScore;
    }
    return score;
  }
}