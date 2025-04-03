import cs3500.queensblood.model.Deck;
import cs3500.queensblood.model.PawnsGameStructure;
import cs3500.queensblood.view.PawnsBoardFrame;
import cs3500.queensblood.controller.PawnsGameControllerStub;
import cs3500.queensblood.controller.GameController;
import cs3500.queensblood.view.BoardPanel;
import cs3500.queensblood.view.HandPanel;
import cs3500.queensblood.model.GameCard;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.util.List;

/**
 * Entry point for launching the Pawns Board application.
 * This class initializes the model, view, and controller components
 * and starts the GUI on the Swing event dispatch thread.
 */
public class PawnsBoardMain {

  /**
   * The main method sets up the game by loading decks, initializing the model,
   * and launching the GUI view with full MVC wiring.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    try {
      // Step 1: Load card data from a configuration file
      Deck loader = new Deck("docs/deck.config");
      List<GameCard> redDeck = loader.getDeck();
      List<GameCard> blueDeck = loader.getDeck();

      // Step 2: Create a new game model with a 5x5 board and hand limit of 5
      PawnsGameStructure model = new PawnsGameStructure(5, 5, redDeck, blueDeck, 5);

      // Step 3: Launch the Swing-based UI on the Event Dispatch Thread
      SwingUtilities.invokeLater(() -> {
        HandPanel handPanel = new HandPanel(model);
        BoardPanel boardPanel = new BoardPanel(model);
        GameController controller = new PawnsGameControllerStub(model, boardPanel, handPanel);
        PawnsBoardFrame frame = new PawnsBoardFrame(model, boardPanel, handPanel, controller);

        controller.startGame();  // Begin game loop and initialize view

        frame.setVisible(true); // Display the game window
      });

    } catch (IOException e) {
      // Output error message if the deck fails to load from file
      System.out.println("❌ Failed to load deck: " + e.getMessage());
    }
  }
}
