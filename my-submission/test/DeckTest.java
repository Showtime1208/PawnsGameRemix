import static org.junit.Assert.*;

import cs3500.queensblood.model.Deck;
import cs3500.queensblood.model.GameCard;
import cs3500.queensblood.model.enumsModel.InfluenceType;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * Unit tests for verifying the behavior of the Deck loader.
 * Ensures valid deck files are parsed correctly and invalid
 * deck formats are rejected appropriately.
 */
public class DeckTest {

  private static final String CONFIG_PATH = "test_deck.config";

  /**
   * Sets up a reusable valid deck configuration file on disk
   * before each test. The file includes two distinct cards.
   */
  @Before
  public void prepareTestDeckFile() throws IOException {
    String mockDeckContent =
            "Guardian 2 5\n" +
                    "XXXXX\n" +
                    "XXIXX\n" +
                    "XICIX\n" +
                    "XXIXX\n" +
                    "XXXXX\n" +
                    "Scout 1 3\n" +
                    "XXIXX\n" +
                    "XXXXX\n" +
                    "XXCXX\n" +
                    "XXXXX\n" +
                    "XXIXX\n";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
      writer.write(mockDeckContent);
    }
  }

  /**
   * Validates that a properly formatted deck file loads successfully.
   * Checks the size, attributes, and influence grid of the first card.
   */
  @Test
  public void testLoadValidDeckFile() throws IOException {
    Deck deckLoader = new Deck(CONFIG_PATH);
    List<GameCard> cards = deckLoader.getDeck();

    assertEquals(2, cards.size());

    GameCard card = cards.get(0);
    assertEquals("Guardian", card.getName());
    assertEquals(2, card.getCost());
    assertEquals(5, card.getValueScore());

    InfluenceType[][] grid = card.getInfluences();
    assertEquals(InfluenceType.CENTER, grid[2][2]);
    assertEquals(InfluenceType.INFLUENCE, grid[2][1]);
  }

  /**
   * Verifies that loading a deck with no 'C' (center) character
   * throws an IllegalArgumentException as expected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeckMissingCenterCell() throws IOException {
    String badDeck =
            "MissingCenter 1 4\n" +
                    "XXXXX\n" +
                    "XXIXX\n" +
                    "XIIIX\n" +
                    "XXIXX\n" +
                    "XXXXX\n";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
      writer.write(badDeck);
    }

    new Deck(CONFIG_PATH); // Should throw
  }

  /**
   * Ensures that a deck with a malformed row (not 5 characters) is rejected.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeckWithMalformedRowLength() throws IOException {
    String badDeck =
            "RowIssue 2 2\n" +
                    "XXXXX\n" +
                    "XIXIX\n" +
                    "XICIX\n" +
                    "XXX\n" +       // <- Only 3 characters here
                    "XXXXX\n";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
      writer.write(badDeck);
    }

    new Deck(CONFIG_PATH); // Should throw
  }

  /**
   * Tests behavior when the deck's cost/value line contains non-numeric input.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeckWithNonNumericCostValue() throws IOException {
    String badDeck =
            "WeirdCard Z Q\n" +
                    "XXXXX\n" +
                    "XXIXX\n" +
                    "XICIX\n" +
                    "XXIXX\n" +
                    "XXXXX\n";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_PATH))) {
      writer.write(badDeck);
    }

    new Deck(CONFIG_PATH); // Should throw
  }
}
