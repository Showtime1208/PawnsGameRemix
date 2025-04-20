package provider.controller;

import provider.model.ModelStatus;
import provider.view.PlayerAction;

/**
 * Controller Interface for the PBController. Hey TA, this is the customer again.
 * They didn't send me this documentation and I didn't realize until too late.
 * Controller Interface. Handles and delegates all game action between the view and the model.
 */
public interface PBController extends ModelStatus, PlayerAction {

  /**
   * Begins the game of PawnsBoard, notifies the view to show the start of game message dialogue.
   */
  void startGame();
}
