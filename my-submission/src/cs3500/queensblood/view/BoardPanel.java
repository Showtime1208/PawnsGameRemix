package cs3500.queensblood.view;

import cs3500.queensblood.model.ImmutableTile;
import cs3500.queensblood.model.ImmutableGame;
import cs3500.queensblood.model.enumsModel.Ownership;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * A graphical component for rendering the game board of Queensblood.
 * Displays each cell’s content, visualizes player ownership, cards, pawns, and row scores,
 * and tracks user selection via mouse interaction.
 */
public class BoardPanel extends JPanel {
  private final ImmutableGame model;
  private final Map<Rectangle, Point> cellBounds = new HashMap<>();
  private int selectedRow = -1;
  private int selectedCol = -1;

  /**
   * Constructs a new board panel tied to the given immutable game model.
   * Sets up mouse input to enable tile selection.
   *
   * @param model the read-only view of the game state
   */
  public BoardPanel(ImmutableGame model) {
    this.model = model;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int rows = model.getNumRows();
        int cols = model.getNumCols();
        int cellWidth = getWidth() / (cols + 2);
        int cellHeight = getHeight() / rows;

        int correctedX = e.getX() - cellWidth;

        if (correctedX < 0 || correctedX >= (cols * cellWidth)) {
          System.out.println("Clicked outside the board area!");
          return;
        }

        int col = correctedX / cellWidth;
        int row = e.getY() / cellHeight;

        if (row < 0 || row >= rows) {
          System.out.println("Clicked outside the board vertically!");
          return;
        }

        System.out.println("Clicked on cell: (" + row + ", " + col + ")");
        setSelectedCell(row, col);
      }
    });
  }

  /**
   * Paints the game grid, cards, pawns, and score areas using the provided graphics context.
   *
   * @param g the graphics object for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    cellBounds.clear();

    int rows = model.getNumRows();
    int cols = model.getNumCols();

    if (rows <= 0 || cols <= 0) {
      throw new IllegalStateException("Invalid board dimensions in model.");
    }

    int cellWidth = getWidth() / (cols + 2);
    int cellHeight = getHeight() / rows;

    for (int row = 0; row < rows; row++) {
      for (int c = 0; c < cols; c++) {
        if (row >= 0 && row < rows && c >= 0 && c < cols) {
          int x = (c + 1) * cellWidth;
          int y = row * cellHeight;

          ImmutableTile cell;
          try {
            cell = model.getCell(row, c);
          } catch (IllegalArgumentException e) {
            System.err.println("Skipping invalid cell at [" + row + "][" + c + "]");
            continue;
          }

          if (cell.containsCard()) {
            Color bg;
            switch (cell.tileOwner()) {
              case RED:
                bg = new Color(255, 220, 220);
                break;
              case BLUE:
                bg = new Color(220, 220, 255);
                break;
              default:
                bg = Color.LIGHT_GRAY;
                break;
            }
            g2.setColor(bg);
            g2.fillRect(x + 1, y + 1, cellWidth - 2, cellHeight - 2);

            g2.setColor(Color.BLACK);
            String val = String.valueOf(cell.getCard().getValueScore());
            g2.drawString(val, x + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
          } else if (cell.participantCount() > 0) {
            g2.setColor(cell.tileOwner() == Ownership.RED ? Color.PINK : Color.CYAN);
            drawPawns(g2, cell.tileOwner(), cell.participantCount(), x, y, cellWidth, cellHeight);
          } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 1, y + 1, cellWidth - 2, cellHeight - 2);
          }

          if (row == selectedRow && c == selectedCol) {
            g2.setColor(new Color(255, 255, 0, 120));
            g2.fillRect(x + 1, y + 1, cellWidth - 2, cellHeight - 2);
          }

          g2.setColor(Color.BLACK);
          g2.drawRect(x, y, cellWidth, cellHeight);
        }
      }

      int y = row * cellHeight;

      // Left score panel
      g2.setColor(Color.LIGHT_GRAY);
      g2.fillRect(0, y, cellWidth, cellHeight);
      g2.setColor(Color.BLACK);
      g2.drawRect(0, y, cellWidth, cellHeight);
      g2.drawString(String.valueOf(model.getRowScore(row, Ownership.RED)), cellWidth / 2 - 4, y + cellHeight / 2 + 4);

      // Right score panel
      int rightX = (cols + 1) * cellWidth;
      g2.setColor(Color.LIGHT_GRAY);
      g2.fillRect(rightX, y, cellWidth, cellHeight);
      g2.setColor(Color.BLACK);
      g2.drawRect(rightX, y, cellWidth, cellHeight);
      g2.drawString(String.valueOf(model.getRowScore(row, Ownership.BLUE)), rightX + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
    }
  }

  /**
   * Draws pawns on a tile using colored circles based on owner and quantity.
   */
  private void drawPawns(Graphics2D g2, Ownership owner, int count, int x, int y, int cellWidth, int cellHeight) {
    int size = Math.min(cellWidth, cellHeight) / 4;
    int cx = x + cellWidth / 2;
    int cy = y + cellHeight / 2;

    g2.setColor(owner == Ownership.RED ? Color.PINK : Color.CYAN);

    switch (count) {
      case 1:
        g2.fillOval(cx - size / 2, cy - size / 2, size, size);
        break;
      case 2:
        int spacing = size + 4;
        g2.fillOval(cx - spacing / 2 - size, cy - size / 2, size, size);
        g2.fillOval(cx + spacing / 2, cy - size / 2, size, size);
        break;
      case 3:
        int offset = size + 2;
        g2.fillOval(cx - offset, cy, size, size);
        g2.fillOval(cx + offset - size, cy, size, size);
        g2.fillOval(cx - size / 2, cy - offset, size, size);
        break;
      default:
        g2.fillOval(cx - size / 2, cy - size / 2, size, size);
        break;
    }
  }

  /**
   * Sets the currently selected cell and triggers a repaint for highlight update.
   */
  public void setSelectedCell(int row, int col) {
    this.selectedRow = row;
    this.selectedCol = col;
    repaint();
  }

  /**
   * @return the row index of the currently selected cell, or -1 if none
   */
  public int getSelectedRow() {
    return selectedRow;
  }

  /**
   * @return the column index of the currently selected cell, or -1 if none
   */
  public int getSelectedCol() {
    return selectedCol;
  }

  /**
   * Deselects any selected cell and refreshes the panel.
   */
  public void clearSelection() {
    selectedRow = -1;
    selectedCol = -1;
    repaint();
  }
}
