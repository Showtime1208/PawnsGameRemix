package strategy;

import java.util.ArrayList;
import model.Cell;
import java.util.List;
import model.Player;
import model.card.Card;
import model.card.Pawn;
import model.card.SimplePawn;

/**
 * Mock cell class for the MockBoard. No rules once again.
 */
public class MockCell implements Cell {
  private final StringBuilder log;
  private final int row;
  private final int col;

  private Card card;
  private List<Pawn> pawns;

  /**
   * Constructor for the mock cell.
   * @param log the log.
   * @param row the row.
   * @param col the col.
   */
  public MockCell(StringBuilder log, int row, int col) {
    this.log = log;
    this.row = row;
    this.col = col;
    this.pawns = new ArrayList<>();
  }

  @Override
  public Card getCard() {
    log.append("Cell(").append(row).append(",").append(col).append(").getCard called\n");
    return card;
  }

  @Override
  public void setCard(Card card) {
    log.append("Cell(").append(row).append(",").append(col)
        .append(").setCard called with card=")
        .append(card)
        .append("\n");
    this.card = card;
  }

  @Override
  public List<Pawn> getPawns() {
    log.append("Cell(").append(row).append(",").append(col).append(").getPawns called\n");

    return pawns;
  }

  @Override
  public void addPawn(Player owner) {
    log.append("Cell(").append(row).append(",").append(col)
        .append(").addPawn called with owner=")
        .append(owner)
        .append("\n");
    pawns.add(new SimplePawn(owner));
  }
}