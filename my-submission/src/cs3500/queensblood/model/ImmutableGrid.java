package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Provides a read-only interface to the game board.
 * Allows external systems to inspect board structure, tile contents,
 * scoring metrics, and card placement validity without modifying state.
 */
public interface ImmutableGrid {

  /**
   * Gets the total number of rows in the board layout.
   *
   * @return the vertical dimension of the grid
   */
  int getHeight();

  /**
   * Gets the total number of columns in the board layout.
   *
   * @return the horizontal dimension of the grid
   */
  int getWidth();

  /**
   * Returns an immutable view of the tile located at the given coordinates.
   *
   * @param row the row index
   * @param col the column index
   * @return the tile at the specified position
   */
  ImmutableTile getTileAt(int row, int col);

  /**
   * Computes the total score for a given player based on all rows they control.
   *
   * @param ownership the player to evaluate (RED or BLUE)
   * @return the full score across the board
   */
  int getTotalScore(Ownership ownership);

  /**
   * Calculates the total score for a single row, counting only
   * the cards owned by the specified player.
   *
   * @param row   the index of the row
   * @param owner the player whose score is being calculated
   * @return the cumulative row score for that player
   */
  int getRowScore(int row, Ownership owner);

  /**
   * Checks whether a card placement is valid at the given coordinates
   * for a specific player, based on current game board conditions.
   *
   * @param row   the target row
   * @param col   the target column
   * @param card  the card being placed
   * @param owner the player attempting to place the card
   * @return true if the placement is permitted; false otherwise
   */
  boolean isValidPlacement(int row, int col, GameCard card, Ownership owner);
}
