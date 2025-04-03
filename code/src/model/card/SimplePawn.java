package model.card;

import model.Player;

public class SimplePawn implements Pawn {

  private Player owner;

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
