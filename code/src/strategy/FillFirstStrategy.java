package strategy;

import model.Board;
import model.Player;
import model.card.Card;

/**
 * Strategy implementation representing the strategy of Filling the first available square. It goes
 * through squares from left to right, then top down and selects the first card that can take that
 * spot.
 */
public class FillFirstStrategy implements Strategy {


  @Override
  public Move getMove(Board board, Player player) {
    if (board == null || player == null) {
      throw new IllegalArgumentException("Board or player is null");
    }
    for (int cardIdx = 0; cardIdx < player.getHandSize() - 1; cardIdx++) {
      Card card = player.getHand().get(cardIdx);
      for (int row = 0; row < board.getHeight(); row++) {
        for (int col = 0; col < board.getWidth(); col++) {
          if (isLegal(board, player, card, row, col)) {
            return new Move(row, col, false, cardIdx);
          }
        }
      }
    }
    return new Move(-1, -1, true, -1);
  }

  private boolean isLegal(Board board, Player player, Card card, int row, int col) {
    if (row < 0 || row >= board.getHeight() || col < 0 || col >= board.getWidth()) {
      return false;
    }
    if (board.getCell(row, col).getCard() != null) {
      return false;
    }

    if (board.getCell(row, col).getPawns().isEmpty()) {
      return false;
    }
    if (!board.getCell(row, col).getPawns().get(0).getOwner().equals(player)) {
      return false;
    }
    return board.getCell(row, col).getPawns().size() >= card.getCost();
  }

}
