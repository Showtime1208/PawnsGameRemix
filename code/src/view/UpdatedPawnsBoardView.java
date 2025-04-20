package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import model.Cell;
import model.ReadOnlyBoard;
import java.awt.Graphics;
import java.awt.Point;
import model.card.Card;
import model.card.Pawn;
import java.util.List;

public class UpdatedPawnsBoardView extends PawnsBoardView {
  private static final int CELL_SIZE = 100;
  private final ReadOnlyBoard board;
  private HighContrastMode highContrastMode;

  /**
   * Constructs a responsive PawnsBoardView.
   *
   * @param model the game board model
   */
  public UpdatedPawnsBoardView(ReadOnlyBoard model) {
    super(model);
    this.board = model;
    this.highContrastMode = new HighContrastMode();
  }

  /**
   * Sets the high contrast mode for this view.
   *
   * @param mode the high contrast mode to use
   */
  public void setHighContrastMode(HighContrastMode mode) {
    this.highContrastMode = mode;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    if (!highContrastMode.isEnabled()) {
      super.paintComponent(g);
      return;
    }

    Graphics2D g2 = (Graphics2D) g;
    AffineTransform original = g2.getTransform();
    double scaleX = (double) getWidth() / (board.getWidth() * CELL_SIZE);
    double scaleY = (double) getHeight() / (board.getHeight() * CELL_SIZE);
    double scale = Math.min(scaleX, scaleY);
    g2.scale(scale, scale);

    // Paint with high contrast colors
    paintHighContrast(g2);

    g2.setFont(new Font("Arial", Font.BOLD, 18));
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        Cell cell = board.getCell(row, col);
        int mod = cell.getValueModifier();
        if (mod == 0) {
          continue;
        }

        // Use high contrast colors for modifiers
        g2.setColor(mod > 0 ? highContrastMode.getPlayerColor(true) :
                highContrastMode.getPlayerColor(false));

        String string = (mod > 0 ? "+" : "-") + mod;
        int x = col * CELL_SIZE + CELL_SIZE - 28;
        int y = row * CELL_SIZE + CELL_SIZE - 8;
        g2.drawString(string, x, y);
      }
    }
    g2.setTransform(original);
  }

  /**
   * Paints the board using high contrast colors.
   *
   * @param g2 the graphics context
   */
  private void paintHighContrast(Graphics2D g2) {
    // Fill background with black
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, getWidth(), getHeight());

    // Draw cells with high contrast colors
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        Cell cell = board.getCell(row, col);
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;

        // Determine if cell is highlighted
        boolean isHighlighted = isHighlighted(row, col);

        // Get appropriate colors from high contrast mode
        Color bgColor = highContrastMode.getCellBackgroundColor(isHighlighted);

        // Draw cell background
        g2.setColor(bgColor);
        g2.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        g2.setColor(Color.GRAY);
        g2.drawRect(x, y, CELL_SIZE, CELL_SIZE);

        // Draw cell contents
        drawCellContents(g2, cell, x, y, isHighlighted);
      }
    }
  }

  /**
   * Draws the contents of a cell with appropriate high contrast colors.
   *
   * @param g2 the graphics context
   * @param cell the cell to draw
   * @param x the x coordinate
   * @param y the y coordinate
   * @param isHighlighted whether the cell is highlighted
   */
  private void drawCellContents(Graphics2D g2, Cell cell, int x, int y, boolean isHighlighted) {
    Card card = cell.getCard();
    List<Pawn> pawns = cell.getPawns();

    g2.setFont(new Font("Arial", Font.BOLD, 16));

    if (card != null) {
      // Draw card
      boolean isRed = card.getOwner().getIsRed();
      Color playerColor = highContrastMode.getPlayerColor(isRed);
      g2.setColor(playerColor);
      g2.fillRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);

      // Draw card text
      g2.setColor(Color.BLACK); // Always black text on player color
      String cardInfo = String.format("%s (%d)", card.getName(), card.getValue());
      g2.drawString(cardInfo, x + 10, y + CELL_SIZE/2);
    } else if (!pawns.isEmpty()) {
      // Draw pawns
      boolean isRed = pawns.get(0).getOwner().getIsRed();
      g2.setColor(highContrastMode.getPlayerColor(isRed));
      String pawnInfo = String.format("Pawns: %d", pawns.size());
      g2.drawString(pawnInfo, x + 10, y + CELL_SIZE/2);
    } else {
      // Empty cell - use white text on black background
      g2.setColor(Color.WHITE);
      g2.drawString("Empty", x + 10, y + CELL_SIZE/2);
    }
  }

  /**
   * Checks if a cell is currently highlighted.
   *
   * @param row the row to check
   * @param col the column to check
   * @return true if the cell is highlighted, false otherwise
   */
  private boolean isHighlighted(int row, int col) {
    Point highlightedCell = getHighlightedCell();
    return highlightedCell != null &&
            highlightedCell.x == col &&
            highlightedCell.y == row;
  }

  /**
   * Gets the currently highlighted cell from the parent class.
   *
   * @return the Point representing the highlighted cell, or null if no cell is highlighted
   */
  private Point getHighlightedCell() {
    try {
      java.lang.reflect.Field field = PawnsBoardView.class.getDeclaredField("highlightedCell");
      field.setAccessible(true);
      return (Point) field.get(this);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Gets the underlying board model.
   *
   * @return the board model
   */
  public ReadOnlyBoard getBoard() {
    return board;
  }
}