import controller.StubController;
import model.Board;
import model.GameBoard;
import model.Player;
import model.SimplePlayer;
import model.card.Card;
import model.card.DeckReader;
import view.PawnsBoardGame;

import javax.swing.SwingUtilities;
import java.io.File;
import java.util.List;

/**
 * Placeholder main class clearly instantiating and running PawnsBoardGame GUI.
 */
public final class PawnsGame {
  /**
   * Main method for the program.
   *
   * @param args the command line args(not used in this project).
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {

      Board model = createInitialBoard();

      PawnsBoardGame view = new PawnsBoardGame(model, model.getP1());
      PawnsBoardGame game = new PawnsBoardGame(model, model.getP2());

      //model.placeCard(model.getP1(), 0, 0, 0);
      //model.placeCard(model.getP2(), 0, 0, 4);
      //model.placeCard(model.getP1(), 0, 1, 0);
      //model.placeCard(model.getP2(), 0, 1, 4);

      StubController controller = new StubController();
      StubController controller2 = new StubController();

      view.setViewListener(controller);
      game.setViewListener(controller2);

      view.makeVisible();
      game.makeVisible();

    });
  }

  /**
   * Creates an example initialized Board model clearly.
   * Adjust or extend this to fit your actual game initialization logic.
   */
  private static Board createInitialBoard() {
    int boardRows = 5;
    int boardCols = 7;

    DeckReader reader = new DeckReader();
    String path = "code" + File.separator + "docs" + File.separator + "deck.config";
    List<Card> cardList = reader.readDeck(path);


    Player p1 = new SimplePlayer(5, true);
    p1.setDeck(cardList);
    Player p2 = new SimplePlayer(5, false);
    p2.setDeck(reader.readDeckReverse(path));

    Board board = new GameBoard(boardRows, boardCols);

    board.startGame(p1, p2);

    return board;
  }
}