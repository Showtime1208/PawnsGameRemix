package model;

import java.util.List;
import model.card.Card;
import model.card.Pawn;

/**
 * Interface for a specific cell on the board. Holds all relevant information for the game.
 */
public interface Cell {

  /**
   * Returns the Card in the given Cell.
   *
   * @return the Card in the cell, or null if none.
   */
  public Card getCard();

  /**
   * Sets the card in the given Cell.
   *
   * @param card the Card that will be assigned to the cell
   * @throws IllegalArgumentException if the card is null.
   */
  public void setCard(Card card);

  /**
   * Gets the Pawns in the Cell.
   *
   * @return the List of Pawns in the cell.
   */
  public List<Pawn> getPawns();


  /**
   * Adds a Pawn to the Cell.
   *
   * @throws IllegalArgumentException if the play is invalid.
   */
  public void addPawn(Player owner);

  /**
   * Returns the value modifier of the cell.
   */
  public int getValueModifier();

  /**
   * Changes the value modifier of the cell.
   * @param value the value that will be added to the modifier.
   */
  public void changeValueModifier(int value);

  /**
   * Resets the value modifier back to zero.
   */
  public void resetValueModifier();

}
