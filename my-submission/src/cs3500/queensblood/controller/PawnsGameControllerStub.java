package cs3500.queensblood.controller;

import cs3500.queensblood.model.GameCard;
import cs3500.queensblood.model.Game;
import cs3500.queensblood.model.enumsModel.Ownership;
import cs3500.queensblood.view.BoardPanel;
import cs3500.queensblood.view.HandPanel;

/**
 * Implementation of {@link GameController} that connects
 * the {@link Game} to the Swing view components.
 * This version requires keyboard confirmation to place cards.
 */
public class PawnsGameControllerStub implements GameController {
  private final Game model;
  private final BoardPanel boardPanel;
  private final HandPanel handPanel;
  private boolean gameEnded = false;

  /**
   * Constructs a controller with the given model and view components.
   *
   * @param model       the mutable game model
   * @param boardPanel  the panel displaying the board
   * @param handPanel   the panel displaying the current player's hand
   */
  public PawnsGameControllerStub(Game model, BoardPanel boardPanel, HandPanel handPanel) {
    this.model = model;
    this.boardPanel = boardPanel;
    this.handPanel = handPanel;
  }

  @Override
  public void startGame() {
    model.startGame();
    boardPanel.repaint();
    handPanel.repaint();
  }

  @Override
  public void handleCellClick(int row, int col) {
    boardPanel.setSelectedCell(row, col);
    boardPanel.repaint();
    System.out.println("📌 Selected cell: (" + row + ", " + col + ")");
  }

  @Override
  public void handleCardSelection(int cardIndex) {
    System.out.println("🃏 Selected card index: " + cardIndex);
  }

  @Override
  public void handlePassKey() {
    System.out.println("🚫 Participant chose to pass.");
    model.passTurn();
    boardPanel.clearSelection();
    handPanel.clearSelection();
    boardPanel.repaint();
    handPanel.repaint();
    checkGameOver();
  }

  @Override
  public void handleAIMove() {

  }

  @Override
  public void handleConfirmKey() {
    GameCard selected = handPanel.getSelectedCard();
    int row = boardPanel.getSelectedRow();
    int col = boardPanel.getSelectedCol();

    if (selected == null) {
      System.out.println("⚠️ No card selected.");
      return;
    }
    if (row < 0 || col < 0) {
      System.out.println("⚠️ No cell selected.");
      return;
    }

    boolean success = model.placeCard(row, col, selected);
    if (success) {
      System.out.println("✅ Card placed at (" + row + ", " + col + ")");
      boardPanel.clearSelection();
      handPanel.clearSelection();
      boardPanel.repaint();
      handPanel.repaint();
    } else {
      System.out.println("❌ Invalid placement at (" + row + ", " + col + ")");
    }
  }


  /**
   * Checks if the game has ended. If so, announces the winner (or tie)
   * and sets gameEnded to true so no further moves occur.
   */
  private void checkGameOver() {
    if (model.isGameOver() && !gameEnded) {
      gameEnded = true; // block further moves

      Ownership winner = model.getWinner();
      if (winner == null) {
        System.out.println("🏁 Game over: It's a tie!");
      } else {
        System.out.println("🏁 Game over! Winner: " + winner.name());
      }

      // Optionally disable board/hand input:
      boardPanel.setEnabled(false);
      handPanel.setEnabled(false);
    }
  }




}
