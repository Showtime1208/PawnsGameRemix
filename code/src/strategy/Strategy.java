package strategy;

import model.Board;
import model.Player;

/**
 * The Strategy interface defines the contract for a move-generation strategy.
 * <p>
 * Implementations of this interface are responsible for determining the next move for a player
 * given the current state of the board. Different strategies (e.g., aggressive, defensive, random,
 * etc.) can be implemented to vary the behavior of the game.
 * </p>
 */
public interface Strategy {

  /**
   * Determines the next move for the given player on the current board.
   *
   * @param board  the current state of the game board
   * @param player the player for whom the move is being determined
   */
  public Move getMove(Board board, Player player);


}
