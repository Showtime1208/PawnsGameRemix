package strategy;

import model.Board;
import model.Player;
import model.card.Card;

/**
 * Strategy implementation representing the strategy of maximizing the row score. The strategy will
 * go from top to bottom, looking for any rows where the Player is losing or even. They will then
 * play the first card and location option that will increase that score above the opponent's row
 * score. If the Player can't make their score more than the opponents in any row, it passes.
 */
public class MaximizeRowScoreStrategy implements Strategy {

  @Override
  public Move getMove(Board board, Player player) {
    if (player == null || board == null) {
      throw new IllegalArgumentException("Board/player cannot be null.");
    }
    Player opponent = (board.getP1() == player) ? board.getP2() : board.getP1();
    for (int row = 0; row < board.getHeight(); row++) {
      int myScore = board.getRowScore(player, row);
      int oppScore = board.getRowScore(opponent, row);
      if (myScore <= oppScore) {
        for (int cardIdx = 0; cardIdx < player.getHandSize(); cardIdx++) {
          Card card = player.getHand().get(cardIdx);
          for (int col = 0; col < board.getWidth(); col++) {
            if (isLegal(board, player, card, row, col)) {
              int newScore = scoreIfPlaced(board, player, card, row);
              if (newScore > oppScore) {
                return new Move(row, col, false, cardIdx);
              }
            }
          }
        }
      }
    }
    return new Move(-1, -1, true, -1);
  }

  private int scoreIfPlaced(Board board, Player player, Card card, int row) {
    int currScore = board.getRowScore(player, row);
    return currScore + card.getValue();
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
    if (board.getCell(row, col).getPawns().size() < card.getCost()) {
      return false;
    }
    return true;
  }
}
