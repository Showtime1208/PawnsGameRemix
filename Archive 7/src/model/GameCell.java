package model;

import model.card.Card;
import model.card.Pawn;
import model.card.SimplePawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of th Cell interface. holds a card and a list of pawns.
 */
public class GameCell implements Cell {
  private Card card;
  private List<Pawn> pawns;

  /**
   * Constructor for the gameCell.
   * @param card the card that will be in the cell.
   * @throws IllegalArgumentException if the card is null.
   */
  public GameCell(Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null.");
    }
    this.card = card;
    this.pawns = new ArrayList<>();
  }

  /**
   * Constructor for a cell that has no card, initializes the card to null.
   */
  public GameCell() {
    this.card = null;
    this.pawns = new ArrayList<>();
  }

  @Override
  public Card getCard() {
    return card;
  }

  @Override
  public void setCard(Card card) {
    if (card != null && card.getValue() <= pawns.size()) {
      this.card = card;
    } else  {
      throw new IllegalArgumentException("Card is null/too large.");
    }
  }

  @Override
  public List<Pawn> getPawns() {
    return pawns;
  }


  @Override
  public void addPawn(Player owner) {
    if (this.pawns.isEmpty()) {
      pawns.add(new SimplePawn(owner));
    } else if (this.pawns.get(0).getOwner().equals(owner) && this.pawns.size() < 3) {
      pawns.add(new SimplePawn(owner));
    } else {
      throw new IllegalStateException("Pawns in square have a different owner");
    }
  }
}
