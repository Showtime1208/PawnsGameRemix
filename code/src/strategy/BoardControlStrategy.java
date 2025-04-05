package strategy;

import java.util.List;
import model.Board;
import model.Cell;
import model.Player;
import model.card.Card;
import model.card.Influence;
import model.card.Pawn;

/**
 * Strategy Representation representing the strategy of controlling the most squares on the board.
 * The Strategy breaks ties by selecting the uppermost, then the leftmost board position. For the
 * hand, the strategy breaks ties by taking the left most card.
 */
public class BoardControlStrategy implements Strategy {

  @Override
  public Move getMove(Board board, Player player) {
    if (board == null || player == null) {
      throw new IllegalArgumentException("Board/player cannot be null.");
    }
    Move bestMove = new Move(-1, -1, true, -1);
    int bestOwnedCount = countCellsOwned(board.getCopy(), player);
    for (int handIdx = 0; handIdx < player.getHandSize(); handIdx++) {

      Card card = player.getHand().get(handIdx);
      for (int row = 0; row < board.getHeight(); row++) {
        for (int col = 0; col < board.getWidth(); col++) {

          if (isLegal(board.getCopy(), player, card, row, col)) {
            Cell[][] testCopy = testPlacement(board, player, card, handIdx, row, col);
            if (testCopy == null) {
              continue;
            }

            int ownershipCount = countCellsOwned(testCopy, player);
            if (ownershipCount > bestOwnedCount) {
              bestOwnedCount = ownershipCount;
              bestMove = new Move(row, col, false, handIdx);
            } else if (ownershipCount == bestOwnedCount) {

              if (tieBreaker(row, col, handIdx, bestMove)) {
                bestMove = new Move(row, col, false, handIdx);
              }
            }
          }
        }
      }
    }
    return bestMove;
  }

  private boolean isLegal(Cell[][] board, Player player, Card card, int row, int col) {
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
      return false;
    }
    if (board[row][col].getCard() != null) {
      return false;
    }

    if (board[row][col].getPawns().isEmpty()) {
      return false;
    }
    if (!board[row][col].getPawns().get(0).getOwner().equals(player)) {
      return false;
    }
    return board[row][col].getPawns().size() >= card.getCost();
  }

  protected Cell[][] testPlacement(Board board, Player player, Card card, int handIdx, int row,
      int col) {
    Cell[][] copy = board.getCopy();
    try {
      copy[row][col].setCard(card);
      applyInfluenceOnCellArray(player, card, copy, row, col);
      return copy;
    } catch (IllegalArgumentException e) {
      return null;
    }

  }

  protected int countCellsOwned(Cell[][] board, Player player) {
    int ownedCount = 0;
    int rows = board.length;
    int cols = board[0].length;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (board[row][col].getCard() != null) {
          if (board[row][col].getCard().getOwner().equals(player)) {
            ownedCount++;
          }
        } else {
          if (!board[row][col].getPawns().isEmpty()) {
            if (board[row][col].getPawns().get(0).getOwner().equals(player)) {
              ownedCount++;
            }
          }
        }
      }
    }
    return ownedCount;
  }


  protected boolean tieBreaker(int row, int col, int handIdx, Move bestMove) {
    if (bestMove.isPass()) {
      return true;
    }
    if (row < bestMove.getRow()) {
      return true;
    } else if (row == bestMove.getRow()) {
      if (col < bestMove.getCol()) {
        return true;
      } else if (col == bestMove.getCol()) {
        return handIdx < bestMove.getCardIdx();
      }
    }
    return false;
  }

  private void applyInfluenceOnCellArray(Player player, Card card, Cell[][] board, int placedRow,
      int placedCol) {
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
        if (!canApplyInfluence(board, targetRow, targetCol)) {
          continue;
        }
        Cell targetCell = board[targetRow][targetCol];
        if (targetCell.getCard() != null) {
          continue;
        }
        List<Pawn> pawns = targetCell.getPawns();
        if (pawns.isEmpty()) {
          targetCell.addPawn(player);
        } else {
          if (pawns.get(0).getOwner() == player) {
            if (pawns.size() < 3) {
              targetCell.addPawn(player);
            }
          } else {
            for (Pawn pawn : pawns) {
              pawn.setOwner(player);
            }
          }
        }
      }
    }
  }

  private boolean canApplyInfluence(Cell[][] board, int row, int col) {
    if (row < 0 || row >= board.length) {
      return false;
    }
    if (col < 0 || col >= board[0].length) {
      return false;
    }
    return board[row][col].getCard() == null;
  }

}
