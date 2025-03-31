package view;

/**
 * Interface representing the listener for the user's hand. It confirms what
 * card in the hand is selected, and what the hand does on a passed turn.
 */
public interface HandActionListener {
  /**
  * Called when the user confirms a move for the selected card.
  * @param selectedCardIndex the index of the card confirmed.
  */
  void onConfirmCard(int selectedCardIndex);

  /**
  * Called when the user decides to pass their turn.
  */
  void onPassTurn();
}