package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests for the FillFirstStrategy class.
 */
public class FillFirstStrategyTest {

  private MockPawnsBoardModel mockModel;
  private FillFirstStrategy strategy;

  @Before
  public void setUp() {
    mockModel = new MockPawnsBoardModel();
    strategy = new FillFirstStrategy();
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

    // Set up row scores (not used by FillFirst but included for consistency)
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
    FillFirstStrategy strategy = new FillFirstStrategy();
    strategy.findBestMove(mockModel, Player.RED);

    // Save the transcript
    File docsDir = new File("docs");
    if (!docsDir.exists()) {
      docsDir.mkdir();
    }

    File file = new File(docsDir, "strategy-transcript-first.txt");
    FileWriter writer = new FileWriter(file);
    for (String line : mockModel.getTranscript()) {
      writer.write(line + "\n");
    }
    writer.close();

    System.out.println("Transcript file created at: " + file.getAbsolutePath());

    // Also create a copy in the root directory as required by the assignment
    File rootFile = new File("strategy-transcript-first.txt");
    writer = new FileWriter(rootFile);
    for (String line : mockModel.getTranscript()) {
      writer.write(line + "\n");
    }
    writer.close();

    System.out.println("Transcript file also created at: " + rootFile.getAbsolutePath());
  }
}