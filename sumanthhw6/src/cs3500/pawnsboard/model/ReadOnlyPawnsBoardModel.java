package cs3500.pawnsboard.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReadOnlyPawnsBoardModel<C extends Card> {

  /**
   * Gets the player whose turn it is right now.
   *
   * @return the current player.
   */
  Player getCurrentTurn();

  /**
   * Gets the number of rows in the board.
   *
   * @return the number of rows.
   */
  int getNumberOfRows();

  /**
   * Gets the number of cols in the board.
   *
   * @return the number of cols.
   */
  int getNumberOfColumns();


  /**
   * Returns the card in the indicated position on the board. If there is no card on the board
   * and the position is valid, the method will return null.
   *
   * @param row the row to access
   * @param col the column to access
   * @return the number pawns in the valid position for the given player
   * @throws IllegalArgumentException if the row and column are not a valid location
   *                                  for a card in the polygonal board
   */

  Pawn getPawnsAt(int row, int col);

  /**
   * Gets the player at target location.
   *
   * @return null if position has no card and otherwise the owner of the card at the
   * valid location.
   * @throws IllegalArgumentException if the row and column are not a valid location
   *                                  for a card in the polygonal board
   */
  Player getPlayerAt(int row, int col);


  /**
   * RowScores of the target player.
   *
   * @return a map of the row number to the score of the given player at that row.
   */
  Map<Integer, Integer> getRowScores(Player player);


  /**
   * Returns a copy of the chosen player's current hand. If their hand is empty, then an empty
   * list is returned.
   *
   * @return a copy of the player's current hand
   * @throws IllegalStateException if the game has not started
   */
  List<CardUse> getHand(Player player);


  /**
   * Returns true if the game is over. The implementation must
   * describe what it means for the game to be over.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Gets a copy of the Board Cards. Copies the Cards within as well
   * for added safety.
   *
   * @return A copy of the board cards.
   * @throws IllegalStateException if the game has not started
   */
  Player[][] getCardBoard();

  /**
   * Gets a copy of the Board Pawns. Copies the Pawns within as well
   * for added safety.
   *
   * @return A copy of the board pawns.
   * @throws IllegalStateException if the game has not started
   */
  Pawn[][] getPawnsBoard();

  Optional<Player> getWinner();

}
