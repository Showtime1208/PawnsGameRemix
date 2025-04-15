package provider.model;

import model.Cell;
import model.Player;
import model.card.GameCard;

public class CellAdapter implements ProviderCell {

  public final Cell cell;
  private PlayerEnum owner;
  private Pawns pawns;
  private ProviderCard card;

  public CellAdapter(Cell cell, Player owner) {
    PlayerEnum owner1;
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null");
    }
    this.cell = cell;

    if (owner == null) {
      owner1 = PlayerEnum.None;
      return;
    }
    if (owner.getIsRed()) {
      owner1 = PlayerEnum.Red;
    } else  {
      owner1 = PlayerEnum.Blue;
    }
    this.owner = owner1;
  }

  @Override
  public PlayerEnum getOwner() {
    return owner;
  }

  @Override
  public int getPawns() {
    return this.cell.getPawns().size();
  }

  @Override
  public ProviderCard getCard() {
    return null;
  }

  @Override
  public int getScore() {
    return 0;
  }

  @Override
  public void addPawn() {
    try {
      pawns.increment();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void placeCard(ProviderCard providerCard) {
    this.cell.setCard(new GameCard(providerCard.getName(), providerCard.getInfluence()));
  }

  @Override
  public void changeOwner(PlayerEnum newOwner) {
    this.owner = newOwner;
  }
}
