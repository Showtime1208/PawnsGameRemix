package cs3500.queensblood.model;

import cs3500.queensblood.model.enumsModel.InfluenceType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class responsible for loading and parsing deck configuration files.
 * Each card is defined by a name, numeric stats, and a 5x5 influence grid.
 * This class reads and validates those entries to construct a list of usable cards.
 */
public class Deck {

  // Internal list of cards loaded from the config file
  private final List<GameCard> deck = new ArrayList<>();

  /**
   * Constructs a deck loader by reading and parsing a configuration file.
   *
   * @param filePath path to the deck file
   * @throws IOException if the file cannot be read
   */
  public Deck(String filePath) throws IOException {
    loadDeck(filePath);
  }

  /**
   * Reads the specified file and constructs card objects from it.
   * Ensures that each card includes a valid 5x5 influence grid with a single center tile.
   *
   * @param filePath path to the configuration file
   * @throws IOException if the file is inaccessible or contains invalid format
   */
  private void loadDeck(String filePath) throws IOException {
    File file = new File(filePath);
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
          continue;
        }

        String[] cardInfo = line.split("\\s+");
        if (cardInfo.length != 3) {
          throw new IllegalArgumentException("Invalid card format: " + Arrays.toString(cardInfo));
        }

        String name = cardInfo[0].trim();
        int cost;
        int value;

        try {
          cost = Integer.parseInt(cardInfo[1].trim());
          value = Integer.parseInt(cardInfo[2].trim());
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(
                  "Invalid cost or value: " + Arrays.toString(cardInfo), e);
        }

        InfluenceType[][] influenceGrid = new InfluenceType[5][5];
        boolean hasCenter = false;

        for (int i = 0; i < 5; i++) {
          if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("Incomplete card grid in " + name);
          }

          String row = scanner.nextLine().trim();
          if (row.length() != 5) {
            throw new IllegalArgumentException(
                    "Invalid grid row length in " + name + " at line " + (i + 1));
          }

          for (int j = 0; j < 5; j++) {
            InfluenceType type = charToInfluenceType(row.charAt(j));

            if (type == InfluenceType.CENTER) {
              if (i != 2 || j != 2) {
                throw new IllegalArgumentException(
                        "Invalid 'C' position in " + name + " at (" + i + "," + j + ")");
              }
              if (hasCenter) {
                throw new IllegalArgumentException("Multiple 'C' found in " + name);
              }
              hasCenter = true;
            }

            influenceGrid[i][j] = type;
          }
        }

        if (!hasCenter) {
          throw new IllegalArgumentException("Missing 'C' in " + name);
        }

        deck.add(new PawnsGameCard(name, cost, value, influenceGrid));
      }
    }
  }

  /**
   * Maps a character symbol to its corresponding InfluenceType enum value.
   *
   * @param c character to interpret (e.g. 'X', 'I', 'C')
   * @return the appropriate InfluenceType
   * @throws IllegalArgumentException if the character is unrecognized
   */
  private InfluenceType charToInfluenceType(char c) {
    switch (c) {
      case 'X':
        return InfluenceType.NONE;
      case 'I':
        return InfluenceType.INFLUENCE;
      case 'C':
        return InfluenceType.CENTER;
      default:
        throw new IllegalArgumentException("Invalid character in influence grid: " + c);
    }
  }

  /**
   * Returns the list of parsed GameCards loaded from the file.
   *
   * @return list of deck cards
   */
  public List<GameCard> getDeck() {
    return deck;
  }
}
