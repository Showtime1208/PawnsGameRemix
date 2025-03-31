package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Pawn;
import cs3500.pawnsboard.model.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for the MaximizeRowScoreStrategy class.
 */
public class MaximizeRowScoreStrategyTest {

  private MockPawnsBoardModel mockModel;
  private MaximizeRowScoreStrategy strategy;

  @Before
  public void setUp() {
    mockModel = new MockPawnsBoardModel();
    strategy = new MaximizeRowScoreStrategy();
  }

  @Test
  public void testFindBestMove_RowWithLowerScore() {
    // Set up row scores where RED has a lower score in row 0
    Map<Integer, Integer> redScores = new HashMap<>();
    redScores.put(0, 3);  // RED has score 3 in row 0
    redScores.put(1, 5);  // RED has score 5 in row 1
    redScores.put(2, 7);  // RED has score 7 in row 2

    Map<Integer, Integer> blueScores = new HashMap<>();
    blueScores.put(0, 5);  // BLUE has score 5 in row 0
    blueScores.put(1, 4);  // BLUE has score 4 in row 1
    blueScores.put(2, 6);  // BLUE has score 6 in row 2

    mockModel.setRowScores(Player.RED, redScores);
    mockModel.setRowScores(Player.BLUE, blueScores);

    // Make position (0,2) valid for a move
    mockModel.setValidMove(0, 2, true);

    // Run the strategy
    PawnsBoardStrategy.Move move = strategy.findBestMove(mockModel, Player.RED);

    // The strategy should choose row 0, column 2 because:
    // 1. Row 0 is the first row where RED's score (3) is less than BLUE's (5)
    // 2. Playing a card with value 3 would make RED's score 6, which is > BLUE's 5
    assertEquals(0, move.getCardIndex());  // First card in hand (value 3)
    assertEquals(0, move.getRow());        // Row 0
    assertEquals(2, move.getCol());        // Column 2

    // Check that the transcript shows the strategy checked rows in order
    java.util.List<String> transcript = mockModel.getTranscript();
    // Verify it checked row scores first
    assertEquals("getRowScores(RED)", transcript.get(0));
    assertEquals("getRowScores(BLUE)", transcript.get(1));
  }

  @Test
  public void testFindBestMove_NoValidMoves() {
    // Set up row scores where RED already has higher scores in all rows
    Map<Integer, Integer> redScores = new HashMap<>();
    redScores.put(0, 6);  // RED has score 6 in row 0
    redScores.put(1, 5);  // RED has score 5 in row 1
    redScores.put(2, 7);  // RED has score 7 in row 2

    Map<Integer, Integer> blueScores = new HashMap<>();
    blueScores.put(0, 5);  // BLUE has score 5 in row 0
    blueScores.put(1, 4);  // BLUE has score 4 in row 1
    blueScores.put(2, 6);  // BLUE has score 6 in row 2

    mockModel.setRowScores(Player.RED, redScores);
    mockModel.setRowScores(Player.BLUE, blueScores);

    // Explicitly set all positions as invalid
    for (int row = 0; row < mockModel.getNumberOfRows(); row++) {
      for (int col = 0; col < mockModel.getNumberOfColumns(); col++) {
        mockModel.setValidMove(row, col, false);
      }
    }

    // Run the strategy
    PawnsBoardStrategy.Move move = strategy.findBestMove(mockModel, Player.RED);

    // The strategy should return null because there are no valid moves
    assertNull(move);
  }
  @Test
  public void generateTranscriptFile() throws IOException {
    // Create a fresh mock model
    MockPawnsBoardModel mockModel = new MockPawnsBoardModel();

    // Set up a standard 3x5 board
    mockModel.setNumberOfRows(3);
    mockModel.setNumberOfColumns(5);

    // Set up some valid moves
    mockModel.setValidMove(0, 2, true);
    mockModel.setValidMove(1, 3, true);

    // Set up row scores
    Map<Integer, Integer> redScores = new HashMap<>();
    redScores.put(0, 3);
    redScores.put(1, 2);
    redScores.put(2, 5);

    Map<Integer, Integer> blueScores = new HashMap<>();
    blueScores.put(0, 5);
    blueScores.put(1, 4);
    blueScores.put(2, 3);

    mockModel.setRowScores(Player.RED, redScores);
    mockModel.setRowScores(Player.BLUE, blueScores);

    // Run the strategy
    MaximizeRowScoreStrategy strategy = new MaximizeRowScoreStrategy();
    strategy.findBestMove(mockModel, Player.RED);

    // Save the transcript
    java.util.List<String> transcript = mockModel.getTranscript();
    java.io.FileWriter writer = new java.io.FileWriter("strategy-transcript-score.txt");
    for (String line : transcript) {
      writer.write(line + "\n");
    }
    writer.close();
  }
  @Test
  public void testFindBestMove_ChecksRowsInOrder() {
    // Set up row scores where RED has lower scores in multiple rows
    Map<Integer, Integer> redScores = new HashMap<>();
    redScores.put(0, 3);  // RED has score 3 in row 0
    redScores.put(1, 2);  // RED has score 2 in row 1
    redScores.put(2, 4);  // RED has score 4 in row 2

    Map<Integer, Integer> blueScores = new HashMap<>();
    blueScores.put(0, 5);  // BLUE has score 5 in row 0
    blueScores.put(1, 4);  // BLUE has score 4 in row 1
    blueScores.put(2, 6);  // BLUE has score 6 in row 2

    mockModel.setRowScores(Player.RED, redScores);
    mockModel.setRowScores(Player.BLUE, blueScores);

    // Make positions in different rows valid
    mockModel.setValidMove(0, 2, true);  // Row 0, Col 2
    mockModel.setValidMove(1, 3, true);  // Row 1, Col 3

    // Run the strategy
    PawnsBoardStrategy.Move move = strategy.findBestMove(mockModel, Player.RED);

    // The strategy should choose row 0, column 2 because it checks rows from top to bottom
    assertEquals(0, move.getRow());
    assertEquals(2, move.getCol());

    // Check the transcript to verify it checked rows in order
    java.util.List<String> transcript = mockModel.getTranscript();

    // Extract all getPawnsAt calls to see the order of checking
    java.util.List<String> pawnsChecks = new java.util.ArrayList<>();
    for (String call : transcript) {
      if (call.startsWith("getPawnsAt")) {
        pawnsChecks.add(call);
      }
    }

    // Verify it checked row 0 before row 1
    boolean foundRow0Before1 = false;
    for (String check : pawnsChecks) {
      if (check.contains("getPawnsAt(0,")) {
        foundRow0Before1 = true;
        break;
      }
      if (check.contains("getPawnsAt(1,")) {
        foundRow0Before1 = false;
        break;
      }
    }

    assertTrue("Strategy should check row 0 before row 1", foundRow0Before1);
  }
}