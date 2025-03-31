package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.Pawn;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyPawnsBoardModel;

import java.util.List;

/**
 * A strategy that chooses the first card and location that can be played on.
 */
public class FillFirstStrategy implements PawnsBoardStrategy {

  @Override
  public Move findBestMove(ReadOnlyPawnsBoardModel<?> model, Player player) {
    if (model == null || player == null) {
      throw new IllegalArgumentException("Model and player cannot be null");
    }

    List<? extends Card> hand = model.getHand(player);

    for (int cardIdx = 0; cardIdx < hand.size(); cardIdx++) {
      Card card = hand.get(cardIdx);
      if (card == null) {
        continue;
      }

      // Try each position on the board
      for (int row = 0; row < model.getNumberOfRows(); row++) {
        for (int col = 0; col < model.getNumberOfColumns(); col++) {
          // Check if the move is valid
          if (isValidMove(model, player, row, col, card)) {
            return new Move(cardIdx, row, col);
          }
        }
      }
    }

    // If no valid move is found, return null (player should pass)
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