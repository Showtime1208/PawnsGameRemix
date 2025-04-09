package provider.model;

import java.util.List;

import provider.model.Card;
import provider.model.Cell;
import provider.model.Player;

/**
 * A version of the PawnsBoard game that cannot be modified. This version is read only, and
 * properties of the game can only be viewed, never changed.
 */
public interface ReadonlyPawnsBoardModel {

  /**
   * Checks if the game is over.
   * A game over state is defined as if the board has no remaining empty spaces, or if both players
   * pass their turn consecutively.
   * @return true if the game is over, false if not.
   * @throws IllegalStateException if the game has not started yet.
   */
  boolean gameOver();

  /**
   * Returns the current score of the game.
   * @param row the row being scored.
   * @param player the player whose score is calculated.
   * @return the score of the game.
   * @throws IllegalStateException if the game is not over or has not started.
   */
  int getRowScore(int row, Player player);

  /**
   * Gets winner of the game.
   * @return player with highest score.
   * @throws IllegalStateException if game is not over.
   */
  Player getWinner();

  /**
   * Determines the width in number of cells of this PawnsBoardModel's playing board.
   * @return width of this PawnsBoardModel's playing board.
   */
  int getWidth();

  /**
   * Determines the height in number of cells of this PawnsBoardModel's playing board.
   * @return height of this PawnsBoardModel's playing board.
   */
  int getHeight();

  /**
   * Determines what cell is in a specified position in this PawnsBoardModel's playing board.
   * @param row represents the row position to query.
   * @param col represents the column position to query.
   * @return a deep copy of the cell occupying the position given on the board.
   * @throws IllegalStateException if the game is not in progress
   * @throws IllegalArgumentException if the row or col are out of bounds.
   */
  Cell getCellAt(int row, int col);

  /**
   * Gets the current player whose turn it is.
   * @return the player whose turn it is.
   */
  Player getTurn();

  /**
   * Gets the current Cells which make up the Board of this game.
   * @return the game's board.
   */
  Cell[][] getBoard();

  /**
   * Retrieves the hand of the given player.
   * @param player represents the player whose hand we wish to retrieve.
   * @return the hand of the given player.
   */
  List<Card> getHand(Player player);

  /**
   * Determines who owns a specified cell on the game board.
   * @param row represents the row coordinate of the cell.
   * @param col represents the col coordinate of the cell.
   * @return the owner of the specified cell.
   */
  Player getOwnerOf(int row, int col);

  /**
   * Determines if a given move is a playable move.
   * @param handIdx represents the index of the card (0-indexed) we wish to play.
   * @param row represents the row coordinate of the cell the card will be placed on.
   * @param col represents the col coordinate of the cell the card will be placed on.
   * @return true if the move is playable, false otherwise.
   * @throws IllegalArgumentException if row, col, or handIdx are out of bounds.
   * @throws IllegalStateException if the game has not started yet.
   */
  boolean isMoveLegal(int handIdx, int row, int col);

  /**
   * Retrieves the score of the given player.
   * @param player represents the player we wish to retrieve the score for.
   * @return the score of the given player.
   * @throws IllegalStateException if game has not started.
   */
  int getPlayerScore(Player player);
}
