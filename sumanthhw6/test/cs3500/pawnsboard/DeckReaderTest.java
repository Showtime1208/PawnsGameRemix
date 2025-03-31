package cs3500.pawnsboard;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.CardUse;
import cs3500.pawnsboard.model.DeckReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the DeckReader utility for reading config files.
 */
public class DeckReaderTest {
  private File tempFile;

  @Before
  public void setUp() throws IOException {
    // Create a temporary file for testing
    tempFile = File.createTempFile("test-deck", ".config");
    tempFile.deleteOnExit(); // Simple cleanup approach

    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write("Warrior 3 1\n");
      writer.write("XXXXX\n");
      writer.write("XXIXX\n");
      writer.write("XICIX\n");
      writer.write("XXIXX\n");
      writer.write("XXXXX\n");
      writer.write("Dragon 1 1\n");
      writer.write("XXXXX\n");
      writer.write("XIIII\n");
      writer.write("XICXX\n");
      writer.write("XXXXX\n");
      writer.write("XXXXX\n");
    }
  }

  @Test
  public void testReadDeck() {
    // Read the deck from the temporary file
    List<CardUse> cards = DeckReader.readDeck(tempFile.getAbsolutePath());

    assertFalse("Deck should not be empty", cards.isEmpty());
    assertEquals("Deck should have 2 cards", 2, cards.size());

    Card firstCard = cards.get(0);
    assertEquals("Warrior", firstCard.getName());
    assertEquals(3, firstCard.getCost());
    assertEquals(1, firstCard.getValueScore());

    Card secondCard = cards.get(1);
    assertEquals("Dragon", secondCard.getName());
    assertEquals(1, secondCard.getCost());
    assertEquals(1, secondCard.getValueScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadDeckWithInvalidFile() {
    DeckReader.readDeck("nonexistent-file.txt");
  }
}