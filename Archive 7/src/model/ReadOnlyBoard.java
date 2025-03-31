package model;

import model.card.Card;
import model.card.Pawn;

import java.util.List;

/**
 * Represents a ReadOnlyBoard for the view to use to \
 * prevent any changes from being made.
 */
public interface ReadOnlyBoard {

  /**
   * Returns the boolean value if the game is over.
   * @return whether the game is over;
   * @throws IllegalStateException if the game has not started.
   */
  public boolean isGameOver();

  /**
   * Gets the cell at the desired index.
   * @param row the row idx.
   * @param col the col idx.
   * @return the Cell.
   * @throws IllegalArgumentException if the idx are invalid.
   */

  public Cell getCell(int row, int col);
  /**
   * Returns the individual row score of the player.
   * @param player the player that you want the score of.
   * @param row the rowIdx you want.
   * @return the score fo the row.
   * @throws IllegalStateException if the game hasn't started.
   * @throws IllegalArgumentException if the row is invalid.
   */

  public int getRowScore(Player player, int row);

  /**
   * Returns that players total score.
   * @param player the player that you want the score of.
   * @return the total score value.
   * @throws IllegalStateException if the game hasn't started.
   * @throws IllegalArgumentException if the player is null.
   */
  public int getTotalScore(Player player);

  /**
   * Gets the width of the board.
   * @return the width of the board.
   */
  public int getWidth();

  /**
   * The height of the board.
   * @return the height of the board.
   */
  public int getHeight();

  /**
   * Returns player1.
   */
  public Player getP1();

  /**
   * Returns player2.
   */
  public Player getP2();

  /**
   * Returns the turn.
   * @return true if p1, false if p2.
   */
  public boolean getTurn();

  /**
   * Gets a copy of the board.
   * @return a copy of the board.
   */
  public Cell[][] getCopy();

  /** THROWS A NULL POINTER EXCEPTION MAKE SURE TO ACCOUNT FOR IT.
   * Gets the winner of the game.
   * @return the winner of the game, or null if tied.
   * @throws IllegalStateException if the game is not over or has not started.
   */
  public Player getWinner();

  /**
   * Returns the card at the specified cell.
   * If there is no card, returns null.
   */
  Card getCardAt(int row, int col);

  /**
   * Returns a list of pawns present at the specified cell.
   * Returns an empty list if there are no pawns.
   */
  List<Pawn> getPawnsAt(int row, int col);
}
