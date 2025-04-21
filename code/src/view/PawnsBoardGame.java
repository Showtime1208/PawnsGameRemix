package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.Board;
import model.Player;
import view.HighContrastMode;

/**
 * The class represents the main frame combining the game board, hand, and scoring panels into a
 * single GUI.
 */
public class PawnsBoardGame extends JFrame implements PawnsBoardViewInterface {
  private final UpdatedPawnsBoardView boardView;
  private final PlayerHandView handView;
  private final ScorePanel redScorePanel;
  private final ScorePanel blueScorePanel;
  private HighContrastMode highContrastMode = new HighContrastMode();  // Add this line
  private final Player currentPlayer;

  /**
   * Constructor for the game view. Takes in the model and prints out the view representation of the
   * game.
   *
   * @param model the Board that the game will be played with.
   * @param player the Player this view represents
   */
  public PawnsBoardGame(Board model, Player player) {
    super(model.getTurn() ? "red" : "blue");
    this.currentPlayer = player;
    this.highContrastMode = new HighContrastMode();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize clearly
    boardView = new UpdatedPawnsBoardView(model);
    boardView.setHighContrastMode(highContrastMode);
    handView = new PlayerHandView(player);
    handView.setHighContrastMode(highContrastMode);

    redScorePanel = new ScorePanel(model, model.getP1(), 90);
    blueScorePanel = new ScorePanel(model, model.getP2(), 90);

    // Create menu bar with high contrast toggle
    createMenuBar();

    // Add panels clearly aligned
    add(redScorePanel, BorderLayout.WEST);
    add(boardView, BorderLayout.CENTER);
    add(blueScorePanel, BorderLayout.EAST);
    add(handView, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(boardView);
  }

  /**
   * Creates the menu bar with accessibility options.
   */
  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu accessibilityMenu = new JMenu("Accessibility");
    accessibilityMenu.setMnemonic(KeyEvent.VK_A);

    JMenuItem highContrastItem = new JMenuItem("Toggle High Contrast");
    highContrastItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
            java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

    highContrastItem.addActionListener(e -> {
      highContrastMode.toggle();
      boardView.repaint();
      handView.repaint();
      refreshBoard((Board)boardView.getModel(), currentPlayer);
    });

    accessibilityMenu.add(highContrastItem);
    menuBar.add(accessibilityMenu);
    setJMenuBar(menuBar);
  }

  @Override
  public void setViewListener(PawnsBoardViewListener listener) {
    boardView.addClickListener(listener::handleCellClick);
    handView.addClickListener(new HandActionListener() {
      @Override
      public void onConfirmCard(int selectedCardIndex) {
        listener.handleCardClick(selectedCardIndex);
      }

      @Override
      public void onPassTurn() {
        listener.handlePassTurn();
      }
    });
    handView.addCommandListener((CommandListener) listener);
  }

  @Override
  public void refreshBoard(Board board, Player player) {
    // Update all panels clearly
    boardView.repaint();
    handView.updateHand(player.getHand());
    redScorePanel.repaint();
    blueScorePanel.repaint();
    this.setTitle(board.getTurn() ? "red" : "blue");
    this.repaint();
  }

  @Override
  public void highlightCell(int row, int col) {
    boardView.highlightCell(row, col);
  }

  @Override
  public void highlightCard(int cardId) {
    handView.setSelectedCardIndex(cardId);
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
  /**
   * Gets the high contrast mode object.
   *
   * @return the HighContrastMode object
   */
  public HighContrastMode getHighContrastMode() {
    return highContrastMode;
  }
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Gets the current high contrast mode state.
   *
   * @return true if high contrast mode is enabled, false otherwise
   */
  public boolean isHighContrastEnabled() {
    return highContrastMode.isEnabled();
  }
}