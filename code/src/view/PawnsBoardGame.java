package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Board;
import model.Player;


/**
 * The class represents the main frame combining the game board, hand, and scoring panels into a
 * single GUI.
 */
public class PawnsBoardGame extends JFrame implements PawnsBoardViewInterface {

  private final PawnsBoardView boardView;
  private final PlayerHandView handView;
  private final ScorePanel redScorePanel;
  private final ScorePanel blueScorePanel;

  /**
   * Constructor for the game view. Takes in the model and prints out the view representation of the
   * game.
   *
   * @Param model the Board that the game will be played with.
   */
  public PawnsBoardGame(Board model, Player player) {
    super(model.getTurn() ? "red" : "blue");

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize clearly
    boardView = new PawnsBoardView(model);
    handView = new PlayerHandView(player);

    redScorePanel = new ScorePanel(model, model.getP1(), 90);
    blueScorePanel = new ScorePanel(model, model.getP2(), 90);

    // Add panels clearly aligned
    add(redScorePanel, BorderLayout.WEST);
    add(boardView, BorderLayout.CENTER);
    add(blueScorePanel, BorderLayout.EAST);
    add(handView, BorderLayout.SOUTH);
    //  add(this.commandPanel, BorderLayout.AFTER_LAST_LINE);

    pack();
    setLocationRelativeTo(boardView);
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
  public void refreshBoard(Board board) {
    // Update all panels clearly
    boardView.repaint();
    handView.updateHand(board.getP1().getHand());
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

  @Override
  public void makeVisible() {
    setVisible(true);
  }
}