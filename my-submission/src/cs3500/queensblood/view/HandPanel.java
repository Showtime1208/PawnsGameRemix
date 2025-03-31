package cs3500.queensblood.view;

import cs3500.queensblood.model.GameCard;
import cs3500.queensblood.model.ImmutableGame;
import cs3500.queensblood.model.enumsModel.InfluenceType;
import cs3500.queensblood.model.enumsModel.Ownership;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Visual component responsible for rendering the current player's hand of cards.
 * Displays card metadata and influence patterns, and handles card selection via mouse interaction.
 */
public class HandPanel extends JPanel {
  private final ImmutableGame model;
  private int selectedIndex = -1;

  /**
   * Creates a new hand panel that displays cards for the current player and tracks selection events.
   *
   * @param model a read-only reference to the game state
   */
  public HandPanel(ImmutableGame model) {
    this.model = model;

    setPreferredSize(new Dimension(0, 200));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        List<GameCard> hand = model.getHand(model.getCurrentPlayer());
        int cardWidth = getWidth() / Math.max(1, hand.size());
        int index = e.getX() / cardWidth;
        if (index >= 0 && index < hand.size()) {
          selectedIndex = (selectedIndex == index) ? -1 : index;
          repaint();
        }
      }
    });
  }

  /**
   * Renders the hand panel, including each card’s information and its influence grid.
   * Visually distinguishes the selected card and adapts colors based on player ownership.
   *
   * @param g the graphics context to draw onto
   */
  @Override
  protected void paintComponent(Graphics g) {
    Ownership currentOwner = model.getCurrentPlayer().getColor();
    if (currentOwner == Ownership.RED) {
      setBackground(new Color(255, 220, 220));
    } else if (currentOwner == Ownership.BLUE) {
      setBackground(new Color(220, 220, 255));
    } else {
      setBackground(Color.LIGHT_GRAY);
    }

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    List<GameCard> hand = model.getHand(model.getCurrentPlayer());
    int numCards = hand.size();
    if (numCards == 0) return;

    int cardWidth = getWidth() / numCards;
    int cardHeight = getHeight();

    for (int i = 0; i < numCards; i++) {
      GameCard card = hand.get(i);
      int x = i * cardWidth;
      int y = 0;

      g2.setColor(i == selectedIndex ? Color.CYAN :
              currentOwner == Ownership.RED ? new Color(255, 200, 200) : new Color(200, 200, 255));
      g2.fillRect(x, y, cardWidth, cardHeight);

      g2.setColor(Color.BLACK);
      g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
      FontMetrics fm = g2.getFontMetrics();
      int lineHeight = fm.getHeight();
      int textYStart = y + 10 + lineHeight;
      int centerX = x + cardWidth / 2;

      g2.drawString(card.getName(), centerX - fm.stringWidth(card.getName()) / 2, textYStart);
      g2.drawString("Cost: " + card.getCost(), centerX - fm.stringWidth("Cost: " + card.getCost()) / 2, textYStart + lineHeight);
      g2.drawString("Value: " + card.getValueScore(), centerX - fm.stringWidth("Value: " + card.getValueScore()) / 2, textYStart + lineHeight * 2);

      InfluenceType[][] grid = card.getInfluences();
      int gridSize = 5;
      int cellSize = Math.min(cardWidth, cardHeight - 60) / gridSize;
      int gridX = x + (cardWidth - gridSize * cellSize) / 2;
      int gridY = y + cardHeight - gridSize * cellSize - 10;

      for (int r = 0; r < gridSize; r++) {
        for (int c = 0; c < gridSize; c++) {
          InfluenceType type = grid[r][c];
          Color color;
          switch (type) {
            case NONE:
              color = Color.DARK_GRAY;
              break;
            case CENTER:
              color = Color.ORANGE;
              break;
            case INFLUENCE:
              color = Color.CYAN;
              break;
            default:
              color = Color.BLACK;
          }

          g2.setColor(color);
          g2.fillRect(gridX + c * cellSize, gridY + r * cellSize, cellSize, cellSize);
          g2.setColor(Color.BLACK);
          g2.drawRect(gridX + c * cellSize, gridY + r * cellSize, cellSize, cellSize);
        }
      }
    }
  }

  /**
   * Gets the card that is currently selected by the user, if any.
   *
   * @return the selected card, or null if none is selected
   */
  public GameCard getSelectedCard() {
    if (selectedIndex == -1) return null;
    List<GameCard> hand = model.getHand(model.getCurrentPlayer());
    if (selectedIndex >= hand.size()) return null;
    return hand.get(selectedIndex);
  }

  /**
   * Clears the current selection and triggers a repaint.
   */
  public void clearSelection() {
    selectedIndex = -1;
    repaint();
  }
}
