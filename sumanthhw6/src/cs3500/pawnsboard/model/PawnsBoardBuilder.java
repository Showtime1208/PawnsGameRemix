package cs3500.pawnsboard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Class for customizing a PawnsBoardRuleEnforcer.
 */
public class PawnsBoardBuilder {
  private int numRows;
  private int numCols;
  private final List<CardUse> redDeck;
  private final List<CardUse> blueDeck;
  private Player firstPlayer;


  /**
   * Initializes the fields to defaults.
   */
  public PawnsBoardBuilder() {
    this.redDeck = new ArrayList<CardUse>();
    this.blueDeck = new ArrayList<CardUse>();
    this.firstPlayer = Player.RED;
  }

  /**
   * Sets the number of rows to the given number.
   *
   * @param numRows the desired number of rows.
   * @return this object for further customization.
   */
  public PawnsBoardBuilder setNumRows(int numRows) {
    this.numRows = numRows;
    return this;
  }

  /**
   * Sets the number of cols to the given number.
   *
   * @param numCols the desired number of cols.
   * @return this object for further customization.
   */
  public PawnsBoardBuilder setNumCols(int numCols) {
    this.numCols = numCols;
    return this;
  }

  /**
   * Sets the red Players deck to the given list of cards.
   *
   * @param redDeck the desired deck of cards.
   * @return this object for further customization.
   */
  public PawnsBoardBuilder setRedDeck(List<CardUse> redDeck) {
    for (CardUse r : redDeck) {
      this.redDeck.add(r);
    }
    return this;
  }

  /**
   * Sets the blue Players deck to the given list of cards.
   *
   * @param blueDeck the desired deck of cards.
   * @return this object for further customization.
   */
  public PawnsBoardBuilder setBlueDeck(List<CardUse> blueDeck) {
    for (CardUse r : blueDeck) {
      this.blueDeck.add(r);
    }
    return this;
  }

  /**
   * Sets the first player to move to the given player.
   *
   * @param firstPlayer the desired first player to move.
   * @return this object for further customization.
   */
  public PawnsBoardBuilder setFirstPlayer(Player firstPlayer) {
    this.firstPlayer = firstPlayer;
    return this;
  }

  /**
   * Creates the pawns board rule enforcer with the set customizations.
   *
   * @return a pawns board rule enforcer that is set up correctly.
   */
  public PawnsBoardRuleEnforcer build() {
    return new PawnsBoardRuleEnforcer(numRows, numCols, redDeck, blueDeck, firstPlayer);
  }


}
