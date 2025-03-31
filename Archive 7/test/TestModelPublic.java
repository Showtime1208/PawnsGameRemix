import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Cell;
import model.GameBoard;
import model.GameCell;
import model.Player;
import model.SimplePlayer;
import model.card.Card;
import model.card.DeckReader;
import model.card.GameCard;
import model.card.Influence;
import model.card.Pawn;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import view.SimpleTextualView;
import view.TextualView;

import static org.junit.Assert.assertEquals;

/**
 * Represents all the tests for the public methods for the model.
 */
public class TestModelPublic {

  private Board board;
  private TextualView view;
  private Player player1;
  private Player player2;
  private List<Card> cardList;

  @Before
  public void setUp() {
    DeckReader reader = new DeckReader();
    String path = "docs" + File.separator + "deck.config";
    this.cardList = reader.readDeck(path);
    this.player1 = new SimplePlayer(3, true);
    player1.setDeck(cardList);
    this.player2 = new SimplePlayer(3, false);
    player2.setDeck(reader.readDeckReverse(path));
    this.board = new GameBoard(3, 5);
    this.view = new SimpleTextualView(board);
    board.startGame(player1, player2);
  }

  /*
  Board Tests
   */
  @Test
  public void testValidBoardConstructor() {
    Board board = new GameBoard(5, 3);
    assertEquals(5, board.getHeight());
    assertEquals(3, board.getWidth());
  }

  @Test
  public void testInvalidBoardConstructorEvenRows() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new GameBoard(3, 4); } );
  }

  @Test
  public void testInvalidBoardConstructorZero() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new GameBoard(0, 3); } );
  }

  @Test
  public void testInvalidBoardConstructorColSizeOne() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      new GameBoard(1, 1); } );
  }

  @Test
  public void testInvalidPlaceCardGameNotStart() {
    Board board = new  GameBoard(3, 5);
    Player player = new SimplePlayer(5, true);
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeCard(player, 0, 0, 0); } );
  }

  @Test
  public void testInvalidGameOverGameNotStart() {
    Board board = new  GameBoard(3, 5);
    Assert.assertThrows(IllegalStateException.class, board::isGameOver);
  }

  @Test
  public void testInvalidPassTurnGameNotStart() {
    Board board = new  GameBoard(3, 5);
    Player player = new SimplePlayer(5, true);
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.passTurn(player); } );
  }

  /*
  Cell Tests
   */

  @Test
  public void testValidCellSetup() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    cell.addPawn(player);
    cell.setCard(card);
    assertEquals(card, cell.getCard());
  }

  @Test
  public void testReplaceExistingCard() {
    Influence[][] influences = new Influence[5][5];
    Card card1 = new GameCard("card1", influences, 1, 1);
    Card card2 = new GameCard("card2", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);

    cell.addPawn(player);
    cell.setCard(card1);
    cell.setCard(card2); // Should replace card1

    assertEquals(card2, cell.getCard()); // Ensure card1 is replaced
  }

  @Test
  public void testInvalidCardNull() {
    Influence[][] influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Assert.assertThrows(IllegalArgumentException.class, () -> new GameCell(null));
  }

  @Test
  public void testInvalidCellSetupNoPawns() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    Assert.assertThrows(IllegalArgumentException.class, () -> cell.setCard(card));
  }

  @Test
  public void testInvalidCellMorePawnsThanValue() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 20);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    cell.addPawn(player);
    cell.addPawn(player);
    Assert.assertThrows(IllegalArgumentException.class, () -> cell.setCard(card));
  }

  @Test
  public void testAddPawnDoesNotModifyExistingOnFailure() {
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);

    cell.addPawn(player);
    cell.addPawn(player);
    cell.addPawn(player);

    List<Pawn> before = new ArrayList<>(cell.getPawns());

    Assert.assertThrows(IllegalStateException.class, () -> cell.addPawn(player));

    assertEquals(before, cell.getPawns()); // Ensure pawns are unchanged
  }

  @Test
  public void testAddPawnToEmptyCell() {
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);

    cell.addPawn(player);

    assertEquals(1, cell.getPawns().size());
  }

  @Test
  public void testAddPawnValidDifferentPlayerNewCell() {
    Cell cell = new GameCell();
    Player player1 = new SimplePlayer(3, true);
    Player player2 = new SimplePlayer(3, false);

    cell.addPawn(player1);
    Cell anotherCell = new GameCell();
    anotherCell.addPawn(player2); // Should work fine

    assertEquals(1, cell.getPawns().size());
    assertEquals(1, anotherCell.getPawns().size());
  }

  @Test
  public void testGetPawns() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    cell.addPawn(player);
    cell.addPawn(player);
    assertEquals(2, cell.getPawns().size());
  }

  @Test
  public void testAddPawnInvalidWrongOwner() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    Player player2 = new SimplePlayer(3, false);
    cell.addPawn(player);
    Assert.assertThrows(IllegalStateException.class, () -> {
      cell.addPawn(player2); } );
  }

  @Test
  public void testAddPawnInvalid3Pawns() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    cell.addPawn(player);
    cell.addPawn(player);
    cell.addPawn(player);
    Assert.assertThrows(IllegalStateException.class, () -> {
      cell.addPawn(player); } );
  }

  @Test
  public void testSetCardInvalidNull() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Cell cell = new GameCell();
    Player player = new SimplePlayer(3, true);
    Assert.assertThrows(IllegalArgumentException.class, () -> cell.setCard(null));
  }

  /*
  Card Tests
   */

  @Test
  public void testCardSetUpValid() {
    Influence[][]  influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    assertEquals(1, card.getCost());
    assertEquals("empty", card.getName());
    assertEquals(1, card.getValue());
    assertEquals(influences, card.getInfluenceArray());
  }

  @Test
  public void testCardSetUpInvalidName() {
    Influence[][] influences = {};
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard(null, influences, 2, 1));
  }

  @Test
  public void testCardSetUpInvalidCostOverThree() {
    Influence[][] influences = {};
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", influences, 4, 1));
  }

  @Test
  public void testCardSetUpNegativeCost() {
    Influence[][] influences = {};
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", influences, -4, 1));
  }

  @Test
  public void testCardSetUpNegativeValue() {
    Influence[][] influences = {};
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", influences, 2, -4));
  }

  @Test
  public void testCardSetUpInvalidNoInfluenceArray() {
    Influence[][] influences = null;
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", influences, 1, 1));
  }

  @Test
  public void testInfluenceArrayWithInvalidRows() {
    Influence[][] influences = new Influence[4][5];
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", influences, 1, 1));
  }

  @Test
  public void testInfluenceArrayWithInvalidColumns() {
    Influence[][] invalidInfluenceArray = new Influence[5][];
    for (int i = 0; i < 5; i++) {
      invalidInfluenceArray[i] = (i == 0) ? new Influence[4] : new Influence[5];
      // First row has only 4 columns instead of 5
    }
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new GameCard("empty", invalidInfluenceArray, 1, 1));
  }

  @Test
  public void testCardInvalidGetOwner() {
    Influence[][] influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      card.getOwner(); } );
  }

  @Test
  public void testCardInvalidSetOwner() {
    Influence[][] influences = new Influence[5][5];
    Card card = new GameCard("empty", influences, 1, 1);
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      card.setOwner(null); } );
  }

  @Test
  public void testCardValidGetOwner() {
    Influence[][]  influences = new Influence[5][5];
    Player player = new SimplePlayer(3, true);
    Card card = new GameCard("empty", influences, 1, 1);
    card.setOwner(player);
    assertEquals(player, card.getOwner());
  }

  @Test
  public void testGetInfluenceArrayValid() {
    Influence[][] influences = new Influence[5][5];
    Card card = new GameCard("testCard", influences, 1, 1);

    Influence[][] result = card.getInfluenceArray();

    assertEquals(5, result.length);
    assertEquals(5, result[0].length);
  }

  /*
  Player Tests
   */

  @Test
  public void testPlayerValidInitialize() {
    Player player = new SimplePlayer(3, true);
    assertEquals(3, player.getHandSize());
    assertEquals(true, player.getIsRed());
  }

  @Test
  public void testPlayerInvalidHandSize() {
    Assert.assertThrows(IllegalArgumentException.class,() -> new SimplePlayer(0, true));
  }

  @Test
  public void testPlayerInvalidHandSizeNegative() {
    Assert.assertThrows(IllegalArgumentException.class,() -> new SimplePlayer(-2, true));
  }

  /*
  Deck Reader Tests
   */

  /**
   * Helper method to create a temporary deck file for testing.
   *
   * @param fileName The name of the temporary file.
   * @param content The content to write to the file.
   * @return The created file.
   */
  private File createTempFile(String fileName, String content) throws IOException {
    File tempFile = File.createTempFile(fileName, ".txt");
    tempFile.deleteOnExit();
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(content);
    }
    return tempFile;
  }

  @Test
  public void testDeckReader() throws IOException {
    DeckReader deckReader = new DeckReader();
    String fileContent =
            "Fireball 3 5\n" +
                    "XXXXX\n" +
                    "XIIIX\n" +
                    "XICIX\n" +
                    "XIIIX\n" +
                    "XXXXX\n" +
                    "IceShield 2 4\n" +
                    "IIIII\n" +
                    "IIIII\n" +
                    "IICII\n" +
                    "IIIII\n" +
                    "IIIII\n";

    File tempFile = createTempFile("exact_deck", fileContent);

    // Read the deck using the DeckReader method
    List<Card> deck = deckReader.readDeck(tempFile.getAbsolutePath());

    // Ensure the deck contains exactly 2 cards
    assertEquals(2, deck.size());

    assertEquals("Fireball", deck.get(0).getName());
    assertEquals("IceShield", deck.get(1).getName());

    // Verify card values
    assertEquals(5, deck.get(0).getValue());
    assertEquals(4, deck.get(1).getValue());

    // Verify card costs
    assertEquals(3, deck.get(0).getCost());
    assertEquals(2, deck.get(1).getCost());
  }

  /*
  Test View/Game
   */


  @Test
  public void setUpValidGame() {
    StringBuilder sb = new StringBuilder();
    sb.append("0 1___1 0\n"
        + "0 1___1 0\n"
        + "0 1___1 0\n");
    assertEquals(sb.toString(), view.toString());
  }

  @Test
  public void testInvalidPlayPlayTwiceInARow() {
    board.placeCard(player1, 0, 0, 0);
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeCard(player1, 0, 1, 0); } );
  }

  @Test
  public void testInvalidPlayP2PlaysFirst() {
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeCard(player2, 0, 0, 4); } );
  }

  @Test
  public void testGameOverIFPassTwice() {
    board.passTurn(player1);
    board.passTurn(player2);
    Assert.assertTrue(board.isGameOver());
  }

  @Test
  public void testPlayer2PassesFirstInvalid() {
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.passTurn(player2); } );
  }

  @Test
  public void testPlayerPassesTwiceINARowInvalid() {
    board.passTurn(player1);
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.passTurn(player1); } );
  }

  @Test
  public void testPlayerNonConsecutivePassesDoesntEndGame() {
    board.passTurn(player1);
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player1, 0, 0, 0);
    board.passTurn(player2);
    Assert.assertFalse(board.isGameOver());
  }

  @Test
  public void testPlayerPassTwiceINARowDoesntEndGame() {
    board.passTurn(player1);
    board.placeCard(player2, 0, 0, 4);
    board.passTurn(player1);
    Assert.assertFalse(board.isGameOver());
  }

  @Test
  public void testDoubleLobberChangesPawnCorrectly() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player1, 0, 1, 0);
    board.placeCard(player2, 0, 1, 4);
    assertEquals(1, board.getCell(1, 2).getPawns().size());
  }

  @Test
  public void testDoubleLobberRendersCorrectly() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player1, 0, 1, 0);
    board.placeCard(player2, 0, 1, 4);
    StringBuilder sb = new StringBuilder("1 R1_1B 1\n"
        + "1 R_1_B 1\n"
        + "0 1___1 0\n");
    assertEquals(view.toString(), sb.toString());
  }

  @Test
  public void testInvalidStartGameNotEnoughCards() {
    DeckReader reader = new DeckReader();
    String path = "docs" + File.separator + "deck.config";
    this.cardList = reader.readDeck(path);
    this.player1 = new SimplePlayer(3, true);
    player1.setDeck(cardList);
    this.player2 = new SimplePlayer(3, false);
    player2.setDeck(reader.readDeckReverse(path));
    this.board = new GameBoard(7, 7);
    this.view = new SimpleTextualView(board);
    Assert.assertThrows(IllegalArgumentException.class, () -> board.startGame(player1, player2));
  }

  @Test
  public void testCannotPutCardOnOpponentsPawns() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      board.placeCard(player1, 0, 0, 4); } );
  }

  @Test
  public void testCorrectRowScores() {
    board.placeCard(player1, 0, 0, 0);
    assertEquals(1, board.getRowScore(player1, 0));
  }

  @Test
  public void testCorrectTotalScores() {
    board.placeCard(player1, 0, 0, 0);
    board.placeCard(player2, 0, 0, 4);
    board.placeCard(player1, 0, 1, 0);
    assertEquals(1, board.getTotalScore(player1));
  }

  @Test
  public void testGetPlayerInvalidNull() {
    Assert.assertThrows(IllegalArgumentException.class,() ->  board.passTurn(null));
    Assert.assertThrows(IllegalArgumentException.class, () -> board.placeCard(null, 0, 0, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getRowScore(null, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getTotalScore(null));
  }

  @Test
  public void testPlaceCardInvalidIndex() {
    Assert.assertThrows(IllegalStateException.class, () -> {
      board.placeCard(player1, 0, 0, -1); } );
  }

  @Test
  public void testPlaceCardInvalidHandIdx() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      board.placeCard(player1, -1, 0, 0); } );
    Assert.assertThrows(IllegalArgumentException.class, () -> board.placeCard(player1, 3, 0, 0));
  }

  @Test
  public void tetGetCellInvalidIdx() {
    Assert.assertThrows(IllegalArgumentException.class, () -> {
      board.getCell( 0, -1); } );
    Assert.assertThrows(IllegalArgumentException.class, () -> board.getCell(0, 8));
  }
}
