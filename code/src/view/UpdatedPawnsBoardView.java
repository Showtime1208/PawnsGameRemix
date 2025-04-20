package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import model.Cell;
import model.ReadOnlyBoard;
import java.awt.Graphics;

public class UpdatedPawnsBoardView extends PawnsBoardView {
  private static final int CELL_SIZE = 100;
  private final ReadOnlyBoard board;
  /**
   * Constructs a responsive PawnsBoardView.
   *
   * @param model
   */

  public UpdatedPawnsBoardView(ReadOnlyBoard model) {
    super(model);
    this.board = model;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    AffineTransform original = g2.getTransform();
    double scaleX = (double) getWidth() / (board.getWidth() * CELL_SIZE);
    double scaleY = (double) getHeight() / (board.getHeight() * CELL_SIZE);
    double scale = Math.min(scaleX, scaleY);
    g2.scale(scale, scale);
    super.paintComponent(g2);
    g2.setFont(new Font("Arial", Font.BOLD, 18));
    for (int row = 0; row < board.getHeight(); row++) {
      for (int col = 0; col < board.getWidth(); col++) {
        Cell cell = board.getCell(row, col);
        int mod = cell.getValueModifier();
        if (mod == 0) {
          continue;
        }
        g2.setColor(mod > 0 ? new Color(0, 128,0) :
            new Color (160, 32, 240));
        String string = (mod > 0 ? "+" : "-") + mod;
        int x = col * CELL_SIZE +  CELL_SIZE - 28;
        int y = row * CELL_SIZE +  CELL_SIZE - 8;
        g2.drawString(string, x, y);
      }
    }
    g2.setTransform(original);
  }
}
