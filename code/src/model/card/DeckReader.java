package model.card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a class to read the deck from the deck.config file.
 */
public class DeckReader implements Reader {

  /**
   * Reads a deck configuration file and returns a list of GameCard objects.
   *
   * @param filePath the path to the deck configuration file.
   * @return a List of GameCard objects parsed from the file.
   */
  public List<Card> readDeck(String filePath) {
    return readDeckHelper(filePath, false);
  }

  /**
   * Reads a deck configuration file and returns a list of GameCard objects with reversed influence
   * grids.
   *
   * @param filePath the path to the deck configuration file.
   * @return a List of GameCard objects parsed from the file with reversed influence grids.
   */
  public List<Card> readDeckReverse(String filePath) {
    return readDeckHelper(filePath, true);
  }

  /**
   * Helper method to read a deck file and optionally reverse the influence grid.
   *
   * @param filePath    the path to the deck configuration file.
   * @param reverseGrid boolean to optionally reverse the influence arrays.
   * @return a List of GameCard objects parsed from the file.
   */
  private List<Card> readDeckHelper(String filePath, boolean reverseGrid) {
    List<Card> deck = new ArrayList<>();
    File configFile = new File(filePath);

    try (Scanner scanner = new Scanner(configFile)) {
      while (scanner.hasNextLine()) {
        String header = scanner.nextLine().trim();
        if (header.isEmpty()) {
          continue;
        }

        // Parse the header
        String[] headerParts = parseCardHeader(header);
        String cardName = headerParts[0];
        int cost = Integer.parseInt(headerParts[1]);
        int value = Integer.parseInt(headerParts[2]);

        // Read and validate the grid
        char[][] charGrid = readAndValidateGrid(scanner, cardName);

        // Reverse grid if needed
        if (reverseGrid) {
          charGrid = reverseInfluenceGrid(charGrid);
        }

        // Convert and create the GameCard
        deck.add(convertToGameCard(cardName, charGrid, cost, value));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return deck;
  }

  /**
   * Parses the header of a card entry in the deck file.
   *
   * @param header The header string containing the card name, cost, and value.
   * @return An array containing [cardName, cost, value].
   * @throws IllegalArgumentException If the header does not contain exactly three parts.
   */
  private String[] parseCardHeader(String header) {
    String[] parts = header.split("\\s+");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid card header: " + header);
    }
    return parts;
  }

  /**
   * Reads and validates a 5x5 influence grid from the scanner.
   *
   * @param scanner  The Scanner object used to read the file.
   * @param cardName The name of the card for which the grid is being read.
   * @return A 5x5 character array representing the influence grid.
   * @throws IllegalArgumentException If the grid is incomplete or contains invalid characters.
   */
  private char[][] readAndValidateGrid(Scanner scanner, String cardName) {
    char[][] charGrid = new char[5][5];

    for (int row = 0; row < 5; row++) {
      if (!scanner.hasNextLine()) {
        throw new IllegalArgumentException("Incomplete grid for card: " + cardName);
      }
      String line = scanner.nextLine();
      if (line.length() != 5) {
        throw new IllegalArgumentException(
            "Grid row must have exactly 5 characters for card: " + cardName);
      }

      for (int col = 0; col < 5; col++) {
        charGrid[row][col] = line.charAt(col);
      }
    }

    // Validate the grid
    validateGrid(charGrid, cardName);
    return charGrid;
  }

  /**
   * Validates the influence grid ensuring it meets format requirements.
   *
   * @param charGrid The 5x5 influence grid to validate.
   * @param cardName The name of the card being validated.
   * @throws IllegalArgumentException If the grid contains invalid characters or improper placement
   *                                  of 'C'.
   */
  private void validateGrid(char[][] charGrid, String cardName) {
    if (charGrid[2][2] != 'C') {
      throw new IllegalArgumentException("Center cell must be C for card: " + cardName);
    }
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (row == 2 && col == 2) {
          continue;
        }
        char ch = charGrid[row][col];
        if (ch == 'C') {
          throw new IllegalArgumentException("Invalid 'C' found outside center for card");
        }
        if (ch != 'X' && ch != 'I' && ch != 'U' && ch != 'D') {
          throw new IllegalArgumentException("Invalid character in grid for card");
        }
      }
    }
  }

  /**
   * Converts a validated character grid into a GameCard object.
   *
   * @param cardName The name of the card.
   * @param charGrid The 5x5 character array representing the influence grid.
   * @param cost     The cost of the card.
   * @param value    The value of the card.
   * @return A new GameCard object.
   */
  private GameCard convertToGameCard(String cardName, char[][] charGrid, int cost, int value) {
    SimpleInfluence[][] influenceArray = convertToInfluenceArray(charGrid);
    return new GameCard(cardName, influenceArray, cost, value);
  }


  /**
   * Reverses the influence grid by flipping it horizontally and vertically.
   *
   * @param grid the original character grid
   * @return the reversed grid
   */
  private char[][] reverseInfluenceGrid(char[][] grid) {
    char[][] reversed = new char[5][5];

    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        reversed[4 - row][4 - col] = grid[row][col];
      }
    }

    return reversed;
  }

  /**
   * Converts a character grid to a SimpleInfluence array.
   *
   * @param charGrid the character grid
   * @return the SimpleInfluence array
   */
  private SimpleInfluence[][] convertToInfluenceArray(char[][] charGrid) {
    SimpleInfluence[][] influenceArray = new SimpleInfluence[5][5];

    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        char ch = charGrid[row][col];
        switch (ch) {
          case 'C':
          case 'I':
            influenceArray[row][col] = new SimpleInfluence(InfluenceKind.CLAIM);
          break;
          case 'U': influenceArray[row][col] = new SimpleInfluence(InfluenceKind.UPGRADE);
          break;
          case 'D': influenceArray[row][col] = new SimpleInfluence(InfluenceKind.DEVALUE);
          break;
          default: influenceArray[row][col] = new SimpleInfluence(InfluenceKind.NONE);
          break;
        }

      }
    }
    return influenceArray;
  }
}