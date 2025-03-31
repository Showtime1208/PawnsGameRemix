package cs3500.queensblood.model;
import java.util.List;
import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Abstract base implementation of the read-only game model.
 * Provides common functionality for observing game state, such as
 * board dimensions, score computations, hand access, and winner determination.
 * Intended to be extended by concrete models that introduce state mutations.
 *
 * Fields:
 * - {@code board}: the current game board (grid of tiles)
 * - {@code redPlayer}: reference to the red-side player
 * - {@code bluePlayer}: reference to the blue-side player
 * - {@code currentPlayer}: the player whose turn is active
 */
public abstract class AbstractGameStructure implements ImmutableGame {
  protected ImmutableGrid board;
  protected ImmutableParticipant redPlayer;
  protected ImmutableParticipant bluePlayer;
  protected ImmutableParticipant currentPlayer;

  /**
   * Returns the number of columns in the board.
   * This corresponds to the horizontal dimension of the grid.
   *
   * @return board width (column count)
   */
  @Override
  public int getNumCols() {
    return board.getHeight();
  }

  /**
   * Returns the number of rows in the board.
   * This corresponds to the vertical dimension of the grid.
   *
   * @return board height (row count)
   */
  @Override
  public int getNumRows() {
    return board.getWidth();
  }

  /**
   * Returns an immutable view of the tile at the given coordinates.
   *
   * @param row the row index (0-based)
   * @param col the column index (0-based)
   * @return read-only view of the requested tile
   */
  @Override
  public ImmutableTile getCell(int row, int col) {
    return board.getTileAt(row, col);
  }

  /**
   * Retrieves the hand of the given participant.
   *
   * @param participant the player whose hand should be accessed
   * @return list of cards currently held by the participant
   */
  @Override
  public List<GameCard> getHand(ImmutableParticipant participant) {
    return participant.getHand();
  }

  /**
   * Computes the total score for the specified row, based on ownership.
   *
   * @param row   the row to evaluate
   * @param owner the player whose points are being calculated
   * @return score total for that row
   */
  @Override
  public int getRowScore(int row, Ownership owner) {
    return board.getRowScore(row, owner);
  }

  /**
   * Computes the total accumulated score for the given player.
   *
   * @param owner the player to score
   * @return the player’s total points across the board
   */
  @Override
  public int getTotalScore(Ownership owner) {
    return board.getTotalScore(owner);
  }

  /**
   * Checks if a card can be legally placed at the specified location.
   *
   * @param card  the card to place
   * @param row   row position on the board
   * @param col   column position on the board
   * @param owner the placing player
   * @return true if the placement is allowed; false otherwise
   */
  @Override
  public boolean isValidPlacement(GameCard card, int row, int col, Ownership owner) {
    return board.isValidPlacement(row, col, card, owner);
  }

  /**
   * Returns the participant whose turn is currently active.
   *
   * @return the current player
   */
  @Override
  public ImmutableParticipant getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Determines which player has won the game.
   * Returns null in the case of a tie.
   *
   * @return {@code Ownership.RED}, {@code Ownership.BLUE}, or null for tie
   */
  @Override
  public Ownership getWinner() {
    int red = board.getTotalScore(Ownership.RED);
    int blue = board.getTotalScore(Ownership.BLUE);

    if (red == blue) {
      return null;
    }
    return red > blue ? Ownership.RED : Ownership.BLUE;
  }

  /**
   * Returns a read-only version of the current game board.
   *
   * @return the immutable board view
   */
  @Override
  public ImmutableGrid getReadonlyBoard() {
    return this.board;
  }
}
