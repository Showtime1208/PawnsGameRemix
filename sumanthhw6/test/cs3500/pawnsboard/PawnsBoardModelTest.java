package cs3500.pawnsboard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cs3500.pawnsboard.model.CardUse;
import cs3500.pawnsboard.model.DeckReader;
import cs3500.pawnsboard.model.PawnsBoardBuilder;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Player;


/**
 * Tests for the Model Interface of the Game.
 * There are no package private methods in the Model
 * to test in another class.
 */
public class PawnsBoardModelTest {

  PawnsBoardModel gameModel;

  @Before
  public void setUp() {
    List<CardUse> deck = DeckReader.readDeck("docs/deck.config");

    gameModel = new PawnsBoardBuilder().setBlueDeck(deck).
            setRedDeck(deck).setNumCols(5).setNumRows(3).
            build();
    gameModel.startGame(Player.RED, 2);
  }


  @Test
  public void testCurrentTurnAndPassingBehaviors() {
    Assert.assertEquals(gameModel.getCurrentTurn(), Player.RED);
    gameModel.passCurrentTurn();
    Assert.assertFalse(gameModel.isGameOver());
    Assert.assertEquals(gameModel.getCurrentTurn(), Player.BLUE);
    gameModel.passCurrentTurn();
    Assert.assertEquals(gameModel.getCurrentTurn(), Player.RED);
    Assert.assertTrue(gameModel.isGameOver());
  }

  @Test
  public void testGetPawnsAt() {
    Assert.assertEquals(gameModel.getPawnsAt(0, 0), gameModel.getPawnsAt(1, 0));
  }

  @Test
  public void getPlayerAt() {
    gameModel.placeCardInPosition(1, 0, 0);
    Assert.assertEquals(gameModel.getPlayerAt(0, 0), Player.RED);
    Assert.assertNull(gameModel.getPlayerAt(1, 0));
    Assert.assertThrows(IndexOutOfBoundsException.class, () ->
            gameModel.getPlayerAt(0, -1));
    Assert.assertThrows(IndexOutOfBoundsException.class, () ->
            gameModel.getPlayerAt(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameModel.placeCardInPosition(0, -1, 1));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            gameModel.placeCardInPosition(0, 0, 5));
    Assert.assertEquals(Arrays.deepToString(gameModel.getCardBoard()),
            "[[RED, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null]]");
  }

  @Test
  public void getRowScores() {
    Assert.assertTrue(gameModel.getRowScores(Player.RED).isEmpty());
    Assert.assertEquals(Arrays.deepToString(gameModel.getCardBoard()),
            "[[null, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null], "
                    + "[null, null, null]]");
  }

  @Test
  public void getHand() {
    Assert.assertEquals(gameModel.getHand(Player.RED).toString(), "[Warrior 3 1, Dragon 1 1]");
  }

}