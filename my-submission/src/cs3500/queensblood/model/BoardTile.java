package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.Ownership;

/**
 * Represents a mutable tile on the game board.
 * Each tile tracks its ownership, number of pawns, and any card placed on it.
 * Implements {@link Tile} to expose mutation and inspection operations.
 */
public class BoardTile implements Tile {

  // Current owner of this tile (e.g., RED, BLUE, or NONE)
  private Ownership owner;

  // Number of pawns occupying the tile
  private int pawnCount;

  // Card currently placed on this tile, if any
  private GameCard card;

  /**
   * Initializes a tile in a neutral state — no owner, no pawns, and no card.
   * Default constructor is package-private.
   */
  BoardTile() {
    this.owner = Ownership.NONE;
    this.pawnCount = 0;
    this.card = null;
  }

  /**
   * Returns the current ownership status of the tile.
   *
   * @return the player who owns this tile, or NONE if unclaimed
   */
  @Override
  public Ownership tileOwner() {
    return this.owner;
  }

  /**
   * Assigns a new owner to this tile.
   *
   * @param owner the player taking control of the tile
   */
  @Override
  public void setOwner(Ownership owner) {
    this.owner = owner;
  }

  /**
   * Returns the number of pawns currently on the tile.
   *
   * @return total pawn count
   */
  @Override
  public int participantCount() {
    return this.pawnCount;
  }

  /**
   * Updates the number of pawns on the tile to a specific value.
   *
   * @param count the new pawn count to set
   */
  @Override
  public void setPawnCount(int count) {
    this.pawnCount = count;
  }

  /**
   * Increases the number of pawns on this tile by one.
   */
  @Override
  public void addPawn() {
    this.pawnCount += 1;
  }

  /**
   * Checks whether a card has been placed on this tile.
   *
   * @return true if a card exists on the tile, false otherwise
   */
  @Override
  public boolean containsCard() {
    return this.card != null;
  }

  /**
   * Places a card on this tile. The owner parameter may be used
   * for validation or external tracking but is not stored here.
   *
   * @param card  the card to place
   * @param owner the placing player (not stored internally)
   */
  @Override
  public void placeCard(GameCard card, Ownership owner) {
    this.card = card;
  }

  /**
   * Retrieves the card currently placed on this tile.
   *
   * @return the placed card, or null if none exists
   */
  @Override
  public GameCard getCard() {
    return this.card;
  }
}

