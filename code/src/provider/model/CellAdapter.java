package provider.model;

import java.util.List;
import model.Cell;
import model.Player;
import model.card.Card;
import model.card.GameCard;
import model.card.Pawn;

public class CellAdapter implements ProviderCell {

  public final Cell cell;

  public CellAdapter(Cell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Cell argument cannot be null");
    }
    this.cell = cell;

  }

  @Override
  public PlayerEnum getOwner() {
    List<Pawn> pawns = cell.getPawns();
    if (pawns.isEmpty()) {
      return PlayerEnum.None;
    }
    Player owner = pawns.get(0).getOwner();
    return owner.getIsRed() ? PlayerEnum.Red : PlayerEnum.Blue;
  }

  @Override
  public int getPawns() {
    return this.cell.getPawns().size();
  }

  @Override
  public ProviderCard getCard() {
    Card card =  this.cell.getCard();
    if (card == null) {
      return null;
    }
    Player owner = card.getOwner();
    return new CardAdapter(card);
  }

  @Override
  public int getScore() {
    Card card  = this.cell.getCard();
    return (card != null) ? card.getValue() : 0;
  }

  @Override
  public void addPawn() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void placeCard(ProviderCard providerCard) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void changeOwner(PlayerEnum newOwner) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
