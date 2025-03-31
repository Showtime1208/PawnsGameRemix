package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;
import java.util.List;

/**
 * Represents a read-only view of the Pawns Board game.
 * Provides inspection methods for querying board layout, player state,
 * scores, and game status without exposing any mutability.
 */
public interface ImmutableGame {

  /**
   * Gets the number of rows on the game board.
   *
   * @return the board's row count
   */
  int getNumRows();

  /**
   * Gets the number of columns on the game board.
   *
   * @return the board's column count
   */
  int getNumCols();

  /**
   * Retrieves an immutable view of the tile at the given position.
   *
   * @param row the row index
   * @param col the column index
   * @return the read-only tile at the specified coordinates
   * @throws IllegalArgumentException if the indices are out of bounds
   */
  ImmutableTile getCell(int row, int col);

  /**
   * Returns a snapshot of the given participant's hand.
   * The returned list cannot be modified.
   *
   * @param player the participant whose hand is being viewed
   * @return list of cards currently held by the player
   */
  List<GameCard> getHand(ImmutableParticipant player);

  /**
   * Checks whether placing the specified card at the given location is valid
   * for the specified player. This is a non-mutating check.
   *
   * @param card  the card being placed
   * @param row   the target row index
   * @param col   the target column index
   * @param owner the player attempting the placement
   * @return true if the move is valid; false otherwise
   */
  boolean isValidPlacement(GameCard card, int row, int col, Ownership owner);

  /**
   * Determines if the game has reached a terminal state.
   *
   * @return true if the game is over; false otherwise
   */
  boolean isGameOver();

  /**
   * Returns the winner of the game, if one exists.
   * If the game is still ongoing or ends in a tie, this returns null.
   *
   * @return the winning player, or null for tie/incomplete
   */
  Ownership getWinner();

  /**
   * Returns the player whose turn is currently active.
   *
   * @return the current participant
   */
  ImmutableParticipant getCurrentPlayer();

  /**
   * Computes the total value of all cards owned by a player on a specific row.
   *
   * @param row   the row to evaluate
   * @param owner the player whose score is being calculated
   * @return total value score on that row
   */
  int getRowScore(int row, Ownership owner);

  /**
   * Computes the total score for a player across all rows they control.
   *
   * @param owner the player to score
   * @return overall score for the specified player
   */
  int getTotalScore(Ownership owner);

  /**
   * Retrieves an immutable reference to the entire board.
   *
   * @return the current game board as a read-only grid
   */
  ImmutableGrid getReadonlyBoard();
}
