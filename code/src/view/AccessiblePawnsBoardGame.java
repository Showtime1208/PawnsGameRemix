package view;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import model.Board;
import model.Player;

/**
 * Accessible version of PawnsBoardGame that implements high contrast mode
 * and other accessibility features.
 */
public class AccessiblePawnsBoardGame extends PawnsBoardGame {
  private final HighContrastMode highContrastMode;

  /**
   * Constructor for the accessible game view.
   *
   * @param model the Board that the game will be played with
   * @param player the Player this view represents
   */
  public AccessiblePawnsBoardGame(Board model, Player player) {
    super(model, player);
    this.highContrastMode = new HighContrastMode();
    setupAccessibilityFeatures();
  }

  private void setupAccessibilityFeatures() {
    createAccessibilityMenu();
    if (boardView instanceof UpdatedPawnsBoardView) {
      ((UpdatedPawnsBoardView) boardView).setHighContrastMode(highContrastMode);
    }
    handView.setHighContrastMode(highContrastMode);
  }

  private void createAccessibilityMenu() {
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

  /**
   * Gets the high contrast mode object.
   *
   * @return the HighContrastMode object
   */
  public HighContrastMode getHighContrastMode() {
    return highContrastMode;
  }
}