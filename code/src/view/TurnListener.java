package view;

public interface TurnListener {

  /**
   * Listener called whenever the turn switches to the next player.
   * @param isRed
   */
  void onTurnChange(boolean isRed);

}
