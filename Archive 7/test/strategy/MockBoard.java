package strategy;

import model.Board;
import model.Cell;
import model.Player;
import model.card.Card;

import java.util.List;
import model.card.Influence;
import model.card.Pawn;

/**
 * Testable Mock class for strategy tests. Fully functional board, without any rules.
 */
public class MockBoard implements Board {

  private final StringBuilder log;
  private final int width;
  private final int height;
  private final Player p1;
  private final Player p2;
  private Cell[][] board;

  /**
   * Construct a mock Board, specifying how big it is and whether the game is over.
   * We also store row-scores for each row for each player, so that
   * getRowScore calls can return predictable values.
   */
  public MockBoard(StringBuilder log,
      int width,
      int height,
      boolean gameOver,
      Player p1,
      Player p2) {
    this.log = log;
    this.width = width;
    this.height = height;
    this.p1 = p1;
    this.p2 = p2;
    this.board = new MockCell[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        this.board[row][col] = new MockCell(log, row, col);
      }
    }
    for (int row = 0; row < height; row++) {
      this.board[row][0].addPawn(p1);
      this.board[row][width - 1].addPawn(p2);
    }
  }

  @Override
  public void startGame(Player player1, Player player2) {
    log.append("startGame called\n");
  }

  @Override
  public void placeCard(Player player, int handIdx, int row, int col) {
    log.append("placeCard called with (player=")
        .append(player)
        .append(",handIdx=")
        .append(handIdx)
        .append(",row=")
        .append(row)
        .append(",col=")
        .append(col)
        .append(")\n");

    this.board[row][col].setCard(player.getCardAt(handIdx));
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver called\n");
    return false;
  }

  @Override
  public Cell getCell(int row, int col) {
    log.append("getCell called with (row=")
        .append(row).append(",col=")
        .append(col).append(")\n");
    return board[row][col];
  }

  @Override
  public int getRowScore(Player player, int row) {
    log.append("getRowScore called with (player=").append(player)
        .append(",row=").append(row).append(")\n");
    int rowScore = 0;
    for (int col = 0; col < width; col++) {
      Cell cell = board[row][col];
      Card card = cell.getCard();
      if (card != null) {
        if (card.getOwner().equals(player)) {
          rowScore += card.getValue();
        }
      }
    }
    log.append(rowScore + "\n");
    return rowScore;
  }

  @Override
  public int getTotalScore(Player player) {
    log.append("getTotalScore called with player=")
        .append(player)
        .append("\n");
    int total = 0;
    Player red = this.p1;
    Player blue = this.p2;
    for (int row = 0; row < height; row++) {
      int redScore = getRowScore(red, row);
      int blueScore = getRowScore(blue, row);
      if (redScore > blueScore) {
        if (player == red) {
          log.append(redScore).append("R").append("\n");
          total += redScore;
        }
      } else if (blueScore > redScore) {
        if (player == blue) {
          log.append(blueScore).append("B").append("\n");
          total += blueScore;
        }
      } else {
        continue;
      }
    }
    log.append(total + "\n");
    return total;
  }

  @Override
  public void passTurn(Player player) {
    log.append("passTurn called with player=")
        .append(player)
        .append("\n");
  }

  @Override
  public int getWidth() {
    log.append("getWidth called\n");
    return width;
  }

  @Override
  public int getHeight() {
    log.append("getHeight called\n");
    return height;
  }

  @Override
  public Player getP1() {
    log.append("getP1 called\n");
    return p1;
  }

  @Override
  public Player getP2() {
    log.append("getP2 called\n");
    return p2;
  }

  @Override
  public boolean getTurn() {
    log.append("getTurn called\n");
    return false;
  }

  @Override
  public Cell[][] getCopy() {
    log.append("getCopy called\n");
    Cell[][] copy = new Cell[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Cell cell = board[row][col];
        copy[row][col] = cell;
      }
    }
    return copy;
  }

  @Override
  public Player getWinner() {
    log.append("getWinner called\n");
    return null;
  }

  @Override
  public Card getCardAt(int row, int col) {
    log.append("getCard called with (row=")
        .append(row).append(",col=")
        .append(col).append(")\n");
    return board[row][col].getCard();
  }

  @Override
  public List<Pawn> getPawnsAt(int row, int col) {
    log.append("getPawns called with (row=")
        .append(row).append(",col=")
        .append(col).append(")\n");
    return getCell(row, col).getPawns();
  }

  private void applyInfluence(Player player, Card card, int placedRow, int placedCol) {
    Influence[][] grid = card.getInfluenceArray();
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        boolean influence = grid[row][col].getInfluence();
        if (!influence) {
          continue;
        }
        int rowOffset = row - 2;
        int colOffset = col - 2;
        int targetRow = placedRow + rowOffset;
        int targetCol = placedCol + colOffset;
        Cell targetCell = board[targetRow][targetCol];
        if (targetCell.getCard() != null) {
          continue;
        }
        List<Pawn> pawns = targetCell.getPawns();
        if (pawns.isEmpty()) {
          targetCell.addPawn(player);
        }
        else {
          if (pawns.get(0).getOwner() == player) {
            if (pawns.size() < 3) {
              targetCell.addPawn(player);
            }
          }
          else {
            for (Pawn pawn : pawns) {
              pawn.setOwner(player);
            }
          }
        }
      }
    }
  }
}