package provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import model.card.Card;
import model.card.Influence;
import model.card.SimpleInfluence;
import org.junit.Before;
import org.junit.Test;
import provider.model.ModelAdapter;
import provider.model.PlayerEnum;
import provider.model.ProviderCard;
import strategy.MockBoard;
import strategy.MockCard;
import strategy.MockPlayer;

/**
 * Test class for the model adapter. Tests to ensure that the adapter correctly
 * delegates and receives info from the functional model.
 */
public class ModelAdapterTest {

  private StringBuilder log;
  private ModelAdapter adapter;

  @Before
  public void setUp() {
    log = new StringBuilder();

    // Create influence array
    Influence[][] influence = new Influence[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        influence[i][j] = new SimpleInfluence(false);
      }
    }

    // Create deck of cards
    List<Card> redDeck = new ArrayList<>();
    List<Card> blueDeck = new ArrayList<>();
    for (int i = 0; i < 15; i++) {  // Create enough cards for the game
      redDeck.add(new MockCard("RedCard" + i, 5, 1, influence));
      blueDeck.add(new MockCard("BlueCard" + i, 3, 2, influence));
    }

    // Create players with decks
    MockPlayer redPlayer = new MockPlayer(log, true, redDeck);
    MockPlayer bluePlayer = new MockPlayer(log, false, blueDeck);

    // Create board (3x5 board)
    MockBoard board = new MockBoard(log, 5, 3, false, redPlayer, bluePlayer);
    board.startGame(redPlayer, bluePlayer);

    adapter = new ModelAdapter(redPlayer, bluePlayer, board);
  }

  @Test
  public void testGameOverInitiallyFalse() {
    assertFalse(adapter.gameOver());
    assertTrue(log.toString().contains("isGameOver called"));
  }

  @Test
  public void testGetWidthAndHeight() {
    assertEquals(5, adapter.getWidth());
    assertEquals(3, adapter.getHeight());
    assertTrue(log.toString().contains("getWidth called"));
    assertTrue(log.toString().contains("getHeight called"));
  }

  @Test
  public void testGetTurn() {
    assertEquals(PlayerEnum.Red, adapter.getTurn());
    assertTrue(log.toString().contains("getTurn called"));
  }

  @Test
  public void testGetHand() {
    List<ProviderCard> hand = adapter.getHand(PlayerEnum.Red);
    assertNotNull(hand);
    assertEquals(5, hand.size());  // Initial hand size should be 5
    assertTrue(log.toString().contains("getHand called"));
  }

  @Test
  public void testGetRowScore() {
    int score = adapter.getRowScore(0, PlayerEnum.Red);
    assertEquals(0, score);  // Initially should be 0 as no cards placed
    assertTrue(log.toString().contains("getRowScore called"));
  }

  @Test
  public void testGetCellAt() {
    assertNotNull(adapter.getCellAt(0, 0));
    assertTrue(log.toString().contains("getCell called"));
  }

  @Test
  public void testGetOwnerOf() {
    assertEquals(PlayerEnum.Red, adapter.getOwnerOf(0, 0));
    assertEquals(PlayerEnum.Blue, adapter.getOwnerOf(0, 4));
    assertTrue(log.toString().contains("getCell called"));
  }

  @Test
  public void testIsMoveLegal() {
    assertTrue(adapter.isMoveLegal(0, 0, 0));
    assertFalse(adapter.isMoveLegal(0, 0, 2));
  }
}