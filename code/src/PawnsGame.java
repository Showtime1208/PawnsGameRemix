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
import provider.controller.PBControllerAdapter;
import provider.model.ModelAdapter;
import provider.model.PlayerEnum;
import provider.model.ReadonlyPawnsBoardModel;
import provider.view.PBFrame;
import provider.view.PBViewAdapter;
import strategy.BoardControlStrategy;
import strategy.FillFirstStrategy;
import strategy.MaximizeRowScoreStrategy;
import strategy.Strategy;
import view.PawnsBoardGame;
import view.PawnsBoardViewInterface;

/**
 * Main class showing how to run PawnsBoardGame (Red) and PBFrame (Blue).
 */
public final class PawnsGame {

  /**
   * Main entry point.
   * args[0] = red deck path args[1] = blue deck path args[2] = red player type (human or
   * strategy1/2/3) args[3] = blue player type (human or strategy1/2/3)
   */
  public static void main(String[] args) {
    final boolean whichGame = args[0].equals("original");
    final String redDeckPath = args[1];
    final String blueDeckPath = args[2];
    final String redPlayerType = args[3].toLowerCase();
    final String bluePlayerType = args[4].toLowerCase();
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

    //final ReadonlyPawnsBoardModel adapter = new ModelAdapter(redPlayer, bluePlayer, board);
    PawnsBoardGame redView = new PawnsBoardGame(board, redPlayer);
    PawnsBoardGame blueView = new PawnsBoardGame(board, bluePlayer);
    //PBFrame blueFrame = new PBFrame(adapter, PlayerEnum.Blue);
    //PawnsBoardViewInterface blueViewAdapter = new PBViewAdapter(blueFrame);
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
