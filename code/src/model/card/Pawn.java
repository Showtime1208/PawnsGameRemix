package model.card;

import model.Player;

public interface Pawn {

  /**
   * Returns the boolean value determining what color the Pawn is.
   *
   * @return the boolean value determining its color, true = red false = blue.
   */
  public Player getOwner();

  /**
   * Sets the owner of the pawn.
   *
   * @param owner which player owns the pawn
   */
  public void setOwner(Player owner);

}
