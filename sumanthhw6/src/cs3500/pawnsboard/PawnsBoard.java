package cs3500.pawnsboard;

import java.io.File;
import java.io.IOException;

import cs3500.pawnsboard.model.CardUse;
import cs3500.pawnsboard.model.DeckReader;
import cs3500.pawnsboard.model.PawnsBoardBuilder;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.view.PawnsBoardTextualView;
import cs3500.pawnsboard.view.PawnsBoardTextualViewImpl;

/**
 * The class to play a game of PawnsBoard.
 */
public class PawnsBoard {

  /**
   * The runner for a game of PawnsBoard.
   * @throws IOException if there is something wrong with the input or
   *                     output.
   */
  public static void main(String[] args) throws IOException {
    String path = "docs" + File.separator + "deck.config";
    PawnsBoardModel gameModel = new PawnsBoardBuilder().setBlueDeck(
            DeckReader.readDeck(path)).
            setRedDeck(DeckReader.readDeck(path))
            .setNumCols(5).setNumRows(3).
            build();

    gameModel.startGame(Player.RED, 5);
    PawnsBoardTextualView view = new PawnsBoardTextualViewImpl(gameModel);

    gameModel.placeCardInPosition(1, 1, 0);
    view.render(System.out);
    gameModel.placeCardInPosition(1, 0, 4);
    view.render(System.out);
    gameModel.passCurrentTurn();
    view.render(System.out);
    gameModel.passCurrentTurn();
    view.render(System.out);
  }
}
