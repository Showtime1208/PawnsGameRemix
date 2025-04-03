package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JPanel;
import model.Player;
import model.ReadOnlyBoard;
import model.card.Card;
import model.card.Pawn;

/**
 * JPanel for rendering a responsive Pawns game board.
 */
public class PawnsBoardView extends JPanel {

  private static final int CELL_SIZE = 100;
  private static final int CARD_PADDING = 10;
  private static final int PAWN_SIZE = 20;
  private static final int PAWN_SPACING = 5;
  private final ReadOnlyBoard model;
  private double scaleFactor;
  private Point highlightedCell;
  private CellClickListener cellClickListener;

  /**
   * Constructs a responsive PawnsBoardView.
   */
  public PawnsBoardView(ReadOnlyBoard model) {
    this.model = model;
    this.highlightedCell = null;

    setPreferredSize(new Dimension(model.getWidth() * CELL_SIZE, model.getHeight() * CELL_SIZE));

    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        updateScaleFactor();
      }
    });

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        processClick(e);
      }
    });

    updateScaleFactor();
  }

  private void updateScaleFactor() {
    double scaleX = (double) getWidth() / (model.getWidth() * CELL_SIZE);
    double scaleY = (double) getHeight() / (model.getHeight() * CELL_SIZE);
    scaleFactor = Math.min(scaleX, scaleY);
    repaint();
  }

  private void processClick(MouseEvent e) {
    int row = (int) (e.getY() / (CELL_SIZE * scaleFactor));
    int col = (int) (e.getX() / (CELL_SIZE * scaleFactor));

    if (row >= 0 && row < model.getHeight() && col >= 0 && col < model.getWidth()) {
      // If the cell is already highlighted, unhighlight it.
      if (highlightedCell != null && highlightedCell.equals(new Point(col, row))) {
        highlightedCell = null;
      } else {
        highlightedCell = new Point(col, row);
      }
      repaint();
      if (cellClickListener != null) {
        cellClickListener.onCellClicked(row, col);
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    AffineTransform original = g2d.getTransform();
    g2d.scale(scaleFactor, scaleFactor);

    drawGrid(g2d);
    drawBoardContent(g2d);

    g2d.setTransform(original);
  }

  private void drawGrid(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(1.5f));

    for (int i = 0; i <= model.getHeight(); i++) {
      g2d.drawLine(0, i * CELL_SIZE, model.getWidth() * CELL_SIZE, i * CELL_SIZE);
    }
    for (int i = 0; i <= model.getWidth(); i++) {
      g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, model.getHeight() * CELL_SIZE);
    }
  }

  private void drawBoardContent(Graphics2D g2d) {
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        drawCellHighlight(g2d, row, col);
        drawCardIfPresent(g2d, row, col);
        drawPawnsIfPresent(g2d, row, col);
      }
    }
  }

  private void drawCellHighlight(Graphics2D g2d, int row, int col) {
    if (highlightedCell != null && highlightedCell.equals(new Point(col, row))) {
      g2d.setColor(new Color(173, 216, 230, 120));
      g2d.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
  }

  public void highlightCell(int row, int col) {
    highlightedCell = new Point(col, row);
    repaint();
  }

  private void drawCardIfPresent(Graphics2D g2d, int row, int col) {
    Card card = model.getCardAt(row, col);
    if (card != null) {
      int x = col * CELL_SIZE + CARD_PADDING;
      int y = row * CELL_SIZE + CARD_PADDING;
      int width = CELL_SIZE - 2 * CARD_PADDING;
      int height = (2 * CELL_SIZE / 3) - CARD_PADDING;

      g2d.setColor(playerColor(card.getOwner()));
      g2d.fillRect(x, y, width, height);
    }
  }

  private void drawPawnsIfPresent(Graphics2D g2d, int row, int col) {
    List<Pawn> pawns = model.getPawnsAt(row, col);
    if (!pawns.isEmpty()) {
      int startX = col * CELL_SIZE + PAWN_SPACING;
      int startY = row * CELL_SIZE + (2 * CELL_SIZE / 3) + PAWN_SPACING;
      int pawnsPerRow = 3;

      for (int i = 0; i < pawns.size(); i++) {
        int px = startX + (i % pawnsPerRow) * (PAWN_SIZE + PAWN_SPACING);
        int py = startY + (i / pawnsPerRow) * (PAWN_SIZE + PAWN_SPACING);

        g2d.setColor(playerColor(pawns.get(i).getOwner()));
        g2d.fillOval(px, py, PAWN_SIZE, PAWN_SIZE);
      }
    }
  }

  private Color playerColor(Player player) {
    if (player == null) {
      return Color.GRAY;
    }
    return player.getIsRed() ? Color.RED : Color.BLUE;
  }

  public void addClickListener(CellClickListener listener) {
    this.cellClickListener = listener;
  }
}


