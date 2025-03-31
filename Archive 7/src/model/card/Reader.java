package model.card;

import java.util.List;

/**
 * Represents the reader interface.
 */
public interface Reader {

  /**
  * Reads a deck configuration file and returns a list of GameCard objects.
  *
  * @param filePath the path to the deck configuration file.
  * @return a List of GameCard objects parsed from the file.
  */
  public List<Card> readDeck(String filePath);

  /**
  * Reads a deck configuration file and returns a list of GameCard objects
  * with reversed influence grids.
  *
  * @param filePath the path to the deck configuration file.
  * @return a List of GameCard objects parsed from the file with reversed influence grids.
  */
  public List<Card> readDeckReverse(String filePath);

}
