import controller.PawnsGameController;
import java.util.List;
import javax.swing.SwingUtilities;
import model.Board;
import model.GameBoard;
import model.Player;
import model.SimplePlayer;
import model.card.Card;
import model.card.DeckReader;
import strategy.BoardControlStrategy;
import strategy.FillFirstStrategy;
import strategy.MaximizeRowScoreStrategy;
import strategy.Strategy;
import view.PawnsBoardGame;

/**
 * Placeholder main class clearly instantiating and running PawnsBoardGame GUI.
 */
public final class PawnsGame {

  /**
   * Main method for the program.
   *
   * @param args the command line args.
   */
  public static void main(String[] args) {
    final String redDeckPath = args[0];
    final String blueDeckPath = args[1];
    final String redPlayerType = args[2].toLowerCase();
    final String bluePlayerType = args[3].toLowerCase();
    final DeckReader reader = new DeckReader();
    final List<Card> redDeck = reader.readDeck(redDeckPath);
    final List<Card> blueDeck = reader.readDeckReverse(blueDeckPath);
    final Player redPlayer = new SimplePlayer(5, true);
    final Player bluePlayer = new SimplePlayer(5, false);
    redPlayer.setDeck(redDeck);
    bluePlayer.setDeck(blueDeck);
    final Board board = new GameBoard(5, 7);
    board.startGame(redPlayer, bluePlayer);
    PawnsBoardGame redView = new PawnsBoardGame(board, redPlayer);
    PawnsBoardGame blueView = new PawnsBoardGame(board, bluePlayer);
    final Strategy redStrategy = getStrat(redPlayerType);
    final Strategy blueStrategy = getStrat(bluePlayerType);
    PawnsGameController redController;
    if (redStrategy == null) {
      redController = new PawnsGameController(redView, board, redPlayer);
      redView.setViewListener(redController);
    } else {
      redController = new PawnsGameController(redView, board, redPlayer, redStrategy);
    }

    PawnsGameController blueController;
    if (blueStrategy == null) {
      blueController = new PawnsGameController(blueView, board, bluePlayer);
      blueView.setViewListener(blueController);
    } else {
      blueController = new PawnsGameController(blueView, board, bluePlayer, blueStrategy);
    }
    SwingUtilities.invokeLater(() -> {
      redView.makeVisible();
      blueView.makeVisible();

      if (redStrategy != null) {
        System.out.println("Red: " + redStrategy.getClass().getSimpleName());
        redController.playGame();
      }
      if (blueStrategy != null) {
        blueController.playGame();
      }
    });
  }


  private static Strategy getStrat(String type) {
    switch (type) {
      case "human":
        return null;
      case "strategy1":
        return new FillFirstStrategy();
      case "strategy2":
        return new MaximizeRowScoreStrategy();
      case "strategy3":
        return new BoardControlStrategy();
      default:
        System.err.println("Invalid strategy type");
        return null;
    }
  }

}