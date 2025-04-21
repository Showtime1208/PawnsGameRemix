import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import model.UpdatedGameBoard;
import model.Cell;
import model.Player;
import model.SimplePlayer;
import model.card.Card;
import model.card.GameCard;
import model.card.Influence;
import model.card.InfluenceKind;
import model.card.SimpleInfluence;
import org.junit.Test;
import view.UpdatedTextualView;

/**
 * Public tests that cover ONLY the *new* mechanics added in UpdatedGameBoard
 * (value modifiers, UPGRADE & DEVALUE influence kinds, and the revised
 * scoring logic which now accounts for value modifiers).
 */
public class TestUpdatedModelPublic {

  private UpdatedGameBoard board;
  private Player red;
  private Player blue;
  private UpdatedTextualView view;

  private Influence[][] blankGrid() {
    Influence[][] grid = new Influence[5][5];
    for (int r = 0; r < 5; r++) {
      for (int c = 0; c < 5; c++) {
        grid[r][c] = new SimpleInfluence(InfluenceKind.NONE);
      }
    }
    grid[2][2] = new SimpleInfluence(InfluenceKind.CLAIM);
    return grid;
  }

  private Influence[][] gridWith(int row, int col, InfluenceKind kind) {
    Influence[][] grid = blankGrid();
    grid[row][col] = new SimpleInfluence(kind);
    return grid;
  }

  private List<Card> makeDeck(int neededCards, Card... firstCards) {
    List<Card> deck = new ArrayList<>();
    for (Card c : firstCards) {
      deck.add(c);
    }
    Card filler = new GameCard("F", blankGrid(), 1, 1);
    while (deck.size() < neededCards) {
      deck.add(filler);
    }
    return deck;
  }

  private void freshOneRowBoard() {
    board = new UpdatedGameBoard(1, 3);
    view  = new UpdatedTextualView(board);
    red  = new SimplePlayer(3, true);
    blue = new SimplePlayer(3, false);
  }


  @Test
  public void testUpgradeInfluenceRaisesRowScore() {
    freshOneRowBoard();

    Card base = new GameCard("Base", blankGrid(), 1, 1);

    Card upgrade = new GameCard("Up", gridWith(2, 1, InfluenceKind.UPGRADE), 1, 1);

    red.setDeck(makeDeck(3, base, upgrade));
    blue.setDeck(makeDeck(3));

    board.startGame(red, blue);
    Cell cell01 = board.getCell(0, 1);
    cell01.addPawn(red);

    board.placeCard(red, 0, 0, 0);
    board.passTurn(blue);

    board.placeCard(red, 0, 0, 1);


    assertEquals("Modifier should be +1", 1, board.getCell(0, 0).getValueModifier());
    assertEquals(3, board.getRowScore(red, 0));
  }


  @Test
  public void testDevalueInfluenceDestroysCardAtZero() {
    freshOneRowBoard();

    Card target = new GameCard("T", blankGrid(), 1, 1);
    Card devalue = new GameCard("D", gridWith(2, 1,
        InfluenceKind.DEVALUE), 1, 1);

    red.setDeck(makeDeck(3, target));
    blue.setDeck(makeDeck(3, devalue));

    board.startGame(red, blue);

    board.getCell(0, 1).addPawn(blue);

    board.placeCard(red, 0, 0, 0);
    board.placeCard(blue, 0, 0, 1);

    assertNull("Card should have been destroyed", board.getCell(0, 0).getCard());
    assertEquals(1, board.getCell(0, 0).getPawns().size());
    assertEquals(red, board.getCell(0, 0).getPawns().get(0).getOwner());
    assertEquals("Modifier must reset", 0,
        board.getCell(0, 0).getValueModifier());
    assertEquals(0, board.getRowScore(red, 0));
  }


  @Test
  public void testRowScoreUsesValuePlusModifier() {
    freshOneRowBoard();

    Card sturdy = new GameCard("S", blankGrid(), 1, 2); // value 2
    Card devalue = new GameCard("D", gridWith(2, 1, InfluenceKind.DEVALUE),
        1, 1);

    red.setDeck(makeDeck(3, sturdy));
    blue.setDeck(makeDeck(3, devalue));

    board.startGame(red, blue);

    board.getCell(0, 1).addPawn(blue);

    board.placeCard(red, 0, 0, 0);
    board.placeCard(blue, 0, 0, 1);

    assertEquals(-1, board.getCell(0, 0).getValueModifier());
    assertEquals(1, board.getRowScore(red, 0));
  }
}
