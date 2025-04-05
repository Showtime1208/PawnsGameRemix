package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import model.Player;
import model.ReadOnlyBoard;

/**
 * Panel representing the user's score. A new object is created for each player.
 */
public class ScorePanel extends JPanel {

  private static final Font SCORE_FONT = new Font("arial", Font.BOLD, 14);
  private final ReadOnlyBoard model;
  private final Player player;

  /**
   * Constrcts a score panel.
   *
   * @param model  the read only game board model
   * @param player the player this panel represents
   */

  public ScorePanel(ReadOnlyBoard model, Player player, int rowHeight) {
    this.model = model;
    this.player = player;

    int panelHeight =
        (model.getHeight()) * rowHeight + rowHeight;  // Include extra row for Total Score clearly
    setPreferredSize(new Dimension(100, panelHeight));
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int width = getWidth();
    int height = getHeight();
    int rows = model.getHeight();

    int rowHeight = height / (rows + 1);  // one extra for total score
    int fontSize = rowHeight / 3;         // dynamically sized font
    Font dynamicFont = new Font("arial", Font.BOLD, fontSize);
    g2d.setFont(dynamicFont);
    FontMetrics fm = g2d.getFontMetrics();

    // Draw total score clearly
    String cumulativeScore = String.valueOf(model.getTotalScore(player));
    int totalWidth = fm.stringWidth(cumulativeScore);
    g2d.setColor(player.getIsRed() ? Color.RED : Color.BLUE);
    g2d.drawString(cumulativeScore, (width - totalWidth) / 2, fm.getAscent() + 5);

    // Clearly draw dynamic row scores
    for (int row = 0; row < rows; row++) {
      String scoreText = String.valueOf(model.getRowScore(player, row));
      double yPosition = (row + 0.5) * rowHeight;

      double textWidth = fm.stringWidth(scoreText);
      double textX = (width - textWidth) / 2;
      double textY = yPosition + (rowHeight - fm.getHeight()) / 2 + fm.getAscent();

      g2d.drawString(scoreText, (int) textX, (int) textY);
    }

    g2d.dispose();
  }
}