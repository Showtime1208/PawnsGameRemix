package provider.controller;

import controller.PawnsGameController;
import model.Board;
import model.Player;
import provider.model.PlayerEnum;

/**
 * Adapter Class for the PBController. Adapts the PawnsGameController methods in order to create
 * compatibility with the provider view.
 */
public class PBControllerAdapter implements PBController {

  private final PawnsGameController controller;
  private final Board board;
  private final Player player;

  /**
   * Creates a new controller adapter.
   *
   * @param controller our game controller
   * @param board      the game board
   * @param player     the player this controller represents
   * @param me         the player enum (Red/Blue) from provider's implementation
   * @throws IllegalArgumentException if any argument is null
   */

  public PBControllerAdapter(PawnsGameController controller, Board board, Player player,
      PlayerEnum me) {
    if (controller == null || board == null || player == null || me == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.controller = controller;
    this.board = board;
    this.player = player;
  }

  @Override
  public void startGame() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void yourTurn() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void gameOver() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void refreshView() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void moveChosen(int handIdx, int row, int col) {
    if (board.getTurn() == player.getIsRed()) {
      controller.handleCardClick(handIdx);
      controller.handleCellClick(row, col);
      controller.processCommand("c");
    }
  }

  @Override
  public void passTurn() {
    if (board.getTurn() == player.getIsRed()) {
      controller.processCommand("p");
    }
  }
}