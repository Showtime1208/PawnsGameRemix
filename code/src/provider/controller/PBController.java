package provider.controller;

import provider.model.ModelStatus;
import provider.view.PlayerAction;

public interface PBController extends ModelStatus, PlayerAction {

  /**
   * Begins the game of PawnsBoard, notifies the view to show the start of game message dialogue.
   */
  void startGame();
}
