package provider.controller;

import controller.PawnsGameController;
import model.Board;
import model.Player;
import provider.model.PlayerEnum;

public class PBControllerAdapter implements PBController {

  private final PawnsGameController controller;
  private final Board board;
  private final Player player;
  private final PlayerEnum me;

  public PBControllerAdapter(PawnsGameController controller, Board board, Player player, PlayerEnum me) {
    this.controller = controller;
    this.board = board;
    this.player = player;
    this.me = me;
  }

  @Override
  public void startGame() {

  }

  @Override
  public void yourTurn() {

  }

  @Override
  public void gameOver() {

  }

  @Override
  public void refreshView() {

  }

  @Override
  public void moveChosen(int handIdx, int row, int col) {

  }

  @Override
  public void passTurn() {

  }
}
