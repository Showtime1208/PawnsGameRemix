package provider.controller;

import cs3500.pawnsboard.model.hw07.ModelStatus;
import cs3500.pawnsboard.view.hw07.PlayerAction;

public interface PBController extends ModelStatus, PlayerAction {

  /**
   * Begins the game of PawnsBoard, notifies the view to show the start of game message dialogue.
   */
  void startGame();
}
