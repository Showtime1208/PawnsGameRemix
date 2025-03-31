package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.Pawn;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyPawnsBoardModel;

import java.util.List;
import java.util.Map;

/**
 * A strategy that tries to maximize row scores.
 * It chooses a card and location that will allow the player to win a row by making
 * their row-score higher than the opponent's row-score.
 */
public class MaximizeRowScoreStrategy implements PawnsBoardStrategy {

  @Override
  public Move findBestMove(ReadOnlyPawnsBoardModel<?> model, Player player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }

    // Determine the opponent
    Player opponent = (player == Player.RED) ? Player.BLUE : Player.RED;

    // Get row scores for both players
    Map<Integer, Integer> playerScores = model.getRowScores(player);
    Map<Integer, Integer> opponentScores = model.getRowScores(opponent);

    // Get the player's hand
    List<? extends Card> hand = model.getHand(player);

    // Check each row from top to bottom
    for (int row = 0; row < model.getNumberOfRows(); row++) {
      int playerRowScore = playerScores.getOrDefault(row, 0);
      int opponentRowScore = opponentScores.getOrDefault(row, 0);

      // If player's score is lower or equal to opponent's score in this row
      if (playerRowScore <= opponentRowScore) {
        // Try each card in hand
        for (int cardIdx = 0; cardIdx < hand.size(); cardIdx++) {
          Card card = hand.get(cardIdx);
          if (card == null) {
            continue;
          }

          // Try each column in this row
          for (int col = 0; col < model.getNumberOfColumns(); col++) {
            // Check if the move is valid
            if (isValidMove(model, player, row, col, card)) {
              // Calculate the new score if this card is played
              int newScore = playerRowScore + card.getValueScore();

              // If this move would make the player's score greater than the opponent's
              if (newScore > opponentRowScore) {
                return new Move(cardIdx, row, col);
              }
            }
          }
        }
      }
    }

    // If no move can improve any row score, return null (player should pass)
    return null;
  }

  /**
   * Checks if a move is valid.
   *
   * @param model the game model
   * @param player the player making the move
   * @param row the row to place the card
   * @param col the column to place the card
   * @param card the card to place
   * @return true if the move is valid, false otherwise
   */
  private boolean isValidMove(ReadOnlyPawnsBoardModel<?> model, Player player, int row, int col, Card card) {
    // Check if the cell is empty (no card)
    if (model.getPlayerAt(row, col) != null) {
      return false;
    }

    // Check if there are pawns at this location
    Pawn pawns = model.getPawnsAt(row, col);
    if (pawns == null) {
      return false;
    }

    // Check if the pawns belong to the player
    if (pawns.getOwner() != player) {
      return false;
    }

    return pawns.getNumPawns() >= card.getCost();
  }
}