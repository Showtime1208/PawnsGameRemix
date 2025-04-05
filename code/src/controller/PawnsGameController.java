package controller;

import javax.swing.JOptionPane;
import model.Board;
import model.Player;
import strategy.Move;
import strategy.Strategy;
import view.PawnsBoardViewInterface;

/**
 * Controller implementation for the PawnsGame. Each Player has their own specific controller, which
 * interacts with the model and the view on behalf of each player.
 */
public class PawnsGameController implements Controller {

  private final Board board;
  private final Player player;
  private final Strategy strat;
  private PawnsBoardViewInterface view;
  private int selectedRow;
  private int selectedCol;
  private int cardIdx;


  /**
   * Human constructor. Constructs the game without a strategy present, designating it as human.
   *
   * @param view   the view that will be played.
   * @param board  the board that is being played.
   * @param player the Player that the controller is representing.
   */
  public PawnsGameController(PawnsBoardViewInterface view, Board board, Player player) {
    if (view == null || board == null || player == null) {
      throw new IllegalArgumentException("View and board or player can't be null");
    }
    this.view = view;
    this.board = board;
    this.player = player;
    this.strat = null;
    this.selectedRow = -1;
    this.selectedCol = -1;
    this.cardIdx = -1;
    view.setViewListener(this);
  }


  /**
   * AI Constructor Constructs the Controller with a strategy and without listeners, designating it
   * as the AI.
   *
   * @param view   the view that will be played.
   * @param board  the board that is being played.
   * @param player the Player the AI will be playing as.
   * @param strat  Which strategy the AI will be playing as.
   */
  public PawnsGameController(PawnsBoardViewInterface view, Board board, Player player,
      Strategy strat) {
    if (view == null || board == null || player == null || strat == null) {
      throw new IllegalArgumentException("View and board or player can't be null");
    }
    this.view = view;
    this.board = board;
    this.player = player;
    this.strat = strat;
    this.selectedCol = -1;
    this.selectedRow = -1;
    this.cardIdx = -1;
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (board.isGameOver()) {
      announceGameOver();
    }
    this.selectedRow = row;
    this.selectedCol = col;
    view.refreshBoard(board);
  }

  @Override
  public void handlePassTurn() {
    if (board.isGameOver()) {
      announceGameOver();
    }
    view.refreshBoard(board);
  }

  @Override
  public void handleCardClick(int cardIndex) {
    if (board.isGameOver()) {
      announceGameOver();
    }
    this.cardIdx = cardIndex;
    view.refreshBoard(board);
  }


  @Override
  public void processCommand(String command) {
    if (command.equalsIgnoreCase("c")) {
      if (selectedRow < 0 || selectedCol < 0 || cardIdx < 0) {
        JOptionPane.showMessageDialog(null, "Please select a row, col, and"
            + " card before selecting.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      try {
        board.placeCard(player, cardIdx, selectedRow, selectedCol);
        selectedRow = -1;
        selectedCol = -1;
        cardIdx = -1;
        if (board.isGameOver()) {
          announceGameOver();
        }
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid Move."
            + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    if (command.equalsIgnoreCase("p")) {
      try {
        board.passTurn(player);
        selectedRow = -1;
        selectedCol = -1;
        cardIdx = -1;
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid Move."
            + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }

    }
    view.refreshBoard(board);
  }

  @Override
  public void processAICommand() {

    if (strat == null) {
      JOptionPane.showMessageDialog(null, "Not an AI.",
          "Error", JOptionPane.ERROR_MESSAGE);
    }
    Move move = strat.getMove(board, player);
    if (move.isPass()) {
      try {
        board.passTurn(player);
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid Move."
            + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      try {
        board.placeCard(player, move.getCardIdx(), move.getRow(), move.getCol());
      } catch (IllegalArgumentException | IllegalStateException e) {
        JOptionPane.showMessageDialog(null, "Invalid Move."
            + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    if (board.isGameOver()) {
      announceGameOver();
    }
    view.refreshBoard(board);
  }


  private void announceGameOver() {
    try {
      Player winner = board.getWinner();
      if (winner == null) {
        JOptionPane.showMessageDialog(null, "Game Over. It's A Tie!",
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
      }
      int winningScore = board.getTotalScore(player);
      if (winner == player) {
        JOptionPane.showMessageDialog(null, "Game Over."
            + " You Win! with score  "
            + winningScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null, "Game Over."
            + " You Lose! Winning Score: "
            + winningScore, "Game Over", JOptionPane.INFORMATION_MESSAGE);
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      JOptionPane.showMessageDialog(null, "Game is not over.",
          "Error", JOptionPane.ERROR_MESSAGE
      );
    }
  }

  @Override
  public void playGame() {
    new Thread(() -> {
      try {
        while (!board.isGameOver()) {
          if (board.getTurn() == player.getIsRed()) {
            if (this.strat != null) {
              processAICommand();
            } else {
              continue;
            }
          }
          Thread.sleep(200);
        }
        announceGameOver();
      } catch (IllegalArgumentException | IllegalStateException e) {
        view.displayMessage("Invalid Move.");
      } catch (InterruptedException ex) {
        view.displayMessage("?");
      }
    }).start();
  }
}
