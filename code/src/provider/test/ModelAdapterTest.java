package provider.test;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import model.GameBoard;
import model.Player;
import model.SimplePlayer;
import model.card.Card;
import model.card.GameCard;
import model.card.Influence;
import model.card.SimpleInfluence;
import provider.model.ModelAdapter;
import provider.model.PlayerEnum;
import provider.model.ProviderCard;

import static org.junit.Assert.*;

public class ModelAdapterTest {

  private Player redPlayer;
  private Player bluePlayer;
  private GameBoard board;
  private ModelAdapter adapter;

  @Before
  public void setUp() {
    redPlayer = new SimplePlayer(3, true);
    bluePlayer = new SimplePlayer(3, false);

    Influence[][] influence = new Influence[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        influence[i][j] = new SimpleInfluence(false);
      }
    }

    Card card1 = new GameCard("TestCard1", influence, 1, 5);
    Card card2 = new GameCard("TestCard2", influence, 2, 3);
    List<Card> redDeck = Arrays.asList(card1);
    List<Card> blueDeck = Arrays.asList(card2);

    redPlayer.setDeck(redDeck);
    bluePlayer.setDeck(blueDeck);

    board = new GameBoard(3, 5);
    board.startGame(redPlayer, bluePlayer);

    adapter = new ModelAdapter(redPlayer, bluePlayer, board);
  }

  @Test
  public void testGameOverInitiallyFalse() {
    assertFalse(adapter.gameOver());
  }

  @Test
  public void testGetWidthAndHeight() {
    assertEquals(5, adapter.getWidth());
    assertEquals(3, adapter.getHeight());
  }

  @Test
  public void testGetTurn() {
    assertEquals(PlayerEnum.Red, adapter.getTurn());
  }

  @Test
  public void testGetHand() {
    List<ProviderCard> hand = adapter.getHand(PlayerEnum.Red);
    assertEquals(1, hand.size());
    assertEquals("TestCard1", hand.get(0).getName());
  }

  @Test
  public void testIsMoveLegalFalseForEmptyCell() {
    assertFalse(adapter.isMoveLegal(0, 0, 0));
  }
  @Test
  public void testGetRowScoreInitiallyZero() {
    assertEquals(0, adapter.getRowScore(0, PlayerEnum.Red));
    assertEquals(0, adapter.getRowScore(0, PlayerEnum.Blue));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerThrowsIfGameNotOver() {
    adapter.getWinner();
  }

  @Test
  public void testGetCellAtReturnsProviderCell() {
    assertNotNull(adapter.getCellAt(0, 0));
    assertNull(adapter.getCellAt(0, 0).getCard());
  }

  @Test
  public void testGetOwnerOfReturnsNoneInitially() {
    assertEquals(PlayerEnum.None, adapter.getOwnerOf(0, 0));
  }
}