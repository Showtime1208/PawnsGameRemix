package model;

import java.util.List;
import model.card.Card;
import model.card.Influence;
import model.card.Pawn;

/**
 * Updated Game Board class with new rules for upgrading and devaluing cards. Handles all influence
 * and rule following logic.
 */
public class UpdatedGameBoard extends GameBoard {

  /**
   * Constructor for the game, same as the superclass.
   *
   * @param row the row.
   * @param col the col.
   */
  public UpdatedGameBoard(int row, int col) {
    super(row, col);
  }

  @Override
  protected void applyInfluence(Player player, Card card, int placedRow, int placedCol) {
    Influence[][] grid = card.getInfluenceArray();
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        Influence influence = grid[row][col];
        if (!influence.getInfluence()) {
          continue;
        }
        int targetRow = placedRow + row - 2;
        int targetCol = placedCol + col - 2;
        if (!isInBounds(targetRow, targetCol)) {
          continue;
        }
        Cell cell = getCell(targetRow, targetCol);
        switch (influence.getInfluenceKind()) {
          case CLAIM:
            handleClaimInfluence(player, cell);
            break;
          case UPGRADE:
            cell.changeValueModifier(+1);
            break;
          case DEVALUE: {
            cell.changeValueModifier(-1);
            destroyCardIfNecessary(cell);
            break;
          }
          default:
            break;
        }
      }
    }
  }

  private void handleClaimInfluence(Player player, Cell cell) {
    List<Pawn> pawns = cell.getPawns();
    if (cell.getCard() != null) {
      return;
    }
    if (pawns.isEmpty()) {
      cell.addPawn(player);
      return;
    }
    if (pawns.get(0).getOwner().equals(player)) {
      if (pawns.size() < 3) {
        cell.addPawn(player);
      }
    } else {
      pawns.forEach(pawn -> pawn.setOwner(player));
    }
  }

  private void destroyCardIfNecessary(Cell cell) {
    Card card = cell.getCard();
    if (card == null) {
      return;
    }
    int cardVal = card.getValue() + cell.getValueModifier();
    if (cardVal > 0) {
      return;
    }
    cell.setCard(null);
    cell.getPawns().clear();
    for (int i = 0; i < card.getCost(); i++) {
      cell.addPawn(card.getOwner());
    }
    cell.resetValueModifier();
  }

  @Override
  public int getRowScore(Player player, int row) {
    if (!isGameStart()) {
      throw new IllegalStateException("game has not started.");
    }
    if (row < 0 || row >= getHeight()) {
      throw new IllegalArgumentException("Invalid row index.");
    }
    int score = 0;
    for (int col = 0; col < getWidth(); col++) {
      Cell cell = getCell(row, col);
      Card card = cell.getCard();
      if (card != null && card.getOwner().equals(player)) {
        score += Math.max(0, card.getValue() + cell.getValueModifier());
      }
    }
    return score;
  }

}
