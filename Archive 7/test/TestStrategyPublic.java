import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.Player;
import model.card.DeckReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import strategy.BoardControlStrategy;
import strategy.FillFirstStrategy;
import strategy.MaximizeRowScoreStrategy;
import strategy.MockBoard;
import strategy.MockPlayer;
import strategy.Move;
import strategy.Strategy;
import view.SimpleTextualView;
import view.TextualView;

/**
 * Represents all the public tests for the stratagie implemetatons.
 */
public class TestStrategyPublic {
  public MockBoard board;
  public Strategy fillFirstStrategy;
  public Strategy maximizeRowScoreStrategy;
  public Strategy boardControllStrategy;
  public Player player1;
  public Player player2;
  public StringBuilder log = new StringBuilder();
  public File file = new File("test" + File.separator + "strategy" + File.separator
          + "docs" + File.separator + "strategy-transcript-first.txt");
  public File file2 = new File("test" + File.separator + "strategy" + File.separator
          + "docs" + File.separator + "strategy-transcript-score.txt");


  @Before
  public void setUp() {
    DeckReader reader = new DeckReader();
    String path = "docs" + File.separator + "deck.config";
    this.player1 = new MockPlayer(log, true, reader.readDeck(path));
    this.player2 = new MockPlayer(log, false, reader.readDeckReverse(path));
    this.board = new MockBoard(log, 5, 3, false, player1, player2);
    this.fillFirstStrategy = new FillFirstStrategy();
    this.maximizeRowScoreStrategy = new MaximizeRowScoreStrategy();
    this.boardControllStrategy = new BoardControlStrategy();
  }

  @Test
  public void testFillFirstAutoPlacesToTopLeft() {
    Assert.assertEquals(new Move(0, 0, false, 0),
        fillFirstStrategy.getMove(board, player1));
  }

  @Test
  public void testFillFirstAutoPlacesToTopLeftOnSecondMove() {
    board.placeCard(player1, 0, 0, 0);
    Assert.assertEquals(new Move(0, 4, false, 0), fillFirstStrategy.getMove(board, player2));
  }

  @Test
  public void testFillFirstAutoPlacesInAColIfNoRowOpen() {
    board.placeCard(player1, 0, 0, 0);
    Assert.assertEquals(new Move(1, 0, false, 0),  fillFirstStrategy.getMove(board, player1));
  }

  @Test
  public void testFillFirstAutoPlacesToLeftByDefault() {
    board.placeCard(player1, 0, 0, 0);
    board.getCell(0, 1).addPawn(player1);
    TextualView view = new SimpleTextualView(board);
    Assert.assertEquals(new Move(0, 1, false,0), fillFirstStrategy.getMove(board, player1));
  }

  @Test
  public void testFillFirstPassesIfNoMoves() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player1, 0, 1, 0);
    board.placeCard(player1, 0, 2, 0);
    //Obviously the cards would normally place the influence down, allowing the model to place more
    // cards. However, for the purpose of this test it just illustrates when the player would pass.
    Assert.assertEquals(new Move(-1, -1, true, -1), fillFirstStrategy.getMove(board, player1));
  }

  @Test
  public void testFillFirstPassesForPlayer2IfNoMoves() {
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player2, 0, 1, 4);
    board.placeCard(player2, 0, 2, 4);
    Assert.assertEquals(new Move(-1, -1, true, -1), fillFirstStrategy.getMove(board, player2));
  }

  @Test
  public void testFillFirstPlacesDirectlyToLeftOnDefault() {
    board.placeCard(player2, 0, 0, 4);
    Assert.assertEquals(new Move(1, 4, false, 0), fillFirstStrategy.getMove(board, player2));
  }

  @Test
  public void testFillFirstPlacesDownOnP2IfSpaceIsFull() {
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player2, 0, 0, 3);
    Assert.assertEquals(new Move(1, 4, false, 0), fillFirstStrategy.getMove(board,player2));
    try (FileWriter fw = new FileWriter(file, true)) {
      fw.write(log.toString());
      log.setLength(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void testGetBestRowScorePlaysFirstToTop() {
    Assert.assertEquals(new Move(0,0, false, 0), maximizeRowScoreStrategy.getMove(board, player1));
  }

  @Test
  public void testGetBestRowScorePlaysToSecondRowOnFirstTurns() {
    //not enough pawns to play something winning, so plays to second row.
    board.placeCard(player1, 0, 0, 0);
    Assert.assertEquals(1, board.getRowScore(player1, 0));
    Assert.assertEquals(new Move(1, 4, false, 0), maximizeRowScoreStrategy.getMove(board, player2));
  }

  @Test
  public void testBestRowScorePlaysFirstCardToTopToBreakTie() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player1, 2, 1, 0);
    board.placeCard(player2, 2, 1, 4);
    board.placeCard(player1, 0, 2, 0);
    board.placeCard(player2, 0, 2, 4);
    board.getCell(0, 1).addPawn(player1);
    board.getCell(0, 1).addPawn(player1);
    board.getCell(1, 1).addPawn(player1);
    board.getCell(1, 1).addPawn(player1);
    Assert.assertEquals(new Move(0, 1, false, 0), maximizeRowScoreStrategy.getMove(board, player1));
  }

  @Test
  public void testBestRowScorePlaysToLosingRowIfTwoAreWinning() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player1, 0, 1, 0);
    board.placeCard(player2, 2, 2, 4);
    board.getCell(2, 0).addPawn(player1);
    Assert.assertEquals(new Move(2, 0, false, 4), maximizeRowScoreStrategy.getMove(board, player1));
  }

  @Test
  public void testBestRowScorePlaysToTopRowIfMultipleAreLosing() {
    board.placeCard(player2, 2, 0, 4);
    board.placeCard(player2, 2, 2, 4);
    board.getCell(0, 1).addPawn(player1);
    board.getCell(0, 1).addPawn(player1);
    board.getCell(1, 0).addPawn(player1);
    Assert.assertEquals(new Move(0, 1, false, 4), maximizeRowScoreStrategy.getMove(board, player1));
  }

  @Test
  public void testBestRowScorePassesIfNoMoves() {
    board.placeCard(player1, 4, 0, 0);
    board.placeCard(player1, 4, 1, 0);
    board.placeCard(player1, 4, 2, 0);
    Assert.assertEquals(new Move(-1, -1, true, -1),
            maximizeRowScoreStrategy.getMove(board, player2));
  }

  @Test
  public void testBestRowScorePassesIfWinningAll() {
    board.placeCard(player1, 4, 0, 0);
    board.placeCard(player1, 4, 1, 0);
    board.placeCard(player1, 4, 2, 0);
    Assert.assertEquals(new Move(-1, -1, true, -1),
            maximizeRowScoreStrategy.getMove(board, player1));
    try (FileWriter fw = new FileWriter(file2, true)) {
      fw.write(log.toString());
      log.setLength(0);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



}
