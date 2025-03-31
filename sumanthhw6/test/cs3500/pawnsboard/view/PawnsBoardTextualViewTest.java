package cs3500.pawnsboard.view;

import org.junit.Before;

import java.io.File;
import java.util.List;

import cs3500.pawnsboard.model.CardUse;
import cs3500.pawnsboard.model.DeckReader;
import cs3500.pawnsboard.model.PawnsBoardBuilder;
import cs3500.pawnsboard.model.PawnsBoardModel;
import cs3500.pawnsboard.model.Player;


/**
 * Testing the Text view of the game.
 */
public class PawnsBoardTextualViewTest {

  PawnsBoardModel gameModel;
  PawnsBoardTextualView textualView;

  @Before
  public void setUp() {
    List<CardUse> deck = DeckReader.readDeck("docs" + File.separator + "deck.config");

    gameModel = new PawnsBoardBuilder().setBlueDeck(deck).
            setRedDeck(deck).setNumCols(5).setNumRows(3).
            build();
    gameModel.startGame(Player.RED, 2);
    textualView = new PawnsBoardTextualViewImpl(gameModel);
  }

}
