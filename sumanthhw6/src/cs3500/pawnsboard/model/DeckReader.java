package cs3500.pawnsboard.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for reading deck configuration files.
 * This class is responsible for parsing deck files and creating Card objects.
 */
public class DeckReader {
  /**
   * Reads a deck from a configuration file.
   *
   * @param filePath the path to the configuration file
   * @return a list of cards created from the file
   * @throws IllegalArgumentException if the file is invalid or cannot be read
   */
  public static List<CardUse> readDeck(String filePath) {
    try {
      File file = new File(filePath);
      Scanner scanner = new Scanner(file);
      List<CardUse> cards = new ArrayList<>();

      while (scanner.hasNextLine()) {
        // Read card header (name, cost, value)
        String headerLine = scanner.nextLine();
        String[] headerParts = headerLine.split(" ");

        if (headerParts.length != 3) {
          throw new IllegalArgumentException("Invalid card header: " + headerLine);
        }

        String name = headerParts[0];
        int cost;
        int valueScore;

        try {
          cost = Integer.parseInt(headerParts[1]);
          valueScore = Integer.parseInt(headerParts[2]);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid cost or value: " + headerLine);
        }

        // Validate cost and value
        if (cost < 1 || cost > 3) {
          throw new IllegalArgumentException("Card cost must be between 1 and 3: " + name);
        }
        if (valueScore <= 0) {
          throw new IllegalArgumentException("Card value must be positive: " + name);
        }

        // Read influence grid
        boolean[][] influenceGrid = new boolean[5][5];
        boolean foundCenter = false;

        for (int r = 0; r < 5; r++) {
          if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("Incomplete influence grid for card: " + name);
          }

          String gridLine = scanner.nextLine();
          if (gridLine.length() != 5) {
            throw new IllegalArgumentException("Invalid influence grid line: " + gridLine);
          }

          for (int c = 0; c < 5; c++) {
            char ch = gridLine.charAt(c);

            switch (ch) {
              case 'X':
                influenceGrid[r][c] = false;
                break;
              case 'I':
                influenceGrid[r][c] = true;
                break;
              case 'C':
                if (r != 2 || c != 2) {
                  throw new IllegalArgumentException("Center marker 'C' must be at position (2,2)");
                }
                influenceGrid[r][c] = false; // Center is not influenced
                foundCenter = true;
                break;
              default:
                throw new IllegalArgumentException("Invalid character in influence grid: " + ch);
            }
          }
        }

        if (!foundCenter) {
          throw new IllegalArgumentException("Missing center marker 'C' for card: " + name);
        }

        cards.add(new CardUse(name, cost, valueScore, influenceGrid));
      }

      scanner.close();

      if (cards.isEmpty()) {
        throw new IllegalArgumentException("No cards found in file");
      }

      return cards;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + filePath, e);
    }
  }
}