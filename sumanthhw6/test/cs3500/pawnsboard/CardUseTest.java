package cs3500.pawnsboard;

import org.junit.Test;

import cs3500.pawnsboard.model.CardUse;
import cs3500.pawnsboard.model.Player;

import static org.junit.Assert.*;

/**
 * Tests for the CardUse class.
 */
public class CardUseTest {

  /**
   * Creates a sample influence grid for testing.
   *
   * @return a 5x5 boolean array representing an influence grid
   */
  private boolean[][] createSampleGrid() {
    boolean[][] grid = new boolean[5][5];
    grid[1][2] = true;
    grid[2][1] = true;
    grid[2][3] = true;
    grid[3][2] = true;
    return grid;
  }

  @Test
  public void testConstructorValidInputs() {
    boolean[][] grid = createSampleGrid();
    CardUse card = new CardUse("Warrior", 2, 3, grid);

    assertEquals("Warrior", card.getName());
    assertEquals(2, card.getCost());
    assertEquals(3, card.getValueScore());
    assertNull(card.getPlayerOwnedBy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    boolean[][] grid = createSampleGrid();
    new CardUse(null, 2, 3, grid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyName() {
    boolean[][] grid = createSampleGrid();
    new CardUse("", 2, 3, grid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorCostBelowMin() {
    boolean[][] grid = createSampleGrid();
    new CardUse("Warrior", 0, 3, grid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorCostAboveMax() {
    boolean[][] grid = createSampleGrid();
    new CardUse("Warrior", 4, 3, grid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNonPositiveValueScore() {
    boolean[][] grid = createSampleGrid();
    new CardUse("Warrior", 2, 0, grid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullInfluenceGrid() {
    new CardUse("Warrior", 2, 3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWrongGridDimensions() {
    boolean[][] grid = new boolean[4][5]; // Wrong number of rows
    new CardUse("Warrior", 2, 3, grid);
  }

  @Test
  public void testGetInfluenceGridDefensiveCopy() {
    boolean[][] grid = createSampleGrid();
    CardUse card = new CardUse("Warrior", 2, 3, grid);

    boolean[][] returnedGrid = card.getInfluenceGrid();

    assertNotSame(grid, returnedGrid);

    grid[0][0] = true;

    assertFalse(card.getInfluenceGrid()[0][0]);
  }

  @Test
  public void testEquals() {
    boolean[][] grid1 = createSampleGrid();
    boolean[][] grid2 = createSampleGrid();

    CardUse card1 = new CardUse("Warrior", 2, 3, grid1);
    CardUse card2 = new CardUse("Warrior", 2, 3, grid2);

    assertEquals(card1, card2);
    assertEquals(card2, card1);

    assertEquals(card1, card1);

    assertNotEquals(card1, "Not a card");

    assertNotEquals(card1, null);
  }

  @Test
  public void testEqualsDifferentName() {
    boolean[][] grid = createSampleGrid();

    CardUse card1 = new CardUse("Warrior", 2, 3, grid);
    CardUse card2 = new CardUse("Dragon", 2, 3, grid);

    assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsDifferentCost() {
    boolean[][] grid = createSampleGrid();

    CardUse card1 = new CardUse("Warrior", 2, 3, grid);
    CardUse card2 = new CardUse("Warrior", 3, 3, grid);

    assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsDifferentValueScore() {
    boolean[][] grid = createSampleGrid();

    CardUse card1 = new CardUse("Warrior", 2, 3, grid);
    CardUse card2 = new CardUse("Warrior", 2, 4, grid);

    assertNotEquals(card1, card2);
  }

  @Test
  public void testEqualsDifferentInfluenceGrid() {
    boolean[][] grid1 = createSampleGrid();
    boolean[][] grid2 = createSampleGrid();
    grid2[0][0] = true; // Make grid2 different

    CardUse card1 = new CardUse("Warrior", 2, 3, grid1);
    CardUse card2 = new CardUse("Warrior", 2, 3, grid2);

    assertNotEquals(card1, card2);
  }

  @Test
  public void testHashCode() {
    boolean[][] grid1 = createSampleGrid();
    boolean[][] grid2 = createSampleGrid();

    CardUse card1 = new CardUse("Warrior", 2, 3, grid1);
    CardUse card2 = new CardUse("Warrior", 2, 3, grid2);

    // Equal objects should have equal hash codes
    assertEquals(card1.hashCode(), card2.hashCode());
  }

  @Test
  public void testToString() {
    boolean[][] grid = createSampleGrid();
    CardUse card = new CardUse("Warrior", 2, 3, grid);

    assertEquals("Warrior 2 3", card.toString());
  }
}