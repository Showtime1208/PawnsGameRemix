package provider.model;

import java.util.List;

import provider.model.ReadonlyPawnsBoardModel;
import provider.model.ModelStatus;

/**
 * Represents a board to play a game of PawnsBoardModel.
 */
public interface PawnsBoardModel<C extends Card> extends ReadonlyPawnsBoardModel {

  /**
   * Places a card on the board.
   * @param row represents the row the card is placed in
   * @param col represents the col the card is placed in
   * @param handIdx represents the index in the hand of the card to be placed
   * @throws IllegalStateException if the game has not started.
   * @throws IllegalArgumentException if the row, col, or handIdx is out of bounds.
   *                                  or if the target coordinate has a card, or if the number
   *                                  of pawns is less than the cost of the card to be placed.
   */
  void placeCard(int row, int col, int handIdx);

  /**
   * passes the turn to the next player.
   * @throws IllegalStateException if the game has not started.
   */
  void passTurn();

  /**
   * Starts the game. The hand size is the same for both players.
   * Allows the model to recognize the game as being in progress.
   * @param deckRed     represents the deck being used for the red player.
   * @param deckBlue    represents the deck being used for the blue player.
   * @param handSize represents the size of each player's starting hand.
   * @throws IllegalArgumentException if the hand size is larger than the deck or negative,
   *                                  or if the game has already started.
   */
  void startGame(List<C> deckRed, List<C> deckBlue, int handSize);

  void addModelStatusListener(ModelStatus observer, Player player);
}
