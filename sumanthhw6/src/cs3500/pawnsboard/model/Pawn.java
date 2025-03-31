package cs3500.pawnsboard.model;

import java.util.Objects;

/**
 * Represents a group of Pawns on the board in a game of PawnsBoard.
 * It has a number of pawns and is owned by a Player.
 */
public class Pawn {
  private int numPawns;
  private Player playerOwnedBy;

  /**
   * Constructs a pawn with the given of pawns, owned by the given player.
   *
   * @param numPawns      the number of pawns.
   * @param playerOwnedBy the owner of these pawns.
   */
  public Pawn(int numPawns, Player playerOwnedBy) {
    this.numPawns = numPawns;
    this.playerOwnedBy = playerOwnedBy;
  }

  /**
   * Gets the number of pawns that this has.
   *
   * @return the number of pawns.
   */
  public int getNumPawns() {
    return numPawns;
  }

  /**
   * Sets the number of pawns that this has.
   */
  public void setNumPawns(int numPawns) {
    this.numPawns = numPawns;
  }

  /**
   * Gets the owner of this pawn.
   *
   * @return the owner of pawns.
   */
  public Player getOwner() {
    return playerOwnedBy;
  }

  /**
   * Changes the owner of this to the other player.
   */
  public void changePlayerOwnedBy() {
    switch (playerOwnedBy) {
      case BLUE:
        playerOwnedBy = Player.RED;
        break;
      case RED:
        playerOwnedBy = Player.BLUE;
        break;
      default:
        throw new IllegalArgumentException("Invalid player owned by");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pawn pawn = (Pawn) o;
    return numPawns == pawn.numPawns && playerOwnedBy == pawn.playerOwnedBy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numPawns, playerOwnedBy);
  }
}
