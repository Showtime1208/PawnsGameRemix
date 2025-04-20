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
import model.card.InfluenceKind;

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
   *
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

    HighContrastMode highContrastMode = parent.getHighContrastMode();
    boolean isHighContrast = highContrastMode != null && highContrastMode.isEnabled();

    setBackground(isHighContrast ? Color.BLACK : Color.WHITE);

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
        Color highlightColor;
        if (isHighContrast) {
          highlightColor = highContrastMode.getPlayerColor(player.getIsRed());
        } else {
          highlightColor = player.getIsRed() ? Color.RED : Color.BLUE;
        }
        g2d.setColor(highlightColor);
        g2d.fillRect(x - 3, y - 3, PlayerHandView.CARD_WIDTH + 6, PlayerHandView.CARD_HEIGHT + 6);
      }

      g2d.setColor(isHighContrast ? Color.BLACK : new Color(240, 240, 240));
      g2d.fillRect(x, y, PlayerHandView.CARD_WIDTH, PlayerHandView.CARD_HEIGHT);

      g2d.setColor(isHighContrast ? Color.WHITE : Color.BLACK);
      g2d.drawRect(x, y, PlayerHandView.CARD_WIDTH, PlayerHandView.CARD_HEIGHT);

      g2d.setFont(new Font("Arial", Font.BOLD, 12));
      g2d.setColor(isHighContrast ? Color.WHITE : Color.BLACK);
      g2d.drawString(card.getName(), x + 5, y + 15);
      g2d.drawString("Cost: " + card.getCost(), x + 5, y + 30);
      g2d.drawString("Value: " + card.getValue(), x + 5, y + 45);

      drawInfluenceGrid(g2d, card, x + 5, y + 50, isHighContrast, highContrastMode);
    }
    g2d.dispose();
  }

  private void drawInfluenceGrid(Graphics2D g2d, Card card, int startX, int startY,
                                 boolean isHighContrast, HighContrastMode highContrastMode) {
    Influence[][] influence = card.getInfluenceArray();
    int rows = influence.length;
    int cols = influence[0].length;

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int cellX = startX + c * INFLUENCE_CELL_SIZE;
        int cellY = startY + r * INFLUENCE_CELL_SIZE;

        Color cellColor;
        if (r == 2 && c == 2) {
          // Center cell
          cellColor = isHighContrast ? Color.YELLOW : Color.ORANGE;
        } else {
          InfluenceKind kind = influence[r][c].getInfluenceKind();
          if (isHighContrast) {
            switch (kind) {
              case CLAIM:
                cellColor = highContrastMode.getPlayerColor(player.getIsRed());
                break;
              case UPGRADE:
                cellColor = Color.GREEN;
                break;
              case DEVALUE:
                cellColor = Color.RED;
                break;
              default:
                cellColor = Color.DARK_GRAY;
                break;
            }
          } else {
            cellColor = InfluenceLegend.colorOf(kind);
          }
        }

        // Draw cell background
        g2d.setColor(cellColor);
        g2d.fillRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);

        // Draw cell border
        g2d.setColor(isHighContrast ? Color.WHITE : Color.BLACK);
        g2d.drawRect(cellX, cellY, INFLUENCE_CELL_SIZE, INFLUENCE_CELL_SIZE);
      }
    }
  }

  @Override
  public void repaint() {
    if (parent != null && parent.getHighContrastMode() != null
            && parent.getHighContrastMode().isEnabled()) {
      setBackground(Color.BLACK);
    } else {
      setBackground(Color.WHITE);
    }
    super.repaint();
  }
}