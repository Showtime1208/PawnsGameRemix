package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import model.Player;
import model.card.Card;
import model.card.Influence;

/**
 * A panel for displaying the player's hand.
 * <p>
 * This panel draws the player's cards, highlights the selected card in cyan, and also draws the
 * influence grid for each card below the card details.
 * </p>
 */
public class HandDisplayPanel extends JPanel {

  private static final int INFLUENCE_CELL_SIZE = 12;
  private final PlayerHandView parent;
  private final Player player;

  /**
   * Constructor for the hand display panel. Is unique based on each player.
   * @param parent the parent view.
   * @param player the player's hand.
   */
  public HandDisplayPanel(PlayerHandView parent, Player player) {
    this.parent = parent;
    this.player = player;

    setPreferredSize(new Dimension(PlayerHandView.BASE_WIDTH, PlayerHandView.BASE_HEIGHT - 50));
    setBackground(Color.WHITE);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        parent.handleCardClick(e);
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int startX = PlayerHandView.CARD_SPACING;
    int startY = PlayerHandView.CARD_SPACING;
    List<Card> hand = parent.getPlayerHand();
    int selectedIndex = parent.getSelectedCardIndx();
    int cardsToDisplay = Math.min(hand.size(), PlayerHandView.MAX_HAND_SIZE);

    for (int i = 0; i < cardsToDisplay; i++) {
      int x = startX + i * (PlayerHandView.CARD_WIDTH + PlayerHandView.CARD_SPACING);
      int y = startY;
      Card card = hand.get(i);
      if (i == selectedIndex) {
        Color color = player.getIsRed() ? Color.RED : Color.BLUE;
        g2d.setColor(color);
        g2d.fillRect(x - 3, y - 3, PlayerHandView.CARD_WIDTH + 6, PlayerHandView.CARD_HEIGHT + 6);
      }

      // Draw card background.
      g2d.setColor(new Color(240, 240, 240));
      g2d.fillRect(x, y, PlayerHandView.CARD_WIDTH, PlayerHandView.CARD_HEIGHT);

      // Draw card border.
      g2d.setColor(Color.BLACK);
      g2d.drawRect(x, y, PlayerHandView.CARD_WIDTH, PlayerHandView.CARD_HEIGHT);

      // Draw card details.
      g2d.setFont(new Font("Arial", Font.BOLD, 12));
      g2d.setColor(Color.BLACK);
      g2d.drawString(card.getName(), x + 5, y + 15);
      g2d.drawString("Cost: " + card.getCost(), x + 5, y + 30);
      g2d.drawString("Value: " + card.getValue(), x + 5, y + 45);

      // Draw the influence grid for the card below the details.
      drawInfluenceGrid(g2d, card, x + 5, y + 50);
    }
    g2d.dispose();
  }

  /**
   * Draws the influence grid for the specified card.
   *
   * @param g2d    the Graphics2D context
   * @param card   the card whose influence grid is drawn
   * @param startX the x-coordinate to start drawing the grid
   * @param startY the y-coordinate to start drawing the grid
   */
  private void drawInfluenceGrid(Graphics2D g2d, Card card, int startX, int startY) {
    // Assuming card.getInfluenceArray() returns a 2D array of Influence objects.
    Influence[][] influence = card.getInfluenceArray();
    int rows = influence.length;
    int cols = influence[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int cellX = startX + c * INFLUENCE_CELL_SIZE;
        int cellY = startY + r * INFLUENCE_CELL_SIZE;
        // Highlight the middle cell with orange.
        if (r == 2 && c == 2) {
          g2d.setColor(Color.ORANGE);
        } else if (influence[r][c].getInfluence()) {
          g2d.setColor(Color.CYAN);
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
        }
        g2d.fillRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);
      }
    }
  }
}
