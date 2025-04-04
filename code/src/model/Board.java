package model;

import view.TurnListener;

/**
 * Model interface. Handles all game-related logic, and delegates to component classes.
 */
public interface Board extends ReadOnlyBoard {

  /**
   * Starts a game of PawnsGame with the two provided players. These players have already been
   * initialized with their respective decks.
   *
   * @param player1 the first Player (red side).
   * @param player2 the second Player(blue side).
   * @throws IllegalArgumentException if the decks are too small or a player is null.
   * @throws IllegalStateException    if the game has started or player's hands are different.
   */
  public void startGame(Player player1, Player player2);

  /**
   * Places a card from the Player's hand onto the board.
   *
   * @param player  the player that will be placing the card.
   * @param handIdx tbe card that will be placed.
   * @param row     the Board row.
   * @param col     the Board column.
   * @throws IllegalArgumentException if the player is null;
   * @throws IllegalStateException    if the coordinates are out of bounds.
   */
  public void placeCard(Player player, int handIdx, int row, int col);


  /**
   * Passes the turn.
   *
   * @param player the player that will pass the turn.
   * @throws IllegalArgumentException if the Player is null.
   * @throws IllegalStateException    if the game has not started.
   */
  public void passTurn(Player player);

  /**
   * Adds listeners
   */
  void addTurnListener(TurnListener listener);

  void removeTurnListener(TurnListener listener);
}
