package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import model.Cell;
import model.ReadOnlyBoard;

/**
 * Class For the UpdatedGameBoard Visually represents the game.
 */
public class ModifierOverlayBoardView extends UpdatedPawnsBoardView {

  private static final int CELL_SIZE = 100;

  /**
   * Constructor for the view.
   *
   * @param board the board.
   */
  public ModifierOverlayBoardView(ReadOnlyBoard board) {
    super(board);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (getHighContrastMode().isEnabled()) {
      return;
    }

    Graphics2D g2 = (Graphics2D) g.create();
    g2.scale(getScaleFactor(), getScaleFactor());
    g2.setFont(new Font("Arial", Font.BOLD, 18));

    ReadOnlyBoard board = getBoard();
    for (int r = 0; r < board.getHeight(); r++) {
      for (int c = 0; c < board.getWidth(); c++) {
        Cell cell = board.getCell(r, c);
        int mod = cell.getValueModifier();
        if (mod == 0) {
          continue;
        }

        g2.setColor(mod > 0 ? new Color(0, 128, 0)
            : new Color(160, 30, 240));

        String label = (mod > 0 ? "+" : "") + mod;
        int x = c * CELL_SIZE + CELL_SIZE - 28;
        int y = r * CELL_SIZE + CELL_SIZE - 8;
        g2.drawString(label, x, y);
      }
    }
    g2.dispose();
  }


}
