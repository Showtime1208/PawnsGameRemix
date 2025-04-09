package provider.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import provider.model.Player;
import provider.model.ReadonlyPawnsBoardModel;
import provider.view.PlayerAction;

/**
 * GUI view for Pawns Board.
 */
public class PBFrame extends JFrame implements PBGUIView {

  private PBPanel panel;

  /**
   * Creates a GUI for PawnsBoard.
   */
  public PBFrame(ReadonlyPawnsBoardModel model, Player player) {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = new PBPanel(model, player);
    this.setSize(model.getWidth() * 100 + 210, model.getHeight() * 100 + 140);
    this.add(panel);
    panel.requestFocusInWindow();
  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    this.panel.repaint();
  }

  @Override
  public void addListener(PlayerAction observer) {
    this.panel.addPlayerActionObserver(observer);
  }

  /**
   * Makes the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * sets the focus of the JFrame.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void showMessage(String msg) {
    this.panel.showMessage(msg);
  }
}
