package cs3500.queensblood.view;

import cs3500.queensblood.controller.GameController;
import cs3500.queensblood.model.ImmutableGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Represents the main game window for the Pawns Board application.
 * This frame hosts the board and hand panels and handles keyboard input
 * to interact with the controller.
 */
public class PawnsBoardFrame extends JFrame implements GameView {
  private GameController controller;
  private ImmutableGame model;

  /**
   * Constructs a new game frame with the specified model, panels, and controller.
   * Initializes layout and sets up keyboard bindings for gameplay interaction.
   *
   * @param model      the read-only game model to be visualized
   * @param boardPanel the panel responsible for rendering the game board
   * @param handPanel  the panel displaying the player's hand
   * @param controller the controller coordinating view-model interactions
   */
  public PawnsBoardFrame(ImmutableGame model,
                         BoardPanel boardPanel,
                         HandPanel handPanel,
                         GameController controller) {
    super("Pawns Board Game");
    this.controller = controller;
    this.model = model;

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    boardPanel.setPreferredSize(new Dimension(800, 600));
    add(boardPanel, BorderLayout.CENTER);
    add(handPanel, BorderLayout.SOUTH);

    setupKeyListeners();

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * Sets up key bindings for player actions.
   * <ul>
   *   <li>Pressing 'P' will pass the player's turn</li>
   *   <li>Pressing 'Enter' will confirm a selected move</li>
   * </ul>
   * Delegates input to the game controller.
   */
  private void setupKeyListeners() {
    this.getRootPane().setFocusable(true);
    this.getRootPane().requestFocusInWindow();

    this.getRootPane().addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
          System.out.println("Pass key pressed");
          controller.handlePassKey();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          System.out.println("Confirm key pressed");
          controller.handleConfirmKey();
        }
      }
    });
  }

  /**
   * Requests a UI repaint or refresh.
   * Currently unused but implemented for future compatibility.
   */
  @Override
  public void refresh() {
    // Stub for future use
  }

  /**
   * Displays a game-over message to the user.
   * Currently a placeholder.
   *
   * @param message the message to show when the game ends
   */
  @Override
  public void showGameOver(String message) {
    // Stub for future use
  }

  /**
   * Makes the game window visible.
   * Currently handled in constructor; placeholder for controller use.
   */
  @Override
  public void makeVisible() {
    // Stub for future use
  }
}
