package cs3500.pawnsboard.model;

/**
 * The public behaviors to play a game of PawnsBoard.
 *
 */
public interface PawnsBoardModel extends ReadOnlyPawnsBoardModel<CardUse> {


  /**
   * Starts the game with the given deck and hand size. If shuffle is set to true,
   * then the deck is shuffled prior to dealing the hand.
   *
   * <p>Note that modifying the deck given here outside this method should have no effect
   * on the game itself.
   *
   * @throws IllegalStateException    if the game has already been started
   * @throws IllegalArgumentException if the deck is null or contains a null object,
   *                                  if handSize is not positive (i.e. 0 or less),
   *                                  or if the deck does not contain enough cards to fill the board
   *                                  AND fill a starting hand
   */
  void startGame(Player startingPlayer, int handSize);



  /**
   * Passes the current turn. If the last move was also a pass
   * the game is over.
   */
  void passCurrentTurn();



  /**
   * Places a card from the hand to a given position on the polygonal board and then
   * draws a card from the deck if able.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row     row to place the card in (0-index based)
   * @param col     column to place the card in (0-index based)
   * @throws IllegalStateException    if the game has not started or there is a card at the given
   *                                  position
   * @throws IllegalArgumentException if cardIdx is out of bounds of the hand or
   *                                  row and col do not indicate a position on the polygon
   */
  void placeCardInPosition(int cardIdx, int row, int col);




}
