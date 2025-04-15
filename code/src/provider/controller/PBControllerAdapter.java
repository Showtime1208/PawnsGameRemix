package provider.controller;

import controller.PawnsGameController;

public class PBControllerAdapter implements PBController {

  public final PawnsGameController controller;

  public PBControllerAdapter(PawnsGameController controller) {
    this.controller = controller;
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
