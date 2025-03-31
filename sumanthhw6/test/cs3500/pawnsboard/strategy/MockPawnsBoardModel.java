package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Card;
import cs3500.pawnsboard.model.Pawn;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadOnlyPawnsBoardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A simple mock implementation of ReadOnlyPawnsBoardModel for testing strategies.
 * Records method calls in a transcript.
 */
public class MockPawnsBoardModel implements ReadOnlyPawnsBoardModel {
  private final List<String> transcript = new ArrayList<>();
  private final Map<String, Boolean> validMoves = new HashMap<>();
  private final Map<Integer, Integer> redRowScores = new HashMap<>();
  private final Map<Integer, Integer> blueRowScores = new HashMap<>();
  private final Map<String, Object> returnValues = new HashMap<>();

  /**
   * Creates a new mock model with default settings.
   */
  public MockPawnsBoardModel() {
    // Set up default row scores
    redRowScores.put(0, 3);
    redRowScores.put(1, 2);
    redRowScores.put(2, 5);

    blueRowScores.put(0, 5);
    blueRowScores.put(1, 4);
    blueRowScores.put(2, 3);

    // Set up some valid moves
    setValidMove(0, 2, true);
    setValidMove(1, 0, true);
  }

  /**
   * Gets the transcript of method calls.
   *
   * @return the list of method calls in order
   */
  public List<String> getTranscript() {
    return new ArrayList<>(transcript);
  }

  /**
   * Sets whether a move is valid at the given position.
   *
   * @param row the row
   * @param col the column
   * @param isValid whether the move is valid
   */
  public void setValidMove(int row, int col, boolean isValid) {
    validMoves.put(row + "," + col, isValid);
  }

  @Override
  public int getNumberOfRows() {
    transcript.add("getNumberOfRows()");
    return (int) returnValues.getOrDefault("numberOfRows", 3);
  }

  @Override
  public int getNumberOfColumns() {
    transcript.add("getNumberOfColumns()");
    return (int) returnValues.getOrDefault("numberOfColumns", 5);
  }

  public void setRowScores(Player player, Map<Integer, Integer> scores) {
    returnValues.put("rowScores_" + player, scores);
  }
  public void setNumberOfRows(int numRows) {
    returnValues.put("numberOfRows", numRows);
  }
  public void setNumberOfColumns(int numCols) {
    returnValues.put("numberOfColumns", numCols);
  }
  @Override
  public Pawn getPawnsAt(int row, int col) {
    transcript.add("getPawnsAt(" + row + ", " + col + ")");
    if (validMoves.getOrDefault(row + "," + col, false)) {
      return new Pawn(2, Player.RED);  // Default to 2 RED pawns for valid moves
    }
    return null;
  }

  @Override
  public Player getPlayerAt(int row, int col) {
    transcript.add("getPlayerAt(" + row + ", " + col + ")");
    return null;
  }



  @Override
  public Map<Integer, Integer> getRowScores(Player player) {
    transcript.add("getRowScores(" + player + ")");
    if (player == Player.RED) {
      return new HashMap<>(redRowScores);
    } else {
      return new HashMap<>(blueRowScores);
    }
  }

  @Override
  public List getHand(Player player) {
    transcript.add("getHand(" + player + ")");
    List<SimpleCard> hand = new ArrayList<>();
    hand.add(new SimpleCard("Card1", 1, 3));
    hand.add(new SimpleCard("Card2", 2, 4));
    return hand;
  }

  @Override
  public Player getCurrentTurn() {
    transcript.add("getCurrentTurn()");
    return Player.RED;
  }

  @Override
  public boolean isGameOver() {
    transcript.add("isGameOver()");
    return false;
  }

  @Override
  public Player[][] getCardBoard() {
    transcript.add("getCardBoard()");
    return new Player[5][3];
  }

  @Override
  public Pawn[][] getPawnsBoard() {
    transcript.add("getPawnsBoard()");
    return new Pawn[5][3];
  }

  @Override
  public Optional<Player> getWinner() {
    transcript.add("getWinner()");
    return Optional.empty();
  }

  /**
   * A simple implementation of Card for testing.
   */
  private static class SimpleCard implements Card {
    private final String name;
    private final int cost;
    private final int valueScore;

    public SimpleCard(String name, int cost, int valueScore) {
      this.name = name;
      this.cost = cost;
      this.valueScore = valueScore;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public int getCost() {
      return cost;
    }

    @Override
    public int getValueScore() {
      return valueScore;
    }

    @Override
    public void setPlayerOwnedBy(Player player) {
      // Do nothing for mock
    }

    @Override
    public boolean[][] getInfluenceGrid() {
      return new boolean[5][5];
    }

    @Override
    public Player getPlayerOwnedBy() {
      return Player.RED;
    }
  }
}