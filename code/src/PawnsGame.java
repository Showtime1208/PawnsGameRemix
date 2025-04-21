import controller.PawnsGameController;
import java.util.List;
import javax.swing.SwingUtilities;
import model.Board;
import model.GameBoard;
import model.Player;
import model.SimplePlayer;
import model.UpdatedGameBoard;
import model.card.Card;
import model.card.DeckReader;
import strategy.BoardControlStrategy;
import strategy.FillFirstStrategy;
import strategy.MaximizeRowScoreStrategy;
import strategy.Strategy;
import view.AccessiblePawnsBoardGame;
import view.PawnsBoardGame;


/**
 * Main class showing how to run PawnsBoardGame.
 */
public final class PawnsGame {

  /**
   * Main method. Runs the program.
   * @param args the command line args.
   */
  public static void main(String[] args) {
    if (args.length < 6) {
      System.err.println(
          "Usage: PawnsGame <game_type> <view_mode> <red_deck> <blue_deck> <red_player> "
              + "<blue_player>");
      System.err.println(
          "Example: PawnsGame original accessible code/docs/deck.config "
              + "code/docs/deck.config human human");
      return;
    }

    final boolean whichGame = args[0].equals("original");
    final boolean accessibleMode = args[1].equals("accessible");
    final String redDeckPath = args[2];
    final String blueDeckPath = args[3];
    final String redPlayerType = args[4].toLowerCase();
    final String bluePlayerType = args[5].toLowerCase();

    final DeckReader reader = new DeckReader();
    final List<Card> redDeck = reader.readDeck(redDeckPath);
    final List<Card> blueDeck = reader.readDeckReverse(blueDeckPath);

    final Player redPlayer = new SimplePlayer(5, true);
    final Player bluePlayer = new SimplePlayer(5, false);
    redPlayer.setDeck(redDeck);
    bluePlayer.setDeck(blueDeck);
    final Board board = whichGame ? new GameBoard(5, 7)
        : new UpdatedGameBoard(5, 7);
    board.startGame(redPlayer, bluePlayer);

    // Create appropriate views based on accessibility mode
    PawnsBoardGame redView;
    PawnsBoardGame blueView;
    if (accessibleMode) {
      redView = new AccessiblePawnsBoardGame(board, redPlayer);
      blueView = new AccessiblePawnsBoardGame(board, bluePlayer);
      // Toggle high contrast for both views
      ((AccessiblePawnsBoardGame) redView).getHighContrastMode().toggle();
      ((AccessiblePawnsBoardGame) blueView).getHighContrastMode().toggle();
    } else {
      redView = new PawnsBoardGame(board, redPlayer);
      blueView = new PawnsBoardGame(board, bluePlayer);
    }

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
        System.out.println("Red is " + redStrategy.getClass().getSimpleName());
        redController.playGame();
      }

      if (blueStrategy != null) {
        System.out.println("Blue is " + blueStrategy.getClass().getSimpleName());
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