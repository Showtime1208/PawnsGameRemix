package model.card;

import model.Player;

/**
 * Represents the SimplePawn class.
 */
public class SimplePawn implements Pawn {
  private Player owner;

  /**
   * SimplePawn constructor.
   * @param owner the pawn owner.
   */
  public SimplePawn(Player owner) {
    this.owner = owner;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public void setOwner(Player owner) {
    this.owner = owner;
  }
}
