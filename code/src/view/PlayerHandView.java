package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import model.Player;
import model.card.Card;

/**
 * A custom JPanel for displaying the current player's hand along with a command panel.
 * <p>
 * The top portion (HandDisplayPanel) shows the cards in the player's hand and supports mouse clicks
 * to select (highlighted in cyan) or unselect a card. The bottom portion (CommandPanel) provides a
 * text field and an "Execute" button where the user can enter a command:
 * <ul>
 *   <li>"c" - confirm the move</li>
 *   <li>"p" - pass the turn</li>
 * </ul>
 * When the command is executed, a message is printed to the console
 * (via the attached CommandListener).
 * </p>
 */
public class PlayerHandView extends JPanel {

  public static final int BASE_WIDTH = 800;
  public static final int BASE_HEIGHT = 180;
  // Card drawing constants
  public static final int CARD_WIDTH = 100;
  public static final int CARD_HEIGHT = 150;
  public static final int CARD_SPACING = 15;
  public static final int MAX_HAND_SIZE = 5;
  public static final int INFLUENCE_CELL_SIZE = 12;
  private final HandDisplayPanel handDisplay;
  private final CommandPanel commandPanel;
  private List<Card> playerHand;
  private int selectedCardIndx = -1;
  private HandActionListener listener;

  /**
   * Constructs the hand view with the current player's hand.
   *
   * @param player the player whose hand will be displayed.
   */
  public PlayerHandView(Player player) {
    super(new BorderLayout());
    this.playerHand = player.getHand();
    setPreferredSize(new Dimension(BASE_WIDTH, BASE_HEIGHT));
    setBackground(Color.WHITE);

    // Create sub-panels.
    handDisplay = new HandDisplayPanel(this, player);
    commandPanel = new CommandPanel();

    // Add the hand display (cards) in the center and the command panel below.
    add(handDisplay, BorderLayout.CENTER);
    add(commandPanel, BorderLayout.SOUTH);
  }

  /**
   * Updates the player's hand and repaints the hand display.
   *
   * @param newHand the updated list of cards.
   */
  public void updateHand(List<Card> newHand) {
    this.playerHand = newHand;
    handDisplay.repaint();
  }

  /**
   * Sets the selected card index and repaints the hand display.
   *
   * @param index the index of the selected card.
   */
  public void setSelectedCardIndex(int index) {
    this.selectedCardIndx = index;
    handDisplay.repaint();
  }

  /**
   * Returns the list of cards in the player's hand.
   */
  public List<Card> getPlayerHand() {
    return playerHand;
  }

  /**
   * Returns the index of the currently selected card.
   */
  public int getSelectedCardIndx() {
    return selectedCardIndx;
  }

  /**
   * Called by the HandDisplayPanel when a card is clicked.
   *
   * @param e the MouseEvent.
   */
  public void handleCardClick(MouseEvent e) {
    int clickX = e.getX();
    int clickY = e.getY();
    int cardsToDisplay = Math.min(playerHand.size(), MAX_HAND_SIZE);
    for (int i = 0; i < cardsToDisplay; i++) {
      int cardX = CARD_SPACING + i * (CARD_WIDTH + CARD_SPACING);
      int cardY = CARD_SPACING;
      if (clickX >= cardX && clickX <= cardX + CARD_WIDTH &&
          clickY >= cardY && clickY <= cardY + CARD_HEIGHT) {
        // Toggle selection: if the clicked card is already selected, unselect it.
        if (selectedCardIndx == i) {
          selectedCardIndx = -1;
        } else {
          selectedCardIndx = i;
        }
        repaint();
        //Andres added after
        if (listener != null) {
          listener.onConfirmCard(selectedCardIndx);
        }
        break;
      }
    }
  }

  /**
   * Attaches a CommandListener to the command panel.
   *
   * @param listener the CommandListener implementation.
   */
  public void addCommandListener(CommandListener listener) {
    commandPanel.addCommandListener(listener);
  }

  public void addClickListener(HandActionListener listener) {
    this.listener = listener;
  }
}
