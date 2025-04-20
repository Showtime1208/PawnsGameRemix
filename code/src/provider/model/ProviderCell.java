package provider.model;

/**
 * Represents a Cell for a game of PawnsBoardModel.
 */
public interface ProviderCell {

  /**
   * Returns the player that owns the cell, or none if no player owns it.
   *
   * @return the owner of the cell.
   */
  PlayerEnum getOwner();

  /**
   * Returns the number of pawns in the cell.
   *
   * @return the value of pawns in the cell, represented by a pawns enum for 0, 1, 2, or 3.
   */
  int getPawns();

  /**
   * Returns the card that is occupied in the cell.
   *
   * @return the card contained by the cell, or null if the cell does not contain a card.
   */
  ProviderCard getCard();

  /**
   * Returns the value of the card in the cell.
   *
   * @return the value of the card in the cell.
   */
  int getScore();

  /**
   * Adds a pawn to the current board if allowed by the rules of the game. Will not add a pawn if
   * the current number of pawns is 3, the maximum, or if there is a card placed in the cell, in
   * which case pawns are irrelevant.
   */
  void addPawn();

  /**
   * Places the given card in the cell. Will not place the card if the cost of the card is above the
   * number of pawns on the board. Sets the number of pawns in the cell to 0, as the cell is now
   * filled by a card.
   *
   * @param providerCard the card to be placed in the cell.
   * @throws IllegalArgumentException if the given card is null.
   */
  void placeCard(ProviderCard providerCard);

  /**
   * Changes the owner of the cell to the given one.
   *
   * @param newOwner the new owner of the cell.
   * @throws IllegalArgumentException if the new owner is null.
   */
  void changeOwner(PlayerEnum newOwner);
}
